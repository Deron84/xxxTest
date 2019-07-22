Ext.onReady(function() {
	var roleLevelData;
	// 权限级别
	var roleLevelStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	// 权限级别
	SelectOptionsDWR.getComboData('BRH_LVL',function(ret){
		roleLevelStore.loadData(Ext.decode(ret));
	});
	
	//显示当前用户及其以下角色级别
	if(oprLevel == '0') {
		roleLevelData = [
			['0','总行'],
			['1','分行'],
			['2','支行']
		];
	} else if(oprLevel == '1') {
		roleLevelData = [
			['1','分行'],
			['2','支行']
		];
	} else if(oprLevel == '2') {
		roleLevelData = [
			['2','支行']
		];
	}
	
	var roleGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=roleInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'roleId'
		},[
			{name: 'roleId',mapping: 'roleId'},
			{name: 'roleName',mapping: 'roleName'},
			{name: 'roleType',mapping: 'roleType'},
			{name: 'description',mapping: 'description'},
			{name: 'recUpdOpr',mapping: 'recUpdOpr'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'recUpdTs',mapping: 'recUpdTs'}
		])
	});
	
	roleGridStore.load({
		params:{start: 0}
	});
	
	
	var roleColModel = new Ext.grid.ColumnModel([
		{id: 'roleId',header: '角色编码',dataIndex: 'roleId',width: 70,sortable: true,hidden: false},
		{header: '角色名称',dataIndex: 'roleName',width: 120,sortable: true,
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			blankText: '角色名称不能为空',
			maxLength: 64,
			vtype: 'isOverMax'
		 })},
		//{header: '角色级别',dataIndex: 'roleType',renderer:roleLevel},
		{header: '角色描述',dataIndex: 'description',width: 357,id:'description',sortable: true,
		 editor: new Ext.form.TextField({
		 	allowBlank: true,
			maxLength: 1024,
			vtype: 'isOverMax'
		 })},
		{header: '最后更新操作员',sortable: true,dataIndex: 'recUpdOpr'},
		{header: '创建时间',dataIndex: 'recCrtTs',renderer:formatTs,sortable: true,width: 145},
		{header: '最后更新时间',dataIndex: 'recUpdTs',renderer:formatTs,sortable: true,width: 145}
	]);
	
	/**
	 * 角色级别
	 */
	function roleLevel(val) {
		switch(val) {
			case '0' : return '总行';
			case '1' : return '分行';
			case '2' : return '支行';
		}
	}
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			roleWin.show();
			roleWin.center();
		}
	};
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(roleGrid.getSelectionModel().hasSelection()) {
				var rec = roleGrid.getSelectionModel().getSelected();
				
				showConfirm('确定要删除该条角色信息吗？',roleGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10301Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,roleGrid);
									roleGrid.getStore().reload();
									roleGrid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,roleGrid);
								}
							},
							params: { 
								roleId: rec.get('roleId'),
								txnId: '10301',
								subTxnId: '02'
							}
						});
					}
				});
			}
		}
	};
	var upMenu = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
			var modifiedRecords = roleGrid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
