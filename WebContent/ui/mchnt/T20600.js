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
	
	// 所在地区数据集
	var cityStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	SelectOptionsDWR.getComboData('CITY_CODE',function(ret){
		cityStore.loadData(Ext.decode(ret));
	});
	
	
	// 企业资质等级
	var branchSvrLvlStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	SelectOptionsDWR.getComboData('BRANCH_SVR_LVL',function(ret){
		branchSvrLvlStore.loadData(Ext.decode(ret));
	});
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=recUpdTs'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtCd',mapping: 'mchtCd'},
			{name: 'branchCd',mapping: 'branchCd'},
			{name: 'branchNm',mapping: 'branchNm'},
			{name: 'branchArea',mapping: 'branchArea'},
			{name: 'branchSvrLvl',mapping: 'branchSvrLvl'},
			{name: 'branchContMan',mapping: 'branchContMan'},
			{name: 'branchTel',mapping: 'branchTel'},
			{name: 'branchFax',mapping: 'branchFax'},
			{name: 'branchNmEn',mapping: 'branchNmEn'},
			{name: 'branchAddr',mapping: 'branchAddr'},
			{name: 'custNager',mapping: 'custNager'},
			{name: 'custMobile',mapping: 'custMobile'},
			{name: 'custTel',mapping: 'custTel'},
			{name: 'oprNm',mapping: 'oprNm'},
			{name: 'signDate',mapping: 'signDate'},
			{name: 'branchSta',mapping: 'branchSta'}
		])
	});
	
	mchntStore.load({
		params:{start: 0}
	});
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>英文名：{branchNmEn}</p>',
			'<p>营业地址：{branchAddr}</p>',
			'<p>客户经理姓名：{custNager}</p>',
			'<p>客户经理手机：{custMobile}</p>',
			'<p>客户经理电话：{custTel}</p>',
			'<p>收银员数目：{oprNm}</p>'
		)
	});
	
	
	var oprColModel = new Ext.grid.ColumnModel([
	        mchntRowExpander,
    		{id: 'mchtCd',header: '商户编号',dataIndex: 'mchtCd',sortable: true,width: 100},
    		{header: '分店号',dataIndex: 'branchCd',width: 100},
    		{header: '中文名',dataIndex: 'branchNm',id:'branchNm'},
    		{header: '所在区域',dataIndex: 'branchArea'},
    		{header: '服务等级',dataIndex: 'branchSvrLvl',renderer: svrLvlType},
    		{header: '分店联系人',dataIndex: 'branchContMan',width: 100},
    		{header: '联系电话',dataIndex: 'branchTel'},
    		{header: '分店传真',dataIndex: 'branchFax'},
    		{header: '签约日期',dataIndex: 'signDate',width: 100,renderer: formatDt}
    	]);
	
		
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
	};
		
		
	
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	
	var menuArr = new Array();
	menuArr.push(queryCondition);  //[6]

	
	// 操作员信息列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '商户分店查询',
		iconCls: 'T206',
		region:'center',
		autoExpandColumn:'branchNm',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载操作员信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
	});
	
	oprGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			oprGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		}
	});
	
	
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
        	xtype: 'combo',
			fieldLabel: '商户编号*',
			labelStyle: 'padding-left: 5px',
			store: mchntNoStore,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			lazyRender: true,
			width: 130,
			blankText: '请选择商户编号',
			id: 'mchtCdId',
			hiddenName: 'mchtCd'
    	},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			id: 'branchCd',
			name: 'branchCd',
			vtype: 'alphanum',
			fieldLabel: '商店编号'
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			id: 'branchNm',
			name: 'branchNm',
			vtype: 'isOverMax',
			fieldLabel: '商店名'
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
			mchtCd: queryForm.findById('mchtCdId').getValue(),
			branchCd: queryForm.findById('branchCd').getValue(),
			branchNm: queryForm.findById('branchNm').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[oprGrid]
	});
});