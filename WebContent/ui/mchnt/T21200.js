/******
 *页面初始化加载电话终端类型商户信息
 *
 */
Ext.onReady(function() {
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data',
		idProperty: 'valueField'
});
 SelectOptionsDWR.getMCHTSignAcc(function(ret){debugger;
        mchntStore.loadData(Ext.decode(ret));
 });
/******
 *页面初始化加载签约商户状态
 *
 */
 var signStatusStore = new Ext.data.JsonStore({
	fields: ['valueField','displayField'],
	root: 'data',
	id: 'valueField'
});
SelectOptionsDWR.getComboData('SIGN_STATUS',function(ret){
	signStatusStore.loadData(Ext.decode(ret));
});
	
	/***********************************商户签约账户信息*******************************************/
	var sysParamStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=gettblMchtSignAccInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchntId',mapping: 'MCHT_NO'},
			{name: 'signAcct',mapping: 'SIGN_ACCT'},
			{name: 'signType',mapping: 'SIGN_TYPE'},
			{name: 'signStatus',mapping: 'SIGN_STATUS'},
			{name: 'rcvBankId',mapping: 'RCV_BANK_ID'},
			{name: 'rcvBankNo',mapping: 'RCV_BANK_NO'},
			{name: 'recUpdTs',mapping: 'REC_UPD_TS'},
			{name: 'recCrtTs',mapping: 'REC_CRT_TS'},
			{name: 'updOprId',mapping: 'UPD_OPR_ID'},
			{name: 'crtOprId',mapping: 'CRT_OPR_ID'},
			{name: 'acctName',mapping: 'ACCT_NAME'},
			{name: 'bankName',mapping: 'BANK_NAME'},
			{name: 'bankNo',mapping: 'BANK_NO'},
			{name: 'bankAddr',mapping: 'BANK_ADDR'}
		])
	});
/******
 *页面初始化加载查询数据
 *
 */	
	sysParamStore.load({
		params:{start: 0}
	});
