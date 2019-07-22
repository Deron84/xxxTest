Ext.onReady(function() {
	var selectedRecord ;
	//EPOS版本号
    var eposStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION_NEW','',function(ret){
		eposStore.loadData(Ext.decode(ret));
	});
    // 商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_NO02',function(ret){
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
        width: 570,
        autoHeight: true,
        labelWidth: 100,
        items: [new Ext.form.TextField({
                id: 'termIdQ',
                name: 'termId',
                fieldLabel: '终端ID'
            }),
            {
        	xtype:'textfield',
        	fieldLabel: '终端号',
        	id:'acequipmentIdQ',
        	name:'acequipmentIdQ',
        	width:300
        },
        {
        	xtype:'textfield',
        	fieldLabel: '商户号',
        	id:'acmchntIdQ',
        	name:'acmchntIdQ',
        	width:300
        },{
            	
//                store: mchntStore,
                id: 'mchtCdQ',
                displayField: 'displayField',
                valueField: 'valueField',
            	xtype: 'dynamicCombo',
                fieldLabel: '商户ID',
                methodName: 'getMchtCdInTemp',
                hiddenName: 'mchtCd',
                editable : true,
                width: 420
            
//                xtype: 'dynamicCombo',
//                fieldLabel: '商户号',
//                store: mchntStore,
//                hiddenName: 'mchtCd',
//                id: 'mchtCdQ',
//                displayField: 'displayField',
//                valueField: 'valueField',
//                width: 420
           },{  
        	    xtype: 'basecomboselect',
				baseParams: 'BRH_BELOW',
				fieldLabel: '归属机构',
				id: 'agrBrId',
				hiddenName: 'agrBr',
				width: 420
        },{
        	 xtype: 'basecomboselect',
             fieldLabel: '终端状态',
             baseParams:'TERM_STATUS',
             id: 'termStaQ',
             name: 'termSta'
        },{ 
        	xtype: 'combo',
            fieldLabel: '第三方服务机构',
            store: organStore,
            id: 'propInsNm',
            hiddenName: 'propInsNNew'
        },{
        	xtype: 'basecomboselect',
	        baseParams: 'MCHT_FALG1',
			labelStyle: 'padding-left: 0px',
			fieldLabel:'商户性质1',
			id: 'idMchtFlag1',
			hiddenName: 'mchtFlag1'
		},{
			xtype: 'basecomboselect',
	        baseParams: 'CONN_TYPE',
			labelStyle: 'padding-left: 0px',
			fieldLabel:'商户接入方式',
			id: 'idconnType',
			hiddenName: 'connType'
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
//            	termStoreQuery.load();
//              grid.getStore().load();    //grid加载的store为[termStore]
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
			url: 'gridPanelStoreAction.asp?storeId=termInfoNew'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'mchtCd',mapping: 'mchtCd'},
			{name: 'termSta',mapping: 'termSta'},
			{name: 'mappingMchntcdOne',mapping: 'mappingMchntcdOne'},
			{name: 'mappingTermidOne',mapping: 'mappingTermidOne'},
			{name: 'mappingMchntcdTwo',mapping: 'mappingMchntcdTwo'},
			{name: 'mappingTermidTwo',mapping: 'mappingTermidTwo'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'termIdId',mapping: 'termIdId'},
			{name: 'termFactory',mapping: 'termFactory'},
			{name: 'termMachTp',mapping: 'termMachTp'},
			{name: 'termVer',mapping: 'termVer'},
			{name: 'termTp',mapping: 'termTp'},
			{name: 'termBranch',mapping: 'termBranch'},
			{name: 'termIns',mapping: 'termIns'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'leaseFee',mapping: 'leaseFee'},
			{name: 'rentFee',mapping: 'rentFee'}
		])
	});
	
	
	termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termIdQ').getValue(),
            acmchntId: Ext.getCmp('acmchntIdQ').getValue(),
            acequipmentId: Ext.getCmp('acequipmentIdQ').getValue(),
            termSta: Ext.getCmp('termStaQ').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
            mchtCd: Ext.getCmp('mchtCdQ').getValue(),
            agrBr: Ext.getCmp('agrBrId').getValue(),
            connType: Ext.getCmp('idconnType').getValue(),
            mchtFlag1: Ext.getCmp('idMchtFlag1').getValue(),
            propInsNmNew: Ext.getCmp('propInsNm').getValue()
        });
    }); 
