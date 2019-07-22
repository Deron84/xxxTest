Ext.onReady(function() {
	// 可选机构数据集
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});

	/************************************系统参数信息*******************************************/
	var sysParamStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=settleDocInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'brhId',mapping: 'BRH_ID'},
			{name: 'exchangeNo',mapping: 'EXCHANGE_NO'},
			{name: 'payerName',mapping: 'PAYER_NAME'},
			{name: 'payerAcctNo',mapping: 'PAYER_ACCOUNT_NO'},
			{name: 'payBankNo',mapping: 'PAY_BANK_NO'},
			{name: 'inBankSettleNo',mapping: 'IN_BANK_SETTLE_NO'},
			{name: 'settleBusNo',mapping: 'SETTLE_BUS_NO'},
			{name: 'innerAcct',mapping: 'INNER_ACCT'},
			{name: 'innerAcct1',mapping: 'INNER_ACCT1'},
			{name: 'innerAcct2',mapping: 'INNER_ACCT2'}
		])
	});
	
	sysParamStore.load({
		params:{start: 0}
	});
	
	var paramColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'brhId',header: '机构编号',dataIndex: 'brhId',width:60,sortable: true},
		{header: '交换号',dataIndex: 'exchangeNo',width:50,
		 editor: new Ext.form.TextField({
			allowBlank: false,
		 	maxLength: 6,
			maxLengthText: '交换号最多可以输入6个数字',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		 })},
		 {id: 'payerName',header: '付款人全称',dataIndex: 'payerName',width:150,
		 editor: new Ext.form.TextField({
		 	maxLength: 100,
			vtype: 'isOverMax'
		 })},
		 {header: '付款人账号',dataIndex: 'payerAcctNo',width:120,
		 editor: new Ext.form.TextField({
		 	maxLength: 20,
			maxLengthText: '付款人账号最多可以输入20个数字',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		 })},
		 {header: '付款人开户银行',dataIndex: 'payBankNo',width:120,
		 editor: new Ext.form.TextField({
		 	maxLength: 100,
			vtype: 'isOverMax'
		 })},
		 {header: '行内自动清算单位编号',dataIndex: 'inBankSettleNo',width:130,
		 editor: new Ext.form.TextField({
		 	maxLength: 10,
			maxLengthText: '行内自动清算单位编号最多可以输入10个数字',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		 })},
		 {header: '行内自动清算业务种类编号',dataIndex: 'settleBusNo',width:160,
		 editor: new Ext.form.TextField({
		 	maxLength: 10,
			maxLengthText: '行内自动清算业务种类编号最多可以输入10个数字',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		 })},
		 {header: '内部账户号',dataIndex: 'innerAcct',width:120,
			 editor: new Ext.form.TextField({
		 	maxLength: 28,
			maxLengthText: '内部账户号最多可以输入28个数字',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		 })},
		 {header: '总分行内部清分账户1',dataIndex: 'innerAcct1',width:130,
			 editor: new Ext.form.TextField({
		 	maxLength: 28,
			maxLengthText: '总分行内部清分账户1最多可以输入28个数字',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		 })},
		 {header: '总分行内部清分账户2',dataIndex: 'innerAcct2',width:130,
			 editor: new Ext.form.TextField({
		 	maxLength: 28,
			maxLengthText: '总分行内部清分账户2最多可以输入28个数字',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		 })}
	]);
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			paramWin.show();
			paramWin.center();
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
				
				showConfirm('确定要删除该条记录吗？',grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10102Action.asp?method=delete',
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
								brhId: rec.get('brhId'),
								txnId: '10102',
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
			var modifiedRecords = grid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			var store = grid.getStore();
			var len = store.getCount();
			for(var i = 0; i < len; i++) {
				var record = store.getAt(i);
			}		
			//存放要修改的参数信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					id : record.get('brhId'),
					exchangeNo : record.get('exchangeNo'),
					payerName : record.get('payerName'),
					payerAccountNo : record.get('payerAcctNo'),
					payBankNo : record.get('payBankNo'),
					inBankSettleNo : record.get('inBankSettleNo'),
					settleBusNo : record.get('settleBusNo'),
					innerAcct : record.get('innerAcct'),
					innerAcct1 : record.get('innerAcct1'),
					innerAcct2 : record.get('innerAcct2')
				};
				array.push(data);
			}
