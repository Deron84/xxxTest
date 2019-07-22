Ext.onReady(function() {

	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bthDtlErrGc'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'dateSettlmt',mapping: 'DATE_SETTLMT'},
			{name: 'outSsn',mapping: 'OUT_SSN'},
			{name: 'TERM_SSN',mapping: 'TERM_SSN'},
			{name: 'acqTxnSsn',mapping: 'acq_txn_ssn'},
			{name: 'pan',mapping: 'PAN'},
			{name: 'amtTrans',mapping: 'AMT_TRANS'},
			{name: 'trans_date_time',mapping: 'trans_date_time'},
			{name: 'err_proc_cd',mapping: 'err_proc_cd'},
			{name:'txn_num',mapping:'txn_num'},
			{name:'txnNm',mapping:'txnNm'},
			{name:'stlmFlag',mapping:'stlmFlag'},
			{name:'mchtNo',mapping:'mchtNo'},
			{name:'mchtNm',mapping:'mchtNm'}
		]),
		autoLoad: true
	}); 
	
	var txnColModel = new Ext.grid.ColumnModel([
		{header: '清算日期',dataIndex: 'dateSettlmt',width: 80},
		{header: '交易时间',dataIndex: 'trans_date_time'},
	    {header: '管理机构',dataIndex: 'bankNo',width: 180,renderer:function(val){return getRemoteTrans(val, "brhName");}},
        {header: '商户号',dataIndex: 'mchtNo',width: 130},
        {header: '商户名',dataIndex: 'mchtNm',width: 420},
        {header: '收单流水号',dataIndex: 'acqTxnSsn',width: 100},
        {header: '终端流水号',dataIndex: 'TERM_SSN',width: 100},
        {header: '外部流水号',dataIndex: 'outSsn',width: 100},
		{header: '卡号',dataIndex: 'pan',width: 150},
		{header: '交易类型',dataIndex: 'txnNm',width: 120},
		{header: '交易编号',dataIndex: 'txn_num',hidden:true},
		{header: '金额',dataIndex: 'amtTrans',width: 100},
		{header: '清算状态',dataIndex: 'stlmFlag',width: 180,renderer:errFlag},
		{header: '差错处理状态',dataIndex: 'err_proc_cd',width: 150,renderer:transTxnAcqType}
	]);
	
	var menuArr = new Array();

	var queryConditionMebu = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	
	
	var deal0Menu = {
			text: '按正常交易向商户清算',
			width: 85,
			iconCls: 't90301',
			disabled: true,
			handler:function() {
				
				var rec = grid.getSelectionModel().getSelected();
				var flag = rec.get('acqTxnSsn');
				
				if(flag == null || flag ==''){
					showErrorMsg('无收单流水号，不能进行该操作！',grid);
					return;
				}
				
				showConfirm('确认执行此操作吗？',grid,function(bt) {
					
					if(bt == 'yes') {
						showProcessMsg('正在提交信息，请稍后......');
						
						Ext.Ajax.requestNeedAuthorise({
							url: 'T90301Action.asp',
							params: {
								trans_date_time: rec.get('trans_date_time'),
								pan: rec.get('pan'),
								acqTxnSsn: rec.get('acqTxnSsn'),
								outSsn: rec.get('outSsn'),
								txnId: '90301',
								subTxnId: '0'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载终端待审核信息
								grid.getStore().reload();
							}
						});
						hideProcessMsg();
	                    
					}
				});
			}
		};
		
	var deal2Menu = {
			text: '等待调查押款处理',
			width: 85,
			iconCls: 't90301',
			disabled: true,
			handler:function() {
				showConfirm('确认执行此操作吗？',grid,function(bt) {
					
					if(bt == 'yes') {
						showProcessMsg('正在提交信息，请稍后......');
						var rec = grid.getSelectionModel().getSelected();
						
						Ext.Ajax.requestNeedAuthorise({
							url: 'T90301Action.asp',
							params: {
								trans_date_time: rec.get('trans_date_time'),
								pan: rec.get('pan'),
								acqTxnSsn: rec.get('acqTxnSsn'),
								outSsn: rec.get('outSsn'),
								txnId: '90301',
								subTxnId: '2'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载终端待审核信息
								grid.getStore().reload();
							}
						});
						hideProcessMsg();
	                    
					}
				});
			}
		};
		
	var deal3Menu = {
			text: '次日资金扣回',
			width: 85,
			iconCls: 't90301',
			disabled: true,
			handler:function() {
				
				var rec = grid.getSelectionModel().getSelected();
				var flag = rec.get('acqTxnSsn');
				
				if(flag == null || flag ==''){
					showErrorMsg('无收单流水号，不能进行该操作！',grid);
					return;
				}
				
				showConfirm('确认执行此操作吗？',grid,function(bt) {
					
					if(bt == 'yes') {
						showProcessMsg('正在提交信息，请稍后......');
						var rec = grid.getSelectionModel().getSelected();
						
						Ext.Ajax.requestNeedAuthorise({
							url: 'T90301Action.asp',
							params: {
								trans_date_time: rec.get('trans_date_time'),
								pan: rec.get('pan'),
								acqTxnSsn: rec.get('acqTxnSsn'),
								outSsn: rec.get('outSsn'),
								txnId: '90301',
								subTxnId: '3'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载终端待审核信息
								grid.getStore().reload();
							}
						});
						hideProcessMsg();
	                    
					}
				});
			}
		};
		
	var deal4Menu = {
			text: '行方托收',
			width: 85,
			iconCls: 't90301',
			disabled: true,
			handler:function() {
				var rec = grid.getSelectionModel().getSelected();
				var flag = rec.get('acqTxnSsn');
				
				if(flag == null || flag ==''){
					showErrorMsg('无收单流水号，不能进行该操作！',grid);
					return;
				}
				
				showConfirm('确认执行此操作吗？',grid,function(bt) {
					
					if(bt == 'yes') {
						showProcessMsg('正在提交信息，请稍后......');
						var rec = grid.getSelectionModel().getSelected();
						
						Ext.Ajax.requestNeedAuthorise({
							url: 'T90301Action.asp',
							params: {
								trans_date_time: rec.get('trans_date_time'),
								pan: rec.get('pan'),
								acqTxnSsn: rec.get('acqTxnSsn'),
								outSsn: rec.get('outSsn'),
								txnId: '90301',
								subTxnId: '4'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载终端待审核信息
								grid.getStore().reload();
							}
						});
						hideProcessMsg();
	                    
					}
				});
			}
		};
		
	var deal5Menu = {
			text: '多收款项退回发卡行',
			width: 85,
			iconCls: 't90301',
			disabled: true,
			handler:function() {
				var rec = grid.getSelectionModel().getSelected();
				var flagAcc = rec.get('acqTxnSsn');
				
				if(flagAcc == null || flagAcc ==''){
					showErrorMsg('无收单流水号，不能进行该操作！',grid);
					return;
				}
				
				showConfirm('确认执行此操作吗？',grid,function(bt) {
					if(bt == 'yes') {
						showProcessMsg('正在提交信息，请稍后......');
						var rec = grid.getSelectionModel().getSelected();
						
						Ext.Ajax.requestNeedAuthorise({
							url: 'T90301Action.asp',
							params: {
								trans_date_time: rec.get('trans_date_time'),
								pan: rec.get('pan'),
								acqTxnSsn: rec.get('acqTxnSsn'),
								outSsn: rec.get('outSsn'),
								txnId: '90301',
//								record:text,
								subTxnId: '5'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载终端待审核信息
								grid.getStore().reload();
							}
						});
						hideProcessMsg();
					}
				});
			}
		};
		
		
	//装按钮
	menuArr.push(deal0Menu);//0
	menuArr.push('-');
	menuArr.push(deal2Menu);//2
