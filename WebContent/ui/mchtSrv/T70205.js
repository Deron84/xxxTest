Ext.onReady(function() {

    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 400,
        autoHeight: true,
        labelWidth: 80,
        items: [new Ext.form.TextField({
                id: 'actNo',
                name: 'actNo',
                width:250,
                fieldLabel: '活动编号'
            })],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                actStore.load();
//                queryWin.hide();
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
//			{name: 'checkFee',mapping: 'actFee'},
			{name: 'flag01',mapping: 'flag01'}
		])
	});
	
	actStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            state: 1,
            noFlag: 0,
            actNo: Ext.getCmp('actNo').getValue()
        });
    }); 
    
    actStore.load();
	function doIt(val,metadata,record,rowIndex){
        var actFee = record.get('actFee');
        var flag = record.get('flag01');
        if(flag == '2') {
        	return actFee;
        }
        return val;
    }
	
	var sm = new Ext.grid.CheckboxSelectionModel();
    sm.handleMouseDown = Ext.emptyFn;
	var actColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header: '活动编号',dataIndex: 'actNo',width: 80},
		{header: '活动名称',dataIndex: 'actName',width: 350},
		{id: 'mchntNo',header: '商户号',dataIndex: 'mchntNo',width: 150},
		{header: '商户名称',dataIndex: 'mchtNm',width: 400},
		{header: '所属机构',dataIndex: 'bankNo',width: 60},
		{header: '操作',dataIndex: 'flag01',renderer: mchntOprState,width: 50},
		{header: '活动状态',dataIndex: 'state',renderer: actState,width: 60},
		{header: '起始日期',dataIndex: 'startDate'},
		{header: '截止日期',dataIndex: 'endDate'}
//		,
//		{header: '费率(%)',dataIndex: 'checkFee'}
	]);
	
   var refuseMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		handler:function() {
           var record = grid.getSelectionModel().getSelected();
           if(record == null)
           {
           		showAlertMsg("请选择要拒绝记录",grid);
           		return;
           }
      	   var records = grid.getSelectionModel().getSelections();
           if(records.length > 1)
           {
           		sm.clearSelections();
           		grid.getTopToolbar().items.items[1].disable();
           		grid.getTopToolbar().items.items[2].disable();
           		showAlertMsg("只能选择一条拒绝记录",grid);
           		return;
           }
		   showConfirm('确定要拒绝这商户吗？',grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T70205Action_refuse.asp',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().load();
									topQueryPanel.getForm().reset();
									grid.getTopToolbar().items.items[1].disable();
									grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								actNo: record.get('actNo'),
								mchntNo: record.get('mchntNo'),
								txnId: '70205',
								subTxnId: '02'
							}
						}
						);
					}
				});
		}
	}
    
   var checkMenu = {
		text: '审核',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
           var selects = grid.getSelectionModel().getSelections();
           var actNos = new Array();
           var mchntNos = new Array();
//           var checkFees = new Array();
           var flags = new Array();
           if(selects == null)
           {
           		showAlertMsg("请选择要审核的记录",grid);
           		return;
           }
           for(var i = 0;i<selects.length;i++){
           		actNos[i] = selects[i].get('actNo');
				mchntNos[i] = selects[i].get('mchntNo');
//				checkFees[i] = selects[i].get('checkFee');
				flags[i] = selects[i].get('flag01');
			}
		   showConfirm('确定要审核这批商户吗？',grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T70205Action_batch.asp',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									actStore.load();
									topQueryPanel.getForm().reset();
									grid.getTopToolbar().items.items[1].disable();
									grid.getTopToolbar().items.items[2].disable();
								} else {
									actStore.load();
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								actNos: actNos,
								mchntNos: mchntNos,
//								actFees: checkFees,
								flags: flags,
								txnId: '70205',
								subTxnId: '03'
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
        width: 400,
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
	
	menuArr.push(queryMenu);	
	menuArr.push(checkMenu);		
	menuArr.push(refuseMenu);

	// 终端信息列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户营销活动商户调整复核',
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
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelections();
			if(rec != null ) {
				grid.getTopToolbar().items.items[1].enable();
				grid.getTopToolbar().items.items[2].enable();
			} else {
				grid.getTopToolbar().items.items[1].disable();
				grid.getTopToolbar().items.items[2].disable();
			}
		}
	});
   
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});

})