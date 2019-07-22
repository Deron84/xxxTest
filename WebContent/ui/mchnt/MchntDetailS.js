

//商户详细信息，在外部用函数封装
function showMchntDetailS(mchntId,El){

	//图片显示Store
	var storeImg = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'}
		])
	});
	//图像显示dataview
	var dataview = new Ext.DataView({
		frame: true,
	    store: storeImg,
	    tpl  : new Ext.XTemplate(
	        '<ul>',
	            '<tpl for=".">',
	                '<li class="phone">',
	                	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
	                    	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName={fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
						'</div>',
	                '</li>',
	            '</tpl>',
	        '</ul>'
	    ),
	    id: 'phones',
	    itemSelector: 'li.phone',
	    overClass   : 'phone-hover',
	    singleSelect: true,
	    multiSelect : true,
	    autoScroll  : true
	});
	
	var clearTypeStore1 = new Ext.data.ArrayStore ({
		
		fields: ['valueField','displayField'],
		data: [['A','本行对公账户'],['P','本行对私账户或单位卡'],['O','他行对公账户'],['S','他行对私账户']],
		reader: new Ext.data.ArrayReader()
	});
	
	//神思电子的只有本行的账户,上面的不用
	var clearTypeStore3 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['A','本行对公账户'],['P','本行对私账户或单位卡']],
		reader: new Ext.data.ArrayReader()
	});
	
	//账户清算方式
	var acctSettleTypeStore = new Ext.data.ArrayStore ({
		
		fields: ['valueField','displayField'],
		data: [['1','单账户'],['2','多账户按比例'],['3','多账户限额']],
		reader: new Ext.data.ArrayReader()
	});

	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});
	
	//判断清算方式
	function checkSettleType(value){if(value=='1'){
    	//Ext.getCmp('idClearType2').reset();
    	Ext.getCmp('settleBankNoSnd').reset();
    	Ext.getCmp('settleBankNmSnd').reset();
    	Ext.getCmp('settleAcctNmSnd').reset();
    	Ext.getCmp('settleAcctSnd').reset();
    	Ext.getCmp('acctSettleRate').reset();
    	Ext.getCmp('acctSettleLimit').reset();
    	
    	Ext.getCmp('idClearType2').disable();
    	Ext.getCmp('settleBankNoSnd').disable();
    	Ext.getCmp('settleBankNmSnd').disable();
    	Ext.getCmp('settleAcctNmSnd').disable();
    	Ext.getCmp('settleAcctSnd').disable();
    	Ext.getCmp('acctSettleRate').disable();
    	Ext.getCmp('acctSettleLimit').disable();
    }else{
    	Ext.getCmp('idClearType2').enable();
    	Ext.getCmp('settleBankNoSnd').enable();
    	Ext.getCmp('settleBankNmSnd').enable();
    	Ext.getCmp('settleAcctNmSnd').enable();
    	Ext.getCmp('settleAcctSnd').enable();
    	if(value=='2'){
    		Ext.getCmp('acctSettleRate').enable();
    		Ext.getCmp('acctSettleLimit').reset();
    		Ext.getCmp('acctSettleLimit').disable();
    	}else{
    		Ext.getCmp('acctSettleRate').reset();
    		Ext.getCmp('acctSettleRate').disable();
    		Ext.getCmp('acctSettleLimit').enable();
    	}
    	
    }}

	/***********  直联商户费率设置  *****************/
	var cupMccStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
	SelectOptionsDWR.getComboData('MCHNT_TP_CUP',function(ret){
		cupMccStore.loadData(Ext.decode(ret));
	});
	
	var cupFeeStore1 = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
	SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP1','----',function(ret){
		cupFeeStore1.loadData(Ext.decode(ret));
	});
	
	var feeForm = new Ext.form.FormPanel({
    	region: 'north',
        height: 100,
        frame: true,
        layout: 'column',
        labelWidth: 80,
        items: [{
    			columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_FEE_ACT',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '决定索引1*',
					id: 'mchtCup521I',
					hiddenName: 'mchtCup521',
					allowBlank: false,
					readOnly: true,
					anchor: '90%',
					value: 'NK001'
		        }]
			},{
    			columnWidth: .22,
            	layout: 'form',
        		items: [{
			        xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','0-按比例'],['1','1-固定金额']],
						reader: new Ext.data.ArrayReader()
					}),
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法类型1*',
					id: 'mchtCup522I',
					hiddenName: 'mchtCup522',
					allowBlank: false,
					anchor: '90%',
					listeners:{
						'select': function() {
							cupFeeStore1.removeAll();
							SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP1',feeForm.getForm().findField('mchtCup522I').getValue(),function(ret){
								cupFeeStore1.loadData(Ext.decode(ret));
							});
							feeForm.getForm().findField('mchtCup523I').reset();
						}
					}
		        }]
			},{
    			columnWidth: .44,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        store: cupFeeStore1,
			        displayField: 'displayField',
					valueField: 'valueField',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法配置1*',
					id: 'mchtCup523I',
					hiddenName: 'mchtCup523',
					allowBlank: false,
					anchor: '90%'
		        }]
			},{
    			columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_FEE_ACT',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '决定索引2*',
					id: 'mchtCup524I',
					hiddenName: 'mchtCup524',
					allowBlank: false,
					anchor: '90%',
					listeners:{
						'select': function() {
							var feeAct2 = feeForm.getForm().findField('mchtCup524I').getValue();
							if(feeAct2 == 'NK001'){
								feeForm.getForm().findField('mchtCup524I').reset();
								showMsg('请选择“NK001-全国南卡北用决定索引”以外的类型！',feeForm);
							}
						}
					}
		        }]
			},{
    			columnWidth: .22,
            	layout: 'form',
        		items: [{
			        xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','0-按比例'],['1','1-固定金额']],
						reader: new Ext.data.ArrayReader()
					}),
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法类型2*',
					id: 'mchtCup525I',
					hiddenName: 'mchtCup525',
					allowBlank: false,
					anchor: '90%',
					listeners:{
						'select': function() {
							cupFeeStore1.removeAll();
							SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP1',feeForm.getForm().findField('mchtCup525I').getValue(),function(ret){
								cupFeeStore1.loadData(Ext.decode(ret));
							});
							feeForm.getForm().findField('mchtCup526I').reset();
						}
					}
		        }]
			},{
    			columnWidth: .44,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        store: cupFeeStore1,
			        displayField: 'displayField',
					valueField: 'valueField',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法配置2*',
					id: 'mchtCup526I',
					hiddenName: 'mchtCup526',
					allowBlank: false,
					anchor: '90%'
		        }]
			}]
    });
    
    
	var feeWin = new Ext.Window({
		title: '商户手续费决定索引',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 1000,
		autoHeight: true,
		layout: 'fit',
		items: [feeForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {

				if(feeForm.getForm().isValid()) {
					var fee1 = feeForm.getForm().findField('mchtCup521I').getValue();
					var fee2 = feeForm.getForm().findField('mchtCup522I').getValue();
					var fee3 = feeForm.getForm().findField('mchtCup523I').getValue();
					var fee4 = feeForm.getForm().findField('mchtCup524I').getValue();
					var fee5 = feeForm.getForm().findField('mchtCup525I').getValue();
					var fee6 = feeForm.getForm().findField('mchtCup526I').getValue();
					
					mchntForm.getForm().findField("fee_act").setValue(fee1 + '&' + fee3 + '&#' + fee4 + '&' + fee6);
					
				    feeForm.getForm().reset();
                  	feeWin.hide();
				} 
			}
		},{
			text: '重置',
			handler: function() {
				feeForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				feeForm.getForm().reset();
				feeWin.hide();
			}
		}]
	});
	
	var feeForm2 = new Ext.form.FormPanel({
    	region: 'north',
        height: 100,
        frame: true,
        layout: 'column',
        labelWidth: 80,
        items: [{
    			columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_FEE_ACT2',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '决定索引*',
					id: 'mchtCup2521I',
					hiddenName: 'mchtCup2521',
					allowBlank: false,
					readOnly: true,
					anchor: '90%',
					value: 'JS119'
		        }]
			},{
    			columnWidth: .22,
            	layout: 'form',
        		items: [{
			        xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['3','3-按比例'],['4','4-固定金额']],
						reader: new Ext.data.ArrayReader()
					}),
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法类型*',
					id: 'mchtCup2522I',
					hiddenName: 'mchtCup2522',
					allowBlank: false,
					anchor: '90%',
					listeners:{
						'select': function() {
							cupFeeStore1.removeAll();
							SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP1',feeForm2.getForm().findField('mchtCup2522I').getValue(),function(ret){
								cupFeeStore1.loadData(Ext.decode(ret));
							});
							feeForm2.getForm().findField('mchtCup2523I').reset();
						}
					}
		        }]
			},{
    			columnWidth: .44,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        store: cupFeeStore1,
			        displayField: 'displayField',
					valueField: 'valueField',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法配置*',
					id: 'mchtCup2523I',
					hiddenName: 'mchtCup2523',
					allowBlank: false,
					anchor: '90%'
		        }]
			}]
    });
    
    var feeWin2 = new Ext.Window({
		title: '商户分润手续费索引',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 1000,
		autoHeight: true,
		layout: 'fit',
		items: [feeForm2],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {

				if(feeForm2.getForm().isValid()) {
					var fee1 = feeForm2.getForm().findField('mchtCup2521I').getValue();
					var fee2 = feeForm2.getForm().findField('mchtCup2522I').getValue();
					var fee3 = feeForm2.getForm().findField('mchtCup2523I').getValue();
					
					mchntForm.getForm().findField("feerate_index").setValue(fee1 + '&' + fee3 + '&#');
					
				    feeForm2.getForm().reset();
                  	feeWin2.hide();
				} 
			}
		},{
			text: '重置',
			handler: function() {
				feeForm2.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				feeForm2.getForm().reset();
				feeWin2.hide();
			}
		}]
	});
	/***********  直联商户费率设置 结束 *****************/
	
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getMchntInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mappingMchntcdOne',mapping: 'mappingMchntcdOne'},
			{name: 'mappingMchntcdTwo',mapping: 'mappingMchntcdTwo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'mchtLvl',mapping: 'mchtLvl'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'manuAuthFlag',mapping: 'manuAuthFlag'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'discConsFlg',mapping: 'discConsFlg'},
			{name: 'discConsRebate',mapping: 'discConsRebate'},
			{name: 'passFlag',mapping: 'passFlag'},
			{name: 'openDays',mapping: 'openDays'},
			{name: 'sleepDays',mapping: 'sleepDays'},
			{name: 'mchtCnAbbr',mapping: 'mchtCnAbbr'},
			{name: 'spellName',mapping: 'spellName'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mchtEnAbbr',mapping: 'mchtEnAbbr'},
			{name: 'areaNo',mapping: 'areaNo'},
			{name: 'addr',mapping: 'addr'},
			{name: 'homePage',mapping: 'homePage'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'tcc',mapping: 'tcc'},
			{name: 'etpsAttr',mapping: 'etpsAttr'},
			{name: 'mngMchtId',mapping: 'mngMchtId'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mchtAttr',mapping: 'mchtAttr'},
			{name: 'mchtGroupFlag',mapping: 'mchtGroupFlag'},
			{name: 'mchtGroupId',mapping: 'mchtGroupId'},
			{name: 'mchtEngNm',mapping: 'mchtEngNm'},
			{name: 'mchtEngAddr',mapping: 'mchtEngAddr'},
			{name: 'mchtEngCityName',mapping: 'mchtEngCityName'},
			{name: 'saLimitAmt',mapping: 'saLimitAmt'},
			{name: 'saAction',mapping: 'saAction'},
			{name: 'psamNum',mapping: 'psamNum'},
			{name: 'cdMacNum',mapping: 'cdMacNum'},
			{name: 'posNum',mapping: 'posNum'},
			{name: 'connType',mapping: 'connType'},
			{name: 'mchtMngMode',mapping: 'mchtMngMode'},
			{name: 'mchtFunction',mapping: 'mchtFunction'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'licenceEndDate',mapping: 'licenceEndDate'},
			{name: 'bankLicenceNo',mapping: 'bankLicenceNo'},
			{name: 'busType',mapping: 'busType'},
			{name: 'faxNo',mapping: 'faxNo'},
			{name: 'busAmt',mapping: 'busAmt'},
			{name: 'mchtCreLvl',mapping: 'mchtCreLvl'},
			{name: 'contact',mapping: 'contact'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			{name: 'commMobil',mapping: 'commMobil'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'manager1',mapping: 'manager1'},
			{name: 'artifCertifTp',mapping: 'artifCertifTp'},
			{name: 'identityNo',mapping: 'identityNo'},
			{name: 'managerTel',mapping: 'managerTel'},
			{name: 'fax',mapping: 'fax'},
			{name: 'electrofax',mapping: 'electrofax'},
			{name: 'regAddr',mapping: 'regAddr'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'enableDate',mapping: 'enableDate'},
			{name: 'preAudNm',mapping: 'preAudNm'},
			{name: 'confirmNm',mapping: 'confirmNm'},
			{name: 'protocalId',mapping: 'protocalId'},
			{name: 'signInstId',mapping: 'signInstId'},
			{name: 'netNm',mapping: 'netNm'},
			{name: 'agrBr',mapping: 'agrBr'},
			{name: 'netTel',mapping: 'netTel'},
			{name: 'prolDate',mapping: 'prolDate'},
			{name: 'prolTlr',mapping: 'prolTlr'},
			{name: 'closeDate',mapping: 'closeDate'},
			{name: 'closeTlr',mapping: 'closeTlr'},
			{name: 'operNo',mapping: 'operNo'},
			{name: 'operNm',mapping: 'operNm'},
			{name: 'procFlag',mapping: 'procFlag'},
			{name: 'setCur',mapping: 'setCur'},
      		{name: 'printInstId',mapping: 'printInstId'},
			{name: 'acqInstId',mapping: 'acqInstId'},
			{name: 'acqBkName',mapping: 'acqBkName'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'orgnNo',mapping: 'orgnNo'},
			{name: 'subbrhNo',mapping: 'subbrhNo'},
			{name: 'subbrhNm',mapping: 'subbrhNm'},
			{name: 'openTime',mapping: 'openTime'},
			{name: 'closeTime',mapping: 'closeTime'},
			{name: 'visActFlg',mapping: 'visActFlg'},
			{name: 'visMchtId',mapping: 'visMchtId'},
			{name: 'mstActFlg',mapping: 'mstActFlg'},
			{name: 'mstMchtId',mapping: 'mstMchtId'},
			{name: 'amxActFlg',mapping: 'amxActFlg'},
			{name: 'amxMchtId',mapping: 'amxMchtId'},
			{name: 'dnrActFlg',mapping: 'dnrActFlg'},
			{name: 'dnrMchtId',mapping: 'dnrMchtId'},
			{name: 'jcbActFlg',mapping: 'jcbActFlg'},
			{name: 'jcbMchtId',mapping: 'jcbMchtId'},
			{name: 'cupMchtFlg',mapping: 'cupMchtFlg'},
			{name: 'debMchtFlg',mapping: 'debMchtFlg'},
			{name: 'creMchtFlg',mapping: 'creMchtFlg'},
			{name: 'cdcMchtFlg',mapping: 'cdcMchtFlg'},
			{name: 'idOtherNo',mapping: 'idOtherNo'},
			{name: 'recUpdTs',mapping: 'recUpdTs'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'internalNo',mapping: 'internalNo'},
			{name: 'reject',mapping: 'reject'},
			{name: 'mchntAttr',mapping: 'mchntAttr'},
			{name: 'linkPer',mapping: 'linkPer'},
			{name: 'SettleAreaNo',mapping: 'SettleAreaNo'},
			{name: 'MainTlr',mapping: 'MainTlr'},
			{name: 'CheckTlr',mapping: 'CheckTlr'},
			{name: 'settleType',mapping: 'settleType'},
			{name: 'rateFlag',mapping: 'rateFlag'},
			{name: 'settleChn',mapping: 'settleChn'},
			{name: 'batTime',mapping: 'batTime'},
			{name: 'autoStlFlg',mapping: 'autoStlFlg'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'feeFixed',mapping: 'feeFixed'},
			{name: 'feeMaxAmt',mapping: 'feeMaxAmt'},
			{name: 'feeMinAmt',mapping: 'feeMinAmt'},
			{name: 'feeRate',mapping: 'feeRate'},
			{name: 'feeDiv1',mapping: 'feeDiv1'},
			{name: 'feeDiv2',mapping: 'feeDiv2'},
			{name: 'feeDiv3',mapping: 'feeDiv3'},
			{name: 'settleMode',mapping: 'settleMode'},
			{name: 'feeCycle',mapping: 'feeCycle'},
			{name: 'settleRpt',mapping: 'settleRpt'},
			{name: 'settleBankNo',mapping: 'settleBankNo'},
			{name: 'settleBankNm',mapping: 'settleBankNm'},
			{name: 'settleAcctNm',mapping: 'settleAcctNm'},
			{name: 'settleAcct',mapping: 'settleAcct'},
			{name: 'clearType',mapping: 'clearType'},
			{name: 'feeAcctNm',mapping: 'feeAcctNm'},
			{name: 'feeAcct',mapping: 'feeAcct'},
			{name: 'groupFlag',mapping: 'groupFlag'},
			{name: 'openStlno',mapping: 'openStlno'},
			{name: 'changeStlno',mapping: 'changeStlno'},
			{name: 'speSettleTp',mapping: 'speSettleTp'},
			{name: 'speSettleLv',mapping: 'speSettleLv'},
			{name: 'speSettleDs',mapping: 'speSettleDs'},
			{name: 'feeBackFlg',mapping: 'feeBackFlg'},
			{name: 'reserved',mapping: 'reserved'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'outChannel',mapping: 'outChannel'},
			{name: 'checkFrqc',mapping: 'checkFrqc'},
			{name: 'otscName',mapping: 'otscName'},
			{name: 'otscDivRate',mapping: 'otscDivRate'},
			{name: 'contactSnd',mapping: 'contactSnd'},
			{name: 'commEmailSnd',mapping: 'commEmailSnd'},
			{name: 'commMobilSnd',mapping: 'commMobilSnd'},
			{name: 'commTelSnd',mapping: 'commTelSnd'},
			{name: 'mchtPostEmail',mapping: 'mchtPostEmail'},
			{name: 'marketerName',mapping: 'marketerName'},
			{name: 'marketerContact',mapping: 'marketerContact'},
			{name: 'whiteListFlag',mapping: 'whiteListFlag'},
			{name: 'personSettleFlg',mapping: 'personSettleFlg'},
			{name: 'settleBankNmSnd',mapping: 'settleBankNmSnd'},
			{name: 'settleBankNoSnd',mapping: 'settleBankNoSnd'},
			{name: 'settleAcctNmSnd',mapping: 'settleAcctNmSnd'},
			{name: 'settleAcctSnd',mapping: 'settleAcctSnd'},
			{name: 'acctSettleRate',mapping: 'acctSettleRate'},
			{name: 'acctSettleLimit',mapping: 'acctSettleLimit'},
			{name: 'acctSettleType',mapping: 'acctSettleType'},
			{name: 'mchtFlag1',mapping: 'mchtFlag1'},
			{name: 'mchtFlag2',mapping: 'mchtFlag2'},
			{name: 'mcht_no',mapping: 'mcht_no'},
			{name: 'inner_acq_inst_id',mapping: 'inner_acq_inst_id'},
			{name: 'acq_inst_id_code',mapping: 'acq_inst_id_code'},
			{name: 'inst_id',mapping: 'inst_id'},
			{name: 'mcht_nm',mapping: 'mcht_nm'},
			{name: 'mcht_short_cn',mapping: 'mcht_short_cn'},
			{name: 'area_no',mapping: 'area_no'},
			{name: 'inner_stlm_ins_id',mapping: 'inner_stlm_ins_id'},
			{name: 'mcht_type',mapping: 'mcht_type'},
			{name: 'mcht_status',mapping: 'mcht_status'},
			{name: 'acq_area_cd',mapping: 'acq_area_cd'},
			{name: 'settle_flg',mapping: 'settle_flg'},
			{name: 'conn_inst_cd',mapping: 'conn_inst_cd'},
			{name: 'mchnt_tp_grp',mapping: 'mchnt_tp_grp'},
			{name: 'mchnt_srv_tp',mapping: 'mchnt_srv_tp'},
			{name: 'real_mcht_tp',mapping: 'real_mcht_tp'},
			{name: 'settle_area_cd',mapping: 'settle_area_cd'},
			{name: 'mcht_acq_curr',mapping: 'mcht_acq_curr'},
			{name: 'conn_type',mapping: 'conn_type'},
			{name: 'currcy_cd',mapping: 'currcy_cd'},
			{name: 'deposit_flag',mapping: 'deposit_flag'},
			{name: 'res_pan_flag',mapping: 'res_pan_flag'},
			{name: 'res_track_flag',mapping: 'res_track_flag'},
			{name: 'process_flag',mapping: 'process_flag'},
			{name: 'cntry_code',mapping: 'cntry_code'},
			{name: 'ch_store_flag',mapping: 'ch_store_flag'},
			{name: 'mcht_tp_reason',mapping: 'mcht_tp_reason'},
			{name: 'mcc_md',mapping: 'mcc_md'},
			{name: 'rebate_flag',mapping: 'rebate_flag'},
			{name: 'mcht_acq_rebate',mapping: 'mcht_acq_rebate'},
			{name: 'rebate_stlm_cd',mapping: 'rebate_stlm_cd'},
			{name: 'reason_code',mapping: 'reason_code'},
			{name: 'rate_type',mapping: 'rate_type'},
			{name: 'fee_cycle',mapping: 'fee_cycle'},
			{name: 'fee_type',mapping: 'fee_type'},
			{name: 'limit_flag',mapping: 'limit_flag'},
			{name: 'fee_rebate',mapping: 'fee_rebate'},
			{name: 'settle_amt',mapping: 'settle_amt'},
			{name: 'amt_top',mapping: 'amt_top'},
			{name: 'amt_bottom',mapping: 'amt_bottom'},
			{name: 'disc_cd',mapping: 'disc_cd'},
			{name: 'fee_type_m',mapping: 'fee_type_m'},
			{name: 'limit_flag_m',mapping: 'limit_flag_m'},
			{name: 'settle_amt_m',mapping: 'settle_amt_m'},
			{name: 'amt_top_m',mapping: 'amt_top_m'},
			{name: 'amt_bottom_m',mapping: 'amt_bottom_m'},
			{name: 'disc_cd_m',mapping: 'disc_cd_m'},
			{name: 'fee_rate_type',mapping: 'fee_rate_type'},
			{name: 'settle_bank_no',mapping: 'settle_bank_no'},
			{name: 'settle_bank_type',mapping: 'settle_bank_type'},
			{name: 'advanced_flag',mapping: 'advanced_flag'},
			{name: 'prior_flag',mapping: 'prior_flag'},
			{name: 'open_stlno',mapping: 'open_stlno'},
			{name: 'feerate_index',mapping: 'feerate_index'},
			{name: 'rate_role',mapping: 'rate_role'},
			{name: 'rate_disc',mapping: 'rate_disc'},
			{name: 'cycle_mcht',mapping: 'cycle_mcht'},
			{name: 'mac_chk_flag',mapping: 'mac_chk_flag'},
			{name: 'fee_div_mode',mapping: 'fee_div_mode'},
			{name: 'settle_mode',mapping: 'settle_mode'},
			{name: 'attr_bmp',mapping: 'attr_bmp'},
			{name: 'cycle_settle_type',mapping: 'cycle_settle_type'},
			{name: 'report_bmp',mapping: 'report_bmp'},
			{name: 'day_stlm_flag',mapping: 'day_stlm_flag'},
			{name: 'cup_stlm_flag',mapping: 'cup_stlm_flag'},
			{name: 'adv_ret_flag',mapping: 'adv_ret_flag'},
			{name: 'mcht_file_flag',mapping: 'mcht_file_flag'},
			{name: 'fee_act',mapping: 'fee_act'},
			{name: 'licence_no',mapping: 'licence_no'},
			{name: 'licence_add',mapping: 'licence_add'},
			{name: 'principal',mapping: 'principal'},
			{name: 'comm_tel',mapping: 'comm_tel'},
			{name: 'manager',mapping: 'manager'},
			{name: 'mana_cred_tp',mapping: 'mana_cred_tp'},
	      	{name: 'mana_cred_no',mapping: 'mana_cred_no'},
			{name: 'capital_sett_cycle',mapping: 'capital_sett_cycle'},
			{name: 'card_in_start_date',mapping: 'card_in_start_date'},
			{name: 'settle_acct',mapping: 'settle_acct'},
			{name: 'mcht_e_nm',mapping: 'mcht_e_nm'},
			{name: 'settle_bank',mapping: 'settle_bank'},
			{name: 'rate_no',mapping: 'rate_no'},
			{name: 'card_in_settle_bank',mapping: 'card_in_settle_bank'},
			{name: 'fee_spe_type',mapping: 'fee_spe_type'},
			{name: 'fee_spe_gra',mapping: 'fee_spe_gra'},
			{name: 'routingFlag',mapping: 'routingFlag'}
		]),
		autoLoad: false
	});

	var fm = Ext.form;

	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});


