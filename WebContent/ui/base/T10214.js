Ext.onReady(function() {
	
	/************************************支付宝分润参数*******************************************/
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	var sharetypeStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','固定金额'],['1','比例(%)']],
		reader: new Ext.data.ArrayReader()
	});
	var sharestatusStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','停止'],['1','启用']],
		reader: new Ext.data.ArrayReader()
	});
	//上级营业网点编号
	SelectOptionsDWR.getComboData('BRH_ID_NEW',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	var alipayShareParamStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=alipayShareParamInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
//			{name: 'paramId',mapping: 'PARAM_ID'},
			{name: 'brhId',mapping: 'BRH_ID'},
			{name: 'brhName',mapping: 'BRH_NAME'},
			{name: 'shareStandardTotal',mapping: 'SHARE_STANDARD_TOTAL'},
			{name: 'singleShareMoney',mapping: 'SINGLE_SHARE_MONEY'},
			{name: 'shareThreshold',mapping: 'SHARE_THRESHOLD'},
			{name: 'share_ruleid',mapping: 'SHARE_RULEID'},
			{name: 'share_rulename',mapping: 'SHARE_RULENAME'},
			{name: 'freeratio',mapping: 'FREERATIO'},
			{name: 'sharetype',mapping: 'SHARETYPE'},
			{name: 'mcht_shareval',mapping: 'MCHT_SHAREVAL'},
			{name: 'orgshareval_first',mapping: 'ORGSHAREVAL_FIRST'},
			{name: 'orgshareval_second',mapping: 'ORGSHAREVAL_SECOND'},
			{name: 'orgshareval_three',mapping: 'ORGSHAREVAL_THREE'},
			{name: 'share_rulestatus',mapping: 'SHARE_RULESTATUS'}
			
		])
	});
	
	alipayShareParamStore.load({
		params:{start: 0}
	});
	
	var paramColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
