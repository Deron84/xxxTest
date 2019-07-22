Ext.onReady(function() {
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntNet&operate=check'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchntId'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'status',mapping: 'status'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'legalNm',mapping: 'legalNm'},
			{name: 'createTime',mapping: 'createTime'}
		])
	});
	
	mchntStore.load({
		params: {
			start: 0
		}
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		{id: 'mchntId',header: '入网商户序列号',dataIndex: 'id',sortable: true,width: 100},
		{header: '所属机构',dataIndex: 'brhId',width: 200},
		{header: '商户状态',dataIndex: 'status',renderer: mchntSt},
		{header: '入网商户名称',dataIndex: 'mchtNm'},
		{header: '法人姓名',dataIndex: 'legalNm',width: 200},
		{header: '申请时间',dataIndex: 'createTime',width: 200}
	]);
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
	
	var acceptMenu = {
			text: '通过',
			width: 85,
			iconCls: 'accept',
			disabled: true,
			handler:function() {
				showConfirm('确认审核通过吗？',mchntGrid,function(bt) {
					if(bt == 'yes') {
						showProcessMsg('正在提交审核信息，请稍后......');
						rec = mchntGrid.getSelectionModel().getSelected();
						Ext.Ajax.request({
							url: 'T20201Action.asp?method=accept',
							params: {
								mchntId: rec.get('mchntId'),
								txnId: '20201',
								subTxnId: '01'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,mchntGrid);
								} else {
									showErrorMsg(rspObj.msg,mchntGrid);
								}
								// 重新加载商户待审核信息
								mchntGrid.getStore().reload();
							}
						});
						hideProcessMsg();
					}
				});
			}
		};
		
		var backMenu = {
			text: '退回',
			width: 85,
			iconCls: 'back',
			disabled: true,
			handler:function() {
				showConfirm('确认退回吗？',mchntGrid,function(bt) {
					if(bt == 'yes') {
						showInputMsg('提示','请输入退回原因',true,back);
					}
				});
			}
		}
		
		// 退回按钮执行的方法
		function back(bt,text) {
			if(bt == 'ok') {
				if(getLength(text) > 60) {
					alert('退回原因最多可以输入30个汉字或60个字母、数字');
					showInputMsg('提示','请输入退回原因',true,back);
					return;
				}
				showProcessMsg('正在提交审核信息，请稍后......');
				rec = mchntGrid.getSelectionModel().getSelected();
				Ext.Ajax.request({
					url: 'T20201Action.asp?method=back',
					params: {
						mchntId: rec.get('mchntId'),
						txnId: '20201',
						subTxnId: '02',
						refuseInfo: text
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							showSuccessMsg(rspObj.msg,mchntGrid);
						} else {
							showErrorMsg(rspObj.msg,mchntGrid);
						}
						// 重新加载商户待审核信息
						mchntGrid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
		}
		
		var refuseMenu = {
			text: '拒绝',
			width: 85,
			iconCls: 'refuse',
			disabled: true,
			handler:function() {
				showConfirm('确认拒绝吗？',mchntGrid,function(bt) {
					if(bt == 'yes') {
						showInputMsg('提示','请输入拒绝原因',true,refuse);
					}
				});
			}
		}
		
		// 拒绝按钮调用方法
		function refuse(bt,text) {
			if(bt == 'ok') {
				if(getLength(text) > 60) {
					alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
					showInputMsg('提示','请输入拒绝原因',true,refuse);
					return;
				}
				showProcessMsg('正在提交审核信息，请稍后......');
				rec = mchntGrid.getSelectionModel().getSelected();
				Ext.Ajax.request({
					url: 'T20201Action.asp?method=refuse',
					params: {
						mchntId: rec.get('mchntId'),
						txnId: '20201',
						subTxnId: '03',
						refuseInfo: text
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							showSuccessMsg(rspObj.msg,mchntGrid);
						} else {
							showErrorMsg(rspObj.msg,mchntGrid);
						}
						// 重新加载商户待审核信息
						mchntGrid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
		}
		
		
	
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			window.open(Ext.contextPath + '/MchntDetailAction.asp?mchntId=' + rec.get('mchntId'), 'newwindow', 'height=600,width=780,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no');
		}
	}
	
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
    };
	
	menuArr.push(acceptMenu);  //[0]
	menuArr.push('-'); 	       //[1]
	menuArr.push(backMenu);    //[2]
	menuArr.push('-');         //[3]
	menuArr.push(refuseMenu);  //[4]
	menuArr.push('-');         //[5]
	menuArr.push(detailMenu);  //[6]
	menuArr.push('-'); //[7]
	menuArr.push(queryCondition); //[8]
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'id',
			name: 'id',
			vtype: 'alphanum',
			fieldLabel: '入网序列号'
		},{
			xtype: 'textfield',
			id: 'mchtNm',
			name: 'mchtNm',
			vtype: 'alphanum',
			fieldLabel: '入网名称'
		}]
	});
	
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 300,
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
			mchntStore.load();
			queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,						
			id: queryForm.findById('id').getValue(),
			mchtNm: queryForm.findById('mchtNm').getValue(),
			operate: 'search'
		});
	});

	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户信息',
		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[2].disable();
		mchntGrid.getTopToolbar().items.items[4].disable();
		mchntGrid.getTopToolbar().items.items[6].disable();
		mchntGrid.getTopToolbar().items.items[8].disable();
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
		
		// 根据所选择的商户状态判断哪个按钮可用
		rec = mchntGrid.getSelectionModel().getSelected();
		// 当商户状态为添加待审核和修改待审核的时候，拒绝按钮可用
		if(rec.get('mchntSt') == '1' || rec.get('mchntSt') == '3') {
			mchntGrid.getTopToolbar().items.items[4].enable();
		} else {
			mchntGrid.getTopToolbar().items.items[4].disable();
		}
		mchntGrid.getTopToolbar().items.items[0].enable();
		mchntGrid.getTopToolbar().items.items[2].enable();
		mchntGrid.getTopToolbar().items.items[6].enable();
		}
	});
});