Ext.onReady(function() {
	
	var _startDate;
	var	_localTimeStart;
	var	_endDate;
	var	_localTimeEnd;
	var	_mchntNo;
	var	_termNo;
	var _repNo;
	
	// 联机交易数据集
	var vegeStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=vegDetailInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'tradeTime',mapping: 'TRADE_TIME'},
			{name: 'repNum',mapping: 'REPNUM'},
			{name: 'mchtNm',mapping: 'MCHT_NM'},
			{name: 'mchntNo',mapping: 'MCHNT_NO'},
			{name: 'termNo',mapping: 'TERM_NO'},
			{name: 'vegeCode',mapping: 'VEGE_CODE'},
			{name: 'vegeName',mapping: 'VEGE_NAME'},
			{name: 'weight',mapping: 'WEIGHT'},
			{name: 'amount',mapping: 'AMOUNT'},
			{name: 'price',mapping: 'PRICE'},
			{name: 'payType',mapping: 'PAYTYPE'},
			{name: 'agrBr',mapping: 'AGR_BR'},
			{name: 'brhName',mapping: 'BRH_NAME'},
			{name: 'repeatFlag',mapping: 'REPEAT_FLAG'},
			{name: 'uploadTime',mapping: 'UPLOAD_TIME'}
		])
	}); 
	
	var vegeColModel = new Ext.grid.ColumnModel([
	        new Ext.grid.RowNumberer(),
			{header: '交易时间',dataIndex: 'tradeTime',width: 140,align:'center'},
			{header: '终端流水号',dataIndex: 'repNum',width: 140,align:'center'},
			{header: '商户名称',dataIndex: 'mchtNm',width: 140,align:'center'},
			{header: '商户号',dataIndex: 'mchntNo',width: 140,align:'center'},
			{header: '终端号',dataIndex: 'termNo',width: 100,align:'center'},
			{header: '菜品编码',dataIndex: 'vegeCode',width: 100,align:'center'},
			{header: '菜品名称',dataIndex: 'vegeName',width: 100,align:'center'},
			{header: '重量（KG）',dataIndex: 'weight',width: 80,align:'center'},
			{header: '价格（元）',dataIndex: 'amount',width: 80,align:'center'},
			{header: '单价(元/KG)',dataIndex: 'price',width: 80,align:'center'},
			{header: '支付方式',dataIndex: 'payType',width: 120,align:'center'},
			{header: '机构号',dataIndex: 'agrBr',width: 100,align:'center'},
			{header: '归属机构',dataIndex: 'brhName',width: 120,align:'center'},
			{header: '上传标志',dataIndex: 'repeatFlag',width: 100,align:'center'},
			{header: '上传时间',dataIndex: 'uploadTime',width: 140,align:'center'}
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
				editable: false
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
				xtype : 'textfield',
				fieldLabel : '商户号',
				id:'mchntNo',
				width : 300
			},{
				xtype: 'textfield',
				width: 300,
				fieldLabel: '终端号',
				id: 'termNo'
			},{
				xtype: 'textfield',
				width: 300,
				fieldLabel: '终端流水号',
				id: 'repNo'
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
	            	var localTimeStart=Ext.getCmp('localTimeStart').getValue().replaceAll(" ", ""), localTimeEnd=Ext.getCmp('localTimeEnd').getValue().replaceAll(" ", "");
	 
	            	if((!(endtime<starttime))&&(!(endtime>starttime))&&localTimeEnd!=''&&localTimeStart!=''&&localTimeEnd<localTimeStart){
	            		showErrorMsg("请保证截止时间不小于起始时间",grid);
	    				return;
	            	}
					_startDate = typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
					_localTimeStart = queryForm.getForm().findField('localTimeStart').getValue(),
					_endDate = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
					_localTimeEnd = queryForm.getForm().findField('localTimeEnd').getValue(),
					_mchntNo = queryForm.getForm().findField('mchntNo').getValue(),
					_termNo = queryForm.getForm().findField('termNo').getValue(),
					_repNo = queryForm.getForm().findField('repNo').getValue(),
					vegeStore.load();
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
	vegeStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
        	start: 0,
        	startDate: _startDate,
			localTimeStart: _localTimeStart,
			endDate: _endDate,
			localTimeEnd: _localTimeEnd,
			mchntNo: _mchntNo,
			termNo: _termNo,
			repNo: _repNo,
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
			var totalCount=vegeStore.getTotalCount();
			if(totalCount > recordNum){
				showErrorMsg("导出的数据不能超过"+ recordNum + "条",grid);
				return;
			}
			showMask("正在为您准备报表，请稍后。。。",grid);
			Ext.Ajax.request({
				url: 'T50106Action_download2.asp',
				params: {
					startDate :_startDate,
					localTimeStart : _localTimeStart,
					endDate : _endDate,
					localTimeEnd : _localTimeEnd,
					mchntNo	: _mchntNo,
					termNo : _termNo,
					repNo: _repNo,
				},
				success: function(rsp,opt){
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
													rspObj.msg+'&key=exl24exl';
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
		title: '菜品明细查询',
		iconCls: 'T501',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		store: vegeStore,
		cm: vegeColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载联机交易列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: vegeStore,
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