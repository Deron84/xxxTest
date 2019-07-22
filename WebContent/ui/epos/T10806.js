Ext.onReady(function(){
	
	//form
	var fm = Ext.form;
	//当前操作
	var curOp = '';
	
	/**编辑题目表单部分**/
	var QUESTION = Ext.data.Record.create([{
        name: 'option',
        type: 'string'
	},{
        name: 'point',
        type: 'string'
    }])
	
	var subStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getQuestionInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'option',mapping: 'option',type:'string'},
			{name: 'point',mapping: 'point',type:'string'}
		]),
		autoLoad: false
	});
	
	var subModel = new Ext.grid.ColumnModel({
		columns: [{
            header: '指令编号',
            dataIndex: 'option',
            width: 60,
            sortable: true,
            editor: new fm.TextField({
                    allowBlank: false
                })
        },{
            header: '提示信息',
            dataIndex: 'point',
            width: 60,
            sortable: true,
            editor: new fm.NumberField({
                    allowNegative: false,
                    maxValue: 100,
                    minValue: 0,
                    decimalPrecision: 0
                })
        },{
            header: '指令类型',
            dataIndex: 'option',
            width: 60,
            sortable: true,
            editor: new fm.TextField({
                    allowBlank: false
                })
        },{
            header: '加密方式',
            dataIndex: 'option',
            width: 60,
            sortable: true,
            editor: new fm.TextField({
                    allowBlank: false
                })
        },{
            header: '加密算法',
            dataIndex: 'option',
            width: 60,
            sortable: true,
            editor: new fm.TextField({
                    allowBlank: false
                })
        },{
            header: '是否校验',
            dataIndex: 'option',
            width: 60,
            sortable: true,
            editor: new fm.TextField({
                    allowBlank: false
                })
        },{
            header: '校验算法',
            dataIndex: 'option',
            width: 60,
            sortable: true,
            editor: new fm.TextField({
                    allowBlank: false
                })
        },{
            header: '描述',
            dataIndex: 'option',
            id: 'option2',
            width: 160,
            sortable: true,
            editor: new fm.TextField({
                    allowBlank: false
                })
        }]
	});
	var subGrid = new Ext.grid.EditorGridPanel({
		region: 'center',
        title: '交易原子指令集',
        store: subStore,
        cm: subModel,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        autoExpandColumn: 'option2', 
        frame: true,
        clicksToEdit: 1,
        tbar: [{
            text: '添加一行',
            iconCls: 'add_max',
            handler : function(){
                var p = new QUESTION();
                subGrid.stopEditing();
                subStore.insert(subStore.getCount(), p);
                subGrid.startEditing(subStore.getCount() - 1, 0);
            }
        },'-',{
            text: '删除一行',
            iconCls: 'del_min',
            handler : function(){
            	subGrid.stopEditing();
            	subStore.remove(subGrid.getSelectionModel().getSelected());
            }
        }]
    });
	//题目表单
	var subForm = new Ext.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		buttonAlign: 'center',
		items: [{
			xtype: 'panel',
			layout: 'form',
			defaults: {
				xtype: 'textnotnull',
				labelStyle: 'padding-left: 5px',
				anchor: '60%'
			},
			items: [{
				xtype: 'basecomboselect',
	        	baseParams: 'CUP_BRH_BELOW',
				fieldLabel: '机构编号*',
				allowBlank: false,
				hiddenName: 'brhId'
			},{
				fieldLabel: '交易码*',
				name: 'pptMsg',
				maxLength: 128,
				vtype: 'isOverMax'
			},{
				xtype: 'basecomboselect',
	        	baseParams: 'USAGE_KEY',
				fieldLabel: '状态*',
				allowBlank: false,
				hiddenName: 'usageKey'
			},{
				fieldLabel: '描述',
				name: 'pptMsg',
				maxLength: 128,
				vtype: 'isOverMax'
			}]
		},{
			xtype: 'panel',
			height: 280,
			layout: 'border',
			items: [subGrid]
		}],
		buttons: [{
			text: '保存',
			id: 'saveBt',
			handler: function(btn) {
				subGrid.stopEditing();
				Ext.getCmp('saveBt').disable();
				var frm = subForm.getForm();
				if(frm.isValid()) {
					subGrid.getStore().commitChanges();
					var dataArray = new Array();
					for(var i=0;i<subStore.getCount();i++){
						var re = subStore.getAt(i);
						subGrid.getSelectionModel().selectRow(i);
						
						if(re.data.option == null&&re.data.point == null){
							continue;
						}
						
						if(re.data.option == null){showMsg("第" + (i+1) + "行的[选项]不能为空",subGrid);return;}
						if(re.data.point == null){showMsg("第" + (i+1) + "行的[分数]不能为空",subGrid);return;}
                        
						if(Number(re.data.point)<0||Number(re.data.point)>100){
							showMsg("第" + (i+1) + "行的[分数]应为0-100间的数字(整数)",subGrid);return;
						}
						
						var data = {
							option: re.data.option,
							point: re.data.point
						};
						dataArray.push(data);
					}
					if(dataArray.length == 0){
						showMsg("题目应该至少包含一个选项",subGrid);
						return;
					}
					if(1!=1){
						
					}else{
						frm.submit({
							url: 'T70102Action_' + (curOp=='01'?'add':'update') + '.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								T70102.getCount(getCountCallback);
								mainStore.reload();
								subStore.removeAll();
								showSuccessMsg(action.result.msg,mainGrid);
								subWin.hide(mainGrid);
								frm.resetAll();
							},
							failure : function(form,action) {
								T70102.getCount(getCountCallback);
								Ext.getCmp('saveBt').enable();
								showErrorMsg(action.result.msg,mainGrid);
							},
							params: {
								txnId: '70102',
								subTxnId: curOp,
								data: Ext.encode(dataArray)
							}
						});
					}
				}
			}
			},{
			text: '取消',
			handler: function() {
				subWin.hide(mainGrid);
				subForm.getForm().resetAll();
				subStore.removeAll();
			}
		}]
	});
	
	
	var subWin = new Ext.Window({
		title: '终端交易详细定义',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 670,
		autoHeight: true,
		items: [subForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false
	})
	
	
	
	
	
	
	
	
	
	
	
	//新增
	function add(){
		subWin.show();
	}
	
	
	
	//数据集
	var mainStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=txnCfgDsp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'BRH_ID',mapping: 'BRH_ID'},	
			{name: 'BRH_NAME',mapping: 'BRH_NAME'},	
			{name: 'TERM_TXN_CODE',mapping: 'TERM_TXN_CODE'},
			{name: 'USAGE_KEY',mapping: 'USAGE_KEY'},
			{name: 'DSP',mapping: 'DSP'}
		]),
		autoLoad: true
	});

	
	//数据集
	var detailStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=PaperHisInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'CMD_ID',mapping: 'CMD_ID'},	
			{name: 'CMD_DSP',mapping: 'CMD_DSP'},	
			{name: 'USAGE_KEY',mapping: 'USAGE_KEY'},	
			{name: 'PPT_DSP',mapping: 'PPT_DSP'},	
			{name: 'CMD_TYPE',mapping: 'CMD_TYPE'},	
			{name: 'ENC_MOD0',mapping: 'ENC_MOD0'},	
			{name: 'ENC_MOD1',mapping: 'ENC_MOD1'},	
			{name: 'ENC_MOD4',mapping: 'ENC_MOD4'},	
			{name: 'ENC_MOD5',mapping: 'ENC_MOD5'},
			{name: 'DSP',mapping: 'DSP'}
		])
	});
	
	//列模型
	var mainModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '机构编号',dataIndex: 'BRH_ID',width: 60},
			{header: '机构名称',dataIndex: 'BRH_NAME',width: 80},
    		{header: '交易码',dataIndex: 'TERM_TXN_CODE',width: 60},
    		{header: '状态',dataIndex: 'USAGE_KEY',width: 60,renderer:usageKey},
    		{header: '描述',dataIndex: 'DSP',id: 'DSP',width: 60}
    ]);
	
    	
    //列模型
	var detailModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '指令编号',dataIndex: 'CMD_ID',width: 60},
			{header: '指令名称',dataIndex: 'CMD_DSP',width: 60},
