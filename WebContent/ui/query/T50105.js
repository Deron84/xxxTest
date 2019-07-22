Ext.onReady(function() {
	var _payType;
	var _startDate;
	var _localTimeStart;
	var _endDate;
	var _localTimeEnd;
	var _merchantNo;
	var _transCode;
	var _repCode;
	var _orderNum;
	var _payStatus;
	var _merchantName;
	var _brhId;
	
	var payStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	
	SelectOptionsDWR.getMchntData('PAY_TYPE',function(ret){
		payStore.loadData(Ext.decode(ret));
	});
	
	// 联机交易数据集
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=posTxnInfoAir'    // posTxnInfoAll
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
//			{name: 'tradeTime',mapping: 'TRADE_TIME'},
//			{name: 'ylMchntNo',mapping: 'YL_MCHNT_NO'},
//			{name: 'ylTermNo',mapping: 'YL_TERM_NO'},
//			{name: 'accountNum',mapping: 'ACCOUNT_NUM'},
//			{name: 'mchntNum',mapping: 'MCHNT_NUM'},
//			{name: 'replenNum',mapping: 'REPLEN_NUM'},
//			{name: 'tradeMon',mapping: 'TRADE_MON'},
//			{name: 'payType',mapping: 'PAY_TYPE'},
//			{name: 'agrBr',mapping: 'AGR_BR'},
//			{name: 'mchtNm',mapping: 'MCHT_NM'},
//			{name: 'brhName',mapping: 'BRH_NAME'}
			{name: 'orderTime',mapping: 'ORDER_TIME'},
			{name: 'mchtNm',mapping: 'MCHT_NM'},
			{name: 'settleAreaNo',mapping: 'SETTLE_AREA_NO'},
			{name: 'infoId',mapping: 'INFO_ID'},
			{name: 'ylMchntNo',mapping: 'Yl_MCHNT_NO'},
			{name: 'ylTermNo',mapping: 'YL_TERM_NO'},
			{name: 'termOrderId',mapping: 'TERM_ORDER_ID'},
			{name: 'orgFee',mapping: 'ORG_FEE'},
			{name: 'orderFee',mapping: 'ORDER_FEE'},
			{name: 'fee',mapping: 'FEE'},
			{name: 'noticePayType',mapping: 'PAY_TYPE'},
			{name: 'noticeStatus',mapping: 'NOTICE_STATUS'},
			{name: 'cMchntNo',mapping: 'C_MCHNT_NO'},
			{name: 'noticeOrderId',mapping: 'NOTICE_ORDER_ID'},
			{name: 'cMchntBrh',mapping: 'C_MCHNT_BRH'},
			{name: 'orderRespTime',mapping: 'ORDER_RESP_TIME'},
			{name: 'agrBr',mapping: 'AGR_BR'},
			{name: 'brhName',mapping: 'BRH_NAME'},
			{name: 'sellerId',mapping: 'SELLER_ID'},
			{name: 'buyerId',mapping: 'BUYER_ID'},
			{name: 'sellerAccount',mapping: 'SELLER_EMAIL'},
			{name: 'buyerAccount',mapping: 'BUYER_LOGON_ID'}
		])
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([
	        new Ext.grid.RowNumberer(),
//			{header: '交易时间',dataIndex: 'tradeTime',width: 140,align:'center'},
//			{header: '商户号',dataIndex: 'ylMchntNo',width: 175,align:'center'},
//			{header: '终端号',dataIndex: 'ylTermNo',width: 175,align:'center'},
//			{header: '卡号',dataIndex: 'accountNum',width: 175,align:'center'},
//			{header: '商户订单号',dataIndex: 'mchntNum',width: 250,align:'center'},
//			{header: '流水号',dataIndex: 'replenNum',width: 165,align:'center'},
//			{header: '交易金额',dataIndex: 'tradeMon',width: 75,align:'center'},
//			{header: '支付方式',dataIndex: 'payType',width: 190,align:'center'},
//			{header: '签约网点',dataIndex: 'agrBr',width: 75,align:'center'},
//			{header: '商户名称',dataIndex: 'mchtNm',width: 145,align:'center'},
//			{header: '网点名称',dataIndex: 'brhName',width: 120,align:'center'}
	        {header: '交易时间',dataIndex: 'orderTime',width: 140,align:'center'},
			{header: '商户名称',dataIndex: 'mchtNm',width: 150,align:'center'},
			{header: '归属机构',dataIndex: 'settleAreaNo',width: 150,align:'center',renderer:function(val){return getRemoteTrans(val, "brhName");}},
			{header: '银联商户号',dataIndex: 'ylMchntNo',width: 140,align:'center'},
			{header: '银联终端号',dataIndex: 'ylTermNo',width: 140,align:'center'},
			{header: '终端流水号',dataIndex: 'termOrderId',width: 135,align:'center'},
			{header: '商户订单号',dataIndex: 'infoId',width: 235,align:'center'},
			{header: '原始金额（元）',dataIndex: 'orgFee',width: 85,align:'center'},
			{header: '交易金额（元）',dataIndex: 'orderFee',width: 85,align:'center'},
			{header: '手续费（元）',dataIndex: 'fee',width: 85,align:'center'},
			{header: '支付方式',dataIndex: 'noticePayType',width: 80,align:'center'},
			{header: '交易状态',dataIndex: 'noticeStatus',width: 75,align:'center'},
			{header: '扫码支付商户号',dataIndex: 'cMchntNo',width: 140,align:'center'},
			{header: '第三方订单号',dataIndex: 'noticeOrderId',width: 135,align:'center'},
			{header: '第三方机构',dataIndex: 'cMchntBrh',width: 135,align:'center'},
			{header: '异步通知时间',dataIndex: 'orderRespTime',width: 140,align:'center'},
			{header: '签约网点',dataIndex: 'agrBr',width: 75,align:'center'},
			{header: '网点名称',dataIndex: 'brhName',width: 135,align:'center'},
			{header: '支付宝卖家ID',dataIndex: 'sellerId',width: 100,align:'center'},
			{header: '支付宝买家ID',dataIndex: 'buyerId',width: 100,align:'center'},
			{header: '支付宝卖家账号',dataIndex: 'sellerAccount',width: 100,align:'center'},
			{header: '支付宝买家账号',dataIndex: 'buyerAccount',width: 100,align:'center'}
	]);

	/**
	 * 查询条件
	 */
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
	//	height: 285,
		autoHeight: true,
		items: [{
            id : 'aline',  
            name : 'aline',  
            layout: 'form',  
            items : [
//{  
//            	xtype: 'basecomboselect',
//    			width: 300,
//    			fieldLabel: '支付类型',
//    			id: 'payType',
//    			store: new Ext.data.ArrayStore({
//    	            fields: ['valueField','displayField'], 
//    	            data: [
//    	                   ['all','所有'],
////    	                   ['unionPay','银联卡支付'],
//    	                   ['scan','扫码支付'],
//    	                   ['weChat','微信支付'],
//    	                   ['aliPay','支付宝支付'],
//    	                   ['qqPay','QQ支付']
//    	            ]
//    	        }),
//    	        listeners: {
//    	        	select:function(combo,record,opts) {  
//    		        	var flag = record.data.valueField;
//    		        	if(flag == 'all'){
////    		        		queryForm.findById('zline').setVisible(false);  
////    		            	queryForm.findById('zline2').setVisible(false);  
////    		            	queryForm.findById('zline3').setVisible(false);  
//    		            	queryForm.findById('zline').setVisible(false); 
//    		            	queryForm.findById('zline2').setVisible(true); 
//    		            	queryForm.findById('zline3').setVisible(true); 
//    		        	}else if(flag == 'unionPay'){
//    		        		//  是否可操作 		
//    		      //  		queryForm.findById('merchants').setDisabled(true);  
//    		        		//  是否显示
//    		        		queryForm.findById('zline').setVisible(true); 
//    		            	queryForm.findById('zline2').setVisible(false); 
//    		            	queryForm.findById('zline3').setVisible(false); 
//    		        	}else if(flag == 'weChat' || flag == 'aliPay' || flag == 'scan' || flag == 'qqPay'){
//    		        		queryForm.findById('zline').setVisible(false); 
//    		            	queryForm.findById('zline2').setVisible(true); 
//    		            	queryForm.findById('zline3').setVisible(true); 
//    		        	}else{
//    		        		queryForm.findById('zline').setVisible(false); 
//    		            	queryForm.findById('zline2').setVisible(false);  
//    		            	queryForm.findById('zline3').setVisible(false);
//    		        	}	
//            	   } 
//    	        },
//    	        editable: false
           // },
            {
            	xtype: 'combo',
				store: payStore,
				displayField: 'displayField',
				valueField: 'valueField',
				emptyText: '请选择',
				id:'payType',
			//	mode: 'local',
			//	triggerAction: 'all',
			//	forceSelection: true,
			//	typeAhead: true,
			//	selectOnFocus: true,
				editable: false,
				fieldLabel: '支付类型',
				width:300
            },{
            	xtype: 'datefield',
    			width : 300,
    			id: 'startDate',
    			fieldLabel: '交易开始日期*',
    			allowBlank: false,
    			editable: false
			},{
				xtype: 'textfield',
				width: 300,
				fieldLabel: '交易开始时间',
				regex: /^([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$/,
	          	regexText: '请按时分秒的格式输入6位数，最大235959，最小000000，如052822',
				id: 'localTimeStart',
				maxLength: 6,
				minLength: 6,
				allowBlank: true,
			},{
				xtype: 'datefield',
				width : 300,
				id: 'endDate',
				fieldLabel: '交易结束日期*',
				allowBlank: false,
				editable: false
			},{
				xtype: 'textfield',
				width: 300,
				fieldLabel: '交易结束时间',
				regex: /^([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$/,
	          	regexText: '请按时分秒的格式输入6位数，最大235959，最小000000，如052822',
				id: 'localTimeEnd',
				maxLength: 6,
				minLength: 6,
				allowBlank: true,
			},{
				xtype: 'basecomboselect',
		        baseParams: 'BRH_BELOW',
				fieldLabel: '归属机构',
				width: 300,
				id: 'brhIdQ',
				editable: true,
				hiddenName: 'brhId'
			},{
				xtype : 'textfield',
				fieldLabel : '商户名称',
				id:'merchantName',
				width : 300
			},{
				xtype : 'textfield',
				fieldLabel : '商户号',
				id:'merchantNo',
				width : 300
			},{
				xtype: 'textfield',
				width: 300,
				fieldLabel: '终端号',
				id: 'transCode'
			}]
		},{
			 id : 'zline',  
			 name : 'zline',  
			 layout: 'form', 
			 items : [{ 
					xtype: 'textfield',
	     			width: 300,
	     			fieldLabel: '卡号',
	     			id: 'repCode'
			}]
		},{
			id : 'zline2',  
			 name : 'zline2',  
			 layout: 'form',
			 items : [{ 
				xtype: 'textfield',
				width: 300,
    			fieldLabel: '商户订单号',
    			id: 'repCode2'
			}]	
		},{
			id : 'zline3',  
			 name : 'zline3',  
			 layout: 'form',
			 items : [{ 
				xtype: 'textfield',
				width: 300,
				fieldLabel: '终端流水号',
				id: 'orderNum'
			}]	
		},{ 
			 layout: 'form',
			 items : [{ 
				xtype: 'basecomboselect',
    			width: 300,
    			fieldLabel: '支付状态',
    			id: 'payStatus',
    			store: new Ext.data.ArrayStore({
    	            fields: ['valueField','displayField'], 
    	            data: [
    	                   ['0','失败'],
    	                   ['1','成功'],
    	                   ['3','退款'],
    	                   ['2','暂未支付']
    	            ]
    	        })
			}]	
		}]
	});
	queryForm.findById('zline').setVisible(false); 
	queryForm.findById('zline2').setVisible(true); 
	queryForm.findById('zline3').setVisible(true); 
//	Ext.getCmp('localTimeStart').setValue('000000');
//	Ext.getCmp('localTimeEnd').setValue('235959');
	
	var pageBar = new Ext.PagingToolbar({
		store: txnStore,
		pageSize: System[QUERY_RECORD_COUNT],
		displayInfo: true,
		displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
		emptyMsg: '没有找到符合条件的记录'
	})
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
	//	height : 350,
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
            	var endtime=Ext.getCmp('endDate').getValue(),starttime=Ext.getCmp('startDate').getValue();
            	if(endtime!=''&&starttime!=''&&endtime<starttime){
            		showErrorMsg("请保证截止日期不小于起始日期",grid);
    				return;
            	}
            	var localTimeStart=Ext.getCmp('localTimeStart').getValue().replaceAll(" ", ""), localTimeEnd=Ext.getCmp('localTimeEnd').getValue().replaceAll(" ", "");
            	
//            	if(localTimeStart == '' || localTimeEnd == ''){
//            		showErrorMsg("交易时间不能为空",grid);
//    				return;
//            	}
            	if((!(endtime<starttime))&&(!(endtime>starttime))&&localTimeEnd!=''&&localTimeStart!=''&&localTimeEnd<localTimeStart){
            		showErrorMsg("请保证截止时间不小于起始时间",grid);
    				return;
            	}
				if(queryForm.getForm().isValid()) {
					var midVar = Ext.getCmp('payType').getValue();
					if(midVar == 'unionPay'){
						txnStore = new Ext.data.Store({
							baseParams: { 
								payType: queryForm.getForm().findField('payType').getValue(),
								startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
								localTimeStart: queryForm.getForm().findField('localTimeStart').getValue(),
								endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
								localTimeEnd: queryForm.getForm().findField('localTimeEnd').getValue(),
								merchantNo: queryForm.getForm().findField('merchantNo').getValue(),
								transCode: queryForm.getForm().findField('transCode').getValue(),
								repCode:  queryForm.getForm().findField('repCode').getValue(),
								merchantName:queryForm.getForm().findField('merchantName').getValue(),
								brhId:queryForm.getForm().findField('brhId').getValue()
							},
							proxy: new Ext.data.HttpProxy({
								url: 'gridPanelStoreAction.asp?storeId=posTxnInfoTxn'
							}),
							reader: new Ext.data.JsonReader({
								root: 'data',
								totalProperty: 'totalCount'
							},[
								{name: 'instDate',mapping: 'INST_DATE'},
								{name: 'mappingMchntcdtwo',mapping: 'MAPPING_MCHNTCDTWO'},
								{name: 'cardAccpTermId',mapping: 'CARD_ACCP_TERM_ID'},
								{name: 'sysSeqNum',mapping: 'SYS_SEQ_NUM'},
								{name: 'amtTrans',mapping: 'AMT_TRANS'},
								{name: 'amtSettlmt',mapping: 'AMT_SETTLMT'},
								{name: 'pan',mapping: 'PAN'},
								{name: 'transType',mapping: 'TRANS_TYPE'},
								{name: 'revsalFlag',mapping: 'REVSAL_FLAG'},
								{name: 'respCode',mapping: 'RESP_CODE'},
								{name: 'transState',mapping: 'TRANS_STATE'},
								{name: 'markSubsidyAmount',mapping: 'MARKSUBSIDY_AMOUNT'},
								{name: 'subsidyRule',mapping: 'SUBSIDY_RULE'},
								{name: 'agrBr',mapping: 'AGR_BR'},
								{name: 'mchtNm',mapping: 'MCHT_NM'},
								{name: 'settleAreaNo',mapping: 'SETTLE_AREA_NO'},
								{name: 'brhName',mapping: 'BRH_NAME'},
								{name: 'sellerId',mapping: 'SELLER_ID'},
								{name: 'buyerId',mapping: 'BUYER_ID'},
								{name: 'sellerAccount',mapping: 'SELLER_EMAIL'},
								{name: 'buyerAccount',mapping: 'BUYER_LOGON_ID'}
							])
						}); 
						
						txnColModel = new Ext.grid.ColumnModel([
						    new Ext.grid.RowNumberer(),
							{header: '交易时间',dataIndex: 'instDate',width: 145,align:'center'},
							{header: '银联商户号',dataIndex: 'mappingMchntcdtwo',width: 145,align:'center'},
							{header: '银联终端号',dataIndex: 'cardAccpTermId',width: 90,align:'center'},
							{header: '流水号',dataIndex: 'sysSeqNum',width: 95,align:'center'},
							{header: '原始金额（元）',dataIndex: 'amtTrans',width: 70,align:'center'},
							{header: '交易金额（元）',dataIndex: 'amtSettlmt',width: 70,align:'center'},
							{header: '银联卡号',dataIndex: 'pan',width: 140,align:'center'},
							{header: '交易类型',dataIndex: 'transType',width: 80,align:'center'},
							{header: '冲正标识',dataIndex: 'revsalFlag',width: 80,align:'center'},
							{header: '应答码',dataIndex: 'respCode',width: 80,align:'center'},
							{header: '交易状态',dataIndex: 'transState',width: 80,align:'center'},
							{header: '商户补贴金额',dataIndex: 'markSubsidyAmount',width: 120,align:'center'},
							{header: '商户补贴规则',dataIndex: 'subsidyRule',width: 220,align:'center'},
							{header: '签约网点',dataIndex: 'agrBr',width: 100,align:'center'},
							{header: '商户名称',dataIndex: 'mchtNm',width: 150,align:'center'},
							{header: '归属机构',dataIndex: 'settleAreaNo',width: 150,align:'center',renderer:function(val){return getRemoteTrans(val, "brhName");}},
							{header: '网点名称',dataIndex: 'brhName',width: 100,align:'center'},
							{header: '支付宝卖家ID',dataIndex: 'sellerId',width: 100,align:'center'},
							{header: '支付宝买家ID',dataIndex: 'buyerId',width: 100,align:'center'},
							{header: '支付宝卖家账号',dataIndex: 'sellerAccount',width: 100,align:'center'},
							{header: '支付宝买家账号',dataIndex: 'buyerAccount',width: 100,align:'center'}
						]);
						grid.reconfigure(txnStore,txnColModel);
						pageBar.bindStore(txnStore);
						pageBar.doRefresh();
						txnStore.load();
			
						_payType = queryForm.getForm().findField('payType').getValue();
						_startDate = typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd');
						_localTimeStart = queryForm.getForm().findField('localTimeStart').getValue();
						_endDate = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd');
						_localTimeEnd = queryForm.getForm().findField('localTimeEnd').getValue();
						_merchantNo = queryForm.getForm().findField('merchantNo').getValue();
						_transCode = queryForm.getForm().findField('transCode').getValue();
						_repCode = queryForm.getForm().findField('repCode').getValue();
						_merchantName = queryForm.getForm().findField('merchantName').getValue();
						_brhId = queryForm.getForm().findField('brhId').getValue();
					}else{
						txnStore = new Ext.data.Store({
							baseParams: { 
								payType: queryForm.getForm().findField('payType').getValue(),
								startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
								localTimeStart: queryForm.getForm().findField('localTimeStart').getValue(),
								endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
								localTimeEnd: queryForm.getForm().findField('localTimeEnd').getValue(),
								merchantNo: queryForm.getForm().findField('merchantNo').getValue(),
								transCode: queryForm.getForm().findField('transCode').getValue(),
								repCode:  queryForm.getForm().findField('repCode2').getValue(),
								orderNum:  queryForm.getForm().findField('orderNum').getValue(),
								payStatus: queryForm.getForm().findField('payStatus').getValue(),
								merchantName:queryForm.getForm().findField('merchantName').getValue(),
								brhId:queryForm.getForm().findField('brhId').getValue()
							},
							proxy: new Ext.data.HttpProxy({
								url: 'gridPanelStoreAction.asp?storeId=posTxnInfoAir'
							}),
							reader: new Ext.data.JsonReader({
								root: 'data',
								totalProperty: 'totalCount'
							},[
								{name: 'orderTime',mapping: 'ORDER_TIME'},
								{name: 'mchtNm',mapping: 'MCHT_NM'},
								{name: 'settleAreaNo',mapping: 'SETTLE_AREA_NO'},
								{name: 'infoId',mapping: 'INFO_ID'},
								{name: 'ylMchntNo',mapping: 'Yl_MCHNT_NO'},
								{name: 'ylTermNo',mapping: 'YL_TERM_NO'},
								{name: 'termOrderId',mapping: 'TERM_ORDER_ID'},
								{name: 'orgFee',mapping: 'ORG_FEE'},
								{name: 'orderFee',mapping: 'ORDER_FEE'},
								{name: 'fee',mapping: 'FEE'},
								{name: 'noticePayType',mapping: 'PAY_TYPE'},
								{name: 'noticeStatus',mapping: 'NOTICE_STATUS'},
								{name: 'cMchntNo',mapping: 'C_MCHNT_NO'},
								{name: 'noticeOrderId',mapping: 'NOTICE_ORDER_ID'},
								{name: 'cMchntBrh',mapping: 'C_MCHNT_BRH'},
								{name: 'orderRespTime',mapping: 'ORDER_RESP_TIME'},
								{name: 'agrBr',mapping: 'AGR_BR'},
								{name: 'brhName',mapping: 'BRH_NAME'},
								{name: 'sellerId',mapping: 'SELLER_ID'},
								{name: 'buyerId',mapping: 'BUYER_ID'},
								{name: 'sellerAccount',mapping: 'SELLER_EMAIL'},
								{name: 'buyerAccount',mapping: 'BUYER_LOGON_ID'}
							])
						}); 
						
						txnColModel = new Ext.grid.ColumnModel([
						    new Ext.grid.RowNumberer(),
							{header: '交易时间',dataIndex: 'orderTime',width: 140,align:'center'},
							{header: '商户名称',dataIndex: 'mchtNm',width: 150,align:'center'},
							{header: '归属机构',dataIndex: 'settleAreaNo',width: 150,align:'center',renderer:function(val){return getRemoteTrans(val, "brhName");}},
							{header: '商户号',dataIndex: 'ylMchntNo',width: 140,align:'center'},
							{header: '终端号',dataIndex: 'ylTermNo',width: 140,align:'center'},
							{header: '终端流水号',dataIndex: 'termOrderId',width: 135,align:'center'},
							{header: '商户订单号',dataIndex: 'infoId',width: 235,align:'center'},
							{header: '原始金额（元）',dataIndex: 'orgFee',width: 80,align:'center'},
							{header: '交易金额（元）',dataIndex: 'orderFee',width: 80,align:'center'},
							{header: '手续费（元）',dataIndex: 'fee',width: 85,align:'center'},
							{header: '支付方式',dataIndex: 'noticePayType',width: 80,align:'center'},
							{header: '交易状态',dataIndex: 'noticeStatus',width: 75,align:'center'},
							{header: '扫码支付商户号',dataIndex: 'cMchntNo',width: 135,align:'center'},
							{header: '第三方订单号',dataIndex: 'noticeOrderId',width: 135,align:'center'},
							{header: '第三方机构',dataIndex: 'cMchntBrh',width: 135,align:'center'},
							{header: '异步通知时间',dataIndex: 'orderRespTime',width: 140,align:'center'},
							{header: '签约网点',dataIndex: 'agrBr',width: 75,align:'center'},
							{header: '网点名称',dataIndex: 'brhName',width: 135,align:'center'},
							{header: '支付宝卖家ID',dataIndex: 'sellerId',width: 100,align:'center'},
							{header: '支付宝买家ID',dataIndex: 'buyerId',width: 100,align:'center'},
							{header: '支付宝卖家账号',dataIndex: 'sellerAccount',width: 100,align:'center'},
							{header: '支付宝买家账号',dataIndex: 'buyerAccount',width: 100,align:'center'}
						]);
						grid.reconfigure(txnStore,txnColModel);
						pageBar.bindStore(txnStore);
						pageBar.doRefresh();
						txnStore.load();
						
						_payType = queryForm.getForm().findField('payType').getValue();
						_startDate = typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd');
						_localTimeStart = queryForm.getForm().findField('localTimeStart').getValue();
						_endDate = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd');
						_localTimeEnd = queryForm.getForm().findField('localTimeEnd').getValue();
						_merchantNo = queryForm.getForm().findField('merchantNo').getValue();
						_transCode = queryForm.getForm().findField('transCode').getValue();
						_repCode = queryForm.getForm().findField('repCode2').getValue();
						_orderNum = queryForm.getForm().findField('orderNum').getValue();
						_payStatus = queryForm.getForm().findField('payStatus').getValue();
						_merchantName = queryForm.getForm().findField('merchantName').getValue();
						_brhId = queryForm.getForm().findField('brhId').getValue();
					}
			//		else{
//						txnStore = new Ext.data.Store({
//							baseParams: { 
//								payType: queryForm.getForm().findField('payType').getValue(),
//								startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
//								localTimeStart: queryForm.getForm().findField('localTimeStart').getValue(),
//								endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
//								localTimeEnd: queryForm.getForm().findField('localTimeEnd').getValue(),
//								merchantNo: queryForm.getForm().findField('merchantNo').getValue(),
//								transCode: queryForm.getForm().findField('transCode').getValue(),
//								repCode:  queryForm.getForm().findField('repCode2').getValue(),
//								orderNum:  queryForm.getForm().findField('orderNum').getValue(),
//								payStatus: queryForm.getForm().findField('payStatus').getValue(),
//								merchantName:queryForm.getForm().findField('merchantName').getValue(),
//								brhId:queryForm.getForm().findField('brhId').getValue()
//							},
//							proxy: new Ext.data.HttpProxy({
//								url: 'gridPanelStoreAction.asp?storeId=posTxnInfoAir'
//							}),
//							reader: new Ext.data.JsonReader({
//								root: 'data',
//								totalProperty: 'totalCount'
//							},[
//								{name: 'orderTime',mapping: 'ORDER_TIME'},
//								{name: 'mchtNm',mapping: 'MCHT_NM'},
//								{name: 'settleAreaNo',mapping: 'SETTLE_AREA_NO'},
//								{name: 'infoId',mapping: 'INFO_ID'},
//								{name: 'ylMchntNo',mapping: 'Yl_MCHNT_NO'},
//								{name: 'ylTermNo',mapping: 'YL_TERM_NO'},
//								{name: 'termOrderId',mapping: 'TERM_ORDER_ID'},
//								{name: 'orgFee',mapping: 'ORG_FEE'},
//								{name: 'orderFee',mapping: 'ORDER_FEE'},
//								{name: 'noticePayType',mapping: 'PAY_TYPE'},
//								{name: 'noticeStatus',mapping: 'NOTICE_STATUS'},
//								{name: 'cMchntNo',mapping: 'C_MCHNT_NO'},
//								{name: 'noticeOrderId',mapping: 'NOTICE_ORDER_ID'},
//								{name: 'cMchntBrh',mapping: 'C_MCHNT_BRH'},
//								{name: 'orderRespTime',mapping: 'ORDER_RESP_TIME'},
//								{name: 'agrBr',mapping: 'AGR_BR'},
//								{name: 'brhName',mapping: 'BRH_NAME'}
//							])
//						}); 
//						
//						txnColModel = new Ext.grid.ColumnModel([
//						    new Ext.grid.RowNumberer(),
//							{header: '交易时间',dataIndex: 'orderTime',width: 140,align:'center'},
//							{header: '商户名称',dataIndex: 'mchtNm',width: 150,align:'center'},
//							{header: '归属机构',dataIndex: 'settleAreaNo',width: 150,align:'center',renderer:function(val){return getRemoteTrans(val, "brhName");}},
//							{header: '商户号',dataIndex: 'ylMchntNo',width: 140,align:'center'},
//							{header: '终端号',dataIndex: 'ylTermNo',width: 140,align:'center'},
//							{header: '终端流水号',dataIndex: 'termOrderId',width: 135,align:'center'},
//							{header: '商户订单号',dataIndex: 'infoId',width: 235,align:'center'},
//							{header: '原始金额（元）',dataIndex: 'orgFee',width: 80,align:'center'},
//							{header: '交易金额（元）',dataIndex: 'orderFee',width: 80,align:'center'},
//							{header: '支付方式',dataIndex: 'noticePayType',width: 80,align:'center'},
//							{header: '交易状态',dataIndex: 'noticeStatus',width: 75,align:'center'},
//							{header: '扫码支付商户号',dataIndex: 'cMchntNo',width: 135,align:'center'},
//							{header: '第三方订单号',dataIndex: 'noticeOrderId',width: 135,align:'center'},
//							{header: '第三方机构',dataIndex: 'cMchntBrh',width: 135,align:'center'},
//							{header: '异步通知时间',dataIndex: 'orderRespTime',width: 140,align:'center'},
//							{header: '签约网点',dataIndex: 'agrBr',width: 75,align:'center'},
//							{header: '网点名称',dataIndex: 'brhName',width: 135,align:'center'}
//						]);
//						grid.reconfigure(txnStore,txnColModel);
//						pageBar.bindStore(txnStore);
//						pageBar.doRefresh();
//						txnStore.load();
//						
//						_payType = queryForm.getForm().findField('payType').getValue();
//						_startDate = typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd');
//						_localTimeStart = queryForm.getForm().findField('localTimeStart').getValue();
//						_endDate = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd');
//						_localTimeEnd = queryForm.getForm().findField('localTimeEnd').getValue();
//						_merchantNo = queryForm.getForm().findField('merchantNo').getValue();
//						_transCode = queryForm.getForm().findField('transCode').getValue();
//						_repCode = queryForm.getForm().findField('repCode2').getValue();
//						_orderNum = queryForm.getForm().findField('orderNum').getValue();
//						_payStatus = queryForm.getForm().findField('payStatus').getValue();
//						_merchantName = queryForm.getForm().findField('merchantName').getValue();
//						_brhId = queryForm.getForm().findField('brhId').getValue();
//					}
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
			var midVar = Ext.getCmp('payType').getValue();
			if(midVar == 'unionPay'){
				Ext.Ajax.request({
					url: 'T50105Action_download2.asp',
					params: {
						payType:_payType,
						startDate: _startDate,
						localTimeStart: _localTimeStart,
						endDate: _endDate,
						localTimeEnd: _localTimeEnd,
						merchantNo: _merchantNo,
						transCode: _transCode,
						repCode: _repCode,
						merchantName :_merchantName,
						brhId : _brhId
					},
					success: function(rsp,opt){
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
														rspObj.msg+'&key=exl22exl';
						} else {
							showErrorMsg(rspObj.msg,grid2);
						}
					},
					failure: function(rsp,opt){
						hideMask();
					}
				});
			}else if(midVar == 'weChat' || midVar == 'aliPay' || midVar == 'scan' ||  midVar == 'qqPay'){
				Ext.Ajax.request({
					url: 'T50105Action_download2.asp',
					params: {
						payType:_payType,
						startDate: _startDate,
						localTimeStart: _localTimeStart,
						endDate: _endDate,
						localTimeEnd: _localTimeEnd,
						merchantNo: _merchantNo,
						transCode: _transCode,
						repCode: _repCode,
						orderNum: _orderNum,
						payStatus: _payStatus,
						merchantName :_merchantName,
						brhId : _brhId
					},
					success: function(rsp,opt){
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
														rspObj.msg+'&key=exl22exl';
						} else {
							showErrorMsg(rspObj.msg,grid2);
						}
					},
					failure: function(rsp,opt){
						hideMask();
					}
				});
			}else{
//				Ext.Ajax.request({
//					url: 'T50105Action_download2.asp',
//					params: {
//						payType:_payType,
//						startDate: _startDate,
//						localTimeStart: _localTimeStart,
//						endDate: _endDate,
//						localTimeEnd: _localTimeEnd,
//						merchantNo: _merchantNo,
//						transCode: _transCode,
//					},
//					success: function(rsp,opt){
//						hideMask();
//						var rspObj = Ext.decode(rsp.responseText);
//						if(rspObj.success) {
//							window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
//														rspObj.msg+'&key=exl22exl';
//						} else {
//							showErrorMsg(rspObj.msg,grid2);
//						}
//					},
//					failure: function(rsp,opt){
//						hideMask();
//					}
//				});
				Ext.Ajax.request({
					url: 'T50105Action_download2.asp',
					params: {
						payType:_payType,
						startDate: _startDate,
						localTimeStart: _localTimeStart,
						endDate: _endDate,
						localTimeEnd: _localTimeEnd,
						merchantNo: _merchantNo,
						transCode: _transCode,
						repCode: _repCode,
						orderNum: _orderNum,
						payStatus: _payStatus,
						merchantName :_merchantName,
						brhId : _brhId
					},
					success: function(rsp,opt){
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
														rspObj.msg+'&key=exl22exl';
						} else {
							showErrorMsg(rspObj.msg,grid2);
						}
					},
					failure: function(rsp,opt){
						hideMask();
					}
				});
			}	
		}
	};
	
	
	var menuArr = new Array();
	menuArr.push(queryConditionMebu);  //[0]
	menuArr.push('-');  //[0]
	menuArr.push(report);


	// 交易查询
	var grid = new Ext.grid.GridPanel({
		title: '二维码交易查询',
		iconCls: 'T501',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		store: txnStore,
		cm: txnColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载联机交易列表......'
		},
		tbar: menuArr,
		bbar: pageBar
	});
	grid.getTopToolbar().items.items[2].disable();
	new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});