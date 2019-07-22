Ext.onReady(function(){

	
	var curOp = "01";
	var curIndex = "";
	
	var mainForm = new Ext.form.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 230,
		waitMsgTarget: true,
		labelAlign: 'center',
		defaults: {
			xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			anchor: '90%',
			regex: /^[0-9A-F]+$/,
			regexText: '该输入框只能输入字符0-9或A-F',
			maskRe: /^[0-9A-F]+$/
		},
		items: [{
			fieldLabel: '终端支持的借/贷记应用列表（AID）',
			name: 'TAG1',
			maxLength: 32,
			minLength: 10
		},{
			fieldLabel: '应用选择指示符（ASI）',
			name: 'TAG2',
			maxLength: 2,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  2'
		},{
			fieldLabel: '应用版本号',
			name: 'TAG3',
			maxLength: 4,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  4'
		},{
			fieldLabel: 'TAC－缺省',
			name: 'TAG4',
			maxLength: 10,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度 10'
		},{
			fieldLabel: 'TAC－联机',
			name: 'TAG5',
			maxLength: 10,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  10'
		},{
			fieldLabel: 'TAC－拒绝',
			name: 'TAG6',
			maxLength: 10,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  10'
		},{
			fieldLabel: '终端最低限额',
			name: 'TAG7',
			maxLength: 8,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  8'
		},{
			fieldLabel: '偏置随机选择的阈值',
			name: 'TAG8',
			maxLength: 8,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  8'
		},{
			fieldLabel: '偏置随机选择的最大目标百分数',
			name: 'TAG9',
			maxLength: 2,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  2'
		},{
			fieldLabel: '随机选择的目标百分数',
			name: 'TAG10',
			maxLength: 2,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  2'
		},{
			fieldLabel: '缺省DDOL',
			name: 'TAG11',
			maxLength: 198,
			maxLengthText:'该输入项最长198'
		},{
			fieldLabel: '终端联机PIN 支持能力',
			name: 'TAG12',
			maxLength: 2,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  2'
		},{
			fieldLabel: '终端电子现金交易限额',
			name: 'TAG13',
			maxLength: 12,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  12'
		},{
			fieldLabel: '非接触读写器脱机最低限额',
			name: 'TAG14',
			maxLength: 12,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度 12'
		},{
			fieldLabel: '非接触读写器交易限额',
			name: 'TAG15',
			maxLength: 12,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  12'
		},{
			fieldLabel: '读写器持卡人验证方法（CVM）所需限制',
			name: 'TAG16',
			maxLength: 12,
			vtype: 'isFixLength',
			vtypeText: '该输入项内容应为固定长度  12'
		}],
		buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = mainForm.getForm();
				if (frm.isValid()) {
					
					var TAG1 = mainForm.getForm().findField('TAG1').getValue();
					var TAG11 = mainForm.getForm().findField('TAG11').getValue();
					
					if(Number(TAG1.length) % 2){
						showErrorMsg('参数[终端支持的借/贷记应用列表（AID）]的长度应为偶数',mainForm);
						return;
					}
					if(Number(TAG11.length) % 2){
						showErrorMsg('参数[缺省DDOL]的长度应为偶数',mainForm);
						return;
					}
					
					if(1 != 1){
					}else{
						frm.submit({
							url: 'T10208Action.asp?method=' + (curOp == '01'?'add':'update'),
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
								txnId: '10208',
								subTxnId: curOp,
								index: curIndex
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
	})
	
	var mainWin = new Ext.Window({
		title: 'IC卡其他参数维护',
		iconCls: 'T10208',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 670,
		autoHeight: true,
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		items: [mainForm]
	});
	//functions
	function add(){
		curOp = '01';
		mainWin.show(mainGrid);
	};
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getIcParamInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data'
		},[
			{name: 'TAG1',mapping: 'TAG1'},
			{name: 'TAG2',mapping: 'TAG2'},
			{name: 'TAG3',mapping: 'TAG3'},
			{name: 'TAG4',mapping: 'TAG4'},
			{name: 'TAG5',mapping: 'TAG5'},
			{name: 'TAG6',mapping: 'TAG6'},
			{name: 'TAG7',mapping: 'TAG7'},
			{name: 'TAG8',mapping: 'TAG8'},
			{name: 'TAG9',mapping: 'TAG9'},
			{name: 'TAG10',mapping: 'TAG10'},
			{name: 'TAG11',mapping: 'TAG11'},
			{name: 'TAG12',mapping: 'TAG12'},
			{name: 'TAG13',mapping: 'TAG13'},
			{name: 'TAG14',mapping: 'TAG14'},
			{name: 'TAG15',mapping: 'TAG15'},
			{name: 'TAG16',mapping: 'TAG16'}
		]),
		autoLoad: false
	});

	function update(){
		var sel = mainGrid.getSelectionModel().getSelected();
		if(sel == null){
			showMsg("请选择一条记录后再进行操作。",mainGrid);return;
		}
		baseStore.load({
			params: {
				index: sel.data.PARA_IDX
			},
			callback: function(records, options, success){
				if(success){
					curOp = '02';
					mainForm.getForm().loadRecord(baseStore.getAt(0));
					mainForm.getForm().clearInvalid();
					mainWin.show();
					curIndex = sel.data.PARA_IDX;
				}else{
					showErrorMsg("加载信息失败，请刷新并检查数据后重试",mainGrid);
				}
			}
		});
	
	}
	
	function del(){
		var sel = mainGrid.getSelectionModel().getSelected();
		if(sel == null){
			showMsg("请选择一条记录后再进行操作。",mainGrid);return;
		}
		showConfirm('确定要删除该条信息吗？',mainGrid,function(bt) {
		
			if(bt == "yes") {
				Ext.Ajax.request({
				url: 'T10208Action.asp?method=delete',
				success: function(rsp,opt) {
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						mainStore.reload();
						showSuccessAlert(rspObj.msg,mainForm);
					} else {
						showErrorMsg(rspObj.msg,detailGrid);
					}
				},
				failure: function(){
					hideMask();
				},
				params: { 
					index: sel.data.PARA_IDX,
					txnId: '10208',
					subTxnId: '03'
				}
			});
				
		}
		})
	};
	
	function detail(){
		var sel = mainGrid.getSelectionModel().getSelected();
		if(sel == null){
			showMsg("请选择一条记录后再进行操作。",mainGrid);return;
		}
		showDetail(sel.data.PARA_IDX,mainGrid);
	}
	
	//数据集
	var mainStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=icParam'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'USAGE_KEY',mapping: 'USAGE_KEY'},	
			{name: 'PARA_IDX',mapping: 'PARA_IDX'},
			{name: 'PARA_ID',mapping: 'PARA_ID'},
			{name: 'PARA_LEN',mapping: 'PARA_LEN'},
			{name: 'PARA_VAL',mapping: 'PARA_VAL'},
			{name: 'REC_UPD_OPR',mapping: 'REC_UPD_OPR'},
			{name: 'REC_CRT_TS',mapping: 'REC_CRT_TS'}
		]),
		autoLoad: true
	});
	
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<table width="85%"  style="word-break:break-all; word-wrap:break-all;"><tr><td><font color="green">参数值</font></td><tr><tr><td>{PARA_VAL}</td></tr></table>'
		)
	});
	
	//列模型
	var mainModel = new Ext.grid.ColumnModel([
		rowExpander,
		{header: '参数序号',dataIndex: 'PARA_IDX',width: 120},
		{header: '参数编号',dataIndex: 'PARA_ID',width: 160,id: 'PARA_ID'},
    	{header: '参数长度',dataIndex: 'PARA_LEN',width: 140},
    	{header: '创建柜员',dataIndex: 'REC_UPD_OPR',width: 120},
    	{header: '创建时间',dataIndex: 'REC_CRT_TS',width: 190}
    ]);
	
	
	//GRID
	var mainGrid = new Ext.grid.GridPanel({
		title: 'IC卡其他参数维护',
		iconCls: 'T10208',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'PARA_ID',
		forceValidation: true,
		plugins: rowExpander,
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
			name: 'update',
			id: 'update',
			iconCls: 'edit',
			width: 75,
			handler:function() {
				update();
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
		},'-',{
			xtype: 'button',
			text: '查看详细信息',
			name: 'detail',
			id: 'detail',
			iconCls: 'detail',
			width: 85,
			handler:function() {
				detail();
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
	
	
	//Main View
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mainGrid],
		renderTo: Ext.getBody()
	})
})




