Ext.onReady(function() {
	//0工具编码、1工具标签、2工具分类、3工具名称、4工具型号、5材质、6入库时间、7审核结果、8、审核状态、9审核人、10、审核时间 -->
	//toolCode,rfid,toolType,toolName,toolModel,toolMaterial,addDate,verifyMsg,infoSign,verifyUser,verifyDate]]>
	//toolCode,rfid,toolType,toolName,toolModel,toolMaterial,addDate,verifyMsg,infoSign,verifyUser,verifyDate]]>
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=railToolRepair'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'whseName',mapping: 'whseName'},
			{name: 'toolName',mapping: 'toolName'},
			{name: 'rfid',mapping: 'rfid'},
			{name: 'location',mapping: 'location'},
			{name: 'applyMsg',mapping: 'applyMsg'},
			{name: 'applyUser',mapping: 'applyUser'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'verifyMsg',mapping: 'verifyMsg'},
			{name: 'infoSign',mapping: 'infoSign'},
			{name: 'verifyUser',mapping: 'verifyUser'},
			{name: 'verifyDate',mapping: 'verifyDate'}
		])
	});
	
	gridStore.load();
	//0工具编码、1工具标签、2工具分类、3工具名称、4工具型号、5材质、6入库时间、7审核结果、8、审核状态、9审核人、10、审核时间 -->
		//toolCode,rfid,toolType,toolName,toolModel,toolMaterial,addDate,verifyMsg,infoSign,verifyUser,verifyDate]]>
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '仓库名称',dataIndex: 'whseName',sortable: true,width: 90},
		{header: '工具名称',dataIndex: 'toolName',sortable: true,width: 90},
		{header: '工具标签',dataIndex: 'rfid',sortable: true,width: 150},	
		{header: '存放位置（架层位）',sortable: true,dataIndex: 'location',/*renderer: brhLvlRender,*/width: 120},
		{header: '维修信息',dataIndex: 'applyMsg',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '申请人',dataIndex: 'applyUser',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '申请时间',dataIndex: 'applyDate',sortable: true,width: 150/*,renderer: formatDt*/},
		{header: '审核信息',dataIndex: 'verifyMsg',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '审核状态',dataIndex: 'infoSign',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '审核人',dataIndex: 'verifyUser',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '审核时间 ',dataIndex: 'verifyDate',sortable: true,width: 150/*,renderer: formatDt*/}
	]);

	var addMenuapplay = {
		text: '维修申请',
		width: 85,
		iconCls: 'add',
		handler:function() {
			brhWinAll.show();
			brhWinAll.center();
//			Ext.getCmp('ensure1').show().enable();
//			Ext.getCmp('reset1').show().enable();
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
					var whseCode = queryForm.findById('whseCodeS').getValue();
					var dateStart =  typeof(queryForm.findById('dateStart').getValue()) == 'string' ? '' : queryForm.findById('dateStart').getValue().dateFormat('Y-m-d');
			        var dateEnd = typeof(queryForm.findById('dateEnd').getValue()) == 'string' ? '' : queryForm.findById('dateEnd').getValue().dateFormat('Y-m-d');
			        var infoSign = queryForm.findById('infoSign').getValue();
					if(infoSign){
						infoSign = infoSign.inputValue;
					}
					var param = "?a=1";
					param = param + "&txnId=excel&subTxnId=110402"//日志用
					
					if(dateStart){
						param = param + "&dateStart="+dateStart;
					}
					if(infoSign){
						param = param + "&infoSign="+infoSign;
					}
					if(dateEnd){
						param = param + "&dateEnd="+dateEnd;
					}
					if(whseCode){
						param = param + "&whseCode="+whseCode;
					}
					window.location.href = Ext.contextPath +"/exportExcelT1104.asp"+param;
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
	menuArr.push(addMenuapplay);
//	menuArr.push('-');
//	menuArr.push(addMenuCompelete);
	menuArr.push('-');
//	menuArr.push(upMenu);	
	//menuArr.push('-');
	menuArr.push(queryCondition);
	menuArr.push('-');
	menuArr.push(exportMenu);
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 530,
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
			xtype: 'datefield',
			width: 300,
			labelStyle: 'padding-left: 5px',
			id: 'dateStart',
			name: 'dateStart',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'dateEnd',
			fieldLabel: '开始(申请时间)',
			editable: false
		},{
			xtype: 'datefield',
			width: 300,
			labelStyle: 'padding-left: 5px',
			id: 'dateEnd',
			name: 'dateEnd',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'dateStart',
			fieldLabel: '结束(申请时间)',
			editable: false
		},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'radiogroup',
       	            width:400,
       	            fieldLabel: '审核状态',
       	            id:'infoSign',
       	            labelStyle: 'padding-left: 5px',
       	            vertical: true,
       	            items: [{
						boxLabel: '不限', 
						name: 'infoSign', 
						inputValue: -1, 
						checked: true
					},{//0未审核;1审核通过；2审核不通过
						boxLabel: '未审核', 
						name: 'infoSign', 
						inputValue: 0
					},{
						boxLabel: '审核通过', 
						name: 'infoSign', 
						inputValue: 1
					},{
						boxLabel: '审核不通过', 
						name: 'infoSign', 
						inputValue: 2
					},{
						boxLabel: '维修完成', 
						name: 'infoSign', 
						inputValue: 3
					}]
			}]
		}]
	});

	//机构列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '工具维修管理',
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
		width: 580,
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
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//单击行，使删除按钮可用
		'rowclick': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			grid.getTopToolbar().items.items[1].enable();
			grid.getTopToolbar().items.items[2].enable();
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[2].enable();
		
		}
	});
	var brhInfoFormAll = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		fileUpload: true,
		items: [{
        	xtype: 'dynamicCombo',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '选择工具',
			methodName: 'getToolCode1',
			hiddenName: 'toolCode',
			blankText: '请选择工具',
			emptyText: "--请选择工具--",
			allowBlank: false,
			editable: false,
			width:300
    	},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '申请人*',
			width:300,
			id: 'applyUser',
			name: 'applyUser'
		},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '申请事由*',
			width:300,
			id: 'applyMsg',
			name: 'applyMsg'
		}]
	});
	
	var brhWinAll = new Ext.Window({
		title: '新增维修申请',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [brhInfoFormAll],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			id : 'ensure1',
			text: '确定',
			handler: function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
				if(brhInfoFormAll.getForm().isValid()) {
					//var submitValues = brhInfoFormAll.getForm().getValues();  
					brhInfoFormAll.getForm().submit({
						url: 'T110401Action_add.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
						},
						failure: function(form,action) {
							if(action.result.code ==200){
								brhWinAll.hide();
								showSuccessMsg(action.result.msg,brhInfoFormAll);
								brhInfoFormAll.getForm().reset();
								grid.getStore().reload();
							}else{
								showErrorMsg(action.result.msg,brhInfoFormAll);
							}
						},
						params: {
							txnId: '1104',
							subTxnId: '03',
							applyType:1//applyType1为维修 2维修完成，
							
						}
					});
				}
			}
		},{
			id : 'reset1',
			text: '重置',
			handler: function() {
				brhInfoFormAll.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				brhWinAll.hide(grid);
				brhInfoFormAll.getForm().reset();
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
		var infoSign = queryForm.findById('infoSign').getValue();
		if(infoSign){
			infoSign = infoSign.inputValue;
		}
		Ext.apply(this.baseParams, {
			start: 0,
			whseCode: queryForm.findById('whseCodeS').getValue(),	
			dateStart: queryForm.findById('dateStart').getValue(),
			dateEnd: queryForm.findById('dateEnd').getValue(),
			infoSign: infoSign
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});