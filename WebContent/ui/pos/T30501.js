Ext.onReady(function() {
	
	// 终端菜品映射
	var vegeStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=vegMappingInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchntNo',mapping: 'MCHNT_NO'},
			{name: 'termNo',mapping: 'TERM_NO'},
			{name: 'keyCode',mapping: 'KEY_CODE'},
			{name: 'vegeCode',mapping: 'VEGE_CODE'},
			{name: 'vegeName',mapping: 'VEGE_NAME'}
		]),
		autoLoad :true
	}); 
	
	var vegeColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
 		{header: '商户号',dataIndex: 'mchntNo',width: 175,align:'center'},
 		{header: '终端号',dataIndex: 'termNo',width: 175,align:'center'},
 		{header: '按键号',dataIndex: 'keyCode',width: 175,align:'center'},
 		{header: '菜品编码',dataIndex: 'vegeCode',width: 175,align:'center',
 			editor: new Ext.form.TextField({
			 	allowBlank: false,
				blankText: '菜品编码不能为空',
				emptyText: '请输入菜品编码',
				maxLength: 32,
				maxLengthText: '菜品名称最多可以输入32个数字'
		 })},
 		{header: '菜品名称',dataIndex: 'vegeName',width: 175,align:'center'}
 	]);
	// 菜品编码
	var menuLvl1Store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=vegCodeInfo2'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'vegeCode',mapping: 'VEGE_CODE'},
			{name: 'vegeName',mapping: 'VEGE_NAME'}
		])
	});
	
	var menuLvl1Col = new Ext.grid.ColumnModel([
	 //   new Ext.grid.RowNumberer(), 
	    new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		{header: '<center>菜单编号</center>',dataIndex: 'vegeCode', width: 170,menuDisabled: true,resizable: false,align: 'center',
	    	editor: new Ext.form.TextField({
			 	allowBlank: false,
			 	readOnly: true
	    	})},   //hidden: true,
		{header: '<center>菜品名称</center>',dataIndex: 'vegeName',width: 170,menuDisabled: true,resizable: false,align: 'center'}
	]);
	
	var menuLvl1Grid = new Ext.grid.EditorGridPanel({
		store: menuLvl1Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		height: 250,
		width: 400,
		cm: menuLvl1Col,
//		keys: [{
//			key: Ext.EventObject.RIGHT,
//			handler: function() {
//				changeFocus(menuLvl1Grid,menuLvl2Grid);
//			}
//		}],
		lastSelectedRowIndex: -1
	});
	
	//机构添加表单
	var vegInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		border:true,
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '商户号*',
			xtype: 'textfield',
			allowBlank: false,
			emptyText: '请输入商户号',
			id: 'mchntNo',
			name: 'mchntNo',
			width: 300,
			maxLength: 15,
			maxLengthText: '商户号最多可以输入15个数字',
			vtype: 'isNumber',
			regex:/^[0-9]{15}$/,
    		regexText:'只能输入15位数字',
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '终端号*',
			xtype: 'textfield',
			allowBlank: false,
			emptyText: '请输入菜品名称',
			id: 'termNo',
			name: 'termNo',
			width: 300,
			maxLength: 8,
			regex:/^[a-zA-Z0-9]{8}$/,
			regexText:'只能输入8位数字或字母',
			maxLengthText: '终端号最多可以输入8个数字或字母',
			blankText: '该输入项只能包含数字或字母'
		},{
			fieldLabel: '按键号*',
			xtype: 'textfield',
			allowBlank: false,
			emptyText: '请输入要设置的按键号',
			id: 'keyCode',
			name: 'keyCode',
			width: 300,
			maxLength: 32,
			vtype: 'isNumber',
			maxLengthText: '按键号最多可以输入32个数字',
			blankText: '该输入项只能包含数字'
		},{
			layout:'column', 
			width:750,
            items:[{
            	columnWidth:0.45,
                layout: 'form',
                items: [{
                	xtype:'textfield',
        			fieldLabel: '菜品编码',
        	//		allowBlank: false,
        			id: 'vegeCode3',
          			name: 'vegeCode3',
          			width: 240,
        			emptyText: '请输入菜品编码,查询对应菜品',
        			maxLength: 32,
        			maxLengthText: '菜品编码号最多可以输入32个数字',
        			blankText: '该输入项只能包含数字'
                }]
            },
            {
            	columnWidth:0.55,
                layout: 'form',
                items: [
                  new Ext.Button({
                     text:'模糊匹配',
                     handler:function(){
                    	 menuLvl1Grid.getStore().reload({
                    		 params:{
                    			 start: 0,
                    			 vegCode: vegInfoForm.getForm().findField('vegeCode3').getValue()
                    		 }
                    	 });
                     }        
                  })
                ]
            }]
		},new Ext.Panel({
			title: '<center>请选择匹配的菜品编码(必选)</center>',
			layout: 'table',
			items: [menuLvl1Grid]
		})]
	});
	
	// 添加菜品编码窗口
	var vegWin = new Ext.Window({
		title: '菜品映射关系添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [vegInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(vegInfoForm.getForm().isValid()) {
					var submitValues = vegInfoForm.getForm().getValues();  
					for (var param in submitValues) {  
						if (vegInfoForm.getForm().findField(param) && vegInfoForm.getForm().findField(param).emptyText == submitValues[param]) {  
							vegInfoForm.getForm().findField(param).setValue(' ');  
						}  
					}
					var mchntNo= vegInfoForm.getForm().findField('mchntNo').getValue();
	            	if(mchntNo =='' || mchntNo == null){
	            		showErrorMsg("商户号不能为空",grid);
	    				return;
	            	}
	            	var termNo= vegInfoForm.getForm().findField('termNo').getValue();
	            	if(termNo =='' || termNo == null){
	            		showErrorMsg("终端号不能为空",grid);
	    				return;
	            	}
	            	var keyCode= vegInfoForm.getForm().findField('keyCode').getValue();
	            	if(keyCode =='' || keyCode ==null){
	            		showErrorMsg("按键号不能为空",grid);
	    				return;
	            	}
					var rec = menuLvl1Grid.getSelectionModel().getSelected();
					var _vageCode = rec.get('vegeCode');
					vegInfoForm.getForm().submit({
						url: 'T30501Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,vegInfoForm);
							vegInfoForm.getForm().reset();
							grid.getStore().reload();
//							SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
//								upBrhStore.loadData(Ext.decode(ret));
//							});
							vegWin.hide(grid);
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,vegInfoForm);
						},
						params: {
							vegeCode: _vageCode,
							txnId: '30501',
							subTxnId: '00'
						}
					});
				}
				
			}
		},{
			text: '重置',
			handler: function() {
				vegInfoForm.getForm().reset();
				menuLvl1Store.removeAll();
			}
		},{
			text: '关闭',
			handler: function() {
				menuLvl1Store.removeAll();
				vegInfoForm.getForm().reset();
				vegWin.hide(grid);
			}
		}]
	});
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			vegWin.show();
			vegWin.center();
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
				var _mchntNo = rec.get('mchntNo');
				var _termNo = rec.get('termNo');
				var _keyCode = rec.get('keyCode');
				showConfirm('确定要删除该机构吗？商户号：' + _mchntNo+',终端号：'+_termNo+',按键号：'+_keyCode,grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T30501Action.asp?method=delete',
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
								mchntNo: _mchntNo,
								termNo: _termNo,
								keyCode: _keyCode,
								txnId: '30501',
								subTxnId: '01'
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
			var store = grid.getStore();
			var len = store.getCount();
			for(var i = 0; i < len; i++) {
				var record = store.getAt(i);
				
				if(record.get('vegeCode')==""){
					showAlertMsg('菜品编码不能为空',grid);
					grid.getSelectionModel().selectRow(i);
					return false;
				}
			}
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				//校验机构名称
				var name=record.get('vegeCode');
				var reg=/^\S+$/;
				if(!reg.test(name)){
					showAlertMsg('菜品编码不能包含空字符',grid);
					return false;
				}
				var data = {
					mchntNo : record.get('mchntNo'),
					termNo: record.get('termNo'),
					keyCode : record.get('keyCode'),
					vegeCode: record.get('vegeCode'),
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T30501Action.asp?method=update',
				method: 'post',
				params: {
					vegDataList : Ext.encode(array),
					txnId: '30501',
					subTxnId: '02'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					grid.enable();
					if(rspObj.success) {
						grid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,grid);
						grid.getTopToolbar().items.items[4].disable();
						grid.getStore().reload();
					} else {
			//			grid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,grid);
					}
				
			//		hideProcessMsg();
				}
			});
		}
	}
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '商户号',
	//		allowBlank: false,
			emptyText: '请输入商户号',
			id: 'mchntNo2',
			name: 'mchntNo',
			width: 300,
			maxLength: 15,
			maxLengthText: '商户号最多可以输入15个数字',
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '终端号',
	//		allowBlank: false,
			emptyText: '请输入设备终端号',
			id: 'termNo2',
			name: 'termNo',
			width: 300,
			maxLength: 8,
			blankText: '该输入项只能包含数字或字母'
		},{
			fieldLabel: '菜品编号',
	//		allowBlank: false,
			emptyText: '请输入菜品编码',
			id: 'vegeCode2',
			name: 'vegeCode',
			width: 300,
			maxLength: 32,
			maxLengthText: '菜品编码最多可以输入32个数字',
			vtype: 'isNumber',
			blankText: '该输入想只能包含数字'
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
		autoHeight: true,
		items: [queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				queryWin.collapse();
				queryWin.getEl().pause(1);
				queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryWin.expand();
				queryWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {			
				vegeStore.load({
					params:{
						start: 0,
						mchntNo: queryForm.getForm().findField('mchntNo2').getValue(),
						termNo: queryForm.getForm().findField('termNo2').getValue(),
						vegeCode: queryForm.getForm().findField('vegeCode2').getValue()
					}
				});
				queryWin.hide(grid);
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
			queryForm.getForm().reset();
		}
	};
	
	var menuArr = new Array();
	menuArr.push(addMenu);     //增加
	menuArr.push('-');
	menuArr.push(delMenu);      //删除
	menuArr.push('-');
	menuArr.push(upMenu);		//修改
	menuArr.push('-');
	menuArr.push(queryCondition);     //查询
	
	// 交易查询
	var grid = new Ext.grid.EditorGridPanel({
		title: '终端菜品关联',
		iconCls: 'T501',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: vegeStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: vegeColModel,
		clicksToEdit: true,
		forceValidation: true,
		loadMask: {
			msg: '正在加载联机交易列表......'
		},
		tbar: menuArr,
		bbar:  new Ext.PagingToolbar({
			store: vegeStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[4].disable();
		grid.getStore().rejectChanges();
	});
	vegeStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
        	start: 0,
        	mchntNo: queryForm.getForm().findField('mchntNo2').getValue(),
			termNo: queryForm.getForm().findField('termNo2').getValue(),
			vegeCode: queryForm.getForm().findField('vegeCode2').getValue()
        });
    });
	
	grid.on({
		//单击行，使删除按钮可用
		'rowclick': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[2].enable();
		},
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});