Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	
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
				url : 'T100101Action_getData.asp',
				params : {
					whseCode : selectedRecord.get('whseCode')
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						updTermWin.show();
						updTermWin.center();
						Ext.getCmp('whseCodeUp').disable();
			            Ext.getCmp('whseCodeUp').setValue(rspObj.msg.whseCode);
			            Ext.getCmp('whseNameUp').setValue(rspObj.msg.whseName);
			            Ext.getCmp('whseAddressUp').setValue(rspObj.msg.whseAddress);
			            var parentWhseCodeUp = rspObj.msg.parentWhseCode;
			            if(parentWhseCodeUp==0){
			            	updTermForm.getForm().findField('parentWhseCodeUp').setValue("");
			            }else{
			            	updTermForm.getForm().findField('parentWhseCodeUp').setValue(rspObj.msg.parentWhseCode);
			            }
			            Ext.getCmp('whseCapaUp').setValue(rspObj.msg.whseCapa);
			            Ext.getCmp('whsePicUp').setValue(rspObj.msg.whsePic);
			            Ext.getCmp('whseTelUp').setValue(rspObj.msg.whseTel);
			            updTermForm.getForm().findField("whseDeptUp").setValue(rspObj.msg.whseDept);
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
        height: 260,
        width: 350,
        labelWidth: 85,
        waitMsgTarget: true,
            items: [{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'textnotnull',
       	            labelStyle: 'padding-left: 5px',
       	            fieldLabel: '仓库编码*',
       	            id: 'whseCodeUp',
       	            name: 'whseCodeUp',
       	            width: 200
       	       }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库名称*',
					id: 'whseNameUp',
					name: 'whseNameUp',
					maxLength: 80,
					width:200,
	        	}]
			},{
				columnWidth: .5,
				xtype: 'panel',
				layout: 'form',
				items: [{
					xtype: 'dynamicCombo',
					methodName: 'getAllWhse',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '上级仓库',
					hiddenName: 'parentWhseCodeUp',
					allowBlank: true,
					editable: false,
					width:200,
					emptyText: "--请选择上级仓库--"
				}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库地址*',
					width:200,
					maxLength: 80,
					id: 'whseAddressUp',
					name: 'whseAddressUp'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库容量*',
					width:200,
					id: 'whseCapaUp',
					name: 'whseCapaUp',
					maxLength: 80,
				    regexText:"只能填写数字！", 
				    regex: /^[0-9]\d*$/
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '负责人*',
					width:200,
					maxLength: 40,
					id: 'whsePicUp',
					name: 'whsePicUp'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '联系方式*',
					width:200,
					id: 'whseTelUp',
					name: 'whseTelUp',
					regexText:"请填写正确的联系方式！", 
					regex: /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|[1][3,4,5,7,8,9][0-9]{9})$/
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       				xtype: 'dynamicCombo',
       		        methodName: 'getBranchId12',
       		        labelStyle: 'padding-left: 5px',
       				fieldLabel: '所属机构*',
       				hiddenName: 'whseDeptUp',
       				allowBlank: false,
       				editable: false,
       				width:200,
       				emptyText: "--请选择所属机构--"
       	    	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'radiogroup',
					labelStyle: 'padding-left: 5px;',
					fieldLabel: '仓库状态*',
					width:200,
					id: 'enableStatusUp',
					name:'enableStatusUp',
					items: [{
						name: 'whStatusUp',
						inputValue: '0',
						boxLabel: '启用'
					}, {
						name: 'whStatusUp',
						inputValue: '1',
						boxLabel: '停用'
					}]
	        	}]
			}],
    });
	var updTermWin = new Ext.Window({
        title: '修改仓库信息',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 350,
        autoHeight: true,
        layout: 'fit',
        items: [updTermForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'edit',
        resizable: false,
        buttons: [{
            text: '提交',
            handler: function() {
            	var aa= Ext.getCmp('whseCodeUp').getValue();
				var bb= updTermForm.getForm().findField('parentWhseCodeUp').getValue();
				if(aa==bb){
					showAlertMsg("不能选择自己作为上级仓库",grid);
	                return;
				}
				if(updTermForm.getForm().isValid()) {
					var enableStatusUp=Ext.getCmp('enableStatusUp'); 
					var esVal = "0";
					enableStatusUp.eachItem(function(item){  
					    if(item.checked===true){  
					    	esVal = item.inputValue;
					    }  
					});
					var submitValues = updTermForm.getForm().getValues();  
					for (var param in submitValues) {  
						if (updTermForm.getForm().findField(param) && updTermForm.getForm().findField(param).emptyText == submitValues[param]) {  
							updTermForm.getForm().findField(param).setValue(' ');  
						}  
					}
					updTermForm.getForm().submit({
						url: 'T100101Action.asp?method=edit',
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
							whseCode: Ext.getCmp('whseCodeUp').getValue(),
							whseName:Ext.getCmp('whseNameUp').getValue(),
							whseAddress:Ext.getCmp('whseAddressUp').getValue(),
							parentWhseCode: updTermForm.getForm().findField('parentWhseCodeUp').getValue(),
							whseCapa: Ext.getCmp('whseCapaUp').getValue(),
							whsePic: Ext.getCmp('whsePicUp').getValue(),
							whseTel: Ext.getCmp('whseTelUp').getValue(),
							whseDept:  updTermForm.getForm().findField("whseDeptUp").getValue(),
							enableStatus: esVal,
							txnId: '100101',
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
	var openMenu = {
		text : '启用',
		width : 60,
		iconCls : 'accept',
		disabled : true,
		handler : function() {
			if(grid.getSelectionModel().hasSelection()) {
        		var rec = grid.getSelectionModel().getSelected();
				var whseCode = rec.get('whseCode');
				showConfirm('确定要启用该仓库吗？仓库编码：' + whseCode,grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T100101Action.asp?method=open',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj == "00"){
//									Ext.MessageBox.alert('操作提示', whseCode+'仓库已成功启用!');
									showSuccessMsg(whseCode+'仓库已成功启用!',grid);//showErrorMsg(action.result.msg,addMhForm)
									grid.getStore().reload();
								}else if(rspObj == "-1"){
									Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
								}
							},
							params: { 
								whseCode: whseCode,
								txnId: '100101',
								subTxnId: '01'
							}
						});
					}
				});
        	}else{
        		Ext.Msg.alert('提示', '请选择一个仓库！');
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
				var whseCode = rec.get('whseCode');
				showConfirm('确定要停用该仓库吗？仓库编码：' + whseCode,grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T100101Action.asp?method=close',
							success: function(rsp,opt) {
//								alert(JSON.stringify(rsp)+"  >><<  "+JSON.stringify(opt));
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj == "00"){
//									Ext.MessageBox.alert('操作提示', whseCode+'仓库已被停用!');
									showSuccessMsg(whseCode+'仓库已被停用!',grid);//showErrorMsg(action.result.msg,addMhForm);
									grid.getStore().reload();
								}else if(rspObj == "-1"){
									Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
								}
							},
							params: { 
								whseCode: whseCode,
								txnId: '100101',
								subTxnId: '02'
							}
						});
					}
				});
        	}else{
        		Ext.Msg.alert('提示', '请选择一个仓库！');
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
				url : 'T100101Action_getData.asp',
				params : {
					whseCode : selectedRecord.get('whseCode')
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						detTermWin.show();
						detTermWin.center();
			            Ext.getCmp('whseCodeDet').setValue(rspObj.msg.whseCode);
			            Ext.getCmp('whseNameDet').setValue(rspObj.msg.whseName);
			            Ext.getCmp('whseAddressDet').setValue(rspObj.msg.whseAddress);
			            Ext.getCmp('whseParentWhseDet').setValue(rspObj.msg.parentWhse);
			            Ext.getCmp('whseCapaDet').setValue(rspObj.msg.whseCapa);
			            Ext.getCmp('whsePicDet').setValue(rspObj.msg.whsePic);
			            Ext.getCmp('whseTelDet').setValue(rspObj.msg.whseTel);
			            Ext.getCmp('whseDeptDet').setValue(rspObj.msg.whseBrhName);
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
        height: 260,
        width: 350,
        labelWidth: 85,
        waitMsgTarget: true,
            items: [{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'displayfield',
       	            labelStyle: 'padding-left: 5px',
       	            fieldLabel: '仓库编码',
       	            id: 'whseCodeDet',
       	            name: 'whseCodeDet',
       	            width: 200
       	       }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库名称',
					id: 'whseNameDet',
					name: 'whseNameDet',
					width:200,
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库地址',
					width:200,
					id: 'whseAddressDet',
					name: 'whseAddressDet'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '上级仓库',
					width:200,
					id: 'whseParentWhseDet',
					name: 'whseParentWhseDet'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库容量',
					width:200,
					id: 'whseCapaDet',
					name: 'whseCapaDet',
					regexText:"只能填写数字！", 
					regex: /^[0-9]\d*$/
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '负责人',
					width:200,
					id: 'whsePicDet',
					name: 'whsePicDet'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '联系方式',
					width:200,
					id: 'whseTelDet',
					name: 'whseTelDet'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '所属机构',
					width:200,
					id: 'whseDeptDet',
					name: 'whseDeptDet'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库状态',
					width:200,
					id: 'enableStatusDet',
					name: 'enableStatusDet'
	        	}]
			}],
    });
	var detTermWin = new Ext.Window({
        title: '查看仓库详细信息',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 350,
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
			id: 'whseCode',
			name: 'whseCode',
			fieldLabel: '仓库编码',
			width:200
		},{
			xtype:'textfield',
			fieldLabel: '仓库名称',
			id:'whseName',
			name:'whseName',
			width:200
		},{
			xtype:'textfield',
			fieldLabel: '负责人',
			id:'whsePic',
			name:'whsePic',
			width:200
		},{
			xtype: 'dynamicCombo',
			methodName: 'getAllWhse',
			fieldLabel: '上级仓库',
			hiddenName: 'parentWhseCode',
			allowBlank: true,
			editable: false,
			width:200,
			emptyText: "--请选择上级仓库--"
		},{
			xtype: 'dynamicCombo',
       		methodName: 'getBranchId12',
       	    fieldLabel: '所属机构',
       	    hiddenName: 'whseDept',
       		allowBlank: true,
       		editable: false,
       		width:200,
       		emptyText: "--请选择所属机构--"
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
            	grid.getTopToolbar().items.items[0].disable();
                grid.getTopToolbar().items.items[1].disable();
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[3].disable();
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
				var whseCode = Ext.getCmp('whseCode').getValue();
	            var whseName= Ext.getCmp('whseName').getValue();
				var whseDept=queryForm.getForm().findField("whseDept").getValue();
	            var whsePic = Ext.getCmp('whsePic').getValue();
				var param = "?a=1";
				if(whseCode){
					param = param + "&whseCode="+whseCode;
				}
				if(whseName){
					param = param + "&whseName="+whseName;
				}
				if(whseDept){
					param = param + "&whseDept="+whseDept;
				}
				if(whsePic){
					param = whsePic + "&whsePic="+whsePic;
				}
				window.location.href = Ext.contextPath +"/exportExcelT100101.asp"+param;
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
	menuArr.push(editMenu);
	menuArr.push(openMenu);
	menuArr.push(stopMenu);
	menuArr.push(detailMenu);
	menuArr.push(queryMenu);
	menuArr.push(exportMenu);
	
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=railWhseInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'whseCode'
        },[
            {name: 'whseCode',mapping: 'whseCode'},
            {name: 'whseName',mapping: 'whseName'},
            {name: 'whseAddress',mapping: 'whseAddress'},
            {name: 'parentWhse',mapping: 'parentWhse'},
			{name: 'whseCapa',mapping: 'whseCapa'},
			{name: 'whsePic',mapping: 'whsePic'},
            {name: 'whseTel',mapping: 'whseTel'},
            {name: 'brhName',mapping: 'brhName'},
            {name: 'enableStatus',mapping: 'enableStatus'}
        ])
    });
	termStore.load();
	function enableStatusRender(enableStatus) {
		switch(enableStatus) {
			case '0': return '启用';
			case '1': return '停用';
		}
	}
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            whseCode: Ext.getCmp('whseCode').getValue(),
            whseName: Ext.getCmp('whseName').getValue(),
            parentWhseCode: queryForm.getForm().findField('parentWhseCode').getValue(),
            whseDept: queryForm.getForm().findField("whseDept").getValue(),
            whsePic: Ext.getCmp('whsePic').getValue()
        });
    });
	
	
	
	
	var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
    	{id:'whseCode',header: '仓库编码',dataIndex: 'whseCode',sortable: true,width: 120},
		{header: '仓库名称',dataIndex: 'whseName',sortable: true,width: 120},
		{header: '仓库地址',dataIndex: 'whseAddress',sortable: true,width: 200},
		{header: '上级仓库',dataIndex: 'parentWhse',sortable: true,width: 120},
		{header: '仓库容量',dataIndex: 'whseCapa',sortable: true,width: 120},
		{header: '负责人',dataIndex: 'whsePic',sortable: true,width: 120},
		{header: '联系方式',dataIndex: 'whseTel',sortable: true,width: 120},
		{header: '所属机构',dataIndex: 'brhName',sortable: true,width: 120},
		{header: '仓库状态',dataIndex: 'enableStatus',sortable: true,renderer: enableStatusRender,width: 120}
    ]);
	// 仓库信息列表
    var grid = new Ext.grid.GridPanel({
        title: '仓库信息维护',
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
                if(enableStatus=="1"){
                	grid.getTopToolbar().items.items[1].enable();
                	grid.getTopToolbar().items.items[2].disable();
                }else if(enableStatus=="0"){
                	grid.getTopToolbar().items.items[1].disable();
                	grid.getTopToolbar().items.items[2].enable();
                }
                grid.getTopToolbar().items.items[3].enable();
            } else {
                grid.getTopToolbar().items.items[0].disable();
                grid.getTopToolbar().items.items[1].disable();
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[3].disable();
            }
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})