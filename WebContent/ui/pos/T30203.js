Ext.onReady(function() {
    
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
        labelWidth: 120,
        items: [new Ext.form.TextField({
                id: 'termIdIdQry',
                name: 'termIdIdQry',
                width: 280,
                fieldLabel: '终端号'
            }),{
            xtype: 'combo',
            fieldLabel: '第三方服务机构',
            id: 'propInsNmQ',
            hiddenName: 'propInsNm',
            width: 280,
            store: thirdBrhStore,
            displayField: 'displayField',
            valueField: 'valueField'
        },                                       
            new Ext.form.TextField({
                id: 'batchNoQry',
                width: 280,
                name: 'batchNoQry',
                fieldLabel: '批次号'
            })
            ,new Ext.form.TextField({
                id: 'reqOprQry',
                width: 280,
                name: 'reqOprQry',
                fieldLabel: '申请操作员'})
         ,{ 
                xtype: 'combo',
                fieldLabel: '申请状态',
                hiddenName: 'stateQ',
                id: 'stateQry',
                width: 280,
                displayField: 'displayField',
                valueField: 'valueField',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','已申请'],['1','已审核']]
                })
        },{
			width: 280,
			fieldLabel : '起始申请时间',
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
			endDateField: 'endDate'
		}, {
			width: 280,
			fieldLabel : '结束申请时间',
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
			maxValue: new Date(),
			startDateField: 'startDate'
		}],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                termStore.load();
                queryWin.hide();
                grid.getTopToolbar().items.items[0].disable();
                grid.getTopToolbar().items.items[1].disable();
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
            url: 'gridPanelStoreAction.asp?storeId=termTmkInfoAll'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'batchNo',mapping: 'batchNo'},
            {name: 'termIdId',mapping: 'termIdId'},
            {name: 'mchnNo',mapping: 'mchnNo'},
            {name: 'termBranch',mapping: 'termBranch'},
            {name: 'state',mapping: 'state'},
            {name: 'propInsNm',mapping: 'propInsNm'},
            {name: 'reqOpr',mapping: 'reqOpr'},
            {name: 'reqDate',mapping: 'reqDate'},
            {name: 'chkOpr',mapping: 'chkOpr'},
            {name: 'chkDate',mapping: 'chkDate'},
            {name: 'misc',mapping: 'misc'},
            {name: 'recUpdOpr',mapping: 'recUpdOpr'},
            {name: 'recUpdTs',mapping: 'recUpdTs'},
          //zjx 
            {name: 'prtOpr',mapping: 'prtOpr'},
            {name: 'prtDate',mapping: 'prtDate'},
            {name: 'prtCount',mapping: 'prtCount'}
        ])
    });
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termIdId: Ext.getCmp('termIdIdQry').getValue(),
            batchNo: Ext.getCmp('batchNoQry').getValue(),
            reqOpr: Ext.getCmp('reqOprQry').getValue(),
            state: Ext.getCmp('stateQry').getValue(),
            startDate: typeof(topQueryPanel.findById('startDate').getValue()) == 'string' ? '' : topQueryPanel.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(topQueryPanel.findById('endDate').getValue()) == 'string' ? '' : topQueryPanel.findById('endDate').getValue().dateFormat('Ymd'),
			propInsNm: topQueryPanel.getForm().findField('propInsNmQ').getValue()
        });
    });
    termStore.load();

    var sm = new Ext.grid.CheckboxSelectionModel();
    sm.handleMouseDown = Ext.emptyFn;
    
    var termColModel = new Ext.grid.ColumnModel([
         new Ext.grid.RowNumberer(),
         sm,
        {id: 'termIdId',header: '终端号',dataIndex: 'termIdId',width: 90},
        {header: '批次号',dataIndex: 'batchNo',width: 100},
        {header: '商户',dataIndex: 'mchnNo',width:280,id:'mchnNo',renderer:function(val){return getRemoteTrans(val, "mchntName");}},
        {header: '终端所属机构',dataIndex: 'termBranch',width: 190},
        {header: '申请状态',dataIndex: 'state',width:70,renderer: termState},
        {header: '第三方服务机构',dataIndex: 'propInsNm',width:140},
        {header: '申请操作员',dataIndex: 'reqOpr',width:80},
        {header: '申请日期',dataIndex: 'reqDate',width:100},
        {header: '审核操作员',dataIndex: 'chkOpr',width:80},
        {header: '审核日期',dataIndex: 'chkDate',width:100}
    ]);
    
    var qryMenu = {
        text: '审核',
        width: 85,
        iconCls: 'edit',
        disabled: true,
        handler:function() {

    	if(grid.getSelectionModel().hasSelection()) {
    		var rec = grid.getSelectionModel().getSelections();
			if(rec.length < 1){
				showErrorMsg("请选择需要审核的商户",grid);
				return;
			}
			
			var termIdArray=new Array();
			var batchArray=new Array();
			for(var i=0;i<rec.length;i++){
				termIdArray[i]=rec[i].get('termIdId');
				batchArray[i]=rec[i].get('batchNo');
			}
			showConfirm('确认审核吗？',grid,function(bt) {
				 if(bt == 'yes') {
	                    showProcessMsg('正在提交信息，请稍后......');
				        Ext.Ajax.request({
				                url: 'T30203Action.asp',
				                waitMsg: '正在提交，请稍后......',
				                params: {
				                    termIdId: termIdArray,
				                    batchNo: batchArray,
				                    txnId: '30203',
				                    subTxnId: '01'
				                },
				                success: function(rsp,opt) {
				                    var rspObj = Ext.decode(rsp.responseText);
				                    if(rspObj.success) {
				                        showSuccessMsg(rspObj.msg,grid);
				                    } else {
				                        showErrorMsg(rspObj.msg,grid);
				                    }
				                    // 重新加载终端待审核信息
				                    grid.getStore().load();
				                }
				            });
				            hideProcessMsg();
				            grid.getTopToolbar().items.items[0].disable();
				            grid.getTopToolbar().items.items[1].disable();
				 }
			});
        }else{
	    	showErrorMsg("请选择需要审核的对象",grid);
			return;
	    }
        }
    }


    var refuseMenu = {
        text: '拒绝',
        width: 85,
        iconCls: 'refuse',
        disabled: true,
        handler:function() {
    	if(grid.getSelectionModel().hasSelection()) {
    		var rec = grid.getSelectionModel().getSelections();
			if(rec.length==0){
				showErrorMsg("请选择需要拒绝的商户",grid);
				return;
			}
			var termIdArray=new Array();
			var batchArray=new Array();
			for(var i=0;i<rec.length;i++){
				termIdArray[i]=rec[i].get('termIdId');
				batchArray[i]=rec[i].get('batchNo');
			}
            showConfirm('确认拒绝吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在提交信息，请稍后......');
//                    var rec = grid.getSelectionModel().getSelected();
                    Ext.Ajax.request({
                        url: 'T30203Action.asp',
                        params: {
                            termIdId: termIdArray,
                            batchNo: batchArray,
                            txnId: '30203',
                            subTxnId: '03'
                        },
                        success: function(rsp,opt) {
                            var rspObj = Ext.decode(rsp.responseText);
                            if(rspObj.success) {
                                showSuccessMsg(rspObj.msg,grid);
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                            // 重新加载终端待审核信息
                            grid.getStore().load();
                        }
                    });
                    hideProcessMsg();
                    grid.getTopToolbar().items.items[0].disable();
                    grid.getTopToolbar().items.items[1].disable();
                }
            });
        }else{
	    	showErrorMsg("请选择需要拒绝的对象",grid);
			return;
	    }
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
    
    menuArr.push(qryMenu); 
    menuArr.push(refuseMenu);
    menuArr.push(queryMenu);
    
    // 终端信息列表
    var grid = new Ext.grid.GridPanel({
        title: '终端密钥下发审核',
        region:'center',
        iconCls: 'T30203',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: termStore,
//        autoExpandColumn: 'mchnNo',
//        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        sm:sm,
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
            if(rec != null&&rec.get('state') == '0') {
                grid.getTopToolbar().items.items[0].enable();
                grid.getTopToolbar().items.items[1].enable();
            } else {
                grid.getTopToolbar().items.items[0].disable();
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