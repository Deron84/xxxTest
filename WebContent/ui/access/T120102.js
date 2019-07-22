Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	
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
					url : 'T120101Action_getData.asp',
					params : {
						accessCode : selectedRecord.get('accessCode')
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							detTermWin.show();
							detTermWin.center();
				            Ext.getCmp('accessCodeDet').setValue(rspObj.msg.accessCode);
				            Ext.getCmp('accessNameDet').setValue(rspObj.msg.accessName);
				            Ext.getCmp('accessTypeDet').setValue(rspObj.msg.accessType);
				            Ext.getCmp('accessRouteDet').setValue(rspObj.msg.accessRoute);
				            Ext.getCmp('accessAddressDet').setValue(rspObj.msg.accessAddress);
				            Ext.getCmp('accessDeptDet').setValue(rspObj.msg.accessDept);
				            Ext.getCmp('accessPicDet').setValue(rspObj.msg.accessPic);
				            Ext.getCmp('accessTelDet').setValue(rspObj.msg.accessTel );
				            Ext.getCmp('policeOfficeDet').setValue(rspObj.msg.policeOffice );
				            Ext.getCmp('examPeriodDet').setValue(rspObj.msg.examPeriod );
				            Ext.getCmp('lastExamDet').setValue(strdate(rspObj.msg.lastExam) );
				            Ext.getCmp('accessStatusDet').setValue(accessStatusRender(rspObj.msg.accessStatus));
				            Ext.getCmp('openStatusDet').setValue(openStatusRender(rspObj.msg.openStatus));
//				            Ext.getCmp('warnWeixinDet').setValue(warnWeixinRender(rspObj.msg.warnWeixin) );
				            Ext.getCmp('mileageDet').setValue(rspObj.msg.mileage );
				            Ext.getCmp('mileagePreviousDet').setValue(rspObj.msg.mileagePrevious );
				            Ext.getCmp('mileageNextDet').setValue(rspObj.msg.mileageNext );
				            Ext.getCmp('installDateDet').setValue(strdate(rspObj.msg.installDate) );
				            Ext.getCmp('longitudeDet').setValue(rspObj.msg.longitude );
				            Ext.getCmp('latitudeDet').setValue(rspObj.msg.latitude );
				            Ext.getCmp('whseCodeDet').setValue(rspObj.msg.whseCode );
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					}
				});
			}
		};
		var detTermForm = new Ext.form.FormPanel({
	        frame: true,
	        autoHeight: true,
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
	       	            fieldLabel: '门禁编码',
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
						fieldLabel: '门禁名称',
						id: 'accessNameDet',
						name: 'accessNameDet',
						width:300,
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '门禁类型',
						id: 'accessTypeDet',
						name: 'accessTypeDet',
						width:300,
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '线路名称',
						width:300,
						id: 'accessRouteDet',
						name: 'accessRouteDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '门禁位置',
						width:300,
						id: 'accessAddressDet',
						name: 'accessAddressDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '所属机构',
						width:300,
						id: 'accessDeptDet',
						name: 'accessDeptDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '负责人',
						width:300,
						id: 'accessPicDet',
						name: 'accessPicDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系电话',
						width:300,
						id: 'accessTelDet',
						name: 'accessTelDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '所属派出所',
						width:300,
						id: 'policeOfficeDet',
						name: 'policeOfficeDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '检查周期（天）',
						width:300,
						id: 'examPeriodDet',
						name: 'examPeriodDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '最后一次检修时间',
						width:300,
						id: 'lastExamDet',
						name: 'lastExamDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '仓库编码',
						width:300,
						id: 'whseCodeDet',
						name: 'whseCodeDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '通道门里程',
						width:300,
						id: 'mileageDet',
						name: 'mileageDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '与上一通道门距离',
						width:300,
						id: 'mileagePreviousDet',
						name: 'mileagePreviousDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '与下一通道门距离',
						width:300,
						id: 'mileageNextDet',
						name: 'mileageNextDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '安装时间',
						width:300,
						id: 'installDateDet',
						name: 'installDateDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '地理经度',
						width:300,
						id: 'longitudeDet',
						name: 'longitudeDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '地理纬度',
						width:300,
						id: 'latitudeDet',
						name: 'latitudeDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '门禁使用状态',
						width:300,
						id: 'accessStatusDet',
						name: 'accessStatusDet'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '门禁开启状态',
						width:300,
						id: 'openStatusDet',
						name: 'openStatusDet'
		        	}]