//	var txnStore = new Ext.data.JsonStore({
//		fields: ['valueField','displayField'],
//		root: 'data',
//		id: 'valueField'
//	});
//
//	SelectOptionsDWR.getComboData('TXN_NUM_FEE',function(ret){
//		txnStore.loadData(Ext.decode(ret));
//	});
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('TXN_NUM_FEE',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('TXN_NUM_EPOS',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getDiscInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnNum',mapping: 'txnNum',type:'string'},
			{name: 'cardType',mapping: 'cardType',type:'string'},
			{name: 'floorMount',mapping: 'floorMount'},
			{name: 'minFee',mapping: 'minFee'},
			{name: 'maxFee',mapping: 'maxFee'},
			{name: 'flag',mapping: 'flag'},
			{name: 'feeValue',mapping: 'feeValue'}
		]),
		sortInfo: {field: 'floorMount', direction: 'ASC'},
		autoLoad: false
	});

	var cm = new Ext.grid.ColumnModel({
		columns: [{
            header: '交易类型',
            dataIndex: 'txnNum',
            width: 160,
            editor: {
					xtype: 'basecomboselect',
			        store: txnStore,
					id: 'idTxnNum',
					hiddenName: 'txnNum',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = txnStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '交易卡种',
            dataIndex: 'cardType',
            width: 120,
            editor: {
					xtype: 'basecomboselect',
			        store: cardTypeStore,
					id: 'idCardType',
					hiddenName: 'cardType',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = cardTypeStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '手续费类型',
            dataIndex: 'flag',
            width: 90,
            editor: {
					xtype: 'combofordispaly',
			        store: flagStore,
					id: 'idfalg',
					hiddenName: 'falg',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = flagStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '值',
            dataIndex: 'feeValue',
            width: 50
        },{
            header: '按比最低收费',
            dataIndex: 'minFee',
            width: 85
        },{
            header: '按比最高收费',
            dataIndex: 'maxFee',
            width: 85
        }]
	});

    var detailGrid = new Ext.grid.GridPanel({
		title: '详细信息',
		autoWidth: true,
		frame: true,
		border: true,
		height: 210,
		columnLines: true,
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true
	});
    var custom;
	var customEl;
	function showPIC(id){
 		var rec = storeImg.getAt(id.substring(5)).data;
 		custom.resizeTo(rec.width, rec.height);
 		var src = document.getElementById('showBigPic').src;

 		if(src.indexOf(rec.fileName) == -1){
	 		document.getElementById('showBigPic').src = "";
	 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName=' + rec.fileName;
 		}
 		customEl.center();
	    customEl.show(true);
 	}


	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfDiscCd'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'discCd',mapping: 'discCd'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'discOrg',mapping: 'discOrg'}
		])
	});

	gridStore.on('load',function(){
		store.load({
			params: {
				start: 0,
				discCd: baseStore.getAt(0).data.feeRate
			}
		});
	});

	var gridColumnModel = new Ext.grid.ColumnModel([
		{header: '计费代码',dataIndex: 'discCd',width: 90},
		{header: '计费名称',dataIndex: 'discNm',id:'discNm',width:190},
		{header: '所属机构',dataIndex: 'discOrg',width:180,renderer:function(val){return getRemoteTrans(val, "brh");}}
	]);
	var gridPanel = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 210,
		autoWidth: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		disableSelection: true,
		cm: gridColumnModel,
		forceValidation: true
	});

	var flag2Store = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
        items: [{
        	layout:'column',
        	items: [{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'MCHT_FALG1',
					fieldLabel: '商户性质1',
					id: 'idMchtFlag1',
					hiddenName: 'mchtFlag1'
		        }]
        	},{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'combofordispaly',
			        store:flag2Store,
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户性质2*',
					id: 'idMchtFlag2',
					hiddenName: 'mchtFlag2'
		        }]
        	},{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'CONN_TYPE',
					fieldLabel: '商户接入方式',
					hiddenName: 'connType'
		        	}
        		]
        	},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'BRH_BELOW',
					fieldLabel: '商户所在地区',
					hiddenName: 'signInstId',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'displayfield',
					fieldLabel: '&nbsp;商户全称',
					id: 'mchtNm',
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '商户简称',
						id: 'mchtCnAbbr',
						anchor: '90%'
		        	}]
			},{
        		columnWidth: .33,
       			xtype: 'panel',
	        	layout: 'form',
       			items: [{
			        xtype: 'displayfield',
					fieldLabel: '商户受理机构',
					id: 'acqInstId',
					anchor:'90%'
	       		}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '&nbsp;英文名称',
						id: 'engName',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
						xtype: 'combofordispaly',
			        	baseParams: 'CITY_CODE',
						fieldLabel: '商户装机地区',
						hiddenName: 'areaNo',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'combofordispaly',
			        	baseParams: 'MCHNT_GRP_ALL',
						fieldLabel: '商户组别',
						hiddenName: 'mchtGrp',
						anchor: '90%'
		        	}]
			},{
        		columnWidth: .34,
       			xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'checkbox',
					//labelStyle: 'padding-left: 5px',
					fieldLabel: '白名单商户标志',
					disabled: true,
					id: 'whiteListFlag',
					name: 'whiteListFlag'
	        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'combofordispaly',
						fieldLabel: '商户MCC',
						store: mchntMccStore,
						hiddenName: 'mcc',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '商户号1',
						id: 'mappingMchntcdOne',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '商户号2',
						id: 'mappingMchntcdTwo',
						anchor: '90%'
		        	}]
			},{
				columnWidth: .34,
       			xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'combofordispaly',
			        baseParams: 'CUP_BRH_SETT',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '&nbsp;银联清算机构',
					hiddenName: 'printInstId',
					hidden:true,
					hideLabel:true,
					anchor: '90%'
	        	}]
			}]
        },{
        	xtype: 'tabpanel',
        	id: 'tab',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 320,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 180,
        	items:[{
                title:'基本信息',
                id: 'basic',
                frame: true,
				layout:'column',
                items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '营业执照编号',
						id: 'licenceNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '商户地址',
						name: 'addr'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '巡检频率(天)',
						name: 'checkFrqc'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id:'accountHide3',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '法人代表（负责人）*',
						width:250,
						maxLength: 10,
						vtype: 'isOverMax',
						id: 'manager1'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id:'accountHide4',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	baseParams: 'CERTIFICATE_CUP',
						fieldLabel: '法人代表(负责人)证件类型*',
						width:250,
						allowBlank: false,
						hiddenName: 'artifCertifTp',
						id: 'idartifCertifTp'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id:'accountHide5',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '法人代表(负责人)证件号码*',
						width:250,
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'identityNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '联系人',
						name: 'contact'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '联系人手机',
						name: 'commMobil'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '联系人电话',
						name: 'commTel'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '联系人电子邮件',
						name: 'commEmail'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '联系人二',
						name: 'contactSnd'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '联系人二手机',
						name: 'commMobilSnd'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '联系人二电话',
						name: 'commTelSnd'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '联系人二电子邮件',
						name: 'commEmailSnd'
		        	}]
				},{
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '邮政编码',
						name: 'postCode'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '商户对账单邮箱',
						name: 'mchtPostEmail'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '单笔限额(分)',
						name: 'homePage'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id:'accountHiderouting',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	baseParams: 'ROUTING_FLAG',
						fieldLabel: '路由信息*',
						width:250,
						allowBlank: false,
						hiddenName: 'routingFlag',
						id: 'idroutingflag'
		        	}]
				}]
            },{
                title:'附加信息',
                layout:'column',
                id: 'append',
                frame: true,
                items: [{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						fieldLabel: '是否支持他行借记卡',
						disabled: true,
						id: 'cupMchtFlg',
						name: 'cupMchtFlg'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
			        	disabled: true,
						fieldLabel: '&nbsp;是否支持他行贷记卡',
						id: 'debMchtFlg',
						name: 'debMchtFlg'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
			        	disabled: true,
						fieldLabel: '是否支持本行借记卡',
						id: 'creMchtFlg',
						name: 'creMchtFlg'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
			        	disabled: true,
						fieldLabel: '&nbsp;是否支持本行贷记卡',
						id: 'cdcMchtFlg',
						name: 'cdcMchtFlg'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'displayfield'
	        		},
	       			items: [
//	       			        
					{
						fieldLabel: '协议编号',
						id: 'protocalId',
						name: 'protocalId'
		        	},{
						fieldLabel: '归属机构',
						xtype: 'combofordispaly',
			        	baseParams: 'BRH_BELOW',
						id: 'agrBr',
						hiddenName: 'agrBrHd'
		        	},{
						fieldLabel: '营销员工号',
						id: 'operNo',
						name: 'operNo'
		        	},{
		        		fieldLabel: '营销员',
						id: 'marketerName',
						name: 'marketerName'
					},{
						fieldLabel: '商户营业开始时间',
						id: 'openTime',
						name: 'openTime'
					}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'displayfield',
	        			labelStyle: 'padding-left: 5px'
	        		},
	       			items: [{
						fieldLabel: '批准人',
						id: 'confirmNm',
						name: 'confirmNm'
					}
//	       			,{
//						fieldLabel: '客户经理电话',
//						id: 'netTel',
//						name: 'netTel'
//		        	}
	       			,{
						fieldLabel: '营销方',
						id: 'operNm',
						name: 'operNm'
		        	},{
						fieldLabel: '营销员联系方式',
						id: 'marketerContact',
						name: 'marketerContact'
		        	},{
						fieldLabel: '商户营业结束时间',
						id: 'closeTime',
						name: 'closeTime'
					}]
				}]
			},{
                title:'清算信息',
                layout:'column',
                id: 'settle',
                readOnly: true,
                frame: true,
                items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	baseParams: 'SETTLE_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户结算周期*',
						hiddenName: 'settleType',
						width: 150,
						anchor: '55%'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '商户开户行同城清算号',
						width:150,
						id: 'openStlno'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '商户开户行同城交换号',
						width:150,
						id: 'changeStlno'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combofordispaly',
						store: acctSettleTypeStore,
						labelStyle: 'padding-left: 5px',
						displayField: 'displayField',
						valueField: 'valueField',
						hiddenName: 'acctSettleType',
						fieldLabel: '账户清算方式*',
						anchor: '55%',
						listWidth: 150
	       			}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combofordispaly',
						store: clearTypeStore1,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '账户1结算类型',
						width:150,
						id: 'clearType'
					}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户账户1开户行名称*',
						width:150,
						id: 'settleBankNm'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '账户1所属行联行号*',
						width:150,
						id: 'settleBankNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户账户1户名*',
						width:150,
						id: 'settleAcctNm_nouse'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'displayfield',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户账户1账号*',
                        maxLength: 39,
						vtype: 'isOverMax',
                        width:150,
                        id: 'settleAcct'
                    }]
				}//下面是第二账户信息  支持双账户则显示
				,{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '支付宝账号',
						width:150,
						allowBlank: true,
//						disabled:true,
						id: 'acctSettleRate'
		        	}]
				},
				{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '账户1分账限额(元)*',
						maxLength: 15,
						vtype: 'isOverMax',
						width:150,
						allowBlank: true,
						disabled:true,
						regex:/^[0-9]{1,12}\.[0-9]{2}$/,
						regexText:"请输入正的不超过十五位的带两位小数的数字",
						id: 'acctSettleLimit'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'dynamicCombo',
   			            methodName: 'getShareRole',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润规则',
						allowBlank: true,
						editable:true,
						//blankText: '请选择分润规则',
						id: 'idClearType2',
						hiddenName: 'clearType2',
						anchor: '85%',
						listWidth:300,
						readOnly:false
					}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户账户2开户行名称*',
						maxLength: 80,
						vtype: 'isOverMax',
						width:150,
						allowBlank: true,
						disabled:true,
						id: 'settleBankNmSnd'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '账户2所属行联行号*',
						maxLength: 12,
						vtype: 'isOverMax',
						width:150,
						allowBlank: true,
						disabled:true,
						id: 'settleBankNoSnd'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户账户2户名*',
						maxLength: 80,
						vtype: 'isOverMax',
						width:150,
						allowBlank: true,
						disabled:true,
						id: 'settleAcctNmSnd'
		        	}]
				},{
	        		columnWidth: .35,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户账户2账号*',
                        maxLength: 39,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        width:150,
                        allowBlank: true,
						disabled:true,
                        id: 'settleAcctSnd'
                    }]
				}]
			},{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                	region: 'north',
                	height: 35,
                	layout: 'column',
                	items: [{
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					xtype: 'displayfield',
	       					fieldLabel: '计费代码',
							id: 'discCode',
							name: 'discCode',
							readOnly: true
						}]
					},{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
	       					xtype: 'button',
							iconCls: 'detail',
							text:'计费算法配置说明',
							id: 'detailbu',
							width: 60,
							handler: function(){
								Ext.MessageBox.show({
									msg: '<font color=red>第一步</font>：确定交易类型，每个交易类型都需要配置；（非固话POS商户计费算法配置跳过该步。）<font color=red>第二步</font>：确定交易卡种，一次性选择全卡种或分四次选择不同卡种；<br>' +
										  '<font color=red>第三步</font>：确定手续费计费方式，分每笔按固定金额，按比例，按比例保底，按比例封顶，按比例保底加封顶。<br>' +
										  '<font color=red>第四步</font>：具体配置指定卡种范围指定手续费计费方式的参数<br>' +
										  '&nbsp&nbsp<font color=green>1)</font>：按固定金额方式：在“值”一栏填写手续费收取金额，如：0.30元/笔输入0.30；<br>'+
										  '&nbsp&nbsp<font color=green>2)</font>：按比例方式：在“值”一栏填写手续费占交易金额百分比，如0.78%输入0.78；<br>'+
										  '&nbsp&nbsp<font color=green>3)</font>：按比例加固定金额限制方式：<br>'+
										  '&nbsp&nbsp&nbsp&nbsp<font color=gray>a</font>：按比例保底：在“值”一栏填写手续费占交易金额百分比，在“按笔最低收费”一栏填写保底金额，如：0.3%保底1.20元，即在“值”项输入0.3、在“按笔最低收费”项输入1.2；<br>'+
										  '&nbsp&nbsp&nbsp&nbsp<font color=gray>b</font>：按比例封顶：在“值”一栏填写手续费占交易金额百分比，在“按笔最高收费”一栏填写封顶金额，如：1.228%封顶80元，即在“值”项输入1.228、在“按笔最高收费”项输入80；<br>'+
										  '&nbsp&nbsp&nbsp&nbsp<font color=gray>c</font>：按比例封顶加保底：在“值”一栏填写手续费占交易金额百分比，在“按笔最低收费”和“按笔最高收费”一栏分别填写保底和封顶金额，如：1.228%保底20封顶80元，即在“值”项输入1.228、在“按笔最低收费”项输入20、在“按笔最高收费”项输入80。<br>',
									title: '计费算法配置说明',
									animEl: Ext.get(mchntForm.getEl()),
									buttons: Ext.MessageBox.OK,
									modal: true,
									width: 650
								});
							}
                		}]
                	}]
                },{
                	region: 'center',
                	items:[gridPanel]
                },{
                	region: 'east',
//                	width:600,
                	items: [detailGrid]
                }]
		    },{
        		title:'直联基本',
        		xtype: 'panel',
                id: 'cupBasic',
                disabled:true,
                frame: true,
				layout:'column',
	            items: [{
	        		columnWidth: .33,
	            	layout: 'form',
	        		items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'MCHT_CUP_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户服务类型*',
						id: 'idmchnt_srv_tp',
						hiddenName: 'mchnt_srv_tp',
//						allowBlank: false,
						anchor: '90%',
						value: '00'
			        }]
			    },{
		        	columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
			        	xtype: 'combofordispaly',
						labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-普通商户'],['1','1-仿公益类商户'],['2','2-周期商户']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户归类',
						id: 'cycle_mchtI',
						hiddenName: 'cycle_mcht',
						value:'0',
						anchor: '90%',
						'listeners':{
								'select': function(){
									var mchtTp = mchntForm.getForm().findField('cycle_mchtI').getValue();
									
									if(mchtTp == 1){
										mchntForm.getForm().findField('rate_typeI').allowBlank = false;
									}else{
										mchntForm.getForm().findField('rate_typeI').allowBlank = true;
									}
								}
							}
		        	}]
				},{
					columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combofordispaly',
			        		labelStyle: 'padding-left: 5px',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['156','156-中国']],
								reader: new Ext.data.ArrayReader()
							}),
							fieldLabel: '国家代码*',
							id: 'cntry_codeI',
							hiddenName: 'cntry_code',
							value: '156',
							anchor: '90%'
			        	}]
				},{
	        		columnWidth: .5,
	        		xtype: 'panel',
			        layout: 'form',
		       		items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'CUP_BRH_S',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '内卡收单机构*',
//						allowBlank: false,
						hiddenName: 'inner_acq_inst_id',
						anchor: '90%'
			        }]
				},{
//		        	columnWidth: .33,
//		        	layout: 'form',
//		        	xtype: 'panel',
//		        	hidden:true,
//		       		items: [{
//			        	xtype: 'combofordispaly',
//			        	baseParams: 'CUP_BRH_S',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '代理机构*32',
//						id: 'mchtCup5I',
//						hiddenName: 'mchtCup5',
//						anchor: '90%'
//		        	}]
//				},{
					columnWidth: .5,
		        	xtype: 'panel',
			        layout: 'form',
		       		items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'CUP_BRH_S',
						hiddenName: 'inner_stlm_ins_id',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '内卡结算机构*',
//						allowBlank: false,
						anchor: '90%'
			        }]
				},{
		        	columnWidth: .33,
		        	xtype: 'panel',
			        layout: 'form',
		       		items: [{
		       			xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '所属平台机构*',
						id: 'inst_id',
						name: 'inst_id',
						value: '0800093600',
//						allowBlank: false,
						anchor: '90%'
			        }]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
			        layout: 'form',
		       		items: [{
		       			xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收单接入机构*',
						id: 'conn_inst_cd',
						name: 'conn_inst_cd',
						value: '0800093600',
//						allowBlank: false,
						blankText: '请选接入机构',
						anchor: '90%'
			        }]
				},{
		        	columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
							xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '行政区划码*',
//							allowBlank: false,
							id: 'area_no',
							name: 'area_no',
							regex: /^[0-9]{6}$/,
							regexText: '行政区划码必须是6位数字',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
//							xtype: 'combofordispaly',
							xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
//							methodName: 'getAreaCode',
							fieldLabel: '受理地区码*',
							name: 'acq_area_cd',
//							allowBlank: false,
							anchor: '90%'
			        	}]
				},{
					columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
//							xtype: 'combofordispaly',
							xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
//							methodName: 'getAreaCode',
							fieldLabel: '清算地区码*',
							name: 'settle_area_cd',
//							allowBlank: false,
							anchor: '90%'
			        	}]
				},{
					columnWidth: .99,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combofordispaly',
				        	store: cupMccStore,
				        	displayField: 'displayField',
							valueField: 'valueField',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户交易类型*',
//							allowBlank: false,
							id: 'mcht_typeI',
							hiddenName: 'mcht_type',
							anchor: '90%',
							'listeners':{
								'select': function(){
									var amMchtTp = mchntForm.getForm().findField('mcht_typeI').getValue();
									var realMchtTp = mchntForm.getForm().findField('real_mcht_tpI').getValue();
									
									if(amMchtTp != realMchtTp){
										mchntForm.getForm().findField('mcht_tp_reasonI').allowBlank = false;
										mchntForm.getForm().findField('mcc_mdI').allowBlank = false;
									}else{
										mchntForm.getForm().findField('mcht_tp_reasonI').allowBlank = true;
										mchntForm.getForm().findField('mcc_mdI').allowBlank = true;
									}
								}
							}
			        	}]
				},{
					columnWidth: .99,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combofordispaly',
				        	store: cupMccStore,
				        	displayField: 'displayField',
							valueField: 'valueField',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '真实商户类型*',
//							allowBlank: false,
							id: 'real_mcht_tpI',
							hiddenName: 'real_mcht_tp',
							anchor: '99%',
							'listeners':{
								'select': function(){
									var amMchtTp = mchntForm.getForm().findField('mcht_typeI').getValue();
									var realMchtTp = mchntForm.getForm().findField('real_mcht_tpI').getValue();
									
									if(amMchtTp != realMchtTp){
										mchntForm.getForm().findField('mcht_tp_reasonI').allowBlank = false;
										mchntForm.getForm().findField('mcc_mdI').allowBlank = false;
									}else{
										mchntForm.getForm().findField('mcht_tp_reasonI').allowBlank = true;
										mchntForm.getForm().findField('mcc_mdI').allowBlank = true;
									}
								}
							}
			        	}]
				},{
		        	columnWidth: .33,
		        	xtype: 'panel',
			        layout: 'form',
		       		items: [{
							xtype: 'combofordispaly',
				        	baseParams: 'MCHNT_GRP_CUP',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户组别*',
//							allowBlank: false,
							hiddenName: 'mchnt_tp_grp',
							id:'mchnt_tp_grpI',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combofordispaly',
				        	baseParams: 'APP_REASON',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '套用商户类型原因',
							vtype: 'isOverMax',
							id: 'mcht_tp_reasonI',
							hiddenName: 'mcht_tp_reason',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combofordispaly',
				        	baseParams: 'MCC_MD',
							labelStyle: 'padding-left: 5px',
							fieldLabel: 'MCC套用依据',
							id: 'mcc_mdI',
							hiddenName: 'mcc_md',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combofordispaly',
				        	baseParams: 'CH_STORE_FLAG',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '连锁店标识*',
							width:150,
							id: 'ch_store_flagI',
							hiddenName: 'ch_store_flag',
							value: '0',
//							allowBlank: false,
							anchor: '90%',
							'listeners':{
                        		'select': function() {
                        			mchntForm.getForm().findField('mcht_file_flagI').reset();
                        		}
							}
			        	}]
				},{
                  	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不生成'],['1','1-生成自身流水文件'],['2','2-生成子商户流水文件']],
							reader: new Ext.data.ArrayReader()
						}),
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户文件生成标志*',
                        id: 'mcht_file_flagI',
						hiddenName: 'mcht_file_flag',
						value: '0',
