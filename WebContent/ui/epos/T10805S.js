Ext.onReady(function(){
	
	var lastSelectMain = -1;
	var lastSelectLv1 = -1;
	var lastSelectLv2 = -1;
	var lastSelectLv3 = -1;
	
	//交易代码
    var txnStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('TXN_CODE',function(ret){
    	txnStore.loadData(Ext.decode(ret));
	});
  //EPOS交易版本号
    var eposStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboDataWithParameter('EPOS_VER','',function(ret){
		eposStore.loadData(Ext.decode(ret));
	});
    //EPOS交易提示信息
    var pptStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboDataWithParameter('PPT_MSG','',function(ret){
		pptStore.loadData(Ext.decode(ret));
	});
	
    var brhId = '';
    var verId = '';
    var menuLevel = '';
    var menuPreId1 = '';
    var menuPreId2 = '';
    var lastSta = '';
    var curLevel = '';
    
    function reloadStore(){
    	if(menuLevel=='1'){
    		menuLvl1Store.load();
    	}else if(menuLevel=='2'){
    		menuLvl2Store.load();
    	}else if(menuLevel=='3'){
    		menuLvl3Store.load();
    	}
    	
    	
//    	if(lastSelectMain!=-1){
//    		mainGrid.getSelectionModel().selectRow(lastSelectMain);
//    	}
    	
    }
    
    
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
			xtype: 'displayfield',
			fieldLabel: '机构编号*',
			name: 'brhIdNew',
			id: 'brhIdNew'
		},{
			xtype: 'displayfield',
			fieldLabel: '版本编号*',
			hiddenName: 'verIdNew',
			id: 'verIdNew'
		},{
			xtype: 'combofordispaly',
			fieldLabel: '菜单级别*',
			hiddenName: 'menuLevelNew',
			id: 'menuLvlNew',
			store: new Ext.data.ArrayStore({
                 fields: ['valueField','displayField'],
                 data: [['1','1级菜单'],['2','2级菜单'],['3','3级菜单']]
            })
		},{
			xtype: 'displayfield',
			fieldLabel: '上级一级菜单',
			name: 'menuPreId1New',
			allowBlank: false,
			id: 'menuPreId1New'
		},{
			xtype: 'displayfield',
			fieldLabel: '上级二级菜单',
			name: 'menuPreId2New',
			allowBlank: false,
			id: 'menuPreId2New'
		},{
			fieldLabel: '菜单显示内容*',
			name: 'menuMsgNew',
			id: 'menuMsgNew',
			maxLength: 64
		},{
			xtype: 'combo',
			fieldLabel: '交易代码',
			hiddenName: 'txnCodeNew',
			id: 'idtxnCodeNew',
			store: txnStore
		},{
			xtype: 'checkbox',
			fieldLabel: '末笔交易查询标志',
			name: 'conFlagNew',
			id: 'conFlagNew',
			inputValue: '1'
		},{
			xtype: 'combo',
			store: pptStore,
			fieldLabel: '交易提示信息',
			hiddenName: 'pptIdNew',
			id: 'idpptIdNew'
		}],
		buttons: [{
            text: '保存',
            handler : function(btn) {
				if (mainForm.getForm().isValid()) {
					mainForm.getForm().submitNeedAuthorise({
							url: 'T10805Action_add.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								showSuccessAlert(action.result.msg,mainForm);
								mainForm.getForm().reset();
								mainWin.hide(mainGrid);
								reloadStore();
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,mainForm);
							},
							params: {
								txnId: '10805',
								subTxnId: '01',
								brhId: brhId,
								verId: verId,
								menuLevel: menuLevel,
								menuPreId1: menuPreId1,
								menuPreId2: menuPreId2,
								conFlagNew: Ext.getCmp('conFlagNew').value
							}
					});
			}}
		},{
            text: '关闭',
            handler: function() {
				mainWin.hide(mainGrid);
				mainForm.getForm().reset();
			}
        }]
	});
	
	function submitUpdateForm(frm){
		
		frm.submitNeedAuthorise({
			url: 'T10805Action_update.asp',
			waitTitle : '请稍候',
			waitMsg : '正在提交表单数据,请稍候...',
			success : function(form, action) {
				reloadStore();
				showSuccessMsg(action.result.msg,updateForm);
				updateWin.hide(mainGrid);
				frm.resetAll();
			},
			failure : function(form,action) {
				showErrorMsg(action.result.msg,updateForm);
			},
			params: {
				txnId: '10805',
				subTxnId: '02',
				brhId: updateForm.getForm().findField('brhId').getValue(),
				verId: updateForm.getForm().findField('verId').getValue(),
				menuId: updateForm.getForm().findField('menuId').getValue(),
				menuLevel: menuLevel,
				menuPreId1: updateForm.getForm().findField('menuPreId1').getValue(),
				menuPreId2: updateForm.getForm().findField('menuPreId2').getValue()
			}
		});
		
		
	}
	
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
			xtype: 'displayfield',
			fieldLabel: '机构编号*',
			name: 'brhId',
			id: 'brhId'
		},{
			xtype: 'displayfield',
			fieldLabel: '版本编号*',
			hiddenName: 'verId',
			id: 'verId'
		},{
			xtype: 'combofordispaly',
			fieldLabel: '菜单级别*',
			hiddenName: 'menuLevel',
			id: 'menuLv',
			store: new Ext.data.ArrayStore({
                 fields: ['valueField','displayField'],
                 data: [['1','1级菜单'],['2','2级菜单'],['3','3级菜单']]
            })
		},{
			xtype: 'displayfield',
			fieldLabel: '菜单编号*',
			name: 'menuId',
			id: 'menuId'
		},{
			xtype: 'displayfield',
			fieldLabel: '上级一级菜单',
			name: 'menuPreId1',
			allowBlank: false,
			id: 'menuPreId1'
		},{
			xtype: 'displayfield',
			fieldLabel: '上级二级菜单',
			name: 'menuPreId2',
			allowBlank: false,
			id: 'menuPreId2'
		},{
			fieldLabel: '菜单显示内容*',
			name: 'menuMsg',
			id: 'menuMsg',
			maxLength: 64
		},{
			xtype: 'combo',
			fieldLabel: '交易代码',
			hiddenName: 'txnCode',
			id: 'idtxnCode',
			store: txnStore
		},{
			xtype: 'checkbox',
			fieldLabel: '末笔交易查询标志',
			name: 'conFlag',
			id: 'conFlag',
			allowBlank: true,
			inputValue: 1
		},{
			xtype: 'combo',
			store: pptStore,
			fieldLabel: '交易提示信息',
			hiddenName: 'pptId',
			id: 'idpptId'
		},{
			xtype: 'combo',
			fieldLabel: '菜单状态*',
			hiddenName: 'oprIdUp',
			id: 'oprId',
			allowBlank: false,
			store: new Ext.data.ArrayStore({
                fields: ['valueField','displayField'],
                data: [['1','启用'],['0','作废']]
           })
		}],
		
		buttons: [{
            text: '保存',
            handler : function(btn) {
				var frm = updateForm.getForm();
				if (frm.isValid()) {
					var oprId = updateForm.getForm().findField('oprIdUp').getValue();
					if(oprId!=lastSta&&oprId=='0'){
						showConfirm('菜单作废将同时作废所有下级菜单，确定要执行该操作吗？',mainGrid,function(bt) {
							if(bt == 'yes') {
								submitUpdateForm(frm);
							}
						});
					}else{
						submitUpdateForm(frm);
					}
			}}
		},{
            text: '关闭',
            handler: function() {
            	updateWin.hide(mainGrid);
            	updateForm.getForm().resetAll();
			}
        }]
	});
	
	var mainWin = new Ext.Window({
		title: '新增菜单信息',
		iconCls: 'T30505',
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
	function addMenu(level,sel){
		mainForm.getForm().findField('menuLevelNew').setValue(level);
		menuLevel = level;
		
		if(level=='1'){
			mainForm.getForm().findField('brhIdNew').setValue(sel.data.BANK_ID);
			mainForm.getForm().findField('verIdNew').setValue(sel.data.VER_ID);
			mainForm.getForm().findField('menuPreId1New').setValue('0');
			mainForm.getForm().findField('menuPreId2New').setValue('0');
			brhId = sel.data.BANK_ID;
			verId = sel.data.VER_ID;
			menuPreId1 = '0';
			menuPreId2 = '0';
		}else if(level=='2'){
			mainForm.getForm().findField('brhIdNew').setValue(sel.data.brhId);
			mainForm.getForm().findField('verIdNew').setValue(sel.data.verId);
			mainForm.getForm().findField('menuPreId1New').setValue(menuLvl1Grid.getSelectionModel().getSelected().data.menuId);
			mainForm.getForm().findField('menuPreId2New').setValue('0');
			brhId = sel.data.brhId;
			verId = sel.data.verId;
			menuPreId1 = menuLvl1Grid.getSelectionModel().getSelected().data.menuId;
			menuPreId2 = '0';
		}else if(level=='3'){
			mainForm.getForm().findField('brhIdNew').setValue(sel.data.brhId);
			mainForm.getForm().findField('verIdNew').setValue(sel.data.verId);
			mainForm.getForm().findField('menuPreId1New').setValue(menuLvl1Grid.getSelectionModel().getSelected().data.menuId);
			mainForm.getForm().findField('menuPreId2New').setValue(menuLvl2Grid.getSelectionModel().getSelected().data.menuId);
			brhId = sel.data.brhId;
			verId = sel.data.verId;
			menuPreId1 = menuLvl1Grid.getSelectionModel().getSelected().data.menuId;
			menuPreId2 = menuLvl2Grid.getSelectionModel().getSelected().data.menuId;
		}
		
		
		mainWin.show(mainGrid);
		
	}
	
	var updateWin = new Ext.Window({
		title: '菜单配置信息',
		iconCls: 'T30505',
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
	
	function updateMenu(level,sel){
		
		updateForm.getForm().loadRecord(sel);
		menuLevel = level;
		lastSta = updateForm.getForm().findField('oprIdUp').getValue();
		updateWin.show(mainGrid);
		
	}
	
	
	function changeColor(v){
		if(v!='0'){
			return "<font color='red'>"+v+"</font>";
		}else{
			return v;
		}
	}

	
	//数据集
	var mainStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getEposMenu'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'BANK_ID',mapping: 'BANK_ID'},	
			{name: 'BRH_NAME',mapping: 'BRH_NAME'},
			{name: 'VER_ID',mapping: 'VER_ID'},
			{name: 'MISC',mapping: 'MISC'},
			{name: 'MENU_NUM1',mapping: 'MENU_NUM1'},
			{name: 'MENU_NUM2',mapping: 'MENU_NUM2'},
			{name: 'MENU_NUM3',mapping: 'MENU_NUM3'}
		]),
		autoLoad: true
	});
	
	
	
	//列模型
	var mainModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '机构编号',dataIndex: 'BANK_ID',width: 100},
    		{header: '机构名称',dataIndex: 'BRH_NAME',width: 120},
    		{header: '版本编号',dataIndex: 'VER_ID',width: 100},
    		{header: '版本名称',dataIndex: 'MISC',width: 120,id: 'MISC'},
    		{header: '已配置一级菜单数',dataIndex: 'MENU_NUM1',width: 110,renderer:changeColor},
    		{header: '已配置二级菜单数',dataIndex: 'MENU_NUM2',width: 110,renderer:changeColor},
    		{header: '已配置三级菜单数',dataIndex: 'MENU_NUM3',width: 110,renderer:changeColor}
    ]);
	
	//GRID
	var mainGrid = new Ext.grid.GridPanel({
		title: '终端菜单配置',
		iconCls: 'T30505',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'MISC',
		tbar: [{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 55,
			handler:function() {
				mainStore.load();
			}
		},'-','机构编号：',{
			xtype: 'basecomboselect',
			baseParams: 'CUP_BRH_BELOW',
			id: 'idbrhIds',
			hiddenName: 'brhIds',
			width: 220,
			listeners:{
				'select': function() {
					SelectOptionsDWR.getComboDataWithParameter('EPOS_VER',Ext.getCmp('idbrhIds').value,function(ret){
						eposStore.loadData(Ext.decode(ret));
					});
				}
			}
		},'-','版本编号：',{
			xtype: 'combo',
			fieldLabel: '版本号*',
			store: eposStore,
			id: 'idverIds',
			hiddenName: 'verIds'
//		},'-',{
//			xtype: 'button',
//			text: '重置',
//			name: 'reset',
//			id: 'reset',
//			iconCls: 'query',
//			width: 55,
//			handler:function() {
//				Ext.getCmp('idbrhIds').getStore().reload();
//			}
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
	mainStore.on('beforeload', function() {
		//重置全部
		menuLvl1Store.removeAll();
		menuLvl2Store.removeAll();
		menuLvl3Store.removeAll();
		menuLvl1Grid.getTopToolbar().items.items[0].disable();
		menuLvl1Grid.getTopToolbar().items.items[2].disable();
		menuLvl2Grid.getTopToolbar().items.items[0].disable();
		menuLvl2Grid.getTopToolbar().items.items[2].disable();
		menuLvl3Grid.getTopToolbar().items.items[0].disable();
		menuLvl3Grid.getTopToolbar().items.items[2].disable();
		Ext.apply(this.baseParams, {
            start: 0,
            brhId: Ext.get('brhIds').getValue(),
            verId: Ext.get('verIds').getValue()
        });
    });
	mainGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		menuLvl1Store.removeAll();
		menuLvl2Store.removeAll();
		menuLvl3Store.removeAll();
		menuLvl1Grid.getTopToolbar().items.items[0].enable();
		menuLvl1Grid.getTopToolbar().items.items[2].disable();
		menuLvl2Grid.getTopToolbar().items.items[0].disable();
		menuLvl2Grid.getTopToolbar().items.items[2].disable();
		menuLvl3Grid.getTopToolbar().items.items[0].disable();
		menuLvl3Grid.getTopToolbar().items.items[2].disable();
		menuLvl1Store.load();
		lastSelectMain = mainGrid.getSelectionModel().last;
	});
	
	
	
	
	//一级菜单数据集
	var menuLvl1Store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=eposMenuMsg'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'usageKey',mapping:  'USAGE_KEY'},   
			{name: 'brhId',mapping:'BRH_ID'},
			{name: 'menuId',mapping: 'MENU_ID'},
			{name: 'menuLv',mapping: 'MENU_LEVEL'},
			{name: 'menuMsg',mapping: 'MENU_MSG'},
			{name: 'menuPreId1',mapping: 'MENU_PRE_ID1'},
	      	{name: 'menuPreId2',mapping: 'MENU_PRE_ID2'},
			{name: 'txnCode',mapping: 'TXN_CODE'},
			{name: 'conFlag',mapping: 'CON_FLAG'},
			{name: 'pptId',mapping: 'PPT_ID'},
			{name: 'oprId',mapping: 'OPR_ID'},
			{name: 'verId',mapping: 'VER_ID'},
			{name: 'crtDate',mapping: 'CRT_DATE'},
			{name: 'updDate',mapping: 'UPD_DATE'},
			{name: 'txnDsp',mapping: 'TXN_DSP'},
			{name: 'pptMsg',mapping: 'PPT_MSG'}
		])
	});
	menuLvl1Store.on('beforeload', function() {
		
		menuLvl1Store.removeAll();
		menuLvl2Store.removeAll();
		menuLvl3Store.removeAll();
		menuLvl1Grid.getTopToolbar().items.items[2].disable();
		menuLvl2Grid.getTopToolbar().items.items[0].disable();
		menuLvl2Grid.getTopToolbar().items.items[2].disable();
		menuLvl3Grid.getTopToolbar().items.items[0].disable();
		menuLvl3Grid.getTopToolbar().items.items[2].disable();
		
		
        Ext.apply(this.baseParams, {
            start: 0,
            brhId: mainGrid.getSelectionModel().getSelected().data.BANK_ID,
            verId: mainGrid.getSelectionModel().getSelected().data.VER_ID,
            menuLevel: '1'
        });
    });
	var menuLvl1Expander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>交易代码描述：{txnDsp}</p>',
			'<p>末笔交易查询标志：{conFlag:this.getconFlag()}</p>',
			'<p>交易提示信息：{pptId}</p>',
			'<p>交易提示信息描述：{pptMsg}</p>',
			'<p>创建时间：{crtDate}</p>',
			'<p>更新时间：{updDate}</p>',{
				getconFlag : function(val){
					if(val == '1')
						return "<font color='green'>启用</font>";
					if(val == '0')
						return "<font color='red'>未启用</font>";
					return val;
				}
			}
		)
	});
	//一级菜单列模型
	var menuLvl1Model = new Ext.grid.ColumnModel([menuLvl1Expander,
			{header: '机构编号',dataIndex: 'brhId',width:80,hidden: true},
			{header: '版本号',dataIndex: 'verId',width:60,hidden: true},
			{header: '菜单编号',dataIndex: 'menuId',width:60},
			{header: '菜单显示内容',dataIndex: 'menuMsg',width:140,id:'menuMsg'},
    		{header: '菜单级别',dataIndex: 'menuLv',renderer: menuLevel,width:80,hidden: true},
    		{header: '交易代码',dataIndex: 'txnCode',width:60},
    		{header: '菜单状态',dataIndex: 'oprId',renderer: opreator,width:60}
    ]);
	
	
	//二级菜单数据集
	var menuLvl2Store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=eposMenuMsg'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'usageKey',mapping:  'USAGE_KEY'},   
			{name: 'brhId',mapping:'BRH_ID'},
			{name: 'menuId',mapping: 'MENU_ID'},
			{name: 'menuLv',mapping: 'MENU_LEVEL'},
			{name: 'menuMsg',mapping: 'MENU_MSG'},
			{name: 'menuPreId1',mapping: 'MENU_PRE_ID1'},
	      	{name: 'menuPreId2',mapping: 'MENU_PRE_ID2'},
			{name: 'txnCode',mapping: 'TXN_CODE'},
			{name: 'conFlag',mapping: 'CON_FLAG'},
			{name: 'pptId',mapping: 'PPT_ID'},
			{name: 'oprId',mapping: 'OPR_ID'},
			{name: 'verId',mapping: 'VER_ID'},
			{name: 'crtDate',mapping: 'CRT_DATE'},
			{name: 'updDate',mapping: 'UPD_DATE'},
			{name: 'txnDsp',mapping: 'TXN_DSP'},
			{name: 'pptMsg',mapping: 'PPT_MSG'}
		])
	});
	
	menuLvl2Store.on('beforeload', function() {
		menuLvl2Store.removeAll();
		menuLvl3Store.removeAll();
		menuLvl2Grid.getTopToolbar().items.items[2].disable();
		menuLvl3Grid.getTopToolbar().items.items[0].disable();
		menuLvl3Grid.getTopToolbar().items.items[2].disable();
        Ext.apply(this.baseParams, {
            start: 0,
            brhId: menuLvl1Grid.getSelectionModel().getSelected().data.brhId,
            verId: menuLvl1Grid.getSelectionModel().getSelected().data.verId,
            upMenuId1: menuLvl1Grid.getSelectionModel().getSelected().data.menuId,
            menuLevel: '2'
        });
    });
	
	var menuLvl2Expander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>交易代码描述：{txnDsp}</p>',
			'<p>末笔交易查询标志：{conFlag:this.getconFlag()}</p>',
			'<p>交易提示信息：{pptId}</p>',
			'<p>交易提示信息描述：{pptMsg}</p>',
			'<p>创建时间：{crtDate}</p>',
			'<p>更新时间：{updDate}</p>',{
				getconFlag : function(val){
					if(val == '1')
						return "<font color='green'>启用</font>";
					if(val == '0')
						return "<font color='red'>未启用</font>";
					return val;
				}
			}
		)
	});
	
	
	//二级菜单列模型
	var menuLvl2Model = new Ext.grid.ColumnModel([menuLvl2Expander,
			{header: '机构编号',dataIndex: 'brhId',width:80,hidden: true},
			{header: '版本号',dataIndex: 'verId',width:60,hidden: true},
			{header: '菜单编号',dataIndex: 'menuId',width:60},
			{header: '菜单显示内容',dataIndex: 'menuMsg',width:140,id:'menuMsg'},
    		{header: '菜单级别',dataIndex: 'menuLv',renderer: menuLevel,width:80,hidden: true},
    		{header: '交易代码',dataIndex: 'txnCode',width:60},
    		{header: '菜单状态',dataIndex: 'oprId',renderer: opreator,width:60}
    ]);
	
	//三级菜单数据集
	var menuLvl3Store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=eposMenuMsg'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'usageKey',mapping:  'USAGE_KEY'},   
			{name: 'brhId',mapping:'BRH_ID'},
			{name: 'menuId',mapping: 'MENU_ID'},
			{name: 'menuLv',mapping: 'MENU_LEVEL'},
			{name: 'menuMsg',mapping: 'MENU_MSG'},
			{name: 'menuPreId1',mapping: 'MENU_PRE_ID1'},
	      	{name: 'menuPreId2',mapping: 'MENU_PRE_ID2'},
			{name: 'txnCode',mapping: 'TXN_CODE'},
			{name: 'conFlag',mapping: 'CON_FLAG'},
			{name: 'pptId',mapping: 'PPT_ID'},
			{name: 'oprId',mapping: 'OPR_ID'},
			{name: 'verId',mapping: 'VER_ID'},
			{name: 'crtDate',mapping: 'CRT_DATE'},
			{name: 'updDate',mapping: 'UPD_DATE'},
			{name: 'txnDsp',mapping: 'TXN_DSP'},
			{name: 'pptMsg',mapping: 'PPT_MSG'}
		])
	});
	
	menuLvl3Store.on('beforeload', function() {
		menuLvl3Store.removeAll();
		menuLvl3Grid.getTopToolbar().items.items[2].disable();
        Ext.apply(this.baseParams, {
            start: 0,
            brhId: menuLvl2Grid.getSelectionModel().getSelected().data.brhId,
            verId: menuLvl2Grid.getSelectionModel().getSelected().data.verId,
            upMenuId1: menuLvl2Grid.getSelectionModel().getSelected().data.menuPreId1,
            upMenuId2: menuLvl2Grid.getSelectionModel().getSelected().data.menuId,
            menuLevel: '3'
        });
    });
	
	var menuLvl3Expander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>交易代码描述：{txnDsp}</p>',
			'<p>末笔交易查询标志：{conFlag:this.getconFlag()}</p>',
			'<p>交易提示信息：{pptId}</p>',
			'<p>交易提示信息描述：{pptMsg}</p>',
			'<p>创建时间：{crtDate}</p>',
			'<p>更新时间：{updDate}</p>',{
			getconFlag : function(val){
				if(val == '1')
					return "<font color='green'>启用</font>";
				if(val == '0')
					return "<font color='red'>未启用</font>";
				return val;
				}
			}
		)
	});
	
	
	//三级菜单列模型
	var menuLvl3Model = new Ext.grid.ColumnModel([menuLvl3Expander,
			{header: '机构编号',dataIndex: 'brhId',width:80,hidden: true},
			{header: '版本号',dataIndex: 'verId',width:60,hidden: true},
			{header: '菜单编号',dataIndex: 'menuId',width:60},
			{header: '菜单显示内容',dataIndex: 'menuMsg',width:140,id:'menuMsg'},
    		{header: '菜单级别',dataIndex: 'menuLv',renderer: menuLevel,width:80,hidden: true},
    		{header: '交易代码',dataIndex: 'txnCode',width:60},
    		{header: '菜单状态',dataIndex: 'oprId',renderer: opreator,width:60}
    ]);
	
	

	
	
	// 一级菜单列表
	var menuLvl1Grid = new Ext.grid.GridPanel({
		title: '一级菜单',
		flex: 1,
		store: menuLvl1Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'menuMsg',
		plugins: menuLvl1Expander,
		height: 280,
		//width: 400,
		cm: menuLvl1Model,
		loadMask: {
			msg: '正在加载一级菜单信息......'
		},
		tbar: [{
			xtype: 'button',
			text: '新增',
			name: 'add1',
			id: 'add1',
			iconCls: 'add',
			disabled: true,
			width: 75,
			handler:function() {
				var sel = mainGrid.getSelectionModel().getSelected();
				if(sel == null){
					showMsg("请选择一条终端菜单配置信息后操作.",mainGrid);return;
				}
				addMenu('1',sel);
			}
		},'-',{
			xtype: 'button',
			text: '修改',
			name: 'edit1',
			id: 'edit1',
			iconCls: 'edit',
			disabled: true,
			width: 75,
			handler:function() {
				var sel = menuLvl1Grid.getSelectionModel().getSelected();
				if(sel == null){
					showMsg("请选择一条一级菜单信息后操作.",mainGrid);return;
				}
				updateMenu('1',sel);
			}
		}]
	});
	
	menuLvl1Grid.getSelectionModel().on('rowselect',function() {
		menuLvl2Store.removeAll();
		menuLvl3Store.removeAll();
		menuLvl2Store.load();
		menuLvl1Grid.getTopToolbar().items.items[2].enable();
		menuLvl2Grid.getTopToolbar().items.items[0].enable();
		lastSelectLv1 = menuLvl1Grid.getSelectionModel().last;
	});
	
	// 二级菜单列表
	var menuLvl2Grid = new Ext.grid.GridPanel({
		title: '二级菜单',
		flex: 1,
		store: menuLvl2Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'menuMsg',
		plugins: menuLvl2Expander,
		height: 280,
		//width: 400,
		cm: menuLvl2Model,
		loadMask: {
			msg: '正在加载二级菜单信息......'
		},
		tbar: [{
			xtype: 'button',
			text: '新增',
			name: 'add2',
			id: 'add2',
			iconCls: 'add',
			disabled: true,
			width: 75,
			handler:function() {
				var sel = menuLvl1Grid.getSelectionModel().getSelected();
				if(sel == null){
					showMsg("请选择一条一级菜单信息后操作.",mainGrid);return;
				}
				addMenu('2',sel);
			}
		},'-',{
			xtype: 'button',
			text: '修改',
			name: 'edit2',
			id: 'edit2',
			iconCls: 'edit',
			disabled: true,
			width: 75,
			handler:function() {
				var sel = menuLvl2Grid.getSelectionModel().getSelected();
				if(sel == null){
					showMsg("请选择一条二级菜单信息后操作.",mainGrid);return;
				}
				updateMenu('2',sel);
			}
		}]
	});
	
	menuLvl2Grid.getSelectionModel().on('rowselect',function() {
		menuLvl3Store.removeAll();
		menuLvl3Store.load();
		menuLvl2Grid.getTopToolbar().items.items[2].enable();
		menuLvl3Grid.getTopToolbar().items.items[0].enable();
		lastSelectLv2 = menuLvl2Grid.getSelectionModel().last;
	});
	
	function opreator(val) {
		if(val == '1') 
			return "<font color='green'>启用</font>";
	    if(val == '0')
	        return "<font color='red''>作废</font>";
		return val;
	}
	
	// 三级菜单列表
	var menuLvl3Grid = new Ext.grid.GridPanel({
		title: '三级菜单',
		flex: 1,
		margins: '0',
		store: menuLvl3Store,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'menuMsg',
		plugins: menuLvl3Expander,
		height: 280,
		width: 400,
		cm: menuLvl3Model,
		loadMask: {
			msg: '正在加载三级菜单信息......'
		},
		tbar: [{
			xtype: 'button',
			text: '新增',
			name: 'add3',
			id: 'add3',
			iconCls: 'add',
			disabled: true,
			width: 75,
			handler:function() {
				var sel = menuLvl2Grid.getSelectionModel().getSelected();
				if(sel == null){
					showMsg("请选择一条二级菜单信息后操作.",mainGrid);return;
				}
				addMenu('3',sel);
			}
		},'-',{
			xtype: 'button',
			text: '修改',
			name: 'edit3',
			id: 'edit3',
			iconCls: 'edit',
			disabled: true,
			width: 75,
			handler:function() {
				var sel = menuLvl3Grid.getSelectionModel().getSelected();
				if(sel == null){
					showMsg("请选择一条三级菜单信息后操作.",mainGrid);return;
				}
				updateMenu('3',sel);
			}
		}]
	});
	
	menuLvl3Grid.getSelectionModel().on('rowselect',function() {
		menuLvl3Grid.getTopToolbar().items.items[2].enable();
		lastSelectLv3 = menuLvl3Grid.getSelectionModel().last;
	});
	
	var mainPanel = new Ext.Panel({
		title: '终端菜单详细配置',
		iconCls: 'detail',
		region: 'south',
		anchor: '100%',
		height: 318,
		layout: {
			type: 'hbox',
			padding: '5',
			align: 'top'
		},
		defaults: {
			margins: '0 5 0 0'
		},
		autoScroll: true,
		items: [menuLvl1Grid,menuLvl2Grid,menuLvl3Grid]
	});
	
	//Main View
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mainGrid,mainPanel],
		renderTo: Ext.getBody()
	});
	
});