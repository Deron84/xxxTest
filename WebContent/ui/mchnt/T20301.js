Ext.onReady(function() {
	var mchntMccStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	SelectOptionsDWR.getComboData('MCHT_MCC',function(ret){
		mchntMccStore.loadData(Ext.decode(ret));
	});
	
	var mchntMccGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntMccInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchntTp'
		},[
			{name: 'mchntTp',mapping: 'mchntTp'},
			{name: 'mchntTpGrp',mapping: 'mchntTpGrp'},
			{name: 'descr',mapping: 'descr'},
			{name: 'recSt',mapping: 'recSt'}
		])
	});
	
	mchntMccGridStore.load({
		params:{start: 0}
	});
	
	
	var roleColModel = new Ext.grid.ColumnModel([
		{id: 'mchntTp',header: '商户MCC',dataIndex: 'mchntTp',sortable: true,width: 100},
		{header: '商户组别编号',dataIndex: 'mchntTpGrp',sortable: true,width: 300},
		{header: '商户MCC描述',dataIndex: 'descr',width: 400},
		{header: '记录状态',dataIndex: 'recSt',width: 200}
	]);
	
	var edit = {
		text: '权限修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler: function() {
			editRoleWin.show();
			editRoleWin.center();
		}
	};
	
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	
	var menuArr = new Array();	
	menuArr.push(edit);      // [0]
	menuArr.push('-');         //[1]
	menuArr.push(queryCondition);  //[2]
	
	// 角色列表
	var roleGrid = new Ext.grid.EditorGridPanel({
		title: '商户交易权限维护',
		iconCls: 'T20301',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		store: mchntMccGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: roleColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载商户交易权限信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntMccGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})

	});

	roleGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(roleGrid.getView().getRow(roleGrid.getSelectionModel().last)).frame();
			//激活菜单
			roleGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	
	
	/******************************************************编辑菜单信息********************************************************/
	
	var menuLvl3Col = new Ext.grid.ColumnModel([
    		{id: 'valueField',header: '菜单编号',hidden: true,dataIndex: 'valueField'},
    		{header: '<center>所有交易</center>',dataIndex: 'displayField',width: 245,menuDisabled: true,resizable: false,align: 'center'}
    	]);
	
	// 三级菜单数据集
	var editMenuLvl3Store = new Ext.data.JsonStore({
		root: 'data',
		fields: ['valueField','displayField']
	});
	SelectOptionsDWR.getComboData('MER_FUNC_ALL',function(ret) {
		editMenuLvl3Store.loadData(Ext.decode(ret));
	});
	
	// 已选菜单数据集
	var editSelectedMenuStore = new Ext.data.JsonStore({
		root: 'data',
		fields: ['valueField','displayField','op']
	});
	
	SelectOptionsDWR.getComboData('MER_FUNC_ROLE',function(ret) {
		editSelectedMenuStore.loadData(Ext.decode(ret));
	});	
	
	var editSelectedCol = new Ext.grid.ColumnModel([
		{id: 'valueField',header: '菜单编号',hidden: true,dataIndex: 'valueField'},
		{header: '<center>已选菜单</center>',dataIndex: 'displayField',width: 220,menuDisabled: true,resizable: false,align: 'center'},
		{header: '',dataIndex: 'op',width: 35,menuDisabled: true,resizable: false,align: 'center',
		 renderer: editRenderDelete}
	]);
	
	function editRenderDelete(val) {
		return '<img src="' + Ext.contextPath + '/ext/resources/images/recycle.png" ' +
				       		'title="删除此菜单" onclick=editDeleteMenu(' + val + ') onmouseover="this.style.cursor=\'pointer\'"/>';
	}
	
	window.editDeleteMenu = function(val) {
//		if(val<10){
//			val = '000'+val;
//		}else{
//			val = '00'+val;
//		}
//		alert(val);
		var rec = editSelectedMenuGrid.getStore().getById(val);
		editSelectedMenuGrid.getStore().remove(rec);
	}
	

	
	// 系统权限
	var editMenuLvl3Grid = new Ext.grid.GridPanel({
		store: editMenuLvl3Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		height: 300,
		width: 250,
		cm: menuLvl3Col,
		
		keys: [{
			key: Ext.EventObject.LEFT,
			handler: function() {
//				//changeFocus(editMenuLvl3Grid,editMenuLvl2Grid);
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
	
	var SelectedMenuRecord = new Ext.data.Record.create([
 		{name: 'valueField',type: 'string'},
 		{name: 'displayField',type: 'string'},
 		{name: 'op',type: 'string'}
 	]);

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
	
	
	// 已选系统权限
	var editSelectedMenuGrid = new Ext.grid.GridPanel({
		store: editSelectedMenuStore,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		height: 300,
		width: 270,
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
		width: 700,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '商户MCC',
			width: '100',
			id: 'mchntTp',
			name: 'mchntTp',
			readOnly: true
		},{
			fieldLabel: '商户组别编号',
			id: 'mchntTpGrp',
			name: 'mchntTpGrp',
			width: '100',
			readOnly: true
		},{
			fieldLabel: '商户MCC描述',
			width: '300',
			id: 'descr',
			name: 'descr',
			readOnly: true
		},new Ext.Panel({
			title: '<center>请为该商户类型编辑权限</center>',
			layout: 'table',
			items: [editMenuLvl3Grid,editSelectedMenuGrid]
		})]
	});
	
	// 角色编辑窗口
	var editRoleWin = new Ext.Window({
		title: '商户类型权限维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 545,
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
					showConfirm('您没有为该商户交易权限选择的任何菜单信息，确定要提交吗？',editSelectedMenuGrid,function(bt) {
						if(bt == 'yes') {
							editSubmit();
						}
					})
				} else {
					editSubmit();
				}
			}
		},{
			text: '重置',
			handler: editFormReset
		},{
			text: '关闭',
			handler: function() {
				editRoleWin.hide(roleGrid);
			}
		}]
	});
	
	
	/***************************查询条件*************************/
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户组别',
						allowBlank: false,
						id: 'mchtGrp',
						hiddenName: 'mchtGrpTp',
						width: 450
