Ext.onReady(function() {

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=accountSettle'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'pan',mapping: 'pan'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'bank',mapping: 'bank'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'settleDate',mapping: 'settleDate'},
			{name: 'transDate',mapping: 'transDate'},
			{name: 'transTime',mapping: 'transTime'},
			{name: 'termId',mapping: 'termId'},
			{name: 'transAmt',mapping: 'transAmt'},
			{name: 'fee',mapping: 'fee'},
			{name: 'settleAmt',mapping: 'settleAmt'},
			{name: 'txnSsn',mapping: 'txnSsn'},
			{name: 'backFlag',mapping: 'backFlag'}
		]),
		autoLoad: true
	});
	
	var paramColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '清算日期',dataIndex: 'settleDate',width:90},
		{header: '交易日期',dataIndex: 'transDate',width:90},
		{header: '交易时间',dataIndex: 'transTime',width:130},
		{header: '开户行名称',dataIndex: 'bank',width:160},
		{header: '商户号',dataIndex: 'mchtNo',width:130},
		{header: '商户名',dataIndex: 'mchtNm',width:180},
		{header: '交易流水',dataIndex: 'txnSsn',width:130},
		{header: '交易金额(元)',dataIndex: 'transAmt'},
		{header: '交易手续费(元)',dataIndex: 'fee',width:120},
		{header: '清算金额',dataIndex: 'settleAmt',width:110},
		{header: '退货标识',dataIndex: 'backFlag',width:110,renderer:backFlag}
	]);
	
	var menuArray = new Array();
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 490,
		autoHeight: true,
		items: [{
			fieldLabel: '起始清算日期',
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			vtype: 'daterange',
			endDateField: 'endDate',
			width: 340
		},{
			fieldLabel: '截止清算日期',
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			vtype: 'daterange',
			startDateField: 'startDate',
			width: 340
		},{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户',
			id: 'mchntId',
			hiddenName: 'HmchntId',
			allowBlank: true,
			editable: true,
			width: 340
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
				store.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	
	var queryMenu = {
			text: '查询',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	
	var report = {
			text: '生成报表',
			width: 160,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",grid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T80911Action.asp',
					timeout: 60000,
					params: {
						startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
						endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),						
						mchntId: queryForm.findById('mchntId').getValue(),
						txnId: '80301',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					},
					failure: function(){
						hideMask();
					}
				});
			}
	}
	
	menuArray.add(queryMenu);
//	menuArray.add('-');
//	menuArray.add(report);
	
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户对账单查询',
		iconCls: 'T80301',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		store: store,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: paramColModel,
		clicksToEdit: true,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载信息列表......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: store,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	store.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0, 
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			mchntId: queryForm.findById('mchntId').getValue()
		});
	});
	 
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
	
});