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

	var upMenuAll = {
		text: '编辑',
		width: 85,
		disabled: true,
		iconCls: 'edit',
		handler:function() {
			var	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            
            var auditStatus = selectedRecord.get("toolStatus");
            if(auditStatus == "维护"){
            	Ext.MessageBox.alert('操作提示', '该工具维护中，不可编辑！');
                return;
            }
            if(auditStatus == "报废"){
            	Ext.MessageBox.alert('操作提示', '该工具已报废，不可编辑！');
                return;
            }
            if(auditStatus == "停用"){
            	Ext.MessageBox.alert('操作提示', '该工具已停用，不可编辑！');
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
						brhWinAll.show();
						brhWinAll.center();
						brhInfoFormAll.getForm().findField('toolName').setValue(rspObj.info.toolName);
						brhInfoFormAll.getForm().findField('toolName').disable();
						
						
						Ext.getCmp('rfid').setValue(rspObj.info.rfid);

			            Ext.getCmp('purchaseUser').setValue(rspObj.info.purchaseUser);
			            Ext.getCmp('purchaseUser').setValue(rspObj.info.purchaseUser);
			            Ext.getCmp('toolMaterial').setValue(rspObj.info.toolMaterial);
			            Ext.getCmp('examPeriod').setValue(rspObj.info.examPeriod);
			            

			            if(rspObj.info.toolExpiration){
			            	var a = rspObj.info.toolExpiration;
			            	brhInfoFormAll.getForm().findField('toolExpiration1').
			            	setValue(a.substr(0,4)+a.substr(5,2)+a.substr(8,2));
			            }
			            brhInfoFormAll.getForm().findField('position').setValue(rspObj.info.position);
			            brhInfoFormAll.getForm().findField('floor').setValue(rspObj.info.floor);
			            brhInfoFormAll.getForm().findField('stand').setValue(rspObj.info.stand);
			            brhInfoFormAll.getForm().findField('note2').setValue(rspObj.info.note2);
			            brhInfoFormAll.getForm().findField('whseCode').setValue(rspObj.info.whseCode);
			            brhInfoFormAll.getForm().findField('purchaseDept').setValue(rspObj.info.purchaseDept);
			            brhInfoFormAll.getForm().findField('mfrsOrg').setValue(rspObj.info.mfrsOrg);
			            brhInfoFormAll.getForm().findField('dept').setValue(rspObj.info.dept);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				}
			});
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
				var brhId = rec.get('brhId');		
				
				showConfirm('确认删除选中工具？',grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T110100Action_del.asp',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(opt.result);
								if(opt.result.code=200) {
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
								id: rec.id
							}
						}
						);
					}
				});
			}
		}
	};
	
	var upMenu = {
		text: '绑定标签',
		width: 85,
		iconCls: 'add',
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
						//brhInfoFormStatus.getForm().findField('toolStatus1').setValue(rspObj.info.dept);
			            Ext.getCmp('rfid').setValue(rspObj.info.rfid);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				}
			});
          
		}
	};
	var upMenuStatus = {
			text: '停用/启用',
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

				var	selectedRecord = grid.getSelectionModel().getSelected();
				var data = selectedRecord.data;
	            Ext.Ajax.request({
					url : 'T110101Action_get.asp',
					params : {
						id : selectedRecord.id
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						
						if(rspObj.code == 200) {
							if(rspObj.info.toolStatus == 1|| rspObj.info.toolStatus==3 ){
								showAlertMsg("请选择工具状态为正常或者停用的工具",grid);
								return;
							}
							brhWinStatus.show();
							brhWinStatus.center();
							
							//brhInfoFormStatus.getForm().findField('toolStatus1').setValue(rspObj.info.dept);
				            Ext.getCmp('toolStatus1').setValue(rspObj.info.toolStatus);
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
					var  whseCode = queryForm.findById('whseCodeS').getValue();
					var toolName =  queryForm.findById('toolNameS').getValue();
					var param = "?a=1";
					param = param + "&txnId=excel&subTxnId=110101"//日志用
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
	menuArr.push(upMenuAll);
	menuArr.push('-');
//	menuArr.push(delMenu);
//	menuArr.push('-');
//	menuArr.push(upMenu);	
//	menuArr.push('-');
	menuArr.push(upMenuStatus);	
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
		title: '工具信息维护',
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
		grid.getTopToolbar().items.items[0].disable();
		grid.getTopToolbar().items.items[2].disable();
//		grid.getTopToolbar().items.items[4].disable();
		//grid.getTopToolbar().items.items[6].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//单击行，使删除按钮可用
		'rowclick': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[0].enable();
			grid.getTopToolbar().items.items[2].enable();
//			grid.getTopToolbar().items.items[4].enable();
		//	grid.getTopToolbar().items.items[6].enable();
		},
		
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[0].enable();
			grid.getTopToolbar().items.items[2].enable();
//			grid.getTopToolbar().items.items[4].enable();
		//	grid.getTopToolbar().items.items[6].enable();
			
			var brhId = grid.getSelectionModel().getSelected().data.brhId;
		
		}
	});
	
	//机构添加表单
	var brhInfoFormStatus = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
            xtype: 'radiogroup',
            width:300,
            fieldLabel: '工具状态*',//0正常；1维护；3报废；4停用
            id:'toolStatus1',
            labelStyle: 'padding-left: 5px',
            vertical: true,
            items: [{
			boxLabel: '正常', 
			name: 'toolStatus', 
			inputValue: 0, 
			checked: true
		}/*,{
			boxLabel: '维护', 
			name: 'toolStatus', 
			inputValue: 1
		},{
			boxLabel: '报废', 
			name: 'toolStatus', 
			inputValue: 3
		}*/,{
			boxLabel: '停用', 
			name: 'toolStatus', 
			inputValue: 4
		}]
        }
	]
	});
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
		}]
	});
	//修改工具信息
	var brhInfoFormAll = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		fileUpload: true,
		items: [{
   	          xtype: 'displayfield',
   	          labelStyle: 'padding-left: 5px',
   	           fieldLabel: '工具标签*',
   	            maxLength: 20,
   	            id: 'rfid',
   	            name: 'rfid',
   	            width: 300,
		},{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
   				xtype: 'dynamicCombo',
		        methodName: 'getToolNameLong',
				fieldLabel: '工具名称*',
				labelStyle: 'padding-left: 5px',
				hiddenName: 'toolName',
				allowBlank: false,
				editable: false,
				width:300,
				emptyText: "--请选择名称--"
        	}]
		},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '工具型号*',
			width:300,
			maxLength: 10,
			id: 'note2',
			name: 'note2'
		},{
        	xtype: 'dynamicCombo',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '仓库编码',
			methodName: 'getRailWhse',
			hiddenName: 'whseCode',
			blankText: '请选择仓库',
			emptyText: "--请选择仓库--",
			allowBlank: false,
			editable: false,
			width:300
    	},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '架*',
			width:300,
			maxLength: 10,
			id: 'stand',
			name: 'stand'
		},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '层*',
			width:300,
			maxLength: 10,
			id: 'floor',
			name: 'floor'
		},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '位*',
			width:300,
			maxLength: 10,
			id: 'position',
			name: 'position'
		},{
			fieldLabel: '材质*',
			labelStyle: 'padding-left: 5px',
			allowBlank: false,
			emptyText: '请输入材质',
			maxLength: 20,
			id: 'toolMaterial',
			name: 'toolMaterial',
			width: 300
		},{
			xtype: 'numberfield',
			fieldLabel: '保养周期(天)*',
			labelStyle: 'padding-left: 5px',
			allowBlank: false,
			emptyText: '请输入保养周期',
			maxLength: 20,
			id: 'examPeriod',
			name: 'examPeriod',
			width: 300
		},{
        	xtype: 'datefield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '有效期*',
			minValue: new Date(),
			editable: false,
			id: 'toolExpiration1',
			name: 'toolExpiration1',
			width:300,
    	},{
    		xtype: 'dynamicCombo',
	        methodName: 'getBranchId12',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '采购部门',
			hiddenName: 'purchaseDept',
			allowBlank: true,
			editable: false,
			width:300,
			emptyText: "--请选择采购部门--"
    	},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '采购人',
			width:300,
			id: 'purchaseUser',
			name: 'purchaseUser',
			maxLength: 40,
			allowBlank: true
		},{
				xtype: 'dynamicCombo',
	        methodName: 'getOrgName',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '供应商',
			hiddenName: 'mfrsOrg',	
			allowBlank: true,
			editable: false,
			width:300,
			emptyText: "--请选择供应商--"
    	},{
				xtype: 'dynamicCombo',
	        methodName: 'getBranchId12',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '所属机构*',
			hiddenName: 'dept',
			allowBlank: false,
			editable: false,
			width:300,
			emptyText: "--请选择所属机构--"
    	}/*,{
	            xtype: 'radiogroup',
	            width:300,
	            fieldLabel: '工具状态*',//0正常；1维护；3报废；4停用
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
	        }*/]
	});
	
	//绑定标签
	var brhWin = new Ext.Window({
		title: '绑定标签',
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
		fileUpload: true,
		buttons: [{
			id : 'ensure11',
			text: '确定',
			handler: function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
					brhInfoForm.getForm().submit({
						url: 'T110101Action_update.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {},
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
		},
//		{
//			id : 'reset11',
//			text: '重置',
//			handler: function() {
//				brhInfoForm.getForm().reset();
//			}
//		},
		{
			text: '关闭',
			handler: function() {
				brhWin.hide(grid);
				brhInfoForm.getForm().reset();
			}
		}]
	});
	//停用启用
	var brhWinStatus = new Ext.Window({
		title: '停用/启用',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [brhInfoFormStatus],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			id : 'ensure1',
			text: '确定',
			handler: function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
				brhInfoFormStatus.getForm().submit({
					url: 'T110101Action_update.asp',
					waitMsg: '正在提交，请稍后......',
					success: function(form,action) {},
					failure: function(form,action) {
						if(action.result.code ==200){
							brhWinStatus.hide();
							showSuccessMsg(action.result.msg,brhInfoFormStatus);
							brhInfoFormStatus.getForm().reset();
							grid.getStore().reload();
						}else{
							showErrorMsg(action.result.msg,brhInfoFormStatus);
						}
					},
					params: {
						txnId: '1101',
						subTxnId: '04',
						id : selectedRecord.id
					}
				});
			}
		},
//		{
//			id : 'reset1',
//			text: '重置',
//			handler: function() {
//				brhInfoFormStatus.getForm().reset();
//			}
//		},
		{
			text: '关闭',
			handler: function() {
				brhWinStatus.hide(grid);
				brhInfoFormStatus.getForm().reset();
			}
		}]
	});
	var brhWinAll = new Ext.Window({
		title: '修改工具信息',
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
						url: 'T110101Action_updateAll.asp',
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
							txnId: '1101',
							subTxnId: '03',
							id : selectedRecord.id
						}
					});
				}
			}
		},
//		{
//			id : 'reset1',
//			text: '重置',
//			handler: function() {
//				brhInfoFormAll.getForm().reset();
//			}
//		},
		{
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
		Ext.apply(this.baseParams, {
			start: 0,
			whseCode: queryForm.findById('whseCodeS').getValue(),
			toolName: queryForm.findById('toolNameS').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});