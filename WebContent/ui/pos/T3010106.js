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
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        items: [new Ext.form.TextField({
                id: 'termIdQ',
                name: 'termId',
                fieldLabel: '终端号'
            }),{
                xtype: 'dynamicCombo',
                fieldLabel: '商户号',
                store: mchntStore,
                hiddenName: 'mchtCd',
                id: 'mchtCdQ',
                displayField: 'displayField',
                valueField: 'valueField',
                width: 200
           },{                                         
                xtype: 'basecomboselect',
                fieldLabel: '终端状态',
                baseParams:'TERM_STATUS',
                id: 'termStaQ',
                name: 'termSta'
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
            	termStoreQuery.load();
                grid.getStore().load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
    
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termInfoBaseAll'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'mchtCd',mapping: 'mchtCd'},
			{name: 'termSta',mapping: 'termSta'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'termIdId',mapping: 'termIdId'},
			{name: 'termFactory',mapping: 'termFactory'},
			{name: 'termMachTp',mapping: 'termMachTp'},
			{name: 'termVer',mapping: 'termVer'},
			{name: 'termTp',mapping: 'termTp'},
			{name: 'termBranch',mapping: 'termBranch'},
			{name: 'termIns',mapping: 'termIns'},
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	
	termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termIdQ').getValue(),
            termSta: Ext.getCmp('termStaQ').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
            mchtCd: Ext.getCmp('mchtCdQ').getValue()
        });
    });
	termStore.load();
	var termColModel = new Ext.grid.ColumnModel([
		{id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
		{id:'mchtCd',header: '商户号',dataIndex: 'mchtCd',width: 150,renderer:function(val){return getRemoteTrans(val, "mchntName");}},
		{header: '终端状态',dataIndex: 'termSta',width: 150,renderer: termSta},
		{header: '终端所属机构',dataIndex: 'termBranch',width:200,renderer:function(val){return getRemoteTrans(val, "brhName");}},
		{header: '终端库存编号',dataIndex: 'termIdId'},
		{header: '终端厂商',dataIndex: 'termFactory',width: 150},
		{header: '终端型号',dataIndex: 'termMachTp',width: 150}
	]);
	
	var termStoreQuery = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termInfoRealByCondition'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'mchtCd',mapping: 'mchtCd'},
			{name: 'termSta',mapping: 'termSta'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'termIdId',mapping: 'termIdId'},
			{name: 'termFactory',mapping: 'termFactory'},
			{name: 'termMachTp',mapping: 'termMachTp'},
			{name: 'termVer',mapping: 'termVer'},
			{name: 'termTp',mapping: 'termTp'},
			{name: 'termBranch',mapping: 'termBranch'},
			{name: 'termIns',mapping: 'termIns'},
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			termWin.show();
			termWin.center();
		}
	};
	var termInfoStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'termIdUpd',mapping: 'id.termId'},
            {name: 'mchnNoU',mapping: 'id.mchtCd'},
            {name: 'termStaU',mapping: 'termSta'},
            {name: 'termMccUpd',mapping: 'termMcc'},
            {name: 'termBranchUpd',mapping: 'termBranch'},
            {name: 'termSignStaU',mapping: 'termSignSta'},
            {name: 'termMachTpUpd',mapping: 'termMachTp'},
            {name: 'termIdIdU',mapping: 'termIdId'},
            {name: 'termVerUpd',mapping: 'termVer'},
            {name: 'termTpU',mapping: 'termTp'},
            {name: 'contTelUpd',mapping: 'contTel'},
            {name: 'propTpU',mapping: 'propTp'},
            {name: 'termTxnSupU',mapping: 'termTxnSup'},
            {name: 'propInsNmUpd',mapping: 'propInsNm'},
            {name: 'termBatchNmUpd',mapping: 'termBatchNm'},
            {name: 'termStlmDtUpd',mapping: 'termStlmDt'},
            {name: 'connectModeU',mapping: 'connectMode'},
            {name: 'bindTel1Upd',mapping: 'bindTel1'},
            {name: 'bindTel2Upd',mapping: 'bindTel2'},
            {name: 'bindTel3Upd',mapping: 'bindTel3'},
            {name: 'termAddrUpd',mapping: 'termAddr'},
            {name: 'termPlaceUpd',mapping: 'termPlace'},
            {name: 'propInsRateUpd',mapping: 'propInsRate'},
            {name: 'oprNmUpd',mapping: 'oprNm'},
            {name: 'termParaUpd',mapping: 'termPara'},
            {name: 'termPara1Upd',mapping: 'termPara1'},
            {name: 'keyDownSignUpd',mapping: 'keyDownSign'},
            {name: 'paramDownSignUpd',mapping: 'paramDownSign'},
            {name: 'icDownSignUpd',mapping: 'icDownSign'},
            {name: 'reserveFlag1Upd',mapping: 'reserveFlag1'},
            {name: 'cardTypeUpd',mapping: 'cardType'},
            {name: 'leaseFeeUpd',mapping: 'leaseFee'},
            {name: 'depositFlagUpd',mapping: 'depositFlag'},
            {name: 'depositStateUpd',mapping: 'depositState'},
            {name: 'depositAmtUpd',mapping: 'depositAmt'},
            {name: 'checkCardNoUpd',mapping: 'checkCardNo'},
            {name: 'termSingleLimitUpd',mapping: 'termSingleLimit'},
            {name: 'rentFeeUpd',mapping: 'rentFee'}
        ]),
        autoLoad: false
    });
	function parseTxnSup(val){
		val = val.trim();
		for(var i=1,len=val.length;i<=len;i++){
			Ext.getCmp('txnSupUpd'+i).setValue(val.charAt(i-1));
		}
	}
    
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
                     storeId: 'getTermInfo',
                     termId: selectedRecord.get('termId'),
                     mchtCd: selectedRecord.get('mchtCd')
                },
                callback: function(records, options, success){
                
                    if(success){
//                    	 alert(2);
                       updTermForm.getForm().loadRecord(termInfoStore.getAt(0));
                       var propTp = Ext.getCmp('propTpU').getValue();
                   	   var mcc = Ext.getCmp('termMccUpd').getValue();
                   	   //termSingleLimit是char类型的 去空格
                   	   Ext.getCmp('termSingleLimitUpd').setValue(Ext.getCmp('termSingleLimitUpd').getValue().trim());
                       if(propTp == 1)
                       {
                       	Ext.getCmp("propInsRateUpd").enable();
                        Ext.getCmp("propInsNmU").enable();
                       }
                       else
                       {
                           Ext.getCmp("propInsRateUpd").disable();
                           Ext.getCmp("propInsNmU").disable();
                           Ext.getCmp("propInsRateUpd").setValue("");
                           Ext.getCmp("propInsNmU").setValue("");
                       }
                      
                       //处理所支持的交易选框
                       var termTxnSup = termInfoStore.getAt(0).data.termTxnSupU;
                       parseTxnSup(termTxnSup);
                       
                       updTermWin.show();
                    }else{
                    
                       updTermWin.hide();
                    }
                    updTermPanel.setActiveTab('info1Upd');
                }
            });
		}
	};
	var stopMenu={
	        text: '冻结',
	        width: 85,
	        iconCls: 'stop',                                     
	        disabled: true,
	        handler:function() {
	            selectedRecord = grid.getSelectionModel().getSelected();
	            if(selectedRecord == null)
	            {
	                showAlertMsg("没有选择记录",grid);
	                return;
	            }  
	            showConfirm('确定要冻结该终端吗？',grid,function(bt) {
	                if(bt == 'yes') {
//	                    showProcessMsg('正在提交信息，请稍后......');
	                    rec = grid.getSelectionModel().getSelected();
	                     Ext.Ajax.requestNeedAuthorise({
	                        url: 'T3010104Action.asp?method=stop',
	                        params: {
	                            termId: selectedRecord.get('termId'),
	                            mchtCd: selectedRecord.get('mchtCd'),
	                            termSta:selectedRecord.get('termSta'),
	                            txnId: '30101',
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
	    }
	var recoverMenu={
	        text: '解冻',
	        width: 85,
	        iconCls: 'recover',                                     
	        disabled: true,
	        handler:function() {
	            selectedRecord = grid.getSelectionModel().getSelected();
	            if(selectedRecord == null)
	            {
	                showAlertMsg("没有选择记录",grid);
	                return;
	            }  
	            showConfirm('确定要解冻该终端吗？',grid,function(bt) {
	                if(bt == 'yes') {
//	                    showProcessMsg('正在提交信息，请稍后......');
	                    rec = grid.getSelectionModel().getSelected();
	                     Ext.Ajax.requestNeedAuthorise({
	                        url: 'T3010104Action.asp?method=recover',
	                        params: {
	                            termId: selectedRecord.get('termId'),
	                            mchtCd: selectedRecord.get('mchtCd'),
	                            termSta:selectedRecord.get('termSta'),
	                            txnId: '30101',
	                            subTxnId: '04'
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
	    }
    var closeMenu = {
	        text: '注销',
	        width: 85,
	        iconCls: 'stop',                                     
	        disabled: true,
	        handler:function() {
	            selectedRecord = grid.getSelectionModel().getSelected();
	            if(selectedRecord == null)
	            {
	                showAlertMsg("没有选择记录",grid);
	                return;
	            }  
	            showConfirm('确定要注销该终端吗？',grid,function(bt) {
	                if(bt == 'yes') {
//	                    showProcessMsg('正在提交信息，请稍后......');
	                    rec = grid.getSelectionModel().getSelected();
	                     Ext.Ajax.requestNeedAuthorise({
	                        url: 'T3010104Action.asp?method=close',
	                        params: {
	                    	 termId: selectedRecord.get('termId'),
	                            mchtCd: selectedRecord.get('mchtCd'),
	                            termSta:selectedRecord.get('termSta'),
	                            txnId: '30101',
	                            subTxnId: '05'
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
	
//	var delMenu = {//不提供删除
//	        text: '删除',
//	        width: 85,
//	        iconCls: 'delete',                                     
//	        disabled: true,
//	        handler:function() {
//	            selectedRecord = grid.getSelectionModel().getSelected();
//	            if(selectedRecord == null)
//	            {
//	                showAlertMsg("没有选择记录",grid);
//	                return;
//	            }  
//	            showConfirm('确定要删除该终端吗？',grid,function(bt) {
//	                if(bt == 'yes') {
////	                    showProcessMsg('正在提交信息，请稍后......');
//	                    rec = grid.getSelectionModel().getSelected();
//	                     Ext.Ajax.requestNeedAuthorise({
//	                        url: 'T3010104Action.asp?method=delete',
//	                        params: {
//	                    	 termId: selectedRecord.get('termId'),
//	                            mchtCd: selectedRecord.get('mchtCd'),
//	                            termSta:selectedRecord.get('termSta'),
//	                            txnId: '30101',
//	                            subTxnId: '06'
//	                        },
//	                        success: function(rsp,opt) {
//	                            var rspObj = Ext.decode(rsp.responseText);
//	                            termStore.reload();
//	                            if(rspObj.success) {
//	                                showSuccessMsg(rspObj.msg,grid);
//	                            } else {
//	                                showErrorMsg(rspObj.msg,grid);
//	                            }
//	                        }
//	                    });
//	                    grid.getTopToolbar().items.items[3].disable();
//	                    hideProcessMsg();
//	                }
//	            });
//	        }
//	    };
	
	var detailMenu = {
	        text: '详细信息',
	        width: 85,
	        iconCls: 'detail',
	        disabled: true,
	        handler:function() {
	            var rec = grid.getSelectionModel().getSelected();
	            if(rec == null)
	            {
	                showAlertMsg("没有选择记录",grid);
	                return;
	            }
	            selectTermInfo(rec.get('termId'),rec.get('mchtCd'));	
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
	
	menuArr.push(addMenu);		//[0]
	menuArr.push(queryMenu);	//[1]
	menuArr.push(editMenu);		//[2]
    menuArr.push(stopMenu);     //[3]
    menuArr.push(recoverMenu);  //[4]
    menuArr.push(closeMenu);    //[5]
//    menuArr.push(delMenu);      //[6]不提供删除
    menuArr.push(detailMenu);   //[6]
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '终端信息维护',
		iconCls: 'T301',
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
			//0：新增待审核 1：待装机 2 ：已装机 3：修改待审核4：冻结 
			//5：冻结待审核 6:解冻待审核7：注销8：注销待审核 9：撤机 A:新增退回
			var sta=rec.get('termSta');
			grid.getTopToolbar().items.items[6].enable();
			if(sta=='0'||sta=='1'||sta=='2'||sta=='3'||sta=='A'){
				grid.getTopToolbar().items.items[2].enable();
			}else{
				grid.getTopToolbar().items.items[2].disable();
			}
			if(sta=='1'||sta=='2'){
				grid.getTopToolbar().items.items[3].enable();
				grid.getTopToolbar().items.items[5].enable();
			}else{
				grid.getTopToolbar().items.items[3].disable();
				if(sta=='4'){
					grid.getTopToolbar().items.items[5].enable();
				}else{
					grid.getTopToolbar().items.items[5].disable();
				}
			}
			
//			if(sta=='7'||sta=='A'){
//				grid.getTopToolbar().items.items[6].enable();
//			}else{
//				grid.getTopToolbar().items.items[6].disable();
//			}
			
			if(sta=='4'){
				grid.getTopToolbar().items.items[4].enable();
			}else{
				grid.getTopToolbar().items.items[4].disable();
			}
			
		},
		'beforeload':function(){
			grid.getTopToolbar().items.items[2].disable();
			grid.getTopToolbar().items.items[3].disable();
			grid.getTopToolbar().items.items[4].disable();
			grid.getTopToolbar().items.items[5].disable();
			grid.getTopToolbar().items.items[6].disable();
//			grid.getTopToolbar().items.items[7].disable();
		}
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
                 columnWidth: .8,
                 layout: 'form',
                 items: [{
                    xtype: 'combo',
                    fieldLabel: '商户号*',
                    store: mchntStore,
                    hiddenName: 'mchnNoNew',
                    id: 'mchnNoN',
                    displayField: 'displayField',
                    valueField: 'valueField',
                    blankText: '商户号不能为空',
                    allowBlank: false,
                    emptyText: '请选择商户号',
                    width: 300,
                    listeners: {
                     'select': function() {
                	 		var mid = Ext.getCmp("mchnNoN").getValue();
                            T30101.getMchnt(mid,function(ret){
                                var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
                                Ext.getCmp("termMccNew").setValue(mchntInfo.mcc);
                                Ext.getCmp("termBranchNew").setValue(mchntInfo.signInstId);
                                
                            });
                            //处理支持的交易，把mcc支持的交易置于选中状态
                            T30101.getTxnSup(mid,function(data){
//                            	data = Ext.decode(data);
                            	for(var i=1;i<=19;i++){
                            		termForm.getForm().findField("txnSupNew"+i).setValue('0');//状态//先清空全部置于未选中
                            	}
                            	for(var i=0,len=data.length;i<len;i++){
                            		var filedName="txnSupNew"+data[i];
                            		termForm.getForm().findField(filedName).setValue('1');//将mcc支持的交易打钩
                            	}
                            });
                            //去商户的连接类型(商户类型)，并把终端的连接类型设为同商户一样、只读，方便以后的报表查询
                            T30101.getMchtConnType(mid,function(data){
                            	termForm.getForm().findField("connectModeNew").setValue(data);
                            });
                        }
                    }
                  }]
             },{
                columnWidth: .5,
                layout: 'form',
                items:[{
                   xtype: 'combo',
                   fieldLabel: '终端所属分行*',
                   id: 'termBranchNew',
                   hiddenName: 'brhIdNew',
                   width:150,
                   store: brhStore,
                   displayField: 'displayField',
                   valueField: 'valueField',
                   mode: 'local',
                   allowBlank: false,
                   readOnly:true,
                   blankText: '终端所属分行不能为空',
                   listeners:{
                       'select': function() {
                       }
                   }
                }]
           },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '终端MCC码',
                        width:150,
                        id: 'termMccNew',
                        name: 'termMccNew',
                        readOnly: true
                    }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '产权属性*',
                    id: 'propTpN',
                    allowBlank: false,
                    width:150,
                    hiddenName: 'propTpNew',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','我行产权'],['1','第三方投入']]
                    }),
                    listeners:{
                        'select': function() {
	                        var args = Ext.getCmp('propTpN').getValue();
	                        if(args == 1)
	                        {
	                        	Ext.getCmp("propInsRateNew").enable();
	                            Ext.getCmp("propInsNmN").enable();
	                        }
                            else
                            {
                            	Ext.getCmp("propInsRateNew").reset();
                                Ext.getCmp("propInsNmN").reset();
                                Ext.getCmp("propInsRateNew").disable();
                                Ext.getCmp("propInsNmN").disable();
                            }
                        }
                   }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '连接类型*',
                    width:150,
                    id: 'connectModeN',
                    hiddenName: 'connectModeNew',
                    width:160,
                    readOnly:true,
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['J','间联'],['Z','直联']]
                    })
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '第三方服务机构*',
                    disabled:true,
                    width:150,
                    store: organStore,
                    id: 'propInsNmN',
                    allowBlank: false,
                    hiddenName: 'propInsNmNew'
                }]
            },{
                columnWidth: .5,
                hidden: false,
                layout: 'form',
                items: [{
                    xtype: 'numberfield',
                    fieldLabel: '分成比例(%)*',
                    width:150,
                    disabled:true,
                    maxLength:2,
                    allowBlank: false,
                    id: 'propInsRateNew',
                    name: 'propInsRateNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'combo',
	                    fieldLabel: '终端类型*',
	                    id: 'termTpN',
	                    allowBlank: false,
	                    hiddenName: 'termTpNew',
	                    width:150,
	                    store: termTypeStore
                    }]
           },{
               columnWidth: .5,
               layout: 'form',
               items: [{
                       xtype: 'textfield',
                       fieldLabel: '终端单笔限额*',
                       width:150,
                       allowBlank:false,
                       vtype: 'isMoney',
                       maxLength: 12,
                       id: 'termSingleLimitNew',
                       name: 'termSingleLimitNew'
                   }]
           },{
               columnWidth: .5,
               layout: 'form',
               items: [{
                   xtype: 'textfield',
                   fieldLabel: '终端安装地址',
                   maxLength: 200,
                   width:150,
                   id: 'termAddrNew',
                   name: 'termAddrNew'
               }]
           },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话1*',
                    maxLength: 15,
                    allowBlank: false,
                    width:150,
                    vtype: 'isNumber',
                    id: 'bindTel1New',
                    name: 'bindTel1New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话2',
                    maxLength: 15,
//                    allowBlank: false,
                    width:150,
                    vtype: 'isNumber',
                    id: 'bindTel2New',
                    name: 'bindTel2New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话3',
                    maxLength: 15,
//                    allowBlank: false,
                    width:150,
                    vtype: 'isNumber',
                    id: 'bindTel3New',
                    name: 'bindTel3New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1New',
                    name: 'reserveFlag1New',
                    width:150,
                    inputValue: '1'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '是否收取押金',
                    id: 'depositFlagNew',
                    width:150,
                    name: 'depositFlagNew',
                    inputValue: '1'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '押金金额',
                        width:150,
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'depositAmtNew',
                        name: 'depositAmtNew'
                    }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '押金收取状态',
                    id: 'depositStateN',
                    width:150,
                    hiddenName: 'depositStateNew',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','已收'],['1','未收'],['2','退回']]
                    })
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '终端租赁费用(天/元)',
                        vtype: 'isMoney',
                        maxLength: 12,
                        width:150,
                        id: 'leaseFeeNew',
                        name: 'leaseFeeNew'
                    }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '单月限额(元)',
                        vtype: 'isMoney',
                        maxLength: 12,
                        width:150,
                        id: 'rentFeeNew',
                        name: 'rentFeeNew'
                    }]
            }]
            },{
                title: '交易信息',
                id: 'info3New',
                layout: 'column',
                frame: true,
                items: [{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '预授权',
                            id: 'txnSupNew1',
                            name: 'txnSupNew1',
                            inputValue: '1'
                        }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '预授权撤销',
                            id: 'txnSupNew2',
                            name: 'txnSupNew2',
                            inputValue: '1'
                        }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '预授权完成',
                            id: 'txnSupNew3',
                            name: 'txnSupNew3',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '预授权完成撤销',
                            id: 'txnSupNew4',
                            name: 'txnSupNew4',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '消费',
                            id: 'txnSupNew5',
                            name: 'txnSupNew5',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '消费撤销',
                            id: 'txnSupNew6',
                            name: 'txnSupNew6',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '余额查询',
                            id: 'txnSupNew7',
                            name: 'txnSupNew7',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '存款',
                            id: 'txnSupNew8',
                            name: 'txnSupNew8',
                            
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '存款撤销',
                            id: 'txnSupNew9',
                            name: 'txnSupNew9',
                            
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '取款',
                            id: 'txnSupNew10',
                            name: 'txnSupNew10',
                           
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '取款撤销',
                            id: 'txnSupNew11',
                            name: 'txnSupNew11',
                           
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '分期消费',
                            id: 'txnSupNew12',
                            name: 'txnSupNew12',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '分期消费撤销',
                            id: 'txnSupNew13',
                            name: 'txnSupNew13',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '转账',
                            id: 'txnSupNew14',
                            name: 'txnSupNew14',
                           
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '转账撤销',
                            id: 'txnSupNew15',
                            name: 'txnSupNew15',
                            
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '快速支付',
                            id: 'txnSupNew16',
                            name: 'txnSupNew16',
                            inputValue: '1'
                         }]
                    },{
                        columnWidth: .5,
                        layout: 'form',
                        items: [{
                                xtype: 'checkbox',
                                fieldLabel: '脚本处理结果通知',
                                id: 'txnSupNew17',
                                name: 'txnSupNew17',
                                inputValue: '1'
                             }]
                        },{
                            columnWidth: .5,
                            layout: 'form',
                            items: [{
                                    xtype: 'checkbox',
                                    fieldLabel: '无磁无密消费',
                                    id: 'txnSupNew18',
                                    name: 'txnSupNew18',
                                    inputValue: '1'
                                 }]
                            },{
                                columnWidth: .5,
                                layout: 'form',
                                items: [{
                                        xtype: 'checkbox',
                                        fieldLabel: '无磁无密消费撤销',
                                        id: 'txnSupNew19',
                                        name: 'txnSupNew19',
                                        inputValue: '1'
                                     }]
                                }]
            }]
    });
    /**************  终端表单  *********************/
	var termForm = new Ext.form.FormPanel({
		frame: true,
		height: 350,
		width: 750,
		labelWidth: 140,
		waitMsgTarget: true,
		layout: 'column',
		items: [termPanel]
	});
   
    /***********  终端信息窗口  *****************/
	var termWin = new Ext.Window({
		title: '终端添加',
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
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
//				termPanel.setActiveTab("info3New");
//				termPanel.setActiveTab("info2New"); 
				if(termForm.getForm().isValid()) {
					termForm.getForm().submitNeedAuthorise({
						url: 'T30101Action.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessDtl(action.result.msg,termWin);
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							termForm.getForm().reset();
                            
                            termWin.hide();
						},
						failure: function(form,action) {
							termPanel.setActiveTab('info1New');
							showErrorMsg(action.result.msg,termWin);
						},
						params: {
							txnId: '30101',
							subTxnId: '01'
						}
					});
//				}

			}else{
				termPanel.setActiveTab('info1New');
				termForm.getForm().isValid();
			}
		}
		},{
			text: '重置',
			handler: function() {
					termForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				termWin.hide();
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
                 columnWidth: .8,
                 layout: 'form',
                 items: [{
                    xtype: 'combo',
                    fieldLabel: '商户号*',
                    store: mchntStore,
                    hiddenName: 'mchnNoUpd',
                    id: 'mchnNoU',
                    displayField: 'displayField',
                    valueField: 'valueField',
                    blankText: '商户号不能为空',
                    allowBlank: false,
                    readOnly:true,
                    emptyText: '请选择商户号',
                    width: 300
                  }]
             },{
                columnWidth: .5,
                layout: 'form',
                items:[{
                   xtype: 'combo',
                   fieldLabel: '终端所属分行*',
                   id: 'termBranchUpd',
                   hiddenName: 'brhIdUpd',
                   width:150,
                   store: brhStore,
                   displayField: 'displayField',
                   valueField: 'valueField',
                   mode: 'local',
                   allowBlank: false,
                   readOnly:true,
                   blankText: '终端所属分行不能为空'
                }]
           },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '终端MCC码',
                        width:150,
                        id: 'termMccUpd',
                        name: 'termMccUpd',
                        readOnly: true
                    }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '产权属性*',
                    id: 'propTpU',
                    allowBlank: false,
                    width:150,
                    hiddenName: 'propTpUpd',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','我行产权'],['1','第三方投入']]
                    }),
                    listeners:{
                        'select': function() {
	                        var args = Ext.getCmp('propTpU').getValue();
	                        if(args == 1)
	                        {
	                        	Ext.getCmp("propInsRateUpd").enable();
	                            Ext.getCmp("propInsNmU").enable();
	                        }
                            else
                            {
                            	
                                Ext.getCmp("propInsRateUpd").disable();
                                Ext.getCmp("propInsNmU").disable();
                                Ext.getCmp("propInsRateUpd").setValue("");
                                Ext.getCmp("propInsNmU").setValue("");
                            }
                        }
                   }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '连接类型*',
                    width:150,
                    id: 'connectModeU',
                    hiddenName: 'connectModeUpd',
                    width:160,
                    readOnly:true,
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['J','间联'],['Z','直联']]
                    })
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '第三方服务机构*',
                    disabled:true,
                    width:150,
                    store: organStore,
                    id: 'propInsNmU',
                    allowBlank: false,
                    hiddenName: 'propInsNmUpd'
                }]
            },{
                columnWidth: .5,
                hidden: false,
                layout: 'form',
                items: [{
                    xtype: 'numberfield',
                    fieldLabel: '分成比例(%)*',
                    width:150,
                    disabled:true,
                    maxLength:2,
                    allowBlank: false,
                    id: 'propInsRateUpd',
                    name: 'propInsRateUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'combo',
	                    fieldLabel: '终端类型*',
	                    id: 'termTpU',
	                    allowBlank: false,
	                    hiddenName: 'termTpUpd',
	                    width:150,
	                    store: termTypeStore
                    }]
           },{
               columnWidth: .5,
               layout: 'form',
               items: [{
                       xtype: 'textfield',
                       fieldLabel: '终端单笔限额*',
                       width:150,
                       allowBlank:false,
                       vtype: 'isMoney',
                       maxLength: 12,
                       id: 'termSingleLimitUpd',
                       name: 'termSingleLimitUpd'
                   }]
           },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话1*',
                    maxLength: 15,
                    allowBlank: false,
                    width:150,
                    vtype: 'isNumber',
                    id: 'bindTel1Upd',
                    name: 'bindTel1Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话2',
                    maxLength: 15,
                   
                    width:150,
                    vtype: 'isNumber',
                    id: 'bindTel2Upd',
                    name: 'bindTel2Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话3',
                    maxLength: 15,
                    
                    width:150,
                    vtype: 'isNumber',
                    id: 'bindTel3Upd',
                    name: 'bindTel3Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1Upd',
                    name: 'reserveFlag1Upd',
                    width:150,
                    inputValue: '1'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '是否收取押金',
                    id: 'depositFlagUpd',
                    width:150,
                    name: 'depositFlagUpd',
                    inputValue: '1'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '押金金额',
                        width:150,
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'depositAmtUpd',
                        name: 'depositAmtUpd'
                    }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '押金收取状态',
                    id: 'depositStateU',
                    width:150,
                    hiddenName: 'depositStateUpd',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','已收'],['1','未收'],['2','退回']]
                    })
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '终端租凭费用(天)',
                        vtype: 'isMoney',
                        maxLength: 12,
                        width:150,
                        id: 'leaseFeeUpd',
                        name: 'leaseFeeUpd'
                    }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '单月限额(元)',
                        vtype: 'isMoney',
                        maxLength: 12,
                        width:150,
                        id: 'rentFeeUpd',
                        name: 'rentFeeUpd'
                    }]
            }]
            },{
                title: '交易信息',
                id: 'info3Upd',
                layout: 'column',
                frame: true,
                items: [{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '预授权',
                            id: 'txnSupUpd1',
                            name: 'txnSupUpd1',
                            inputValue: '1'
                        }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '预授权撤销',
                            id: 'txnSupUpd2',
                            name: 'txnSupUpd2',
                            inputValue: '1'
                        }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '预授权完成',
                            id: 'txnSupUpd3',
                            name: 'txnSupUpd3',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '预授权完成',
                            id: 'txnSupUpd4',
                            name: 'txnSupUpd4',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '消费',
                            id: 'txnSupUpd5',
                            name: 'txnSupUpd5',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '消费撤销',
                            id: 'txnSupUpd6',
                            name: 'txnSupUpd6',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '余额查询',
                            id: 'txnSupUpd7',
                            name: 'txnSupUpd7',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '存款',
                            id: 'txnSupUpd8',
                            name: 'txnSupUpd8',
                            
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '存款撤销',
                            id: 'txnSupUpd9',
                            name: 'txnSupUpd9',
                           
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '取款',
                            id: 'txnSupUpd10',
                            name: 'txnSupUpd10',
                            
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '取款撤销',
                            id: 'txnSupUpd11',
                            name: 'txnSupUpd11',
                            
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '分期消费',
                            id: 'txnSupUpd12',
                            name: 'txnSupUpd12',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '分期消费撤销',
                            id: 'txnSupUpd13',
                            name: 'txnSupUpd13',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '转账',
                            id: 'txnSupUpd14',
                            name: 'txnSupUpd14',
                            
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    hidden:true,//暂时去掉存取款和转账相关交易
                    disable:true,
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '转账撤销',
                            id: 'txnSupUpd15',
                            name: 'txnSupUpd15',
                           
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '快速支付',
                            id: 'txnSupUpd16',
                            name: 'txnSupUpd16',
                            inputValue: '1'
                         }]
                    },{
                        columnWidth: .5,
                        layout: 'form',
                        items: [{
                                xtype: 'checkbox',
                                fieldLabel: '脚本处理结果通知',
                                id: 'txnSupUpd17',
                                name: 'txnSupUpd17',
                                inputValue: '1'
                             }]
                        },{
                            columnWidth: .5,
                            layout: 'form',
                            items: [{
                                    xtype: 'checkbox',
                                    fieldLabel: '无磁无密消费',
                                    id: 'txnSupUpd18',
                                    name: 'txnSupUpd18',
                                    inputValue: '1'
                                 }]
                            },{
                                columnWidth: .5,
                                layout: 'form',
                                items: [{
                                        xtype: 'checkbox',
                                        fieldLabel: '无磁无密消费撤销',
                                        id: 'txnSupUpd19',
                                        name: 'txnSupUpd19',
                                        inputValue: '1'
                                     }]
                                }]
            }]
    });
