Ext.onReady(function() {
	
	var selectedRecord ;
	function hideLabel(sId, bShow){ 
		Ext.getCmp(sId).getEl().up('.x-form-item').child('.x-form-item-label').setDisplayed(bShow);
        Ext.getCmp(sId).getEl().up('.x-panel').setDisplayed(bShow); 
	} 
    
    //查询搞定
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 570,
        autoHeight: true,
        labelWidth: 100,
        items: [{
        	xtype: 'combo',
            fieldLabel: '折扣类型',
            id: 'discountTypeQ',
            hiddenName: 'discountTypeQuery',
            store: new Ext.data.ArrayStore({
                fields: ['valueField','displayField'], 

//                data: [['0','满减'],['1','折扣'],['2','任意'],['3','仅联机优惠']]
                data: [['0','满减'],['1','折扣'],['2','任意']]
            }),
            width:180,
            readOnly:false
        },new Ext.form.TextField({
            id: 'cardbinQ',
            name: 'cardbinQ',
            fieldLabel: '卡bin',
            width:150
        }),{
            width: 150,
            xtype: 'datefield',
            fieldLabel: '规则创建起始时间',
            format : 'Y-m-d',
            name :'startTime',
            id :'startTime',
        	editable:false,
            anchor :'60%'       
      },{                                         
            width: 150,
            xtype: 'datefield',
            fieldLabel: '规则创建截止时间',
            format : 'Y-m-d',
            name :'endTime',
            id :'endTime',
        	editable:false,
            anchor :'60%'       
      },new Ext.form.TextField({
                id: 'discountCodeQ',
                name: 'discountCodeQuery',
                maxLength:20,
                fieldLabel: '营销编码'
            })],
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
                grid.getTopToolbar().items.items[3].disable();
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
			url: 'gridPanelStoreAction.asp?storeId=getMchntDiscountRuleInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'discountId',mapping: 'discountId'},
			{name: 'discountCode',mapping: 'discountCode'},
			{name: 'discountType',mapping: 'discountType'},
			{name: 'discountCard',mapping: 'discountCard'},
			{name: 'discountValue',mapping: 'discountValue'},
            {name: 'openType',mapping: 'openType'},
            {name: 'isDownload',mapping: 'isDownload'},
            {name: 'openLian',mapping: 'openLian'},
            {name: 'acquirersId',mapping: 'acquirersId'},
			{name: 'discountValueInfo',mapping: 'discountValueInfo'},
			{name: 'createTime',mapping: 'createTime'},
            {name: 'createPerson',mapping: 'createPerson'},
            {name: 'remark',mapping: 'remark'},
            {name: 'issuedContent',mapping: 'issuedContent'},
            {name: 'startTimeEff',mapping: 'startTimeEff'},
            {name: 'endTimeEff',mapping: 'endTimeEff'},
            {name: 'sumcount0',mapping: 'sumcount0'},
            {name: 'sumcount1',mapping: 'sumcount1'},
            {name: 'sumcount0l',mapping: 'sumcount0l'},
            {name: 'sumcount1l',mapping: 'sumcount1l'}
		]),
		autoLoad:true
	});
	function  linkrender(val){
		return "<a href='"+Ext.contextPath+"/page/mchnt/T21502.jsp?discountId="+val+"'>"+val+"</a>";
	}
	termStore.on('beforeload', function() {
		Ext.select('td.x-grid3-cell-first').setStyle({ visibility:'hidden'});
        Ext.apply(this.baseParams, {
            start: 0,
            discountCode: Ext.getCmp('discountCodeQ').getValue(),
            discountType: Ext.getCmp('discountTypeQ').getValue(),
            cardbin: Ext.getCmp('cardbinQ').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue()  
        });
    });
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect: true});
    sm.handleMouseDown = Ext.emptyFn;
	var termColModel = new Ext.grid.ColumnModel([
	    sm,
		{id: 'discountId',header: '优惠规则ID',dataIndex: 'discountId',width:200,align:'center',hidden:false,renderer:linkrender},
		{id:'discountType',header: '优惠类型',dataIndex: 'discountType',width:100,align:'center', renderer:discountType},
		{header: '营销代码',dataIndex: 'discountCode',width:150,align:'center'},
		{header: '规则内容',dataIndex: 'discountValueInfo',width:200,align:'center',renderer:discountInfo},
        {header: '开启方式',dataIndex: 'openType',width:70,align:'center',renderer:openType},
		{header: '是否已下载',dataIndex: 'isDownload',width:70,align:'center',renderer:isDownload},
		{header: '优惠方式',dataIndex: 'openLian',width:100,align:'center',renderer:openLian},
		{header: '收单方',dataIndex: 'acquirersId',width:100,align:'center',renderer:shoudanfang},
        {header: '卡bin',dataIndex: 'discountCard',width:100,align:'center'},
        {header: '下发内容',dataIndex: 'issuedContent',width:100,align:'center'},
		{id: 'createTime',header: '创建时间',dataIndex: 'createTime',width: 100},
	    {id: 'createPerson',header: '创建人',dataIndex: 'createPerson',width: 100},
	    {id: 'startTimeEff',header: '规则开始时间',dataIndex: 'startTimeEff',width: 100},
	    {id: 'endTimeEff',header: '规则结束时间',dataIndex: 'endTimeEff',width: 100},
	    {id: 'sumcount0',header: '脱机按卡限次',dataIndex: 'sumcount0',width: 80},
	    {id: 'sumcount1',header: '脱机终端限次',dataIndex: 'sumcount1',width: 80},
	    {id: 'sumcount0l',header: '联机按卡限次',dataIndex: 'sumcount0l',width: 80},
	    {id: 'sumcount1l',header: '联机终端限次',dataIndex: 'sumcount1l',width: 80},
        {header: '备注',dataIndex: 'remark',width:200,align:'center'}
	]);
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			termWin.show();
			termWin.center();
			hideLabel('discount0R',false);
			hideLabel('discount0Z',false);
			hideLabel('sumcount0',false);
			hideLabel('sumcount1',false);
			hideLabel('sumcount0l',false);
			hideLabel('sumcount1l',false);
			hideLabel('discount0M',false);
			hideLabel('discount0J',false);
            hideLabel('openLianN',false);
            hideLabel('isDownloadN',false);
            hideLabel('openTypeN',false);
            hideLabel('acquirersIdN',false);
            hideLabel('discountCardNew',false);
            Ext.getCmp("sumcount0").hide().disable();
            Ext.getCmp("sumcount1").hide().disable();
			Ext.getCmp("discount0M").hide().disable();
			Ext.getCmp("discount0J").hide().disable();
			Ext.getCmp("discount0Z").hide().disable();
			Ext.getCmp("discount0R").hide().disable();
			Ext.getCmp('openTypeN').hide().disable();
            Ext.getCmp('isDownloadN').hide().disable();
            Ext.getCmp('openLianN').hide().disable();
            Ext.getCmp('acquirersIdN').hide().disable();
            Ext.getCmp('discountCardNew').hide().disable();
			Ext.getCmp("discountCodeNew").setValue(getCode());
		}
	};
	var termInfoStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=getMchntDiscountRuleInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'discountIdUpd',mapping: 'discountId'},
            {name: 'discountCodeUpd',mapping: 'discountCode'},
            {name: 'openTypeUpd',mapping: 'openType'},
            {name: 'isDownloadUpd',mapping: 'isDownload'},
            {name: 'openLianUpd',mapping: 'openLian'},
            {name: 'acquirersIdUpd',mapping: 'acquirersId'},
            {name: 'remarkUpd',mapping: 'remark'},
            {name: 'discountTypeUpd',mapping: 'discountType'},
            {name: 'discountCardUpd',mapping: 'discountCard'},
            {name: 'discountValueUpd',mapping: 'discountValue'},
            {name: 'startTimeEffUpd',mapping: 'startTimeEff'},
            {name: 'endTimeEffUpd',mapping: 'endTimeEff'},
            {name: 'sumcount0Upd',mapping: 'sumcount0'},
            {name: 'sumcount1Upd',mapping: 'sumcount1'},
            {name: 'sumcount0lUpd',mapping: 'sumcount0l'},
            {name: 'sumcount1lUpd',mapping: 'sumcount1l'}
        ])
    });
	
    
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			
            selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }    
            termInfoStore.load({
                params: {
                	discountId: selectedRecord.get('discountId'),
                	discountType: selectedRecord.get('discountType')
                },
                callback: function(records, options, success){
                
                    if(success){
                    	var start=records[0].data.startTimeEffUpd.substr(0,8),end=records[0].data.endTimeEffUpd.substr(0,8);
                    	start=start.substr(0,4)+'-'+start.substr(4,2)+'-'+start.substr(6,2);
                    	end=end.substr(0,4)+'-'+end.substr(4,2)+'-'+end.substr(6,2);
                    	updTermWin.show();
                    	hideLabel('discount0MU',false);
                    	hideLabel('discount0JU',false);
                    	hideLabel('discount0ZU',false);
                    	hideLabel('discount0RU',false);
                        hideLabel('openTypeU',false);
                        hideLabel('isDownloadU',false);
                        hideLabel('openLianU',false);
                        hideLabel('acquirersIdU',false);
                        hideLabel('discountCardUpd',false);
                    	Ext.getCmp('discount0MU').hide().disable();
                    	Ext.getCmp('discount0JU').hide().disable();
                    	Ext.getCmp('discount0ZU').hide().disable();
                    	Ext.getCmp('discount0RU').hide().disable();
                        Ext.getCmp("openTypeU").hide().disable();
                        Ext.getCmp("isDownloadU").hide().disable();
                        Ext.getCmp("openLianU").hide().disable();
                        Ext.getCmp("acquirersIdU").hide().disable();
                        Ext.getCmp("discountCardUpd").hide().disable();
                    	Ext.getCmp('discountIdUpd').setValue(records[0].data.discountIdUpd);
                       Ext.getCmp('discountCodeUpd').setValue(records[0].data.discountCodeUpd);
                       Ext.getCmp('discountTypeUpd').setValue(records[0].data.discountTypeUpd);
                       Ext.getCmp('sumcount0Upd').setValue(records[0].data.sumcount0Upd);
                       Ext.getCmp('sumcount1Upd').setValue(records[0].data.sumcount1Upd);
                       Ext.getCmp('sumcount0lUpd').setValue(records[0].data.sumcount0lUpd);
                       Ext.getCmp('sumcount1lUpd').setValue(records[0].data.sumcount1lUpd);
                       Ext.getCmp('remarkUpd').setValue(records[0].data.remarkUpd);
                       Ext.getCmp('startTimeEffUpd').setValue(start);
                       Ext.getCmp('endTimeEffUpd').setValue(end);
                       if(records[0].data.discountTypeUpd=='0'){
                    	   var value=records[0].data.discountValueUpd.split('-');
                    	   Ext.getCmp('discount0MU').setValue(value[0]);
                    	   Ext.getCmp('discount0JU').setValue(value[1]);
                    	   Ext.getCmp('discount0MU').show().enable();
                       	   Ext.getCmp('discount0JU').show().enable();
                       	   hideLabel('discount0MU',true);
                    	   hideLabel('discount0JU',true);
                           hideLabel('openTypeU',true);
                            hideLabel('isDownloadU',true);
                            hideLabel('openLianU',true);
                            hideLabel('acquirersIdU',true);
                           hideLabel('discountCardUpd',true);
                           Ext.getCmp("openTypeU").show().enable();
                           Ext.getCmp("isDownloadU").show().enable();
                           Ext.getCmp("openLianU").show().enable();
                           Ext.getCmp("acquirersIdU").show().enable();
                           Ext.getCmp("discountCardUpd").show().enable();
                           Ext.getCmp('openTypeU').setValue(records[0].data.openTypeUpd);
                           Ext.getCmp('isDownloadU').setValue(records[0].data.isDownloadUpd);
                           Ext.getCmp('openLianU').setValue(records[0].data.openLianUpd);
                           Ext.getCmp('acquirersIdU').setValue(records[0].data.acquirersIdUpd);
                           Ext.getCmp('discountCardUpd').setValue(records[0].data.discountCardUpd);
            			   Ext.getCmp("discount0MU").getEl().up('.x-form-item').child('.x-form-item-label').dom.innerHTML="满*（单位:分）";
                       }
                       if(records[0].data.discountTypeUpd=='1'){
                    	   var value=records[0].data.discountValueUpd.split('-');
                    	   Ext.getCmp('discount0MU').setValue(parseInt(value[0],10));
                    	   Ext.getCmp('discount0ZU').setValue(value[1]);
                    	   Ext.getCmp('discount0MU').show().enable();
                    	   Ext.getCmp('discount0ZU').show().enable();
                    	   hideLabel('discount0ZU',true);
                    	   hideLabel('discount0MU',true);
                           hideLabel('openTypeU',true);
                            hideLabel('isDownloadU',true);
                            hideLabel('openLianU',true);
                            hideLabel('acquirersIdU',true);
                           hideLabel('discountCardUpd',true);
                           Ext.getCmp("openTypeU").show().enable();
                           Ext.getCmp("isDownloadU").show().enable();
                           Ext.getCmp("openLianU").show().enable();
                           Ext.getCmp("acquirersIdU").show().enable();
                           Ext.getCmp("discountCardUpd").show().enable();
                           Ext.getCmp('openTypeU').setValue(records[0].data.openTypeUpd);
                           Ext.getCmp('isDownloadU').setValue(records[0].data.isDownloadUpd);
                           Ext.getCmp('openLianU').setValue(records[0].data.openLianUpd);
                           Ext.getCmp('acquirersIdU').setValue(records[0].data.acquirersIdUpd);
                           Ext.getCmp('discountCardUpd').setValue(records[0].data.discountCardUpd);
            			   Ext.getCmp("discount0MU").getEl().up('.x-form-item').child('.x-form-item-label').dom.innerHTML="限额*（单位:分）";
                       }
                       if(records[0].data.discountTypeUpd=='2'){
                    	   var value=records[0].data.discountValueUpd;
                    	   Ext.getCmp('discount0RU').setValue(value);
                    	   Ext.getCmp('discount0RU').show().enable();
                    	   hideLabel('discount0RU',true);
                           hideLabel('discountCardUpd',true);
                           Ext.getCmp("discountCardUpd").show().enable();
                           Ext.getCmp('discountCardUpd').setValue(records[0].data.discountCardUpd);
                       }
                       
                       
                     if(records[0].data.openLianUpd=='00'){
          			     Ext.getCmp("sumcount0Upd").show().enable();
         			     Ext.getCmp("sumcount1Upd").show().enable();
          			     Ext.getCmp("sumcount0lUpd").hide().disable();
                         Ext.getCmp("sumcount1lUpd").hide().disable();
                         Ext.getCmp('sumcount0Upd').setValue(records[0].data.sumcount0Upd);
                         Ext.getCmp('sumcount1Upd').setValue(records[0].data.sumcount1Upd);
                         Ext.getCmp('sumcount0lUpd').setValue(records[0].data.sumcount0lUpd);
                         Ext.getCmp('sumcount1lUpd').setValue(records[0].data.sumcount1lUpd);
                         hideLabel('sumcount0Upd',true);
                         hideLabel('sumcount1Upd',true);
                         hideLabel('sumcount0lUpd',false);
                         hideLabel('sumcount1lUpd',false);
          		   }else if(records[0].data.openLianUpd=='01'){
          			   Ext.getCmp("sumcount0Upd").show().enable();
          			   Ext.getCmp("sumcount1Upd").show().enable();
          			   Ext.getCmp("sumcount0lUpd").show().enable();
          			   Ext.getCmp("sumcount1lUpd").show().enable();
                       Ext.getCmp('sumcount0Upd').setValue(records[0].data.sumcount0Upd);
                       Ext.getCmp('sumcount1Upd').setValue(records[0].data.sumcount1Upd);
                       Ext.getCmp('sumcount0lUpd').setValue(records[0].data.sumcount0lUpd);
                       Ext.getCmp('sumcount1lUpd').setValue(records[0].data.sumcount1lUpd);
                       hideLabel('sumcount0Upd',true);
                       hideLabel('sumcount1Upd',true);
                       hideLabel('sumcount0lUpd',true);
                       hideLabel('sumcount1lUpd',true);
          		   }else if(records[0].data.openLianUpd=='02'){
          			   Ext.getCmp("sumcount0Upd").hide().disable();
          			   Ext.getCmp("sumcount1Upd").hide().disable();
          			   Ext.getCmp("sumcount0lUpd").show().enable();
          			   Ext.getCmp("sumcount1lUpd").show().enable();
                       Ext.getCmp('sumcount0Upd').setValue(records[0].data.sumcount0Upd);
                       Ext.getCmp('sumcount1Upd').setValue(records[0].data.sumcount1Upd);
                       Ext.getCmp('sumcount0lUpd').setValue(records[0].data.sumcount0lUpd);
                       Ext.getCmp('sumcount1lUpd').setValue(records[0].data.sumcount1lUpd);
                       hideLabel('sumcount0Upd',false);
                       hideLabel('sumcount1Upd',false);
                       hideLabel('sumcount0lUpd',true);
                       hideLabel('sumcount1lUpd',true);
          		   }
                       
                       
                    }else{
                    
                       updTermWin.hide();
                    }
                }
            });
		}
	};    
	var delMenu = {//不提供删除
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
	                        url: 'T21402Action.asp?method=delete',
	                        params: {
	                        	discountId: selectedRecord.get('discountId'),
	                            txnId: '21402',
	                            subTxnId: '03'
	                        },
	                        success: function(rsp,opt) {
	                            var rspObj = Ext.decode(rsp.responseText);
	                            
	                            if(rspObj.success) {
	                                showSuccessMsg(rspObj.msg,grid);
	                                termStore.reload();
	                            } else {
	                                showErrorMsg(rspObj.msg,grid);
	                            }
	                        }
	                    });
	                     grid.getTopToolbar().items.items[2].disable();
	                     grid.getTopToolbar().items.items[3].disable();
	                    hideProcessMsg();
	                }
	            });
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
        }
    };
	var menuArr = new Array();
	
	menuArr.push(addMenu);		//[0]新增
	menuArr.push(queryMenu);	//[1]查询
	menuArr.push(editMenu);		//[2]修改
    menuArr.push(delMenu);      //[7]不提供删除
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '客户优惠规则',
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
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});

	function fix2Length(str){
		if((str+"").length<2){
			str="0"+str;
		}
		return str;
	}
	function fix6Length(str){
		if((str+"").length<6){
			str="0"+str;
		}
		return str;
	}
	
    function getCode(){
	   var date=new Date();
	   return date.getFullYear()+""+fix2Length(date.getMonth()+1)+fix2Length(date.getDate())+fix2Length(date.getHours())+fix2Length(date.getMinutes())+fix2Length(date.getSeconds())+fix6Length(Math.floor(Math.random()*1000000));
    }

     var termPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 400,
        width: 750,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1New',
                layout: 'column',
                frame: true,
                items: [{
                 columnWidth: 1,
                 layout: 'form',
                 items: [{
                    xtype: 'textfield',
                    fieldLabel: '营销代码*',
                    allowBlank:false,
                    id: 'discountCodeNew',
                    name: 'discountCodeNew',
                    width: 180,
					 value:getCode(),
					  readOnly:true,
//                    maxLength:20,
//                    minLength:20,
//                    minLengthText:'长度固定20位',
//                    maxLengthText:'长度固定20位',
                    listeners:{
                    	render:function(field){
//                    		Ext.QuickTips.init();   
//                    		Ext.QuickTips.register({   
//                    		target : field.el,   
//                    		text : '请输入数字或字母，长度固定20位'   
//                    		}) 
                    	}
                    }
                  }]
             },{
                columnWidth: 1,
                layout: 'form',
                items:[{
                   xtype: 'combo',
                   fieldLabel: '优惠类型*',
                   id: 'discountTypeNew',
                   hiddenName: 'discountTypeN',
                   store: new Ext.data.ArrayStore({
                       fields: ['valueField','displayField'], 
    
                       data: [['0','满减'],['1','折扣'],['2','任意']]
//                       data: [['0','满减'],['1','折扣'],['2','任意'],['3','仅联机优惠']]
                   }),
                   width:180,
                   allowBlank: false,
                   readOnly:false,
                   listeners:{
                	   'select':function(){
                		   var type = Ext.getCmp("discountTypeNew").getValue();
                		   if(type=='0'){
                			   Ext.getCmp("sumcount0").show().enable();
                			   Ext.getCmp("sumcount1").show().enable();
                			   Ext.getCmp("sumcount0l").show().enable();
                			   Ext.getCmp("sumcount1l").show().enable();
                			   Ext.getCmp("discount0M").show().enable();
                			   Ext.getCmp("discount0J").show().enable();
                			   Ext.getCmp("discount0R").hide().disable();
                			   Ext.getCmp("discount0Z").hide().disable();
                               Ext.getCmp("openTypeN").show().enable();
                               Ext.getCmp("isDownloadN").show().enable();
                               Ext.getCmp("openLianN").show().enable();
                               Ext.getCmp("acquirersIdN").show().enable();
                               Ext.getCmp("discountCardNew").show().enable();
                               Ext.getCmp("sumcount0").reset();
                               Ext.getCmp("sumcount1").reset();
                               Ext.getCmp("sumcount0l").reset();
                               Ext.getCmp("sumcount1l").reset();
                			   Ext.getCmp("discount0M").reset();
                			   Ext.getCmp("discount0J").reset();
                			   Ext.getCmp("discount0R").reset();
                			   Ext.getCmp("discount0Z").reset();
                               Ext.getCmp("openTypeN").reset();
                               Ext.getCmp("isDownloadN").reset();
                               Ext.getCmp("openLianN").reset();
                               Ext.getCmp("acquirersIdN").reset();
                               Ext.getCmp("discountCardNew").reset();
                			   hideLabel('discount0Z',false);
                			   hideLabel('discount0R',false);
                			   hideLabel('discount0J',true);
                			   hideLabel('sumcount0',true);
                			   hideLabel('sumcount1',true);
                			   hideLabel('sumcount0l',true);
                			   hideLabel('sumcount1l',true);
                			   hideLabel('discount0M',true);
                               hideLabel('openTypeN',true);
                               hideLabel('isDownloadN',true);
                               hideLabel('openLianN',true);
                               hideLabel('acquirersIdN',true);
                               hideLabel('discountCardNew',true);
                			   Ext.getCmp("discount0M").getEl().up('.x-form-item').child('.x-form-item-label').dom.innerHTML="满*（单位:分）";
                		   }else if(type=='1'){
                			   Ext.getCmp("sumcount0").show().enable();
                			   Ext.getCmp("sumcount1").show().enable();
                			   Ext.getCmp("sumcount0l").show().enable();
                			   Ext.getCmp("sumcount1l").show().enable();
                			   Ext.getCmp("discount0M").show().enable();
                			   Ext.getCmp("discount0J").hide().disable();
                			   Ext.getCmp("discount0R").hide().disable();
                			   Ext.getCmp("discount0Z").show().enable();
                               Ext.getCmp("openTypeN").show().enable();
                               Ext.getCmp("isDownloadN").show().enable();
                               Ext.getCmp("openLianN").show().enable();
                               Ext.getCmp("acquirersIdN").show().enable();
                               Ext.getCmp("discountCardNew").show().enable();
                               Ext.getCmp("sumcount0").reset();
                               Ext.getCmp("sumcount1").reset();
                               Ext.getCmp("sumcount0l").reset();
                               Ext.getCmp("sumcount1l").reset();
                			   Ext.getCmp("discount0M").reset();
                			   Ext.getCmp("discount0J").reset();
                			   Ext.getCmp("discount0R").reset();
                			   Ext.getCmp("discount0Z").reset();
                               Ext.getCmp("openTypeN").reset();
                               Ext.getCmp("isDownloadN").reset();
                               Ext.getCmp("openLianN").reset();
                               Ext.getCmp("acquirersIdN").reset();
                               Ext.getCmp("discountCardNew").reset();
                               hideLabel('sumcount0',true);
                               hideLabel('sumcount1',true);
                               hideLabel('sumcount0l',true);
                               hideLabel('sumcount1l',true);
                			   hideLabel('discount0Z',true);
                			   hideLabel('discount0R',false);
                			   hideLabel('discount0J',false);
                			   hideLabel('discount0M',true);
                               hideLabel('openTypeN',true);
                               hideLabel('isDownloadN',true);
                               hideLabel('openLianN',true);
                               hideLabel('acquirersIdN',true);
                               hideLabel('discountCardNew',true);
                			   Ext.getCmp("discount0M").getEl().up('.x-form-item').child('.x-form-item-label').dom.innerHTML="限额*（单位:分）";
                		   }else if(type=='2'){
                			   Ext.getCmp("sumcount0").hide().disable();
                			   Ext.getCmp("sumcount1").hide().disable();
                			   Ext.getCmp("sumcount0l").show().enable();
                			   Ext.getCmp("sumcount1l").show().enable();
                			   Ext.getCmp("discount0M").hide().disable();
                			   Ext.getCmp("discount0J").hide().disable();
                			   Ext.getCmp("discount0Z").hide().disable();
                			   Ext.getCmp("discount0R").show().enable();
                               Ext.getCmp("openTypeN").hide().disable();
                               Ext.getCmp("isDownloadN").hide().disable();
                               Ext.getCmp("openLianN").hide().disable();
                               Ext.getCmp("acquirersIdN").hide().disable();
                               Ext.getCmp("discountCardNew").show().enable();
                               Ext.getCmp("sumcount0").reset();
                               Ext.getCmp("sumcount1").reset();
                               Ext.getCmp("sumcount0l").reset();
                               Ext.getCmp("sumcount1l").reset();
                			   Ext.getCmp("discount0M").reset();
                			   Ext.getCmp("discount0J").reset();
                			   Ext.getCmp("discount0R").reset();
                			   Ext.getCmp("discount0Z").reset();
                               Ext.getCmp("openTypeN").reset();
                               Ext.getCmp("isDownloadN").reset();
                               Ext.getCmp("openLianN").reset();
                               Ext.getCmp("acquirersIdN").reset();
                               Ext.getCmp("discountCardNew").reset();
                			   hideLabel('discount0Z',false);
                			   hideLabel('discount0R',true);
                			   hideLabel('discount0J',false);
                			   hideLabel('sumcount0',false);
                			   hideLabel('sumcount1',false);
                			   hideLabel('sumcount0l',false);
                			   hideLabel('sumcount1l',false);
                			   hideLabel('discount0M',false);
                               hideLabel('openTypeN',false);
                               hideLabel('isDownloadN',false);
                               hideLabel('openLianN',false);
                               hideLabel('acquirersIdN',false);
                               hideLabel('discountCardNew',true);
                		   }else if(type=='3'){
                			   Ext.getCmp("sumcount0").show().enable();
                			   Ext.getCmp("sumcount1").show().enable();
                			   Ext.getCmp("sumcount0l").show().enable();
                			   Ext.getCmp("sumcount1l").show().enable();
                               Ext.getCmp("discount0M").hide().disable();
                			   Ext.getCmp("discount0J").hide().disable();
                			   Ext.getCmp("discount0Z").hide().disable();
                			   Ext.getCmp("discount0R").hide().disable();
                               Ext.getCmp("openTypeN").hide().disable();
                               Ext.getCmp("isDownloadN").hide().disable();
                               Ext.getCmp("openLianN").hide().disable();
                               Ext.getCmp("acquirersIdN").hide().disable();
                               Ext.getCmp("discountCardNew").hide().disable();
                               Ext.getCmp("sumcount0").reset();
                               Ext.getCmp("sumcount1").reset();
                               Ext.getCmp("sumcount0l").reset();
                               Ext.getCmp("sumcount1l").reset();
                			   Ext.getCmp("discount0M").reset();
                			   Ext.getCmp("discount0J").reset();
                			   Ext.getCmp("discount0R").reset();
                			   Ext.getCmp("discount0Z").reset();
                               Ext.getCmp("openTypeN").reset();
                               Ext.getCmp("isDownloadN").reset();
                               Ext.getCmp("openLianN").reset();
                               Ext.getCmp("acquirersIdN").reset();
                               Ext.getCmp("discountCardNew").reset();
                               hideLabel('sumcount0',true);
                               hideLabel('sumcount1',true);
                               hideLabel('sumcount0l',true);
                               hideLabel('sumcount1l',true);
                			   hideLabel('discount0Z',false);
                			   hideLabel('discount0R',false);
                			   hideLabel('discount0J',false);
                			   hideLabel('discount0M',false);
                               hideLabel('openTypeN',false);
                               hideLabel('isDownloadN',false);
                               hideLabel('openLianN',false);
                               hideLabel('acquirersIdN',false);
                               hideLabel('discountCardNew',false);
                           }
                	   }
                   }
                }]
           },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'numberfield',
                        fieldLabel: '满*（单位:分）',
                        width:180,
                        id: 'discount0M',
                        name: 'discount0M',
                        readOnly: false,
                        allowBlank: false,
                        regex: /^[0-9]{1,10}$/,
                   	    regexText: '只能输入1-10位数字',
                        hidden:true
                    }]
            },{
                columnWidth: .5, 
                    layout: 'form',
                    items:[{
                        xtype: 'numberfield',
                        fieldLabel: '减*（单位:分）',
                        width:180,
                        id: 'discount0J',
                        name: 'discount0J',
                        readOnly: false,
                        allowBlank: false,
                        regex: /^[0-9]{1,10}$/,
                   	    regexText: '只能输入1-10位数字',
                        hidden:true
                    }]
            },{
                columnWidth: .5, 
                    layout: 'form',
                    items:[{
                        xtype: 'numberfield',
                        fieldLabel: '折扣*(%)',
                        width:180,
                        minValue:0,
                        maxValue:99,
                        minText:'最小为0',
                        maxText:'最大为99',
                        maxLength:2,
                        maxLengthText:'长度过长',
                        id: 'discount0Z',
                        name: 'discount0Z',
                        readOnly: false,
                        allowBlank: false,
                        hidden:true,
                        listeners:{
                           	render:function(field){
                           		Ext.QuickTips.init();   
                           		Ext.QuickTips.register({   
                           		target : field.el,   
                           		text : '填写90即按90%打折'   
                           		}) 
                           	}
                           } 
                    }]
            },{
                columnWidth: .5, 
                    layout: 'form',
                    items:[{
                        xtype: 'textfield',
                        fieldLabel: '任意*',
                        width:180,
                        maxLength:200,
                        maxLengthText:'长度过长',
                        id: 'discount0R',
                        name: 'discount0R',
                        readOnly: false,
                        allowBlank: false,
                        hidden:true
                    }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items: [{
                     xtype: 'combo',
                     fieldLabel: '开启方式*',
                     id: 'openTypeN',
                     hiddenName:'openTypeNew',
                     allowBlank: false,
                     width:180,
                     store: new Ext.data.ArrayStore({
                         fields: ['valueField','displayField'], 

                         data: [['01','依据终端开启'],['02','依据卡bin开启']]
                     })
                 }]
             }
            ,{
                 columnWidth: .5,
                 layout: 'form',
                 items: [{
                     xtype: 'combo',
                     fieldLabel: '是否已下载*',
                     id: 'isDownloadN',
                     hiddenName:'isDownloadNew',
                     allowBlank: false,
                     width:180,
                     store: new Ext.data.ArrayStore({
                         fields: ['valueField','displayField'], 

//                         data: [['00','已完成'],['01','需继续下载']]
                         data: [['00','已完成']]
                     }),
                     value : '00',
                     editable:false
                 }]
             },
            {
                 columnWidth: .5,
                 layout: 'form',
                 items: [{
                     xtype: 'combo',
                     fieldLabel: '优惠方式*',
                     id: 'openLianN',
                     hiddenName:'openLianNew',
                     allowBlank: false,
                     width:180,
                     store: new Ext.data.ArrayStore({
                         fields: ['valueField','displayField'], 

//                         data: [['00','不开启'],['01','开启']]
                     data: [['00','仅脱机优惠'],['01','脱机+联机优惠'],['02','仅联机优惠']]
//                     data: [['01','脱机+联机优惠']]
                     }),
                     value:'01',
                     editable:false,
                     listeners:{
                  	   'select':function(){
                  		   var type = Ext.getCmp("openLianN").getValue();
                  		   if(type=='00'){
                  			     Ext.getCmp("sumcount0").show().enable();
                 			     Ext.getCmp("sumcount1").show().enable();
                  			     Ext.getCmp("sumcount0l").hide().disable();
                                 Ext.getCmp("sumcount1l").hide().disable();
                                 Ext.getCmp("sumcount0").reset();
                                 Ext.getCmp("sumcount1").reset();
                                 Ext.getCmp("sumcount0l").reset();
                                 Ext.getCmp("sumcount1l").reset();
                                 hideLabel('sumcount0',true);
                                 hideLabel('sumcount1',true);
                                 hideLabel('sumcount0l',false);
                                 hideLabel('sumcount1l',false);
                  		   }else if(type=='01'){
                  			   Ext.getCmp("sumcount0").show().enable();
                  			   Ext.getCmp("sumcount1").show().enable();
                  			   Ext.getCmp("sumcount0l").show().enable();
                  			   Ext.getCmp("sumcount1l").show().enable();
                  			   Ext.getCmp("sumcount0").reset();
                               Ext.getCmp("sumcount1").reset();
                               Ext.getCmp("sumcount1l").reset();
                               Ext.getCmp("sumcount1l").reset();
                               hideLabel('sumcount0',true);
                               hideLabel('sumcount1',true);
                               hideLabel('sumcount0l',true);
                               hideLabel('sumcount1l',true);
                  		   }else if(type=='02'){
                  			   Ext.getCmp("sumcount0").hide().disable();
                  			   Ext.getCmp("sumcount1").hide().disable();
                  			   Ext.getCmp("sumcount0l").show().enable();
                  			   Ext.getCmp("sumcount1l").show().enable();
                  			   Ext.getCmp("sumcount0").reset();
                               Ext.getCmp("sumcount1").reset();
                               Ext.getCmp("sumcount1l").reset();
                               Ext.getCmp("sumcount1l").reset();
                               hideLabel('sumcount0',false);
                               hideLabel('sumcount1',false);
                               hideLabel('sumcount0l',true);
                               hideLabel('sumcount1l',true);
                  		   }
                  	   }
                     }
                 }]
             },
             {
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '收单方*',
                    id: 'acquirersIdN',
                    hiddenName:'acquirersIdNew',
                    allowBlank: false,
                    width:180,
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'], 

                        data: [['01','银联'],['00','山东一卡通']]
                    }),
                    value : '01'
                }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'textarea',
                    fieldLabel: '卡bin',
                    id: 'discountCardNew',
                    name:'discountCardNew',
                    width:'80%',
                    height:60,
                    grow:true,
                    listeners : {  
                        render : function(field) {  
                            Ext.QuickTips.init();  
                            Ext.QuickTips.register({  
                                target : field.el,  
                                text : '示例：111111,222222'  
                            })  
                        }  
                    }  
                }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'datefield',
                    fieldLabel: '开始日期*',
                    format : 'Y-m-d',
                    id: 'startTimeEffNew',
                    name:'startTimeEffNew',
                    anchor :'60%' ,
                    vtype: 'daterange',
                    minValue:nowDate,
                    allowBlank: false,
                    width:180                  
                }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                	xtype: 'datefield',
                    fieldLabel: '截止日期*',
                    format : 'Y-m-d',
                    name :'endTimeEffNew',
                    id :'endTimeEffNew',
                    vtype: 'daterange',
                    minValue:nowDate,
                    anchor :'60%' ,
                    allowBlank: false,
                    width:180                  
                }]
            },{
                 columnWidth: 1,
                 layout: 'form',
                 items: [{
                    xtype: 'textfield',
                    fieldLabel: '备注',
                    id: 'remarkNew',
                    name: 'remarkNew',
                    width: 180
                  }]
             },{
                 columnWidth: .5, 
                 layout: 'form',
                 items: [{
                     xtype: 'numberfield',
                     fieldLabel: '脱机按卡限次(不限填0)',
                     width:180,
                     id: 'sumcount0',
                     name: 'sumcount0',
                     readOnly: false,
                     allowBlank: false,
             		   regex: /^[0-9]{1,4}$/,
                	   regexText: '只能输入1-4位数字'
                 }]
  	       },{
  	           columnWidth: .5, 
  	           layout: 'form',
  	           items: [{
  	               xtype: 'numberfield',
  	               fieldLabel: '脱机终端限次(不限填0)',
  	               width:180,
  	               id: 'sumcount1',
  	               name: 'sumcount1',
  	               readOnly: false,
  	               allowBlank: false,
  	               regex: /^[0-9]{1,4}$/,
                	   regexText: '只能输入1-4位数字'
  	           }]
  	   },{
             columnWidth: .5, 
             layout: 'form',
             items: [{
                 xtype: 'numberfield',
                 fieldLabel: '联机按卡限次(不限填0)',
                 width:180,
                 id: 'sumcount0l',
                 name: 'sumcount0l',
                 readOnly: false,
                 allowBlank: false,
         		   regex: /^[0-9]{1,4}$/,
            	   regexText: '只能输入1-4位数字'
             }]
         },{
             columnWidth: .5, 
             layout: 'form',
             items: [{
                 xtype: 'numberfield',
                 fieldLabel: '联机终端限次(不限填0)',
                 width:180,
                 id: 'sumcount1l',
                 name: 'sumcount1l',
                 readOnly: false,
                 allowBlank: false,
                 regex: /^[0-9]{1,4}$/,
            	   regexText: '只能输入1-4位数字'
             }]
        }]
            }]
    });
    /**************  终端表单  *********************/
	var termForm = new Ext.form.FormPanel({
		frame: true,
		height: 400,
		width: 750,
		labelWidth: 140,
		waitMsgTarget: true,
		layout: 'column',
		items: [termPanel]
	});
   
    /***********  终端信息窗口  *****************/
	var termWin = new Ext.Window({
		title: '客户优惠规则添加',
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
				
				var strExp=/^[A-Za-z0-9]+$/;
				var discountCodeN=Ext.getCmp("discountCodeNew").getValue()
				if(!strExp.test(discountCodeN)){
					showErrorMsg("营销代码：请输入数字和字母",grid);
					return    false;
					}
				
				//验证卡bin格式
				var openTypeN=Ext.getCmp("openTypeN").getValue();
				if(openTypeN=="02"){
					var discountCardNew=Ext.getCmp("discountCardNew").getValue();
					var reg=/^((([0-9]{5})|([0-9]{6})|([0-9]{8})){1,})((,(([0-9]{5})|([0-9]{6})|([0-9]{8})))*)$/;
					if(!reg.test(discountCardNew)){
						showErrorMsg("卡bin格式不正确。(示例：111111,222222)",grid);
						return    false;
					}
				}
				
				
		//		if(termForm.getForm().isValid()) {
					var endtime=Ext.getCmp('endTimeEffNew').getValue(),starttime=Ext.getCmp('startTimeEffNew').getValue();
	            	if(endtime!=''&&starttime!=''&&endtime<starttime){
	            		showErrorMsg("请保证截止时间不小于起始时间",grid);
	    				return;
	            	}
					var type=Ext.getCmp('discountTypeNew').getValue();
					if(type=='0'){
						var discountm=Ext.getCmp('discount0M').getValue(),discountj=Ext.getCmp('discount0J').getValue();
						if(discountm<=discountj){
							showErrorMsg("请保证减少数额小于总数额",grid);
		    				return;
						}
						var mlen=discountm.toString().length,jlen=discountj.toString().length;
						if(mlen+jlen>20){
							showErrorMsg("满减数额总长度过长",grid);
		    				return;
						}
					}
					if(type=='1'){
						var discountz=Ext.getCmp('discount0Z').getValue();
						if(discountz> 99 || discountz < 0){
							showErrorMsg("请保证折扣数小于100",grid);
		    				return;
						}
					}
					Ext.Ajax.requestNeedAuthorise({
						url: 'T21402Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(rsp,opt) {
							 var rspObj = Ext.decode(rsp.responseText);
							 if(rspObj.msg.indexOf("成功")>0){
								 showSuccessMsg(rspObj.msg,termWin); 
							 }else{
								 showErrorMsg(rspObj.msg,termWin);
							 }
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							termForm.getForm().reset();
                            
                            termWin.hide();
						},
						failure: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							termPanel.setActiveTab('info1New');
							showErrorMsg(rspObj.msg,termWin);
						},
						params: {
							sumcount0:Ext.getCmp("sumcount0").getValue(),
							sumcount1:Ext.getCmp("sumcount1").getValue(),
							sumcount0l:Ext.getCmp("sumcount0l").getValue(),
							sumcount1l:Ext.getCmp("sumcount1l").getValue(),
							discountCode:Ext.getCmp("discountCodeNew").getValue(),
							discountType:Ext.getCmp("discountTypeNew").getValue(),
							discountCard:Ext.getCmp("discountCardNew").getValue(),
                            openLian:Ext.getCmp("openLianN").getValue(),
                            openType:Ext.getCmp("openTypeN").getValue(),
                            isDownload:Ext.getCmp("isDownloadN").getValue(),
                            acquirersId:Ext.getCmp("acquirersIdN").getValue(),
                            remark:Ext.getCmp("remarkNew").getValue(),						
							discountm:Ext.getCmp("discount0M").getValue(),
							discountj:Ext.getCmp("discount0J").getValue(),
							discountzk:Ext.getCmp("discount0Z").getValue(),
							discountAny:Ext.getCmp("discount0R").getValue(),
							startTimeEff:Ext.getCmp("startTimeEffNew").getValue(),
							endTimeEff:Ext.getCmp("endTimeEffNew").getValue(),
							txnId: '21402',
							subTxnId: '01'
						}
					});

