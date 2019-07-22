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
			fieldLabel: '银联机构编号*',
			name: 'cupIdNew',
			id: 'cupIdNew',
			maxLength: 8
		},{
			fieldLabel: '人行机构编号*',
			name: 'bcIdNew',
			id: 'bcIdNew',
			maxLength: 12
		},{
			fieldLabel: '银行名称*',
			name: 'bankNameNew',
			id: 'bankNameNew',
			maxLength: 40
		}],
		buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = mainForm.getForm();
				if (frm.isValid()) {
						frm.submit({
							url: 'T30508Action_add.asp',
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
								txnId: '30508',
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
		title: '新增银联人行映射信息',
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
						url: 'T30508Action_del.asp',
						params: {
							cupId: selectedRecord.get('cupId'),
							txnId: '30508',
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
	                mainGrid.getTopToolbar().items.items[4].disable();
			   }
		});
	}
	function save(){

	}
	
	//数据集
	var mainStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=cupBcMap'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'cupId',mapping:  'CUP_ID'},   
			{name: 'bcId',mapping: 'BC_ID'},
			{name: 'bankName',mapping: 'BANK_NAME'}
			
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
			{header: '银联机构编号',dataIndex: 'cupId'},
			{header: '人行机构编号',dataIndex: 'bcId'},
			{header: '银行名称',dataIndex: 'bankName',
				editor: new Ext.form.TextField({
    		 	allowBlank: false,
    			blankText: '描述不能为空',
    			emptyText: '请输入描述',
    			maxLength: 60,
				vtype: 'isOverMax'
    		 })}
    ]);
	
	//GRID
	var mainGrid = new Ext.grid.EditorGridPanel({
		title: '银联人行映射信息',
		iconCls: 'T30508',
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
			text: '保存',
			name: 'save',
			id: 'save',
			iconCls: 'reload',
			width: 75,
			handler:function() {
				var modifiedRecords = mainGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				var store = mainGrid.getStore();
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
							cupId : record.get('cupId'),
							bcId: record.get('bcId'),
							bankName: record.get('bankName')
					};
					array.push(data);
				}
				Ext.Ajax.requestNeedAuthorise({
					url: 'T30508Action_update.asp',
					method: 'post',
					params: {
						dataList : Ext.encode(array),
						txnId: '30508',
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
				mainGrid.getTopToolbar().items.items[2].disable();
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
				mainGrid.getTopToolbar().items.items[4].enable();
			}
			else{
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