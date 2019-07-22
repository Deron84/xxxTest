Ext.onReady(function() {
	
	/**********************************************商户组别***************************************************/
	
	// 商户组别数据集
	var mchntGrpGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntGrpInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchntTpGrp'
		},[
			{name: 'mchntTpGrp',mapping: 'mchntTpGrp'},
			{name: 'descr',mapping: 'descr'}
		])
	});
	
	mchntGrpGridStore.load({
		params:{start: 0}
	});
	
	var mchntGrpColModel = new Ext.grid.ColumnModel([
		{id: 'mchntTpGrp',header: '商户组别编号',dataIndex: 'mchntTpGrp',sortable: true,width: 100},
		{header: '商户组别描述',dataIndex: 'descr',width: 300,editor: new Ext.form.TextField({
			allowBlank: false,
			blankText: '商户组别描述不能为空',
			maxLength: 200,
			vtype: 'isOverMax'
		})}
	]);
	
	var grpAddMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			mchntGrpWin.show();
			mchntGrpWin.center();
		}
	};
	var grpDelMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			showConfirm('确定要删除该商户组别信息吗？',mchntGrpGrid,function(bt) {
				if(bt == 'yes') {
//					showProcessMsg('正在提交，请稍后......');
					var rec = mchntGrpGrid.getSelectionModel().getSelected();
					Ext.Ajax.requestNeedAuthorise({
						url: 'T20302Action.asp?method=delete',
						method: 'post',
						params: {
							mchntTpGrp : rec.get('mchntTpGrp'),
							txnId: '20302',
							subTxnId: '02'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								mchntGrpGrid.getStore().commitChanges();
								showSuccessMsg(rspObj.msg,mchntGrpGrid);
							} else {
								mchntGrpGrid.getStore().rejectChanges();
								showErrorMsg(rspObj.msg,mchntGrpGrid);
							}
							mchntGrpGrid.getStore().reload();
							hideProcessMsg();
						}
					});
					
				}
			});
		}
	};
	var grpUpMenu = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
