Ext.onReady(function() {
	
	var brhLvlStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	//上级营业网点编码
	SelectOptionsDWR.getComboData('BRH_ID_NEW',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	var upBrhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	//机构级别
	SelectOptionsDWR.getComboData('BRH_LVL_BRH_INFO',function(ret){
		brhLvlStore.loadData(Ext.decode(ret));
	});
	
	//上级营业网点编码
	SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
		upBrhStore.loadData(Ext.decode(ret));
	});
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'brhId'
		},[
			{name: 'brhId',mapping: 'brhId'},
			{name: 'brhLvl',mapping: 'brhLvl'},
			{name: 'upBrhId',mapping: 'upBrhId'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'brhAddr',mapping: 'brhAddr'},
			{name: 'brhTelNo',mapping: 'brhTelNo'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'brhContName',mapping: 'brhContName'},
			{name: 'cupBrhId',mapping: 'cupBrhId'},
			{name: 'resv1',mapping: 'resv1'}
		])
	});
	
	gridStore.load();
	
	function brhLvlRender(brhLvl) {
		switch(brhLvl) {
			case '0': return '总行';
			case '1': return '分行';
			case '2': return '支行';
			case '3':return '网点';
		}
	}
	
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'brhId',header: '工具编码',dataIndex: 'brhId',sortable: true,width: 90},
		{header: '工具名称',dataIndex: 'brhName',sortable: true,width: 150
//		 editor: new Ext.form.TextField({
//		 	allowBlank: false,
//			blankText: '机构名称不能为空',
//			emptyText: '请输入机构名称',
//			maxLength: 40,
//			maxLengthText: '机构名称最多可以输入40个汉字'
//		 })
	},	
	{header: '工具类型',dataIndex: 'brhId',/*renderer: brhLvlRender,*/sortable: true,width: 90,
//		 editor: new Ext.form.ComboBox({
//		 	store: brhLvlStore,
//			displayField: 'displayField',
//			valueField: 'valueField',
//			mode: 'local',
//			triggerAction: 'all',
//			forceSelection: true,
//			typeAhead: true,
//			selectOnFocus: true,
//			editable: true,
//			lazyRender: true
//		 })
	},{header: '注册日期',dataIndex: 'brhAddr',sortable: true,width: 80,renderer: formatDt},
		{header: '库房名称',dataIndex: 'brhAddr',sortable: true,width: 200,id:'brhAddr'
//		 editor: new Ext.form.TextField({
//		 	allowBlank: false,
//			blankText: '机构地址不能为空',
//			emptyText: '请输入机构地址',
//			maxLength: 40,
//			vtype: 'isOverMax',
//			maxLengthText: '机构地址最多可以输入40个汉字'
//		 })
	},
		/*{header: '联系方式',dataIndex: 'brhTelNo',width: 100
//		 editor: new Ext.form.TextField({
//		 	allowBlank: false,
//			blankText: '机构联系电话不能为空',
//			emptyText: '请输入机构联系电话',
//			maxLength: 20,
//			maxLengthText: '机构联系电话最多可以输入20个数字',
//			vtype: 'isNumber',
//			maskRe:/^[0-9]$/,
//			blankText: '该输入项只能包含数字'
//		 })
	},*/
		{header: '工具存放位置',dataIndex: 'postCode',sortable: true,width: 120
//		 editor: new Ext.form.TextField({
//		 	allowBlank: false,
//			blankText: '机构邮政编码不能为空',
//			emptyText: '请输入机构邮政编码',
//			minLength: 6,
//			minLengthText: '机构邮政编码必须是6个数字',
//			maxLength: 6,
//			maxLengthText: '机构邮政编码必须是6个数字',
//			vtype: 'isNumber',
//			maskRe:/^[0-9]$/,
//			blankText: '该输入项只能包含数字'
//		 })
	},
		 {header: '采购单位',dataIndex: 'resv1',sortable: true,width: 120
//		 editor:new Ext.form.TextField({
//		 	allowBlank: false,
//			emptyText: '请输入地区编码',
//			minLength: 4,
//			maxLength: 4,
//			maxLengthText: '地区编码最多可以输入4个数字',
//			vtype: 'isNumber',
//			maskRe:/^[0-9]$/,
//			blankText: '该输入项只能包含数字'
//		 })
	},
		{header: '采购人',dataIndex: 'cupBrhId',sortable: true,width: 90
//		editor:new Ext.form.TextField({
//		 	allowBlank: false,
//			emptyText: '请输入银联编码',
//			minLength: 8,
//			minLengthText: '银联编码必须是8个数字',
//			maxLength: 8,
//			maxLengthText: '银联编码必须是8个数字',
//			vtype: 'isNumber',
//			maskRe:/^[0-9]$/,
//			blankText: '该输入项只能包含8位数字'
//		 })
	},
	{header: '工具状态',dataIndex: 'cupBrhId',sortable: true,width: 90
//		editor:new Ext.form.TextField({
//		 	allowBlank: false,
//			emptyText: '请输入银联编码',
//			minLength: 8,
//			minLengthText: '银联编码必须是8个数字',
//			maxLength: 8,
//			maxLengthText: '银联编码必须是8个数字',
//			vtype: 'isNumber',
//			maskRe:/^[0-9]$/,
//			blankText: '该输入项只能包含8位数字'
//		 })
	}
	]);

	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			brhWin.show();
			brhWin.center();
			Ext.getCmp('ensure1').show().enable();
			Ext.getCmp('ensure2').hide().disable();
			Ext.getCmp('reset1').show().enable();
			Ext.getCmp('brhId1').enable();
			Ext.getCmp('brhLvl1').enable();
		}
	};
	
	var delMenu = {
		text: '报废',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				var brhId = rec.get('brhId');		
				
				showConfirm('确认报废选中工具？',grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10101Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
										upBrhStore.loadData(Ext.decode(ret));
									});
									grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								brhId: brhId,
								txnId: '10101',
								subTxnId: '02'
							}
						}
						);
					}
				});
			}
		}
	};
	
	var upMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler: function() {
			var	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            Ext.Ajax.request({
				url : 'T10101Action_getData.asp',
				params : {
					brhId : selectedRecord.get('brhId')
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						brhWin.show();
						brhWin.center();
//			            Ext.getCmp('ensure1').hide().disable();
//			            Ext.getCmp('ensure2').show().enable();
//			            Ext.getCmp('reset1').hide().disable();
//						Ext.getCmp('brhId1').disable();
//						if(rspObj.msg.brhLevel == '' || rspObj.msg.brhLevel == '0'){
//							Ext.getCmp('brhLvl1').disable();
//						}else{
//							Ext.getCmp('brhLvl1').enable();
//						}
			            Ext.getCmp('brhId1').setValue(rspObj.msg.id);
			            Ext.getCmp('brhLvl1').setValue(rspObj.msg.brhLevel);
			           // Ext.getCmp('upBrhId1').setValue(rspObj.msg.brhLevel);
			            Ext.getCmp('brhName1').setValue(rspObj.msg.brhName);
			          //  Ext.getCmp('resv11').setValue(rspObj.msg.resv1);
//			            Ext.getCmp('brhAddr1').setValue(rspObj.msg.brhAddr);
//			            Ext.getCmp('brhTelNo1').setValue(rspObj.msg.brhTelNo);
//			            Ext.getCmp('postCode1').setValue(rspObj.msg.postCd);
//			            Ext.getCmp('brhContName1').setValue(rspObj.msg.brhContName);
//			            Ext.getCmp('cupBrhId1').setValue(rspObj.msg.cupBrhId );
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
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
//				queryForm.getForm().reset();
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
	
	// 可选机构下拉列表
//	var brhCombo = new Ext.form.ComboBox({
//		store: brhStore,
//		displayField: 'displayField',
//		valueField: 'valueField',
//		emptyText: '请选择机构',
//		mode: 'local',
//		triggerAction: 'all',
//		forceSelection: true,
//		typeAhead: true,
//		selectOnFocus: true,
////		editable: false,
//		blankText: '请选择一个机构',
//		fieldLabel: '机构编码',
//		width: 320,
//		id: 'editBrh',
//		name: 'editBrh',
//		hiddenName: 'brhIdEdit'
//	});
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 490,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'searchBrhName1',
			name: 'searchBrhName1',
			maxLength: 40,
			width: 320,
			fieldLabel: '库房名称'
		},{
			xtype: 'textfield',
			id: 'searchBrhName',
			name: 'searchBrhName',
			maxLength: 40,
			width: 320,
			fieldLabel: '工具名称'
		}]
	});

	//机构列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '工具报废',
		iconCls: 'T110105',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
