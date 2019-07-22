Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	
	var editBaseInfoMenu = {
		text : '修改基本信息',
		width : 60,
		iconCls : 'edit',
		disabled : true,
		handler : function() {
			var	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null){
                showAlertMsg("没有选择记录",grid);
                return;
            }
            var auditStatus = selectedRecord.get("auditStatus");
            if(auditStatus == "审核通过"){
                showAlertMsg("该工单已经审核过了！",grid);
                return;
            }
            Ext.Ajax.request({
				url : 'T130103Action_getWorkInfoData.asp',
				params : {
					workCode : selectedRecord.get('workCode')
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						editBasicInfoWin.show();
						editBasicInfoWin.center();
						Ext.getCmp('workCodeEdt').disable();
			            Ext.getCmp('workCodeEdt').setValue(rspObj.msg.workCode);
			            Ext.getCmp('workNameEdt').setValue(rspObj.msg.workName);
			            var constOrg = rspObj.msg.constOrg;
			            if(constOrg==0){
			            	editTermForm.getForm().findField("constOrgEdt").setValue("");
			            	
			            }else{			            	
			            	editTermForm.getForm().findField("constOrgEdt").setValue(rspObj.msg.constOrg);
			            }
			            Ext.getCmp('dispatchCodeEdt').setValue(rspObj.msg.dispatchCode);
//			            Ext.getCmp('registerTypeEdt').setValue(rspObj.msg.registerType);
			            Ext.getCmp('skylightStartEdt').setValue(rspObj.msg.skylightStartStr);
			            Ext.getCmp('skylightEndEdt').setValue(rspObj.msg.skylightEndStr);
			            
			            editTermForm.getForm().findField("accessInCodeEdt").setValue(rspObj.msg.accessInCode);
			            editTermForm.getForm().findField("accessOutCodeEdt").setValue(rspObj.msg.accessOutCode);
			            editTermForm.getForm().findField("whseCodeEdt").setValue(rspObj.msg.whseCode);
			            
			            Ext.getCmp('workPicEdt').setValue(rspObj.msg.workPic);
			            Ext.getCmp('workTelEdt').setValue(rspObj.msg.workTel);
			            Ext.getCmp('workCountEdt').setValue(rspObj.msg.workCount);
			            Ext.getCmp('inPatrolEdt').setValue(rspObj.msg.inPatrol);
			            Ext.getCmp('outPatrolEdt').setValue(rspObj.msg.outPatrol);
			            Ext.getCmp('workAddressEdt').setValue(rspObj.msg.workAddress);
			            Ext.getCmp('workMileageEdt').setValue(rspObj.msg.workMileage);
			            var emCount = rspObj.msg.employeeCount;
			            if(emCount==0){
			            	Ext.getCmp('employeeCountEdt').setValue("");
			            }else{
			            	Ext.getCmp('employeeCountEdt').setValue(rspObj.msg.employeeCount);
			            }
//			            Ext.getCmp('employeeCountEdt').setValue(rspObj.msg.employeeCount);
			            Ext.getCmp('workStandardEdt').setValue(rspObj.msg.workStandard);
			            Ext.getCmp('riskControlEdt').setValue(rspObj.msg.riskControl);
			            Ext.getCmp('lineLevelEdt').setValue(rspObj.msg.lineLevel);
			            Ext.getCmp('rowLevelEdt').setValue(rspObj.msg.rowLevel);
			            editTermForm.getForm().findField("maintenceTypeEdt").setValue(rspObj.msg.maintenceType);
			            
			            	Ext.getCmp('stationEdt').setValue(rspObj.msg.station);
			            
//			            editTermForm.getForm().findField("formOrgEdt").setValue(rspObj.msg.formOrg);
			            
			            Ext.getCmp('interphoneEdt').setValue(rspObj.msg.interphone);
			            
			            var patrol = rspObj.msg.patrol;
			            if(patrol==0){
			            	editTermForm.getForm().findField("patrolEdt").setValue("");
			            }else{
			            	editTermForm.getForm().findField("patrolEdt").setValue(rspObj.msg.patrol);
			            }
//			            editTermForm.getForm().findField("patrolEdt").setValue(rspObj.msg.patrol);
			            Ext.getCmp('regStationEdt').setValue(rspObj.msg.regStation);
			            Ext.getCmp('residentLiaisonEdt').setValue(rspObj.msg.residentLiaison);
			            Ext.getCmp('residentStationEdt').setValue(rspObj.msg.residentStation);
			            Ext.getCmp('residentOnlineEdt').setValue(rspObj.msg.residentOnline);
			            
			            editTermForm.getForm().findField("workTeamEdt").setValue(rspObj.msg.workTeam);
			            editTermForm.getForm().findField("targetingEmployeeCodeEdt").setValue(rspObj.msg.targetingEmployeeCode);
			            
			            editTermForm.getForm().findField("deptEdt").setValue(rspObj.msg.dept);
			            Ext.getCmp('applyMsgEdt').setValue(rspObj.msg.applyMsg);
//			            Ext.getCmp('auditStatusEdt').setValue(rspObj.msg.auditStatus);
//			            Ext.getCmp('auditMsgEdt').setValue(rspObj.msg.auditMsg);
//			            Ext.getCmp('auditUserEdt').setValue(rspObj.msg.auditUser);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				}
			});
		}
	};
	
	
	
	var inStationToolsMenu = {
			text : '进出高铁机具料清单',
			width : 60,
			iconCls : 'query',
			disabled : true,
			handler : function() {
	            var	selectedRecord = grid.getSelectionModel().getSelected();
	            if(selectedRecord == null){
	                showAlertMsg("没有选择记录",grid);
	                return;
	            }
	            Ext.Ajax.request({
					url : 'T130103Action_getWorkInfoData.asp',
					params : {
						workCode : selectedRecord.get('workCode')
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							inStationToolsStore.load();
							inStationToolsWin.show();
							inStationToolsWin.center();
				            Ext.getCmp('workCodeDet3').setValue(rspObj.msg.workCode);
				            Ext.getCmp('workNameDet3').setValue(rspObj.msg.workName);
				            Ext.getCmp('dispatchCodeDet3').setValue(rspObj.msg.dispatchCode);
				            Ext.getCmp('workTeamDet3').setValue(rspObj.msg.teamName);
				            Ext.getCmp('skylightStartDet3').setValue(rspObj.msg.skylightStartStr);
				            Ext.getCmp('skylightEndDet3').setValue(rspObj.msg.skylightEndStr);
				            Ext.getCmp('workPicDet3').setValue(rspObj.msg.workPic);
				            Ext.getCmp('inPatrolDet3').setValue(rspObj.msg.inPatrol);
				            Ext.getCmp('outPatrolDet3').setValue(rspObj.msg.outPatrol);
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					}
				});
			}
		};
		var inStationToolsForm = new Ext.form.FormPanel({
			frame: true,
	        height: 110,
	        width: 950,
	        waitMsgTarget: true,
	        items: [{
	        	layout:'column',
	            items: [{
	        		columnWidth: .3,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       	            xtype: 'displayfield',
	       	            labelStyle: 'padding-left: 5px;width:130px',
	       	            fieldLabel: '工单编码',
	       	            id: 'workCodeDet3',
	       	            name: 'workCodeDet3',
	       	            width: 150,
	       	       }]
				},{
	        		columnWidth: .3,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px;width:130px',
						fieldLabel: '工单名称',
						id: 'workNameDet3',
						name: 'workNameDet3',
						width: 150,
		        	}]
				},{
	        		columnWidth: .3,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px;width:130px;',
						fieldLabel: '调度号',
						width: 150,
						id: 'dispatchCodeDet3',
						name: 'dispatchCodeDet3'
		        	}]
				},{
	        		columnWidth: .3,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px;width:130px;',
						fieldLabel: '班组',
						width: 150,
						id: 'workTeamDet3',
						name: 'workTeamDet3'
		        	}]
				},{
	        		columnWidth: .3,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px;width:130px;',
						fieldLabel: '天窗作业开始时间',
						width: 150,
						id: 'skylightStartDet3',
						name: 'skylightStartDet3'
		        	}]
				},{
	        		columnWidth: .3,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px;width:130px;',
						fieldLabel: '天窗作业结束时间',
						width: 150,
						id: 'skylightEndDet3',
						name: 'skylightEndDet3'
		        	}]
				},{
	        		columnWidth: .3,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px;width:130px;',
						fieldLabel: '作业负责人',
						width: 150,
						id: 'workPicDet3',
						name: 'workPicDet3'
		        	}]
				},{
	        		columnWidth: .3,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px;width:130px;',
						fieldLabel: '进门巡护确认人',
						width: 150,
						id: 'inPatrolDet3',
						name: 'inPatrolDet3'
		        	}]
				},{
	        		columnWidth: .3,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px;width:130px;',
						fieldLabel: '出门巡护确认人',
						width: 150,
						id: 'outPatrolDet3',
						name: 'outPatrolDet3'
		        	}]
				}],
	        }]
	    });
		var inStationToolsModel = new Ext.grid.ColumnModel([
			{header: '工具型号', dataIndex: 'note2'},
			{header: '工具名称', dataIndex: 'toolName'},
			{header: '工具单位', dataIndex: 'toolUnit'},
			{header: '当前状态', dataIndex: 'infoSign'},
			{header: '出库房数量', dataIndex: 'infoSign1'},
			{header: '入网数量', dataIndex: 'infoSign2'},
			{header: '出网数量', dataIndex: 'infoSign3'},
			{header: '入库数量', dataIndex: 'infoSign4'}
		]);
		var inStationToolsStore = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
	            url: 'gridPanelStoreAction.asp?storeId=worktoolsSeeList'
	        }),
	        reader: new Ext.data.JsonReader({
	            root: 'data',
	            totalProperty: 'totalCount'
	        },[
	        	{name: 'note2'},
				{name: 'toolName'},
				{name: 'toolUnit'},
				{name: 'infoSign'},
				{name: 'infoSign1'},
				{name: 'infoSign2'},
				{name: 'infoSign3'},
				{name: 'infoSign4'}
			])
		});
			
		inStationToolsStore.on('beforeload', function() {
			var	selectedWork = grid.getSelectionModel().getSelected();
	        Ext.apply(this.baseParams, {
	            start: 0,
	            workCode: selectedWork.get("workCode")
	        });
	    });	
		var inStationToolsGrid = new Ext.grid.GridPanel({
			title:'机料列表',
			store: inStationToolsStore,
			height: 300,
			width: 863,
			colModel: inStationToolsModel,
			loadMask: {
				msg: '加载中......'
			},
	        bbar: new Ext.PagingToolbar({
	            store: inStationToolsStore,
	            pageSize: System[QUERY_RECORD_COUNT],
	            displayInfo: true,
	            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
	            emptyMsg: '没有找到符合条件的记录'
	        })
		});
		var inStationToolsPanel = new Ext.Panel({
			title : '工单基本信息',
			bodyStyle:'overflow-x:hidden;overflow-y:auto;',
			height:115,
			items : [inStationToolsForm,inStationToolsGrid]
		});
		var inStationToolsBoxPanel = new Ext.Panel({
			bodyStyle:'overflow-x:hidden;overflow-y:auto;',
			region : 'center',
			margins : '5 0 0 0',
			height:330,
			layout : 'fit',
			items : [inStationToolsPanel]

		});
		var inStationToolsWin = new Ext.Window({
	        title: '进出高铁机具料清单',
	        initHidden: true,
	        header: true,
	        frame: true,
	        closable: false,
	        modal: true,
	        width: 900,
	        height:500,
	        autoHeight: false,
	        layout: 'fit',
	        items: [inStationToolsPanel],
	        buttonAlign: 'center',
	        closeAction: 'hide',
	        iconCls: 'edit',
	        resizable: false,
	        buttons: [{
	            text: '关闭',
	            handler: function() {
	            	inStationToolsWin.hide();
	            }
	        }]
	    });
	
	
	
	var inStationEmMenu = {
		text : '进出高铁作业人员名单',
		width : 60,
		iconCls : 'query',
		disabled : true,
		handler : function() {
            var	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null){
                showAlertMsg("没有选择记录",grid);
                return;
            }
            Ext.Ajax.request({
				url : 'T130103Action_getWorkInfoData.asp',
				params : {
					workCode : selectedRecord.get('workCode')
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						inStationEmStore.load();
						inStationEmWin.show();
			            inStationEmWin.center();
			            Ext.getCmp('workCodeDet4').setValue(rspObj.msg.workCode);
			            Ext.getCmp('workNameDet4').setValue(rspObj.msg.workName);
			            Ext.getCmp('dispatchCodeDet4').setValue(rspObj.msg.dispatchCode);
			            Ext.getCmp('workTeamDet4').setValue(rspObj.msg.teamName);
			            Ext.getCmp('skylightStartDet4').setValue(rspObj.msg.skylightStartStr);
			            Ext.getCmp('skylightEndDet4').setValue(rspObj.msg.skylightEndStr);
			            Ext.getCmp('workPicDet4').setValue(rspObj.msg.workPic);
			            Ext.getCmp('inPatrolDet4').setValue(rspObj.msg.inPatrol);
			            Ext.getCmp('outPatrolDet4').setValue(rspObj.msg.outPatrol);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				}
			});
		}
	};
	var inStationEmForm = new Ext.form.FormPanel({
		frame: true,
        height: 110,
        width: 900,
        waitMsgTarget: true,
        items: [{
        	layout:'column',
            items: [{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'displayfield',
       	            labelStyle: 'padding-left: 5px;width:130px',
       	            fieldLabel: '工单编码',
       	            id: 'workCodeDet4',
       	            name: 'workCodeDet4',
       	            width: 150,
       	       }]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:130px',
					fieldLabel: '工单名称',
					id: 'workNameDet4',
					name: 'workNameDet4',
					width: 150,
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:130px;',
					fieldLabel: '调度号',
					width: 150,
					id: 'dispatchCodeDet4',
					name: 'dispatchCodeDet4'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:130px;',
					fieldLabel: '班组',
					width: 150,
					id: 'workTeamDet4',
					name: 'workTeamDet4'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:130px;',
					fieldLabel: '天窗作业开始时间',
					width: 150,
					id: 'skylightStartDet4',
					name: 'skylightStartDet4'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:130px;',
					fieldLabel: '天窗作业结束时间',
					width: 150,
					id: 'skylightEndDet4',
					name: 'skylightEndDet4'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:130px;',
					fieldLabel: '作业负责人',
					width: 150,
					id: 'workPicDet4',
					name: 'workPicDet4'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:130px;',
					fieldLabel: '进门巡护确认人',
					width: 150,
					id: 'inPatrolDet4',
					name: 'inPatrolDet4'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:130px;',
					fieldLabel: '出门巡护确认人',
					width: 150,
					id: 'outPatrolDet4',
					name: 'outPatrolDet4'
	        	}]
			}],
        }]
    });
	var inStationEmModel = new Ext.grid.ColumnModel([
		{header: '人员姓名', dataIndex: 'employeeName'},
		{header: '性别', dataIndex: 'employeeSex'},
		{header: '年龄', dataIndex: 'employeeAge'},
		{header: '当前状态', dataIndex: 'employeeStatus'},
		{header: '点名人数', dataIndex: 'dmNum'},
		{header: '入网人数', dataIndex: 'inSignNum'},
		{header: '出网人数', dataIndex: 'outSignNum'}
	]);
	var inStationEmStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=workEmployeesSeeList'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
			{name: 'employeeName'},
			{name: 'employeeSex'},
			{name: 'employeeAge'},
			{name: 'employeeStatus'},
			{name: 'dmNum'},
			{name: 'inSignNum'},
			{name: 'outSignNum'}
		])
	});
		
	inStationEmStore.on('beforeload', function() {
		var	selectedWork = grid.getSelectionModel().getSelected();
        Ext.apply(this.baseParams, {
            start: 0,
            workCode: selectedWork.get("workCode")
        });
    });	
	var inStationEmGrid = new Ext.grid.GridPanel({
		title:'人员名单',
		store: inStationEmStore,
		height: 300,
		width: 890,
		colModel: inStationEmModel,
		loadMask: {
			msg: '加载中......'
		},
        bbar: new Ext.PagingToolbar({
            store: inStationEmStore,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
	});
	var inStationEmPanel = new Ext.Panel({
		title : '工单基本信息',
		bodyStyle:'overflow-x:hidden;overflow-y:auto;',
		height:115,
		items : [inStationEmForm,inStationEmGrid]
	});
	var inStationEmBoxPanel = new Ext.Panel({
		bodyStyle:'overflow-x:hidden;overflow-y:auto;',
		region : 'center',
		margins : '5 0 0 0',
		height:330,
		layout : 'fit',
		items : [inStationEmPanel]

	});
	var inStationEmWin = new Ext.Window({
        title: '进出高铁作业人员名单',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 910,
        height:500,
        autoHeight: false,
        layout: 'fit',
        items: [inStationEmPanel],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'edit',
        resizable: false,
        buttons: [{
            text: '关闭',
            handler: function() {
            	inStationEmWin.hide();
            }
        }]
    });
	
	
	
	
	var updTermForm = new Ext.form.FormPanel({
		frame: true,
        height: 500,
        width: 950,
        waitMsgTarget: true,
        layout: 'fit',
        items: [{
        	layout:'column',
            items: [{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'displayfield',
       	            labelStyle: 'padding-left: 5px;width:120px',
       	            fieldLabel: '工单编码',
       	            id: 'workCodeDet',
       	            name: 'workCodeDet',
       	            width: 150,
       	       }]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '工单名称',
					id: 'workNameDet',
					name: 'workNameDet',
					width: 150,
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业单位',
					width: 150,
					id: 'costOrgDet',
					name: 'costOrgDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '调度号',
					width: 150,
					id: 'dispatchCodeDet',
					name: 'dispatchCodeDet'
	        	}]
//			},{
//        		columnWidth: .3,
//	        	xtype: 'panel',
//	        	layout: 'form',
//       			items: [{
//		        	xtype: 'displayfield',
//					labelStyle: 'padding-left: 5px;width:120px;',
//					fieldLabel: '工单类型',
//					width: 150,
//					id: 'registerTypeDet',
//					name: 'registerTypeDet'
//	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '施工作业天窗开始',
					width: 150,
					id: 'skylightStartDet',
					name: 'skylightStartDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '施工作业天窗结束',
					width: 150,
					id: 'skylightEndDet',
					name: 'skylightEndDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '入网通道门禁',
					width: 150,
					id: 'accessInCodeDet',
					name: 'accessInCodeDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '出网通道门禁',
					width: 150,
					id: 'accessOutCodeDet',
					name: 'accessOutCodeDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业负责人',
					width: 150,
					id: 'workPicDet',
					name: 'workPicDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '负责人电话',
					width: 150,
					id: 'workTelDet',
					name: 'workTelDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业清点人',
					width: 150,
					id: 'workCountDet',
					name: 'workCountDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '进门巡护确认人',
					width: 150,
					id: 'inPatrolDet',
					name: 'inPatrolDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '出门巡护确认人',
					width: 150,
					id: 'outPatrolDet',
					name: 'outPatrolDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业地点',
					width: 150,
					id: 'workAddressDet',
					name: 'workAddressDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业里程',
					width: 150,
					id: 'workMileageDet',
					name: 'workMileageDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业人数',
					width: 150,
					id: 'employeeCountDet',
					name: 'employeeCountDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '工作量及质量标准',
					width: 150,
					id: 'workStandardDet',
					name: 'workStandardDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '风险控制措施',
					width: 150,
					id: 'riskControlDet',
					name: 'riskControlDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '线别',
					width: 150,
					id: 'lineLevelDet',
					name: 'lineLevelDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '行别',
					width: 150,
					id: 'rowLevelDet',
					name: 'rowLevelDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '维修类型',
					width: 150,
					id: 'maintenceTypeDet',
					name: 'maintenceTypeDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '站/区段',
					width: 150,
					id: 'stationDet',
					name: 'stationDet'
	        	}]
//			},{
//        		columnWidth: .3,
//	        	xtype: 'panel',
//	        	layout: 'form',
//       			items: [{
//		        	xtype: 'displayfield',
//					labelStyle: 'padding-left: 5px;width:120px;',
//					fieldLabel: '组织单位',
//					width: 150,
//					id: 'formOrgDet',
//					name: 'formOrgDet'
//	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '巡护中队',
					width: 150,
					id: 'patrolNameDet',
					name: 'patrolNameDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '登记站',
					width: 150,
					id: 'regStationDet',
					name: 'regStationDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '驻所联系人',
					width: 150,
					id: 'residentLiaisonDet',
					name: 'residentLiaisonDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '驻站联系人',
					width: 150,
					id: 'residentStationDet',
					name: 'residentStationDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '现场联系人',
					width: 150,
					id: 'residentOnlineDet',
					name: 'residentOnlineDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '对讲机联系频道',
					width: 150,
					id: 'interphoneDet',
					name: 'interphoneDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '班组',
					width: 150,
					id: 'workTeamDet',
					name: 'workTeamDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '仓库',
					width: 150,
					id: 'whseCodeDet',
					name: 'whseCodeDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '盯控干部',
					width: 150,
					id: 'targetingEmployeeNameDet',
					name: 'targetingEmployeeNameDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '所属机构',
					width: 150,
					id: 'brhNameDet',
					name: 'brhNameDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '申请事由',
					width: 150,
					id: 'applyMsgDet',
					name: 'applyMsgDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '审核状态',
					width: 150,
					id: 'auditStatusDet',
					name: 'auditStatusDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '审核意见',
					width: 150,
					id: 'auditMsgDet',
					name: 'auditMsgDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '审核时间',
					width: 150,
					id: 'auditDateDet',
					name: 'auditDateDet'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '审核人',
					width: 150,
					id: 'auditUserDet',
					name: 'auditUserDet'
	        	}]
			}],
        }]
    });
	var workInfoPanel = new Ext.Panel({
		title : '基本信息',
		height:500,
		items : [updTermForm],
	});
	function sexRender(sex) {// renderer: sexRender
		switch(sex) {
			case '1': return '男';
			case '2': return '女';
		}
	}
	var workEmployeeModel = new Ext.grid.ColumnModel([
		{header: '人员编码', dataIndex: 'employeeCode',width:80},
		{header: '人员姓名', dataIndex: 'employeeName'},
		{header: '性别', dataIndex: 'sex',renderer: sexRender,width:50},
//		{header: '出生日期', dataIndex: 'birthday',width:150},
		{header: '身份证号', dataIndex: 'idNumber',width:150},
		{header: '职务', dataIndex: 'job'},
		{header: '人员类型', dataIndex: 'typeName'},
		{header: '联系方式', dataIndex: 'employeeTel',width:150},
		{header: '是否在网', dataIndex: 'infoSign'}
	]);
	var workEmployeeStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=workEmployee'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
			{name: 'employeeCode'},
			{name: 'employeeName'},
			{name: 'sex'},