//			showProcessMsg('正在保存角色信息，请稍后......');
			//存放要修改的机构信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var description = record.get('description');
				if(description != null && description != '' && description.length > 500){
					showErrorMsg("描述信息超过最大限制",roleGrid);
					return;
				}
				var data = {
					roleId : record.get('roleId'),
					roleName : record.get('roleName'),
					roleType: record.get('roleType'),
					description: description,
					recCrtTs: record.get('recCrtTs')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10301Action.asp?method=update',
				method: 'post',
				params: {
					roleInfoList: Ext.encode(array),
					txnId: '10301',
					subTxnId: '03'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					roleGrid.enable();
					if(rspObj.success) {
						roleGrid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,roleGrid);
					} else {
						roleGrid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,roleGrid);
					}
					roleGrid.getTopToolbar().items.items[4].disable();
					roleGrid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	var edit = {
		text: '编辑角色',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler: function() {
			editRoleWin.show();
			editRoleWin.center();
		}
	};
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);
	menuArr.push('-');
	menuArr.push(edit);
	
	
	// 角色列表
	var roleGrid = new Ext.grid.EditorGridPanel({
		title: '角色维护',
		iconCls: 'T103',
		region:'center',
		frame: true,
		border: true,
		autoExpandColumn:'description',
		columnLines: true,
		stripeRows: true,
		store: roleGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: roleColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载角色信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: roleGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	roleGrid.getStore().on('beforeload',function() {
		roleGrid.getTopToolbar().items.items[4].disable();
		roleGrid.getStore().rejectChanges();
	});
	
	roleGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(roleGrid.getTopToolbar().items.items[4] != undefined) {
				roleGrid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	
	roleGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(roleGrid.getView().getRow(roleGrid.getSelectionModel().last)).frame();
			//激活菜单
			roleGrid.getTopToolbar().items.items[2].enable();
			roleGrid.getTopToolbar().items.items[6].enable();
		}
	});
	
	// 一级菜单数据集
	var menuLvl1Store = new Ext.data.JsonStore({
		root: 'data',
		fields: ['valueField','displayField']
	});
	
	SelectOptionsDWR.getComboData('MENU_LEVEL_1',function(ret) {
		menuLvl1Store.loadData(Ext.decode(ret));
	});	
	
	// 二级菜单数据库集
	var menuLvl2Store = new Ext.data.JsonStore({
		root: 'data',
		fields: ['valueField','displayField']
	});
	
	// 三级菜单数据集
	var menuLvl3Store = new Ext.data.JsonStore({
		root: 'data',
		fields: ['valueField','displayField']
	});
	
	// 已选菜单数据集
	var selectedMenuStore = new Ext.data.JsonStore({
		root: 'data',
		fields: ['valueField','displayField','op']
	});
	
	var menuLvl1Col = new Ext.grid.ColumnModel([
		{id: 'valueField',header: '菜单编码',hidden: true,dataIndex: 'valueField'},
		{header: '<center>一级菜单</center>',dataIndex: 'displayField',width: 100,menuDisabled: true,resizable: false,align: 'center'}
	]);
	
	var menuLvl2Col = new Ext.grid.ColumnModel([
		{id: 'valueField',header: '菜单编码',hidden: true,dataIndex: 'valueField'},
		{header: '<center>二级菜单</center>',dataIndex: 'displayField',width: 150,menuDisabled: true,resizable: false,align: 'center'}
	]);
	
	var menuLvl3Col = new Ext.grid.ColumnModel([
		{id: 'valueField',header: '菜单编码',hidden: true,dataIndex: 'valueField'},
		{header: '<center>三级菜单</center>',dataIndex: 'displayField',width: 240,menuDisabled: true,resizable: false,align: 'center'}
	]);
	
	var selectedCol = new Ext.grid.ColumnModel([
		{id: 'valueField',header: '菜单编码',hidden: true,dataIndex: 'valueField'},
		{header: '<center>已选菜单</center>',dataIndex: 'displayField',width: 240,menuDisabled: true,resizable: false,align: 'center'},
		{header: '',dataIndex: 'op',width: 35,menuDisabled: true,resizable: false,align: 'center',
		 renderer: renderDelete}
	]);
	
	function renderDelete(val) {
		return '<img src="' + Ext.contextPath + '/ext/resources/images/recycle.png" ' +
				       		'title="删除此菜单" onclick=deleteMenu('+ val + ') onmouseover="this.style.cursor=\'pointer\'"/>';
	}
	
	window.deleteMenu = function(val) {
		var rec = selectedMenuGrid.getStore().getById(val);
		selectedMenuGrid.getStore().remove(rec);
	}
	
	/**
	 * 两个gridpanel之间切换焦点
	 */
	function changeFocus(blurGrid,focusGrid) {
		blurGrid.getView().focusEl.blur();
		focusGrid.getView().focusEl.focus();
		if(focusGrid.lastSelectedRowIndex == -1) {
			focusGrid.getSelectionModel().selectFirstRow();
		} else {
			focusGrid.getSelectionModel().selectRow(focusGrid.lastSelectedRowIndex);
		}
		Ext.get(focusGrid.getView().getRow(focusGrid.lastSelectedRowIndex)).frame();
	}
	
	
	// 一级菜单列表
	var menuLvl1Grid = new Ext.grid.GridPanel({
		store: menuLvl1Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		height: 300,
		width: 120,
		cm: menuLvl1Col,
		keys: [{
			key: Ext.EventObject.RIGHT,
			handler: function() {
				changeFocus(menuLvl1Grid,menuLvl2Grid);
			}
		}],
		lastSelectedRowIndex: -1
	});
	
	menuLvl1Grid.getSelectionModel().on('rowselect',function() {
		if(menuLvl1Grid.lastSelectedRowIndex != menuLvl1Grid.getSelectionModel().last) {
			var func_parent_id = menuLvl1Grid.getSelectionModel().getSelected().get('valueField');
			menuLvl2Grid.getStore().removeAll();
			SelectOptionsDWR.getComboDataWithParameter('MENU_LEVEL',func_parent_id,function(ret) {
				menuLvl2Store.loadData(Ext.decode(ret));
			});
			menuLvl1Grid.lastSelectedRowIndex = menuLvl1Grid.getSelectionModel().last;
			menuLvl2Grid.lastSelectedRowIndex = -1;
		}
	})
	
	// 二级菜单列表
	var menuLvl2Grid = new Ext.grid.GridPanel({
		store: menuLvl2Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		height: 300,
		width: 170,
		cm: menuLvl2Col,
		loadMask: {
			msg: '正在加载二级菜单信息......'
		},
		keys: [{
			key: Ext.EventObject.LEFT,
			handler: function() {
				changeFocus(menuLvl2Grid,menuLvl1Grid);
			}
		},{
			key: Ext.EventObject.RIGHT,
			handler: function() {
				changeFocus(menuLvl2Grid,menuLvl3Grid);
			}
		}],
		lastSelectedRowIndex: -1
	});
	
	menuLvl2Grid.getSelectionModel().on('rowselect',function() {
		if(menuLvl2Grid.lastSelectedRowIndex != menuLvl2Grid.getSelectionModel().last) {
			var func_parent_id = menuLvl2Grid.getSelectionModel().getSelected().get('valueField');
			menuLvl3Grid.getStore().removeAll();
			SelectOptionsDWR.getComboDataWithParameter('MENU_LEVEL',func_parent_id,function(ret) {
				menuLvl3Store.loadData(Ext.decode(ret));
			});
			menuLvl2Grid.lastSelectedRowIndex = menuLvl2Grid.getSelectionModel().last;
			menuLvl3Grid.lastSelectedRowIndex = -1;
		}
	});
	
	// 三级菜单列表
	var menuLvl3Grid = new Ext.grid.GridPanel({
		store: menuLvl3Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		height: 300,
		width: 220,
		cm: menuLvl3Col,
		loadMask: {
			msg: '正在加载三级菜单信息......'
		},
		keys: [{
			key: Ext.EventObject.LEFT,
			handler: function() {
				changeFocus(menuLvl3Grid,menuLvl2Grid);
			}
		},{
			key: Ext.EventObject.ENTER,
			handler: addSelectedMenu
		}],
		lastSelectedRowIndex: -1,
		ddGroup: 'menu',
		enableDragDrop: true
	});
	
	menuLvl3Grid.getSelectionModel().on('rowselect',function() {
		if(menuLvl3Grid.lastSelectedRowIndex != menuLvl3Grid.getSelectionModel().last) {
			menuLvl3Grid.lastSelectedRowIndex = menuLvl3Grid.getSelectionModel().last;
		}
	})
	
	menuLvl3Grid.on('rowdblclick',addSelectedMenu);
	
	var SelectedMenuRecord = new Ext.data.Record.create([
		{name: 'valueField',type: 'string'},
		{name: 'displayField',type: 'string'},
		{name: 'op',type: 'string'}
	]);
	
	function addSelectedMenu() {
		var rec = menuLvl3Grid.getSelectionModel().getSelected();
		var itemIndex = selectedMenuGrid.getStore().findExact("valueField",rec.data.valueField);
		if(itemIndex == -1) {
			var selectedRec = new SelectedMenuRecord();
			selectedRec.id = rec.get('valueField');
			selectedRec.set('valueField',rec.get('valueField'));
			selectedRec.set('displayField',rec.get('displayField'));
			selectedRec.set('op',rec.get('valueField'));
			selectedMenuGrid.getStore().add(selectedRec);
			selectedMenuGrid.getStore().sort('valueField','asc');
		}
	}
	
	// 已选菜单列表
	var selectedMenuGrid = new Ext.grid.GridPanel({
		store: selectedMenuStore,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		height: 300,
		width: 310,
		cm: selectedCol
	});
	
	selectedMenuGrid.on('render',function() {
		var selectedMenuDDEL = selectedMenuGrid.getView().el.dom;
		var selectedMenuDDTarget = new Ext.dd.DropTarget(selectedMenuDDEL,{
			ddGroup: 'menu',
			notifyDrop: function(ddSource,e,data) {
				function addRow(record,index,allItem) {
					var itemIndex = selectedMenuGrid.getStore().findExact("valueField",record.data.valueField);
					if(itemIndex == -1) {
						var selectedRec = new SelectedMenuRecord();
						selectedRec.id = record.get('valueField');
						selectedRec.set('valueField',record.get('valueField'));
						selectedRec.set('displayField',record.get('displayField'));
						selectedRec.set('op',record.get('valueField'));
						selectedMenuGrid.getStore().add(selectedRec);
						selectedMenuGrid.getStore().sort("valueField","ASC");
					}
				}
				Ext.each(ddSource.dragData.selections,addRow);
				return (true);
			}
		});
	});
	
	// 角色添加表单
	var roleInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 850,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '角色名称*',
			allowBlank: false,
			blankText: '角色名称不能为空',
			emptyText: '请输入角色名称',
			id: 'roleName',
			name: 'roleName',
			width: 200,
			maxLength: 64,
			vtype: 'isOverMax'
		},{
			fieldLabel: '角色描述',
			vtype: 'isOverMax',
			id: 'description',
			name: 'description',
			maxLength: 500,
			width: 300
		},new Ext.Panel({
			title: '<center>请为该角色选择权限</center>',
			layout: 'table',
			items: [menuLvl1Grid,menuLvl2Grid,menuLvl3Grid,selectedMenuGrid]
		})]
	});
	
	// 角色添加窗口
	var roleWin = new Ext.Window({
		title: '角色添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 850,
		autoHeight: true,
		layout: 'fit',
		items: [roleInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(roleInfoForm.getForm().isValid()) {
					if(selectedMenuGrid.getStore().getCount() == 0) {
						showConfirm('您没有为该角色选择任何菜单信息，确定要提交吗？',selectedMenuGrid,function(bt) {
							if(bt == 'yes') {
								addSubmit();
							}
						})
					} else {
						addSubmit();
					}
				}
			}
		},{
			text: '重置',
			handler: addFormReset
		},{
			text: '关闭',
			handler: function() {
				roleWin.hide(roleGrid);
			}
		}]
	});
	
	// 添加角色提交表单
	function addSubmit() {
		var menuArray = new Array();
		
		for(var i = 0,n = selectedMenuGrid.getStore().getCount(); i < n; i++) {
			var rec = selectedMenuGrid.getStore().getAt(i);
			var data = {
				valueId: rec.get('valueField')
			};
			menuArray.push(data);
		}
		
		roleInfoForm.getForm().submitNeedAuthorise({
			url: 'T10301Action.asp?method=add',
			waitMsg: '正在提交，请稍后......',
			success: function(form,action) {
				showSuccessMsg(action.result.msg,roleInfoForm);
				//重置表单
				roleInfoForm.getForm().reset();
				//重新加载列表
				roleGrid.getStore().reload();
				// 重置表单
				addFormReset();
			},
			failure: function(form,action) {
				showErrorMsg(action.result.msg,roleInfoForm);
			},
			params: {
				txnId: '10301',
				subTxnId: '01',
				menuList: Ext.encode(menuArray)
			}
		});
	}
	
	// 重置表单
	function addFormReset() {
		roleInfoForm.getForm().reset();
		selectedMenuGrid.getStore().removeAll();
	}
	
	
	
	
	/******************************************************编辑菜单信息********************************************************/
	
	// 一级菜单数据集
	var editMenuLvl1Store = new Ext.data.JsonStore({
		root: 'data',
		fields: ['valueField','displayField']
	});
	
	SelectOptionsDWR.getComboData('MENU_LEVEL_1',function(ret) {
		editMenuLvl1Store.loadData(Ext.decode(ret));
	});	
	
	// 二级菜单数据库集
	var editMenuLvl2Store = new Ext.data.JsonStore({
		root: 'data',
		fields: ['valueField','displayField']
	});
	
	// 三级菜单数据集
	var editMenuLvl3Store = new Ext.data.JsonStore({
		root: 'data',
		fields: ['valueField','displayField']
	});
	
	// 已选菜单数据集
	var editSelectedMenuStore = new Ext.data.JsonStore({
		root: 'data',
		fields: ['valueField','displayField','op']
	});
	
	var editSelectedCol = new Ext.grid.ColumnModel([
		{id: 'valueField',header: '菜单编码',hidden: true,dataIndex: 'valueField'},
		{header: '<center>已选菜单</center>',dataIndex: 'displayField',width: 240,menuDisabled: true,resizable: false,align: 'center'},
		{header: '',dataIndex: 'op',width: 35,menuDisabled: true,resizable: false,align: 'center',
		 renderer: editRenderDelete}
	]);
	
	function editRenderDelete(val) {
		return '<img src="' + Ext.contextPath + '/ext/resources/images/recycle.png" ' +
				       		'title="删除此菜单" onclick=editDeleteMenu(' + val + ') onmouseover="this.style.cursor=\'pointer\'"/>';
	}
	
	window.editDeleteMenu = function(val) {
		var rec = editSelectedMenuGrid.getStore().getById(val);
		editSelectedMenuGrid.getStore().remove(rec);
	}
	
	// 一级菜单列表
	var editMenuLvl1Grid = new Ext.grid.GridPanel({
		store: editMenuLvl1Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		height: 300,
		width: 120,
		cm: menuLvl1Col,
		loadMask: {
			msg: '正在加载一级菜单信息......'
		},
		keys: [{
			key: Ext.EventObject.RIGHT,
			handler: function() {
				changeFocus(editMenuLvl1Grid,editMenuLvl2Grid);
			}
		}],
		lastSelectedRowIndex: -1
	});
	
	editMenuLvl1Grid.getSelectionModel().on('rowselect',function() {
		if(editMenuLvl1Grid.lastSelectedRowIndex != editMenuLvl1Grid.getSelectionModel().last) {
			var func_parent_id = editMenuLvl1Grid.getSelectionModel().getSelected().get('valueField');
			editMenuLvl2Grid.getStore().removeAll();
			SelectOptionsDWR.getComboDataWithParameter('MENU_LEVEL',func_parent_id,function(ret) {
				editMenuLvl2Grid.getStore().loadData(Ext.decode(ret));
			});
			editMenuLvl1Grid.lastSelectedRowIndex = editMenuLvl1Grid.getSelectionModel().last;
			editMenuLvl2Grid.lastSelectedRowIndex = -1;
		}
	})
	
	// 二级菜单列表
	var editMenuLvl2Grid = new Ext.grid.GridPanel({
		store: editMenuLvl2Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		height: 300,
		width: 170,
		cm: menuLvl2Col,
		loadMask: {
			msg: '正在加载二级菜单信息......'
		},
		keys: [{
			key: Ext.EventObject.LEFT,
			handler: function() {
				changeFocus(editMenuLvl2Grid,editMenuLvl1Grid);
			}
		},{
			key: Ext.EventObject.RIGHT,
			handler: function() {
				changeFocus(editMenuLvl2Grid,editMenuLvl3Grid);
			}
		}],
		lastSelectedRowIndex: -1
	});
	
	editMenuLvl2Grid.getSelectionModel().on('rowselect',function() {
		if(editMenuLvl2Grid.lastSelectedRowIndex != editMenuLvl2Grid.getSelectionModel().last) {
			var func_parent_id = editMenuLvl2Grid.getSelectionModel().getSelected().get('valueField');
			editMenuLvl3Grid.getStore().removeAll();
			SelectOptionsDWR.getComboDataWithParameter('MENU_LEVEL',func_parent_id,function(ret) {
				editMenuLvl3Grid.getStore().loadData(Ext.decode(ret));
			});
			editMenuLvl2Grid.lastSelectedRowIndex = editMenuLvl2Grid.getSelectionModel().last;
			editMenuLvl3Grid.lastSelectedRowIndex = -1;
		}
	})
	
	// 三级菜单列表
	var editMenuLvl3Grid = new Ext.grid.GridPanel({
		store: editMenuLvl3Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		height: 300,
		width: 220,
		cm: menuLvl3Col,
		loadMask: {
			msg: '正在加载三级菜单信息......'
		},
		keys: [{
			key: Ext.EventObject.LEFT,
			handler: function() {
				changeFocus(editMenuLvl3Grid,editMenuLvl2Grid);
			}
		},{
			key: Ext.EventObject.ENTER,
			handler: editAddSelectedMenu
		}],
		lastSelectedRowIndex: -1,
		ddGroup: 'editMenu',
		enableDragDrop: true
	});
	
	editMenuLvl3Grid.getSelectionModel().on('rowselect',function() {
		if(editMenuLvl3Grid.lastSelectedRowIndex != editMenuLvl3Grid.getSelectionModel().last) {
			editMenuLvl3Grid.lastSelectedRowIndex = editMenuLvl3Grid.getSelectionModel().last;
		}
	})
	
	editMenuLvl3Grid.on('rowdblclick',editAddSelectedMenu);
	
	function editAddSelectedMenu() {
		var rec = editMenuLvl3Grid.getSelectionModel().getSelected();
		var itemIndex = editSelectedMenuGrid.getStore().findExact("valueField",rec.data.valueField);
		if(itemIndex == -1) {
			var selectedRec = new SelectedMenuRecord();
			selectedRec.id = rec.get('valueField');
			selectedRec.set('valueField',rec.get('valueField'));
			selectedRec.set('displayField',rec.get('displayField'));
			selectedRec.set('op',rec.get('valueField'));
			editSelectedMenuGrid.getStore().add(selectedRec);
			editSelectedMenuGrid.getStore().sort('valueField','asc');
		}
	}
	
	
	// 已选菜单列表
	var editSelectedMenuGrid = new Ext.grid.GridPanel({
		store: editSelectedMenuStore,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		height: 300,
		width: 310,
		cm: editSelectedCol
	});
	
	
	editSelectedMenuGrid.on('render',function() {
		var selectedMenuDDEL = editSelectedMenuGrid.getView().el.dom;
		var selectedMenuDDTarget = new Ext.dd.DropTarget(selectedMenuDDEL,{
			ddGroup: 'editMenu',
			notifyDrop: function(ddSource,e,data) {
				function addRow(record,index,allItem) {
					var itemIndex = editSelectedMenuGrid.getStore().findExact("valueField",record.data.valueField);
					if(itemIndex == -1) {
						var selectedRec = new SelectedMenuRecord();
						selectedRec.id = record.get('valueField');
						selectedRec.set('valueField',record.get('valueField'));
						selectedRec.set('displayField',record.get('displayField'));
						selectedRec.set('op',record.get('valueField'));
						editSelectedMenuGrid.getStore().add(selectedRec);
						editSelectedMenuGrid.getStore().sort("valueField","ASC");
					}
				}
				Ext.each(ddSource.dragData.selections,addRow);
				return (true);
			}
		});
	});
	
	
	// 编辑菜单表单
	var editMenuForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 800,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '角色名称',
			id: 'roleNmU',
			width: '200',
			disabled : true,
			readOnly: true
		},{
			fieldLabel: '角色描述',
			id:'decU',
			width: '300',
			disabled : true,
			readOnly: true
		},new Ext.Panel({
			title: '<center>请为该角色编辑权限</center>',
			layout: 'table',
			items: [editMenuLvl1Grid,editMenuLvl2Grid,editMenuLvl3Grid,editSelectedMenuGrid]
		})]
	});
	
	// 角色编辑窗口
	var editRoleWin = new Ext.Window({
		title: '角色维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 850,
		autoHeight: true,
		layout: 'fit',
		items: [editMenuForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(editSelectedMenuGrid.getStore().getCount() == 0) {
					showConfirm('您没有为该角色选择任何菜单信息，确定要提交吗？',editSelectedMenuGrid,function(bt) {
						if(bt == 'yes') {
							editSubmit();
						}
					})
				} else {
					editSubmit();
				}
			}
		},{
			text: '关闭',
			handler: function() {
				editRoleWin.hide(roleGrid);
			}
		}]
	});
	
	
	editRoleWin.on('show',function(){
		var selectedRec = roleGrid.getSelectionModel().getSelected();
		/*if(selectedRec.get('roleType') == '0') {
			editMenuForm.items.items[1].setValue('总行');
		} else if(selectedRec.get('roleType') == '1') {
			editMenuForm.items.items[1].setValue('分行');
		} else if(selectedRec.get('roleType') == '2') {
			editMenuForm.items.items[1].setValue('支行');
		}*/
		editMenuForm.items.items[0].setValue(selectedRec.get('roleName'));
		
		editMenuForm.items.items[1].setValue(selectedRec.get('description'));
		
		editFormReset();
		editRoleWin.getEl().mask('正在加载菜单信息......');
		setTimeout(function() {
		    editRoleWin.getEl().unmask();
		},600);
    });
      
	// 	编辑提交
	function editSubmit() {
		
		var menuArray = new Array();
		
		for(var i = 0,n = editSelectedMenuGrid.getStore().getCount(); i < n; i++) {
			var rec = editSelectedMenuGrid.getStore().getAt(i);
			var data = {
				valueId: rec.get('valueField')
			};
			menuArray.push(data);
			
		}
		editMenuForm.getForm().submitNeedAuthorise({
			url: 'T10301Action.asp?',
			waitMsg: '正在提交，请稍后......',
			success: function(form,action) {
				showSuccessMsg(action.result.msg,editMenuForm);
				editRoleWin.hide(roleGrid);
			},
			failure: function(form,action) {
				showErrorMsg(action.result.msg,editMenuForm);
			},
			params: {
				method:'edit',
				txnId: '10301',
				subTxnId: '04',
				roleId: roleGrid.getSelectionModel().getSelected().get('roleId'),
				menuList: Ext.encode(menuArray)
			}
		});
	}
	
	// 编辑重置
	function editFormReset() {
		var selectedRec = roleGrid.getSelectionModel().getSelected();
		SelectOptionsDWR.getComboDataWithParameter('ROLE_MENU',selectedRec.get('roleId'),function(ret) {
			var json = Ext.decode(ret);
			editSelectedMenuStore.removeAll();
			if(json.data[0].valueField == "") {
				return;
			}
			
			var recordCreator = new Ext.data.Record.create([
				{name: 'valueField',type: 'string'},
				{name: 'displayField',type: 'string'},
				{name: 'op',type: 'string'}
			]);
			
			for(var i = 0,n = json.data.length; i < n; i++) {
				var rec = new recordCreator();
				rec.set('valueField',json.data[i].valueField);
				rec.set('displayField',json.data[i].displayField);
				rec.set('op',json.data[i].valueField);
				rec.id = json.data[i].valueField;
				editSelectedMenuStore.add(rec);
			}
		});
	}
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [roleGrid],
		renderTo: Ext.getBody()
	});
});