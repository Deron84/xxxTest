Ext.onReady(function() {
    var termInfo;
    var termManagementInfo;
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    var termStateStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('TERM_STATE',function(ret){
        termStateStore.loadData(Ext.decode(ret));
    });
	var termInfoStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=termInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'termId',mapping: 'termId'},
            {name: 'mchntNo',mapping: 'mchntNo'},
            {name: 'termSta',mapping: 'termSta'},
            {name: 'termSignSta',mapping: 'termSignSta'},
            {name: 'termIdId',mapping: 'termIdId'},
            {name: 'termFactory',mapping: 'termFactory'},
            {name: 'termMachTp',mapping: 'termMachTp'},
            {name: 'termVer',mapping: 'termVer'},
            {name: 'termTp',mapping: 'termTp'},
            {name: 'termBranch',mapping: 'termBranch'},
            {name: 'termIns',mapping: 'termIns'}
        ])
    });
    

    
    
    var termInfoColModel = new Ext.grid.ColumnModel([
        {id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
        {header: '商户号',dataIndex: 'mchntNo',width: 150,renderer:function(val){return getRemoteTrans(val, "mchntName")}},
        {header: '终端状态',dataIndex: 'termSta',width: 150,renderer: termSta},
        {header: '终端库存编号',dataIndex: 'termIdId'},
        {header: '终端厂商',dataIndex: 'termFactory',width: 150,renderer:function(val){return getRemoteTrans(val, "MANUFATURER")}},
        {header: '终端型号',dataIndex: 'termMachTp',renderer:function(val){return getRemoteTrans(val, "sysPara")}}
    ]);
	
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'state',mapping: 'state'},
			{name: 'manufacturer',mapping: 'manufacturer'},
			{name: 'productCd',mapping: 'productCd'},
			{name: 'terminalType',mapping: 'terminalType'},
			{name: 'termType',mapping: 'termType'},
			{name: 'mchnNo',mapping: 'mchnNo'},
			{name: 'batchNo',mapping: 'batchNo'},
			{name: 'storOprId',mapping: 'storOprId'},
			{name: 'storDate',mapping: 'storDate'},
			{name: 'reciOprId',mapping: 'reciOprId'},
			{name: 'reciDate',mapping: 'reciDate'},
			{name: 'bankOprId',mapping: 'bankOprId'},
			{name: 'bankDate',mapping: 'bankDate'},
			{name: 'invalidOprId',mapping: 'invalidOprId'},
			{name: 'invalidDate',mapping: 'invalidDate'},
			{name: 'signOprId',mapping: 'signOprId'},
			{name: 'signDate',mapping: 'signDate'},
            {name: 'misc',mapping: 'misc'},
            {name: 'pinPad',mapping: 'pinPad'}
		])
	});
	
	
	var termRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>入库操作员：{storOprId}</p>',
			'<p>入库时间：{storDate}</p>'
		)
	});
	
	var termColModel = new Ext.grid.ColumnModel([
	    termRowExpander,
	    {id: 'termId',header: '终端库存编号',dataIndex: 'termId',width: 100},
        {header: '批次号',dataIndex: 'batchNo'},
	    {header: '库存状态',dataIndex: 'state',renderer: translateState},
	    {header: '终端厂商',dataIndex: 'manufacturer',width: 150,renderer:function(val){return getRemoteTrans(val, "MANUFATURER")}},
	    {header: '终端序列号',dataIndex: 'productCd'},
	    {header: '终端型号',dataIndex: 'terminalType',renderer:function(val){return getRemoteTrans(val, "sysPara")}},
	    {header: '终端类型',dataIndex: 'termType',renderer: termType},
	    {header: '所属商户号',dataIndex: 'mchnNo',width: 150,renderer:function(val){return getRemoteTrans(val, "mchntName")}},
	    {header: '密码键盘序号',dataIndex: 'pinPad',width: 150}
	]);
	
	var acceptMenu = {
        id: 'acceptButton',
		text: '终端绑定',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			termInfo = grid2.getSelectionModel().getSelected();
            termManagementInfo = grid.getSelectionModel().getSelected();
            if( termInfo == null )
            {
            	showErrorMsg('请选择终端信息',mainView);
            }
            if( termManagementInfo == null )
            {
            	showErrorMsg('请选择终端库存信息',mainView);
            }
            if( termManagementInfo!=null&&termInfo!=null )
            {
                modifyWin.show();
            }
		}
	};
	var modifyForm = new Ext.FormPanel({
		frame: true,
		width: 450,
		height: 180,
		//layout: 'column',
		waitMsgTarget: true,
		labelWidth: 80,
		defaults: {
			bodyStyle: 'padding-left: 50px'
		},
		items: [{					
			columnWidth: .4,
			layout: 'form',
			items:[{
				id: 'pinPad',
				name: 'pinPad',
				xtype: 'textfield',
                allowblank: false,
				width: 100,
				fieldLabel: '密码键盘序号'
			}]
           },{                  
            columnWidth: .4,
            layout: 'form',
            items:[{
                id: 'confirm',
                name: 'confirm',
                xtype: 'textfield',
                allowblank: false,
                width: 100,
                fieldLabel: '确认密码键盘序号'
            }]
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(modifyForm.getForm().isValid()) {
					if(Ext.getCmp('pinPad').getValue() != Ext.getCmp('confirm').getValue())
                    {
						showErrorMsg('两次输入的密码键盘不相同',mainView);
                    }
                    else
                    {
	                    modifyForm.getForm().submit({
	                        url: 'T30305Action.asp',
	                        waitMsg: '正在提交，请稍后......',
	                        success: function(form,action) {
	                            modifyForm.getForm().reset();
	                            showSuccessMsg(action.result.msg,mainView);
	                            modifyWin.hide();
                                termStore.load();
                                termInfoStore.load();
	                        },
	                        failure: function(form,action) {
	                            showErrorMsg(action.result.msg,mainView);
	                        },
	                        params: {
	                            txnId: '30305',
	                            subTxnId: '01',
                                termId: termInfo.get('termId'),
                                mchtCd:termInfo.get('mchntNo'),
                                termIdId: termManagementInfo.get('termId')
	                        }
	                    });
                    }
				}
				modifyWin.hide();
				
			}
		},{
			text: '关闭',
			handler: function() {
				modifyWin.hide();
				grid.getStore().reload();
			}
		}]
	});
	var modifyWin = new Ext.Window({
		title: '终端信息',
		layout: 'fit',
		width: 400,
		closeAction: 'hide',
		resizable: false,
		closable: true,
		modal: true,
		autoHeight: true,
		items: [modifyForm]
	});
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '终端库存信息',
		region: 'center',
		iconCls: 'pos',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		forceValidation: true,
		plugins: [termRowExpander],
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    
    var grid2 = new Ext.grid.GridPanel({
        title: '终端信息',
        region: 'east',
		width: 560,
        iconCls: 'pos',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: termInfoStore,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        cm: termInfoColModel,
        forceValidation: true,
        loadMask: {
            msg: '正在加载终端信息列表......'
        },
        bbar: new Ext.PagingToolbar({
            store: termInfoStore,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
    });
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			var termInfo = grid2.getSelectionModel().getSelected();
            var termManagementInfo = grid.getSelectionModel().getSelected();
			if(termInfo != null&&termManagementInfo != null) {
				Ext.getCmp('acceptButton').enable();
			} else {
				Ext.getCmp('acceptButton').disable();
			}
		}
	});
    grid2.getSelectionModel().on({
        'rowselect': function() {
            var termInfo = grid2.getSelectionModel().getSelected();
            var termManagementInfo = grid.getSelectionModel().getSelected();
            if(termInfo != null&&termManagementInfo != null) {
                Ext.getCmp('acceptButton').enable();
            } else {
                Ext.getCmp('acceptButton').disable();
            }
        }
    });
    
    var queryPanel = new Ext.form.FormPanel({
        region: 'north',
        height: 160,
        frame: true,
        layout: 'column',
        items: [{                                           
            columnWidth: .5,
            layout: 'form',
            items: [new Ext.form.TextField({
                id: 'termNo',
                name: 'termNo',
                fieldLabel: '终端库存编号'
            }),{
            columnWidth: .5,
            layout: 'form',
            items: [{
                xtype: 'textfield',
                fieldLabel: '终端序列号',
                name: 'termIdId',
                id: 'termIdId',
                width: 200
           }]
        },{
            columnWidth: .5,
            layout: 'form',
            items: [{
                xtype: 'textfield',
                fieldLabel: '批次号',
                name: 'batchNo',
                id: 'batchNo',
                width: 200
           }]
        },{                                           
            columnWidth: .4,
            layout: 'form',
            items: [{
                xtype: 'basecomboselect',
                fieldLabel: '库存状态',
                id: 'stateQ',
                hiddenName: 'state',
                store: termStateStore
            }]
        }]
        },{
            columnWidth: .5,
            layout: 'form',
            items: [{                                         
            columnWidth: .5,
            layout: 'form',
            items: [new Ext.form.TextField({
                id: 'termNoQ',
                name: 'termNo',
                fieldLabel: '终端号'
            })]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items: [{
                    xtype: 'combo',
                    fieldLabel: '商户号',
                    store: mchntStore,
                    hiddenName: 'mchnNo',
                    id: 'mchnNoQ',
                    displayField: 'displayField',
                    valueField: 'valueField',
                    width: 450
                  }]
        },{
            columnWidth: .5,
            layout: 'form',
            items: [{
                width: 150,
                xtype: 'datefield',
                fieldLabel: '起始时间',
                format : 'Y-m-d',
                name :'startTime',
                id :'startTime',
                anchor :'60%'       
            } ]
        },{                                         
            columnWidth: .5,
            layout: 'form',
            items: [{
                width: 150,
                xtype: 'datefield',
                fieldLabel: '截止时间',
                format : 'Y-m-d',
                name :'endTime',
                id :'endTime',
                anchor :'60%'       
            }]
        }]
        }],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                termStore.load();
                termInfoStore.load();
            }
        },{
            text: '重填',
            handler: function() {
                queryPanel.getForm().reset();
            }
        },acceptMenu]
    });
    
    termInfoStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termNoQ').getValue(),
            startTime: queryPanel.getForm().findField('startTime').getValue(),
            endTime: queryPanel.getForm().findField('endTime').getValue(),
            mchnNo: Ext.getCmp('mchnNoQ').getValue()
        });
    }); 
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            state: Ext.getCmp('stateQ').getValue(),
            termNo: Ext.getCmp('termNo').getValue(),
            termIdId: Ext.getCmp('termIdId').getValue(),
            batchNo: Ext.getCmp('batchNo').getValue()
        });
    });
	var mainView = new Ext.Viewport({
        title: '终端出库',
		layout: 'border',
		items: [queryPanel,grid,grid2],
		renderTo: Ext.getBody()
	});
    
})