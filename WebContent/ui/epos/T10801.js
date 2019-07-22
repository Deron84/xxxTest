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
			xtype: 'basecomboselect',
        	baseParams: 'CUP_BRH_BELOW',
			fieldLabel: '机构编号*',
			allowBlank: false,
			blankText: '请选择机构编号',
			hiddenName: 'brhId'
		},{
			fieldLabel: '首页提示信息*',
			name: 'pptMsg',
			maxLength: 128,
			vtype: 'isOverMax'
		}],
		buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = mainForm.getForm();
				if (frm.isValid()) {
						frm.submitNeedAuthorise({
							url: 'T10801Action_add.asp',
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
								txnId: '10801',
								subTxnId: '01'
							}
					});
				}
			}
		},{
            text: '关闭',
            handler: function() {
				mainWin.hide(mainGrid);
				mainForm.getForm().resetAll();
			}
        }]
	});
	
	var mainWin = new Ext.Window({
		title: '新增首页提示信息',
		iconCls: 'T30501',
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
		showConfirm('确定要删除该条记录吗？',mainGrid,function(bt) {
			if(bt == "yes") {
				var sel = mainGrid.getSelectionModel().getSelected();
				if(sel == null){
					showMsg("请选择一条记录后再进行操作。",mainGrid);
					return;
				}
				Ext.Ajax.requestNeedAuthorise({
					url: 'T10801Action_delete.asp',
					method: 'post',
					success : function(form, action) {
						var rspObj = Ext.util.JSON.decode(form.responseText);
						if(rspObj.success){
							showSuccessMsg(rspObj.msg,mainGrid);
							mainStore.reload();
						}else{
							showErrorMsg(rspObj.msg,mainGrid);
						}
					},
					params: {
						brhId: sel.get('BRH_ID'),
						txnId: '10801',
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
			handler: function() {
				var modifiedRecords = mainGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				var store = mainGrid.getStore();
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						brhId : record.get('BRH_ID'),
						pptMsg: record.get('PPT_MSG')
						
					};
					array.push(data);
				}
				Ext.Ajax.requestNeedAuthorise({
					url: 'T10801Action_update.asp',
					method: 'post',
					params: {
						dataList : Ext.encode(array),
						txnId: '10801',
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
			url: 'gridPanelStoreAction.asp?storeId=firstMsg'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'BRH_ID',mapping: 'BRH_ID'},
			{name: 'BRH_NAME',mapping: 'BRH_NAME'},
			{name: 'PPT_MSG',mapping: 'PPT_MSG'},
			{name: 'CRT_OPR',mapping: 'CRT_OPR'},
			{name: 'UPD_OPR',mapping: 'UPD_OPR'},
			{name: 'CRT_DATE',mapping: 'CRT_DATE'},
			{name: 'UPD_DATE',mapping: 'UPD_DATE'}
		]),
		autoLoad: true
	});
	//列模型
	var mainModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '机构编号',dataIndex: 'BRH_ID',width: 80},
			{header: '机构名称',dataIndex: 'BRH_NAME',width: 240},
    		{header: '首页提示信息',dataIndex: 'PPT_MSG',id: 'PPT_MSG',
				editor: new Ext.form.TextField({
    		 	allowBlank: false,
    			blankText: '首页提示信息不能为空',
    			emptyText: '请输入首页提示信息',
    			maxLength: 128,
				vtype: 'isOverMax'
    		 })},
			{header: '录入柜员',dataIndex: 'CRT_OPR'},
			{header: '录入时间',dataIndex: 'CRT_DATE'},
			{header: '修改柜员',dataIndex: 'UPD_OPR'},
			{header: '最近修改时间',dataIndex: 'UPD_DATE'}
    ]);
	
	//GRID
	var mainGrid = new Ext.grid.EditorGridPanel({
		title: '配置首页提示信息',
		iconCls: 'T10801',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'PPT_MSG',
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