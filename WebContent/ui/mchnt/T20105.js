Ext.onReady(function() {
	var _mchntId;
	var _acmchntId;
	var _mchtStatus;
	var _mchtGrp;
	var _startDate;
	var _endDate;
	var _brhId;
	var _MchtFlag1;
	var _connType;
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntBaseInfoQueryTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name : 'mappingMchntcdOne',mapping : 'mappingMchntcdOne'}, 
			{name : 'mappingMchntcdTwo',mapping : 'mappingMchntcdTwo'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'addr',mapping: 'addr'},
			{name: 'commEmail',mapping: 'commEmail'},
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'termCount',mapping: 'termCount'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'updTs',mapping: 'updTs'}
		])
	});
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>商户英文名称：{engName}</p>',
			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			'<p>商户地址：{addr}</p>',
			//'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{commEmail}</p>',
			//'<p>法人代表名称：{manager}</p>',
			'<p>联系人姓名：{contact}</p>',
			'<p>联系人电话：{commTel}</p>',
			'<p>录入柜员：{crtOprId}&nbsp;&nbsp;&nbsp;&nbsp;审核柜员：{updOprId}</p>',
			'<p>最近更新时间：{updTs}</p>',
			{
				getmcc : function(val){return getRemoteTrans(val, "mcc");}
			}
		)
	});

	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchtNo',header: '商户ID',dataIndex: 'mchtNo',sortable: true,width: 120,align:'center'},
		{header: '商户名称',dataIndex: 'mchtNm',width: 460,id: 'mchtNm',align:'center'},
		{header: '商户号1',dataIndex: 'mappingMchntcdOne',width: 100,align:'center'},
		{header: '商户号2',dataIndex: 'mappingMchntcdTwo',width: 100,align:'center'},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 140,align:'center'},
		{header: '注册日期',dataIndex: 'applyDate',width: 80,renderer: formatDt,align:'center'},
		{header: '商户状态',dataIndex: 'mchtStatus',renderer: mchntSt,width: 60,align:'center'},
		{header: '终端数量',dataIndex: 'termCount',width: 60,align:'center'}
	]);
	
	
	setHeader(mchntColModel,3,4);
	//终端数据部分
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntTermInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'termStatus',mapping: 'termStatus'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'mchtCd',mapping: 'mchtCd'}
		])
	});
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'termId',header: '终端编号',dataIndex: 'termId',sortable: true,width: 70},
		{id: 'termSta',header: '终端状态',dataIndex: 'termStatus',renderer: termSta,width: 80},
		{id: 'termSta',header: '签到状态',dataIndex: 'termSignSta',renderer: termSignSta,hidden:true},
