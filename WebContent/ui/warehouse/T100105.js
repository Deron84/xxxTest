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
			queryWin.show();
		}
	};
	//0正常；1维护；3报废；4停用
	var isInWhseStore = new Ext.data.ArrayStore({
		fields : [ 'valueField', 'displayField' ],
		data : [ [ '1', '在库' ], [ '2', '出库' ]]
	})
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		items: [
			{
				xtype: 'textfield',
				id: 'whseCodeQuery',
				name: 'whseCodeQuery',
				fieldLabel: '仓库编码',
				width:200
			},{
				xtype:'textfield',
				fieldLabel: '仓库名称',
				id:'whseNameQuery',
				name:'whseNameQuery',
				width:200
			},{
				xtype:'textfield',
				fieldLabel: '工具名称',
				id:'toolNameQuery',
				name:'toolNameQuery',
				width:200
			}
		]
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
				var whseCode = queryForm.getForm().findField("whseCodeQuery").getValue();
				var whseName = queryForm.getForm().findField("whseNameQuery").getValue();
				var toolName = queryForm.getForm().findField("toolNameQuery").getValue();
				var param = "?a=1";
				if(whseCode){
					param = param + "&whseCode="+whseCode;
				}
				if(whseName){
					param = param + "&whseName="+whseName;
				}
				if(toolName){
					param = param + "&toolName="+toolName;
				}
				window.location.href = Ext.contextPath +"/exportExcelT100105.asp"+param;
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
            url: 'gridPanelStoreAction.asp?storeId=railWhseToolTongji'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
        	//仓库编码、仓库名称、工具名称、存放位置（架-层-位）、数量
            {name: 'whseCode',mapping: 'whseCode'},
            {name: 'whseName',mapping: 'whseName'},
            {name: 'toolName',mapping: 'toolName'},
            {name: 'position',mapping: 'position'},
			{name: 'inWhseNum',mapping: 'inWhseNum'},
			{name: 'outWhseNum',mapping: 'outWhseNum'},
			{name: 'num',mapping: 'num'}
        ])
    });
	termStore.load();
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {//toolStatusQuery，whseCodeQuery
            start: 0,
            whseCode: queryForm.getForm().findField("whseCodeQuery").getValue(),
            whseName: queryForm.getForm().findField("whseNameQuery").getValue(),
            toolName: queryForm.getForm().findField("toolNameQuery").getValue()
        });
    });
	
	
	var termColModel = new Ext.grid.ColumnModel([
//    	new Ext.grid.RowNumberer(),
//    	工具类型、工具型号、工具名称、库、架、层、位、数量
//    	toolType,toolModel,toolName,whse,stand,floor,position,countNum
    	{header: '仓库编码',dataIndex: 'whseCode',sortable: true,width: 120},
		{header: '仓库名称',dataIndex: 'whseName',sortable: true,width: 120},
		{header: '工具名称',dataIndex: 'toolName',sortable: true,width: 120},
		{header: '存放位置（架-层-位）',dataIndex: 'position',sortable: true,width: 250},
		{header: '在库数量',dataIndex: 'inWhseNum',sortable: true,width: 120},
		{header: '出库数量',dataIndex: 'outWhseNum',sortable: true,width: 120},
		{header: '总数量',dataIndex: 'num',sortable: true,width: 120}
    ]);
	// 仓库盘点
    var grid = new Ext.grid.GridPanel({
        title: '仓库统计',
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