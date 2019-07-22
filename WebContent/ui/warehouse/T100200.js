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
//			if(rec == null){
//				showAlertMsg("没有选择记录",grid);
//				return;
//			}
//            selectTermInfo(rec.get('termId'),rec.get('mchtCd'));
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
	//employeeCode,employeeName,sex,employeeTel,costOrgName,whseCode,whseName,infoSign,addDate
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'whseCodeQuery',
			name: 'whseCodeQuery',
			fieldLabel: '仓库编码',
			width:260
		},{
			xtype: 'textfield',
			id: 'whseNameQuery',
			name: 'whseNameQuery',
			fieldLabel: '仓库名称',
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
		},{
			xtype: 'textfield',
			fieldLabel: '人员名称',
			id: 'employeeNameQuery',
			hiddenName: 'employeeNameQuery',
			width: 260
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
				var  whseCode =  queryForm.findById('whseCodeQuery').getValue();
	            var whseName =  queryForm.findById('whseNameQuery').getValue();
	            var startDate =  typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Y-m-d');
	        	var endDate = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Y-m-d');
	            var employeeName = queryForm.findById('employeeNameQuery').getValue();
				var param = "?a=1";
				if(whseCode){
					param = param + "&whseCode="+whseCode;
				}
				if(whseName){
					param = param + "&whseName="+whseName;
				}
				if(startDate){
					param = param + "&startDate="+startDate;
				}
				if(endDate){
					param = param + "&endDate="+endDate;
				}
				if(employeeName){
					param = param + "&employeeName="+employeeName;
				}
				window.location.href = Ext.contextPath +"/exportExcelT100200.asp"+param;
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
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=railWhseEmployee'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
        	//0employeeCode,1employeeName,2sex,3employeeTel,4costOrgName,5workCode,6workName,7whseCode,8whseName,9infoSign,10addDate
            {name: 'employeeCode',mapping: 'employeeCode'},
            {name: 'employeeName',mapping: 'employeeName'},
            {name: 'sex',mapping: 'sex'},
            {name: 'employeeTel',mapping: 'employeeTel'},
            {name: 'costOrgName',mapping: 'costOrgName'},
            {name: 'workCode',mapping: 'workCode'},
            {name: 'workName',mapping: 'workName'},
			{name: 'whseCode',mapping: 'whseCode'},
			{name: 'whseName',mapping: 'whseName'},
			{name: 'infoSign',mapping: 'infoSign'},
			{name: 'addDate',mapping: 'addDate'}
        ])
    });
	termStore.load();
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            whseCode: queryForm.findById('whseCodeQuery').getValue(),
            whseName: queryForm.findById('whseNameQuery').getValue(),
            startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Y-m-d'),
        	endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Y-m-d'),
            employeeName: queryForm.findById('employeeNameQuery').getValue()
        });
    });
	
    function sexRender(sex) {
		switch(sex) {
			case '1': return '男';
			case '2': return '女';
		}
	}
	
    function infoSignRender(infoSign) {
		switch(infoSign) {
			case '1': return '入库';
			case '2': return '出库';
		}
	}
    
	var termColModel = new Ext.grid.ColumnModel([
//    	new Ext.grid.RowNumberer(),
    	{id:'employeeCode',header: '人员编码',dataIndex: 'employeeCode',sortable: true,width: 80},
		{header: '人员名称',dataIndex: 'employeeName',sortable: true,width: 80},
		{header: '性别',dataIndex: 'sex',sortable: true,width: 50},//renderer: sexRender,
		{header: '联系方式',dataIndex: 'employeeTel',sortable: true,width: 120},
		{header: '所属单位',dataIndex: 'costOrgName',sortable: true,width: 150},
		{header: '工单编码',dataIndex: 'workCode',sortable: true,width: 150},
		{header: '工单名称',dataIndex: 'workName',sortable: true,width: 150},
		{header: '仓库编码',dataIndex: 'whseCode',sortable: true,width: 80},
		{header: '仓库名称',dataIndex: 'whseName',sortable: true,width: 120},
		{header: '出库/入库',dataIndex: 'infoSign',sortable: true,width: 80},
		{header: '出入时间',dataIndex: 'addDate',sortable: true,width: 150,}
    ]);
	// 仓库盘点
    var grid = new Ext.grid.GridPanel({
        title: '人员出入库查询',
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