//			
			//存放要修改的商户组别信息
			var array = new Array();
			var modifiedRecords = mchntGrpGrid.getStore().getModifiedRecords();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					mchntTpGrp : record.get('mchntTpGrp'),
					descr: record.get('descr')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20302Action.asp?method=update',
				method: 'post',
				params: {
					mchntTpGrpList : Ext.encode(array),
					txnId: '20302',
					subTxnId: '03'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						mchntGrpGrid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,mchntGrpGrid);
					} else {
						mchntGrpGrid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,mchntGrpGrid);
					}
					mchntGrpGrid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	var grpMenuArr = new Array();
	grpMenuArr.push(grpAddMenu);
	grpMenuArr.push('-');
	grpMenuArr.push(grpDelMenu);
	grpMenuArr.push('-');
	grpMenuArr.push(grpUpMenu);
	
	// 商户组别信息列表
	var mchntGrpGrid = new Ext.grid.EditorGridPanel({
		title: '商户组别信息',
		iconCls: 'mchnt-grp',		
		frame: true,
		border: true,
		columnLines: true,
//		height:Ext.get("body").getHeight(),
		autoHeight: true,
//		region:'center',
//		autoExpandColumn:'descr',
		store: mchntGrpGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntGrpColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: grpMenuArr,
		loadMask: {
			msg: '正在加载商户组别信息......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntGrpGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	mchntGrpGrid.on('afteredit',function() {
		mchntGrpGrid.getTopToolbar().items.items[4].enable();
	});
	
	// 禁用编辑按钮
	mchntGrpGrid.getStore().on('beforeload',function() {
		mchntGrpGrid.getTopToolbar().items.items[2].disable();
		mchntGrpGrid.getTopToolbar().items.items[4].disable();
		mchntGrpGrid.getStore().rejectChanges();
	});
	
	mchntGrpGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrpGrid.getView().getRow(mchntGrpGrid.getSelectionModel().last)).frame();
			// 启用编辑按钮
			mchntGrpGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	
	// 添加商户组别信息表单
	var mchntGrpForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '商户组别编号*',
			allowBlank: false,
			blankText: '商户组别编号不能为空',
			emptyText: '请输入商户组别编号',
			id: 'mchntTpGrp',
			name: 'mchntTpGrp',
			width: 150,
			regex: /^[0-9]{2}$/,
			regexText: '商户组别编号必须是2位数字'
		},{
			xtype: 'textarea',
			fieldLabel: '商户组别描述*',
			allowBlank: false,
			blankText: '商户组别描述不能为空',
			emptyText: '请输入商户组别描述',
			id: 'grpDescr',
			name: 'grpDescr',
			vtype: 'isOverMax',
			maxLength: 200,
			width: 200
		}]
	});
	
	// 商户组别添加窗体
	var mchntGrpWin = new Ext.Window({
		title: '商户组别添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		resizable: false,
		layout: 'fit',
		items: [mchntGrpForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		buttons: [{
			text: '确定',
			handler: function() {
				if(mchntGrpForm.getForm().isValid()) {
					mchntGrpForm.getForm().submitNeedAuthorise({
						url: 'T20302Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,mchntGrpForm);
							//重置表单
							mchntGrpForm.getForm().reset();
							//重新加载商户组别列表
							mchntGrpGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,mchntGrpForm);
						},
						params: {
							txnId: '20302',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				mchntGrpForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				mchntGrpWin.hide(mchntGrpGrid);
			}
		}]
	});
	
	
	/**********************************************商户MCC***************************************************/
	
	// 商户组别数据集
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
			{name: 'descr',mapping: 'descr'}
			//{name: 'recSt',mapping: 'recSt'}
		])
	});
	
	mchntMccGridStore.load({
		params:{start: 0}
	});
	
	var mchntGrpStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	SelectOptionsDWR.getComboData('MCHNT_GRP_ALL',function(ret){
		mchntGrpStore.loadData(Ext.decode(ret));
	});
	
	var mchntMccStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	SelectOptionsDWR.getComboData('MCHT_MCC',function(ret){
		mchntMccStore.loadData(Ext.decode(ret));
	});
	
	var mchntMccColModel = new Ext.grid.ColumnModel([
		{id: 'mchntTp',header: '商户MCC',dataIndex: 'mchntTp',sortable: true,width: 100},
		{id:'mchntTpGrp',header: '商户组别编号',dataIndex: 'mchntTpGrp',width: 300,sortable: true,
		 editor: new Ext.form.ComboBox({
		 	store: mchntGrpStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'mchntTpGrp',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false
		 })
		},
		{header: '商户MCC描述',dataIndex: 'descr',id:'descr',width: 400,editor: new Ext.form.TextField({
			allowBlank: false,
			blankText: '商户MCC描述不能为空',
			maxLength: 200,
			vtype: 'isOverMax'
		})}
	]);
	
	var mccAddMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			mchntMccWin.show();
			mchntMccWin.center();
		}
	};
	var mccDelMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			showConfirm('确定要删除该条商户MCC信息吗？',mchntMccGrid,function(bt) {
				if(bt == 'yes') {
					var rec = mchntMccGrid.getSelectionModel().getSelected();
//					showProcessMsg('正在提交，请稍后......');
					Ext.Ajax.requestNeedAuthorise({
						url: 'T20303Action.asp?method=delete',
						method: 'post',
						params: {
							mchntTp : rec.get('mchntTp'),
							txnId: '20303',
							subTxnId: '02'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								mchntMccGrid.getStore().commitChanges();
								showSuccessMsg(rspObj.msg,mchntMccGrid);
							} else {
								mchntMccGrid.getStore().rejectChanges();
								showErrorMsg(rspObj.msg,mchntMccGrid);
							}
							mchntMccGrid.getTopToolbar().items.items[4].disable();
							mchntMccGrid.getStore().reload();
							hideProcessMsg();
						}
					});
					
				}
			});
		}
	};
	var mccUpMenu = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
