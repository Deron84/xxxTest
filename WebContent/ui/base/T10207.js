Ext.onReady(function() {	

	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	//上级营业网点编号
	SelectOptionsDWR.getComboData('BRH_ID_NEW_MAIN',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	var brhTlrInfoStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhTlrInfo01'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'brhId',mapping: 'brhId'},
			{name: 'tlrId',mapping: 'tlrId'},
		//	{name: 'tlrSta',mapping: 'tlrSta'},
			{name: 'resv1',mapping: 'resv1'},
			{name: 'lastUpdOprId',mapping: 'lastUpdOprId'},
			{name: 'recUpdTs',mapping: 'recUpdTs'},
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		]),
		autoLoad: true
	});
	
	brhTlrInfoStore.load({
		params:{start: 0}
	});
	
	
	var oprColModel = new Ext.grid.ColumnModel([
    		{id: 'brhId',header: '机构编号',dataIndex: 'brhId',sortable: true,width: 100},
    		{header: '虚拟柜员号',dataIndex: 'tlrId',id:'tlrId',width: 100},
    	//	{header: '状态是否正常',dataIndex: 'tlrSta'},
    		{header: '最后更新操作员',dataIndex: 'lastUpdOprId',width: 200},    		
    		{header: '记录更新时间',dataIndex: 'recUpdTs',width: 200,renderer: formatDt},
    		{header: '记录创建时间',dataIndex: 'recCrtTs',width: 200,renderer: formatDt}
    	]);
		
	/*****************************虚拟柜员信息添加******************************/
	// 可选机构下拉列表
	var brhStoreCombo = new Ext.form.ComboBox({
//		store: brhStore,
		xtype: 'combo',
//		displayField: 'displayField',
//		valueField: 'valueField',
		labelStyle: 'padding-left: 5px',
		emptyText: '请选择机构号',
		store: new Ext.data.ArrayStore({
			fields: ['valueField','displayField'],
			data: [['9901','9901-神思电子']],
			reader: new Ext.data.ArrayReader()
		}),
//		mode: 'local',
//		triggerAction: 'all',
//		forceSelection: true,
//		typeAhead: true,
//		selectOnFocus: true,
//		editable: false,
		allowBlank: false,
		blankText: '请选择一个机构',
		fieldLabel: '机构号*',
		id: 'brhIdId',
		hiddenName: 'brhId',
		anchor: '90%',
		width: 250
	});
	
	
	var addMenu = {
		text: '虚拟柜员新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			oprWin.show();
			oprWin.center();
		}
	};
	
	// 地区码表单
	var tlrInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 80,
		waitMsgTarget: true,
		items: [brhStoreCombo,{
			fieldLabel: '虚拟柜员号*',
			id: 'tlrId',
			name: 'tlrId',
			width: 250,
			allowBlank: false,
			maxLength: 8,
			emptyText: '请输入虚拟柜员号',
			maskRe:/^[0-9]$/,
			regex:/^[0-9]{1,8}$/,
			regexText:'虚拟柜员号最多可以输入8个数字',
			blankText: '该输入项只能包含数字'
		}]
	});
	
	// 操作员添加窗口
	var oprWin = new Ext.Window({
		title: '虚拟柜员添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [tlrInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(tlrInfoForm.getForm().isValid()) {
					tlrInfoForm.getForm().submitNeedAuthorise({
						url: 'T10207Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,tlrInfoForm);
							tlrInfoForm.getForm().reset();
							oprGrid.getStore().reload();
							oprWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,tlrInfoForm);
						},
						params: {
							txnId: '10207',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				tlrInfoForm.getForm().reset();
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
					showConfirm('确定要删除该条操作员信息吗？',oprGrid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise ({
								url: 'T10207Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,oprGrid);
										oprGrid.getTopToolbar().items.items[2].disable();										
										oprGrid.getStore().reload();
									} else {
										showErrorMsg(rspObj.msg,oprGrid);
									}
								},
								params: { 
									brhId: rec.get('brhId'),
									tlrId: rec.get('tlrId'),									
									txnId: '10207',
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
				showProcessMsg('正在保存操作员信息，请稍后......');
				//存放要修改的机构信息
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						brhId : record.get('brhId'),
						tlrId: record.get('tlrId'),
					//	tlrSta : record.get('tlrSta'),
						resv1 : record.get('resv1')
					};
					array.push(data);
				}
				Ext.Ajax.request({
					url: 'T10207Action.asp?method=update',
					method: 'post',
					params: {
					tblBrhTlrInfoList: Ext.encode(array),
						txnId: '10207',
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
	
	var queryCondition = {
			text: '录入查询条件',
			width: 50,
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
//	menuArr.push('-');			// [3]
//	menuArr.push(upMenu);		// [4]	
//	menuArr.push('-');  //[3]
//	menuArr.push(queryCondition);  //[4]

	
	// 操作员信息列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '虚拟柜员维护',
		iconCls: 'T10207',
		region: 'center',
		autoExpandColumn:'tlrId',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: brhTlrInfoStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载操作员信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: brhTlrInfoStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[2].disable();
//		oprGrid.getTopToolbar().items.items[4].disable();
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
		width: 300,
		autoHeight: true,
		labelWidth: 80,
		items: [{
        	xtype: 'combo',
			fieldLabel: '机构编号',
			labelStyle: 'padding-left: 5px',
			store: brhStore,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			lazyRender: true,
			width: 230,
			blankText: '请选择机构编号',
			id: 'searchbrhId',
			hiddenName: 'searchbrh'
    	}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 350,
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
				brhTlrInfoStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	brhTlrInfoStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			brhId: queryForm.findById('searchbrhId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
	
});