Ext.onReady(function() {
	
	//查询商户编号
	var mchntNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntNoStore.loadData(Ext.decode(ret));
	});
	
	//交易类型
	var txnNumStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('TXN_NUM',function(ret){
		txnNumStore.loadData(Ext.decode(ret));
	});
	//卡类型
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CARD_TYPE',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});
	//交易渠道
	var channelStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CHANNEL',function(ret){
		channelStore.loadData(Ext.decode(ret));
	});
	
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntInfoTrue'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchntId'
		},[
			{name: 'mchntId',mapping: 'mchntId'},
			{name: 'mchntCnName',mapping: 'mchntCnName'},
			{name: 'mchntEnName',mapping: 'mchntEnName'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'certificate',mapping: 'certificate'},
			{name: 'addr',mapping: 'addr'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'email',mapping: 'email'},
			{name: 'legalName',mapping: 'legalName'},
			{name: 'contactName',mapping: 'contactName'},
			{name: 'contactTel',mapping: 'contactTel'},
			{name: 'registerDate',mapping: 'registerDate'},
			{name: 'mchntSt',mapping: 'mchntSt'}
		])
	});
	
	mchntStore.load({
		params: {
			start: 0
		}
	});
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>商户地址：{addr}</p>',
			'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{email}</p>',
			'<p>法人代表名称：{legalName}</p>',
			'<p>联系人姓名：{contactName}</p>',
			'<p>联系人电话：{contactTel}</p>'
		)
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchntId',header: '商户编号',dataIndex: 'mchntId',sortable: true,width: 100},
		{header: '商户名称',dataIndex: 'mchntCnName',width: 200},
		{header: '商户英文名称',dataIndex: 'mchntEnName',width: 200},
		{header: '商户MCC',dataIndex: 'mcc'},
		{header: '营业执照编号',dataIndex: 'certificate',width: 200},
		{header: '注册日期',dataIndex: 'registerDate',width: 100,renderer: formatDt},
		{header: '商户状态',dataIndex: 'mchntSt',renderer: mchntSt}
	]);
	
	// 商户状态转译
	function mchntSt(val) {
		if(val == '0') {
			return '<font color="green">正常</font>';
		} else if(val == '1') {
			return '<font color="gray">添加待审核</font>';
		} else if(val == '2') {
			return '<font color="gray">添加审核退回</font>';
		} else if(val == '3') {
			return '<font color="gray">修改待审核</font>';
		} else if(val == '4') {
			return '<font color="gray">修改审核退回</font>';
		} else if(val == '5') {
			return '<font color="gray">停用待审核</font>';
		} else if(val == '6') {
			return '<font color="red">停用</font>';
		} else if(val == '7') {
			return '<font color="gray">恢复待审核</font>';
		}
		return val;
	}
	
	// 菜单集合
	var menuArr = new Array();
	
	var detailMenu = {
		text: '商户限额详情',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			window.open(Ext.contextPath + '/MchntDetailAction.asp?mchntId=' + rec.get('mchntId'), 'newwindow', 'height=600,width=780,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no');
		}
	}
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	var addMenu = {
		text: '商户限额新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			oprWin.show();
			oprWin.center();
		}
	};
	
	
	menuArr.push(detailMenu);  //[0]
	menuArr.push('-');  //[1]
	menuArr.push(addMenu);  //[2]
	menuArr.push('-');  //[3]
	menuArr.push(queryCondition);  //[4]
	
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户信息',
		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].disable();
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			rec = mchntGrid.getSelectionModel().getSelected();
			mchntGrid.getTopToolbar().items.items[0].enable();
			mchntGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	// 可选机构下拉列表
	var mchntNoCombo = new Ext.form.ComboBox({
		store: mchntNoStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择商户号',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个商户号',
		fieldLabel: '商户号*',
		hiddenName: 'mchntNoId'
	});
	
	// 可选机构下拉列表
	var txnNumCombo = new Ext.form.ComboBox({
		store: txnNumStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择交易类型',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个交易类型',
		fieldLabel: '交易类型*',
		hiddenName: 'txnNumId'
	});
	
	// 可选卡类型下拉列表
	var cardTypeCombo = new Ext.form.ComboBox({
		store: cardTypeStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择卡类型',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个卡类型',
		fieldLabel: '卡类型*',
		hiddenName: 'cardTypeId'
	});
	
	// 交易渠道下拉列表
	var channelCombo = new Ext.form.ComboBox({
		store: channelStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择交易渠道',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个交易渠道',
		fieldLabel: '交易渠道*',
		hiddenName: 'channelId'
	});
	
	
	
	/******************************限额信息添加******************************/
	// 限额信息添加
	var oprInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 500,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [mchntNoCombo,
		txnNumCombo,
		cardTypeCombo,
		channelCombo,
		{
			fieldLabel: '单日累计交易笔数*',
			allowBlank: false,
			blankText: '单日累计交易笔数不能为空',
			emptyText: '请输入单日累计交易笔数',
			maxLength: 10,
			vtype: 'isOverMax',
			id: 'DayNum',
			name: 'DayNum',
			vtype: 'isOverMax',
			width: 150
		},
		{
			fieldLabel: '单日累计交易金额*',
			allowBlank: false,
			blankText: '单日累计交易金额不能为空',
			emptyText: '请输入单日累计交易金额',
			maxLength: 40,
			vtype: 'isOverMax',
			id: 'DayAmt',
			name: 'DayAmt',
			vtype: 'isOverMax',
			width: 150
		},
		{
			fieldLabel: '单月累计交易笔数*',
			allowBlank: false,
			blankText: '单月累计交易笔数不能为空',
			emptyText: '请输入单月累计交易笔数',
			maxLength: 40,
			vtype: 'isOverMax',
			id: 'DaySingle',
			name: 'DaySingle',
			vtype: 'isOverMax',
			width: 150
		},
		{
			fieldLabel: '单月累计交易金额*',
			allowBlank: false,
			blankText: '单月累计交易金额不能为空',
			emptyText: '请输入单月累计交易金额',
			maxLength: 40,
			vtype: 'isOverMax',
			id: 'monNum',
			name: 'monNum',
			vtype: 'isOverMax',
			width: 150
		},
		{
			fieldLabel: '单月累计交易金额*',
			allowBlank: false,
			blankText: '单月累计交易金额不能为空',
			emptyText: '请输入单月累计交易金额',
			maxLength: 40,
			vtype: 'isOverMax',
			id: 'monAtm',
			name: 'monAtm',
			vtype: 'isOverMax',
			width: 150
		}]
	});
	
	// 操作员添加窗口
	var oprWin = new Ext.Window({
		title: '商户限额添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 500,
		autoHeight: true,
		layout: 'fit',
		items: [oprInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(oprInfoForm.getForm().isValid()) {
					oprInfoForm.getForm().submit({
						url: 'T10401Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,oprInfoForm);
							//重置表单
							oprInfoForm.getForm().reset();
							//重新加载列表
							oprGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,oprInfoForm);
						},
						params: {
							txnId: '10401',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				oprInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				oprWin.hide(mchntGrid);
			}
		}]
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'mchntId',
			name: 'mchntId',
			vtype: 'alphanum',
			fieldLabel: '商户编号'
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
				mchntStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntId: queryForm.findById('mchntId').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd')
		});
	});
});