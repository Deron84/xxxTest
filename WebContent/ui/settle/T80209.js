
function missionConsole(value){
	alert("该功能待确认接口。。。。");
}

Ext.onReady(function() {

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getSettleLog'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'BRH_ID',mapping: 'BRH_ID'},
			{name: 'INST_DATE',mapping: 'INST_DATE'},
			{name: 'START_DATE',mapping: 'START_DATE'},
			{name: 'END_DATE',mapping: 'END_DATE'},
			{name: 'SETTLE_AMT',mapping: 'SETTLE_AMT'},
			{name: 'TXN_AMT',mapping: 'TXN_AMT'},
			{name: 'SETTLE_FEE1',mapping: 'SETTLE_FEE1'},
			{name: 'MCHNT_COUNT',mapping: 'MCHNT_COUNT'},
			{name: 'SETTLE_AMT_DIS',mapping: 'SETTLE_AMT_DIS'},
			{name: 'TXN_AMT_DIS',mapping: 'TXN_AMT_DIS'},
			{name: 'SETTLE_FEE1_DIS',mapping: 'SETTLE_FEE1_DIS'},
			{name: 'MCHNT_COUNT_DIS',mapping: 'MCHNT_COUNT_DIS'},
			{name: 'SETTLE_AMT_TAL',mapping: 'SETTLE_AMT_TAL'},
			{name: 'TXN_AMT_TAL',mapping: 'TXN_AMT_TAL'},
			{name: 'SETTLE_FEE1_TAL',mapping: 'SETTLE_FEE1_TAL'},
			{name: 'MCHNT_COUNT_TAL',mapping: 'MCHNT_COUNT_TAL'},
			{name: 'SUCC_FILE',mapping: 'SUCC_FILE'},
			{name: 'FAIL_FILE',mapping: 'FAIL_FILE'},
			{name: 'STLM_FLAG',mapping: 'STLM_FLAG'},
			{name: 'OPR_ID1',mapping: 'OPR_ID1'},
			{name: 'REC_CRE_TIME',mapping: 'REC_CRE_TIME'},
			{name: 'STLM_FLAG2',mapping: 'STLM_FLAG2'},
			{name: 'OPR_ID2',mapping: 'OPR_ID2'},
			{name: 'REC_UPD_TIME',mapping: 'REC_UPD_TIME'},
			{name: 'STLM_DSP',mapping: 'STLM_DSP'}
		]),
		autoLoad: true
	});
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<font color="blue">清算金额(合计)：</font>{SETTLE_AMT_TAL}</br>',
			'<font color="blue">交易金额(合计)：</font>{TXN_AMT_TAL}</br>',
			'<font color="blue">手续费(合计)：</font>{SETTLE_FEE1_TAL}</br>',
			'<font color="blue">商户数量(合计)：</font>{MCHNT_COUNT_TAL}</br>',
			'<font color="blue">生成文件柜员：</font>{OPR_ID1}</br>',
			'<font color="blue">生成文件时间：</font>{REC_CRE_TIME}</br>',
			'<font color="blue">解析结果柜员：</font>{OPR_ID2}</br>',
			'<font color="blue">解析结果时间：</font>{REC_UPD_TIME}</br>',
			'<font color="blue">失败原因：</font>{STLM_DSP}</br>'
		)
	});
	
	var paramColModel = new Ext.grid.ColumnModel([
		rowExpander,
		{header: '机构编号',dataIndex: 'BRH_ID',sortable: true,width:60},
		{header: '代发日期',dataIndex: 'INST_DATE',width:70},
		{header: '清算起始日期',dataIndex: 'START_DATE',width:80},
		{header: '清算终止日期',dataIndex: 'END_DATE',width:80},
		{header: '清算金额(自动)',dataIndex: 'SETTLE_AMT',width:90},
		{header: '交易金额(自动)',dataIndex: 'TXN_AMT',width:90},
		{header: '手续费(自动)',dataIndex: 'SETTLE_FEE1',width:90},
		{header: '商户数量(自动)',dataIndex: 'MCHNT_COUNT',width:90},
		{header: '清算金额(手动)',dataIndex: 'SETTLE_AMT_DIS',width:90},
		{header: '交易金额(手动)',dataIndex: 'TXN_AMT_DIS',width:90},
		{header: '手续费(手动)',dataIndex: 'SETTLE_FEE1_DIS',width:90},
		{header: '商户数量(手动)',dataIndex: 'MCHNT_COUNT_DIS',width:90},
		{header: '生成文件状态',dataIndex: 'STLM_FLAG',width:80,renderer:function(v){
			if('1' == v){return '初始化';}
			else if('2' == v){return '<font color="#00FF00">生成成功</font>';}
			else if('3' == v){return '<font color="red">生成失败</font>';}
			else return v;
		}},
		{header: '解析结果状态',dataIndex: 'STLM_FLAG2',width:80,renderer:function(v){
			if('1' == v){return '初始化';}
			else if('2' == v){return '<font color="#00FF00">解析成功</font>';}
			else if('3' == v){return '<font color="red">解析失败</font>';}
			else return v;
		}}
	]);
	
	var grid = new Ext.grid.EditorGridPanel({
		id: 'SystemParameter',
		title: '商户代发记录查询',
		iconCls: 'T80209',
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
		plugins: rowExpander,
		tbar: [{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 55,
			handler:function() {
				store.load();
			}
		},'-','代发日期：',{
			xtype: 'datefield',
			id: 'date',
			name: 'date'
		},'-','代发机构：',{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			id: 'idbrhId',
			hiddenName: 'brhId',
			width: 160
		}],
		loadMask: {
			msg: '正在加载入账结果信息列表......'
		},
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
			date: typeof(Ext.getCmp('date').getValue()) == 'string' ? '' : Ext.getCmp('date').getValue().format('Ymd'),
			brhId: Ext.get('brhId').getValue()
		});
	})
	 
	
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
	Ext.getCmp('idbrhId').setValue(BRHID);
});