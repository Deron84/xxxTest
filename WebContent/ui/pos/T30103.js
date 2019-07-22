Ext.onReady(function() {
	
	// 数据集
	var termTypeMap = new DataMap('TERM_TYPE');

	// 列表当前选择记录
	var rec;
	
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termInfoAll'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'termSta',mapping: 'termSta'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'termIdId',mapping: 'termIdId'},
			{name: 'termFactory',mapping: 'termFactory'},
			{name: 'termMachTp',mapping: 'termMachTp'},
			{name: 'termVer',mapping: 'termVer'},
			{name: 'termTp',mapping: 'termTp'},
			{name: 'termBranch',mapping: 'termBranch'},
			{name: 'termIns',mapping: 'termIns'},
            {name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	termStore.on('beforeload',function() 
		{Ext.apply(this.baseParams, {
		    start: 0,
		    termSta: '4'
		});
	});
	termStore.load();
	
	
	var termColModel = new Ext.grid.ColumnModel([
		{id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
		{header: '商户号',dataIndex: 'mchntNo',width: 150,renderer:function(val){return getRemoteTrans(val, "mchntName")}},
		{header: '终端状态',dataIndex: 'termSta',width: 150,renderer: termSta},
		{header: '终端所属机构',dataIndex: 'termBranch'},
		{header: '终端库存编号',dataIndex: 'termIdId'},
		{header: '终端厂商',dataIndex: 'termFactory',width: 150},
		{header: '终端型号',dataIndex: 'termMachTp',width: 150}
	]);
	
	var stopMenu = {
		text: '恢复',
		width: 85,
		iconCls: 'recover',
		disabled: true,
		handler:function() {
			showConfirm('确定恢复吗？',grid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交信息，请稍后......');
					rec = grid.getSelectionModel().getSelected();
                    if(rec == null)
		            {
		                showAlertMsg("没有选择记录",grid);
		                return;
		            } 
					Ext.Ajax.request({
						url: 'T30103Action.asp',
						params: {
							termId: rec.get('termId'),
                            recCrtTs: rec.get('recCrtTs'),
							txnId: '30103',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,grid);
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
							// 重新加载终端信息
							grid.getStore().reload();
						}
					});
					hideProcessMsg();
                    grid.getTopToolbar().items.items[0].disable();
				}
			});
		}
	}
	
	
	
	var menuArr = new Array();
	
	menuArr.push(stopMenu);		//[0]
	
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '终端信息恢复',
		iconCls: 'T30103',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});

	grid.getTopToolbar().items.items[0].disable();
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('termSta') == '4' ) {
				grid.getTopToolbar().items.items[0].enable();
			} else {
				grid.getTopToolbar().items.items[0].disable();
			}
		}
	});
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});

})