Ext.onReady(function() {

	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=entrustInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'tradeId',mapping: 'trade_id'},
			{name: 'status',mapping: 'status'},
			{name: 'pan',mapping: 'pan'},
			{name: 'cardAccpId',mapping: 'card_accp_id'},
			{name: 'amtTrans',mapping: 'amt_trans'}

		]),
		autoLoad: true
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([

		{header: '托收流水号',dataIndex: 'tradeId',width: 200},
		{header: '卡号',dataIndex: 'pan',width: 200},
		{header: '商户编号',dataIndex: 'cardAccpId',width: 150},
		{header: '交易金额',dataIndex: 'amtTrans',width: 100},
		{header: '托收状态',dataIndex: 'status',width: 100,renderer: St}
	]);
	
	function St(val) {
		if(val == '1') {
			return '<font color="green">待处理</font>';
		} else if(val == '2') {
			return '<font color="gray">已处理</font>';
		} else if(val == '3') {
			return '<font color="red">注销</font>';
		} 
		return val;
	}
	
	var menuArr = new Array();

	var queryConditionMebu = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	var addMenu = {
			text: '添加',
			width: 85,
			iconCls: 'add',
			handler:function() {
				addWin.show();
				addWin.center();
			}
		};
	
	var delMenu = {
			text: '注销',
			width: 85,
			iconCls: 'delete',
			handler: function() {
				if(grid.getSelectionModel().hasSelection()) {
					var rec = grid.getSelectionModel().getSelected();
					var tradeId = rec.get('tradeId');				
					showConfirm('确定要注销该托收交易吗？托收流水号：' + tradeId,grid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T90201Action.asp?method=del',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,grid);
										grid.getStore().reload();
									} else {
										showErrorMsg(rspObj.msg,grid);
									}
								},
								params: { 
									tradeId: tradeId,
									txnId: '90201',
									subTxnId: '03'
								}
							}
							);
						}
					});
				}
			}
		};
	
	var update = {
			text: '修改',
			width: 85,
			iconCls: 'edit',
			handler: function() {
				if(grid.getSelectionModel().hasSelection()) {
					var rec = grid.getSelectionModel().getSelected();
					var tradeId = rec.get('tradeId');
					var status = rec.get('status');
					var pan = rec.get('pan');
					var cardAccpId = rec.get('cardAccpId');
					var amtTrans = rec.get('amtTrans');

					editForm.getForm().findField("entrustId").setValue(tradeId);
					editForm.getForm().findField("panIdUpd").setValue(pan);
					editForm.getForm().findField("mchtNoUpd").setValue(cardAccpId);
					editForm.getForm().findField("transIdUpd").setValue(amtTrans);
					
					editWin.show();
					editWin.center();
				}
			}
		};

	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(update);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(queryConditionMebu);

	//托收信息添加表单
	var addForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntId',
			hiddenName: 'mchtNo',
			allowBlank: false,
			editable: true,
			width: 300
		},{
			fieldLabel: '卡号*',
			allowBlank: false,
			emptyText: '请输入卡号',
			id: 'panId',
			name: 'panName',
			width: 200,
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '金额*',
			allowBlank: false,
			emptyText: '请输入金额',
			id: 'transId',
			name: 'transName',
			width: 200,
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','请款'],['1','例外协商']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			hiddenName: 'kind',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: false,
			fieldLabel: '托收类型*'
		}]
	});

	var addWin = new Ext.Window({
		title: '托收交易添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [addForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(addForm.getForm().isValid()) {
					addForm.getForm().submit({
						url: 'T90201Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,addForm);
							addForm.getForm().reset();
							grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,addForm);
						},
						params: {
							txnId: '90201',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				addForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addWin.hide(grid);
			}
		}]
	});
	
	//托收信息修改表单
	var editForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '托收流水号*',
			id: 'entrustId',
			name: 'entrustName',
			width: 200,
			readOnly: true
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntId',
			hiddenName: 'mchtNoUpd',
			allowBlank: false,
			editable: true,
			width: 300
		},{
			fieldLabel: '卡号*',
			allowBlank: false,
			emptyText: '请输入卡号',
			id: 'panIdUpd',
			name: 'panNameUpd',
			width: 200,
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '金额*',
			allowBlank: false,
			emptyText: '请输入金额',
			id: 'transIdUpd',
			name: 'transNameUpd',
			width: 200,
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		}]
	});
	
	var editWin = new Ext.Window({
		title: '托收交易修改',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [editForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(editForm.getForm().isValid()) {
					editForm.getForm().submit({
						url: 'T90201Action.asp?method=edit',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,editForm);
							editForm.getForm().reset();
							grid.getStore().reload();
							editWin.hide(grid);
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,editForm);
						},
						params: {
							txnId: '90201',
							subTxnId: '02'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				editForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				editWin.hide(grid);
			}
		}]
	});
	
	// 交易查询
	var grid = new Ext.grid.GridPanel({
		title: '托收交易信息管理',
		iconCls: 'T501',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		store: txnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: txnColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载交易列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: txnStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			fieldLabel: '托收流水号',
			id: 'key'
		},{
			xtype: 'textfield',
			fieldLabel: '卡号',
			id: 'pan'
		},{
			xtype: 'textfield',
			fieldLabel: '商户号',
			id: 'mno'
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 300,
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
				txnStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	txnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			key: queryForm.getForm().findField('key').getValue(),
			pan: queryForm.getForm().findField('pan').getValue(),
			mno: queryForm.getForm().findField('mno').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});