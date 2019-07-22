Ext.onReady(function() {
	
	function hideLabel(sId, bShow){ 
		Ext.getCmp(sId).getEl().up('.x-form-item').child('.x-form-item-label').setDisplayed(bShow); 
	} 
	
	
	var flag2Store = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCHT_FALG3',function(ret){
 		var data=Ext.decode(ret);
 		flag2Store.loadData(data);
	});
					
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntInfoQuery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
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
			{name: 'connType',mapping: 'connType'},			
			{name: 'commEmail',mapping: 'commEmail'},			
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'termCount',mapping: 'termCount'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'updTs',mapping: 'updTs'},
			{name: 'mchtFlag1',mapping: 'mchtFlag1'},
			{name: 'mchtFlag2',mapping: 'mchtFlag2'}
		])
	});
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>商户英文名称：{engName}</p>',
			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			'<p>商户地址：{addr}</p>',			
			'<p>电子邮件：{commEmail}</p>',			
			'<p>联系人姓名：{contact}</p>',
			'<p>联系人电话：{commTel}</p>',
			'<p>录入柜员：{crtOprId}&nbsp;&nbsp;&nbsp;&nbsp;审核柜员：{updOprId}</p>',
			'<p>最近更新时间：{updTs}</p>',{
			getmcc : function(val){return getRemoteTrans(val, "mcc");}
			}
		)
	});

	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchtNo',header: '商户ID',dataIndex: 'mchtNo',sortable: true,width: 130},
		{header: '商户名称',dataIndex: 'mchtNm',width: 100,id: 'mchtNm'},
		{header: '商户号1',dataIndex: 'mappingMchntcdOne',width: 100},
		{header: '商户号2',dataIndex: 'mappingMchntcdTwo',width: 100},
		{header: '注册日期',dataIndex: 'applyDate',width: 80,renderer: formatDt},
		{header: '商户状态',dataIndex: 'mchtStatus',renderer: mchntSt}		
	]);
	
	setHeader(mchntColModel,3,4);
	
	//手续费配置数据部分
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getMchntFeeInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchntId',mapping: 'mchntId'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'feeRate',mapping: 'feeRate'},
			{name: 'feeMin',mapping: 'feeMin'},
			{name: 'feeMax',mapping: 'feeMax'},
			{name: 'createTime',mapping: 'createTime'},
			{name: 'updateTime',mapping: 'updateTime'},
			{name: 'operator',mapping: 'operator'}
		])
	});
//	var termColModel = new Ext.grid.ColumnModel([
//		new Ext.grid.RowNumberer(),		
//		{id: 'feeType',header: '手续费类型',dataIndex: 'feeType',width: 80},
//		{id: 'feeRate',header: '手续费率',dataIndex: 'feeRate',width: 60},
//		{id: 'feeMin',header: '最低手续费',dataIndex:'feeMin',width: 100},
//		{id: 'feeMax',header: '最高手续费',dataIndex:'feeMax',width: 100}
//	]);
	
	
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
		
//	var detailMenu = {
//		text: '查看详细信息',
//		width: 85,
//		iconCls: 'detail',
//		disabled: true,
//		handler:function() {
//			showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid)
//		}
//	}
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};

