Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	
	var editMenu = {
		text : '审核工单',
		width : 60,
		iconCls : 'edit',
		disabled : true,
		handler : function() {
			var	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null){
//                Ext.MessageBox.alert('操作提示', '没有选择记录!');
                showAlertMsg("没有选择记录!",grid);
                return;
            }
            var auditStatus = selectedRecord.get("auditStatus");
            if(auditStatus == "审核通过"){
//            	Ext.MessageBox.alert('操作提示', '该工单已经审核过了！');
            	 showAlertMsg("已经审核，无需再审核",grid);
                return;
            }
            if(auditStatus == "未通过"){
//            	Ext.MessageBox.alert('操作提示', '该工单未通过审核，请重新添加工具及人员！');
            	showAlertMsg("该工单未通过审核，请重新添加工具及人员！",grid);
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
						updTermWin.show();
						updTermWin.center();
						workEmployeeStore.load();
						toolWorkStore.load();
			            Ext.getCmp('workCodeDet').setValue(rspObj.msg.workCode);
			            Ext.getCmp('workNameDet').setValue(rspObj.msg.workName);
			            Ext.getCmp('costOrgDet').setValue(rspObj.msg.costOrgName);
			            Ext.getCmp('dispatchCodeDet').setValue(rspObj.msg.dispatchCode);
//			            Ext.getCmp('registerTypeDet').setValue(rspObj.msg.registerType);
			            Ext.getCmp('skylightStartDet').setValue(rspObj.msg.skylightStartStr);
			            Ext.getCmp('skylightEndDet').setValue(rspObj.msg.skylightEndStr);
			            Ext.getCmp('accessInCodeDet').setValue(rspObj.msg.accessInCode );
			            Ext.getCmp('accessOutCodeDet').setValue(rspObj.msg.accessOutCode);
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
			            Ext.getCmp('stationDet').setValue(rspObj.msg.station);
//			            Ext.getCmp('formOrgDet').setValue(rspObj.msg.formOrgName);
			            Ext.getCmp('interphoneDet').setValue(rspObj.msg.interphone);
			            Ext.getCmp('workTeamDet').setValue(rspObj.msg.teamName);
			            Ext.getCmp('whseCodeDet').setValue(rspObj.msg.whseName);
//			            alert(rspObj.msg.patrolName+"  >>>>>>>>>>>>");
			            Ext.getCmp('patrolNameDet').setValue(rspObj.msg.patrolName);
			            Ext.getCmp('regStationDet').setValue(rspObj.msg.regStation);
			            Ext.getCmp('residentLiaisonDet').setValue(rspObj.msg.residentLiaison);
			            Ext.getCmp('residentStationDet').setValue(rspObj.msg.residentStation);
			            Ext.getCmp('residentOnlineDet').setValue(rspObj.msg.residentOnline);
			            Ext.getCmp('targetingEmployeeNameDet').setValue(rspObj.msg.targetingEmployeeName);
			            Ext.getCmp('brhNameDet').setValue(rspObj.msg.brhName);
			            Ext.getCmp('applyMsgDet').setValue(rspObj.msg.applyMsg);
//			            Ext.getCmp('auditStatusDet').setValue(rspObj.msg.auditStatus);
//			            Ext.getCmp('auditMsgDet').setValue(rspObj.msg.auditMsg);
//			            Ext.getCmp('auditUserDet').setValue(rspObj.msg.auditUser);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				}
			});
		}
	};
	var updTermForm = new Ext.form.FormPanel({
		frame: true,
        height: 330,
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
			}],
        }]
    });
	var workInfoPanel = new Ext.Panel({
		title : '基本信息',
		height:330,
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
        },[//toolName,toolTypeName,toolNum
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
		height:330,
		layout : 'fit',
		items : [workInfoPanel,workEmployeeGrid,toolWorkGrid]

	});

	var updTermWin = new Ext.Window({
        title: '审核',
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
            text: '通过',
            handler: function() {
            	var weCount=workEmployeeGrid.getStore().getCount();
            	var wtCount=toolWorkGrid.getStore().getCount();
            	if(weCount==0){
            		Ext.MessageBox.alert('操作提示', '请为工单分配员工!');
            		return;
            	}
            	if(wtCount==0){
            		Ext.MessageBox.alert('操作提示', '请为工单分配工具!');
            		return;
            	}
            	var	selectedRecord = grid.getSelectionModel().getSelected();
            	Ext.Ajax.request({
    				url : 'T130103Action.asp?method=update',
    				params : {
    					workCode : selectedRecord.get('workCode'),
    					auditStatus : "1",
    					txnId: '130103',
    					subTxnId: '00'
    				},
    				success : function(rsp, opt) {
    					var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success){
//							Ext.MessageBox.alert('操作提示', '操作成功!');
							showSuccessMsg('操作成功!',grid);//showErrorMsg(action.result.msg,addMhForm)
							updTermWin.hide();
							grid.getStore().reload();
						}else{
							Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
						}
    				}
    			});
            }
        },{
            text: '拒绝',
            handler: function() {
            	refuseWin.show();
            	refuseWin.center();
            }
        },{
            text: '取消',
            handler: function() {
            	updTermWin.hide();
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
    				url : 'T130103Action.asp?method=update',
    				params : {
    					workCode : selectedRecord.get('workCode'),
    					auditStatus : "2",
    					auditMsg : auditMsg,
    					txnId: '130103',
    					subTxnId: '01'
    				},
    				success : function(rsp, opt) {
    					var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success){
//							Ext.MessageBox.alert('操作提示', '操作成功!');
							showSuccessMsg('操作成功!',grid);//showErrorMsg(action.result.msg,addMhForm)
							updTermWin.hide();
							refuseWin.hide();
							grid.getStore().reload();
						}else{
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
            	Ext.MessageBox.alert("操作提示","没有选择记录");
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
			            Ext.getCmp('workCodeDet4').setValue(rspObj.msg.workCode);
			            Ext.getCmp('workNameDet4').setValue(rspObj.msg.workName);
			            Ext.getCmp('costOrgDet4').setValue(rspObj.msg.costOrgName);
			            Ext.getCmp('dispatchCodeDet4').setValue(rspObj.msg.dispatchCode);
//			            Ext.getCmp('registerTypeDet4').setValue(rspObj.msg.registerType);
			            Ext.getCmp('skylightStartDet4').setValue(rspObj.msg.skylightStartStr);
			            Ext.getCmp('skylightEndDet4').setValue(rspObj.msg.skylightEndStr);
			            Ext.getCmp('accessInCodeDet4').setValue(rspObj.msg.accessInCode );
			            Ext.getCmp('accessOutCodeDet4').setValue(rspObj.msg.accessOutCode);
			            Ext.getCmp('workPicDet4').setValue(rspObj.msg.workPic);
			            Ext.getCmp('workTelDet4').setValue(rspObj.msg.workTel);
			            Ext.getCmp('workCountDet4').setValue(rspObj.msg.workCount);
			            Ext.getCmp('inPatrolDet4').setValue(rspObj.msg.inPatrol);
			            Ext.getCmp('outPatrolDet4').setValue(rspObj.msg.outPatrol);
			            Ext.getCmp('workAddressDet4').setValue(rspObj.msg.workAddress);
			            Ext.getCmp('workMileageDet4').setValue(rspObj.msg.workMileage);
			            var emCount = rspObj.msg.employeeCount;
			            if(emCount==0){
			            	Ext.getCmp('employeeCountDet4').setValue("");
			            }else{
			            	Ext.getCmp('employeeCountDet4').setValue(rspObj.msg.employeeCount);
			            }
			            Ext.getCmp('workStandardDet4').setValue(rspObj.msg.workStandard);
			            Ext.getCmp('riskControlDet4').setValue(rspObj.msg.riskControl);
			            Ext.getCmp('lineLevelDet4').setValue(rspObj.msg.lineLevel);
			            Ext.getCmp('rowLevelDet4').setValue(rspObj.msg.rowLevel);
			            Ext.getCmp('stationDet4').setValue(rspObj.msg.station);
//			            Ext.getCmp('formOrgDet4').setValue(rspObj.msg.formOrgName);
			            Ext.getCmp('interphoneDet4').setValue(rspObj.msg.interphone);
			            Ext.getCmp('workTeamDet4').setValue(rspObj.msg.teamName);
			            Ext.getCmp('whseCodeDet4').setValue(rspObj.msg.whseName);
			            Ext.getCmp('patrolNameDet4').setValue(rspObj.msg.patrolName);
			            Ext.getCmp('regStationDet4').setValue(rspObj.msg.regStation);
			            Ext.getCmp('residentLiaisonDet4').setValue(rspObj.msg.residentLiaison);
			            Ext.getCmp('residentStationDet4').setValue(rspObj.msg.residentStation);
			            Ext.getCmp('residentOnlineDet4').setValue(rspObj.msg.residentOnline);
			            Ext.getCmp('targetingEmployeeNameDet4').setValue(rspObj.msg.targetingEmployeeName);
			            Ext.getCmp('brhNameDet4').setValue(rspObj.msg.brhName);
			            Ext.getCmp('applyMsgDet4').setValue(rspObj.msg.applyMsg);
			            var adStatus = rspObj.msg.auditStatus;//0未审核;1审核通过；2审核不通过
			            if(adStatus==0){
			            	Ext.getCmp('auditStatusDet4').setValue("未审核");
			            }else if(adStatus==1){
			            	Ext.getCmp('auditStatusDet4').setValue("审核通过");
			            }else if(adStatus==2){
			            	Ext.getCmp('auditStatusDet4').setValue("审核不通过");
			            }
//			            Ext.getCmp('auditStatusDet4').setValue(rspObj.msg.auditStatus);
			            Ext.getCmp('auditMsgDet4').setValue(rspObj.msg.auditMsg);
			            Ext.getCmp('auditUserDet4').setValue(rspObj.msg.auditUser);
			            Ext.getCmp('auditDateDet4').setValue(rspObj.msg.auditDateStr);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				}
            })
		}
	};
	
	var detailToolWorkGrid = new Ext.grid.GridPanel({
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
	var detailUpdTermForm = new Ext.form.FormPanel({
		frame: true,
        height: 350,
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
					labelStyle: 'padding-left: 5px;width:120px',
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
					labelStyle: 'padding-left: 5px;width:120px;',
					fieldLabel: '作业单位',
					width: 150,
					id: 'costOrgDet4',
					name: 'costOrgDet4'
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
					id: 'dispatchCodeDet4',
					name: 'dispatchCodeDet4'
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
//					id: 'registerTypeDet2',
//					name: 'registerTypeDet2'
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
					id: 'skylightStartDet4',
					name: 'skylightStartDet4'
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
					id: 'skylightEndDet4',
					name: 'skylightEndDet4'
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
					id: 'accessInCodeDet4',
					name: 'accessInCodeDet4'
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
					id: 'accessOutCodeDet4',
					name: 'accessOutCodeDet4'
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
					id: 'workPicDet4',
					name: 'workPicDet4'
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
					id: 'workTelDet4',
					name: 'workTelDet4'
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
					id: 'workCountDet4',
					name: 'workCountDet4'
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
					id: 'inPatrolDet4',
					name: 'inPatrolDet4'
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
					id: 'outPatrolDet4',
					name: 'outPatrolDet4'
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
					id: 'workAddressDet4',
					name: 'workAddressDet4'
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
					id: 'workMileageDet4',
					name: 'workMileageDet4'
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
					id: 'employeeCountDet4',
					name: 'employeeCountDet4'
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
					id: 'workStandardDet4',
					name: 'workStandardDet4'
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
					id: 'riskControlDet4',
					name: 'riskControlDet4'
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
					id: 'lineLevelDet4',
					name: 'lineLevelDet4'
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
					id: 'rowLevelDet4',
					name: 'rowLevelDet4'
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
					id: 'stationDet4',
					name: 'stationDet4'
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
//					id: 'formOrgDet2',
//					name: 'formOrgDet2'
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
					id: 'patrolNameDet4',
					name: 'patrolNameDet4'
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
					id: 'regStationDet4',
					name: 'regStationDet4'
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
					id: 'residentLiaisonDet4',
					name: 'residentLiaisonDet4'
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
					id: 'residentStationDet4',
					name: 'residentStationDet4'
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
					id: 'residentOnlineDet4',
					name: 'residentOnlineDet4'
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
					id: 'interphoneDet4',
					name: 'interphoneDet4'
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
					id: 'workTeamDet4',
					name: 'workTeamDet4'
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
					id: 'whseCodeDet4',
					name: 'whseCodeDet4'
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
					id: 'targetingEmployeeNameDet4',
					name: 'targetingEmployeeNameDet4'
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
					id: 'brhNameDet4',
					name: 'brhNameDet4'
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
					id: 'applyMsgDet4',
					name: 'applyMsgDet4'
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
					id: 'auditStatusDet4',
					name: 'auditStatusDet4'
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
					id: 'auditMsgDet4',
					name: 'auditMsgDet4'
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
					id: 'auditDateDet4',
					name: 'auditDateDet4'
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
					id: 'auditUserDet4',
					name: 'auditUserDet4'
	        	}]
			}],//,auditMsg,auditDate,auditUser
        }]
    });
	var  detailWorkInfoPanel = new Ext.Panel({
		title : '基本信息',
		height:350,
		items : [detailUpdTermForm],
	});
	var detailWorkEmployeeGrid = new Ext.grid.GridPanel({
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
	var detailInfoBoxPanel = new Ext.Panel({
		bodyStyle:'overflow-x:hidden;overflow-y:auto;',
		region : 'center',
		margins : '5 0 0 0',
		height:350,
		layout : 'fit',
		items : [detailWorkInfoPanel,detailWorkEmployeeGrid,detailToolWorkGrid]

	});
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
        items: [detailInfoBoxPanel],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'detail',
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
				
	            
	            var workStatus= queryForm.getForm().findField('workStatus').getValue();
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
				window.location.href = Ext.contextPath +"/exportExcelT130103.asp"+param;
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

	menuArr.push(editMenu);
	menuArr.push('-');
	menuArr.push(detailMenu);
	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	menuArr.push('-');
	menuArr.push(printMenu)
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

    termStore.on('beforeload', function() {//alert(queryForm.getForm().findField('dateStart').getValue());
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
    	//workCode,dispatchCode,workName,maintenceType,station,workPic,skylightStart,skylightEnd,workStatus,auditStatus,auditMsg,auditDate,auditUser
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
        title: '工单审核',
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
                grid.getTopToolbar().items.items[8].enable();
            } else {
            	grid.getTopToolbar().items.items[0].disable();
            	grid.getTopToolbar().items.items[2].disable();
            	grid.getTopToolbar().items.items[8].disable();
            }
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})