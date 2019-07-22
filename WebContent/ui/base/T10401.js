Ext.onReady(function() {
	
	// 可选机构数据集
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('BRH_ID_NEW',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});

	var roleForLook = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('ROLE_FOR_LOOK',function(ret){
		roleForLook.loadData(Ext.decode(ret));
	});
	
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=oprInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'oprId'
		},[
			{name: 'oprId',mapping: 'oprId'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprDegree',mapping: 'oprDegree'},
			{name: 'oprName',mapping: 'oprName'},
			{name: 'oprGender',mapping: 'oprGender'},
			{name: 'registerDt',mapping: 'registerDt'},
			{name: 'oprTel',mapping: 'oprTel'},
			{name: 'oprMobile',mapping: 'oprMobile'},
			{name: 'pwdOutDate',mapping: 'pwdOutDate'},
			{name: 'oprSta',mapping: 'oprSta'}
		])
	});
	
	oprGridStore.load({
		params:{start: 0}
	});
	
	
	
	
	var oprColModel = new Ext.grid.ColumnModel([
		{id: 'oprId',header: '操作员编码',dataIndex: 'oprId',sortable: true,align: 'center'},
		{header: '所属机构号',dataIndex: 'brhId',sortable: true,align: 'center'},
		{header: '角色',dataIndex: 'oprDegree',align: 'center',width: 150,sortable: true,renderer:function(data){
	    	if(null == data) return '';
	    	var record = roleForLook.getById(data);
	    	if(null != record){
	    		return record.data.displayField;
	    	}else{
	    		return '';
	    	}
	    }},
		{header: '名称',dataIndex: 'oprName',align: 'center',sortable: true,id:'oprName',
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			blankText: '操作员名称不能为空',
			maxLength: 20,
			vtype: 'isOverMax'
		 })},
		{header: '性别',dataIndex: 'oprGender',align: 'center',sortable: true,renderer: gender,editor: new Ext.form.ComboBox({
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','男'],['1','女']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择性别',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false
		})},
		{header: '注册日期',dataIndex: 'registerDt',width: 100,sortable: true,renderer: formatDt,align: 'center'},
		{header: '联系电话',dataIndex: 'oprTel',align: 'center',sortable: true,
		 editor: new Ext.form.TextField({
		 	allowBlank: true,
			maxLength: 20,
			vtype: 'isOverMax',
			regexText:"联系电话格式有误！", 
			regex: /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|[1][3,4,5,7,8,9][0-9]{9})$/
		 })},
		{header: '微信推送号',dataIndex: 'oprMobile',align: 'center',sortable: true,
		 editor: new Ext.form.TextField({
		 	allowBlank: true,
			maxLength: 12,
			vtype: 'isOverMax'
		 })},
		{header: '密码有效期',dataIndex: 'pwdOutDate',sortable: true,renderer: formatDt,align: 'center'},
		{header: '状态',dataIndex: 'oprSta',renderer: oprState,align: 'center',sortable: true,width: 80}
	]);
	
	/**
	 * 操作员性别
	 */
	function gender(val) {
		if(val == '0') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/male.png" />';
		} else if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/female.png" />';
		}
		return val;
	}
	
	/**
	 * 操作员状态
	 */
	function oprState(val) {
		if(val == '0') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="可用"/>';
		} else if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="锁定"/>';
		}
		return val;
	}
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			oprWin.show();
			oprWin.center();
		}
	};
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(oprGrid.getSelectionModel().hasSelection()) {
				var rec = oprGrid.getSelectionModel().getSelected();
		                if(rec.get('oprId')=='admin'){
		                	showErrorMsg('不能删除admin用户',oprGrid);
		                	return false;
		                }
				showConfirm('确定要删除该条操作员信息吗？',oprGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10401Action.asp?method=delete',
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
								oprId: rec.get('oprId'),
								txnId: '10401',
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
//			showProcessMsg('正在保存操作员信息，请稍后......');
			//存放要修改的机构信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					oprId : record.get('oprId'),
					oprDegree: record.get('oprDegree'),
					oprName: record.get('oprName'),
					oprGender: record.get('oprGender'),
					oprTel: record.get('oprTel'),
					oprMobile: record.get('oprMobile')
				};
				array.push(data);
			}
			
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10401Action.asp?method=update',
				method: 'post',
				params: {
					oprInfoList: Ext.encode(array),
					txnId: '10401',
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
					oprGrid.getTopToolbar().items.items[4].disable();
					oprGrid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	var edit = {
		text: '编辑机构/角色',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler: function() {
			var rec = oprGrid.getSelectionModel().getSelected();
			
			SelectOptionsDWR.getComboDataWithParameter('ROLE_BY_BRH',rec.get('brhId'),function(ret){
				gridRoleStore.removeAll();
				gridRoleStore.loadData(Ext.decode(ret));
				editForm.findById('editRole').setValue(rec.get('oprDegree'));
			});
			oprEditWin.show();
			oprEditWin.center();
			editForm.findById('editBrh').setValue(rec.get('brhId'));
			editForm.findById('editRole').setValue(rec.get('oprDegree'));
		}
	};
	
	var reset = {
		text: '解锁并重置',
		width: 85,
		iconCls: 'reset',
		disabled: true,
		handler: function() {
			showConfirm('确定要解锁并重置该操作员吗？',oprGrid,function(bt) {
				if(bt == "yes") {
					Ext.Ajax.requestNeedAuthorise({
						url: 'T10401Action.asp?method=reset',
						method: 'post',
						params: {
							oprId: oprGrid.getSelectionModel().getSelected().get('oprId'),
							txnId: '10401',
							subTxnId: '05'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,oprGrid);
							} else {
								showErrorMsg(rspObj.msg,oprGrid);
							}
							oprGrid.getTopToolbar().items.items[8].disable();
							oprGrid.getStore().reload();
							hideProcessMsg();
						}
					});
				}
			});
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
	
	var menuArr = new Array();
	menuArr.push(addMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(delMenu);		// [2]
	menuArr.push('-');			// [3]
	menuArr.push(upMenu);		// [4]
	menuArr.push('-');			// [5]
	menuArr.push(edit);			// [6]
	menuArr.push('-');			// [7]
//	menuArr.push(reset);		// [8]
//	menuArr.push('-');			// [7]
	menuArr.push(queryCondition);		// [8]
	
	// 操作员信息列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '操作员维护',
		iconCls: 'T104',
		region:'center',
		autoExpandColumn:'oprName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载操作员信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: oprGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[4].disable();
		oprGrid.getTopToolbar().items.items[6].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			oprGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
			//激活菜单
			oprGrid.getTopToolbar().items.items[2].enable();
			oprGrid.getTopToolbar().items.items[6].enable();
			var rec = oprGrid.getSelectionModel().getSelected();
//			if(rec.get('oprSta') == '1') {
//				oprGrid.getTopToolbar().items.items[8].enable();
//			} else {
//				oprGrid.getTopToolbar().items.items[8].disable();
//			}
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
		width:240,
		allowBlank: false,
		blankText: '请选择一个机构',
		fieldLabel: '所属机构号*',
		hiddenName: 'brhId'
	});
	
	// 根据机构不同，显示不同级别的角色信息
	// brhCombo.on('select',function() {
	// 	SelectOptionsDWR.getComboDataWithParameter('ROLE_BY_BRH',brhCombo.getValue(),function(ret){
	// 		roleStore.removeAll();
	// 		roleStore.loadData(Ext.decode(ret));
	// 		roleCombo.setValue(roleStore.getAt(0).get('valueField'));
	// 	});
	// });
	
	// 权限数据集
	var roleStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('ROLE_BY_BRH','1',function(ret){
		roleStore.loadData(Ext.decode(ret));
	});

	// 信息列表可选角色下拉列表，随机构变动
	var roleCombo = new Ext.form.ComboBox({
		store: roleStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择角色',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		width: 240,
		blankText: '请选择一个角色',
		fieldLabel: '操作员角色*',
		hiddenName: 'oprDegree'
	});
	
	// 角色添加表单
	var oprInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '操作员编码*',
			allowBlank: false,
			blankText: '操作员编码不能为空',
			emptyText: '请输入操作员编码',
			id: 'oprId',
			name: 'oprId',
			regex: /^[a-zA-Z0-9]{6,32}$/,
			regexText:'操作员编码由6-32位的数字或字母组成',
			width: 240,
			maxLength: 40,
			vtype: 'isOverMax'
		},
		brhCombo,
		roleCombo,
		{
			fieldLabel: '操作员姓名*',
			allowBlank: false,
			blankText: '操作员姓名不能为空',
			emptyText: '请输入操作员姓名',
			maxLength: 20,
			width: 240,
			id: 'oprName',
			name: 'oprName',
			vtype: 'isOverMax'
		},{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','男'],['1','女']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择性别',
			hiddenName: 'oprGender',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			width: 240,
			allowBlank: false,
			fieldLabel: '性别*'
		},{
			inputType: 'password',
			fieldLabel: '密码*',
			width: 240,
			allowBlank: false,
			blankText: '操作员密码不能为空',
			emptyText: '',
			vtype: 'isOverMax',
			id: 'oprPwd',
			name: 'oprPwd',
			regex: /^[a-zA-Z0-9]{6,16}$/,
			regexText: '密码必须由6-16位数字或字母组成'
		},{
			fieldLabel: '操作员联系电话',
			allowBlank: true,
//			maxLength: 20,
			vtype: 'isOverMax',
			id: 'oprTel',
			name: 'oprTel',
			width: 240,
			regexText:"请填写正确的联系电话！", 
			regex: /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|[1][3,4,5,7,8,9][0-9]{9})$/
		},{
			fieldLabel: '微信推送号',
			allowBlank: true,
			maxLength: 20,
			vtype: 'isOverMax',
			id: 'oprMobile',
			name: 'oprMobile',
			vtype: 'isOverMax',
			width: 240
		}]
	});
	
	// 操作员添加窗口
	var oprWin = new Ext.Window({
		title: '操作员添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [oprInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(oprInfoForm.getForm().isValid()) {
					oprInfoForm.getForm().submitNeedAuthorise({
						url: 'T10401Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,oprInfoForm);
							//重置表单
							oprInfoForm.getForm().reset();
							//重新加载列表
							oprGrid.getStore().reload();
							oprWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,oprInfoForm);
						},
						params: {
							txnId: '10401',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				oprInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				oprWin.hide(oprGrid);
			}
		}]
	});
	
	// 权限数据集
	var gridRoleStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	// 信息列表可选机构下拉列表
	var gridBrhCombo = new Ext.form.ComboBox({
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
		allowBlank: false,
		blankText: '请选择一个机构',
		fieldLabel: '机构',
		width:240,
		id: 'editBrh',
		name: 'editBrh',
		hiddenName: 'brhIdEdit'
	});
	
	// 根据机构不同，显示不同级别的角色信息
	/*gridBrhCombo.on('select',function() {
		SelectOptionsDWR.getComboDataWithParameter('ROLE_BY_BRH',gridBrhCombo.getValue(),function(ret){
			gridRoleStore.removeAll();
			gridRoleStore.loadData(Ext.decode(ret));
			var rec = gridRoleStore.getAt(0);
			editForm.findById('editRole').setValue(rec.get('valueField'));
		});
	});*/
	
	// 信息列表可选角色下拉列表，随机构变动
	var gridRoleCombo = new Ext.form.ComboBox({
		store: gridRoleStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择角色',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个角色',
		fieldLabel: '角色',
		width:240,
		id: 'editRole',
		name: 'editRole',
		hiddenName: 'oprDegreeEdit'
	});
	
	// 编辑操作员机构和权限
	var editForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 290,
		defaultType: 'textfield',
		labelWidth: 80,
		waitMsgTarget: true,
		items: [gridBrhCombo,gridRoleCombo]
	});
	
	// 操作员编辑窗口
	var oprEditWin = new Ext.Window({
		title: '编辑机构/角色',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [editForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(editForm.getForm().isValid()) {
					editForm.getForm().submitNeedAuthorise({
						url: 'T10401Action.asp?method=edit',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,editForm);
							//重新加载列表
							oprGrid.getStore().reload();
							oprEditWin.hide(oprGrid);
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,editForm);
						},
						params: {
							oprId: oprGrid.getSelectionModel().getSelected().get('oprId'),
							brhId: editForm.findById('editBrh').getValue(),
							oprDegree: editForm.findById('editRole').getValue(),
							txnId: '10401',
							subTxnId: '04'
						}
					});
				}
			}
		},{
			text: '关闭',
			handler: function() {
				oprEditWin.hide(oprGrid);
			}
		}]
	});
	
/***************************查询条件*************************/
	
	// 可选机构下拉列表
	var searchBrhCombo = new Ext.form.ComboBox({
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
		allowBlank: false,
		blankText: '请选择一个机构',
		fieldLabel: '机构',
		width:240,
		id: 'searchBrh',
		name: 'searchBrh',
		hiddenName: 'brhIdSearch'
	});
	
	
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 390,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'oprIdQuery',
			width: 240,
			name: 'oprIdQuery',
			vtype: 'alphanum',
			fieldLabel: '操作员编码'
		},searchBrhCombo]
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
			oprGridStore.load();
			queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	oprGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			oprId: queryForm.findById('oprIdQuery').getValue(),
			brhId: queryForm.findById('searchBrh').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
});