//			{name: 'birthday'},
			{name: 'idNumber'},
			{name: 'job'},
			{name: 'typeName'},
			{name: 'employeeTel'},
			{name: 'infoSign'}
		])
	});
		
	workEmployeeStore.on('beforeload', function() {
		var	selectedWork = grid.getSelectionModel().getSelected();
        Ext.apply(this.baseParams, {
            start: 0,
            workCode: selectedWork.get("workCode")
        });
    });	
	var workEmployeeGrid = new Ext.grid.GridPanel({
		title:'工单人员',
		store: workEmployeeStore,
		height: 300,
		width: 865,
		colModel: workEmployeeModel,
		loadMask: {
			msg: '加载中......'
		},
        bbar: new Ext.PagingToolbar({
            store: workEmployeeStore,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
	});
	
	
	
	var toolWorkPanel = new Ext.Panel({
		title : '工单工具',
		height : 100
	});
	//toolName,toolTypeName,toolNum
	var toolWorkModel = new Ext.grid.ColumnModel([
		{header: '工具型号', dataIndex: 'note2'},
		{header: '工具名称', dataIndex: 'toolName'},
		{header: '工具类型', dataIndex: 'toolTypeName'},
		{header: '状态', dataIndex: 'inWhse'}
	]);
	
	var toolWorkStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=workToolInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
        	{name: 'note2'},
        	{name: 'toolName'},
			{name: 'toolTypeName'},
			{name: 'inWhse'}
		])
	});
	toolWorkStore.on('beforeload', function() {
		var	selectedWork = grid.getSelectionModel().getSelected();
        Ext.apply(this.baseParams, {
            start: 0,
            workCode: selectedWork.get("workCode")
        });
    });
		
	var toolWorkGrid = new Ext.grid.GridPanel({
		title:'工单工具',
		store: toolWorkStore,
		height: 300,
		width: 865,
		colModel: toolWorkModel,
		loadMask: {
			msg: '加载中......'
		},
        bbar: new Ext.PagingToolbar({
            store: toolWorkStore,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
	});
	var infoBoxPanel = new Ext.Panel({
		bodyStyle:'overflow-x:hidden;overflow-y:auto;',
		region : 'center',
		margins : '5 0 0 0',
		height:380,
		layout : 'fit',
		items : [workInfoPanel,workEmployeeGrid,toolWorkGrid]

	});
	var editTermForm = new Ext.form.FormPanel({
		frame: true,
        width: 950,
        waitMsgTarget: true,
        layout: 'fit',
        items: [{
        	layout:'column',
            items: [{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'textnotnull',
       	            labelStyle: 'padding-left: 5px;width:120px',
       	            fieldLabel: '工单编码*',
       	            maxLength: 20,
       	            id: 'workCodeEdt',
       	            name: 'workCodeEdt',
       	            width: 300,
       	       }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '工单名称',
					id: 'workNameEdt',
					name: 'workNameEdt',
					maxLength: 80,
					width:300,
					allowBlank: true
	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
			        methodName: 'getAllRailConstOrg',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '作业单位',
					hiddenName: 'constOrgEdt',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择作业单位--"
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '调度号',
					width:300,
					id: 'dispatchCodeEdt',
					name: 'dispatchCodeEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
//			},{
//        		columnWidth: .5,
//	        	xtype: 'panel',
//	        	layout: 'form',
//       			items: [{
//       	            xtype: 'radiogroup',
//       	            width:300,
//       	            fieldLabel: '工单类型*',
//       	            labelStyle: 'padding-left: 5px;width:120px',
//       	            id:'registerTypeEdt',
//       	            name:'registerTypeEdt',
//       	            vertical: true,
//       	            items: [{
//						boxLabel: '同步', 
//						name: 'regTypeEdt', 
//						inputValue: 1
//					},{
//						boxLabel: 'OCR', 
//						name: 'regTypeEdt', 
//						inputValue: 2
//					},{
//						boxLabel: 'WEB', 
//						name: 'regTypeEdt', 
//						inputValue: 3
//					},{
//						boxLabel: '终端', 
//						name: 'regTypeEdt', 
//						inputValue: 4
//					}]
//       	        }]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
					xtype: 'datetimefield',
					width: 300,
					labelStyle: 'padding-left: 5px;width:120px',
					id: 'skylightStartEdt',
					name: 'skylightStartEdt',
					format: 'Y-m-d H:m:s',
       				altFormats: 'Y-m-d H:m:s',
					vtype: 'daterange',
					endDateField: 'skylightEndEdt',
					fieldLabel: '施工作业天窗开始*',
					editable: false,
					allowBlank: false
       			}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
					xtype: 'datetimefield',
					width: 300,
					labelStyle: 'padding-left: 5px;width:120px',
					id: 'skylightEndEdt',
					name: 'skylightEndEdt',
					format: 'Y-m-d H:m:s',
       				altFormats: 'Y-m-d H:m:s',
					vtype: 'daterange',
					startDateField: 'skylightStartEdt',
					fieldLabel: '施工作业天窗结束*',
					editable: false,
					allowBlank: false
				}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
			        methodName: 'getTdAccesses',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '入网通道门禁',
					hiddenName: 'accessInCodeEdt',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择入网通道门禁--"
	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
			        methodName: 'getTdAccesses',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '出网通道门禁',
					hiddenName: 'accessOutCodeEdt',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择出网通道门禁--"
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '作业负责人',
					width:300,
					id: 'workPicEdt',
					name: 'workPicEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '负责人电话',
					width:300,
					id: 'workTelEdt',
					name: 'workTelEdt',
					allowBlank: true,
					regexText:"请填写正确的联系方式！", 
					regex: /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|[1][3,4,5,7,8,9][0-9]{9})$/,///^[1][3,4,5,7,8,9][0-9]{9}$/
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '作业清点人',
					width:300,
					id: 'workCountEdt',
					name: 'workCountEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '进门巡护确认人',
					width:300,
					id: 'inPatrolEdt',
					name: 'inPatrolEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '出门巡护确认人',
					width:300,
					id: 'outPatrolEdt',
					name: 'outPatrolEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '作业地点',
					width:300,
					id: 'workAddressEdt',
					name: 'workAddressEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '作业里程',
					width:300,
					id: 'workMileageEdt',
					name: 'workMileageEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '作业人数',
					width:300,
					id: 'employeeCountEdt',
					name: 'employeeCountEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '工作量及质量标准',
					width:300,
					id: 'workStandardEdt',
					name: 'workStandardEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '风险控制措施',
					width:300,
					id: 'riskControlEdt',
					name: 'riskControlEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '线别',
					width:300,
					id: 'lineLevelEdt',
					name: 'lineLevelEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '行别',
					width:300,
					id: 'rowLevelEdt',
					name: 'rowLevelEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{//0维护，1维修
	   				xtype: 'basecomboselect',
//	   				store:maintenceTypeStore,
	   				baseParams : 'maintenceTypeStore',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '维修类型',
					hiddenName: 'maintenceTypeEdt',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择维修类型--"
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '站/区段',
					width:300,
					id: 'stationEdt',
					name: 'stationEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
//			},{
//				columnWidth: .5,
//	        	xtype: 'panel',
//	        	layout: 'form',
//	   			items: [{
//	   				xtype: 'dynamicCombo',
//			        methodName: 'getRailFormOrg',
//					labelStyle: 'padding-left: 5px;width:120px',
//					fieldLabel: '组织单位*',
//					hiddenName: 'formOrgEdt',
//					allowBlank: false,
//					editable: true,
//					width:300,
//					emptyText: "--请选择组织单位--"
//	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
			        methodName: 'getRailpatrol',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '巡护中队',
					hiddenName: 'patrolEdt',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择巡护中队--"
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '登记站',
					width:300,
					id: 'regStationEdt',
					name: 'regStationEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '驻所联系人',
					width:300,
					id: 'residentLiaisonEdt',
					name: 'residentLiaisonEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '驻站联系人',
					width:300,
					id: 'residentStationEdt',
					name: 'residentStationEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '现场联系人',
					width:300,
					id: 'residentOnlineEdt',
					name: 'residentOnlineEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '对讲机联系频道',
					width:300,
					id: 'interphoneEdt',
					name: 'interphoneEdt',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
					xtype: 'dynamicCombo',
					methodName: 'getAllRailTeam',
					fieldLabel: '班组*',
					labelStyle: 'padding-left: 5px;width:120px',
					labelAlign: 'right',
					hiddenName: 'workTeamEdt',
					blankText: '请选择班组',
					width:300,
					editable: false,
					allowBlank: false,
					emptyText: "--请选择班组--"
       			}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
    				xtype: 'dynamicCombo',
    				methodName: 'getAllWhse',
    				fieldLabel: '仓库*',
    				labelStyle: 'padding-left: 5px;width:120px',
    				labelAlign: 'right',
    				hiddenName: 'whseCodeEdt',
    				blankText: '请选择',
    				width:300,
    				editable: false,
					allowBlank: false,
    				emptyText: "--请选择仓库--",
    			}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
			        methodName: 'getRailDkEmployee',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '盯控干部',
					hiddenName: 'targetingEmployeeCodeEdt',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择盯控干部--"
	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
	   				methodName: 'getBranchId12',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '所属机构',
					hiddenName: 'deptEdt',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择所属机构--"
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '申请事由',
					width:300,
					id: 'applyMsgEdt',
					name: 'applyMsgEdt',
					maxLength: 100,
					allowBlank: true
	        	}]
			}],
        }]
    });
	var editBasicInfoWin = new Ext.Window({
        title: '修改基本信息',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 950,
        height:550,
        autoHeight: false,
        layout: 'fit',
        items: [editTermForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'edit',
        resizable: false,
        buttons: [{
            text: '提交',
            handler: function() {
            	if(editTermForm.getForm().isValid()) {
					var submitValues = editTermForm.getForm().getValues();  
					for (var param in submitValues) {  
						if (editTermForm.getForm().findField(param) && editTermForm.getForm().findField(param).emptyText == submitValues[param]) {  
							editTermForm.getForm().findField(param).setValue(' ');  
						}  
					}
					editTermForm.getForm().submit({
						url: 'T130100Action.asp?method=edit',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							editBasicInfoWin.hide();
							showSuccessMsg(action.result.msg,editTermForm);
							//editTermForm.reset();
							grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,editTermForm);
						},
						params: {
							workCode: Ext.getCmp('workCodeEdt').getValue(),
							workName: Ext.getCmp('workNameEdt').getValue(),
							constOrg: editTermForm.getForm().findField("constOrgEdt").getValue(),
							dispatchCode: Ext.getCmp('dispatchCodeEdt').getValue(),
//							registerType: rtVal,//Ext.getCmp('registerTypeEdt').getValue(),
							skylightStart: Ext.getCmp('skylightStartEdt').getValue(),
							skylightEnd: Ext.getCmp('skylightEndEdt').getValue(),
					            
							accessInCode: editTermForm.getForm().findField("accessInCodeEdt").getValue(),
							accessOutCode: editTermForm.getForm().findField("accessOutCodeEdt").getValue(),
					            
							workPic: Ext.getCmp('workPicEdt').getValue(),
							workTel: Ext.getCmp('workTelEdt').getValue(),
							workCount: Ext.getCmp('workCountEdt').getValue(),
							inPatrol: Ext.getCmp('inPatrolEdt').getValue(),
							outPatrol: Ext.getCmp('outPatrolEdt').getValue(),
							workAddress: Ext.getCmp('workAddressEdt').getValue(),
							workMileage: Ext.getCmp('workMileageEdt').getValue(),
							employeeCount: Ext.getCmp('employeeCountEdt').getValue(),
							workStandard: Ext.getCmp('workStandardEdt').getValue(),
							riskControl: Ext.getCmp('riskControlEdt').getValue(),
							lineLevel: Ext.getCmp('lineLevelEdt').getValue(),
							rowLevel: Ext.getCmp('rowLevelEdt').getValue(),
							maintenceType: editTermForm.getForm().findField("maintenceTypeEdt").getValue(),
							station: Ext.getCmp('stationEdt').getValue(),
					            
//							formOrg: editTermForm.getForm().findField("formOrgEdt").getValue(),
					            
							interphone: Ext.getCmp('interphoneEdt').getValue(),
					            
							patrol: editTermForm.getForm().findField("patrolEdt").getValue(),
							regStation: Ext.getCmp('regStationEdt').getValue(),
							residentLiaison: Ext.getCmp('residentLiaisonEdt').getValue(),
							residentStation: Ext.getCmp('residentStationEdt').getValue(),
							residentOnline: Ext.getCmp('residentOnlineEdt').getValue(),
					        workTeam: editTermForm.getForm().findField('workTeamEdt').getValue(), 
							targetingEmployeeCode: editTermForm.getForm().findField("targetingEmployeeCodeEdt").getValue(),
					        whseCode: editTermForm.getForm().findField('whseCodeEdt').getValue(), 
							dept: editTermForm.getForm().findField("deptEdt").getValue(),
							applyMsg: Ext.getCmp('applyMsgEdt').getValue(),
							txnId: '130104',
							subTxnId: '00'
						}
					});
				}
            }
        },{
            text: '取消',
            handler: function() {
            	editBasicInfoWin.hide();
            }
        }]
    });
	var refuseWin = new Ext.Window({
        title: '填写拒绝原因',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 310,
        height:150,
        autoHeight: true,
        layout: 'fit',
        items: [new Ext.form.FormPanel({
    		frame: true,
            height: 150,
            width: 310,
            waitMsgTarget: true,
            layout: 'fit',
            items: [{
	        	xtype: 'textarea',
				fieldLabel: '',
				width:300,
				height:150,
				id: 'auditMsgTxtArea',
				name: 'auditMsgTxtArea'
        	}]
    	})],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'edit',
        resizable: false,
        buttons: [{
            text: '提交',
            handler: function() {
            	var auditMsg = Ext.getCmp('auditMsgTxtArea').getValue();
            	if(auditMsg==""){
            		Ext.MessageBox.alert('提示', '请填写拒绝原因!');
            		return;
            	}
            	var	selectedRecord = grid.getSelectionModel().getSelected();
            	Ext.Ajax.request({
    				url : 'T130103Action_auditWorkData.asp',
    				params : {
    					workCode : selectedRecord.get('workCode'),
    					auditStatus : "2",
    					auditMsg : auditMsg
    				},
    				success : function(rsp, opt) {
    					var rspObj = Ext.decode(rsp.responseText);
						if(rspObj == "00"){
//							Ext.MessageBox.alert('操作提示', '操作成功!');
							showSuccessMsg('操作成功!',grid);//showErrorMsg(action.result.msg,addMhForm)
							refuseWin.hide();
							grid.getStore().reload();
						}else if(rspObj == "-1"){
							Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
							refuseWin.hide();
						}
    				}
    			});
            }
        },{
            text: '取消',
            handler: function() {
            	refuseWin.hide();
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
				url : 'T130103Action_getWorkInfoData.asp',
				params : {
					workCode : selectedRecord.get('workCode')
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						detailWin.show();
						detailWin.center();
						workEmployeeStore.load();
						toolWorkStore.load();
			            Ext.getCmp('workCodeDet').setValue(rspObj.msg.workCode);
			            Ext.getCmp('workNameDet').setValue(rspObj.msg.workName);
			            Ext.getCmp('costOrgDet').setValue(rspObj.msg.costOrgName);
			            Ext.getCmp('dispatchCodeDet').setValue(rspObj.msg.dispatchCode);
			            Ext.getCmp('skylightStartDet').setValue(rspObj.msg.skylightStartStr);
			            Ext.getCmp('skylightEndDet').setValue(rspObj.msg.skylightEndStr);
			            Ext.getCmp('accessInCodeDet').setValue(rspObj.msg.accessInName);
			            Ext.getCmp('accessOutCodeDet').setValue(rspObj.msg.accessOutName);
			            Ext.getCmp('workPicDet').setValue(rspObj.msg.workPic);
			            Ext.getCmp('workTelDet').setValue(rspObj.msg.workTel);
			            Ext.getCmp('workCountDet').setValue(rspObj.msg.workCount);
			            Ext.getCmp('inPatrolDet').setValue(rspObj.msg.inPatrol);
			            Ext.getCmp('outPatrolDet').setValue(rspObj.msg.outPatrol);
			            Ext.getCmp('workAddressDet').setValue(rspObj.msg.workAddress);
			            Ext.getCmp('workMileageDet').setValue(rspObj.msg.workMileage);
			            var emCount = rspObj.msg.employeeCount;
			            if(emCount==0){
			            	Ext.getCmp('employeeCountDet').setValue("");
			            }else{
			            	Ext.getCmp('employeeCountDet').setValue(rspObj.msg.employeeCount);
			            }
			            Ext.getCmp('workStandardDet').setValue(rspObj.msg.workStandard);
			            Ext.getCmp('riskControlDet').setValue(rspObj.msg.riskControl);
			            Ext.getCmp('lineLevelDet').setValue(rspObj.msg.lineLevel);
			            Ext.getCmp('rowLevelDet').setValue(rspObj.msg.rowLevel);
			            var maintenceType=rspObj.msg.maintenceType;
			            if(maintenceType==0){
			            	Ext.getCmp('maintenceTypeDet').setValue("施工");
			            }else if(maintenceType==1){
			            	Ext.getCmp('maintenceTypeDet').setValue("维修");
			            }else{
			            	Ext.getCmp('maintenceTypeDet').setValue("未知");
			            }
			            Ext.getCmp('stationDet').setValue(rspObj.msg.station);
//			            Ext.getCmp('formOrgDet').setValue(rspObj.msg.formOrgName);
			            Ext.getCmp('interphoneDet').setValue(rspObj.msg.interphone);
			            Ext.getCmp('patrolNameDet').setValue(rspObj.msg.patrolName);
			            Ext.getCmp('regStationDet').setValue(rspObj.msg.regStation);
			            Ext.getCmp('residentLiaisonDet').setValue(rspObj.msg.residentLiaison);
			            Ext.getCmp('residentStationDet').setValue(rspObj.msg.residentStation);
			            Ext.getCmp('residentOnlineDet').setValue(rspObj.msg.residentOnline);
			            Ext.getCmp('workTeamDet').setValue(rspObj.msg.teamName);
			            Ext.getCmp('whseCodeDet').setValue(rspObj.msg.whseName);
			            Ext.getCmp('targetingEmployeeNameDet').setValue(rspObj.msg.targetingEmployeeName);
			            Ext.getCmp('brhNameDet').setValue(rspObj.msg.brhName);
			            Ext.getCmp('applyMsgDet').setValue(rspObj.msg.applyMsg);
			            var adStatus = rspObj.msg.auditStatus;//0未审核;1审核通过；2审核不通过
			            if(adStatus==0){
			            	Ext.getCmp('auditStatusDet').setValue("未审核");
			            }else if(adStatus==1){
			            	Ext.getCmp('auditStatusDet').setValue("审核通过");
			            }else if(adStatus==2){
			            	Ext.getCmp('auditStatusDet').setValue("审核不通过");
			            }
			            Ext.getCmp('auditMsgDet').setValue(rspObj.msg.auditMsg);
			            Ext.getCmp('auditUserDet').setValue(rspObj.msg.auditUser);
			            Ext.getCmp('auditDateDet').setValue(rspObj.msg.auditDateStr);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				}
            })
		}
	};
	var detailWin = new Ext.Window({
        title: '查看详细信息',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 900,
        height:500,
        autoHeight: true,
        layout: 'fit',
        items: [infoBoxPanel],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'edit',
        resizable: false,
        buttons: [{
            text: '关闭',
            handler: function() {
            	detailWin.hide();
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
			xtype: 'basecomboselect',
			baseParams: 'workStatusStore',
			fieldLabel: '工单状态',
			hiddenName: 'workStatus',
			width: 200
		},{
			xtype: 'datefield',
			width: 200,
			id: 'dateStart',
			name: 'dateStart',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'dateEnd',
			fieldLabel: '开始时间',
			editable: false
		},{
			xtype: 'datefield',
			width: 200,
			id: 'dateEnd',
			name: 'dateEnd',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'dateStart',
			fieldLabel: '结束时间',
			editable: false
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
		       	var endtime=Ext.getCmp('dateEnd').getValue(),starttime=Ext.getCmp('dateStart').getValue();
            	if(endtime!=''&&starttime!=''&&endtime<starttime){
            		showErrorMsg("请保证截止日期不小于起始日期",queryWin);
    				return;
            	}
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
				   var dateStart =  typeof(queryForm.findById('dateStart').getValue()) == 'string' ? '' : queryForm.findById('dateStart').getValue().dateFormat('Y-m-d');
					var dateEnd =  typeof(queryForm.findById('dateEnd').getValue()) == 'string' ? '' : queryForm.findById('dateEnd').getValue().dateFormat('Y-m-d');
					
		            var workStatus=  queryForm.getForm().findField('workStatus').getValue();
				var param = "?a=1";
				if(dateStart){
					param = param + "&dateStart="+dateStart;
				}
				if(dateEnd){
					param = param + "&dateEnd="+dateEnd;
				}
				if(workStatus){
					param = param + "&workStatus="+workStatus;
				}
				window.location.href = Ext.contextPath +"/exportExcelT130104.asp"+param;
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
	
	
	
	
	
	
	
	var updTermForm2 = new Ext.form.FormPanel({
		frame: true,
        height: 500,
        width: 950,
        waitMsgTarget: true,
        layout: 'fit',
        items: [{
        	layout:'column',
            items: [{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'displayfield',
       	            labelStyle: 'padding-left: 5px;width:120px',
       	            fieldLabel: '工单编码',
       	            id: 'workCodeDet2',
       	            name: 'workCodeDet2',
       	            width: 150,
       	       }]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '工单名称',
					id: 'workNameDet2',
					name: 'workNameDet2',
					width: 150,
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业单位',
					width: 150,
					id: 'costOrgDet2',
					name: 'costOrgDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '调度号',
					width: 150,
					id: 'dispatchCodeDet2',
					name: 'dispatchCodeDet2'
	        	}]
//			},{
//        		columnWidth: .3,
//	        	xtype: 'panel',
//	        	layout: 'form',
//       			items: [{
//		        	xtype: 'displayfield',
//					labelStyle: 'padding-left: 5px;width:120px;',
//					fieldLabel: '工单类型',
//					width: 150,
//					id: 'registerTypeDet',
//					name: 'registerTypeDet'
//	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '施工作业天窗开始',
					width: 150,
					id: 'skylightStartDet2',
					name: 'skylightStartDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '施工作业天窗结束',
					width: 150,
					id: 'skylightEndDet2',
					name: 'skylightEndDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '入网通道门禁',
					width: 150,
					id: 'accessInCodeDet2',
					name: 'accessInCodeDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '出网通道门禁',
					width: 150,
					id: 'accessOutCodeDet2',
					name: 'accessOutCodeDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业负责人',
					width: 150,
					id: 'workPicDet2',
					name: 'workPicDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '负责人电话',
					width: 150,
					id: 'workTelDet2',
					name: 'workTelDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业清点人',
					width: 150,
					id: 'workCountDet2',
					name: 'workCountDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '进门巡护确认人',
					width: 150,
					id: 'inPatrolDet2',
					name: 'inPatrolDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '出门巡护确认人',
					width: 150,
					id: 'outPatrolDet2',
					name: 'outPatrolDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业地点',
					width: 150,
					id: 'workAddressDet2',
					name: 'workAddressDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业里程',
					width: 150,
					id: 'workMileageDet2',
					name: 'workMileageDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业人数',
					width: 150,
					id: 'employeeCountDet2',
					name: 'employeeCountDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '工作量及质量标准',
					width: 150,
					id: 'workStandardDet2',
					name: 'workStandardDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '风险控制措施',
					width: 150,
					id: 'riskControlDet2',
					name: 'riskControlDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '线别',
					width: 150,
					id: 'lineLevelDet2',
					name: 'lineLevelDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '行别',
					width: 150,
					id: 'rowLevelDet2',
					name: 'rowLevelDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '维修类型',
					width: 150,
					id: 'maintenceTypeDet2',
					name: 'maintenceTypeDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '站/区段',
					width: 150,
					id: 'stationDet2',
					name: 'stationDet2'
	        	}]
//			},{
//        		columnWidth: .3,
//	        	xtype: 'panel',
//	        	layout: 'form',
//       			items: [{
//		        	xtype: 'displayfield',
//					labelStyle: 'padding-left: 5px;width:120px;',
//					fieldLabel: '组织单位',
//					width: 150,
//					id: 'formOrgDet',
//					name: 'formOrgDet'
//	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '巡护中队',
					width: 150,
					id: 'patrolNameDet2',
					name: 'patrolNameDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '登记站',
					width: 150,
					id: 'regStationDet2',
					name: 'regStationDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '驻所联系人',
					width: 150,
					id: 'residentLiaisonDet2',
					name: 'residentLiaisonDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '驻站联系人',
					width: 150,
					id: 'residentStationDet2',
					name: 'residentStationDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '现场联系人',
					width: 150,
					id: 'residentOnlineDet2',
					name: 'residentOnlineDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '对讲机联系频道',
					width: 150,
					id: 'interphoneDet2',
					name: 'interphoneDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '班组',
					width: 150,
					id: 'workTeamDet2',
					name: 'workTeamDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '仓库',
					width: 150,
					id: 'whseCodeDet2',
					name: 'whseCodeDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '盯控干部',
					width: 150,
					id: 'targetingEmployeeNameDet2',
					name: 'targetingEmployeeNameDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '所属机构',
					width: 150,
					id: 'brhNameDet2',
					name: 'brhNameDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '申请事由',
					width: 150,
					id: 'applyMsgDet2',
					name: 'applyMsgDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '审核状态',
					width: 150,
					id: 'auditStatusDet2',
					name: 'auditStatusDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '审核意见',
					width: 150,
					id: 'auditMsgDet2',
					name: 'auditMsgDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '审核时间',
					width: 150,
					id: 'auditDateDet2',
					name: 'auditDateDet2'
	        	}]
			},{
        		columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px;width:120px',
					fieldLabel: '审核人',
					width: 150,
					id: 'auditUserDet2',
					name: 'auditUserDet2'
	        	}]
			}],
        }]
    });
	var workInfoPanel2 = new Ext.Panel({
		title : '基本信息',
		height:500,
		items : [updTermForm2],
	});

	var workEmployeeModel2 = new Ext.grid.ColumnModel([
		{header: '人员编码', dataIndex: 'employeeCode',width:80},
		{header: '人员姓名', dataIndex: 'employeeName'},
		{header: '性别', dataIndex: 'sex',renderer: sexRender,width:50},
//		{header: '出生日期', dataIndex: 'birthday',width:150},
		{header: '身份证号', dataIndex: 'idNumber',width:150},
		{header: '职务', dataIndex: 'job'},
		{header: '人员类型', dataIndex: 'typeName'},
		{header: '联系方式', dataIndex: 'employeeTel',width:150},
		{header: '是否在网', dataIndex: 'infoSign'}
	]);
	var workEmployeeStore2 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=workEmployee'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
			{name: 'employeeCode'},
			{name: 'employeeName'},
			{name: 'sex'},