//		{id: 'paramId',header: '主键',dataIndex: 'paramId',hidden: true},
		{id: 'share_ruleid', header: '规则ID',dataIndex: 'share_ruleid',width: 150},
		{id: 'share_rulename', header: '规则名称',dataIndex: 'share_rulename',width: 150},
		{id: 'brhId', header: '机构编号',dataIndex: 'brhId',hidden: true},
		{id: 'brhName', header: '机构名称',dataIndex: 'brhName',width: 150},
		{id:'shareStandardTotal',header: '分润达标总笔数',dataIndex: 'shareStandardTotal',width: 150,
			editor: new Ext.form.TextField({
			 	maxLength: 100,
				allowBlank: false,
				vtype: 'isOverMax',
				regex: /^[1-9]\d*$/
			 })},
		{id: 'singleShareMoney',header: '单笔分润金额(元)',dataIndex: 'singleShareMoney',width: 150,
		 editor: new Ext.form.TextField({
		 	maxLength: 50,
			allowBlank: false,
			vtype: 'isOverMax',
			regex: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/
		 })},
		{id:'shareThreshold',header: '参与分润单笔金额阈值(元)',dataIndex: 'shareThreshold',width: 150,
		 editor: new Ext.form.TextField({
			maxLength: 50,
		 	allowBlank: false,
			vtype: 'isOverMax',
			regex: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/
		 })},
		 {id: 'sharetype', header: '分润方式',dataIndex: 'sharetype',width: 150},
		 {id: 'mcht_shareval', header: '商户分润金额/比例%',dataIndex: 'mcht_shareval',width: 150},
		 {id: 'orgshareval_first', header: '一级机构分润金额/比例%',dataIndex: 'orgshareval_first',width: 150},
		 {id: 'orgshareval_second', header: '二级机构分润金额/比例%',dataIndex: 'orgshareval_second',width: 150},
		 {id: 'orgshareval_three', header: '三级机构分润金额/比例%',dataIndex: 'orgshareval_three',width: 150},
		 {id: 'freeratio', header: '手续费比例%',dataIndex: 'freeratio',width: 150},
		 {id: 'share_rulestatus', header: '启用状态',dataIndex: 'share_rulestatus',width: 150}
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
							url: 'T10214Action.asp?method=delete',
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
								//brhId: rec.get('brhId'),
								share_ruleid: rec.get('share_ruleid'),
								txnId: '10214',
								subTxnId: '02'
							}
						});
					}
				});
			}
		}
	};
	/*
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
			showProcessMsg('正在保存系统参数信息，请稍后......');
			//存放要修改的参数信息
			var array = new Array();
			var vfail=0;
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				if(record.get('brhId').trim()==''
					||record.get('singleShareMoney').trim()==''
					||record.get('shareThreshold').trim()==''
					||record.get('shareStandardTotal').trim()==''){
					vfail=1;
					break;
				}
				var data = {
//					paramId : record.get('paramId'),
					brhId: record.get('brhId'),
					shareStandardTotal: record.get('shareStandardTotal'),                
					singleShareMoney: record.get('singleShareMoney'),
					shareThreshold: record.get('shareThreshold'),
				};
				array.push(data);
			}
			if(vfail==1){
				showErrorMsg("数据项不能为空",grid);
				grid.getStore().reload();
				hideProcessMsg();
				return;
			}
			Ext.Ajax.request({
				url: 'T10214Action.asp?method=update',
				method: 'post',
				params: {
					parameterList : Ext.encode(array),
					txnId: '10214',
					subTxnId: '03'
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
					hideProcessMsg();
				}
			});
			grid.getTopToolbar().items.items[2].disable();
		}
	};
	*/
	
	var closeMenu = {
			text : '停用',
			width : 85,
			iconCls : 'stop',
			disabled : true,
			handler : function() {
				showConfirm('确定停用该规则吗？', grid, function(bt) {
					if (bt == 'yes') {
						var sharetypeV =grid.getSelectionModel().getSelected().get('sharetype');
						if(sharetypeV=='固定金额'){
							sharetypeV='0';
						}
						else{
							sharetypeV='1';
						}
						showProcessMsg('正在提交信息，请稍后......');
						Ext.Ajax.requestNeedAuthorise( {
							url: 'T10214Action.asp?method=update',
							params : {
								share_ruleid : grid.getSelectionModel()
										.getSelected().get('share_ruleid'),
								share_rulename : grid.getSelectionModel()
										.getSelected().get('share_rulename'),
								shareStandardTotal : grid.getSelectionModel()
									.getSelected().get('shareStandardTotal'),
								singleShareMoney : grid.getSelectionModel()
									.getSelected().get('singleShareMoney'),
								shareThreshold : grid.getSelectionModel()
									.getSelected().get('shareThreshold'),
								freeratio : grid.getSelectionModel()
									.getSelected().get('freeratio'),
								mcht_shareval : grid.getSelectionModel()
									.getSelected().get('mcht_shareval'),
								orgshareval_first : grid.getSelectionModel()
									.getSelected().get('orgshareval_first'),
									
									orgshareval_second : grid.getSelectionModel()
								.getSelected().get('orgshareval_second'),
								
								orgshareval_three : grid.getSelectionModel()
								.getSelected().get('orgshareval_three'),
								brhId : grid.getSelectionModel()
								.getSelected().get('brhId'),
								sharetype : sharetypeV,
							    share_rulestatus :'0',
								txnId : '10214',
								subTxnId : '03'
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
				});

			}
		};
	var openMenu = {
			text : '启用',
			width : 85,
			iconCls : 'recover',
			disabled : true,
			handler : function() {
				showConfirm('确定启用该规则吗？', grid, function(bt) {
					if (bt == 'yes') {
						var sharetypeV =grid.getSelectionModel().getSelected().get('sharetype');
						if(sharetypeV=='固定金额'){
							sharetypeV='0';
						}
						else{
							sharetypeV='1';
						}
						//if(sharetypeV)
						showProcessMsg('正在提交信息，请稍后......');
						Ext.Ajax.requestNeedAuthorise( {
							//url : 'T10214Action.asp',
							url: 'T10214Action.asp?method=update',
							params : {
								share_ruleid : grid.getSelectionModel()
										.getSelected().get('share_ruleid'),
								share_rulename : grid.getSelectionModel()
										.getSelected().get('share_rulename'),
								shareStandardTotal : grid.getSelectionModel()
									.getSelected().get('shareStandardTotal'),
								singleShareMoney : grid.getSelectionModel()
									.getSelected().get('singleShareMoney'),
								shareThreshold : grid.getSelectionModel()
									.getSelected().get('shareThreshold'),
								freeratio : grid.getSelectionModel()
									.getSelected().get('freeratio'),
								mcht_shareval : grid.getSelectionModel()
									.getSelected().get('mcht_shareval'),
								orgshareval_first : grid.getSelectionModel()
									.getSelected().get('orgshareval_first'),
									
									orgshareval_second : grid.getSelectionModel()
								.getSelected().get('orgshareval_second'),
								
								orgshareval_three : grid.getSelectionModel()
								.getSelected().get('orgshareval_three'),
								brhId : grid.getSelectionModel()
								.getSelected().get('brhId'),
								sharetype : sharetypeV,
							    share_rulestatus :'1',
								txnId : '10214',
								subTxnId : '03'
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
				});

			}
		};
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	//menuArr.push(upMenu);
	menuArr.push(closeMenu);
	menuArr.push('-');
	menuArr.push(openMenu);
	
	// 系统参数列表
	var grid = new Ext.grid.EditorGridPanel({
		id: 'alipayShareParameter',
		title: '支付宝分润参数维护',
		iconCls: 'T10214',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoExpandColumn:'brhId',
//		autoHeight: true,
		store: alipayShareParamStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: paramColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载支付宝分润参数信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: alipayShareParamStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
//		grid.getTopToolbar().items.items[4].disable();
//		grid.getStore().rejectChanges();
		
	});
	
	grid.on({
		//在编辑单元格后使保存按钮可用
//		'afteredit': function(e) {
//			if(grid.getTopToolbar().items.items[4] != undefined) {
//				grid.getTopToolbar().items.items[4].enable();
//			}
//		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			//激活菜单
			grid.getTopToolbar().items.items[2].enable();
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelected();
			grid.getTopToolbar().items.items[4].enable();//停止
			grid.getTopToolbar().items.items[6].enable();//启用
			var stat = rec.get('share_rulestatus');
			if(stat == '停止' ){
				grid.getTopToolbar().items.items[6].enable();
				grid.getTopToolbar().items.items[4].disable();
			}else{
				grid.getTopToolbar().items.items[4].enable();
				grid.getTopToolbar().items.items[6].disable();
			}
		}
	});
	// 可选机构下拉列表
	var brhCombo = new Ext.form.ComboBox({
		store: brhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择机构',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		blankText: '请选择一个机构',
		fieldLabel: '机构编号',
		width: 200,
		hiddenName: 'brhId'
	});
	//添加系统参数表单
	var paramInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 380,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [brhCombo,{
			fieldLabel: '分润规则名称*',
			id: 'share_rulename',
			name: 'share_rulename',
			width: 200,
			maxLength: 20,
			allowBlank: false,
			blankText: '分润规则名称不能为空',
			emptyText: '请输入分润规则名称',
		},{
			fieldLabel: '分润达标总笔数*',
			id: 'shareStandardTotal',
			name: 'shareStandardTotal',
			width: 200,
			maxLength: 20,
			allowBlank: false,
			blankText: '分润达标总笔数不能为空',
			emptyText: '请输入分润达标总笔数',
			vtype: 'isOverMax',
			regex:/^[1-9]\d*$/,
			regexText:'只能为正整数'
		},{
			fieldLabel: '单笔分润金额(元)*',
			id: 'singleShareMoney',
			name: 'singleShareMoney',
			width: 200,
			maxLength: 50,
			allowBlank: false,
			blankText: '单笔分润金额不能为空',
			emptyText: '请输入单笔分润金额',
			vtype: 'isOverMax',
			regex:/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/,
			regexText:'只能为最多两位小数的数字'
		},{
			fieldLabel: '参与分润单笔金额阈值(元)*',
			allowBlank: false,
			blankText: '参与分润单笔金额阈值为空',
			emptyText: '请输入参与分润单笔金额阈值',
			id: 'shareThreshold',
			name: 'shareThreshold',
			width: 200,
			maxLength: 50,
			vtype: 'isOverMax',
			regex:/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/,
			regexText:'只能为最多两位小数的数字'
		},{xtype: 'combo',
	        fieldLabel: '分润方式',
	        store: sharetypeStore,
	        id: 'shareTypeC',
	        hiddenName: 'sharetype',
	        emptyText: '请选择',
//	        store: new Ext.data.ArrayStore({
//	            fields: ['valueField','displayField'], 
//	            data: [['0','固定金额'],['1','比例(%)']]
//	        }),
	        width:200,
	        readOnly:false},{
        	xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '商户分润金额/比例(%)',
			maxLength: 5,
			blankText: '商户分润金额/比例(%)为空',
			emptyText: '请输入商户分润金额/比例(%)',
			vtype: 'isOverMax',
			regex: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})|[0]))$/,
			regexText: '该输入框只能输入0或最多两位小数的正数',
			width:200,
			allowBlank: true,
//			disabled:true,
			id: 'mcht_shareval'
    	},{
        	xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '一级机构分润金额/比例(%)',
			maxLength: 5,
			blankText: '一级机构分润金额/比例(%)为空',
			emptyText: '请输入1级机构分润金额/比例(%)',
			vtype: 'isOverMax',
			regex: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})|[0]))$/,
			regexText: '该输入框只能输入0或最多两位小数的正数',
			width:200,
			allowBlank: true,
//			disabled:true,
			id: 'orgshareval_first'
    	},{
        	xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '二级机构分润金额/比例(%)',
			maxLength: 5,
			blankText: '二级机构分润金额/比例(%)为空',
			emptyText: '请输入二级机构分润金额/比例(%)',
			vtype: 'isOverMax',
			regex: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})|[0]))$/,
			regexText: '该输入框只能输入0或最多两位小数的正数',
			width:200,
			allowBlank: true,
