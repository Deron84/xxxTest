Ext.onReady(function() {
    var store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=batchDtl'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
            idProperty: 'brhCode'
        },[
            {name: 'dateSettlm',mapping: 'dateSettlm'},
            {name: 'brhCode',mapping: 'brhCode'},
            {name: 'settleAmt1',mapping: 'settleAmt1'},
            {name: 'settleAmt2',mapping: 'settleAmt2'},
            {name: 'settleAmt3',mapping: 'settleAmt3'},
            {name: 'brhName',mapping: 'brhName'},
            {name: 'settleAmt',mapping: 'settleAmt'},
            {name: 'sendNum',mapping: 'sendNum'},
            {name: 'flag',mapping: 'flag'},
            {name: 'failResn',mapping: 'failResn'},
            {name: 'settleFlag',mapping: 'settleFlag'}
        ])
    });
    store.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            date: settleForm.findById('date').getValue()
        });
    });
    
    function translate(val){
        if(val >=0)
            return "<font color='green' >"+val+"</font>";
        return "<font color='red' >"+val+"</font>";
    }

    
    var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<font color="blue">状态描述：</font>{failResn}</br>'
		)
	});
    
    var cm = new Ext.grid.ColumnModel([
        rowExpander,
        {id: 'brhCode',header: '机构号',dataIndex: 'brhCode',sortable: true,width: 60},
        {header: '机构名称',dataIndex: 'brhName'},
        {header: '新系统本金',dataIndex: 'settleAmt1'},
        {header: '新系统手续费',dataIndex: 'settleAmt2'},
        {header: '旧系统手续费',dataIndex: 'settleAmt3'},
        {header: '清算金额',dataIndex: 'settleAmt',renderer:translate},
        {header: '当前状态',dataIndex: 'flag',renderer:settleFlag,width:80}
        
    ]);
    
    var propertyStore = new Ext.data.JsonStore({
        autoLoad: true,  //自动加载数据
        url: 'gridPanelStoreAction.asp?storeId=batchBankInfo',
        root: 'data',
        fields: ['总行新系统本金', '总行新系统手续费', '总行旧系统手续费','清算金额总计'],
        listeners: {
            load: {
                fn: function(store, records, options){
                    var propGrid = Ext.getCmp('propGrid');
                        if (propGrid&&store.getAt(0) != null) {
                            propGrid.setSource(store.getAt(0).data);
                            propertyGrid.show();
                        }
                    }
                }
        }
    });

    var propertyGrid = new Ext.grid.PropertyGrid({
        id: 'propGrid',
        autoHeight: true,
        hideHeaders: true,
        hidden: true,
        width: 300,
        store: propertyStore
    });
    propertyStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            date: settleForm.findById('date').getValue()
        });
        propertyGrid.hide();
    });

    var settleForm = new Ext.form.FormPanel({
        frame: true,
        layout: 'column',
        items: [{
                columnWidth: .7,
                layout: 'form',
                width: 300,
                labelWidth : 120,
                items: [{
                    xtype: 'datefield',
                    name: 'date',
                    id: 'date',
                    format: 'Y/m/d',
                    fieldLabel: '清算日期',
                    allowBlank: false,
                    blankText: '请选择清算日期'
                }]
            },propertyGrid
            ],
        buttons: [{
            text: '显示对账信息',
            handler: function() {
                store.load();
                propertyStore.load();
            }
        },{
            text: '清空条件',
            handler: function() {
                settleForm.getForm().reset();
            }
        }]
    });

    
    // 主面板
    var secondMainPanel = new Ext.Panel({
        region: 'north',
        title: '银联资金清算划拨结果查询',
        iconCls: 'T80202',
        frame: true,
        borde: true,
        height: 180,
        renderTo: Ext.getBody(),
        items: [settleForm]
    });
    //机构列表
    var grid = new Ext.grid.GridPanel({
        region: 'center',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: store,
        cm: cm,
        plugins: rowExpander,
        loadMask: {
            msg: '正在加载机构信息列表......'
        },
        bbar: new Ext.PagingToolbar({
            store: store,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
    });
    
    var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid,secondMainPanel],
		renderTo: Ext.getBody()
	});
	settleForm.getForm().findField('date').setValue(YESTERDAY);

})