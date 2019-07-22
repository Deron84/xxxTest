Ext.onReady(function() {

    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 490,
        autoHeight: true,
        labelWidth: 80,
        items: [new Ext.form.TextField({
                id: 'actNo',
                name: 'actNo',
                width:380,
                fieldLabel: '活动编号'
            }),{
                xtype: 'textfield',
                fieldLabel: '活动名称',
                width:380,
                id: 'actNm',
                name: 'actNm'
            },{
				xtype : 'dynamicCombo',
				fieldLabel : '商户',
				methodName : 'getMchntIdInBase',
				hiddenName : 'mchtNo',
				editable : true,
				width : 380
			}],
	        buttons: [{
	            text: '查询',
	            handler: function() 
	            {
	                actStore.load();
//	                queryWin.hide();
	            }
	        },{
	            text: '重填',
	            handler: function() {
	                topQueryPanel.getForm().reset();
	            }
	        }]
	    });
    
	var actStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntParticipat'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'actNo',mapping: 'actNo'},
			{name: 'actName',mapping: 'actName'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'state',mapping: 'state'},
			{name: 'startDate',mapping: 'startDate'},
			{name: 'endDate',mapping: 'endDate'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'actContent',mapping: 'actContent'},
			{name: 'actFee',mapping: 'actFee'},
			{name: 'flag01',mapping: 'flag01'}
		])
	});
	
	actStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            actNo: Ext.getCmp('actNo').getValue(),
            actNm: Ext.getCmp('actNm').getValue(),
            mchtNo:topQueryPanel.getForm().findField('mchtNo').getValue()
        });
    }); 
	
	var sm = new Ext.grid.CheckboxSelectionModel();

	var actColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header: '活动编号',dataIndex: 'actNo'},
		{header: '活动名称',dataIndex: 'actName',width: 350},
		{id: 'mchntNo',header: '商户号',dataIndex: 'mchntNo',width: 120},
		{header: '商户名称',dataIndex: 'mchtNm',width: 400},
		{header: '所属机构',dataIndex: 'bankNo',width: 60},
		{header: '操作',dataIndex: 'flag01',renderer: mchntOprState,width: 50},
		{header: '活动状态',dataIndex: 'state',renderer: actState,width: 60},
		{header: '起始日期',dataIndex: 'startDate'},
		{header: '截止日期',dataIndex: 'endDate'}
//		,
//		{header: '费率(%)',dataIndex: 'actFee'}
	]);
	
	var deleteMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler:function() {
           var selects = grid.getSelectionModel().getSelections();
           var selectedOptions = new Array();
           var actNos=new Array();
           if(selects == null)
           {
           		showAlertMsg("请选择要删除记录",activityForm);
           		return;
           }
//		   var actNo = selects[0].get('actNo');
           for(var i = 0;i<selects.length;i++){
			   selectedOptions[i] = selects[i].get('mchntNo');
			   actNos[i]=selects[i].get('actNo');
		   }
		   showConfirm('确定要删除这些商户吗？',grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T70203Action_delete.asp',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									topQueryPanel.getForm().reset();
									grid.getTopToolbar().items.items[1].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								actNo: actNos,
								selectedOptions: selectedOptions,
								txnId: '70203',
								subTxnId: '01'
							}
						}
						);
					}
				});
		}
	}
   
    
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
	
	menuArr.push(queryMenu);	//[0]
	menuArr.push(deleteMenu);		//[1]
	

	// 终端信息列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户营销活动商户维护',
		iconCls: 'T702',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: actStore,
		sm: sm,
		cm: actColModel,
		tbar: menuArr,
		clicksToEdit: true,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载参与营销活动的商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: actStore,
			pageSize: 200,
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
			rec = grid.getSelectionModel().getSelections();
			if(rec != null ) {
				grid.getTopToolbar().items.items[1].enable();
			} else {
				grid.getTopToolbar().items.items[1].disable();
			}
		}
	});
	actStore.load();
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});

})