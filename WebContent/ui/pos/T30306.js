Ext.onReady(function() {
	// 数据集
	var rec;

	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'state',mapping: 'state'},
			{name: 'manufacturer',mapping: 'manufacturer'},
			{name: 'productCd',mapping: 'productCd'},
			{name: 'terminalType',mapping: 'terminalType'},
			{name: 'termType',mapping: 'termType'},
			{name: 'mchnNo',mapping: 'mchnNo'},
			{name: 'batchNo',mapping: 'batchNo'},
			{name: 'storOprId',mapping: 'storOprId'},
			{name: 'storDate',mapping: 'storDate'},
			{name: 'reciOprId',mapping: 'reciOprId'},
			{name: 'reciDate',mapping: 'reciDate'},
			{name: 'bankOprId',mapping: 'bankOprId'},
			{name: 'bankDate',mapping: 'bankDate'},
			{name: 'invalidOprId',mapping: 'invalidOprId'},
			{name: 'invalidDate',mapping: 'invalidDate'},
			{name: 'signOprId',mapping: 'signOprId'},
			{name: 'signDate',mapping: 'signDate'}
		])
	});
	termStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
		    state: 3
		});
	});
	termStore.load();
	
	var termRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>入库操作员：{storOprId}</p>',
			'<p>入库时间：{storDate}</p>',
			'<p>下发操作员：{reciOprId}</p>',
			'<p>下发时间：{reciDate}</p>',
			'<p>回退操作员：{bankOprId}</p>',
			'<p>回退时间：{bankDate}</p>',
			'<p>作废操作员：{invalidOprId}</p>',
			'<p>作废时间：{invalidDate}</p>',
			'<p>签收操作员：{signOprId}</p>',
			'<p>签收时间：{signDate}</p>'
		)
	});
	
	var termColModel = new Ext.grid.ColumnModel([
	    termRowExpander,
	    {id: 'termId',header: '终端库存编号',dataIndex: 'termId',width: 100},
	    {header: '终端状态',dataIndex: 'state',renderer: translateState},
	    {header: '终端厂商',dataIndex: 'manufacturer',width: 150},
	    {header: '终端序列号',dataIndex: 'productCd'},
	    {header: '终端型号',dataIndex: 'terminalType'},
	    {header: '终端类型',dataIndex: 'termType',renderer: termType,width: 150},
	    {header: '所属机构号',dataIndex: 'mchnNo',width: 150},
	    {header: '批次号',dataIndex: 'batchNo',width: 150}
	]);
	

	var acceptMenu = {
		text: '签收',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			var selectedRecord = grid.getSelectionModel().getSelected();
			modifyForm.getForm().loadRecord(selectedRecord);
			modifyWin.show();
		}
	};

	
	
	
	var menuArr = new Array();
	
	menuArr.push(acceptMenu);	//[0]

	var modifyForm = new Ext.FormPanel({
		frame: true,
		width: 450,
		height: 180,
		waitMsgTarget: true,
		labelWidth: 80,
		defaults: {
			bodyStyle: 'padding-left: 50px'
		},
		items: [{					
			columnWidth: .4,
			layout: 'form',
			items:[{
				id: 'termId',
				name: 'termId',
				xtype: 'textfield',
				width: 100,
				fieldLabel: '终端库存编号',
				readOnly: true,
				maxLength: 32
			}]
		},{					
			columnWidth: .4,
			layout: 'form',
			items:[{
				id: 'batchNo',
				name: 'batchNo',
				xtype: 'textfield',
				width: 100,
				fieldLabel: '批次号',
				readOnly: true,
				maxLength: 32
			}]
		},{
			columnWidth: .4,
			layout: 'form',
			items: [{
				xtype: 'combo',
				fieldLabel: '处理方式*',
				id: 'action',
				name: 'action',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','单笔处理'],['1','批次处理']]
				})
			}]
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '签收',
			iconCls: 'accept',
			handler: function() {
				if(modifyForm.getForm().isValid()) {
					modifyForm.getForm().submit({
						url: 'T30306Action.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							modifyForm.getForm().reset();
							grid.getStore().reload();
							showSuccessMsg(action.result.msg,modifyWin);
							modifyWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,modifyWin);
						},
						params: {
							txnId: '30306',
							subTxnId: '01',
							method: Ext.getCmp('action').getValue()
						}
					});
				}
				modifyWin.hide();
				
			}
		},{
			text: '关闭',
			handler: function() {
				modifyWin.hide();
				grid.getStore().reload();
			}
		}]
	});
	var modifyWin = new Ext.Window({
		title: '终端信息',
		layout: 'fit',
		width: 400,
		closeAction: 'hide',
		resizable: false,
		closable: true,
		modal: true,
		autoHeight: true,
		items: [modifyForm]
	});
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '终端签收',
		iconCls: 'pos',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		plugins: [termRowExpander],
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
	
	//每次在列表信息加载前都将按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[0].disable();
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('state') == '3') {
				grid.getTopToolbar().items.items[0].enable();
			} else {
				grid.getTopToolbar().items.items[0].disable();
			}
	}
	});
})