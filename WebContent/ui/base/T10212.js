Ext.onReady(function() {
	var _termTpUpd;
	var topQueryPanel = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 300,
        autoHeight: true,
        labelWidth: 80,
		items: [new Ext.form.TextField({
				id: 'qryTermTp',
				name: 'qryTermTp',
			//	allowBlank: false,
				fieldLabel: '类型编号',
				maxLength:10
			}),new Ext.form.TextField({
				id: 'qryDescr',
				name: 'qryDescr',
				fieldLabel: '终端类型',
				maxLength:40
			}),new Ext.form.TextField({
				id: 'qryTermType',
				name: 'qryTermType',
				fieldLabel: '终端型号',
				maxLength:20
			}),new Ext.form.TextField({
				id: 'qryTermNum',
				name: 'qryTermNum',
				fieldLabel: '终端版号',
				maxLength:20
			})],
		buttons: [{
			text: '查询',
			handler: function() 
			{
				termStore.load();
                queryWin.hide();
			}
		},{
			text: '重填',
			handler: function() {
				topQueryPanel.getForm().reset();
			}
		}]
	});

	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termTypeInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termTpUpd',mapping: 'termTp'},
			{name: 'descrUpd',mapping: 'descr'},
			{name: 'termTypeUpd',mapping: 'termType'},
			{name: 'termNumUpd',mapping: 'termNum'},
			{name: 'proDescrUpd',mapping: 'proDescr'}
		])
	});
	var termMode = new Ext.grid.ColumnModel([
		{id: 'termTp',header: '类型编号',dataIndex: 'termTpUpd',width: 100,align:'center'},
        {header: '终端类型',dataIndex: 'descrUpd',width: 200,align:'center'},
        {header: '终端型号',dataIndex: 'termTypeUpd',width: 150,align:'center'},
		{header: '终端版号',dataIndex: 'termNumUpd',width: 100,align:'center'},
		{header: '产品描述',dataIndex: 'proDescrUpd',width: 300,align:'center'}
	]);
	var addMenu = {
		text: '新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			termWin.show();
			termWin.center();
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
			showConfirm('确定要删除该终端类型参数信息吗？',grid,function(bt) {
				if(bt == 'yes') {
		//			showProcessMsg('正在提交，请稍后......');
					var rec = grid.getSelectionModel().getSelected();
					Ext.Ajax.requestNeedAuthorise({
						url: 'T10212Action.asp?method=delete',
						method: 'post',
						params: {
							termTpUpd : rec.get('termTpUpd'),
							descrUpd : rec.get('descrUpd'),
							txnId: '10212',
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
		title: '终端类型参数维护',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termMode,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载终端类型参数信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	

	//每次在列表信息加载前都将保存按钮屏蔽
	termStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
			termTp: Ext.getCmp('qryTermTp').getValue(),
		    descr: Ext.getCmp('qryDescr').getValue(),
		    termNum: Ext.getCmp('qryTermNum').getValue(),
			termType: Ext.getCmp('qryTermType').getValue()
		});
	});
	termStore.load();
	
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
		width: 330,
		waitMsgTarget: true,
		layout: 'form',
		defaults:{
			labelSeparator :": ",
			labelWidth : 100,
			allowBlank: false,
			msgTarget : 'side',
			labelAlign:'center'
		},
		items: [{
        	xtype: 'textfield',
			fieldLabel: '类型编号 ',
			name: 'termTpUpd',
			id: 'termTpUpd',
			width:200,
			allowBlank: false,
			readOnly: true,
			disabled:true,
			maxLength:10
		},{
			xtype: 'textfield',
			fieldLabel: '终端型号*',
			maxLength:20,
			name: 'termTypeUpd',
			width: 200,
			id: 'termTypeUpd',
			allowBlank: false,
			blankText: "终端型号不能为空！"
		},{
			xtype: 'textfield',
			fieldLabel: '终端类型*',
			maxLength:40,
			name: 'descrUpd',
			width: 200,
			id: 'descrUpd',
			allowBlank: false,
			hidden:true,
			hideLabel:true
		},{
			xtype: 'textfield',
			fieldLabel: '终端版号*',
			maxLength:20,
			name: 'termNumUpd',
			width: 200,
			id: 'termNumUpd',
			allowBlank: false,
			blankText: "终端版号不能为空！"
		},{
			xtype: 'textfield',
			fieldLabel: '产品描述',
			maxLength:100,
			name: 'proDescrUpd',
			width: 200,
			id: 'proDescrUpd',
			allowBlank: true
		}]
	});
	
	
	
	//添加表单
	var termForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		waitMsgTarget: true,
		defaults:{
			labelSeparator:": ",
			labelWidth: 80,
			width : 200,
			allowBlank: false,
			msgTarget : 'side',
			labelAlign:'center'
		},
		items: [{
			layout: 'form',
        	xtype: 'textfield',
			fieldLabel: '终端型号*',
			name: 'termType',
			id: 'termType',
			maxLength: 20,
			anchor: '95%',
			blankText: "终端型号不能为空"
		},{
			layout: 'form',
        	xtype: 'textfield',
			fieldLabel: '终端版号*',
			name: 'termNum',
			id: 'termNum',
			maxLength: 20,
			anchor: '95%',
			blankText: "终端版号不能为空"   
		},{
			layout: 'form',
        	xtype: 'textfield',
			fieldLabel: '产品描述',
			name: 'proDescr',
			id: 'proDescr',
			maxLength: 100,
			anchor: '95%' ,
			allowBlank: true
		}]
	});


	//信息窗口
	var termWin = new Ext.Window({
		title: '终端类型新增',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [termForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(termForm.getForm().isValid()) {
					termForm.getForm().submit({
						url:'T10212Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						params:{
							txnId:'10212',
							subTxnId:'01'
						},
						success:function(form,action) {
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							termForm.getForm().reset();
							showSuccessMsg(action.result.msg,grid);
							termWin.hide();
						},
						failure:function(form,action) {
							showErrorMsg(action.result.msg,grid);

						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				termForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				termForm.getForm().reset();
				termWin.hide();
			}
		}]
	});
	//终端信息窗口
	var updateWin = new Ext.Window({
		title: '终端类型修改',
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
					_termTpUpd = Ext.getCmp('termTpUpd').getValue();
					updateForm.getForm().submit({
						url: 'T10212Action.asp?method=update',
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
							txnId: '10212',
							subTxnId: '03',
							termTpUpd : _termTpUpd
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