//						allowBlank: false,
                        anchor: '90%',
                        'listeners':{
                        	'select': function() {
							     
							    var storeFlag = mchntForm.getForm().findField('ch_store_flagI').getValue();
							    var fileFlag = mchntForm.getForm().findField('mcht_file_flagI').getValue();
							    
							    if(storeFlag == ''|| storeFlag == null){
							    	mchntForm.getForm().findField('mcht_file_flagI').reset();
							    	showMsg('请先选择连锁店标识！',mchntForm);
							    }else{
							    	if(storeFlag != '1' && fileFlag =='2'){
								    	mchntForm.getForm().findField('mcht_file_flagI').reset();
								    	showMsg('仅在连锁店标识为“根总店”时可选“2-生成子商户流水文件”！',mchntForm);
							    	}
							    }
                        	}
                        }
                    }]
				}]
		    },{
                title:'直联签约',
                xtype: 'panel',
                disabled:true,
                id: 'sign',
                frame: true,
				layout:'column',
                items: [{
                	columnWidth: .5,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['80000000000000000000','80000000000000000000-人民币']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户受理币种*',
							maxLength: 40,
//							allowBlank: false,
							vtype: 'isOverMax',
							value:'80000000000000000000',
							id: 'mcht_acq_currI',
							hiddenName: 'mcht_acq_curr',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .5,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['156','156-人民币']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户默认交易币种*',
							maxLength: 40,
//							allowBlank: false,
							vtype: 'isOverMax',
							value:'156',
							id: 'currcy_cdI',
							hiddenName: 'currcy_cd',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        disabled: true,
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否向商户收取押金 *',
	                        id: 'deposit_flag',
	                        name: 'deposit_flag'
	                    }]
                },{
                	columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        disabled: true,
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '商户是否保存卡号 *',
	                        id: 'res_pan_flag',
	                        name: 'res_pan_flag'
	                    }]
                },{
                	columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        disabled: true,
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '商户是否保存磁道信息 ',
	                        id: 'res_track_flag',
	                        name: 'res_track_flag'
	                    }]
                },{
                	columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        disabled: true,
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否向商户收取服务费 ',
	                        id: 'process_flag',
	                        name: 'process_flag'
	                    }]
                },{
//                	columnWidth: .5,
//		        	layout: 'form',
//		        	xtype: 'panel',
//		       		items: [{
//				        	xtype: 'checkbox',
//	                        disabled: true,
//							labelStyle: 'padding-left: 5px',
//							fieldLabel: '是否为周期商户*',
//							id: 'cycle_mcht',
//							name: 'cycle_mcht'
//			        	}]
//				},{
					columnWidth: .5,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['1','1-校验MAC、交易加密'],['2','2-校验MAC、交易不加密'],
									   ['3','3-不校验MAC、交易加密'],['4','4-不校验MAC、交易不加密']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: 'MAC校验和交易加密标志*',
							id: 'mac_chk_flagI',
							hiddenName: 'mac_chk_flag',
							value:'4',
							anchor: '90%'
			        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	id:'accountHide1',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
						xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照编号',
						maxLength: 20,
						id: 'licence_no',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业地址',
						maxLength: 60,
						vtype: 'isOverMax',
						id: 'licence_add',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '负责人',
						maxLength: 10,
						vtype: 'isOverMax',
						id: 'principal',
						anchor: '90%'
		        	}]
		        },{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '固定电话',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maxLength: 15,
						vtype: 'isOverMax',
						id: 'comm_tel',
						anchor: '90%'
		        	}]
