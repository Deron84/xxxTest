Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();

	var detailMenu = {
			text: '查看人员图片',
			width: 85,
			disabled: false,
			iconCls: 'detail',
			handler:function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
	            if(selectedRecord == null)
	            {
	                showAlertMsg("没有选择记录",grid);
	                return;
	            }
	            Ext.Ajax.request({
	            	url : 'T130400Action_getData.asp',
					params : {
						employeeCode : selectedRecord.get('employeeCode')
					},
					success : function(rsp, opt) {
						//showErrorMsg(rspObj.msg,grid);
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							
							var imgPath = selectedRecord.data.employeeImg;
				            html = '<div style="width:100%;height:100%;"><img id="videoSource" style="width:100%;height:100%;" src="'+imgPath+'" /><div>';
				            songPlayer.show();
				            songPlayer.center();
				            Ext.getCmp("playerPanel").body.update(html); 
//				            Ext.getCmp('toolNameDet').setValue(rspObj.msg.toolName);
//				            Ext.getCmp('enableStatusDet').setValue(enableStatusRender(rspObj.msg.enableStatus));
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					}
				}); 
	          
			}
	};
	
	 var playerForm=new Ext.Panel({
	        frame: true,
	        height: 100,
	        width: 400,
	        labelWidth: 85,
	        waitMsgTarget: true,
	        items: [{  
			        xtype : 'panel',  
			        id : 'playerPanel', 
			        labelStyle: 'padding-left: 5px',
			        width:400,  
			        height:400, 
			        html:""
			    }],
	    });
			 
	var songPlayer = new Ext.Window({  
        layout : 'fit',  
        width:400,  
        height:400,  
        modal : true,  
        frame: true,
        closable : false,
        items : [playerForm]  ,
    	buttons: [{
			text: '关闭',
			handler: function() {
				songPlayer.hide();
				Ext.getCmp("playerPanel").body.update("");  
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
			id: 'employeeCodefind',
			name: 'employeeCodefind',
			fieldLabel: '人员编码',
			width:200
		},{
			xtype: 'textfield',
			id: 'employeeNamefind',
			name: 'employeeNamefind',
			fieldLabel: '人员名称',
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
				var employeeCode= Ext.getCmp('employeeCodefind').getValue();
	            var employeeName= Ext.getCmp('employeeNamefind').getValue();
				var param = "?a=1";
				if(employeeCode){
					param = param + "&employeeCode="+employeeCode;
				}
				if(employeeName){
					param = param + "&employeeName="+employeeName;
				}
				window.location.href = Ext.contextPath +"/exportExcelT130401.asp"+param;
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
	
	menuArr.push(detailMenu);
	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=employees'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'employeeCode'
        },[
            {name: 'employeeCode',mapping: 'employeeCode'},
            {name: 'employeeName',mapping: 'employeeName'},
            {name: 'sex',mapping: 'sex'},
			{name: 'birthday',mapping: 'age'},
			{name: 'idNumber',mapping: 'idNumber'},
			{name: 'employeeImg',mapping: 'employeeImg'},
			{name: 'constOrg',mapping: 'constOrgName'},
			{name: 'job',mapping: 'job'},
			{name: 'employeeType',mapping: 'typeName'},
			{name: 'employeeTel',mapping: 'employeeTel'},
			{name: 'password',mapping: 'password'},
			{name: 'entryDate',mapping: 'entryDate'},
			{name: 'dept',mapping: 'brhName'}
            ])
    });
	termStore.load();
	
	/**
	 * 人员性别
	 */
	function sexRender(val) {
		if(val == '男') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/male.png" />';
		} else if(val == '女') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/female.png" />';
		}
		return val;
	}
	
	
    termStore.on('beforeload', function() {
    	Ext.apply(this.baseParams, {
            start: 0,
            employeeCode: Ext.getCmp('employeeCodefind').getValue(),
            employeeName: Ext.getCmp('employeeNamefind').getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
//    	new Ext.grid.RowNumberer(),
		{id: 'id',header: '人员编码',dataIndex: 'employeeCode',sortable: true,width: 80},
		{header: '人员名称',dataIndex: 'employeeName',sortable: true,width: 80},
		{header: '性别',dataIndex: 'sex',sortable: true,renderer:sexRender,width: 50},
		{header: '年龄',dataIndex: 'birthday',sortable: true,width: 50},
		{header: '身份证号',dataIndex: 'idNumber',sortable: true,width: 150},
		{header: '照片存放地址',dataIndex: 'employeeImg',sortable: true,width: 150},
		{header: '作业单位',dataIndex: 'constOrg',sortable: true,width: 100},
		{header: '职务',dataIndex: 'job',sortable: true,width: 90},
		{header: '人员类型',dataIndex: 'employeeType',sortable: true,width: 80},
		{header: '联系方式',dataIndex: 'employeeTel',sortable: true,width: 100},
		{header: '所属机构',dataIndex: 'dept',sortable: true,width: 120}
    ]);
	// 工单信息列表
    var grid = new Ext.grid.GridPanel({
        title: '人员信息查询',
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