Ext.onReady(function() {
	
	//商户数据部分
	var licenceStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=licenceInfoTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'licenceNoUpd'
		},[
			{name: 'licenceNoUpd',mapping: 'licenceNoUpd'},
			{name: 'etpsNmUpd',mapping: 'etpsNmUpd'},
			{name: 'licenceEndDate',mapping: 'licenceEndDateUpd'},
			{name: 'bankLicenceNoUpd',mapping: 'bankLicenceNoUpd'},
			{name: 'etpsTypeUpd',mapping: 'etpsTypeUpd'},
			{name: 'faxNoUpd',mapping: 'faxNoUpd'},
			{name: 'busAmtUpd',mapping: 'busAmtUpd'},
			{name: 'mchtCreLvlUpd',mapping: 'mchtCreLvlUpd'},
			{name: 'managerUpd',mapping: 'managerUpd'},
			{name: 'artifCertifTpUpd',mapping: 'artifCertifTpUpd'},
			{name: 'identityNoUpd',mapping: 'identityNoUpd'},
			{name: 'managerTelUpd',mapping: 'managerTelUpd'},
			{name: 'faxUpd',mapping: 'faxUpd'},
			{name: 'regAddrUpd',mapping: 'regAddrUpd'},
			{name: 'applyDateUpd',mapping: 'applyDateUpd'},
			{name: 'enableDateUpd',mapping: 'enableDateUpd'},
			{name: 'preAudNmUpd',mapping: 'preAudNmUpd'},
			{name: 'confirmNmUpd',mapping: 'confirmNmUpd'},
			{name: 'etpsStateUpd',mapping: 'etpsStateUpd'},
			{name: 'updOprIdUpd',mapping: 'updOprIdUpd'},
			{name: 'crtOprIdUpd',mapping: 'crtOprIdUpd'},
			{name: 'recUpdTsUpd',mapping: 'recUpdTsUpd'},
			{name: 'recCrtTsUpd',mapping: 'recCrtTsUpd'}
		])
	});
	
	var licenceRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>企业性质：{etpsTypeUpd}</p>',
			'<p>企业资质等级：{mchtCreLvlUpd}</p>',
			'<p>注册资金：{busAmtUpd}</p>',
			'<p>注册地址：{regAddrUpd}</p>',
			'<p>录入柜员：{crtOprIdUpd}&nbsp;&nbsp;&nbsp;&nbsp;审核柜员：{confirmNmUpd}</p>',
			'<p>最近更新时间：{recUpdTsUpd}</p>'
		)
	});

	
	var licenceColModel = new Ext.grid.ColumnModel([
		licenceRowExpander,
		{id: 'licenceNoUpd',header: '营业执照编号',dataIndex: 'licenceNoUpd',sortable: true,width: 150},
		{header: '企业名称',dataIndex: 'licenceNmUpd',width: 200},
		{header: '法人姓名',dataIndex: 'managerUpd'},
//		{header: '注册日期',dataIndex: 'applyDateUpd',width: 80,renderer: formatDt},
//		{header: '企业状态',dataIndex: 'etpsStateUpd',renderer: mchntSt}
		{header: '注册日期',dataIndex: 'applyDateUpd',width: 80,renderer: formatDt},
		{header: '企业状态',dataIndex: 'etpsStateUpd'}
	]);
	
	
	// 菜单集合
	var menuArr = new Array();
	
	var addMenu = {
		text: '新增',
		width: 85,
		iconCls: 'add',
		//disabled: true,
		handler:function() {
			addWin.show();
		}
	};
	
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			window.location.href = Ext.contextPath + '/page/mchnt/T2010102.jsp?mchntId='+licenceGrid.getSelectionModel().getSelected().get('mchtNo');
		}
	};
	
	var stopMenu = {
		text: '停用',
		width: 85,
		iconCls: 'stop',
		disabled: true,
		handler:function() {
			showConfirm('确定停用吗？',licenceGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入进行该操作的原因',true,stopBack);
				}
			});
		}
	};
	
	function stopBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,stopBack);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20101Action_stop.asp',
				params: {
					mchtNo: licenceGrid.getSelectionModel().getSelected().get('mchtNo'),
					txnId: '20101',
					subTxnId: '03',
					exMsg: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,licenceGrid);
					} else {
						showErrorMsg(rspObj.msg,licenceGrid);
					}
					// 重新加载商户信息
					licenceGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	
	var recoverMenu = {
		text: '恢复',
		width: 85,
		iconCls: 'recover',
		disabled: true,
		handler:function() {
			showConfirm('确定恢复吗？',licenceGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入进行该操作的原因',true,recoverBack);
				}
			});
		}
	};
	
	function recoverBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,stopBack);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20101Action_recover.asp',
				params: {
					mchtNo: licenceGrid.getSelectionModel().getSelected().get('mchtNo'),
					txnId: '20101',
					subTxnId: '04',
					exMsg: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,licenceGrid);
					} else {
						showErrorMsg(rspObj.msg,licenceGrid);
					}
					// 重新加载商户信息
					licenceGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			showMchntDetailS(licenceGrid.getSelectionModel().getSelected().get('mchtNo'),licenceGrid);	
		}
	};
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	menuArr.push(addMenu);     //[0]
	menuArr.push('-'); 	       //[1]
	menuArr.push(editMenu);    //[2]
	menuArr.push('-');         //[3]
	menuArr.push(stopMenu);    //[4]
	menuArr.push('-');         //[5]
	menuArr.push(recoverMenu); //[6]
	menuArr.push('-');         //[7]
	menuArr.push(detailMenu);  //[8]
	menuArr.push('-');         //[9]
	menuArr.push(queryCondition);  //[10]	
	
	
	var licenceGrid = new Ext.grid.GridPanel({
		title: '商户信息维护',
		region: 'center',
		//iconCls: 'T20101',
		frame: true,
		border: true,
		columnLines: true,
		//autoExpandColumn: 'mchtNm',
		stripeRows: true,
		store: licenceStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: licenceColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		plugins: licenceRowExpander,
		loadMask: {
			msg: '正在加载证照号信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: licenceStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	licenceStore.load();
	
	licenceGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = licenceGrid.getSelectionModel().getSelected().data.mchtNo;
//		termStore.load({
//			params: {
//				start: 0,
//				mchntNo: id
//				}
//			});
	});
//	termStore.on('beforeload', function() {
//		termStore.removeAll();
//	});
	
	
	// 禁用编辑按钮
	licenceGrid.getStore().on('beforeload',function() {
		licenceGrid.getTopToolbar().items.items[0].disable();
		licenceGrid.getTopToolbar().items.items[2].disable();
		licenceGrid.getTopToolbar().items.items[4].disable();
		licenceGrid.getTopToolbar().items.items[6].disable();
	});
	
	var rec;
	
	licenceGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(licenceGrid.getView().getRow(licenceGrid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = licenceGrid.getSelectionModel().getSelected();
			if(rec.get('mchtStatus') == '0' || rec.get('mchtStatus') == '1' || rec.get('mchtStatus') == '3') {
				licenceGrid.getTopToolbar().items.items[0].enable();
			} else {
				licenceGrid.getTopToolbar().items.items[0].disable();
			}
			if(rec.get('mchtStatus') == '0') {
				licenceGrid.getTopToolbar().items.items[2].enable();
			} else {
				licenceGrid.getTopToolbar().items.items[2].disable();
			}
			if(rec.get('mchtStatus') == '6') {
				licenceGrid.getTopToolbar().items.items[4].enable();
			} else {
				licenceGrid.getTopToolbar().items.items[4].disable();
			}
			licenceGrid.getTopToolbar().items.items[6].enable();
		}
	});
	
	/**********************新增证照号****************************/
	var termForm = new Ext.form.FormPanel({
		frame: true,
		height: 450,
		width: 600,
		labelWidth: 100,
		waitMsgTarget: true,
		layout: 'column',
		items: [termPanel]
	});
	var termWin = new Ext.Window({
		title: '终端添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
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
//				if(termForm.getForm().isValid()) {
//					termForm.getForm().submitNeedAuthorise({
//						url: 'T30101Action.asp',
//						waitMsg: '正在提交，请稍后......',
//						success: function(form,action) {
//							showSuccessDtl(action.result.msg,termWin);
//							//重新加载参数列表
//							grid.getStore().reload();
//							//重置表单
//							termForm.getForm().reset();
//                            
//                            termWin.hide();
//						},
//						failure: function(form,action) {
//							termPanel.setActiveTab('info3New');
//							showErrorMsg(action.result.msg,termWin);
//						},
//						params: {
//							txnId: '30101',
//							subTxnId: '01'
//						}
//					});
//				}
//                else
//                {
//                    var finded = true;
//	                termForm.getForm().items.each(function(f){
//	                    if(finded && !f.validate()){
//	                        var tab = f.ownerCt.ownerCt.id;
//	                        if(tab == 'info1New' || tab == 'info2New' || tab == 'info3New' ){
//	                             termPanel.setActiveTab(tab);
//	                        }
//	                        finded = false;
//	                    }
//	                });
//                }
			}
		},{
			text: '重置',
			handler: function() {
					//termForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				termWin.hide();
			}
		}]
	});
	
	
	
	
	
	
	
	
	var termPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 450,
        width: 600,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1New',
                layout: 'column',
                items: [{
                 columnWidth: .7,
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
                            T30101.getMchnt(Ext.getCmp("mchnNoN").getValue(),function(ret){
                                var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
                                Ext.getCmp("termMccNew").setValue(mchntInfo.mcc);
                                Ext.getCmp("termBranchNew").setValue(mchntInfo.acqInstId);
                                Ext.getCmp("txn22New").setValue(mchntInfo.mchtCnAbbr);
                                Ext.getCmp("txn27New").setValue(mchntInfo.engName);
                                if( mchntInfo.mcc == '0000' )
                                {
	                                Ext.getCmp('accountBox1').show();
	                                
	                                termForm.getForm().findField("termTpN").setValue(1);
	                                termForm.getForm().findField("termTpN").setReadOnly(true);
	                                termForm.getForm().findField("financeCard1New").setValue(mchntInfo.updOprId);
	                                termForm.getForm().findField("financeCard1New").setReadOnly(true);
	                                Ext.getCmp('financeCard1New').allowBlank = false;
	                                
	                                termForm.getForm().findField("param1New").setValue(1);
	                                termForm.getForm().findField("param11New").setValue(1);       
	                                termForm.getForm().findField("param12New").setValue(1);       
	                                termForm.getForm().findField("param13New").setValue(1);       
	                                termForm.getForm().findField("param14New").setValue(1);       
	                                termForm.getForm().findField("param15New").setValue(1); 
	                             }else{
	                                Ext.getCmp('accountBox1').hide();
	                               
	                           		termForm.getForm().findField("termTpN").setValue(0);
	                                termForm.getForm().findField("termTpN").setReadOnly(false);
	                                termForm.getForm().findField("financeCard1New").setValue("");
	                                termForm.getForm().findField("financeCard1New").setReadOnly(false);
	                                Ext.getCmp('financeCard1New').allowBlank = true;
	                                
	                                termForm.getForm().findField("param1New").setValue(0);
	                                termForm.getForm().findField("param11New").setValue(0);
	                                termForm.getForm().findField("param12New").setValue(0);
	                                termForm.getForm().findField("param13New").setValue(0);
	                                termForm.getForm().findField("param14New").setValue(0);
	                                termForm.getForm().findField("param15New").setValue(0);
                                }
                            });
                        }
                    }
                  }]
             },{
                    columnWidth: .8, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户名',
                        id: 'txn22New',
                        name: 'txn22New',
                        readOnly: true,
                        width: 300
                    }]
            },{
                    columnWidth: .8, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户英文名',
                        id: 'txn27New',
                        name: 'txn27New',
                        readOnly: true,
                        width: 300
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '终端MCC码',
                        id: 'termMccNew',
                        name: 'termMccNew',
                        readOnly: true
                    }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items:[{
                    xtype: 'combo',
                    fieldLabel: '终端所属分行*',
                    id: 'termBranchNew',
                    hiddenName: 'brhIdNew',
                    store: brhStore,
                    displayField: 'displayField',
                    valueField: 'valueField',
                    mode: 'local',
                    allowBlank: false,
                    width: 130,
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
                    fieldLabel: '联系电话*',
                    maxLength: 20,
                    allowBlank: false,
                    regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
                    id: 'contTelNew',
                    name: 'contTelNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '产权属性*',
                    id: 'propTpN',
                    allowBlank: false,
                    hiddenName: 'propTpNew',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','我行产权'],['1','我行租赁'],['2','第三方投入']]
                    }),
                    listeners:{
                        'select': function() {
	                        var args = Ext.getCmp('propTpN').getValue();
	                        if(args == 2)
	                        {
	                            Ext.getCmp("termPara1New").show();
                                Ext.getCmp("flagBox1").show();
	                        }
                            else
                            {
                                Ext.getCmp("termPara1New").hide();
                                Ext.getCmp("flagBox1").hide();
                            }
                        }
                   }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '收单服务机构',
                    store: organStore,
                    id: 'propInsNmN',
                    hiddenName: 'propInsNmNew'
                }]
            },{
                columnWidth: .5,
                id: "flagBox1",
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'numberfield',
                    fieldLabel: '第三方分成比例(%)',
                    id: 'termPara1New',
                    name: 'termPara1New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '连接类型*',
                    id: 'connectModeN',
                    value: 2,
                    allowBlank: false,
                    hiddenName: 'connectModeNew',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['2','间联'],['1','直联'],['3','第三方平台接入']]
                    })
                }]
            }]
            },{
                title: '附加信息',
                layout: 'column',
                id: 'info2New',
                items: [{
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
	                    listeners: {
	                     'select': function() {
	                                var value1 = Ext.getCmp("termMccNew").getValue();
	                                var value2 = Ext.getCmp('termTpN').getValue();
	                                if( value1 != '0000' && value2 == '1')
	                                {
	                                	Ext.getCmp('termTpN').setValue(0);
		                                showAlertMsg("非财务POS商户，终端类型不能选择财务POS",grid);
	                                }
	                        }
                    }
                    }]
           },{
                columnWidth: .5,
                id: "accountBox1",
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '财务账号1*',
                    maxLength: 16,
                    vtype: 'isNumber',
                    id: 'financeCard1New',
                    name: 'financeCard1New'
                },{
                    xtype: 'textfield',
                    fieldLabel: '财务账号2',
                    maxLength: 16,
                    vtype: 'isNumber',
                    id: 'financeCard2New',
                    name: 'financeCard2New'
                },{
                    xtype: 'textfield',
                    fieldLabel: '财务账号3',
                    maxLength: 20,
                    vtype: 'isNumber', 
                    id: 'financeCard3New',
                    name: 'financeCard3New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终端安装地址',
                    maxLength: 200,
                    id: 'termAddrNew',
                    name: 'termAddrNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话1*',
                    maxLength: 15,
                    allowBlank: false,
                    vtype: 'isNumber',
                    id: 'txn14New',
                    name: 'txn14New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话2',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'txn15New',
                    name: 'txn15New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话3',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'txn16New',
                    name: 'txn16New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话1*',
                    maxLength: 15,
                    allowBlank: false,
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
                    vtype: 'isNumber',
                    id: 'bindTel3New',
                    name: 'bindTel3New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'CA公钥下载标志',
                    id: 'keyDownSignNew',
                    name: 'keyDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '终端参数下载标志',
                    id: 'paramDownSignNew',
                    name: 'paramDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'IC卡参数下载标志',
                    id: 'icDownSignNew',
                    name: 'icDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1New',
                    name: 'reserveFlag1New',
                    inputValue: '1'
                }]
            }]
            },{
                title: '交易信息',
                id: 'info3New',
                layout: 'column',
                items: [{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '分期付款期数',
                        vtype: 'isNumber',
                        id: 'txn35New',
                        maxLength: 2,
                        name: 'txn35New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '分期付款限额',
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'txn36New',
                        name: 'txn36New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '消费单笔上限 ',
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'txn37New',
                        name: 'txn37New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '退货单笔上限',
                        id: 'txn38New',
                        vtype: 'isMoney',
                        maxLength: 12,
                        name: 'txn38New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '转帐单笔上限',
                        vtype: 'isMoney',
                        id: 'txn39New',
                        maxLength: 12,
                        name: 'txn39New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '退货时间跨度',
                        vtype: 'isNumber',
                        id: 'txn40New',
                        maxLength: 2,
                        name: 'txn40New',
                        value: 30
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '查询 ',
                        id: 'param1New',
                        name: 'param1New',
                        inputValue: '1'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权 ',
                        id: 'param2New',
                        name: 'param2New',
                        inputValue: '1'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权撤销 ',
                        id: 'param3New',
                        name: 'param3New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成联机 ',
                        id: 'param4New',
                        name: 'param4New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成撤销 ',
                        id: 'param5New',
                        name: 'param5New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费 ',
                        id: 'param6New',
                        name: 'param6New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费撤销 ',
                        id: 'param7New',
                        name: 'param7New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '退货 ',
                        id: 'param8New',
                        name: 'param8New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '离线结算 ',
                        id: 'param9New',
                        name: 'param9New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '结算调整 ',
                        id: 'param10New',
                        name: 'param10New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '公司卡转个人卡（财务POS） ',
                        id: 'param11New',
                        name: 'param11New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '个人卡转公司卡（财务POS） ',
                        id: 'param12New',
                        name: 'param12New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '卡卡转帐',
                        id: 'param13New',
                        name: 'param13New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '上笔交易查询（财务POS） ',
                        id: 'param14New',
                        name: 'param14New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '交易查询（财务POS） ',
                        id: 'param15New',
                        name: 'param15New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '定向汇款 ',
                        id: 'param16New',
                        name: 'param16New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款 ',
                        id: 'param17New',
                        name: 'param17New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款撤销 ',
                        id: 'param18New',
                        name: 'param18New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '代缴费 ',
                        id: 'param19New',
                        name: 'param19New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '电子现金 ',
                        id: 'param20New',
                        name: 'param20New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: 'IC现金充值 ',
                        id: 'param21New',
                        name: 'param21New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '指定账户圈存',
                        id: 'param22New',
                        name: 'param22New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非指定账户圈存 ',
                        id: 'param23New',
                        name: 'param23New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非接快速支付 ',
                        id: 'param24New',
                        name: 'param24New',
                        inputValue: '1'
                     }]
            }]
            }]
    });
    
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '注册开始日期',
			editable: false
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '注册结束日期',
			editable: false
		},{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '商户状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','正常'],['1','添加待审核'],['3','修改待审核'],['6','停用']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW',
			fieldLabel: '收单机构',
			id: 'idacqInstId',
			hiddenName: 'acqInstId',
			anchor: '70%'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntIdTmp',
			hiddenName: 'mchtNo',
			editable: true,
			width: 380
		},{
			xtype: 'basecomboselect',
			baseParams: 'MCHNT_GRP_ALL',
			fieldLabel: 'MCC类别',
			hiddenName: 'mchtGrp',
			width: 380
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
		autoHeight: true,
		items: [queryForm],
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
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				licenceStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	licenceStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntId: queryForm.getForm().findField('mchtNo').getValue(),
			mchtStatus: queryForm.findById('mchtStatus').getValue(),
			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			brhId: queryForm.getForm().findField('acqInstId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [licenceGrid],
		renderTo: Ext.getBody()
	});
	
});