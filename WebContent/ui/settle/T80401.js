Ext.onReady(function() {
	
	// 商户付款交易信息查询
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtPayInfoCount'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
//			{name: 'DATE_SETTLMT',mapping: 'DATE_SETTLMT'},
			{name: 'mcht_cd',mapping: 'mcht_cd'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mchtTp1',mapping: 'mchtTp1'},
			{name: 'mchtTp2',mapping: 'mchtTp2'},
			{name: 'mchtFeeTp',mapping: 'mchtFeeTp'},
			{name: 'trans_amt',mapping: 'trans_amt'},
			{name: 'transFee',mapping: 'transFee'},
			{name: 'settl_amt',mapping: 'settl_amt'},
			{name: 'transCount',mapping: 'transCount'},
			{name: 'amtIn',mapping: 'amtIn'}
		])
//	,
//		autoLoad: true
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([
//		{header: '清算日期',dataIndex: 'DATE_SETTLMT',width: 80},
		{header: '商户号',dataIndex: 'mcht_cd',width: 130},
		{header: '商户名',dataIndex: 'mchtNm',width: 420},
		{header: '商户组别',dataIndex: 'mchtGrp',width: 80},
		{header: '商户性质1',dataIndex: 'mchtTp1',width: 70,renderer:mchtTp},
		{header: '商户性质2',dataIndex: 'mchtTp2',width: 120,renderer:mchtTp2},
		{header: '商户归属机构',dataIndex: 'mchtFeeTp',width: 80},
		{header: '商户所在地',dataIndex: 'trans_amt',align : 'right'},
		{header: '付款转出账号',dataIndex: 'transFee',align : 'right'},
		{header: '开户行名称',dataIndex: 'settl_amt',align : 'right'},
		{header: '付款笔数(笔)',dataIndex: 'transCount',align : 'right'},
		{header: '付款金额(元)',dataIndex: 'amtIn',align : 'right'}
	]);
	
	
	var queryConditionMebu = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	var report = {
		text: '生成报表',
		width: 85,
		id: 'report',
		iconCls: 'download',
		handler:function() {
			showMask("正在为您准备报表，请稍后。。。",grid);
			Ext.Ajax.requestNeedAuthorise({
				url: 'T80401Action.asp',
				params: {
					mchtCd: queryForm.getForm().findField('mchtCd').getValue(),
					mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
					mchtFlag1: queryForm.getForm().findField('mchtFlag1').getValue(),
					signInstId: queryForm.getForm().findField('signInstId').getValue(),
					agrBr: queryForm.getForm().findField('agrBr').getValue(),
					startDate: typeof (queryForm.findById('date').getValue()) == 'string' ? '' : queryForm.findById('date').getValue().dateFormat('Ymd'),
					endDate: typeof (queryForm.findById('date1').getValue()) == 'string' ? '' : queryForm.findById('date1').getValue().dateFormat('Ymd'),
					txnId: '80401',
					subTxnId: '01'
				},
				success: function(rsp,opt) {
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													rspObj.msg;
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				},
				failure: function(){
					hideMask();
				}
			});
		}
	};

	var menuArr = new Array();
	menuArr.push(queryConditionMebu);  //[0]
	menuArr.push('-');      //[1]
	menuArr.push(report);      //[2]
	
	// 商户对账交易查询
	var grid = new Ext.grid.GridPanel({
		title: '间联商户付款交易汇总',
		iconCls: 'T804',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		store: txnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: txnColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载商户对账交易信息列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: txnStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 550,
		autoHeight: true,
		items: [{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','1-所在地区'],['2','2-归属机构']],
				reader: new Ext.data.ArrayReader()
			}),
			fieldLabel: '查询类型',
			id:'qryTpID',
			hiddenName: 'qryTp',
			allowBlank:false,
			value:'1',
			width: 320,
			listeners:{
				'select':function(){
					var tp = queryForm.getForm().findField('qryTpID').getValue();
					queryForm.getForm().findField('signInstId').reset();
					queryForm.getForm().findField('agrBr').reset();
					if(tp=='1'){
						Ext.getCmp('IdSignInstId').show();
						Ext.getCmp('agrBrId').hide();
						
//						queryForm.getForm().findField('agrBr').allowBlank = true;
//						queryForm.getForm().findField('signInstId').allowBlank = false;
					}
					if(tp=='2'){
						Ext.getCmp('agrBrId').show();
						Ext.getCmp('IdSignInstId').hide();
						
//						queryForm.getForm().findField('signInstId').allowBlank = true;
//						queryForm.getForm().findField('agrBr').allowBlank = false;
					}
				}
			}
		},{
			xtype:'panel',
			layout: 'form',
			id:'IdSignInstId',
			items: [{
				xtype: 'basecomboselect',
		        baseParams: 'BRH_BELOW_BRANCH',
				fieldLabel: '商户所在地区*',
				editable:true,
				blankText: '请选择商户所在地区',
				hiddenName: 'signInstId',
				width: 320
			}]
		},{
			xtype:'panel',
			layout: 'form',
			id:'agrBrId',
			hidden:true,
			items: [{
				xtype: 'basecomboselect',
		        baseParams: 'BRH_BELOW',
				fieldLabel: '归属机构*',
				editable:true,
				blankText: '请选择归属机构',
				hiddenName: 'agrBr',
				width: 320
			}]
		},{//查询正式表的商户
			xtype : 'dynamicCombo',
			methodName : 'getMchntIdJ',
			fieldLabel: '商户编号',
			hiddenName: 'mchtCd',
			width: 410,
			editable: true
		},{
			xtype: 'basecomboselect',
        	baseParams: 'MCHNT_GRP_ALL',
			fieldLabel: '商户组别',
			hiddenName: 'mchtGrp',
			width: 380
		},{
			xtype: 'basecomboselect',
	        baseParams: 'MCHT_FALG1',
			fieldLabel: '商户性质1',
			id: 'idMchtFlag1Q',
			hiddenName: 'mchtFlag1',
			width: 380
		},{
			xtype:'datefield',
			fieldLabel:'起始清算日期*',
			id:'date',
			name:'date',
			endDateField: 'date1',
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
//			allowBlank:false,
			width: 120,
			editable:false
		},{
			xtype:'datefield',
			fieldLabel:'结束清算日期*',
			id:'date1',
			name:'date1',
			startDateField: 'date',
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
//			allowBlank:false,
			width: 120,
			editable:false
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 550,
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
				if(queryForm.getForm().isValid()){
					txnStore.load();
				}
//				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	txnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			mchtCd: queryForm.getForm().findField('mchtCd').getValue(),
			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
			mchtFlag1: queryForm.getForm().findField('mchtFlag1').getValue(),
			signInstId: queryForm.getForm().findField('signInstId').getValue(),
			agrBr: queryForm.getForm().findField('agrBr').getValue(),
			date: typeof (queryForm.findById('date').getValue()) == 'string' ? '' : queryForm.findById('date').getValue().dateFormat('Ymd'),
			date1: typeof (queryForm.findById('date1').getValue()) == 'string' ? '' : queryForm.findById('date1').getValue().dateFormat('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});