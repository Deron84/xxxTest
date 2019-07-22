Ext.onReady(function() {
	var _txnNum;
	var _startDate;
	var _endDate;
	var _brhId;
	var _mchtCd;
	var _sysSeqNum;
	var _pan;
	var _track1Data;
	var _transCode;
	// 联机交易数据集
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=posTxnInfoHis'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'instDate',mapping: 'INST_DATE'},
			{name: 'sysSeqNum',mapping: 'SYS_SEQ_NUM'},
			{name: 'pan',mapping: 'PAN'},
			{name: 'cardAccpId',mapping: 'CARD_ACCP_ID'},
			{name: 'mchtNm',mapping: 'MCHT_NM'},
			{name: 'settleAreaNo',mapping: 'SETTLE_AREA_NO'},
			{name: 'cardAccpTermId',mapping: 'CARD_ACCP_TERM_ID'},
			{name: 'retrivlRef',mapping: 'RETRIVL_REF'},
			{name: 'amtTrans',mapping: 'AMT_TRANS'},
			{name: 'acqInstIdCode',mapping: 'ACQ_INST_ID_CODE'},
			{name: 'respCode',mapping: 'RESP_CODE'},
			{name: 'amtCdhldrBil',mapping: 'AMT_CDHLDR_BIL'},
			{name: 'cupSsn',mapping: 'CUP_SSN'},
			{name: 'dateLocalTrans',mapping: 'DATE_LOCAL_TRANS'},
			{name: 'timeLocalTrans',mapping: 'TIME_LOCAL_TRANS'},
			{name: 'transType',mapping: 'TRANS_TYPE'},
			{name: 'markSubsidyAmount',mapping: 'MARKSUBSIDY_AMOUNT'},
			{name: 'subsidyRule',mapping: 'SUBSIDY_RULE'},
			{name: 'track1Data',mapping: 'TRACK_1_DATA'},
			{name: 'transCode',mapping: 'TRANS_CODE'}
		]),
		autoLoad: false
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([
		{header: '交易时间',dataIndex: 'instDate',width: 140,align:'center'},
		{header: '系统流水',dataIndex: 'sysSeqNum',width: 60,align:'center'},
		{header: '卡号',dataIndex: 'pan',width: 150,align:'center'},
		{header: '收单方商户号',dataIndex: 'cardAccpId',width: 130,align:'center'},
		{header: '商户名称',dataIndex: 'mchtNm',width: 180,id:'mchtNm',align:'center'},
		{header: '归属机构',dataIndex: 'settleAreaNo',align:'center',renderer:function(val){return getRemoteTrans(val, "brhName");}},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 70,align:'center'},
		{header: '检索参考号',dataIndex: 'retrivlRef',width: 110,align:'center'},
		{header: '交易金额(元)',dataIndex: 'amtTrans',width: 90,align:'center'},
		{header: '代理机构标识码',dataIndex: 'acqInstIdCode',width: 100,align:'center'},
		{header: '应答码',dataIndex: 'respCode',width: 60,renderer: respCode,align:'center'},
		{header: '原始金额(元)',dataIndex: 'amtCdhldrBil',width: 90,align:'center'},
		{header: 'POS流水号',dataIndex: 'cupSsn',align:'center'},
		{header: '受卡方交易日期',dataIndex: 'dateLocalTrans',align:'center'},
		{header: '受卡方交易时间',dataIndex: 'timeLocalTrans',align:'center'},
		{header: '交易类型',dataIndex: 'transType',width: 80,align:'center', renderer:function(val){return getTransType(val)}},
		{header: '商户补贴金额',dataIndex: 'markSubsidyAmount',align:'center'},
		{header: '商户补贴规则',dataIndex: 'subsidyRule',align:'center'},
		{header: '营销代码',dataIndex: 'track1Data',align:'center'},
		{header: '消费类别',dataIndex: 'transCode',width: 150,align:'center', renderer:function(val){return getTransCode(val)}}
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
		}else if(val == 'PP'){
			return '消费(已冲正)';
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
	var excelExport = {
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
				url: 'T50103Action_download2.asp',
				params: {
					txnNum : _txnNum,
					startDate : _startDate,
					endDate : _endDate,
					brhId : _brhId,
					mchtCd : _mchtCd,
					sysSeqNum : _sysSeqNum,
					pan : _pan,
					track1Data : _track1Data,
					transCode : _transCode
				},
				success: function(rsp,opt){
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
													rspObj.msg+'&key=exl29exl';
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
	menuArr.push(queryConditionMebu);  //[0]
	menuArr.push('-');
	menuArr.push(excelExport);
	
//	menuArr.push(queryConditionMebu);  //[0]

	var task = {
		run: function() {
			txnStore.load({
				params: {start: 0}
			});
		},
		interval: 10000
	};
	
	// 历史交易查询
	var grid = new Ext.grid.GridPanel({
		title: '交易查询(十日前)',
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
//	txnStore.load();
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
			xtype: 'basecomboselect',
			fieldLabel: '交易类型',
			id: 'txnNumQ',
			hiddenName: 'txnNum',
		    baseParams: 'TXN_NUM_NEW',
		    width : 420,
			editable: true
		},{
			xtype: 'datefield',
			width : 420,
			id: 'startDate',
			name: 'startDate',
			vtype: 'daterange',
			//endDateField: 'endDate',
			fieldLabel: '交易开始日期*',
			//minValue:agoDate,
			allowBlank: false,
			editable: false
		},{
			xtype: 'datefield',
			width : 420,
			id: 'endDate',
			name: 'endDate',
			vtype: 'daterange',
			//startDateField: 'startDate',
			fieldLabel: '交易结束日期*',
			//maxValue:nowDate,
			allowBlank: false,
			editable: false
		},{
			xtype: 'basecomboselect',
	        baseParams: 'BRH_BELOW',
			fieldLabel: '归属机构',
			width: 420,
			id: 'brhIdQ',
			hiddenName: 'brhId'
		},{
			xtype : 'textfield',
			fieldLabel : '收单方商户号',
			id:'mchtCd',
			name : 'mchtCd',
//			editable : true,
			width : 420
		},{
			xtype: 'textfield',
			fieldLabel: '系统流水号',
			width : 420,
			id: 'sysSeqNum'
		},{
			xtype: 'textfield',
			fieldLabel: '卡号',
			width : 420,
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
	var excelQueryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
		autoHeight: true,
//		renderTo: 'report',
		iconCls: 'T50902',
//		waitMsgTarget: true,
//		items: [{
//			xtype: 'panel',
//			html: '<br/><br/>'
//		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确认导出',
			iconCls: 'download',
			handler: function() {
				if(!excelQueryForm.getForm().isValid()) {
					return;
				}
				excelQueryForm.getForm().submit({
					url: 'T50103Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																		action.result.msg+'&key=exl16exl';
						excelDown.hide();
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,excelQueryForm);
						excelDown.hide();
					},
					params: {
						txnId: '50103',
						subTxnId: '01'
					}
				});
			}
		},{
			text: '取消导出',
			iconCls: 'refuse',
			handler: function() {
				excelDown.hide();
			}
		}]
	});
	var excelDown = new Ext.Window({
		title: '历史交易信息',
		layout: 'fit',
		width: 350,
		autoHeight: true,
		items: [excelQueryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				excelDown.collapse();
				excelDown.getEl().pause(1);
				excelDown.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				excelDown.expand();
				excelDown.center();
			},
			qtip: '恢复',
			hidden: true
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 600,
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
        
				if(queryForm.getForm().isValid()){
					_txnNum = queryForm.getForm().findField('txnNum').getValue();
					_startDate = typeof (queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd');
					_endDate = typeof (queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd');
					_brhId = queryForm.getForm().findField('brhId').getValue();
					_mchtCd = queryForm.getForm().findField('mchtCd').getValue();
					_sysSeqNum = queryForm.getForm().findField('sysSeqNum').getValue();
					_pan = queryForm.getForm().findField('pan').getValue();
					_track1Data = queryForm.getForm().findField('track1Data').getValue();
					_transCode = queryForm.getForm().findField('transCode').getValue();
					txnStore.load();
					grid.getTopToolbar().items.items[2].enable();
					queryWin.hide();
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
			txnNum : _txnNum,
			startDate : _startDate,
			endDate : _endDate,
			brhId : _brhId,
			mchtCd : _mchtCd,
			sysSeqNum : _sysSeqNum,
			pan : _pan,
			track1Data : _track1Data,
			transCode : _transCode
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
	
});