//			showProcessMsg('正在保存信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10102Action.asp?method=update',
				method: 'post',
				params: {
					parameterList : Ext.encode(array),
					txnId: '10102',
					subTxnId: '03'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					grid.enable();
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
			grid.getTopToolbar().items.items[2].disable();
		}
	};
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);
	
	// 系统参数列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '清算凭证信息',
		iconCls: 'T10102',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: sysParamStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: paramColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载清算凭证信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: sysParamStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[4].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			//激活菜单
			grid.getTopToolbar().items.items[2].enable();
		}
	});
	// 可选机构下拉列表
	var brhCombo = new Ext.form.ComboBox({
		store: brhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择机构',
		mode: 'local',
		triggerAction: 'all',
		width: 200,
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个机构',
		fieldLabel: '机构号*',
		hiddenName: 'brhId'
	});
	//添加系统参数表单
	var paramInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
//		width: 300,
		defaultType: 'textfield',
		labelWidth: 150,
		waitMsgTarget: true,
		items: [
//		        {
//			fieldLabel: '机构号*',
//			id: 'brhId',
//			name: 'brhId',
//			width: 200,
//			maxLength: 20,
//			allowBlank: false,
//			blankText: '不能为空',
//			emptyText: '不能为空',
//			vtype: 'isOverMax'
//		},
		brhCombo,{
			fieldLabel: '交换号*',
			id: 'exchangeNo',
			name: 'exchangeNo',
			width: 200,
			maxLength: 6,
			allowBlank: false,
			emptyText: '请输入交换号',
			maxLengthText: '交换号最多可以输入6个数字',
			vtype: 'isOverMax',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '付款人全称',
			id: 'payerName',
			name: 'payerName',
			width: 200,
			maxLength: 100,
			vtype: 'isOverMax'
		},{
			fieldLabel: '付款人账号',
			id: 'payerAcctNo',
			name: 'payerAcctNo',
			width: 200,
			maxLength: 20,
			maxLengthText: '付款人账号最多可以输入20个数字',
			maskRe:/^[0-9]$/
		},{
			fieldLabel: '付款人开户银行',
			id: 'payBankNo',
			name: 'payBankNo',
			width: 200,
			maxLength: 100,
			vtype: 'isOverMax'
		},{
			fieldLabel: '行内自动清算单位编号',
			id: 'inBankSettleNo',
			name: 'inBankSettleNo',
			width:200,
			maxLength: 10,
			maxLengthText: '行内自动清算单位编号最多可以输入10个数字',
			maskRe:/^[0-9]$/
		},{
			fieldLabel: '行内自动清算业务种类编号',
			id: 'settleBusNo',
			name: 'settleBusNo',
			width: 200,
			maxLength: 10,
			maxLengthText: '行内自动清算业务种类编号最多可以输入10个数字',
			maskRe:/^[0-9]$/
		},{
			fieldLabel: '内部账户号',
			id: 'innerAcct',
			name: 'innerAcct',
			width: 200,
			maxLength: 28,
			maxLengthText: '内部账户号最多可以输入28个数字',
			maskRe:/^[0-9]$/
		},{
			fieldLabel: '总分行内部清分账户1',
			id: 'innerAcct1',
			name: 'innerAcct1',
			width: 200,
			maxLength: 28,
			maxLengthText: '总分行内部清分账户1最多可以输入28个数字',
			maskRe:/^[0-9]$/
		},{
			fieldLabel: '总分行内部清分账户2',
			id: 'innerAcct2',
			name: 'innerAcct2',
			width: 200,
			maxLength: 28,
			maxLengthText: '总分行内部清分账户2最多可以输入28个数字',
			maskRe:/^[0-9]$/
		}]
	});
	
	//参数信息添加窗口
	var paramWin = new Ext.Window({
		title: '清算凭证添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [paramInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(paramInfoForm.getForm().isValid()) {
					paramInfoForm.getForm().submit({
						url: 'T10102Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,paramInfoForm);
							//重置表单
							paramInfoForm.getForm().reset();
							//重新加载参数列表
							grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,paramInfoForm);
						},
						params: {
							txnId: '10102',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				paramInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				paramWin.hide(grid);
			}
		}]
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});