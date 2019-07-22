Ext.onReady(function() {
	var _appId;
	var _outTradeNo;
	var _tradeNo;
	var _cmchntBrh;
	var _status;
	var _startPayTime;
	var _endPayTime;
	
	// 支付方式 
	var cmchntBrhStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['16','微信'],['15','支付宝']],
		reader: new Ext.data.ArrayReader()
	});
	
	// 对账状态
	var statusStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','未对账'],['1','对账成功'],['2','对账异常'],['3','对账不一致']],
		reader: new Ext.data.ArrayReader()
	});
	
	// 联机交易数据集
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=weChatAccount'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
            {name: 'appId',mapping: 'APPID'},
            {name: 'outTradeNo',mapping: 'OUTTRADENO'},
			{name: 'tradeNo',mapping: 'TRADENO'},
			{name: 'mchId',mapping: 'MCHID'},
			{name: 'subMchId',mapping: 'SUBMCHID'},
            {name: 'userFlag',mapping: 'USERFLAG'},	
			{name: 'tradeType',mapping: 'TRADETYPE'},
			{name: 'tradeStatus',mapping: 'TRADESTATUS'},
			{name: 'payBank',mapping: 'PAYBANK'},
			{name: 'curryType',mapping: 'CURRYTYPE'},
			{name: 'totalFee',mapping: 'TOTALFEE'},
			{name: 'enterBons',mapping: 'ENTERBONS'},
			{name: 'weChatRfdNo',mapping: 'WECHATRFDNO'},
			{name: 'refundNo',mapping: 'REFUNDNO'},
			{name: 'refundFee',mapping: 'REFUNDFEE'},
			{name: 'enterRfdBons',mapping: 'ENTERRFDBONS'},
			{name: 'refundType',mapping: 'REFUNDTYPE'},
			{name: 'refundStatus',mapping: 'REFUNDSTATUS'},
			{name: 'goodName',mapping: 'GOODNAME'},
			{name: 'packge',mapping: 'PACKGE'},
			{name: 'fee',mapping: 'FEE'},
			{name: 'feeRate',mapping: 'FEERATE'},
			{name: 'sn',mapping: 'SN'},
			{name: 'cmchntBrh',mapping: 'CMCHNTBRH'},
			{name: 'payTime',mapping: 'PAYTIME'},
			{name: 'status',mapping: 'STATUS'},
			{name: 'chkMark',mapping: 'CHKMARK'},
			{name: 'chkTime',mapping: 'CHKTIME'}
		])
	}); 
	
	var cm = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
	    {header: '公众账号ID',dataIndex: 'appId',width: 140,sortable: true,align:'center'},
        {header: '微信订单号',dataIndex: 'outTradeNo',width: 220,sortable: true,align:'center'},
        {header: '商户订单号',dataIndex: 'tradeNo',width: 180,sortable: true,align:'center'},
        {header: '商户号',dataIndex: 'mchId',width: 80,sortable: true,align:'center'},
        {header: '商户子号',dataIndex: 'subMchId',width: 100,sortable: true,align:'center'},
        {header: '用户标识',dataIndex: 'userFlag',width: 220,sortable: true,align:'center'},
		{header: '交易类型',dataIndex: 'tradeType',width: 60,sortable: true,align:'center'},
		{header: '交易状态',dataIndex: 'tradeStatus',width: 80,sortable: true,align:'center'},
        {header: '付款银行',dataIndex: 'payBank',width: 100,sortable: true,align:'center'},
        {header: '货币种类',dataIndex: 'curryType',width: 60,sortable: true,align:'center'},
		{header: '总金额(分)',dataIndex: 'totalFee',width: 100,sortable: true,align:'center'},			
		{header: '企业红包金额(分)',dataIndex: 'enterBons',width: 110,sortable: true,align:'center'},
		{header: '微信退款单号',dataIndex: 'weChatRfdNo',width: 90,sortable: true,align:'center'},
		{header: '商户退款单号',dataIndex: 'refundNo',width: 100,sortable: true,align:'center'},
		{header: '退款金额(分)',dataIndex: 'refundFee',width: 80,sortable: true,align:'center'},
		{header: '企业红包退款金额(分)',dataIndex: 'enterRfdBons',width: 130,sortable: true,align:'center'}	,
		{header: '退款类型',dataIndex: 'refundType',width: 80,sortable: true,align:'center'},
		{header: '退款状态',dataIndex: 'refundStatus',width: 80,sortable: true,align:'center'},
		{header: '商品名称',dataIndex: 'goodName',width: 100,sortable: true,align:'center'},
		{header: '商户数据包',dataIndex: 'packge',width: 80,sortable: true,align:'center'},
		{header: '手续费(分)',dataIndex: 'fee',width: 80,sortable: true,align:'center'},
		{header: '费率',dataIndex: 'feeRate',width: 60,sortable: true,align:'center'},
		{header: '设备号',dataIndex: 'sn',width: 80,sortable: true,align:'center'},
		{header: '支付机构',dataIndex: 'cmchntBrh',width: 60,sortable: true,align:'center'},
		{header: '交易时间',dataIndex: 'payTime',width: 120,sortable: true,align:'center'},
		{header: '对账状态',dataIndex: 'status',width: 80,sortable: true,align:'center'},
		{header: '对账描述',dataIndex: 'chkMark',width: 150,sortable: true,align:'center'},
		{header: '对账时间',dataIndex: 'chkTime',width: 80,sortable: true,align:'center'}
	]);
	
	
	var queryWeChatAccount = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
			queryForm.getForm().reset();
		}
	};
	
	var report = {
			text: '生成交易报表',
			width: 85,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				var totalCount=txnStore.getTotalCount();
				if(totalCount > recordNum){
					showErrorMsg("导出的数据不能超过"+ recordNum + "条",grid);
					return;
				}
				showMask("正在为您准备报表，请稍后。。。",grid);
				Ext.Ajax.request({
					url: 'T51007Action_download2.asp',
					params: {
						appId : _appId,
						outTradeNo : _outTradeNo,
						tradeNo : _tradeNo,
						cmchntBrh : _cmchntBrh,
						status : _status,
						startPayTime : _startPayTime,
						endPayTime : _endPayTime
					},
					success: function(rsp,opt){
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
														rspObj.msg+'&key=exl34exl';
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					},
					failure: function(rsp,opt){
						hideMask();
					}
				});
				//	grid.getTopToolbar().items.items[2].disable();
			}
		};
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
		labelWidth:120,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			width: 290,
			fieldLabel: '公众账号ID',
			labelStyle: 'padding-left: 5px',
			emptyText: '请输入公众账号Id',
			id: 'appId',
			maxLength:32
		},{
			xtype: 'textfield',
			width: 290,
			fieldLabel: '微信订单号',
			labelStyle: 'padding-left: 5px',
			emptyText: '请输入微信订单号',
			id: 'outTradeNo',
			maxLength:32
    	},{
    		xtype: 'textfield',
			width: 290,
			fieldLabel: '商户订单号',
			labelStyle: 'padding-left: 5px',
			emptyText: '请输入商户订单号',
			id: 'tradeNo',
			maxLength:32
		},{
			xtype: 'combo',
			store: cmchntBrhStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'cmchntBrh',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '支付机构',
			anchor: '60%',
			listWidth: 290
		},{
			xtype: 'combo',
			store: statusStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'status',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '对账状态',
			anchor: '60%',
			listWidth: 290
		},{
			xtype: 'datefield',
			width : 290,
			id: 'startPayTime',
			name: 'startPayTime',
			fieldLabel: '交易开始时间*',
			labelStyle: 'padding-left: 5px',
			allowBlank: false,
			editable: false
		},{
			xtype: 'datefield',
			width : 290,
			id: 'endPayTime',
			name: 'endPayTime',
			fieldLabel: '交易结束时间*',
			labelStyle: 'padding-left: 5px',
			allowBlank: false,
			editable: false
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
            	_appId = queryForm.getForm().findField('appId').getValue();
        		_outTradeNo = queryForm.getForm().findField('outTradeNo').getValue();
        		_tradeNo=queryForm.getForm().findField('tradeNo').getValue();
        		_cmchntBrh=queryForm.getForm().findField('cmchntBrh').getValue();
        		_status=queryForm.getForm().findField('status').getValue();
    			_startPayTime = typeof (queryForm.findById('startPayTime').getValue()) == 'string' ? '' : queryForm.findById('startPayTime').getValue().dateFormat('Ymd');
        		_endPayTime = typeof (queryForm.findById('endPayTime').getValue()) == 'string' ? '' : queryForm.findById('endPayTime').getValue().dateFormat('Ymd');
        		
        		if(_startPayTime!=''&&_endPayTime!=''&&_endPayTime<_startPayTime){
            		showErrorMsg("请保证交易结束时间不小于交易开始时间",grid);
    				return;
            	}
				if(queryForm.getForm().isValid()) {
					txnStore.load();
					grid.getTopToolbar().items.items[2].enable();
					queryWin.hide();
				}else{
					queryForm.getForm().isValid();
				}
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	var menuArr = new Array();
	
	menuArr.push(queryWeChatAccount);  //[0]
	menuArr.push('-');
	menuArr.push(report);              //[1]
	
	// 支付宝对账查询
	var grid = new Ext.grid.GridPanel({
		title: '微信对账查询',
		iconCls: 'T501',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		store: txnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: cm,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载支付宝对账列表......'
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
	grid.getTopToolbar().items.items[2].disable();
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	txnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			appId : _appId,
			outTradeNo : _outTradeNo,
			tradeNo : _tradeNo,
			cmchntBrh : _cmchntBrh,
			status : _status,
			startPayTime : _startPayTime,
			endPayTime : _endPayTime
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});