//	menuArr.push(detailMenu);  //[8]
//	menuArr.push('-');         //[9]
	menuArr.push(queryCondition);  //[10]
	

	
	
	var feeformPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 350,
        width: 'auto',
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
                     id: 'feeType',
                 	 xtype: 'combo',
                     fieldLabel: '手续费类型*',
                     hiddenName: 'feeTypeH',
                     allowBlank: false,
                     editable : false,
                     store: new Ext.data.ArrayStore({
                         fields: ['valueField','displayField'], 

                         data: [['0','按固定金额'],['1','按比例'],['2','按比例保底'],['3','按比例封顶'],['4','按比例保底封顶']]
                     }),
                     width : 110,
                     listeners:{
                    	 'select':function(){
                    		 var type = Ext.getCmp("feeType").getValue();
                    		 if(type=='0'){
                    			 Ext.getCmp("feeRate").hide().disable().reset();
                    			 Ext.getCmp("fee").show().enable().reset();
                    			 Ext.getCmp("feeMin").hide().disable().reset();
                    			 Ext.getCmp("feeMax").hide().disable().reset();
                    			 hideLabel('feeRate',false);
                    			 hideLabel('fee',true);
                    			 hideLabel('feeMin',false);
                    			 hideLabel('feeMax',false);
                    		 }
                    		 if(type=='1'){
                    			 Ext.getCmp("feeRate").show().enable().reset();
                    			 Ext.getCmp("fee").hide().disable().reset();
                    			 Ext.getCmp("feeMin").hide().disable().reset();
                    			 Ext.getCmp("feeMax").hide().disable().reset();
                    			 hideLabel('feeRate',true);
                    			 hideLabel('fee',false);
                    			 hideLabel('feeMin',false);
                    			 hideLabel('feeMax',false);
                    		 }
                    		 if(type=='2'){
                    			 Ext.getCmp("feeRate").show().enable().reset();
                    			 Ext.getCmp("fee").hide().disable().reset();
                    			 Ext.getCmp("feeMin").show().enable().reset();
                    			 Ext.getCmp("feeMax").hide().disable().reset();
                    			 hideLabel('feeRate',true);
                    			 hideLabel('fee',false);
                    			 hideLabel('feeMin',true);
                    			 hideLabel('feeMax',false);
                    		 }
                    		 if(type=='3'){
                    			 Ext.getCmp("feeRate").show().enable().reset();
                    			 Ext.getCmp("fee").hide().disable().reset();
                    			 Ext.getCmp("feeMin").hide().disable().reset();
                    			 Ext.getCmp("feeMax").show().enable().reset();
                    			 hideLabel('feeRate',true);
                    			 hideLabel('fee',false);
                    			 hideLabel('feeMin',false);
                    			 hideLabel('feeMax',true);
                    		 }
                    		 if(type=='4'){
                    			 Ext.getCmp("feeRate").show().enable().reset();
                    			 Ext.getCmp("fee").hide().disable().reset();
                    			 Ext.getCmp("feeMin").show().enable().reset();;
                    			 Ext.getCmp("feeMax").show().enable().reset();
                    			 hideLabel('feeRate',true);
                    			 hideLabel('fee',false);
                    			 hideLabel('feeMin',true);
                    			 hideLabel('feeMax',true);
                    		 }
                    	 }
                     }
                 }]
             },{
                columnWidth: 1,
                layout: 'form',
                items:[{
                    xtype: 'textfield',
                    fieldLabel: '手续费率(%)*',
                    width:110,
                    id: 'feeRate',
                    name: 'feeRate',
                    readOnly: false,
                    allowBlank: false,
                    hidden:true,
                    listeners:{
                    	render:function(field){
                    		Ext.QuickTips.init();   
                    		Ext.QuickTips.register({   
                    		target : field.el,   
                    		text : '按百分比计算'   
                    		}) 
                    	}
                    }
                }]
           },{
               columnWidth: 1, 
               layout: 'form',
               items: [{
                   xtype: 'textfield',
                   fieldLabel: '手续费*(单位:元)',
                   id: 'fee',
                   name:'feeMin',
                   allowBlank: false,
                   width:110,
                   hidden:true,
                   listeners:{
                   	render:function(field){
                   		Ext.QuickTips.init();   
                   		Ext.QuickTips.register({   
                   		target : field.el,   
                   		text : '固定金额按元计算'   
                   		}) 
                   	}
                   }                  
               }]
           },{
                    columnWidth: 1, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '最低手续费*(单位:元)',
                        id: 'feeMin',
                        name:'feeMin',
                        allowBlank: false,
                        width:110,
                        hidden:true,
                        listeners:{
                        	render:function(field){
                        		Ext.QuickTips.init();   
                        		Ext.QuickTips.register({   
                        		target : field.el,   
                        		text : '最低金额按元计算'   
                        		}) 
                        	}
                        }                  
                    }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '最高手续费*(单位:元)',
                    id: 'feeMax',
                    name:'feeMax',
                    allowBlank: false,
                    width:110,
                    hidden:true,
                    listeners:{
                    	render:function(field){
                    		Ext.QuickTips.init();   
                    		Ext.QuickTips.register({   
                    		target : field.el,   
                    		text : '最高金额按元计算'   
                    		}) 
                    	}
                    }                  
                }]
            }]
            }]
    });
