Ext.onReady(function() {
	var _termTpUpd;
	

	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termKeyInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'ylMchntNo',mapping: 'YL_MCHNT_NO'},
			{name: 'ylTermNo',mapping: 'YL_TERM_NO'},
			{name: 'cKey',mapping: 'C_KEY'}
		])
	});
	var termMode = new Ext.grid.ColumnModel([
        {header: '银联商户号',dataIndex: 'ylMchntNo',width: 150,align:'center'},
        {header: '银联终端号',dataIndex: 'ylTermNo',width: 100,align:'center'},
		{header: '主密钥',dataIndex: 'cKey',width: 300,align:'center'}
	]);
	
   
	
	
	
	
	//添加表单
	var addKeyForm = new Ext.form.FormPanel({
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
			fieldLabel: '银联商户号*',
			id: 'addYlMchntNo',
			maxLength: 15,
			anchor: '95%',
			blankText: "银联商户号不能为空" 
		},{
			layout: 'form',
        	xtype: 'textfield',
			fieldLabel: '银联终端号*',
			id: 'addYlTermNo',
			regex:/^[a-zA-Z0-9]{8}$/,
			anchor: '95%',
			regexText:'只能输入8位数字或字母'   
		}]
	});
	var queryKeyForm = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 400,
        autoHeight: true,
        labelWidth: 80,
		items: [{
			xtype: 'textfield',
			width: 250,
			fieldLabel: '银联商户号',
			labelStyle: 'padding-left: 5px',
			emptyText: '请输入银联商户号',
			id: 'qryYlMchntNo',
			maxLength:15
		},{
			xtype: 'textfield',
			width: 250,
			fieldLabel: '银联终端号',
			labelStyle: 'padding-left: 5px',
			emptyText: '请输入银联终端号',
			id: 'qryYlTermNo',
			maxLength:8
		}]
	});

	//密钥绑定
	var keyWin = new Ext.Window({
		title: '国密终端密钥绑定',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [addKeyForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(addKeyForm.getForm().isValid()) {
					addKeyForm.getForm().submit({
						url:'T10213Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						params:{
							txnId:'10213',
							subTxnId:'01'
						},
						success:function(form,action) {
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							addKeyForm.getForm().reset();
							showSuccessMsg(action.result.msg,grid);
							keyWin.hide();
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
				addKeyForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addKeyForm.getForm().reset();
				keyWin.hide();
			}
		}]
	});
	
	 var queryWin = new Ext.Window({
	        title: '查询条件',
	        layout: 'fit',
	        width: 400,
	        autoHeight: true,
	        items: [queryKeyForm],
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
				handler: function() 
				{
					termStore.load();
	                queryWin.hide();
	                queryKeyForm.getForm().reset();
				}
			},{
				text: '清除查询条件',
				handler: function() {
					queryKeyForm.getForm().reset();
				}
			}]
	    });
	
	var addMenu = {
			text: '添加绑定',
			width: 85,
			iconCls: 'add',
			handler:function() {
				keyWin.show();
				keyWin.center();
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
	
	var menuArr = new Array();
	
	menuArr.push(addMenu);		//[0]
	menuArr.push('-');
	menuArr.push(queryMenu);    //[1]
	
	// 国密终端密钥参数列表
	var grid = new Ext.grid.GridPanel({
		title: '国密终端密钥参数维护',
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
			msg: '正在加载国密终端密钥参数信息列表......'
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
			YL_MCHNT_NO: Ext.getCmp('qryYlMchntNo').getValue(),
		    YL_TERM_NO: Ext.getCmp('qryYlTermNo').getValue()
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
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
})