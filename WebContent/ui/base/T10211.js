Ext.onReady(function() {
	var upBrhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});

	//上级营业网点编号
	SelectOptionsDWR.getComboData('BRH_ID_NEW',function(ret){
		upBrhStore.loadData(Ext.decode(ret));
	});
	var brhParamStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhParamInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount' ,
			idProperty: 'brhId'
		},[
			{name: 'brhId',mapping: 'brhId'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'statDate',mapping: 'statDate'},
			{name: 'stdToalcounts',mapping: 'stdToalcounts'},
			{name: 'stdToalmoney',mapping: 'stdToalmoney'},
			{name: 'stdCondition',mapping: 'stdCondition'}
		])
	});
	
	brhParamStore.load({
		params:{start: 0}
	});
	
	var paramColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
		{id: 'brhId',header: '机构编号',dataIndex: 'brhId',sortable: true,width: 100},
		{header: '机构名称',dataIndex: 'brhName',sortable: true,width: 180},
		{header: '统计周期日',dataIndex: 'statDate',width: 100,align: 'right',
		 editor: new Ext.form.TextField({
			 	allowBlank: false,
				blankText: '统计周期日不能为空',
				emptyText: '请输入统计周期日',
				maxLength: 2,
				maxLengthText: '格式:dd,如只有一位左补0'
			 })},
		{header: '消费总次数达标值',dataIndex: 'stdToalcounts',width: 120,align: 'right',
		 editor: new Ext.form.TextField({
			 	allowBlank: false,
				blankText: '消费总次数达标值不能为空',
				emptyText: '请输入消费总次数达标值',
				maxLength: 6,
				maxLengthText: '最大位数是6位'
			 })},
	   {header: '消费总金额达标值(分)',dataIndex: 'stdToalmoney',width: 150,align: 'right',
		 editor: new Ext.form.TextField({
			 	allowBlank: false,
				blankText: '消费总金额达标值不能为空',
				emptyText: '请输入消费总金额达标值，单位:分',
				maxLength: 12,
				maxLengthText: '最大位数是12位'
			 })},
	  {header: '达标条件',dataIndex: 'stdCondition',width: 480,renderer:function(data){
		    if(data == '01')
				return "总金额达标";
			if(data == '02')
				return "总笔数达标";
			if(data == '03')
				return "总金额达标或总笔数达标";
			if(data == '04')
				return "总金额达标且总笔数达标";
	    },
		 editor: new Ext.form.ComboBox({
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['01','01-总金额达标'],
					       ['02','02-总笔数达标'],
					       ['03','03-总金额达标或总笔数达标'],
					       ['04','04-总金额达标且总笔数达标']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true,
				lazyRender: true
			})}
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
							url: 'T10211Action.asp?method=delete',
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
								brhId: rec.get('brhId'),
								txnId: '10211',
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
			var modifiedRecords = grid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			showProcessMsg('正在保存参数信息，请稍后......');
			//存放要修改的参数信息
			var array = new Array();
			var vfail=0;
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				if(record.get('brhId').trim()==''||record.get('statDate').trim()==''
					||record.get('stdToalcounts').trim()==''||record.get('stdToalmoney').trim()==''
					||record.get('stdCondition').trim()==''){
					vfail=1;
					break;
				}
				var stDate = record.get('statDate');
				if(isNaN(stDate)){
				   hideProcessMsg();
				   showErrorMsg("统计周期日不是数字",grid);
				   return;
				}
				if(stDate > 31 || stDate < 1){
					hideProcessMsg();
					showErrorMsg("请输入1-31之内的数字",grid);
    				return;
				}
				var stdToalcounts = record.get('stdToalcounts');
				if(isNaN(stdToalcounts)){
					hideProcessMsg();
				   showErrorMsg("消费总次数达标值不是数字",grid);
				   return;
				}
				var stdToalmoney = record.get('stdToalmoney');
				if(isNaN(stdToalmoney)){
					hideProcessMsg();
				   showErrorMsg("消费总金额达标值不是数字",grid);
				   return;
				}
				var data = {
					id : record.get('brhId'),
					brhId : record.get('brhId'),
					statDate: record.get('statDate'),
					stdToalcounts: record.get('stdToalcounts'),
					stdToalmoney: record.get('stdToalmoney'),
					stdCondition: record.get('stdCondition'),
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
				url: 'T10211Action.asp?method=update',
				method: 'post',
				params: {
					parameterList : Ext.encode(array),
					txnId: '10211',
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
	
	// 可选机构下拉列表
	var brhQueryCombo = new Ext.form.ComboBox({
		store: upBrhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择机构',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		allowBlank: true,
		width: 260,
		fieldLabel: '机构编号',
		id: 'editBrh',
		name: 'editBrh',
		hiddenName: 'brhIdEdit'
	});
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 490,
		autoHeight: true,
		items: [brhQueryCombo,{
			xtype: 'textfield',
			id: 'searchBrhName',
			name: 'searchBrhName',
			maxLength: 40,
			width: 320,
			fieldLabel: '机构名称'
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
			    brhParamStore.load();
				queryWin.hide();
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
	
	// 机构达标参数列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '机构参数维护',
		iconCls: 'T10211',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: brhParamStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: paramColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载机构参数信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: brhParamStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
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
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('BRH_ID_NEW',function(ret){
		brhStore.loadData(Ext.decode(ret));
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
		width:400,
		allowBlank: false,
		blankText: '请选择一个机构',
		fieldLabel: '所属机构号*',
		hiddenName: 'brhId'
	});
	var stdConditionCombo = new Ext.form.ComboBox({
		store: new Ext.data.ArrayStore({
			fields: ['valueField','displayField'],
			data: [['01','总金额达标'],
			       ['02','总笔数达标'],
			       ['03','总金额达标或总笔数达标'],
			       ['04','总金额达标且总笔数达标']],
			reader: new Ext.data.ArrayReader()
		}),
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择达标条件',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		width:400,
		allowBlank: false,
		blankText: '请选择一个达标条件',
		fieldLabel: '达标条件*',
		hiddenName: 'stdCondition'
	});
	//添加表单
	var paramInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 620,
		defaultType: 'textfield',
		labelWidth: 150,
		waitMsgTarget: true,
		items: [
		    brhCombo,
		    {
				fieldLabel: '统计周期日*',
				id: 'statDate',
				name: 'statDate',
				width: 400,
				maxLength: 2,
				allowBlank: false,
				blankText: '统计周期日不能为空',
				emptyText: '请输入统计周期日值',
				vtype: 'isNumber'
			},{
				fieldLabel: '消费总次数达标值*',
				id: 'stdToalcounts',
				name: 'stdToalcounts',
				width: 400,
				maxLength: 6,
				allowBlank: false,
				blankText: '不能为空',
				emptyText: '请输入值',
				vtype: 'isNumber',
				regexText:'不能包含空格'
			},{
				fieldLabel: '消费总金额达标值(分)*',
				id: 'stdToalmoney',
				name: 'stdToalmoney',
				width: 400,
				maxLength: 12,
				allowBlank: false,
				blankText: '不能为空',
				emptyText: '请输入值,单位是分',
				vtype: 'isNumber',
				regexText:'不能包含空格'
			},
			stdConditionCombo]
	});
	
	//参数信息添加窗口
	var paramWin = new Ext.Window({
		title: '信息添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 650,
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
					var stDate = Ext.getCmp("statDate").getValue();
					if(stDate > 31 || stDate < 1){
						showErrorMsg("请输入1-31之内的数字",grid);
	    				return;
					}
					paramInfoForm.getForm().submit({
						url: 'T10211Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,paramInfoForm);
							//重置表单
							paramInfoForm.getForm().reset();
							//重新加载参数列表
							grid.getStore().reload();
							paramWin.hide(grid);
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,paramInfoForm);
						},
						params: {
							txnId: '10211',
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
	brhParamStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			brhId: queryForm.findById('editBrh').getValue(),
			brhName: queryForm.findById('searchBrhName').getValue()
		});
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});