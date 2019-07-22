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
			case '0': return '一级';
			case '1': return '二级';
			case '2': return '三级';
			case '3':return '四级';
		}
	}
	
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'brhId',header: '机构编码',dataIndex: 'brhId',sortable: true,width: 120},
		{header: '上级编码',dataIndex: 'upBrhId',width: 120,sortable: true
//		 editor: new Ext.form.ComboBox({
//		 	store: upBrhStore,
//			displayField: 'displayField',
//			valueField: 'valueField',
//			mode: 'local',
//			triggerAction: 'all',
//			forceSelection: true,
//			typeAhead: true,
//			selectOnFocus: true,
//			editable: true
//		 })
	},
		{header: '机构名称',dataIndex: 'brhName',sortable: true,width: 160
//		 editor: new Ext.form.TextField({
//		 	allowBlank: false,
//			blankText: '机构名称不能为空',
//			emptyText: '请输入机构名称',
//			maxLength: 40,
//			maxLengthText: '机构名称最多可以输入40个汉字'
//		 })
	},
		{id:'brhAddr',header: '机构地址',sortable: true,dataIndex: 'brhAddr',width: 120},
		{header: '联系方式',dataIndex: 'brhTelNo',sortable: true,width: 120
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
	},
		{header: '联系人',dataIndex: 'brhContName',sortable: true,width: 120
//		 editor: new Ext.form.TextField({
//		 	allowBlank: false,
//			emptyText: '请输入负责人姓名',
//			maxLength: 20,
//			maxLengthText: '负责人姓名最多可以输入20个汉字'
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
				var brhId = rec.get('brhId');		
				
				showConfirm('确定要删除该机构吗？机构编码：' + brhId,grid,function(bt) {
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
			            Ext.getCmp('ensure1').hide().disable();
			            Ext.getCmp('ensure2').show().enable();
			            Ext.getCmp('reset1').hide().disable();
						Ext.getCmp('brhId1').disable();
						/*if(rspObj.msg.brhLevel == '' || rspObj.msg.brhLevel == '0'){
							Ext.getCmp('brhLvl1').disable();
						}else{
							Ext.getCmp('brhLvl1').enable();
						}*/
			            Ext.getCmp('brhId1').setValue(rspObj.msg.id);
			            Ext.getCmp('upBrhId1').setValue(rspObj.msg.upBrhId);
			            Ext.getCmp('brhName1').setValue(rspObj.msg.brhName);
			            Ext.getCmp('brhAddr1').setValue(rspObj.msg.brhAddr);
			            Ext.getCmp('brhTelNo1').setValue(rspObj.msg.brhTelNo);
			            Ext.getCmp('brhContName1').setValue(rspObj.msg.brhContName);
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
				queryForm.getForm().reset();
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
		fieldLabel: '机构编码',
		width: 320,
		id: 'editBrh',
		name: 'editBrh',
		hiddenName: 'brhIdEdit'
			
	
	});
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 490,
		autoHeight: true,
		items: [brhCombo,{
			xtype: 'textfield',
			id: 'searchBrhName',
			name: 'searchBrhName',
			maxLength: 20,
			width: 320,
			fieldLabel: '机构名称'
		}]
	});

	//机构列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '机构信息维护',
		iconCls: 'T10101',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		autoExpandColumn:'brhAddr',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载机构信息列表......'
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
		grid.getTopToolbar().items.items[4].disable();
		grid.getTopToolbar().items.items[2].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//单击行，使删除按钮可用
		'rowclick': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[2].enable();
			grid.getTopToolbar().items.items[4].enable();
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
			grid.getTopToolbar().items.items[2].enable();
			grid.getTopToolbar().items.items[4].enable();
			
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
			fieldLabel: '机构编码*',
			allowBlank: false,
			emptyText: '请输入机构编码',
			id: 'brhId1',
			name: 'brhIdNM1',
			width: 300,
			maxLength: 8,
			maxLengthText: '机构编码最多可以输入8个数字',
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			id:'upBrhId1',
			xtype: 'combo',
			store: upBrhStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'upBrhIdNM1',
			emptyText: '请选择上级机构编码',
			fieldLabel: '上级机构编码*',
			mode: 'local',
			width: 300,
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: false,
			blankText: '请选择一个上级机构编码'
		},{
			fieldLabel: '机构名称*',
			allowBlank: false,
			blankText: '机构名称不能为空',
			emptyText: '请输入机构名称',
			id: 'brhName1',
			name: 'brhNameNM1',
			width: 300,
			maxLength: 20,
			regex:/^\S+$/,
			regexText:'机构名称不能包含空字符'
//			maxLengthText: '机构名称最多可以输入40个汉字'
		},{
			fieldLabel: '机构地址',
			allowBlank: true,
			blankText: '',
			emptyText: '请输入机构地址',
			id: 'brhAddr1',
			name: 'brhAddrNM1',
			maxLength: 40,
//			maxLengthText: '机构地址最多可以输入40个汉字',
			width: 300
		},{
			fieldLabel: '机构联系电话*',
			allowBlank: false,
			emptyText: '请输入机构联系电话',
			id: 'brhTelNo1',
			name: 'brhTelNoNM1',
			width: 300,
			regexText:"请填写正确的联系电话！", 
			regex: /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|[1][3,4,5,7,8,9][0-9]{9})$/
