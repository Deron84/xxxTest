Ext.onReady(function() {
	// 当前选择记录
	var record;
	var dealStore=[['1','手工清算已办结'],
	['2','正常清算已办结'],
	['3','暂不清算等待处理'],
	['4','押款已办结']];
	// 风险交易信息(套现)
	var riskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskTxnInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'ABS_MON',mapping: 'ABS_MON'},
			{name: 'BRH_NO',mapping: 'BRH_NO'},
			{name: 'MCHT_NO',mapping: 'MCHT_NO'},
			{name: 'MCHT_NM',mapping: 'MCHT_NM'},
			//{name: 'FIRST_T_DATE',mapping: 'FIRST_T_DATE'},	
			{name: 'SETTLE_ACCT',mapping: 'SETTLE_ACCT'},
			{name: 'SINGLE_COUNT',mapping: 'SINGLE_COUNT'},
			{name: 'SINGLE_AMT',mapping: 'SINGLE_AMT'},
			//{name: 'AVE_AMT',mapping: 'AVE_AMT'},
			{name: 'CREDIT_COUNT',mapping: 'CREDIT_COUNT'},
			{name: 'CREDIT_AMT',mapping: 'CREDIT_AMT'},
			{name: 'RET_COUNT',mapping: 'RET_COUNT'},
			{name: 'RET_AMT',mapping: 'RET_AMT'},
			{name: 'INTE_COUNT',mapping: 'INTE_COUNT'},
			{name: 'INTE_AMT',mapping: 'INTE_AMT'},
			{name: 'BIG_COUNT',mapping: 'BIG_COUNT'},
			{name: 'BIG_AMT',mapping: 'BIG_AMT'},
			{name: 'REF_COUNT',mapping: 'REF_COUNT'},
			{name: 'REF_AMT',mapping: 'REF_AMT'},
			{name: 'MISC',mapping: 'MISC'},
			{name: 'whiteFlag',mapping: 'whiteFlag'},
			{name: 'FLAG',mapping: 'FLAG'}
		]),
		autoLoad: true
	}); 
	
	var riskRowExpander = new Ext.ux.grid.RowExpander(
			{
				tpl : new Ext.Template(
						'<p>单月交易笔数：{SINGLE_COUNT}</p>',
						'<p>单月交易金额：{SINGLE_AMT}</p>',
						//'<p>单月交易平均金额：{AVE_AMT}</p>',
						'<p>信用卡交易占比笔数：{CREDIT_COUNT}</p>',
						'<p>信用卡交易占比金额：{CREDIT_AMT}</p>',
						'<p>单月退货笔数：{RET_COUNT}</p>',
						'<p>单月退货金额：{RET_AMT}</p>',
						'<p>整数（信用卡）笔数：{INTE_COUNT}</p>',
						'<p>整数（信用卡）金额：{INTE_AMT}</p>',
						'<p>大额笔数：{BIG_COUNT}</p>', 
						'<p>大额金额：{BIG_AMT}</p>', 
						'<p>单月拒绝交易笔数：{REF_COUNT}</p>', 
						'<p>单月拒绝交易金额：{REF_AMT}</p>', 
						'<p>备注：{MISC:this.getMisc}</p>',{
							getMisc:function(val){
								var t1= val.charAt(0)
								var t3 = val.charAt(2)
								if(t1=='1' && t3=='1'){
									return '即触发日风控，又触发月风控';
								}else if(t1=='1' && t3!='1'){
									return '触发日风控';
								}else if(t1!='1' && t3=='1'){
									return '触发月风控';
								}
							}
						})
			});
	
	
	//var sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn}); 
	var riskColModel = new Ext.grid.ColumnModel([
//		riskRowExpander,
	    {header: '监控日期(月)',dataIndex: 'ABS_MON',width: 100},
	    {header: '归属机构',dataIndex: 'BRH_NO',width: 100},
	    {header: '商户号',dataIndex: 'MCHT_NO',width: 150},
	    {header: '商户名称',dataIndex: 'MCHT_NM',width:300},
	    //{header: '第一次交易时间',dataIndex: 'FIRST_T_DATE',width: 100},
	    {header: '结算帐号',dataIndex: 'SETTLE_ACCT',width: 150},
	    {header: '状态',dataIndex: 'FLAG',renderer:riskDealType},
	    {header: '白名单商户',dataIndex: 'whiteFlag',renderer:mchtType,width: 70},
	    {header: '触发模型',dataIndex: 'MISC',width: 140,renderer:getMisc}
	]);
	
	
	var menuArr = new Array();

	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 500,
		autoHeight : true,
		labelWidth : 80,
		items : [
				new Ext.ux.MonthField({
					id: 'mon',
					name: 'mon',
					fieldLabel: '监控日期(月)',
					allowBlank: true,
					width: 150
				}),
				{
					xtype : 'basecomboselect',
					name:'brhId',
					fieldLabel : '归属机构',
					baseParams : 'BRH_BELOW',
					anchor : '70%'
				}, {
					xtype : 'dynamicCombo',
					fieldLabel : '商户编号',
					methodName : 'getMchntIdInBase',
					hiddenName : 'mchtCd',
					editable : true,
					width : 380
				}]
	});
	var queryWin = new Ext.Window( {
		title : '查询条件',
		layout : 'fit',
		width : 500,
		autoHeight : true,
		items : [ queryForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		closable : true,
		animateTarget : 'query',
		tools : [
				{
					id : 'minimize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.maximize.show();
						toolEl.hide();
						queryWin.collapse();
						queryWin.getEl().pause(1);
						queryWin.setPosition(10, Ext.getBody()
								.getViewSize().height - 30);
					},
					qtip : '最小化',
					hidden : false
				}, {
					id : 'maximize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.minimize.show();
						toolEl.hide();
						queryWin.expand();
						queryWin.center();
					},
					qtip : '恢复',
					hidden : true
				} ],
		buttons : [ {
			text : '查询',
			handler : function() {
				riskStore.load();
				queryForm.getForm().reset();
				queryWin.hide();
			}
		}, {
			text : '清除查询条件',
			handler : function() {
				queryForm.getForm().reset();
			}
		} ]
	});
	var queryCondition = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};
	
	
	//后续处理
	var dealForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 500,
		autoHeight : true,
		labelWidth : 80,
		items : [{
			xtype:'combo',
			store: new Ext.data.ArrayStore({
				reader: new Ext.data.ArrayReader(),
				fields: ['valueField','displayField'],
				data: dealStore
//				data:[
//						['1','手工清算已办结'],
//						['2','正常清算已办结'],
//						['3','暂不清算等待处理'],
//						['4','押款已办结']
//					]
			}),
			fieldLabel:'后续处理',
			hiddenName:'followDeal',
//			baseParams:'RISK_FOLLOW_DEAL',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank:false,
			blankText:'请选择后续处理动作'
		}]
	});
	var dealWin = new Ext.Window( {
		title : '后续处理',
		layout : 'fit',
		width : 400,
		autoHeight : true,
		items : [ dealForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		closable : true,
		animateTarget : 'query',
		tools : [
				{
					id : 'minimize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.maximize.show();
						toolEl.hide();
						dealWin.collapse();
						dealWin.getEl().pause(1);
						dealWin.setPosition(10, Ext.getBody()
								.getViewSize().height - 30);
					},
					qtip : '最小化',
					hidden : false
				}, {
					id : 'maximize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.minimize.show();
						toolEl.hide();
						dealWin.expand();
						dealWin.center();
					},
					qtip : '恢复',
					hidden : true
				} ],
		buttons : [ {
			text : '确定',
			handler : function() {
			if(!dealForm.getForm().isValid()){
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise( {
				url : 'T40101Action.asp',
				params : {
					mchtCd : grid.getSelectionModel()
							.getSelected().get('MCHT_NO'),
					mon:grid.getSelectionModel()
							.getSelected().get('ABS_MON'),
					flag:grid.getSelectionModel().getSelected().get('FLAG'),
					txnId : '40101',
					subTxnId:'1',
					
					dealType : dealForm.getForm().findField('followDeal').getValue()
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if (rspObj.success) {
						showSuccessMsg(rspObj.msg, grid);
					} else {
						showErrorMsg(rspObj.msg, grid);
					}
					// 重新加载信息
					riskStore.reload();
			}
			});
			hideProcessMsg();
			dealWin.hide();
			}
		}]
	});
	var dealCondition = {
			text : '后续处理',
			width : 85,
			id : 'deal',
			iconCls : 'edit',
			handler : function() {
				dealWin.show();
			}
		};
	
	
	
	riskStore.on('beforeload', function() {
//		grid.getTopToolbar().items.items[1].disable();
		Ext.apply(this.baseParams,
				{
					start : 0,
					mchtCd : queryForm.getForm().findField('mchtCd')
							.getValue(),
					mon : Ext.getCmp('mon').getValue(),
					brhId : queryForm.getForm().findField('brhId')
							.getValue()
				});
	});
	
	
	
	menuArr.push(queryCondition);  
	//该页面仅查询套现交易，后续处理去除。后续处理功能在另一页面实现。
//	menuArr.push(dealCondition); 
	
	// 风险模型列表
	var grid = new Ext.grid.GridPanel( {
		title : '疑似套现交易查询',
		region : 'center',
		iconCls : 'risk',
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		store : riskStore,
		sm : new Ext.grid.RowSelectionModel( {
			singleSelect : true
		}),
		cm : riskColModel,
		clicksToEdit : true,
		forceValidation : true,
		tbar : menuArr,
//		plugins : riskRowExpander,
		loadMask : {
			msg : '正在加载疑似套现交易信息列表......'
		},
		bbar : new Ext.PagingToolbar( {
			store : riskStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});
	
	grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});
	
	
//	grid.getSelectionModel().on({
//		'rowselect': function() {
//			//行高亮
//			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			var flag = grid.getSelectionModel().getSelected().get('FLAG');
//			if(flag=='0'||flag=='3'){
//				grid.getTopToolbar().items.items[1].enable();
//			}else{
//				grid.getTopToolbar().items.items[1].disable();
//				dealWin.hide();
//			}
//		}
//	});

	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})