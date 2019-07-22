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
	
	//仓库名称、工具名称、工具标签、存放位置（架层位）、维修信息、申请人、申请时间、审核状态、审核人、审核时间、审核信息
		//toolCode,rfid,toolType,toolName,toolModel,toolMaterial,addDate,verifyMsg,infoSign,verifyUser,verifyDate]]>
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '仓库名称',dataIndex: 'whseName',sortable: true,width: 90},
		{header: '工具名称',dataIndex: 'toolName',sortable: true,width: 90},
		{header: '工具标签',dataIndex: 'rfid',sortable: true,width: 150},	
		{header: '存放位置（架层位）',dataIndex: 'location',sortable: true,/*renderer: brhLvlRender,*/width: 120},
		{header: '维修信息',dataIndex: 'applyMsg',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '申请人',dataIndex: 'applyUser',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '申请时间',dataIndex: 'applyDate',sortable: true,width: 150/*,renderer: formatDt*/},
		{header: '审核信息',dataIndex: 'verifyMsg',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '审核状态',dataIndex: 'infoSign',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '审核人',dataIndex: 'verifyUser',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '审核时间 ',dataIndex: 'verifyDate',sortable: true,width: 150/*,renderer: formatDt*/}
	]);

	var addMenu = {
		text: '审核',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			
			var	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            if(selectedRecord.data.infoSign!='未审核'){
                showAlertMsg("请选择未审核的维修申请",brhInfoForm);
				return;
			}
            Ext.Ajax.request({
				url : 'T110401Action_get.asp',
				params : {
					id : selectedRecord.id
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.code == 200) {
						brhWin.show();
						brhWin.center();
						Ext.getCmp('infoSign1').setValue(rspObj.info.infoSign);
			            Ext.getCmp('verifyMsg').setValue(rspObj.info.verifyMsg);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				}
			});
			
//			Ext.getCmp('ensure1').show().enable();
////			Ext.getCmp('ensure2').hide().disable();
//			Ext.getCmp('reset1').show().enable();
////			Ext.getCmp('brhId1').enable();
////			Ext.getCmp('brhLvl1').enable();
		}
	};
	var addMenuCompelete1 = {
			text: '维修完成确认',
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
	            console.log(selectedRecord);
	            if(selectedRecord.data.infoSign!='审核通过'){
					showErrorMsg("请选择审核通过的维修申请",brhInfoForm);
					return;
				}
	            
	            
	            showConfirm('确认维修完成？',grid,function(bt) {
					if(bt == "yes") {
						alert('5555555555')
					}
				});
	            
	            
//	            
//	            brhWin2.show();
//			    brhWin2.center();
			}
		};
	
	
	
	
	
	var addMenuCompelete= {
			text: '维修完成确认',
			width: 85,
			iconCls: 'edit',
			disabled : true,
			handler : function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
	            if(selectedRecord == null)
	            {
	                showAlertMsg("没有选择记录",grid);
	                return;
	            }
	            console.log(selectedRecord);
	            if(selectedRecord.data.infoSign!='审核通过'){
					showErrorMsg("请选择审核通过的维修申请",brhInfoForm);
					return;
				}
	            showConfirm('确认维修完成？',grid,function(bt) {
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T110104Action_updateComplete.asp',
								waitMsg: '正在提交，请稍后......',
								success: function(rsp,opt) {
									 grid.getStore().reload();
								},
								params: { 
									txnId: '1104',
									subTxnId: '04',
									id : selectedRecord.id
								}
							});
						}
					});
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
			            Ext.getCmp('toolCode').setValue(rspObj.info.toolCode);
			            Ext.getCmp('infoSign').setValue(rspObj.info.infoSign);
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
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(addMenuCompelete);
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
		width: 580,
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
       	            width:350,
       	            fieldLabel: '审核状态*',
       	            id:'infoSign',
       	            labelStyle: 'padding-left: 5px',
       	            vertical: true,
       	            items: [{//0未审核;1审核通过；2审核不通过
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
					}]
       	        }]
		}]
	});

	//机构列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '工具维修审核',
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
		grid.getTopToolbar().items.items[0].disable();
		grid.getTopToolbar().items.items[1].disable();
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
			grid.getTopToolbar().items.items[2].enable();
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
	            xtype: 'radiogroup',
	            width:300,
	            fieldLabel: '审核状态*',
	            id:'infoSign1',
	            labelStyle: 'padding-left: 5px',
	            vertical: true,
	            items: [/*{//0未审核;1审核通过；2审核不通过
				boxLabel: '未审核', 
				name: 'infoSign', 
				inputValue: 0, 
				checked: true
			},*/{
				boxLabel: '审核通过', 
				name: 'infoSign', 
				inputValue: 1
			},{
				boxLabel: '审核不通过', 
				name: 'infoSign', 
				inputValue: 2
			}]
	        },{
			fieldLabel: '审核内容',
			allowBlank: true,
			labelStyle: 'padding-left: 5px',
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
		title: '审核',
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
						url: 'T110401Action_update.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							brhWin.hide();
							showSuccessMsg(action.result.msg,brhInfoForm);
							brhInfoForm.getForm().reset();
							grid.getStore().reload();
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
							txnId: '1104',
							subTxnId: '02'
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
	var brhInfoForm2 = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '申请信息*',
			allowBlank: true,
			emptyText: '选填',
			id: 'applyMsg1',
			name: 'applyMsg',
			width: 300
		}]
	});
	//绑定标签
	var brhWin2 = new Ext.Window({
		title: '维修完成申请',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [brhInfoForm2/*{
			fieldLabel: '申请信息*',
			allowBlank: false,
			emptyText: '请输入申请信息',
			id: 'applyMsg1',
			name: 'applyMsg',
			width: 300
		}*/],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			id : 'ensure11',
			text: '确定',
			handler: function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
				if(!selectedRecord.data.infoSign=='审核通过'){
					showErrorMsg("请选择审核通过的维修申请",brhInfoForm);
					return;
				}
					brhInfoForm.getForm().submit({
						url: 'T110104Action_updateComplete.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {},
						failure: function(form,action) {
							if(action.result.code ==200){
								brhWin2.hide();
								showSuccessMsg(action.result.msg,brhInfoForm2);
								brhInfoForm2.getForm().reset();
								grid.getStore().reload();
							}else{
								showErrorMsg(action.result.msg,brhInfoForm2);
							}
						},
						params: {
							txnId: '1104',
							subTxnId: '04',
							id : selectedRecord.id
						}
					});
				}
		},{
			id : 'reset222',
			text: '重置',
			handler: function() {
				brhInfoForm2.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				brhWin2.hide(grid);
				brhInfoForm2.getForm().reset();
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