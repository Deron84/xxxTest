Ext.onReady(function() {
	
	// 联机交易数据集
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=posTxnInfogd'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'instDate',mapping: 'instDate'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'addr',mapping: 'addr'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'settleAcctNm',mapping: 'settleAcctNm'},
			{name: 'settleAcct',mapping: 'settleAcct'},
			{name: 'pan',mapping: 'pan'},
			{name: 'instTime',mapping: 'instTime'},
			{name: 'termSsn',mapping: 'termSsn'},
			{name: 'amtSettlmt',mapping: 'amtSettlmt'},
			{name: 'btSettlmt',mapping: 'btSettlmt'},
			{name: 'transType',dataIndex: 'transType'},
			{name: 'track1Data',mapping: 'track1Data'},
			{name: 'transCode',mapping: 'transCode'}
		])
//	,
//		autoLoad: true
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([
		{header: '交易日期',dataIndex: 'instDate',width: 80},
		{header: '商户号',dataIndex: 'cardAccpId',width: 120},
		{header: '商户名称',dataIndex: 'mchtNm',width: 160},
		{header: '归属行',dataIndex: 'brhName',width:140},
		{header: '市场',dataIndex: 'addr',width: 180},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 90},
		{header: '结算账号名称',dataIndex: 'settleAcctNm',width: 70},
		{header: '结算账号',dataIndex: 'settleAcct',width: 130},
		{header: '卡号',dataIndex: 'pan',width: 140},
		{header: '交易时间',dataIndex: 'instTime',width: 160},
		{header: 'POS流水号',dataIndex: 'termSsn',width:80},
		{header: '折前金额(元)',dataIndex: 'amtSettlmt',width: 80},
		{header: '补贴金额(元)',dataIndex: 'btSettlmt',width: 80},
		{header: '交易类型',dataIndex: 'transType',width: 80, renderer:function(val){return getTransType(val)}},
		{header: '营销代码',dataIndex: 'track1Data',width:150},
		{header: '消费类别',dataIndex: 'transCode',width: 150, renderer:function(val){return getTransCode(val)}}
		
		
		
	]);

	// 应答码
	function respCode(val) {
		if(val == '00')
			return "<font color='green'>" + val + "</font>";
		else
			return "<font color='red'>" + val + "</font>";
	}
	
	
	function getTransType(val) {
		if(val == 'P'){
			return '消费';
		}else if(val == 'Y'){
			return '预授权';
		}else if(val == 'X'){
			return '撤销';
		}else if(val == 'W'){
			return '预授权完成';
		}else if(val == 'R'){
			return '冲正';
		}else {
			return '未知';
		}
	}
	
	function getTransCode(val) {
		if(val == '022'){
			return '联机消费';
		}else if(val == '036'){
			return '脱机消费';
		}else {
			return '未知';
		}
	}
	
	var menuArr = new Array();
	
	var monitorMenu = new Ext.Button({
		iconCls: 'remote_16',
		text: '启动监控模式',
		enableToggle: true,
		toggleHandler: function(bt,state) {
			// 监控模式
			if(state) {
				this.setText('停止监控模式');
				this.setIconClass('pause');
				queryWin.hide();
				queryForm.getForm().reset();
				grid.getTopToolbar().items.first().disable();
				Ext.TaskMgr.start(task);
			} else { // 手工查询模式
				this.setText('启动监控模式');
				this.setIconClass('remote_16');
				grid.getTopToolbar().items.first().enable();
				Ext.TaskMgr.stop(task);
			}
		}
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
	
	var report = {
		text: '生成交易报表',
		width: 85,
		id: 'report',
		iconCls: 'download',
		handler:function() {
			showMask("正在为您准备报表，请稍后。。。",grid);
			Ext.Ajax.requestNeedAuthorise({
				url: 'T50104Action_download.asp',
				params: {
//					mchtFlag1: queryForm.getForm().findField('mchtFlag1').getValue(),
					txnNum: queryForm.getForm().findField('txnNum').getValue(),
					startDate:typeof (queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
					endDate:typeof (queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
					localTimeStart: queryForm.getForm().findField('localTimeStart').getValue(),
					localTimeEnd: queryForm.getForm().findField('localTimeEnd').getValue(),
					brhId: queryForm.getForm().findField('brhId').getValue(),
					mchtCd: queryForm.getForm().findField('mchtCd').getValue(),
					termId: queryForm.getForm().findField('termId').getValue(),
					sysSeqNum: queryForm.getForm().findField('sysSeqNum').getValue(),
					pan: queryForm.getForm().findField('pan').getValue(),
					txnId: '50104',
					subTxnId: '01'
				},
				success: function(rsp,opt) {
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
						rspObj.msg+'&key=exl161exl';
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
//	menuArr.push('-');  //[1]
//	menuArr.push(monitorMenu);  //[2]//需求上没有这些东西  注掉  以文档需求为主
	menuArr.push('-');
	menuArr.push(report);
	// 监控模式定时器
	var task = {
		run: function() {
			txnStore.load({
				params: {start: 0}
			});
		},
		interval: 10000
	};
	
	// 交易查询(十日内)
	var grid = new Ext.grid.GridPanel({
		title: '交易查询',
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
			xtype: 'basecomboselect',
			fieldLabel: '交易类型',
			id: 'txnNumQ',
			hiddenName: 'txnNum',
		    baseParams: 'TXN_NUM_NEW',
		    width: 290,
			editable: true
		},{
			xtype: 'datefield',
			width : 290,
			id: 'startDate',
			name: 'startDate',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '交易开始日期*',
			minValue:agoDate,
			allowBlank: false,
			editable: false
		},{
			xtype: 'textfield',
			width: 290,
			fieldLabel: '交易开始时间',
			regex: /^([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$/,
          	regexText: '请按时分秒的格式输入6位数，最大235959，最小000000，如052822',
			id: 'localTimeStart',
			maxLength:6
		},{
			xtype: 'datefield',
			width : 290,
			id: 'endDate',
			name: 'endDate',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '交易结束日期*',
			maxValue:nowDate,
			allowBlank: false,
			editable: false
		},{
			xtype: 'textfield',
			width: 290,
			fieldLabel: '交易结束时间',
			regex: /^([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$/,
          	regexText: '请按时分秒的格式输入6位数，最大235959，最小000000，如052822',
			id: 'localTimeEnd',
			maxLength:6
		},{
			xtype: 'basecomboselect',
	        baseParams: 'BRH_BELOW',
			fieldLabel: '归属机构',
			width: 290,
			id: 'brhIdQ',
			hiddenName: 'brhId'
		},
		{
			xtype : 'textfield',
			fieldLabel : '收单方商户',
//			methodName : 'getMchntIdInBase',
//			hiddenName : 'mchtCd',
			id:'mchtCd',
			name:'mchtCd',
//			editable : true,
			width : 290},
//		{
//			xtype : 'dynamicCombo',
//			fieldLabel : '商户',
//			methodName : 'getMchntIdInBase',
//			hiddenName : 'mchtCd',
//			editable : true,
//			width : 290},
//		},{
//			xtype: 'basecomboselect',
//	        baseParams: 'MCHT_FALG1',
//			fieldLabel: '商户性质1',
//			id: 'idMchtFlag1Q',
//			hiddenName: 'mchtFlag1',
//			width: 290
//		},{
		{
			xtype: 'textfield',
			width: 290,
			fieldLabel: '收单方终端号',
			id: 'termId'
		},{
			xtype: 'textfield',
			width: 290,
			fieldLabel: '系统流水号',
			id: 'sysSeqNum'
		},{
			xtype: 'textfield',
			width: 290,
			fieldLabel: '卡号',
			id: 'pan'
		},{
			xtype: 'textfield',
			width: 290,
			fieldLabel: '营销代码',
			id: 'track1Data'
		},{
			xtype: 'basecomboselect',
			width: 290,
			fieldLabel: '消费类别',
			id: 'transCode',
			 store: new Ext.data.ArrayStore({
                 fields: ['valueField','displayField'], 

                 data: [['022','联机消费'],['036','脱机消费']]
             })
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
				if(queryForm.getForm().isValid()) {
					txnStore.load();
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
//			mchtFlag1: queryForm.getForm().findField('mchtFlag1').getValue(),
			txnNum: queryForm.getForm().findField('txnNum').getValue(),
			startDate:typeof (queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate:typeof (queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			localTimeStart: queryForm.getForm().findField('localTimeStart').getValue(),
			localTimeEnd: queryForm.getForm().findField('localTimeEnd').getValue(),
			brhId: queryForm.getForm().findField('brhId').getValue(),
			mchtCd: queryForm.getForm().findField('mchtCd').getValue(),
			termId: queryForm.getForm().findField('termId').getValue(),
			sysSeqNum: queryForm.getForm().findField('sysSeqNum').getValue(),
			pan: queryForm.getForm().findField('pan').getValue(),
			track1Data: queryForm.getForm().findField('track1Data').getValue(),
			transCode: queryForm.getForm().findField('transCode').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});