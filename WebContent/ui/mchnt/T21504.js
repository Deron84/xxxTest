Ext.onReady(function() {
	var _ruleID;
	var _mchntNO;
	var _termNO;
	var allData ;
	Ext.ux.form.LovCombo = Ext.form.LovCombo || Ext.ux.form.LovCombo;  
	function hideLabel(sId, bShow){ 
		Ext.getCmp(sId).getEl().up('.x-form-item').child('.x-form-item-label').setDisplayed(bShow);
        Ext.getCmp(sId).getEl().up('.x-panel').setDisplayed(bShow); 
	} 
	var sudyBindStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtMarkSubsidyBind'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
            {name: 'recordID',mapping: 'RECORD_ID'},
			{name: 'ruleID',mapping: 'RULE_ID'},
			{name: 'mchntNO',mapping: 'MCHNT_NO'},
			{name: 'termNO',mapping: 'TERM_NO'},
			{name: 'openFlag',mapping: 'OPEN_FLAG'},
			{name: 'mchtNM',mapping: 'MCHT_NM'},			
			{name: 'brhName',mapping: 'BRH_NAME'},
            {name:'bindTime',mapping:'BIND_TIME'},
            {name:'modifyTime',mapping:'MODIFY_TIME'},
            {name:'remark',mapping:'REMARK'}
		]),
		autoLoad:true
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect: true});
    sm.handleMouseDown = Ext.emptyFn;
	var sudyBindColModel = new Ext.grid.ColumnModel([
	    sm,
	    {header: '主键',dataIndex: 'recordID',width:80,align:'center',hidden:true},
        {header: '规则ID',dataIndex: 'ruleID',width:160,align:'center'},
        {header: '商户号',dataIndex: 'mchntNO',width:160,align:'center'},
		{header: '终端号',dataIndex: 'termNO',width:120,align:'center'},
		{header: '是否开启',dataIndex: 'openFlag',width:100,align:'center'},
		{header: '商户名称',dataIndex: 'mchtNM',width:200,align:'center'},
        {header: '机构名称',dataIndex: 'brhName',width:150,align:'center'},
        {header: '绑定时间',dataIndex: 'bindTime',width:140,align:'center'},
		{header: '修改时间',dataIndex: 'modifyTime',width:140,align:'center',hidden:true},
		{header: '备注',dataIndex: 'remark',width:140,align:'center'}
	]);
	
	   //终端ID
    var terminalStore= new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	
    // 商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
    //规则ID
    var ruleStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
   var termPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 250,
        width: 730,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1New',
                layout: 'column',
                frame: true,
                items: [{
                    columnWidth:.5,
                    layout:'form',
                    labelWidth: 80,
                    items:[{
                     xtype: 'combo',
                     fieldLabel: '补贴类型*',
          //           labelStyle: 'padding-left: 20px',
                     id: 'subsidyType1',
                     hiddenName:'subsidyTypeNM1',
                     allowBlank: false,
                     width:180,
                     store: new Ext.data.ArrayStore({
                         fields: ['valueField','displayField'], 
                         data: [['00','满额补'],['01','按比例补']]
                     }),
                     listeners:{
	                   	   'select':function(){
		                   		Ext.getCmp('selectMchnt').setValue('0');
		                    	Ext.getCmp('selectMchntId').setValue('1');
		               		   	var type=Ext.getCmp("subsidyType1").getValue();
		        //       		   	var ruleid;
		               		   	SelectOptionsDWR.getComboDataWithParameter('RULE_NAME',type,function(ret){
		               		   		ruleStore.loadData(Ext.decode(ret));
		               		   		Ext.getCmp('subsidyRule1').reset();
		             //  		   		ruleid =Ext.decode(ret).data[0].valueField;
		             //  		   		Ext.getCmp('subsidyRule1').setValue(ruleid);
		                       	});
		             //  			SelectOptionsDWR.getComboDataWithParameter('OPEN_TYPE',ruleid,function(ret){
		              // 		   		Ext.getCmp('openType1').setValue(Ext.decode(ret).data[0].valueField);
		              //         	});
		                   		if(document.querySelector('.ux-combo-selectall-icon')){
		                    		if(Ext.get(document.querySelector('.ux-combo-selectall-icon')).hasClass('ux-combo-selectall-icon-checked')){
		                    			Ext.get(document.querySelector('.ux-combo-selectall-icon')).removeClass('ux-combo-selectall-icon-checked').addClass('ux-combo-selectall-icon-unchecked');
		                    		}
		                    	}
	                   	   }
                    }
                 }]
                },{
                    columnWidth:.5,
                    labelWidth: 80,
                    layout:'form',
                    items:[{
		                 id: 'openType1',
		                 hiddenName: 'openTypeNM1',
		                 displayField: 'displayField',
		                 valueField: 'valueField',
		                 xtype: 'combo',
		                 store: new Ext.data.ArrayStore({
	                         fields: ['valueField','displayField'], 
	                         data: [['00','依据商户开启'],['01','依据终端开启'],['02','依据卡bin开启']] 
	                     }),
		         //        labelStyle: 'padding-left: -50px',
		                 fieldLabel: '开启方式*',
		                 disabled : true,
		                 editable : false,
		             	 readOnly : true,
		                 width: 180
                    }]
                },{
                    columnWidth: 1,
                    layout:'form',
                    labelWidth: 80,
                    items:[{
		                 id: 'subsidyRule1',
		       //          labelStyle: 'padding-left: 20px',
		                 hiddenName: 'subsidyRuleNM1',
		                 displayField: 'displayField',
		                 valueField: 'valueField',
		                 xtype: 'basecomboselect',
		                 store: ruleStore,
		                 fieldLabel: '补贴规则*',
		                 editable : true,
		                 width: 300,
		                 listeners:{
		                    'select':function () {
		                    	Ext.getCmp('selectMchnt').enable();
		            			Ext.getCmp('selectMchntId').enable();
		                        var ruleid=Ext.getCmp('subsidyRule1').getValue();
		                        SelectOptionsDWR.getComboDataWithParameter('OPEN_TYPE',ruleid,function(ret){
		                        	Ext.getCmp('openType1').setValue(Ext.decode(ret).data[0].valueField);
//		                        	var midTmp = Ext.decode(ret).data[0].valueField;
//		                        	if(midTmp == '00'){
//		                        		Ext.getCmp('openType1').setValue('依据商户开启');
//		                        	}else if(midTmp == '01'){
//		                        		Ext.getCmp('openType1').setValue('依据终端开启');
//		                        	}else if(midTmp == '02'){
//		                        		Ext.getCmp('openType1').setValue('依据卡bin开启');
//		                        	}
		                       	});
		                        SelectOptionsDWR.getMchntData('MCHNT_NAME',function(ret){
		                        	mchntStore.loadData(Ext.decode(ret));
			                    });
		                        Ext.getCmp('mchntNO2').reset();
		                        terminalStore.removeAll();
		                    },
		                  
		                }
                    }]
                },{
                    columnWidth:.25,
                    labelWidth: 80,
                    layout:'form',
                    items:[{
                        xtype:'checkbox',
                        id:'selectMchnt',
                        name:'selectMchnt',
                        fieldLabel:'',
                        boxLabel:'所有商户',
            //            labelStyle: 'padding-left: 20px',
                        listeners:{
                        	'afterrender':function(obj){  
                                obj.getEl().dom.onclick = function(e){     
                             		allData = 1;
                                	Ext.getCmp('selectMchntId').setValue('0');
                                	Ext.getCmp('selectMchnt').setValue('1');
                                	Ext.getCmp('mchntNO2').hide().disable();
                                	hideLabel('mchntNO2',false);
                                	Ext.getCmp('termNO2').hide().disable();
                                	hideLabel('termNO2',false);	
                                	Ext.getCmp('mchntNO2').allowBlank=true;
                                	Ext.getCmp('termNO2').allowBlank=true;
                                	terminalStore.removeAll();
                        			Ext.getCmp('mchntNO2').reset();
                        			Ext.getCmp('termNO2').reset();
                                }  
                            }   
                        }
                        
                    }]
                },{
                    columnWidth:.25,
                    labelWidth: 80,
                    layout:'form',
                    items:[{
                        xtype:'checkbox',
                        id:'selectMchntId',
                        checked:true,
                        name:'selectMchntId',
                        fieldLabel:'',
                        boxLabel:'指定商户',
                        listeners:{
                        	'afterrender':function(obj){  
                                obj.getEl().dom.onclick = function(e){
                                	Ext.getCmp('selectMchnt').setValue('0');
                                	Ext.getCmp('selectMchntId').setValue('1');
                                	Ext.getCmp('mchntNO2').show().enable();
                                 	hideLabel('mchntNO2',true);
                                	Ext.getCmp('termNO2').show().enable();
                                 	hideLabel('termNO2',true);
                                	Ext.getCmp('mchntNO2').allowBlank=false;
                                	Ext.getCmp('termNO2').allowBlank=true;
                                	Ext.getCmp('mchntNO2').reset();
                        			Ext.getCmp('termNO2').reset();
                                }  
                            }
                        }
                    }]
                },{
                    columnWidth:1,
                    layout:'form',
                    labelWidth: 80,
                    items:[new Ext.ux.form.LovCombo({
					    	id: 'mchntNO2',
						    hiddenName:'mchntNONM2',
					    	store:mchntStore,
			//		    	labelStyle: 'padding-left: 20px',
					    	fieldLabel: '商户号*',
						    displayField: 'displayField',
						    valueField: 'valueField',
						    triggerAction   : "all",
						    showSelectAll   : true, 
						    allowBlank: false,
						    editable:true,
						    width:300,
                            listeners:{
                                'select':function (that,record,index) {
                                	console.log(that,record,index);	
                                    var mchntid=Ext.getCmp('mchntNO2').getValue(),selAll=Ext.getCmp('selectMchnt').getValue(),selOne=Ext.getCmp('selectMchntId').getValue();
                                    var mchntArr=mchntid.split(',').length,allLen=mchntStore.length;
                                    if(selAll){
                                    	allData=1;
                                    }else{
                                    	showProcessMsg('正在加载终端信息，请稍后......');
                                    	if(document.querySelectorAll('.ux-combo-selectall-icon')[1]){
                                    		if(Ext.get(document.querySelectorAll('.ux-combo-selectall-icon')[1]).hasClass('ux-combo-selectall-icon-checked')){
                                    			Ext.get(document.querySelectorAll('.ux-combo-selectall-icon')[1]).removeClass('ux-combo-selectall-icon-checked').addClass('ux-combo-selectall-icon-unchecked');
                                    		}
                                    	}
                                    	SelectOptionsDWR.getComboDataWithParameter('MAPPING_MCHNTCD',mchntid,function(ret){
                                    		terminalStore.loadData(Ext.decode(ret));
                                    		hideProcessMsg();
                                    	});
                                    	allData=2;
                                    }  
                                }
//                                'afterrender':function(obj){
//                                	obj.getEl().up('.x-form-field-wrap').dom.onclick=function(){
//                                		
//                                		if(Ext.get(document.querySelector('.ux-combo-selectall-icon')).hasClass('ux-combo-selectall-icon-unchecked')){
//                                			document.querySelector('.ux-combo-selectall-icon').onclick=function(){
//                      				 		   	var mchntids=Ext.getCmp('mchntNO2').getValue();
//                      				 		   	showProcessMsg('正在加载终端信息，请稍后......');
//                                                SelectOptionsDWR.getComboDataWithParameter('MAPPING_MCHNTCD',mchntids,function(ret){
//                                                	terminalStore.loadData(Ext.decode(ret));
//                                                	hideProcessMsg();
//                                                });
////                                                Ext.getCmp('mchntIdN').collapse();
////                                                Ext.getCmp('equipmentIdN').reset();
//                                			}
//                     			    }
//                                	}
//                    			     
//                                }
                                
                            }
					    })]
                },{
                    columnWidth:1,
                    labelWidth: 80,
                    layout:'form',
                    items:[new Ext.ux.form.LovCombo({
					    	id: 'termNO2',
						    hiddenName:'termNONM2',
					    	store:terminalStore,
			//		    	labelStyle: 'padding-left: 20px',
					    	fieldLabel: '终端号*',
						    displayField: 'displayField',
						    valueField: 'valueField',
						    triggerAction   : "all",
						    showSelectAll   : true, 
						    allowBlank: false,
						    editable:false,
						    width:300,
						    listeners:{
						    	'select':function(){
						    		var eqData=Ext.getCmp('termNO2').getValue(),eqLen=eqData.length,allLen=terminalStore.length;
						    		if(eqLen==0){
						    			allData=2;
						    		}else{
						    			allData=3;
						    		}
						    	}
//						    	'afterrender':function(obj){
//						    		obj.getEl().up('.x-form-field-wrap').dom.onclick=function(){
//						    			if(Ext.get(document.querySelectorAll('.ux-combo-selectall-icon')[1]).hasClass('ux-combo-selectall-icon-unchecked')){
//						    				document.querySelectorAll('.ux-combo-selectall-icon')[1].onclick=function(){
//						    					allData=3;
//						    					Ext.getCmp('equipmentIdN').collapse();
//						    				}
//						    			}
//						    		}
//						    	}
						    }
					    })]
                },{
	                columnWidth: 1,
	                layout: 'form',
	                labelWidth: 80,
	                items: [{
	                    xtype: 'combo',
	                    fieldLabel: '开关状态*',
//	                    labelStyle: 'padding-left: 20px',
	                    id: 'openFlag1',
	                    hiddenName:'openFlagNM1',
	                    allowBlank: false,
	                    width:180,
	                    value: '0',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'], 
	                        data: [['1','开启'],['0','关闭']]
	                    })
	                }]
              }]
        }]		
    });
    /**************  终端表单  *********************/
	var termForm = new Ext.form.FormPanel({
		frame: true,
		height: 250,
		width: 750,
		labelWidth: 140,
		waitMsgTarget: true,
		layout: 'column',
		items: [termPanel]
	});
	
	 /***********  终端信息窗口  *****************/
	var termWin = new Ext.Window({
		title: '商户补贴规则绑定',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 750,
		autoHeight: true,
		layout: 'fit',
		items: [termForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'T201',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(termForm.getForm().isValid()) {
					termForm.getForm().submitNeedAuthorise({
						url: 'T21504Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessDtl(action.result.msg,termWin);
							disid = '';
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							termForm.getForm().reset();
							terminalStore.removeAll();
							mchntStore.removeAll();
                            termWin.hide();
						},
						failure: function(form,action) {
							termPanel.setActiveTab('info1New');
							showErrorMsg(action.result.msg,termWin);
						},
						params: {
							ruleID:Ext.getCmp("subsidyRule1").getValue(),
							mchntNO:Ext.getCmp("mchntNO2").getValue(),
							termNO:Ext.getCmp("termNO2").getValue(),
							isOpen:Ext.getCmp("openFlag1").getValue(),
							all:allData,
							txnId: '21504',
							subTxnId: '01'
						}
					});

			}else{
				termPanel.setActiveTab('info1New');
				termForm.getForm().isValid();
			}
		}
		},{
			text: '重置',
			handler: function() {
				termForm.getForm().reset();
				terminalStore.removeAll();
				mchntStore.removeAll();
			}
		},{
			text: '关闭',
			handler: function() {
				termWin.hide();
				termForm.getForm().reset();
				terminalStore.removeAll();
				mchntStore.removeAll();
			}
		}]
	});

	
	var addMenu = {
		text: '添加绑定',
		width: 85,
		iconCls: 'add',
		handler:function() {
			termWin.show();
			termWin.center();
			Ext.getCmp('mchntNO2').disable();
			Ext.getCmp('termNO2').disable();
			Ext.getCmp('selectMchnt').disable();
			Ext.getCmp('selectMchntId').disable();
			Ext.getCmp('mchntNO2').show().enable();
		  	hideLabel('mchntNO2',true);
        	Ext.getCmp('termNO2').show().enable();
         	hideLabel('termNO2',true);
        	Ext.getCmp('mchntNO2').allowBlank=false;
        	Ext.getCmp('termNO2').allowBlank=true;
			terminalStore.removeAll();
			mchntStore.removeAll();
			ruleStore.removeAll();
		}
	};
	
	 //查询
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 100,
        items: [{
			columnWidth : 1,
			layout : 'form',
			labelWidth: 80,
			items : [ {
				xtype : 'textfield',
	//			labelStyle: 'padding-left: 10px',
				fieldLabel : '规则ID',
	//			allowBlank : false,
				id : 'ruleID1',
				name : 'ruleIDNM1',
	//			disabled : true,
				width : 200			
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			labelWidth: 80,
			items : [ {
				xtype : 'textfield',
				fieldLabel : '商户号',
	//			labelStyle: 'padding-left: 10px',
	//			allowBlank : false,
				id : 'mchntNO1',
				name : 'mchntNONM1',
	//			disabled : true,
				width : 200		
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			labelWidth: 80,
			items : [ {
				xtype : 'textfield',
				fieldLabel : '终端号',
	//			labelStyle: 'padding-left: 10px',
	//			allowBlank : false,
				id : 'termNO1',
				name : 'termNONM1',
//				disabled : true,
				width : 200		
			}]
		}],
        buttons: [{
            text: '查询',
            handler: function() 
            {
            	_ruleID=Ext.getCmp('ruleID1').getValue();
            	_mchntNO=Ext.getCmp('mchntNO1').getValue();
            	_termNO=Ext.getCmp('termNO1').getValue();
            	disid = '';
            	sudyBindStore.load();
            	queryWin.hide();
            	grid.getTopToolbar().items.items[2].disable();
                
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
    sudyBindStore.on('beforeload', function(){
    	Ext.select('td.x-grid3-cell-first').setStyle({ visibility:'hidden'});
		Ext.apply(this.baseParams, {
			start: 0,
			ruleID : _ruleID||disid,
    		mchntNO : _mchntNO,
    		termNO : _termNO
		});
	});	
   var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 500,
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
        },{
            id: 'close',
            handler: function(event,toolEl,panel,tc) { 
                queryWin.hide();
                topQueryPanel.getForm().reset();
            },
            qtip: '关闭'
        }]
    });
	
	var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
        	queryWin.show();
        	topQueryPanel.getForm().reset();
        }
    };
	
	var delMenu = {
        text: '删除',
        width: 85,
        iconCls: 'delete',                                     
        disabled: true,
        handler:function() {
        	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }  
	        showConfirm('确定要删除该规则吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在提交信息，请稍后......');
                    rec = grid.getSelectionModel().getSelected();
                     Ext.Ajax.requestNeedAuthorise({
                        url: 'T21504Action.asp?method=delete',
                        params: {
                        	recordID: selectedRecord.get('recordID'),
                            txnId: '21504',
                            subTxnId: '02'
                        },
                        success: function(rsp,opt) {
                            var rspObj = Ext.decode(rsp.responseText);
                            sudyBindStore.reload();
                            if(rspObj.success) {
                                showSuccessMsg(rspObj.msg,grid);
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                        }
                    });
                    grid.getTopToolbar().items.items[2].disable();
                    hideProcessMsg();
                }
	        });
        }
    };
	
	var openMenu = {
        text: '开启',
        width: 85,
        iconCls: 'accept',                                     
        disabled: true,
        handler:function() {
        	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }  
            showConfirm('确定开启吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在提交信息，请稍后......');
                    rec = grid.getSelectionModel().getSelected();
                     Ext.Ajax.requestNeedAuthorise({
                        url: 'T21504Action.asp?method=update',
                        params: {
                        	recordID: selectedRecord.get('recordID'),
                        	isOpen:'1',
                        	txnId: '21504',
                            subTxnId: '03'
                        },
                        success: function(rsp,opt) {
                            var rspObj = Ext.decode(rsp.responseText);
                            sudyBindStore.reload();
                            if(rspObj.success) {
                                showSuccessMsg(rspObj.msg,grid);
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                        }
                    });
                    grid.getTopToolbar().items.items[2].disable();
                    grid.getTopToolbar().items.items[3].disable();
                    grid.getTopToolbar().items.items[4].disable();
                    hideProcessMsg();
                }
            });
        }
    };
	
	var closeMenu = {//不提供删除
        text: '关闭',
        width: 85,
        iconCls: 'stop',                                     
        disabled: true,
        handler:function() {
        	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }  
            showConfirm('确定要关闭吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在提交信息，请稍后......');
                    rec = grid.getSelectionModel().getSelected();
                     Ext.Ajax.requestNeedAuthorise({
                        url: 'T21504Action.asp?method=update',
                        params: {
                        	recordID: selectedRecord.get('recordID'),
                        	isOpen:'0',
                        	txnId: '21504',
                            subTxnId: '04'
                        },
                        success: function(rsp,opt) {
                            var rspObj = Ext.decode(rsp.responseText);
                            sudyBindStore.reload();
                            if(rspObj.success) {
                                showSuccessMsg(rspObj.msg,grid);
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                        }
                    });
                    grid.getTopToolbar().items.items[2].disable();
                    grid.getTopToolbar().items.items[3].disable();
                    grid.getTopToolbar().items.items[4].disable();
                    hideProcessMsg();
                }
            });
        }
    };
	
	var backMenu = {
        text: '返回',
        width: 85,
        iconCls: 'back',
        id:'backmenu',
        disabled: true,
        handler:function() {
        	window.location.href = Ext.contextPath + '/page/mchnt/T21503.jsp';
        }
    };
	
	var menuArr = new Array();
	
	menuArr.push(addMenu);		//[0]新增
	menuArr.push(queryMenu);	//[1]查询
    menuArr.push(delMenu);      //[7]不提供删除
    menuArr.push(openMenu);
    menuArr.push(closeMenu);
    menuArr.push(backMenu);
    
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '商户补贴规则绑定',
		iconCls: 'T201',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: sudyBindStore,
		sm: sm,
		cm: sudyBindColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载商户绑定规则列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: sudyBindStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	 //工具按钮可用性
    grid.getSelectionModel().on({
        'rowselect': function() {
            //行高亮
            Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
            // 根据商户状态判断哪个编辑按钮可用
            rec = grid.getSelectionModel().getSelected();
            if(rec != null) {
                grid.getTopToolbar().items.items[2].enable();
                if(rec.get('openFlag')=='开启'){
                	grid.getTopToolbar().items.items[4].enable();
                	grid.getTopToolbar().items.items[3].disable();
                }else{
                	grid.getTopToolbar().items.items[3].enable();
                	grid.getTopToolbar().items.items[4].disable();
                }
               
            } else {
                grid.getTopToolbar().items.items[2].disable();
                
            }

        }
    });
	//渲染到页面
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
	if(disid){
		Ext.getCmp('backmenu').enable();
	}
});