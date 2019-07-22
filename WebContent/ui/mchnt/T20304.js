Ext.onReady(function() {	
	
	//交易类型
	var txnNumStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('TXN_NUM_LIMIT',function(ret){
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
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtFeeInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtCd',mapping: 'mchtCd'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'dayNum',mapping: 'dayNum'},
			{name: 'dayAmt',mapping: 'dayAmt'},
			{name: 'daySingle',mapping: 'daySingle'},
			{name: 'monNum',mapping: 'monNum'},
			{name: 'monAmt',mapping: 'monAmt'}
		])
	});
	
	mchntStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
    		new Ext.grid.RowNumberer(),
    		{id: 'mchtCd',header: '商户编号',dataIndex: 'mchtCd',sortable: true},
    		{id: 'mchtNm',header: '商户名称',dataIndex: 'mchtNm',sortable: true},
    		{header: '交易名称',dataIndex: 'txnNum',width: 100,renderer:function(data){
		    	if(null == data) return '';
		    	var record = txnNumStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }},
    		{header: '卡类型',dataIndex: 'cardType',width: 60,renderer: cardType},
    		{header: '单日累计交易笔数',dataIndex: 'dayNum',width: 110,
    		 	editor: new Ext.form.TextField({
    				allowBlank: false,
    				maxLength: 5,
					maskRe: /^[0-9]+$/
    		 	})},
    		 {header: '单日累计交易金额',dataIndex: 'dayAmt',width: 110,
        		 editor: new Ext.form.TextField({
        		 	maxLength: 22,
        		 	maskRe: /^[0-9\\.]+$/,
        			allowBlank: false,
        			vtype: 'isMoney'
        		 })},
    		 {header: '单日单笔交易金额',dataIndex: 'daySingle',width: 110,
        		 editor: new Ext.form.TextField({
        		 	maxLength: 22,
        		 	maskRe: /^[0-9\\.]+$/,
        			allowBlank: false,
        			vtype: 'isMoney'
        		 })},
    		 {header: '单月累计交易笔数',dataIndex: 'monNum',width: 110,
        		 editor: new Ext.form.TextField({
        		 	maxLength: 6,
        			allowBlank: false,
					maskRe: /^[0-9]+$/
        		 })},
    		 {header: '单月累计交易金额',dataIndex: 'monAmt',width: 110,
        		 editor: new Ext.form.TextField({
        		 	maxLength: 22,
        		 	maskRe: /^[0-9\\.]+$/,
        			allowBlank: false,
        			vtype: 'isMoney'
        	})}
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
		
	/******************************限额信息添加******************************/
	
	var txnNumCombo = new Ext.form.ComboBox({
		store: txnNumStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择交易类型',
		mode: 'local',
		width: 400,
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个交易类型',
		fieldLabel: '交易类型*',
		id: 'txnNumId',
		hiddenName: 'txnNum'
	});
	
	var cardTypeCombo = new Ext.form.ComboBox({
		store: cardTypeStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择卡类型',
		mode: 'local',
		width: 400,
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个卡类型',
		fieldLabel: '卡类型*',
		hiddenName: 'cardType'
	});
	
	
	var addMenu = {
		text: '商户限额新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			oprWin.show();
			oprWin.center();
		}
	};
	
	var oprInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 500,
		defaultType: 'textfield',
		labelWidth: 140,
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号*',
			hiddenName: 'mchtCd',
			id: 'idMchtCd',
			allowBlank: false,
			editable: true,
			width: 400
    	},txnNumCombo,
		cardTypeCombo,
		{
			fieldLabel: '单日累计交易笔数*',
			allowBlank: false,
			blankText: '单日累计交易笔数不能为空',
			emptyText: '请输入单日累计交易笔数',
			maxLength: 5,
			maskRe: /^[0-9]+$/,
			vtype: 'isOverMax',
			id: 'dayNum',
			name: 'dayNum',
			vtype: 'alphanum',
			width: 400
		},
		{
			fieldLabel: '单日累计交易金额*',
			allowBlank: false,
			blankText: '单日累计交易金额不能为空',
			emptyText: '请输入单日累计交易金额',
			maxLength: 22,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'dayAmt',
			name: 'dayAmt',
			vtype: 'isMoney',
			width: 400
		},
		{
			fieldLabel: '单日单笔交易金额*',
			allowBlank: false,
			blankText: '单日单笔交易金额不能为空',
			emptyText: '请输入单日单笔交易金额',
			maxLength: 22,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'daySingle',
			name: 'daySingle',
			vtype: 'isMoney',
			width: 400
		},
		{
			fieldLabel: '单月累计交易笔数*',
			allowBlank: false,
			blankText: '单月累计交易笔数不能为空',
			emptyText: '请输入单月累计交易笔数',
			maxLength: 6,
			maskRe: /^[0-9]+$/,
			vtype: 'isOverMax',
			id: 'monNum',
			name: 'monNum',
			vtype: 'alphanum',
			width: 400
		},
		{
			fieldLabel: '单月累计交易金额*',
			allowBlank: false,
			blankText: '单月累计交易金额不能为空',
			emptyText: '请输入单月累计交易金额',
			maxLength: 22,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'monAmt',
			name: 'monAmt',
			vtype: 'isMoney',
			width: 400
		}]
	});
	
	var oprWin = new Ext.Window({
		title: '商户限额添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
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
                    var dayNum = oprInfoForm.getForm().findField('dayNum').getValue();
                    var monNum = oprInfoForm.getForm().findField('monNum').getValue();
                    if(Number(monNum) < Number(dayNum))
                    {
                        showErrorMsg("[单月累计交易笔数]不能小于[单日累计交易笔数]",oprInfoForm);
                        return;
                    }
                    var daySingle = oprInfoForm.getForm().findField('daySingle').getValue();
                    var dayAmt = oprInfoForm.getForm().findField('dayAmt').getValue();
                    var monAmt = oprInfoForm.getForm().findField('monAmt').getValue();
                    if(Number(dayAmt) < Number(daySingle))
                    {
                        showErrorMsg("[单日累计交易金额]不能小于[单日单笔交易金额]",oprInfoForm);
                        return;
                    }
                    if(Number(monAmt) < Number(dayAmt))
                    {
                        showErrorMsg("[单月累计交易金额]不能小于[单日累计交易金额]",oprInfoForm);
                        return;
                    }
                    if(Number(monNum)==0||Number(dayNum)==0||Number(daySingle)==0||Number(dayAmt)==0||Number(monAmt)==0){
                    	showErrorMsg("商户限额的交易笔数和金额不能为0",oprInfoForm);
                        return;
                    }
                    var txn = oprInfoForm.getForm().findField('txnNum').getValue();
                    var card = oprInfoForm.getForm().findField('cardType').getValue();
                    if(txn == '1501' || txn == 'E786'){
                    	//01 借记卡
                    	if(!(card == '01')){
                    		showErrorMsg("T+0的交易只能配置为借记卡",oprInfoForm);
                        	return;
                    	}
                    }
					oprInfoForm.getForm().submitNeedAuthorise({
						url: 'T20304Action.asp?method=add',
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
							txnId: '20304',
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
				oprWin.hide(oprColModel);
			}
		}]
	});
	
	
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: true,
			handler: function() {
				if(oprGrid.getSelectionModel().hasSelection()) {
					var rec = oprGrid.getSelectionModel().getSelected();
					
					showConfirm('确定要删除该条商户限额信息吗？',oprGrid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T20304Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,oprGrid);
										oprGrid.getStore().reload();
										oprGrid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,oprGrid);
									}
								},
								params: { 
									mchtCd: rec.get('mchtCd'),
									txnNum: rec.get('txnNum'),
									cardType: rec.get('cardType'),
									txnId: '20304',
									subTxnId: '02'
								}
							});
						}
					});
				}
			}
		};
		var upMenu = {
			text: '保存',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				var modifiedRecords = oprGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						mchtCd : record.get('mchtCd'),
						txnNum: record.get('txnNum'),
						cardType: record.get('cardType'),
						dayNum: record.get('dayNum'),
						dayAmt: record.get('dayAmt'),
						daySingle: record.get('daySingle'),
						monNum: record.get('monNum'),
						monAmt: record.get('monAmt')
					};
                    var dayNum = record.get('dayNum');
                    var monNum = record.get('monNum');
                    var daySingle = record.get('daySingle');
                    var dayAmt = record.get('dayAmt');
                    var monAmt = record.get('monAmt');
                    if(Number(dayNum)==0||Number(monNum)==0||Number(daySingle)==0||Number(dayAmt)==0||Number(monAmt)==0){
                    	showErrorMsg("商户限额的交易笔数和金额不能为0",oprInfoForm);
                        return;
                    }
                    
                    if(Number(monNum) < Number(dayNum))
                    {
                        showErrorMsg("[单月累计交易笔数]不能小于[单日累计交易笔数]",oprInfoForm);
                        return;
                    }
                    if(Number(dayAmt) < Number(daySingle))
                    {
                        showErrorMsg("[单日累计交易金额]不能小于[单日单笔交易金额]",oprInfoForm);
                        return;
                    }
                    if(Number(monAmt) < Number(dayAmt))
                    {
                        showErrorMsg("[单月累计交易金额]不能小于[单日累计交易金额]",oprInfoForm);
                        return;
                    }
                    var txn = record.get('txnNum');
                    var card = record.get('cardType');
                    if(txn == '1501' || txn == 'E786'){
                    	//01 借记卡
                    	if(!(card == '01')){
                    		showErrorMsg("T+0的交易只能配置为借记卡",oprInfoForm);
                        	return;
                    	}
                    }
					array.push(data);
				}
				Ext.Ajax.requestNeedAuthorise({
					url: 'T20304Action.asp?method=update',
					method: 'post',
					params: {
						cstMchtFeeList: Ext.encode(array),
						txnId: '20304',
						subTxnId: '03'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							oprGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,oprGrid);
						} else {
							oprGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,oprGrid);
						}
						oprGrid.getTopToolbar().items.items[4].disable();
						oprGrid.getStore().reload();
						hideProcessMsg();
					}
				});
			}
		};
	
	mchntStore.on('beforeload',function(){
		this.rejectChanges();
	})
	
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
	menuArr.push(addMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(delMenu);		// [2]
	menuArr.push('-');			// [3]
	menuArr.push(upMenu);		// [4]	
	menuArr.push('-');  //[5]
	menuArr.push(queryCondition);  //[6]
	menuArr.push('-');  
	menuArr.push('单位金额(人民币)：元');  

	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '商户限额信息',
		region: 'center',
		iconCls: 'cityCode',
		autoExpandColumn: 'mchtNm',
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
		loadMask: {
			msg: '正在加载商户限额信息列表......'
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
		oprGrid.getTopToolbar().items.items[2].disable();
		oprGrid.getTopToolbar().items.items[4].disable();
		oprGrid.getStore().rejectChanges();
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
		oprGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		autoHeight: true,
		items: [{
			xtype: 'dynamicCombo',
			labelStyle: 'padding-left: 5px',
			methodName: 'getMchntId',
			fieldLabel: '商户编号*',
			hiddenName: 'mchtCd',
			id: 'mchtCdId',
			allowBlank: true,
			editable: true,
			width: 400
    	}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 560,
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
			mchntId: queryForm.findById('mchtCdId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
});