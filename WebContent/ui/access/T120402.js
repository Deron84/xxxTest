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
					url : 'T120402Action_getData.asp',
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
				            Ext.getCmp('addDateDet').setValue(rspObj.msg.addDate);
				            Ext.getCmp('maintainUserDet').setValue(rspObj.msg.maintainUser);
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
	        width: 450,
	        labelWidth: 110,
	        waitMsgTarget: true,
	        items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       	            xtype: 'displayfield',
	       	            labelStyle: 'padding-left: 5px',
	       	            fieldLabel: '保养id*',
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
						fieldLabel: '保养时间*',
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
						fieldLabel: '保养人*',
						width:300,
						id: 'maintainUserDet',
						name: 'maintainUserDet'
		        	}]
				}],
	    });
		var detTermWin = new Ext.Window({
	        title: '查看门禁检修保养详细信息',
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
        	},{
    			xtype: 'datefield',
    			labelStyle: 'padding-left: 5px',
    			width: 300,
    			id: 'startDate',
    			name: 'startDate',
    			format: 'Y-m-d',
    			altFormats: 'Y-m-d',
    			vtype: 'daterange',
    			endDateField: 'endDate',
    			fieldLabel: '开始日期',
    			editable: false
    		},{
    			xtype: 'datefield',
    			labelStyle: 'padding-left: 5px',
    			width: 300,
    			id: 'endDate',
    			name: 'endDate',
    			format: 'Y-m-d',
    			altFormats: 'Y-m-d',
    			vtype: 'daterange',
    			startDateField: 'startDate',
    			fieldLabel: '结束日期',
    			editable: false
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
				var endtime=Ext.getCmp('endDate').getValue(),starttime=Ext.getCmp('startDate').getValue();
		       	if(endtime!=''&&starttime==''){
		       		showErrorMsg("请选择开始日期",queryWin);
    				return;
		       	}
		       	if(endtime==''&&starttime!=''){
		       		showErrorMsg("请选择结束日期",queryWin);
    				return;
		       	}
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
				var accessCode = queryForm.getForm().findField("accessCode").getValue();
				var  startDate=  typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Y-m-d');
			    var endDate = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Y-m-d');
				            
				var param = "?a=1";
				if(accessCode){
					param = param + "&accessCode="+accessCode;
				}
				if(startDate){
					param = param + "&startDate="+startDate;
				}
				if(endDate){
					param = param + "&endDate="+endDate;
				}
				window.location.href = Ext.contextPath +"/exportExcelT120402.asp"+param;
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
	
	//终端设备添加表单
	var brhInfoForm = new Ext.form.FormPanel({
		frame: true,
        height: 40,
        width: 450,
        labelWidth: 85,
        waitMsgTarget: true,
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
					allowBlank: false,
					editable: false,
					width:300
	        	}]
			}],
	});
	
	//终端设备添加窗口
	var brhWin = new Ext.Window({
		title: '新增门禁检修保养',
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
			//btn.disable();
			frm.submit({
				url: 'T120402Action.asp?method=add',
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
				},
				params: {
					txnId: '120402',
					subTxnId: '00'
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
//	menuArr.push(detailMenu);
//	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=railAccessMaintain'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'id'
        },[
            {name: 'warnId',mapping: 'id'},
            {name: 'accessCode',mapping: 'accessCode'},
            {name: 'accessName',mapping: 'accessName'},
            {name: 'accessRoute',mapping: 'accessRoute'},
            {name: 'accessAddress',mapping: 'accessAddress'},
            {name: 'accessPic',mapping: 'accessPic'},
            {name: 'accessTel',mapping: 'accessTel'},
			{name: 'addDate',mapping: 'addDate'},
			{name: 'maintainUser',mapping: 'maintainUser'},
        ])
    });
	termStore.load();
	
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            accessCode: queryForm.getForm().findField("accessCode").getValue(),
            startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Y-m-d'),
            endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Y-m-d')
                   
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
    	{id:'warnId',header: '保养id',dataIndex: 'warnId',sortable: true,width: 60,hidden : true},
    	{id:'accessCode',header: '门禁编码',dataIndex: 'accessCode',sortable: true,width: 120},
		{header: '门禁名称',dataIndex: 'accessName',sortable: true,width: 150},
		{header: '线路名称',dataIndex: 'accessRoute',sortable: true,width: 150},
		{header: '门禁位置',dataIndex: 'accessAddress',sortable: true,width: 150},
		{header: '门禁负责人',dataIndex: 'accessPic',sortable: true,width: 120},
		{header: '联系电话',dataIndex: 'accessTel',sortable: true,width: 120},
		{header: '保养时间',dataIndex: 'addDate',sortable: true,width: 150},
		{header: '保养人',dataIndex: 'maintainUser',sortable: true,width: 150},
    ]);
	// 仓库信息列表
    var grid = new Ext.grid.GridPanel({
        title: '门禁检修保养管理',
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
           
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})