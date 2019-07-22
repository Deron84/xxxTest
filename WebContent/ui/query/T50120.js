Ext.onReady(function() {
	var _brhId;
	var	_date;
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});

	//上级营业网点编号
	SelectOptionsDWR.getComboData('BRH_ID_PARAM',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	// 联机交易数据集
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getStdCountInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'addr',mapping: 'addr'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'transCnt',mapping: 'transCnt'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'amtSettlmt',mapping: 'amtSettlmt'},
			{name: 'panNums',mapping: 'panNums'},
			{name: 'reachsdt',mapping: 'reachsdt'},
			{name: 'startDate',mapping: 'startDate'},
			{name: 'endDate',mapping: 'endDate'}
		])
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([
		{header: '商户名称',dataIndex: 'mchtNm',width: 200,align:'center'},
		{header: '商户号',dataIndex: 'cardAccpId',width: 150,align:'center'},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 100,align:'center'},
		{header: '交易总笔数',dataIndex: 'transCnt',width: 90,align:'center'},
		{header: '交易总金额(元)',dataIndex: 'amtTrans',width: 120,align:'center'},
		{header: '原交易总金额(元)',dataIndex: 'amtSettlmt',width: 120,align:'center'},
		{header: '银行卡总个数',dataIndex: 'panNums',width: 100,align:'center'},
		{header: '是否达标',dataIndex: 'reachsdt',width: 80,renderer: respStdCode,align:'center'},
		{header: '起始日期',dataIndex: 'startDate',width: 80,align:'center'},
		{header: '终止日期',dataIndex: 'endDate',width: 80,align:'center'},
		{header: '商户地址',dataIndex: 'addr',width: 250,align:'center'},
		{header: '归属机构',dataIndex: 'brhName',width: 250,align:'center'}
	]);

	// 应答码
	function respStdCode(val) {
		if(val == '达标')
			return "<font color='green'>" + val + "</font>";
		else
			return "<font color='red'>" + val + "</font>";
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
	
	var report = {
		text: '生成报表',
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
				url: 'T50120Action_download2.asp',
				params: {
					brhId: _brhId,
					date: _date,
					txnId: '50120',
					subTxnId: '01'
				},
				success: function(rsp,opt){
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
													rspObj.msg+'&key=exl50120exl';
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				},
				failure: function(rsp,opt){
					hideMask();
				}
			});
//			Ext.Ajax.requestNeedAuthorise({
//				url: 'T50120Action_download.asp',
//				params: {
//					brhId: queryForm.findById('brhId').getValue(),
//					date: queryForm.findById('date').getValue().dateFormat('Ym'),
//					txnId: '50120',
//					subTxnId: '01'
//				},
//				success: function(rsp,opt) {
//					hideMask();
//					var rspObj = Ext.decode(rsp.responseText);
//					if(rspObj.success) {
//						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
//						rspObj.msg+'&key=exl50120exl';
//					} else {
//						showErrorMsg(rspObj.msg,grid);
//					}
//				},
//				failure: function(){
//					hideMask();
//				}
//			});
		}
	};
	
	menuArr.push(queryConditionMebu);  //[0]
	menuArr.push('-');
	menuArr.push(report);
	// 达标统计
	var grid = new Ext.grid.GridPanel({
		title: '商户达标信息查询',
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
			msg: '正在加载商户达标信息查询结果......'
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
	
	var brhCombo = new Ext.form.ComboBox({
		store: brhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择机构',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		blankText: '请选择一个机构',
		fieldLabel: '机构信息*',
		width: 320,
		id: 'brhId',
		name: 'brhId',
		allowBlank: false,
		hiddenName: 'brhIdEdit'
	});
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
		autoHeight: true,
		items: [brhCombo,{
			xtype: 'datefield',
			width : 290,
			plugins: 'monthPickerPlugin', 
			format:'Ym', 
			id: 'date',
			name: 'date',
			fieldLabel: '统计月份*',
			emptyText : '统计当前月份到下一月份的交易', 
			allowBlank: false,
			editable: false 
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
					_brhId =queryForm.findById('brhId').getValue();
					_date = queryForm.findById('date').getValue().dateFormat('Ym');
					txnStore.load();
					queryWin.hide();
				    grid.getTopToolbar().items.items[2].enable();
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
			start: 0,
			brhId: _brhId,
			date: _date
		});
	});
    grid.getTopToolbar().items.items[2].disable();
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});