//			{header: '指令状态',dataIndex: 'USAGE_KEY',width: 60},
			{header: '提示信息',dataIndex: 'PPT_DSP',width: 60},
			{header: '指令类型',dataIndex: 'CMD_TYPE',width: 60},
			{header: '加密方式',dataIndex: 'ENC_MOD0',width: 60},
			{header: '加密算法',dataIndex: 'ENC_MOD1',width: 60},
			{header: '是否校验',dataIndex: 'ENC_MOD4',width: 60},
			{header: '校验算法',dataIndex: 'ENC_MOD5',width: 60},
    		{header: '描述',dataIndex: 'DSP',id: 'DSP'}
    ]);
	
	
	//GRID
	var mainGrid = new Ext.grid.GridPanel({
		title: '终端交易详细定义',
		iconCls: 'T30506',
		region: 'west',
		width: 400,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'DSP',
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
		},{
			xtype: 'button',
			text: '修改',
			name: 'update',
			id: 'update',
			iconCls: 'edit',
			width: 75,
			handler:function() {
				update();
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
	mainGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mainGrid.getSelectionModel().getSelected().data.PAPER_ID;
		detailStore.load({
			params: {
				start: 0,
				PAPER_ID: mainGrid.getSelectionModel().getSelected().data.BRH_ID,
				TERM_TXN_CODE: mainGrid.getSelectionModel().getSelected().data.TERM_TXN_CODE
				}
			});
	});
	
	
	//GRID
	var detailGrid = new Ext.grid.GridPanel({
		title: '交易原子指令集',
		iconCls: 'application_16x16',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: detailStore,
		cm: detailModel,
		disableSelection: true,
		autoExpandColumn: 'DSP',
		tbar: [{
//			xtype: 'button',
//			text: '生成PDF',
//			name: 'pdf',
//			id: 'pdf',
//			iconCls: 'script_16x16',
//			width: 75,
//			handler:function() {
//				getPDF();
//			}
		}],
		loadMask: {	
			msg: '正在加载信息列表，请稍候......'
		},
		bbar: new Ext.PagingToolbar({
			store: detailStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
		
	});
	
	//Main View
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mainGrid,detailGrid],
		renderTo: Ext.getBody()
	});
	
	/*
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
			fieldLabel: '渠道号*',
			name: 'chgNo',
			maxLength: '4',
			regex: /^[0-9]+$/,
			regexText: '该输入框只能输入数字',
			maskRe: /^[0-9]+$/
		},{
			fieldLabel: '错误码*',
			name: 'errNo',
			maxLength: '2',
			regex: /^[0-9]+$/,
			regexText: '该输入框只能输入数字',
			maskRe: /^[0-9]+$/
		},{
			fieldLabel: '错误码描述*',
			name: 'errMsg',
			maxLength: '64',
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
							url: 'T30507Action_add.asp',
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
								txnId: '30507',
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
					url: 'T30507Action_delete.asp',
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
						errNo:sel.data.ERR_NO,
						txnId: '30507',
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
						errNo: record.get('ERR_NO'),
						errMsg: record.get('ERR_MSG')
					};
					array.push(data);
				}
				Ext.Ajax.requestNeedAuthorise({
					url: 'T30507Action_update.asp',
					method: 'post',
					params: {
						dataList : Ext.encode(array),
						txnId: '30507',
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
			{header: '渠道号',dataIndex: 'CHG_NO',width: 120},
			{header: '错误码',dataIndex: 'ERR_NO',width: 120},
    		{header: '错误码描述',dataIndex: 'ERR_MSG',id: 'ERR_MSG',
				editor: new Ext.form.TextField({
    		 	allowBlank: false,
    			blankText: '错误码描述不能为空',
    			emptyText: '请输入错误码描述',
    			maxLength: '64',
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
	
	*/
});