//				},{
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'displayfield',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '法人代表*',
//						maxLength: 20,
////						allowBlank: false,
//						vtype: 'isOverMax',
//						id: 'manager',
//						anchor: '90%'
//		        	}]
//				},{
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'combofordispaly',
//			        	baseParams: 'CERTIFICATE_CUP',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '法人代表证件类型*',
////						allowBlank: false,
//						value: '01',
//						hiddenName: 'mana_cred_tp',
//						id: 'mana_cred_tpI',
//						anchor: '90%'
//		        	}]
//				},{
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'displayfield',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '法人证件号*',
//						maxLength: 20,
////						allowBlank: false,
//						vtype: 'isOverMax',
//						id: 'mana_cred_no',
//						anchor: '90%'
//		        	}]
				}]
		    },{
                title:'直联清算',
                xtype: 'panel',
                layout:'column',
                id: 'cupSettle',
                disabled:true,
                frame: true,
                items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'rebateFlag',
	       			items: [{
			        	xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','1-固定比例'],['2','2-固定金额'],
								   ['3','3-算法描述']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '扣率算法标识*',
						id: 'rebate_flagI',
						hiddenName: 'rebate_flag',
//						allowBlank: false,
						anchor: '90%',
						listeners: {
						      'select': function() {
							     
							    var rebateFlag = mchntForm.getForm().findField('rebate_flagI').getValue();
	                    	
		                    	if(rebateFlag == '1'){
		                    		Ext.getCmp('rebateACQH').hide();
		                    		Ext.getCmp('rebateACQW').show();
		                    		Ext.getCmp('rebate_stlm_cd').disable();
		                    		
		                    		Ext.getCmp('mchtCup401').allowBlank = false;
		                    		Ext.getCmp('mchtCup40').allowBlank = true;
		                    		Ext.getCmp('rebate_stlm_cd').allowBlank = true;
		                    		
		                    		mchntForm.getForm().findField("mchtCup40").reset();
		                    		mchntForm.getForm().findField("mchtCup401").reset();
		                    		mchntForm.getForm().findField("rebate_stlm_cd").reset();
			                     	
		                    	}else if(rebateFlag == '2'){
		                    		Ext.getCmp('rebateACQW').hide();
		                    		Ext.getCmp('rebateACQH').show();
		                    		Ext.getCmp('rebate_stlm_cd').disable();
		                    		
		                    		Ext.getCmp('mchtCup40').allowBlank = false;
		                    		Ext.getCmp('mchtCup401').allowBlank = true;
		                    		Ext.getCmp('rebate_stlm_cd').allowBlank = true;
		                    		
		                    		mchntForm.getForm().findField("mchtCup40").reset();
		                    		mchntForm.getForm().findField("mchtCup401").reset();
		                    		mchntForm.getForm().findField("rebate_stlm_cd").reset();
		                    	}else if(rebateFlag == '3'){
		                    		Ext.getCmp('rebateACQH').hide();
		                    		Ext.getCmp('rebateACQW').show();
		                    		
		                    		Ext.getCmp('mchtCup401').allowBlank = false;
		                    		Ext.getCmp('mchtCup40').allowBlank = true;
		                    		Ext.getCmp('rebate_stlm_cd').enable();
		                    		Ext.getCmp('rebate_stlm_cd').allowBlank = false;
			                     	
			                     	
			                     	mchntForm.getForm().findField("mchtCup40").reset();
		                    		mchntForm.getForm().findField("mchtCup401").reset();
		                    		mchntForm.getForm().findField("rebate_stlm_cd").reset();
		                    	}
					        }}
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'rebateACQH',
		        	labelWidth:160,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '签约商户内卡收单扣率(分)*',
						maxLength: 8,
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						id: 'mchtCup40',
						name: 'mchtCup40',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'rebateACQW',
		        	hidden: true,
		        	labelWidth:180,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '签约商户内卡收单扣率(%%)*',
						maxLength: 8,
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						id: 'mchtCup401',
						name: 'mchtCup401',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'stlmCd',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '扣率算法代码*',
						maxLength: 5,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						disabled: true,
						id: 'rebate_stlm_cd',
						name: 'rebate_stlm_cd',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combofordispaly',
	       				labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','1-清算到收单行'],['2','2-清算到开户行'],
								   ['3','3-清算到银联']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '清算机构类型*',
//						allowBlank: false,
						id: 'settle_bank_typeI',
						hiddenName: 'settle_bank_type',
						anchor: '90%',
						listeners:{
							'select': function() {
								var settleBrhTp = mchntForm.getForm().findField('settle_bank_typeI').getValue();
								
								if(settleBrhTp == '1'){
									mchntForm.getForm().findField('fee_act').allowBlank = false;
									mchntForm.getForm().findField('card_in_settle_bank').allowBlank = true;
									mchntForm.getForm().findField('settle_bank_no').allowBlank = true;
								}
								if(settleBrhTp == '2'){
									mchntForm.getForm().findField('fee_act').allowBlank = false;
									mchntForm.getForm().findField('card_in_settle_bank').allowBlank = false;
									mchntForm.getForm().findField('settle_bank_no').allowBlank = false;
								}
								if(settleBrhTp == '3'){
									mchntForm.getForm().findField('fee_act').allowBlank = true;
									mchntForm.getForm().findField('card_in_settle_bank').allowBlank = false;
									mchntForm.getForm().findField('settle_bank_no').allowBlank = false;
								}
							}
						}
					}]
				}, {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
//						methodName: 'getMchntCupBank',
						fieldLabel: '内卡清算资金开户行清算号',
						id: 'card_in_settle_bank',
						name: 'card_in_settle_bank',
						anchor: '90%'
		        	}]
		        },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '内卡清算资金开户行名称',
						id: 'settle_bank_no',
						name: 'settle_bank_no',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'displayfield',
		        		labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行清算号*',
