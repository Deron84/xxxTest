Ext.onReady(function() {

	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bthDtlErrGcFinish'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'dateSettlmt',mapping: 'DATE_SETTLMT'},
			{name: 'outSsn',mapping: 'OUT_SSN'},
			{name: 'TERM_SSN',mapping: 'TERM_SSN'},
			{name: 'acqTxnSsn',mapping: 'acq_txn_ssn'},
			{name: 'pan',mapping: 'PAN'},
			{name: 'amtTrans',mapping: 'AMT_TRANS'},
			{name: 'trans_date_time',mapping: 'trans_date_time'},
			{name: 'err_proc_cd',mapping: 'err_proc_cd'},
			{name:'txn_num',mapping:'txn_num'},
			{name:'stlmFlag',mapping:'stlmFlag'},
			{name:'mchtNo',mapping:'mchtNo'},
			{name:'mchtNm',mapping:'mchtNm'}
		]),
		autoLoad: true
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([
		{header: '清算日期',dataIndex: 'dateSettlmt',width: 80},
		{header: '交易时间',dataIndex: 'trans_date_time'},
	    {header: '管理机构',dataIndex: 'bankNo',width: 180,renderer:function(val){return getRemoteTrans(val, "brhName");}},
        {header: '商户号',dataIndex: 'mchtNo',width: 130},
        {header: '商户名',dataIndex: 'mchtNm',width: 420},
        {header: '收单流水号',dataIndex: 'acqTxnSsn',width: 100},
        {header: '终端流水号',dataIndex: 'TERM_SSN',width: 100},
        {header: '外部流水号',dataIndex: 'outSsn',width: 100},
		{header: '卡号',dataIndex: 'pan',width: 150},
		{header: '交易类型',dataIndex: 'txn_num',width: 120},
		{header: '金额',dataIndex: 'amtTrans',width: 100},
		{header: '清算状态',dataIndex: 'stlmFlag',width: 180,renderer:errFlag},
		{header: '差错处理状态',dataIndex: 'err_proc_cd',width: 150,renderer:transTxnAcqType}
	]);
	
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
		
	menuArr.push(queryConditionMebu);
	
	// 交易查询
	var grid = new Ext.grid.GridPanel({
		title: '已完结差错查询',
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
			msg: '正在加载查询列表......'
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
		width : 390,
		autoHeight: true,
		items: [{
			xtype : 'dynamicCombo',
			width : 320,
			fieldLabel : '商户编号',
			methodName : 'getMchntIdInCup',
			hiddenName : 'mchtNoQ',
			editable : true
		}, {
			xtype: 'numberfield',
			width : 320,
			fieldLabel: '卡号',
			id: 'pan'
		},{
			xtype: 'basecomboselect',
			baseParams:'TXN_ACQ_TYPE',
			width : 320,
			fieldLabel: '差错处理状态',
			id: 'errProcCdQ',
			name:'errProcCd'
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
			pan: queryForm.getForm().findField('pan').getValue(),
			mchtNo : queryForm.getForm().findField('mchtNoQ').getValue(),
			errProcCd:queryForm.getForm().findField('errProcCd').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});