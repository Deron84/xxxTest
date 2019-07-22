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
        items: [{
    	    xtype: 'dynamicCombo',
            id: 'mchnNoQ',
            hiddenName: 'mchnNoQuery',
            displayField: 'displayField',
            valueField: 'valueField',
            methodName: 'getMchtCdInTemp',
            fieldLabel: '商户ID',
            width:200
       },{
			xtype:'textfield',
			fieldLabel: '商户号',
			id:'acmchntIdQ',
			name:'acmchntIdQ',
			width:200
		},{                                         
           xtype: 'combo',
           fieldLabel: '申请状态',
           id: 'stateQ',
           hiddenName: 'stateQuery',
           width: 150,
           store: new Ext.data.ArrayStore({
               fields: ['valueField','displayField'],                    
               //0：新增待审核 1：待装机 2 ：已装机 3：修改待审核4：冻结 5：冻结待审核 6:解冻待审核7：注销8：注销待审核 9：撤机A:新增退回 

               data: [['0','待审核'],['2','已审核'],['3','已拒绝']]
           })
   },
       {
           width: 200,
           xtype: 'datefield',
           fieldLabel: '起始时间',
           format : 'Y-m-d',
           name :'startTime',
           id :'startTime',
           anchor :'60%'       
     },{                                         
           width: 200,
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
            	var endtime=Ext.getCmp('endTime').getValue(),starttime=Ext.getCmp('startTime').getValue();
            	if(endtime!=''&&starttime!=''&&endtime<starttime){
            		showErrorMsg("请保证截止时间不小于起始时间",grid);
    				return;
            	}
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
            url: 'gridPanelStoreAction.asp?storeId=gettblMchntApplyInf'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'applyId',mapping: 'applyId'},
            {name: 'mchnNo',mapping: 'mchnNo'},
            {
				name : 'mappingMchntcdOne',
				mapping : 'mappingMchntcdOne'
			}, {
				name : 'mappingMchntcdTwo',
				mapping : 'mappingMchntcdTwo'
			},
            {name: 'type',mapping: 'type'},
            {name: 'state',mapping: 'state'},            
            {name: 'createTime',mapping: 'createTime'},
            {name: 'createPerson',mapping: 'createPerson'},
            {name: 'userName',mapping: 'userName'},
            {name: 'createPhone',mapping: 'createPhone'},
            {name: 'auditTime',mapping: 'auditTime'},
            {name: 'auditId',mapping: 'auditId'},
            {name: 'auditPhone',mapping: 'auditPhone'},
            {name: 'remark',mapping: 'remark'}
        ]),
        autoLoad: true
    });
    
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            mchnNo: Ext.getCmp('mchnNoQ').getValue(),
            acmchntId: Ext.getCmp('acmchntIdQ').getValue(),
            startTime:Ext.getCmp('startTime').getValue(),
            endTime:Ext.getCmp('endTime').getValue(),
            applyStatus:Ext.getCmp('stateQ').getValue()
        });
    });
  
   

    var sm = new Ext.grid.CheckboxSelectionModel({singleSelect: true});
    sm.handleMouseDown = Ext.emptyFn;
    
    var termColModel = new Ext.grid.ColumnModel([
         new Ext.grid.RowNumberer(),
         sm,
        {id: 'applyId',header: '申请编号',dataIndex: 'applyId',width: 100},
        {id: 'mchnNo',header: '商户ID',dataIndex: 'mchnNo',width: 200,align:'center'},
        {header: '商户号1',dataIndex: 'mappingMchntcdOne',width: 100},
		{header: '商户号2',dataIndex: 'mappingMchntcdTwo',width: 100},
        {header: '申请状态',dataIndex: 'state',width:100,renderer:applyStatus,align:'center'},
        {id: 'createTime',header: '申请时间',dataIndex: 'createTime',width: 120},
        {id: 'userName',header: '申请人',dataIndex: 'userName',width: 100},
        {id: 'createPhone',header: '申请人电话',dataIndex: 'createPhone',width: 120},
        {id: 'auditTime',header: '审核时间',dataIndex: 'auditTime',width: 120},
        {id: 'auditId',header: '审核人',dataIndex: 'auditId',width: 100},
        {id: 'auditPhone',header: '审核人电话',dataIndex: 'auditPhone',width: 100},
        {id: 'remark',header: '备注',dataIndex: 'remark',width: 200}
    ]);
    
    
    setHeader(termColModel,4,5);
    var qryMenu = {
        text: '审核',
        width: 85,
        iconCls: 'edit',
        disabled: true,
        handler:function() {

    	if(grid.getSelectionModel().hasSelection()) {
    		var rec = grid.getSelectionModel().getSelected();
			if(rec.length < 1){
				showErrorMsg("请选择需要审核的商户",grid);
				return;
			}
			
			showConfirm('确认审核吗？',grid,function(bt) {
				 if(bt == 'yes') {
	                    /*showProcessMsg('正在提交信息，请稍后......');*/
					    showInputMsg('提示','请输入通过备注',true,confirm);
				       
				 }
			});
        }else{
	    	showErrorMsg("请选择需要审核的对象",grid);
			return;
	    }
        }
    }
    
    function confirm(bt,text){
    	if(bt=='ok'){
    		if(getLength(text) > 60) {
				alert('通过备注最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入通过备注',true,confirm);
				return false;
			}
			
			var rec = grid.getSelectionModel().getSelected(); 
    		 Ext.Ajax.request({
	                url: 'T21400Action.asp?method=pass',
	                waitMsg: '正在提交，请稍后......',
	                params: {
	                	applyId: rec.get('applyId'),
	                	mchntId: rec.get('mchnNo'),						
						applyStatus: rec.get('state'),
						applyType: '3',
	                    txnId: '21400',
	                    subTxnId: '01',
	                    refuseInfo:text
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
	           /* hideProcessMsg();*/
	            grid.getTopToolbar().items.items[0].disable();
	            grid.getTopToolbar().items.items[1].disable();
    	}
    }


    var refuseMenu = {
        text: '拒绝',
        width: 85,
        iconCls: 'refuse',
        disabled: true,
        handler:function() {
    	if(grid.getSelectionModel().hasSelection()) {
    		var rec = grid.getSelectionModel().getSelected();
			if(rec.length==0){
				showErrorMsg("请选择需要拒绝的商户",grid);
				return;
			}
			
            showConfirm('确认拒绝吗？',grid,function(bt) {
                if(bt == 'yes') {
                	showInputMsg('提示','请输入拒绝原因',true,refuse);
                    /*showProcessMsg('正在提交信息，请稍后......');*/
                    
                }
            });
        }else{
	    	showErrorMsg("请选择需要拒绝的对象",grid);
			return;
	    }
      }
    }
    
    function refuse(bt,text){
    	if(bt=='ok'){
    		if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return false;
			}
    		var rec = grid.getSelectionModel().getSelected();
            Ext.Ajax.request({
                url: 'T21400Action.asp?method=refuse',
                params: {
                	applyId: rec.get('applyId'),
                	mchntId: rec.get('mchnNo'),					
					applyStatus: rec.get('state'),
					applyType: '3',
                    txnId: '21400',
                    subTxnId: '02',
                    refuseInfo:text
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
            /*hideProcessMsg();*/
            grid.getTopToolbar().items.items[0].disable();
            grid.getTopToolbar().items.items[1].disable();
    		
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
        title: '商户密码重置审核',
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