//		{id: 'termSta',header: '签到状态',dataIndex: 'termSignSta',renderer: termSignSta,width: 60},
		{id: 'mchtCd',dataIndex:'mchtCd',hidden:true}
	]);
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
		
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);
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
	
	var excelExport = {
			text: '导出报表',
			width: 85,
			renderTo: 'report',
			iconCls: 'download',
			waitMsgTarget: true,
			items: [{
				xtype: 'panel',
				html: '<br/><br/>'
			}],
			handler:function() {
				var totalCount=mchntStore.getTotalCount();
				if(totalCount > 1000){
					showErrorMsg("导出的数据不能超过10000条",mchntGrid);
					return;
				}
				Ext.Ajax.request({
					url: 'T20105Action_download2.asp',
					waitMsg: '正在下载报表，请稍等......',
					params: {
						mchntId: _mchntId,
						acmchntId: _acmchntId,
						mchtStatus:_mchtStatus,
						mchtGrp:_mchtGrp,
						startDate:_startDate,
						endDate:_endDate,
						brhId:_brhId,
						MchtFlag1:_MchtFlag1,
						connType:_connType
					},
					success: function(rsp,opt){
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
														rspObj.msg+'&key=exl23exl';
						} else {
							showErrorMsg(rspObj.msg,mchntGrid);
						}
					},
					failure: function(rsp,opt){
						hideMask();
					}
				});
			}
	};

	menuArr.push(detailMenu);  //[8]
	menuArr.push('-');         //[9]
	menuArr.push(queryCondition);  //[10]
	menuArr.push('-');  //[11]
	menuArr.push(excelExport);  //[12]
	
	var termDetailMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			selectTermInfo(termGrid.getSelectionModel().getSelected().get('termId'),termGrid.getSelectionModel().getSelected().get('mchtCd'));	
		}
	};
	
	
	var termGrid = new Ext.grid.GridPanel({
		title: '终端信息',
		region: 'east',
		width: 200,
		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'termSta',
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [termDetailMenu],
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: false
		})
	});
	
	// 禁用编辑按钮
	termGrid.getStore().on('beforeload',function() {
		termGrid.getTopToolbar().items.items[0].disable();
	
	});
	
	termGrid.getSelectionModel().on({
		'rowselect': function() {
			termGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户间联信息导出',
		region: 'center',
		iconCls: 'T10403',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'mchtNm',
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
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
//	mchntStore.load();
	mchntGrid.getTopToolbar().items.items[4].disable();
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo;
		termStore.load({
			params: {
				start: 0,
				mchntNo: id
				}
			});
	});
	termStore.on('beforeload', function() {
		Ext.apply(this.baseParams,
				{mchntNo : mchntGrid.getSelectionModel().getSelected().data.mchtNo
				});
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
		}
	});
	
	// Mcc下拉列表
	var MccStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCC',function(ret){
		MccStore.loadData(Ext.decode(ret));
	});
	// Mcc下拉列表
	var MccCombo = new Ext.form.ComboBox({
		store: MccStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择Mcc',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		blankText: '请选择Mcc',
		fieldLabel: 'Mcc',
		id: 'mcc'
	});
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			//endDateField: 'endDate',
			fieldLabel: '注册开始日期',
			editable: false
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			//startDateField: 'startDate',
			fieldLabel: '注册结束日期',
			editable: false
		},{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '商户状态',
			baseParams: 'MCHT_STATUS',
			anchor: '70%'
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW',
			fieldLabel: '归属机构',
			id: 'idacqInstId',
			hiddenName: 'acqInstId',
			anchor: '70%'
		},{
			xtype: 'basecomboselect',
	        baseParams: 'MCHT_FALG1',
			//labelStyle: 'padding-left: 5px',
			fieldLabel:'商户性质1',
			id: 'idMchtFlag1',
			hiddenName: 'mchtFlag1',
			anchor: '70%'
		},{
			xtype: 'basecomboselect',
	        baseParams: 'CONN_TYPE',
			//labelStyle: 'padding-left: 5px',
			fieldLabel:'商户接入方式',
			id: 'idconnType',
			hiddenName: 'connType',
			anchor: '70%'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户ID',
			methodName: 'getMchntIdJ',
			hiddenName: 'mchtNo',
			editable: true,
			width: 380
		},{
			xtype:'textfield',
			fieldLabel: '商户号',
			id:'acmchntIdQ',
			name:'acmchntIdQ',
			width:300
		},{
			xtype: 'basecomboselect',
			baseParams: 'MCHNT_GRP_ALL',
			fieldLabel: 'MCC类别',
			hiddenName: 'mchtGrp',
			width: 380
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
		       	var endtime=Ext.getCmp('endDate').getValue(),starttime=Ext.getCmp('startDate').getValue();
            	if(endtime!=''&&starttime!=''&&endtime<starttime){
            		showErrorMsg("请保证截止日期不小于起始日期",queryWin);
    				return;
            	}
            	_mchntId = queryForm.getForm().findField('mchtNo').getValue();
            	_acmchntId = Ext.getCmp('acmchntIdQ').getValue();
            	_mchtStatus = queryForm.findById('mchtStatus').getValue();
            	_mchtGrp = queryForm.getForm().findField('mchtGrp').getValue();
            	_startDate = typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd');
            	_endDate = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd');
            	_brhId = queryForm.getForm().findField('acqInstId').getValue();
            	_MchtFlag1 = queryForm.getForm().findField('idMchtFlag1').getValue();
            	_connType = queryForm.getForm().findField('idconnType').getValue();
				mchntStore.load();
				queryWin.hide();
				mchntGrid.getTopToolbar().items.items[4].enable();
				queryForm.getForm().reset();
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
			mchntId: _mchntId,
			acmchntId: _acmchntId,
			mchtStatus:_mchtStatus,
			mchtGrp:_mchtGrp,
			startDate:_startDate,
			endDate:_endDate,
			brhId:_brhId,
			MchtFlag1:_MchtFlag1,
			connType:_connType
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,termGrid],
		renderTo: Ext.getBody()
	});
	
});