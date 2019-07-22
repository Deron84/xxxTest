Ext.onReady(function() {
	// 数据集
	var rec;
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_ID',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfo'
		}),
		params:{start: 0,state: 2},
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
		    state: 2
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
		text: '详细信息',
		width: 85,
		iconCls: 'edit',
		handler:function() {
			var selectedRecord = grid.getSelectionModel().getSelected();
			modifyForm.getForm().loadRecord(selectedRecord);
			modifyWin.show();
		}
	};
	
	
	var menuArr = new Array();
	
	menuArr.push(acceptMenu);	//[0]
	
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '终端下发审核',
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
	var modifyForm = new Ext.FormPanel({
		frame: true,
		width: 450,
		height: 180,
		//layout: 'column',
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
		},{
			columnWidth: .4,
			layout: 'form',
			items: [{
				xtype: 'combo',
				store: brhStore,
				id: 'mchnNo',
				hiddenname: 'mechNo',
				name: 'mechNo',
				emptyText: '请选择机构',
				fieldLabel: '下发机构*',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				blankText: '请选择一个机构'
			}]
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '通过',
			iconCls: 'accept',
			handler: function() {
				if(modifyForm.getForm().isValid()) {
					var param = '0'+Ext.getCmp('action').getValue();
					modifyForm.getForm().submit({
						url: 'T30303Action.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							modifyForm.getForm().reset();
							showSuccessMsg(action.result.msg,modifyWin);
							modifyWin.hide();
                            grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,modifyWin);
                            grid.getStore().reload();
						},
						params: {
							txnId: '30303',
							subTxnId: '01',
							method: param,
							mechNo: Ext.getCmp('mchnNo').getValue()
						}
					});
				}
				modifyWin.hide();
			}
		},{
			text: '拒绝',
			iconCls: 'refuse',
			handler: function() {
				if(modifyForm.getForm().isValid()) {
					var param = '1'+Ext.getCmp('action').getValue();
					modifyForm.getForm().submit({
						url: 'T30303Action.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							modifyForm.getForm().reset();
							showSuccessMsg(action.result.msg,modifyWin);
							modifyWin.hide();
                            grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,modifyWin);
                            grid.getStore().reload();
						},
						params: {
							txnId: '30303',
							subTxnId: '02',
							method: param,
							mechNo: Ext.getCmp('mchnNo').getValue()
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

	grid.getTopToolbar().items.items[0].disable();
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据所选择的终端状态判断哪个按钮可用
			rec = grid.getSelectionModel().getSelected();
			// 当终端状态为添加待审核和修改待审核的时候，拒绝按钮可用
			if(rec.get('state') == '2') {
				grid.getTopToolbar().items.items[0].enable();
			} else {
				grid.getTopToolbar().items.items[0].disable();
			}
		}
	});
})