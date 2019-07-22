Ext.onReady(function() {
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=railToolMaintainWarnning'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'whseName',mapping: 'whseName'},
			{name: 'toolName',mapping: 'toolName'},
			{name: 'rfid',mapping: 'rfid'},
			{name: 'location',mapping: 'location'},
			{name: 'warnMsg',mapping: 'warnMsg'},
			{name: 'infoSign',mapping: 'infoSign'},
			{name: 'verifyMsg',mapping: 'verifyMsg'},
			{name: 'verifyUser',mapping: 'verifyUser'},
			{name: 'note1',mapping: 'note1'},
			{name: 'verifyDate',mapping: 'verifyDate'}
		])
	});
	
	gridStore.load();
	 //仓库名称、工具名称、工具标签、存放位置（架层位）、预警内容、是否确认、审核人、审核时间
	//【审核、定时任务根据保养周期/最后一次保养时间，有效期进行插入】 --
	//whseName,toolName,rfid,location,warnMsg,infoSign,verifyUser,verifyDate
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '仓库名称',dataIndex: 'whseName',sortable: true,width: 120},
		{header: '工具名称',dataIndex: 'toolName',sortable: true,width: 120},
		{header: '工具标签',dataIndex: 'rfid',sortable: true,width: 180},	
		{header: '存放位置（架层位）',dataIndex: 'location',sortable: true,/*renderer: brhLvlRender,*/width: 150},
		{header: '预警信息',dataIndex: 'warnMsg',sortable: true,width: 120/*,renderer: formatDt*/},
		{header: '是否确认',dataIndex: 'infoSign',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '操作备注',dataIndex: 'verifyMsg',sortable: true,width: 120/*,renderer: formatDt*/},
		{header: '操作员',dataIndex: 'verifyUser',sortable: true,width: 80/*,renderer: formatDt*/},
		{header: '操作时间 ',dataIndex: 'verifyDate',sortable: true,width: 150/*,renderer: formatDt*/},
		{header: '预警类型',dataIndex: 'note1',sortable: true,width: 80/*,renderer: formatDt*/}
	]);
	var editMenu = {
			text : '预警确认',
			width : 60,
			iconCls : 'edit',
			disabled : true,
			handler : function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
				var data = selectedRecord.data;
				 console.log(selectedRecord);
				 console.log(data);
				var infoSign=data.infoSign;
	            if(selectedRecord == null)
	            {
	                showAlertMsg("没有选择记录",grid);
	                return;
	            } 
	            if(infoSign == '已确认')
	            {
	                showAlertMsg("预警已确认！",grid);
	                return;
	            } 
	          
	            updTermWin.show();
				updTermWin.center();
				Ext.getCmp('whseName1').setValue(data.whseName);
				Ext.getCmp('toolName1').setValue(data.toolName);
				Ext.getCmp('location1').setValue(data.location);
	            Ext.getCmp('warnMsg1').setValue(data.warnMsg);
				}
		};
	
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
//				queryForm.getForm().reset();
			}
		};
	
	var excelDowload = {
			text: '导出',
			width: 60,
			id: 'query1',
			iconCls: 'download',
			handler:function() {
				showConfirm('确认导出？',grid,function(bt) {
					if(bt == "yes") {
						
					}
				});
			}
	};
	var exportMenu = {
			text: '导出',
			width: 100,
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
					var whseCode = queryForm.findById('whseCodeS').getValue();
					var dateStart =  typeof(queryForm.findById('dateStart').getValue()) == 'string' ? '' : queryForm.findById('dateStart').getValue().dateFormat('Y-m-d');
			        var dateEnd = typeof(queryForm.findById('dateEnd').getValue()) == 'string' ? '' : queryForm.findById('dateEnd').getValue().dateFormat('Y-m-d');
			        var infoSign = queryForm.findById('infoSign').getValue();
			        var warnType = queryForm.findById('warnType').getValue();
					if(infoSign){
						infoSign = infoSign.inputValue;
					}
					if(warnType){
						warnType = warnType.inputValue;
					}
					var param = "?a=1";
					param = param + "&txnId=excel&subTxnId=110701"//日志用
					if(dateStart){
						param = param + "&dateStart="+dateStart;
					}
					if(infoSign){
						param = param + "&infoSign="+infoSign;
					}
					if(dateEnd){
						param = param + "&dateEnd="+dateEnd;
					}
					if(whseCode){
						param = param + "&whseCode="+whseCode;
					}
					if(warnType){
						param = param + "&warnType="+warnType;
					}
					window.location.href = Ext.contextPath +"/exportExcelT1107.asp"+param;
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
	var menuArr = new Array();
	menuArr.push(editMenu);
	menuArr.push('-');
	menuArr.push(queryCondition);
	menuArr.push('-');
	menuArr.push(exportMenu);
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 490,
		autoHeight: true,
		items: [{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'dynamicCombo',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '仓库编码',
				methodName: 'getRailWhse',
				hiddenName: 'whseCode',
				id: 'whseCodeS',
				blankText: '请选择仓库',
				emptyText: "--请选择仓库--",
				allowBlank: true,
				editable: false,
				width:300
        	}]
		},{
			xtype: 'datefield',
			width: 300,
			labelStyle: 'padding-left: 5px',
			id: 'dateStart',
			name: 'dateStart',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'dateEnd',
			fieldLabel: '开始日期',
			editable: false
		},{
			xtype: 'datefield',
			width: 300,
			labelStyle: 'padding-left: 5px',
			id: 'dateEnd',
			name: 'dateEnd',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'dateStart',
			fieldLabel: '结束日期',
			editable: false
		},{
   	            xtype: 'radiogroup',
   	            width:300,
   	            fieldLabel: '预警类型',
   	            id:'warnType',
   	            labelStyle: 'padding-left: 5px',
   	            vertical: true,
   	            items: [{//0未确认;1已确认
					boxLabel: '不限', 
					name: 'warnType', 
					inputValue: '-1', 
					checked: true
				},{//0未确认;1已确认
					boxLabel: '报废预警', 
					name: 'warnType', 
					inputValue: '1',
				},{
					boxLabel: '检修预警', 
					name: 'warnType', 
					inputValue: '2'
				}]
   	        },{
   	            xtype: 'radiogroup',
   	            width:300,
   	            fieldLabel: '预警状态',
   	            id:'infoSign',
   	            labelStyle: 'padding-left: 5px',
   	            vertical: true,
   	            items: [{//0未确认;1已确认
					boxLabel: '不限', 
					name: 'infoSign', 
					inputValue: -1, 
					checked: true
				},{//0未确认;1已确认
					boxLabel: '未确认', 
					name: 'infoSign', 
					inputValue: 0, 
					checked: false
				},{
					boxLabel: '已确认', 
					name: 'infoSign', 
					inputValue: 1
				}]
		}]
	});

	//机构列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '工具预警',
		iconCls: 'T110105',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '加载中......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	var updTermForm = new Ext.form.FormPanel({
        frame: true,
        height: 150,
        width: 450,
        labelWidth: 85,
        waitMsgTarget: true,
            items: [{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
//	        	hidden : true,
       			items: [{
       	            xtype: 'displayfield',
       	            labelStyle: 'padding-left: 5px',
       	            fieldLabel: '工具名称',
       	            maxLength: 20,
       	            id: 'toolName1',
       	            name: 'toolName1',
       	            width: 300,
       	       }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库名称',
					id: 'whseName1',
					name: 'whseName1',  
					width:300,
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '工具位置',
					id: 'location1',
					name: 'location1',
					width:300,
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '预警信息',
					width:300,
					id: 'warnMsg1',
					name: 'warnMsg1'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '处理意见*',
					width:300,
					id: 'verifyMsg1',
					name: 'verifyMsg'
	        	}]
			}],
    });
	
	var updTermWin = new Ext.Window({
        title: '门禁预警确认',
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
            text: '确认',
            handler: function() {
            	if(updTermForm.getForm().isValid()) {
            		var	selectedRecord = grid.getSelectionModel().getSelected();
            		
					updTermForm.getForm().submit({
						url: 'T110701Action_check.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							updTermWin.hide();
							showSuccessMsg(action.result.msg,updTermForm);
							updTermForm.getForm().reset();
							grid.getStore().reload();
						},
						failure: function(form,action) {
							if(action.result.code ==200){
								updTermWin.hide();
								showSuccessMsg(action.result.msg,updTermForm);
								updTermForm.getForm().reset();
								grid.getStore().reload();
							}else{
								showErrorMsg(action.result.msg,updTermForm);
							}
						},
						params: {
							id : selectedRecord.id,
							txnId: '120300',
							subTxnId: '00'
						}
					});
				}else{
					console.log(0);
				}
            }
        },{
            text: '关闭',
            handler: function() {
                updTermWin.hide();
            }
        }]
    });
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
			gridStore.load();
			queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//单击行，使删除按钮可用
		'rowclick': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[0].enable();
		}
	});
	
	/************************************************以下是机构相关操作员信息************************************************************/
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});

	gridStore.on('beforeload', function(){
		var infoSign = queryForm.findById('infoSign').getValue();
		var warnType = queryForm.findById('warnType').getValue();
		if(infoSign){
			infoSign = infoSign .inputValue;
		}
		if(warnType){
			warnType = warnType .inputValue;
		}
		Ext.apply(this.baseParams, {
			start: 0,
			whseCode: queryForm.findById('whseCodeS').getValue(),	
			dateStart: queryForm.findById('dateStart').getValue(),
			dateEnd: queryForm.findById('dateEnd').getValue(),
			infoSign:infoSign,
			warnType:warnType
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});