/*******************  终端修改表单  *********************/
    var updTermForm = new Ext.form.FormPanel({
        frame: true,
        height: 350,
        width: 750,
        labelWidth: 140,
        waitMsgTarget: true,
        layout: 'column',
        items: [updTermPanel]
    });
   
/*******************  终端修改信息 *********************/
    var updTermWin = new Ext.Window({
        title: '终端修改',
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
        iconCls: 'logo',
        resizable: false,
        buttons: [{
            text: '确定',
            handler: function() {

                	if(updTermForm.getForm().isValid()){
                    updTermForm.getForm().submitNeedAuthorise({
                        url: 'T3010102Action.asp',
                        waitMsg: '正在提交，请稍后......',
                        success: function(form,action) {
                            showSuccessMsg(action.result.msg,updTermForm);
                            grid.getStore().reload();
                            updTermForm.getForm().reset();
                            updTermWin.hide();
                            grid.getTopToolbar().items.items[2].disable();
                        },
                        failure: function(form,action) {
                            updTermPanel.setActiveTab('info1Upd');
                            showErrorMsg(action.result.msg,updTermForm);
                        },
                        params: {
                            txnId: '30101',
                            subTxnId: '02',
                            termIdUpd:termInfoStore.getAt(0).data.termIdUpd
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
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});