Ext.onReady(function() {
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=railToolInfofix'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'whseName',mapping: 'whseName'},
			{name: 'toolName',mapping: 'toolName'},
			{name: 'rfid',mapping: 'rfid'},
			{name: 'toolType',mapping: 'toolType'},
			{name: 'toolMaterial',mapping: 'toolMaterial'},
			{name: 'toolUnit',mapping: 'toolUnit'},
			{name: 'location',mapping: 'location'},
			{name: 'examPeriod',mapping: 'examPeriod'},
			{name: 'lastExam',mapping: 'lastExam'},
			{name: 'toolExpiration',mapping: 'toolExpiration'},
			{name: 'purchaseDept',mapping: 'purchaseDept'},
			{name: 'purchaseUser',mapping: 'purchaseUser'},
			{name: 'mfrsOrg',mapping: 'mfrsOrg'},
			{name: 'addDate',mapping: 'addDate'},
			{name: 'toolStatus',mapping: 'toolStatus'},
			{name: 'inWhse',mapping: 'inWhse'},
			{name: 'note2',mapping: 'note2'},
			{name: 'toolStatus',mapping: 'toolStatus'}
		])
	});
	
	gridStore.load();
	
	function brhLvlRender(brhLvl) {
		switch(brhLvl) {
			case '0': return '总行';
			case '1': return '分行';
			case '2': return '支行';
			case '3':return '网点';
		}
	}
	
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '仓库名称',dataIndex: 'whseName',sortable: true,width: 90},
		{header: '工具名称',dataIndex: 'toolName',sortable: true,width: 90},
		{header: '工具标签',dataIndex: 'rfid',sortable: true,width: 90},	
		{header: '工具类型',dataIndex: 'toolType',/*renderer: brhLvlRender,*/sortable: true,width: 90},
		{header: '材质',dataIndex: 'toolMaterial',/*renderer: brhLvlRender,*/sortable: true,width: 90},
		{header: '单位',dataIndex: 'toolUnit',/*renderer: brhLvlRender,*/sortable: true,width: 90},
		{header: '存放位置（架层位）',dataIndex: 'location',/*renderer: brhLvlRender,*/sortable: true,width: 120},
		{header: '保养周期(天)',dataIndex: 'examPeriod',sortable: true,width: 110},
		{header: '最后保养时间',dataIndex: 'lastExam',sortable: true,width: 150,id:'lastExam'},
		{header: '有效期',dataIndex: 'toolExpiration',sortable: true,width: 150},
		{header: '入库时间',dataIndex: 'addDate',sortable: true,width: 150},
		{header: '工具状态',dataIndex: 'toolStatus',sortable: true,width: 90},
		{header: '在库状态',dataIndex: 'inWhse',sortable: true,width: 90},
		{header: '工具型号',dataIndex: 'note2',/*renderer: brhLvlRender,*/sortable: true,width: 90},
		{header: '采购部门',dataIndex: 'purchaseDept',sortable: true,width: 150},
		{header: '采购人',dataIndex: 'purchaseUser',sortable: true,width: 150},
		{header: '供应商',dataIndex: 'mfrsOrg',sortable: true,width: 150}
		]);

	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			brhWin.show();
			brhWin.center();
			Ext.getCmp('ensure1').show().enable();
			Ext.getCmp('ensure2').hide().disable();
			Ext.getCmp('reset1').show().enable();
			Ext.getCmp('brhId1').enable();
			Ext.getCmp('brhLvl1').enable();
		}
	};
	
	var delMenu = {
		text: '报废',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				var brhId = rec.get('brhId');		
				
				showConfirm('确认报废选中工具？',grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10101Action.asp?method=delete',
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
								brhId: brhId,
								txnId: '10101',
								subTxnId: '02'
							}
						}
						);
					}
				});
			}
		}
	};
	
	var upMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler: function() {
			var	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            Ext.Ajax.request({
				url : 'T110101Action_get.asp',
				params : {
					id : selectedRecord.id
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.code == 200) {
						brhWin.show();
						brhWin.center();
						console.log(Ext.getCmp('toolCode'));
			            Ext.getCmp('toolCode').setValue(rspObj.info.toolCode);
			            //Ext.getCmp('rfid').setValue(rspObj.info.rfid);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
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
//				queryForm.getForm().reset();
			}
		};
	
	var excelDowload = {
			text: '导出',
			width: 60,
			id: 'query1',
			iconCls: 'download',
			handler:function() {
				showConfirm('确认导出？',grid,function(bt) {
					if(bt == "yes") {
						
					}
				});
			}
	};
	var exportMenu = {
			text: '导出',
			width: 100,
			id:'download',
			iconCls: 'download',
			handler:function() {
				//Ext.MessageBox.alert('提示', '你点了导出报表按钮!');
				excelDown.show();
			}
		};
		var excelQueryForm = new Ext.form.FormPanel({
			frame: true,
			border: true,
			width: 520,
			autoHeight: true,
			iconCls: 'T50902',
			buttonAlign: 'center',
			buttons: [{
				text: '确认导出',
				iconCls: 'download',
				handler: function() {
					
					if(!excelQueryForm.getForm().isValid()) {
						return;
					}
					var  whseCode = queryForm.findById('whseCodeS').getValue();
					var param = "?a=1";
					param = param + "&txnId=excel&subTxnId=110101"//日志用
					var toolName =  queryForm.findById('toolNameS').getValue();
					if(toolName){
						param = param + "&toolName="+toolName;
					}
					if(whseCode){
						param = param + "&whseCode="+whseCode;
					}
					
					window.location.href = Ext.contextPath +"/T11010x_excel.asp"+param;
					excelDown.hide();
				}
			},{
				text: '取消导出',
				iconCls: 'refuse',
				handler: function() {
					excelDown.hide();
				}
			}]
		});
		var excelDown = new Ext.Window({
			title: '导出',
			layout: 'fit',
			width: 350,
			autoHeight: true,
			items: [excelQueryForm],
			buttonAlign: 'center',
			closeAction: 'hide',
			resizable: false,
			closable: true,
			tools: [{
				id: 'minimize',
				handler: function(event,toolEl,panel,tc) {
					panel.tools.maximize.show();
					toolEl.hide();
					excelDown.collapse();
					excelDown.getEl().pause(1);
					excelDown.setPosition(10,Ext.getBody().getViewSize().height - 30);
				},
				qtip: '最小化',
				hidden: false
			},{
				id: 'maximize',
				handler: function(event,toolEl,panel,tc) {
					panel.tools.minimize.show();
					toolEl.hide();
					excelDown.expand();
					excelDown.center();
				},
				qtip: '恢复',
				hidden: true
			}]
		});
	var menuArr = new Array();
//	menuArr.push(addMenu);
//	menuArr.push('-');
//	menuArr.push(delMenu);
//	menuArr.push('-');
//	menuArr.push(upMenu);	
	//menuArr.push('-');
	menuArr.push(queryCondition);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 490,
		autoHeight: true,
		items: [{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'dynamicCombo',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '仓库编码',
				methodName: 'getRailWhse',
				hiddenName: 'whseCode',
				id: 'whseCodeS',
				blankText: '请选择仓库',
				emptyText: "--请选择仓库--",
				allowBlank: true,
				editable: false,
				width:300
        	}]
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			id: 'toolNameS',
			name: 'toolName',
			maxLength: 40,
			width: 300,
			fieldLabel: '工具名称'
		}]
	});

	//机构列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '工具信息查询',
		iconCls: 'T110105',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
