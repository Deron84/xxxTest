Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	var addPatrolMenu = {
		text: '新增',
		width: 85,
		disabled: false,
		iconCls: 'add',
		handler:function() {
			addPatrolWin.show();
		}
	};
	
	var delPatrolMenu = {
		text : '删除',
		width : 60,
		iconCls : 'delete',
		disabled : true,
		handler : function() {
			if(grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				var patrolId = rec.get('id');
				var patrolName = rec.get('patrolName');
				showConfirm('确定要删除该巡护中队吗？巡护中队名称为：' + patrolName,grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T130405Action.asp?method=del',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj == "00"){
									showSuccessMsg(patrolName+'巡护中队已成功删除!',grid);//showErrorMsg(action.result.msg,addMhForm)
									grid.getStore().reload();
								}else if(rspObj == "-1"){
									Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
								}
							},
							params: { 
								id:patrolId
							}
						});
					}
				});
			}else{
				Ext.Msg.alert('提示', '请选择一个巡护中队！');
			}
		}
	};
	
	var editPatrolMenu = {
		text : '修改',
		width : 60,
		iconCls : 'edit',
		disabled : true,
		handler : function() {
			var	selectedRecord = grid.getSelectionModel().getSelected();
			if(selectedRecord == null){
				showAlertMsg("没有选择记录",grid);
				return;
			} 
			Ext.Ajax.request({
				url : 'T130405Action_getData.asp',
				params : {
					id : selectedRecord.get('id')
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						editPatrolWin.show();
						editPatrolWin.center();
						Ext.getCmp('idUp').disable();
						Ext.getCmp('idUp').setValue(rspObj.msg.id);
						Ext.getCmp('patrolNameUp').setValue(rspObj.msg.patrolName);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				}
			});
		}
	};
	var editPatrolForm = new Ext.form.FormPanel({
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
				fieldLabel: '巡护中队id*',
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
				fieldLabel: '巡护中队名称*',
				id: 'patrolNameUp',
				name: 'patrolNameUp',
				maxLength: 80,
				width:300,
			}]
		}]
	});
	var editPatrolWin = new Ext.Window({
		title: '修改巡护中队信息',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [editPatrolForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'edit',
		resizable: false,
		buttons: [{
			text: '提交',
			handler: function() {
				if(editPatrolForm.getForm().isValid()) {
					var enableStatusUp=Ext.getCmp('enableStatusUp'); 
					editPatrolForm.getForm().submit({
						url: 'T130405Action.asp?method=edit',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							editPatrolWin.hide();
							showSuccessMsg(action.result.msg,editPatrolForm);
							editPatrolForm.getForm().reset();
							grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,editPatrolForm);
						},
						params: {
							id: Ext.getCmp('idUp').getValue(),
							patrolName: Ext.getCmp('patrolNameUp').getValue(),
						}
					});
				}
			}
		},{
			text: '关闭',
			handler: function() {
				editPatrolWin.hide();
			}
		}]
	});
		
	var queryMenu = {
		text : '录入查询条件',
		width : 100,
		iconCls : 'query',
		handler : function() {
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
			id: 'patrolNamefind',
			name: 'patrolNamefind',
			fieldLabel: '巡护中队名称',
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
				var patrolName =  Ext.getCmp('patrolNamefind').getValue();
				var param = "?a=1";
				if(patrolName){
					param = param + "&patrolName="+patrolName;
				}
				window.location.href = Ext.contextPath +"/exportExcelT130405.asp"+param;
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
	
	//班组添加表单
	var addPatrolForm = new Ext.form.FormPanel({
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
					fieldLabel: '巡护中队名称*',
					id: 'patrolName',
					name: 'patrolName',
					maxLength: 80,
					width:300,
	        	}]
			}],
	});
	
	//班组添加窗口
	var addPatrolWin = new Ext.Window({
		title: '新增巡护中队',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [addPatrolForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			id : 'save',
			text: '确定',
			handler: function() {
				savePatrol();
			}
		},{
			id : 'reset1',
			text: '重置',
			handler: function() {
				addPatrolForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addPatrolWin.hide(grid);
				addPatrolForm.getForm().reset();
			}
		}]
	});
	
	var hasSub = false;
	function savePatrol(){
		var btn = Ext.getCmp('save');
		var frm = addPatrolForm.getForm();
		hasSub = true;
		if (frm.isValid()) {
//			btn.disable();
			frm.submit({
				url: 'T130405Action.asp?method=add',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
					addPatrolWin.hide();
					showSuccessMsg(action.result.msg,addPatrolForm);
					addPatrolForm.getForm().reset();
					grid.getStore().reload();
				},
				failure : function(form,action) {
					btn.enable();
					hasSub = false;
					if (action.result.msg.substr(0,2) == 'CZ') {
						Ext.MessageBox.show({
							msg: action.result.msg.substr(2) + '<br><h1>是否继续保存吗？</h1>',
							title: '确认提示',
							animEl: Ext.get(addPatrolForm.getEl()),
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
						showErrorMsg(action.result.msg,addPatrolForm);
					}
				}
			});
		}
	}
	
	menuArr.push(addPatrolMenu);
	menuArr.push('-');
	menuArr.push(delPatrolMenu);
	menuArr.push('-');
	menuArr.push(editPatrolMenu);
	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=patrols'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'id'
        },[
            {name: 'id',mapping: 'id'},
            {name: 'patrolName',mapping: 'patrolName'},
            {name: 'addDate',mapping: 'addDate'}
        ])
    });
	termStore.load();
//	
//	function enableStatusRender(enableStatus) {
//		switch(enableStatus) {
//			case '0': return '启用';
//			case '1': return '停用';
//		}
//	}
	
    termStore.on('beforeload', function() {
    	Ext.apply(this.baseParams, {
            start: 0,
            patrolName: Ext.getCmp('patrolNamefind').getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
		{id: 'id',header: '巡护中队编码',dataIndex: 'id',sortable: true,width: 120},
		{header: '巡护中队名称',dataIndex: 'patrolName',sortable: true,width: 120},
		{header: '添加时间',dataIndex: 'addDate',sortable: true,width: 150}
    ]);
	// 工单信息列表
    var grid = new Ext.grid.GridPanel({
        title: '巡护中队管理',
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
                grid.getTopToolbar().items.items[2].enable();
                grid.getTopToolbar().items.items[4].enable();
            } else {
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