/*******************  终端修改表单  *********************/
    var feeForm = new Ext.form.FormPanel({
        frame: true,
        height: 350,
        width:290,
        labelWidth: 100,
        waitMsgTarget: true,
        layout: 'column',
        items: [feeformPanel]
    });
	/*******************  手续费配置 *********************/
    var feePanel = new Ext.Panel({
        title: '手续费配置',
        region: 'east',
		width: 300,
        header: true,
        frame: true,
        split:true,
        autoHeight:'auto',
        items: [feeForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'T201',
        resizable: false,
        buttons: [{
            text: '保存',
            id:'saveBtn',
            handler: function() {
            		feeformPanel.setActiveTab("info1Upd");
            		var type = Ext.getCmp("feeType").getValue();
            		if(type=='4'){
            			var min=Ext.getCmp("feeMin").getValue(),max=Ext.getCmp("feeMax").getValue();
                		if(parseInt(min)>parseInt(max)){
                			alert('最小费率不能大于最大费率');
                			return false;
                		}
            		}
            		
                	if(feeForm.getForm().isValid()){
                		
                		feeForm.getForm().submitNeedAuthorise({
                        url: 'T21405Action.asp?method=saveOrupdate',
                        waitMsg: '正在提交，请稍后......',
                        success: function(form,action) {
                            showSuccessMsg(action.result.msg,feeForm);
                        },
                        failure: function(form,action) {
                        	feeformPanel.setActiveTab('info1Upd');
                            showErrorMsg(action.result.msg,feeForm);
                        },
                        params: {
                            txnId: '21405',
                            subTxnId: '01',
                            mchntId:mchntGrid.getSelectionModel().getSelected().data.mchtNo,
                            feeType:Ext.getCmp("feeType").getValue()
//                            feeRate:Ext.getCmp("feeRate").getValue(),
//                            feeMin:Ext.getCmp("feeMin").getValue(),
//                            feeMax:Ext.getCmp("feeMax").getValue()
                        }
                    });
               
                	}else{
                		feeformPanel.setActiveTab('info1Upd');
                		feeForm.getForm().isValid()
                	}
            }
        }]
    });
//	var termGrid = new Ext.grid.GridPanel({
//		title: '终端信息',
//		region: 'east',
//		width: 300,
//		iconCls: 'T301',
//		split: true,
//		collapsible: true,
//		frame: true,
//		border: true,
//		columnLines: true,
//		autoExpandColumn: 'feeRate',
//		stripeRows: true,
//		store: termStore,
//		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
//		cm: termColModel,
//		clicksToEdit: true,
//		forceValidation: true,
//		loadMask: {
//			msg: '正在加载终端信息列表......'
//		},
//		bbar: new Ext.PagingToolbar({
//			store: termStore,
//			pageSize: System[QUERY_RECORD_COUNT],
//			displayInfo: false
//		})
//	});
	
	// 禁用编辑按钮
//	termGrid.getStore().on('beforeload',function() {
//		termGrid.getTopToolbar().items.items[0].disable();
//	});
//	
//	termGrid.getSelectionModel().on({
//		'rowselect': function() {
//			termGrid.getTopToolbar().items.items[0].enable();
//		}
//	});
	
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户信息查询',
		region: 'center',
		iconCls: 'T10403',
		frame: true,
		border: true,
		width:350,
		columnLines: true,
		split:true,
//		autoExpandColumn: 'mchtNm',
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
		
		var status=mchntGrid.getSelectionModel().getSelected().data.mchtStatus;
		if(status=='9'){
			Ext.getCmp('feeType').disable();
			Ext.getCmp('saveBtn').disable();
			Ext.getCmp('feeType').reset();
			Ext.getCmp("feeRate").hide().disable();
  			 Ext.getCmp("fee").hide().disable();
  			 Ext.getCmp("feeMin").hide().disable();
  			 Ext.getCmp("feeMax").hide().disable();
  			 hideLabel('feeRate',false);
  			 hideLabel('fee',false);
  			 hideLabel('feeMin',false);
  			 hideLabel('feeMax',false);
  			Ext.getCmp('feeRate').reset();
			Ext.getCmp('feeMin').reset();
			Ext.getCmp('feeMax').reset();
			Ext.getCmp('fee').reset();
			showAlertMsg("商户已注销，无法配置手续费！",mchntGrid);
		}else{
			Ext.getCmp('feeType').enable();
			Ext.getCmp('saveBtn').enable();
			
			var initMask = new Ext.LoadMask(Ext.getBody(),{msg:'正在加载信息，请稍后......'});
			initMask.show();
			var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo;
			termStore.load({
				params: {
					start: 0,
					mchntId: id
					},
				callback: function(record, options, success){
					Ext.getCmp('feeType').reset();
					Ext.getCmp('feeRate').reset();
					Ext.getCmp('feeMin').reset();
					Ext.getCmp('feeMax').reset();
					Ext.getCmp('fee').reset();
					if(record[0]){
						var type=record[0].get('feeType');
						if(type=='0'){
	           			 Ext.getCmp("feeRate").hide().disable();
	           			 Ext.getCmp("fee").show().enable();
	           			 Ext.getCmp("feeMin").hide().disable();
	           			 Ext.getCmp("feeMax").hide().disable();
	           			 hideLabel('feeRate',false);
	           			 hideLabel('fee',true);
	           			 hideLabel('feeMin',false);
	           			 hideLabel('feeMax',false);
	           			Ext.getCmp('fee').setValue(record[0].get('feeMin'));
	           		 }
	           		 if(type=='1'){
	           			 Ext.getCmp("feeRate").show().enable();
	           			 Ext.getCmp("fee").hide().disable();
	           			 Ext.getCmp("feeMin").hide().disable();
	           			 Ext.getCmp("feeMax").hide().disable();
	           			 hideLabel('feeRate',true);
	           			 hideLabel('fee',false);
	           			 hideLabel('feeMin',false);
	           			 hideLabel('feeMax',false);
	           			 Ext.getCmp('feeRate').setValue(record[0].get('feeRate'));
	           		 }
	           		 if(type=='2'){
	           			 Ext.getCmp("feeRate").show().enable();
	           			 Ext.getCmp("fee").hide().disable();
	           			 Ext.getCmp("feeMin").show().enable();
	           			 Ext.getCmp("feeMax").hide().disable();
	           			 hideLabel('feeRate',true);
	           			 hideLabel('fee',false);
	           			 hideLabel('feeMin',true);
	           			 hideLabel('feeMax',false);
	           			Ext.getCmp('feeRate').setValue(record[0].get('feeRate'));
						Ext.getCmp('feeMin').setValue(record[0].get('feeMin'));
	           		 }
	           		 if(type=='3'){
	           			 Ext.getCmp("feeRate").show().enable();
	           			 Ext.getCmp("fee").hide().disable();
	           			 Ext.getCmp("feeMin").hide().disable();
	           			 Ext.getCmp("feeMax").show().enable();
	           			 hideLabel('feeRate',true);
	           			 hideLabel('fee',false);
	           			 hideLabel('feeMin',false);
	           			 hideLabel('feeMax',true);
	           			Ext.getCmp('feeRate').setValue(record[0].get('feeRate'));
						Ext.getCmp('feeMax').setValue(record[0].get('feeMax'));
	           		 }
	           		 if(type=='4'){
	           			 Ext.getCmp("feeRate").show().enable();
	           			 Ext.getCmp("fee").hide().disable();
	           			 Ext.getCmp("feeMin").show().enable();
	           			 Ext.getCmp("feeMax").show().enable();
	           			 hideLabel('feeRate',true);
	           			 hideLabel('fee',false);
	           			 hideLabel('feeMin',true);
	           			 hideLabel('feeMax',true);
	           			Ext.getCmp('feeRate').setValue(record[0].get('feeRate'));
						Ext.getCmp('feeMin').setValue(record[0].get('feeMin'));
						Ext.getCmp('feeMax').setValue(record[0].get('feeMax'));
	           		 }
						Ext.getCmp('feeType').setValue(record[0].get('feeType'));
						initMask.hide();
					}else{
						 Ext.getCmp("feeRate").hide().disable();
	          			 Ext.getCmp("fee").hide().disable();
	          			 Ext.getCmp("feeMin").hide().disable();
	          			 Ext.getCmp("feeMax").hide().disable();
	          			 hideLabel('feeRate',false);
	          			 hideLabel('fee',false);
	          			 hideLabel('feeMin',false);
	          			 hideLabel('feeMax',false);
						initMask.hide();
					}
					
				}
				});
		}
		
//		termStore.each(function(record){
//			console.log(record.get('feeType'));
//			Ext.getCmp('feeType').setValue(record.get('feeType'));
//		})
	});
	
	
	
	
	// 禁用编辑按钮
