Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			brhWin.show();
		}
	};
	
	var delMenu = {
			text : '删除',
			width : 60,
			iconCls : 'delete',
			disabled : true,
			handler : function() {
				if(grid.getSelectionModel().hasSelection()) {
	        		var rec = grid.getSelectionModel().getSelected();
					var railConstOrgid = rec.get('id');
					showConfirm('确定要删除该仓库工具预警阈值吗？仓库工具预警阈值id：' + railConstOrgid,grid,function(bt) {
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T100300Action.asp?method=delete',
								success: function(rsp,opt) {
//									alert(JSON.stringify(rsp)+"  >><<  "+JSON.stringify(opt));
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj == "00"){
//										Ext.MessageBox.alert('操作提示', railConstOrgid+'仓库工具预警阈值已成功删除!');
										showSuccessMsg('仓库工具预警阈值已成功删除!',grid);//showErrorMsg(action.result.msg,addMhForm)
										grid.getStore().reload();
									}else if(rspObj == "-1"){
										Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
									}
								},
								params: { 
									id:railConstOrgid
								}
							});
						}
					});
	        	}else{
	        		Ext.Msg.alert('提示', '请选择一个仓库工具预警阈值！');
	        	}
			}
	};
	
	var editMenu = {
			text : '修改',
			width : 60,
			iconCls : 'edit',
			disabled : true,
			handler : function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
	            if(selectedRecord == null)
	            {
	                showAlertMsg("没有选择记录",grid);
	                return;
	            } 
	            Ext.Ajax.request({
					url : 'T100300Action_getData.asp',
					params : {
						id : selectedRecord.get('id')
					},
					success : function(rsp, opt) {
						//showErrorMsg(rspObj.msg,grid);
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							updTermWin.show();
							updTermWin.center();
							Ext.getCmp('idUp').disable();
							Ext.getCmp('idUp').setValue(rspObj.msg.id);
				            updTermForm.getForm().findField("whseCodeUp").setValue(rspObj.msg.whseCode);
				            updTermForm.getForm().findField("toolNameUp").setValue(rspObj.msg.toolName);
				            Ext.getCmp('lowerThresholdUp').setValue(rspObj.msg.lowerThreshold);
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					}
				});
				}
		};
		var updTermForm = new Ext.form.FormPanel({
	        frame: true,
	        autoHeight: true,
	        width: 450,
	        labelWidth: 85,
	        waitMsgTarget: true,
	        items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden : true,
	       			items: [{
	       	            xtype: 'textnotnull',
	       	            labelStyle: 'padding-left: 5px',
	       	            fieldLabel: '仓库工具预警阈值id*',
	       	            maxLength: 20,
	       	            id: 'idUp',
	       	            name: 'idUp',
	       	            width: 300,
	       	       }]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '仓库编码*',
						methodName: 'getRailWhse',
						hiddenName: 'whseCodeUp',
						blankText: '请选择仓库',
						emptyText: "--请选择仓库--",
						allowBlank: false,
						editable: false,
						width:300
		        	}]
				},{
		    		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		   			items: [{
		   				xtype: 'dynamicCombo',
				        methodName: 'getToolNameLong',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '工具名称*',
						hiddenName: 'toolNameUp',
						allowBlank:false,
						editable: false,
						width:300,
						emptyText: "--请选择名称--"
		        	}]
				},{
		    		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		   			items: [{
		   	            xtype: 'textnotnull',
		   	            labelStyle: 'padding-left: 5px',
		   	            fieldLabel: '下限阈值*',
		   	            maxLength: 20,
					    regexText:"只能填写数字！", 
					    regex: /^[0-9]\d*$/,
		   	            id: 'lowerThresholdUp',
		   	            name: 'lowerThresholdUp',
		   	            width: 300,
		   	       }]
				}],
	    });
		var updTermWin = new Ext.Window({
	        title: '修改仓库工具预警阈值信息',
	        initHidden: true,
	        header: true,
	        frame: true,
	        closable: false,
	        modal: true,
	        width: 450,
	        autoHeight: true,
	        layout: 'fit',
	        items: [updTermForm],
	        buttonAlign: 'center',
	        closeAction: 'hide',
	        iconCls: 'edit',
	        resizable: false,
	        buttons: [{
	            text: '保存',
	            handler: function() {
	            	if(updTermForm.getForm().isValid()) {
						
						var submitValues = updTermForm.getForm().getValues();  
						for (var param in submitValues) {  
							if (updTermForm.getForm().findField(param) && updTermForm.getForm().findField(param).emptyText == submitValues[param]) {  
								updTermForm.getForm().findField(param).setValue(' ');  
							}  
						}
						
						updTermForm.getForm().submit({
							url: 'T100300Action.asp?method=edit',
							waitMsg: '正在保存，请稍后......',
							success: function(form,action) {
								updTermWin.hide();
								showSuccessMsg(action.result.msg,updTermForm);
								updTermForm.getForm().reset();
								grid.getStore().reload();
							},
							failure: function(form,action) {
								showErrorMsg(action.result.msg,updTermForm);
							},
							params: {
								id: Ext.getCmp('idUp').getValue(),
								whseCode:updTermForm.getForm().findField("whseCodeUp").getValue(),
								toolName: updTermForm.getForm().findField("toolNameUp").getValue(),
								lowerThreshold:Ext.getCmp('lowerThresholdUp').getValue(),
							}
						});
					}
	            }
	        },{
	            text: '关闭',
	            handler: function() {
	                updTermWin.hide();
	            }
	        }]
	    });
		
	
	var queryMenu = {
		text : '录入查询条件',
		width : 100,
		iconCls : 'query',
		handler : function() {
			//Ext.MessageBox.alert('提示', '你点了录入查询条件按钮!');
			queryWin.show();
		}
	};
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 450,
		autoHeight: true,
		items: [{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
        	id: 'whseCodefind',
			name: 'whseCodefind',
   			items: [{
	        	xtype: 'dynamicCombo',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '仓库编码',
				methodName: 'getRailWhse',
				hiddenName: 'whseCodefind',
				blankText: '请选择仓库',
				emptyText: "--请选择仓库--",
				allowBlank: true,
				editable: false,
				width:300
        	}]
		},{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
        	id: 'toolNamefind',
			name: 'toolNamefind',
   			items: [{
   				xtype: 'dynamicCombo',
		        methodName: 'getToolNameLong',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '工具名称',
				hiddenName: 'toolNamefind',
				allowBlank: true,
				editable: false,
				width:300,
				emptyText: "--请选择名称--"
        	}]
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		iconCls : 'query',
		width: 450,
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
				termStore.load();
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[4].disable();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
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
				var  whseCode = queryForm.getForm().findField("whseCodefind").getValue();
				var  toolName = queryForm.getForm().findField("toolNamefind").getValue();
				
				var param = "?a=1";
				if(whseCode){
					param = param + "&whseCode="+whseCode;
				}
				if(toolName){
					param = param + "&toolName="+toolName;
				}
				window.location.href = Ext.contextPath +"/exportExcelT100300.asp"+param;
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
	
	//仓库工具预警阈值添加表单
	var brhInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
        width: 450,
        labelWidth: 85,
        waitMsgTarget: true,
        items: [{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
        	id: 'whseCode',
			name: 'whseCode',
   			items: [{
	        	xtype: 'dynamicCombo',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '仓库编码*',
				methodName: 'getRailWhse',
				hiddenName: 'whseCode',
				blankText: '请选择仓库',
				emptyText: "--请选择仓库--",
				allowBlank: false,
				editable: false,
				width:300
        	}]
		},{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
   				xtype: 'dynamicCombo',
		        methodName: 'getToolNameLong',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '工具名称*',
				hiddenName: 'toolName',
				allowBlank: false,
				editable: false,
				width:300,
				emptyText: "--请选择名称--"
        	}]
		},{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
   	            xtype: 'textnotnull',
   	            labelStyle: 'padding-left: 5px',
   	            fieldLabel: '下限阈值*',
   	            maxLength: 20,
			    regexText:"只能填写数字！", 
			    regex: /^[0-9]\d*$/,
   	            id: 'lowerThreshold',
   	            name: 'lowerThreshold',
   	            width: 300,
   	       }]
		}],
	});
	
	//仓库工具预警阈值添加窗口
	var brhWin = new Ext.Window({
		title: '新增仓库工具预警阈值',
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
			id : 'save',
			text: '确定',
			handler: function() {
				saveWhse();
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
	
	var hasSub = false;
	function saveWhse(){
		var btn = Ext.getCmp('save');
		var frm = brhInfoForm.getForm();
		hasSub = true;
		if (frm.isValid()) {
//			btn.disable();
			frm.submit({
				url: 'T100300Action.asp?method=add',
				waitTitle : '请稍候',
				waitMsg : '正在保存表单数据,请稍候...',
				success : function(form, action) {
					brhWin.hide();
					showSuccessMsg(action.result.msg,brhInfoForm);
					brhInfoForm.getForm().reset();
					grid.getStore().reload();
				},
				failure : function(form,action) {
					btn.enable();
					hasSub = false;
					if (action.result.msg.substr(0,2) == 'CZ') {
						Ext.MessageBox.show({
							msg: action.result.msg.substr(2) + '<br><h1>是否继续保存吗？</h1>',
							title: '确认提示',
							animEl: Ext.get(brhInfoForm.getEl()),
							buttons: Ext.MessageBox.YESNO,
							icon: Ext.MessageBox.QUESTION,
							modal: true,
							width: 500,
							fn: function(bt) {
								if(bt == 'yes') {
									saveWhse();
								}
							}
						});
					} else {
						//showErrorMsg(action.result.msg,brhInfoForm);
					}
				}
			});
		}else{
			// 自动切换到未通过验证的tab
			var finded = true;
			frm.items.each(function(f){
	    		if(finded && !f.validate()){
	    			var tab = f.ownerCt.ownerCt.id;
	    			var tab2 = f.ownerCt.ownerCt.ownerCt.id;
	    			if(tab == 'basic' || tab == 'append'|| tab == 'settle' ){
	    				 Ext.getCmp("tab").setActiveTab(tab);
	    			}
	    			finded = false;
	    		}
	    	});
		}
	}
	
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(editMenu);
	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=railWhseToolWarn'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'id'
        },[
            {name: 'id',mapping: 'id'},
            {name: 'whseCode',mapping: 'whseCode'},
            {name: 'whseName',mapping: 'whseName'},
            {name: 'toolName',mapping: 'toolName'},
            {name: 'toolNameName',mapping: 'toolNameName'},
            {name: 'lowerThreshold',mapping: 'lowerThreshold'},
			{name: 'addUser',mapping: 'addUser'},
			{name: 'addDate',mapping: 'addDate'}
            ])
    });
	termStore.load();
	
	function enableStatusRender(val) {
		if(val == '启用') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="启用"/>启用';
		} else if(val == '停用') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="停用"/>停用';
		} 
		return '状态未知';
	}
	
    termStore.on('beforeload', function() {
    	Ext.apply(this.baseParams, {
            start: 0,
            whseCode:queryForm.getForm().findField("whseCodefind").getValue(),
            toolName:queryForm.getForm().findField("toolNamefind").getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
		{id: 'id',header: '阈值id',dataIndex: 'id',sortable: true,width: 120,hidden : true},
		{header: '仓库编码',dataIndex: 'whseCode',sortable: true,width: 150},
		{header: '仓库名称',dataIndex: 'whseName',sortable: true,width: 150},
		{header: '工具名称编码',dataIndex: 'toolName',sortable: true,width: 150},
		{header: '工具名称',dataIndex: 'toolNameName',sortable: true,width: 150},
		{header: '下限预警阈值',dataIndex: 'lowerThreshold',sortable: true,width: 120},
		{header: '创建人',dataIndex: 'addUser',sortable: true,width: 120},
		{header: '创建时间',dataIndex: 'addDate',sortable: true,width: 150}
    ]);
	// 工单信息列表
    var grid = new Ext.grid.GridPanel({
        title: '仓库工具预警阈值',
        iconCls: 'T301',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        region:'center',
        store: termStore,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        cm: termColModel,
        clicksToEdit: true,
        forceValidation: true,
        tbar: menuArr,
        renderTo: Ext.getBody(),
        loadMask: {
            msg: '加载中......'
        },
        bbar: new Ext.PagingToolbar({
            store: termStore,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
    });
    
    grid.getSelectionModel().on({
        'rowselect': function() {
            //行高亮
            Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
            // 根据商户状态判断哪个编辑按钮可用
            rec = grid.getSelectionModel().getSelected();
            if(rec != null) {           
            	var enableStatus=rec.get('enableStatus');  
                grid.getTopToolbar().items.items[0].enable();
                grid.getTopToolbar().items.items[2].enable();
                grid.getTopToolbar().items.items[4].enable();
            } else {
                grid.getTopToolbar().items.items[0].disable();
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[4].disable();
            }
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})