//		autoExpandColumn:'brhAddr',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '加载中......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
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
			gridStore.load();
			queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
//		grid.getTopToolbar().items.items[4].disable();
//		grid.getTopToolbar().items.items[2].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//单击行，使删除按钮可用
		'rowclick': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			grid.getTopToolbar().items.items[2].enable();
//			grid.getTopToolbar().items.items[4].enable();
		}
//		//在编辑单元格后使保存按钮可用
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
//			grid.getTopToolbar().items.items[2].enable();
//			grid.getTopToolbar().items.items[4].enable();
			
			var brhId = grid.getSelectionModel().getSelected().data.brhId;
		
		}
	});
	
	//机构添加表单
	var brhInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '工具编码*',
			allowBlank: false,
			emptyText: '请输入工具编码',
			id: 'brhId1',
			name: 'brhIdNM1',
			width: 300
//			maxLength: 4,
//			maxLengthText: '行内机构编码最多可以输入4个数字',
//			vtype: 'isNumber',
//			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '工具名称*',
			allowBlank: false,
			blankText: '工具名称不能为空',
			emptyText: '请工具名称',
			id: 'brhName1',
			name: 'brhNameNM1',
			width: 300,
			maxLength: 40,
			regex:/^\S+$/,
			regexText:'工具名称不能包含空字符',
			maxLengthText: '工具名称最多可以输入40个汉字'
		},{
			id:'brhLvl1',
			xtype: 'combo',
			store: brhLvlStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'brhLvlNM1',
			emptyText: '请工具类型',
			fieldLabel: '机构级别*',
			mode: 'local',
			width: 300,
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个工具类型'
		},{
			fieldLabel: '工具有效期*',
			xtype: 'datefield',
			width : 290,
			id: 'brhName11',
			name: 'brhName11',
			//vtype: 'daterange',
			//endDateField: 'endDate',
			//minValue:agoDate,
			allowBlank: false,
			editable: false
		},{
			fieldLabel: '库房名称*',
			allowBlank: false,
			blankText: '库房名称不能为空',
			emptyText: '请输入库房名称',
			id: 'brhName1',
			name: 'brhNameNM1',
			width: 300,
			maxLength: 40,
			regex:/^\S+$/,
			regexText:'机构名称不能包含空字符',
			maxLengthText: '机构名称最多可以输入40个汉字'
		},{
			fieldLabel: '工具存放位置*',
			allowBlank: false,
			blankText: '工具存放位置不能为空',
			emptyText: '请输入工具存放位置',
			id: 'brhAddr1',
			name: 'brhAddrNM1',
			maxLength: 40,
			maxLengthText: '工具存放位置最多可以输入40个汉字',
			width: 300,
			regex:/^[\u4e00-\u9fa5,0-9,a-z,\-,_]+$/,
			regexText:'机构地址只能是包含：中文、小写字母、数字、-、_'
		},{
			fieldLabel: '采购单位*',
			allowBlank: false,
			blankText: '采购单位不能为空',
			emptyText: '请输入采购单位',
			id: 'brhName1',
			name: 'brhNameNM1',
			width: 300,
			maxLength: 40,
			regex:/^\S+$/,
			regexText:'机构名称不能包含空字符',
			maxLengthText: '机构名称最多可以输入40个汉字'
		},{
			xtype: 'textfield',
			fieldLabel: '采购人*',
			emptyText: '请输入采购人姓名',
			allowBlank: false,
			id: 'brhContName1',
			name: 'brhContNameNM1',
			width: 300,
			maxLength: 20,
			maxLengthText: '采购人姓名最多可以输入20个汉字'
		}]
	});
	
	//机构添加窗口
	var brhWin = new Ext.Window({
		title: '机构维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [brhInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			id : 'ensure1',
			text: '确定',
			handler: function() {
				if(brhInfoForm.getForm().isValid()) {
					var submitValues = brhInfoForm.getForm().getValues();  
					for (var param in submitValues) {  
						if (brhInfoForm.getForm().findField(param) && brhInfoForm.getForm().findField(param).emptyText == submitValues[param]) {  
							brhInfoForm.getForm().findField(param).setValue(' ');  
						}  
					}
					brhInfoForm.getForm().submit({
						url: 'T10101Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							brhWin.hide();
							showSuccessMsg(action.result.msg,brhInfoForm);
							brhInfoForm.getForm().reset();
							grid.getStore().reload();
							SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
								upBrhStore.loadData(Ext.decode(ret));
							});
//							grid.getTopToolbar().items.items[4].disable();
//							grid.getTopToolbar().items.items[2].disable();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,brhInfoForm);
						},
						params: {
							brhId : Ext.getCmp('brhId1').getValue(),
							cupBrhId : Ext.getCmp('cupBrhId1').getValue(),
							brhLvl : Ext.getCmp('brhLvl1').getValue(),
							resv1 : Ext.getCmp('resv11').getValue(),
							upBrhId : Ext.getCmp('upBrhId1').getValue(),
							postCode : Ext.getCmp('postCode1').getValue(),
							brhAddr : Ext.getCmp('brhAddr1').getValue(),
							brhName : Ext.getCmp('brhName1').getValue(),
							brhTelNo : Ext.getCmp('brhTelNo1').getValue(),
							brhContName : Ext.getCmp('brhContName1').getValue(),
							txnId: '10101',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			id : 'ensure2',
			text: '确定',
			handler: function() {
				if(brhInfoForm.getForm().isValid()) {
					var submitValues = brhInfoForm.getForm().getValues();  
					for (var param in submitValues) {  
						if (brhInfoForm.getForm().findField(param) && brhInfoForm.getForm().findField(param).emptyText == submitValues[param]) {  
							brhInfoForm.getForm().findField(param).setValue(' ');  
						}  
					}
					var _brhId =  Ext.getCmp('brhId1').getValue();
					var _brhLvl = Ext.getCmp('brhLvl1').getValue();
					var _upBrhId = Ext.getCmp('upBrhId1').getValue();
					var _brhName = Ext.getCmp('brhName1').getValue();
					
					if(_brhLvl=="0" && _upBrhId != '-'){
						showAlertMsg('不能修改总行上级编码',grid);
						return false;
					}
					if(_brhId == _upBrhId) {
						showAlertMsg('上级机构编码不能与本机构编码相同',grid);
						return false;
					}
					if(_brhName == null || _brhName == ''){
						showAlertMsg('机构名称不能为空',grid);
						return false;
					}
					
					brhInfoForm.getForm().submit({
						url: 'T10101Action.asp?method=update',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							brhWin.hide();
							showSuccessMsg(action.result.msg,brhInfoForm);
							brhInfoForm.getForm().reset();
							grid.getStore().reload();
							SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
								upBrhStore.loadData(Ext.decode(ret));
							});
//							grid.getTopToolbar().items.items[4].disable();
//							grid.getTopToolbar().items.items[2].disable();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,brhInfoForm);
						},
						params: {
							brhId : _brhId,
							cupBrhId : Ext.getCmp('cupBrhId1').getValue(),
							brhLvl : _brhLvl,
							resv1 : Ext.getCmp('resv11').getValue(),
							upBrhId : _upBrhId,
							postCode : Ext.getCmp('postCode1').getValue(),
							brhAddr : Ext.getCmp('brhAddr1').getValue(),
							brhName : _brhName,
							brhTelNo : Ext.getCmp('brhTelNo1').getValue(),
							brhContName : Ext.getCmp('brhContName1').getValue(),
							txnId: '10101',
							subTxnId: '03'
						}
					});
				}	
			}
		},{
			id : 'reset1',
			text: '重置',
			handler: function() {
				brhInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				brhWin.hide(grid);
				brhInfoForm.getForm().reset();
			}
		}]
	});
	
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});
	
	

	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			//brhId: queryForm.findById('editBrh').getValue(),	
			brhName: queryForm.findById('searchBrhName').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});