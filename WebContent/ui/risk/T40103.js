Ext.onReady(function() {
	
	// 风险交易数据集
	var riskTxnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskModelInfoUpdLog'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'saModelKind',mapping: 'saModelKind'},
			{name:'saBranchCode',mapping:'saBranchCode'},
			{name: 'saFieldName',mapping: 'saFieldName'},
			{name: 'saFieldValueBF',mapping: 'saFieldValueBF'},
			{name: 'saFieldValue',mapping: 'saFieldValue'},
			{name: 'modiZoneNo',mapping: 'modiZoneNo'},
			{name: 'modiTime',mapping: 'modiTime'}
		]),
	autoLoad: true
	}); 
	
	var riskColModel = new Ext.grid.ColumnModel([
		{header: '模型',dataIndex: 'saModelKind',width: 320,renderer:saModelKind,id:'saModelKind'},
		{header: '机构编号',dataIndex: 'saBranchCode',width: 100},
		{header: '修改属性',dataIndex: 'saFieldName',width: 140,renderer:saFieldName},
		{header: '原值',dataIndex: 'saFieldValueBF',width:100},
		{header: '修改值',dataIndex: 'saFieldValue',width: 100},
		{header: '修改人',dataIndex: 'modiZoneNo',width: 100},
		{header: '修改时间',dataIndex: 'modiTime',width: 130,renderer: formatTs}
	]);
	
	var _fName;
	// 转译修改属性
	function saFieldName(val,metadata,record,rowIndex) {
		_fName = val;
		var model = record.get('saModelKind')
		if(val == 'saBeUse') {
			return '使用标识';
		} else if(val == 'SaLimitAmountSingle') {
			return '受控金额-单笔（元）';
		} else if(val == 'SaLimitAmountTotle') {
			return '受控金额-总计(元)';
		} else if(val == 'saLimitNum') {
			return '受控交易笔数';
		} else if(val == 'saAction') {
			return '受控动作';
		} else {
			return '未知';
		}
	}
	
	// 转译启用标识
	function saBeUse(val) {
		if(val == '1') {
			return '<font color="green">启用</font>';
		} else {
			return '<font color="red">未启用</font>';
		}
	}
	
	// 转译受控动作
	function saAction(val) {
		if(val == '0') {
			return '<font color="green">正常</font>';
		} else if(val == '1') {
			return '<font color="gray">托收</font>';
		} else if(val == '2') {
			return '<font color="red">拒绝</font>';
		} else {
			return '未知的受控动作';
		}
	}
	// 转译风险模型
	function saModelKind(val) {
		if(val == 'C1') {
			return 'C1-疑似套现按月监控';
		} else if(val == 'C2') {
			return 'C2-疑似套现按日监控';
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
		}
	};
	
	var detail = {
		text: '模型说明',
		width: 85,
		iconCls: 'detail',
		handler: function(){
			Ext.MessageBox.show({
				msg: 
//					 '<font color=green>参加风控商户</font>：商户类型MCC为《银联卡特约商户类别码使用细则》规定的。<br>' +
//					 '&nbsp;&nbsp;<font color=grey>a</font>：全部批发商户MCC。<br>' +
//					 '&nbsp;&nbsp;<font color=grey>b</font>：零售商户MCC中教育、卫生、福利及其他政府服务MCC。<br>'+
//					 '&nbsp;&nbsp;<font color=grey>c</font>：零售商户MCC中抵扣率（房地产、加油）。<br><br>' +
					 '<font color=red>C1-疑似套现按月监测触发规则</font><br>' +
					 '<font color=green>1</font>：商户信用卡当月交易总额超过<font color=blue>受控金额-总计</font>或当月交易总笔数超过<font color=blue>受控交易笔数</font>。<br>' +
					 '<font color=green>2</font>：商户信用卡交易单笔交易金额超过<font color=blue>受控金额-单笔</font>。<br>' +
					 '<font color=red>C2-疑似套现按日监测触发规则</font><br>' +
					 '<font color=green>1</font>：商户信用卡当日交易总笔数超过<font color=blue>受控交易笔数</font>。<br>' +
					 '<font color=green>2</font>：商户信用卡交易单笔整数交易金额超过<font color=blue>受控金额-单笔</font>。<br>',
				buttons: Ext.MessageBox.OK,
				modal: true,
				width: 480
			});
		}
	};
	
	menuArr.push(queryConditionMebu);  //[0]
	menuArr.push('-'); 
	menuArr.push(detail);
	
	// 风险交易监控
	var grid = new Ext.grid.GridPanel({
		title: '风险参数变更记录',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'saModelKind',
		store: riskTxnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载监控模型修改记录列表......'
		},
		tbar: 	menuArr,
		bbar: new Ext.PagingToolbar({
			store: riskTxnStore,
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
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 280,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'saModelKind',
			name: 'saModelKind',
			fieldLabel: '风控模型编号',
			anchor: '90%'
		},{
			xtype: 'textfield',
			id: 'srBrhNo',
			name: 'srBrhNo',
			vtype: 'alphanum',
			fieldLabel: '分行号',
			anchor: '90%'
		},{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '修改开始日期',
			anchor: '90%'
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '修改结束日期',
			anchor: '90%'
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 300,
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
				riskTxnStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	riskTxnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			srBrhNo: queryForm.findById('srBrhNo').getValue(),
			saModelKind: queryForm.findById('saModelKind').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});