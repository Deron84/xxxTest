Ext.onReady(function() {
	
	/************************************系统参数信息*******************************************/
	var sysParamStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=routeParamInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'usage_key',mapping: 'usage_key'},
			{name: 'line_index',mapping: 'line_index'},
			{name: 'local_addr',mapping: 'local_addr'},
			{name: 'remote_addr',mapping: 'remote_addr'},
			{name: 'in_sock_num',mapping: 'in_sock_num'},
			{name: 'out_sock_num',mapping: 'out_sock_num'},
			{name: 'line_dsp',mapping: 'line_dsp'}
		])
	});
	
	sysParamStore.load({
		params:{start: 0}
	});
	
	var paramColModel = new Ext.grid.ColumnModel([
	                                      		new Ext.grid.RowNumberer(),
	                                      		{id: 'usage_key',header: '参数编号',dataIndex: 'usage_key',sortable: true,width: 100},
	                                    		{header: '通讯方式',dataIndex: 'line_index',width: 60,renderer:function(data){
	                                    	    	if(null == data) return '';
	                                    	    	
	                                    	    	if(data=="1"){
	                                    	    		return "TCP/IP";
	                                    	    	}
	                                    	    	if(data=="2"){
	                                    	    		return "HTTPS";
	                                    	    	}
	                                    	    },editor: new Ext.form.ComboBox({
	                                    	    	store: new Ext.data.ArrayStore({
	                                    				fields: ['valueField','displayField'],
//	                                    				data: [['00','可维护'],['01','不可维护']],
	                                    				data: [['1','TCP/IP'],['2','HTTPS']],
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
	                                    		 })},
	                                    		{header: '本地地址',dataIndex: 'local_addr',width: 120, 
	                                    			editor: new Ext.form.TextField({
	                                    		 	maxLength: 200,
	                                    		 	regex:/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/,
	                                    			regexText:'地址格式错误',
	                                    			allowBlank: false,
	                                    			vtype: 'isOverMax'
	                                    		 })},
	                                    		{header: '远程地址',dataIndex: 'remote_addr',width: 120,
	                                    		 editor: new Ext.form.TextField({
	                                    		 	maxLength: 200,
	                                    		 	regex:/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/,
	                                    			regexText:'地址格式错误',
	                                    			allowBlank: false,
	                                    			vtype: 'isOverMax'
	                                    		 })},
	                                    		{header: '本地端口',dataIndex: 'in_sock_num',width: 70,
	                                    		 editor: new Ext.form.TextField({
	                                    		 	allowBlank: false,
	                                    		 	regex:/^[0-9]{0,5}$/,
	                                    			regexText:'端口格式错误',
	                                    			maxLength: 60,
	                                    			vtype: 'isOverMax'
	                                    		 })},
	                                    			{header: '远程端口',dataIndex: 'out_sock_num',width: 70,
	                                    			 editor: new Ext.form.TextField({
	                                    			 	allowBlank: false,
	                                    			 	regex:/^[0-9]{0,5}$/,
		                                    			regexText:'端口格式错误',
	                                    				maxLength: 60,
	                                    				vtype: 'isOverMax'
	                                    			 })},
	                                      		{header: '参数描述',dataIndex: 'line_dsp',id:'line_dsp',width: 200,
	                                      		 editor: new Ext.form.TextField({
	                                      		 	allowBlank: false,
	                                      			maxLength: 60,
	                                      			vtype: 'isOverMax'
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
							url: 'T10210Action.asp?method=delete',
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
								usage_key: rec.get('usage_key'),
								txnId: '10210',
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
				//校验数据格式
				var addr=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
				if(!addr.test(record.get('local_addr'))||!addr.test(record.get('remote_addr'))){
					showErrorMsg("地址格式不正确",grid);
					grid.getStore().reload();
					hideProcessMsg();
					return;
				}
				var port=/^[0-9]{0,5}$/;
				if(!port.test(record.get('in_sock_num'))||!port.test(record.get('out_sock_num'))){
					showErrorMsg("端口格式不正确",grid);
					grid.getStore().reload();
					hideProcessMsg();
					return;
				}
				var dsp=/^\S{1,}$/;
				if(!dsp.test(record.get('line_dsp'))){
					showErrorMsg("描述信息格式不正确",grid);
					grid.getStore().reload();
					hideProcessMsg();
					return;
				}
				var data = {
					usage_key : record.get('usage_key'),
					line_index : record.get('line_index'),
					local_addr: record.get('local_addr'),
					remote_addr: record.get('remote_addr'),                 
					in_sock_num: record.get('in_sock_num'),
					out_sock_num: record.get('out_sock_num'),
					line_dsp: record.get('line_dsp').trim()
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T10210Action.asp?method=update',
				method: 'post',
				params: {
					parameterList : Ext.encode(array),
					txnId: '10210',
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
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);
	menuArr.push('<font color=\'red\'>修改路由信息后，请重新启动核心交易系统</font>');
	
	// 系统参数列表
	var grid = new Ext.grid.EditorGridPanel({
		id: 'SystemParameter',
		title: '路由参数维护',
		iconCls: 'T10210',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoExpandColumn:'line_dsp',
//		autoHeight: true,
		store: sysParamStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: paramColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载参数信息列表......'
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
		width: 380,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '参数编号',
			id: 'usage_key',
			name: 'usage_key',
			width: 200,
			maxLength: 4,
			allowBlank: false,
			blankText: '参数编号不能为空',
			emptyText: '参数编号不能为空',
			vtype: 'isOverMax',
			regex:/^[1-9]{1}[0-9]{3}$/,
			regexText:'四位纯数字'
		},{
			fieldLabel: '通讯方式',
			xtype: 'combo',
			width: 200,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
//				data: [['00','可维护'],['01','不可维护']],
				data: [['1','TCP/IP'],['2','HTTPS']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'line_index',
			emptyText: '请选择通讯方式',
			fieldLabel: '通讯方式*',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个通讯方式'
		},{
			fieldLabel: '本地地址*',
			id: 'local_addr',
			name: 'local_addr',
			width: 200,
			maxLength: 20,
			allowBlank: false,
			blankText: '本地地址不能为空',
			emptyText: '请输入本地地址',
			vtype: 'isOverMax',
			regex:/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/,
			regexText:'地址格式错误',
		},{
			fieldLabel: '远程地址*',
			id: 'remote_addr',
			name: 'remote_addr',
			width: 200,
			maxLength: 20,
			allowBlank: false,
			blankText: '本地地址不能为空',
			emptyText: '请输入本地地址',
			vtype: 'isOverMax',
			regex:/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/,
			regexText:'地址格式错误',
		},{
			fieldLabel: '本地端口*',
			id: 'in_sock_num',
			name: 'in_sock_num',
			width: 200,
			maxLength: 200,
			allowBlank: false,
			blankText: '本地端口不能为空',
			emptyText: '请输入本地端口号',
			vtype: 'isOverMax',
		 	regex:/^[0-9]{0,5}$/,
			regexText:'端口格式错误'
		},{
			fieldLabel: '远程端口*',
			id: 'out_sock_num',
			name: 'out_sock_num',
			width: 200,
			maxLength: 200,
			allowBlank: false,
			blankText: '远程端口不能为空',
			emptyText: '请输入远程端口号',
			vtype: 'isOverMax',
		 	regex:/^[0-9]{0,5}$/,
			regexText:'端口格式错误'
		},{
			fieldLabel: '参数描述*',
			allowBlank: false,
			blankText: '参数描述不能为空',
			emptyText: '请输入参数描述',
			id: 'line_dsp',
			name: 'line_dsp',
			width: 200,
			maxLength: 60,
			vtype: 'isOverMax',
			regex:/^\S{1,}$/,
			regexText:'不能包含空格'
		}]
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
						url: 'T10210Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,paramInfoForm);
							//重置表单
							paramInfoForm.getForm().reset();
							//重新加载参数列表
							grid.getStore().reload();
							paramWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,paramInfoForm);
						},
						params: {
							txnId: '10210',
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