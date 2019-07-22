Ext.onReady(function() {
	var myData ={"totalCount":"1","data":[{"job":"工人","employeeCode":"0002","employeeName":"李四"}]};
	var fields = [
		{name: 'employeeCode'},
		{name: 'employeeName'},
		{name: 'job'},
		{name: 'opt'},
	];

	var firstGridStore = new Ext.data.JsonStore({
		fields : fields,
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=teamEmployee2'
		}),
		root: 'data'
	});

	var cols = [
		{id:"employeeCode",header: "人员编码", width: 100, dataIndex: 'employeeCode'},
		{header: "人员姓名", width: 100, dataIndex: 'employeeName'},
		{header: "职务", width: 100, dataIndex: 'job'}
	];
	var cols2 = [
		{id:"employeeCode",header: "人员编码", width: 100, dataIndex: 'employeeCode'},
		{header: "人员姓名", width: 100, dataIndex: 'employeeName'},
		{header: "职务", width: 100, dataIndex: 'job'},
//		{header: "设置开门权限", width: 100, dataIndex: 'opt'}
	];
	var secondGridStore = new Ext.data.JsonStore({
		fields : fields,
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=workEmployee'
		}),
		root: 'data'
	});
	var firstGrid = new Ext.grid.GridPanel({
		ddGroup          : 'secondGridDDGroup',
		store            : firstGridStore,
		bodyStyle: 'border-right:1px solid #99bbe8',
		columns          : cols,
		enableDragDrop   : true,
		stripeRows       : true,
		viewConfig: { forceFit:true},
		autoExpandColumn : 'employeeCode',
		title            : '班组未入网人员<span style="color:red;margin-left:20px">注：按住CTRL键可多选多人拖动</span>',
		height: 300,
		width: 400
	});
	
	var secondGrid = new Ext.grid.GridPanel({
		ddGroup          : 'firstGridDDGroup',
		store            : secondGridStore,
		columns          : cols2,
		viewConfig: { forceFit:true},
		enableDragDrop   : true,
		stripeRows       : true, 
		autoExpandColumn : 'employeeCode',
		title            : '已选择人员<span style="color:red;margin-left:20px">注：按住CTRL键可多选多人拖动</span>',
		height: 300,
		width: 430,
	});
	var addEmployeeMenu = {
		text: '人员维护',
		width: 85,
		iconCls: 'logo',
		disabled: false,
		handler: function() {
			firstGridStore.removeAll();
			secondGridStore.removeAll();
			addEmployeeForm.getForm().reset();
			addEmployeeWin.show();
			addEmployeeWin.center();
		}
	};
	// 编辑菜单表单
	var addEmployeeForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: 800,
		width: 850,
		labelAlign:"right",
		align:"center",
		defaultType: 'textfield',
		labelWidth: 290,
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			methodName: 'getAllRailWork2',
			fieldLabel: '工单',
			labelStyle: 'padding-left: 5px',
			labelAlign: 'right',
			hiddenName: 'workCodeCb',
			blankText: '请选择',
			width:300,
			editable: false,
			allowBlank: false,
			emptyText: "--请选择工单--",
			listeners : {'select' : function() {
				var workCode = addEmployeeForm.getForm().findField("workCodeCb").getValue();
				Ext.Ajax.request({
					url : 'T130103Action_getWorkInfoData.asp',
					params : {
						workCode : workCode
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success){
							var teamId = rspObj.msg.workTeam;
							firstGridStore.load({            
								params : {               
									start : 0,               
									teamId : teamId,
									workCode : workCode
								}
							});
							secondGridStore.load({            
								params : {               
									start : 0,           
									workCode : workCode
								}
							});
						}
					}
				});
			}}
		},new Ext.Panel({
			title: '<center>请为该工单选择人员</center>',
			layout: 'table',
			items: [firstGrid,secondGrid]
		})]
	});
	// 编辑测试
	var addEmployeeWin = new Ext.Window({
		title: '人员维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 850,
		autoHeight: true,
		layout: 'fit',
		items: [addEmployeeForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '提交',
			handler: function() {//alert(secondGrid.getStore().getCount());
				var workCode = addEmployeeForm.getForm().findField("workCodeCb").getValue();
				if(secondGrid.getStore().getCount() == 0) {
					showConfirm('您确定要清除该工单所分配的员工吗？',secondGrid,function(bt) {
						if(bt == 'yes') {
							Ext.Ajax.request({
								url : 'T130101Action.asp?method=delWorkEmployee',
								params : {
									workCode : workCode,
									txnId: '130101',
									subTxnId: '00'
								},
								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success){
//										Ext.MessageBox.alert('操作提示', '员工分配成功!');
										showSuccessMsg('员工分配成功!',grid);//showErrorMsg(action.result.msg,addMhForm)
										firstGridStore.removeAll();
										secondGridStore.removeAll();
										addEmployeeForm.getForm().reset();
										addEmployeeWin.hide();
										grid.getStore().reload();
										
									}else if(rspObj == "-1"){
										Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
									}
								}
							});
						}
					})
				} else {
					
					var records = secondGrid.getStore();
					var subParams=new Array();
					for(var i = 0; i < records.getCount(); i++){
						var record = records.getAt(i);
						var employeeCode = record.data.employeeCode;
						var subparam={
							workCode: workCode,
							employeeCode: employeeCode
						}
						subParams.push(subparam);
					}
					Ext.Ajax.request({
						url : 'T130101Action.asp?method=addWorkEmployee',
						params : {
							em : JSON.stringify(subParams),
							workCode: workCode,
							txnId: '130101',
							subTxnId: '01'
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success){
								showSuccessMsg('员工分配成功!',grid);//showErrorMsg(action.result.msg,addMhForm)
								firstGridStore.removeAll();
								secondGridStore.removeAll();
								addEmployeeForm.getForm().reset();
								addEmployeeWin.hide();
								grid.getStore().reload();
							}else if(rspObj == "-1"){
								Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
							}
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				var workCode = addEmployeeForm.getForm().findField("workCodeCb").getValue();
				firstGridStore.load({            
					params : {               
						start : 0,               
						workCode : workCode
					}
				});
				secondGridStore.load({            
					params : {               
						start : 0,           
						workCode : workCode
					}
				});
			}
		},{
			text: '关闭',
			handler: function() {
				addEmployeeWin.hide();
			}
		}]
	});
	addEmployeeWin.on('show',function(){
		var selectedRec = grid.getSelectionModel().getSelected();
		addEmployeeWin.getEl().mask('加载中......');
		setTimeout(function() {
			addEmployeeWin.getEl().unmask();
		},600);
		var blankRecord =  Ext.data.Record.create(fields);
		var firstGridDropTargetEl =  firstGrid.getView().scroller.dom;
		var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
			ddGroup    : 'firstGridDDGroup',
			notifyDrop : function(ddSource, e, data){
				var records =  ddSource.dragData.selections;
				Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
				firstGrid.store.add(records);
				firstGrid.store.sort('employeeCode', 'ASC');
				return true
			}
		});

		var secondGridDropTargetEl = secondGrid.getView().scroller.dom;
		var secondGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
			ddGroup    : 'secondGridDDGroup',
			notifyDrop : function(ddSource, e, data){
				var records =  ddSource.dragData.selections;
				Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
				secondGrid.store.add(records);
				secondGrid.store.sort('employeeCode', 'ASC');
				return true
			}
		});
	});
	// 菜单集合
	var menuArr = new Array();
	
	var editMenu = {
		text : '修改',
		width : 60,
		iconCls : 'edit',
		disabled : true,
		handler : function() {
			updTermWin.show();
		}
	};
	var updTermForm = new Ext.form.FormPanel({
        frame: true,
        height: 100,
        width: 750,
        labelWidth: 85,
        waitMsgTarget: true,
        layout: 'column',
        items: [{
        	layout:'column',
            items: [{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'textnotnull',
       	            labelStyle: 'padding-left: 5px',
       	            fieldLabel: '工单标识*',
       	            id: 'whNo',
       	            name: 'whNo',
       	            width: 200
       	       }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'radiogroup',
					labelStyle: 'padding-left: 5px;',
					fieldLabel: '出入网状态*',
					width:200,
					id: 'whStatusGroup',
					items: [{
						name: 'whStatus',
						inputValue: '0',
						boxLabel: '未入网',
						checked: true
					}, {
						name: 'whStatus',
						inputValue: '1',
						boxLabel: '已入网'
					}, {
						name: 'whStatus',
						inputValue: '2',
						boxLabel: '已出网'
					}]
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '人员标识*',
					id: 'whName',
					name: 'whName',
					width:200,
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'combo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '关联班组*',
					id: 'whParent',
					hiddenName: 'whParent',
					blankText: '请选择关联班组',
					emptyText: "--请选择关联班组--",
					anchor: '90%',
					allowBlank: false,
					listeners: {
						'select': function() {
							
						}
					}
	        	},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'radiogroup',
						labelStyle: 'padding-left: 5px;',
						fieldLabel: '开门权限*',
						width:200,
						id: 'whStatusGroup1',
						items: [{
							name: 'whStatus1',
							inputValue: '0',
							boxLabel: '能开门',
							checked: true
						}, {
							name: 'whStatus1',
							inputValue: '1',
							boxLabel: '不能开门'
						}]
		        	}]
				}]
			}],
        }]
    });
	var updTermWin = new Ext.Window({
        title: '修改作业人员信息',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 750,
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
            }
        },{
            text: '关闭',
            handler: function() {
                updTermWin.hide();
            }
        }]
    });
	var acceptOpen = {
		text : '给予开门权限',
		width : 100,
		iconCls : 'accept',
		disabled : true,
		handler : function() {
			var	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null){
                showAlertMsg("没有选择记录",grid);
                return;
            }
            showConfirm('您确定要允许该员工有开门权限吗？',secondGrid,function(bt) {
            	if(bt=="yes"){
            		Ext.Ajax.request({
    					url : 'T130101Action.asp?method=update',
    					params : {
    						openSign:"0",//0能开门；1不能开门
    						workCode : selectedRecord.get('workCode'),
    						employeeCode : selectedRecord.get('employeeCode'),
							txnId: '130101',
							subTxnId: '02'
    					},
    					success : function(rsp, opt) {
    						var rspObj = Ext.decode(rsp.responseText);
    						if(rspObj.success) {
    							showSuccessMsg(rspObj.msg,grid);
    							grid.getStore().reload();
    						} else {
    							showErrorMsg(rspObj.msg,grid);
    						}
    					}
    				});
            	}
	            
            })
		}
	};
	var noAcceptOpen = {
    	text : '取消开门权限',
    	width : 100,
    	iconCls : 'stop',
    	disabled : true,
    	handler : function() {
    		var	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null){
                showAlertMsg("没有选择记录",grid);
                return;
            }
            showConfirm('您确定要取消该员工有开门权限吗？',secondGrid,function(bt) {
            	if(bt=="yes"){
            		Ext.Ajax.request({
    					url : 'T130101Action.asp?method=update',
    					params : {
    						openSign:"1",//0能开门；1不能开门
    						workCode : selectedRecord.get('workCode'),
    						employeeCode : selectedRecord.get('employeeCode'),
							txnId: '130101',
							subTxnId: '03'
    					},
    					success : function(rsp, opt) {
    						var rspObj = Ext.decode(rsp.responseText);
    						if(rspObj.success) {
    							showSuccessMsg(rspObj.msg,grid);
    							grid.getStore().reload();
    						} else {
    							showErrorMsg(rspObj.msg,grid);
    						}
    					}
    				});
            	}
	            
            })
    	}
    };
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
			xtype: 'dynamicCombo',
			methodName: 'getAllRailWork',
			fieldLabel: '选择工单',
			hiddenName: 'workCodeQu',
			blankText: '请选择工单',
			width:200,
			editable: false,
			allowBlank: true,
			emptyText: "--请选择工单--"
		},{
			xtype: 'textnotnull',
			fieldLabel: '调度号',
			width:200,
			id: 'dispatchCodeQu',
			name: 'dispatchCodeQu',
			allowBlank: true
			
		},{
			xtype: 'basecomboselect',
			baseParams: 'workStatusStore',
			fieldLabel: '工单状态',
			hiddenName: 'workStatusQu',
			width: 200
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
				var  workCode =  queryForm.getForm().findField("workCodeQu").getValue();//Ext.getCmp('workCodeQu').getValue(),
	            var workStatus =  queryForm.getForm().findField("workStatusQu").getValue();
	            var dispatchCode =  queryForm.getForm().findField("dispatchCodeQu").getValue();
				var param = "?a=1";
				if(workCode){
					param = param + "&workCode="+workCode;
				}
				if(workStatus){
					param = param + "&workStatus="+workStatus;
				}
				if(dispatchCode){
					param = param + "&dispatchCode="+dispatchCode;
				}
				window.location.href = Ext.contextPath +"/exportExcelT130101.asp"+param;
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
	
	var addMenu = {
		text : '添加',
		width : 60,
		iconCls : 'add',
		disabled : true,
		handler : function() {
			var	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null){
                showAlertMsg("没有选择记录",grid);
                return;
            }
//            Ext.getCmp('workCodeAddEm').setValue(selectedRecord.get('workCode'));
//            Ext.getCmp('workNameAddEm').setValue(selectedRecord.get('workName'));
			employeeTeamWin.show();
			employeeTeamWin.center();
		}
	};
	var employeeTeamForm = new Ext.Panel({
		frame: true,
        height: 400,
        width: 750,
        bodyStyle:'overflow-x:hidden;overflow-y:auto;',
        labelWidth: 85,
        waitMsgTarget: true,
        layout: 'fit',
        items: []
    });
	
	var employeeTeamWin = new Ext.Window({
        title: '添加作业人员',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 750,
        autoHeight: true,
        layout: 'fit',
        items: [employeeTeamForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'edit',
        resizable: false,
        buttons: [{
            text: '提交',
            handler: function() {
            	var emCbs = Ext.query("*[name=employeeCodeCb]");//Ext.get("employeeCodeCb");
            	var isopens = Ext.query("*[name=isopen]");
            	var ems=new Array();
            	for(var i=0;i<emCbs.length;i++){
            		if(emCbs[i].checked){
            			var em = {
            				emCode:emCbs[i].value,
            				isopen:isopens[i].checked,
            				teamCode: employeeTeamForm.getForm().findField("railTeam").getValue(),
            				workCode: Ext.getCmp('workCodeAddEm').getValue()
            			};
            			ems.push(em);
            		}
            	}
            	if(ems.length>0){ 
	            	Ext.Ajax.request({
						url : 'T130101Action_addWorkEmployee.asp',
						params : {
							em : JSON.stringify(ems)
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj == "00"){
//								Ext.MessageBox.alert('操作提示', '人员分配成功!');
								showSuccessMsg('人员分配成功!',grid);//showErrorMsg(action.result.msg,addMhForm)
								grid.getStore().reload();
							}else if(rspObj == "-1"){
								Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
							}
						}
					});
            	}else{
            		Ext.MessageBox.alert('操作提示', '请为当前工单选择员工');
            	}
            }
           
        },{
            text: '关闭',
            handler: function() {
            	employeeTeamWin.hide();
            }
        }]
    });
	
	menuArr.push(addEmployeeMenu);
	menuArr.push('-');
	menuArr.push(acceptOpen);
	menuArr.push('-');
	menuArr.push(noAcceptOpen);
	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=workEmployees'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
        	//workCode,dispatchCode,workName,workTeamName,workName,employeeName,openSign,infoSign,addDate
            {name: 'workCode',mapping: 'workCode'},
            {name: 'dispatchCode',mapping: 'dispatchCode'},
            {name: 'workName',mapping: 'workName'},
            {name: 'workStatus',mapping: 'workStatus'},
            {name: 'workTeamName',mapping: 'workTeamName'},
            {name: 'employeeCode',mapping: 'employeeCode'},
            {name: 'employeeName',mapping: 'employeeName'},
			{name: 'openSign',mapping: 'openSign'},
			{name: 'infoSign',mapping: 'infoSign'},
            {name: 'addDate',mapping: 'addDate'}
        ])
    });
	termStore.load();
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            workCode: queryForm.getForm().findField("workCodeQu").getValue(),//Ext.getCmp('workCodeQu').getValue(),
            workStatus: queryForm.getForm().findField("workStatusQu").getValue(),
            dispatchCode:Ext.getCmp('dispatchCodeQu').getValue()
        });
    });
  //0计划号、1调度号、2工单名称、3班组、4人员编码、5姓名、6开门权限、7人员状态、8添加时间
  //workCode,dispatchCode,workName,workTeamName,employeeName,openSign,infoSign,addDate
	var termColModel = new Ext.grid.ColumnModel([
//    	new Ext.grid.RowNumberer(),
    	{header: '工单编码',dataIndex: 'workCode',sortable: true,width: 120},
		{header: '调度号',dataIndex: 'dispatchCode',sortable: true,width: 100},
		{header: '工单名称',dataIndex: 'workName',sortable: true,width: 160},
		{header: '工单状态',dataIndex: 'workStatus',sortable: true,width: 160},
		{header: '班组',dataIndex: 'workTeamName',sortable: true,width: 120},
		{header: '人员编码',dataIndex: 'employeeCode',sortable: true,width: 120},
		{header: '人员名称',dataIndex: 'employeeName',sortable: true,width: 120},
		{header: '开门权限',dataIndex: 'openSign',sortable: true,width: 120},
		{header: '人员状态',dataIndex: 'infoSign',sortable: true,width: 120},
		{header: '添加时间',dataIndex: 'addDate',sortable: true,width: 150}
    ]);
	// 仓库信息列表
    var grid = new Ext.grid.GridPanel({
        title: '作业单元管理',
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