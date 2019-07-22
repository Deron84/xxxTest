Ext.onReady(function() {

	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=divInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'oprId'
		},[
			{name: 'mchtId',mapping: 'mcht_id'},
			//{name: 'mchNumber',mapping: 'mch_number'},
			{name: 'divNo',mapping: 'div_no'},
			{name: 'productCode',mapping: 'product_code'},
			{name: 'inproductCode',mapping: 'inproduct_code'},
			{name: 'feeCode',mapping: 'fee_code'}
		])
	});
	gridStore.load({
		params:{start: 0}
	});
	var colModel = new Ext.grid.ColumnModel([
	    {header: '商户编号',dataIndex: 'mchtId',width: 150},
//	    {header: 'mch_number',dataIndex: 'mchNumber',width: 80,
//	    	editor: new Ext.form.TextField({
//			 	allowBlank: false,
//				blankText: '不能为空',
//				maxLength: 32,
//				vtype: 'isOverMax'
//			 })},
	    {header: '分期期数',dataIndex: 'divNo',width: 80
//	    	,editor: new Ext.form.TextField({
//			 	allowBlank: false,
//				blankText: '不能为空',
//				maxLength: 32,
//				vtype: 'isOverMax'
//			 })
	    },
		{header: '外部商品代码',dataIndex: 'productCode',width: 80
//	    	,editor: new Ext.form.TextField({
//				allowBlank: false,
//				blankText: '不能为空',
//				maxLength: 32,
//				vtype: 'isOverMax'
//			 })
	    },
	    {header: '内部商品代码',dataIndex: 'inproductCode',width: 80
//	    	,editor: new Ext.form.TextField({
//			 	allowBlank: false,
//				blankText: '不能为空',
//				maxLength: 32,
//				vtype: 'isOverMax'
//			 })
	    },
	    {header: '费率代码',dataIndex: 'feeCode',width: 80
//	    	,editor: new Ext.form.TextField({
//			 	allowBlank: false,
//				blankText: '不能为空',
//				maxLength: 4,
//				vtype: 'isOverMax'
//			 })
	    }
	    ]);

	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			addWin.show();
			addWin.center();
		}
	};
	var detaiInfoMenu={
	    text: '录入查询条件',
		width: 85,
		iconCls: 'query',
		handler:function() {
		queryWin.show();
	}
	};
	var feeCodeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});

	SelectOptionsDWR.getComboData('FEE_CODE',function(ret){
		feeCodeStore.loadData(Ext.decode(ret));
	});
	var feeCodeCombo = new Ext.form.ComboBox({
		store: feeCodeStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择费率代码',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个费率代码',
		fieldLabel: '费率代码',
		id: 'feeCodeEdit',
		name: 'feeCodeEdit',
		hiddenName: 'editFeeCode'
	});
	var queryForm = new Ext.form.FormPanel({
		frame:true,
		border:true,
		width:300,
		height:100,
		items:[feeCodeCombo]
	});
	var queryWin=new Ext.Window({
		title:'查询条件',
		layout:'fit',
		width:300,
		autoHeight:true,
		items:[queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools:[{
			id:'minimize',
			handler:function(event,toolEl,panel,tc){
			panel.tools.maximize.show();
			toolEl.hide();
			queryWin.collapse();
			queryWin.getEl().pause(1);
			queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
		},
			qtip:'最小化',
			hidden:false
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
			   gridStore.load();
			   queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			feeCode: queryForm.findById('feeCodeEdit').getValue()
		});
	});
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				showConfirm('确定要删除该条信息吗？',grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T10701Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								mchtId: rec.get('mchtId'),
								divNo: rec.get('divNo'),
								productCode: rec.get('productCode'),
								txnId: '10701',
								subTxnId: '03'
							}
						});
					}
				});
			}
		}
	};
	
