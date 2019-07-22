Ext.onReady(function() {
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtCheckInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mcht_no'},
			{name: 'checkDate',mapping: 'check_date'},
			{name: 'checkName',mapping: 'check_name'},
			{name: 'checkInf',mapping: 'check_inf'},
			
			{name: 'mchtNm',mapping: 'mcht_nm'},
			{name: 'addr',mapping: 'addr'},
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'comm_tel'},
		])
	});
	
	gridStore.load({
		params:{start: 0}
	});
	
	var infRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
				'<font color="blue">商户名称：</font>{mchtNm}</br>',
				'<font color="blue">商户地址：</font>{addr}</br>',
				'<font color="blue">联系人：</font>{contact}</br>',
				'<font color="blue">联系电话：</font>{commTel}</br>'
		)
	});
	
	var colModel = new Ext.grid.ColumnModel([
         infRowExpander,
	    {header: '商户号',dataIndex: 'mchtNo',width: 120},
	    {header: '巡检日期',dataIndex: 'checkDate',width: 120},
	    {header: '巡检人姓名',dataIndex: 'checkName',width: 120,
	    	editor: new Ext.form.TextField({
				maxLength: 60,
				vtype: 'isOverMax'
			 })},
	    {header: '巡检情况备注',dataIndex: 'checkInf',id: 'checkInfLenId',
	    	editor: new Ext.form.TextField({
				maxLength: 512,
				vtype: 'isOverMax'
			 })}
	    ]);

	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			addWin.show();
			addWin.center();
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
				showConfirm('确定要删除该条信息吗？',grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T20801Action.asp?method=delete',
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
								mchtNo: rec.get('mchtNo'),
								checkDate: rec.get('checkDate'),
								txnId: '20801',
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
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
						mchtNo : record.get('mchtNo'),
						checkDate: record.get('checkDate'),
						checkName: record.get('checkName'),
						checkInf : record.get('checkInf')
				};
				array.push(data);
			}
			
			Ext.Ajax.request({
				url: 'T20801Action.asp?method=update',
				params: {
					infoList: Ext.encode(array),
					txnId: '20801',
					subTxnId: '02'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
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
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);

	var grid = new Ext.grid.EditorGridPanel({
		title: '商户巡检信息维护',
		iconCls: 'T208',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		autoExpandColumn: 'checkInfLenId',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: colModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		plugins: infRowExpander,
		loadMask: {
			msg: '正在加载信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	
	grid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			grid.getTopToolbar().items.items[4].enable();
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[2].enable();
		}
	});	
	
	//信息添加表单    
	var infoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		labelWidth: 80,
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号*',
			methodName: 'getMchntIdfor20801',
			hiddenName: 'mchtNo',
			allowBlank: false,
			editable: true,
			width: 240
		},{
			xtype: 'datefield',
			fieldLabel: '巡检日期*',
			id: 'checkDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			editable: false,
			width: 160
		},{
			xtype: 'textfield',
			fieldLabel: '巡检人姓名',
			maxLength: 32,
			vtype: 'isOverMax',
			width:160,
			id: 'checkName'
		},{
        	xtype: 'textarea',
        	width:160,
        	height: 120,
        	maxLength: 512,
			fieldLabel: '巡检情况备注',
			vtype: 'isOverMax',
			id: 'checkInf'
    	}]
	});
	
	//新增Win
	var addWin = new Ext.Window({
		title: '商户巡检信息维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 360,
		autoHeight: true,
		layout: 'fit',
		items: [infoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(infoForm.getForm().isValid()) {
					infoForm.getForm().submitNeedAuthorise({
						url: 'T20801Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,infoForm);
							infoForm.getForm().reset();
							grid.getStore().reload();
							addWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,infoForm);
							addWin.hide();
						},
						params: {
							txnId: '20801',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				infoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addWin.hide(grid);
			}
		}]
	});
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});