//				},{
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'displayfield',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '微信预警',
//						width:300,
//						id: 'warnWeixinDet',
//						name: 'warnWeixinDet'
//		        	}]
				}],
	        }]
	    });
		var detTermWin = new Ext.Window({
	        title: '查看门禁详细信息',
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
		width: 400,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'accessCode',
			name: 'accessCode',
			fieldLabel: '门禁编码',
			width:200
		},{
			xtype:'textfield',
			fieldLabel: '门禁名称',
			id:'accessName',
			name:'accessName',
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
//            	grid.getTopToolbar().items.items[0].disable();
//                grid.getTopToolbar().items.items[2].disable();
//                grid.getTopToolbar().items.items[4].disable();
//                grid.getTopToolbar().items.items[6].disable();
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
				var accessCode = Ext.getCmp('accessCode').getValue();
	            var accessName = Ext.getCmp('accessName').getValue();
				var param = "?a=1";
				if(accessCode){
					param = param + "&accessCode="+accessCode;
				}
				if(accessName){
					param = param + "&accessName="+accessName;
				}
				window.location.href = Ext.contextPath +"/exportExcelT120101.asp"+param;
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
//	menuArr.push(detailMenu);
//	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=railAccessInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'accessCode'
        },[
            {name: 'accessCode',mapping: 'accessCode'},
            {name: 'accessName',mapping: 'accessName'},
            {name: 'accessType',mapping: 'accessType'},
            {name: 'note1',mapping: 'note1'},
            {name: 'accessRoute',mapping: 'accessRoute'},
			{name: 'accessAddress',mapping: 'accessAddress'},
			{name: 'accessDept',mapping: 'brhName'},
			{name: 'accessPic',mapping: 'accessPic'},
            {name: 'accessTel',mapping: 'accessTel'},
            {name: 'policeOffice',mapping: 'policeOffice'},
            {name: 'examPeriod',mapping: 'examPeriod'},
            {name: 'lastExam',mapping: 'lastExam'},
            {name: 'accessStatus',mapping: 'accessStatus'},
            {name: 'note5',mapping: 'note5'},
            {name: 'openStatus',mapping: 'openStatus'},
//            {name: 'warnWeixin',mapping: 'warnWeixin'},
            {name: 'mileage',mapping: 'mileage'},
            {name: 'mileagePrevious',mapping: 'mileagePrevious'},
            {name: 'mileageNext',mapping: 'mileageNext'},
            {name: 'installDate',mapping: 'installDate'},
            {name: 'longitude',mapping: 'longitude'},
            {name: 'latitude',mapping: 'latitude'},
            {name: 'whseCode',mapping: 'whseCode'},
            {name: 'addUser',mapping: 'addUser'}
        ])
    });
	termStore.load();
	function accessStatusRender(val) {
		if(val == '0') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="启用"/>启用';
		} else if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="停用"/>停用';
		} 
		return '状态未知';
	}
	function openStatusRender(val) {
		if(val == '已连接') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="已连接"/>已连接';
		} else if(val == '未连接') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="未连接"/>未连接';
		} 
		return '状态未知';
	}
	function openNote5Render(val) {
		if(val == '正常') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="正常"/>正常';
		} else if(val == '故障') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="故障"/>故障';
		} 
		return '状态未知';
	}
//	function warnWeixinRender(val) {
//		if(val == '0') {
//			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="预警"/>预警';
//		} else if(val == '1') {
//			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="不预警"/>不预警';
//		} 
//		return '状态未知';
//	}
	
	function accessTypeRender(accessType) {
		switch(accessType) {
			case '0': return '通道门';
			case '1': return '库房门';
		}
	}
	function strdate(date) {
		var date = new Date(date);
		Y = date.getFullYear() + '-';
		M = (date.getMonth() < 10 ? '0'+(date.getMonth()+1) : date.getMonth()) + '-';
		D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
		h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
		m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
		s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds()) + '';
		return  Y+M+D+h+m+s;
	    }
	
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            accessCode: Ext.getCmp('accessCode').getValue(),
            accessName: Ext.getCmp('accessName').getValue()
//            whseAddress: Ext.getCmp('whseAddress').getValue(),
//            whseRank: Ext.getCmp('whseRank').getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
    	{id:'accessCode',header: '门禁编码',dataIndex: 'accessCode',sortable: true,width: 120},
		{header: '门禁名称',dataIndex: 'accessName',sortable: true,width: 120},
		{header: '门禁类型',dataIndex: 'accessType',sortable: true,width: 100},
		{header: '配套设备编码',dataIndex: 'note1',sortable: true,width: 120},
		{header: '线路名称',dataIndex: 'accessRoute',sortable: true,width: 120},
		{header: '门禁位置',dataIndex: 'accessAddress',sortable: true,width: 120},
		{header: '所属机构',dataIndex: 'accessDept',sortable: true,width: 120},
		{header: '负责人',dataIndex: 'accessPic',sortable: true,width: 120},
		{header: '联系电话',dataIndex: 'accessTel',sortable: true,width: 120},
		{header: '所属派出所',dataIndex: 'policeOffice',sortable: true,width: 120},
		{header: '检修周期（天）',dataIndex: 'examPeriod',sortable: true,width: 120},
		{header: '最后一次检修时间',dataIndex: 'lastExam',sortable: true,width: 120},
		{header: '使用状态',dataIndex: 'accessStatus',sortable: true,width: 120},
		{header: '设备状态',dataIndex: 'note5',sortable: true,renderer: openNote5Render,width: 80},
		{header: '当前状态',dataIndex: 'openStatus',sortable: true,renderer: openStatusRender,width: 120},
//		{header: '微信预警',dataIndex: 'warnWeixin',renderer: warnWeixinRender,width: 120},
		{header: '通道门里程',dataIndex: 'mileage',sortable: true,width: 120},
		{header: '与上一通道门距离',dataIndex: 'mileagePrevious',sortable: true,width: 120},
		{header: '与下一通道门距离',dataIndex: 'mileageNext',sortable: true,width: 120},
		{header: '安装时间',dataIndex: 'installDate',sortable: true,width: 120},
		{header: '地理经度',dataIndex: 'longitude',sortable: true,width: 120},
		{header: '地理纬度',dataIndex: 'latitude',sortable: true,width: 120},
		{header: '仓库编码',dataIndex: 'whseCode',sortable: true,width: 120},
		{header: '创建人',dataIndex: 'addUser',sortable: true,width: 120}
    ]);
	// 仓库信息列表
    var grid = new Ext.grid.GridPanel({
        title: '门禁信息维护',
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
//            rec = grid.getSelectionModel().getSelected();
//            if(rec != null) {
//            	var openStatus=rec.get('openStatus');
//                grid.getTopToolbar().items.items[0].enable();
//            } else {
//                grid.getTopToolbar().items.items[0].disable();
//                
//            }
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})