Ext.onReady(function() {
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtAwaitCheckInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mcht_no'},
			{name: 'rctCheckDate',mapping: 'rct_check_date'},
			{name: 'checkFrqc',mapping: 'check_frqc'},
			{name: 'planCheckDate',mapping: 'plan_check_date'},
			{name: 'mchtNm',mapping: 'mcht_nm'},
			{name: 'addr',mapping: 'addr'},
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'comm_tel'},
			{name: 'agr_br',mapping: 'agr_br'}
		])
	});
	
	gridStore.load({
		params:{start: 0}
	});
	
	var infRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
				'<font color="blue">商户名称：</font>{mchtNm}</br>',
				'<font color="blue">商户地址：</font>{addr}</br>',
				'<font color="blue">联系人：</font>{contact}</br>',
				'<font color="blue">联系电话：</font>{commTel}</br>'
		)
	});
	 var sm = new Ext.grid.CheckboxSelectionModel();
	 sm.handleMouseDown = Ext.emptyFn;
	var colModel = new Ext.grid.ColumnModel([
         new Ext.grid.RowNumberer(),
         sm,
        {header: '归属机构',dataIndex: 'agr_br',width: 160},
	    {id: 'idMchtNo',header: '商户号',dataIndex: 'mchtNo',width: 120},
	    {header: '商户名',dataIndex: 'mchtNm',width: 220},
	    {header: '商户地址',dataIndex: 'addr',width: 280},
	    {header: '联系人',dataIndex: 'contact',width: 80},
	    {header: '联系电话',dataIndex: 'commTel'},
	    {header: '最近巡检日期',dataIndex: 'rctCheckDate'},
	    {header: '巡检频率(天)',dataIndex: 'checkFrqc'},
	    {header: '手动巡检日期',dataIndex: 'planCheckDate'}
	    ]);

	function transFlag(val){
		if(val == '0')
			return '已巡检';
		else if(val == '1')
			return '待巡检';
		else
			return val;
	}
	
	var checkMenu = {
			text: '巡检',
			width: 85,
			iconCls: 'T801',
			disabled: true,
			handler: function() {
				if(grid.getSelectionModel().hasSelection()) {
					var rec = grid.getSelectionModel().getSelections();
					var mchtArray=new Array();
					for(var i=0;i<rec.length;i++){
						mchtArray[i]=rec[i].get('mchtNo');
					}
					showConfirm('确定要手动巡检该商户吗？',grid,function(bt) {
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T20801Action.asp?method=check',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,grid);
										grid.getStore().reload();
										//grid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,grid);
									}
								},
								params: { 
//									mchtNo: rec.get('mchtNo'),
									mchtArray:mchtArray,
									txnId: '20801',
									subTxnId: '04'
								}
							});
						}
					});
				}else{
					showErrorMsg("请选择需要巡检的商户",grid);
					return;
				}
			}
		};
		
	var topQueryPanel = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 440,
        autoHeight: true,
        labelWidth: 80,
		items: [{
				xtype : 'basecomboselect',
				baseParams: 'BRH_BELOW',
				labelStyle: 'padding-left: 5px',
				fieldLabel : '归属机构',
				hiddenName : 'agrBr',
				id : 'agrBrId',
				anchor: '90%'
			},{
				xtype : 'basecomboselect',
				baseParams: 'ORGAN',
				labelStyle: 'padding-left: 5px',
				fieldLabel : '外包商',
				hiddenName : 'thirdBrhId',
				id : 'thirdId',
				anchor: '90%'
			},{
                xtype: 'dynamicCombo',
                fieldLabel: '商户',
                methodName: 'getMchntIdInBase',
                labelStyle: 'padding-left: 5px',
                hiddenName: 'mchtCd',
                id: 'mchtCdQ',
                displayField: 'displayField',
                valueField: 'valueField',
                anchor: '99%'
           }],
		buttons: [{
			text: '查询',
			handler: function() 
			{
			    gridStore.load();
                queryWin.hide();
			}
		},{
			text: '重填',
			handler: function() {
				topQueryPanel.getForm().reset();
			}
		}]
	});
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
        width: 530,
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
	
	var report = {
		text: '导出巡检信息',
		width: 160,
		id: 'report',
		iconCls: 'download',
		handler:function() {
			showMask("正在为您准备报表，请稍后。。。",grid);
			Ext.Ajax.requestNeedAuthorise({
				url: 'T2080201Action.asp',
				params: {
					agrBrId:topQueryPanel.findById('agrBrId').getValue(),
					thirdId:topQueryPanel.findById('thirdId').getValue(),
					txnId: '2080201',
					subTxnId: '01'
				},
				success: function(rsp,opt) {
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													rspObj.msg;
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				},
				failure: function(){
					hideMask();
				}
			});
		}
	};
		
	var menuArr = new Array();
	
	menuArr.push(checkMenu);
	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(report);

	var grid = new Ext.grid.EditorGridPanel({
		title: '商户巡检信息查询及维护',
		iconCls : 'T20101',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		sm:sm,
		cm: colModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[0].enable();
		}
	});	
	
	//信息添加表单    
	var infoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		labelWidth: 80,
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号*',
			methodName: 'getMchntIdfor20801',
			hiddenName: 'mchtNo',
			allowBlank: false,
			editable: true,
			width: 240
		},{
			xtype: 'datefield',
			fieldLabel: '巡检日期*',
			id: 'checkDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			editable: false,
			width: 160
		},{
			xtype: 'textfield',
			fieldLabel: '巡检人姓名',
			maxLength: 32,
			vtype: 'isOverMax',
			width:160,
			id: 'checkName'
		},{
        	xtype: 'textarea',
        	width:160,
        	height: 120,
        	maxLength: 512,
			fieldLabel: '巡检情况备注',
			vtype: 'isOverMax',
			id: 'checkInf'
    	}]
	});
	
	//新增Win
	var addWin = new Ext.Window({
		title: '商户巡检信息维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 360,
		autoHeight: true,
		layout: 'fit',
		items: [infoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(infoForm.getForm().isValid()) {
					infoForm.getForm().submitNeedAuthorise({
						url: 'T20801Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,infoForm);
							infoForm.getForm().reset();
							grid.getStore().reload();
							addWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,infoForm);
							addWin.hide();
						},
						params: {
							txnId: '20801',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				infoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addWin.hide(grid);
			}
		}]
	});
	
	gridStore.on('beforeload', function(){
		grid.getTopToolbar().items.items[0].disable();
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNo:Ext.getCmp('mchtCdQ').getValue(),
			agrBrId:Ext.getCmp('agrBrId').getValue(),
			thirdId:Ext.getCmp('thirdId').getValue()
		});
	});
	grid.getSelectionModel().on({
		'rowselect':function(){
		grid.getTopToolbar().items.items[0].enable();
		}
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});