Ext.onReady(function() {
	var selectedRecord,acquirersId,allData ;
	Ext.ux.form.LovCombo = Ext.form.LovCombo || Ext.ux.form.LovCombo;  
	function hideLabel(sId, bShow){ 
		Ext.getCmp(sId).getEl().up('.x-form-item').child('.x-form-item-label').setDisplayed(bShow);
        Ext.getCmp(sId).getEl().up('.x-panel').setDisplayed(bShow); 
	} 
	
    // 商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    // SelectOptionsDWR.getComboData('MCHNT_NO1',function(ret){
    //     mchntStore.loadData(Ext.decode(ret));
    // });
    //规则ID
    var ruleStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
    // SelectOptionsDWR.getComboData('DISCOUNT_RULE',function(ret){
    //     ruleStore.loadData(Ext.decode(ret));
    // });
    
    //终端ID
    var equipmentStore= new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
    
    
    //查询搞定
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 570,
        autoHeight: true,
        labelWidth: 100,
        items: [{
                        id: 'acquirersTypeQ',
                    	xtype: 'combo',
                        fieldLabel: '优惠类型',
                        hiddenName: 'acquirersTypeQuery',
                        store: new Ext.data.ArrayStore({
                            fields: ['valueField','displayField'], 

                            data: [['02','任意优惠'],['01','折扣优惠'],['00','满减优惠'],['03','仅联机优惠']]
                        }), 
                        editable : false,
                        width: 300},{
                    id: 'discountIdQ',
                	xtype: 'textfield',
                    fieldLabel: '优惠ID',
                    name: 'discountIdQuery',
                    width: 300},
        //             {
        //     xtype: 'dynamicCombo',
        //     fieldLabel: '商户号',
        //     displayField: 'displayField',
        //     valueField: 'valueField',
        //     width:300,
        //     methodName: 'getMchtCdInTemp',
        //     id: 'mchntIdQ',
        //     hiddenName: 'mchntIdQuery',
        //     readOnly: false,
        //     listeners:{
        //     	'select':function(){
        //     		var mchntid=Ext.getCmp("mchntIdQ").getValue();
        //     		Ext.getCmp("equipmentIdQ").setValue('');
        //     		SelectOptionsDWR.getComboDataWithParameter('TERM_ID',mchntid,function(ret){
        //     			equipmentStore.loadData(Ext.decode(ret));
        //     	    });
        //     	}
        //     }
        // },
        {
        	xtype:'textfield',
        	fieldLabel: '商户号',
        	id:'mchntIdQ',
        	name:'mchntIdQ',
        	width:300
        },
   /*     {
        	xtype:'textfield',
        	fieldLabel: '商户地址',
        	id:'mchntAddressQ',
        	name:'mchntAddressQ',
        	width:300
        },*/
        {
        	xtype:'textfield',
        	fieldLabel: '终端号',
        	id:'equipmentIdQ',
        	name:'equipmentIdQ',
        	width:300
        },{
                            id: 'isOpenQ',
                        	xtype: 'combo',
                            fieldLabel: '开启状态',
                            hiddenName: 'isOpenQuery',
                            store: new Ext.data.ArrayStore({
                                fields: ['valueField','displayField'], 

                                data: [['00','不开启'],['01','开启']]
                            }), 
                            editable : false,
                            width: 300},{
            width: 150,
            xtype: 'datefield',
            fieldLabel: '绑定起始时间',
            format : 'Y-m-d',
            name :'startTime',
            id :'startTime',
            editable:false,
            anchor :'60%'       
      },{                                         
            width: 150,
            xtype: 'datefield',
            fieldLabel: '绑定截止时间',
            format : 'Y-m-d',
            name :'endTime',
            id :'endTime',
            editable:false,
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
            	grid.getTopToolbar().items.items[2].disable();
                
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
                equipmentStore.removeAll();
            }
        }]
    });
    
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getMchntDiscountRuleBindInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
            {name: 'bindId',mapping: 'bindId'},
			{name: 'discountId',mapping: 'discountId'},
			{name: 'discountValue',mapping: 'discountValue'},
			{name: 'discountValueInfo',mapping: 'discountValueInfo'},
			{name: 'mchntId',mapping: 'mchntId'},
			{name: 'equipmentId',mapping: 'equipmentId'},			
			{name: 'acquirersType',mapping: 'acquirersType'},
            {name:'acquirersId',mapping:'acquirersId'},
			{name: 'isOpen',mapping: 'isOpen'},
            {name: 'discountStart',mapping: 'startTime'},
            {name: 'discountEnd',mapping: 'endTime'},
			{name: 'createPerson',mapping: 'createPerson'},
			{name: 'createTime',mapping: 'createTime'}
		]),
		autoLoad:true
	});
	
	termStore.on('beforeload', function() {
		Ext.select('td.x-grid3-cell-first').setStyle({ visibility:'hidden'});
        Ext.apply(this.baseParams, {
            start: 0,
            equipmentId: Ext.getCmp('equipmentIdQ').getValue(),
            mchntId: Ext.getCmp('mchntIdQ').getValue(),
//            mchntAddress: Ext.getCmp('mchntAddressQ').getValue(),
            mchntAddress:"",
            discountId:Ext.getCmp('discountIdQ').getValue()||disid,
            isOpen:Ext.getCmp('isOpenQ').getValue(),
            acquirersType:Ext.getCmp('acquirersTypeQ').getValue(),
            startTime:Ext.getCmp('startTime').getValue(),
            endTime:Ext.getCmp('endTime').getValue()
        });
    });
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect: true});
    sm.handleMouseDown = Ext.emptyFn;
	var termColModel = new Ext.grid.ColumnModel([
	    sm,
	    {header: '绑定ID',dataIndex: 'bindId',width:200,align:'center',hidden:true},
        {header: '优惠ID',dataIndex: 'discountId',width:200,align:'center'},
        {header: '终端ID',dataIndex: 'acquirersId',width:200,align:'center',hidden:true},
		{header: '优惠类型',dataIndex: 'acquirersType',width:150,align:'center',renderer:acquirersType},
		{header: '优惠规则内容',dataIndex: 'discountValue',width:200,align:'center',renderer:discountInfo},
		{header: '下发内容',dataIndex: 'discountValueInfo',width:200,align:'center'},
        {header: '开始时间',dataIndex: 'discountStart',width:200,align:'center'},
        {header: '结束时间',dataIndex: 'discountEnd',width:200,align:'center'},
		{id:'mchntId',header: '商户号',dataIndex: 'mchntId',width:200,align:'center'},
		{id: 'equipmentId',header: '终端号',dataIndex: 'equipmentId',width:150,align:'center'},
		{header: '开启状态',dataIndex: 'isOpen',width:70,align:'center',renderer:openStatus},
		{header: '绑定时间',dataIndex: 'createTime',width:200,align:'center'}
	]);
	
	var addMenu = {
		text: '添加绑定',
		width: 85,
		iconCls: 'add',
		handler:function() {
			termWin.show();
			termWin.center();
			Ext.getCmp('selectMchnt').disable();
			Ext.getCmp('selectMchntId').disable();
			equipmentStore.removeAll();
			mchntStore.removeAll();
		}
	};
	var termInfoStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=getMchntDiscountRuleBindInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'mchntIdUpd',mapping: 'mchntId'},
            {name: 'equipmentIdUpd',mapping: 'equipmentId'},
            {name: 'discountIdUpd',mapping: 'discountId'},
            {name: 'acquirersIdUpd',mapping: 'acquirersId'}
        ])
    });
	
    
    
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
	            showConfirm('确定要删除该终端吗？',grid,function(bt) {
	                if(bt == 'yes') {
	                    showProcessMsg('正在提交信息，请稍后......');
	                    rec = grid.getSelectionModel().getSelected();
	                     Ext.Ajax.requestNeedAuthorise({
	                        url: 'T21403Action.asp?method=delete',
	                        params: {
	                        	bindId: selectedRecord.get('bindId'),
	                        	mchntId: selectedRecord.get('mchntId'),
	                        	equipmentId: selectedRecord.get('equipmentId'),
	                        	acquirersId:selectedRecord.get('acquirersId'),
	                            txnId: '21403',
	                            subTxnId: '03'
	                        },
	                        success: function(rsp,opt) {
	                            var rspObj = Ext.decode(rsp.responseText);
	                            termStore.reload();
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
	                        url: 'T21403Action.asp?method=update',
	                        params: {
	                        	bindId: selectedRecord.get('bindId'),
	                        	mchntId: selectedRecord.get('mchntId'),
	                        	equipmentId: selectedRecord.get('equipmentId'),
	                        	acquirersId:selectedRecord.get('acquirersId'),
	                        	isOpen:'01',
	                            txnId: '21403',
	                            subTxnId: '04'
	                        },
	                        success: function(rsp,opt) {
	                            var rspObj = Ext.decode(rsp.responseText);
	                            termStore.reload();
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
	                        url: 'T21403Action.asp?method=update',
	                        params: {
	                        	bindId: selectedRecord.get('bindId'),
	                        	mchntId: selectedRecord.get('mchntId'),
	                        	equipmentId: selectedRecord.get('equipmentId'),
	                        	acquirersId:selectedRecord.get('acquirersId'),
	                        	isOpen:'00',
	                            txnId: '21403',
	                            subTxnId: '05'
	                        },
	                        success: function(rsp,opt) {
	                            var rspObj = Ext.decode(rsp.responseText);
	                            termStore.reload();
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
	//返回
	var backMenu = {
	        text: '返回',
	        width: 85,
	        iconCls: 'back',
	        id:'backmenu',
	        disabled: true,
	        handler:function() {
	        	window.location.href = Ext.contextPath + '/page/mchnt/T21501.jsp';
	        }
	    };
    
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 570,
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
                equipmentStore.removeAll();
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
            equipmentStore.removeAll();
        }
    };
	var menuArr = new Array();
	
	menuArr.push(addMenu);		//[0]新增
	menuArr.push(queryMenu);	//[1]查询
//	menuArr.push(editMenu);		//[2]修改
    menuArr.push(delMenu);      //[7]不提供删除
    menuArr.push(openMenu);
    menuArr.push(closeMenu);
    menuArr.push(backMenu);
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '客户优惠规则绑定',
		iconCls: 'T201',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		sm: sm,
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载商户绑定规则列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});


     var termPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 350,
        width: 750,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1New',
                layout: 'column',
                frame: true,
                items: [{
                    columnWidth:1,
                    layout:'form',
                    items:[{
                     xtype: 'combo',
                     fieldLabel: '优惠类型*',
                     id: 'acquirersTypeN',
                     hiddenName:'acquirersTypeNew',
                     allowBlank: false,
                     width:180,
                     store: new Ext.data.ArrayStore({
                         fields: ['valueField','displayField'], 

                         data: [['02','任意优惠'],['01','折扣优惠'],['00','满减优惠']]
//                         data: [['02','任意优惠'],['01','折扣优惠'],['00','满减优惠'],['03','仅联机优惠']]
                     }),
                     listeners:{
                   	   'select':function(){
                   		   //
                   		Ext.getCmp('selectMchnt').setValue('0');
                    	Ext.getCmp('selectMchntId').setValue('1');
                    	Ext.getCmp('equipmentIdN').show().enable();
                    	hideLabel('equipmentIdN',true);
                    	Ext.getCmp('mchntIdN').show().enable();
                    	hideLabel('mchntIdN',true);
                    	Ext.getCmp('selectMchnt').allowBlank=true;
                   		   //
                   		   Ext.getCmp('discountIdN').reset();
                   		   var type=Ext.getCmp("acquirersTypeN").getValue();
                   		   SelectOptionsDWR.getComboDataWithParameter('DISCOUNT_RULE_TYPE',type,function(ret){
                				   ruleStore.loadData(Ext.decode(ret));
	                       	    });
                   		   Ext.getCmp('mchntIdN').reset();
                   		   mchntStore.removeAll();
                   		   Ext.getCmp('equipmentIdN').reset();
                   		   equipmentStore.removeAll();
	                   		if(document.querySelector('.ux-combo-selectall-icon')){
	                    		if(Ext.get(document.querySelector('.ux-combo-selectall-icon')).hasClass('ux-combo-selectall-icon-checked')){
	                    			Ext.get(document.querySelector('.ux-combo-selectall-icon')).removeClass('ux-combo-selectall-icon-checked').addClass('ux-combo-selectall-icon-unchecked');
	                    		}
	                    	}
                   		}}
                 }]
                },{
                    columnWidth:1,
                    layout:'form',
                    items:[{
                 id: 'discountIdN',
                 displayField: 'displayField',
                 valueField: 'valueField',
//             	 xtype: 'dynamicCombo',
                 xtype: 'combo',
                 store: ruleStore,
                 fieldLabel: '优惠规则*',
                 hiddenName: 'discountIdNew',
                 editable : true,
                 width: 300,
                listeners:{
                    'select':function () {
                    	Ext.getCmp('selectMchnt').enable();
            			Ext.getCmp('selectMchntId').enable();
                        var ruleid=Ext.getCmp('discountIdN').getValue();
                        SelectOptionsDWR.getComboDataWithParameter('DISCOUNT_RULE_ID',ruleid,function(ret){
                				  mchntStore.loadData(Ext.decode(ret));
	                    });
                        Ext.getCmp('mchntIdN').reset();
                        Ext.getCmp('equipmentIdN').reset();
                        equipmentStore.removeAll();
                    }
                }}]
                },
                {
                    columnWidth:.3,
                    layout:'form',
                    items:[{
                        xtype:'checkbox',
                        id:'selectMchnt',
                        name:'selectMchnt',
                        fieldLabel:'',
                        boxLabel:'所有商户',
                        allowBlank:false,
                        listeners:{
                        	'afterrender':function(obj){  
                                obj.getEl().dom.onclick = function(e){
                                	allData=1;                                	
                                	Ext.getCmp('selectMchntId').setValue('0');
                                	Ext.getCmp('selectMchnt').setValue('1');
                                	Ext.getCmp('equipmentIdN').hide().disable();
                                	hideLabel('equipmentIdN',false);
                                	Ext.getCmp('mchntIdN').hide().disable();
                                	hideLabel('mchntIdN',false);
                                	Ext.getCmp('selectMchntId').allowBlank=true;
                                	equipmentStore.removeAll();
                        			Ext.getCmp('mchntIdN').reset();
                        			Ext.getCmp('equipmentIdN').reset();
                                }  
                            }   
                        }
                        
                    }]
                },{
                    columnWidth:.5,
                    labelWidth:10,
                    layout:'form',
                    items:[{
                        xtype:'checkbox',
                        id:'selectMchntId',
                        name:'selectMchntId',
                        checked:true,
                        fieldLabel:'',
                        boxLabel:'指定商户',
                        listeners:{
                        	'afterrender':function(obj){  
                                obj.getEl().dom.onclick = function(e){
                                	Ext.getCmp('selectMchnt').setValue('0');
                                	Ext.getCmp('selectMchntId').setValue('1');
                                	Ext.getCmp('equipmentIdN').show().enable();
                                	hideLabel('equipmentIdN',true);
                                	Ext.getCmp('mchntIdN').show().enable();
                                	hideLabel('mchntIdN',true);
                                	Ext.getCmp('selectMchnt').allowBlank=true;
                                }  
                            }
                        }
                    }]
                },{
                    columnWidth:1,
                    layout:'form',
                    items:[new Ext.ux.form.LovCombo({
					    	id: 'mchntIdN',
					    	store:mchntStore,
					    	fieldLabel: '商户号*',
						    displayField: 'displayField',
						    valueField: 'valueField',
						    triggerAction   : "all",
						    showSelectAll   : true, 
						    hiddenName:'mchntIdNew',
						    allowBlank: false,
						    editable:true,
						    width:300,
                            listeners:{
                                'select':function (that,record,index) {
                                	console.log(that,record,index);	
                                    var mchntid=Ext.getCmp('mchntIdN').getValue(),selAll=Ext.getCmp('selectMchnt').getValue(),selOne=Ext.getCmp('selectMchntId').getValue();
                                    
                                    var mchntArr=mchntid.split(',').length,allLen=mchntStore.length;
                                    if(selAll){
                                    	allData=1;
                                    }else{
                                    	showProcessMsg('正在加载终端信息，请稍后......');
                                    	Ext.getCmp('equipmentIdN').reset();
                                    	if(document.querySelectorAll('.ux-combo-selectall-icon')[1]){
                                    		if(Ext.get(document.querySelectorAll('.ux-combo-selectall-icon')[1]).hasClass('ux-combo-selectall-icon-checked')){
                                    			Ext.get(document.querySelectorAll('.ux-combo-selectall-icon')[1]).removeClass('ux-combo-selectall-icon-checked').addClass('ux-combo-selectall-icon-unchecked');
                                    		}
                                    	}
                                      SelectOptionsDWR.getComboDataWithParameter('MAPPING_MCHNTCD',mchntid,function(ret){
					        			equipmentStore.loadData(Ext.decode(ret));
					        			hideProcessMsg();
					        	    });
                                      allData=2;
                                    }
                                    
                                },
                                'afterrender':function(obj){
                                	obj.getEl().up('.x-form-field-wrap').dom.onclick=function(){
                                		
                                		if(Ext.get(document.querySelector('.ux-combo-selectall-icon')).hasClass('ux-combo-selectall-icon-unchecked')){
                                			document.querySelector('.ux-combo-selectall-icon').onclick=function(){
                                				
                      				 		   	var mchntids=Ext.getCmp('mchntIdN').getValue();
                      				 		   	showProcessMsg('正在加载终端信息，请稍后......');
                                                SelectOptionsDWR.getComboDataWithParameter('MAPPING_MCHNTCD',mchntids,function(ret){
                                                	equipmentStore.loadData(Ext.decode(ret));
                                                	hideProcessMsg();
                                                });
                                                Ext.getCmp('mchntIdN').collapse();
                                                Ext.getCmp('equipmentIdN').reset();
                                			}
                     			    }
                                	}
                    			     
                                }
                                
                            }
					    })]
                },{
                    columnWidth:1,
                    layout:'form',
                    items:[new Ext.ux.form.LovCombo({
					    	id: 'equipmentIdN',
					    	store:equipmentStore,
					    	fieldLabel: '终端号*',
						    displayField: 'displayField',
						    valueField: 'valueField',
						    triggerAction   : "all",
						    showSelectAll   : true, 
						    hiddenName:'equipmentIdNew',
						    allowBlank: false,
						    editable:false,
						    width:300,
						    listeners:{
						    	'select':function(){
						    		var eqData=Ext.getCmp('equipmentIdN').getValue(),eqLen=eqData.length,allLen=equipmentStore.length;
						    		if(eqLen==0){
						    			allData=2;
						    		}else{
						    			allData=3;
						    		}
						    	},
						    	'afterrender':function(obj){
						    		obj.getEl().up('.x-form-field-wrap').dom.onclick=function(){
						    			if(Ext.get(document.querySelectorAll('.ux-combo-selectall-icon')[1]).hasClass('ux-combo-selectall-icon-unchecked')){
						    				document.querySelectorAll('.ux-combo-selectall-icon')[1].onclick=function(){
						    					allData=3;
						    					Ext.getCmp('equipmentIdN').collapse();
						    				}
						    			}
						    		}
						    	}
						    }
					    })]
                },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '开关状态*',
                    id: 'isOpenN',
                    hiddenName:'isOpenNew',
                    allowBlank: false,
                    width:180,
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'], 

                        data: [['01','开启'],['00','关闭']]
                    })
                }]
            }]
        }]		
    });
    /**************  终端表单  *********************/
	var termForm = new Ext.form.FormPanel({
		frame: true,
		height: 350,
		width: 750,
		labelWidth: 140,
		waitMsgTarget: true,
		layout: 'column',
		items: [termPanel]
	});
   
    /***********  终端信息窗口  *****************/
	var termWin = new Ext.Window({
		title: '客户优惠规则绑定',
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
						url: 'T21403Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessDtl(action.result.msg,termWin);
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							termForm.getForm().reset();
							equipmentStore.removeAll();
							mchntStore.removeAll();
                            termWin.hide();
						},
						failure: function(form,action) {
							termPanel.setActiveTab('info1New');
							showErrorMsg(action.result.msg,termWin);
						},
						params: {
							mchntId:Ext.getCmp("mchntIdN").getValue().substring(1),
							discountId:Ext.getCmp("discountIdN").getValue(),
							acquirersType:Ext.getCmp("acquirersTypeN").getValue(),
							isOpen:Ext.getCmp("isOpenN").getValue(),
                            equipmentId:Ext.getCmp("equipmentIdN").getValue(),
                            acquirersId:Ext.getCmp("mchntIdN").getValue().substring(0,1)=='S'?'00':'01',
							txnId: '21403',
							all:allData,
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
					equipmentStore.removeAll();
					mchntStore.removeAll();
			}
		},{
			text: '关闭',
			handler: function() {
				termWin.hide();
				termForm.getForm().reset();
				equipmentStore.removeAll();
				mchntStore.removeAll();
			}
		}]
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
                if(rec.get('isOpen')=='01'){
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