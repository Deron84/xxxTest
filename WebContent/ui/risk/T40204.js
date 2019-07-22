Ext.onReady(function() {
	
	// 风险交易数据集
	var riskTxnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtRiskHistory'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'saTxnCard',mapping: 'saTxnCard'},
			{name: 'saMchtNo',mapping: 'saMchtNo'},
			{name: 'saTermNo',mapping: 'saTermNo'},
			{name: 'saTxnNum',mapping: 'saTxnNum'},
			{name: 'saTxnAmt',mapping: 'saTxnAmt'},
			{name: 'saTxnDate',mapping: 'saTxnDate'},
			{name: 'saTxnTime',mapping: 'saTxnTime'},
			{name: 'saClcAction',mapping: 'saClcAction'}
		])
	}); 
	
	var riskColModel = new Ext.grid.ColumnModel([
		{header: '交易卡号',dataIndex: 'saTxnCard',width: 150,id:'saTxnCard'},
		{header: '交易商户号',dataIndex: 'saMchtNo',width: 150},
		{header: '交易终端号',dataIndex: 'saTermNo',width: 100},
		{header: '交易码',dataIndex: 'saTxnNum'},
		{header: '交易金额（元）',dataIndex: 'saTxnAmt',width: 150},
		{header: '交易日期',dataIndex: 'saTxnDate',width: 100,renderer: formatTs},
		{header: '交易时间',dataIndex: 'saTxnTime',width: 100},
		{header: '受控动作',dataIndex: 'saClcAction',width: 100,renderer: clcAction}
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
	var report = {
		text: '生成报表',
		width: 85,
		id: 'report',
		iconCls: 'download',
		handler:function() {
			showMask("正在为您准备报表，请稍后。。。",grid);
			Ext.Ajax.requestNeedAuthorise({
				url: 'T40204Action.asp',
				params: {
					srMerNo: queryForm.findById('srMerNo').getValue(),
					srBrhNo: queryForm.findById('srBrhNo').getValue(),
					startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
					endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
					txnId: '40204',
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
	};
	
	menuArr.push(queryConditionMebu);  //[0]
	menuArr.push('-');
	menuArr.push(report);              //[1]
	
	// 历史风险记录
	var grid = new Ext.grid.GridPanel({
		title: '黑名单商户历史交易记录',
		iconCls: 'T40204',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		autoExpandColumn: 'saTxnCard',
		clicksToEdit: true,
		store: riskTxnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载黑名单商户历史交易列表......'
		},
		tbar: 	menuArr,
		bbar: new Ext.PagingToolbar({
			store: riskTxnStore,
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
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'srMerNo',
			name: '',
			vtype: 'alphanum',
			fieldLabel: '受控商户号'
		},{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '交易开始日期'
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '交易结束日期'
		},{
			xtype: 'textfield',
			id: 'srBrhNo',
			name: 'srBrhNo',
			vtype: 'alphanum',
			fieldLabel: '分行号'
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
				riskTxnStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	riskTxnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			srMerNo: queryForm.findById('srMerNo').getValue(),
			srBrhNo: queryForm.findById('srBrhNo').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});