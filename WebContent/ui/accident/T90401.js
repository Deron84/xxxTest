Ext.onReady(function() {

	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=costInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'no',mapping: 'no'},
			{name: 'state',mapping: 'state'},
			{name: 'amount',mapping: 'amount'},
			{name: 'cardId',mapping: 'card_id'},
			{name: 'mchtNo',mapping: 'mcht_no'},
			{name: 'remarkInf',mapping: 'remark_inf'}
		])
	});
	
	gridStore.load({
		params:{start: 0}
	});
	
	var colModel = new Ext.grid.ColumnModel([
	    {header: '收费流水号',dataIndex: 'no',width: 120},
	    {header: '费用金额',dataIndex: 'amount',width: 120,
	    	editor: new Ext.form.TextField({
			 	allowBlank: false,
				blankText: '不能为空',
				maxLength: 15,
				vtype: 'isOverMax'
			 })},
	    {header: '账户号(卡号)',dataIndex: 'cardId',width: 120,
	    	editor: new Ext.form.TextField({
			 	allowBlank: false,
				blankText: '不能为空',
				maxLength: 40,
				vtype: 'isOverMax'
			 })},
	    {header: '商户号',dataIndex: 'mchtNo',width: 240,
			editor: new Ext.ux2.dynamicCombo({
				xtype: 'dynamicCombo',
				fieldLabel: '商户编号*',
				methodName: 'getMchntIdfor90401',
				hiddenName: 'mchtNo',
				allowBlank: false,
				editable: true
			})},
		{header: '备注信息',dataIndex: 'remarkInf',id:'remarkId',
			 editor: new Ext.form.TextField({
				maxLength: 512,
				vtype: 'isOverMax'
			})}
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
							url: 'T90401Action.asp?method=del',
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
								no: rec.get('no'),
								txnId: '90401',
								subTxnId: '03'
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
			var modifiedRecords = grid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
						no:record.get('no'),
						amount:record.get('amount'),
						cardId:record.get('cardId'),
						mchtNo:record.get('mchtNo'),
						remarkInf:record.get('remarkInf')
				};
				array.push(data);
			}
			
			Ext.Ajax.request({
				url: 'T90401Action.asp?method=upd',
				params: {
					infoList: Ext.encode(array),
					txnId: '90401',
					subTxnId: '02'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						grid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,grid);
					} else {
						grid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,grid);
					}
					grid.getTopToolbar().items.items[4].disable();
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
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
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);

	var grid = new Ext.grid.EditorGridPanel({
		title: '费用收取信息维护',
		iconCls: 'T904',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		autoExpandColumn: 'remarkId',
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
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号*',
			methodName: 'getMchntIdfor90401',
			hiddenName: 'mchtNo',
			allowBlank: false,
			editable: true,
			width: 240
		},{
			xtype: 'textnotnull',
			fieldLabel: '费用金额*',
			maxLength: 15,
			vtype: 'isOverMax',
			width:160,
			id: 'amount'
		},{
			xtype: 'textnotnull',
			fieldLabel: '账号*',
			maxLength: 40,
			vtype: 'isOverMax',
			width:160,
			id: 'cardId '
		},{
        	xtype: 'textarea',
        	width:160,
        	height: 120,
        	maxLength: 512,
			fieldLabel: '情况备注',
			vtype: 'isOverMax',
			id: 'remarkInf'
    	}]
	});
	
	//新增Win
	var addWin = new Ext.Window({
		title: '费用信息新增',
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
						url: 'T90401Action.asp?method=add',
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
							txnId: '90401',
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