Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();

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
			xtype: 'textfield',
			id: 'whseBeforeCode',
			name: 'whseBeforeCode',
			fieldLabel: '移前仓库编码',
			width:260
		},{
			xtype:'textfield',
			fieldLabel: '移前仓库名称',
			id:'whseBeforeName',
			name:'whseBeforeName',
			width:260
		},{
			xtype: 'textfield',
			id: 'whseAfterCode',
			name: 'whseAfterCode',
			fieldLabel: '移后仓库编码',
			width:260
		},{
			xtype:'textfield',
			fieldLabel: '移后仓库名称',
			id:'whseAfterName',
			name:'whseAfterName',
			width:260
		},{
			xtype:'textfield',
			fieldLabel: '工具编码',
			id:'toolCode',
			name:'toolCode',
			width:260
		},{
			xtype:'textfield',
			fieldLabel: '工具名称',
			id:'toolName',
			name:'toolName',
			width:260
		},{
			xtype: 'datefield',
			width: 260,
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
			width: 260,
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '结束日期',
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
		       	var endtime=Ext.getCmp('endDate').getValue(),starttime=Ext.getCmp('startDate').getValue();
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
				var  whseBeforeCode =  queryForm.findById('whseBeforeCode').getValue();
	            var whseBeforeName = queryForm.findById('whseBeforeName').getValue();
				var whseAfterCode = queryForm.findById('whseAfterCode').getValue();
	            var whseAfterName = queryForm.findById('whseAfterName').getValue();
	            var startDate =  typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Y-m-d');
	        	var endDate = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Y-m-d');
	            var toolName = queryForm.findById('toolName').getValue();
	            var toolCode= queryForm.findById('toolCode').getValue();
				var param = "?a=1";
				if(whseBeforeCode){
					param = param + "&whseBeforeCode="+whseBeforeCode;
				}
				if(whseBeforeName){
					param = param + "&whseBeforeName="+whseBeforeName;
				}
				if(whseAfterCode){
					param = param + "&whseAfterCode="+whseAfterCode;
				}
				if(whseAfterName){
					param = param + "&whseAfterName="+whseAfterName;
				}
				if(startDate){
					param = param + "&startDate="+startDate;
				}
				if(endDate){
					param = param + "&endDate="+endDate;
				}
				if(toolName){
					param = param + "&toolName="+toolName;
				}
				if(toolCode){
					param = param + "&toolCode="+toolCode;
				}
				if(whseBeforeName){
					param = param + "&whseBeforeName="+whseBeforeName;
				}
				window.location.href = Ext.contextPath +"/exportExcelT100104.asp"+param;
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
//	menuArr.push(detailMenu);
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=toolTransfer'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
        	//toolCode,toolName,transferMsg,infoSign,whseBefore,whseBeforeName,whseAfter,whseAfterName,addUser,addDate,modelName,toolExpiration,toolStatus,inWhse,lastExam
            {name: 'toolCode',mapping: 'toolCode'},
            {name: 'toolName',mapping: 'toolName'},
            {name: 'transferMsg',mapping: 'transferMsg'},
            {name: 'infoSign',mapping: 'infoSign'},
			{name: 'whseBefore',mapping: 'whseBefore'},
			{name: 'whseBeforeName',mapping: 'whseBeforeName'},
			{name: 'whseAfter',mapping: 'whseAfter'},
            {name: 'whseAfterName',mapping: 'whseAfterName'},
            {name: 'addUser',mapping: 'addUser'},
            {name: 'addDate',mapping: 'addDate'}
        ])
    });
    termStore.load();
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            whseBeforeCode: queryForm.findById('whseBeforeCode').getValue(),
            whseBeforeName: queryForm.findById('whseBeforeName').getValue(),
            whseAfterCode: queryForm.findById('whseAfterCode').getValue(),
            whseAfterName: queryForm.findById('whseAfterName').getValue(),
            startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Y-m-d'),
        	endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Y-m-d'),
            toolName: queryForm.findById('toolName').getValue(),
            toolCode: queryForm.findById('toolCode').getValue()
        });
    });
	
	
//    toolCode,toolName,transferMsg,infoSign,whseBefore,whseBeforeName,whseAfter,whseAfterName,addUser,addDate,modelName,toolExpiration,toolStatus,inWhse,lastExam
  //0正常；1维护；3报废；4停用
    
    function infoSignRender(infoSign) {
		switch(infoSign) {
			case '0': return '手工调库';
			case '1': return '自动调库';
		}
	}
	var termColModel = new Ext.grid.ColumnModel([
//    	new Ext.grid.RowNumberer(),
    	{header: '工具编码',dataIndex: 'toolCode',sortable: true,width: 100},
		{header: '工具名称',dataIndex: 'toolName',sortable: true,width: 100},
		{header: '移库前仓库编码',dataIndex: 'whseBefore',sortable: true,width: 100},
		{header: '移库前仓库名称',dataIndex: 'whseBeforeName',sortable: true,width: 120},
		{header: '移库后仓库编码',dataIndex: 'whseAfter',sortable: true,width: 100},
		{header: '移库后仓库名称',dataIndex: 'whseAfterName',sortable: true,width: 120},
		{header: '移库事由',dataIndex: 'transferMsg',sortable: true,width: 150},
		{header: '移库类型',dataIndex: 'infoSign',sortable: true,width: 80},//0手工调库；1自动调库//,renderer: infoSignRender
		{header: '移库操作人',dataIndex: 'addUser',sortable: true,width: 100},
		{header: '移库时间',dataIndex: 'addDate',sortable: true,width: 150}
    ]);
	// 仓库盘点
    var grid = new Ext.grid.GridPanel({
        title: '移库查询',
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