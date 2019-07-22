Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	
	var addToolMenu = {
		text : '工具维护',
		width : 60,
		iconCls : 'logo',
		disabled : false,
		handler : function() {
			addToolWin.show();
		}
	};

	var fields = [
		{name: 'toolCode'},
		{name: 'toolName'},
		{name: 'note1'}
	];

	var firstGridStore = new Ext.data.JsonStore({
		fields : fields,
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=whseTool'
		}),
		root: 'data'
	});

	var cols = [
		{id:"toolCode",header: "工具编码", width: 100, dataIndex: 'toolCode',hidden: true},
		{header: "工具名称", width: 100, dataIndex: 'toolName'},
		{header: "工具型号", width: 100, dataIndex: 'note1'}
	];
	var cols2 = [
		{id:"toolCode",header: "工具编码", width: 100, dataIndex: 'toolCode',hidden: true},
		{header: "工具名称", width: 100, dataIndex: 'toolName'},
		{header: "工具型号", width: 100, dataIndex: 'note1'}
	];
	var secondGridStore = new Ext.data.JsonStore({
		fields : fields,
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=workTool'
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
		autoExpandColumn : 'toolCode',
		title            : '在库工具<span style="color:red;margin-left:20px">注：按住CTRL键可多选多件拖动</span>',
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
		autoExpandColumn : 'toolCode',
		title            : '已选择工具<span style="color:red;margin-left:20px">注：按住CTRL键可多选多件拖动</span>',
		height: 300,
		width: 430,
	});
	
	
	
	
	
	
	
	
	var addToolForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: 800,
		width: 600,
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
				var workCode = addToolForm.getForm().findField("workCodeCb").getValue();
				Ext.Ajax.request({
					url : 'T130103Action_getWorkInfoData.asp',
					params : {
						workCode : workCode
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success){
							var whseCode = rspObj.msg.whseCode;
							firstGridStore.load({            
								params : {               
									start : 0,               
									whseCode : whseCode,
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
			title: '<center>请为该工单选择工具</center>',
			layout: 'table',
			items: [firstGrid,secondGrid]
		})]
	});
	//toolWorkStore.load();
    var addToolWin = new Ext.Window({
		title: '工具维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 850,
		autoHeight: true,
		layout: 'fit',
		items: [addToolForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '提交',
			handler: function() {
				var workCode = addToolForm.getForm().findField("workCodeCb").getValue();
				if(secondGrid.getStore().getCount() == 0) {
					showConfirm('您确定要清除该工单所分配的工具吗？',secondGrid,function(bt) {
						if(bt == 'yes') {
							Ext.Ajax.request({
								url : 'T130103Action.asp?method=delWorkTool',
								params : {
									workCode : workCode,
									txnId: '130102',
									subTxnId: '02'
								},
								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success){
										showSuccessMsg('工具分配成功!',grid);
										firstGridStore.removeAll();
										secondGridStore.removeAll();
										addToolForm.getForm().reset();
										addToolWin.hide();
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
						var toolCode = record.data.toolCode;
						var subparam={
							workCode: workCode,
							toolCode: toolCode
						}
						subParams.push(subparam);
					}
					Ext.Ajax.request({
						url : 'T130103Action.asp?method=addWorkTool',
						params : {
							tools : JSON.stringify(subParams),
							workCode: workCode,
							txnId: '130102',
							subTxnId: '01'
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success){
								showSuccessMsg('工具分配成功!',grid);//showErrorMsg(action.result.msg,addMhForm)
								firstGridStore.removeAll();
								secondGridStore.removeAll();
								addToolForm.getForm().reset();
								addToolWin.hide();
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
				var workCode = addToolForm.getForm().findField("workCodeCb").getValue();
				Ext.Ajax.request({
					url : 'T130103Action_getWorkInfoData.asp',
					params : {
						workCode : workCode
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success){
							var whseCode = rspObj.msg.whseCode;
							firstGridStore.load({            
								params : {               
									start : 0,               
									whseCode : whseCode,
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
			}
		},{
			text: '关闭',
			handler: function() {
				addToolWin.hide();
			}
		}]
	});
    addToolWin.on('show',function(){
		var selectedRec = grid.getSelectionModel().getSelected();
		addToolWin.getEl().mask('加载中......');
		setTimeout(function() {
			addToolWin.getEl().unmask();
		},600);
		var blankRecord =  Ext.data.Record.create(fields);
		var firstGridDropTargetEl =  firstGrid.getView().scroller.dom;
		var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
			ddGroup    : 'firstGridDDGroup',
			notifyDrop : function(ddSource, e, data){
				var records =  ddSource.dragData.selections;
				Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
				firstGrid.store.add(records);
				firstGrid.store.sort('toolCode', 'ASC');
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
				secondGrid.store.sort('toolCode', 'ASC');
				return true
			}
		});
	});
	var detailMenu = {
		text : '查看详细信息',
		width : 100,
		iconCls : 'detail',
		disabled : false,
		handler : function() {
			Ext.MessageBox.alert('提示', '你点了查看详情按钮!');
			var rec = grid.getSelectionModel().getSelected();
            selectTermInfo();
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
			fieldLabel: '工单',
			labelStyle: 'padding-left: 5px',
			labelAlign: 'right',
			hiddenName: 'workCodeQu',
			blankText: '请选择',
			width:200,
			editable: false,
			allowBlank: true,
			emptyText: "--请选择工单--",
		},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '调度号',
			width:200,
			id: 'dispatchCodeQu',
			name: 'dispatchCodeQu',
			allowBlank: true
			
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
//	            var workStatus =  queryForm.getForm().findField("workStatusQu").getValue();
	            var dispatchCode =  queryForm.getForm().findField("dispatchCodeQu").getValue();
				var param = "?a=1";
				if(workCode){
					param = param + "&workCode="+workCode;
				}
//				if(workStatus){
//					param = param + "&workStatus="+workStatus;
//				}
				if(dispatchCode){
					param = param + "&dispatchCode="+dispatchCode;
				}
				window.location.href = Ext.contextPath +"/exportExcelT130102.asp"+param;
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
	menuArr.push(addToolMenu);
	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=workTools'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[//workCode,dispatchCode,workName,toolTypeName,toolName,toolStatus,inWhse,infoSign,addDate
            {name: 'workCode',mapping: 'workCode'},
            {name: 'dispatchCode',mapping: 'dispatchCode'},
            {name: 'workName',mapping: 'workName'},
            {name: 'workStatus',mapping: 'workStatus'},
            {name: 'toolTypeName',mapping: 'toolTypeName'},
			{name: 'toolName',mapping: 'toolName'},
			{name: 'note2',mapping: 'note2'},
			{name: 'toolStatus',mapping: 'toolStatus'},
			{name: 'inWhse',mapping: 'inWhse'},
            {name: 'infoSign',mapping: 'infoSign'},
            {name: 'addDate',mapping: 'addDate'}
        ])
    });
	termStore.load();
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            workCode: queryForm.getForm().findField("workCodeQu").getValue(),//Ext.getCmp('workCodeQu').getValue(),
//            workStatus: queryForm.getForm().findField("workStatusQu").getValue(),
            dispatchCode:Ext.getCmp('dispatchCodeQu').getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([//workCode,dispatchCode,workName,toolTypeName,toolName,toolStatus,inWhse,infoSign,addDate
//    	new Ext.grid.RowNumberer(),
    	{header: '工单编码',dataIndex: 'workCode',sortable: true,width: 120},
		{header: '调度号',dataIndex: 'dispatchCode',sortable: true,width: 100},
		{header: '工单名称',dataIndex: 'workName',sortable: true,width: 180},
		{header: '工单状态',dataIndex: 'workStatus',sortable: true,width: 100},
		{header: '工具类型',dataIndex: 'toolTypeName',sortable: true,width: 120},
		{header: '工具名称',dataIndex: 'toolName',sortable: true,width: 120},
		{header: '工具型号',dataIndex: 'note2',sortable: true,width: 120},
		{header: '工具状态',dataIndex: 'toolStatus',sortable: true,width: 100},
		{header: '是否在库',dataIndex: 'inWhse',sortable: true,width: 100},
		{header: '当前状态',dataIndex: 'infoSign',sortable: true,width: 120},
		{header: '添加时间',dataIndex: 'addDate',sortable: true,width: 150}
    ]);
	// 仓库信息列表
    var grid = new Ext.grid.GridPanel({
        title: '作业工具管理',
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