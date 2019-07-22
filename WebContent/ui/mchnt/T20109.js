Ext.onReady(function() {
	// 支付通道
	var apiCodeStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0005','微信'],['0007','支付宝'],['0008','QQ钱包'],
		       ['0009','京东钱包'],['0010','百度钱包']],
		reader: new Ext.data.ArrayReader()
	});
	//接入类型
	var operateTypeStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['1','直联'],['2','间联']],
		reader: new Ext.data.ArrayReader()
	});
	
	// 费率标识
	var rateFlagStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','比例费率'],['1','特殊费率']],
		reader: new Ext.data.ArrayReader()
	});
	// 开户类型
	var acctTypeStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['1','对私'],['2','对公']],
		reader: new Ext.data.ArrayReader()
	});
	var mchntInfStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=payChannelInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
            {name: 'outMchntId',mapping: 'OUTMCHNTID'},
			{name: 'operId',mapping: 'OPERID'},			
			{name: 'cmbcMchntId',mapping: 'CMBCMCHNTID'},
			{name: 'apiCode',mapping: 'APICODE'},
			{name: 'industryId',mapping: 'INDUSTRYID'},
			{name: 'operateType',mapping: 'OPERATETYPE'},
			{name: 'dayLimit',mapping: 'DAYLIMIT'},
			{name: 'monthLimit',mapping: 'MONTHLIMIT'},
			{name: 'rateFlag',mapping: 'RATEFLAG'},
			{name: 'feeRate',mapping: 'FEERATE'},
			{name: 'account',mapping: 'ACCOUNT'},
			{name: 'pbcBankId',mapping: 'PBCBANKID'},
			{name: 'acctName',mapping: 'ACCTNAME'},
			{name: 'acctType',mapping: 'ACCTTYPE'},
			{name: 'cmbcSignId',mapping: 'CMBCSIGNID'},
			{name: 'createTime',mapping: 'CREATE_TIME'},
			{name: 'modifyTime',mapping: 'MODIFY_TIME'},
			{name: 'message',mapping: 'MESSAGE'}
		]),
		autoLoad :true
	});
	var mchntInfMode = new Ext.grid.ColumnModel([
		{id: 'outMchntId',header: '外部商户号',dataIndex: 'outMchntId',width: 130,align:'center'},
        {header: '拓展人编号',dataIndex: 'operId',width: 80,align:'center'},
        {header: '民生商户号',dataIndex: 'cmbcMchntId',width: 180,align:'center'},
        {header: '支付通道',dataIndex: 'apiCode',width: 70,align:'center'},
        {header: '商户类别',dataIndex: 'industryId',width: 140,align:'center'},
        {header: '接入类型',dataIndex: 'operateType',width: 75,align:'center'},
        {header: '日限额(分)',dataIndex: 'dayLimit',width: 70,align:'center'},
        {header: '月限额(分)',dataIndex: 'monthLimit',width: 80,align:'center'},
        {header: '费率标识',dataIndex: 'rateFlag',width: 80,align:'center'},
        {header: '费率',dataIndex: 'feeRate',width: 50,align:'center'},
        {header: '结算账号',dataIndex: 'account',width: 150,align:'center'},
        {header: '开户行号',dataIndex: 'pbcBankId',width: 150,align:'center'},
        {header: '开户人',dataIndex: 'acctName',width: 50,align:'center'},
        {header: '开户类型',dataIndex: 'acctType',width: 80,align:'center'},
        {header: '民生签约编号',dataIndex: 'cmbcSignId',width: 180,align:'center'},
        {header: '创建时间',dataIndex: 'createTime',width: 120,align:'center'},
        {header: '修改时间',dataIndex: 'modifyTime',width: 120,align:'center'},
        {header: '民生返回信息',dataIndex: 'message',width: 150,align:'center'}
	]);
	var addPanel = new Ext.TabPanel({
		activeTab: 0,
        height: 400,
        width: 680,
        frame: true,
        items:[{
        	title: '基本信息',
            id: 'infoNew1',
            layout: 'column',
            frame: true,
            items :[{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '拓展人编号*',
		         	labelStyle: 'padding-left: 5px',
		        	allowBlank: false,
		        	id: 'operIdAdd',
		        	width: 220,
		        	maxLength:20,
			        emptyText: '请输入拓展人编号'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '外部商户号*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'outMchntIdAdd',
					width: 220,
					maxLength:64,
					emptyText: '请输入外部商户号'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '民生商户号*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'cmbcMchntIdAdd',
					width: 220,
					maxLength:21,
					emptyText: '请输入民生商户号'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: apiCodeStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'apiCodeAdd',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '支付通道',
					anchor: '60%',
					value: '0005',
					listWidth: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '商户类别*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'industryIdAdd',
					width: 220,
					maxLength:64,
					emptyText: '请输入商户类别'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: operateTypeStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'operateTypeAdd',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '接入类型',
					anchor: '60%',
					value: '1',
					listWidth: 220
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '日限额(分)*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'dayLimitAdd',
	    			width: 220,
	    			maxLength:12,
	    			emptyText: '请输入日限额'
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '月限额(分)*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'monthLimitAdd',
	    			width: 220,
	    			maxLength:12,
	    			emptyText: '请输入月限额'
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: rateFlagStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'rateFlagAdd',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '费率标识',
					anchor: '60%',
					value: '0',
					listWidth: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '费率*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'feeRateAdd',
					width: 220,
					maxLength:100,
					emptyText: '请输入费率'
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '结算账号*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'accountAdd',
					width: 220,
					maxLength:32,
					emptyText: '请输入结算账号'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '开户行号*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'pbcBankIdAdd',
					width: 220,
					maxLength:12,
					emptyText: '请输入开户行号'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '开户人*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'acctNameAdd',
					width: 220,
					maxLength:20,
					emptyText: '请输入开户人姓名'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: acctTypeStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'acctTypeAdd',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '开户类型',
					anchor: '60%',
					value: '1',
					listWidth: 220
				}]					
            },{
            	columnWidth : 1,
				layout : 'form',
				style:'padding-top: 10px',
				items :[{
					xtype:'textarea',
					labelStyle: 'padding-left: 5px',
					id:'responseAdd',
			        grow:true,
			        growMin:140,
			        growMax:140,
			        fieldLabel: '返回内容',
			        width: 400
				}]
            }]
        }]
	});
	var updatePanel= new Ext.TabPanel({
		activeTab: 0,
        height: 400,
        width: 680,
        frame: true,
        items:[{
        	title: '基本信息',
            id: 'infoNew2',
            layout: 'column',
            frame: true,
            items :[{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '外部商户号*',
					width: 220,
					maxLength: 32,
					disabled : true,
					editable: false,
					maxLength:64,
					id: 'outMchntIdUp'
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '民生商户号*',
					width: 220,
					disabled : true,
					editable: false,
					maxLength:21,
					id: 'cmbcMchntIdUp'
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '拓展人编号*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'operIdUp',
	    			maxLength:20,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: apiCodeStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'apiCodeUp',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '支付通道',
					anchor: '60%',
					listWidth: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '民生签约编号*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'cmbcSignIdUp',
	    			maxLength:21,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '日限额(分)*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'dayLimitUp',
	    			maxLength:12,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '月限额(分)*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'monthLimitUp',
	    			maxLength:12,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: rateFlagStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'rateFlagUp',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '费率标识',
					anchor: '60%',
					listWidth: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '费率*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'feeRateUp',
	    			maxLength:100,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '结算账号*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'accountUp',
	    			maxLength:32,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '开户行号*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'pbcBankIdUp',
	    			maxLength:12,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '开户人*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'acctNameUp',
	    			maxLength:20,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: acctTypeStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'acctTypeUp',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '开户类型',
					anchor: '60%',
					listWidth: 220
				}]					
            },{
            	columnWidth : 1,
				layout : 'form',
				style:'padding-top: 10px',
				items :[{
					xtype:'textarea',
					labelStyle: 'padding-top: 5px',
					labelStyle: 'padding-left: 5px',
					id:'responseUp',
			        grow:true,
			        growMin:140,
			        growMax:140,
			        fieldLabel: '返回内容',
			        width: 400
				}]
            }]
        }]
	});
	//添加表单
	var addForm = new Ext.form.FormPanel({
		frame: true,
		height: 400,
		width: 680,
		labelWidth: 85,
		waitMsgTarget: true,
		layout: 'column',
		items: [addPanel]
	});
	// 修改表单
	var updateForm = new Ext.form.FormPanel({
		frame: true,
		height: 400,
		width: 680,
		labelWidth: 85,
		waitMsgTarget: true,
		layout: 'column',
		items: [updatePanel]
	});
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype:'textfield',
			fieldLabel: '外部商户号',
			labelStyle: 'padding-left: 5px',
			allowBlank: true,
			id: 'outMchntId',
			width: 220,
			maxLength:64,
			emptyText: '请输入外部商户号'
		},{
			xtype:'textfield',
			fieldLabel: '民生商户号',
			labelStyle: 'padding-left: 5px',
			allowBlank: true,
			id: 'cmbcMchntId',
			width: 220,
			maxLength:21,
			emptyText: '请输入民生商户号'
		},{
			xtype: 'combo',
			store: apiCodeStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'apiCode',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '支付通道',
			anchor: '60%',
			listWidth: 220
		}]
	});
	var queryInfForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 430,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype:'textnotnull',
			fieldLabel: '外部商户号*',
			labelStyle: 'padding-left: 5px',
			allowBlank: false,
			id: 'outMchntIdInf',
			width: 220,
			maxLength:64,
			emptyText: '请输入外部商户号'
		},{
			xtype:'textnotnull',
			fieldLabel: '民生商户号*',
			labelStyle: 'padding-left: 5px',
			allowBlank: false,
			id: 'cmbcMchntIdInf',
			width: 220,
			maxLength:21,
			emptyText: '请输入民生商户号'
		},{
			xtype: 'combo',
			store: apiCodeStore,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择',
			id:'apiCodeInf',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '支付通道',
			anchor: '60%',
			value: '0005',
			listWidth: 220
		},{
			xtype:'textarea',
			labelStyle: 'padding-top: 5px',
			labelStyle: 'padding-left: 5px',
			id:'response',
	        grow:true,
	        growMin:140,
	        growMax:140,
	        fieldLabel: '返回内容',
	        width: 300
		}]
	});
	var addMenu = {
		text: '添加绑定',
		width: 85,
		iconCls: 'add',
		handler:function() {
			addWin.show();
			addWin.center();
		}
	};
	var updMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		handler:function() {
			var selectedRecord = grid.getSelectionModel().getSelected();
	        if(selectedRecord == null)
	        {
	            showAlertMsg("没有选择记录",grid);
	            return;
	        }
			if(grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				updateForm.getForm().findField("outMchntIdUp").setValue(rec.get('outMchntId'));
				updateForm.getForm().findField("cmbcMchntIdUp").setValue(rec.get('cmbcMchntId'));
				updateForm.getForm().findField("operIdUp").setValue(rec.get('operId'));
				if(rec.get('apiCode') == "微信"){
					updateForm.getForm().findField("apiCodeUp").setValue('0005');
				}else if(rec.get('apiCode') == "支付宝"){
					updateForm.getForm().findField("apiCodeUp").setValue('0007');
				}else if(rec.get('apiCode') == "QQ钱包"){
					updateForm.getForm().findField("apiCodeUp").setValue('0008');
				}else if(rec.get('apiCode') == "京东钱包"){
					updateForm.getForm().findField("apiCodeUp").setValue('0009');
				}else if(rec.get('apiCode') == "百度钱包"){
					updateForm.getForm().findField("apiCodeUp").setValue('0010');
				}
				updateForm.getForm().findField("cmbcSignIdUp").setValue(rec.get('cmbcSignId'));
				updateForm.getForm().findField("dayLimitUp").setValue(rec.get('dayLimit'));
				updateForm.getForm().findField("monthLimitUp").setValue(rec.get('monthLimit'));
				if(rec.get('rateFlag') == "比例费率"){
					updateForm.getForm().findField("rateFlagUp").setValue('0');
				}else if(rec.get('rateFlag') == "特殊费率"){
					updateForm.getForm().findField("rateFlagUp").setValue('1');    
				}
				updateForm.getForm().findField("feeRateUp").setValue(rec.get('feeRate'));
				updateForm.getForm().findField("accountUp").setValue(rec.get('account'));
				updateForm.getForm().findField("pbcBankIdUp").setValue(rec.get('pbcBankId'));
				updateForm.getForm().findField("acctNameUp").setValue(rec.get('acctName'));
				if(rec.get('acctType') == "对私"){
					updateForm.getForm().findField("acctTypeUp").setValue('1');
				}else if(rec.get('acctType') == "对公"){
					updateForm.getForm().findField("acctTypeUp").setValue('0');    
				}
				updateWin.show();
				updateWin.center();
			}	
		}
	};
	var queryMenu = {
		text: '录入查询条件',
	    width: 85,
	    id: 'query',
	    iconCls: 'query',
	    handler:function() {
	      queryWin.show();
	      queryForm.getForm().reset();
	    }
	};
	var queryInfMenu = {
		text: '查询接口条件',
		width: 85,
		id: 'queryInf',
		iconCls: 'query',
		handler:function() {
			queryInfWin.show();
			queryInfForm.getForm().reset();
		}
	};
	
	
	//信息窗口
	var addWin = new Ext.Window({
		title: '民生支付渠道添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 680,
		autoHeight: true,
		layout: 'fit',
		items: [addForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(addForm.getForm().isValid()) {
					operId = addForm.getForm().findField("operIdAdd").getValue();
					outMchntId = addForm.getForm().findField("outMchntIdAdd").getValue();
					cmbcMchntId = addForm.getForm().findField("cmbcMchntIdAdd").getValue();
					apiCode = addForm.getForm().findField("apiCodeAdd").getValue();
					industryId = addForm.getForm().findField("industryIdAdd").getValue();
					operateType = addForm.getForm().findField("operateTypeAdd").getValue();
					dayLimit = addForm.getForm().findField("dayLimitAdd").getValue();
					monthLimit = addForm.getForm().findField("monthLimitAdd").getValue();
					rateFlag = addForm.getForm().findField("rateFlagAdd").getValue();
					feeRate = addForm.getForm().findField("feeRateAdd").getValue();
					account = addForm.getForm().findField("accountAdd").getValue();
					pbcBankId = addForm.getForm().findField("pbcBankIdAdd").getValue();
					acctName = addForm.getForm().findField("acctNameAdd").getValue();
					acctType = addForm.getForm().findField("acctTypeAdd").getValue();
					Ext.Ajax.requestNeedAuthorise({
						url: 'payChannelAction.asp?method=add',
						method: 'post',
						params: {
							operId:operId,
							outMchntId:outMchntId,
							cmbcMchntId:cmbcMchntId,
							apiCode:apiCode,
							industryId:industryId,
							operateType:operateType,
							dayLimit:dayLimit,
							monthLimit:monthLimit,
							rateFlag:rateFlag,
							feeRate:feeRate,
							account:account,
							pbcBankId:pbcBankId,
							acctName:acctName,
							acctType:acctType,
							txnId: '20109',
							subTxnId: '00'
						},
						success : function(response, options) { 
							var rspObj = Ext.decode(response.responseText);
							addForm.getForm().findField("responseAdd").setValue(rspObj.msg);
							grid.getStore().reload();
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				addForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addForm.getForm().reset();
				addWin.hide();
			}
		}]
	});
	//民生支付渠道修改
	var updateWin = new Ext.Window({
		title: '民生支付渠道修改',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 680,
		autoHeight: true,
		layout: 'fit',
		items: [updateForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{							
			text: '确定',
			handler: function() {
				if(updateForm.getForm().isValid()) {
					operId = updateForm.getForm().findField('operIdUp').getValue();
					outMchntId = updateForm.getForm().findField('outMchntIdUp').getValue();
					cmbcMchntId = updateForm.getForm().findField('cmbcMchntIdUp').getValue();
					cmbcSignId = updateForm.getForm().findField('cmbcSignIdUp').getValue();
					apiCode = updateForm.getForm().findField('apiCodeUp').getValue();
					dayLimit = updateForm.getForm().findField('dayLimitUp').getValue();
					monthLimit = updateForm.getForm().findField('monthLimitUp').getValue();
					rateFlag = updateForm.getForm().findField('rateFlagUp').getValue();
					feeRate = updateForm.getForm().findField('feeRateUp').getValue();
					account = updateForm.getForm().findField('accountUp').getValue();
					pbcBankId = updateForm.getForm().findField('pbcBankIdUp').getValue();
					acctName = updateForm.getForm().findField('acctNameUp').getValue();
					acctType = updateForm.getForm().findField('acctTypeUp').getValue();
					Ext.Ajax.requestNeedAuthorise({
						url: 'payChannelAction.asp?method=update',
						method: 'post',
						params: {
							operId:operId,
							outMchntId:outMchntId,
							cmbcMchntId:cmbcMchntId,
							cmbcSignId:cmbcSignId,
							apiCode:apiCode,
							dayLimit:dayLimit,
							monthLimit:monthLimit,
							rateFlag:rateFlag,
							feeRate:feeRate,
							account:account,
							pbcBankId:pbcBankId,
							acctName:acctName,
							acctType:acctType,
							txnId: '20109',
							subTxnId: '01'
						},
						success : function(response, options) { 
							var rspObj = Ext.decode(response.responseText);
							updateForm.getForm().findField("responseUp").setValue(rspObj.msg);
							grid.getStore().reload();
						}
					});
				}
			}
		},{
			text: '关闭',
			handler: function() {
				updateWin.hide();
				updateForm.getForm().findField("responseUp").reset();
			}
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 400,
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
				disid = '';
				mchntInfStore.load();;
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	var queryInfWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 430,
		autoHeight: true,
		items: [queryInfForm],
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
				queryInfWin.collapse();
				queryInfWin.getEl().pause(1);
				queryInfWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryInfWin.expand();
				queryInfWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				if(queryInfForm.getForm().isValid()) {
					outMchntId = queryInfForm.getForm().findField('outMchntIdInf').getValue();
					cmbcMchntId = queryInfForm.getForm().findField('cmbcMchntIdInf').getValue();
					apiCode = queryInfForm.getForm().findField('apiCodeInf').getValue();
					Ext.Ajax.requestNeedAuthorise({
						url: 'payChannelAction.asp?method=query',
						method: 'post',
						params: {
							outMchntId:outMchntId,
							cmbcMchntId:cmbcMchntId,
							apiCode:apiCode,
							txnId: '20109',
							subTxnId: '02'
						},
						success : function(response, options) { 
							var rspObj = Ext.decode(response.responseText);
							queryInfForm.getForm().findField("response").setValue(rspObj.msg);
							grid.getStore().reload();
						}
					});
				}
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryInfForm.getForm().reset();
			}
		}]
	});
	var backMenu = {
	        text: '返回',
	        width: 85,
	        iconCls: 'back',
	        id:'backmenu',
	        disabled: true,
	        handler:function() {
	        	window.location.href = Ext.contextPath + '/page/mchnt/T20108.jsp';
	        }
	    };
	var menuArr = new Array();
	
	menuArr.push(addMenu);		//[0]
	menuArr.push('-');
	menuArr.push(updMenu);		//[1]
	menuArr.push('-');
	menuArr.push(queryMenu);		//[2]
	menuArr.push('-');
	menuArr.push(queryInfMenu);    //[3]
	menuArr.push('-');
	menuArr.push(backMenu);
	
	// 民生支付渠道列表
	var grid = new Ext.grid.GridPanel({
		title: '民生支付渠道维护',
		iconCls: 'T501',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntInfStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntInfMode,
		clicksToEdit: true,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载联机交易列表......'
		},
		tbar: menuArr,
		bbar:  new Ext.PagingToolbar({
			store: mchntInfStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});

	//每次在列表信息加载前都将保存按钮屏蔽
	mchntInfStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
		    outMchntId: queryForm.findById('outMchntId').getValue() || disid,
			cmbcMchntId : queryForm.findById('cmbcMchntId').getValue(),
			apiCode : queryForm.findById('apiCode').getValue()
		});
	});
	mchntInfStore.load();
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
	if(disid){
		Ext.getCmp('backmenu').enable();
	}
})