/******
 *填充查询数据
 *
 */	
	var paramColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'mchntId',header: '商户编号',dataIndex: 'mchntId',width: 300,renderer:function(data){
	    	if(null == data) return '';
	    	var record = mchntStore.getById(data);
	    	if(null != record){
	    		return record.data.displayField;
	    	}else{
	    		return '';
	    	}
	    }},
		{header: '签约账户',dataIndex: 'signAcct',width: 100},
		{header: '签约账户名称',dataIndex: 'acctName',width: 100},
		{header: '签约状态',dataIndex: 'signStatus',width: 100,renderer:function(data){
	    	if(null == data) return '';
	    	var record = signStatusStore.getById(data);
	    	if(null != record){
	    		return record.data.displayField;
	    	}else{
	    		return '';
	    	}
	    }, editor: new Ext.form.ComboBox({
		 	store: signStatusStore,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			hiddenName: 'signStatus',
			allowBlank: false
		 })},
		{header: '开户行号',dataIndex: 'rcvBankId',width: 100,editor: new Ext.form.TextField({
		 	maxLength: 12,
			allowBlank: false,
			vtype: 'isOverMax',
			regex: /^[0-9]+$/,
			regexText: '该输入框只能输入数字0-9'
		 })},
		{header: '开户行名称',dataIndex: 'rcvBankNo',id:'rcvBankNo',width: 120,editor: new Ext.form.TextField({
		 	allowBlank: false,
			maxLength: 50,
			vtype: 'isOverMax'
		 })},
		{header: '创建操作员',dataIndex: 'crtOprId'},
		{header: '最后更新操作员',dataIndex: 'updOprId'},
		{header: '创建时间',dataIndex: 'recCrtTs',renderer:formatTs},
		{header: '最后更新时间',dataIndex: 'recUpdTs',renderer:formatTs}
	]);
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			paramWin.show();
			paramWin.center();
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
				
				showConfirm('确定要删除该条记录吗？',grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T21200Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								mchntId: rec.get('mchntId'),
								signAcct: rec.get('signAcct'),
								txnId: '21200',
								subTxnId: '03'
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
			var modifiedRecords = grid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			//存放要修改的参数信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					mchntId : record.get('mchntId'),
					signAcct: record.get('signAcct'),
					acctName: record.get('acctName'),
					rcvBankId: record.get('rcvBankId'),
					rcvBankNo: record.get('rcvBankNo'),
					signStatus: record.get('signStatus')
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T21200Action.asp?method=update',
				method: 'post',
				params: {
					TblMchtSignAccInfList : Ext.encode(array),
					txnId: '21200',
					subTxnId: '02'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					grid.enable();
					if(rspObj.success) {
						grid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,grid);
					} else {
						grid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,grid);
					}
					grid.getTopToolbar().items.items[4].disable();
					grid.getStore().reload();
				}
			});
			grid.getTopToolbar().items.items[2].disable();
		}
	};
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'combo',
			store: mchntStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'mchtNo',
			fieldLabel: '商户',
			mode: 'local',
			width: 280,
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true
		}
		]
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
				sysParamStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});	
	/***************查询事件***********************/
	sysParamStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNo: queryForm.getForm().findField('mchtNo').getValue()
		});
	});
	
	
   var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);
	menuArr.push('-');
	menuArr.push(queryCondition);
	// 系统参数列表
	var grid = new Ext.grid.EditorGridPanel({
		id: 'SystemParameter',
		title: '固话POS商户付款账户信息维护',
		iconCls: 'T10205',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoExpandColumn:'mchntId',
		store: sysParamStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: paramColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载系统参数信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: sysParamStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[4].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			//激活菜单
			grid.getTopToolbar().items.items[2].enable();
		}
	});
	
	//添加系统参数表单
	var paramInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 450,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			xtype: 'combo',
			store: mchntStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'mchntId',
			emptyText: '请选择需要签约的商户',
			fieldLabel: '商户*',
			mode: 'local',
			width: 230,
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个签约的商户'	
		},{
			fieldLabel: '签约账户*',
			id: 'signAcct',
			name: 'signAcct',
			width: 230,
			maxLength: 30,
			allowBlank: false,
			blankText: '签约账户不能为空',
			emptyText: '请输入签约账户',
			vtype: 'isOverMax',
			regex: /^[0-9]+$/,
			regexText: '该输入框只能输入数字0-9',
			maskRe: /^[0-9]+$/
		},{
			fieldLabel: '签约账户名称*',
			id: 'acctName',
			name: 'acctName',
			width: 230,
			maxLength: 40,
			allowBlank: false,
			blankText: '签约账户名称不能为空',
			emptyText: '请输入签约账户名称'
		},{
			xtype : 'basecomboselect',
			baseParams : 'Select_Bank_No',	
			fieldLabel: '开户行名称*',
			id: 'idrcvBankNo',
			hiddenName: 'rcvBankNo',
			width: 230,
			maxLength: 50,
			editable: true,
			allowBlank: false,
			blankText: '请选择开户行',
			emptyText: '请选择开户行',
			vtype: 'isOverMax',
			listeners: {
				'select': function() {
					var bankNo = (Ext.getDom(Ext.getDoc()).getElementById('idrcvBankNo').value).substring(0,12);
					//Ext.Msg.alert(Ext.getDom(Ext.getDoc()).getElementById('idrcvBankNo').value);
					//Ext.Msg.alert(bankNo);
					paramInfoForm.getForm().findField('rcvBankId').setValue(bankNo);
				}
			}
		},{
			fieldLabel: '开户行号*',
			id: 'rcvBankId',
			name: 'rcvBankId',
			width: 230,
			maxLength: 12,
			allowBlank: false,
			blankText: '开户行号不能为空',
			emptyText: '请输入开户行号',
			vtype: 'isOverMax',
			regex: /^[0-9]+$/,
			regexText: '该输入框只能输入数字0-9'
		},{
        	/**xtype: 'radiogroup',
            id: 'signStatus',
            name: 'signStatus',
            fieldLabel: '签约状态*',
            allowBlank: false,
			blankText: '请选择签约状态',
            items: [{boxLabel: '正常', name: 'signStatus', inputValue: '0',checked: true},
            	   	{boxLabel: '未启用', name: 'signStatus', inputValue: '1'}
            ]*/
            
            
            
            xtype: 'combo',
			store: signStatusStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'signStatus',
			emptyText: '请选择签约状态',
			fieldLabel: '签约状态*',
			mode: 'local',
			width: 230,
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个签约状态'	
            
            
            
            
			
		},{
        	xtype: 'hidden',
            id: 'signType',
            name: 'signType',
            value:'1'
		}
		]
	});
	
	//参数信息添加窗口
	var paramWin = new Ext.Window({
		title: '参数信息添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 380,
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
						url: 'T21200Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,paramInfoForm);
							//重置表单
							paramInfoForm.getForm().reset();
							//重新加载参数列表
							grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,paramInfoForm);
						},
						params: {
							txnId: '21200',
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
				paramWin.hide(grid);
			}
		}]
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
	// 主面板
	var tabPanel = new Ext.TabPanel({
		items: [grid],
		renderTo: Ext.getBody(),
		activeTab: 0
	});
});