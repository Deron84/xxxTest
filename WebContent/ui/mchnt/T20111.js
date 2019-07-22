Ext.onReady(function() {
	
	var ylMchntNo;
	var ylTermNo;
	var appId;
	var payFlag;
	var appId;
	var mchId;
	var publicKey;
	var privateKey;
	var signType;
	var flag;
	var authorityFlag;
	
	// 支付方式 
	var payFlagStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','微信'],['1','支付宝']],
		reader: new Ext.data.ArrayReader()
	});
	// 状态标识 
	var flagStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','停用'],['1','启用']],
		reader: new Ext.data.ArrayReader()
	});
	// 银联商户号
	var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	// 银联终端号
	var termStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	SelectOptionsDWR.getMchntData('MCHNT_NAME',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	// 签名类型
	var signTypeStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','RSA2'],['1','RSA']],
		reader: new Ext.data.ArrayReader()
	});
	// 授权标识 
	var authorityFlagStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','否'],['1','是']],
		reader: new Ext.data.ArrayReader()
	});
	var appStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=airpayMapInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'ylMchntNo',mapping: 'YL_MCHNT_NO'},
			{name: 'ylTermNo',mapping: 'YL_TERM_NO'},
			{name: 'appId',mapping: 'APP_ID'},
			{name: 'payFlag',mapping: 'PAY_FLAG'},
			{name: 'mchId',mapping: 'MCH_ID'},
			{name: 'cKey1',mapping: 'C_KEY1'},
			{name: 'cKey2',mapping: 'C_KEY2'},
			{name: 'sighType',mapping: 'SIGN_TYPE'},
			{name: 'flag',mapping: 'FLAG'},
			{name: 'authorityFlag',mapping: 'AUTHORITY_FLAG'}
		])
	});
	var appMode = new Ext.grid.ColumnModel([
        {header: '银联商户号',dataIndex: 'ylMchntNo',width: 150,align:'center'},
        {header: '银联终端号',dataIndex: 'ylTermNo',width: 80,align:'center'},
		{header: '应用Id',dataIndex: 'appId',width: 150,align:'center'},
		{header: '支付方式',dataIndex: 'payFlag',width: 100,align:'center'},
		{header: '商户号',dataIndex: 'mchId',width: 150,align:'center'},
		{header: '公钥/密钥',dataIndex: 'cKey1',width: 300,align:'center'},
		{header: '私钥',dataIndex: 'cKey2',width: 300,align:'center'},
		{header: '签名类型',dataIndex: 'sighType',width: 100,align:'center'},
		{header: '状态标识',dataIndex: 'flag',width: 80,align:'center'},
		{header: '授权标识',dataIndex: 'authorityFlag',width: 80,align:'center'}
	]);
	
	//添加微信、支付宝渠道维护表单
	var appAddForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		waitMsgTarget: true,
		defaults:{
			labelSeparator:": ",
			labelWidth: 80,
			width : 250,
			allowBlank: false,
			msgTarget : 'side',
			labelAlign:'center'
		},
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '银联商户号*',
			methodName: 'getMchntNameNew',
			labelStyle: 'padding-left: 5px',
			hiddenName: 'ylMchntNoAdd',
			editable: true,
			width: 250,
			listeners: {
            	'select': function() {    
                 	var ylMchntNo = appAddForm.getForm().findField('ylMchntNoAdd').getValue();
                 	termStore.removeAll();
                 	SelectOptionsDWR.getTermData('TERM_NAME',ylMchntNo,function(ret){
                 		var data=Ext.decode(ret);
                 		termStore.loadData(data);
					});
                 	appAddForm.getForm().findField('ylTermNoAdd').reset();
                }
			}
		},{
			xtype: 'combo',
			store: termStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'ylTermNoAdd',
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
			xtype: 'textfield',
            fieldLabel: '应用Id*', 
            labelStyle: 'padding-left: 5px',
            id: 'appIdAdd',
		    width: 250,
		    allowBlank: false,
		    emptyText: '请输入appId',
		    maxLength:32
		},{
			xtype: 'combo',
			store: payFlagStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'payFlagAdd',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '支付方式*',
			anchor: '60%',
			value: '1',
			listWidth: 250,
			listeners: {
				'select': function() { 
					var payFlag = appAddForm.getForm().findField('payFlagAdd').getValue();
					if(payFlag == 0){
						appAddForm.findById('mchIdAdd').getEl().up('.x-form-item').setDisplayed(true);
						appAddForm.findById('privateKeyAdd').getEl().up('.x-form-item').setDisplayed(false);
						appAddForm.findById('signTypeAdd').getEl().up('.x-form-item').setDisplayed(false);
						document.getElementById("publicKeyAdd").parentNode.previousSibling.innerHTML="微信密钥*:";
					} else if(payFlag == 1){
						appAddForm.findById('mchIdAdd').getEl().up('.x-form-item').setDisplayed(false);
						appAddForm.findById('privateKeyAdd').getEl().up('.x-form-item').setDisplayed(true);
						appAddForm.findById('signTypeAdd').getEl().up('.x-form-item').setDisplayed(true);
						document.getElementById("publicKeyAdd").parentNode.previousSibling.innerHTML="支付宝公钥*:";
					}
				}
			}
		},{
			xtype: 'textfield',
            fieldLabel: '商户号*', 
            labelStyle: 'padding-left: 5px',
            id: 'mchIdAdd',
		    width: 250,
		    allowBlank: true,
		    emptyText: '请输入商户号',
		    maxLength:32
		},{
			xtype: 'textarea',
			fieldLabel: '支付宝公钥*', 
			labelStyle: 'padding-left: 5px',
			id:'publicKeyAdd',
			width: 250,
		    height :40,
		    grow : true,
		    cols:40,
	        growMax:40,
		    allowBlank: false,
		    emptyText: '请输入密钥',
		    maxLength:2048
		},{
			xtype: 'textarea',
			fieldLabel: '支付宝私钥*', 
			labelStyle: 'padding-left: 5px',
			id:'privateKeyAdd',
			width: 250,
		    height :40,
		    grow : true,
		    cols:40,
	        growMax:40,
		    allowBlank: true,
		    emptyText: '请输入密钥',
		    maxLength:2048
		},{
			xtype: 'combo',
			store: signTypeStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'signTypeAdd',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '签名类型*',
			anchor: '60%',
			value: '0',
			listWidth: 250
		},{
			xtype: 'combo',
			store: flagStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'flagAdd',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '状态标识*',
			anchor: '60%',
			value: '1',
			listWidth: 250
		},{
			xtype: 'combo',
			store: authorityFlagStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'authorityFlagAdd',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '授权标识*',
			anchor: '60%',
			value: '1',
			listWidth: 250
		}]
	});
	var appUpdateForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		waitMsgTarget: true,
		layout: 'form',
		defaults:{
			labelSeparator :": ",
			labelWidth : 80,
			width : 250,
			allowBlank: false,
			msgTarget : 'side',
			labelAlign:'center'
		},
		items: [{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '银联商户号*',
			width: 250,
			maxLength: 15,
			disabled : true,
			editable: false,
			vtype: 'isOverMax',
			id: 'ylMchntNoCp'
		},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '银联终端号*',
			width: 250,
			maxLength: 8,
			disabled : true,
			editable: false,
			vtype: 'isOverMax',
			id: 'ylTermNoCp'
		},{
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '支付方式*',
			width: 250,
			disabled : true,
			editable: false,
			vtype: 'isOverMax',
			id: 'payFlagCp'
		}/*{
			xtype: 'combo',
			store: payFlagStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'payFlagCp',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '支付方式*',
			anchor: '60%',
			listWidth: 250,
			listeners: {
				'select': function() { 
					var payFlag = appUpdateForm.getForm().findField('payFlagCp').getValue();
					if(payFlag == 0){
						appUpdateForm.findById('mchIdCp').getEl().up('.x-form-item').setDisplayed(true);
						appUpdateForm.findById('privateKeyCp').getEl().up('.x-form-item').setDisplayed(false);
						appUpdateForm.findById('signTypeCp').getEl().up('.x-form-item').setDisplayed(false);
						document.getElementById("publicKeyCp").parentNode.previousSibling.innerHTML="微信密钥*:";
					} else if(payFlag == 1){
						appUpdateForm.findById('mchIdCp').getEl().up('.x-form-item').setDisplayed(false);
						appUpdateForm.findById('privateKeyCp').getEl().up('.x-form-item').setDisplayed(true);
						appUpdateForm.findById('signTypeCp').getEl().up('.x-form-item').setDisplayed(true);
						document.getElementById("publicKeyCp").parentNode.previousSibling.innerHTML="支付宝公钥*:";
					}
				}
			}
		}*/,{
			xtype: 'textfield',
    		labelStyle: 'padding-left: 5px',
    		id:'appIdCp',
    		fieldLabel: '应用ID*',
    		width: 250,
    		maxLength:32
		},{
			xtype: 'textfield',
    		labelStyle: 'padding-left: 5px',
    		id:'mchIdCp',
    		fieldLabel: '商户号*',
    		width: 250,
    		allowBlank: true,
    		maxLength:32
		},{
			xtype: 'textarea',
			fieldLabel: '支付宝公钥*', 
			labelStyle: 'padding-left: 5px',
			id:'publicKeyCp',
			width: 250,
		    height :40,
		    grow : true,
		    cols:40,
	        growMax:40,
		    allowBlank: false,
		    emptyText: '请输入密钥',
		    maxLength:2048
		},{
			xtype: 'textarea',
			fieldLabel: '支付宝私钥*', 
			labelStyle: 'padding-left: 5px',
			id:'privateKeyCp',
			width: 250,
		    height :40,
		    grow : true,
		    cols:40,
	        growMax:40,
		    allowBlank: true,
		    emptyText: '请输入密钥',
		    maxLength:2048
		},{
			xtype: 'combo',
			store: signTypeStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'signTypeCp',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '签名类型*',
			anchor: '60%',
			listWidth: 250
		},{
			xtype: 'combo',
			store: flagStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'flagCp',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '状态标识*',
			anchor: '60%',
			listWidth: 250
		},{
			xtype: 'combo',
			store: authorityFlagStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'authorityFlagCp',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '授权标识*',
			anchor: '60%',
			listWidth: 250
		}]
	});
	
	var appQueryForm = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 400,
        autoHeight: true,
        labelWidth: 90,
		items: [
		    new Ext.form.TextField({
				id: 'qryYlMchntNo',
				name: 'qryYlMchntNo',
				fieldLabel: '银联商户号',
				width: 280,
				maxLength:15
			}),new Ext.form.TextField({
				id: 'qryYlTermNo',
				name: 'qryYlTermNo',
				fieldLabel: '银联终端号',
				width: 280,
				maxLength:8
			}),new Ext.form.ComboBox({
				store: payFlagStore,
				displayField: 'displayField',
				valueField: 'valueField',
				emptyText: '请选择',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: false,
				allowBlank: true,
				anchor: '60%',
				id: 'qryPayFlag',
				name: 'qryPayFlag',
				fieldLabel: '支付方式',
				width: 280
			})
		],
		
	});

	//信息窗口
	var addWin = new Ext.Window({
		title: '微信支付宝渠道新增',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 420,
		autoHeight: true,
		layout: 'fit',
		items: [appAddForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		shadow: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(appAddForm.getForm().isValid()) {
					
					ylMchntNo = appAddForm.getForm().findField('ylMchntNoAdd').getValue();
					ylTermNo = appAddForm.getForm().findField('ylTermNoAdd').getValue();
					appId = appAddForm.getForm().findField('appIdAdd').getValue();
					payFlag = appAddForm.getForm().findField('payFlagAdd').getValue();
					mchId = appAddForm.getForm().findField('mchIdAdd').getValue();
					publicKey = appAddForm.getForm().findField('publicKeyAdd').getValue();
					privateKey = appAddForm.getForm().findField('privateKeyAdd').getValue();
					signType = appAddForm.getForm().findField('signTypeAdd').getValue();
					flag = appAddForm.getForm().findField('flagAdd').getValue();
					authorityFlag = appAddForm.getForm().findField('authorityFlagAdd').getValue();
					
					if(ylTermNo =='' || ylTermNo == null){
		            	showErrorMsg("请选择有效终端",grid);
		    			return;
		            }
					
					if(payFlag == 0 && (mchId =='' || mchId == null)){
						showErrorMsg("商户号不能为空",grid);
						return;
					}
					
					if(payFlag == 1){
						if(privateKey =='' || privateKey == null){
							showErrorMsg("支付宝私钥不能为空",grid);
							return;
						}
						if(signType =='' || signType == null){
							showErrorMsg("签名类型不能为空",grid);
							return;
						}
					}
					
					appAddForm.getForm().submit({
						url:'T20111Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						params:{
							txnId:'20111',
							subTxnId:'01',
							ylMchntNo:ylMchntNo,
							ylTermNo:ylTermNo,
							appId:appId,
							payFlag:payFlag,
							mchId:mchId,
							publicKey:publicKey,
							privateKey:privateKey,
							signType:signType,
							flag:flag,
							authorityFlag:authorityFlag
						},
						success:function(form,action) {
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							appAddForm.getForm().reset();appAddForm.findById('mchIdAdd').getEl().up('.x-form-item').setDisplayed(false);
							appAddForm.findById('privateKeyAdd').getEl().up('.x-form-item').setDisplayed(true);
							appAddForm.findById('signTypeAdd').getEl().up('.x-form-item').setDisplayed(true);
							document.getElementById("publicKeyAdd").parentNode.previousSibling.innerHTML="支付宝公钥*:";
							
							showSuccessMsg(action.result.msg,grid);
							addWin.hide();
						},
						failure:function(form,action) {
							showErrorMsg(action.result.msg,grid);

						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				appAddForm.getForm().reset();
				appAddForm.findById('mchIdAdd').getEl().up('.x-form-item').setDisplayed(false);
				appAddForm.findById('privateKeyAdd').getEl().up('.x-form-item').setDisplayed(true);
				appAddForm.findById('signTypeAdd').getEl().up('.x-form-item').setDisplayed(true);
				document.getElementById("publicKeyAdd").parentNode.previousSibling.innerHTML="支付宝公钥*:";
			}
		},{
			text: '关闭',
			handler: function() {
				appAddForm.getForm().reset();
				appAddForm.findById('mchIdAdd').getEl().up('.x-form-item').setDisplayed(false);
				appAddForm.findById('privateKeyAdd').getEl().up('.x-form-item').setDisplayed(true);
				appAddForm.findById('signTypeAdd').getEl().up('.x-form-item').setDisplayed(true);
				document.getElementById("publicKeyAdd").parentNode.previousSibling.innerHTML="支付宝公钥*:";
				addWin.hide();
			}
		}]
	});
	//微信、支付宝渠道修改窗口
	var updateWin = new Ext.Window({
		title: '微信支付宝渠道修改',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		shadow: false,
		layout: 'fit',
		items: [appUpdateForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(appUpdateForm.getForm().isValid()) {
					
					payFlag = appUpdateForm.getForm().findField('payFlagCp').getValue();
					mchId = appUpdateForm.getForm().findField('mchIdCp').getValue();
					privateKey = appUpdateForm.getForm().findField('privateKeyCp').getValue();
					signType = appUpdateForm.getForm().findField('signTypeCp').getValue();
					
					if(payFlag == '微信' && (mchId =='' || mchId == null)){
						showErrorMsg("商户号不能为空",grid);
						return;
					}
					
					if(payFlag == '支付宝'){
						if(privateKey =='' || privateKey == null){
							showErrorMsg("支付宝私钥不能为空",grid);
							return;
						}
						if(signType =='' || signType == null){
							showErrorMsg("签名类型不能为空",grid);
							return;
						}
					}
					
					var array = new Array();
					var data = {
						ylMchntNo : appUpdateForm.getForm().findField('ylMchntNoCp').getValue(),
						ylTermNo : appUpdateForm.getForm().findField('ylTermNoCp').getValue(),
						appId : appUpdateForm.getForm().findField('appIdCp').getValue(),
						payFlag : appUpdateForm.getForm().findField('payFlagCp').getValue(),
						mchId : appUpdateForm.getForm().findField('mchIdCp').getValue(),	
						cKey1 : appUpdateForm.getForm().findField('publicKeyCp').getValue(),
						cKey2 : appUpdateForm.getForm().findField('privateKeyCp').getValue(),
						signType : appUpdateForm.getForm().findField('signTypeCp').getValue(),
						flag : appUpdateForm.getForm().findField('flagCp').getValue(),
						authorityFlag : appUpdateForm.getForm().findField('authorityFlagCp').getValue()
					};
					array.push(data);
					
					Ext.Ajax.requestNeedAuthorise({
						url: 'T20111Action.asp?method=update',
						method: 'post',
						params: {
							mchntDataStr : Ext.encode(array),
							txnId: '20111',
							subTxnId: '02'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							grid.enable();
							if(rspObj.success) {
								grid.getStore().commitChanges();
								showSuccessMsg(rspObj.msg,grid);
								updateWin.hide(grid);
							} else {
								grid.getStore().rejectChanges();
								showErrorMsg(rspObj.msg,grid);
							}
							grid.getStore().reload();
						}
					});
				}
			}
		},{
			text: '关闭',
			handler: function() {
				updateWin.hide(grid);
			}
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
        width: 450,
        autoHeight: true,
        items: [appQueryForm],
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
			handler: function() 
			{
				appStore.load();
                queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				appQueryForm.getForm().reset();
			}
		}]
	 });
	 var addMenu = {
		 text: '新增',
		 width: 85,
		 iconCls: 'add',
		 handler:function() {
			 addWin.show();
			 appAddForm.findById('mchIdAdd').getEl().up('.x-form-item').setDisplayed(false);
			 addWin.center();
		}
	};
	 
	 var updMenu = {
	     text: '修改',
	     width: 85,
		 iconCls: 'edit',
		 handler:function() {
			 var selectedRecord = grid.getSelectionModel().getSelected();
		     if(selectedRecord == null){
		    	 showAlertMsg("没有选择记录",grid);
		         return;
		     }
		     appUpdateForm.getForm().loadRecord(selectedRecord);
			 updateWin.show();
			 appUpdateForm.getForm().findField("ylMchntNoCp").setValue(rec.get('ylMchntNo'));
			 appUpdateForm.getForm().findField("ylTermNoCp").setValue(rec.get('ylTermNo'));
			 appUpdateForm.getForm().findField("payFlagCp").setValue(rec.get('payFlag'));
			 appUpdateForm.getForm().findField("appIdCp").setValue(rec.get('appId'));
			 appUpdateForm.getForm().findField("mchIdCp").setValue(rec.get('mchId'));
			 appUpdateForm.getForm().findField("publicKeyCp").setValue(rec.get('cKey1'));
			 appUpdateForm.getForm().findField("privateKeyCp").setValue(rec.get('cKey2'));
			 appUpdateForm.getForm().findField("signTypeCp").setValue(rec.get('sighType'));
			 appUpdateForm.getForm().findField("flagCp").setValue(rec.get('flag'));
			 appUpdateForm.getForm().findField("authorityFlagCp").setValue(rec.get('authorityFlag'));
			 if(rec.get('flag') == "停用"){
				 appUpdateForm.getForm().findField("flagCp").setValue('0');
			 }else if(rec.get('flag') == "启用"){
				 appUpdateForm.getForm().findField("flagCp").setValue('1');
			 }
			 if(rec.get('authorityFlag') == "否"){
				 appUpdateForm.getForm().findField("authorityFlagCp").setValue('0');
			 }else if(rec.get('authorityFlag') == "是"){
				 appUpdateForm.getForm().findField("authorityFlagCp").setValue('1');
			 }
			 var payFlag = appUpdateForm.getForm().findField('payFlagCp').getValue();
			 if(payFlag == "微信"){
				 appUpdateForm.findById('mchIdCp').getEl().up('.x-form-item').setDisplayed(true);
				 appUpdateForm.findById('privateKeyCp').getEl().up('.x-form-item').setDisplayed(false);
				 appUpdateForm.findById('signTypeCp').getEl().up('.x-form-item').setDisplayed(false);
				 document.getElementById("publicKeyCp").parentNode.previousSibling.innerHTML="微信密钥*:";
			 } else if(payFlag == "支付宝"){
				 appUpdateForm.findById('mchIdCp').getEl().up('.x-form-item').setDisplayed(false);
				 appUpdateForm.findById('privateKeyCp').getEl().up('.x-form-item').setDisplayed(true);
				 appUpdateForm.findById('signTypeCp').getEl().up('.x-form-item').setDisplayed(true);
				 document.getElementById("publicKeyCp").parentNode.previousSibling.innerHTML="支付宝公钥*:";
			 }
			 updateWin.center();
		}
	};
		
	 var delMenu = {
		 text: '删除',
		 width: 85,
		 iconCls: 'delete',
		 handler:function() {
			 var selectedRecord = grid.getSelectionModel().getSelected();
			 if(selectedRecord == null){
				 showAlertMsg("没有选择记录",grid);
			     return;
			 }
			 showConfirm('确定要删除该条信息吗？',grid,function(bt) {
				 if(bt == 'yes') {
					showProcessMsg('正在提交，请稍后......');
					 var rec = grid.getSelectionModel().getSelected();
					 Ext.Ajax.requestNeedAuthorise({
						 url: 'T20111Action.asp?method=delete',
						 method: 'post',
						 params: {
							 ylMchntNo : rec.get('ylMchntNo'),
							 ylTermNo : rec.get('ylTermNo'),
							 payFlag : rec.get('payFlag'),
							 txnId: '20111',
							 subTxnId: '03'
						 },
						 success: function(rsp,opt) {
							 var rspObj = Ext.decode(rsp.responseText);
							 if(rspObj.success) {
								 grid.getStore().commitChanges();
								 showSuccessMsg(rspObj.msg,grid);
							 } else {
								 grid.getStore().rejectChanges();
								 showErrorMsg(rspObj.msg,grid);
							 }
							 grid.getStore().reload();
							 hideProcessMsg();
						 }
					 });
							
				 }
			 });
		  }
	 };
	 var queryMenu = {
	     text: '录入查询条件',
	     width: 85,
	     id: 'query',
	     iconCls: 'query',
	     handler:function() {
	    	 queryWin.show();
	    	 appQueryForm.getForm().reset();
		 }
     };
		
	var menuArr = new Array();
	
	menuArr.push(addMenu);		//[0]
	menuArr.push('-');
	menuArr.push(updMenu);		//[1]
	menuArr.push('-');
	menuArr.push(delMenu);		//[2]
	menuArr.push('-');
	menuArr.push(queryMenu);    //[3]
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '微信、支付宝渠道维护',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: appStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: appMode,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载微信、支付宝渠道信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: appStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});

	//每次在列表信息加载前都将保存按钮屏蔽
	appStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
		    YL_MCHNT_NO: Ext.getCmp('qryYlMchntNo').getValue(),
		    YL_TERM_NO: Ext.getCmp('qryYlTermNo').getValue(),
		    PAY_FLAG: Ext.getCmp('qryPayFlag').getValue()
		});
	});
	appStore.load();
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if(rec != null) {
				grid.getTopToolbar().items.items[1].enable();
			} else {
				grid.getTopToolbar().items.items[1].disable();
			}
		}
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});