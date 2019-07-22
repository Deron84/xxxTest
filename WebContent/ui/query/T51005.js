Ext.onReady(function() {
	var _mchtName;
	var _agrBr;
	var _marketerName;
	var _cityName;
	var _orderFee;
	var _startDate;
	var _endDate;
	// 联机交易数据集
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=dealMchtTxnInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
            {name: 'cityName',mapping: 'CITY_NAME'},
            {name: 'agrBr',mapping: 'BRH_NAME'},
			{name: 'mchtNm',mapping: 'MCHT_NM'},
			{name: 'ylMchntNo',mapping: 'Yl_MCHNT_NO'},
			{name: 'ylTermNo',mapping: 'Yl_TERM_NO'},
            {name: 'marketerName',mapping: 'MARKETER_NAME'},	
			{name: 'TotalFee',mapping: 'TOTAL_FEE'},
			{name: 'totalNum',mapping: 'TOTAL_NUM'},
			{name: 'wechatTotalFee',mapping: 'WECHAT_TOTAL_FEE'},
			{name: 'wechatTotalNum',mapping: 'WECHAT_TOTAL_NUM'},
			{name: 'alipayTotalFee',mapping: 'ALIPAY_TOTAL_FEE'},
			{name: 'alipayTotalNum',mapping: 'ALIPAY_TOTAL_NUM'},
			{name: 'bankCardFee',mapping: 'BANK_CARD_FEE'},
			{name: 'bankCardNum',mapping: 'BANK_CARD_NUM'},
			{name: 'otherFee',mapping: 'OTHER_FEE'},
			{name: 'otherNum',mapping: 'OTHER_NUM'}
		])
	}); 
	
	var cm = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
	    {header: '地区',dataIndex: 'cityName',width: 80,sortable: true,align:'center'},
        {header: '机构名称',dataIndex: 'agrBr',width: 120,sortable: true,align:'center'},
        {header: '商户名称',dataIndex: 'mchtNm',width: 140,sortable: true,align:'center'},
        {header: '商户号',dataIndex: 'ylMchntNo',width: 120,sortable: true,align:'center'},
        {header: '终端号',dataIndex: 'ylTermNo',width: 120,sortable: true,align:'center'},
        {header: '营销员',dataIndex: 'marketerName',width: 80,sortable: true,align:'center'},
		{header: '总金额',dataIndex: 'TotalFee',width: 80,sortable: true,align:'center'},
		{header: '总笔数',dataIndex: 'totalNum',width: 80,sortable: true,align:'center'},
        {header: '微信总金额',dataIndex: 'wechatTotalFee',width: 80,sortable: true,align:'center'},
        {header: '微信总笔数',dataIndex: 'wechatTotalNum',width: 80,sortable: true,align:'center'},
		{header: '支付宝总金额',dataIndex: 'alipayTotalFee',width: 100,sortable: true,align:'center'},			
		{header: '支付宝总笔数',dataIndex: 'alipayTotalNum',width: 100,sortable: true,align:'center'},
		{header: '银联总金额',dataIndex: 'bankCardFee',width: 100,sortable: true,align:'center'},
		{header: '银联总笔数',dataIndex: 'bankCardNum',width: 100,sortable: true,align:'center'},
		{header: '其他总金额',dataIndex: 'otherFee',width: 80,sortable: true,align:'center'},
		{header: '其他总笔数',dataIndex: 'otherNum',width: 80,sortable: true,align:'center'}	
	]);
	
	
	var queryConditionMebu = {
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
				url: 'T51005Action_download2.asp',
				params: {
					mchtName: _mchtName,
					agrBr : _agrBr,
					marketerName : _marketerName,
					cityName :_cityName,
					orderFee : _orderFee,
					startDate : _startDate,
					endDate : _endDate
				},
				success: function(rsp,opt){
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
													rspObj.msg+'&key=exl32exl';
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
	var menuArr = new Array();
	
	menuArr.push(queryConditionMebu);  //[0]
	menuArr.push('-');
	menuArr.push(report);              //[1]
	
	// 交易查询(报表)
	var grid = new Ext.grid.GridPanel({
		title: '交易查询（终端）',
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
			msg: '正在加载联机交易列表......'
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
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
		autoHeight: true,
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '商户名称',
			methodName: 'getMchntNameNew',
			hiddenName: 'mchtName',
			editable: true,
			width: 290
		},{
    		xtype: 'dynamicCombo',
    		width: 290,
	        methodName: 'getBranchIdAll',
			fieldLabel: '所属机构',
			editable:true,
			id: 'agrBrId',
			hiddenName:'agrBr'
    	},{
    		xtype: 'textfield',
			width: 290,
			fieldLabel: '营销员',
			emptyText: '请输入营销员姓名',
			id: 'marketerName'
		},{
			xtype: 'dynamicCombo',
    		width: 290,
	        methodName: 'getCityName',
			fieldLabel: '地区名称',
			editable:true,
			id: 'cityNameId',
			hiddenName:'cityName'
		},{
			xtype: 'textfield',
			width: 290,
			fieldLabel: '有效金额(元)',
			id: 'orderFee',
			regex:/^\d+(\.\d{1,2})?$/,
			regexText:'只能输入数字和小数点，并且最多两位小数',
			value:1.00
		},{
			xtype: 'datefield',
			width : 290,
			id: 'startDate',
			name: 'startDate',
			fieldLabel: '交易开始日期*',
			allowBlank: false,
			editable: false
		},{
			xtype: 'datefield',
			width : 290,
			id: 'endDate',
			name: 'endDate',
			fieldLabel: '交易结束日期*',
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
            	var endtime=Ext.getCmp('endDate').getValue(),
            	starttime=Ext.getCmp('startDate').getValue();
            	if(endtime!=''&&starttime!=''&&endtime<starttime){
            		showErrorMsg("请保证交易结束日期不小于交易开始日期",grid);
    				return;
            	}
            	_mchtName = queryForm.getForm().findField('mchtName').getValue();
        		_agrBr = queryForm.getForm().findField('agrBr').getValue();
        		_marketerName=queryForm.getForm().findField('marketerName').getValue();
        		_cityName=queryForm.getForm().findField('cityName').getValue();
        		_orderFee=queryForm.getForm().findField('orderFee').getValue();
    			_startDate = typeof (queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd');
        		_endDate = typeof (queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd');
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
	txnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			mchtName: _mchtName,
			agrBr : _agrBr,
			marketerName : _marketerName,
			cityName :_cityName,
			orderFee : _orderFee,
			startDate : _startDate,
			endDate : _endDate
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});