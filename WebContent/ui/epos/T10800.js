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
			xtype: 'textfield',
			fieldLabel: '版本编号*',
			name: 'verId',
			maxLength: 4,
//			vtype: 'isOverMax'
			regex: /^[0-9a-zA-Z]{4}$/,
			regexText: '授权码必须是4位数字或字母',
		},{
			xtype: 'textfield',
			fieldLabel: '版本名称',
			name: 'misc',
			maxLength: 255,
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
							url: 'T10800Action_add.asp',
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
								txnId: '10800',
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
		title: '新增终端版本管理',
		iconCls: 'T30500',
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
					url: 'T10800Action_del.asp',
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
						brhId: sel.data.bankId,
						verId: sel.data.verId,
						txnId: '10800',
						subTxnId: '03'
					}
				});
			}
		});
	}
	
	//数据集
	var mainStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=verMng'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'bankId',mapping: 'BANK_ID'},
			{name: 'brhName',mapping: 'BRH_NAME'},
			{name: 'verId',mapping: 'VER_ID'},
			{name: 'misc',mapping: 'MISC'},
			{name: 'crtDate',mapping: 'CRT_DATE'},
			{name: 'updDate',mapping: 'UPD_DATE'}
		]),
		autoLoad: true
	});
	
	//列模型
	var mainModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '机构编号',dataIndex: 'bankId',width: 80},
			{header: '机构名称',dataIndex: 'brhName',width: 240},
    		{header: '版本编号',dataIndex: 'verId',width: 80},
    		{header: '版本名称',dataIndex: 'misc',id:'misc'},
    		{header: '创建时间',dataIndex: 'crtDate',width: 120},
   		 	{header: '更新时间',dataIndex: 'updDate',width: 120}
    ]);
	
	//GRID
	var mainGrid = new Ext.grid.EditorGridPanel({
		title: '终端版本管理',
		iconCls: 'T30500',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
		autoExpandColumn: 'misc',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
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
		}],
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
			if(mainGrid.getTopToolbar().items.items[2] != undefined) {
				mainGrid.getTopToolbar().items.items[2].enable();
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