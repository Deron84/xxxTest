Ext.onReady(function() {
	// 当前选择记录
	var record;
	var dealStore=[['1','手工清算已办结'],
	           	['2','正常清算已办结'],
	           	['3','暂不清算等待处理'],
	           	['4','押款已办结']];
	// 移机监测信息
	var riskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=shiftTermDayInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'ABS_DATE',mapping: 'ABS_DATE'},
			{name: 'BRH_NO',mapping: 'BRH_NO'},
			{name: 'ACQ_INST_ID',mapping: 'ACQ_INST_ID'},
			{name: 'MCHT_NO',mapping: 'MCHT_NO'},
			{name: 'MCHT_NM',mapping: 'MCHT_NM'},
			{name: 'SETTLE_ACCT',mapping: 'SETTLE_ACCT'},	
			{name: 'TERM_NO',mapping: 'TERM_NO'},
			{name: 'REAL_TEL',mapping: 'REAL_TEL'},
			{name: 'BIND_TEL1',mapping: 'BIND_TEL1'},
			{name: 'BIND_TEL2',mapping: 'BIND_TEL2'},
			{name: 'BIND_TEL3',mapping: 'BIND_TEL3'},
			{name: 'TXN_NUM',mapping: 'TXN_NUM'},
			{name: 'INST_TIME',mapping: 'INST_TIME'},
			{name: 'PAN',mapping: 'PAN'},
			{name: 'AMT_TRANS',mapping: 'AMT_TRANS'},
			{name: 'TERM_SSN',mapping: 'TERM_SSN'},
			{name: 'CERTI_ID',mapping: 'CERTI_ID'},
			{name: 'AUTHR_ID_R',mapping: 'AUTHR_ID_R'},
			{name: 'RETRIVL_REF',mapping: 'RETRIVL_REF'},
			{name: 'FLAG',mapping: 'FLAG'},
			{name: 'DESR',mapping: 'DESR'}
		]),
		autoLoad: true
	}); 
	
	var riskRowExpander = new Ext.ux.grid.RowExpander(
			{
				tpl : new Ext.Template(
						'<p>代理机构码：{ACQ_INST_ID}</p>',
						'<p>主呼叫号：{REAL_TEL}</p>',
						'<p>绑定电话1：{BIND_TEL1}</p>',
						'<p>绑定电话2：{BIND_TEL2}</p>',
						'<p>绑定电话3：{BIND_TEL3}</p>',
						'<p>交易时间：{INST_TIME}</p>',
						'<p>交易卡号：{PAN}</p>',
						'<p>交易金额：{AMT_TRANS}</p>',
						'<p>终端流水号：{TERM_SSN}</p>',
						'<p>交易码：{TXN_NUM}</p>',
						'<p>凭证号：{CERTI_ID}</p>',
						'<p>密码：{AUTHR_ID_R}</p>',
						'<p>交易参考号：{RETRIVL_REF}</p>', 
						'<p>备注：{DESR}</p>'
						)
			});
	
	
	//var sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn}); 
	var riskColModel = new Ext.grid.ColumnModel([riskRowExpander,
	    {header: '监测时间(日)',dataIndex: 'ABS_DATE',width: 100},
	    {header: '机构号',dataIndex: 'BRH_NO',width: 70},
	    {header: '商户号',dataIndex: 'MCHT_NO',width: 130},
	    {header: '商户名称',dataIndex: 'MCHT_NM',width:250},
	    {header: '结算帐号',dataIndex: 'SETTLE_ACCT',width: 150},
	    {header: 'POS编号',dataIndex: 'TERM_NO'},
	    {header: '状态',dataIndex: 'FLAG',renderer:riskDealType},
	    {header: '白名单商户',dataIndex: 'DESR',renderer:mchtType}
	]);
	
	
	var menuArr = new Array();

	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 500,
		autoHeight : true,
		labelWidth : 80,
		items : [{
					xtype:'datefield',
					id: 'dateStart',
					fieldLabel: '监测起始时间',
					vtype: 'daterange',
					endDateField: 'dateEnd',
					allowBlank: false,
					width : 380
				},{
					xtype:'datefield',
					id: 'dateEnd',
					fieldLabel: '监测终止时间',
					vtype: 'daterange',
					startDateField: 'dateStart',
					allowBlank: false,
					width : 380
				},{
					xtype : 'basecomboselect',
					name:'brhId',
					fieldLabel : '机构号',
					baseParams : 'BRH_BELOW',
					width : 380
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
				url : 'T40102Action.asp',
				params : {
					mchtCd : grid.getSelectionModel()
							.getSelected().get('MCHT_NO'),
					mon:grid.getSelectionModel()
							.getSelected().get('ABS_MON'),
					flag:grid.getSelectionModel().getSelected().get('FLAG'),
					txnId : '40102',
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
		Ext.apply(this.baseParams,
				{
					start : 0,
					mchtCd : queryForm.getForm().findField('mchtCd')
							.getValue(),
					dateStart : typeof (queryForm.findById('dateStart')
							.getValue()) == 'string' ? '' : queryForm
									.findById('dateStart').getValue().dateFormat(
											'Ymd'),
					dateEnd : typeof (queryForm.findById('dateEnd')
							.getValue()) == 'string' ? '' : queryForm
									.findById('dateEnd').getValue().dateFormat(
											'Ymd'),
					brhId : queryForm.getForm().findField('brhId')
							.getValue()
				});
	});
	
	menuArr.push(queryCondition);  
//	menuArr.push(dealCondition);
	
	// 移机监测信息
	var grid = new Ext.grid.GridPanel( {
		title : '移机详细查询',
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
		plugins : riskRowExpander,
		loadMask : {
			msg : '正在加载移机监测信息列表......'
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
	
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})