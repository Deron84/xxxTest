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
				hiddenName: 'accessCodeselect',
				blankText: '请选择门禁',
				emptyText: "--请选择门禁--",
				allowBlank: true,
				editable: false,
				width:300
        	}]
		},{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'dynamicCombo',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '终端设备编码',
				methodName: 'getTdAccessesEquip',
				hiddenName: 'equipCodeselect',
				blankText: '请选择终端设备',
				emptyText: "--请选择终端设备--",
				allowBlank: true,
				editable: false,
				width:300
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
			   var equipCode=  queryForm.getForm().findField("equipCodeselect").getValue();
			   var accessCode=  queryForm.getForm().findField("accessCodeselect").getValue();
				var param = "?a=1";
				if(accessCode){
					param = param + "&accessCode="+accessCode;
				}
				if(equipCode){
					param = param + "&equipCode="+equipCode;
				}
				window.location.href = Ext.contextPath +"/exportExcelT120103.asp"+param;
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
            url: 'gridPanelStoreAction.asp?storeId=railAccessEquipInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: ''
        },[
            {name: 'accessCode',mapping: 'accessCode'},
            {name: 'equipCode',mapping: 'equipCode'},
            {name: 'equipName',mapping: 'equipName'},
            {name: 'equipType',mapping: 'equipTypeName'},
            {name: 'equipIp',mapping: 'equipIp'},
            {name: 'note1',mapping: 'note1'},
            {name: 'note2',mapping: 'note2'},
            {name: 'installDate',mapping: 'installDate'},
            {name: 'equipStatus',mapping: 'equipStatus'},
            {name: 'addUser',mapping: 'addUser'},
            {name: 'addDate',mapping: 'addDate'}
            ])
    });
	termStore.load();
	
	function equipStatusRender(val) {
		if(val == '正常') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="正常"/>正常';
		} else if(val == '故障') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="故障"/>故障';
		} else if(val == '未连接') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="未连接"/>未连接';
		}  
		return '状态未知';
	}
	
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            accessCode: queryForm.getForm().findField("accessCodeselect").getValue(),
            equipCode: queryForm.getForm().findField("equipCodeselect").getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
    	{header: '门禁编码',dataIndex: 'accessCode',sortable: true,width: 100},
		{id: 'equipCode',header: '终端设备编码',dataIndex: 'equipCode',sortable: true,width: 100},
		{header: '终端设备名称',dataIndex: 'equipName',sortable: true,width: 120},
		{header: '设备类型',dataIndex: 'equipType',sortable: true,width: 100},
		{header: '设备IP',dataIndex: 'equipIp',sortable: true,width: 100},
		{header: '用户名',dataIndex: 'note1',sortable: true,width: 80},
		{header: '密码',dataIndex: 'note2',sortable: true,width: 100},
		{header: '安装时间',dataIndex: 'installDate',sortable: true,width: 150},
		{header: '设备状态',dataIndex: 'equipStatus',sortable: true,renderer: equipStatusRender,width: 80},
		{header: '创建人',dataIndex: 'addUser',sortable: true,width: 80},
		{header: '创建时间',dataIndex: 'addDate',sortable: true,width: 150}
    ]);
	// 工单信息列表
    var grid = new Ext.grid.GridPanel({
        title: '终端设备查询',
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