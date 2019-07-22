Ext.onReady(function() {
    // 商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_NO01',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    
    var flag2Store = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCHT_FALG2',function(ret){
 		var data=Ext.decode(ret);
 		flag2Store.loadData(data);
	});
	
	//专业服务机构
    var organStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('ORGAN',function(ret){
        organStore.loadData(Ext.decode(ret));
    });
    
    // 终端类型数据集
    var termTypeStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('TERM_TYPE',function(ret){
        termTypeStore.loadData(Ext.decode(ret));
    });
    
    var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=termInfoAll01'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'termId',mapping: 'termId'},
            {name: 'organId',mapping: 'orgId'},
            {name: 'mchtCd',mapping: 'mchtCd'},
            {name: 'mappingMchntcdOne',mapping: 'mappingMchntcdOne'},
			{name: 'mappingTermidOne',mapping: 'mappingTermidOne'},
			{name: 'mappingMchntcdTwo',mapping: 'mappingMchntcdTwo'},
			{name: 'mappingTermidTwo',mapping: 'mappingTermidTwo'},
            {name: 'mchtFlag1',mapping: 'mchtFlag1'},
            {name: 'mchtFlag2',mapping: 'mchtFlag2'},
            {name: 'connTp',mapping: 'connTp'},
            {name: 'termSta',mapping: 'termSta'},
            {name: 'termSignSta',mapping: 'termSignSta'},
            {name: 'termIdId',mapping: 'termIdId'},
            {name: 'termFactory',mapping: 'termFactory'},
            {name: 'termMachTp',mapping: 'termMachTp'},
            {name: 'termVer',mapping: 'termVer'},
            {name: 'termTp',mapping: 'termTp'},
            {name: 'mchtGrp',mapping: 'mchtGrp'},
            {name: 'termBranch',mapping: 'termBranch'},
            {name: 'termIns',mapping: 'termIns'},
            {name: 'recCrtTs',mapping: 'recCrtTs'},
            {name: 'proRate',mapping: 'proRate'},
			{name: 'leaseFee',mapping: 'leaseFee'},
			{name: 'rentFee',mapping: 'rentFee'},
			{name: 'useDate',mapping: 'useDate'}
        ])