//			maxLength: 20,
//			maxLengthText: '机构联系电话最多可以输入20个数字',
//			vtype: 'isNumber',
//			blankText: '该输入项只能包含数字'
		},{
			xtype: 'textfield',
			fieldLabel: '联系人姓名*',
			emptyText: '请输入联系人姓名',
			allowBlank: false,
			id: 'brhContName1',
			name: 'brhContNameNM1',
			width: 300,
			maxLength: 20
//			maxLengthText: '联系人姓名最多可以输入20个汉字'
		}]
	});
	
	//机构添加窗口
	var brhWin = new Ext.Window({
		title: '机构信息',
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
								brhStore.loadData(Ext.decode(ret));
							});
							grid.getTopToolbar().items.items[4].disable();
							grid.getTopToolbar().items.items[2].disable();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,brhInfoForm);
						},
						params: {
							brhId : Ext.getCmp('brhId1').getValue(),
							cupBrhId : '0000',
							resv1 : '0000',
							brhLvl : 0,
							upBrhId : Ext.getCmp('upBrhId1').getValue(),
							postCode : '0000',
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
					var _upBrhId = Ext.getCmp('upBrhId1').getValue();
					var _brhName = Ext.getCmp('brhName1').getValue();

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
								brhStore.loadData(Ext.decode(ret));
							});
							grid.getTopToolbar().items.items[4].disable();
							grid.getTopToolbar().items.items[2].disable();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,brhInfoForm);
						},
						params: {
							brhId : _brhId,
							cupBrhId : '0000',
							resv1 : '0000',
							upBrhId : _upBrhId,
							postCode : '0000',
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
	/************************************************以下是机构相关操作员信息************************************************************/
	
	/*****************************************操作员信息*****************************************/
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=oprInfoWithBrh'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'oprId'
		},[
			{name: 'oprId',mapping: 'oprId'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprName',mapping: 'oprName'},
			{name: 'oprGender',mapping: 'oprGender'},
			{name: 'registerDt',mapping: 'registerDt'},
			{name: 'oprTel',mapping: 'oprTel'},
			{name: 'oprMobile',mapping: 'oprMobile'}
		])
	});
	
	oprGridStore.on('beforeload',function() {
		Ext.apply(this.baseParams,{
			start: 0,
			brhId: grid.getSelectionModel().getSelected().get('brhId')
		});
	});
	
	
	
	var oprTpl = new Ext.Template(
			'<p title="操作员姓名"><img src="' + Ext.contextPath + '/ext/resources/images/user.png"/>：{oprName}</p>',
			'<p title="操作员性别"><img src="' + Ext.contextPath + '/ext/resources/images/gender_16.png"/>：{oprGender:this.gender}</p>',
			'<p title="注册日期"><img src="' + Ext.contextPath + '/ext/resources/images/calendar_16.png"/>：{registerDt:this.date}</p>',
			'<p title="操作员联系电话"><img src="' + Ext.contextPath + '/ext/resources/images/phone_16.png"/>：{oprTel}</p>',
			'<p title="操作员移动电话"><img src="' + Ext.contextPath + '/ext/resources/images/mobile_16.png"/>：{oprMobile}</p>'
	);
	
	oprTpl.gender = function(val) {
		if(val == '0') {
			return '男';
		} else {
			return '女';
		}
	};
	
	oprTpl.date = function(val) {
		if(val.length == 8) {
			return val.substring(0,4) + '年' +
				   val.substring(4,6) + '月' + 
				   val.substring(6,8) + '日';
		} else {
			return val;
		}
	};
	
	
	
	var oprRowExpander = new Ext.ux.grid.RowExpander({
		tpl: oprTpl
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		oprRowExpander,
		{id: 'oprId',header: '操作员编码',dataIndex: 'oprId',align: 'center',sortable: true},
		{header: '所在机构编码',align: 'center',dataIndex: 'brhId'}
	]);
	
	var oprGridPanel = new Ext.grid.GridPanel({
		id: 'oprPanel',
		title: '操作员信息',
		frame: true,
		iconCls: 'T104',
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		collapsible: true,
		plugins: oprRowExpander,
		loadMask: {
			msg: '正在加载操作员信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: oprGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	/********************************主界面*************************************************/
	
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});
	
	var rightPanel = new Ext.Panel({
		region: 'east',
		split: true,
		width: 265,
		frame: true,
		collapsible: true,
		autoScroll: true,
		title: '机构关联信息',
		items:[oprGridPanel]
	});
	
	
	oprGridPanel.on('render',function() {
		new Ext.dd.DragSource(oprGridPanel.getEl(), {
			ddGroup: 'dd'
		});
	});
	
	var items;
	var draggedPanel;
	var positionY;
	var itemY;
	var index;
	rightPanel.on('render',function() {
		var rightPanelTarget = new Ext.dd.DropTarget(rightPanel.getEl(),{
			ddGroup: 'dd',
			notifyDrop: function(source,e) {
				items = rightPanel.items.items;
				draggedPanel = Ext.getCmp(source.getEl().id);
				positionY = e.getPageY();
				heigh = 0;
				index = 10;
				for(var i = 0; i < draggedPanel.el.dom.parentNode.childNodes.length; i++) {
					itemY = Ext.getCmp(draggedPanel.el.dom.parentNode.childNodes[i].id).getPosition()[1];
					if(itemY > positionY) {
						index = i;
						break;
					}
				}
				if(index == 10) {
					draggedPanel.el.dom.parentNode.appendChild(draggedPanel.el.dom);
				} else {
					draggedPanel.el.dom.parentNode.insertBefore(draggedPanel.el.dom, draggedPanel.el.dom.parentNode.childNodes[index]);
				}
				
			}
		});		
	});

	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			brhId: queryForm.findById('editBrh').getValue(),
			brhName: queryForm.findById('searchBrhName').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});