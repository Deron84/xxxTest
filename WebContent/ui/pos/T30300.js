Ext.onReady(function() {
	var termStateStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('TERM_STATE',function(ret){
        termStateStore.loadData(Ext.decode(ret));
    });
	var topQueryPanel = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 100,
		items: [{
				xtype: 'basecomboselect',
				baseParams: 'BRH_BELOW1',
				fieldLabel: '归属分行*',
				id:'brhIdId',
				hiddenName: 'brhId',
				width: 300
 	       	},new Ext.form.TextField({
				id: 'batchNo',
				name: 'batchNo',
				fieldLabel: '批次号',
				width: 300
			}),new Ext.form.TextField({
				id: 'termNo',
				name: 'termNo',
				fieldLabel: '终端库存编号',
				width: 300
			}),{											
				xtype: 'combo',
				fieldLabel: '库存状态',
				id: 'state',
				name: 'state',
				store: termStateStore,
				width: 300
		  },{
				width: 150,
				xtype: 'datefield',
				fieldLabel: '起始时间',
				format : 'Y-m-d',
				name :'startTime',
				id :'startTime',
				anchor :'60%'		
		  },{											
				width: 150,
				xtype: 'datefield',
				fieldLabel: '截止时间',
				format : 'Y-m-d',
				name :'endTime',
				id :'endTime',
				anchor :'60%'		
		}],
		buttons: [{
			text: '查询',
			handler: function() 
			{
				gridStore.load();
                queryWin.hide();
			}
		},{
			text: '重填',
			handler: function() {
				topQueryPanel.getForm().reset();
			}
		}]
	});

    
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termIdUpd',mapping: 'termId'},
			{name: 'stateUpd',mapping: 'state'},
			{name: 'manufacturerUpd',mapping: 'manufacturer'},
			{name: 'productCdUpd',mapping: 'productCd'},
			{name: 'terminalTypeUpd',mapping: 'terminalType'},
			{name: 'termTypeUpd',mapping: 'termType'},
			{name: 'mchnNoUpd',mapping: 'mchnNo'},
			{name: 'batchNoUpd',mapping: 'batchNo'},
			{name: 'storOprIdUpd',mapping: 'storOprId'},
			{name: 'storDateUpd',mapping: 'storDate'},
			{name: 'reciOprIdUpd',mapping: 'reciOprId'},
			{name: 'reciDateUpd',mapping: 'reciDate'},
			{name: 'bankOprIdUpd',mapping: 'bankOprId'},
			{name: 'bankDateUpd',mapping: 'bankDate'},
			{name: 'invalidOprIdUpd',mapping: 'invalidOprId'},
			{name: 'invalidDateUpd',mapping: 'invalidDate'},
			{name: 'signOprIdUpd',mapping: 'signOprId'},
			{name: 'signDateUpd',mapping: 'signDate'},
            {name: 'miscUpd',mapping: 'misc'},
            {name: 'brhId',mapping: 'brhId'}
		]),
		autoLoad: true
	});
		
	gridStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
		    brhId: Ext.getCmp('brhIdId').getValue(),
		    termNo: Ext.getCmp('termNo').getValue(),
		    batchNo: Ext.getCmp('batchNo').getValue(),
		    state: Ext.getCmp('state').getValue(),
		    startTime: Ext.getCmp('startTime').getValue(),
		    endTime: Ext.getCmp('endTime').getValue()
		});
	}); 
	var gridColumnModel = new Ext.grid.ColumnModel([
	  {header: '所属分行',dataIndex: 'brhId',width: 150,renderer:function(val){return getRemoteTrans(val, "brh")}},
	  {header: '批次号',dataIndex: 'batchNoUpd'},
	  {id: 'termId',header: '终端库存编号',dataIndex: 'termIdUpd',width: 100},
	  {header: '库存状态',dataIndex: 'stateUpd',renderer: translateState},
	  {header: '终端厂商',dataIndex: 'manufacturerUpd',width: 150,renderer:function(val){return getRemoteTrans(val, "MANUFATURER")}},
	  {header: '终端序列号',dataIndex: 'productCdUpd'},
	  {header: '终端型号',dataIndex: 'terminalTypeUpd',renderer:function(val){return getRemoteTrans(val, "sysPara")}},
	  {header: '终端类型',dataIndex: 'termTypeUpd',renderer: termType},
	  {header: '所属商户号',dataIndex: 'mchnNoUpd',width: 150,renderer:function(val){return getRemoteTrans(val, "mchntName")}},
      {header: '备注',dataIndex: 'miscUpd'}
	]);
	var addMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'edit',
		handler: function() {
			selectedRecord = gridPanel.getSelectionModel().getSelected();
			modifyBrhForm.getForm().loadRecord(selectedRecord);
			modifyBrhWin.show();
		}
	}
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
	var menuArr = new Array();
	menuArr.push(addMenu);		
    menuArr.push(queryMenu);
    
    
	//应用信息
	var gridPanel = new Ext.grid.EditorGridPanel({
		title: '终端库存查询',
		store: gridStore,
		region:'center',
		iconCls: 'T30301',
		frame: true,
		border: true,
		columnLines: true,
		tbar: menuArr,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    var brhStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('BRH_ID',function(ret){
        brhStore.loadData(Ext.decode(ret));
    });
	// 详细信息
	var modifyBrhForm = new Ext.FormPanel({
		frame: true,
		width: 450,
		autoHeight: true,
		waitMsgTarget: true,
		labelWidth: 120,
		defaults: {
			bodyStyle: 'padding-left: 20px'
		},
		items: [{					
			columnWidth: .4,
			layout: 'form',
			items:[{
				xtype: 'displayfield',
				id: 'termIdUpd',
				name: 'termIdUpd',
				width: 100,
				fieldLabel: '终端库存编号'
			}]
		},{
            columnWidth: .4,
            layout: 'form',
            items:[{
            	xtype: 'displayfield',
                fieldLabel: '批次号',
                id: 'batchNoUpd',
                name: 'batchNoUpd'
            }]
        },{
            columnWidth: .4,
            layout: 'form',
            items:[{
            	xtype: 'displayfield',
                fieldLabel: '终端序列号',
                id: 'productCdUpd',
                name: 'productCdUpd'
            }]
        },{
            columnWidth: .4,
            layout: 'form',
            items: [{
            	xtype: 'displayfield',
                fieldLabel: '所属商户号',
                id: 'mchnNoUpd',
                name: 'mchnNoUpd'
            }]
        },{
            layout: 'form',
            columnWidth: .4,
            items: [{
                xtype: 'combofordispaly',
                fieldLabel: '终端类型',
                id: 'termTypeUpd',
                readOnly: true,
                store: new Ext.data.ArrayStore({
                      fields: ['valueField','displayField'],
                      data: [['P','POS'],['E','EPOS']]
                })
            }]
        },{
            layout: 'form',
            columnWidth: .4,
            items: [{
                xtype: 'displayfield',
                fieldLabel: '终端厂商',
                name: 'manufacturerUpd',
                id: 'manufacturerUpd'
            }]
        },{
            layout: 'form',
            columnWidth: .4,
            items: [{
                xtype: 'displayfield',
                fieldLabel: '终端型号',
                id: 'terminalTypeUpd',
                name: 'terminalTypeUpd'
            }]
        },{
            layout: 'form',
            columnWidth: .4,
            items: [{
                xtype: 'displayfield',
                fieldLabel: '入库操作员',
                id: 'storOprIdUpd',
                name: 'storOprIdUpd'
            }]
        },{
            layout: 'form',
            columnWidth: .4,
            items: [{
                xtype: 'displayfield',
                fieldLabel: '入库日期',
                format: 'Y-m-d',
                id: 'storDateUpd',
                name: 'storDateUpd'
            }]
        }],
		buttonAlign: 'center',
		buttons: [{
			text: '关闭',
			handler: function() {
				modifyBrhWin.hide();
			}
		}]
	});
	// 详细信息 窗口
	var modifyBrhWin = new Ext.Window({
		title: '详细信息',
		animateTarget: 'modifyBt',
		layout: 'fit',
		width: 450,
		closeAction: 'hide',
		resizable: false,
		closable: true,
		modal: true,
		autoHeight: true,
		items: [modifyBrhForm]
	});
	gridPanel.getTopToolbar().items.items[0].disable();
	gridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel.getView().getRow(gridPanel.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = gridPanel.getSelectionModel().getSelected();
			if(rec != ''  ) {
				gridPanel.getTopToolbar().items.items[0].enable();
			} else {
				gridPanel.getTopToolbar().items.items[0].disable();
			}
		}
	});
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[gridPanel]
	});
	
})