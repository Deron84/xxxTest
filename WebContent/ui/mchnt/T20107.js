Ext.onReady(function() {
	var _ylMchntNo;
    var _ylTermNo;
    var _termFixNo;
	var cMchntNo;
	var termFixNo;
	var cKey;
	var ylMchntNo;
	var ylTermNo;
	var startDate;
	var endDate;
	var enableFlag;
	var brhFlag;
	var bussId;
	var pwdId;
	var resv1;
	var flagg=true;
	var branchCode;
	var cMchntNo_2;
	var cipher;
	var uploadCert;
	var scanFlag;
	// 启用标识
	var enableStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','停用'],['1','启用']],
		reader: new Ext.data.ArrayReader()
	});
/*	// 聚合标志
	var mergeStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','不聚合'],['1','聚合银联']],
		reader: new Ext.data.ArrayReader()
	});*/
	var scanStore = new Ext.data.ArrayStore({
		fields: ['valueField','displayField'],
		data:[['0','扫码(主被扫)'],['1','主扫方式'],['2','被扫方式']],
		reader:new Ext.data.ArrayReader()
	});
	var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	
	SelectOptionsDWR.getMchntData('MCHNT_NAME',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	
	var termStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	// 菜品编码
	var posStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=posCodeInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'vageCode'
		},[
			{name: 'mchtNm',mapping: 'MCHT_NM'},
			{name: 'ylMchntNo',mapping: 'Yl_MCHNT_NO'},
			{name: 'ylTermNo',mapping: 'Yl_TERM_NO'},
			{name: 'cMchntNo',mapping: 'C_MCHNT_NO'},
			{name: 'termFixNo',mapping: 'TERM_FIX_NO'},
			{name: 'scanCodeFlag',mapping: 'SCANCODEFLAG'},
			{name: 'cMchntBrh',mapping: 'C_BRH_NAME'},
			{name: 'cKey',mapping: 'C_KEY'},
			{name: 'cipher',mapping: 'CIPHER'},
			{name: 'bussId',mapping: 'BUSS_ID'},
			{name: 'resv1',mapping: 'RESV1'},
			{name: 'certPath',mapping: 'CERTPATH'},
			{name: 'branchCode',mapping: 'BRANCHCODE'},
			{name: 'flag',mapping: 'FLAG'},
			{name: 'validStartDate',mapping: 'VALID_START_DATE'},
			{name: 'validEndDate',mapping: 'VALID_END_DATE'},
			{name: 'id',mapping: 'ID'}
		]),
		autoLoad :true
	}); 
	
	var posColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        { header: '商户名称',dataIndex: 'mchtNm',width: 180,align:'center'},
		{ header: '银联商户号',dataIndex: 'ylMchntNo',width: 140,align:'center'},
		{ header: '银联终端号',dataIndex: 'ylTermNo',width: 100,align:'center'},
		{ header: '扫码支付商户号',dataIndex: 'cMchntNo',width: 180,align:'center'},
		{ header: '终端硬件编号',dataIndex: 'termFixNo',width: 130,align:'center'},
		{ header: '扫码方式',dataIndex: 'scanCodeFlag',width: 130,align:'center'},
		{ header: '第三方机构',dataIndex: 'cMchntBrh',width: 130,align:'center'},
		{ header: '密钥',dataIndex: 'cKey',width: 240,align:'center'},
		{ header: '银联密码',dataIndex: 'cipher',width: 80,align:'center'},
		{ header: '业务代码',dataIndex: 'bussId',width: 100,align:'center'},
		{ header: '备注',dataIndex: 'resv1',width: 100,align:'center'},
		{ header: '证书名称',dataIndex: 'certPath',width: 150,align:'center'},
		{ header: '分行代码',dataIndex: 'branchCode',width: 80,align:'center'},
		{ header: '状态标识',dataIndex: 'flag',width: 80,align:'center'},
		{ header: '有效期开始时间',dataIndex: 'validStartDate',width: 130,align:'center'},
		{ header: '有效期结束时间',dataIndex: 'validEndDate',width: 130,align:'center'},
		{ header: '主键',dataIndex: 'id',width: 120,align:'center',hidden:true}
	]);
	
	var posInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		fileUpload: true,
		enctype:'multipart/form-data',
		items: [{
			xtype: 'panel',
        	layout: 'form',
    		labelWidth: 120,
        	items: [{
        		xtype: 'combo',
				store: scanStore,
				labelStyle: 'padding-left: 5px',
				displayField: 'displayField',
				valueField: 'valueField',
				emptyText: '请选择',
				id:'scanFlag2',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: false,
				allowBlank: true,
				fieldLabel: '扫码方式*',
				value: '',
				listWidth: 250
        	},{
				xtype : 'dynamicCombo',
				labelStyle: 'padding-left: 5px',
				methodName : 'getBrhName',
				fieldLabel: '第三方机构*',
				hiddenName: 'brhFlag2',
				emptyText: '请选择第三方机构',
				width: 250,
				editable: true,
				allowBlank: false,
				listeners:{
					'select': function(){
					//	posInfoForm.getForm().findField('mergeFlagAdd').reset();
						var brhFlag2 = posInfoForm.getForm().findField('brhFlag2').getValue();
						if(brhFlag2==2){
							posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
							document.getElementById("cMchntNo2").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
							document.getElementById("cKeyId").parentNode.previousSibling.innerHTML="密钥*:";
							document.getElementById("pwdIdYL").parentNode.previousSibling.innerHTML="银联密码*:";
						//	posInfoForm.findById('mergeFlagAdd').setDisabled(false);
						} else if(brhFlag2==3){
							posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
							document.getElementById("cMchntNo2").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
							document.getElementById("bussId").parentNode.previousSibling.innerHTML="业务代码*:";
							document.getElementById("cKeyId").parentNode.previousSibling.innerHTML="密钥*:";
							document.getElementById("pwdIdYL").parentNode.previousSibling.innerHTML="银联密码*:";
					//		posInfoForm.findById('mergeFlagAdd').setDisabled(false);
						}else if(brhFlag2==17){
							posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
							document.getElementById("cMchntNo2").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
							document.getElementById("bussId").parentNode.previousSibling.innerHTML="扫码终端号*:";
							document.getElementById("cKeyId").parentNode.previousSibling.innerHTML="密钥*:";
							document.getElementById("pwdIdYL").parentNode.previousSibling.innerHTML="银联密码*:";
					//		posInfoForm.findById('mergeFlagAdd').setDisabled(false);
						}else if(brhFlag2==16){
							posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
							document.getElementById("cMchntNo2").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
							document.getElementById("cKeyId").parentNode.previousSibling.innerHTML="数据密钥*:";
							document.getElementById("pwdIdYL").parentNode.previousSibling.innerHTML="交易密钥*:";
					//		posInfoForm.findById('mergeFlagAdd').setDisabled(false);
						}else if(brhFlag2==4){
							posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
							document.getElementById("cMchntNo2").parentNode.previousSibling.innerHTML="用户名*:";
							document.getElementById("cKeyId").parentNode.previousSibling.innerHTML="密钥*:";
							document.getElementById("pwdIdYL").parentNode.previousSibling.innerHTML="银联密码*:";
					//		posInfoForm.findById('mergeFlagAdd').setDisabled(false);
						}else if (brhFlag2==5) {
							posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
							document.getElementById("cKeyId").parentNode.previousSibling.innerHTML="密钥*:";
							document.getElementById("pwdIdYL").parentNode.previousSibling.innerHTML="银联密码*:";
						//	posInfoForm.findById('mergeFlagAdd').setValue("不聚合");
						//	posInfoForm.findById('mergeFlagAdd').setDisabled(true);
						}else if (brhFlag2==6) {
							posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
							document.getElementById("cMchntNo2").parentNode.previousSibling.innerHTML="AppId*:";
							document.getElementById("bussId").parentNode.previousSibling.innerHTML="SessionId*:";
							document.getElementById("cKeyId").parentNode.previousSibling.innerHTML="密钥*:";
							document.getElementById("pwdIdYL").parentNode.previousSibling.innerHTML="银联密码*:";
						//	posInfoForm.findById('mergeFlagAdd').setDisabled(false);
						} else if (brhFlag2==7) {
							posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(true);
							document.getElementById("cMchntNo2").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
							document.getElementById("cKeyId").parentNode.previousSibling.innerHTML="密钥*:";
							document.getElementById("pwdIdYL").parentNode.previousSibling.innerHTML="银联密码*:";
						//	posInfoForm.findById('mergeFlagAdd').setValue("不聚合");
						//	posInfoForm.findById('mergeFlagAdd').setDisabled(true);
						} else if (brhFlag2==9) {
							posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
							document.getElementById("cKeyId").parentNode.previousSibling.innerHTML="密钥*:";
							document.getElementById("pwdIdYL").parentNode.previousSibling.innerHTML="银联密码*:";
						} else{
							posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
							document.getElementById("cMchntNo2").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
							document.getElementById("cKeyId").parentNode.previousSibling.innerHTML="密钥*:";
							document.getElementById("pwdIdYL").parentNode.previousSibling.innerHTML="银联密码*:";
						//	posInfoForm.findById('mergeFlagAdd').setDisabled(false);
						}
					}
				}
			}/*,{
				xtype: 'combo',
				store: mergeStore,
				labelStyle: 'padding-left: 5px',
				displayField: 'displayField',
				valueField: 'valueField',
				emptyText: '请选择',
				id:'mergeFlagAdd',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: false,
				disabled:false,
				allowBlank: true,
				fieldLabel: '聚合标识*',
				anchor: '60%',
				value: '0',
				listWidth: 250,
				listeners: {
	            	'select': function() { 
	            		var mFlag = posInfoForm.getForm().findField('mergeFlagAdd').getValue();
	            		var brhFlag2 = posInfoForm.getForm().findField('brhFlag2').getValue();
	            		if(mFlag == 1){
	            			posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(true);
	            			posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(true);
	            			posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
	            			posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
	            			posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
	            		} 
	            		if(mFlag ==0 || mFlag == '' || mFlag == null){
	            			posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
	            			posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
	            			posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
	            			posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
	            			posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
	            		}
	            	}
				}
			}*/,{
				xtype: 'datefield',
				format : 'Ymd',  
			    value:new Date(),
				width : 250,
				labelStyle: 'padding-left: 5px',
				id: 'startDate2',
				fieldLabel: '有效期开始时间*',
				allowBlank: false,
				editable: false  
			},{
				xtype: 'datefield',
				format : 'Ymd',  
		        value:new Date().add(Date.YEAR, +1),
				width : 250,
				labelStyle: 'padding-left: 5px',
				id: 'endDate2',
				fieldLabel: '有效期结束时间*',
				allowBlank: false,
				editable: false
			},{
				xtype: 'textfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '扫码支付商户号*',
				width: 250,
				maxLength: 21,
				vtype: 'isOverMax',
				id: 'cMchntNo2'
			},{
				xtype: 'textnotnull',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '终端硬件编码*',
				width: 250,
				maxLength: 20,
				vtype: 'isOverMax',
				id: 'termFixNo2'
			},{
				xtype: 'combo',
				store: mchntStore,
				labelStyle: 'padding-left: 5px',
				displayField: 'displayField',
				valueField: 'valueField',
				emptyText: '请选择',
			//	hiddenName: 'ylMchntNo',
				id:'ylMchntNo2',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true,
				allowBlank: true,
		//		disabled:true,
				fieldLabel: '商户名称*',
		//		anchor: '60%',
			//	listWidth: 250,
				width:250,
				listeners: {
	            	'select': function() {    
	                 	var ylMchntNo2 = posInfoForm.getForm().findField('ylMchntNo2').getValue();
	              //   	mchntForm.getForm().findField('mchtFlag2').enable();
	                 	termStore.removeAll();
	                 	SelectOptionsDWR.getTermData('TERM_NAME',ylMchntNo2,function(ret){
	                 		var data=Ext.decode(ret);
	                 		termStore.loadData(data);
						});
	                 	posInfoForm.getForm().findField('ylTermNo2').reset();
	                 	var brhFlag2 = posInfoForm.getForm().findField('brhFlag2').getValue();
	                 //	var mFlag = posInfoForm.getForm().findField('mergeFlagAdd').getValue();	           
	                 	if(brhFlag2==5 || mFlag==1){
	                 		waitMsg: '正在检查是否存在证书，请稍后......',
	                 		Ext.Ajax.requestNeedAuthorise({
	                 			url: 'T20107Action.asp?method=isExistYLCert',
        						method: 'post',
        						waitMsg: '正在检查是否存在证书，请稍后......',
        						success: function(rsp,opt) {
        							var rspObj = Ext.decode(rsp.responseText);
        							if(rspObj.success) {
        								posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(true);
        								posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
        								flagg=true;
        							} else {
        								posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
        								posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(true);
        								posInfoForm.findById('uploadCert').setValue(rspObj.msg);
        								flagg=false;
        							}
        						},
        						params: {
        							txnId: '20107',
        							subTxnId: '04',
        							ylMchntNo:ylMchntNo2
        						}
	                 		});
	                 	}
	                }
				}
			},{
				xtype: 'combo',
				store: termStore,
				labelStyle: 'padding-left: 5px',
				displayField: 'displayField',
				valueField: 'valueField',
				emptyText: '请选择',
				id:'ylTermNo2',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: false,
				allowBlank: true,
				fieldLabel: '银联终端号*',
				width: 250
			},{
				xtype:'textarea',
				labelStyle: 'padding-left: 5px',
				id:'cKeyId',
				height :40,
				grow : true,
		        fieldLabel: '密钥*',
		        width: 250,
		        cols:40,
		        growMax:40,
		        maxLength:1024
			},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'bussId',
        		fieldLabel: '业务代码*',
        		width: 250,
        		maxLength:32
        	},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'bussId2',
        		fieldLabel: '柜台代码*',
        		width: 250,
        		maxLength:32
        	},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'branchCode',
        		fieldLabel: '分行代码*',
        		width: 250,
        		maxLength:20
        	},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'pwdId',
        		fieldLabel: '密码*',
        		width: 250,
        		maxLength:500
        	},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'pwdIdYL',
        		fieldLabel: '银联密码*',
        		width: 250,
        		minLength:6,
        		maxLength:6
        	},{
				xtype: 'combo',
				store: enableStore,
				labelStyle: 'padding-left: 5px',
				displayField: 'displayField',
				valueField: 'valueField',
				emptyText: '请选择',
				id:'enableFlag',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: false,
				allowBlank: true,
				fieldLabel: '启用标识*',
				anchor: '60%',
				value: '1',
				listWidth: 250
			},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'resv1',
        		fieldLabel: '备注',
        		width: 250,
        		maxLength:128
        	},{	  
        		xtype: 'fileuploadfield',
				fieldLabel: '上传证书',
				buttonText:'浏览',
				width:'250',
				style: 'padding-left: 10px',
				id: 'uploadCertId',   
			    name: 'upload',
			    enctype:'multipart/form-data',
    			fileUpload: true
        	},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'uploadCert',
        		fieldLabel: '上传证书',
        		width: 250,
        		maxLength:128,
        		disabled : true,
				editable: false
        	}]
		}]
	});
	var posInfoForm2 = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			xtype: 'panel',
        	layout: 'form',
        	items: [{
        		xtype: 'textfield',
        		inputType: 'password',
        		labelStyle: 'padding-left: 5px',
        		fieldLabel: '请输入密钥*',
        		width: 250,
        		maxLength: 500,
        		vtype: 'isOverMax',
        		id: 'cKey2'
        	},{
        		xtype: 'textfield',
        		inputType: 'password',
        		labelStyle: 'padding-left: 5px',
        		fieldLabel: '确认输入密钥*',
        		width: 250,
        		maxLength: 500,
        		vtype: 'isOverMax',
        		id: 'cKeyCopy'
        	}]
		}]
	});
	
	var posWin2 = new Ext.Window({
		title: '',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [posInfoForm2],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				cKey = posInfoForm2.getForm().findField('cKey2').getValue();
				var cKeyCopy = posInfoForm2.getForm().findField('cKeyCopy').getValue();
				if(cKey =='' || cKey == null || cKeyCopy == '' || cKeyCopy == null){
            		showErrorMsg("密钥不能为空",grid);
    				return;
            	}
				if(cKey != cKeyCopy){
					showErrorMsg("密钥不一致，请重新输入",grid);
					posInfoForm2.getForm().reset();
    				return;
				}
				posInfoForm2.getForm().submit({
					url: 'T20107Action.asp?method=add',
					waitMsg: '正在提交，请稍后......',
					success: function(form,action) {
						showSuccessMsg(action.result.msg,posInfoForm2);
				//		posInfoForm.getForm().reset();
						grid.getStore().reload();
						posWin2.hide(grid);
					},
					failure: function(form,action) {
						showErrorMsg(action.result.msg,posInfoForm2);
					},
					params: {
						txnId: '20107',
						subTxnId: '00',
						cmchntNo: cMchntNo,
						termFixNo: termFixNo,
						cKey: cKey,
						ylMchntNo: ylMchntNo,
						ylTermNo: ylTermNo,
						enableFlag : enableFlag,
						startDate : startDate ,
						endDate : endDate
					}
				});
			}
		},{
			text: '重置',
			handler: function() {
				posInfoForm2.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				posWin2.hide(grid);
			}
		}]
	});
	var posWin = new Ext.Window({
		title: '添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		shadow: false,
		layout: 'fit',
		items: [posInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		style: 'padding-top: 160px',
		buttons: [{
			text: '确定',
			handler: function() {
				if(!posInfoForm.getForm().isValid()) {
					return;
				}
				var submitValues = posInfoForm.getForm().getValues();  
				for (var param in submitValues) {  
					if (posInfoForm.getForm().findField(param) && posInfoForm.getForm().findField(param).emptyText == submitValues[param]) {  
						posInfoForm.getForm().findField(param).setValue('');  
					}  
				}
				cMchntNo = posInfoForm.getForm().findField('cMchntNo2').getValue();
				termFixNo = posInfoForm.getForm().findField('termFixNo2').getValue();
				if(termFixNo =='' || termFixNo == null){
					showErrorMsg("终端硬件编码不能为空",grid);
	    			return;
	            }

				ylMchntNo = posInfoForm.getForm().findField('ylMchntNo2').getValue();
				if(ylMchntNo =='' || ylMchntNo == null){
					showErrorMsg("请选择有效商户",grid);
	    			return;
	            }
				ylTermNo = posInfoForm.getForm().findField('ylTermNo2').getValue();
				if(ylTermNo =='' || ylTermNo == null){
	            	showErrorMsg("请选择有效终端",grid);
	    			return;
	            }
				brhFlag = posInfoForm.getForm().findField('brhFlag2').getValue();
				cKey = posInfoForm.getForm().findField('cKeyId').getValue();
				bussId = posInfoForm.getForm().findField('bussId').getValue();
				bussId2 = posInfoForm.getForm().findField('bussId2').getValue();
				pwdId = posInfoForm.getForm().findField('pwdId').getValue();
				pwdIdYL = posInfoForm.getForm().findField('pwdIdYL').getValue();
				uploadCertId = posInfoForm.getForm().findField('uploadCertId').getValue();
				branchCode = posInfoForm.getForm().findField('branchCode').getValue();
				scanFlag = posInfoForm.getForm().findField('scanFlag2').getValue();
				if(scanFlag =='' || scanFlag == null){
	            	showErrorMsg("扫码方式不能为空",grid);
	    			return;
	            }
				if(brhFlag==0 || brhFlag==1){
					if(cMchntNo =='' || cMchntNo == null){
		            	showErrorMsg("扫码支付商户号不能为空",grid);
		    			return;
		            }
				}
				if(brhFlag==2){
					if(cMchntNo =='' || cMchntNo == null){
		            	showErrorMsg("扫码支付商户号不能为空",grid);
		    			return;
		           	}
					if((cKey =='' || cKey == null)){
						showErrorMsg("密钥不能为空",grid);
						return;
					}	
				} 
				if (brhFlag==3) {
					if(cMchntNo =='' || cMchntNo == null){
		           		showErrorMsg("扫码支付商户号不能为空",grid);
		    			return;
		           	}
					if(cKey =='' || cKey == null || bussId =='' || bussId==null){
						showErrorMsg("密钥或业务代码不能为空",grid);
		    			return;
					}	
				} 
				if (brhFlag==17) {
					if(cMchntNo =='' || cMchntNo == null){
		           		showErrorMsg("扫码支付商户号不能为空",grid);
		    			return;
		           	}
					if(cKey =='' || cKey == null || bussId =='' || bussId==null){
						showErrorMsg("密钥或扫码终端号不能为空",grid);
		    			return;
					}	
				} 
				if (brhFlag==16) {
					
					if(cMchntNo =='' || cMchntNo == null){
		           		showErrorMsg("扫码支付商户号不能为空",grid);
		    			return;
		           	}
					if(cKey =='' || cKey == null){
						showErrorMsg("数据密钥不能为空",grid);
		    			return;
					}
					if(pwdIdYL == '' || pwdIdYL == null){
						showErrorMsg("交易密钥不能为空",grid);
		    			return;
					}
				} 
				if(brhFlag==4){
					if(cMchntNo =='' || cMchntNo == null){
		           		showErrorMsg("用户名不能为空",grid);
		    			return;
		           	}
					if(pwdId == '' || pwdId == null){
						showErrorMsg("密码不能为空",grid);
		    			return;
					}
				}
				if (brhFlag==5) {
					if(pwdIdYL == '' || pwdIdYL == null){
						showErrorMsg("密码不能为空",grid);
		    			return;
					}
					if((uploadCertId == '' || uploadCertId == null) &&(flagg == true)){
						showErrorMsg("证书不能为空",grid);
		    			return;
					}
					if(cMchntNo =='' || cMchntNo == null){
		           		showErrorMsg("扫码支付商户号不能为空",grid);
		    			return;
		           	}
				} 
				if (brhFlag==6) {
					if(cMchntNo =='' || cMchntNo == null){
		           		showErrorMsg("AppId不能为空",grid);
		    			return;
		            }
					if(cKey =='' || cKey == null || bussId =='' || bussId==null){
						showErrorMsg("密钥或SessionId不能为空",grid);
		    			return;
					}
				}
				if (brhFlag==7) {
					if(cMchntNo =='' || cMchntNo == null){
		           		showErrorMsg("扫码支付商户号不能为空",grid);
		    			return;
		            }
					if(cKey =='' || cKey == null){
						showErrorMsg("密钥不能为空",grid);
		    			return;
					}
					if(bussId2 =='' || bussId2 == null){
						showErrorMsg("柜台代码不能为空",grid);
		    			return;
					}
					if(branchCode =='' || branchCode == null){
						showErrorMsg("分行代码不能为空",grid);
		    			return;
					}
				}
				enableFlag1 = posInfoForm.getForm().findField('enableFlag').getValue();
			//	mergeFlag = posInfoForm.getForm().findField('mergeFlagAdd').getValue();
			/*	if(mergeFlag == 1 && (pwdIdYL == '' || pwdIdYL == null)){
					showErrorMsg("聚合银联，银联密码不能为空",grid);
	    			return;
				}*/			
				
				startDate = typeof(Ext.getCmp('startDate2').getValue()) == 'string' ? '' : Ext.getCmp('startDate2').getValue().format('Ymd');
				endDate = typeof(Ext.getCmp('endDate2').getValue()) == 'string' ? '' : Ext.getCmp('endDate2').getValue().format('Ymd');
				if(endDate == '' || startDate == ''){
	            	showErrorMsg("时间不能为空",grid);
	    			return;
	           	}
				if(endDate!=''&&startDate!=''&&endDate<startDate){
	           		showErrorMsg("请保证截止日期不小于开始日期",grid);
	    			return;
	           	}
			//	cMchntNo_2 = posInfoForm.getForm().findField('cMchntNo_2').getValue();
				uploadCert = posInfoForm.getForm().findField('uploadCert').getValue();
				posInfoForm.getForm().submit({
					url: 'T20107Action.asp?method=add',
					method: 'post',
					waitMsg: '正在提交，请稍后......',
					params: {
						txnId: '20107',
						subTxnId: '00',
						cmchntNo: cMchntNo,
						termFixNo: termFixNo,
						ckey: cKey,
						ylMchntNo: ylMchntNo,
						ylTermNo: ylTermNo,
						enableFlag1 : enableFlag1,
				    	startDate : startDate ,
						endDate : endDate,
						brhFlag: brhFlag,
						uploadCert:uploadCert,
						scanFlag: scanFlag
				//		cMchntNo_2:cMchntNo_2
					},
					success:function(form,action) {
						//重新加载参数列表
						grid.getStore().reload();
						grid.getTopToolbar().items.items[2].disable();
						//重置表单
						posInfoForm.getForm().reset();
						posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
						document.getElementById("cMchntNo2").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
					//	posInfoForm.findById('mergeFlagAdd').setDisabled(false);
						showSuccessMsg(action.result.msg,grid);
						posWin.hide();
					},
					failure:function(form,action) {
						showErrorMsg(action.result.msg,grid);
					}	
				});	
			}
		},{
			text: '重置',
			handler: function() {
				posInfoForm.getForm().reset();
				posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
				posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
				document.getElementById("cMchntNo2").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
				//posInfoForm.findById('mergeFlagAdd').setDisabled(false);
			}
		},{
			text: '关闭',
			handler: function() {
				posInfoForm.getForm().reset();
				posInfoForm.findById('cMchntNo2').getEl().up('.x-form-item').setDisplayed(true);
				posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
				posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
				document.getElementById("cMchntNo2").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
			//	posInfoForm.findById('mergeFlagAdd').setDisabled(false);
				posWin.hide(grid);	
			}
		}]
	});
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			posWin.show();			
			posInfoForm.findById('cKeyId').getEl().up('.x-form-item').setDisplayed(false);
			posInfoForm.findById('bussId').getEl().up('.x-form-item').setDisplayed(false);
			posInfoForm.findById('bussId2').getEl().up('.x-form-item').setDisplayed(false);
			posInfoForm.findById('pwdId').getEl().up('.x-form-item').setDisplayed(false);
			posInfoForm.findById('pwdIdYL').getEl().up('.x-form-item').setDisplayed(false);
			posInfoForm.findById('uploadCertId').getEl().up('.x-form-item').setDisplayed(false);
			posInfoForm.findById('branchCode').getEl().up('.x-form-item').setDisplayed(false);
			posInfoForm.findById('uploadCert').getEl().up('.x-form-item').setDisplayed(false);
		//	posWin.top
			posInfoForm.getForm().reset();			
		}
	};
	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				var _ylMchntNo = rec.get('ylMchntNo');
				var _ylTermNo = rec.get('ylTermNo');
				var _termFixNo = rec.get('termFixNo');
				var _id = rec.get('id');
				showConfirm1('确定要删除该此条记录吗？商户号：' + _ylMchntNo+'终端号: '+_ylTermNo +'硬件编号：'+_termFixNo,grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T20107Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									grid.getTopToolbar().items.items[2].disable();
									grid.getTopToolbar().items.items[6].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								ylMchntNo: _ylMchntNo,
								ylTermNo: _ylTermNo,
								id:_id,
								txnId: '20107',
								subTxnId: '01'
							}
						});
					}
				});
			}
		}
	};
	
	var posInfoForm3 = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			xtype: 'panel',
        	layout: 'form',
        	labelWidth: 120,
        	items: [{
        		xtype: 'combo',
				store: scanStore,
				labelStyle: 'padding-left: 5px',
				displayField: 'displayField',
				valueField: 'valueField',
				emptyText: '请选择',
				id:'scanFlagCp',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: false,
				allowBlank: true,
				fieldLabel: '扫码方式*',
				listWidth: 250
        	},{
				xtype : 'dynamicCombo',
				labelStyle: 'padding-left: 5px',
				methodName : 'getBrhName',
				fieldLabel: '第三方机构*',
				hiddenName: 'brhFlagCp',
				emptyText: '请选择第三方机构',
				width: 250,
				editable: true,
				listeners:{
					'select': function(){
						var brhFlag = posInfoForm3.getForm().findField('brhFlagCp').getValue();
					//	posInfoForm3.findById('mergeFlagCp').setValue("不聚合");
						if(brhFlag==2){
							posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
							document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
						//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
						} else if (brhFlag==3) {
							posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
							document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
							document.getElementById("bussIdCp").parentNode.previousSibling.innerHTML="业务代码*:";
							document.getElementById("cKeyIdCp").parentNode.previousSibling.innerHTML="密钥*:";
						//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
						}else if (brhFlag==17) {
							posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
							document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
							document.getElementById("bussIdCp").parentNode.previousSibling.innerHTML="扫码终端号*:";
							document.getElementById("cKeyIdCp").parentNode.previousSibling.innerHTML="密钥*:";
						//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
						}else if (brhFlag==16) {
							posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
							document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
							document.getElementById("cKeyIdCp").parentNode.previousSibling.innerHTML="数据密钥*:";
							document.getElementById("pwdIdYLCp").parentNode.previousSibling.innerHTML="交易密钥*:";
						//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
						} else if (brhFlag==4) {
							posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
							document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="用户名*:";
						//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
						}else if (brhFlag==5) {
							posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
							document.getElementById("pwdIdYLCp").parentNode.previousSibling.innerHTML="银联密码*:";
						//	posInfoForm3.findById('mergeFlagCp').setValue("不聚合");
						//	posInfoForm3.findById('mergeFlagCp').setDisabled(true);
						} else if (brhFlag==6) {
							posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
							document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="AppId*:";
							document.getElementById("bussIdCp").parentNode.previousSibling.innerHTML="SessionId*:";
							document.getElementById("cKeyIdCp").parentNode.previousSibling.innerHTML="密钥*:";
						//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
						} else if (brhFlag==7) {
							posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(true);
							posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
							document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
							document.getElementById("cKeyIdCp").parentNode.previousSibling.innerHTML="密钥*:";
						//	posInfoForm3.findById('mergeFlagCp').setValue("不聚合");
						//	posInfoForm3.findById('mergeFlagCp').setDisabled(true);
						} else if (brhFlag==9) {
							posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(false);
						}else{
							posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
							posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
							document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
						//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
						}
					}
				}
			},/*{
				xtype: 'combo',
				store: mergeStore,
				labelStyle: 'padding-left: 5px',
				displayField: 'displayField',
				valueField: 'valueField',
				emptyText: '请选择',
				id:'mergeFlagCp',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: false,
				disabled:false,
				allowBlank: true,
				fieldLabel: '聚合标识*',
				anchor: '60%',
				value: '0',
				listWidth: 250,
				listeners: {
	            	'select': function() { 
	            		var mFlag = posInfoForm3.getForm().findField('mergeFlagCp').getValue();
	            		var brhFlag = posInfoForm3.getForm().findField('brhFlagCp').getValue();
	            		if(mFlag == 1){
	            			posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(true);
	            			posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
	            			posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
	            		}  
	            		if(mFlag ==0 || mFlag == '' || mFlag == null){
	            			posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
	            			posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
	            			posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
	            		}
	            	}
				}
			},*/{
				xtype: 'textnotnull',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '银联商户号*',
				width: 250,
				maxLength: 20,
				disabled : true,
				editable: false,
				vtype: 'isOverMax',
				id: 'ylMchntNoCp'
			},{
				xtype: 'textnotnull',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '银联终端号*',
				width: 250,
				maxLength: 20,
				disabled : true,
				editable: false,
				vtype: 'isOverMax',
				id: 'ylTermNoCp'
			},{
				xtype: 'textnotnull',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '终端硬件编码*',
				width: 250,
				maxLength: 20,
				vtype: 'isOverMax',
				id: 'termFixNoCp'
			},{
				xtype: 'textnotnull',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '扫码支付商户号*',
				width: 250,
				maxLength: 21,
				vtype: 'isOverMax',
				id: 'cMchntNoCp'
			},{
				xtype:'textarea',
				labelStyle: 'padding-left: 5px',
				id:'cKeyIdCp',
				style:'outline:none;resize:none',
		        grow:true,
		        fieldLabel: '密钥*',
		        width: 250,
		        cols:40,
		        growMax:40,
		        height:40,
		        maxLength:500
			},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'bussIdCp',
        		fieldLabel: '业务代码*',
        		width: 250,
        		maxLength:32
        	},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'bussIdCp2',
        		fieldLabel: '柜台代码*',
        		width: 250,
        		maxLength:32
        	},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'branchCodeCp',
        		fieldLabel: '分行代码*',
        		width: 250,
        		maxLength:20
        	},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'pwdIdCp',
        		fieldLabel: '密码*',
        		width: 250,
        		maxLength:500
        	},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'pwdIdYLCp',
        		fieldLabel: '银联密码*',
        		width: 250,
        		minLength:6,
        		maxLength:6
        	},{
				xtype: 'combo',
				store: enableStore,
				labelStyle: 'padding-left: 5px',
				displayField: 'displayField',
				valueField: 'valueField',
				emptyText: '请选择',
			//	hiddenName: 'ylTermNo',
				id:'enableFlagCp',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: false,
				allowBlank: true,
	//			disabled:true,
				fieldLabel: '状态标识*',
				anchor: '60%',
				listWidth: 250
			},{
        		xtype: 'textfield',
        		labelStyle: 'padding-left: 5px',
        		id:'resv1Cp',
        		fieldLabel: '备注',
        		width: 250,
        		maxLength:128
        	},{
				xtype: 'datefield',
				width : 250,
				labelStyle: 'padding-left: 5px',
				id: 'startDateCp',
				fieldLabel: '有效期开始时间*',
				allowBlank: false,
				editable: false
			},{
				xtype: 'datefield',
				width : 250,
				labelStyle: 'padding-left: 5px',
				id: 'endDateCp',
				fieldLabel: '有效期结束时间*',
				allowBlank: false,
				editable: false
			},{
				xtype: 'textfield',
				width : 250,
				labelStyle: 'padding-left: 5px',
				id: 'idCp',
				allowBlank: true,
				hidden:true,
				editable: false
			}]
		}]
	});
	var posInfoForm4 = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		enctype:'multipart/form-data',
		fileUpload: true,
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '扫码方式*',
			width: 250,
			maxLength: 2,
			disabled : true,
			editable: false,
			vtype: 'isOverMax',
			id: 'scanFlagCertCp'
		},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '第三方机构*',
			width: 250,
			maxLength: 20,
			disabled : true,
			editable: false,
			vtype: 'isOverMax',
			id: 'brhFlagCertCp'
		},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '银联商户号*',
			width: 250,
			maxLength: 20,
			editable: false,
			disabled : true,
			vtype: 'isOverMax',
			id: 'ylMchntNoCertCp'
		},{	  
			xtype: 'fileuploadfield',
			fieldLabel: '上传证书',
			buttonText:'浏览',
			width:'250',
			style: 'padding-left: 10px',
			id: 'uploadCertIdCp',   
		    name: 'upload',
		    enctype:'multipart/form-data',
			fileUpload: true
		}]
	});
	var posWin4 = new Ext.Window({
		title: '银联支付证书修改',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [posInfoForm4],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(posInfoForm4.getForm().isValid()) {
					ylMchntNo = posInfoForm4.getForm().findField("ylMchntNoCertCp").getValue();
					brhFlag = posInfoForm4.getForm().findField("brhFlagCertCp").getValue();
					scanFlag = posInfoForm4.getForm().findField("scanFlagCertCp").getValue();
					if(brhFlag == "谊盛平台"){
						brhFlag="0";
					}else if(brhFlag == "民生银行"){
						brhFlag="1";
					}else if(brhFlag == "威富通平台"){
						brhFlag="2";
					}else if(brhFlag == "易通支付"){
						brhFlag="3";
					}else if (brhFlag == "辽宁农信支付") {
						brhFlag="4";
					}else if(brhFlag == "银联支付"){
						brhFlag="5";
					}else if(brhFlag == "钱宝支付"){
						brhFlag="6";
					}else if(brhFlag == "建行龙支付"){
						brhFlag="7";
					}
					if(scanFlag=="扫码(主被扫)"){
						scanFlag="0";
					} else if (scanFlag=="主扫方式") {
						scanFlag="1";
					} else if (scanFlag=="被扫方式") {
						scanFlag="2";
					}
			/*		if(mergeFlag=="不聚合"){
						mergeFlag="0";
					}else if (mergeFlag=="聚合银联") {
						mergeFlag="1";
					}*/
					posInfoForm4.getForm().submit({
						url: 'T20107Action.asp?method=updateCert',
						method: 'post',
						params: {
							txnId: '20107',
							subTxnId: '03',
							ylMchntNo:ylMchntNo,
							brhFlag:brhFlag,
							scanFlag:scanFlag
						},
						success:function(form,action) {
							//重新加载参数列表
							grid.getStore().reload();
							grid.getTopToolbar().items.items[6].disable();
							//重置表单
							posInfoForm4.getForm().reset();
							showSuccessMsg(action.result.msg,grid);
							posWin4.hide();
						},
						failure:function(form,action) {
							showErrorMsg(action.result.msg,grid);
						}
					});
				}
			}
		},{
			text: '关闭',
			handler: function() {
				posWin4.hide(grid);
			}
		}]
	});
	var posWin3 = new Ext.Window({
		title: '扫码支付商户信息修改',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		shadow: false,
		layout: 'fit',
		items: [posInfoForm3],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		style: 'padding-top: 160px',
		buttons: [{
			text: '确定',
			handler: function() {
				if(posInfoForm3.getForm().isValid()) {
					var cMchntNoStr = posInfoForm3.getForm().findField("cMchntNoCp").getValue();
					
					startDate = typeof(Ext.getCmp('startDateCp').getValue()) == 'string' ? '' : Ext.getCmp('startDateCp').getValue().format('Ymd'); 
					endDate = typeof(Ext.getCmp('endDateCp').getValue()) == 'string' ? '' : Ext.getCmp('endDateCp').getValue().format('Ymd');
					if(endDate == '' ){
	            		showErrorMsg("有效期截止时间不能为空",grid);
	    				return;
	            	}
					if(endDate!=''&&startDate!=''&&endDate<startDate){
	            		showErrorMsg("请保证截止日期不小于当前日期",grid);
	    				return;
	            	}
					bussId = posInfoForm3.getForm().findField("bussIdCp").getValue();
					bussId2 = posInfoForm3.getForm().findField("bussIdCp2").getValue();
					pwdId = posInfoForm3.getForm().findField("pwdIdCp").getValue();
					cipher = posInfoForm3.getForm().findField("pwdIdYLCp").getValue();
					cKey = posInfoForm3.getForm().findField("cKeyIdCp").getValue();
					brhFlag = posInfoForm3.getForm().findField("brhFlagCp").getValue();
					branchCode = posInfoForm3.getForm().findField("branchCodeCp").getValue();
					scanFlag = posInfoForm3.getForm().findField("scanFlagCp").getValue();
			//		mergeFlag = posInfoForm3.getForm().findField("mergeFlagCp").getValue();
					if(brhFlag==2 && (cKey =='' || cKey == null)){
						showErrorMsg("密钥不能为空",grid);
	    				return;
					} else if (brhFlag==3 &&(cKey =='' || cKey == null || bussId =='' || bussId==null)) {
						showErrorMsg("密钥或业务代码不能为空",grid);
	    				return;
					} else if (brhFlag==17 &&(cKey =='' || cKey == null || bussId =='' || bussId==null)) {
						showErrorMsg("密钥或扫码终端号不能为空",grid);
	    				return;
					}else if (brhFlag==4) {
						if(pwdId =='' || pwdId == null){
							showErrorMsg("密码不能为空",grid);
		    				return;
						}
					} else if (brhFlag==5 && (cipher == '' || cipher == null)) {
						showErrorMsg("密码不能为空",grid);
	    				return;
					} else if(brhFlag==6){
						if(cKey =='' || cKey == null || bussId =='' || bussId==null){
							showErrorMsg("密钥或SessionId不能为空",grid);
		    				return;
						}
					} else if(brhFlag==7){
						if(bussId2=='' || bussId2==null){
							showErrorMsg("柜台代码不能为空",grid);
		    				return;
						}
						if(branchCode=='' || branchCode==null){
							showErrorMsg("分行代码不能为空",grid);
		    				return;
						}
						if(cKey =='' || cKey == null){
							showErrorMsg("密钥不能为空",grid);
		    				return;
						}
						
					}else if (brhFlag==16) {
						if(cKey =='' || cKey == null){
							showErrorMsg("数据密钥不能为空",grid);
			    			return;
						}
						if(cipher == '' || cipher == null){
							showErrorMsg("交易密钥不能为空",grid);
			    			return;
						}
					} 
					
				/*	if(mergeFlag == 1 && (cipher == '' || cipher == null)){
						showErrorMsg("聚合银联，银联密码不能为空",grid);
		    			return;
					}*/
					
					var array = new Array();
					var data = {
						ylMchntNo : posInfoForm3.getForm().findField("ylMchntNoCp").getValue(),
						ylTermNo : posInfoForm3.getForm().findField("ylTermNoCp").getValue(),
						termFixNo : posInfoForm3.getForm().findField("termFixNoCp").getValue(),
						cMchntNo : posInfoForm3.getForm().findField("cMchntNoCp").getValue(),
						bussId : posInfoForm3.getForm().findField("bussIdCp").getValue(),
						bussId2 : posInfoForm3.getForm().findField("bussIdCp2").getValue(),
						pwdId : posInfoForm3.getForm().findField("pwdIdCp").getValue(),
						cipher : posInfoForm3.getForm().findField("pwdIdYLCp").getValue(),
						cKey : posInfoForm3.getForm().findField("cKeyIdCp").getValue(),
						branchCode:posInfoForm3.getForm().findField("branchCodeCp").getValue(),
						enableFlag: posInfoForm3.getForm().findField("enableFlagCp").getValue(),
						resv1: posInfoForm3.getForm().findField("resv1Cp").getValue(),
						brhFlag : posInfoForm3.getForm().findField("brhFlagCp").getValue(),
						scanFlag : posInfoForm3.getForm().findField("scanFlagCp").getValue(),
						id : posInfoForm3.getForm().findField("idCp").getValue(),
					//	mergeFlag:posInfoForm3.getForm().findField("mergeFlagCp").getValue(),
						startDate : typeof(Ext.getCmp('startDateCp').getValue()) == 'string' ? '' : Ext.getCmp('startDateCp').getValue().format('Ymd'),
						endDate	: typeof(Ext.getCmp('endDateCp').getValue()) == 'string' ? '' : Ext.getCmp('endDateCp').getValue().format('Ymd')
					};
					array.push(data);
					
					Ext.Ajax.requestNeedAuthorise({
						url: 'T20107Action.asp?method=update',
						method: 'post',
						params: {
							mchntDataStr : Ext.encode(array),
							txnId: '20107',
							subTxnId: '02'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							grid.enable();
							if(rspObj.success) {
								grid.getStore().commitChanges();
								showSuccessMsg(rspObj.msg,grid);
								posWin3.hide(grid);
							} else {
								grid.getStore().rejectChanges();
								showErrorMsg(rspObj.msg,grid);
							}
							grid.getTopToolbar().items.items[2].disable();
							grid.getTopToolbar().items.items[4].disable();
							grid.getTopToolbar().items.items[6].disable();
							grid.getStore().reload();
					//		hideProcessMsg();
						}
					});
				}
			}
		},{
			text: '关闭',
			handler: function() {
				posWin3.hide(grid);
			}
		}]
	});
	var upCertMenu = {
			text: '修改证书',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				var rec = grid.getSelectionModel().getSelected();
				if(grid.getSelectionModel().hasSelection() && (rec.get('cMchntBrh') == "银联支付"/* || rec.get('mergeFlag') == "聚合银联"*/)) {
					posInfoForm4.getForm().findField("uploadCertIdCp").setValue(rec.get('certPath'));
					posInfoForm4.getForm().findField("ylMchntNoCertCp").setValue(rec.get('ylMchntNo'));
					posInfoForm4.getForm().findField("brhFlagCertCp").setValue(rec.get('cMchntBrh'));
					posInfoForm4.getForm().findField("scanFlagCertCp").setValue(rec.get('scanCodeFlag'));
					posWin4.show();
					posWin4.center();
				}
			}
	};
	var upMenu = {
		text: '修改',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function(){
			if(grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				posInfoForm3.getForm().findField("idCp").setValue(rec.get('id'));
				posInfoForm3.getForm().findField("cMchntNoCp").setValue(rec.get('cMchntNo'));
				posInfoForm3.getForm().findField("bussIdCp2").setValue(rec.get('bussId'));
				posInfoForm3.getForm().findField("bussIdCp").setValue(rec.get('bussId'));
				posInfoForm3.getForm().findField("cKeyIdCp").setValue(rec.get('cKey'));
				posInfoForm3.getForm().findField("pwdIdCp").setValue(rec.get('cKey'));
				posInfoForm3.getForm().findField("startDateCp").setValue(new Date( rec.get('validStartDate').replace(/-/g, "/") ) );
				posInfoForm3.getForm().findField("endDateCp").setValue(new Date( rec.get('validEndDate').replace(/-/g, "/") ));				
				posInfoForm3.getForm().findField("ylMchntNoCp").setValue(rec.get('ylMchntNo'));
				posInfoForm3.getForm().findField("ylTermNoCp").setValue(rec.get('ylTermNo'));
				posInfoForm3.getForm().findField("termFixNoCp").setValue(rec.get('termFixNo'));
				posInfoForm3.getForm().findField("resv1Cp").setValue(rec.get('resv1'));	
				//posInfoForm3.getForm().findField("scanFlagCp").setValue(rec.get('scanCodeFlag'));
				
				posInfoForm3.getForm().findField("pwdIdYLCp").setValue(rec.get('cipher'));
				posInfoForm3.getForm().findField("branchCodeCp").setValue(rec.get('branchCode'));
				if(rec.get('scanCodeFlag') == "扫码(主被扫)"){
					posInfoForm3.getForm().findField("scanFlagCp").setValue('0');
				} else if(rec.get('scanCodeFlag') == "主扫方式"){
					posInfoForm3.getForm().findField("scanFlagCp").setValue('1');
				} else if(rec.get('scanCodeFlag') == "被扫方式"){
					posInfoForm3.getForm().findField("scanFlagCp").setValue('2');
				}
				if(rec.get('flag') == "停用"){
					posInfoForm3.getForm().findField("enableFlagCp").setValue('0');
				}else if(rec.get('flag') == "启用"){
					posInfoForm3.getForm().findField("enableFlagCp").setValue('1');
				}
				/*if(rec.get('mergeFlag') == "不聚合"){
					posInfoForm3.getForm().findField("mergeFlagCp").setValue('0');
				}else if(rec.get('mergeFlag') == "聚合银联"){
					posInfoForm3.getForm().findField("mergeFlagCp").setValue('1');
				}*/
				if(rec.get('cMchntBrh') == "谊盛平台"){
					posInfoForm3.getForm().findField("brhFlagCp").setValue('0');
				}else if(rec.get('cMchntBrh') == "民生银行"){
					posInfoForm3.getForm().findField("brhFlagCp").setValue('1');
				}else if(rec.get('cMchntBrh') == "威富通平台"){
					posInfoForm3.getForm().findField("brhFlagCp").setValue('2');
				}else if(rec.get('cMchntBrh') == "易通支付"){
					posInfoForm3.getForm().findField("brhFlagCp").setValue('3');
				}else if (rec.get('cMchntBrh') == "辽宁农信支付") {
					posInfoForm3.getForm().findField("brhFlagCp").setValue('4');
				}else if(rec.get('cMchntBrh') == "银联支付"){
					posInfoForm3.getForm().findField("brhFlagCp").setValue('5');
				}else if(rec.get('cMchntBrh') == "钱宝支付"){
					posInfoForm3.getForm().findField("brhFlagCp").setValue('6');
				}else if(rec.get('cMchntBrh') == "建行龙支付"){
					posInfoForm3.getForm().findField("brhFlagCp").setValue('7');
				}else if(rec.get('cMchntBrh') == "电子会员卡"){
					posInfoForm3.getForm().findField("brhFlagCp").setValue('9');
				}else if(rec.get('cMchntBrh') == "翼支付"){
					posInfoForm3.getForm().findField("brhFlagCp").setValue('16');
				}
				posWin3.show();
				var brhFlagCp=posInfoForm3.getForm().findField("brhFlagCp").getValue();
				if(brhFlagCp==2){
					posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
					document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
				//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
				} else if (brhFlagCp==3) {
					posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
					document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
					document.getElementById("bussIdCp").parentNode.previousSibling.innerHTML="业务代码*:";
				//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
				} else if (brhFlagCp==17) {
					posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
					document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
					document.getElementById("bussIdCp").parentNode.previousSibling.innerHTML="扫码终端号*:";
				//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
				} else if (brhFlagCp==16) {
					posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
					document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
					document.getElementById("cKeyIdCp").parentNode.previousSibling.innerHTML="数据密钥*:";
					document.getElementById("pwdIdYLCp").parentNode.previousSibling.innerHTML="交易密钥*:";
				//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
				} else if (brhFlagCp==4) {
					posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
					document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="用户名*:";
				//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
				}else if (brhFlagCp==5) {
					posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
				//	posInfoForm3.findById('mergeFlagCp').setValue("不聚合");
				//	posInfoForm3.findById('mergeFlagCp').setDisabled(true);
				} else if (brhFlagCp==6) {
					posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
					document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="AppId*:";
					document.getElementById("bussIdCp").parentNode.previousSibling.innerHTML="SessionId*:";
				//	posInfoForm3.findById('mergeFlagCp').setDisabled(false);
				} else if (brhFlagCp==7) {
					posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
					document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
				//	posInfoForm3.findById('mergeFlagCp').setValue("不聚合");
				//	posInfoForm3.findById('mergeFlagCp').setDisabled(true);
				} else if (brhFlagCp==9) {
					posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(false);
				}else{
					posInfoForm3.findById('cKeyIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('bussIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('cMchntNoCp').getEl().up('.x-form-item').setDisplayed(true);
					document.getElementById("cMchntNoCp").parentNode.previousSibling.innerHTML="扫码支付商户号*:";
				}
			/*	var mFlagCp = posInfoForm3.findById('mergeFlagCp').getValue();
				if(mFlagCp == 1){
					posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(true);
					posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
					posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
        		} 
        		if(mFlagCp ==0 || mFlagCp == '' || mFlagCp == null){
        			posInfoForm3.findById('bussIdCp2').getEl().up('.x-form-item').setDisplayed(false);
        			posInfoForm3.findById('branchCodeCp').getEl().up('.x-form-item').setDisplayed(false);
        			posInfoForm3.findById('pwdIdYLCp').getEl().up('.x-form-item').setDisplayed(false);	
        		}*/
			//	posWin3.center();
			}
		}
	};
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			xtype: 'panel',
        	layout: 'form',
        	items: [{
        		xtype: 'dynamicCombo',
    			fieldLabel: '商户名称',
    			methodName: 'getMchntNameNew',
    			labelStyle: 'padding-left: 5px',
    			hiddenName: 'ylMchntNo3',
    			editable: true,
    			width: 280
			},{
				xtype: 'textfield',
                fieldLabel: '银联终端号',
                labelStyle: 'padding-left: 5px',
                id: 'ylTermNo3',
   		        width: 280,
   		        allowBlank: true,
   		        emptyText: '请输入银联终端号',
   		        maxLength:8
			},{
				xtype:'textfield',
				fieldLabel: '设备硬件编号',
				labelStyle: 'padding-left: 5px',
				allowBlank: true,
				emptyText: '请输入设备的硬件编号',
				id: 'termFixNo3',
				width: 280,
				maxLength: 20,
				maxLengthText: '菜品最多可以输入20个',
				blankText: '请输入设备硬件编号'
			}]
		
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
				_ylMchntNo = queryForm.getForm().findField('ylMchntNo3').getValue();
				_ylTermNo = queryForm.getForm().findField('ylTermNo3').getValue();
				_termFixNo = queryForm.getForm().findField('termFixNo3').getValue();
				posStore.load({
					params:{
						start: 0,
						ylMchntNo: _ylMchntNo,
						ylTermNo: _ylTermNo,
						termFixNo : _termFixNo
					}
				});
				queryWin.hide(grid);
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
			queryForm.getForm().reset();
		}
	};
	
	var menuArr = new Array();
	menuArr.push(addMenu);     //增加
	menuArr.push('-');
	menuArr.push(delMenu);      //删除
	menuArr.push('-');
	menuArr.push(upMenu);		//修改
	menuArr.push('-');
	menuArr.push(upCertMenu);		//修改证书
	menuArr.push('-');
	menuArr.push(queryCondition);     //查询
	
	// 扫码终端信息维护
	var grid = new Ext.grid.EditorGridPanel({
		title: '扫码支付商户维护',
		iconCls: 'T501',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: posStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: posColModel,
		clicksToEdit: true,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载联机交易列表......'
		},
		tbar: menuArr,
		bbar:  new Ext.PagingToolbar({
			store: posStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[4].disable();
		grid.getTopToolbar().items.items[2].disable();
		grid.getTopToolbar().items.items[6].disable();
		grid.getStore().rejectChanges();
	});
	posStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
        	start: 0,
        	ylMchntNo: _ylMchntNo,
    		ylTermNo: _ylTermNo,
    		termFixNo : _termFixNo
        });
    }); 
	grid.on({
		//单击行，使删除按钮可用
		'rowclick': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[2].enable();
			grid.getTopToolbar().items.items[4].enable();
			var rec = grid.getSelectionModel().getSelected();
			if(rec.get('cMchntBrh') == "银联支付"/* || rec.get('mergeFlag') == "聚合银联"*/) {
				grid.getTopToolbar().items.items[6].enable();
			} else{
				grid.getTopToolbar().items.items[6].disable();
			}
		},
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});