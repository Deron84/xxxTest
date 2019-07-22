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
			url: 'gridPanelStoreAction.asp?storeId=shiftTermAct'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
            {name: 'settleDate',mapping: 'settleDate'},
            {name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'lastAmt',mapping: 'lastAmt'},
			{name: 'infileAmt',mapping: 'infileAmt'},
			{name: 'returnAmt',mapping: 'returnAmt'},
			{name: 'settleAmt',mapping: 'settleAmt'},
			{name: 'loamAmt',mapping: 'loamAmt'},	
			{name: 'FLAG',mapping: 'FLAG'},
			{name: 'DESR',mapping: 'DESR'},
			{name: 'rank',mapping: 'rank'},
			{name: 'moveTp',mapping: 'moveTp'}
		]),
		autoLoad: true
	}); 
	
	
	var riskColModel = new Ext.grid.ColumnModel([
        {header: '监测日期',dataIndex: 'settleDate'},
        {header: '商户号',dataIndex: 'mchtNo',id:'mchtNo',width: 120},
	    {header: '商户名称',dataIndex: 'mchtNm',width: 250},
	    {header: '上期押款金额(元)',dataIndex: 'lastAmt',width: 130},
	    {header: '本日清算金额(元)',dataIndex: 'infileAmt',width: 130},
	    {header: '扣除金额(元)',dataIndex: 'returnAmt',width:110},
	    {header: '清算金额(元)',dataIndex: 'settleAmt',width: 110},
	    {header: '商户所欠金额(元)',dataIndex: 'loamAmt',width: 120},
	    {header: '状态',dataIndex: 'FLAG',id:'FLAG',renderer:controlType},
	    {header: '是否移机',dataIndex: 'moveTp',id:'moveTp',renderer:moveTp}
//	    {header: '白名单商户',dataIndex: 'DESR',renderer:mchtType}
	    
	]);
	
	function controlType(val){
		if(val=='7'){
			return "手工清算已办结";
		}
		if(val=='5'){
			return "押款已办结";
		}
		if(val=='4'){
			return "正常清算已办结";
		}
		if(val=='2'){
			return "触发风控";
		}
	}
	
	function moveTp(val){
		if(val=='0'){
			return "否";
		}
		if(val>0){
			return "是";
		}
	}

	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 500,
		autoHeight : true,
		labelWidth : 80,
		items : [{
//			xtype : 'combo',
//			store: new Ext.data.ArrayStore({
//						fields: ['valueField','displayField'],
//						data: [['0','0-套现'],['1','1-移机'],['2','2-既移机又套现']],
//						reader: new Ext.data.ArrayReader()
//					}),
//			hiddenName:'FLAG',
//			fieldLabel : '状态',
//			anchor : '70%'
//		}, {
//			xtype : 'basecomboselect',
//			hiddenName:'brhId',
//			fieldLabel : '机构号',
//			baseParams : 'BRH_BELOW',
//			anchor : '70%'
//		}, {
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
		tools : [{
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
	
	var dealCondition1 = {
			text : '手工清算已办结',
			width : 85,
			id : 'deal1',
			disabled:true,
			iconCls : 'edit',
			handler : function() {
				var info = grid.getSelectionModel().getSelected();
				if( info == null ){
	                showErrorMsg('请先选择需要处理的信息！',mainView);
	                return;
	            }
				Ext.Ajax.requestNeedAuthorise( {
					url : 'T40102Action.asp',
					params : {
						mchtNo : info.get('mchtNo'),
						FLAG : info.get('FLAG'),
						txnId : '40102',
						subTxnId:'1',
						dealType : '1'
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if (rspObj.success) {
							showSuccessMsg(rspObj.msg, grid);
						} else {
							showErrorMsg(rspObj.msg, grid);
						}
						grid.getStore().reload();
					}
				});
			}
		};
	
	var dealCondition2 = {
			text : '正常清算已办结',
			width : 85,
			id : 'deal2',
			disabled:true,
			iconCls : 'edit',
			handler : function() {
				var info = grid.getSelectionModel().getSelected();
				if( info == null ){
	                showErrorMsg('请先选择需要处理的信息！',mainView);
	                return;
	            }
				Ext.Ajax.requestNeedAuthorise( {
					url : 'T40102Action.asp',
					params : {
						mchtNo : info.get('mchtNo'),
						FLAG : info.get('FLAG'),
						txnId : '40102',
						subTxnId:'2',
						dealType : '2'
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if (rspObj.success) {
							showSuccessMsg(rspObj.msg, grid);
						} else {
							showErrorMsg(rspObj.msg, grid);
						}
						grid.getStore().reload();
					}
				});
			}
		};
		
	var dealCondition3 = {
			text : '暂不清算等待处理',
			width : 85,
			id : 'deal3',
			disabled:true,
			iconCls : 'edit',
			handler : function() {
				var info = grid.getSelectionModel().getSelected();
				if( info == null ){
	                showErrorMsg('请先选择需要处理的信息！',mainView);
	                return;
	            }
				Ext.Ajax.requestNeedAuthorise( {
					url : 'T40102Action.asp',
					params : {
						mchtNo : info.get('mchtNo'),
						FLAG : info.get('FLAG'),
						txnId : '40102',
						subTxnId:'3',
						dealType : '3'
					},
					success : function(rsp, opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if (rspObj.success) {
							showSuccessMsg(rspObj.msg, grid);
						} else {
							showErrorMsg(rspObj.msg, grid);
						}
						grid.getStore().reload();
					}
				});
			}
		};
		
	var dealCondition4 = {
			text : '押款已办结',
			width : 85,
			id : 'deal4',
			disabled:true,
			iconCls : 'edit',
			handler : function() {
				var info = grid.getSelectionModel().getSelected();
				if( info == null ){
	                showErrorMsg('请先选择需要处理的信息！',mainView);
	                return;
	            }
				Ext.Ajax.requestNeedAuthorise( {
					url : 'T40102Action.asp',
					params : {
						mchtNo : info.get('mchtNo'),
						FLAG : info.get('FLAG'),
						txnId : '40102',
						subTxnId:'4',
						dealType : '4'
					},
					success : function(rsp, opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if (rspObj.success) {
							showSuccessMsg(rspObj.msg, grid);
							grid.getStore().reload();
						} else {
							showErrorMsg(rspObj.msg, grid);
						}
						grid.getStore().reload();
					}
				});
			}
		};
	
	var menuArr = new Array();
	
	menuArr.push(queryCondition);  // 0 
	menuArr.push('-');    // 1
	menuArr.push(dealCondition1);    //2
	menuArr.push('-');           //3
	menuArr.push(dealCondition2);        // 4
	menuArr.push('-');   // 5
	menuArr.push(dealCondition3);   // 6
	menuArr.push('-');   // 7
	menuArr.push(dealCondition4);    // 8
	
	
	// 移机监测信息
	var grid = new Ext.grid.GridPanel( {
		title : '风险控制处理',
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
		loadMask : {
			msg : '正在加载风险控制处理信息列表......'
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
		grid.getTopToolbar().items.items[2].disable();
		grid.getTopToolbar().items.items[4].disable();
		grid.getTopToolbar().items.items[6].disable();
		grid.getTopToolbar().items.items[8].disable();
		grid.getStore().rejectChanges();
	});
	
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[4].enable();
			grid.getTopToolbar().items.items[6].enable();
			grid.getTopToolbar().items.items[8].enable();
		
			var loamAmt = grid.getSelectionModel().getSelected().get('loamAmt');
			if(loamAmt=='0.00'){
				grid.getTopToolbar().items.items[2].enable();
			}else{
				grid.getTopToolbar().items.items[2].disable();
			}
		}
	});
	
	riskStore.on('beforeload', function() {
		Ext.apply(this.baseParams,{
			start : 0,
			mchtCd : queryForm.getForm().findField('mchtCd').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})