Ext.onReady(function() {
	// 当前选择记录
	var record;
	var dealStore=[['1','手工清算已办结'],
	           	['2','正常清算已办结'],
	           	['3','暂不清算等待处理'],
	           	['4','押款已办结']];
	// 移机监测信息
	var riskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=shiftTermInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
            {name: 'settleDate',mapping: 'settleDate'},
            {name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'lastAmt',mapping: 'lastAmt'},
			{name: 'infileAmt',mapping: 'infileAmt'},
			{name: 'returnAmt',mapping: 'returnAmt'},
			{name: 'settleAmt',mapping: 'settleAmt'},
			{name: 'loamAmt',mapping: 'loamAmt'},	
			{name: 'FLAG',mapping: 'FLAG'},
			{name: 'DESR',mapping: 'DESR'},
			{name: 'moveTp',mapping: 'moveTp'}
		]),
		autoLoad: true
	}); 
	
	
	var riskColModel = new Ext.grid.ColumnModel([
        {header: '监测日期',dataIndex: 'settleDate'},
        {header: '商户号',dataIndex: 'mchtNo',id:'mchtNo',width: 120},
	    {header: '商户名称',dataIndex: 'mchtNm',width: 400},
	    {header: '上期押款金额(元)',dataIndex: 'lastAmt',width: 130},
	    {header: '本日清算金额(元)',dataIndex: 'infileAmt',width: 130},
	    {header: '扣除金额(元)',dataIndex: 'returnAmt',width:110},
	    {header: '清算金额(元)',dataIndex: 'settleAmt',width: 110},
	    {header: '商户所欠金额(元)',dataIndex: 'loamAmt',width: 120},
	    {header: '状态',dataIndex: 'FLAG',id:'FLAG',renderer:controlType},
	    {header: '是否移机',dataIndex: 'moveTp',id:'moveTp',renderer:moveTp}
//	    {header: '白名单商户',dataIndex: 'DESR',renderer:mchtType}
	    
	]);
	
	function controlType(val){
		if(val=='7'){
			return "手工清算已办结";
		}
		if(val=='5'){
			return "押款已办结";
		}
		if(val=='4'){
			return "正常清算已办结";
		}
		if(val=='2'){
			return "触发风控";
		}
	}
	
	function moveTp(val){
		if(val=='0'){
			return "否";
		}
		if(val>0){
			return "是";
		}
	}

	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 500,
		autoHeight : true,
		labelWidth : 80,
		items : [{
			xtype : 'dynamicCombo',
			fieldLabel : '商户编号',
			methodName : 'getMchntIdInBase',
			hiddenName : 'mchtCd',
			editable : true,
			width : 380
		}]
	});
	var queryWin = new Ext.Window( {
		title : '查询条件',
		layout : 'fit',
		width : 500,
		autoHeight : true,
		items : [ queryForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		closable : true,
		animateTarget : 'query',
		tools : [{
			id : 'minimize',
			handler : function(event, toolEl, panel, tc) {
			panel.tools.maximize.show();
			toolEl.hide();
			queryWin.collapse();
			queryWin.getEl().pause(1);
			queryWin.setPosition(10, Ext.getBody()
						.getViewSize().height - 30);
			},
			qtip : '最小化',
			hidden : false
		}, {
			id : 'maximize',
			handler : function(event, toolEl, panel, tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryWin.expand();
				queryWin.center();
			},
			qtip : '恢复',
			hidden : true
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {
				riskStore.load();
				queryForm.getForm().reset();
				queryWin.hide();
			}
		}, {
			text : '清除查询条件',
			handler : function() {
				queryForm.getForm().reset();
			}
		} ]
	});
	
	var queryCondition = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};
	
	var menuArr = new Array();
	
	menuArr.push(queryCondition);  
	
	
	// 移机监测信息
	var grid = new Ext.grid.GridPanel( {
		title : '风险控制详细查询',
		region : 'center',
		iconCls : 'risk',
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		store : riskStore,
		sm : new Ext.grid.RowSelectionModel( {
			singleSelect : true
		}),
		cm : riskColModel,
		clicksToEdit : true,
		forceValidation : true,
		tbar : menuArr,
		loadMask : {
			msg : '正在加载风险控制处理信息列表......'
		},
		bbar : new Ext.PagingToolbar( {
			store : riskStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});
	
	grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});
	
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	riskStore.on('beforeload', function() {
		Ext.apply(this.baseParams,{
			start : 0,
			mchtCd : queryForm.getForm().findField('mchtCd').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})