//						allowBlank: false,
						id: 'open_stlno',
						name: 'open_stlno',
						anchor: '90%'
		        	}]
				},{
                	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行',
						id: 'settle_bank',
						name: 'settle_bank',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '内卡开始收单日期',
						format : 'Ymd',
						id: 'card_in_start_date',
						name: 'card_in_start_date',
						anchor: '90%'
					}]
				},{
		        	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '清算账户账号',
                        maxLength: 40,
						id: 'settle_acct',
						name: 'settle_acct',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'combofordispaly',
						labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-否'],['1','1-是']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '异常时收单垫付标志*',
//						allowBlank: false,
						value: '0',
						id: 'advanced_flagI',
						hiddenName: 'advanced_flag',
						anchor: '90%',
						listeners:{
							'select':function(){
								mchntForm.getForm().findField('prior_flagI').reset();
							}
						}
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combofordispaly',
		        		labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-普通商户'],['1','1-重点商户'],['2','2-收单T+1保障']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '商户优先标志*',
//						allowBlank: false,
						value: '0',
						id: 'prior_flagI',
						hiddenName: 'prior_flag',
						anchor: '90%',
						listeners: {
							'select': function() {
								var advanceFlag = mchntForm.getForm().findField('advanced_flagI').getValue();
								var priorFlag = mchntForm.getForm().findField('prior_flagI').getValue();
								
								if(advanceFlag =='' || advanceFlag == null){
									mchntForm.getForm().findField('prior_flagI').reset();
									showMsg('请先选择异常时收单垫付标志！',mchntForm);
								}else{
									if(advanceFlag == '0' && priorFlag == '2'){
										mchntForm.getForm().findField('prior_flagI').reset();
										showMsg('异常时收单垫付标志为否时商户优先标志只能为普通商户和重点商户！',mchntForm);
									}
									if(advanceFlag == '1' && (priorFlag =='0' || priorFlag =='1')){
										mchntForm.getForm().findField('prior_flagI').reset();
										showMsg('异常时收单垫付标志为是时商户优先标志只能为收单T+1保障！',mchntForm);
									}
								}
							}
						}
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','1-清算到本店'],['2','2-清算到总店']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '商户清算模式*',
//						allowBlank: false,
						labelStyle: 'padding-left: 5px',
						id: 'settle_modeI',
						hiddenName: 'settle_mode',
						value: '1',
						anchor: '90%'
			    	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '周期结算类型*',
//						allowBlank: false,
						id: 'cycle_settle_typeI',
						hiddenName: 'cycle_settle_type',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combofordispaly',
		        		labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-否'],['1','1-是']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '是否参加日间清算*',
//						allowBlank: false,
						id: 'day_stlm_flagI',
						hiddenName: 'day_stlm_flag',
						value: '0',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-否'],['1','1-是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '银联代理清算标识*',
//						allowBlank: false,
						id: 'cup_stlm_flagI',
						hiddenName: 'cup_stlm_flag',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combofordispaly',
		        		labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-部分回补'],['1','1-全部回补']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '垫付回补类型*',
//						allowBlank: false,
						id: 'adv_ret_flagI',
						hiddenName: 'adv_ret_flag',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
							reader: new Ext.data.ArrayReader()
						}),
		        		labelStyle: 'padding-left: 5px',
						fieldLabel: '本金清算周期',
