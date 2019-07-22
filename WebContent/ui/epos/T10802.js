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
			fieldLabel: '终端交易码*',
			name: 'termTxnCodeNew',
			id: 'termTxnCodeNew',
			maxLength: 4
		},{
			fieldLabel: '内部交易码*',
			name: 'intTxnCodeNew',
			id: 'intTxnCodeNew',
			maxLength: 4
		},{
			fieldLabel: '描述*',
			name: 'dspNew',
			id: 'dspNew',
			maxLength: 30
		}],
		buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = mainForm.getForm();
				if (frm.isValid()) {
						frm.submit({
							url: 'T10802Action_add.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								showSuccessAlert(action.result.msg,mainForm);
								mainWin.hide(mainGrid);
								mainStore.load();
								frm.resetAll();
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,mainForm);
							},
							params: {
								txnId: '10803',
								subTxnId: '01'
							}
					});
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
		title: '新增终端交易映射信息',
		iconCls: 'T30502',
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
	var updateForm = new Ext.form.FormPanel({
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
			fieldLabel: '终端交易码*',
			name: 'termTxnCode',
			id: 'termTxnCode',
			maxLength: 4
		},{
			fieldLabel: '内部交易码*',
			name: 'intTxnCode',
			id: 'intTxnCode',
			maxLength: 4
		},{
			fieldLabel: '描述*',
			name: 'dsp',
			id: 'dsp',
			maxLength: 30
		}],
		buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = updateForm.getForm();
				if (frm.isValid()) {
						frm.submit({
							url: 'T10802Action_update.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								showSuccessMsg(action.result.msg,updateForm);
								updateWin.hide(mainGrid);
								mainStore.load();
								frm.resetAll();
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,updateForm);
							},
							params: {
								txnId: '10802',
								subTxnId: '02'
							}
					});
			}}
		},{
            text: '关闭',
            handler: function() {
				updateWin.hide(mainGrid);
				updateForm.getForm().resetAll();
			}
        }]
	});
	
	var updateWin = new Ext.Window({
		title: '终端交易映射信息',
		iconCls: 'T30502',
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
		items: [updateForm]
	});
	//functions
	function add(){
		mainWin.show(mainGrid);
	};
	function del(){
		showConfirm('确认删除吗？',mainGrid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交信息，请稍后......');
					var selectedRecord = mainGrid.getSelectionModel().getSelected();
			        if(selectedRecord == null)
			        {
			        	showAlertMsg("没有选择记录",mainGrid);
			            return;
			        }
			        Ext.Ajax.requestNeedAuthorise({
						url: 'T10802Action_del.asp',
						params: {
							termTxnCode: selectedRecord.get('termTxnCode'),
							txnId: '10802',
							subTxnId: '03'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,mainGrid);
							} else {
								showErrorMsg(rspObj.msg,mainGrid);
							}
							// 重新加载终端待审核信息
							mainGrid.getStore().reload();
						}
					});
					hideProcessMsg();
	                mainGrid.getTopToolbar().items.items[2].disable();
	                mainGrid.getTopToolbar().items.items[4].disable();
			   }
		});
	}
	function edit(){
        var selectedRecord = mainGrid.getSelectionModel().getSelected();
        if(selectedRecord == null)
        {
        	showAlertMsg("没有选择记录",mainGrid);
            return;
        }    
        updateForm.getForm().loadRecord(selectedRecord);
		updateWin.show();
		updateWin.center();
	}
	
	//数据集
	var mainStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termTxnCode'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termTxnCode',mapping:  'TERM_TXN_CODE'},   
			{name: 'intTxnCode',mapping: 'INT_TXN_CODE'},
			{name: 'dsp',mapping: 'DSP'}
			
		]),
		autoLoad: true
	});
	
	mainStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0
        });
    }); 
	//列模型
	var mainModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '终端交易码',dataIndex: 'termTxnCode'},
			{header: '内部交易码',dataIndex: 'intTxnCode'},
			{header: '信息描述',dataIndex: 'dsp',id:'dsp',name:'dsp'}
    ]);
	
	//GRID
	var mainGrid = new Ext.grid.EditorGridPanel({
		title: '终端交易映射信息',
		iconCls: 'T30502',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
		autoExpandColumn: 'dsp',
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
			text: '修改',
			name: 'edit',
			id: 'edit',
			iconCls: 'edit',
			width: 75,
			disabled: true,
			handler:function() {
				edit();
			}
		},'-',{
			xtype: 'button',
			text: '删除',
			name: 'delete',
			id: 'delete',
			width: 75,
			iconCls: 'delete',
			disabled: true,
			handler: function() {
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
	
	mainGrid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(mainGrid.getView().getRow(mainGrid.getSelectionModel().last)).frame();
			var rec = mainGrid.getSelectionModel().getSelected();
			if(rec != null){
				mainGrid.getTopToolbar().items.items[2].enable();
				mainGrid.getTopToolbar().items.items[4].enable();
			}
			else{
				mainGrid.getTopToolbar().items.items[2].disable();
				mainGrid.getTopToolbar().items.items[4].disable();				
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