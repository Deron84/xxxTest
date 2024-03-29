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
					var typeName = rec.get('typeName');
					showConfirm('确定要删除该人员类型吗？人员类型名称：' + typeName,grid,function(bt) {
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T130404Action.asp?method=delete',
								success: function(rsp,opt) {
//									alert(JSON.stringify(rsp)+"  >><<  "+JSON.stringify(opt));
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj == "00"){
//										Ext.MessageBox.alert('操作提示', railConstOrgid+'人员类型已成功删除!');
										showSuccessMsg('人员类型已成功删除!',grid);//showErrorMsg(action.result.msg,addMhForm)
										grid.getStore().reload();
									}else if(rspObj == "-1"){
										Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
									}
								},
								params: { 
									id:railConstOrgid,
									txnId: '130404',
									subTxnId: '01'
								}
							});
						}
					});
	        	}else{
	        		Ext.Msg.alert('提示', '请选择一个人员类型！');
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
					url : 'T130404Action_getData.asp',
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
				            Ext.getCmp('typeNameUp').setValue(rspObj.msg.typeName);
				            Ext.getCmp('enableStatusUp').setValue(rspObj.msg.enableStatus);
				        } else {
							showErrorMsg(rspObj.msg,grid);
						}
					}
				});
				}
		};
		var updTermForm = new Ext.form.FormPanel({
	        frame: true,
	        height: 80,
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
	       	            fieldLabel: '人员类型id*',
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
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '人员类型名称*',
						id: 'typeNameUp',
						name: 'typeNameUp',
						maxLength: 80,
						width:300,
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       	            xtype: 'radiogroup',
	       	            width:300,
	       	            fieldLabel: '人员类型状态*',
	       	            labelStyle: 'padding-left: 5px',
	       	            vertical: true,
	       	            id: 'enableStatusUp',
						name:'enableStatusUp',
	       	            items: [{
							boxLabel: '停用', 
							name: 'enableStatusUp', 
							inputValue: 1, 
							checked: true
						},{
							boxLabel: '启用', 
							name: 'enableStatusUp', 
							inputValue: 0
						}]
	       			}]
				}],
	    });
		var updTermWin = new Ext.Window({
	        title: '修改人员类型信息',
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
						var enableStatusUp=Ext.getCmp('enableStatusUp'); 
						var asVal = "0";
						enableStatusUp.eachItem(function(item){  
						    if(item.checked===true){  
						    	asVal = item.inputValue;
						    }  
						});
						
						var submitValues = updTermForm.getForm().getValues();  
						for (var param in submitValues) {  
							if (updTermForm.getForm().findField(param) && updTermForm.getForm().findField(param).emptyText == submitValues[param]) {  
								updTermForm.getForm().findField(param).setValue(' ');  
							}  
						}
						
						updTermForm.getForm().submit({
							url: 'T130404Action.asp?method=edit',
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
								typeName: Ext.getCmp('typeNameUp').getValue(),
							    enableStatus:asVal,
							    txnId: '130404',
								subTxnId: '02'
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
		var openMenu = {
				text : '启用',
				width : 60,
				iconCls : 'accept',
				disabled : true,
				handler : function() {
					if(grid.getSelectionModel().hasSelection()) {
		        		var rec = grid.getSelectionModel().getSelected();
						var railConstOrgid = rec.get('id');
						var typeName = rec.get('typeName');
						showConfirm('确定要启用该人员类型吗？人员类型名称：' + typeName,grid,function(bt) {
							if(bt == "yes") {
								Ext.Ajax.requestNeedAuthorise({
									url: 'T130404Action.asp?method=open',
									success: function(rsp,opt) {
//									   Ext.MessageBox.alert('操作提示', '处理成功,已启用');
									   showSuccessMsg('处理成功,已启用!',grid);//showErrorMsg(action.result.msg,addMhForm)
										grid.getStore().reload();
									},
									params: { 
										id:railConstOrgid,
										txnId: '130404',
										subTxnId: '03'
									}
								});
							}
						});
		        	}else{
		        		Ext.Msg.alert('提示', '请选择一个人员类型！');
		        	}
				}
		};
		var stopMenu = {
				text : '停用',
				width : 60,
				iconCls : 'stop',
				disabled : true,
				handler : function() {
					if(grid.getSelectionModel().hasSelection()) {
		        		var rec = grid.getSelectionModel().getSelected();
						var railConstOrgid = rec.get('id');
						var typeName = rec.get('typeName');
						showConfirm('确定要停用该人员类型吗？人员类型名称：' + typeName,grid,function(bt) {
							if(bt == "yes") {
								Ext.Ajax.requestNeedAuthorise({
									url: 'T130404Action.asp?method=close',
									success: function(rsp,opt) {
										Ext.MessageBox.alert('操作提示', '处理成功,已停用');
										showSuccessMsg('处理成功,已停用!',grid);//showErrorMsg(action.result.msg,addMhForm)
										grid.getStore().reload();
									},
									params: { 
										id: railConstOrgid,
										txnId: '130404',
										subTxnId: '04'
									}
								});
							}
						});
		        	}else{
		        		Ext.Msg.alert('提示', '请选择一个人员类型！');
		        	}
					//window.location.href = Ext.contextPath+ '/page/mchnt/T2010102.jsp?mchntId='+ mchntGrid.getSelectionModel().getSelected().get('mchtNo');
				}
		};
		var detailMenu = {
				text : '查看详细信息',
				width : 100,
				iconCls : 'detail',
				disabled : true,
				handler : function() {
					var	selectedRecord = grid.getSelectionModel().getSelected();
		            if(selectedRecord == null)
		            {
		                showAlertMsg("没有选择记录",grid);
		                return;
		            }
		            Ext.Ajax.request({
		            	url : 'T130404Action_getData.asp',
						params : {
							id : selectedRecord.get('id')
						},
						success : function(rsp, opt) {
							//showErrorMsg(rspObj.msg,grid);
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								detTermWin.show();
								detTermWin.center();
					        	Ext.getCmp('idDet').disable();
								Ext.getCmp('idDet').setValue(rspObj.msg.id);
					            Ext.getCmp('typeNameDet').setValue(rspObj.msg.typeName);
					            Ext.getCmp('enableStatusDet').setValue(enableStatusRender(rspObj.msg.enableStatus));
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
						}
					});
				}
			};
			var detTermForm = new Ext.form.FormPanel({
		        frame: true,
		        height: 100,
		        width: 450,
		        labelWidth: 85,
		        waitMsgTarget: true,
		        items: [{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
		       	            xtype: 'displayfield',
		       	            labelStyle: 'padding-left: 5px',
		       	            fieldLabel: '人员类型id',
		       	            maxLength: 20,
		       	            id: 'idDet',
		       	            name: 'idDet',
		       	            width: 300,
		       	       }]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '人员类型名称',
							id: 'typeNameDet',
							name: 'typeNameDet',
							width:300,
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '人员类型状态',
							id: 'enableStatusDet',
							name: 'enableStatusDet',
							width:300,
			        	}]
					}],
		    });
			var detTermWin = new Ext.Window({
		        title: '查看人员类型详细信息',
		        initHidden: true,
		        header: true,
		        frame: true,
		        closable: false,
		        modal: true,
		        width: 450,
		        autoHeight: true,
		        layout: 'fit',
		        items: [detTermForm],
		        buttonAlign: 'center',
		        closeAction: 'hide',
		        iconCls: 'edit',
		        resizable: false,
		        buttons: [{
		            text: '关闭',
		            handler: function() {
		            	detTermWin.hide();
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
		width: 400,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'typeNamefind',
			name: 'typeNamefind',
			fieldLabel: '人员类型名称',
			width:200
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		iconCls : 'query',
		width: 400,
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
                grid.getTopToolbar().items.items[6].disable();
                grid.getTopToolbar().items.items[8].disable();
               // grid.getTopToolbar().items.items[10].disable();
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
				var  typeName = Ext.getCmp('typeNamefind').getValue();
				var param = "?a=1";
				if(typeName){
					param = param + "&typeName="+typeName;
				}
				window.location.href = Ext.contextPath +"/exportExcelT130404.asp"+param;
				excelDown.hide();
//				excelQueryForm.getForm().submit({
//					url: 'T30104Action_download.asp',
//					
//					waitMsg: '正在下载报表，请稍等......',
//					success: function(form,action) {
//					//showAlertMsg(action.result.msg,grid);
//						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
//																		action.result.msg+'&key=exl20exl';
//						excelDown.hide();
//					},
//					failure: function(form,action) {
//						Ext.MessageBox.show({
//							msg: '下载失败！',
//							title: '错误提示',
//							animEl: Ext.getBody(),
//							buttons: Ext.MessageBox.OK,
//							icon: Ext.MessageBox.ERROR,
//							modal: true,
//							width: 250
//						});
//						excelDown.hide();
//					},
//					params: {
//						txnId: '30104',
//						subTxnId: '03'
//					}
//				});
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
	
	//人员类型添加表单
	var brhInfoForm = new Ext.form.FormPanel({
		frame: true,
        height: 50,
        width: 450,
        labelWidth: 85,
        waitMsgTarget: true,
        items: [{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '人员类型名称*',
					id: 'typeName',
					name: 'typeName',
					maxLength: 80,
					width:300,
	        	}]
			}],
	});
	
	//人员类型添加窗口
	var brhWin = new Ext.Window({
		title: '新增人员类型',
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
				url: 'T130404Action.asp?method=add',
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
						showErrorMsg(action.result.msg,brhInfoForm);
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
	menuArr.push(openMenu);
	menuArr.push('-');
	menuArr.push(stopMenu);
	menuArr.push('-');
//	menuArr.push(detailMenu);
//	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=employeeType'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'id'
        },[
            {name: 'id',mapping: 'id'},
            {name: 'typeName',mapping: 'typeName'},
            {name: 'enableStatus',mapping: 'enableStatus'},
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
            typeName: Ext.getCmp('typeNamefind').getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
		{id: 'id',header: '人员类型id',dataIndex: 'id',sortable: true,width: 120,hidden : true},
		{header: '人员类型名称',dataIndex: 'typeName',sortable: true,width: 120},
		{header: '人员类型状态',dataIndex: 'enableStatus',sortable: true,renderer:enableStatusRender,width: 120},
		{header: '创建人',dataIndex: 'addUser',sortable: true,width: 120},
		{header: '创建时间',dataIndex: 'addDate',sortable: true,width: 150}
    ]);
	// 工单信息列表
    var grid = new Ext.grid.GridPanel({
        title: '人员类型管理',
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
                if(enableStatus=="停用"){
                	grid.getTopToolbar().items.items[6].enable();
                	grid.getTopToolbar().items.items[8].disable();
                }else if(enableStatus=="启用"){
                	grid.getTopToolbar().items.items[6].disable();
                	grid.getTopToolbar().items.items[8].enable();
                }
               // grid.getTopToolbar().items.items[10].enable();
            } else {
                grid.getTopToolbar().items.items[0].disable();
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[4].disable();
                grid.getTopToolbar().items.items[6].disable();
                grid.getTopToolbar().items.items[8].disable();
                //grid.getTopToolbar().items.items[10].disable();
            }
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})