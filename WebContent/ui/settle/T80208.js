
function missionConsole(value){
	alert("该功能待确认接口。。。。");
}

Ext.onReady(function() {

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getMchntSettleInfoNew'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'BRH_CODE',mapping: 'BRH_CODE'},
			{name: 'MCHT_NO',mapping: 'MCHT_NO'},
			{name: 'MCHT_NM',mapping: 'MCHT_NM'},
			{name: 'ACCT_TYPE',mapping: 'ACCT_TYPE'},
			{name: 'MCHT_ACCT',mapping: 'MCHT_ACCT'},
//			{name: 'ACCT_NM',mapping: 'ACCT_NM'},
			{name: 'DATE_SETTLMT',mapping: 'DATE_SETTLMT'},
			{name: 'SETTLE_AMT',mapping: 'SETTLE_AMT'},
			{name: 'TXN_AMT',mapping: 'TXN_AMT'},
			{name: 'SETTLE_FEE1',mapping: 'SETTLE_FEE1'},
			{name: 'TXN_NUM',mapping: 'TXN_NUM'},
			{name: 'SETTLE_FLAG',mapping: 'SETTLE_FLAG'},
			{name: 'FAIL_RESN',mapping: 'FAIL_RESN'},
			{name: 'FLAG',mapping: 'FLAG'}
		])
	});
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<font color="blue">账户类型：</font>{ACCT_TYPE}</br>',
			'<font color="blue">账户户名：</font>{ACCT_NM}</br>',
			'<font color="blue">账户账号：</font>{MCHT_ACCT}</br>',
			'<font color="blue">失败原因：</font>{FAIL_RESN}</br>'
		)
	});
	
	function oprate(val,metadata,record,rowIndex){
		if(val == "2"){
                var value = record.get('MCHT_NO');
				return "<button class=btn_2k3 id="+value+" onclick='javascript:missionConsole(this.id)'>生成凭证</button>";
			}
	}
	
	var paramColModel = new Ext.grid.ColumnModel([
		rowExpander,
		{header: '机构编号',dataIndex: 'BRH_CODE',sortable: true,width:60},
		{header: '商户编号',dataIndex: 'MCHT_NO',width:110},
		{header: '商户名称',dataIndex: 'MCHT_NM',width:140,id:'MCHT_NM'},
		{header: '清算日期',dataIndex: 'DATE_SETTLMT',width:70},
		{header: '清算金额',dataIndex: 'SETTLE_AMT',width:70},
		{header: '交易金额',dataIndex: 'TXN_AMT',width:70},
		{header: '手续费(元)',dataIndex: 'SETTLE_FEE1',width:70},
		{header: '交易笔数',dataIndex: 'TXN_NUM',width:60},
		{header: '清算状态',dataIndex: 'SETTLE_FLAG',width:120,renderer:function(v){
			if('0' == v){return '暂未代发';}
			else if('A' == v){return '待生成文件';}
			else if('B' == v){return '已生成文件';}
			else if('C' == v){return '手动代发商户';}
			else if('1' == v){return "<font color='#00FF00'>入账成功</font>";}
			else if('2' == v){return "<font color='red'>入账失败</font>";}
			else if('3' == v){return "<font color='red'>入多笔账</font>";}
		}}
	]);
	
	var grid = new Ext.grid.EditorGridPanel({
		id: 'SystemParameter',
		title: '商户入账结果查询',
		iconCls: 'T80208',
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
		autoExpandColumn: 'MCHT_NM',
		tbar: [{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 55,
			handler:function() {
				if(Ext.getCmp('date').getValue() == ''){
					showMsg('请选择清算日期', grid)
					return;
				}
				store.load();
			}
		},'-','代发日期：',{
			xtype: 'datefield',
			id: 'date',
			name: 'date'
		},'-','机构：',{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			id: 'idbrhId',
			hiddenName: 'brhId',
			width: 120
		},'-','商户：',{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			hiddenName: 'mchntId',
			width: 260,
			listWidth: 320
		},'-','账号：',{
			xtype: 'textfield',
			name: 'mchntAcct',
			id: 'mchntAcct'
		}
//		,'-','状态：',{
//			xtype: 'basecomboselect',
//			baseParams: 'SETTLE_FLAG',
//			hiddenName: 'settleFlag',
//			width: 100
//		}
		],
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
			brhId: Ext.get('brhId').getValue(),
			mchntId: Ext.get('mchntId').getValue(),
			mchntAcct: Ext.getCmp('mchntAcct').getValue()
//			,
//			settleFlag: Ext.get('settleFlag').getValue()
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
	Ext.getCmp('date').setValue(new Date());
	Ext.getCmp('idbrhId').setValue(BRHID);
});