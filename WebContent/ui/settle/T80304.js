Ext.onReady(function() {
	
	// 商户清算信息查询
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtAlgoInfoCupCount'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		//	{name: 'settleDate',mapping: 'settleDate'},
			{name: 'mchtId',mapping: 'mchtId'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'mchtTp1',mapping: 'mchtTp1'},
			{name: 'mchtTp2',mapping: 'mchtTp2'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'transCount',mapping: 'transCount'},
			{name: 'transSum',mapping: 'transSum'},
			{name: 'fee',mapping: 'fee'},
			{name: 'errCount',mapping: 'errCount'},
			{name: 'errAmt',mapping: 'errAmt'},
			{name: 'backAmt',mapping: 'backAmt'},
			{name: 'othAmt',mapping: 'othAmt'},
			{name: 'settleAmt',mapping: 'settleAmt'},
			{name: 'accountAmt',mapping: 'accountAmt'},
			{name: 'feeSum',mapping: 'feeSum'}
		])
//	,
//		autoLoad: true
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([
	//	{header: '清算日期',dataIndex: 'settleDate',width: 80},
		{header: '商户号',dataIndex: 'mchtId',width: 130},
		{header: '商户名',dataIndex: 'mchtNm',width: 220},
		{header: '商户性质1',dataIndex: 'mchtTp1',width: 70,renderer:mchtTp},
		{header: '商户性质2',dataIndex: 'mchtTp2',width: 120,renderer:mchtTp2},
		{header: '归属机构',dataIndex: 'brhName',width: 130,},
		{header: '交易笔数',dataIndex: 'transCount',align : 'right'},
		{header: '交易金额(元)',dataIndex: 'transSum',align : 'right'},
		{header: '手续费(元)',dataIndex: 'fee',align : 'right'},
		{header: '差错笔数',dataIndex: 'errCount',align : 'right'},
		{header: '差错金额(元)',dataIndex: 'errAmt',align : 'right'},
		{header: '差错回退金额(元)',dataIndex: 'backAmt',align : 'right',width: 120},
		{header: '其他金额(元)',dataIndex: 'othAmt',align : 'right'},
		{header: '清分汇总金额(元)',dataIndex: 'settleAmt',align : 'right',width: 120},
		{header: '入帐金额(元)',dataIndex: 'accountAmt',align : 'right'},
		{header: '收单收入(元)',dataIndex: 'feeSum',align : 'right'}
	]);
	
	
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
		text: '生成报表',
		width: 85,
		id: 'report',
		iconCls: 'download',
		handler:function() {
			showMask("正在为您准备报表，请稍后。。。",grid);
			Ext.Ajax.requestNeedAuthorise({
				url: 'T80304Action.asp',
				params: {
					mchtCd: queryForm.getForm().findField('mchtCd').getValue(),
					signInstId: queryForm.getForm().findField('signInstId').getValue(),
					startDate: typeof (queryForm.findById('date').getValue()) == 'string' ? '' : queryForm.findById('date').getValue().dateFormat('Ymd'),
					endDate: typeof (queryForm.findById('date1').getValue()) == 'string' ? '' : queryForm.findById('date1').getValue().dateFormat('Ymd'),
					txnId: '80304',
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

	var menuArr = new Array();
	menuArr.push(queryConditionMebu);  //[0]
	menuArr.push('-');      			//[1]
	menuArr.push(report);      			//[2]
	
	// 商户对账交易查询
	var grid = new Ext.grid.GridPanel({
		title: '直联商户清算汇总',
		iconCls: 'T506',
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
		items: [{
			xtype:'panel',
			layout: 'form',
			items: [{
				xtype: 'basecomboselect',
		        baseParams: 'BRH_BELOW_BRANCH',
				fieldLabel: '商户所在地区',
				editable:true,
				blankText: '请选择商户所在地区',
				hiddenName: 'signInstId',
				width: 320
			}]
		},{
			xtype : 'dynamicCombo',
			methodName : 'getMchntIdInfoZ',
			fieldLabel: '商户编号',
			hiddenName: 'mchtCd',
			width: 410,
			editable: true
		},{
			xtype:'datefield',
			fieldLabel:'起始清算日期*',
			id:'date',
			name:'date',
			endDateField: 'date1',
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
//			allowBlank:false,
			width: 120,
			editable:false
		},{
			xtype:'datefield',
			fieldLabel:'结束清算日期*',
			id:'date1',
			name:'date1',
			startDateField: 'date',
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
//			allowBlank:false,
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
			signInstId: queryForm.getForm().findField('signInstId').getValue(),
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