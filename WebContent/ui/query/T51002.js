Ext.onReady(function() {
    var payTypeCopy;   // 支付方式
    var statTypeCopy;   // 统计方式
    var startDateCopy;   //开始日期
    var localTimeStartCopy;   //开始时间
    var endDateCopy;   //结束日期
    var localTimeEndCopy;   //结束时间    
	var merchantCode;
	var terminalCode;
    
    function hideLabel(sId, bShow){ 
		Ext.getCmp(sId).getEl().up('.x-form-item').child('.x-form-item-label').setDisplayed(bShow);
        Ext.getCmp(sId).getEl().up('.x-form-item').setDisplayed(bShow); 
	}
    
    var payStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	
	SelectOptionsDWR.getMchntData('PAY_TYPE',function(ret){
		payStore.loadData(Ext.decode(ret));
	});
	
    
    // 小计统计信息
	var statStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=posStatSumInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'merchantCode',mapping: 'MCHNTNO'},
			{name: 'terminalCode',mapping: 'TERMNO'},
			{id:'terminal2', name: 'transMoney',mapping: 'TOTALMON',hidden:true},
			{name: 'transNum',mapping: 'TOTALNUM'}
		])
	});
	var statColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
		{header: '银联商户号',dataIndex: 'merchantCode',width: 140,align:'center'},
		{id: 'terminal', header: '银联终端号',dataIndex: 'terminalCode',width: 100,align:'center'},
		{header: '交易金额',dataIndex: 'transMoney',width: 100,align:'center'},
		{header: '交易量',dataIndex: 'transNum',width: 100,align:'center'}
	]);


	// 小计统计信息
	var statStore2 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=posTotalSumInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'transMoney',mapping: 'TOTALMON'},
			{name: 'transNum',mapping: 'TOTALNUM'}
		])
	});
	var statColModel2 = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
		{header: '交易金额',dataIndex: 'transMoney',width: 100,align:'center'},
		{header: '交易量',dataIndex: 'transNum',width: 100,align:'center'}
	]);
	
	var statDetailStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=posStatSumInfoDetail'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'posSerial',mapping: 'TOTALTIME'},
            {name: 'merchantCode',mapping: 'MCHNTNO'},
            {name: 'terminalCode',mapping: 'TERMNO'},
            {name: 'transTime',mapping: 'TOTALMON'},
            {name: 'masterAccount',mapping: 'TOTALNUM'}
        ])
    });
	

	var statDetailColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
	    {id: 'posSerial',header: '日期',dataIndex: 'posSerial',width: 140,align:'center'},
	    {header: '银联商户号',dataIndex: 'merchantCode',width: 150,align:'center'},
	    {header: '银联终端号',dataIndex: 'terminalCode',width: 120,align:'center'},
	    {header: '交易金额',dataIndex: 'transTime',width: 100,align:'center'},
	    {header: '交易量',dataIndex: 'masterAccount',width: 100,align:'center'}
	]);
 
    var pageBar =  new Ext.PagingToolbar({
		store: statStore,
		pageSize: System[QUERY_RECORD_COUNT],
		displayInfo: true,
		displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
		emptyMsg: '没有找到符合条件的记录'
	})
    
    var menuArr = new Array();
	
	var report = {
		text: '生成交易报表',
		width: 85,
		id: 'report',
		iconCls: 'download',
		handler:function() {
			var totalCount=statDetailStore.getTotalCount();
			if(totalCount > 1000){
				showErrorMsg("导出的数据不能超过10000条",grid2);
				return;
			}
			showMask("正在为您准备报表，请稍后。。。",grid2);
			Ext.Ajax.request({
				url: 'T51002Action_download2.asp',
				params: {
					start: 0,
					merchantCode : merchantCode,
					terminalCode : terminalCode,
					payType : payTypeCopy,
					statType : statTypeCopy,
					startDate : startDateCopy,
					localTimeStart : localTimeStartCopy,
					endDate : endDateCopy,
					localTimeEnd : localTimeEndCopy
				},
				success: function(rsp,opt){
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
													rspObj.msg+'&key=exl25exl';
					} else {
						showErrorMsg(rspObj.msg,grid2);
					}
				},
				failure: function(rsp,opt){
					hideMask();
				}
			});
		}
	};
	menuArr.push(report);
    
	var queryForm = new Ext.form.FormPanel({
		 region: 'north',
		 height: 160,
		 frame: true,
		 columnWidth: 1,
		 layout: 'column',
		 items: [/*{ 
			 columnWidth: 0.5,
			 layout: 'form',
			 items:[
			        {
		//		 xtype: 'combo',
				 xtype: 'basecomboselect',
				 fieldLabel: '支付方式*',
				 width: 280,
				 id: 'payType',
				 hiddenName: 'payTypeQuery',
				 allowBlank:	false,
				 store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'], 
					data: [
							['all','全部'],
							['scanpay','扫码支付'],
							['unionPay','银联支付'],
							['weChat','微信支付'],
							['aliPay','支付宝支付'],
							['qqPay','QQ支付']
						]
				  })
			 }]
		 }
		 */{
			 columnWidth: 0.5,
			 layout: 'form',
			 items:[
			        {
			        	xtype: 'combo',
						store: payStore,
						displayField: 'displayField',
						valueField: 'valueField',
						emptyText: '请选择',
						id:'payType',
						hiddenName: 'payTypeQuery',
					//	mode: 'local',
					//	triggerAction: 'all',
					//	forceSelection: true,
					//	typeAhead: true,
					//	selectOnFocus: true,
						editable: false,
						fieldLabel: '支付类型',
						width:280
			        }]
		 	},{
			 columnWidth: 0.5,
			 layout: 'form',
			 items:[{
	              xtype: 'basecomboselect',
				  fieldLabel: '统计方式*',
				  width: 280,
				  id: 'staType',
				  hiddenName: 'statTypeQuery',
				  allowBlank: false,
				  store: new Ext.data.ArrayStore({
			          fields: ['valueField','displayField'], 
			          data: [
			                 ['M','按商户号'],
		                     ['T','按终端号']
			              ]
				  }),
		          listeners:{
		        	  'select':function(){
		        		  var staType=Ext.getCmp('staType').getValue();
		        		  if(staType == 'M'){
		        			  hideLabel('ylMerchantNo',true);
		            		  hideLabel('ylTermNo',false);
		        			  Ext.getCmp('ylMerchantNo').show();
		            		  Ext.getCmp('ylTermNo').hide();
		           // 		  Ext.getCmp('selectedDate').reset();
		        		  }else if(staType == 'T'){
		        			  hideLabel('ylMerchantNo',true);
		            		  hideLabel('ylTermNo',true);
		        			  Ext.getCmp('ylMerchantNo').show();
		            		  Ext.getCmp('ylTermNo').show();
		        		  }else{
		        			  
		        		  }
		        	  }
		          }
			 }]
		 },{
			 columnWidth: 0.5,
			 layout: 'form',
			 items:[{
				 xtype: 'datefield',
				 width : 280,
				 id: 'startDate',
				 fieldLabel: '交易开始日期*',
				 allowBlank: false,
				 editable: false
			 }]
		 },{
			 columnWidth: 0.5,
			 layout: 'form',
			 items:[{
				 xtype: 'datefield',
				 width : 280,
				 id: 'endDate',
				 fieldLabel: '交易结束日期*',
				 allowBlank: false,
				 editable: false
			 }]
		 },{
			 columnWidth: 0.5,
			 layout: 'form',
			 items:[{
				 xtype: 'textfield',
				 width: 280,
				 fieldLabel: '交易开始时间',
				 regex: /^([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$/,
	          	 regexText: '请按时分秒的格式输入6位数，最大235959，最小000000，如052822',
	          	 id: 'localTimeStart',
	          	 maxLength: 6,
				 allowBlank: true,
			 }]
		 },{
			 columnWidth: 0.5,
			 layout: 'form',
			 items:[{
				 xtype: 'textfield',
				 width: 280,
				 fieldLabel: '交易结束时间',
				 regex: /^([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$/,
	          	 regexText: '请按时分秒的格式输入6位数，最大235959，最小000000，如052822',
	          	 id: 'localTimeEnd',
	          	 maxLength: 6,
				 allowBlank: true,
			 }]
		 },{
			 columnWidth: 0.5,
			 layout: 'form',
			 items:[{
				xtype : 'textfield',
				fieldLabel : '银联商户号',
				id:'ylMerchantNo',
				width : 280
			 }]
		 },{
			 columnWidth: 0.5,
			 layout: 'form',
			 items:[{
				 xtype: 'textfield',
				 width: 280,
				 fieldLabel: '银联终端号',
				 id: 'ylTermNo'
			 }]
		 }],
		 buttons: [{
			 	text: '查询',
			 	handler: function() 
			 	{
			 		if(queryForm.getForm().isValid()) { 
			 			var endtime=Ext.getCmp('endDate').getValue(),starttime=Ext.getCmp('startDate').getValue();
		            	if(endtime!=''&&starttime!=''&&endtime<starttime){
		            		showErrorMsg("请保证截止日期不小于起始日期",queryForm);
		    				return;
		            	}
		            	var localTimeStart=Ext.getCmp('localTimeStart').getValue().replaceAll(" ", ""), localTimeEnd=Ext.getCmp('localTimeEnd').getValue().replaceAll(" ", "");
		            	
//		            	if(localTimeStart == '' || localTimeEnd == ''){
//		            		showErrorMsg("交易时间不能为空",grid);
//		    				return;
//		            	}
		            	if((!(endtime<starttime))&&(!(endtime>starttime))&&localTimeEnd!=''&&localTimeStart!=''&&localTimeEnd<localTimeStart){
		            		showErrorMsg("请保证截止时间不小于起始时间",queryForm);
		    				return;
		            	}
			 			var midPayVar = Ext.getCmp('payType').getValue();
			 			var midStaVar = Ext.getCmp('staType').getValue();
			// 			midPayVar = queryForm.findById('payType').getValue();
			// 			midStaVar = queryForm.findById('staType').getValue();
//			 			txnDetailStore.removeAll();
//			 			txnStore.load();	 
			 			payTypeCopy = midPayVar;   // 支付方式
			 		    statTypeCopy = midStaVar;   // 统计方式
			 		    startDateCopy = typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd') ;   //开始日期
			 		    localTimeStartCopy = queryForm.getForm().findField('localTimeStart').getValue();   //开始时间
			 		    endDateCopy = typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd');   //结束日期
			 		    localTimeEndCopy = queryForm.getForm().findField('localTimeEnd').getValue();   //结束时间    
			 			
			 			if(midStaVar == 'M'){
			 				gridL.getColumnModel().setHidden(2,true); 
			 				statStore.on('beforeload', function(){
			 					Ext.apply(this.baseParams, {
			 						staType: queryForm.getForm().findField('staType').getValue(),
			 						payType: queryForm.getForm().findField('payType').getValue(),
									startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
									localTimeStart: queryForm.getForm().findField('localTimeStart').getValue(),
									endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
									localTimeEnd: queryForm.getForm().findField('localTimeEnd').getValue(),
									ylMerchantNo: queryForm.getForm().findField('ylMerchantNo').getValue(),
									ylTermNo: ''
			 					});
			 				});
			 				statStore2.on('beforeload', function(){
			 					Ext.apply(this.baseParams, {
			 						staType: queryForm.getForm().findField('staType').getValue(),
			 						payType: queryForm.getForm().findField('payType').getValue(),
									startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
									localTimeStart: queryForm.getForm().findField('localTimeStart').getValue(),
									endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
									localTimeEnd: queryForm.getForm().findField('localTimeEnd').getValue(),
									ylMerchantNo: queryForm.getForm().findField('ylMerchantNo').getValue(),
									ylTermNo: ''
			 					});
			 				});
			 				statStore.load();
			 				statStore2.load();
			 				statDetailStore.removeAll();
			 			}else if(midStaVar == 'T'){
			 				gridL.getColumnModel().setHidden(2,false); 
			 				statStore.on('beforeload', function(){
			 					Ext.apply(this.baseParams, {
			 					staType: queryForm.getForm().findField('staType').getValue(),
			 					payType: queryForm.getForm().findField('payType').getValue(),
			 					startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			 					localTimeStart: queryForm.getForm().findField('localTimeStart').getValue(),
			 					endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			 					localTimeEnd: queryForm.getForm().findField('localTimeEnd').getValue(),
			 					ylMerchantNo: queryForm.getForm().findField('ylMerchantNo').getValue(),
			 					ylTermNo: queryForm.getForm().findField('ylTermNo').getValue()
			 					});
			 				});
			 				statStore2.on('beforeload', function(){
			 					Ext.apply(this.baseParams, {
			 					staType: queryForm.getForm().findField('staType').getValue(),
			 					payType: queryForm.getForm().findField('payType').getValue(),
			 					startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			 					localTimeStart: queryForm.getForm().findField('localTimeStart').getValue(),
			 					endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			 					localTimeEnd: queryForm.getForm().findField('localTimeEnd').getValue(),
			 					ylMerchantNo: queryForm.getForm().findField('ylMerchantNo').getValue(),
			 					ylTermNo: queryForm.getForm().findField('ylTermNo').getValue()
			 					});
			 				});
				 			statStore.load();
				 			statStore2.load();
				 			statDetailStore.removeAll();
			 			}else{
			 				gridL.getColumnModel().setHidden(2,true); 
			 				statStore.on('beforeload', function(){
			 					Ext.apply(this.baseParams, {
			 						staType: queryForm.getForm().findField('staType').getValue(),
			 						payType: queryForm.getForm().findField('payType').getValue(),
									startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
									localTimeStart: queryForm.getForm().findField('localTimeStart').getValue(),
									endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
									localTimeEnd: queryForm.getForm().findField('localTimeEnd').getValue(),
									ylMerchantNo: queryForm.getForm().findField('ylMerchantNo').getValue(),
									ylTermNo: ''
			 					});
			 				});
			 				statStore2.on('beforeload', function(){
			 					Ext.apply(this.baseParams, {
			 						staType: queryForm.getForm().findField('staType').getValue(),
			 						payType: queryForm.getForm().findField('payType').getValue(),
									startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
									localTimeStart: queryForm.getForm().findField('localTimeStart').getValue(),
									endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
									localTimeEnd: queryForm.getForm().findField('localTimeEnd').getValue(),
									ylMerchantNo: queryForm.getForm().findField('ylMerchantNo').getValue(),
									ylTermNo: ''
			 					});
			 				});
			 				statStore.load();
			 				statStore2.load();
			 				statDetailStore.removeAll();
			 			}
				 	}else{
				 		queryForm.getForm().isValid();
				 	}
			 	}
		 	},{
		 		text: '重填',
		 		handler: function() {
		 			queryForm.getForm().reset();
		 	//		hideLabel('startMonth',false);
		 	//		Ext.getCmp('startMonth').hide();
		 	//		queryForm.getForm().findField('timeTypeQ').setValue('1');
		 	//		queryForm.getForm().findField('txnTypeQ').setValue('T');
		 		}
		 	}]
		 });
//	Ext.getCmp('localTimeStart').setValue('000000');
//	Ext.getCmp('localTimeEnd').setValue('235959');
	// 终端信息列表
	var gridL = new Ext.grid.GridPanel({
		title: '交易小计',
		region: 'center',
		width: 550,
		iconCls: 'pos',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: statStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: statColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载统计信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: statStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	statDetailStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			merchantCode : merchantCode,
			terminalCode : terminalCode,
			payType : payTypeCopy,
			statType : statTypeCopy,
			startDate : startDateCopy,
			localTimeStart : localTimeStartCopy,
			endDate : endDateCopy,
			localTimeEnd : localTimeEndCopy
		});
	});
	gridL.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridL.getView().getRow(gridL.getSelectionModel().last)).frame();
			merchantCode=gridL.getSelectionModel().getSelected().data.merchantCode;
			terminalCode=gridL.getSelectionModel().getSelected().data.terminalCode;
			grid2.getTopToolbar().items.items[0].enable();
//			var payTypeCopy = midPayVar;   // 支付方式
// 		    var statTypeCopy = midStaVar;   // 统计方式
// 		    var startDateCopy = starttime;   //开始日期
// 		    var localTimeStartCopy = localTimeStart;   //开始时间
// 		    var endDateCopy = endtime;   //结束日期
// 		    var localTimeEndCopy = localTimeEnd;   //结束时间    
			if(statTypeCopy == 'M'){
				grid2.getColumnModel().setHidden(3,true); 
			}else if(statTypeCopy == 'T'){
				grid2.getColumnModel().setHidden(3,false); 
			}else{
				grid2.getColumnModel().setHidden(3,true); 
			}
			statDetailStore.load({
				params: {
					start: 0,
					merchantCode : merchantCode,
					terminalCode : terminalCode,
					payType : payTypeCopy,
					statType : statTypeCopy,
					startDate : startDateCopy,
					localTimeStart : localTimeStartCopy,
					endDate : endDateCopy,
					localTimeEnd : localTimeEndCopy
				},
				callback:function(records, options, success){
					if(success){
		//				Ext.getCmp('report').enable();
					}else{
		//				Ext.getCmp('report').disable();
					}
				}
			})
		}
	});
	
	var gridL2 = new Ext.grid.GridPanel({
		title: '交易统计',
		width: 550,
		height: 130,
		region: 'south',
		iconCls: 'pos',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: statStore2,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: statColModel2,
		forceValidation: true,
		loadMask: {
			msg: '正在加载统计信息列表......'
		},
		bbar:  new Ext.PagingToolbar({
			store: statStore2,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
   var leftPanel = new Ext.Panel({
		region: 'center',
		width: 550,
		frame: true,
		layout: 'border',
		items:[
		      gridL,
		      gridL2
		]
	});
	
   var grid2 = new Ext.grid.GridPanel({
       title: '交易统计详情',
       region: 'east',
	   width: 650,
//       autoWidth : true,
       iconCls: 'pos',
       frame: true,
       border: true,
       columnLines: true,
       stripeRows: true,
       store: statDetailStore,
       sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
       cm: statDetailColModel,
       forceValidation: true,
       loadMask: {
           msg: '正在加载统计信息详细列表......'
       },
       tbar: menuArr,
       bbar: new Ext.PagingToolbar({
			store: statDetailStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
       })
   });
   grid2.getTopToolbar().items.items[0].disable();
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [queryForm,leftPanel,grid2],
		renderTo: Ext.getBody()
	});
	//		Ext.getCmp('selectedDate').reset();
	//		Ext.getCmp('report').disable();
})