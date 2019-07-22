Ext.onReady(function() {
	
	// 商户清算信息查询
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtPayInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'trans_date',mapping: 'trans_date'},
			{name: 'trans_date_time',mapping: 'trans_date_time'},
			{name: 'mcht_cd',mapping: 'mcht_cd'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mcht_flag1',mapping: 'mcht_flag1'},
			{name: 'mcht_flag2',mapping: 'mcht_flag2'},
			{name: 'term_id',mapping: 'term_id'},
			{name: 'arg_br',mapping: 'arg_br'},
			{name: 'ins_nm',mapping: 'ins_nm'},
			{name: 'seq_nm',mapping: 'seq_nm'},
			{name: 'retrivl_ref',mapping: 'retrivl_ref'},
			{name: 'inst_id_code',mapping: 'inst_id_code'},
			{name: 'pan',mapping: 'pan'},
			{name: 'acct_id2',mapping: 'acct_id2'},
			{name: 'cupFee',mapping: 'cupFee'},
			{name: 'amt_trans',mapping: 'amt_trans'}
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
		{header: '付款日期',dataIndex: 'trans_date',width: 80},
		{header: '付款时间',dataIndex: 'trans_date_time',width: 80,sortable: true},
		{header: '商户号',dataIndex: 'mcht_cd',width: 130},
		{header: '商户组别',dataIndex: 'mchtGrp',width: 80},
		{header: '商户性质1',dataIndex: 'mcht_flag1',id: 'mcht_flag1',renderer:mchtTp},
		{header: '商户性质2',dataIndex: 'mcht_flag2',id: 'mcht_flag2',renderer:mchtTp2},
		{header: '终端编号',dataIndex: 'term_id',width: 70},
		{header: '终端所属机构',dataIndex: 'arg_br',width: 80},
		{header: '第三方服务机构',dataIndex: 'ins_nm',renderer:function(val){return getRemoteTrans(val,'organName')}},
		{header: '系统流水',dataIndex: 'seq_nm',width: 120},
		{header: '检索参考号',dataIndex: 'retrivl_ref',width: 160},
		{header: '收单机构码',dataIndex: 'inst_id_code',width: 110},
		{header: '付款转出账户',dataIndex: 'pan',width: 100,align : 'right'},
		{header: '付款转入账户',dataIndex: 'acct_id2',width: 110,align : 'right'},
		{header: '交易类型',dataIndex: 'cupFee',width: 130,align : 'right'},
		{header: '付款金额(元)',dataIndex: 'amt_trans',align : 'right'}
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
				url: 'T80402Action.asp',
				params: {
					mchtCd: queryForm.getForm().findField('mchtCd').getValue(),
					termId: queryForm.getForm().findField('termId').getValue(),
					mchtFlag1: queryForm.getForm().findField('mchtFlag1').getValue(),
					mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
					startDate: typeof (queryForm.findById('date').getValue()) == 'string' ? '' : queryForm.findById('date').getValue().dateFormat('Ymd'),
					endDate: typeof (queryForm.findById('date1').getValue()) == 'string' ? '' : queryForm.findById('date1').getValue().dateFormat('Ymd'),
					txnId: '80402',
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
		title: '间联商户付款交易明细',
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
			msg: '正在加载商户付款交易信息列表......'
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