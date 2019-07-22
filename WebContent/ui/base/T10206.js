Ext.onReady(function() {
	
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});

	SelectOptionsDWR.getComboData('BRH_ID',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});

	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CARD_TYPE',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=emvPara'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'paraIdx',mapping: 'paraIdx'},
			{name: 'usageKey',mapping: 'usageKey'},
			{name: 'paraOrg',mapping: 'paraOrg'},
			{name: 'paraSta',mapping: 'paraSta'},
			{name: '9F06',mapping: '9F06'},
			{name: '9F22',mapping: '9F22'},
			{name: 'DF05',mapping: 'DF05'},
			{name: 'DF06',mapping: 'DF06'},
			{name: 'DF07',mapping: 'DF07'},
			{name: 'DF02',mapping: 'DF02'},
			{name: 'DF04',mapping: 'DF04'},
			{name: 'DF03',mapping: 'DF03'}
		])
	});
	
	mchntStore.load({
		params:{start: 0}
	});
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>操作柜员：{recOprId}</p>',
			'<p>修改操作员：{recUpdOpr}</p>',
			'<p>记录创建时间：{recCrtTs}</p>',
			'<p>记录修改时间：{recUpdTs}</p>'
		)
	});
	
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<table width="85%"  style="word-break:break-all; word-wrap:break-all;">' +
			'<tr><td><font color="green">根CA公钥模</font></td><tr><tr><td>{DF02}</td></tr>' +
			'<tr><td><font color="green">哈希结果</font></td><tr><tr><td>{DF03}</td></tr>' +
			'</table>'
		)
	});
	
	
	
	var oprColModel = new Ext.grid.ColumnModel([
			rowExpander,
    		{header: '索引',dataIndex: 'paraIdx',sortable: true,width: 50,align: 'center'},
    		{header: '参数名称',dataIndex: 'paraOrg',width: 60,align: 'center'},
    		{header: '注册应用提供商标识',dataIndex: '9F06',width: 120,align: 'center',id: 'A9F06'},
    		{header: '根CA公钥索引',dataIndex: '9F22',width: 90,align: 'center'},
    		{header: '证书失效日期',dataIndex: 'DF05',align: 'center',renderer: formatTs,width: 90},
    		{header: '哈希算法标识',dataIndex: 'DF06',align: 'center',width: 90},
    		{header: '数字签名算法长度',dataIndex: 'DF07',align: 'center',width: 110},
    		{header: '根CA公钥指数',dataIndex: 'DF04',align: 'center',width: 110}
    	]);
	
		
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
	};
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			oprWin.show();
			oprWin.center();
		}
	};

	var paramInfoForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 340,
		labelWidth : 120,
		autoHeight: true,
		items: [{
        	xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '参数的名称*',
			allowBlank: false,
			width: 155,
			blankText: '参数的名称不能为空',
			maxLength: '3',
			vtype: 'isOverMax',
			id: 'paraOrg',
			name: 'paraOrg'
    	},{
        	xtype: 'textfield',
        	width: 155,
			labelStyle: 'padding-left: 5px',
			fieldLabel: '注册应用提供商标识*',
			allowBlank: false,
			blankText: '注册应用提供商标识不能为空',
			minLength: 10,
			minLengthText: '注册应用提供商标识为10位',
			maxLength: 10,
			maxLengthText: '注册应用提供商标识为10位',
			vtype: 'isOverMax',
			regex: /^[0-9A-F]+$/,
			regexText: '该输入框只能输入字符0-9或A-F',
			maskRe: /^[0-9A-F]+$/,
			id: 'A9F06',
			name: 'A9F06'
    	},{
        	xtype: 'textfield',
        	width: 155,
			labelStyle: 'padding-left: 5px',
			fieldLabel: '根CA公钥索引*',
			allowBlank: false,
			blankText: '根CA公钥索引不能为空',
			maxLength: 2,
			maxLengthText: '根CA公钥索引最大长度为2位',
			vtype: 'isOverMax',
			regex: /^[0-9A-F]+$/,
			regexText: '该输入框只能输入字符0-9或A-F',
			maskRe: /^[0-9A-F]+$/,
			id: 'A9F22',
			name: 'A9F22'
    	},{
			xtype: 'datefield',
			fieldLabel: '证书失效日期*',
			labelStyle: 'padding-left: 5px',
			maxLength: 8,
			format: 'Ymd',
			width: 155,
			allowBlank: false,
			blankText: '证书失效日期不能为空',
			id: 'DF05',
			name: 'DF05'
    	},{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['01','RSA']],
				reader: new Ext.data.ArrayReader()
			}),
			width: 155,
			labelStyle: 'padding-left: 5px',
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择哈希算法标识',
			hiddenName: 'DF06',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: false,
			//regex: /^[0-9A-F]+$/,
			//regexText: '该输入框只能输入字符0-9或A-F',
			maskRe: /^[0-9A-F]+$/,
			fieldLabel: '哈希算法标识*'
		},{
        	xtype: 'textfield',
        	width: 155,
			labelStyle: 'padding-left: 5px',
			fieldLabel: '数字签名算法长度*',
			allowBlank: false,
			blankText: '数字签名算法长度不能为空',
			vtype: 'isOverMax',
			id: 'DF07',
			name: 'DF07',
			regex: /^[0-9A-F]+$/,
			regexText: '该输入框只能输入字符0-9或A-F',
			maskRe: /^[0-9A-F]+$/,
			maxLength: 2,
			maxLengthText: '数字签名算法长度最大长度为2位'
    	},{
        	xtype: 'textfield',
        	width: 155,
			labelStyle: 'padding-left: 5px',
			fieldLabel: '根CA公钥指数*',
			allowBlank: false,
			blankText: '根CA公钥指数不能为空',
			maxLength: 2,
			maxLengthText: '根CA公钥指数最大长度为2位',
			vtype: 'isOverMax',
			id: 'DF04',
			name: 'DF04',
			regex: /^[0-9A-F]+$/,
			regexText: '该输入框只能输入字符0-9或A-F',
			maskRe: /^[0-9A-F]+$/,
			regexText:'该输入项只能包含数字'
    	},{
        	xtype: 'textarea',
        	width: 155,
        	height: 120,
			labelStyle: 'padding-left: 5px',
			fieldLabel: '根CA公钥模*',
			allowBlank: false,
			blankText: '根CA公钥模不能为空',
			vtype: 'isOverMax',
			regex: /^[0-9A-F]+$/,
			regexText: '该输入框只能输入字符0-9或A-F',
			maskRe: /^[0-9A-F]+$/,
			id: 'DF02',
			name: 'DF02'
    	},{
        	xtype: 'textarea',
        	width: 155,
        	height: 50,
			labelStyle: 'padding-left: 5px',
			fieldLabel: '哈希结果*',
			allowBlank: false,
			blankText: '哈希结果不能为空',
			vtype: 'isOverMax',
			regex: /^[0-9A-F]+$/,
			regexText: '该输入框只能输入字符0-9或A-F',
			maskRe: /^[0-9A-F]+$/,
			id: 'DF03',
			name: 'DF03'
    	}]
	});

	var oprWin = new Ext.Window({
		title: '银联CA公钥信息添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 340,
		autoHeight: true,
		layout: 'fit',
		items: [paramInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(paramInfoForm.getForm().isValid()) {
					paramInfoForm.getForm().submit({
						url: 'T10206Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,paramInfoForm);
							//重置表单
							paramInfoForm.getForm().reset();
							//重新加载列表
							oprGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,paramInfoForm);
						},
						params: {
							txnId: '10206',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				paramInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				oprWin.hide(oprColModel);
			}
		}]
	});
		
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: true,
			handler: function() {
				if(oprGrid.getSelectionModel().hasSelection()) {
					var rec = oprGrid.getSelectionModel().getSelected();
					
					showConfirm('确定要删除该条信息吗？',oprGrid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T10206Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,oprGrid);
										oprGrid.getStore().reload();
										oprGrid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,oprGrid);
									}
								},
								params: { 
									paraIdxPK: rec.get('paraIdx'),
									usageKeyPK: '1',
									txnId: '10206',
									subTxnId: '02'
								}
							});
						}
					});
				}
			}
		};
		var upMenu = {
			text: '保存',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				var modifiedRecords = oprGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				showProcessMsg('正在保存操作员信息，请稍后......');
				//存放要修改的机构信息
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						ind : record.get('ind'),
//						insIdCd : record.get('insIdCd'),
						cardTp: record.get('cardTp'),
						acc1Offset : record.get('acc1Offset'),
						acc1Len : record.get('acc1Len'),
						acc1Tnum: record.get('acc1Tnum'),
						acc2Offset : record.get('acc2Offset'),
						acc2Len : record.get('acc2Len'),
						acc2Tnum: record.get('acc2Tnum'),
						binOffset: record.get('binOffset'),
						binLen: record.get('binLen'),
						binStaNo: record.get('binStaNo'),
						binendNo: record.get('binendNo')
					};
					array.push(data);
				}
				Ext.Ajax.request({
					url: 'T10205Action.asp?method=update',
					method: 'post',
					params: {
					tblBankBinInfList: Ext.encode(array),
						txnId: '10205',
						subTxnId: '03'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							oprGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,oprGrid);
						} else {
							oprGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,oprGrid);
						}
//						oprGrid.getTopToolbar().items.items[4].disable();
						oprGrid.getStore().reload();
						hideProcessMsg();
					}
				});
			}
		};

	var menuArr = new Array();
	menuArr.push(addMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(delMenu);		// [2]
//	menuArr.push('-');			// [3]
//	menuArr.push(upMenu);		// [4]	
//	menuArr.push('-');  //[5]
//	menuArr.push(queryCondition);  //[6]

	
	// 操作员信息列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '银联CA公钥',
		iconCls: 'T10206',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		plugins: rowExpander,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		autoExpandColumn: 'A9F06',
		loadMask: {
			msg: '正在加载操作员信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[2].disable();
//		oprGrid.getTopToolbar().items.items[4].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
//			oprGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		oprGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});	
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 280,
		autoHeight: true,
		labelWidth : 50,
		items: [{
        	xtype: 'combo',
			fieldLabel: '发卡行',
			labelStyle: 'padding-left: 5px',
			store: brhStore,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: true,
			lazyRender: true,
			width: 150,
			blankText: '请选择发卡行',
			id: 'insIdCdId',
			hiddenName: 'insIdCd'
    	}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 280,
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
				mchntStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			insIdCd: queryForm.findById('insIdCdId').getValue()
		});
	});
});
