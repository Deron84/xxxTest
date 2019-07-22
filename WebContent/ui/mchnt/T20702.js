Ext.onReady(function() {
	var divArr=new Array();
	for(var i=1;i<=60;i++){
		if(i < 10){
			divArr[i-1]=["0"+i,"0"+i];
		}else{
			divArr[i-1]=[i+"",i+""];
		}
	}
	//商户分期 、总共36个
	var divNoStore = new Ext.data.ArrayStore ({
		
		fields: ['valueField','displayField'],
		data: divArr,
		reader: new Ext.data.ArrayReader()
	});
	// 商户
	var mchtStore = new Ext.data.JsonStore( {
		fields : [ 'valueField', 'displayField' ],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('MCHT_INFO_FOR_DIV', function(ret) {
		mchtStore.loadData(Ext.decode(ret));
	});
	
	// 计费
	var feeStore = new Ext.data.JsonStore( {
		fields : [ 'valueField', 'displayField' ],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('FEE_INFO', function(ret) {
		feeStore.loadData(Ext.decode(ret));
	});
	var topQueryPanel = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 550,
        autoHeight: true,
        labelWidth: 80,
		items: [{
				xtype : 'combo',
				fieldLabel : '商户',
				hiddenName : 'mchtId',
				id : 'qryMchtId',
				store : mchtStore,
				width:420
			},{
				xtype : 'combo',
				fieldLabel : '分期期数',
				hiddenName : 'divNo',
				id : 'qryDivNo',
				store : divNoStore,
				width:280
			},new Ext.form.TextField({
				id: 'qryProductCode',
				name: 'productCode',
				fieldLabel: '产品代码',
				regex:/^[0-9]?$/,
				regexText:'产品代码只能是0-9的数字',
				width:280
			})],
		buttons: [{
			text: '查询',
			handler: function() 
			{
				divMchtStore.load();
               queryWin.hide();
			}
		},{
			text: '重填',
			handler: function() {
				topQueryPanel.getForm().reset();
			}
		}]
	});

	var divMchtStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=divMchtInfoNew'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtIdUpd',mapping: 'mchtId'},
			{name: 'mchtNameUpd',mapping: 'mchtName'},
			{name: 'divNoUpd',mapping: 'divNo'},
			{name: 'productCodeUpd',mapping: 'productCode'},
			{name: 'productDivNameUpd',mapping: 'productDivName'},
			{name: 'feeCodeUpd',mapping: 'feeCode'},
			{name: 'feeNameUpd',mapping: 'feeName'},
			{name: 'modiOprIdUpd',mapping: 'productDivName'},
			{name: 'modiOprIdUpd',mapping: 'modiOprId'},
			{name: 'initTimeUpd',mapping: 'initTime'},
			{name: 'modiTimeUpd',mapping: 'modiTime'}
		])
	});
	
	
	var divMchtModel = new Ext.grid.ColumnModel([
		{id: 'mchtId',header: '商户号',dataIndex: 'mchtIdUpd',width: 120},
        {header: '商户名称',dataIndex: 'mchtNameUpd',width: 420},
        {header: '分期期数',dataIndex: 'divNoUpd',width: 80},
        {header: '产品代码',dataIndex: 'productCodeUpd',width: 100},
        {header: '计费代码',dataIndex: 'feeCodeUpd',width: 70},
        {header: '计费名称',dataIndex: 'feeNameUpd',width: 200},
        {header: '产品分期名称',dataIndex: 'productDivNameUpd',width: 200},
		{header: '最后操作柜员号',dataIndex: 'modiOprIdUpd',width: 100},
		{header: '最后修改时间',dataIndex: 'modiTimeUpd',width: 120},
		{header: '创建时间',dataIndex: 'initTimeUpd',width: 120}
	]);
	
	
	var addMenu = {
		text: '新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			divWin.show();
			divWin.center();
		}
	};
	var updMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		handler:function() {
			var selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
			updateForm.getForm().loadRecord(selectedRecord);
			updateWin.show();
			updateWin.center();
		}
	};
	
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			handler:function() {
		var selectedRecord = grid.getSelectionModel().getSelected();
        if(selectedRecord == null)
        {
            showAlertMsg("没有选择记录",grid);
            return;
        }
		showConfirm('确定要删除该地区码信息吗？',grid,function(bt) {
			if(bt == 'yes') {
//				showProcessMsg('正在提交，请稍后......');
				var rec = grid.getSelectionModel().getSelected();
				Ext.Ajax.requestNeedAuthorise({
					url: 'T20703Action_delete.asp',
					method: 'post',
					params: {
						mchtId : rec.get('mchtIdUpd'),
						divNo : rec.get('divNoUpd'),
						productCode : rec.get('productCodeUpd'),
						txnId: '20703',
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
						grid.getStore().reload();
						hideProcessMsg();
					}
				});
				
			}
		});
	}
		};
	
	var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 550,
        autoHeight: true,
        items: [topQueryPanel],
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
        }]
    });
	var menuArr = new Array();
	