//	mchntGrid.getStore().on('beforeload',function() {
//		mchntGrid.getTopToolbar().items.items[0].disable();
//	});
	
	var rec;
	
//	mchntGrid.getSelectionModel().on({
//		'rowselect': function() {
//			//行高亮
//			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
//			rec = mchntGrid.getSelectionModel().getSelected();
////			mchntGrid.getTopToolbar().items.items[0].enable();
//		}
//	});
	
	// Mcc下拉列表
	var MccStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCC',function(ret){
		MccStore.loadData(Ext.decode(ret));
	});
	// Mcc下拉列表
	var MccCombo = new Ext.form.ComboBox({
		store: MccStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择Mcc',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		blankText: '请选择Mcc',
		fieldLabel: 'Mcc',
		id: 'mcc'
	});
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '注册开始日期',
			editable: false
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '注册结束日期',
			editable: false
		},{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '商户状态',
			baseParams: 'MCHT_STATUS',
			width: 380
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户ID',
			methodName: 'getMchntIdTmp',
			hiddenName: 'mchtNo',
			editable: true,
			width: 380
		},{
			xtype:'textfield',
			fieldLabel: '商户号',
			id:'acmchntIdQ',
			name:'acmchntIdQ',
			width:300
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
				feeForm.getForm().reset();
				queryForm.getForm().reset();
				
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
			mchntId: queryForm.getForm().findField('mchtNo').getValue(),
			mchtStatus: queryForm.findById('mchtStatus').getValue(),
			acmchntId: Ext.getCmp('acmchntIdQ').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,feePanel],
		renderTo: Ext.getBody()
	});
	
	 hideLabel('feeRate',false);
	 hideLabel('fee',false);
	 hideLabel('feeMin',false);
	 hideLabel('feeMax',false);
	 Ext.getCmp('saveBtn').disable();
	
});