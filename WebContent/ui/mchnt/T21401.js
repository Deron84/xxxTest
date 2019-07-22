Ext.onReady(function() {
	var selectedRecord ;
    // 商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_NO1',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    
  //终端ID
    var equipmentStore= new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    // 终端类型数据集
    var termTypeStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('TERM_TYPE',function(ret){
        termTypeStore.loadData(Ext.decode(ret));
    });
    //专业服务机构
    var organStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('ORGAN',function(ret){
        organStore.loadData(Ext.decode(ret));
    });
  //EPOS版本号
    var eposStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION_NEW','',function(ret){
		eposStore.loadData(Ext.decode(ret));
	});
    
    //查询搞定
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 570,
        autoHeight: true,
        labelWidth: 100,
        items: [{
            id: 'mchntIdQ',
            displayField: 'displayField',
            valueField: 'valueField',
        	xtype: 'dynamicCombo',
            fieldLabel: '商户号',
            methodName: 'getMchtCdInTemp',
            hiddenName: 'mchntIdQuery',
            editable : true,
            width: 300,
            listeners:{
            	'select':function(){
            		var mchntid=Ext.getCmp("mchntIdQ").getValue();
            		Ext.getCmp("equipmentIdQ").setValue('');
            		SelectOptionsDWR.getComboDataWithParameter('TERM_ID',mchntid,function(ret){
            			equipmentStore.loadData(Ext.decode(ret));
            	    });
            	}
            }},{
            	xtype: 'combo',
                fieldLabel: '终端号',
                id: 'equipmentIdQ',
                hiddenName: 'equipmentIdQB',
                displayField: 'displayField',
                valueField: 'valueField',
                width:300,
                readOnly:false,
                blankText: '终端号不能为空',
                store:equipmentStore},{
                        xtype: 'combo',
                        fieldLabel: '收单方',
                        id: 'acquiresIdQ',
                        store: new Ext.data.ArrayStore({
                            fields: ['valueField','displayField'], 

                            data: [['01','银联'],['00','山东一卡通']]
                        }),
                        width:180,
                        hiddenName: 'acquiresIdQB'
                },{
                    width: 180,
                    xtype: 'textfield',
                    fieldLabel: '收单方终端号',
                    name :'acequipmentIdQ',
                    id :'acequipmentIdQ'
                          
              },{
                  width: 180,
                  xtype: 'textfield',
                  fieldLabel: '收单方商户号',
                  name :'acmchntIdQ',
                  id :'acmchntIdQ'
                        
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
            	var endtime=Ext.getCmp('endTime').getValue(),starttime=Ext.getCmp('startTime').getValue();
            	if(endtime!=''&&starttime!=''&&endtime<starttime){
            		showErrorMsg("请保证截止时间不小于起始时间",grid);
    				return;
            	}
            	termStore.load();
            	queryWin.hide();
            	grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[3].disable();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
                equipmentStore.removeAll();
            }
        }]
    });
    
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntMappingInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mappingId',mapping: 'mappingId'},
			{name: 'mchntId',mapping: 'mchntId'},
			{name: 'equipmentId',mapping: 'equipmentId'},
			{name: 'termName',mapping: 'termName'},
			{name: 'acquirersId',mapping: 'acquirersId'},
			{name: 'remark',mapping: 'remark'},
			{name: 'createTime',mapping: 'createTime'},
            {name: 'createPerson',mapping: 'createPerson'},
            {name: 'acmchntId',mapping: 'acmchntId'},
            {name: 'acequipmentId',mapping: 'acequipmentId'}
		]),
		autoLoad:true
	});
	
	termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            equipmentId: Ext.getCmp('equipmentIdQ').getValue(),
            mchntId: Ext.getCmp('mchntIdQ').getValue(),
            acquiresId: Ext.getCmp('acquiresIdQ').getValue(),
            acequipmentId:Ext.getCmp('acequipmentIdQ').getValue(),
            acmchntId:Ext.getCmp('acmchntIdQ').getValue(),
            startTime:Ext.getCmp('startTime').getValue(),
            endTime:Ext.getCmp('endTime').getValue()
        });
    });
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect: true});
    sm.handleMouseDown = Ext.emptyFn;
	var termColModel = new Ext.grid.ColumnModel([
	    sm,
	    {id:'mappingId',header: 'ID',dataIndex: 'mappingId',width:200,align:'center',hidden:true},
		{id:'mchntId',header: '商户号',dataIndex: 'mchntId',width:200,align:'center'},
		{header: '终端号',dataIndex: 'equipmentId',width:200,align:'center'},
		{header: '终端名称',dataIndex: 'termName',width:100,align:'center'},
		{header: '收单方',dataIndex: 'acquirersId',width:100,align:'center',renderer:shoudanfang},
		{id: 'acmchntId',header: '收单方商户号',dataIndex: 'acmchntId',width:100,align:'center'},
		{id: 'acequipmentId',header: '收单方终端号',dataIndex: 'acequipmentId',width:100,align:'center'},
		{header: '备注',dataIndex: 'remark',width:200,align:'center'},
		{id: 'createTime',header: '创建时间',dataIndex: 'createTime',width: 120},
        {id: 'createPerson',header: '创建人',dataIndex: 'createPerson',width: 100}
	]);
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			termWin.show();
			termWin.center();
			equipmentStore.removeAll();
		}
	};
	var termInfoStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=mchntMappingInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'mchntIdU',mapping: 'mchntId'},
            {name: 'equipmentIdUpd',mapping: 'equipmentId'},
            {name: 'termNameUpd',mapping: 'termName'},
            {name: 'mappingIdUpd',mapping: 'mappingId'},
            {name: 'acquirersIdUpd',mapping: 'acquirersId'},
            {name: 'acmchntIdUpd',mapping: 'acmchntId'},
            {name: 'acequipmentIdUpd',mapping: 'acequipmentId'},
            {name: 'remarkUpd',mapping: 'remark'}
        ])
    });
	
    
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			
            selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }    
            termInfoStore.load({
                params: {
                	mchntId: selectedRecord.get('mchntId'),
                	equipmentId: selectedRecord.get('equipmentId'),
                	acquirersId: selectedRecord.get('acquirersId')
                },
                callback: function(records, options, success){
                
                    if(success){
                       Ext.getCmp('mchntIdU').setValue(records[0].data.mchntIdU);
                       Ext.getCmp('equipmentIdUpd').setValue(records[0].data.equipmentIdUpd+'-'+records[0].data.termNameUpd);
                       
                       Ext.getCmp('mappingIdUpd').setValue(records[0].data.mappingIdUpd);
                       Ext.getCmp('acquirersIdUpd').setValue(records[0].data.acquirersIdUpd);
                       Ext.getCmp('acmchntIdUpd').setValue(records[0].data.acmchntIdUpd);
                       Ext.getCmp('acequipmentIdUpd').setValue(records[0].data.acequipmentIdUpd);
                       Ext.getCmp('remarkUpd').setValue(records[0].data.remarkUpd);
                       var mchntid=records[0].data.mchntIdU;
	               		SelectOptionsDWR.getComboDataWithParameter('TERM_ID',mchntid,function(ret){
	               			equipmentStore.loadData(Ext.decode(ret));
	               	    });
                       updTermWin.show();
                       document.getElementById("equipmentIdU").value=records[0].data.equipmentIdUpd;
                    }else{
                    
                       updTermWin.hide();
                    }
                }
            });
		}
	};    
	var delMenu = {//不提供删除
	        text: '删除',
	        width: 85,
	        iconCls: 'delete',                                     
	        disabled: true,
	        handler:function() {
	            selectedRecord = grid.getSelectionModel().getSelected();
	            if(selectedRecord == null)
	            {
	                showAlertMsg("没有选择记录",grid);
	                return;
	            }  
	            showConfirm('确定要删除该终端吗？',grid,function(bt) {
	                if(bt == 'yes') {
//	                    showProcessMsg('正在提交信息，请稍后......');
	                    rec = grid.getSelectionModel().getSelected();
	                     Ext.Ajax.requestNeedAuthorise({
	                        url: 'T21401Action.asp?method=delete',
	                        params: {
	                        	mappingId: selectedRecord.get('mappingId'),
	                            //mchtCd: selectedRecord.get('mchtCd'),
	                            //termSta:selectedRecord.get('termSta'),
	                            txnId: '21401',
	                            subTxnId: '03'
	                        },
	                        success: function(rsp,opt) {
	                            var rspObj = Ext.decode(rsp.responseText);
	                            termStore.reload();
	                            if(rspObj.success) {
	                                showSuccessMsg(rspObj.msg,grid);
	                            } else {
	                                showErrorMsg(rspObj.msg,grid);
	                            }
	                        }
	                    });
	                    grid.getTopToolbar().items.items[3].disable();
	                    hideProcessMsg();
	                }
	            });
	        }
	    };
    
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 570,
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
        },{
            id: 'close',
            handler: function(event,toolEl,panel,tc) { 
                queryWin.hide();
                topQueryPanel.getForm().reset();
                equipmentStore.removeAll();
            },
            qtip: '关闭'
        }]
    });
    
    
    
	var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
            equipmentStore.removeAll();
        }
    };
	var menuArr = new Array();
	
	menuArr.push(addMenu);		//[0]新增
	menuArr.push(queryMenu);	//[1]查询
	menuArr.push(editMenu);		//[2]修改
    menuArr.push(delMenu);      //[7]不提供删除
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '商户映射列表维护',
		iconCls: 'T201',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		sm: sm,
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    
	
	// 所属机构
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	

	// 终端库存号
	var termIdIdStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	// 终端厂商
	var manufacturerStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MANUFACTURER',function(ret){
		manufacturerStore.loadData(Ext.decode(ret));
	});
	
	//终端型号
	var terminalTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});


     var termPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 350,
        width: 750,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1New',
                layout: 'column',
                frame: true,
                items: [{
                 columnWidth: 1,
                 layout: 'form',
                 items: [{
                    xtype: 'combo',
                    fieldLabel: '商户号*',
                    store: mchntStore,
                    id: 'mchntIdN',
                    hiddenName: 'mchntIdNB',
                    displayField: 'displayField',
                    valueField: 'valueField',
                    blankText: '商户号不能为空',
                    allowBlank: false,
                    emptyText: '请选择商户号',
                    width: 250,
                    listeners:{
                    	'select':function(){
                    		var mchntid=Ext.getCmp("mchntIdN").getValue();
                    		Ext.getCmp("equipmentIdN").setValue('');
                    		SelectOptionsDWR.getComboDataWithParameter('TERM_ID',mchntid,function(ret){
                    			equipmentStore.loadData(Ext.decode(ret));
                    	    });
                    	}
                    }
                  }]
             },{
                columnWidth: 1,
                layout: 'form',
                items:[{
                   xtype: 'combo',
                   fieldLabel: '终端号*',
                   id: 'equipmentIdN',
                   hiddenName: 'equipmentIdNB',
                   displayField: 'displayField',
                   valueField: 'valueField',
                   width:250,
                   allowBlank: false,
                   readOnly:false,
                   blankText: '终端号不能为空',
                   store:equipmentStore
                }]
           },{
               columnWidth: 1,
               layout: 'form',
               items: [{
                   xtype: 'combo',
                   fieldLabel: '收单方*',
                   id: 'acquiresIdN',
                   store: new Ext.data.ArrayStore({
                       fields: ['valueField','displayField'], 

                       data: [['01','银联'],['00','山东一卡通']]
                   }),
                   allowBlank: false,
                   width:250,
                   hiddenName: 'acquiresIdNB'                   
               }]
           },{
               columnWidth: 1, 
               layout: 'form',
               items: [{
                   xtype: 'textfield',
                   fieldLabel: '收单方商户号*',
                   width:250,
                   id: 'acmchntIdN',
                   name: 'acmchntIdN',
                   readOnly: false,
                   allowBlank: false,
                   maxLength:20,
                   listeners:{
                   	render:function(field){
                   		Ext.QuickTips.init();   
                   		Ext.QuickTips.register({   
                   		target : field.el,   
                   		text : '请输入数字或字母'   
                   		}) 
                   	}
                   }
               }]
       },{
           columnWidth: 1, 
           layout: 'form',
           items: [{
               xtype: 'textfield',
               fieldLabel: '收单方终端号*',
               width:250,
               id: 'acequipmentIdN',
               name: 'acequipmentIdN',
               readOnly: false,
               allowBlank: false,
               maxLength:20,
               listeners:{
               	render:function(field){
               		Ext.QuickTips.init();   
               		Ext.QuickTips.register({   
               		target : field.el,   
               		text : '请输入数字或字母'   
               		}) 
               	}
               }
           }]
   },{
                columnWidth: 1, 
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '备注',
                    width:250,
                    id: 'remarkN',
                    name: 'remarkN',
                    readOnly: false
                }]
        }]
            }]
    });
    /**************  终端表单  *********************/
	var termForm = new Ext.form.FormPanel({
		frame: true,
		height: 350,
		width: 750,
		labelWidth: 100,
		waitMsgTarget: true,
		layout: 'column',
		items: [termPanel]
	});
   
    /***********  终端信息窗口  *****************/
	var termWin = new Ext.Window({
		title: '商户映射添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 750,
		autoHeight: true,
		layout: 'fit',
		items: [termForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'T201',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(termForm.getForm().isValid()) {
					termForm.getForm().submitNeedAuthorise({
						url: 'T21401Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessDtl(action.result.msg,termWin);
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							termForm.getForm().reset();
							equipmentStore.removeAll();
                            termWin.hide();
						},
						failure: function(form,action) {
							termPanel.setActiveTab('info1New');
							showErrorMsg(action.result.msg,termWin);
						},
						params: {
							txnId: '21401',
							subTxnId: '01',
							mchntId:Ext.getCmp('mchntIdN').getValue(),
                            equipmentId:Ext.getCmp('equipmentIdN').getValue(),
                            acmchntId:Ext.getCmp('acmchntIdN').getValue(),
                            acequipmentId:Ext.getCmp('acequipmentIdN').getValue(),
                            acquiresId:Ext.getCmp('acquiresIdN').getValue(),
                            remark:Ext.getCmp('remarkN').getValue()
						}
					});

			}else{
				termPanel.setActiveTab('info1New');
				termForm.getForm().isValid();
			}
		}
		},{
			text: '重置',
			handler: function() {
					termForm.getForm().reset();
					equipmentStore.removeAll();
			}
		},{
			text: '关闭',
			handler: function() {
				termWin.hide();
				termForm.getForm().reset();
				equipmentStore.removeAll();
			}
		}]
	});
