Ext.onReady(function() {
    // 商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_NO01',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    
    var flag2Store = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCHT_FALG2',function(ret){
 		var data=Ext.decode(ret);
 		flag2Store.loadData(data);
	});
	
	//专业服务机构
    var organStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('ORGAN',function(ret){
        organStore.loadData(Ext.decode(ret));
    });
    
    // 终端类型数据集
    var termTypeStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('TERM_TYPE',function(ret){
        termTypeStore.loadData(Ext.decode(ret));
    });
    
    var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=termInfoAll01'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'termId',mapping: 'termId'},
            {name: 'organId',mapping: 'orgId'},
            {name: 'mchtCd',mapping: 'mchtCd'},
            {name: 'mappingMchntcdOne',mapping: 'mappingMchntcdOne'},
			{name: 'mappingTermidOne',mapping: 'mappingTermidOne'},
			{name: 'mappingMchntcdTwo',mapping: 'mappingMchntcdTwo'},
			{name: 'mappingTermidTwo',mapping: 'mappingTermidTwo'},
            {name: 'mchtFlag1',mapping: 'mchtFlag1'},
            {name: 'mchtFlag2',mapping: 'mchtFlag2'},
            {name: 'connTp',mapping: 'connTp'},
            {name: 'termSta',mapping: 'termSta'},
            {name: 'termSignSta',mapping: 'termSignSta'},
            {name: 'termIdId',mapping: 'termIdId'},
            {name: 'termFactory',mapping: 'termFactory'},
            {name: 'termMachTp',mapping: 'termMachTp'},
            {name: 'termVer',mapping: 'termVer'},
            {name: 'termTp',mapping: 'termTp'},
            {name: 'mchtGrp',mapping: 'mchtGrp'},
            {name: 'termBranch',mapping: 'termBranch'},
            {name: 'termIns',mapping: 'termIns'},
            {name: 'recCrtTs',mapping: 'recCrtTs'},
            {name: 'proRate',mapping: 'proRate'},
			{name: 'leaseFee',mapping: 'leaseFee'},
			{name: 'rentFee',mapping: 'rentFee'},
			{name: 'useDate',mapping: 'useDate'}
        ])
