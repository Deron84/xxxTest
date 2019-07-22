Ext.onReady(function() {
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getGroupMchnt'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'groupMchtCd'
		},[
			{name: 'groupMchtCd',mapping: 'groupMchtCd'},
			{name: 'groupType',mapping: 'groupType'},
			{name: 'groupName',mapping: 'groupName'},
			{name: 'cityCd',mapping: 'cityCd'},
			{name: 'headAddr',mapping: 'headAddr'},
			{name: 'regFund',mapping: 'regFund'},
			{name: 'busRange',mapping: 'busRange'},
			{name: 'mchtPerson',mapping: 'mchtPerson'},
			{name: 'contactAddr',mapping: 'contactAddr'},
			{name: 'zipCd',mapping: 'zipCd'}
		]),
		autoLoad: false
	});
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=groupMchtInf&operate=search'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'groupMchtCd'
		},[
			{name: 'groupMchtCd',mapping: 'groupMchtCd'},
			{name: 'grouName',mapping: 'grouName'},
			{name: 'groupType',mapping: 'groupType'},
			{name: 'regFund',mapping: 'regFund'},
			{name: 'busRange',mapping: 'busRange'},
			{name: 'mchtPerson',mapping: 'mchtPerson'},
			{name: 'contactAddr',mapping: 'contactAddr'}
		])
	});
	
	mchntStore.load({
		params: {
			start: 0
		}
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		{id: 'mchntId',header: '集团商户编号',dataIndex: 'groupMchtCd',sortable: true,width: 120},
		{header: '集团商户名称',dataIndex: 'grouName',width: 200},
		{header: '集团商户类型',dataIndex: 'groupType',width: 100,renderer: function(v){
			if(v == 1){
				return '全国性集团'
			}else if(v == 2){
				return '地方性集团(省内)'
			}else{
				return '未知'
			}
		}},
		{header: '注册资本(万元)',dataIndex: 'regFund',width: 100},
		{header: '经营范围',dataIndex: 'busRange',width: 210},
		{header: '联系人',dataIndex: 'mchtPerson',width: 60},
		{header: '通讯地址',dataIndex: 'contactAddr',width: 210,id: 'contactAddr'}
	]);
	
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
	
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			baseStore.load({
				params: {
					groupMchtCd: mchntGrid.getSelectionModel().getSelected().data.groupMchtCd
				},
				callback: function(records, options, success){
					if(success){
						mchntForm.getForm().loadRecord(baseStore.getAt(0));
						groupWin.show();
					}else{
						showErrorMsg("加载原集团商户信息失败，请刷新数据后重试",mchntGrid);
					}
				}
			});
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
	
	
	menuArr.push(queryCondition);	
	menuArr.push('-');
	menuArr.push(detailMenu);
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'mchntId',
			name: 'mchntId',
			vtype: 'alphanum',
			fieldLabel: '集团商户编号',
			maskRe: /^[0-9]+$/
		},{
			xtype: 'textfield',
			id: 'mchntName',
			name: 'mchntName',
			vtype: 'alphanum',
			fieldLabel: '集团商户名称'
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
			groupMchtCd: queryForm.findById('mchntId').getValue(),
			grouName: queryForm.findById('mchntName').getValue(),
			operate: 'search'
		});
	});
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '集团商户查询',
		region: 'center',
		iconCls: 'T205',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		autoExpandColumn: 'contactAddr',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
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
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			mchntGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	//*********************************集团商户部分*********************************
	var mchntForm = new Ext.FormPanel({
		frame: true,
		labelWidth: 140,
		waitMsgTarget: true,
		labelAlign: 'left',
		items: [{
			xtype: 'panel',
			layout: 'column',
			title: '集团商户:基本信息',
			items: [{
	        	columnWidth: .33,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '集团商户编号*',
						maxLength: '15',
						vtype: 'isOverMax',
						id: 'groupMchtCd',
						disabled: false,
						maskRe: /^[0-9]+$/
		        	}]
	        	}]
			},{
	        	columnWidth: .33,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '集团商户名称*',
						maxLength: '120',
						vtype: 'isOverMax',
						id: 'groupName'
		        	}]
	        	}]
			},{
	        	columnWidth: .33,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'GROUP_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '集团商户类型*',
						id: 'idgroupType',
						hiddenName: 'groupType',
						width: 160
		        	}]
	        	}]
			},{
	        	columnWidth: .33,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '注册资本(万元)',
						id: 'regFund',
						name: 'regFund'
		        	}]
	        	}]
			},{
	        	columnWidth: .66,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经营范围',
						width: 380,
						id: 'busRange',
						name: 'busRange'
		        	}]
	        	}]
			},{
	        	columnWidth: .33,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '邮政编码',
						id: 'zipCd',
						name: 'zipCd'
		        	}]
	        	}]
			},{
	        	columnWidth: .66,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '通讯地址',
						width: 380,
						id: 'contactAddr',
						name: 'contactAddr'
		        	}]
	        	}]
			},{
	        	columnWidth: .33,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人',
						id: 'mchtPerson',
						name: 'mchtPerson'
		        	}]
	        	}]
			},{
	        	columnWidth: .66,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '总部地址',
						width: 380,
						id: 'headAddr',
						name: 'headAddr'
		        	}]
	        	}]
			}]
		},{
			xtype: 'panel',
			layout: 'column',
			title: '集团商户:结算信息',
			items: [{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_STLM_MD',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '清算依据',
						width: 150,
						id: 'idmchtStlmMd',
						hiddenName: 'mchtStlmMd'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_OUT_IN_MD',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '出入账模式',
						width: 150,
						id: 'idmchtOutInMd',
						hiddenName: 'mchtOutInMd'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_CP_STLM_CYCLE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '本金清算周期',
						width: 150,
						id: 'idmchtCpStlmCycle',
						hiddenName: 'mchtCpStlmCycle'
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
						fieldLabel: '本金清算模式(T+n)',
						width: 150,
						id: 'mchtCpStlmMd',
						name: 'mchtCpStlmMd'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_CP_STLM_CYCLE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '手续费清算周期',
						width: 150,
						id: 'idmchtFeStlmCycle',
						hiddenName: 'mchtFeStlmCycle'
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
						fieldLabel: '手续费清算模式(T+n)',
						width: 150,
						id: 'mchtFeStlmMd',
						name: 'mchtFeStlmMd'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_CP_STLM_CYCLE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润清算周期',
						width: 150,
						id: 'idmchtAloStlmCy',
						hiddenName: 'mchtAloStlmCy'
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
						fieldLabel: '分润清算模式(T+n)',
						width: 150,
						id: 'mchtAloStlmMd',
						name: 'mchtAloStlmMd'
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
						fieldLabel: '开户行名称',
						width: 150,
						id: 'mchtStlmInsNm',
						name: 'mchtStlmInsNm'
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
						fieldLabel: '开户行机构代码',
						width: 150,
						id: 'mchtStlmInsId',
						name: 'mchtStlmInsId'
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
						fieldLabel: '收入账号开户名',
						width: 150,
						id: 'mchtStlmCNm',
						name: 'mchtStlmCNm'
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
						fieldLabel: '收入账号',
						width: 150,
						id: 'mchtStlmCAcct',
						name: 'mchtStlmCAcct'
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
						fieldLabel: '支出账号开户名',
						width: 150,
						id: 'mchtStlmDNm',
						name: 'mchtStlmDNm'
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
						fieldLabel: '支出账号',
						width: 150,
						id: 'mchtStlmDAcct',
						name: 'mchtStlmDAcct'
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
						fieldLabel: '开户行同城交换号',
						width: 150,
						id: 'mchtCityTran',
						name: 'mchtCityTran'
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
						fieldLabel: '开户行支付系统号',
						width: 150,
						id: 'mchtPaySysAcct',
						name: 'mchtPaySysAcct'
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_STLM_WAY',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '结算路径',
						width: 150,
						id: 'idmchtStlmWay',
						hiddenName: 'mchtStlmWay'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_STLM_STYLE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '付费方式',
						width: 150,
						id: 'idmchtStlmStyle',
						hiddenName: 'mchtStlmStyle'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'datefield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '自主确定付费日期',
						width: 150,
						maxLength: 10,
						format: 'y-m-d',
						id: 'mchtAccDt',
						name: 'mchtAccDt',
						anchor: '62%'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CHK_SND_CYCLE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '对账单发送周期',
						width: 150,
						id: 'idchkSndCycle',
						hiddenName: 'chkSndCycle'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CHK_SND_MD',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '对账单发送方式',
						width: 150,
						id: 'idchkSndMd',
						hiddenName: 'chkSndMd'
		        	}]
	        	}]
			},{
	        	columnWidth: .3,
	       		items: [{
	       			xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否清算到集团商户',
						id: 'stlmGroupFlg',
						name: 'stlmGroupFlg'
		        	}]
	        	}]
			},{
	        	columnWidth: .3,
	       		items: [{
	       			xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否节假日合并结算',
						id: 'mchtHoliFlg',
						name: 'mchtHoliFlg'
		        	}]
	        	}]
			},{
	        	columnWidth: .3,
	       		items: [{
	       			xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '清算明细是否生成',
						id: 'mchtStlmDtlFlg',
						name: 'mchtStlmDtlFlg'
		        	}]
	        	}]
			}]
		}]
	})
					
	var groupWin = new Ext.Window({
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 980,
		autoHeight: true,
		items: [mchntForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		buttons: [{
			text: '取消',
			handler: function() {
				groupWin.hide(mchntGrid);
			}
		}]
	});
	mchntForm.getForm().disableAll();
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
	
});