//	var upMenu = {
//		text: '保存',
//		width: 85,
//		iconCls: 'reload',
//		disabled: true,
//		handler: function() {
//			var modifiedRecords = grid.getStore().getModifiedRecords();
//			if(modifiedRecords.length == 0) {
//				return;
//			}
//			var array = new Array();
//			for(var index = 0; index < modifiedRecords.length; index++) {
//				var record = modifiedRecords[index];
//				var data = {
//						
//						posMch : record.get('posMch'),
//						//mchNumber: record.get('mchNumber'),
//						posStage: record.get('posStage'),
//						outmchNumber: record.get('outmchNumber'),
//						inmchNumber: record.get('inmchNumber'),
//						feeCode : record.get('feeCode')
//				};
//				array.push(data);
//			}
//			
//			Ext.Ajax.request({
//				url: 'T10701Action.asp?method=update',
//				params: {
//					infoList: Ext.encode(array),
//					txnId: '10701',
//					subTxnId: '02'
//				},
//				success: function(rsp,opt) {
//					var rspObj = Ext.decode(rsp.responseText);
//					if(rspObj.success) {
//						grid.getStore().commitChanges();
//						showSuccessMsg(rspObj.msg,grid);
//					} else {
//						grid.getStore().rejectChanges();
//						showErrorMsg(rspObj.msg,grid);
//					}
//					grid.getTopToolbar().items.items[4].disable();
//					grid.getStore().reload();
//					hideProcessMsg();
//				}
//			});
//		}
//	};
	
//	var queryCondition = {
//			text: '录入查询条件',
//			width: 85,
//			id: 'query',
//			iconCls: 'query',
//			handler:function() {
//				queryWin.show();
//			}
//		};
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(detaiInfoMenu);
	menuArr.push('-');
	//menuArr.push(upMenu);
	
	// 操作员信息列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '普通分期商品种类维护',
		iconCls: 'T105',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: colModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	
	grid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			grid.getTopToolbar().items.items[4].enable();
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[2].enable();
		}
	});	
	
	//信息添加表单
	var infoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 330,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			xtype: 'textnotnull',
			fieldLabel: '商户编号*',
			maxLength: 15,
			vtype: 'isOverMax',
			width:160,
			id: 'mchtId'
		},{
			xtype: 'textnotnull',
			//fieldLabel: 'mch_number*',
			fieldLabel: '分期期数*',
			maxLength: 32,
			vtype: 'isOverMax',
			width:160,
			//id: 'mchNumber '
			id: 'divNo'
		},{
			xtype: 'textnotnull',
			fieldLabel: '外部商品代码*',
			maxLength: 32,
			vtype: 'isOverMax',
			width:160,
			id: 'productCode'
		},{
			xtype: 'textnotnull',
			fieldLabel: '内部商品代码*',
			maxLength: 32,
			vtype: 'isOverMax',
			width:160,
			id: 'inproductCode'
		},{
			xtype: 'combo',
			store:feeCodeStore,
			mode:'local',
			displayField: 'displayField',
			valueField: 'valueField',
			fieldLabel: '费率代码*',
			width:160,
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: true,
			lazyRender: true,
			width: 150,
			blankText: '请选择费率代码',
			id: 'feeCode',
			hiddenName: 'feeCodeId'
		}]
	});
	
	//新增Win
	var addWin = new Ext.Window({
		title: '普通分期商品种类维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 500,
		autoHeight: true,
		layout: 'fit',
		items: [infoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(infoForm.getForm().isValid()) {
					infoForm.getForm().submitNeedAuthorise({
						url: 'T10701Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,infoForm);
							infoForm.getForm().reset();
							grid.getStore().reload();
							addWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,infoForm);
							addWin.hide();
						},
						params: {
							txnId: '10701',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				infoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addWin.hide(grid);
			}
		}]
	});
	
//	gridStore.on('beforeload', function(){
//		Ext.apply(this.baseParams, {
//			
//		});
//	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});