//			{name: 'birthday'},
			{name: 'idNumber'},
			{name: 'job'},
			{name: 'typeName'},
			{name: 'employeeTel'},
			{name: 'infoSign'}
		])
	});
		
	workEmployeeStore2.on('beforeload', function() {
		var	selectedWork = grid.getSelectionModel().getSelected();
        Ext.apply(this.baseParams, {
            start: 0,
            workCode: selectedWork.get("workCode")
        });
    });	
	var workEmployeeGrid2 = new Ext.grid.GridPanel({
		title:'工单人员',
		store: workEmployeeStore2,
		height: 300,
		width: 865,
		colModel: workEmployeeModel2,
		id:'workEmployeeGrid2',
		loadMask: {
			msg: '加载中......'
		},
        bbar: new Ext.PagingToolbar({
            store: workEmployeeStore2,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
	});
	
	
	
	var toolWorkPanel2 = new Ext.Panel({
		title : '工单工具',
		height : 100
	});
	//toolName,toolTypeName,toolNum
	var toolWorkModel2 = new Ext.grid.ColumnModel([
		{header: '工具型号', dataIndex: 'note2'},
		{header: '工具名称', dataIndex: 'toolName'},
		{header: '工具类型', dataIndex: 'toolTypeName'},
		{header: '状态', dataIndex: 'inWhse'}
	]);
	
	var toolWorkStore2 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=workToolInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
        	{name: 'note2'},
        	{name: 'toolName'},
			{name: 'toolTypeName'},
			{name: 'inWhse'}
		])
	});
	toolWorkStore2.on('beforeload', function() {
		var	selectedWork = grid.getSelectionModel().getSelected();
        Ext.apply(this.baseParams, {
            start: 0,
            workCode: selectedWork.get("workCode")
        });
    });
		
	var toolWorkGrid2 = new Ext.grid.GridPanel({
		title:'工单工具',
		store: toolWorkStore2,
		height: 300,
		width: 865,
		colModel: toolWorkModel2,
		id:'toolWorkGrid2',
		loadMask: {
			msg: '加载中......'
		},
        bbar: new Ext.PagingToolbar({
            store: toolWorkStore2,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
	});
	var infoBoxPanel2 = new Ext.Panel({
		bodyStyle:'overflow-x:hidden;overflow-y:auto;',
		region : 'center',
		margins : '5 0 0 0',
		height:435,
		layout : 'fit',
		items : [workInfoPanel2,workEmployeeGrid2,toolWorkGrid2]

	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	var printWin = new Ext.Window({
		title: '打印',
		width: 900,
		height: 500,
		html: '<iframe />',
		resizable: false,
		items: [infoBoxPanel2],
		buttons: [{
			text: '打印',
		    handler: function(){
		    	printGrid();
		    }
		},{
			text: '取消打印',
			handler: function() {
				printWin.hide();
			}
		}]
	});
	var printMenu = {
		text : '打印工单',
		width : 100,
		iconCls : 'detail',
		disabled : true,
		handler : function() {
			var	selectedRecord = grid.getSelectionModel().getSelected();
			if(selectedRecord == null){
				showAlertMsg("没有选择记录",grid);
				return;
			}
			Ext.Ajax.request({
				url : 'T130103Action_getWorkInfoData.asp',
				params : {
					workCode : selectedRecord.get('workCode')
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						printWin.show();
						printWin.center();
						workEmployeeStore2.load();
						toolWorkStore2.load();
			            Ext.getCmp('workCodeDet2').setValue(rspObj.msg.workCode);
			            Ext.getCmp('workNameDet2').setValue(rspObj.msg.workName);
			            Ext.getCmp('costOrgDet2').setValue(rspObj.msg.costOrgName);
			            Ext.getCmp('dispatchCodeDet2').setValue(rspObj.msg.dispatchCode);
			            Ext.getCmp('skylightStartDet2').setValue(rspObj.msg.skylightStartStr);
			            Ext.getCmp('skylightEndDet2').setValue(rspObj.msg.skylightEndStr);
			            Ext.getCmp('accessInCodeDet2').setValue(rspObj.msg.accessInName);
			            Ext.getCmp('accessOutCodeDet2').setValue(rspObj.msg.accessOutName);
			            Ext.getCmp('workPicDet2').setValue(rspObj.msg.workPic);
			            Ext.getCmp('workTelDet2').setValue(rspObj.msg.workTel);
			            Ext.getCmp('workCountDet2').setValue(rspObj.msg.workCount);
			            Ext.getCmp('inPatrolDet2').setValue(rspObj.msg.inPatrol);
			            Ext.getCmp('outPatrolDet2').setValue(rspObj.msg.outPatrol);
			            Ext.getCmp('workAddressDet2').setValue(rspObj.msg.workAddress);
			            Ext.getCmp('workMileageDet2').setValue(rspObj.msg.workMileage);
			            var emCount = rspObj.msg.employeeCount;
			            if(emCount==0){
			            	Ext.getCmp('employeeCountDet2').setValue("");
			            }else{
			            	Ext.getCmp('employeeCountDet2').setValue(rspObj.msg.employeeCount);
			            }
			            Ext.getCmp('workStandardDet2').setValue(rspObj.msg.workStandard);
			            Ext.getCmp('riskControlDet2').setValue(rspObj.msg.riskControl);
			            Ext.getCmp('lineLevelDet2').setValue(rspObj.msg.lineLevel);
			            Ext.getCmp('rowLevelDet2').setValue(rspObj.msg.rowLevel);
			            var maintenceType=rspObj.msg.maintenceType;
			            if(maintenceType==0){
			            	Ext.getCmp('maintenceTypeDet2').setValue("施工");
			            }else if(maintenceType==1){
			            	Ext.getCmp('maintenceTypeDet2').setValue("维修");
			            }else{
			            	Ext.getCmp('maintenceTypeDet2').setValue("未知");
			            }
			            Ext.getCmp('stationDet2').setValue(rspObj.msg.station);
//			            Ext.getCmp('formOrgDet').setValue(rspObj.msg.formOrgName);
			            Ext.getCmp('interphoneDet2').setValue(rspObj.msg.interphone);
			            Ext.getCmp('patrolNameDet2').setValue(rspObj.msg.patrolName);
			            Ext.getCmp('regStationDet2').setValue(rspObj.msg.regStation);
			            Ext.getCmp('residentLiaisonDet2').setValue(rspObj.msg.residentLiaison);
			            Ext.getCmp('residentStationDet2').setValue(rspObj.msg.residentStation);
			            Ext.getCmp('residentOnlineDet2').setValue(rspObj.msg.residentOnline);
			            Ext.getCmp('workTeamDet2').setValue(rspObj.msg.teamName);
			            Ext.getCmp('whseCodeDet2').setValue(rspObj.msg.whseName);
			            Ext.getCmp('targetingEmployeeNameDet2').setValue(rspObj.msg.targetingEmployeeName);
			            Ext.getCmp('brhNameDet2').setValue(rspObj.msg.brhName);
			            Ext.getCmp('applyMsgDet2').setValue(rspObj.msg.applyMsg);
			            var adStatus = rspObj.msg.auditStatus;//0未审核;1审核通过；2审核不通过
			            if(adStatus==0){
			            	Ext.getCmp('auditStatusDet2').setValue("未审核");
			            }else if(adStatus==1){
			            	Ext.getCmp('auditStatusDet2').setValue("审核通过");
			            }else if(adStatus==2){
			            	Ext.getCmp('auditStatusDet2').setValue("审核不通过");
			            }
			            Ext.getCmp('auditMsgDet2').setValue(rspObj.msg.auditMsg);
			            Ext.getCmp('auditUserDet2').setValue(rspObj.msg.auditUser);
			            Ext.getCmp('auditDateDet2').setValue(rspObj.msg.auditDateStr);	
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				}
			})
		}
	};
	function getCurrentDate(format) {
		  var now = new Date();
		  var year = now.getFullYear(); //得到年份
		  var month = now.getMonth();//得到月份
		  var date = now.getDate();//得到日期
		  var day = now.getDay();//得到周几
		  var hour = now.getHours();//得到小时
		  var minu = now.getMinutes();//得到分钟
		  var sec = now.getSeconds();//得到秒
		  month = month + 1;
		  if (month < 10) month = "0" + month;
		  if (date < 10) date = "0" + date;
		  if (hour < 10) hour = "0" + hour;
		  if (minu < 10) minu = "0" + minu;
		  if (sec < 10) sec = "0" + sec;
		  var time = "";
		  //精确到天
		  if(format==1){
			time = year + "-" + month + "-" + date;
		  }
		  //精确到分
		  else if(format==2){
			time = year + "-" + month + "-" + date+ " " + hour + ":" + minu + ":" + sec;
		  }
		  return time;
	}
	function printGrid() {
		var printDate = new Date();
	    
	    var tableStr = '<div style="text-align:center;font-size:18px;font-weight:bold;height:35px;line-height:35px;">作业工单</div><div style="text-align:right;font-size:12px;height:30px;line-height:30px;">打印时间：'+ getCurrentDate(2)+'</div>';
	    tableStr += '<table  style="border:1px #ccc solid; width:100%;font-size:12px;" cellpadding=0 cellspacing=0><tr><td align=center style="border:1px #ccc solid;">';

	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;background:#f5f5f5;font-weight:bold" colspan="3">基本信息</td></tr>';

	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">工单编码：'+Ext.getCmp('workCodeDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">工单名称：'+Ext.getCmp('workNameDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">作业单位：'+Ext.getCmp('costOrgDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">调度号：'+Ext.getCmp('dispatchCodeDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">施工作业天窗开始：'+Ext.getCmp('skylightStartDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">施工作业天窗结束：'+Ext.getCmp('skylightEndDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">入网通道门禁：'+Ext.getCmp('accessInCodeDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">出网通道门禁：'+Ext.getCmp('accessOutCodeDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">作业负责人：'+Ext.getCmp('workPicDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">负责人电话：'+Ext.getCmp('workTelDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">作业清点人：'+Ext.getCmp('workCountDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">进门巡护负责人：'+Ext.getCmp('inPatrolDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">出门巡护人：'+Ext.getCmp('outPatrolDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">作业地点：'+Ext.getCmp('workAddressDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">作业里程：'+Ext.getCmp('workMileageDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">作业人数：'+Ext.getCmp('employeeCountDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">工作量及质量标准：'+Ext.getCmp('workStandardDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">风险控制措施：'+Ext.getCmp('riskControlDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">线别：'+Ext.getCmp('lineLevelDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">行别'+Ext.getCmp('rowLevelDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">维修类型：'+Ext.getCmp('maintenceTypeDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">站/区段：'+Ext.getCmp('stationDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">巡护中队：'+Ext.getCmp('patrolNameDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">登记站：'+Ext.getCmp('regStationDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">驻所联系人：'+Ext.getCmp('residentLiaisonDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">驻站联系人：'+Ext.getCmp('residentStationDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">现场联系人：'+Ext.getCmp('residentOnlineDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">对讲机联系频道：'+Ext.getCmp('interphoneDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">班组：'+Ext.getCmp('workTeamDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">仓库：'+Ext.getCmp('whseCodeDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">盯控干部：'+Ext.getCmp('targetingEmployeeNameDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">所属机构：'+Ext.getCmp('brhNameDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">申请事由：'+Ext.getCmp('applyMsgDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;">审核状态：'+Ext.getCmp('auditStatusDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">审核意见：'+Ext.getCmp('auditMsgDet2').getValue()+'</td><td style="border:1px #ccc solid;padding:5px;">审核时间：'+Ext.getCmp('auditDateDet2').getValue()+'</td></tr>';
	    tableStr = tableStr + '<tr><td style="border:1px #ccc solid;padding:5px;" colspan="3">审核人：'+Ext.getCmp('auditUserDet2').getValue()+'</td></tr>';

	    tableStr = tableStr + '</table>';
	    
	    
	    var wdgTable = '<table  style="border:1px #ccc solid; width:100%;font-size:12px;" cellpadding=0 cellspacing=0><tr><td align=center style="border:1px #ccc solid;">';

	    wdgTable = wdgTable + '<tr><td style="border:1px #ccc solid;padding:5px;background:#f5f5f5;font-weight:bold" colspan="9">工单人员</td></tr>';
	    var weg = Ext.getCmp("workEmployeeGrid2");
	    var wegCm = weg.getColumnModel();
	    var colCount = wegCm.getColumnCount();
	    var temp_obj = new Array();
	    for (var i = 0; i < colCount; i++) {
	        if (wegCm.isHidden(i) ==  true ) {

	        } else {
	            temp_obj.push(i);
	        }
	    }
	    for (var i = 0; i < temp_obj.length; i++) {
	    	wdgTable = wdgTable + '<td style="border:1px #ccc solid;padding:5px;">' + wegCm.getColumnHeader(temp_obj[i]) + '</td>';
	    }
	    wdgTable = wdgTable + '</tr>';
	    var wegStore = weg.getStore();
	    var recordCount = wegStore.getCount();
	    for (var i = 0; i < recordCount; i++) {
	        var r = wegStore.getAt(i);
	        for (var j = 0; j < temp_obj.length; j++) {
	            var dateIndex = wegCm.getDataIndex(temp_obj[j]);
	            var tdvalue = r.get(dateIndex);
	            var renderFunc = wegCm.getRenderer(temp_obj[j]);
	            if (renderFunc != null ) {
	                tdvalue = renderFunc(tdvalue);
	            }
	            if (tdvalue == null ) {
	                tdvalue = "";
	            }
	            wdgTable = wdgTable + '<td style="border:1px #ccc solid;padding:5px;">' + tdvalue + '</td>';
	        }
	        wdgTable = wdgTable + '</tr>';
	    }
	    
	    var twgTable = '<table  style="border:1px #ccc solid; width:100%;font-size:12px;" cellpadding=0 cellspacing=0><tr><td align=center style="border:1px #ccc solid;">';

	    twgTable = twgTable + '<tr><td style="border:1px #ccc solid;padding:5px;background:#f5f5f5;font-weight:bold" colspan="9">工单工具</td></tr>';
	    var twg = Ext.getCmp("toolWorkGrid2");
	    var twgCm = twg.getColumnModel();
	    var colCount2 = twgCm.getColumnCount();
	    var temp_obj2 = new Array();
	    for (var i = 0; i < colCount2; i++) {
	        if (twgCm.isHidden(i) ==  true ) {

	        } else {
	            temp_obj2.push(i);
	        }
	    }
	    for (var i = 0; i < temp_obj2.length; i++) {
	    	twgTable = twgTable + '<td style="border:1px #ccc solid;padding:5px;">' + twgCm.getColumnHeader(temp_obj2[i]) + '</td>';
	    }
	    twgTable = twgTable + '</tr>';
	    var twgStore = twg.getStore();
	    var recordCount2 = twgStore.getCount();
	    for (var i = 0; i < recordCount2; i++) {
	        var r = twgStore.getAt(i);
	        for (var j = 0; j < temp_obj2.length; j++) {
	            var dateIndex = twgCm.getDataIndex(temp_obj2[j]);
	            var tdvalue = r.get(dateIndex);
	            var renderFunc = twgCm.getRenderer(temp_obj2[j]);
	            if (renderFunc != null ) {
	                tdvalue = renderFunc(tdvalue);
	            }
	            if (tdvalue == null ) {
	                tdvalue = "";
	            }
	            twgTable = twgTable + '<td style="border:1px #ccc solid;padding:5px;">' + tdvalue + '</td>';
	        }
	        twgTable = twgTable + '</tr>';
	    }
	    
	    
	    
	    var titleHtml = tableStr+wdgTable+twgTable;
	    var newwin = window.open("printer.html", "", "");
	    newwin.document.write(titleHtml);
	    newwin.document.location.reload();
	    newwin.print();
	    newwin.close();
	}
	menuArr.push(editBaseInfoMenu);
	menuArr.push('-');
	menuArr.push(inStationEmMenu);
	menuArr.push('-');
	menuArr.push(inStationToolsMenu);
	menuArr.push('-');
	menuArr.push(detailMenu);
	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	menuArr.push('-');
	menuArr.push(printMenu);
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=getWorkInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[//workCode,dispatchCode,workName,maintenceType,station,workPic,skylightStart,skylightEnd,workStatus,auditStatus
            {name: 'workCode',mapping: 'workCode'},
            {name: 'dispatchCode',mapping: 'dispatchCode'},
            {name: 'workName',mapping: 'workName'},
            {name: 'maintenceType',mapping: 'maintenceType'},
            {name: 'station',mapping: 'station'},
			{name: 'workPic',mapping: 'workPic'},
			{name: 'skylightStart',mapping: 'skylightStart'},
			{name: 'skylightEnd',mapping: 'skylightEnd'},
            {name: 'workStatus',mapping: 'workStatus'},
            {name: 'workStart',mapping: 'workStart'},
            {name: 'workEnd',mapping: 'workEnd'},
            {name: 'auditStatus',mapping: 'auditStatus'},
            {name: 'auditMsg',mapping: 'auditMsg'},
            {name: 'auditDate',mapping: 'auditDate'},
            {name: 'auditUser',mapping: 'auditUser'}
        ])
    });
	termStore.load();
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            dateStart: queryForm.getForm().findField('dateStart').getValue(),
            dateEnd: queryForm.getForm().findField('dateEnd').getValue(),
            workStatus: queryForm.getForm().findField('workStatus').getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
//    	new Ext.grid.RowNumberer(),
    	//0计划号、1调度号、2工单名称、3维修类型、4站/区段、5负责人、6开始时间、
   	 	//* 7结束时间、8工单状态、9审核状态、
    	//workCode,dispatchCode,workName,maintenceType,station,workPic,skylightStart,skylightEnd,workStatus,auditStatus
    	{header: '工单编码',dataIndex: 'workCode',sortable: true,width: 120},
    	{header: '调度号',dataIndex: 'dispatchCode',sortable: true,width: 120},
		{header: '工单名称',dataIndex: 'workName',sortable: true,width: 200},
		{header: '维修类型',dataIndex: 'maintenceType',sortable: true,width: 120},
		{header: '站/区段',dataIndex: 'station',sortable: true,width: 120},
		{header: '负责人',dataIndex: 'workPic',sortable: true,width: 120},
		{header: '施工作业天窗开始',dataIndex: 'skylightStart',sortable: true,width: 120},
		{header: '施工作业天窗结束',dataIndex: 'skylightEnd',sortable: true,width: 120},
		{header: '工单状态',dataIndex: 'workStatus',sortable: true,width: 120},
		{header: '作业开始时间',dataIndex: 'workStart',sortable: true,width: 120},
		{header: '作业结束时间',dataIndex: 'workEnd',sortable: true,width: 120},
		{header: '审核状态',dataIndex: 'auditStatus',sortable: true,width: 120},
		{header: '审核意见',dataIndex: 'auditMsg',sortable: true,width: 120},
		{header: '审核时间',dataIndex: 'auditDate',sortable: true,width: 120},
		{header: '审核人',dataIndex: 'auditUser',sortable: true,width: 120}
    ]);
	// 仓库信息列表
    var grid = new Ext.grid.GridPanel({
        title: '工单信息维护',
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
                grid.getTopToolbar().items.items[0].enable();
                grid.getTopToolbar().items.items[2].enable();
                grid.getTopToolbar().items.items[4].enable();
                grid.getTopToolbar().items.items[6].enable();
                grid.getTopToolbar().items.items[8].enable();
                grid.getTopToolbar().items.items[12].enable();
            } else {
            	grid.getTopToolbar().items.items[0].disable();
            	grid.getTopToolbar().items.items[2].disable();
            	grid.getTopToolbar().items.items[4].disable();
            	grid.getTopToolbar().items.items[6].disable();
            	grid.getTopToolbar().items.items[8].disable();
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