//		autoExpandColumn:'brhAddr',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '加载中......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
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
	
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
//		grid.getTopToolbar().items.items[4].disable();
//		grid.getTopToolbar().items.items[2].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//单击行，使删除按钮可用
		'rowclick': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		//	grid.getTopToolbar().items.items[1].enable();
		//	grid.getTopToolbar().items.items[2].enable();
//			grid.getTopToolbar().items.items[3].enable();
//			grid.getTopToolbar().items.items[4].enable();
		}
//		//在编辑单元格后使保存按钮可用
//		'afteredit': function(e) {
//			if(grid.getTopToolbar().items.items[4] != undefined) {
//				grid.getTopToolbar().items.items[4].enable();
//			}
//		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[0].enable();
//			grid.getTopToolbar().items.items[2].enable();
//			grid.getTopToolbar().items.items[4].enable();
			
			var brhId = grid.getSelectionModel().getSelected().data.brhId;
		
		}
	});
	
	//机构添加表单
	var brhInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '标签射频卡值*',
			allowBlank: false,
			emptyText: '请输入标签射频卡值',
			id: 'rfid',
			name: 'rfid',
			width: 300
//			maxLength: 4,
//			maxLengthText: '行内机构编码最多可以输入4个数字',
//			vtype: 'isNumber',
//			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '工具编码1*',
			allowBlank: false,
			emptyText: '请输入工具编码',
			id: 'toolCode',
			name: 'toolCode',
			width: 300
//			maxLength: 4,
//			maxLengthText: '行内机构编码最多可以输入4个数字',
//			vtype: 'isNumber',
//			blankText: '该输入项只能包含数字'
		}]
	});
	
	//机构添加窗口
	var brhWin = new Ext.Window({
		title: '机构维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [brhInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			id : 'ensure1',
			text: '确定',
			handler: function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
				if(brhInfoForm.getForm().isValid()) {
					brhInfoForm.getForm().submit({
						url: 'T110101Action_update.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							brhWin.hide();
							showSuccessMsg(action.result.msg,brhInfoForm);
							brhInfoForm.getForm().reset();
							grid.getStore().reload();
							SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
								upBrhStore.loadData(Ext.decode(ret));
							});
//							grid.getTopToolbar().items.items[4].disable();
//							grid.getTopToolbar().items.items[2].disable();
						},
						failure: function(form,action) {
							if(action.result.code ==200){
								brhWin.hide();
								showSuccessMsg(action.result.msg,brhInfoForm);
								brhInfoForm.getForm().reset();
								grid.getStore().reload();
							}else{
								showErrorMsg(action.result.msg,brhInfoForm);
							}
						},
						params: {
							id : selectedRecord.id,
							rfid : Ext.getCmp('rfid').getValue()
						}
					});
				}
			}
		},{
			id : 'reset1',
			text: '重置',
			handler: function() {
				brhInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				brhWin.hide(grid);
				brhInfoForm.getForm().reset();
			}
		}]
	});
	/************************************************以下是机构相关操作员信息************************************************************/
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});
	
	

	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			toolName: queryForm.findById('toolNameS').getValue(),
			whseCode: queryForm.findById('whseCodeS').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});