//	menuArr.push(addMenu);		//[0]
//	menuArr.push('-');
//	menuArr.push(updMenu);		//[1]
//	menuArr.push('-');
//	menuArr.push(delMenu);		//[2]
//	menuArr.push('-');
	menuArr.push(queryMenu);    //[3]
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '商户分期查询',
		region: 'center',
		iconCls: 'T207',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: divMchtStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: divMchtModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载商户分期计费信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: divMchtStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	

	//每次在列表信息加载前都将保存按钮屏蔽
	divMchtStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
		    mchtId: Ext.getCmp('qryMchtId').getValue(),
		    productCode: Ext.getCmp('qryProductCode').getValue(),
			divNo: Ext.getCmp('qryDivNo').getValue()
		});
	});
	divMchtStore.load();
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
		}
	});
	
	
	var updateForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		labelWidth: 160,
		waitMsgTarget: true,
		layout: 'column',
		items: [{
	 	       layout: 'form',
	 	       width: 389,
	 	       items: [{
	 		        	xtype: 'combo',
	 					fieldLabel: '商户*',
	 					hiddenName: 'divMcht.id.mchtId',
	 					id: 'mchtIdUpd',
	 					allowBlank: false,
	 					store:mchtStore,
	 					readOnly: true
	 		        }]
	 			},{
		 	       layout: 'form',
		 	       width: 389,
		 	       items: [{
		 		        	xtype: 'combo',
		 					fieldLabel: '分期期数*',
		 					hiddenName: 'divMcht.id.divNo',
		 					id: 'divNoUpd',
		 					store:divNoStore,
		 					allowBlank: false,
		 					readOnly: true
		 		        }]
		 		   },{
			 	       layout: 'form',
			 	       width: 389,
			 	       items: [{
		 		        	xtype: 'textfield',
		 					fieldLabel: '产品代码*',
		 					maxLength:20,
		 					vtype: 'isOverMax',
		 					name: 'divMcht.id.productCode',
		 					id: 'productCodeUpd',
		 					regex:/^[0-9]+$/,
		 					regexText:'产品代码只能是0-9的数字',
		 					allowBlank: false,
		 					readOnly: true
		 		        }]
		 		   },{
			 	       layout: 'form',
			 	       width: 389,
			 	       items: [{
		 		        	xtype: 'textfield',
		 					fieldLabel: '产品分期名称*',
		 					maxLength:20,
		 					vtype: 'isOverMax',
		 					name: 'divMcht.productDivName',
		 					id: 'productDivNameUpd',
		 					allowBlank: false
		 		        }]
		 		   },{
			 	       layout: 'form',
			 	       width: 389,
			 	       items: [{
			 		        	xtype: 'combo',
			 					fieldLabel: '计费代码*',
			 					hiddenName: 'divMcht.feeCode',
			 					id: 'feeCodeUpd',
			 					store:feeStore,
			 					allowBlank: false
			 		        }]
			 		   }
		]
	});
	
	
	
	//添加表单
	var divForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 300,
		labelWidth: 160,
		waitMsgTarget: true,
		layout: 'column',
		items: [{
	 	       layout: 'form',
	 	       width: 389,
	 	       items: [{
	 		        	xtype: 'combo',
	 					fieldLabel: '商户*',
	 					hiddenName: 'divMcht.id.mchtId',
	 					id: 'mchtIdNew',
	 					allowBlank: false,
	 					store:mchtStore
	 		        }]
	 			},{
		 	       layout: 'form',
		 	       width: 389,
		 	       items: [{
		 		        	xtype: 'combo',
		 					fieldLabel: '分期期数*',
		 					hiddenName: 'divMcht.id.divNo',
		 					id: 'divNoNew',
		 					store:divNoStore,
		 					allowBlank: false
		 		        }]
		 		   },{
			 	       layout: 'form',
			 	       width: 389,
			 	       items: [{
		 		        	xtype: 'textfield',
		 					fieldLabel: '产品代码*',
		 					maxLength:20,
		 					vtype: 'isOverMax',
		 					name: 'divMcht.id.productCode',
		 					id: 'productCodeNew',
		 					regex:/^[0-9]+$/,
		 					regexText:'产品代码只能是0-9的数字',
		 					allowBlank: false
		 		        }]
		 		   },{
			 	       layout: 'form',
			 	       width: 389,
			 	       items: [{
		 		        	xtype: 'textfield',
		 					fieldLabel: '产品分期名称*',
		 					maxLength:20,
		 					vtype: 'isOverMax',
		 					name: 'divMcht.productDivName',
		 					id: 'productDivNameNew',
		 					allowBlank: false
		 		        }]
		 		   },{
			 	       layout: 'form',
			 	       width: 389,
			 	       items: [{
			 		        	xtype: 'combo',
			 					fieldLabel: '计费代码*',
			 					hiddenName: 'divMcht.feeCode',
			 					id: 'feeCodeNew',
			 					store:feeStore,
			 					allowBlank: false
			 		        }]
			 		   }
		]
	});


	//信息窗口
	var divWin = new Ext.Window({
		title: '商户分期计费信息新增',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [divForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(divForm.getForm().isValid()) {
					divForm.getForm().submit({
						url: 'T20703Action_add.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							divForm.getForm().reset();
							showSuccessMsg(action.result.msg,grid);
							divWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,grid);
						},
						params: {
							txnId: '20703',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
					divForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				divWin.hide();
			}
		}]
	});
	//终端信息窗口
	var updateWin = new Ext.Window({
		title: '商户分期计费信息修改',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [updateForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{							
			text: '确定',
			handler: function() {
				if(updateForm.getForm().isValid()) {
					updateForm.getForm().submit({
						url: 'T20703Action_update.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							grid.getStore().reload();
							updateForm.getForm().reset();
							showSuccessMsg(action.result.msg,grid);
							updateWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,grid);
						},
						params: {
							txnId: '20703',
							subTxnId: '03'
						}
					});
				}
			}
		},{
			text: '关闭',
			handler: function() {
				updateWin.hide();
			}
		}]
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
})