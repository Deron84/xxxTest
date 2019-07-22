Ext.onReady(function(){
	//EPOS交易版本号
    var eposStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboDataWithParameter('EPOS_VER','',function(ret){
		eposStore.loadData(Ext.decode(ret));
	});
    //交易代码
    var txnStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('TXN_CODE',function(ret){
    	txnStore.loadData(Ext.decode(ret));
	});
    //EPOS交易提示信息
    var pptStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboDataWithParameter('PPT_MSG','',function(ret){
		pptStore.loadData(Ext.decode(ret));
	});
	var mainForm = new Ext.form.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'center',
		defaults: {
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			anchor: '90%'
		},
		items: [{
			xtype: 'basecomboselect',
        	baseParams: 'CUP_BRH_BELOW',
			fieldLabel: '机构编号*',
			allowBlank: false,
			blankText: '请选择机构编号',
			hiddenName: 'brhIdNew',
			id: 'brhIdN',
			listeners:{
				'select': function() {
					SelectOptionsDWR.getComboDataWithParameter('EPOS_VER',Ext.getCmp('brhIdN').value,function(ret){
						eposStore.loadData(Ext.decode(ret));
					});
				}
			}
		},{
			xtype: 'combo',
			fieldLabel: '版本号*',
			allowBlank: false,
			store: eposStore,
			hiddenName: 'verIdNew',
			id: 'verIdN'
//			listeners:{
//				'select': function() {
//					SelectOptionsDWR.getComboDataWithParameter('PPT_MSG',Ext.getCmp('verIdN').value,function(ret){
//						pptStore.loadData(Ext.decode(ret));
//					});
//				}
//			}
		},{
			xtype: 'combo',
			fieldLabel: '菜单级别*',
			hiddenName: 'menuLevelNew',
			id: 'menuLvlNew',
			allowBlank: false,
			store: new Ext.data.ArrayStore({
                 fields: ['valueField','displayField'],
                 data: [['1','1级菜单'],['2','2级菜单'],['3','3级菜单']]
            })
		},{
			fieldLabel: '上级一级菜单',
			name: 'menuPreId1New',
			allowBlank: false,
			id: 'menuPreId1New'
		},{
			fieldLabel: '上级二级菜单',
			name: 'menuPreId2New',
			allowBlank: false,
			id: 'menuPreId2New'
		},{
			fieldLabel: '菜单显示内容*',
			name: 'menuMsgNew',
			id: 'menuMsgNew',
			maxLength: 64
		},{
			xtype: 'combo',
			fieldLabel: '交易代码',
			hiddenName: 'txnCodeNew',
			id: 'txnCodeN',
			store: txnStore
		},{
			xtype: 'checkbox',
			fieldLabel: '末笔交易查询标志',
			name: 'conFlagNew',
			id: 'conFlagNew',
			allowBlank: true,
			inputValue: 1
		},{
			xtype: 'combo',
			store: pptStore,
			fieldLabel: '交易提示信息',
			hiddenName: 'pptIdNew',
			id: 'pptIdN'
		}],
		buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = mainForm.getForm();
				if (frm.isValid()) {
						frm.submitNeedAuthorise({
							url: 'T30505Action_add.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								showSuccessAlert(action.result.msg,mainForm);
								mainWin.hide(mainGrid);
								mainStore.reload();
								frm.resetAll();
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,mainForm);
							},
							params: {
								txnId: '30505',
								subTxnId: '01'
							}
					});
			}}
		},{
            text: '关闭',
            handler: function() {
				mainWin.hide(mainGrid);
				mainForm.getForm().resetAll();
			}
        }]
	});
	
	var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        items: [{
				xtype: 'basecomboselect',
	        	baseParams: 'CUP_BRH_BELOW',
				fieldLabel: '机构编号',
				allowBlank: false,
				blankText: '请选择机构编号',
				hiddenName: 'brhIdQry',
				id: 'brhIdQ'
			},{
				xtype: 'combo',
				fieldLabel: '版本号',
				allowBlank: false,
				store: eposStore,
				hiddenName: 'verIdQry',
				id: 'verIdQ'
			}],
        buttons: [{
            text: '查询',
            handler: function() 
            {
            	mainStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
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
        width: 500,
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
	var mainWin = new Ext.Window({
		title: '新增菜单信息',
		iconCls: 'T30505',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		items: [mainForm]
	});
	var updateForm = new Ext.form.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'center',
		defaults: {
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			anchor: '90%'
		},
		items: [{
			xtype: 'textfield',
			fieldLabel: '所属机构*',
			name: 'brhId',
			id: 'brhId',
			readOnly: true
		},{
			xtype: 'textfield',
			fieldLabel: '菜单*',
			name: 'menuId',
			id: 'menuId',
			readOnly: true
		},{
			xtype: 'textfield',
			fieldLabel: '版本号*',
			name: 'verId',
			id: 'verId',
			readOnly: true
		},{
			xtype: 'combo',
			fieldLabel: '菜单级别*',
			hiddenName: 'menuLevel',
			id: 'menuLv',
			store: new Ext.data.ArrayStore({
                fields: ['valueField','displayField'],
                data: [['1','1级菜单'],['2','2级菜单'],['3','3级菜单']]
           }),
			readOnly: true
		},{
			xtype: 'textfield',
			fieldLabel: '当前的一级菜单*',
			name: 'menuPreId1',
			id: 'menuPreId1',
			readOnly: true
		},{
			xtype: 'textfield',
			fieldLabel: '当前的二级菜单*',
			name: 'menuPreId2',
			id: 'menuPreId2',
			readOnly: true
		},{
			fieldLabel: '菜单显示内容*',
			name: 'menuMsg',
			id: 'menuMsg',
			maxLength: 64
		},{
			xtype: 'combo',
			fieldLabel: '交易代码',
			hiddenName: 'txnCode',
			id: 'txnCodeU',
			store: txnStore
		},{
			xtype: 'checkbox',
			fieldLabel: '末笔交易查询标志',
			name: 'conFlag',
			id: 'conFlag',
			inputValue: 1
		},{
			xtype: 'checkbox',
			fieldLabel: '启用标志',
			name: 'oprId',
			id: 'oprIdU',
			inputValue: 1
		},{
			xtype: 'combo',
			store: pptStore,
			fieldLabel: '交易提示信息',
			hiddenName: 'pptId',
			id: 'pptIdU'
		}],
		buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = updateForm.getForm();
				if (frm.isValid()) {
						frm.submitNeedAuthorise({
							url: 'T30505Action_update.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								showSuccessMsg(action.result.msg,updateForm);
								updateWin.hide(mainGrid);
								mainStore.load();
								frm.resetAll();
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,updateForm);
							},
							params: {
								txnId: '30505',
								subTxnId: '02'
							}
					});
			}}
		},{
            text: '关闭',
            handler: function() {
				updateWin.hide(mainGrid);
				updateForm.getForm().resetAll();
			}
        }]
	});
	
	var updateWin = new Ext.Window({
		title: '菜单配置信息',
		iconCls: 'T30505',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		items: [updateForm]
	});
	//functions
	function add(){
		mainWin.show(mainGrid);
	};
	function edit(){
        var selectedRecord = mainGrid.getSelectionModel().getSelected();
        if(selectedRecord == null)
        {
        	showAlertMsg("没有选择记录",mainGrid);
            return;
        }    
        updateForm.getForm().loadRecord(selectedRecord);
		updateWin.show();
		updateWin.center();
	}
	
	//数据集
	var mainStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=menuMsg'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'usageKey',mapping:  'USAGE_KEY'},   
			{name: 'brhId',mapping:'BRH_ID'},
			{name: 'menuId',mapping: 'MENU_ID'},
			{name: 'menuLv',mapping: 'MENU_LEVEL'},
			{name: 'menuMsg',mapping: 'MENU_MSG'},
			{name: 'menuPreId1',mapping: 'MENU_PRE_ID1'},
	      	{name: 'menuPreId2',mapping: 'MENU_PRE_ID2'},
			{name: 'txnCode',mapping: 'TXN_CODE'},
			{name: 'conFlag',mapping: 'CON_FLAG'},
			{name: 'pptId',mapping: 'PPT_ID'},
			{name: 'oprIdU',mapping: 'OPR_ID'},
			{name: 'verId',mapping: 'VER_ID'},
			{name: 'crtDate',mapping: 'CRT_DATE'},
			{name: 'updDate',mapping: 'UPD_DATE'}
		]),
		autoLoad: true
	});
	
	mainStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            brhId: Ext.getCmp('brhIdQ').getValue(),
            verId: Ext.getCmp('verIdQ').getValue()
        });
    }); 
	//列模型
	var mainModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '机构编号',dataIndex: 'brhId',width:80},
			{header: '版本号',dataIndex: 'verId',width:60},
			{header: '菜单',dataIndex: 'menuId',width:60},
			{header: '菜单显示内容',dataIndex: 'menuMsg',width:140,id:'menuMsg'},
    		{header: '菜单级别',dataIndex: 'menuLv',renderer: menuLevel,width:80},
    		{header: '当前菜单的一级菜单',dataIndex: 'menuPreId1',width:140},
    		{header: '当前菜单的二级菜单',dataIndex: 'menuPreId2',width:140},
    		{header: '交易代码',dataIndex: 'txnCode',width:80},
    		{header: '末笔交易查询标志',dataIndex: 'conFlag',renderer: conFlag,width:140},
    		{header: '菜单操作标志',dataIndex: 'oprIdU',renderer: opreator},
    		{header: '交易提示信息',dataIndex: 'pptId'}
    ]);
	
	//GRID
	var mainGrid = new Ext.grid.EditorGridPanel({
		title: '终端菜单配置',
		iconCls: 'T30505',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
		autoExpandColumn: 'menuMsg',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		tbar: [{
			xtype: 'button',
			text: '新增',
			name: 'add',
			id: 'add',
			iconCls: 'add',
			width: 75,
			handler:function() {
				add();
			}
		},'-',{
			xtype: 'button',
			text: '修改',
			name: 'edit',
			id: 'edit',
			iconCls: 'edit',
			width: 75,
			disabled: true,
			handler:function() {
				edit();
			}
		},'-',queryMenu],
		loadMask: {
			msg: '正在加载信息列表，请稍候......'
		},
		bbar: new Ext.PagingToolbar({
			store: mainStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	mainGrid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(mainGrid.getView().getRow(mainGrid.getSelectionModel().last)).frame();
			var rec = mainGrid.getSelectionModel().getSelected();
			if(rec != null ){
				mainGrid.getTopToolbar().items.items[2].enable();
			}
			else{
				mainGrid.getTopToolbar().items.items[2].disable();		
			}
		}
	});
	
	//Main View
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mainGrid],
		renderTo: Ext.getBody()
	});
});