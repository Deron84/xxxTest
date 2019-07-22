Ext.onReady(function() {
	
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntCupInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			 {
				name : 'mappingMchntcdOne',
				mapping : 'mappingMchntcdOne'
			}, {
				name : 'mappingMchntcdTwo',
				mapping : 'mappingMchntcdTwo'
			},
			{name: 'mchtStates',mapping: 'mchtStates'},
			{name:'mchtType',mapping:'mchtType'},
			{name:'crtTs',mapping:'crtTs'},
			{name:'updTs',mapping:'updTs'},
			{name:'insetFlag',mapping:'insetFlag'}
		])
	});
	
//	mchntStore.load({
//		params: {
//			start: 0
//		}
//	});
	

	
	var mchntColModel = new Ext.grid.ColumnModel([

		{id: 'mchtNo',header: '商户ID',dataIndex: 'mchtNo',sortable: true,width: 140},
		{header: '商户名称',dataIndex: 'mchtNm',width: 200},
		{header: '商户号1',dataIndex: 'mappingMchntcdOne',width: 100},
		{header: '商户号2',dataIndex: 'mappingMchntcdTwo',width: 100},
		{header: '商户类型',dataIndex: 'mchtType',width: 180,renderer:mchtCupType},
		{header: '商户状态',dataIndex: 'mchtStates',renderer:mchtCupStInfo},
		{header: '入网日期',dataIndex: 'crtTs',width: 140},
		{header: '变更日期',dataIndex: 'updTs',width: 140},
		{header: '变更类型',dataIndex: 'insetFlag',renderer:insetFlag01}
	]);
	
	setHeader(mchntColModel,2,3);
	var menuArray = new Array();
	
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable();},2000);
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
	
	var report = {
			text: '导出直联商户信息',
			width: 160,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",mchntGrid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T20106Action.asp',
					params: {
						mchtNo: queryForm.findById('mchtNoQ').getValue(),
						brhId: queryForm.getForm().findField('brhId').getValue(),
			            mchtStatus: queryForm.findById('mchtStatusQ').getValue(),
			            mchtType: queryForm.findById('mchtTypeQ').getValue(),
			            startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
						endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
						startDateU: typeof(queryForm.findById('startDateU').getValue()) == 'string' ? '' : queryForm.findById('startDateU').getValue().dateFormat('Ymd'),
						endDateU: typeof(queryForm.findById('endDateU').getValue()) == 'string' ? '' : queryForm.findById('endDateU').getValue().dateFormat('Ymd'),
						txnId: '20106',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,mchntGrid);
						}
					},
					failure: function(){
						hideMask();
					}
				});
			}
		}
	

	menuArray.push(detailMenu);  //[0]
	menuArray.push('-');         //[1]
	menuArray.push(queryCondition);  //[2]
	menuArray.push('-');         //[3]
	menuArray.push(report);  //[4]
		
	
	// 商户黑名单列表
	var mchntGrid = new Ext.grid.EditorGridPanel({
		title: '直联商户批导',
		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载直联商户信息列表......'
		},
		tbar: menuArray,
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
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = mchntGrid.getSelectionModel().getSelected();
			mchntGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
//		labelWidth: 80,
		items: [{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '入网日期始',
			editable: false,
			anchor: '90%'
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '入网日期止',
			editable: false,
			anchor: '90%'
		},{
			xtype: 'datefield',
			id: 'startDateU',
			name: 'startDateU',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'endDateU',
			fieldLabel: '变更日期始',
			editable: false,
			anchor: '90%'
		},{
			xtype: 'datefield',
			id: 'endDateU',
			name: 'endDateU',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDateU',
			fieldLabel: '变更日期止',
			editable: false,
			anchor: '90%'
		},{
		    xtype: 'basecomboselect',
	        baseParams: 'MCHT_CUP_TYPE',
			fieldLabel: '商户种类',
			id: 'mchtTypeQ',
			hiddenName: 'mchtType',
			editable: false,
			anchor: '90%'
		},{
			xtype: 'combo',
			id:'mchtStatusQ',
			hiddenName: 'mchtStatus',
			fieldLabel: '商户状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','正常'],['0','注销'],['2','冻结']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '90%'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户ID',
			methodName: 'getMchntCupIdAllInfo',
			id:'mchtNoQ',
			hiddenName: 'mchtNo',
			editable: true,
			anchor: '99%'
		},{
			xtype:'textfield',
			fieldLabel: '商户号',
			id:'acmchntIdQ',
			name:'acmchntIdQ',
			width:300
		},{
	        xtype: 'basecomboselect',
	        baseParams: 'CUP_BRH_S',
			fieldLabel: '内卡收单机构*',
			allowBlank: false,
			hiddenName: 'brhId',
			anchor: '90%'
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
				var frm = queryForm.getForm();
				if(frm.isValid()){
					mchntStore.load();
					queryWin.hide();
				}
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntStore.on('beforeload',function() {
		Ext.apply(this.baseParams, {
            start: 0,
            mchtNo: queryForm.findById('mchtNoQ').getValue(),
            acmchntId: Ext.getCmp('acmchntIdQ').getValue(),
            brhId: queryForm.getForm().findField('brhId').getValue(),
            mchtStatus: queryForm.findById('mchtStatusQ').getValue(),
            mchtType: queryForm.findById('mchtTypeQ').getValue(),
            startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			startDateU: typeof(queryForm.findById('startDateU').getValue()) == 'string' ? '' : queryForm.findById('startDateU').getValue().dateFormat('Ymd'),
			endDateU: typeof(queryForm.findById('endDateU').getValue()) == 'string' ? '' : queryForm.findById('endDateU').getValue().dateFormat('Ymd')
        });
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
});