//			showProcessMsg('正在保存商户MCC信息，请稍后......');
			//存放要修改的商户组别信息
			var array = new Array();
			var modifiedRecords = mchntMccGrid.getStore().getModifiedRecords();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					mchntTp: record.get('mchntTp'),
					mchntTpGrp: record.get('mchntTpGrp'),
					descr: record.get('descr')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20303Action.asp?method=update',
				method: 'post',
				params: {
					mchntTpList : Ext.encode(array),
					txnId: '20303',
					subTxnId: '03'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						mchntMccGrid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,mchntMccGrid);
					} else {
						mchntMccGrid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,mchntMccGrid);
					}
					mchntMccGrid.getTopToolbar().items.items[4].disable();
					mchntMccGrid.getStore().reload();
					hideProcessMsg();
				}
			});
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
	
	
	var mccMenuArr = new Array();
	mccMenuArr.push(mccAddMenu);
	mccMenuArr.push('-');
	mccMenuArr.push(mccDelMenu);
	mccMenuArr.push('-');
	mccMenuArr.push(mccUpMenu);
	mccMenuArr.push('-');         //[9]
	mccMenuArr.push(queryCondition);  //[10]
	
	// 商户MCC信息列表
	var mchntMccGrid = new Ext.grid.EditorGridPanel({
		title: '商户MCC信息',
		iconCls: 'mcc',
		frame: true,
		border: true,
		columnLines: true,
		autoHeight: true,
		stripeRows: true,
		store: mchntMccGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntMccColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: mccMenuArr,
		loadMask: {
			msg: '正在加载商户MCC信息......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntMccGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	mchntMccGrid.on('afteredit',function() {
		mchntMccGrid.getTopToolbar().items.items[4].enable();
	});
	
	// 禁用编辑按钮
	mchntMccGrid.getStore().on('beforeload',function() {
		mchntMccGrid.getTopToolbar().items.items[2].disable();
		mchntMccGrid.getTopToolbar().items.items[4].disable();
	});
	
	mchntMccGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntMccGrid.getView().getRow(mchntMccGrid.getSelectionModel().last)).frame();
			// 启用编辑按钮
			mchntMccGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	// 添加商户MCC信息表单
	var mchntMccForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 600,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '商户MCC编号*',
			allowBlank: false,
			blankText: '商户MCC编号不能为空',
			emptyText: '请输入商户MCC编号',
			id: 'mchntTp',
			name: 'mchntTp',
			width: 150,
			regex: /^[0-9]{4}$/,
			regexText: '商户MCC编号必须是4位数字'
		},{
			xtype: 'combo',
			store: mchntGrpStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'mchntTpGrp',
			emptyText: '请选择商户组别',
			fieldLabel: '商户组别*',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个商户组别',
			width: 400
		},{
			xtype: 'textarea',
			fieldLabel: '商户MCC描述*',
			allowBlank: false,
			blankText: '商户MCC描述不能为空',
			emptyText: '请输入商户MCC描述',
			id: 'mccDescr',
			name: 'mccDescr',
			vtype: 'isOverMax',
			maxLength: 200,
			width: 200
		}]
	});
	
	// 商户MCC添加窗体
	var mchntMccWin = new Ext.Window({
		title: '商户MCC添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
		autoHeight: true,
		resizable: false,
		layout: 'fit',
		items: [mchntMccForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		buttons: [{
			text: '确定',
			handler: function() {
				if(mchntMccForm.getForm().isValid()) {
					mchntMccForm.getForm().submitNeedAuthorise({
						url: 'T20303Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,mchntMccForm);
							//重置表单
							mchntMccForm.getForm().reset();
							//重新加载商户MCC列表
							mchntMccGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,mchntMccForm);
						},
						params: {
							txnId: '20303',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				mchntMccForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				mchntMccWin.hide(mchntGrpGrid);
			}
		}]
	});
	
	/**************************************************主界面****************************************************/
	var mainUI = new Ext.TabPanel({
		items: [mchntGrpGrid,mchntMccGrid],
		renderTo: Ext.getBody(),
		activeTab: 0
	});
	
	mainUI.on('beforetabchange',function() {
		SelectOptionsDWR.getComboData('MCHNT_GRP_ALL',function(ret){
			mchntGrpStore.loadData(Ext.decode(ret));
		});
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
						id: 'mchtGrpId',
						hiddenName: 'mchtGrp',
						width: 450
//						listeners: {
//							'select': function() {
//								mchntMccStore.removeAll();
//								Ext.getCmp('idmcc').setValue('');
//								Ext.getDom(Ext.getDoc()).getElementById('mcc').value = '';
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
			mchtGrp: queryForm.findById('mchtGrpId').getValue(),
			mcc:  queryForm.findById('idmcc').getValue()
		});
	});
	
//	var mainUV = new Ext.Viewport({
//		layout: 'border',
//		renderTo: Ext.getBody(),
//		items:[mchntGrpGrid,mchntMccGrid]
//	})
});