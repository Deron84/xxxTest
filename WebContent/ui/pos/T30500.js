Ext.onReady(function() {
	
	// 菜品编码
	var vegeStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=vegCodeInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'vageCode'
		},[
			{name: 'vageCode',mapping: 'VEGE_CODE'},
			{name: 'vegeName',mapping: 'VEGE_NAME'}
		]),
		autoLoad :true
	}); 
	
	var vegeColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
		{id: 'vageCode',header: '菜品编码',dataIndex: 'vageCode',width: 175,align:'center'},
		{header: '菜品名称',dataIndex: 'vegeName',width: 175,align:'center',
			editor: new Ext.form.TextField({
			 	allowBlank: false,
				blankText: '菜品名称不能为空',
				emptyText: '请输入菜品名称',
				maxLength: 16,
				maxLengthText: '菜品名称最多可以输入16个汉字'
		 })}
	]);

	//菜品编码添加表单
	var vegInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '菜品编码*',
			allowBlank: false,
			emptyText: '请输入菜品编号',
			id: 'vegCode',
			name: 'vegCode',
			width: 300,
			maxLength: 32,
			maxLengthText: '菜品编号最多可以输入32个数字',
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '菜品名称*',
			allowBlank: false,
			emptyText: '请输入菜品名称',
			id: 'vegName',
			name: 'vegName',
			width: 300,
			maxLength: 32,
			maxLengthText: '菜品最多可以输入16个汉字',
			blankText: '该输入项输入菜品名称'
		}]
	});
	
	// 添加菜品编码窗口
	var vegWin = new Ext.Window({
		title: '菜品编码添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [vegInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(vegInfoForm.getForm().isValid()) {
					var submitValues = vegInfoForm.getForm().getValues();  
					for (var param in submitValues) {  
						if (vegInfoForm.getForm().findField(param) && vegInfoForm.getForm().findField(param).emptyText == submitValues[param]) {  
							vegInfoForm.getForm().findField(param).setValue(' ');  
						}  
					}
					var vegCode= vegInfoForm.getForm().findField('vegCode').getValue();
	            	if(vegCode =='' || vegCode == null){
	            		showErrorMsg("菜品编码不能为空",grid);
	    				return;
	            	}
	            	var vegName= vegInfoForm.getForm().findField('vegName').getValue();
	            	if(vegName =='' || vegName == null){
	            		showErrorMsg("菜品名称不能为空",grid);
	    				return;
	            	}
	            	var reg=/^\S+$/;
					if(!reg.test(vegName)){
						showAlertMsg('菜品名称不能包含空字符',grid);
						return;
					}
					vegInfoForm.getForm().submit({
						url: 'T30500Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,vegInfoForm);
							vegInfoForm.getForm().reset();
							grid.getStore().reload();
//							SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
//								upBrhStore.loadData(Ext.decode(ret));
//							});
							vegWin.hide(grid);
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,vegInfoForm);
						},
						params: {
							txnId: '30500',
							subTxnId: '00'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				vegInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				vegWin.hide(grid);
			}
		}]
	});
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '菜品编码',
	//		allowBlank: false,
			emptyText: '请输入菜品编号',
			id: 'vegCode2',
			name: 'vegCode2',
			width: 300,
			maxLength: 32,
			maxLengthText: '菜品编号最多可以输入32个数字',
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '菜品名称',
	//		allowBlank: false,
			emptyText: '请输入菜品名称',
			id: 'vegName2',
			name: 'vegName2',
			width: 300,
			maxLength: 32,
			maxLengthText: '菜品最多可以输入16个汉字',
			blankText: '该输入项输入菜品名称'
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
				vegeStore.load({
					params:{
						start: 0,
						vegCode: queryForm.getForm().findField('vegCode2').getValue(),
						vegName: queryForm.getForm().findField('vegName2').getValue()
					}
				});
				queryWin.hide(grid);
				queryForm.getForm().reset();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			vegWin.show();
			vegWin.center();
			vegWin.getForm().reset();
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
				var _vageCode = rec.get('vageCode');
				showConfirm('确定要删除该此条记录吗？菜品编号：' + _vageCode,grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T30500Action.asp?method=delete',
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
								vegCode: _vageCode,
								txnId: '30500',
								subTxnId: '01'
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
				
				if(record.get('vegeName')==""){
					showAlertMsg('菜品名称不能为空',grid);
					grid.getSelectionModel().selectRow(i);
					return false;
				}
			}
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				//校验机构名称
				var name=record.get('vegeName');
				var reg=/^\S+$/;
				if(!reg.test(name)){
					showAlertMsg('菜品名称不能包含空字符',grid);
					return false;
				}
				var data = {
					vegCode : record.get('vageCode'),
					vegName: record.get('vegeName'),
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T30500Action.asp?method=update',
				method: 'post',
				params: {
					vegDataList : Ext.encode(array),
					txnId: '30500',
					subTxnId: '02'
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
			//		hideProcessMsg();
				}
			});
		}
	}
	
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
	menuArr.push(addMenu);     //增加
	menuArr.push('-');
	menuArr.push(delMenu);      //删除
	menuArr.push('-');
	menuArr.push(upMenu);		//修改
	menuArr.push('-');
	menuArr.push(queryCondition);     //查询
	
	// 交易查询
	var grid = new Ext.grid.EditorGridPanel({
		title: '菜品编码',
		iconCls: 'T501',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: vegeStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: vegeColModel,
		clicksToEdit: true,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载联机交易列表......'
		},
		tbar: menuArr,
		bbar:  new Ext.PagingToolbar({
			store: vegeStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[4].disable();
		grid.getStore().rejectChanges();
	});
	vegeStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
        	start: 0,
			vegCode: queryForm.getForm().findField('vegCode2').getValue(),
			vegName: queryForm.getForm().findField('vegName2').getValue()
        });
    }); 
	grid.on({
		//单击行，使删除按钮可用
		'rowclick': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[2].enable();
		},
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});