//			}else{
//				termPanel.setActiveTab('info1New');
//				termForm.getForm().isValid();
//			}
		}
		},{
			text: '重置',
			handler: function() {
					termForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				termWin.hide();
				termForm.getForm().reset();
			}
		}]
	});
/**************** 终端修改 *************************/
    var updTermPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 450,
        width: 750,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1Upd',
                layout: 'column',
                frame: true,
                items: [{
                 columnWidth: 1,
                 layout: 'form',
                 items: [{
                	 xtype: 'textfield',
                     id: 'discountIdUpd',
                     name: 'discountIdUpd',
                     width: 180,
                     hidden:true,
                     readOnly:true
                  }]
             },{
                 columnWidth: 1,
                 layout: 'form',
                 items: [{
                	 xtype: 'textfield',
                     fieldLabel: '营销代码*',
                     id: 'discountCodeUpd',
                     name: 'discountCodeUpd',
                     width: 180,
                     readOnly:true,
                     allowBlank:false,
//                     maxLength:20,
//                     minLength:20,
//                     minLengthText:'长度固定20位',
//                     maxLengthText:'长度固定20位',
                     listeners:{
                     	render:function(field){
//                     		Ext.QuickTips.init();   
//                     		Ext.QuickTips.register({   
//                     		target : field.el,   
//                     		text : '请输入数字或字母，长度固定20位'   
//                     		}) 
                     	}
                     }
                  }]
             },{
                columnWidth: 1,
                layout: 'form',
                items:[{
                	xtype: 'combo',
                    fieldLabel: '折扣类型*',
                    id: 'discountTypeUpd',
                    hiddenName: 'discountTypeU',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'], 
     
                        data: [['0','满减'],['1','折扣'],['2','任意']]
                    }),
                    width:180,
                    allowBlank: false,
                    readOnly:true,
                    listeners:{
                 	   'select':function(){
                 		   var type = Ext.getCmp("discountTypeUpd").getValue();
                 		   if(type=='0'){
                 			   Ext.getCmp("sumcount0Upd").show().enable();
                 			   Ext.getCmp("sumcount1Upd").show().enable();
                 			   Ext.getCmp("sumcount0lUpd").show().enable();
                 			   Ext.getCmp("sumcount1lUpd").show().enable();
                			   Ext.getCmp("discount0MU").show().enable();
                			   Ext.getCmp("discount0JU").show().enable();
                			   Ext.getCmp("discount0RU").hide().disable();
                			   Ext.getCmp("discount0ZU").hide().disable();
                               Ext.getCmp("openTypeU").show().enable();
                               Ext.getCmp("isDownloadU").show().enable();
                               Ext.getCmp("openLianU").show().enable();
                               Ext.getCmp("acquirersIdU").show().enable();
                               Ext.getCmp("discountCardUpd").show().enable();
                               Ext.getCmp("sumcount0Upd").reset();
                               Ext.getCmp("sumcount1Upd").reset();
                               Ext.getCmp("sumcount0lUpd").reset();
                               Ext.getCmp("sumcount1lUpd").reset();
                			   Ext.getCmp("discount0MU").reset();
                			   Ext.getCmp("discount0JU").reset();
                			   Ext.getCmp("discount0RU").reset();
                			   Ext.getCmp("discount0ZU").reset();
                               Ext.getCmp("openTypeU").reset();
                               Ext.getCmp("isDownloadU").reset();
                               Ext.getCmp("openLianU").reset();
                               Ext.getCmp("acquirersIdU").reset();
                               Ext.getCmp("discountCardUpd").reset();
                			   hideLabel('discount0ZU',false);
                			   hideLabel('discount0RU',false);
                			   hideLabel('discount0JU',true);
                			   hideLabel('discount0MU',true);
                			   hideLabel('sumcount1Upd',true);
                			   hideLabel('sumcount0Upd',true);
                			   hideLabel('sumcount1lUpd',true);
                			   hideLabel('sumcount0lUpd',true);
                               hideLabel('openTypeU',true);
                               hideLabel('isDownloadU',true);
                               hideLabel('openLianU',true);
                               hideLabel('acquirersIdU',true);
                               hideLabel('discountCardUpd',true);
                			   Ext.getCmp("discount0MU").getEl().up('.x-form-item').child('.x-form-item-label').dom.innerHTML="满*（单位:分）";
                		   }else if(type=='1'){
                			   Ext.getCmp("sumcount0Upd").show().enable();
                			   Ext.getCmp("sumcount1Upd").show().enable();
                			   Ext.getCmp("sumcount0lUpd").show().enable();
                			   Ext.getCmp("sumcount1lUpd").show().enable();
                			   Ext.getCmp("discount0MU").show().enable();
                			   Ext.getCmp("discount0JU").hide().disable();
                			   Ext.getCmp("discount0RU").hide().disable();
                			   Ext.getCmp("discount0ZU").show().enable();
                               Ext.getCmp("openTypeU").show().enable();
                               Ext.getCmp("isDownloadU").show().enable();
                               Ext.getCmp("openLianU").show().enable();
                               Ext.getCmp("acquirersIdU").show().enable();
                               Ext.getCmp("discountCardUpd").show().enable();
                               Ext.getCmp("sumcount0Upd").reset();
                               Ext.getCmp("sumcount1Upd").reset();
                               Ext.getCmp("sumcount0lUpd").reset();
                               Ext.getCmp("sumcount1lUpd").reset();
                			   Ext.getCmp("discount0MU").reset();
                			   Ext.getCmp("discount0JU").reset();
                			   Ext.getCmp("discount0RU").reset();
                			   Ext.getCmp("discount0ZU").reset();
                               Ext.getCmp("openTypeU").reset();
                               Ext.getCmp("isDownloadU").reset();
                               Ext.getCmp("openLianU").reset();
                               Ext.getCmp("acquirersIdU").reset();
                               Ext.getCmp("discountCardUpd").reset();
                               hideLabel('sumcount0Upd',true);
                               hideLabel('sumcount1Upd',true);
                               hideLabel('sumcount0lUpd',true);
                               hideLabel('sumcount1lUpd',true);
                			   hideLabel('discount0ZU',true);
                			   hideLabel('discount0RU',false);
                			   hideLabel('discount0JU',false);
                			   hideLabel('discount0MU',true);
                               hideLabel('openTypeU',true);
                               hideLabel('isDownloadU',true);
                               hideLabel('openLianU',true);
                               hideLabel('acquirersIdU',true);
                               hideLabel('discountCardUpd',true);
                			   Ext.getCmp("discount0MU").getEl().up('.x-form-item').child('.x-form-item-label').dom.innerHTML="限额*（单位:分）";
                		   }else if(type=='2'){
                			   Ext.getCmp("sumcount0Upd").hide().disable();
                			   Ext.getCmp("sumcount1Upd").hide().disable();
                			   Ext.getCmp("sumcount0lUpd").hide().disable();
                			   Ext.getCmp("sumcount1lUpd").hide().disable();
                			   Ext.getCmp("discount0MU").hide().disable();
                			   Ext.getCmp("discount0JU").hide().disable();
                			   Ext.getCmp("discount0ZU").hide().disable();
                			   Ext.getCmp("discount0RU").show().enable();
                               Ext.getCmp("openTypeU").hide().disable();
                               Ext.getCmp("isDownloadU").hide().disable();
                               Ext.getCmp("openLianU").hide().disable();
                               Ext.getCmp("acquirersIdU").hide().disable();
                               Ext.getCmp("discountCardUpd").hide().disable();
                               Ext.getCmp("sumcount0Upd").reset();
                               Ext.getCmp("sumcount1Upd").reset();
                               Ext.getCmp("sumcount0lUpd").reset();
                               Ext.getCmp("sumcount1lUpd").reset();
                			   Ext.getCmp("discount0MU").reset();
                			   Ext.getCmp("discount0JU").reset();
                			   Ext.getCmp("discount0RU").reset();
                			   Ext.getCmp("discount0ZU").reset();
                               Ext.getCmp("openTypeU").reset();
                               Ext.getCmp("isDownloadU").reset();
                               Ext.getCmp("openLianU").reset();
                               Ext.getCmp("acquirersIdU").reset();
                               Ext.getCmp("discountCardUpd").reset();
                               hideLabel('sumcount0Upd',false);
                               hideLabel('sumcount1Upd',false);
                               hideLabel('sumcount0lUpd',false);
                               hideLabel('sumcount1lUpd',false);
                			   hideLabel('discount0ZU',false);
                			   hideLabel('discount0RU',true);
                			   hideLabel('discount0JU',false);
                			   hideLabel('discount0MU',false);
                               hideLabel('openTypeU',false);
                               hideLabel('isDownloadU',false);
                               hideLabel('openLianU',false);
                               hideLabel('acquirersIdU',false);
                               hideLabel('discountCardUpd',false);
                		   }else if(type=='3'){
                			   Ext.getCmp("sumcount0Upd").show().enable();
                			   Ext.getCmp("sumcount1Upd").show().enable();
                			   Ext.getCmp("sumcount0lUpd").show().enable();
                			   Ext.getCmp("sumcount1lUpd").show().enable();
                               Ext.getCmp("discount0MU").hide().disable();
                			   Ext.getCmp("discount0JU").hide().disable();
                			   Ext.getCmp("discount0ZU").hide().disable();
                			   Ext.getCmp("discount0RU").hide().disable();
                               Ext.getCmp("openTypeU").hide().disable();
                               Ext.getCmp("isDownloadU").hide().disable();
                               Ext.getCmp("openLianU").hide().disable();
                               Ext.getCmp("acquirersIdU").hide().disable();
                               Ext.getCmp("discountCardUpd").hide().disable();
                               Ext.getCmp("sumcount0Upd").reset();
                               Ext.getCmp("sumcount1Upd").reset();
                               Ext.getCmp("sumcount0lUpd").reset();
                               Ext.getCmp("sumcount1lUpd").reset();
                			   Ext.getCmp("discount0MU").reset();
                			   Ext.getCmp("discount0JU").reset();
                			   Ext.getCmp("discount0RU").reset();
                			   Ext.getCmp("discount0ZU").reset();
                               Ext.getCmp("openTypeU").reset();
                               Ext.getCmp("isDownloadU").reset();
                               Ext.getCmp("openLianU").reset();
                               Ext.getCmp("acquirersIdU").reset();
                               Ext.getCmp("discountCardUpd").reset();
                               hideLabel('sumcount0Upd',true);
                               hideLabel('sumcount1Upd',true);
                               hideLabel('sumcount0lUpd',true);
                               hideLabel('sumcount1lUpd',true);
                			   hideLabel('discount0ZU',false);
                			   hideLabel('discount0RU',false);
                			   hideLabel('discount0JU',false);
                			   hideLabel('discount0MU',false);
                               hideLabel('openTypeU',false);
                               hideLabel('isDownloadU',false);
                               hideLabel('openLianU',false);
                               hideLabel('acquirersIdU',false);
                               hideLabel('discountCardUpd',false);
                           }
                 		   
                 	   }
                    }
                }]
           },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'numberfield',
                        fieldLabel: '满*（单位:分）',
                        width:180,
                        id: 'discount0MU',
                        name: 'discount0MU',
                        regex: /^[0-9]{1,10}$/,
                   	    regexText: '只能输入1-10位数字',
                        readOnly: false,
                        allowBlank: false
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'numberfield',
                        fieldLabel: '减*（单位:分）',
                        width:180,
                        id: 'discount0JU',
                        name: 'discount0JU',
                        regex: /^[0-9]{1,10}$/,
                   	    regexText: '只能输入1-10位数字',
                        readOnly: false,
                        allowBlank: false
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'numberfield',
                        fieldLabel: '折扣*(%)',
                        width:180,
                        minValue:0,
                        maxValue:99,
                        minText:'最小为0',
                        maxText:'最大为99',
                        maxLength:2,
                        maxLengthText:'长度过长',
                        id: 'discount0ZU',
                        name: 'discount0ZU',
                        readOnly: false,
                        allowBlank: false,
                        hidden:true,
                        listeners:{
                           	render:function(field){
                           		Ext.QuickTips.init();   
                           		Ext.QuickTips.register({   
                           		target : field.el,   
                           		text : '填写90即按90%打折'   
                           		}) 
                           	}
                           }
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '任意*',
                        width:180,
                        maxLength:200,
                        maxLengthText:'长度过长',
                        id: 'discount0RU',
                        name: 'discount0RU',
                        readOnly: false,
                        allowBlank: false,
                        hidden:true
                    }]
            },
             {
                 columnWidth: .5,
                 layout: 'form',
                 items: [{
                     xtype: 'combo',
                     fieldLabel: '开启方式*',
                     id: 'openTypeU',
                     hiddenName:'openTypeUpd',
                     allowBlank: false,
                     width:180,
                     store: new Ext.data.ArrayStore({
                         fields: ['valueField','displayField'], 

                         data: [['01','依据终端开启'],['02','依据卡bin开启']]
                     })
                 }]
             },
             {
                 columnWidth: .5,
                 layout: 'form',
                 items: [{
                     xtype: 'combo',
                     fieldLabel: '是否已下载*',
                     id: 'isDownloadU',
                     hiddenName:'isDownloadUpd',
                     allowBlank: false,
                     width:180,
                     store: new Ext.data.ArrayStore({
                         fields: ['valueField','displayField'], 

//                         data: [['00','已完成'],['01','需继续下载']]
                         data: [['00','已完成']]
                     }),
                     value : '00',
                     editable:false
                 }]
             },
             {
                 columnWidth: .5,
                 layout: 'form',
                 items: [{
                     xtype: 'combo',
                     fieldLabel: '优惠方式*',
                     id: 'openLianU',
                     hiddenName:'openLianUpd',
                     allowBlank: false,
                     width:180,
                     store: new Ext.data.ArrayStore({
                         fields: ['valueField','displayField'], 

//                         data: [['00','不开启'],['01','开启']]
                         data: [['00','仅脱机优惠'],['01','脱机+联机优惠'],['02','仅联机优惠']]
//                         data: [['01','脱机+联机优惠']]
                     }),
                 //value : '00',
                 editable:false,
                 listeners:{
                	   'select':function(){
                		   var type = Ext.getCmp("openLianU").getValue();
                		   if(type=='00'){
                			     Ext.getCmp("sumcount0Upd").show().enable();
               			     Ext.getCmp("sumcount1Upd").show().enable();
                			     Ext.getCmp("sumcount0lUpd").hide().disable();
                               Ext.getCmp("sumcount1lUpd").hide().disable();
//                               Ext.getCmp("sumcount0Upd").reset();
//                               Ext.getCmp("sumcount1Upd").reset();
                               Ext.getCmp("sumcount0lUpd").reset();
                               Ext.getCmp("sumcount1lUpd").reset();
                               hideLabel('sumcount0Upd',true);
                               hideLabel('sumcount1Upd',true);
                               hideLabel('sumcount0lUpd',false);
                               hideLabel('sumcount1lUpd',false);
                		   }else if(type=='01'){
                			   Ext.getCmp("sumcount0Upd").show().enable();
                			   Ext.getCmp("sumcount1Upd").show().enable();
                			   Ext.getCmp("sumcount0lUpd").show().enable();
                			   Ext.getCmp("sumcount1lUpd").show().enable();
//                			   Ext.getCmp("sumcount0Upd").reset();
//                             Ext.getCmp("sumcount1Upd").reset();
//                             Ext.getCmp("sumcount1lUpd").reset();
//                             Ext.getCmp("sumcount1lUpd").reset();
                             hideLabel('sumcount0Upd',true);
                             hideLabel('sumcount1Upd',true);
                             hideLabel('sumcount0lUpd',true);
                             hideLabel('sumcount1lUpd',true);
                		   }else if(type=='02'){
                			   Ext.getCmp("sumcount0Upd").hide().disable();
                			   Ext.getCmp("sumcount1Upd").hide().disable();
                			   Ext.getCmp("sumcount0lUpd").show().enable();
                			   Ext.getCmp("sumcount1lUpd").show().enable();
                			   Ext.getCmp("sumcount0Upd").reset();
                             Ext.getCmp("sumcount1Upd").reset();
//                             Ext.getCmp("sumcount1lUpd").reset();
//                             Ext.getCmp("sumcount1lUpd").reset();
                             hideLabel('sumcount0Upd',false);
                             hideLabel('sumcount1Upd',false);
                             hideLabel('sumcount0lUpd',true);
                             hideLabel('sumcount1lUpd',true);
                		   }
                	   }
                   }
                 }]
             },
             {
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '收单方*',
                    id: 'acquirersIdU',
                    hiddenName:'acquirersIdUpd',
                    allowBlank: false,
                    width:180,
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'], 

                        data: [['01','银联'],['00','山东一卡通']]
                    }),
                    value : '01'
                }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'textarea',
                    fieldLabel: '卡bin',
                    id: 'discountCardUpd',
                    name:'discountCardUpd',
                    width:'80%',
                    height:60,
                    grow:true,
                    listeners : {  
                        render : function(field) {  
                            Ext.QuickTips.init();  
                            Ext.QuickTips.register({  
                                target : field.el,  
                                text : '示例：111111,222222'  
                            })  
                        }  
                    } 
                }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'datefield',
                    fieldLabel: '开始日期',
                    format : 'Y-m-d',
                    id: 'startTimeEffUpd',
                    name:'startTimeEffUpd',
                    anchor :'60%' ,
                    vtype: 'daterange',
                    minValue:nowDate,
                    allowBlank: false,
                    width:180                  
                }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                	xtype: 'datefield',
                    fieldLabel: '截止时间',
                    format : 'Y-m-d',
                    name :'endTimeEffUpd',
                    id :'endTimeEffUpd',
                    anchor :'60%' ,
                    vtype: 'daterange',
                    minValue:nowDate,
                    allowBlank: false,
                    width:180                  
                }]
            },{
                 columnWidth: 1,
                 layout: 'form',
                 items: [{
                	 xtype: 'textfield',
                     fieldLabel: '备注',
                     id: 'remarkUpd',
                     name: 'remarkUpd',
                     width: 180,
                     readOnly:false
                  }]
             },{
                 columnWidth: .5, 
                 layout: 'form',
                 items: [{
                     xtype: 'numberfield',
                     fieldLabel: '脱机按卡限次(不限填0)',
                     width:180,
                     id: 'sumcount0Upd',
                     name: 'sumcount1Upd',
                     readOnly: false,
                     allowBlank: false,
             		   regex: /^[0-9]{1,4}$/,
                	   regexText: '只能输入1-4位数字'
                 }]
  	       },{
  	           columnWidth: .5, 
  	           layout: 'form',
  	           items: [{
  	               xtype: 'numberfield',
  	               fieldLabel: '脱机终端限次(不限填0)',
  	               width:180,
  	               id: 'sumcount1Upd',
  	               name: 'sumcount1Upd',
  	               readOnly: false,
  	               allowBlank: false,
  	               regex: /^[0-9]{1,4}$/,
                	   regexText: '只能输入1-4位数字'
  	           }]
  	   },{
             columnWidth: .5, 
             layout: 'form',
             items: [{
                 xtype: 'numberfield',
                 fieldLabel: '联机按卡限次(不限填0)',
                 width:180,
                 id: 'sumcount0lUpd',
                 name: 'sumcount1lUpd',
                 readOnly: false,
                 allowBlank: false,
         		   regex: /^[0-9]{1,4}$/,
            	   regexText: '只能输入1-4位数字'
             }]
         },{
             columnWidth: .5, 
             layout: 'form',
             items: [{
                 xtype: 'numberfield',
                 fieldLabel: '联机终端限次(不限填0)',
                 width:180,
                 id: 'sumcount1lUpd',
                 name: 'sumcount1lUpd',
                 readOnly: false,
                 allowBlank: false,
                 regex: /^[0-9]{1,4}$/,
            	   regexText: '只能输入1-4位数字'
             }]
        }]
            }]
    });
