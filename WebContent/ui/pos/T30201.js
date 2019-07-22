Ext.onReady(function() {
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_NO01',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 550,
        autoHeight: true,
        labelWidth: 80,
        items: [new Ext.form.TextField({
                id: 'termIdQ',
                name: 'termId',
                fieldLabel: '终端ID',
                width: 150
            }),
            {
        	xtype:'textfield',
        	fieldLabel: '终端号',
        	id:'acequipmentIdQ',
        	name:'acequipmentIdQ',
        	width:300
        },
        {
        	xtype:'textfield',
        	fieldLabel: '商户号',
        	id:'acmchntIdQ',
        	name:'acmchntIdQ',
        	width:300
        },{
                xtype: 'combo',
                fieldLabel: '商户ID',
                store: mchntStore,
                hiddenName: 'mchtCd',
                id: 'mchtCdQ',
                displayField: 'displayField',
                valueField: 'valueField',
                width: 420
           },{                                         
                xtype: 'combo',
                fieldLabel: '终端状态',
                id: 'termStaQ',
                name: 'termSta',
                width: 150,
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    //0：新增待审核 1：待装机 2 ：已装机 3：修改待审核4：冻结 5：冻结待审核 6:解冻待审核7：注销8：注销待审核 9：撤机A:新增退回 
 
                    data: [['0','新增待审核'],['1','待装机'],['2','已装机'],['3','修改待审核'],['4','冻结'],['5','冻结待审核'],['6','解冻待审核'],['7','注销'],['8','注销待审核'],['9','撤机'],['A','新增退回']]
                })
        },{
              width: 150,
              xtype: 'datefield',
              fieldLabel: '起始时间',
              format : 'Y-m-d',
              name :'startTime',
              id :'startTime',
              anchor :'60%'       
        },{                                         
              width: 150,
              xtype: 'datefield',
              fieldLabel: '截止时间',
              format : 'Y-m-d',
              name :'endTime',
              id :'endTime',
              anchor :'60%'       
        }],
        buttons: [{
            text: '查询',
            handler: function() 
            {
            	var endtime=Ext.getCmp('endTime').getValue(),starttime=Ext.getCmp('startTime').getValue();
            	if(endtime!=''&&starttime!=''&&endtime<starttime){
            		showErrorMsg("请保证截止时间不小于起始时间",grid);
    				return;
            	}
                termStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=termInfoForAudit'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'termId',mapping: 'termId'},
            {name: 'mchtCd',mapping: 'mchtCd'},
            {name: 'termSta',mapping: 'termSta'},
            {name: 'mappingMchntcdOne',mapping: 'mappingMchntcdOne'},
			{name: 'mappingTermidOne',mapping: 'mappingTermidOne'},
			{name: 'mappingMchntcdTwo',mapping: 'mappingMchntcdTwo'},
			{name: 'mappingTermidTwo',mapping: 'mappingTermidTwo'},
            {name: 'termSignSta',mapping: 'termSignSta'},
            {name: 'termIdId',mapping: 'termIdId'},
            {name: 'termFactory',mapping: 'termFactory'},
            {name: 'termMachTp',mapping: 'termMachTp'},
            {name: 'termVer',mapping: 'termVer'},
            {name: 'termTp',mapping: 'termTp'},
            {name: 'termBranch',mapping: 'termBranch'},
            {name: 'termIns',mapping: 'termIns'},
            {name: 'recCrtTs',mapping: 'recCrtTs'}
        ]),
        autoLoad: true
    });
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termIdQ').getValue(),
            acmchntId: Ext.getCmp('acmchntIdQ').getValue(),
            acequipmentId: Ext.getCmp('acequipmentIdQ').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
            mchtCd: Ext.getCmp('mchtCdQ').getValue(),
            termSta: Ext.getCmp('termStaQ').getValue()
        });
    });
    
    
    var termColModel = new Ext.grid.ColumnModel([
        {id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
        {id: 'mchtCd',header: '商户号',dataIndex: 'mchtCd',width: 200,renderer:function(val){return getRemoteTrans(val, "mchntName");}},
        {header: '一卡通商户号',dataIndex: 'mappingMchntcdOne',width: 100},
		{header: '一卡通终端号',dataIndex: 'mappingTermidOne',width: 100},
		{header: '银联商户号',dataIndex: 'mappingMchntcdTwo',width: 100},
		{header: '银联终端号',dataIndex: 'mappingTermidTwo',width: 100},
        {header: '终端状态',dataIndex: 'termSta',width: 100,renderer: termSta},
        {header: '终端所属机构',dataIndex: 'termBranch',width:200,renderer:function(val){return getRemoteTrans(val, "brhName");}},
        {header: '终端库存编号',dataIndex: 'termIdId'},
        {header: '终端厂商',dataIndex: 'termFactory',width: 100},
        {header: '终端型号',dataIndex: 'termMachTp',renderer:function(val){return getRemoteTrans(val, "sysPara")}}
    ]);
	
    
	var qryMenu = {
        text: '详细信息',
        width: 85,
        iconCls: 'detail',
        disabled: true,
        handler:function() {
            var rec = grid.getSelectionModel().getSelected();
            if(rec == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            selectTermInfo(rec.get('termId'),rec.get('mchtCd'));	
        }	
    };
	
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认通过吗？',grid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交信息，请稍后......');
					var rec = grid.getSelectionModel().getSelected();
                    if(rec == null)
		            {
		                showAlertMsg("没有选择记录",grid);
		                return;
		            }  
					Ext.Ajax.requestNeedAuthorise({
						url: 'T30201Action.asp',
						params: {
							termId: rec.get('termId'),
                            mchtCd: rec.get('mchtCd'),
                            termSta: rec.get('termSta'),
							txnId: '30201',
							subTxnId: '0'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,grid);
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
							// 重新加载终端待审核信息
							grid.getStore().reload();
						}
					});
					hideProcessMsg();
                    grid.getTopToolbar().items.items[0].disable();
	                grid.getTopToolbar().items.items[1].disable();
	                grid.getTopToolbar().items.items[2].disable();
				}
			});
		}
	};
	
	var refuseMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		handler:function() {
            showConfirm('确认拒绝吗？',grid,function(bt) {
                if(bt == 'yes') {
                	showInputMsg('提示','请输入拒绝原因',true,refuse);
                
                }
            });
		}
	};
	var backMenu = {
			text: '新增退回修改',
			width: 85,
			iconCls: 'stop',
			disabled: true,
			handler:function() {
	            showConfirm('确认退回吗？',grid,function(bt) {
	                if(bt == 'yes') {
	                	showInputMsg('提示','请输入退回修改原因',true,function(bt,text){

	                		if(bt == 'ok') {
	                			if(getLength(text) > 60) {
	                				alert('退回原因最多可以输入30个汉字或60个字母、数字');
	                				showInputMsg('提示','请输入拒绝原因',true,refuse);
	                				return;
	                			}
	                			showProcessMsg('正在提交信息，请稍后......');
	                		    var rec = grid.getSelectionModel().getSelected();
	                            if(rec == null)
	                            {
	                                showAlertMsg("没有选择记录",grid);
	                                return;
	                            }  
	                		    Ext.Ajax.requestNeedAuthorise({
	                		        url: 'T30201Action.asp',
	                		        params: {
	                		            termId: rec.get('termId'),
	                		            mchtCd: rec.get('mchtCd'),
	                		            termSta: rec.get('termSta'),
	                		            txnId: '30201',
	                		            subTxnId: '2',
	                		            refuseInfo: text
	                		        },
	                		        success: function(rsp,opt) {
	                		            var rspObj = Ext.decode(rsp.responseText);
	                		            if(rspObj.success) {
	                		                showSuccessMsg(rspObj.msg,grid);
	                		            } else {
	                		                showErrorMsg(rspObj.msg,grid);
	                		            }
	                		            // 重新加载终端待审核信息
	                		            grid.getStore().reload();
	                		        }
	                		    });
	                		    hideProcessMsg();
	                            grid.getTopToolbar().items.items[0].disable();
	                            grid.getTopToolbar().items.items[1].disable();
	                            grid.getTopToolbar().items.items[2].disable();
	                            grid.getTopToolbar().items.items[3].disable();
	                		}
	                	
	                	});
	                
	                }
	            });
			}
		};
	
	// 终端拒绝按钮调用方法
	function refuse(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
		    var rec = grid.getSelectionModel().getSelected();
            if(rec == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }  
		    Ext.Ajax.requestNeedAuthorise({
		        url: 'T30201Action.asp',
		        params: {
		            termId: rec.get('termId'),
		            mchtCd: rec.get('mchtCd'),
		            termSta: rec.get('termSta'),
		            txnId: '30201',
		            subTxnId: '1',
		            refuseInfo: text
		        },
		        success: function(rsp,opt) {
		            var rspObj = Ext.decode(rsp.responseText);
		            if(rspObj.success) {
		                showSuccessMsg(rspObj.msg,grid);
		            } else {
		                showErrorMsg(rspObj.msg,grid);
		            }
		            // 重新加载终端待审核信息
		            grid.getStore().reload();
		        }
		    });
		    hideProcessMsg();
            grid.getTopToolbar().items.items[0].disable();
            grid.getTopToolbar().items.items[1].disable();
            grid.getTopToolbar().items.items[2].disable();
            grid.getTopToolbar().items.items[3].disable();
		}
	}
    
	
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
    
    
    
    var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
	var menuArr = new Array();
	
	menuArr.push(acceptMenu);	//[0]
	menuArr.push(refuseMenu);	//[1]
	menuArr.push(backMenu);	    //[2]
	menuArr.push(qryMenu);      //[3]
	menuArr.push(queryMenu);    //[4]
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: 'POS终端审核',
		iconCls: 'T30201',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
//		autoExpandColumn: 'mchntCd',
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    


	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据所选择的终端状态判断哪个按钮可用
			//0：新增待审核 1：待装机 2 ：已装机 3：修改待审核4：冻结 5：冻结待审核 
			//6:解冻待审核7：注销8：注销待审核 9：撤机 A:新增退回  R:注销恢复审核
			
			var rec = grid.getSelectionModel().getSelected();
			var sta = rec.get('termSta');
			
			if(sta=='0'||sta=='3'||sta=='5'||sta=='6'||sta=='8'||sta=='R'){
				grid.getTopToolbar().items.items[0].enable();
                grid.getTopToolbar().items.items[1].enable();
                grid.getTopToolbar().items.items[3].enable();
                if(sta=='0'){
                	grid.getTopToolbar().items.items[2].enable();
                }else{
                	grid.getTopToolbar().items.items[2].disable();
                }
			}else{
				grid.getTopToolbar().items.items[0].disable();
                grid.getTopToolbar().items.items[1].disable();
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[3].disable();
			}
           
          
		}
	});
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});