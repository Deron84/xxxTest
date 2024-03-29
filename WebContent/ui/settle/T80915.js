Ext.onReady(function() {

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=backTransInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'dateSettle',mapping: 'dateSettle'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtScctNm',mapping: 'mchtScctNm'},
			{name: 'pan',mapping: 'pan'},
			{name: 'transDate',mapping: 'transDate'},
			{name: 'transAmt',mapping: 'transAmt'},
			{name: 'backAmt',mapping: 'backAmt'},
			{name: 'backFee',mapping: 'backFee'}
		]),
		autoLoad: true
	});
	
	var paramColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '清算日期',dataIndex: 'dateSettle',width:90},
		{header: '商户编号',dataIndex: 'mchtNo',width:130},
		{header: '商户账户名',dataIndex: 'mchtScctNm',width:160},
		{header: '退货卡号',dataIndex: 'pan',width:120},
		{header: '交易日期',dataIndex: 'transDate'},
		{header: '原交易金额',dataIndex: 'transAmt'},
		{header: '退款（托收）金额',dataIndex: 'backAmt',width:120},
		{header: '退款手续费',dataIndex: 'backFee'}
	]);
	
	var menuArray = new Array();
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 360,
		autoHeight: true,
		items: [{
			fieldLabel: '起始清算日期',
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			vtype: 'daterange',
			endDateField: 'endDate',
			width: 240
		},{
			fieldLabel: '截止清算日期',
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			vtype: 'daterange',
			startDateField: 'startDate',
			width: 240
		},{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户',
			id: 'mchntId',
			hiddenName: 'HmchntId',
			allowBlank: true,
			editable: true,
			width: 240
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 400,
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
				store.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	
	var queryMenu = {
			text: '查询',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	
	var report = {
			text: '生成报表',
			width: 160,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",grid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T80915Action.asp',
					timeout: 60000,
					params: {
						startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
						endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),						
						mchntId: queryForm.findById('mchntId').getValue(),
						txnId: '80915',
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
	}
	
	menuArray.add(queryMenu);
	menuArray.add('-');
	menuArray.add(report);
	
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户退货明细查询',
		iconCls: 'T80915',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		store: store,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: paramColModel,
		clicksToEdit: true,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载信息列表......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: store,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	store.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0, 
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			mchntId: queryForm.findById('mchntId').getValue()
		});
	});
	 
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
	
});