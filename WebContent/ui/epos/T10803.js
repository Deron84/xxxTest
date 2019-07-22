Ext.onReady(function(){
	//EPOS交易版本号
    var eposStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboDataWithParameter('EPOS_VER','',function(ret){
		eposStore.loadData(Ext.decode(ret));
	});
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
			fieldLabel: '交易提示信息编号*',
			name: 'pptIdNew',
			id: 'pptIdNew'
		},{
			xtype: 'combo',
			fieldLabel: '信息类别*',
			hiddenName: 'msgTypeNew',
			id: 'msgTypeN',
			allowBlank: false,
			store: new Ext.data.ArrayStore({
                 fields: ['valueField','displayField'],
                 data: [['1','功能提示信息'],['2','操作提示信息'],['3','错误提示信息']]
            })
		},{
			xtype: 'combo',
			fieldLabel: '版本号*',
			allowBlank: false,
			store: eposStore,
			hiddenName: 'verIdNew',
			id: 'verIdN'
		},{
			fieldLabel: '提示信息格式1*',
			name: 'msgFmt1New',
			id: 'msgFmt1New',
			maxLength: 2
		},{
			fieldLabel: '提示信息1*',
			name: 'pptMsg1New',
			id: 'pptMsg1New',
			maxLength: 64
		},{
			fieldLabel: '提示信息格式2',
			name: 'msgFmt2New',
			id: 'msgFmt2New',
			maxLength: 2,
			allowBlank: true
		},{
			fieldLabel: '提示信息2',
			name: 'pptMsg2New',
			id: 'pptMsg2New',
			maxLength: 64,
			allowBlank: true
		},{
			fieldLabel: '提示信息格式3',
			name: 'msgFmt3New',
			id: 'msgFmt3New',
			maxLength: 2,
			allowBlank: true
		},{
			fieldLabel: '提示信息3',
			name: 'pptMsg3New',
			id: 'pptMsg3New',
			maxLength: 64,
			allowBlank: true
		}],
		buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = mainForm.getForm();
				if (frm.isValid()) {
						frm.submit({
							url: 'T30503Action_add.asp',
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
								txnId: '30503',
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
		title: '新增交易提示信息',
		iconCls: 'T30503',
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
			fieldLabel: '交易提示信息编号*',
			name: 'pptId',
			id: 'pptId',
			readOnly: true
		},{
			xtype: 'combo',
			fieldLabel: '信息类别*',
			hiddenName: 'msgType',
			id: 'msgTypeU',
			allowBlank: false,
			store: new Ext.data.ArrayStore({
                 fields: ['valueField','displayField'],
                 data: [['1','功能提示信息'],['2','操作提示信息'],['3','错误提示信息']]
            }),
			readOnly: true
		},{
			fieldLabel: '模板号*',
			name: 'tmpId',
			id: 'tmpId',
			maxLength: 2,
			readOnly: true
		},{
			fieldLabel: '版本号*',
			name: 'verId',
			id: 'verId',
			readOnly: true
		},{
			fieldLabel: '提示信息格式1*',
			name: 'msgFmt1',
			id: 'msgFmt1',
			maxLength: 2
		},{
			fieldLabel: '提示信息1*',
			name: 'pptMsg1',
			id: 'pptMsg1',
			maxLength: 64
		},{
			fieldLabel: '提示信息格式2',
			name: 'msgFmt2',
			id: 'msgFmt2',
			maxLength: 2,
			allowBlank: true
		},{
			fieldLabel: '提示信息2',
			name: 'pptMsg2',
			id: 'pptMsg2',
			maxLength: 64,
			allowBlank: true
		},{
			fieldLabel: '提示信息格式3',
			name: 'msgFmt3',
			id: 'msgFmt3',
			maxLength: 2,
			allowBlank: true
		},{
			fieldLabel: '提示信息3',
			name: 'pptMsg3',
			id: 'pptMsg3',
			maxLength: 64,
			allowBlank: true
		}],
		buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = updateForm.getForm();
				if (frm.isValid()) {
						frm.submit({
							url: 'T30503Action_update.asp',
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
								txnId: '30503',
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
		title: '交易提示信息',
		iconCls: 'T30503',
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
						url: 'T30503Action_del.asp',
						params: {
							usageKey: selectedRecord.get('usageKey'),
                            pptId: selectedRecord.get('pptId'),
                            msgType: selectedRecord.get('msgTypeU'),
                            verId: selectedRecord.get('verId'),
							txnId: '30503',
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
							mainStore.load();
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
			url: 'gridPanelStoreAction.asp?storeId=pptMsg'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'usageKey',mapping:  'USAGE_KEY'},   
			{name: 'pptId',mapping:'PPT_ID'},
			{name: 'msgTypeU',mapping: 'MSG_TYPE'},
			{name: 'verId',mapping: 'VER_ID'},
			{name: 'tmpId',mapping: 'TMP_ID'},
			{name: 'msgFmt1',mapping: 'MSG_FMT1'},
			{name: 'pptMsg1',mapping: 'PPT_MSG1'},
			{name: 'msgFmt2',mapping: 'MSG_FMT2'},
	      	{name: 'pptMsg2',mapping: 'PPT_MSG2'},
			{name: 'msgFmt3',mapping: 'MSG_FMT_3'},
			{name: 'pptMsg3',mapping: 'PPT_MSG3'}
			
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
			{header: '提示信息编号',dataIndex: 'pptId'},
			{header: '提示信息类型',dataIndex: 'msgTypeU',renderer: msgType},
			{header: '版本号',dataIndex: 'verId'},
			{header: '模板号',dataIndex: 'tmpId'},
    		{header: '提示信息格式1',dataIndex: 'msgFmt1'},
    		{header: '提示信息1',dataIndex: 'pptMsg1'},
    		{header: '提示信息格式2',dataIndex: 'msgFmt2'},
    		{header: '提示信息2',dataIndex: 'pptMsg2'},
    		{header: '提示信息格式3',dataIndex: 'msgFmt3'},
    		{header: '提示信息3',dataIndex: 'pptMsg3'}
    ]);
	
	//GRID
	var mainGrid = new Ext.grid.EditorGridPanel({
		title: '交易提示信息配置',
		iconCls: 'T30503',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
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