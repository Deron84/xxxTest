Ext.onReady(function() {
	var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	
	SelectOptionsDWR.getMchntData('MCHNT_NAME',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	// 拓展模式
	var devTypeStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['1','神思电子'],['2','民生银行']],
		reader: new Ext.data.ArrayReader()
	}); 
	// 是否持证
	var isCertStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','非持证商户'],['1','持证商户']],
		reader: new Ext.data.ArrayReader()
	});
	// 结算方式
	var autoSettleStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['1','自动结算'],['2','手动提现']],
		reader: new Ext.data.ArrayReader()
	});
	//城市
	var cityStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	//地区代码	
	var acdCodeStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	function linkrender(val) {
		return "<a href='" + Ext.contextPath
				+ "/page/mchnt/T20109.jsp?outMchntId=" + val + "'>" + val
				+ "</a>";
	}
	var mchntInfStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntMSInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
            {name: 'outMchntId',mapping: 'OUTMCHNTID'},
			{name: 'operId',mapping: 'OPERID'},			
			{name: 'mchntName',mapping: 'MCHNTNAME'},
			{name: 'mchntFullName',mapping: 'MCHNTFULLNAME'},
			{name: 'devType',mapping: 'DEVTYPE'},
			{name: 'acdCode',mapping: 'ACDCODE'},
			{name: 'province',mapping: 'PROVINCE'},
			{name: 'city',mapping: 'CITY'},
			{name: 'address',mapping: 'ADDRESS'},
			{name: 'isCert',mapping: 'ISCERT'},
			{name: 'licId',mapping: 'LICID'},
			{name: 'idTCard',mapping: 'IDTCARD'},
			{name: 'corpName',mapping: 'CORPNAME'},
			{name: 'telephone',mapping: 'TELEPHONE'},
			{name: 'autoSettle',mapping: 'AUTOSETTLE'},
			{name: 'status',mapping: 'STATUS'},
			{name: 'cmbcMchntId',mapping: 'CMBCMCHNTID'},
			{name: 'message',mapping: 'MESSAGE'}
		]),
		autoLoad :true
	});
	var mchntInfMode = new Ext.grid.ColumnModel([
		{id: 'outMchntId',header: '外部商户号',dataIndex: 'outMchntId',width: 130,align:'center',renderer : linkrender},
        {header: '拓展人编号',dataIndex: 'operId',width: 80,align:'center'},
        {header: '商户简称',dataIndex: 'mchntName',width: 100,align:'center'},
        {header: '商户全称',dataIndex: 'mchntFullName',width: 200,align:'center'},
        {header: '拓展模式',dataIndex: 'devType',width: 70,align:'center'},
        {header: '地区编号',dataIndex: 'acdCode',width: 75,align:'center'},
        {header: '省份',dataIndex: 'province',width: 50,align:'center'},
        {header: '城市',dataIndex: 'city',width: 50,align:'center'},
        {header: '地址',dataIndex: 'address',width: 200,align:'center'},
        {header: '是否持证',dataIndex: 'isCert',width: 80,align:'center'},
        {header: '营业执照号',dataIndex: 'licId',width: 150,align:'center'},
        {header: '身份证件号',dataIndex: 'idTCard',width: 150,align:'center'},
        {header: '联系人',dataIndex: 'corpName',width: 50,align:'center'},
        {header: '联系电话',dataIndex: 'telephone',width: 100,align:'center'},
        {header: '结算方式',dataIndex: 'autoSettle',width: 80,align:'center'},
        {header: '商户状态',dataIndex: 'status',width: 80,align:'center'},
        {header: '民生商户号',dataIndex: 'cmbcMchntId',width: 180,align:'center'},
        {header: '民生返回信息',dataIndex: 'message',width: 150,align:'center'}
	]);
	var addPanel = new Ext.TabPanel({
		activeTab: 0,
        height: 420,
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
					fieldLabel: '商户简称*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'mchntNameAdd',
					width: 220,
					maxLength:100,
					emptyText: '请输入商户简称'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '商户全称*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'mchntFullNameAdd',
					width: 220,
					maxLength:200,
					emptyText: '请输入商户全称'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: devTypeStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'devTypeAdd',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '拓展模式',
					anchor: '60%',
					value: '1',
					listWidth: 220
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype : 'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					methodName : 'getProvince',
					fieldLabel: '省份*',
					hiddenName: 'provinceAdd',
					emptyText: '请选择省份',
					width: 220,
					anchor: '80%',
					editable: true,
					listeners:{
						'select': function() {
							var provinceAdd = addForm.getForm().findField('provinceAdd').getValue();
				              //   	mchntForm.getForm().findField('mchtFlag2').enable();
							cityStore.removeAll();
							acdCodeStore.removeAll();
				            SelectOptionsDWR.getTermData('AREANAME',provinceAdd,function(ret){
				            	var data=Ext.decode(ret);
				            	cityStore.loadData(data);
							});
				            addForm.getForm().findField('cityAdd').reset();
						}
					}
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: cityStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'cityAdd',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: true,
					allowBlank: true,
					fieldLabel: '城市*',
					anchor: '65%',
					width: 220,
					listeners:{
						'select': function() {
							var cityAdd = addForm.getForm().findField('cityAdd').getValue();
							var provinceAdd = addForm.getForm().findField('provinceAdd').getValue();
				              //   	mchntForm.getForm().findField('mchtFlag2').enable();
							acdCodeStore.removeAll();
				            SelectOptionsDWR.getAreaCodeNew('AREACODE',cityAdd,provinceAdd,function(ret){
				            	var data=Ext.decode(ret);
				            	acdCodeStore.loadData(data);
							});
				            addForm.getForm().findField('acdCodeAdd').reset();
						}
					}
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: acdCodeStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'acdCodeAdd',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: true,
					allowBlank: true,
					fieldLabel: '区/县*',
					anchor: '65%',
					width: 220
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '地址*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'addressAdd',
					width: 220,
					maxLength:80,
					emptyText: '请输入地址'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: isCertStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'isCertAdd',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '是否持证',
					anchor: '60%',
					value: '1',
					listWidth: 220
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textfield',
					fieldLabel: '营业执照号',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					id: 'licIdAdd',
					width: 220,
					maxLength: 20,
					emptyText: '请输入营业执照号'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textfield',
					fieldLabel: '身份证件号',
					labelStyle: 'padding-left: 5px',
					allowBlank: true,
					id: 'idTCardAdd',
					width: 220,
					maxLength:20,
					emptyText: '请输入身份证件号'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '联系人*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'corpNameAdd',
					width: 220,
					maxLength:60,
					emptyText: '请输入联系人姓名'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
					fieldLabel: '联系电话*',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id: 'telephoneAdd',
					width: 220,
					maxLength:11,
					emptyText: '请输入联系电话'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: autoSettleStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'autoSettleAdd',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '结算方式',
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
        height: 370,
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
					editable: false,
					disabled : true,
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
					xtype:'textnotnull',
	    			fieldLabel: '商户简称*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			maxLength:100,
	    			id: 'mchntNameUp',
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '商户全称*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'mchntFullNameUp',
	    			maxLength:200,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '地址*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'addressUp',
	    			maxLength:80,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: isCertStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'isCertUp',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '是否持证',
					anchor: '60%',
					value: '1',
					listWidth: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textfield',
	    			fieldLabel: '营业执照号',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: true,
	    			id: 'licIdUp',
	    			maxLength: 20,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textfield',
	    			fieldLabel: '身份证件号',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: true,
	    			id: 'idTCardUp',
	    			maxLength:20,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '联系人*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'corpNameUp',
	    			maxLength:60,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype:'textnotnull',
	    			fieldLabel: '联系电话*',
	    			labelStyle: 'padding-left: 5px',
	    			allowBlank: false,
	    			id: 'telephoneUp',
	    			maxLength:11,
	    			width: 220
				}]					
            },{
            	columnWidth : .5,
				layout : 'form',
				items :[{
					xtype: 'combo',
					store: autoSettleStore,
					labelStyle: 'padding-left: 5px',
					displayField: 'displayField',
					valueField: 'valueField',
					emptyText: '请选择',
					id:'autoSettleUp',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: false,
					allowBlank: true,
					fieldLabel: '结算方式',
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
		height: 420,
		width: 680,
		labelWidth: 85,
		waitMsgTarget: true,
		layout: 'column',
		items: [addPanel]
	});
	// 修改表单
	var updateForm = new Ext.form.FormPanel({
		frame: true,
		height: 370,
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
			maxLength:64,
			width: 220,
			emptyText: '请输入外部商户号'
		},{
			xtype:'textfield',
			fieldLabel: '商户简称',
			labelStyle: 'padding-left: 5px',
			allowBlank: true,
			id: 'mchntName',
			width: 220,
			maxLength:100,
			emptyText: '请输入商户简称'
		},{
			xtype:'textfield',
			fieldLabel: '商户全称',
			labelStyle: 'padding-left: 5px',
			allowBlank: true,
			id: 'mchntFullName',
			width: 220,
			maxLength:200,
			emptyText: '请输入商户商户全称'
		},{
			xtype:'textfield',
			fieldLabel: '民生商户号',
			labelStyle: 'padding-left: 5px',
			allowBlank: true,
			id: 'cmbcMchntId',
			width: 220,
			maxLength:21,
			emptyText: '请输入民生商户号'
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
			fieldLabel: '外部商户号',
			labelStyle: 'padding-left: 5px',
			allowBlank: false,
			id: 'outMchntIdInf',
			width: 220,
			maxLength:64,
			emptyText: '请输入外部商户号'
		},{
			xtype:'textarea',			
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
		text: '新增',
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
				updateForm.getForm().findField("mchntNameUp").setValue(rec.get('mchntName'));
				updateForm.getForm().findField("mchntFullNameUp").setValue(rec.get('mchntFullName'));
				updateForm.getForm().findField("addressUp").setValue(rec.get('address'));
				if(rec.get('isCert') == "非持证商户"){
					updateForm.getForm().findField("isCertUp").setValue('0');
				}else if(rec.get('isCert') == "持证商户"){
					updateForm.getForm().findField("isCertUp").setValue('1');
				}
				updateForm.getForm().findField("licIdUp").setValue(rec.get('licId'));
				updateForm.getForm().findField("idTCardUp").setValue(rec.get('idTCard'));
				updateForm.getForm().findField("corpNameUp").setValue(rec.get('corpName'));
				updateForm.getForm().findField("telephoneUp").setValue(rec.get('telephone'));
				if(rec.get('autoSettle') == "自动结算"){
					updateForm.getForm().findField("autoSettleUp").setValue('1');
				}else if(rec.get('autoSettle') == "手工提现"){
					updateForm.getForm().findField("autoSettleUp").setValue('2');
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
		title: '民生商户添加',
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
			text: '添加',
			handler: function() {
				var rec = addForm.getForm();
				if(addForm.getForm().isValid()) {
					operId = addForm.getForm().findField("operIdAdd").getValue();
					outMchntId = addForm.getForm().findField("outMchntIdAdd").getValue();
					mchntName = addForm.getForm().findField("mchntNameAdd").getValue();
					mchntFullName = addForm.getForm().findField("mchntFullNameAdd").getValue();
					devType = addForm.getForm().findField("devTypeAdd").getValue();
					acdCode = addForm.getForm().findField("acdCodeAdd").getValue();
					province = addForm.getForm().findField("provinceAdd").getValue();
					city = addForm.getForm().findField("cityAdd").getValue();
					address = addForm.getForm().findField("addressAdd").getValue();
					isCert = addForm.getForm().findField("isCertAdd").getValue();
					licId = addForm.getForm().findField("licIdAdd").getValue();
					idTCard = addForm.getForm().findField("idTCardAdd").getValue();
					corpName = addForm.getForm().findField("corpNameAdd").getValue();
					telephone = addForm.getForm().findField("telephoneAdd").getValue();
					autoSettle = addForm.getForm().findField("autoSettleAdd").getValue();
					if(isCert=='1' && (licId=='' ||licId==null)){
						alert('持证商户营业执照号不能为空！');
						return;
					}
					Ext.Ajax.requestNeedAuthorise({
						url: 'mchntMSAction.asp?method=add',
						method: 'post',
						params: {
							operId:operId,
							outMchntId:outMchntId,
							mchntName:mchntName,
							mchntFullName:mchntFullName,
							devType:devType,
							acdCode:acdCode,
							province:province,
							city:city,
							address:address,
							isCert:isCert,
							licId:licId,
							idTCard:idTCard,
							corpName:corpName,
							telephone:telephone,
							autoSettle:autoSettle,
							txnId: '20108',
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
	//民生商户信息修改
	var updateWin = new Ext.Window({
		title: '民生商户信息修改',
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
					operId = updateForm.getForm().findField("operIdUp").getValue();
					outMchntId = updateForm.getForm().findField("outMchntIdUp").getValue();
					cmbcMchntId = updateForm.getForm().findField("cmbcMchntIdUp").getValue();
					mchntName = updateForm.getForm().findField("mchntNameUp").getValue();
					mchntFullName = updateForm.getForm().findField("mchntFullNameUp").getValue();
					address = updateForm.getForm().findField("addressUp").getValue();
					isCert = updateForm.getForm().findField("isCertUp").getValue();
					licId = updateForm.getForm().findField("licIdUp").getValue();
					idTCard = updateForm.getForm().findField("idTCardUp").getValue();
					corpName = updateForm.getForm().findField("corpNameUp").getValue();
					telephone = updateForm.getForm().findField("telephoneUp").getValue();
					autoSettle = updateForm.getForm().findField("autoSettleUp").getValue();
					if(isCert=='1' && (licId=='' ||licId==null)){
						alert('持证商户营业执照号不能为空！');
						return;
					}
					Ext.Ajax.requestNeedAuthorise({
						url: 'mchntMSAction.asp?method=update',
						method: 'post',
						params: {
							operId:operId,
							outMchntId:outMchntId,
							cmbcMchntId:cmbcMchntId,
							mchntName:mchntName,
							mchntFullName:mchntFullName,
							address:address,
							isCert:isCert,
							licId:licId,
							idTCard:idTCard,
							corpName:corpName,
							telephone:telephone,
							autoSettle:autoSettle,
							txnId: '20108',
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
					outMchntId = queryInfForm.getForm().findField("outMchntIdInf").getValue();
					Ext.Ajax.requestNeedAuthorise({
						url: 'mchntMSAction.asp?method=query',
						method: 'post',
						params: {
							outMchntId:outMchntId,
							txnId: '20108',
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
	var menuArr = new Array();
	
	menuArr.push(addMenu);		//[0]
	menuArr.push('-');
	menuArr.push(updMenu);		//[1]
	menuArr.push('-');
	menuArr.push(queryMenu);		//[2]
	menuArr.push('-');
	menuArr.push(queryInfMenu);    //[3]
	
	// 民生商户信息列表
	var grid = new Ext.grid.GridPanel({
		title: '民生商户信息维护',
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
		    outMchntId: queryForm.findById('outMchntId').getValue(),
			mchntName: queryForm.findById('mchntName').getValue(),
			mchntFullName : queryForm.findById('mchntFullName').getValue(),
			cmbcMchntId : queryForm.findById('cmbcMchntId').getValue()
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
})