//						allowBlank: false,
						id: 'capital_sett_cycleI',
						hiddenName: 'capital_sett_cycle',
						value: '0',
						anchor: '90%'
		        	}]
		        },{
					columnWidth: .25,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv',
		        	labelWidth: 120,
	       			items: [{
			        	xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分段计费模式： 段1',
						id: 'mchtCup54I',
						hiddenName: 'mchtCup54',
						width:100,
						value: '0',
						listeners: {
	                		'select': function() {
	                			var div = mchntForm.getForm().findField('mchtCup54I').getValue();
	                			
	                			if(div =='0'){
	                				mchntForm.getForm().findField("mchtCup541I").setValue('0');
	                				mchntForm.getForm().findField("mchtCup542I").setValue('0');
	                				mchntForm.getForm().findField("mchtCup543I").setValue('0');
	                				Ext.getCmp('mchtCup541I').readOnly = true;
	                				Ext.getCmp('mchtCup542I').readOnly = true;
	                				Ext.getCmp('mchtCup543I').readOnly = true;
	                			}else{
	                				mchntForm.getForm().findField("mchtCup541I").reset();
	                				mchntForm.getForm().findField("mchtCup542I").reset();
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				Ext.getCmp('mchtCup541I').readOnly = false;
	                				Ext.getCmp('mchtCup542I').readOnly = false;
	                				Ext.getCmp('mchtCup543I').readOnly = false;
	                			}
	                		}
						}
		        	}]
				},{
	        		columnWidth: .15,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv1',
		        	labelWidth: 30,
	       			items: [{
			        	xtype: 'combofordispaly',
			        	fieldLabel: '段2',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						id: 'mchtCup541I',
						hiddenName: 'mchtCup541',
						value: '0',
						width:100,
						listeners: {
	                		'select': function() {
	                			var div = mchntForm.getForm().findField('mchtCup54I').getValue();
	                			var div1 = mchntForm.getForm().findField('mchtCup541I').getValue();
	                			
	                			
	                			if(div1 =='0'){
	                				mchntForm.getForm().findField("mchtCup542I").setValue('0');
	                				mchntForm.getForm().findField("mchtCup543I").setValue('0');
	                				Ext.getCmp('mchtCup542I').readOnly = true;
	                				Ext.getCmp('mchtCup543I').readOnly = true;
	                			}else{
	                				if(div=='0'){
	                					mchntForm.getForm().findField("mchtCup541I").reset();
	                					showMsg('上段不启用时此段只能为不启用！',mchntForm);
	                				}
	                				mchntForm.getForm().findField("mchtCup542I").reset();
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				Ext.getCmp('mchtCup542I').readOnly = false;
	                				Ext.getCmp('mchtCup543I').readOnly = false;
	                			}
	                		}
						}
		        	}]
				},{
					columnWidth: .15,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv2',
		        	labelWidth: 30,
	       			items: [{
			        	xtype: 'combofordispaly',
			        	fieldLabel: '段3',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						id: 'mchtCup542I',
						hiddenName: 'mchtCup542',
						value: '0',
						width:100,
						listeners: {
	                		'select': function() {
	                			var div1 = mchntForm.getForm().findField('mchtCup541I').getValue();
	                			var div2 = mchntForm.getForm().findField('mchtCup542I').getValue();
	                			
	                			if(div2 =='0'){
	                				mchntForm.getForm().findField("mchtCup543I").setValue('0');
	                				Ext.getCmp('mchtCup543I').readOnly = true;
	                			}else{
	                				if(div1=='0'){
	                					mchntForm.getForm().findField("mchtCup542I").reset();
	                					showMsg('上段不启用时此段只能为不启用！',mchntForm);
	                				}
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				
	                				Ext.getCmp('mchtCup543I').readOnly = false;
	                			}
	                		}
						}
		        	}]
				},{
					columnWidth: .4,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv3',
		        	labelWidth: 30,
	       			items: [{
			        	xtype: 'combofordispaly',
			        	fieldLabel: '段4',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
//						readOnly: true,
						id: 'mchtCup543I',
						hiddenName: 'mchtCup543',
						value: '0',
						width:100,
//						anchor: '13%',
						listeners: {
	                		'select': function() {
	                			var div2 = mchntForm.getForm().findField('mchtCup542I').getValue();
	                			var div3 = mchntForm.getForm().findField('mchtCup543I').getValue();
	                			
	                			if(div3 !='0' && div2=='0'){
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				showMsg('上段不启用时此段只能为不启用！',mchntForm);
	                			}
	                		}
						}
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['00','00-无特殊计费类型'],['01','01-周期计费'],['02','02-微额打包'],['03','03-固定比例'],['04','04-县乡优惠'],['05','05-大商户优惠']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费类型',
						id: 'fee_spe_typeI',
						hiddenName: 'fee_spe_type',
						value:'00',
						anchor: '90%',
						listeners: {
	                		'select': function() {
	                			mchntForm.getForm().findField('fee_spe_graI').reset();
	                		}
						}
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-月结按MCC计费'],['1','1-月结不按MCC计费'],['2','2-普通商户'],['3','3-三农商户'],
								   ['4','4-大商户1级'],['5','5-大商户2级'],['6','6-大商户3级'],['7','7-无特殊计费档次'] ],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费档次',
						id: 'fee_spe_graI',
						hiddenName: 'fee_spe_gra',
						value:'7',
						anchor: '90%',
						listeners: {
	                		'select': function() {
	                			var feeSpeT = Ext.getCmp("fee_spe_typeI").getValue();
	                			var feeSpe = Ext.getCmp("fee_spe_graI").getValue();
	                			
	                			if(feeSpeT == null || feeSpeT ==''){
	                				mchntForm.getForm().findField('fee_spe_graI').reset();
	                				showMsg('请先选择特殊计费类型！',mchntForm);
	                			}else{
	                				if(feeSpeT =='01'&& feeSpe !='0' && feeSpe !='1'){
		                				mchntForm.getForm().findField('fee_spe_graI').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择月结按MCC计费或月结不按MCC计费！',mchntForm);
		                			}else if(feeSpeT =='04'&& feeSpe !='2' && feeSpe !='3'){
		                				mchntForm.getForm().findField('fee_spe_graI').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择普通商户或三农商户！',mchntForm);
		                			}else if(feeSpeT =='05'&& feeSpe !='4' && feeSpe !='5' && feeSpe !='6'){
		                				mchntForm.getForm().findField('fee_spe_graI').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择大商户1级或大商户2级或大商户3级！',mchntForm);
		                			}else if((feeSpeT =='00' || feeSpeT =='02' || feeSpeT =='03') && feeSpe !='7'){
		                				mchntForm.getForm().findField('fee_spe_graI').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择无特殊计费档次！',mchntForm);
		                			}
	                			}
	                		}
						}
		        	}]
				}]
		    },{
                title:'直联分润',
                xtype: 'panel',
                layout:'column',
                id: 'rate',
                disabled:true,
                frame: true,
                items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'combofordispaly',
				        baseParams: 'REASON_CODE',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '登记原因码*',
//                      allowBlank: false,
                        id: 'reason_codeI',
                        hiddenName: 'reason_code',
                        anchor: '90%'
                    }]
				} ,{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-正常结算类'],['1','1-特殊结算类（普通月结）'],['2','2-特殊结算类（包月类）'],['3','3-特殊结算类（月结封顶、保底类）']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '结算类型标识',
						id: 'rate_typeI',
                        hiddenName: 'rate_type',
						anchor: '90%'
						
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '手续费清算周期',
						value: '0',
						id: 'fee_cycleI',
                        hiddenName: 'fee_cycle',
						anchor: '90%'
		        	}]
				}, {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-固定比例（封顶、保底）'],['1','1-固定金额'],['2','2-算法代码']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收费类型-单笔',
						id: 'fee_typeI',
                        hiddenName: 'fee_type',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-普通'],['1','1-封顶'],['2','2-保底']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '封顶、保底信息-单笔',
						id: 'limit_flagI',
                        hiddenName: 'limit_flag',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '手续费扣率-单笔(%%)',
						maxLength: 8,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'fee_rebate',
                        name: 'fee_rebate',
                        value: '0',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '固定金额-单笔(分)', 
						maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'settle_amt',
                        name: 'settle_amt',
                        value: '0',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'displayfield',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '封顶值-单笔*(分)',
                        maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        id: 'amt_top',
                        name: 'amt_top',
                        value: '999999999999',
//                        allowBlank: false,
						anchor: '90%'
                    }]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '保底值-单笔*(分)',
                        maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        id: 'amt_bottom',
                        name: 'amt_bottom',