/**************** 终端修改 *************************/
    var updTermPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 350,
        width: 750,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1Upd',
                layout: 'column',
                frame: true,
                items: [{
                 columnWidth: 1,
                 layout: 'form',
                 items: [{
                     columnWidth: 1, 
                     layout: 'form',
                     items: [{
                         xtype: 'textfield',
                         width:250,
                         id: 'mappingIdUpd',
                         name: 'mappingIdUpd',
                         allowBlank: false,
                         readOnly: false,
                         hidden:true
                     }]
             },{
                    xtype: 'combo',
                    fieldLabel: '商户号*',
                    store:mchntStore,
                    id: 'mchntIdU',
                    hiddenName:'mchntIdUpd',
                    displayField: 'displayField',
                    valueField: 'valueField',
                    blankText: '商户号不能为空',
                    allowBlank: false,
                    readOnly:false,
                    width:250,
                    emptyText: '请选择商户号',
                    listeners:{
                    	'select':function(){
                    		var mchntid=Ext.getCmp("mchntIdU").getValue();
                    		Ext.getCmp("equipmentIdUpd").setValue('');
                    		SelectOptionsDWR.getComboDataWithParameter('TERM_ID',mchntid,function(ret){
                    			equipmentStore.loadData(Ext.decode(ret));
                    	    });
                    	}
                    }
                  }]
             },{
                columnWidth: 1,
                layout: 'form',
                items:[{
                   xtype: 'combo',
                   fieldLabel: '终端号*',
                   id: 'equipmentIdUpd',
                   hiddenName: 'equipmentIdU',
                   displayField: 'displayField',
                   valueField: 'valueField',
                   allowBlank: false,
                   readOnly:false,
                   width:250,
                   blankText: '终端号不能为空',
                   store:equipmentStore
                }]
           },{
               columnWidth: 1,
               layout: 'form',
               items: [{
                   xtype: 'combo',
                   fieldLabel: '收单方*',
                   id: 'acquirersIdUpd',
                   allowBlank: false,
                   width:250,
                   store: new Ext.data.ArrayStore({
                       fields: ['valueField','displayField'], 

                       data: [['01','银联'],['00','山东一卡通']]
                   }),
                   hiddenName:'acquirersIdU'
               }]
           },{
               columnWidth: 1, 
               layout: 'form',
               items: [{
                   xtype: 'textfield',
                   fieldLabel: '收单方商户号*',
                   width:250,
                   id: 'acmchntIdUpd',
                   name: 'acmchntIdUpd',
                   allowBlank: false,
                   readOnly: false
               }]
       },{
                    columnWidth: 1, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '收单方终端号*',
                        width:250,
                        id: 'acequipmentIdUpd',
                        name: 'acequipmentIdUpd',
                        allowBlank: false,
                        readOnly: false
                    }]
            },{
                columnWidth: 1, 
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '备注',
                    width:250,
                    id: 'remarkUpd',
                    name: 'remarkUpd',
                    readOnly: false
                }]
        }]
            }]
    });