//    ,
//        autoLoad: true
    });
    
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termNoQ').getValue(),
            acmchntId: Ext.getCmp('acmchntIdQ').getValue(),
            acequipmentId: Ext.getCmp('acequipmentIdQ').getValue(),
            orgId: Ext.getCmp('orgIdId').getValue(),
            termTp: topQueryPanel.getForm().findField('termTpId').getValue(),
            mchtGrp: topQueryPanel.getForm().findField('mchtGrp').getValue(),
            brhId: Ext.getCmp('idacqInstId').getValue(),
            mchtFlag1: Ext.getCmp('idMchtFlag1').getValue(),
            mchtFlag2: Ext.getCmp('idMchtFlag2').getValue(),
            connType: topQueryPanel.getForm().findField('connType').getValue(),
            termSta: Ext.getCmp('state').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
            startTimeUse: topQueryPanel.getForm().findField('startTimeUse').getValue(),
            endTimeUse: topQueryPanel.getForm().findField('endTimeUse').getValue(),
            mchtCd: Ext.getCmp('mchnNoQ').getValue()
        });
    }); 
    
    var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
    	{header: '仓库编码',dataIndex: 'brhLvl',renderer: brhLvlRender,width: 120},
		{id: 'kfname',header: '仓库名称',dataIndex: 'kfname',sortable: true,width: 120},
		{header: '仓库地址',dataIndex: 'upBrhId',sortable: true,width: 200},
		{header: '仓库等级',dataIndex: 'brhName',sortable: true,width: 120},
		{header: '仓库容量',dataIndex: 'brhTelNo',sortable: true,width: 120},
		{header: '上级仓库',dataIndex: 'brhTelNo1',sortable: true,width: 120},
		{header: '仓库负责人',dataIndex: 'postCode',sortable: true,width: 120},
		{header: '仓库联系方式',dataIndex: 'brhContName',sortable: true,width: 120},
		{header: '仓库所属机构',dataIndex: 'brhContName1',sortable: true,width: 120},
		{header: '仓库状态',dataIndex: 'brhContName2',sortable: true,width: 120},
		{header: '门禁标识',dataIndex: 'brhContName3',sortable: true,width: 120}
    ]);
    function brhValue(val){
    	if(val == null){
    		return '';
    	}else{
    		return getRemoteTrans(val, "organName");
    	}
    }
    var topAddPanel=new Ext.form.FormPanel({
    	frame:true,
    	border: true,
    	width:550,
    	autoHeight: true,
        labelWidth: 80,
        items: [{
        	xtype:'textfield',
        	fieldLabel: '仓库名称',
        	id:'add0',
        	name:'add0',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '仓库编码',
        	id:'add1',
        	name:'add1',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '仓库地址',
        	id:'add2',
        	name:'add2',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '仓库等级',
        	id:'add3',
        	name:'add3',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '仓库容量',
        	id:'add4',
        	name:'add4',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '仓库负责人',
        	id:'add5',
        	name:'add5',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '联系方式',
        	id:'add6',
        	name:'add6',
        	width:300
        }],
        buttons: [{
			id : 'addEnsure',
			text: '确定',
			handler: function() {
				alert("点击确定");
			}
		},{
			id : 'addRreset',
			text: '重置',
			handler: function() {
				topAddPanel.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addWin.hide(grid);
				topAddPanel.getForm().reset();
			}
		}]
    });
    var topUptPanel=new Ext.form.FormPanel({
    	frame:true,
    	border: true,
    	width:550,
    	autoHeight: true,
        labelWidth: 80,
        items: [{
        	xtype:'textfield',
        	fieldLabel: '仓库名称',
        	id:'upt0',
        	name:'upt0',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '仓库编码',
        	id:'upt1',
        	name:'upt1',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '仓库地址',
        	id:'upt2',
        	name:'upt2',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '仓库等级',
        	id:'upt3',
        	name:'upt3',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '仓库容量',
        	id:'upt4',
        	name:'upt4',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '仓库负责人',
        	id:'upt5',
        	name:'upt5',
        	width:300
        },{
        	xtype:'textfield',
        	fieldLabel: '联系方式',
        	id:'upt6',
        	name:'upt6',
        	width:300
        }],
        buttons: [{
			id : 'uptEnsure',
			text: '确定',
			handler: function() {
				alert("点击确定");
			}
		},{
			id : 'uptRreset',
			text: '重置',
			handler: function() {
				topUptPanel.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addWin.hide(grid);
				topUptPanel.getForm().reset();
			}
		}]
    });
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 550,
        autoHeight: true,
        labelWidth: 80,
        items: [
         {
			xtype:'textfield',
        	fieldLabel: '仓库名称',
        	id:'acequipmentIdQ',
        	name:'acequipmentIdQ',
        	width:300
        }],
        buttons: [{
            text: '查询',
            handler: function() 
            {
            	var endtimeUse=Ext.getCmp('endTimeUse').getValue(),starttimeUse=Ext.getCmp('startTimeUse').getValue();
            	if(endtimeUse!=''&&starttimeUse!=''&&endtimeUse<starttimeUse){
            		showErrorMsg("请保证截止时间不小于起始时间",grid);
    				return;
            	}
            	
            	var endtime=Ext.getCmp('endTime').getValue(),starttime=Ext.getCmp('startTime').getValue();
            	if(endtime!=''&&starttime!=''&&endtime<starttime){
            		showErrorMsg("请保证截止时间不小于起始时间",grid);
    				return;
            	}
            	termStore.load();
//            	termStoreQuery.load();  
//              grid.getStore().load();  //grid加载的store为[termStore]
              queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
    
    // 列表当前选择记录
    var rec;
    
//    var qryMenu = {
//        text: '详细信息',
//        width: 85,
//        iconCls: 'edit',
//        disabled: true,
//        handler:function() {
//            var selectedRecord = grid.getSelectionModel().getSelected();
//            if(selectedRecord == null)
//            {
//                showAlertMsg("没有选择记录",grid);
//                return;
//            } 
//            selectTermInfo(selectedRecord.get('termId'),selectedRecord.get('mchtCd'));	
//        }
//    };
    var addMenu={
    	text: '新增',
    	width: 85,
    	id: 'add',
    	iconCls: 'add',
    	handler: function(){
    		addWin.show();
    	}
    }
    var delMenu={
        text: '删除',
        width: 85,
        iconCls: 'delete',
        handler: function(){
        	if(grid.getSelectionModel().hasSelection()) {
        		var rec = grid.getSelectionModel().getSelected();
				var brhId = rec.get('brhId');
				showConfirm('确定要删除该仓库吗？仓库编码：' + brhId,grid,function(bt) {
					if(bt == "yes") {
						Ext.Msg.alert('提示', '您点击了确定！');
//						Ext.Ajax.requestNeedAuthorise({
//							url: 'T100100Action.asp?method=delete',
//							success: function(rsp,opt) {
//								var rspObj = Ext.decode(rsp.responseText);
//								if(rspObj.success) {
//									showSuccessMsg(rspObj.msg,grid);
//									grid.getStore().reload();
//									SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
//										upBrhStore.loadData(Ext.decode(ret));
//									});
//									grid.getTopToolbar().items.items[2].disable();
//								} else {
//									showErrorMsg(rspObj.msg,grid);
//								}
//							},
//							params: { 
//								brhId: brhId,
//								txnId: '10101',
//								subTxnId: '02'
//							}
//						});
					}else{
						Ext.Msg.alert('提示', '您点击的取消按钮');
					}
				});
        	}else{
        		Ext.Msg.alert('提示', '请选择一条记录！');
        	}
        }
    }
    var updateMenu={
        text: '修改',
        width: 85,
        iconCls: 'edit',
        handler: function(){
        	uptWin.show();
        }
    }
    var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
	var excelExport = {
			text: '导出',
			width: 60,
			id:'download',
//			renderTo: 'report',
			iconCls: 'download',
//			items: [{
//				xtype: 'panel',
//				html: '<br/><br/>'
//			}],
			handler:function() {
				excelDown.show();
			}
	};
	
	var excelQueryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
		autoHeight: true,
//		renderTo: 'report',
		iconCls: 'T50902',
//		waitMsgTarget: true,
//		items: [{
//			xtype: 'panel',
//			html: '<br/><br/>'
//		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确认导出',
			iconCls: 'download',
			handler: function() {
				if(!excelQueryForm.getForm().isValid()) {
					return;
				}
				excelQueryForm.getForm().submit({
					url: 'T30104Action_download.asp',
					
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					//showAlertMsg(action.result.msg,grid);
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																		action.result.msg+'&key=exl20exl';
						excelDown.hide();
					},
					failure: function(form,action) {
						Ext.MessageBox.show({
							msg: '下载失败！',
							title: '错误提示',
							animEl: Ext.getBody(),
							buttons: Ext.MessageBox.OK,
							icon: Ext.MessageBox.ERROR,
							modal: true,
							width: 250
						});
						excelDown.hide();
					},
					params: {
						txnId: '30104',
						subTxnId: '03'
					}
				});
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
		title: '仓库信息',
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
	 var addWin = new Ext.Window({
		 title: '新增仓库',
	     layout: 'fit',
	     width: 550,
	     autoHeight: true,
	     items: [topAddPanel],
	     buttonAlign: 'center',
	     closeAction: 'hide',
	     resizable: false,
	     closable: true,
	     animateTarget: 'add',
	     tools: [{
	     	id: 'minimize',
	        handler: function(event,toolEl,panel,tc) {
		        panel.tools.maximize.show();
		        toolEl.hide();
		        addWin.collapse();
		        addWin.getEl().pause(1);
		        addWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
	        },
	            qtip: '最小化',
	            hidden: false
	        },{
	            id: 'maximize',
	            handler: function(event,toolEl,panel,tc) {
	                panel.tools.minimize.show();
	                toolEl.hide();
	                addWin.expand();
	                addWin.center();
	            },
	            qtip: '恢复',
	            hidden: true
	        }]
	    });
	 var uptWin = new Ext.Window({
		 title: '修改仓库信息',
	     layout: 'fit',
	     width: 550,
	     autoHeight: true,
	     items: [topUptPanel],
	     buttonAlign: 'center',
	     closeAction: 'hide',
	     resizable: false,
	     closable: true,
	     animateTarget: 'add',
	     tools: [{
	     	id: 'minimize',
	        handler: function(event,toolEl,panel,tc) {
		        panel.tools.maximize.show();
		        toolEl.hide();
		        uptWin.collapse();
		        uptWin.getEl().pause(1);
		        uptWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
	        },
	            qtip: '最小化',
	            hidden: false
	        },{
	            id: 'maximize',
	            handler: function(event,toolEl,panel,tc) {
	                panel.tools.minimize.show();
	                toolEl.hide();
	                uptWin.expand();
	                uptWin.center();
	            },
	            qtip: '恢复',
	            hidden: true
	        }]
	    });
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 550,
        autoHeight: true,
        items: [topQueryPanel],
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
        }]
    });
    
    var menuArr = new Array();
    
    menuArr.push(addMenu);     
    menuArr.push(updateMenu); 
    menuArr.push(delMenu); 
    menuArr.push(queryMenu); 
    menuArr.push(excelExport); 
    
    
    // 仓库信息列表
    var grid = new Ext.grid.GridPanel({
        title: '仓库信息维护',
        iconCls: 'T301',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
//      autoHeight: true,
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
    
});