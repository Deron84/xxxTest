Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '该输入项只能包含数字'
});
Ext.onReady(function() {
	
	
	// 一级菜单数据集
	var menuLvl1Store = new Ext.data.JsonStore({
		root: 'data',
		fields: ['valueField','displayField']
	});
	
	SelectOptionsDWR.getComboData('AUTHOR_LEVEL_1',function(ret) {
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
	
	function loadAuthorMenu(){
		SelectOptionsDWR.getComboData('AUTHOR_MENU',function(ret) {
			var json = Ext.decode(ret);
			selectedMenuStore.removeAll();
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
				selectedMenuStore.add(rec);
			}
		});
	}
	
	
	var menuLvl1Col = new Ext.grid.ColumnModel([
		{id: 'valueField',header: '菜单编号',hidden: true,dataIndex: 'valueField'},
		{id: 'display', header: '<center>一级菜单</center>',dataIndex: 'displayField',menuDisabled: true,resizable: false,align: 'center'}
	]);
	
	var menuLvl2Col = new Ext.grid.ColumnModel([
		{id: 'valueField',header: '菜单编号',hidden: true,dataIndex: 'valueField'},
		{id: 'display', header: '<center>二级菜单</center>',dataIndex: 'displayField',menuDisabled: true,resizable: false,align: 'center'}
	]);
	
	var menuLvl3Col = new Ext.grid.ColumnModel([
		{id: 'valueField',header: '菜单编号',hidden: true,dataIndex: 'valueField'},
		{id: 'display', header: '<center>三级菜单</center>',dataIndex: 'displayField',menuDisabled: true,resizable: false,align: 'center'}
	]);
	
	var selectedCol = new Ext.grid.ColumnModel([
		{id: 'valueField',header: '菜单编号',hidden: true,dataIndex: 'valueField'},
		{header: '<center>需授权的交易</center>',dataIndex: 'displayField',width: 170,menuDisabled: true,resizable: false,align: 'center'},
		{id: 'display', header: '',dataIndex: 'op',width: 35,menuDisabled: true,resizable: false,align: 'center',
		 renderer: renderDelete}
	]);
	
	function renderDelete(val) {
		return '<img src="' + Ext.contextPath + '/ext/resources/images/recycle.png" ' +
				       		'title="取消此交易" onclick=deleteMenu('+ val + ') onmouseover="this.style.cursor=\'pointer\'"/>';
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
		autoExpandColumn: 'display',
		height: 400,
		width: 180,
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
			SelectOptionsDWR.getComboDataWithParameter('AUTHOR_LEVEL',func_parent_id,function(ret) {
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
		autoExpandColumn: 'display',
		height: 400,
		width: 180,
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
			SelectOptionsDWR.getComboDataWithParameter('AUTHOR_LEVEL',func_parent_id,function(ret) {
				menuLvl3Store.loadData(Ext.decode(ret));
			});
			menuLvl2Grid.lastSelectedRowIndex = menuLvl2Grid.getSelectionModel().last;
			menuLvl3Grid.lastSelectedRowIndex = -1;
		}
	})
	
	// 三级菜单列表
	var menuLvl3Grid = new Ext.grid.GridPanel({
		store: menuLvl3Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoExpandColumn: 'display',
		height: 400,
		width: 180,
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
		autoExpandColumn: 'display',
		height: 400,
		width: 250,
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
	
	
	var roleInfoForm = new Ext.FormPanel({
        title: '交易授权配置',
		region: 'center',
		iconCls: 'T10302',
		frame: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'left',
		layout: 'table',
		items: [menuLvl1Grid,menuLvl2Grid,menuLvl3Grid,selectedMenuGrid],
		buttonAlign: 'right',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(roleInfoForm.getForm().isValid()) {
					addSubmit();
				}
			}
		},{
			text: '重置',
			handler: loadAuthorMenu
		}]
	});
	
	// 提交表单
	function addSubmit() {
		var menuArray = new Array();
		
		for(var i = 0,n = selectedMenuGrid.getStore().getCount(); i < n; i++) {
			var rec = selectedMenuGrid.getStore().getAt(i);
			var data = {
				valueId: rec.get('valueField')
			};
			menuArray.push(data);
		}
		
		roleInfoForm.getForm().submit({
			url: 'T10302Action.asp?method=add',
			waitMsg: '正在提交，请稍后......',
			success: function(form,action) {
				showSuccessMsg(action.result.msg,roleInfoForm);
				
			},
			failure: function(form,action) {
				showErrorMsg(action.result.msg,roleInfoForm);
				loadAuthorMenu();
			},
			params: {
				txnId: '10302',
				subTxnId: '01',
				menuList: Ext.encode(menuArray)
			}
		});
	}
	loadAuthorMenu();
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [roleInfoForm],
		renderTo: Ext.getBody()
	});
	
});