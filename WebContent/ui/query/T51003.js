Ext.onReady(function() {
	var _startDate;
	var _endDate;		
	var _merchantNo;
	var _brhId;
	var statStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=statRepInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'brhId',mapping: 'BRH_ID'},
			{name: 'brhName',mapping: 'BRH_NAME'},
			{name: 'mchtNo',mapping: 'MCHT_NO'},
			{name: 'mchtNm',mapping: 'MCHT_NM'},
			{name: 'num',mapping: 'NUM'},
			{name: 'amtTrans',mapping: 'AMT_TRANS'},
			{name: 'amtSettlmt',mapping: 'AMT_SETTLMT'},
			{name: 'preFee',mapping: 'PREFEE'},
			{name: 'settleAcctSnd',mapping: 'SETTLE_ACCT_SND'},
			{name: 'settleBankNoSnd',mapping: 'SETTLE_BANK_NO_SND'},
			{name: 'settleBankNmSnd',mapping: 'SETTLE_BANK_NM_SND'},
			{name: 'settleAcctNmSnd',mapping: 'SETTLE_ACCT_NM_SND'}
		])
	});
	var statColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
		{header: '机构号',dataIndex: 'brhId',width: 100,align:'center'},
		{header: '机构名称',dataIndex: 'brhName',width: 125,align:'center'},
		{header: '商户号',dataIndex: 'mchtNo',width: 200,align:'center'},
		{header: '商户名称',dataIndex: 'mchtNm',width: 200,align:'center'},
		{header: '交易量',dataIndex: 'num',width: 80,align:'center'},
		{header: '交易金额',dataIndex: 'amtTrans',width: 80,align:'center'},
		{header: '清算金额',dataIndex: 'amtSettlmt',width: 80,align:'center'},
		{header: '优惠金额',dataIndex: 'preFee',width: 80,align:'center'},
		{header: '清算账户',dataIndex: 'settleAcctSnd',width: 100,align:'center'},
		{header: '联行号',dataIndex: 'settleBankNoSnd',width: 100,align:'center'},
		{header: '开户行',dataIndex: 'settleBankNmSnd',width: 100,align:'center'},
		{header: '户名',dataIndex: 'settleAcctNmSnd',width: 100,align:'center'}
	]);
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
	//	height: 230,
		autoHeight: true,
        items : [{
        	xtype: 'datefield',
			width : 300,
			id: 'startDate',
			fieldLabel: '交易开始日期*',
			allowBlank: false,
			editable: false
		},{
			xtype: 'datefield',
			width : 300,
			id: 'endDate',
			fieldLabel: '交易结束日期*',
			allowBlank: false,
			editable: false
		},{
			xtype : 'textfield',
			fieldLabel : '商户号',
			id:'merchantNo',
			name:'merchantNo',
			width : 300,
			maxLength: 50,
			vtype: 'isNumber',
			emptyText: '请输入商户号',
			maxLengthText: '终端号最多可以输入50个数字',
			blankText: '该输入项只能包含数字'
		},{
			xtype: 'textfield',
			width: 300,
			fieldLabel: '机构号',
			id: 'brhId',
			name: 'brhId',
			maxLength: 10,
			vtype: 'isNumber',
			emptyText: '请输入商户号',
			maxLengthText: '终端号最多可以输入10个数字',
			blankText: '该输入项只能包含数字'
			
		}]
		
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
	//	height : 295,
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
					statStore.load();
					_startDate = typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd');
					_endDate = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd');		
					_merchantNo = queryForm.getForm().findField('merchantNo').getValue();
					_brhId = queryForm.getForm().findField('brhId').getValue();
					
					Ext.getCmp('statInfo').enable();
					Ext.getCmp('report').enable();
					queryWin.hide(grid);
				}
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	statStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
        	start: 0,
    		startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
    		endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),		
    		merchantNo: queryForm.getForm().findField('merchantNo').getValue(),
    		brhId: queryForm.getForm().findField('brhId').getValue()
        });
    });
	var queryConditionMebu = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	var statForm = new Ext.form.FormPanel({
		frame: true,
//		border: true,
		width: 300,
	//	height: 230,
		autoHeight: true,
        items : [{
        	xtype: 'textfield',
			width: 200,
			fieldLabel: '交易量(总)',
			id: 'tradeNum',
			name: 'tradeNum',
			cls:'textfield-red', 
		//	disabled:true
			readOnly:true
        },{
        	xtype: 'textfield',
			width: 200,
			fieldLabel: '原始金额(总)',
			id: 'origFee',
			name: 'origFee',
			cls:'textfield-red', 
			readOnly:true
        },{
        	xtype: 'textfield',
			width: 200,
			fieldLabel: '交易金额(总)',
			id: 'tradeFee',
			name: 'tradeFee',
			cls:'textfield-red', 
			readOnly:true
        },{
        	xtype: 'textfield',
			width: 200,
			fieldLabel: '优惠金额(总)',
			id: 'preFee',
			name: 'preFee',
			cls:'textfield-red', 
			readOnly:true
        }]
	});
	
	var statWin = new Ext.Window({
		title: '统计结果',
		layout: 'fit',
		width: 350,
	//	height : 295,
		autoHeight: true,
		items: [statForm],
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
				statWin.collapse();
				statWin.getEl().pause(1);
				statWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				statWin.expand();
				statWin.center();
			},
			qtip: '恢复',
			hidden: true
		}]
	});
	
	var statInfo = {
			text: '统计结果',
			width: 85,
			id: 'statInfo',
			iconCls: 'upload',
			handler:function() {
				Ext.Ajax.requestNeedAuthorise({
					url: 'gridPanelStoreAction.asp?storeId=statRepInfo2',
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						statForm.get('tradeNum').setValue(rspObj.data[0].NUM);
						statForm.get('origFee').setValue(rspObj.data[0].AMT_SETTLMT);
						statForm.get('tradeFee').setValue(rspObj.data[0].AMT_TRANS);
						statForm.get('preFee').setValue(rspObj.data[0].PREFEE);
						statWin.show();
					},
					params: { 
						start: 0,
						startDate: _startDate,
						endDate: _endDate,		
						merchantNo: _merchantNo,
						brhId: _brhId
					}
				});
			}
		};
	var report = {
		text: '生成交易报表',
		width: 85,
		id: 'report',
		iconCls: 'download',
		handler:function() {
			var totalCount=statStore.getTotalCount();
			if(totalCount > recordNum){
				showErrorMsg("导出的数据不能超过"+ recordNum + "条",grid);
				return;
			}
			showMask("正在为您准备报表，请稍后。。。",grid);
			Ext.Ajax.request({
				url: 'T51003Action_download2.asp',
				params: {
					startDate: _startDate,
					endDate: _endDate,		
					merchantNo: _merchantNo,
					brhId: _brhId
				},
				success: function(rsp,opt){
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
													rspObj.msg+'&key=exl21exl';
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
	menuArr.push(queryConditionMebu);
	menuArr.push('-');
	menuArr.push(statInfo);
	menuArr.push('-');
	menuArr.push(report);
	menuArr.push('-');
	var grid = new Ext.grid.GridPanel({
		title: '交易统计',
		region: 'center',
//		width: 650,
		autoWidth : true,
		iconCls: 'pos',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: statStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: statColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载统计信息详细列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: statStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
 	});
	Ext.getCmp('statInfo').disable();
	Ext.getCmp('report').disable();
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})