/*******************  终端修改表单  *********************/
    var updTermForm = new Ext.form.FormPanel({
        frame: true,
        height: 350,
        width: 750,
        labelWidth: 150,
        waitMsgTarget: true,
        layout: 'column',
        items: [updTermPanel]
    });
   
/*******************  终端修改信息 *********************/
    var updTermWin = new Ext.Window({
        title: '客户映射修改',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 750,
        autoHeight: true,
        layout: 'fit',
        items: [updTermForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'T201',
        resizable: false,
        buttons: [{
            text: '确定',
            handler: function() {
					updTermPanel.setActiveTab("info1Upd");
                	if(updTermForm.getForm().isValid()){
                    updTermForm.getForm().submitNeedAuthorise({
                        url: 'T21401Action.asp?method=update',
                        waitMsg: '正在提交，请稍后......',
                        success: function(form,action) {
                            showSuccessMsg(action.result.msg,updTermForm);
                            grid.getStore().reload();
                            updTermForm.getForm().reset();
                            updTermWin.hide();
                            equipmentStore.removeAll();
                            grid.getTopToolbar().items.items[2].disable();
                        },
                        failure: function(form,action) {
                            updTermPanel.setActiveTab('info1Upd');
                            showErrorMsg(action.result.msg,updTermForm);
                        },
                        params: {
                            txnId: '21401',
                            subTxnId: '02',
                            mappingId:Ext.getCmp('mappingIdUpd').getValue(),
                            mchntId:Ext.getCmp('mchntIdU').getValue(),
                            equipmentId: document.getElementById("equipmentIdU").value,
                            acmchntId:Ext.getCmp('acmchntIdUpd').getValue(),
                            acequipmentId:Ext.getCmp('acequipmentIdUpd').getValue(),
                            acquiresId:Ext.getCmp('acquirersIdUpd').getValue(),
                            remark:Ext.getCmp('remarkUpd').getValue()
                        }
                    });
               
                	}else{
                		updTermPanel.setActiveTab('info1Upd');
                		updTermForm.getForm().isValid()
                	}
            }
        },{
            text: '关闭',
            handler: function() {
                updTermWin.hide();
            }
        }]
    });
    
    //工具按钮可用性
    grid.getSelectionModel().on({
        'rowselect': function() {
            //行高亮
            Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
            // 根据商户状态判断哪个编辑按钮可用
            rec = grid.getSelectionModel().getSelected();
            if(rec != null) {
                grid.getTopToolbar().items.items[2].enable();
                grid.getTopToolbar().items.items[3].enable();
            } else {
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[3].disable();
            }

        }
    });
    //渲染到页面
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});