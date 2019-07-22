Ext.onReady(function() {
	
	// 商户清算信息查询
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtAlgoInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'trans_date',mapping: 'trans_date'},
			{name: 'trans_date_time',mapping: 'trans_date_time'},
			{name: 'date_settlmt',mapping: 'date_settlmt'},
			{name: 'txn_ssn',mapping: 'txn_ssn'},
			{name: 'pan',mapping: 'pan'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'trans_amt',mapping: 'trans_amt'},
			{name: 'transFee',mapping: 'transFee'},
			{name: 'cupFee',mapping: 'cupFee'},
			{name: 'fwFee',mapping: 'fwFee'},
			{name: 'settl_amt',mapping: 'settl_amt'},
			{name: 'mcht_cd',mapping: 'mcht_cd'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mchtFlag1',mapping: 'mchtFlag1'},
			{name: 'term_id',mapping: 'term_id'},
			{name: 'txn_num',mapping: 'txn_num'},
			{name: 'acq_ins_id_cd',mapping: 'acq_ins_id_cd'},
			{name: 'oldFlag',mapping: 'oldFlag'},
			{name: 'refFlag',mapping: 'refFlag'}
		])
//	,
//		autoLoad: true
	}); 
//	trans_date          交易日期
//	trans_date_time     交易时间
//	date_settlmt        清算日期
//	txn_ssn             交易流水号
//	pan                 卡号
//	trans_amt           交易金额
//	settl_amt           清算金额
//	mcht_cd             商户号
//	term_id             终端号
//	txn_num             内部交易码
//	acq_ins_id_cd       收单机构银联码
	
	var txnColModel = new Ext.grid.ColumnModel([
		{header: '清算日期',dataIndex: 'date_settlmt',width: 80},
		{header: '交易日期',dataIndex: 'trans_date',width: 80,sortable: true},
		{header: '交易时间',dataIndex: 'trans_date_time',width: 80},
		{header: '商户号',dataIndex: 'mcht_cd',width: 130},
		{header: '商户组别',dataIndex: 'mchtGrp',id: 'mchtGrp',renderer:mchtGrp},
		{header: '商户性质1',dataIndex: 'mchtFlag1',id: 'mchtFlag1',renderer:mchtTp},
		{header: '终端号',dataIndex: 'term_id',width: 70},
		{header: '系统流水',dataIndex: 'txn_ssn',width: 80},
		{header: '检索参考号',dataIndex: 'refFlag'},
		{header: '收单机构银联码',dataIndex: 'acq_ins_id_cd',width: 120},
		{header: '卡号',dataIndex: 'pan',width: 160},
		{header: '卡类型',dataIndex: 'cardType',width: 160},
		{header: '交易类型',dataIndex: 'txn_num',width: 110},
		{header: '交易金额(元)',dataIndex: 'trans_amt',width: 100,align : 'right'},
		{header: '交易手续费(元)',dataIndex: 'transFee',width: 110,align : 'right'},
		{header: '应付银联手续费(元)',dataIndex: 'cupFee',width: 130,align : 'right'},
		{header: '品牌服务费(元)',dataIndex: 'fwFee',width: 110,align : 'right'},
		{header: '清算金额(元)',dataIndex: 'settl_amt',align : 'right'}
//		{header: '备注',dataIndex: 'oldFlag',align : 'oldFlag',renderer:backFlag}
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
	
	var queryCountMebu = {
			text: '汇总查询',
			width: 85,
			id: 'queryCount',
			iconCls: 'query',
			handler:function() {
				queryCountWin.show();
			}
		};
	
		
	var report = {
		text: '生成报表',
		width: 85,
		id: 'report',
		iconCls: 'download',
		handler:function() {
			showMask("正在为您准备报表，请稍后。。。",grid);
			Ext.Ajax.requestNeedAuthorise({
				url: 'T50601Action.asp',
				params: {
					mchtCd: queryForm.getForm().findField('mchtCd').getValue(),
					termId: queryForm.getForm().findField('termId').getValue(),
					mchtFlag1: queryForm.getForm().findField('mchtFlag1').getValue(),
					mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
					startDate: typeof (queryForm.findById('date').getValue()) == 'string' ? '' : queryForm.findById('date').getValue().dateFormat('Ymd'),
					endDate: typeof (queryForm.findById('date1').getValue()) == 'string' ? '' : queryForm.findById('date1').getValue().dateFormat('Ymd'),
					txnId: '50601',
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
	menuArr.push('-');      //[1]
	menuArr.push(report);      //[2]
	
	// 商户对账交易查询
	var grid = new Ext.grid.GridPanel({
		title: '间联商户清算明细',
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
			msg: '正在加载商户对账交易信息列表......'
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
		width: 550,
		autoHeight: true,
		items: [{//查询正式表的商户
			xtype : 'dynamicCombo',
			methodName : 'getMchntIdJ',
			fieldLabel: '商户编号',
//			allowBlank:false,
			hiddenName: 'mchtCd',
			width: 410,
			editable: true
		},{
			xtype: 'textfield',
			fieldLabel: '终端号',
			id: 'termId',
			name: 'termId',
			width: 410
		},{	
			xtype: 'basecomboselect',
	        baseParams: 'MCHT_FALG1',
			fieldLabel: '商户性质1',
			id: 'idMchtFlag1Q',
			hiddenName: 'mchtFlag1',
			width: 410
		},{
			xtype: 'basecomboselect',
        	baseParams: 'MCHNT_GRP_ALL',
			fieldLabel: '商户组别',
			hiddenName: 'mchtGrp',
			width: 410
		},{
			xtype : 'basecomboselect',
			fieldLabel : '卡类型',
			hiddenName : 'cardType',
			baseParams : 'DISC_FLAG_NUM',
			id:'cardTypeQ',
			width: 410
		},{
			xtype:'datefield',
			fieldLabel:'清算起始日期*',
			id:'date',
			name:'date',
			endDateField: 'date1',
//			allowBlank:false,
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
			width: 120,
			editable:false
		},{
			xtype:'datefield',
			fieldLabel:'清算结束日期*',
			id:'date1',
			name:'date1',
			startDateField: 'date',
//			allowBlank:false,
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
			width: 120,
			editable:false
		}]
	});
	
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 550,
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
				if(queryForm.getForm().isValid()){
					txnStore.load();
					queryWin.hide();
				}
//				queryWin.hide();
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
			mchtCd: queryForm.getForm().findField('mchtCd').getValue(),
			termId: queryForm.getForm().findField('termId').getValue(),
			mchtFlag1: queryForm.getForm().findField('mchtFlag1').getValue(),
			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
			cardType: queryForm.getForm().findField('cardType').getValue(),
			date: typeof (queryForm.findById('date').getValue()) == 'string' ? '' : queryForm.findById('date').getValue().dateFormat('Ymd'),
			date1: typeof (queryForm.findById('date1').getValue()) == 'string' ? '' : queryForm.findById('date1').getValue().dateFormat('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});