//	termStore.load();
	var termColModel = new Ext.grid.ColumnModel([
		{id: 'termId',header: '终端ID',dataIndex: 'termId',width: 100},
		{id:'mchtCd',header: '商户ID',dataIndex: 'mchtCd',width: 180,renderer:function(val){return getRemoteTrans(val, "mchntName");}},
		{header: '一卡通商户号',dataIndex: 'mappingMchntcdOne',width: 100},
		{header: '一卡通终端号',dataIndex: 'mappingTermidOne',width: 100},
		{header: '银联商户号',dataIndex: 'mappingMchntcdTwo',width: 100},
		{header: '银联终端号',dataIndex: 'mappingTermidTwo',width: 100},
		{header: '终端状态',dataIndex: 'termSta',renderer: termSta},
		{header: '终端所属机构',dataIndex: 'termBranch',width:200,renderer:function(val){return getRemoteTrans(val, "brhName");}},
		{header: '终端库存编号',dataIndex: 'termIdId'},
		{header: '终端厂商',dataIndex: 'termFactory',width: 150},
		{header: '终端型号',dataIndex: 'termMachTp',renderer:function(val){return getRemoteTrans(val, "sysPara")}},
		{header: '终端租赁费用(元/天)',dataIndex: 'leaseFee',width: 150},
		{header: '单月限额(元)',dataIndex: 'rentFee'}
	]);
	

	var termInfoStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'termIdN',mapping: 'id.termId'},
            {name: 'mchnNoN',mapping: 'id.mchtCd'},
            {name: 'mappingMchntcdOneN',mapping: 'mappingMchntcdOne'},
			{name: 'mappingTermidOneN',mapping: 'mappingTermidOne'},
			{name: 'mappingMchntcdTwoN',mapping: 'mappingMchntcdTwo'},
			{name: 'mappingTermidTwoN',mapping: 'mappingTermidTwo'},
            {name: 'termStaN',mapping: 'termSta'},
            {name: 'termMccN',mapping: 'termMcc'},
            {name: 'termBranchN',mapping: 'termBranch'},
            {name: 'termSignStaN',mapping: 'termSignSta'},
            {name: 'termMachTpN',mapping: 'termMachTp'},
            {name: 'termIdIdN',mapping: 'termIdId'},
            {name: 'termVerN',mapping: 'termVer'},
            {name: 'termTpN',mapping: 'termTp'},
            {name: 'contTelN',mapping: 'contTel'},
            {name: 'propTpN',mapping: 'propTp'},
            {name: 'termTxnSupN',mapping: 'termTxnSup'},
            {name: 'propInsNmN',mapping: 'propInsNm'},
            {name: 'termBatchNmN',mapping: 'termBatchNm'},
            {name: 'termStlmDtN',mapping: 'termStlmDt'},
            {name: 'connectModeN',mapping: 'connectMode'},
            {name: 'bindTel1N',mapping: 'bindTel1'},
            {name: 'bindTel2N',mapping: 'bindTel2'},
            {name: 'bindTel3N',mapping: 'bindTel3'},
            {name: 'termAddrN',mapping: 'termAddr'},
            {name: 'termPlaceN',mapping: 'termPlace'},
            {name: 'propInsRateN',mapping: 'propInsRate'},
            {name: 'oprNmN',mapping: 'oprNm'},
            {name: 'termParaN',mapping: 'termPara'},
            {name: 'termPara1N',mapping: 'termPara1'},
            {name: 'keyDownSignN',mapping: 'keyDownSign'},
            {name: 'paramDownSignN',mapping: 'paramDownSign'},
            {name: 'icDownSignN',mapping: 'icDownSign'},
            {name: 'reserveFlag1N',mapping: 'reserveFlag1'},
            {name: 'cardTypeN',mapping: 'cardType'},
            {name: 'leaseFeeN',mapping: 'leaseFee'},
            {name: 'depositFlagN',mapping: 'depositFlag'},
            {name: 'depositStateN',mapping: 'depositState'},
            {name: 'depositAmtN',mapping: 'depositAmt'},
            {name: 'checkCardNoN',mapping: 'checkCardNo'},
            {name: 'termSingleLimitN',mapping: 'termSingleLimit'},
            {name: 'rentFeeN',mapping: 'rentFee'} ,
            {name: 'termVersionU',mapping: 'misc2'},
            {name: 'termNameNew',mapping: 'termName'},
        ])
