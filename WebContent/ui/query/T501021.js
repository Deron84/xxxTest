Ext.onReady(function() {
	
	// 联机交易数据集
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=posTxnInfoOutline'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'instDate',mapping: 'instDate'},
			{name: 'instTime',mapping: 'instTime'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'},
			{name: 'pan',mapping: 'pan'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'retrivlRef',mapping: 'retrivlRef'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'acqInstIdCode',mapping: 'acqInstIdCode'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'respCode',mapping: 'respCode'}
		])
	}); 
	txnStore.load();
	var txnColModel = new Ext.grid.ColumnModel([
		{header: '交易日期',dataIndex: 'instDate',width: 100},
		{header: '交易时间',dataIndex: 'instTime',width: 100},
		{header: '系统流水',dataIndex: 'sysSeqNum',width: 100},
		{header: '卡号',dataIndex: 'pan',width: 150},
		{header: '商户编号',dataIndex: 'cardAccpId',width: 100},
		{header: '终端编号',dataIndex: 'cardAccpTermId',width: 70},
		{header: '检索参考号',dataIndex: 'retrivlRef',width: 80},
		{header: '交易金额',dataIndex: 'amtTrans',width: 70},
		{header: '受理行',dataIndex: 'acqInstIdCode',width: 100},
		{header: '交易类型',dataIndex: 'txnNum',width: 100},
		{header: '应答码',dataIndex: 'respCode',width: 70,renderer: respCode}
	]);
	
	// 应答码
	function respCode(val) {
		if(val == '00')
			return '<font color="green">' + val + '</font>';
		else
			return '<font color="red">' + val + '</font>';
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
	
	var report = {
		text: '生成交易报表',
		width: 85,
		id: 'report',
		iconCls: 'download',
		handler:function() {
			showMask("正在为您准备报表，请稍后。。。",grid);
			Ext.Ajax.requestNeedAuthorise({
				url: 'T50102Action.asp',
				params: {
					startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
					endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
					txnNum: Ext.getCmp('txnNum').getValue(),
					mchntNo: Ext.getCmp('mchntNo').getValue(),
					brhId: Ext.getCmp('brhId').getValue(),
					txnId: '50102',
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
	menuArr.push('-');  //[1]
	menuArr.push(report);
	
	// 联机交易查询
	var grid = new Ext.grid.GridPanel({
		title: '脱机交易查询',
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
			msg: '正在加载脱机交易列表......'
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
	
	
	// 交易码数据集合
	var txnNumStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('TXN_NUM',function(ret){
		txnNumStore.loadData(Ext.decode(ret));
	});
	
	// 商户编号数据集合
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	
	// 受理行数据集合
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
		autoHeight: true,
		items: [{
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
			xtype: 'combo',
			fieldLabel: '交易类型',
			store: txnNumStore,
			editable: true,
			id: 'txnNum'
		},{
			xtype: 'combo',
			fieldLabel: '商户名称',
			store: mchntStore,
			editable: true,
			id: 'mchntNo',
			width: 360
		},{
			xtype: 'combo',
			fieldLabel: '受理机构',
			store: brhStore,
			editable: true,
			id: 'brhId'
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
				queryWin.hide();
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
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			txnNum: Ext.getCmp('txnNum').getValue(),
			mchntNo: Ext.getCmp('mchntNo').getValue(),
			brhId: Ext.getCmp('brhId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
	
});