//	menuArr.push('-');
//	menuArr.push(deal3Menu);
	menuArr.push('-');
	menuArr.push(deal4Menu);//4
	menuArr.push('-');
	menuArr.push(deal5Menu);//6
	menuArr.push('-');
	menuArr.push(queryConditionMebu);
	
	// 交易查询
	var grid = new Ext.grid.GridPanel({
		title: '未完结差错处理',
		iconCls: 'T501',
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
			msg: '正在加载查询列表......'
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
			
			var rec = grid.getSelectionModel().getSelected();
			var sta = rec.get('err_proc_cd');
			var dis = rec.get('stlmFlag');
			var txnNum=rec.get('txn_num').charAt(0);
			var flag = rec.get('acqTxnSsn');
			
			
			/**	dis描述
			  	case '1': return '收单成功，银联失败';
				case '2': return '收单失败，银联成功';
				case '3': return '收单与银联金额不一致';
				case '4': return '收单成功，核心失败';
				case '5': return '收单失败， 核心成功';
				case '6': return '收单与核心金额不一致';
				case 'R': return '收单成功，银联数据失败';
				case 'S': return '收单失败，银联数据成功';
				case 'T': return '收单与银联数据金额不一致';**/
			if((sta=='01'||sta=='02')){
				if(flag !='' && txnNum=='1' && (dis =='2' || dis =='5' || dis =='S')){
					grid.getTopToolbar().items.items[0].enable();
				}else{
					grid.getTopToolbar().items.items[0].disable();
				}
				
				grid.getTopToolbar().items.items[2].enable();

				if(flag !='' && txnNum=='3' && dis !='3' && dis !='6' && dis !='T'){
					grid.getTopToolbar().items.items[4].enable();
				}else{
					grid.getTopToolbar().items.items[4].disable();
				}
				
				if(flag !='' && txnNum=='1' && (dis =='2' || dis =='5' || dis =='S')){
					grid.getTopToolbar().items.items[6].enable();
				}else{
					grid.getTopToolbar().items.items[6].disable();
				}
			}else{
				grid.getTopToolbar().items.items[0].disable();
				grid.getTopToolbar().items.items[2].disable();
				grid.getTopToolbar().items.items[4].disable();
				grid.getTopToolbar().items.items[6].disable();
			}
			
		}
	});
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 390,
		autoHeight: true,
		items: [{
			xtype : 'dynamicCombo',
			width : 320,
			fieldLabel : '商户编号',
			methodName : 'getMchntIdInCup',
			hiddenName : 'mchtNoQ',
			editable : true
		}, {
			xtype: 'numberfield',
			fieldLabel: '卡号',
			width : 320,
			id: 'pan'
		},{
			xtype: 'basecomboselect',
			baseParams:'TXN_ACQ_TYPE',
			fieldLabel: '差错处理状态',
			width : 320,
			id: 'errProcCdQ',
			name:'errProcCd'
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
				txnStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	txnStore.on('beforeload', function(){
		grid.getTopToolbar().items.items[0].disable();
		grid.getTopToolbar().items.items[2].disable();
		grid.getTopToolbar().items.items[4].disable();
		grid.getTopToolbar().items.items[6].disable();
		Ext.apply(this.baseParams, {
			pan: queryForm.getForm().findField('pan').getValue(),
			mchtNo : queryForm.getForm().findField('mchtNoQ').getValue(),
			errProcCd:queryForm.getForm().findField('errProcCd').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});