//			disabled:true,
			id: 'orgshareval_second'
    	},{
            xtype: 'textfield',
            labelStyle: 'padding-left: 5px',
            fieldLabel: '三级机构分润金额/比例(%)',
            maxLength: 5,
            blankText: '三级机构分润金额/比例(%)为空',
			emptyText: '请输入三级机构分润金额/比例(%)',
			vtype: 'isOverMax',
			regex: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})|[0]))$/,
			regexText: '该输入框只能输入0或最多两位小数的正数',
//			maskRe: /^[0-9]+$/,
            width:200,
            allowBlank: true,
//			disabled:true,
            id: 'orgshareval_three'
        },{
        	xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '商户手续费比例(%)',
			maxLength: 5,
			blankText: '商户手续费比例(%)为空',
			emptyText: '请输入商户手续费比例(%)',
			vtype: 'isOverMax',
			width:200,
			allowBlank: true,
//			disabled:true,
			regex: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2}))|[0])$/,
			regexText: '该输入框只能输入0或最多两位小数的正数',
			id: 'freeratio',
			
    	},
    	{xtype: 'combo',
        fieldLabel: '启用状态',
        store: sharestatusStore,
        id: 'share_rulestatusC',
        hiddenName: 'share_rulestatus',
        emptyText: '请选择',
//        store: new Ext.data.ArrayStore({
//            fields: ['valueField','displayField'], 
//            data: [['0','停止'],['1','启用']]
//        }),
        width:200,
        readOnly:false}]
	});
	
	//参数信息添加窗口
	var paramWin = new Ext.Window({
		title: '支付宝分润规则添加',
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
						url: 'T10214Action.asp?method=add',
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
							txnId: '10214',
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