function showDetail(id,El){
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getIcParamInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data'
		},[
			{name: 'TAG1',mapping: 'TAG1'},
			{name: 'TAG2',mapping: 'TAG2'},
			{name: 'TAG3',mapping: 'TAG3'},
			{name: 'TAG4',mapping: 'TAG4'},
			{name: 'TAG5',mapping: 'TAG5'},
			{name: 'TAG6',mapping: 'TAG6'},
			{name: 'TAG7',mapping: 'TAG7'},
			{name: 'TAG8',mapping: 'TAG8'},
			{name: 'TAG9',mapping: 'TAG9'},
			{name: 'TAG10',mapping: 'TAG10'},
			{name: 'TAG11',mapping: 'TAG11'},
			{name: 'TAG12',mapping: 'TAG12'},
			{name: 'TAG13',mapping: 'TAG13'},
			{name: 'TAG14',mapping: 'TAG14'},
			{name: 'TAG15',mapping: 'TAG15'},
			{name: 'TAG16',mapping: 'TAG16'}
		]),
		autoLoad: false
	});
	
	baseStore.load({
			params: {
				index: id
			},
			callback: function(records, options, success){
				if(success){
					curOp = '02';
					mainForm.getForm().loadRecord(baseStore.getAt(0));
					mainWin.show(El);
				}else{
					showErrorMsg("加载信息失败，请刷新数据后重试",El);
				}
			}
		});
	var mainForm = new Ext.form.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 230,
		waitMsgTarget: true,
		labelAlign: 'center',
		defaults: {
			xtype: 'displayfield',
			labelStyle: 'padding-left: 5px',
			anchor: '90%'
		},
		items: [{
			fieldLabel: '终端支持的借/贷记应用列表（AID）',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG1'
		},{
			fieldLabel: '应用选择指示符（ASI）',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG2'
		},{
			fieldLabel: '应用版本号',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG3'
		},{
			fieldLabel: 'TAC－缺省',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG4'
		},{
			fieldLabel: 'TAC－联机',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG5'
		},{
			fieldLabel: 'TAC－拒绝',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG6'
		},{
			fieldLabel: '终端最低限额',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG7'
		},{
			fieldLabel: '偏置随机选择的阈值',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG8'
		},{
			fieldLabel: '偏置随机选择的最大目标百分数',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG9'
		},{
			fieldLabel: '随机选择的目标百分数',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG10'
		},{
			fieldLabel: '缺省DDOL',
			xtype:'textarea',
			readOnly:true,
			name: 'TAG11'
		},{
			fieldLabel: '终端联机PIN 支持能力',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG12'
		},{
			fieldLabel: '终端电子现金交易限额',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG13'
		},{
			fieldLabel: '非接触读写器脱机最低限额',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG14'
		},{
			fieldLabel: '非接触读写器交易限额',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG15'
		},{
			fieldLabel: '读写器持卡人验证方法（CVM）所需限制',
			xtype:'textfield',
			readOnly:true,
			name: 'TAG16'
		}]
	})
	
	var mainWin = new Ext.Window({
		title: 'IC卡其他参数详细信息',
		iconCls: 'detail',
		initHidden: true,
		header: true,
		frame: true,
		closable: true,
		modal: true,
		width: 670,
		autoHeight: true,
		buttonAlign: 'center',
		resizable: false,
		items: [mainForm]
	});
}