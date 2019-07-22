Ext.onReady(function() {	

	//查询商户编号
	var mchntNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntNoStore.loadData(Ext.decode(ret));
	});
	
	// 所在地区数据集
	var cityStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	SelectOptionsDWR.getComboData('CITY_CODE',function(ret){
		cityStore.loadData(Ext.decode(ret));
	});
	
	
	// 企业资质等级
	var branchSvrLvlStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	SelectOptionsDWR.getComboData('BRANCH_SVR_LVL',function(ret){
		branchSvrLvlStore.loadData(Ext.decode(ret));
	});
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=recUpdTs'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtCd',mapping: 'mchtCd'},
			{name: 'branchCd',mapping: 'branchCd'},
			{name: 'branchNm',mapping: 'branchNm'},
			{name: 'branchArea',mapping: 'branchArea'},
			{name: 'branchSvrLvl',mapping: 'branchSvrLvl'},
			{name: 'branchContMan',mapping: 'branchContMan'},
			{name: 'branchTel',mapping: 'branchTel'},
			{name: 'branchFax',mapping: 'branchFax'},
			{name: 'branchNmEn',mapping: 'branchNmEn'},
			{name: 'branchAddr',mapping: 'branchAddr'},
			{name: 'custNager',mapping: 'custNager'},
			{name: 'custMobile',mapping: 'custMobile'},
			{name: 'custTel',mapping: 'custTel'},
			{name: 'oprNm',mapping: 'oprNm'},
			{name: 'signDate',mapping: 'signDate'}
		])
	});
	
	mchntStore.load({
		params:{start: 0}
	});
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>英文名：{branchNmEn}</p>',
			'<p>客户经理姓名：{custMnger}</p>',
			'<p>客户经理手机：{custMobile}</p>',
			'<p>客户经理电话：{custTel}</p>',
			'<p>收银员数目：{oprNm}</p>'
		)
	});
	
	
	var oprColModel = new Ext.grid.ColumnModel([
	        mchntRowExpander,
    		{id: 'mchtCd',header: '商户编号',dataIndex: 'mchtCd',sortable: true,width: 100},
    		{header: '分店号',dataIndex: 'branchCd',width: 100},
    		{header: '中文名',dataIndex: 'branchNm',id:'branchNm'},
    		{header: '所在区域',dataIndex: 'branchArea'},
    		{header: '服务等级',dataIndex: 'branchSvrLvl',renderer: svrLvlType,
    			editor: new Ext.form.ComboBox({
    			 	store: branchSvrLvlStore,
    				displayField: 'displayField',
    				valueField: 'valueField',
    				mode: 'local',
    				triggerAction: 'all',
    				forceSelection: true,
    				typeAhead: true,
    				selectOnFocus: true,
    				editable: true
    			 })},
    		{header: '分店联系人',dataIndex: 'branchContMan',width: 100,
          		 editor: new Ext.form.TextField({
          		 	maxLength: 15,
          			allowBlank: false,
          			vtype: 'isOverMax'
          		 })},
    		{header: '联系电话',dataIndex: 'branchTel',
       		 editor: new Ext.form.TextField({
     		 	maxLength: 15,
     			allowBlank: false,
     			vtype: 'isOverMax'
     		 })},
    		{header: '分店传真',dataIndex: 'branchFax',
          		 editor: new Ext.form.TextField({
          		 	maxLength: 15,
          			allowBlank: false,
          			vtype: 'isOverMax'
          		 })},
      		{header: '营业地址',dataIndex: 'branchAddr',
          		 editor: new Ext.form.TextField({
          		 	maxLength: 60,
          			allowBlank: false,
          			vtype: 'isOverMax'
          		 })},
    		{header: '签约日期',dataIndex: 'signDate',width: 100,renderer: formatDt}
    	]);
	
		
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
	};
		
	/******************************分店信息添加******************************/
	// 可选商户下拉列表
	var mchntNoCombo = new Ext.form.ComboBox({
		store: mchntNoStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择商户号',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个商户号',
		fieldLabel: '商户号*',
		hiddenName: 'mchtCd'
	});
	
	
	var addMenu = {
		text: '商店新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			oprWin.show();
			oprWin.center();
		}
	};
	
	// 可选机构下拉列表
	var mchntNoCombo = new Ext.form.ComboBox({
		store: mchntNoStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择商户号',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个商户号',
		fieldLabel: '商户号*',
		hiddenName: 'mchntNoId'
	});
	
	// 限额信息添加
	var oprInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		labelWidth: 165,
		waitMsgTarget: true,
		labelAlign: 'left',
		layout: 'column',
		items: [{
			xtype: 'fieldset',
			title: '分店编号',
			layout: 'column',
			width: 780,
			bodyStyle: 'margin-top: 10px',
			items: [{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 500,
					items: [{
			        	xtype: 'combo',
						fieldLabel: '商户编号*',
						labelStyle: 'padding-left: 5px',
						store: mchntNoStore,
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						allowBlank: false,
						lazyRender: true,
						width: 280,
						blankText: '请选择商户编号',
						id: 'mchtCdId',
						hiddenName: 'mchtCd'
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 500,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分店代码',
						allowBlank: false,
						maxLength: '11',
						vtype: 'isOverMax',
						width: 280,
						id: 'branchCd',
						name: 'branchCd'
		        	}]
	        	}]
			}]
		},{
			xtype: 'fieldset',
			bodyStyle: 'margin-top: 10px;padding-top: 10px',
			title: '分店基本信息',
			layout: 'column',
			width: 780,
			items: [{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分店名称（中文）*',
						allowBlank: false,
						blankText: '请输入分店名称',
						maxLength: '60',
						vtype: 'isOverMax',
						id: 'branchNm',
						name: 'branchNm'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分店名称（英文）*',
						allowBlank: true,
						maxLength: '60',
						vtype: 'isOverMax',
						id: 'branchNmEn',
						name: 'branchNmEn'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 389,
					items: [{
			        	xtype: 'combo',
						fieldLabel: '分店所在区域',
						labelStyle: 'padding-left: 5px',
						store: cityStore,
						displayField: 'displayField',
						valueField: 'displayField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						allowBlank: true,
						lazyRender: true,
						width: 120,
						blankText: '请选择分店所在区域',
						id: 'branchAreaId',
						hiddenName: 'branchArea'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分店营业地址',
						allowBlank: true,
						maxLength: '60',
						vtype: 'isOverMax',
						id: 'branchAddr',
						name: 'branchAddr'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '服务等级',
						store: branchSvrLvlStore,
						lazyRender: true,
						width: 120,
						blankText: '-请选择-',
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						allowBlank: true,
						lazyRender: true,
						id: 'branchSvrLvlId',
						hiddenName: 'branchSvrLvl'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分店联系人*',
						allowBlank: false,
						maxLength: '15',
						vtype: 'isOverMax',
						id: 'branchContMan',
						name: 'branchContMan'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分店联系电话*',
						allowBlank: false,
						maxLength: '15',
						vtype: 'isOverMax',
						id: 'branchTel',
						name: 'branchTel'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分店传真',
						allowBlank: true,
						maxLength: '15',
						vtype: 'isOverMax',
						id: 'branchFax',
						name: 'branchFax'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '客户经理姓名*',
						allowBlank: false,
						maxLength: '15',
						vtype: 'isOverMax',
						id: 'custMnger',
						name: 'custMnger'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '客户经理手机*',
						allowBlank: false,
						maxLength: '15',
						vtype: 'isOverMax',
						id: 'custMobile',
						name: 'custMobile'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '客户经理电话',
						allowBlank: true,
						maxLength: '15',
						vtype: 'isOverMax',
						id: 'custTel',
						name: 'custTel'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收银员数目',
						allowBlank: true,
						maxLength: '10',
						vtype: 'isOverMax',
						id: 'oprNm',
						name: 'oprNm'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 375,
					items: [{
						xtype: 'datefield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '签约日期*',
						maxLength: 10,
						format: 'Ymd',
						id: 'signDate',
						name: 'signDate',
						anchor: '85%'
		        	}]
	        	}]
			}]
		}]
	});
	
	// 操作员添加窗口
	var oprWin = new Ext.Window({
		title: '商店添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 800,
		autoHeight: true,
		layout: 'fit',
		items: [oprInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(oprInfoForm.getForm().isValid()) {
					oprInfoForm.getForm().submitNeedAuthorise({
						url: 'T20601Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,oprInfoForm);
							//重置表单
							oprInfoForm.getForm().reset();
							//重新加载列表
							oprGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,oprInfoForm);
						},
						params: {
							txnId: '20601',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				oprInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				oprWin.hide(oprColModel);
			}
		}]
	});
	
	
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: true,
			handler: function() {
				if(oprGrid.getSelectionModel().hasSelection()) {
					var rec = oprGrid.getSelectionModel().getSelected();
					
					showConfirm('确定要删除该条信息吗？',oprGrid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T20601Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,oprGrid);
										oprGrid.getStore().reload();
										oprGrid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,oprGrid);
									}
								},
								params: { 
									mchtCd: rec.get('mchtCd'),
									branchCd: rec.get('branchCd'),									
									txnId: '20601',
									subTxnId: '02'
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
				var modifiedRecords = oprGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
//				showProcessMsg('正在保存操作员信息，请稍后......');
				//存放要修改的机构信息
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						mchtCd : record.get('mchtCd'),
						branchCd: record.get('branchCd'),
						branchNm : record.get('branchNm'),
						branchNmEn : record.get('branchNmEn'),
						branchArea: record.get('branchArea'),
						branchSvrLvl: record.get('branchSvrLvl'),
						branchContMan: record.get('branchContMan'),
						custMnger:record.get('custMnger'),
						branchTel: record.get('branchTel'),
						branchFax: record.get('branchFax'),
						branchAddr: record.get('branchAddr'),
						custNager: record.get('custNager'),
						custMobile: record.get('custMobile'),
						custTel: record.get('custTel'),
						oprNm: record.get('oprNm'),
						txnNum: record.get('txnNum')
					};
					array.push(data);
				}
				Ext.Ajax.requestNeedAuthorise({
					url: 'T20601Action.asp?method=update',
					method: 'post',
					params: {
					mchtBranInfList: Ext.encode(array),
						txnId: '20601',
						subTxnId: '03'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							oprGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,oprGrid);
						} else {
							oprGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,oprGrid);
						}
						oprGrid.getTopToolbar().items.items[4].disable();
						oprGrid.getStore().reload();
						hideProcessMsg();
					}
				});
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
	
	var menuArr = new Array();
	menuArr.push(addMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(delMenu);		// [2]
	menuArr.push('-');			// [3]
	menuArr.push(upMenu);		// [4]	
	menuArr.push('-');  //[5]
	menuArr.push(queryCondition);  //[6]

	
	// 操作员信息列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '商户分店维护',
		iconCls: 'T206',
		region:'center',
		autoExpandColumn:'branchNm',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载操作员信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[2].disable();
		oprGrid.getTopToolbar().items.items[4].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			oprGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		oprGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
        	xtype: 'combo',
			fieldLabel: '商户编号*',
			labelStyle: 'padding-left: 5px',
			store: mchntNoStore,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			lazyRender: true,
			width: 150,
			blankText: '请选择商户编号',
			id: 'mchtCdId',
			hiddenName: 'mchtCd'
    	},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			id: 'branchCd',
			name: 'branchCd',
			width: 150,
			vtype: 'alphanum',
			fieldLabel: '商店编号'
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			id: 'branchNm',
			width: 150,
			name: 'branchNm',
			vtype: 'isOverMax',
			fieldLabel: '商店名'
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
			mchtCd: queryForm.findById('mchtCdId').getValue(),
			branchCd: queryForm.findById('branchCd').getValue(),
			branchNm: queryForm.findById('branchNm').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[oprGrid]
	});
});