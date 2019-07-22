Ext.onReady(function() {
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntCheckInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'engName',mapping: 'engName'},
			 {
				name : 'mappingMchntcdOne',
				mapping : 'mappingMchntcdOne'
			}, {
				name : 'mappingMchntcdTwo',
				mapping : 'mappingMchntcdTwo'
			},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'addr',mapping: 'addr'},
			//{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			//{name: 'manager',mapping: 'manager'},
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'termCount',mapping: 'termCount'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'updTs',mapping: 'updTs'}
		])
	});
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>商户英文名称：{engName}</p>',
			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			'<p>商户地址：{addr}</p>',
			//'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{commEmail}</p>',
			//'<p>法人代表名称：{manager}</p>',
			'<p>联系人姓名：{contact}</p>',
			'<p>联系人电话：{commTel}</p>',
			'<p>录入柜员：{crtOprId}</p>',
			'<p>申请原因：{partNum}</p>',
			'<p>最近更新时间：{updTs}</p>',{
			getmcc : function(val){return getRemoteTrans(val, "mcc");}
			}
		)
	});

	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchtNo',header: '商户ID',dataIndex: 'mchtNo',sortable: true,width: 130},
		{header: '商户名称',dataIndex: 'mchtNm',width: 200,id: 'mchtNm'},
		{header: '商户号1',dataIndex: 'mappingMchntcdOne',width: 100},
		{header: '商户号2',dataIndex: 'mappingMchntcdTwo',width: 100},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 130},
		{header: '注册日期',dataIndex: 'applyDate',width: 80,renderer: formatDt},
		{header: '商户状态',dataIndex: 'mchtStatus',renderer: mchntSt},
		{header: '终端数量',dataIndex: 'termCount',width: 60}
	]);
	
	setHeader(mchntColModel,3,4);
	
	//终端数据部分
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntTermInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'termStatus',mapping: 'termStatus'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'mchtCd',mapping: 'mchtCd'}
		])
	});
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'termId',header: '终端编号',dataIndex: 'termId',sortable: true,width: 70},
		{id: 'termSta',header: '终端状态',dataIndex: 'termStatus',renderer: termSta,width: 80},
		{id: 'termSta',header: '签到状态',dataIndex: 'termSignSta',renderer: termSignSta,hidden:true},
//		{id: 'termSta',header: '签到状态',dataIndex: 'termSignSta',renderer: termSignSta,width: 60},
		{id: 'mchtCd',dataIndex:'mchtCd',hidden:true}
	]);
	
	
	
	// 菜单集合
	var menuArr = new Array();
		
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认审核通过吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					//showProcessMsg('正在提交审核信息，请稍后......');
					rec = mchntGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T20201Action.asp?method=accept',
						params: {
							mchntId: rec.get('mchtNo'),
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
					//hideProcessMsg();
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
	};
	
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
					mchntId: rec.get('mchtNo'),
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
	};
	var addBackMenu = {
			text: '新增退回修改',
			width: 85,
			iconCls: 'stop',
			disabled: true,
			handler:function() {
				showConfirm('确认退回吗？',mchntGrid,function(bt) {
					if(bt == 'yes') {
						showInputMsg('提示','请输入退回原因',true,back);
					}
				});
			}
		};
	//退回按钮调用方法
	function back(bt,text){
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('退回原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入退回原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = mchntGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T20201Action.asp?method=back',
				params: {
					mchntId: rec.get('mchtNo'),
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
					mchntId: rec.get('mchtNo'),
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
	var childWin;
		
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);
		}
	};
	
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
	menuArr.push('-');         //[1]
	menuArr.push(refuseMenu);  //[2]
	menuArr.push('-');         //[3]
	menuArr.push(addBackMenu);  //[4]
	menuArr.push('-');         //[5]
	menuArr.push(detailMenu);  //[6]
	menuArr.push('-');         //[7]
	menuArr.push(queryCondition);  //[8]
	
	var termDetailMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			selectTermInfo(termGrid.getSelectionModel().getSelected().get('termId'),termGrid.getSelectionModel().getSelected().get('mchtCd'));	
		}
	};
	
	
	var termGrid = new Ext.grid.GridPanel({
		title: '终端信息',
		region: 'east',
		width: 200,
		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'termSta',
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [termDetailMenu],
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: false
		})
	});
	
	// 禁用编辑按钮
	termGrid.getStore().on('beforeload',function() {
		termGrid.getTopToolbar().items.items[0].disable();
	});
	
	termGrid.getSelectionModel().on({
		'rowselect': function() {
			termGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户综合审核',
		region: 'center',
		iconCls: 'T20201',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'mchtNm',
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		plugins: mchntRowExpander,
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
	mchntStore.load();
	
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo;
		termStore.load({
			params: {
				start: 0,
				mchntNo: id
				}
			});
	});
	termStore.on('beforeload', function() {
		Ext.apply(this.baseParams,
				{mchntNo : mchntGrid.getSelectionModel().getSelected().data.mchtNo
				});
	});
	
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].disable();
		mchntGrid.getTopToolbar().items.items[2].disable();
		mchntGrid.getTopToolbar().items.items[4].disable();
		mchntGrid.getTopToolbar().items.items[6].disable();
		
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			
			// 根据所选择的商户状态判断哪个按钮可用
			rec = mchntGrid.getSelectionModel().getSelected();

			mchntGrid.getTopToolbar().items.items[6].enable();
			mchntGrid.getTopToolbar().items.items[0].enable();
			mchntGrid.getTopToolbar().items.items[2].enable();
		
			if(rec.get('mchtStatus')=='1'){
				mchntGrid.getTopToolbar().items.items[4].enable();
			}else{
				mchntGrid.getTopToolbar().items.items[4].disable();
			}
		}
	});
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'mchtNo',
			name: 'mchtNo',
			vtype: 'alphanum',
			fieldLabel: '商户ID',
			maskRe: /^[0-9]+$/
		},{
			xtype:'textfield',
			fieldLabel: '商户号',
			id:'acmchntIdQ',
			name:'acmchntIdQ',
			width:200
		},{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			//endDateField: 'endDate',
			fieldLabel: '注册开始日期',
			editable: false
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			//startDateField: 'startDate',
			fieldLabel: '注册结束日期',
			editable: false
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW',
			fieldLabel: '归属机构',
			id: 'idacqInstId',
			hiddenName: 'acqInstId',
			width: 200
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 400,
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
		       	var endtime=Ext.getCmp('endDate').getValue(),starttime=Ext.getCmp('startDate').getValue();
            	if(endtime!=''&&starttime!=''&&endtime<starttime){
            		showErrorMsg("请保证截止日期不小于起始日期",queryWin);
    				return;
            	}
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
			mchntId: queryForm.findById('mchtNo').getValue(),
			acmchntId: Ext.getCmp('acmchntIdQ').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			brhId: queryForm.getForm().findField('acqInstId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,termGrid],
		renderTo: Ext.getBody()
	});
	
});