Ext.onReady(function() {
	
	var _startDate;
	var	_localTimeStart;
	var	_endDate;
	var	_localTimeEnd;
	
	// 联机交易数据集
	var payBillStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=comparePayBill'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'accDate',mapping: 'ACC_DATE'},
			{name: 'fileName',mapping: 'FILE_NAME'},
			{name: 'steps',mapping: 'STEPS'},
			{name: 'lastTime',mapping: 'LAST_TIME'}
		])
	}); 
	
	var payBillModel = new Ext.grid.ColumnModel([
	        new Ext.grid.RowNumberer(),
			{header: '交易日期',dataIndex: 'accDate',width: 120,align:'center'},
			{header: '对账文件名',dataIndex: 'fileName',width: 380,align:'center'},
			{header: '对账进度',dataIndex: 'steps',width: 180,align:'center'},
			{header: '处理时间',dataIndex: 'lastTime',width: 140,align:'center'}
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
    			width : 250,
    			id: 'startDate',
    			fieldLabel: '交易开始日期*',
    			allowBlank: false,
    			editable: false
			},{
				xtype: 'datefield',
				width : 250,
				id: 'endDate',
				fieldLabel: '交易结束日期*',
				allowBlank: false,
				editable: true
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

//	            	if((!(endtime<starttime))&&(!(endtime>starttime))&&localTimeEnd!=''&&localTimeStart!=''&&localTimeEnd<localTimeStart){
//	            		showErrorMsg("请保证截止时间不小于起始时间",grid);
//	    				return;
//	            	}
					_startDate = typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
					_localTimeStart = '',
					_endDate = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
					_localTimeEnd = ''
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
        	localTimeEnd:_localTimeEnd
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
				url: 'T50108Action_download2.asp',
				params: {
					startDate:_startDate,
		        	localTimeStart:_localTimeStart,
		        	endDate:_endDate,
		        	localTimeEnd:_localTimeEnd
				},
				success: function(rsp,opt){
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
													rspObj.msg+'&key=exl30exl';
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
		title: '对账查询',
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