//	,
//        autoLoad: false
    });
	function parseTxnSup(val){
		for(var i=1;i<=11;i++){
			Ext.getCmp('txnSupNew'+i).setValue(val.charAt(i-1));
		}
	}
    
	var sameTermAddMenu = {
		text: '复制',
		width: 85,
		iconCls: 'add',
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
                    	
                       	termIdSave=termInfoStore.getAt(0).data.TermIdN;
                       	termForm.getForm().loadRecord(termInfoStore.getAt(0));
                       	var mchtId=termInfoStore.getAt(0).data.mchnNoN;
                       	Ext.getCmp('mchnNoN').disabled=false;
                        T30101.getMchntFlag1(mchtId,function(data){
                        	if(data=='7'){
                        		Ext.getCmp('termIdSaveFlagN').setValue('1');
                        		Ext.getCmp('termIdSaveFlagN').disabled=true;
                        		Ext.getCmp('mchnNoN').disabled=true;
                        	}
                        });
                        T30101.getMchnt(mchtId,function(ret){
                       	 var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
//                           SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION',Ext.getCmp('termBranchN').getValue(),function(ret){
//                        	   eposStore.removeAll();
//                        	   eposStore.loadData(Ext.decode(ret));
//						    });
                           if(mchntInfo.mchtFlag1=='7'){
                              	Ext.getCmp('accountBox4').show();
                              	termForm.getForm().findField("termTpN").setValue('2');
                              	termForm.getForm().findField("termTpN").setReadOnly(true);
                              	Ext.getCmp('termTpN').allowBlank = false;
                              }else{
                              	Ext.getCmp('accountBox4').hide();
                              	termForm.getForm().findField("termTpN").setReadOnly(false);
                              	Ext.getCmp('termTpN').allowBlank = true;
                              }
                       });
                     	//termSingleLimit是char类型的 去空格
                   	   	Ext.getCmp('termSingleLimitN').setValue((Ext.getCmp('termSingleLimitN').getValue().trim())/100);

                   	  	var orgFlag = termForm.getForm().findField("propInsNmN").getValue();
                		if(orgFlag == null || orgFlag == ""){
                			termForm.getForm().findField("propInsRateNew").reset();
                			termForm.getForm().findField("leaseFeeNew").reset();
//                			termForm.getForm().findField("rentFeeNew").reset();
                			
                			termForm.getForm().findField("propInsRateNew").allowBlank = true;
                			termForm.getForm().findField("leaseFeeNew").allowBlank = true;
                			termForm.getForm().findField("rentFeeNew").allowBlank = true;
                		}else{
                			termForm.getForm().findField("propInsRateNew").allowBlank = false;
                			termForm.getForm().findField("leaseFeeNew").allowBlank = false;
                			termForm.getForm().findField("rentFeeNew").allowBlank = false;
                			
                			termForm.getForm().findField("propInsRateNew").blankText = '没有请输0';
                			termForm.getForm().findField("leaseFeeNew").blankText = '没有请输0';
                			termForm.getForm().findField("rentFeeNew").blankText = '没有请输0';
                		}
                       	//处理所支持的交易选框
                       	var termTxnSup = termInfoStore.getAt(0).data.termTxnSupN;
                       	parseTxnSup(termTxnSup);
                       
                       	termWin.show();
                    }else{
                    
                       termWin.hide();
                    }
                    termPanel.setActiveTab('info1New');
                }
            });
		}
	};

	var batchAddMenu = {
			text: '批量复制',
			width: 85,
			iconCls: 'add',
			disabled: true,
			handler:function() {
				selectedRecord = grid.getSelectionModel().getSelected();
				if(selectedRecord == null)
				{
					showAlertMsg("没有选择记录",grid);
					return;
				}    
				showInputMsg("批量新增终端信息","请输入新增终端的数量(1-100)",true,batchAddBack);
			}
		};
	function batchAddBack(bt,text){
		if (bt == 'ok') {
			var reg = /^[0-9]{1,3}$/;
			if(!reg.test(text)||parseInt(text)>100||parseInt(text)==0){
				alert('请输入1-100的数字');
				showInputMsg('批量新增终端信息', '请输入新增终端的数量(1-100)', true, batchAddBack);
				return;
			}
			
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise( {
				url : 'T30102Action.asp',
				params : {
                	txnId: '30102',
                	subTxnId: '02',
                	termIdSave:selectedRecord.get('termId'),
                	mchtCdSave:selectedRecord.get('mchtCd'),
                	method:'batchSave',
                	count:parseInt(text)
            	},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if (rspObj.success) {
						showSuccessMsg(rspObj.msg, grid);
					} else {
						showErrorMsg(rspObj.msg, grid);
					}
					// 重新加载商户信息
				grid.getStore().reload();
			}
			});
			hideProcessMsg();
		}
	};
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
	
	
	menuArr.push(sameTermAddMenu);		//[0]
	//menuArr.push(batchAddMenu);		//[1]
    menuArr.push(detailMenu);           //[2]
    menuArr.push(queryMenu);	        //[3]
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: 'POS终端便捷添加 ',
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
			grid.getTopToolbar().items.items[0].enable();
			grid.getTopToolbar().items.items[1].enable();
			grid.getTopToolbar().items.items[2].enable();
			
		},
		'beforeload':function(){
			grid.getTopToolbar().items.items[0].disable();
			grid.getTopToolbar().items.items[1].disable();
			grid.getTopToolbar().items.items[2].disable();
			
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

/**************** 终端修改 *************************/
    var termPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 450,
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
                    width: 440,
                    listeners: {
                     'select': function() {
                	 		var mid = Ext.getCmp("mchnNoN").getValue();
                            T30101.getMchnt(mid,function(ret){
                                var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
                                Ext.getCmp("termMccN").setValue(mchntInfo.mcc);
                                Ext.getCmp("termBranchN").setValue(mchntInfo.agrBr);
                              
                            });
                            Ext.getCmp('termIdSaveFlagN').disabled=false;
                            T30101.getMchntFlag1(mid,function(data){
                            	if(data=='7'){
                            		Ext.getCmp('termIdSaveFlagN').setValue('1');
                            		Ext.getCmp('termIdSaveFlagN').disabled=true;
                            	}
                            });
                            T30101.getMchnt(mid,function(ret){
                            	 var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
//                                SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION',Ext.getCmp('termBranchN').getValue(),function(ret){
//                             	   eposStore.removeAll();
//                             	   eposStore.loadData(Ext.decode(ret));
//     						    });
                                if(mchntInfo.mchtFlag1=='7'){
                                   	Ext.getCmp('accountBox4').show();
                                   	termForm.getForm().findField("termTpN").setValue('2');
                                   	termForm.getForm().findField("termTpN").setReadOnly(true);
                                   	Ext.getCmp('termTpN').allowBlank = false;
                                   }else{
                                   	Ext.getCmp('accountBox4').hide();
                                   	termForm.getForm().findField("termTpN").setReadOnly(false);
                                   	Ext.getCmp('termTpN').allowBlank = true;
                                   }
                            });
                            //处理支持的交易，把mcc支持的交易置于选中状态
                            T30101.getTxnSup(mid,function(data){
//                            	data = Ext.decode(data);
                            	for(var i=1;i<=11;i++){
                            		termForm.getForm().findField("txnSupNew"+i).setValue('0');//状态//先清空全部置于未选中
                            		if(i==5||i==6||i==7){//消费、消费撤销、查询默认勾选
                            			termForm.getForm().findField("txnSupNew"+i).setValue('1')
                            		}
                            	}
                            	for(var i=0,len=data.length;i<len;i++){
                            		var v=parseInt(data[i]);
                            		termForm.getForm().findField("txnSupNew"+v).setValue('1');//将mcc支持的交易打钩
                            	}
                            });
                            //去商户的连接类型(商户类型)，并把终端的连接类型设为同商户一样、只读，方便以后的报表查询
                            T30101.getMchtConnType(mid,function(data){
                            	termForm.getForm().findField("connectModeN").setValue(data);
                            });
                        }
                    }
                  }]
             },{
                 columnWidth: .5, 
                 layout: 'form',
                 items: [{
                     xtype: 'textfield',
                     fieldLabel: '银联商户号',
                     width:150,
                     id: 'mappingMchntcdTwoN',
                     name: 'mappingMchntcdTwoN',
                     readOnly: true
                 }]
         },{
             columnWidth: .5, 
             layout: 'form',
             items: [{
                 xtype: 'textfield',
                 fieldLabel: '银联终端号',
                 width:150,
                 id: 'mappingTermidTwoN',
                 name: 'mappingTermidTwoN',
                 regex:/^[a-zA-Z0-9]{8}$/,
    			 regexText:'只能输入8位数字或字母'
                // readOnly: true
             }]
        },{
                 columnWidth: .5, 
                 layout: 'form',
                 items: [{
                     xtype: 'textfield',
                     fieldLabel: '一卡通商户号',
                     width:150,
                     id: 'mappingMchntcdOneN',
                     name: 'mappingMchntcdOneN',
                     readOnly: true
                 }]
         },{
             columnWidth: .5, 
             layout: 'form',
             items: [{
                 xtype: 'textfield',
                 fieldLabel: '一卡通终端号',
                 width:150,
                 id: 'mappingTermidOneN',
                 name: 'mappingTermidOneN',
                 readOnly: true
             }]
     },{
                columnWidth: .5,
                layout: 'form',
                items:[{
                   xtype: 'combo',
                   fieldLabel: '终端所属机构*',
                   id: 'termBranchN',
                   hiddenName: 'brhIdNew',
                   width:150,
                   store: brhStore,
                   displayField: 'displayField',
                   valueField: 'valueField',
                   mode: 'local',
                   readOnly:true,
                   allowBlank: false,
                   blankText: '终端所属机构不能为空',
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
                        id: 'termMccN',
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
                    })
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
                    fieldLabel: '第三方服务机构',
                    width:150,
                    store: organStore,
                    id: 'propInsNmN',
                    hiddenName: 'propInsNmNew',
                    listeners:{
                    	'select':function(){
                    		var orgFlag = termForm.getForm().findField("propInsNmN").getValue();
                    		
                    		if(orgFlag == null || orgFlag == ""){
	                			termForm.getForm().findField("propInsRateNew").reset();
	                			termForm.getForm().findField("leaseFeeNew").reset();
//	                			termForm.getForm().findField("rentFeeNew").reset();
	                			
	                			termForm.getForm().findField("propInsRateNew").allowBlank = true;
	                			termForm.getForm().findField("leaseFeeNew").allowBlank = true;
	                			termForm.getForm().findField("rentFeeNew").allowBlank = true;
	                		}else{
	                			termForm.getForm().findField("propInsRateNew").allowBlank = false;
	                			termForm.getForm().findField("leaseFeeNew").allowBlank = false;
	                			termForm.getForm().findField("rentFeeNew").allowBlank = false;
	                			
	                			termForm.getForm().findField("propInsRateNew").blankText = '没有请输0';
	                			termForm.getForm().findField("leaseFeeNew").blankText = '没有请输0';
	                			termForm.getForm().findField("rentFeeNew").blankText = '没有请输0';
	                		}
                    	}
                    }
                }]
            },{
                columnWidth: .5,
                hidden: false,
                layout: 'form',
                items: [{
                    xtype: 'numberfield',
                    fieldLabel: '外包分成比例(%)',
                    width:150,
                    regex: /^([0-9]{1,2})(\.[0-9]{1,2})?$/,
                  	regexText: '比例必须是小于100的整数或小数(精确到小数点后2位)，如12、1.2或11.22',
                    id: 'propInsRateN',
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
	                    store: termTypeStore,
	                    listeners:{
	                           'select':function() {
	                	 var value = Ext.getCmp('termTpN').getValue();
	                	 var mid = Ext.getCmp("mchnNoN").getValue();
	                   	 T30101.getMchnt(mid,function(ret){
	                        var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
	                        if(mchntInfo.mchtFlag1!='7'){
	                        	if(value=='2'){
	                        		Ext.getCmp('termTpN').setValue('0');
	                        		showAlertMsg("非固话POS商户，终端类型不能选择刷卡电话",grid);
	                               return;
	                        	}
	                        }
	                    });
	                    }
	                }
                    }]
           },{
               columnWidth: .5,
               layout: 'form',
               items: [{
                       xtype: 'textfield',
                       fieldLabel: '终端单笔限额(元)*',
                       width:150,
                       allowBlank:false,
                       vtype: 'isMoney',
                       maxLength: 10,
                       id: 'termSingleLimitN',
                       name: 'termSingleLimitN'
                   }]
           },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终端安装地址',
                    maxLength: 200,
                    width:150,
                    id: 'termAddrN',
                    name: 'termAddrNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话1',
                    maxLength: 15,
                    width:150,
                    regex: /^(0[0-9]{2,3}\-)([2-9][0-9]{6,7})$/,
					regexText: '拨入号码必须是3、4位区号(以0开头)-7、8位电话号码,如xxxx-xxxxxxx',
                    id: 'bindTel1N',
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
                    regex: /^(0[0-9]{2,3}\-)([2-9][0-9]{6,7})$/,
					regexText: '拨入号码必须是3、4位区号(以0开头)-7、8位电话号码,如xxxx-xxxxxxx',
                    id: 'bindTel2N',
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
                    regex: /^(0[0-9]{2,3}\-)([2-9][0-9]{6,7})$/,
					regexText: '拨入号码必须是3、4位区号(以0开头)-7、8位电话号码,如xxxx-xxxxxxx',
                    id: 'bindTel3N',
                    name: 'bindTel3New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '是否绑定电话',
                    id: 'reserveFlag1N',
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
                    id: 'depositFlagN',
                    width:150,
                    name: 'depositFlagNew',
                    inputValue: '1'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '押金金额(元)',
                        width:150,
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'depositAmtN',
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
                        fieldLabel: '终端租凭费用(元/天)',
                        vtype: 'isMoney',
                        maxLength: 12,
                        width:150,
                        id: 'leaseFeeN',
                        name: 'leaseFeeNew'
                    }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '单月限额（元）',
                        vtype: 'isMoney',
                        maxLength: 12,
                        width:150,
                        id: 'rentFeeN',
                        name: 'rentFeeNew'
                    }]
            },{
                id: 'accountBox4',
                columnWidth: .5,
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '固话POS版本号*',
                    store: eposStore,
                    id: 'termVersionU',
                    hiddenName: 'termVersion',
                    anchor: '80%'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '是否相同终端号*',
                    id: 'termIdSaveFlagN',
                    width:150,
                    allowBlank:false,
                    hiddenName: 'termIdSaveFlag',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','相同实体终端'],['1','不同实体终端']]
                    })
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终端硬件序列号',
                    id: 'termNameNew',
                    width:150,
                    allowBlank:true,
                    name: 'termNameNew'
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
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '分期消费',
                            id: 'txnSupNew8',
                            name: 'txnSupNew8',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '分期消费撤销',
                            id: 'txnSupNew9',
                            name: 'txnSupNew9',
                            inputValue: '1'
                         }]
                    },{
                    columnWidth: .5,
                    layout: 'form',
                    items: [{
                            xtype: 'checkbox',
                            fieldLabel: '无磁无密消费请求',
                            id: 'txnSupNew10',
                            name: 'txnSupNew10',
                            inputValue: '1'
                         }]
                    },{
                        columnWidth: .5,
                        layout: 'form',
                        items: [{
                                xtype: 'checkbox',
                                fieldLabel: '无磁无密消费撤销请求',
                                id: 'txnSupNew11',
                                name: 'txnSupNew11',
                                inputValue: '1'
                             }]
                        }]
            }]
    });
