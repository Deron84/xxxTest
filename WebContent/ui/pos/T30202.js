Ext.onReady(function() {
    var brhStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
        brhStore.loadData(Ext.decode(ret));
    });
    var thirdBrhStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('ORGAN',function(ret){
    	thirdBrhStore.loadData(Ext.decode(ret));
    });
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 100,
        items: [new Ext.form.TextField({
                id: 'termId',
                name: 'termId',
                fieldLabel: '终端号'
            }),{
            xtype: 'combo',
            fieldLabel: '终端所属分行',
            id: 'termBranchQ',
            hiddenName: 'termBranch',
            store: brhStore,
            displayField: 'displayField',
            valueField: 'valueField',
            width: 300
        },{
            xtype: 'combo',
            fieldLabel: '第三方服务机构',
            id: 'propInsNmQ',
            hiddenName: 'propInsNm',
            store: thirdBrhStore,
            displayField: 'displayField',
            valueField: 'valueField'
        }],
        buttons: [{
            text: '查询',
            handler: function(){
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

    // 列表当前选择记录
    var rec;
    
    var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=termTmkInfo01'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'termId',mapping: 'termId'},
            {name: 'mchntNo',mapping: 'mchntNo'},
            {name: 'termSta',mapping: 'termSta'},
            {name: 'termBranch',mapping: 'termBranch'},
            {name: 'propInsNm',mapping: 'propInsNm'}
        ])
    });
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: topQueryPanel.getForm().findField('termId').getValue(),
            propInsNm: topQueryPanel.getForm().findField('propInsNmQ').getValue(),
            termBranch: topQueryPanel.getForm().findField('termBranchQ').getValue()
        });
    });
    termStore.load();

    
    
    var termColModel = new Ext.grid.ColumnModel([
        {id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
        {header: '商户号',dataIndex: 'mchntNo',width: 500,renderer:function(val){return getRemoteTrans(val, "mchntName");}},
        {header: '终端状态',dataIndex: 'termSta',renderer: termSta},
        {header: '终端所属机构',dataIndex: 'termBranch',width:200},
        {header: '第三方服务机构',dataIndex: 'propInsNm',width:200}
    ]);
    
    
    var reqMenu = {
        text: '单笔申请',
        width: 85,
        iconCls: 'accept',
        disabled: true,
        handler:function() {
            showConfirm('确认申请吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在提交信息，请稍后......');
                    rec = grid.getSelectionModel().getSelected();
                    Ext.Ajax.request({
                        url: 'T30202Action.asp',
                        params: {
                            termId: rec.get('termId'),
                            mchntNo: rec.get('mchntNo'),
                            termBranch:rec.get('termBranch'),
                            txnId: '30202',
                            subTxnId: '01'
                        },
                        success: function(rsp,action) {
                            var rspObj = Ext.decode(rsp.responseText);
                            if(rspObj.success) {
                                showSuccessDtl(rspObj.msg,grid);
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                            grid.getStore().reload();
                        }
                    });
                    termStore.load();
                    grid.getTopToolbar().items.items[0].disable();
                    hideProcessMsg();
                }
            });
        }
    }
    
    var queryMenu = {
        text: '查询',
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
    
    
    //终端型号
    var terminalTypeStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
 
	var batchMenu = {
        text: '批量申请',
        width: 85,
        id: 'batch',
        iconCls: 'accept',
        handler:function() {
            showConfirm('确认批量处理吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在提交信息，请稍后......');
                    rec = grid.getSelectionModel().getSelected();
                    Ext.Ajax.requestNeedAuthorise({
                        url: 'T30202Action.asp',
                        params: {
                            termNo: Ext.getCmp('termId').getValue(),
                            termBranch: topQueryPanel.getForm().findField('termBranchQ').getValue(), 
                            propInsNm:topQueryPanel.getForm().findField('propInsNmQ').getValue(),
                            txnId: '30202',
                            subTxnId: '02'
                        },
                        success: function(rsp,action) {
                            var rspObj = Ext.decode(rsp.responseText);
                            if(rspObj.success) {
                                showSuccessDtl(rspObj.msg,grid);
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                            grid.getStore().reload();
                        }
                    });
                    termStore.load();
                    grid.getTopToolbar().items.items[0].disable();
                    hideProcessMsg();
                }
            });
		}
    };
    
    var menuArr = new Array();
    
    menuArr.push(reqMenu);     
    menuArr.push(batchMenu);
    menuArr.push(queryMenu);
    
    // 终端信息列表
    var grid = new Ext.grid.GridPanel({
        title: '终端密钥下发申请',
        iconCls: 'T30202',
        region:'center',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: termStore,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        cm: termColModel,
        clicksToEdit: true,
        forceValidation: true,
        tbar: menuArr,
        renderTo: Ext.getBody(),
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

    var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
})