//                        allowBlank: false,
                        value: '0',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '算法代码-单笔',
						maxLength: 5,
						vtype: 'isOverMax',
						id: 'disc_cd',
						name: 'disc_cd',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-封顶/保底'],['1','1-固定金额'],['2','2-计费代码']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收费类型-月结类',
						id: 'fee_type_mI',
						hiddenName: 'fee_type_m',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-普通'],['1','1-封顶'],['2','2-保底']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '封顶、保底信息-月结类',
						id: 'limit_flag_mI',
						hiddenName: 'limit_flag_m',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '固定金额-月结类（分）',
						maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'settle_amt_m',
						name: 'settle_amt_m',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'displayfield',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '封顶值-月结*（分）',
                        maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        id: 'amt_top_m',
                        name: 'amt_top_m',
                        value: '999999999999',
						anchor: '90%'
                    }]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '保底值-月结类(分)',
						maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'amt_bottom_m',
						name: 'amt_bottom_m',
						value: '0',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '算法代码-月结类',
						maxLength: 5,
						vtype: 'isOverMax',
						id: 'disc_cd_m',
						name: 'disc_cd_m',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润方式（CUPS侧）',
						maxLength: 5,
						vtype: 'isOverMax',
						id: 'fee_rate_type',
						name: 'fee_rate_type',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润代码',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'rate_no',
						name: 'rate_no',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .38,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'textfield',
		        		labelStyle: 'padding-left: 5px',
						fieldLabel: '商户手续费决定索引',
						maxLength: 30,
						id: 'fee_act',
						name: 'fee_act',
						disabled:true,
						readOnly:true,
						anchor: '95%'
		        	}]
				},{
	        		columnWidth: .12,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'button',
                        text: '设置',
                        id: 'setButton',
                        disabled:true,
                        width: 120,
                        handler: function() {
							feeWin.show();
							feeWin.center();
                        }
                    }]
				},{
	        		columnWidth: .38,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户分润手续费索引*',
						readOnly:true,
						disabled:true,
						id: 'feerate_index',
						name: 'feerate_index',
						anchor: '95%'
		        	}]
		        }, {
		        	columnWidth: .12,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'button',
                        text: '设置',
                        id: 'setButton2',
                        disabled:true,
                        width: 120,
                        handler: function() {
							feeWin2.show();
							feeWin2.center();
                        }
                    }]
				},{
	        		columnWidth: .99,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
					    baseParams: 'MCHNT_CUP_FATE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润算法',
						id: 'rate_discI',
						hiddenName: 'rate_disc',
						anchor: '60%'
		        	}]
				},{
	        		columnWidth: .20,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 130,
	       			items: [{
			        	xtype: 'checkbox',
			        	disabled: true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润角色： 分润机构1',
						width: 150,
						id: 'mchtCup781',
						name: 'mchtCup781',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
			        	disabled: true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构2',
						id: 'mchtCup782',
						name: 'mchtCup782',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
			        	disabled: true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构3',
						id: 'mchtCup783',
						name: 'mchtCup783',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
			        	disabled: true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构4',
						id: 'mchtCup784',
						name: 'mchtCup784',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
			        	disabled: true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构5',
						id: 'mchtCup785',
						name: 'mchtCup785',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 40,
	       			items: [{
			        	xtype: 'checkbox',
			        	disabled: true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行',
						id: 'mchtCup786',
						name: 'mchtCup786',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 40,
	       			items: [{
			        	xtype: 'checkbox',
			        	disabled: true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收单行',
						id: 'mchtCup787',
						name: 'mchtCup787',
						anchor: '90%'
		        	}]
				}]
		    },{
                title:'直联其他',
                xtype: 'panel',
                layout:'column',
                id: 'other',
                disabled:true,
                frame: true,
                items: [{
                    columnWidth: .35,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:260,
	       			items: [{
                        xtype: 'checkbox',
                        disabled: true,
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '报表生成标志位图* ：  是否生成汇总类报表',
                        id: 'mchtCup82',
						name: 'mchtCup82',
						checked: true,
						width:60
                    }]
                  },{
                  	columnWidth: .25,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:140,
	       			items: [{
                        xtype: 'checkbox',
                        disabled: true,
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '是否生成扣账垫付类报表',
                        id: 'mchtCup821',
						name: 'mchtCup821',
						checked: true,
                        anchor: '90%'
                    }]
                  },{
                  	columnWidth: .4,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:140,
		        	html:'<br/>',
	       			items: [{
                        xtype: 'checkbox',
                        disabled: true,
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '是否生成交易明细类报表',
                        id: 'mchtCup822',
						name: 'mchtCup822',
						checked: true,
                        anchor: '90%'
                    }]
                  },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:190,
	       			items: [{
                        xtype: 'checkbox',
                        disabled: true,
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户属性位图* : 是否收支两条线',//填充到10位
                        id: 'attr_bmp',
						name: 'attr_bmp',
                        anchor: '90%'
                    }]
                  }]
		    }
//			,{
//				title:'证书影像',
//                id: 'images',
//                frame: true,
//                layout: 'border',
//                items: [{
//                	xtype: 'panel',
//                	region: 'center',
//        			items : dataview,
//        			frame: true,
//        			tbar: [{
//        				xtype: 'button',
//						text: '刷新',
//						width: '80',
//						id: 'view',
//						handler:function() {
//							storeImg.reload({
//								params: {
//									start: 0,
//									imagesId: mchntId
//								}
//							});
//						}
//					}]
//                }]
//		    }
			]
        }]
    });

    var detailWin = new Ext.Window({
    	title: '商户详细信息',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 1080,
		autoHeight: true,
		items: [mchntForm],
		buttonAlign: 'center',
		closable: true,
		resizable: false
    });
 
	baseStore.load({
		params: {
			mchntId: mchntId
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
				
				/***************************直联商户数据部分*****************************/
             	var connType = baseStore.getAt(0).data.connType;
             	if(connType=='Z'){
					Ext.getCmp('cupBasic').enable();
					Ext.getCmp('sign').enable();
					Ext.getCmp('cupSettle').enable();
					Ext.getCmp('rate').enable();
					Ext.getCmp('other').enable();
					
					//签约商户内卡收单扣率
					var rebateFlag = baseStore.getAt(0).data.rebate_flag;
					var mchtAcqRebate = baseStore.getAt(0).data.mcht_acq_rebate;
					if(rebateFlag == '1'){
	            		Ext.getCmp('rebateACQH').hide();
	            		Ext.getCmp('rebateACQW').show();
	            		
	            		mchntForm.getForm().findField("rebate_stlm_cd").disable();
	            		mchntForm.getForm().findField("mchtCup401").setValue(mchtAcqRebate);
	            	}else if(rebateFlag == '2'){
	            		Ext.getCmp('rebateACQW').hide();
	            		Ext.getCmp('rebateACQH').show();
	            		
	            		mchntForm.getForm().findField("rebate_stlm_cd").disable();
	            		mchntForm.getForm().findField("mchtCup40").setValue(mchtAcqRebate);
	            	}else if(rebateFlag == '3'){
	            		Ext.getCmp('rebateACQH').hide();
	            		Ext.getCmp('rebateACQW').show();
	            		
	            		mchntForm.getForm().findField("rebate_stlm_cd").enable();
	            		mchntForm.getForm().findField("mchtCup401").setValue(mchtAcqRebate);
	            	}
	            	
	            	//分段计费
	            	var feeDivMode = baseStore.getAt(0).data.fee_div_mode;
	            	mchntForm.getForm().findField("mchtCup54I").setValue(feeDivMode.substr(0,1));
	            	mchntForm.getForm().findField("mchtCup541I").setValue(feeDivMode.substr(1,1));
					mchntForm.getForm().findField("mchtCup542I").setValue(feeDivMode.substr(2,1));
					mchntForm.getForm().findField("mchtCup543I").setValue(feeDivMode.substr(3,1));
					
					//分润机构
					var rateRole = baseStore.getAt(0).data.rate_role;
	            	mchntForm.getForm().findField("mchtCup781").setValue(rateRole.substr(0,1));
	            	mchntForm.getForm().findField("mchtCup782").setValue(rateRole.substr(1,1));
					mchntForm.getForm().findField("mchtCup783").setValue(rateRole.substr(2,1));
					mchntForm.getForm().findField("mchtCup784").setValue(rateRole.substr(3,1));
					mchntForm.getForm().findField("mchtCup785").setValue(rateRole.substr(4,1));
					mchntForm.getForm().findField("mchtCup786").setValue(rateRole.substr(5,1));
					mchntForm.getForm().findField("mchtCup787").setValue(rateRole.substr(6,1));
					
					//报表生成标志位图
					var reportBmp = baseStore.getAt(0).data.report_bmp;
	            	mchntForm.getForm().findField("mchtCup82").setValue(reportBmp.substr(0,1));
	            	mchntForm.getForm().findField("mchtCup821").setValue(reportBmp.substr(1,1));
					mchntForm.getForm().findField("mchtCup822").setValue(reportBmp.substr(2,1));
					
					//报表生成标志位图
					var attrBmp = baseStore.getAt(0).data.attr_bmp;
	            	mchntForm.getForm().findField("attr_bmp").setValue(attrBmp.substr(0,1));
             	}else if(connType=='J'){
					Ext.getCmp('cupBasic').disable();
					Ext.getCmp('sign').disable();
					Ext.getCmp('cupSettle').disable();
					Ext.getCmp('rate').disable();
					Ext.getCmp('other').disable();
             	}
             	
             	
             	/***************************间联商户数据部分*****************************/
				var discCode = baseStore.getAt(0).data.feeRate;
				Ext.getCmp('discCode').setValue(discCode);
				
				//商户性质2
				var flag1 = baseStore.getAt(0).data.mchtFlag1;
				
                flag2Store.removeAll();
    			SelectOptionsDWR.getComboDataWithParameter('MCHT_FALG2_BY_FLAG1',flag1,function(ret){
    				flag2Store.loadData(Ext.decode(ret));
    				mchntForm.getForm().findField('mchtFlag2').setValue(baseStore.getAt(0).data.mchtFlag2);
    			});
             	
				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				var settleAcctSnd = baseStore.getAt(0).data.settleAcctSnd;
				var acctSettleType =baseStore.getAt(0).data.acctSettleType;
				
				mchntForm.getForm().findField('clearType').setValue(settleAcct.substring(0,1));
				mchntForm.getForm().findField('settleAcct').setValue(settleAcct.substring(1));
//				
//				if(acctSettleType != '1'){
//					mchntForm.getForm().findField('idClearType2').setValue(settleAcctSnd.substring(0,1));
//					mchntForm.getForm().findField('settleAcctSnd').setValue(settleAcctSnd.substring(1));
//				}
				
//				//分润方式  20190128
//				var settleAcctNm = baseStore.getAt(0).data.settleAcctNm;
//				if(settleAcctNm == '0'){
//					mchntForm.getForm().findField('idClearType2').setValue("固定金额");
//				}else if(settleAcctNm == '1'){
//					mchntForm.getForm().findField('idClearType2').setValue("比例(%)");
//				}
				var settleAcctNm = baseStore.getAt(0).data.settleAcctNm;
				mchntForm.getForm().findField('idClearType2').setValue(settleAcctNm);
				
				//判断清算方式
//				checkSettleType(baseStore.getAt(0).data.acctSettleType);
			
             	
				
				detailWin.setTitle('商户详细信息[商户ID：' + mchntId + ']');
				detailWin.show();
				
				//隐藏选项卡
				var maintab=Ext.getCmp('tab');
//				maintab.hideTabStripItem(2);
				maintab.hideTabStripItem(3);
				maintab.hideTabStripItem(4);
				maintab.hideTabStripItem(5);
				maintab.hideTabStripItem(6);
				maintab.hideTabStripItem(7);
				maintab.hideTabStripItem(8);
				
				setDetail('mappingMchntcdOne','mappingMchntcdTwo');

				gridStore.load({
					params: {
						start: 0,
						discCd: baseStore.getAt(0).data.feeRate
					}
				});
				store.load({
					params: {
						start: 0,
						discCd: baseStore.getAt(0).data.feeRate
					}
				});

				SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',baseStore.getAt(0).data.mchtGrp,function(ret){
					mchntMccStore.loadData(Ext.decode(ret));
					mchntForm.getForm().findField('mcc').setValue(baseStore.getAt(0).data.mcc);
				});
				custom = new Ext.Resizable('showBigPic', {
					    wrap:true,
					    pinned:true,
					    minWidth: 50,
					    minHeight: 50,
					    preserveRatio: true,
					    dynamic:true,
					    handles: 'all',
					    draggable:true
					});
				customEl = custom.getEl();
				customEl.on('dblclick', function(){
					customEl.puff();
				});
				customEl.hide(true);


				storeImg.on('load',function(){
					for(var i=0;i<storeImg.getCount();i++){
						var rec = storeImg.getAt(i).data;
				    	Ext.get(rec.btBig).on('click', function(obj,val){
				    		showPIC(val.id);
				    	});
					}
				});
				storeImg.reload({
					params: {
						start: 0,
						imagesId: mchntId
					}
				});
			}else{
				showErrorMsg("加载商户信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
	

}