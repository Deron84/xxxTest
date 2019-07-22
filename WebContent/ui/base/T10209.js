Ext.onReady(function() {
	
	var topQueryPanel = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 300,
        autoHeight: true,
        labelWidth: 80,
		items: [new Ext.form.TextField({
				id: 'qryCityCode',
				name: 'qryCityCode',
				allowBlank: false,
				fieldLabel: '城市地区码'
			}),new Ext.form.TextField({
				id: 'qryCityName',
				name: 'qryCityName',
				fieldLabel: '地区名称'
			})],
		buttons: [{
			text: '查询',
			handler: function() 
			{
				areaStore.load();
                queryWin.hide();
			}
		},{
			text: '重填',
			handler: function() {
				topQueryPanel.getForm().reset();
			}
		}]
	});

	var areaStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=areaInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'cityCodeUpd',mapping: 'cityCode'},
			{name: 'cityNameUpd',mapping: 'cityName'},
			{name: 'modiOprIdUpd',mapping: 'modiOprId'},
			{name: 'modiTimeUpd',mapping: 'modiTime'},
			{name: 'initTimeUpd',mapping: 'initTime'}
		])
	});
	
	
	var areaModel = new Ext.grid.ColumnModel([
		{id: 'cityCode',header: '地区码编号',dataIndex: 'cityCodeUpd',width: 100},
        {header: '地区名称',dataIndex: 'cityNameUpd',width: 100},
		{header: '最后操作柜员号',dataIndex: 'modiOprIdUpd',width: 200},
		{header: '最后修改时间',dataIndex: 'modiTimeUpd',width: 180},
		{header: '创建时间',dataIndex: 'initTimeUpd',width: 180}
	]);
	
	
	var addMenu = {
		text: '新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			areaWin.show();
			areaWin.center();
		}
	};
	var updMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		handler:function() {
			var selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
			updateForm.getForm().loadRecord(selectedRecord);
			updateWin.show();
			updateWin.center();
		}
	};
	
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			handler:function() {
		var selectedRecord = grid.getSelectionModel().getSelected();
        if(selectedRecord == null)
        {
            showAlertMsg("没有选择记录",grid);
            return;
        }
		showConfirm('确定要删除该地区码信息吗？',grid,function(bt) {
			if(bt == 'yes') {
//				showProcessMsg('正在提交，请稍后......');
				var rec = grid.getSelectionModel().getSelected();
				Ext.Ajax.requestNeedAuthorise({
					url: 'T10209Action.asp?method=delete',
					method: 'post',
					params: {
						cityCodeUpd : rec.get('cityCodeUpd'),
						txnId: '10209',
						subTxnId: '02'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							grid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,grid);
						} else {
							grid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,grid);
						}
						grid.getStore().reload();
						hideProcessMsg();
					}
				});
				
			}
		});
	}
		};
	
	var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 300,
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
	
	menuArr.push(addMenu);		//[0]
	menuArr.push('-');
	menuArr.push(updMenu);		//[1]
	menuArr.push('-');
	menuArr.push(delMenu);		//[2]
	menuArr.push('-');
	menuArr.push(queryMenu);    //[3]
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '城市地区码维护',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: areaStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: areaModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载城市地区码信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: areaStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	

	//每次在列表信息加载前都将保存按钮屏蔽
	areaStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
			cityCode: Ext.getCmp('qryCityCode').getValue(),
		    cityName: Ext.getCmp('qryCityName').getValue()
		});
	});
	areaStore.load();
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if(rec != null) {
				grid.getTopToolbar().items.items[1].enable();
			} else {
				grid.getTopToolbar().items.items[1].disable();
			}
		}
	});
	
	
	var updateForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		labelWidth: 160,
		waitMsgTarget: true,
		layout: 'column',
		items: [{
		 	       layout: 'form',
		 	       width: 389,
		 	       items: [{
		 		        	xtype: 'textfield',
		 					fieldLabel: '城市地区码*',
		 					name: 'cityCodeUpd',
		 					id: 'cityCodeUpd',
		 					allowBlank: false,
		 					readOnly: true
		 		        }]
		 		},{
		 	       layout: 'form',
		 	       width: 389,
		 	       items: [{
	 		        	xtype: 'textfield',
	 					fieldLabel: '地区名称*',
	 					maxLength:20,
	 					vtype: 'isOverMax',
	 					name: 'cityNameUpd',
	 					id: 'cityNameUpd',
	 					regex:/^[\u4e00-\u9fa5]+$/,
	 					regexText:'只能输入中文',
	 					allowBlank: false,
	 					blankText: "地区名称不能为空！"
	 		        }]
		 		}
		]
	});
	
	
	
	//添加表单
	var areaForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		labelWidth: 160,
		waitMsgTarget: true,
		layout: 'column',
		items: [{
		 	       layout: 'form',
		 	       width: 389,
		 	       items: [{
		 		        	xtype: 'textfield',
		 					fieldLabel: '城市地区码*',
		 					name: 'cityCode',
		 					id: 'cityCode',
		 					maxLength: 4,
		 					regex: /^[0-9]{4}$/,
		 					regexText: '城市地区码必须是4位数字',
		 					allowBlank: false
		 		        }]
		 		},{
		 	       layout: 'form',
		 	       width: 389,
		 	       items: [{
	 		        	xtype: 'textfield',
	 					fieldLabel: '地区名称*',
	 					name: 'cityName',
	 					id: 'cityName',
	 					maxLength: 20,
	 					vtype: 'isOverMax',
	 					regex:/^[\u4e00-\u9fa5]+$/,
	 					regexText:'只能输入中文',
	 					allowBlank: false,
	 					blankText: "地区名称不能为空！"
	 		        }]
		 		}
		]
	});


	//信息窗口
	var areaWin = new Ext.Window({
		title: '城市地区码新增',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [areaForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(areaForm.getForm().isValid()) {
					areaForm.getForm().submit({
						url: 'T10209Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							areaForm.getForm().reset();
							showSuccessMsg(action.result.msg,grid);
							areaWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,grid);
						},
						params: {
							txnId: '10209',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
					areaForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				areaWin.hide();
			}
		}]
	});
	//终端信息窗口
	var updateWin = new Ext.Window({
		title: '城市地区码修改',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [updateForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{							
			text: '确定',
			handler: function() {
				if(updateForm.getForm().isValid()) {
					updateForm.getForm().submit({
						url: 'T10209Action.asp?method=update',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							grid.getStore().reload();
							updateForm.getForm().reset();
							showSuccessMsg(action.result.msg,grid);
							updateWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,grid);
						},
						params: {
							txnId: '10209',
							subTxnId: '03'
						}
					});
				}
			}
		},{
			text: '关闭',
			handler: function() {
				updateWin.hide();
			}
		}]
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
})