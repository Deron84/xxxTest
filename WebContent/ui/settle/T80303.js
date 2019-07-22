Ext.onReady(function() {
	
	// 商户清算信息查询
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtAlgoCupInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'settleDate',mapping: 'settleDate'},
			{name: 'transDate',mapping: 'transDate'},
			{name: 'brh',mapping: 'brh'},
			{name: 'mchtId',mapping: 'mchtId'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'termId',mapping: 'termId'},
			{name: 'ssn',mapping: 'ssn'},
			{name: 'pan',mapping: 'pan'},
			{name: 'tanNum',mapping: 'tanNum'},
			{name: 'amt',mapping: 'amt'},
			{name: 'fee',mapping: 'fee'},
			{name: 'cupFee',mapping: 'cupFee'},
			{name: 'acount',mapping: 'acount'}
		])
//	,
//		autoLoad: true
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([
		{header: '清算日期',dataIndex: 'settleDate',width: 80},
		{header: '交易时间',dataIndex: 'transDate',width: 90},
		{header: '收单机构',dataIndex: 'brh'},
		{header: '商户号',dataIndex: 'mchtId',width: 130},
		{header: '商户名',dataIndex: 'mchtNm',width: 240},
		{header: '终端号',dataIndex: 'termId',width: 70},
		{header: '银联流水',dataIndex: 'ssn',width: 80},
		{header: '卡号',dataIndex: 'pan',width: 160},
		{header: '交易类型',dataIndex: 'tanNum',width: 110},
		{header: '交易金额(元)',dataIndex: 'amt',width: 100,align : 'right'},
		{header: '交易手续费(元)',dataIndex: 'fee',width: 110,align : 'right'},
		{header: '应收银联手续费(元)',dataIndex: 'cupFee',width: 130,align : 'right'},
		{header: '清算金额(元)',dataIndex: 'acount',align : 'right'}
	]);
	
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
				url: 'T80303Action.asp',
				params: {
					mchtCd: queryForm.getForm().findField('mchtCd').getValue(),
					startDate: typeof (queryForm.findById('date').getValue()) == 'string' ? '' : queryForm.findById('date').getValue().dateFormat('Ymd'),
					endDate: typeof (queryForm.findById('date1').getValue()) == 'string' ? '' : queryForm.findById('date1').getValue().dateFormat('Ymd'),
					txnId: '80303',
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
	
	var menuArr = new Array();
	
	menuArr.push(queryConditionMebu);  	//[0]
	menuArr.push('-');      			//[1]
	menuArr.push(report);      			//[2]
	
	
	// 直联商户清算明细查询
	var grid = new Ext.grid.GridPanel({
		title: '直联商户清算明细',
		iconCls: 'T803',
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
			msg: '正在加载商户对账交易信息列表......'
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
		width: 550,
		autoHeight: true,
		items: [{//查询正式表的商户
			xtype : 'dynamicCombo',
			methodName : 'getMchntIdInfoZ',
			fieldLabel: '商户编号',
//			allowBlank:false,
			hiddenName: 'mchtCd',
			width: 410,
			editable: true
		},{
			xtype:'datefield',
			fieldLabel:'起始日期*',
			id:'date',
			name:'date',
			endDateField: 'date1',
			allowBlank:false,
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
			width: 120,
			editable:false
		},{
			xtype:'datefield',
			fieldLabel:'结束日期*',
			id:'date1',
			name:'date1',
			startDateField: 'date',
			allowBlank:false,
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
			width: 120,
			editable:false
		}]
	});
	
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 550,
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
				if(queryForm.getForm().isValid()){
					txnStore.load();
				}
				//queryWin.hide();
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
			mchtCd: queryForm.getForm().findField('mchtCd').getValue(),
			date: typeof (queryForm.findById('date').getValue()) == 'string' ? '' : queryForm.findById('date').getValue().dateFormat('Ymd'),
			date1: typeof (queryForm.findById('date1').getValue()) == 'string' ? '' : queryForm.findById('date1').getValue().dateFormat('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});