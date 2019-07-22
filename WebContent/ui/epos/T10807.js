Ext.onReady(function(){
	
	var mainForm = new Ext.form.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'center',
		defaults: {
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			anchor: '90%'
		},
		items: [{
			fieldLabel: '错误码*',
			name: 'rspNo',
			maxLength: 10,
			regexText: '该输入框只能输入数字'
		},{
			fieldLabel: '渠道号*',
			name: 'chgNo',
			maxLength: 4,
			regex: /^[0-9]+$/,
			regexText: '该输入框只能输入数字',
			maskRe: /^[0-9]+$/
		},{
			fieldLabel: '内部错误码*',
			name: 'errNo',
			maxLength: 2,
			regex: /^[0-9]+$/,
			regexText: '该输入框只能输入数字',
			maskRe: /^[0-9]+$/
		},{
			fieldLabel: '错误码描述*',
			name: 'errMsg',
			maxLength: 64,
			vtype: 'isOverMax'
		}],
		buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = mainForm.getForm();
				if (frm.isValid()) {
					if(1 != 1){
					}else{
						frm.submit({
							url: 'T10807Action_add.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								showSuccessAlert(action.result.msg,mainForm);
								mainWin.hide(mainGrid);
								mainStore.reload();
								frm.resetAll();
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,mainForm);
							},
							params: {
								txnId: '10807',
								subTxnId: '01'
							}
					});
				}
			}}
		},{
            text: '关闭',
            handler: function() {
				mainWin.hide(mainGrid);
				mainForm.getForm().resetAll();
			}
        }]
	});
	
	var mainWin = new Ext.Window({
		title: '新增返回码说明信息',
		iconCls: 'T30507',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		items: [mainForm]
	});
	//functions
	function add(){
		mainWin.show(mainGrid);
	};
	function del(){
		var sel = mainGrid.getSelectionModel().getSelected();
		if(sel == null){
			showMsg("请选择一条记录后再进行操作。",mainGrid);return;
		}
		showConfirm('确定要删除该条记录吗？',mainGrid,function(bt) {
			if(bt == "yes") {
				Ext.Ajax.request({
					url: 'T10807Action_delete.asp',
					success : function(form, action) {
						var rspObj = Ext.util.JSON.decode(form.responseText);
						if(rspObj.success){
							showSuccessMsg(rspObj.msg,mainGrid);
							mainStore.reload();
						}else{
							showErrorMsg(rspObj.msg,mainGrid);
						}
					},
					failure : function(form,action) {
						showErrorMsg(action.result.msg,mainGrid);
					},
					params: {
						chgNo:sel.data.CHG_NO,
						rspNo:sel.data.RSP_NO,
						txnId: '10807',
						subTxnId: '03'
					}
				});
			}
		});
	}
	
	var updateBut = {
			text: '保存',
			width: 75,
			iconCls: 'reload',
			disabled: true,
			handler: function(bt) {
				var modifiedRecords = mainGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				var store = mainGrid.getStore();
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						chgNo : record.get('CHG_NO'),
						rspNo: record.get('RSP_NO'),
						errNo: record.get('ERR_NO'),
						errMsg: record.get('ERR_MSG')
					};
					array.push(data);
				}
				Ext.Ajax.requestNeedAuthorise({
					url: 'T10807Action_update.asp',
					method: 'post',
					params: {
						dataList : Ext.encode(array),
						txnId: '10807',
						subTxnId: '02'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						mainGrid.enable();
						if(rspObj.success) {
							mainGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,mainGrid);
						} else {
							mainGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,mainGrid);
						}
						mainGrid.getTopToolbar().items.items[4].disable();
						mainGrid.getStore().reload();
						hideProcessMsg();
					}
				});
			}
		};
	//数据集
	var mainStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=rspMsg'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		    {name: 'RSP_NO',mapping: 'RSP_NO'},
			{name: 'CHG_NO',mapping: 'CHG_NO'},
			{name: 'ERR_NO',mapping: 'ERR_NO'},
			{name: 'ERR_MSG',mapping: 'ERR_MSG'},
			{name: 'CRT_DATE',mapping: 'CRT_DATE'},
			{name: 'UPD_DATE',mapping: 'UPD_DATE'}
		]),
		autoLoad: true
	});
	
	//列模型
	var mainModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '错误码',dataIndex: 'RSP_NO',width: 120},
			{header: '渠道号',dataIndex: 'CHG_NO',width: 120},
			{header: '内部错误码',dataIndex: 'ERR_NO',width: 120},
    		{header: '错误码描述',dataIndex: 'ERR_MSG',id: 'ERR_MSG',
				editor: new Ext.form.TextField({
    		 	allowBlank: false,
    			blankText: '错误码描述不能为空',
    			emptyText: '请输入错误码描述',
    			maxLength: 64,
				vtype: 'isOverMax'
    		 })},
    		 {header: '创建时间',dataIndex: 'CRT_DATE',width: 120},
    		 {header: '更新时间',dataIndex: 'UPD_DATE',width: 120}
    ]);
	
	//GRID
	var mainGrid = new Ext.grid.EditorGridPanel({
		title: '返回码说明',
		iconCls: 'T30507',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'ERR_MSG',
		tbar: [{
			xtype: 'button',
			text: '新增',
			name: 'add',
			id: 'add',
			iconCls: 'add',
			width: 75,
			handler:function() {
				add();
			}
		},'-',{
			xtype: 'button',
			text: '删除',
			name: 'delete',
			id: 'delete',
			iconCls: 'delete',
			width: 75,
			handler:function() {
				del();
			}
		},'-',updateBut],
		loadMask: {
			msg: '正在加载信息列表，请稍候......'
		},
		bbar: new Ext.PagingToolbar({
			store: mainStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	mainGrid.on({
		'afteredit': function(e) {
			if(mainGrid.getTopToolbar().items.items[4] != undefined) {
				mainGrid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	
	//Main View
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mainGrid],
		renderTo: Ext.getBody()
	});
});