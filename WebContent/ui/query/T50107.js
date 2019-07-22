Ext.onReady(function() {
	
	var _startDate;
	var	_localTimeStart;
	var	_endDate;
	var	_localTimeEnd;
	var	_mchntNo;
	var _cMchntNo;
	var _orderInfo;
	
	// 联机交易数据集
	var payBillStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=payBillInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'tradeTime',mapping: 'TRADE_TIME'},
			{name: 'mchntID',mapping: 'MCHNT_ID'},
			{name: 'mchntOrderNo',mapping: 'MCHNT_ORDER_NO'},
			{name: 'ysOrderNo',mapping: 'YS_ORDER_NO'},
			{name: 'payType',mapping: 'PAY_TYPE'},
			{name: 'orderFee',mapping: 'ORDER_FEE'},
			{name: 'checkResult',mapping: 'CHECK_RESULT'}
		])
	}); 
	
	var payBillModel = new Ext.grid.ColumnModel([
	        new Ext.grid.RowNumberer(),
			{header: '交易时间',dataIndex: 'tradeTime',width: 140,align:'center'},
			{header: '商户号',dataIndex: 'mchntID',width: 200,align:'center'},
			{header: '商户订单号',dataIndex: 'mchntOrderNo',width: 260,align:'center'},
			{header: '第三方订单号',dataIndex: 'ysOrderNo',width: 210,align:'center'},
			{header: '支付方式',dataIndex: 'payType',width: 100,align:'center'},
			{header: '订单金额',dataIndex: 'orderFee',width: 80,align:'center'},
			{header: '对账结果',dataIndex: 'checkResult',width: 100,align:'center'}    //width: 260,
	]);


	/**
	 * 查询条件
	 */
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
		autoHeight: true,
		items: [{
            id : 'aline',  
            name : 'aline',  
            layout: 'form',  
            items : [{
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
				allowBlank: true
			},{
				xtype: 'datefield',
				width : 300,
				id: 'endDate',
				fieldLabel: '交易结束日期*',
				allowBlank: false,
				editable: true
			},{
				xtype: 'textfield',
				width: 300,
				fieldLabel: '交易结束时间',
				regex: /^([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$/,
	          	regexText: '请按时分秒的格式输入6位数，最大235959，最小000000，如052822',
				id: 'localTimeEnd',
				maxLength: 6,
				allowBlank: true
			},{
				xtype: 'textfield',
	//			labelStyle: 'padding-left: 5px',
				fieldLabel: '商户号',
				width: 300,
				maxLength: 20,
	//			vtype: 'isOverMax',
				id: 'mchntNo'
			},{
				xtype: 'textfield',
		//		labelStyle: 'padding-left: 5px',
				width: 300,
				fieldLabel: '商户订单号',
				maxLength: 50,
	//			vtype: 'isOverMax',
				id: 'cMchntNo'
			},{
				xtype: 'textfield',
		//		labelStyle: 'padding-left: 5px',
				width: 300,
				fieldLabel: '第三方订单号',
				maxLength: 32,
	//			vtype: 'isOverMax',
				id: 'orderInfo'
			}]
		}]	
	});
//	Ext.getCmp('localTimeStart').setValue('000000');
//	Ext.getCmp('localTimeEnd').setValue('235959');
	
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
				if(queryForm.getForm().isValid()) {
					var endtime=Ext.getCmp('endDate').getValue(),starttime=Ext.getCmp('startDate').getValue();
	            	if(endtime!=''&&starttime!=''&&endtime<starttime){
	            		showErrorMsg("请保证截止日期不小于起始日期",grid);
	    				return;
	            	}
//	            	var localTimeStart=Ext.getCmp('localTimeStart').getValue().replaceAll(" ", ""), localTimeEnd=Ext.getCmp('localTimeEnd').getValue().replaceAll(" ", "");
//	            	
//	            	if(localTimeStart == '' || localTimeEnd == ''){
//	            		showErrorMsg("交易时间不能为空",grid);
//	    				return;
//	            	}
	          /*  	if((!(endtime<starttime))&&(!(endtime>starttime))&&localTimeEnd!=''&&localTimeStart!=''&&localTimeEnd<localTimeStart){
	            		showErrorMsg("请保证截止时间不小于起始时间",grid);
	    				return;
	            	}*/
					_startDate = typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
					_localTimeStart = queryForm.getForm().findField('localTimeStart').getValue(),
					_endDate = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
					_localTimeEnd = queryForm.getForm().findField('localTimeEnd').getValue(),
					_mchntNo = queryForm.getForm().findField('mchntNo').getValue(),
					_cMchntNo = queryForm.getForm().findField('cMchntNo').getValue(),
					_orderInfo = queryForm.getForm().findField('orderInfo').getValue(),
					payBillStore.load();
					queryWin.hide();
					grid.getTopToolbar().items.items[1].enable();
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
	payBillStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
        	start: 0,
        	startDate:_startDate,
        	localTimeStart:_localTimeStart,
        	endDate:_endDate,
        	localTimeEnd:_localTimeEnd,
			ylMchntNo:_mchntNo,
			cMchntNo:_cMchntNo,
			orderInfo:_orderInfo
        });
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
			var totalCount=payBillStore.getTotalCount();
			if(totalCount > recordNum){
				showErrorMsg("导出的数据不能超过"+ recordNum + "条",grid);
				return;
			}
			showMask("正在为您准备报表，请稍后。。。",grid);
			Ext.Ajax.request({
				url: 'T50107Action_download2.asp',
				params: {
					startDate:_startDate,
		        	localTimeStart:_localTimeStart,
		        	endDate:_endDate,
		        	localTimeEnd:_localTimeEnd,
					ylMchntNo:_mchntNo,
					cMchntNo:_cMchntNo,
					orderInfo:_orderInfo
				},
				success: function(rsp,opt){
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
													rspObj.msg+'&key=exl28exl';
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				},
				failure: function(rsp,opt){
					hideMask();
				}
			});
		
		}
	};

	var menuArr = new Array();
	menuArr.push(queryConditionMebu);  //[0]
	menuArr.push(report);
	 
	// 菜品明细查询
	var grid = new Ext.grid.GridPanel({
		title: '不平账查询',
		iconCls: 'T501',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		store: payBillStore,
		cm: payBillModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载联机交易列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: payBillStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	grid.getTopToolbar().items.items[1].disable();
	new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});