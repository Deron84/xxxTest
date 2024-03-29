Ext.onReady(function() {
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termRefuseInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'termId',mapping: 'termId'},
			{name: 'mchtcd',mapping: 'mcht_cd'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'refuseType',mapping: 'refuseType'},
			{name: 'refuseInfo',mapping: 'refuseInfo'}
		])
	});
	
	reasonStore.load({
		params: {
			start: 0
		}
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '拒绝时间',dataIndex: 'txnTime',sortable: true,renderer: formatTs,width: 200},
		{header: '终端号',dataIndex: 'termId'},
		{header: '商户名',dataIndex: 'mchtcd'},
		{header: '归属机构',dataIndex: 'brhId',width: 100},
		{header: '交易操作员',dataIndex: 'oprId',width: 180},
		{header: '交易类型',dataIndex: 'refuseType',width: 150},
		{header: '拒绝原因',dataIndex: 'refuseInfo',width: 200,id:'refuseInfo'}
	]);
	
	var grid = new Ext.grid.GridPanel({
		title: '终端退回/拒绝原因查询',
		region: 'center',
		iconCls: 'T30105',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: reasonStore,
		autoExpandColumn: 'refuseInfo',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		loadMask: {
			msg: '正在加载终端拒绝信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: reasonStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});