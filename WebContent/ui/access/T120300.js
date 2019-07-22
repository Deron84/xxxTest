Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	var editMenu = {
		text : '预警确认',
		width : 60,
		iconCls : 'edit',
		disabled : true,
		handler : function() {
			var	selectedRecord = grid.getSelectionModel().getSelected();
			var infoSign=selectedRecord.get('infoSign');
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            } 
            if(infoSign == '1')
            {
                showAlertMsg("预警已确认！",grid);
                return;
            } 
            Ext.Ajax.request({
				url : 'T120300Action_getData.asp',
				params : {
					accessCode : selectedRecord.get('warnId')
				},
				success : function(rsp, opt) {
					//showErrorMsg(rspObj.msg,grid);
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						updTermWin.show();
						updTermWin.center();
						Ext.getCmp('idUp').disable();
						Ext.getCmp('idUp').setValue(rspObj.msg.id);
			            Ext.getCmp('accessCodeUp').setValue(rspObj.msg.accessCode);
			            Ext.getCmp('warnTypeUp').setValue(warnTypeRender(rspObj.msg.warnType));
			            Ext.getCmp('warnMsgUp').setValue(rspObj.msg.warnMsg);
//			            Ext.getCmp('verifyMsgUp').setValue(rspObj.msg.verifyMsg);	            
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
       	            xtype: 'displayfield',
       	            labelStyle: 'padding-left: 5px',
       	            fieldLabel: '预警id*',
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
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '门禁编码*',
					id: 'accessCodeUp',
					name: 'accessCodeUp',  
					width:300,
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '预警类型*',
					id: 'warnTypeUp',
					name: 'warnTypeUp',
					width:300,
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '预警信息*',
					width:300,
					id: 'warnMsgUp',
					name: 'warnMsgUp'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '处理意见*',
					width:300,
					id: 'verifyMsgUp',
					name: 'verifyMsgUp',
					maxLength: 80
	        	}]
			}],
    });
	var updTermWin = new Ext.Window({
        title: '门禁预警确认',
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
            text: '确认',
            handler: function() {
            	if(updTermForm.getForm().isValid()) {
            		
					var submitValues = updTermForm.getForm().getValues();  
					for (var param in submitValues) {  
						if (updTermForm.getForm().findField(param) && updTermForm.getForm().findField(param).emptyText == submitValues[param]) {  
							updTermForm.getForm().findField(param).setValue(' ');  
						}  
					}
					
					updTermForm.getForm().submit({
						url: 'T120300Action.asp?method=edit',
						waitMsg: '正在提交，请稍后......',
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
							verifyMsg: Ext.getCmp('verifyMsgUp').getValue(),
							txnId: '120300',
							subTxnId: '00'
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
					url : 'T120300Action_getData.asp',
					params : {
						accessCode : selectedRecord.get('warnId')
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							detTermWin.show();
							detTermWin.center();
				            Ext.getCmp('idDet').setValue(rspObj.msg.id);
				            Ext.getCmp('accessCodeDet').setValue(rspObj.msg.accessCode);
				            Ext.getCmp('warnTypeDet').setValue(rspObj.msg.warnType);
				            Ext.getCmp('warnMsgDet').setValue(rspObj.msg.warnMsg);
				            Ext.getCmp('infoSignDet').setValue(rspObj.msg.infoSign);
				            Ext.getCmp('addDateDet').setValue(rspObj.msg.addDate);
				            Ext.getCmp('verifyUserDet').setValue(rspObj.msg.verifyUser);
				            Ext.getCmp('verifyDateDet').setValue(rspObj.msg.verifyDate);
				            Ext.getCmp('verifyMsgDet').setValue(rspObj.msg.verifyMsg);
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					}
				});
			}
		};
		var detTermForm = new Ext.form.FormPanel({
	        frame: true,
	        height: 150,
	        width: 1000,
	        labelWidth: 110,
	        waitMsgTarget: true,
	        layout: 'column',
	        items: [{
	        	layout:'column',
	            items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       	            xtype: 'displayfield',
	       	            labelStyle: 'padding-left: 5px',
	       	            fieldLabel: '预警id*',
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
	       	            fieldLabel: '门禁编码*',
	       	            maxLength: 20,
	       	            id: 'accessCodeDet',
	       	            name: 'accessCodeDet',
	       	            width: 300,
	       	       }]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '预警类型*',
						id: 'warnTypeDet',
						name: 'warnTypeDet',
						width:300,
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '预警信息*',
						id: 'warnMsgDet',
						name: 'warnMsgDet',
						width:300,
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否确认*',
						width:300,
						id: 'infoSignDet',
						name: 'infoSignDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '创建时间*',
						width:300,
						id: 'addDateDet',
						name: 'addDateDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '审核人*',
						width:300,
						id: 'verifyUserDet',
						name: 'verifyUserDet'
		        	}]
				}
				,{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '审核时间*',
						width:300,
						id: 'verifyDateDet',
						name: 'verifyDateDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '处理意见*',
						width:300,
						maxLength: 80,
						id: 'verifyMsgDet',
						name: 'verifyMsgDet'
		        	}]
				}],
	        }]
	    });
		var detTermWin = new Ext.Window({
	        title: '查看门禁预警详细信息',
	        initHidden: true,
	        header: true,
	        frame: true,
	        closable: false,
	        modal: true,
	        width: 1000,
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
		width: 450,
		autoHeight: true,
		items: [{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'dynamicCombo',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '门禁编码',
				methodName: 'getTdAccesses2',
				hiddenName: 'accessCode',
				blankText: '请选择门禁',
				emptyText: "--请选择门禁--",
				allowBlank: true,
				editable: false,
				width:300
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
            	grid.getTopToolbar().items.items[0].disable();
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
		width: 60,
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
				var  accessCode = queryForm.getForm().findField("accessCode").getValue();
					var param = "?a=1";
					if(accessCode){
						param = param + "&accessCode="+accessCode;
					}
					window.location.href = Ext.contextPath +"/exportExcelT1203.asp"+param;
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
	menuArr.push(editMenu);
	menuArr.push('-');
//	menuArr.push(detailMenu);
//	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=railAccessWarn'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'id'
        },[
            {name: 'warnId',mapping: 'id'},
            {name: 'accessCode',mapping: 'accessCode'},
            {name: 'accessName',mapping: 'accessName'},
            {name: 'warnType',mapping: 'warnType'},
            {name: 'warnMsg',mapping: 'warnMsg'},
            {name: 'infoSign',mapping: 'infoSign'},
			{name: 'addDate',mapping: 'addDate'},
			{name: 'verifyMsg',mapping: 'verifyMsg'},
			{name: 'verifyUser',mapping: 'verifyUser'},
            {name: 'verifyDate',mapping: 'verifyDate'}
        ])
    });
	termStore.load();
	
	function infoSignRender(val) {
		if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="已确认"/>已确认';
		} else if(val == '0') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="未确认"/>未确认';
		} 
		return '状态未知';
	}
	
	function warnTypeRender(warnType) {
		switch(warnType) {
			case '1': return '意外打开';
			case '2': return '长时间未关闭';
			case '3': return '门禁检测异常';
			case '4': return '断电故障';
		}
	}
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            accessCode: queryForm.getForm().findField("accessCode").getValue(),
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
    	{id:'warnId',header: '预警id',dataIndex: 'warnId',sortable: true,width: 60,hidden : true},
    	{id:'accessCode',header: '门禁编码',dataIndex: 'accessCode',sortable: true,width: 100},
    	{header: '门禁名称',dataIndex: 'accessName',sortable: true,width: 100},
		{header: '预警类型',dataIndex: 'warnType',sortable: true,renderer: warnTypeRender,width: 100},
		{header: '预警内容',dataIndex: 'warnMsg',sortable: true,width: 150},
		{header: '是否确认',dataIndex: 'infoSign',sortable: true,renderer: infoSignRender,width: 120},
		{header: '预警时间',dataIndex: 'addDate',sortable: true,width: 150},
		{header: '操作备注',dataIndex: 'verifyMsg',sortable: true,width: 150},
		{header: '操作员',dataIndex: 'verifyUser',sortable: true,width: 90},
		{header: '操作时间',dataIndex: 'verifyDate',sortable: true,width: 150}
    ]);
	// 仓库信息列表
    var grid = new Ext.grid.GridPanel({
        title: '门禁预警',
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
            	var openStatus=rec.get('openStatus');
                grid.getTopToolbar().items.items[0].enable();
            } else {
                grid.getTopToolbar().items.items[0].disable();
            }
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})