//						listeners: {
//							'select': function() {
//								mchntMccStore.removeAll();
//								Ext.getCmp('idmcc').setValue('');
//								Ext.getDom(Ext.getDoc()).getElementById('idmcc').value = '';
//								SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',queryForm.getForm().findField('mchtGrp').getValue(),function(ret){
//									mchntMccStore.loadData(Ext.decode(ret));
//								});
//							}
//						}
		        	}]
			},{
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户MCC',
						store: mchntMccStore,
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						allowBlank: true,
						lazyRender: true,
						width: 450,
						blankText: '请选择商户MCC',
						id: 'idmcc',
						hiddenName: 'mcc'
		        	}]
			}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 600,
		autoHeight: true,
		items: [queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				queryWin.collapse();
				queryWin.getEl().pause(1);
				queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryWin.expand();
				queryWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				mchntMccGridStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntMccGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mcc: queryForm.findById('idmcc').getValue(),
			mchtGrp: queryForm.findById('mchtGrp').getValue()
		});
	});
	
	editRoleWin.on('show',function(){
		var selectedRec = roleGrid.getSelectionModel().getSelected();
		editMenuForm.items.items[0].setValue(selectedRec.get('mchntTp'));		
		editMenuForm.items.items[1].setValue(selectedRec.get('mchntTpGrp'));
		editMenuForm.items.items[2].setValue(selectedRec.get('descr'));		
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
		editMenuForm.getForm().submit({
			url: 'T20301Action.asp?method=edit',
			waitMsg: '正在提交，请稍后......',
			success: function(form,action) {
				showSuccessMsg(action.result.msg,editMenuForm);
				editRoleWin.hide(roleGrid);
			},
			failure: function(form,action) {
				showErrorMsg(action.result.msg,editMenuForm);
			},
			params: {
				txnId: '10301',
				subTxnId: '04',
				mchntTpGrp: roleGrid.getSelectionModel().getSelected().get('mchntTpGrp'),
				roleFuncList: Ext.encode(menuArray)
			}
		});
	}
	
	// 编辑重置
	function editFormReset() {
		var selectedRec = roleGrid.getSelectionModel().getSelected();
		SelectOptionsDWR.getComboDataWithParameter('MER_FUNC_ROLE',selectedRec.get('mchntTp'),function(ret) {
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
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[roleGrid]
	})
});