Ext.onReady(function() {
	
	// 0工具编码、1工具标签、2工具分类、3工具名称、4工具型号、
	// * 5材质、6入库时间、7工具状态、8、有效期、9、出库状态、10、最后一次检修时间 -->
	//toolCode,rfid,toolType,toolName,toolModel,toolMaterial,addDate,toolStatus,toolExpiration,inWhse,lastExam
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=railToolInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'toolCode',mapping: 'toolCode'},
			{name: 'rfid',mapping: 'rfid'},
			{name: 'toolType',mapping: 'toolType'},
			{name: 'toolName',mapping: 'toolName'},
			{name: 'toolModel',mapping: 'toolModel'},
			{name: 'toolMaterial',mapping: 'toolMaterial'},
			{name: 'addDate',mapping: 'addDate'},
			{name: 'toolStatus',mapping: 'toolStatus'},
			{name: 'toolExpiration',mapping: 'toolExpiration'},
			{name: 'inWhse',mapping: 'inWhse'},
			{name: 'lastExam',mapping: 'lastExam'}
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
	// 0工具编码、1工具标签、2工具分类、3工具名称、4工具型号、
		// * 5材质、6入库时间、7工具状态、8、有效期、9、出库状态、10、最后一次检修时间 -->
		//toolCode,rfid,toolType,toolName,toolModel,toolMaterial,addDate,toolStatus,toolExpiration,inWhse,lastExam
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'brhId',header: '工具编码',dataIndex: 'toolCode',sortable: true,width: 90},
		{header: '工具标签',dataIndex: 'rfid',sortable: true,width: 150},	
		{header: '工具类型',dataIndex: 'toolType',/*renderer: brhLvlRender,*/sortable: true,width: 90,},
		{header: '工具名称',dataIndex: 'toolName',/*renderer: brhLvlRender,*/sortable: true,width: 90,},
		{header: '工具型号',dataIndex: 'toolModel',/*renderer: brhLvlRender,*/sortable: true,width: 90,},
		{header: '材质',dataIndex: 'toolMaterial',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '入库时间',dataIndex: 'addDate',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '工具状态',dataIndex: 'toolStatus',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '有效期',dataIndex: 'toolExpiration',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '出库状态',dataIndex: 'inWhse',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '最后一次检修时间  ',dataIndex: 'lastExam',sortable: true,width: 80/*,renderer: formatDt*/}
	]);

	var addMenu = {
		text: '报废',
		width: 85,
		iconCls: 'delete',
		handler:function() {
			brhWin.show();
			brhWin.center();
			Ext.getCmp('ensure1').show().enable();
//			Ext.getCmp('ensure2').hide().disable();
			Ext.getCmp('reset1').show().enable();
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
//					var  dateStart =  queryForm.findById('dateStart').getValue();
//					var  dateEnd = queryForm.findById('dateEnd').getValue();
					var dateStart =  typeof(queryForm.findById('dateStart').getValue()) == 'string' ? '' : queryForm.findById('dateStart').getValue().dateFormat('Y-m-d');
			        var dateEnd = typeof(queryForm.findById('dateEnd').getValue()) == 'string' ? '' : queryForm.findById('dateEnd').getValue().dateFormat('Y-m-d');
			        var toolStatus =  queryForm.findById('toolStatus').getValue();
					if(toolStatus){
						toolStatus = toolStatus.inputValue
					}
					
					var param = "?a=1";
					param = param + "&txnId=excel&subTxnId=110101"//日志用
					if(dateStart){
						param = param + "&dateStart="+dateStart;
					}
					if(whseCode){
						param = param + "&whseCode="+whseCode;
					}
					if(dateEnd){
						param = param + "&dateEnd="+dateEnd;
					}
					if(toolStatus){
						param = param + "&toolStatus="+toolStatus;
					}
					window.location.href = Ext.contextPath +"/exportExcelT1102.asp"+param;
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
	//menuArr.push(addMenu);
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
		},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'radiogroup',
       	            width:300,
       	            fieldLabel: '工具状态',//0正常；1维护；3报废；4停用
       	            id:'toolStatus',
       	            labelStyle: 'padding-left: 5px',
       	            vertical: true,
       	            items: [{
						boxLabel: '正常', 
						name: 'toolStatus', 
						inputValue: 0, 
						checked: true
					},{
						boxLabel: '维护', 
						name: 'toolStatus', 
						inputValue: 1
					},{
						boxLabel: '报废', 
						name: 'toolStatus', 
						inputValue: 3
					},{
						boxLabel: '停用', 
						name: 'toolStatus', 
						inputValue: 4
					}]
			}]
		}]
	});

	//机构列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '工具还库',
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
			//grid.getTopToolbar().items.items[1].enable();
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
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
   	            xtype: 'radiogroup',
   	            width:300,
   	            fieldLabel: '审核状态*',
   	            labelStyle: 'padding-left: 5px',
   	            vertical: true,
   	            items: [{//0未审核;1审核通过；2审核不通过
					boxLabel: '未审核', 
					name: 'infoSign', 
					inputValue: 0, 
					checked: true
				},{
					boxLabel: '审核通过', 
					name: 'infoSign', 
					inputValue: 1
				},{
					boxLabel: '审核不通过', 
					name: 'infoSign', 
					inputValue: 2
				}]
   	        }]
		},{
			fieldLabel: '审核内容*',
			allowBlank: false,
			emptyText: '请输入审核内容',
			id: 'verifyMsg',
			name: 'verifyMsg',
			width: 300
//			maxLength: 4,
//			maxLengthText: '行内机构编码最多可以输入4个数字',
//			vtype: 'isNumber',
//			blankText: '该输入项只能包含数字'
		}]
	});
	
	//机构添加窗口
	var brhWin = new Ext.Window({
		title: '报废',
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
						url: 'T110601Action_update.asp',
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
							id : selectedRecord.id
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
			dateEnd: queryForm.findById('dateEnd').getValue(),
			toolStatus: queryForm.findById('toolStatus').getValue().inputValue
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});