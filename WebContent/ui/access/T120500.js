Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	
	
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
        	},{//0手工移库；1自动移库
   	            xtype: 'radiogroup',
   	            width:300,
   	            fieldLabel: '出入类型',
   	            id:"infoSign",
   	            labelStyle: 'padding-left: 5px',
   	            vertical: true,
   	            items: [{
					boxLabel: '出', 
					name: 'infoSign', 
					inputValue: 1
				},{
					boxLabel: '入', 
					name: 'infoSign', 
					inputValue: 2
				}]
   	        },{//0手工移库；1自动移库
   	            xtype: 'radiogroup',
   	            width:300,
   	            fieldLabel: '开启形式',
   	            id:"openSign",
   	            labelStyle: 'padding-left: 5px',
   	            vertical: true,
   	            items: [{
					boxLabel: '远程控制', 
					name: 'openSign', 
					inputValue: 1
				},{
					boxLabel: '现场开启', 
					name: 'openSign', 
					inputValue: 2
				},{
					boxLabel: '异常开启', 
					name: 'openSign', 
					inputValue: 3
				}]
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
				var   accessCode =  queryForm.getForm().findField("accessCode").getValue();
				var param = "?a=1";
				if(accessCode){
					param = param + "&accessCode="+accessCode;
				}
				window.location.href = Ext.contextPath +"/exportExcelT120501.asp"+param;
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
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=railAccessOptlog'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'id'
        },[
            {name: 'warnId',mapping: 'id'},
            {name: 'accessCode',mapping: 'accessCode'},
            {name: 'accessName',mapping: 'accessName'},
            {name: 'employeeCode',mapping: 'employeeCode'},
            {name: 'employeeName',mapping: 'employeeName'},
            {name: 'workCode',mapping: 'workCode'},
            {name: 'workName',mapping: 'workName'},
            {name: 'infoSign',mapping: 'infoSign'},
            {name: 'openSign',mapping: 'openSign'},
            {name: 'addDate',mapping: 'addDate'},
			{name: 'addUser',mapping: 'addUser'},
        ])
    });
	
	function infoSignRender(warnSystem) {
		switch(warnSystem) {
			case '1': return '出';
			case '2': return '入';
		}
	}
	function openSignRender(warnSystem) {
		switch(warnSystem) {
			case '1': return '远程控制';
			case '2': return '现场开启';
			case '3': return '异常开启';
		}
	}
	
	termStore.load({
			   params: { start: 0}  
	});
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            accessCode: queryForm.getForm().findField("accessCode").getValue(),
            infoSign : queryForm.findById('infoSign').getValue(),
            openSign : queryForm.findById('openSign').getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
    	{id:'warnId',header: '记录id',dataIndex: 'warnId',sortable: true,width: 60,hidden : true},
    	{id:'accessCode',header: '门禁编码',dataIndex: 'accessCode',sortable: true,width: 100},
		{header: '门禁名称',dataIndex: 'accessName',sortable: true,width: 120},
		{header: '员工编码',dataIndex: 'employeeCode',sortable: true,width: 100},
		{header: '员工名称',dataIndex: 'employeeName',sortable: true,width: 120},
		{header: '工单编码',dataIndex: 'workCode',sortable: true,width: 100},
		{header: '工单名称',dataIndex: 'workName',sortable: true,width: 120},
		{header: '出入类型',dataIndex: 'infoSign',sortable: true,width: 100},
		{header: '开启形式',dataIndex: 'openSign',sortable: true,width: 100},
		{header: '创建时间',dataIndex: 'addDate',sortable: true,width: 150},
		{header: '操作员',dataIndex: 'addUser',sortable: true,width: 100},
    ]);
	// 仓库信息列表
    var grid = new Ext.grid.GridPanel({
        title: '门禁开关记录',
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