Ext.onReady(function() {
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=railToolMaintains'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'whseName',mapping: 'whseName'},
			{name: 'toolCode',mapping: 'toolCode'},
			{name: 'rfid',mapping: 'rfid'},
			{name: 'location',mapping: 'location'},
			{name: 'toolName',mapping: 'toolName'},
			{name: 'toolMaterial',mapping: 'toolMaterial'},
			{name: 'examPeriod',mapping: 'examPeriod'},
			{name: 'addDate',mapping: 'addDate'},
			{name: 'maintainUser',mapping: 'maintainUser'},
			{name: 'maintainDate',mapping: 'maintainDate'}
		])
	});
	
	gridStore.load();
	
	 //仓库名称、工具名称、工具标签、存放位置（架层位）、入库时间、保养周期、保养人、保养时间
	//【新增、新增增加之后要修改工具表最后保养时间】
	//whseName,toolName,rfid,location,addDate,examPeriod,maintainUser,maintainDate
	//工具编码、工具标签、工具分类、工具名称、工具型号、材质、入库时间、维修人、维修时间
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '仓库名称',dataIndex: 'whseName',sortable: true,width: 120},
		{header: '工具名称',dataIndex: 'toolName',sortable: true,width: 120},
		{header: '工具标签',dataIndex: 'rfid',sortable: true,width: 200},	
		{header: '存放位置（架层位）',dataIndex: 'location',/*renderer: brhLvlRender,*/sortable: true,width: 150},
		{header: '入库时间',dataIndex: 'addDate',sortable: true,width: 150/*,renderer: formatDt*/},
		{header: '保养周期（天）',dataIndex: 'examPeriod',sortable: true,width: 100/*,renderer: formatDt*/},
		{header: '保养人',dataIndex: 'maintainUser',sortable: true,/*renderer: brhLvlRender,*/width: 100,},
		{header: '保养时间',dataIndex: 'maintainDate',sortable: true,/*renderer: brhLvlRender,*/width: 150,},
	]);

	var addMenu = {
		text: '新增工具保养',
		width: 85,
		iconCls: 'add',
		handler:function() {
			brhWin.show();
			brhWin.center();
//			Ext.getCmp('ensure1').show().enable();
//			Ext.getCmp('ensure2').hide().disable();
//			Ext.getCmp('reset1').show().enable();
//			Ext.getCmp('brhId1').enable();
//			Ext.getCmp('brhLvl1').enable();
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
									SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
										upBrhStore.loadData(Ext.decode(ret));
									});
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
					var whseCode = queryForm.findById('whseCodeS').getValue();
					var dateStart =  typeof(queryForm.findById('dateStart').getValue()) == 'string' ? '' : queryForm.findById('dateStart').getValue().dateFormat('Y-m-d');
			        var dateEnd = typeof(queryForm.findById('dateEnd').getValue()) == 'string' ? '' : queryForm.findById('dateEnd').getValue().dateFormat('Y-m-d');
			        var param = "?a=1";
					param = param + "&txnId=excel&subTxnId=110801"//日志用
					if(dateStart){
						param = param + "&dateStart="+dateStart;
					}
					if(dateEnd){
						param = param + "&dateEnd="+dateEnd;
					}
					if(whseCode){
						param = param + "&whseCode="+whseCode;
					}
					window.location.href = Ext.contextPath +"/exportExcelT1108.asp"+param;
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
	menuArr.push(addMenu);
//	menuArr.push('-');
//	menuArr.push(delMenu);
//	menuArr.push('-');
//	menuArr.push(upMenu);	
	menuArr.push('-');
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
			xtype: 'datefield',
			width: 300,
			labelStyle: 'padding-left: 5px',
			id: 'dateStart',
			name: 'dateStart',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'dateEnd',
			fieldLabel: '开始日期',
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
			fieldLabel: '结束日期',
			editable: false
		}]
	});

	//机构列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '工具保养维护',
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
			grid.getTopToolbar().items.items[1].enable();
			grid.getTopToolbar().items.items[2].enable();
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
        	xtype: 'dynamicCombo',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '选择工具',
			methodName: 'getToolCode',
			hiddenName: 'toolCode',
			blankText: '请选择工具',
			emptyText: "--请选择工具--",
			allowBlank: false,
			editable: false,
			width:300
    	},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '保养人*',
			width:300,
			id: 'maintainUser',
			name: 'maintainUser'
        }]
	});
	
	//机构添加窗口
	var brhWin = new Ext.Window({
		title: '新增工具保养',
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
				var frm = brhInfoForm.getForm();
				if(brhInfoForm.getForm().isValid()) {
					brhInfoForm.getForm().submit({
						url: 'T110801Action_add.asp',
						waitMsg: '正在提交，请稍后......',
						success : function(form, action) {
							hasSub = false;
							showSuccessAlert("操作成功",brhInfoForm);
							btn.enable();
							frm.reset();
							grid.getStore().reload();
						},
						failure : function(form,action) {
							console.log(action);
							var code = action.result.code;
							if(code==200){
								hasSub = false;
								showSuccessAlert("操作成功",brhInfoForm);
								frm.reset();;
								brhWin.hide(grid);
								grid.getStore().reload();
							}else{
								showErrorMsg("操作失败，请稍后重试",brhInfoForm);
							}
						},
						params: {
							txnId: '1108',
							subTxnId: '01'
							
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
			whseCode: queryForm.findById('whseCodeS').getValue(),	
			dateStart: queryForm.findById('dateStart').getValue(),
			dateEnd: queryForm.findById('dateEnd').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});