//    ,
//        autoLoad: true
    });
    
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termNoQ').getValue(),
            acmchntId: Ext.getCmp('acmchntIdQ').getValue(),
            acequipmentId: Ext.getCmp('acequipmentIdQ').getValue(),
            orgId: Ext.getCmp('orgIdId').getValue(),
            termTp: topQueryPanel.getForm().findField('termTpId').getValue(),
            mchtGrp: topQueryPanel.getForm().findField('mchtGrp').getValue(),
            brhId: Ext.getCmp('idacqInstId').getValue(),
            mchtFlag1: Ext.getCmp('idMchtFlag1').getValue(),
            mchtFlag2: Ext.getCmp('idMchtFlag2').getValue(),
            connType: topQueryPanel.getForm().findField('connType').getValue(),
            termSta: Ext.getCmp('state').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
            startTimeUse: topQueryPanel.getForm().findField('startTimeUse').getValue(),
            endTimeUse: topQueryPanel.getForm().findField('endTimeUse').getValue(),
            mchtCd: Ext.getCmp('mchnNoQ').getValue()
        });
    }); 
    
    var termColModel = new Ext.grid.ColumnModel([
        {id: 'termId',header: '终端ID',dataIndex: 'termId',width: 100},
        {id: 'mchtCd',header: '商户ID',dataIndex: 'mchtCd',width: 200,renderer:function(val){return getRemoteTrans(val, "mchntName");}},
        {header: '一卡通商户号',dataIndex: 'mappingMchntcdOne',width: 100},
		{header: '一卡通终端号',dataIndex: 'mappingTermidOne',width: 100},
		{header: '银联商户号',dataIndex: 'mappingMchntcdTwo',width: 100},
		{header: '银联终端号',dataIndex: 'mappingTermidTwo',width: 100},
        {header: '商户性质1',dataIndex: 'mchtFlag1',renderer:mchtTp},
        {header: '商户性质2',dataIndex: 'mchtFlag2',renderer:mchtTp2},
        {header: '商户组别',dataIndex: 'mchtGrp',width: 100,renderer:mchtGrp},
        {header: '连接类型',dataIndex: 'connTp',width: 70,renderer : transConnType},
        {header: '终端类型',dataIndex: 'termTp',width: 100,renderer:function(val){return getRemoteTrans(val, "termTp");}},
        {header: '终端状态',dataIndex: 'termSta',width: 80,renderer: termSta},
        {header: '装机日期',dataIndex: 'useDate',width: 80},
        {header: '归属机构',dataIndex: 'termBranch',width: 180,renderer:function(val){return getRemoteTrans(val, "brhName");}},
        {header: '终端库存编号',dataIndex: 'termIdId'},
        {header: '终端厂商',dataIndex: 'termFactory',width: 150},
        {header: '终端型号',dataIndex: 'termMachTp',renderer:function(val){return getRemoteTrans(val, "sysPara");}},
        {header: '外包商',dataIndex: 'organId',width: 180},//,renderer:function(val){return getRemoteTrans(val, "organName");}},
		{header: '外包分润比例(%)',dataIndex: 'proRate',width: 120},
		{header: '终端租赁费用(元/天)',dataIndex: 'leaseFee',width: 150},
		{header: '单月限额(元)',dataIndex: 'rentFee'}
    ]);
    function brhValue(val){
    	if(val == null){
    		return '';
    	}else{
    		return getRemoteTrans(val, "organName");
    	}
    }
    
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 550,
        autoHeight: true,
        labelWidth: 80,
        items: [{
        		xtype: 'basecomboselect',
				baseParams: 'BRH_BELOW',
				fieldLabel: '归属机构',
				id: 'idacqInstId',
				hiddenName: 'acqInstId',
				width: 380
			},new Ext.form.TextField({
                id: 'termNoQ',
                name: 'termNo',
                fieldLabel: '终端ID',
                width:150
            }),
            {
        	xtype:'textfield',
        	fieldLabel: '收单方终端号',
        	id:'acequipmentIdQ',
        	name:'acequipmentIdQ',
        	width:300
        },
        {
            	xtype: 'combo',
                fieldLabel: '终端类型',
                id: 'termTpId',
                hiddenName: 'termTp',
                width:150,
                store: termTypeStore
            },{
            	xtype:'textfield',
            	fieldLabel: '收单方商户号',
            	id:'acmchntIdQ',
            	name:'acmchntIdQ',
            	width:300
            },{
				id: 'mchnNoQ',
                displayField: 'displayField',
                valueField: 'valueField',
            	xtype: 'dynamicCombo',
                fieldLabel: '商户号',
                methodName: 'getMchtCdInTemp',
                hiddenName: 'mchtCd',
                editable : true,
                width: 430
            },{
            	xtype: 'basecomboselect',
		        baseParams: 'MCHT_FALG1',
				fieldLabel: '商户性质1',
				id: 'idMchtFlag1',
				hiddenName: 'mchtFlag1',
				width: 380
			},{
				xtype: 'combo',
		        store:flag2Store,
				fieldLabel: '商户性质2',
				id: 'idMchtFlag2',
				hiddenName: 'mchtFlag2',
				editable:false,
				width: 380
			},{
				xtype: 'basecomboselect',
	        	baseParams: 'MCHNT_GRP_ALL',
				fieldLabel: '商户组别',
				hiddenName: 'mchtGrp',
				width: 380
			},{
				xtype: 'basecomboselect',
				baseParams: 'CONN_TYPE',
				fieldLabel: '商户接入方式',
				id: 'connType',
				name: 'connType',
				allowBlank: true,
				width: 380
			},{
				xtype: 'combo',
                fieldLabel: '第三方服务机构',
                width:250,
                store: organStore,
                id: 'orgIdId',
                hiddenName: 'organId'
			},{
                xtype: 'basecomboselect',
                fieldLabel: '终端状态',
                baseParams:'TERM_STATUS',
                id: 'state',
                name: 'state'
            },{
            	width: 150,
                xtype: 'datefield',
                fieldLabel: '装机起始时间',
                format : 'Y-m-d',
                name :'startTimeUse',
                id :'startTimeUse',
                anchor :'60%'       
            },{
                width: 150,
                xtype: 'datefield',
                fieldLabel: '装机截止时间',
                format : 'Y-m-d',
                name :'endTimeUse',
                id :'endTimeUse',
                anchor :'60%'  
            },{
                width: 150,
                xtype: 'datefield',
                fieldLabel: '入网起始时间',
                format : 'Y-m-d',
                name :'startTime',
                id :'startTime',
                anchor :'60%'       
            },{
                width: 150,
                xtype: 'datefield',
                fieldLabel: '入网截止时间',
                format : 'Y-m-d',
                name :'endTime',
                id :'endTime',
                anchor :'60%'       
        }],
        buttons: [{
            text: '查询',
            handler: function() 
            {
            	var endtimeUse=Ext.getCmp('endTimeUse').getValue(),starttimeUse=Ext.getCmp('startTimeUse').getValue();
            	if(endtimeUse!=''&&starttimeUse!=''&&endtimeUse<starttimeUse){
            		showErrorMsg("请保证截止时间不小于起始时间",grid);
    				return;
            	}
            	
            	var endtime=Ext.getCmp('endTime').getValue(),starttime=Ext.getCmp('startTime').getValue();
            	if(endtime!=''&&starttime!=''&&endtime<starttime){
            		showErrorMsg("请保证截止时间不小于起始时间",grid);
    				return;
            	}
            	termStore.load();
//            	termStoreQuery.load();  
//              grid.getStore().load();  //grid加载的store为[termStore]
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
    
    var qryMenu = {
        text: '详细信息',
        width: 85,
        iconCls: 'edit',
        disabled: true,
        handler:function() {
            var selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            } 
            selectTermInfo(selectedRecord.get('termId'),selectedRecord.get('mchtCd'));	
        }
    };
    var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
	var excelExport = {
			text: '导出报表',
			width: 85,
			id:'download',
//			renderTo: 'report',
			iconCls: 'download',
//			items: [{
//				xtype: 'panel',
//				html: '<br/><br/>'
//			}],
			handler:function() {
				excelDown.show();
			}
	};
	
	var excelQueryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
		autoHeight: true,
//		renderTo: 'report',
		iconCls: 'T50902',
//		waitMsgTarget: true,
//		items: [{
//			xtype: 'panel',
//			html: '<br/><br/>'
//		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确认导出',
			iconCls: 'download',
			handler: function() {
				if(!excelQueryForm.getForm().isValid()) {
					return;
				}
				excelQueryForm.getForm().submit({
					url: 'T30104Action_download.asp',
					
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					//showAlertMsg(action.result.msg,grid);
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																		action.result.msg+'&key=exl20exl';
						excelDown.hide();
					},
					failure: function(form,action) {
						Ext.MessageBox.show({
							msg: '下载失败！',
							title: '错误提示',
							animEl: Ext.getBody(),
							buttons: Ext.MessageBox.OK,
							icon: Ext.MessageBox.ERROR,
							modal: true,
							width: 250
						});
						excelDown.hide();
					},
					params: {
						txnId: '30104',
						subTxnId: '03'
					}
				});
			}
		},{
			text: '取消导出',
			iconCls: 'refuse',
			handler: function() {
				excelDown.hide();
			}
		}]
	});
	var excelDown = new Ext.Window({
		title: '终端信息',
		layout: 'fit',
		width: 350,
		autoHeight: true,
		items: [excelQueryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				excelDown.collapse();
				excelDown.getEl().pause(1);
				excelDown.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				excelDown.expand();
				excelDown.center();
			},
			qtip: '恢复',
			hidden: true
		}]
	});
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 550,
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
    menuArr.push(queryMenu); 
    menuArr.push(excelExport); 
    
    
    // 终端信息列表
    var grid = new Ext.grid.GridPanel({
        title: 'POS终端查询',
        iconCls: 'T301',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
//      autoHeight: true,
        region:'center',
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

//    var mainPanel = new Ext.Panel({
//        title: '终端信息查询',
//        frame: true,
//        borde: true,
//        autoHeight: true,
//        renderTo: Ext.getBody(),
//        items: [grid]
//    });
    var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
    
});