/*******************  终端修改表单  *********************/
    var updTermForm = new Ext.form.FormPanel({
        frame: true,
        height: 450,
        width: 750,
        labelWidth: 140,
        waitMsgTarget: true,
        layout: 'column',
        items: [updTermPanel]
    });
   
/*******************  终端修改信息 *********************/
    var updTermWin = new Ext.Window({
        title: '商户优惠规则修改',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 750,
        autoHeight: true,
        layout: 'fit',
        items: [updTermForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'T201',
        resizable: false,
        buttons: [{
            text: '确定',
            handler: function() {
            	var strExp=/^[A-Za-z0-9]+$/;
				var discountCodeN=Ext.getCmp("discountCodeUpd").getValue()
				if(!strExp.test(discountCodeN)){
					showErrorMsg("营销代码：请输入数字和字母",grid);
					return    false;
					}
				//验证卡bin格式
				var openTypeN=Ext.getCmp("openTypeU").getValue();
				if(openTypeN=="02"){
					var discountCardNew=Ext.getCmp("discountCardUpd").getValue();
					var reg=/^((([0-9]{5})|([0-9]{6})|([0-9]{8})){1,})((,(([0-9]{5})|([0-9]{6})|([0-9]{8})))*)$/;
					if(!reg.test(discountCardNew)){
						showErrorMsg("卡bin格式不正确。(示例：111111,222222)",grid);
						return    false;
					}
				}
				
            	
					updTermPanel.setActiveTab("info1Upd");
                	if(updTermForm.getForm().isValid()){
                		var endtime=Ext.getCmp('endTimeEffUpd').getValue(),starttime=Ext.getCmp('startTimeEffUpd').getValue();
    	            	if(endtime!=''&&starttime!=''&&endtime<starttime){
    	            		showErrorMsg("请保证截止时间不小于起始时间",grid);
    	    				return;
    	            	}
                		var type=Ext.getCmp("discountTypeUpd").getValue();
                		if(type=='0'){
                			var discountm=Ext.getCmp('discount0MU').getValue(),discountj=Ext.getCmp('discount0JU').getValue();
    						if(discountm<=discountj){
    							showErrorMsg("请保证减少数额小于总数额",grid);
    		    				return;
    						}
    						var mlen=discountm.toString().length,jlen=discountj.toString().length;
    						if(mlen+jlen>20){
    							showErrorMsg("满减数额总长度过长",grid);
    		    				return;
    						}
                		}
                		Ext.Ajax.requestNeedAuthorise({
                        url: 'T21402Action.asp?method=update',
                        waitMsg: '正在提交，请稍后......',
                        success: function(rsp,opt) {
                        	var rspObj = Ext.decode(rsp.responseText); 
                        	if(rspObj.msg.indexOf("成功")>0){
                        		showSuccessMsg(rspObj.msg,updTermForm);
                        	}else{
                        		 showErrorMsg(rspObj.msg,updTermForm);
                        	}
                            grid.getStore().reload();
                            updTermForm.getForm().reset();
                            updTermWin.hide();
                            grid.getTopToolbar().items.items[2].disable();
                            grid.getTopToolbar().items.items[3].disable();
                        },
                        failure: function(rsp,opt) {
                        	var rspObj = Ext.decode(rsp.responseText);
                            updTermPanel.setActiveTab('info1Upd');
                            showErrorMsg(rspObj.msg,updTermForm);
                        },
                        params: {
                            txnId: '21402',
                            subTxnId: '02',
                            discountId:Ext.getCmp("discountIdUpd").getValue(),
                            discountCode:Ext.getCmp("discountCodeUpd").getValue(),
							discountType:Ext.getCmp("discountTypeUpd").getValue(),
							discountCard:Ext.getCmp("discountCardUpd").getValue(),
                            openLian:Ext.getCmp("openLianU").getValue(),
                            openType:Ext.getCmp("openTypeU").getValue(),
                            isDownload:Ext.getCmp("isDownloadU").getValue(),
                            acquirersId:Ext.getCmp("acquirersIdU").getValue(),
                            remark:Ext.getCmp("remarkUpd").getValue(),							
							discountm:Ext.getCmp("discount0MU").getValue(),
							discountj:Ext.getCmp("discount0JU").getValue(),
							discountzk:Ext.getCmp("discount0ZU").getValue(),
							discountAny:Ext.getCmp("discount0RU").getValue(),
							startTimeEff:Ext.getCmp("startTimeEffUpd").getValue(),
							endTimeEff:Ext.getCmp("endTimeEffUpd").getValue(),
							sumcount0:Ext.getCmp("sumcount0Upd").getValue(),
							sumcount1:Ext.getCmp("sumcount1Upd").getValue(),
							sumcount0l:Ext.getCmp("sumcount0lUpd").getValue(),
							sumcount1l:Ext.getCmp("sumcount1lUpd").getValue(),
                        }
                    });
               
                	}else{
                		updTermPanel.setActiveTab('info1Upd');
                		updTermForm.getForm().isValid()
                	}
            }
        },{
            text: '关闭',
            handler: function() {
                updTermWin.hide();
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
                grid.getTopToolbar().items.items[3].enable();
            } else {
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[3].disable();
            }

        }
    });
    //渲染到页面
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
	
});