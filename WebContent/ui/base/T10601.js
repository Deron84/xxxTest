Ext.onReady(function() {

	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=iPosInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'oprId'
		},[
			{name: 'posMch',mapping: 'pos_mch'},
			{name: 'mchNumber',mapping: 'mch_number'},
			{name: 'posStage',mapping: 'pos_stage'},
			{name: 'outmchNumber',mapping: 'outmch_number'},
			{name: 'inmchNumber',mapping: 'inmch_number'},
			{name: 'feeCode',mapping: 'fee_code'}
		])
	});
	
	gridStore.load({
		params:{start: 0}
	});
	
	var colModel = new Ext.grid.ColumnModel([
	    {header: '商户编号',dataIndex: 'posMch',width: 150},
	    {header: '终端号',dataIndex: 'mchNumber',width: 80
//	    	,editor: new Ext.form.TextField({
//			 	allowBlank: false,
//				blankText: '不能为空',
//				maxLength: 32,
//				vtype: 'isOverMax'
//			 })
	    },
	    {header: '分期期数',dataIndex: 'posStage',width: 80
//	    	,editor: new Ext.form.TextField({
//			 	allowBlank: false,
//				blankText: '不能为空',
//				maxLength: 32,
//				vtype: 'isOverMax'
//			 })
	    },
		{header: '外部商品代码',dataIndex: 'outmchNumber',width: 80
//	    	,editor: new Ext.form.TextField({
//				allowBlank: false,
//				blankText: '不能为空',
//				maxLength: 32,
//				vtype: 'isOverMax'
//			 })
	    },
	    {header: '内部商品代码',dataIndex: 'inmchNumber',width: 80
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
							url: 'T10601Action.asp?method=delete',
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
								posMch: rec.get('posMch'),
								txnId: '10601',
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
//						mchNumber: record.get('mchNumber'),
//						posStage: record.get('posStage'),
//						outmchNumber: record.get('outmchNumber'),
//						inmchNumber: record.get('inmchNumber'),
//						feeCode : record.get('feeCode')
//				};
//				array.push(data);
//			}
//			
//			Ext.Ajax.request({
//				url: 'T10601Action.asp?method=update',
//				params: {
//					infoList: Ext.encode(array),
//					txnId: '10601',
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
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	//menuArr.push(upMenu);
	
	// 操作员信息列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '云SHOP类商品种类维护',
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
			id: 'posMch'
		},{
			xtype: 'textnotnull',
			fieldLabel: '终端号*',
			maxLength: 32,
			vtype: 'isOverMax',
			width:160,
			id: 'mchNumber'
		},{
			xtype: 'textnotnull',
			fieldLabel: '分期期数*',
			maxLength: 32,
			vtype: 'isOverMax',
			width:160,
			id: 'posStage'
		},{
			xtype: 'textnotnull',
			fieldLabel: '外部商品代码*',
			maxLength: 32,
			vtype: 'isOverMax',
			width:160,
			id: 'outmchNumber'
		},{
			xtype: 'textnotnull',
			fieldLabel: '内部商品代码*',
			maxLength: 32,
			vtype: 'isOverMax',
			width:160,
			id: 'inmchNumber'
		},{
			xtype: 'textnotnull',
			fieldLabel: '费率代码*',
			maxLength: 4,
			vtype: 'isOverMax',
			width:160,
			id: 'feeCode'
		}]
	});
	
	//新增Win
	var addWin = new Ext.Window({
		title: '云SHOP类商品种类维护',
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
						url: 'T10601Action.asp?method=add',
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
							txnId: '10601',
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
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});