/*******************  终端修改表单  *********************/
    var termForm = new Ext.form.FormPanel({
        frame: true,
        height: 450,
        width: 750,
        labelWidth: 140,
        waitMsgTarget: true,
        layout: 'column',
        items: [termPanel]
    });
   
/*******************  终端修改信息 *********************/
    var termWin = new Ext.Window({
        title: '终端复制',
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

                	if(termForm.getForm().isValid()){
                    termForm.getForm().submitNeedAuthorise({
                        url: 'T30102Action.asp',
                        waitMsg: '正在提交，请稍后......',
                        success: function(form,action) {
                            showSuccessMsg(action.result.msg,termForm);
                            grid.getStore().reload();
                            termForm.getForm().reset();
                            termWin.hide();
                            grid.getTopToolbar().items.items[2].disable();
                        },
                        failure: function(form,action) {
                            termPanel.setActiveTab('info1New');
                            showErrorMsg(action.result.msg,termForm);
                        },
                        params: {
                            txnId: '30102',
                            subTxnId: '01',
                            termIdSave:termInfoStore.getAt(0).data.termIdN,
                            method:'saveOne'
                        }
                    });
               
                	}else{
                		termPanel.setActiveTab('info1New');
                		termForm.getForm().isValid()
                	}
            }
        },{
            text: '关闭',
            handler: function() {
                termWin.hide();
            }
        }]
    });
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});