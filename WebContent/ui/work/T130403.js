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
			url: 'gridPanelStoreAction.asp?storeId=nullteamEmployee'
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
			url: 'gridPanelStoreAction.asp?storeId=teamEmployee'
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
		title            : '未选择人员<span style="color:red;margin-left:20px">注：按住CTRL键可多选多人拖动</span>',
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
		text: '班组人员管理',
		width: 85,
		iconCls: 'logo',
		disabled: true,
		handler: function() {
			var rec = grid.getSelectionModel().getSelected();
			var teamCode = rec.get('id');
			var dept = rec.get('dept');
			firstGridStore.load({            
				params : {               
					start : 0,               
					teamId : teamCode,
					dept:dept
				}
			});
			secondGridStore.load({            
				params : {               
					start : 0,           
					teamId : teamCode
				}
			});
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
		items: [new Ext.Panel({
//			title: '<center>请为该班组选择人员</center>',
			layout: 'table',
			items: [firstGrid,secondGrid]
		})]
	});
	// 编辑测试
	var addEmployeeWin = new Ext.Window({
		title: '班组人员管理',
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
			handler: function() {
//				var teamCode = addEmployeeForm.getForm().findField("teamCodeCb").getValue();
				var rec = grid.getSelectionModel().getSelected();
				var teamCode = rec.get('id');
				if(secondGrid.getStore().getCount() == 0) {
					showConfirm('您确定要清除该班组的员工吗？',secondGrid,function(bt) {
						if(bt == 'yes') {
							Ext.Ajax.request({
								url : 'T130403Action.asp?method=delTeamEmployee',
								params : {
									teamId : teamCode
								},
								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success){
//										Ext.MessageBox.alert('操作提示', '添加人员成功!');
										showSuccessMsg('添加人员成功!',grid);//showErrorMsg(action.result.msg,addMhForm)
										firstGrid.removeAll();
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
							teamId: teamCode,
							employeeCode: employeeCode
						}
						subParams.push(subparam);
					}
					Ext.Ajax.request({
						url : 'T130403Action.asp?method=addTeamEmployee',
						params : {
							em : JSON.stringify(subParams)
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success){
//								Ext.MessageBox.alert('操作提示', '添加人员成功!');
								showSuccessMsg('添加人员成功!',grid);//showErrorMsg(action.result.msg,addMhForm)
								firstGrid.removeAll();
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
//				var teamCode = addEmployeeForm.getForm().findField("teamCodeCb").getValue();
				var rec = grid.getSelectionModel().getSelected();
				var teamCode = rec.get('id');
				firstGridStore.load({            
					params : {               
						start : 0,               
						teamId : teamCode
					}
				});
				secondGridStore.load({            
					params : {               
						start : 0,           
						teamId : teamCode
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
	var addMenu = {
		text: '新增班组',
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
					showConfirm('确定要删除该班组吗？班组id：' + railConstOrgid,grid,function(bt) {
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T130403Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj == "00"){
//										Ext.MessageBox.alert('操作提示', railConstOrgid+'班组已成功删除!');
										showSuccessMsg('班组已成功删除!',grid);//showErrorMsg(action.result.msg,addMhForm)
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
	        		Ext.Msg.alert('提示', '请选择一个班组！');
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
					url : 'T130403Action_getData.asp',
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
				            Ext.getCmp('workTeamNameUp').setValue(rspObj.msg.workTeamName);
				            updTermForm.getForm().findField("deptUp").setValue(rspObj.msg.dept);
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
	       	            fieldLabel: '班组id*',
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
						fieldLabel: '班组名称*',
						id: 'workTeamNameUp',
						name: 'workTeamNameUp',
						maxLength: 80,
						width:300,
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'deptUp',
					name: 'deptUp',
	       			items: [{
	       				xtype: 'dynamicCombo',
				        methodName: 'getBranchId12',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '所属机构*',
						hiddenName: 'deptUp',
						allowBlank: false,
						editable: false,
						width:300,
						emptyText: "--请选择所属机构--"
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       	            xtype: 'radiogroup',
	       	            width:300,
	       	            fieldLabel: '班组状态*',
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
				}],
	        }]
	    });
		var updTermWin = new Ext.Window({
	        title: '修改班组信息',
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
	            text: '提交',
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
							url: 'T130403Action.asp?method=edit',
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
								workTeamName: Ext.getCmp('workTeamNameUp').getValue(),
								dept: updTermForm.getForm().findField("deptUp").getValue(),
								enableStatus:asVal
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
						showConfirm('确定要启用该班组吗？班组id：' + railConstOrgid,grid,function(bt) {
							if(bt == "yes") {
								Ext.Ajax.requestNeedAuthorise({
									url: 'T130403Action.asp?method=open',
									success: function(rsp,opt) {
//										Ext.MessageBox.alert('操作提示', '处理成功,已启用');
										showSuccessMsg('处理成功，已启用!',grid);//showErrorMsg(action.result.msg,addMhForm)
										grid.getStore().reload();
									},
									params: { 
										id:railConstOrgid
									}
								});
							}
						});
		        	}else{
		        		Ext.Msg.alert('提示', '请选择一个班组！');
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
						showConfirm('确定要停用该班组吗？班组id：' + railConstOrgid,grid,function(bt) {
							if(bt == "yes") {
								Ext.Ajax.requestNeedAuthorise({
									url: 'T130403Action.asp?method=close',
									success: function(rsp,opt) {
//										Ext.MessageBox.alert('操作提示', '处理成功,已停用');
										showSuccessMsg('处理成功，已停用!',grid);//showErrorMsg(action.result.msg,addMhForm)
										grid.getStore().reload();
									},
									params: { 
										id: railConstOrgid
									}
								});
							}
						});
		        	}else{
		        		Ext.Msg.alert('提示', '请选择一个班组！');
		        	}
					//window.location.href = Ext.contextPath+ '/page/mchnt/T2010102.jsp?mchntId='+ mchntGrid.getSelectionModel().getSelected().get('mchtNo');
				}
		};
		var detailMenu = {
				text : '查看班组人员',
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
		            var rec = grid.getSelectionModel().getSelected();
					var employeeNum = rec.get('employeeNum');
					if(employeeNum == 0)
		            {
		                showAlertMsg("该班组还没有添加人员！",grid);
		                return;
		            }
		            Ext.Ajax.request({
		            	url : 'T130403Action_getData.asp',
						params : {
							id : selectedRecord.get('id')
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								teamEmployeeStore.load();
								detTermWin.show();
								detTermWin.center();
//					        	Ext.getCmp('idDet').disable();
//								Ext.getCmp('idDet').setValue(rspObj.msg.id);
//					            Ext.getCmp('workTeamNameDet').setValue(rspObj.msg.workTeamName);
//					            Ext.getCmp('enableStatusDet').setValue(enableStatusRender(rspObj.msg.enableStatus));
					        } else {
								showErrorMsg(rspObj.msg,grid);
							}
						}
					});
				}
			};
		/**
		 * 人员性别
		 */
		function sexRender(val) {
			if(val == '男') {
				return '<img src="' + Ext.contextPath + '/ext/resources/images/male.png" />';
			} else if(val == '女') {
				return '<img src="' + Ext.contextPath + '/ext/resources/images/female.png" />';
			}
			return val;
		}
		var teamEmployeeModel = new Ext.grid.ColumnModel([
			{header: '人员名称',dataIndex: 'employeeName',width: 80},
			{header: '性别',dataIndex: 'sex',renderer:sexRender,width: 50},
			{header: '年龄',dataIndex: 'birthday',width: 100},
			{header: '身份证号',dataIndex: 'idNumber',width: 150},
			{header: '照片地址',dataIndex: 'employeeImg',width: 150},
			{header: '作业单位',dataIndex: 'constOrg',width: 100},
			{header: '职务',dataIndex: 'job',width: 100},
			{header: '人员类型',dataIndex: 'employeeType',width: 80},
			{header: '联系方式',dataIndex: 'employeeTel',width: 100},
			{header: '所属机构',dataIndex: 'dept',width: 120}
		]);
		var teamEmployeeStore = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
	            url: 'gridPanelStoreAction.asp?storeId=teamEmployees'
	        }),
	        reader: new Ext.data.JsonReader({
	            root: 'data',
	            totalProperty: 'totalCount'
	        },[
	        	{name: 'employeeCode',mapping: 'employeeCode'},
	            {name: 'employeeName',mapping: 'employeeName'},
	            {name: 'sex',mapping: 'sex'},
				{name: 'birthday',mapping: 'age'},
				{name: 'idNumber',mapping: 'idNumber'},
				{name: 'employeeImg',mapping: 'employeeImg'},
				{name: 'constOrg',mapping: 'constOrgName'},
				{name: 'job',mapping: 'job'},
				{name: 'employeeType',mapping: 'typeName'},
				{name: 'employeeTel',mapping: 'employeeTel'},
				{name: 'password',mapping: 'password'},
				{name: 'entryDate',mapping: 'entryDate'},
				{name: 'dept',mapping: 'brhName'}
			])
		});
			
		teamEmployeeStore.on('beforeload', function() {
			var	selectedWork = grid.getSelectionModel().getSelected();
	        Ext.apply(this.baseParams, {
	            start: 0,
	            workTeam: selectedWork.get("id")
	        });
	    });	
		var teamEmployeeGrid = new Ext.grid.GridPanel({
//			title:'机料列表',
			store: teamEmployeeStore,
			height: 320,
			width: 890,
			colModel: teamEmployeeModel,
			loadMask: {
				msg: '加载中......'
			},
	        bbar: new Ext.PagingToolbar({
	            store: teamEmployeeStore,
	            pageSize: System[QUERY_RECORD_COUNT],
	            displayInfo: true,
	            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
	            emptyMsg: '没有找到符合条件的记录'
	        })
		});
			var detTermForm = new Ext.form.FormPanel({
		        frame: true,
		        height: 330,
		        width: 900,
		        labelWidth: 85,
		        waitMsgTarget: true,
		        items: [teamEmployeeGrid]
		    });
			var detTermWin = new Ext.Window({
		        title: '查看班组成员',
		        initHidden: true,
		        header: true,
		        frame: true,
		        closable: false,
		        modal: true,
		        width: 920,
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
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			id: 'workTeamNamefind',
			name: 'workTeamNamefind',
			fieldLabel: '班组名称',
			width:300
		},{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
        	id: 'deptfind',
			name: 'deptfind',
   			items: [{
   				xtype: 'dynamicCombo',
		        methodName: 'getBranchId12',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '所属机构',
				hiddenName: 'deptfind',
				allowBlank: true,
				editable: false,
				width:300,
				emptyText: "--请选择所属机构--"
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
                grid.getTopToolbar().items.items[6].disable();
                grid.getTopToolbar().items.items[8].disable();
                grid.getTopToolbar().items.items[10].disable();
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
				var workTeamName =  Ext.getCmp('workTeamNamefind').getValue();
				var param = "?a=1";
				if(workTeamName){
					param = param + "&workTeamName="+workTeamName;
				}
				var dept = queryForm.getForm().findField("deptfind").getValue();
				if(dept){
					param = param + "&dept="+dept;
				}
				window.location.href = Ext.contextPath +"/exportExcelT130403.asp"+param;
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
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '班组名称*',
					id: 'workTeamName',
					name: 'workTeamName',
					maxLength: 80,
					width:300,
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	        	id: 'dept',
				name: 'dept',
       			items: [{
       				xtype: 'dynamicCombo',
			        methodName: 'getBranchId12',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '所属机构*',
					hiddenName: 'dept',
					allowBlank: false,
					editable: false,
					width:300,
					emptyText: "--请选择所属机构--"
	        	}]
			}],
	});
	
	//班组添加窗口
	var brhWin = new Ext.Window({
		title: '新增班组',
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
				url: 'T130403Action.asp?method=add',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
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
	menuArr.push(detailMenu);
	menuArr.push('-');
	menuArr.push(addEmployeeMenu);
	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=workTeam'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: ''
        },[
            {name: 'id',mapping: 'id'},
            {name: 'dept',mapping: 'dept'},
            {name: 'workTeamName',mapping: 'workTeamName'},
            {name: 'deptName',mapping: 'deptName'},
            {name: 'employeeNum',mapping: 'count'},
            {name: 'enableStatus',mapping: 'enableStatus'},
			{name: 'addUser',mapping: 'addUser'},
			{name: 'addDate',mapping: 'addDate'},
			{name: 'enStatus',mapping: 'enStatus'}
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
            workTeamName: Ext.getCmp('workTeamNamefind').getValue(),
            dept:queryForm.getForm().findField("deptfind").getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
//    	new Ext.grid.RowNumberer(),
		{id: 'id',header: '班组id',dataIndex: 'id',sortable: true,width: 120,hidden : true},
		{id: 'enStatus',header: '班组状态',dataIndex: 'enStatus',sortable: true,width: 80,hidden : true},
		{header: '机构代码',dataIndex: 'dept',sortable: true,width: 120,hidden : true},
		{header: '班组名称',dataIndex: 'workTeamName',sortable: true,width: 120},
		{header: '所属机构',dataIndex: 'deptName',sortable: true,width: 120},
		{header: '班组人数',dataIndex: 'employeeNum',sortable: true,width: 120},
		{header: '班组状态',dataIndex: 'enableStatus',sortable: true,width: 120},
		{header: '创建人',dataIndex: 'addUser',sortable: true,width: 120},
		{header: '创建时间',dataIndex: 'addDate',sortable: true,width: 150}
    ]);
	// 工单信息列表
    var grid = new Ext.grid.GridPanel({
        title: '班组管理',
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
                grid.getTopToolbar().items.items[10].enable();
                grid.getTopToolbar().items.items[12].enable();
            } else {
                grid.getTopToolbar().items.items[0].disable();
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[4].disable();
                grid.getTopToolbar().items.items[6].disable();
                grid.getTopToolbar().items.items[8].disable();
                grid.getTopToolbar().items.items[10].disable();
                grid.getTopToolbar().items.items[12].disable();
            }
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})