Ext.onReady(function() {
    
    function hideLabel(sId, bShow){ 
		Ext.getCmp(sId).getEl().up('.x-form-item').child('.x-form-item-label').setDisplayed(bShow);
        Ext.getCmp(sId).getEl().up('.x-form-item').setDisplayed(bShow); 
	}
    
	var txnDetailStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=getTxnSummaryInfoDetail'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'posSerial',mapping: 'posSerial'},
            {name: 'merchantCode',mapping: 'merchantCode'},
            {name: 'terminalCode',mapping: 'terminalCode'},
            {name: 'transTime',mapping: 'transTime'},
            {name: 'masterAccount',mapping: 'masterAccount'},
            {name: 'transAmount',mapping: 'transAmount'},
            {name: 'cupsTransFee',mapping: 'cupsTransFee'},
            {name: 'settleDate',mapping: 'settleDate'},
            {name: 'merchantType',mapping: 'merchantType'},
            {name: 'respCode',mapping: 'respCode'},
            {name: 'checkState',mapping: 'checkState'}
        ])
    });
    

	var txnDetailColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
	       {id: 'posSerial',header: '系统跟踪号',dataIndex: 'posSerial',width: 100},
	      {header: '商户号',dataIndex: 'merchantCode',width: 150},
	      {header: '终端号',dataIndex: 'terminalCode',width: 150},
	      {header: '交易时间',dataIndex: 'transTime',width: 100},
	     {header: '主账户',dataIndex: 'masterAccount',width: 150},
	     {header: '交易金额',dataIndex: 'transAmount',width:100},
	     {header: '交易手续费',dataIndex: 'cupsTransFee',width:100},
	     {header: '交易清算日期',dataIndex: 'settleDate',width:100},
         {header: '商户类型',dataIndex: 'merchantType',width:100},
         {header: '交易返回码',dataIndex: 'respCode',width:100},
         {header: '对账结果',dataIndex: 'checkState',width:100}
	]);
    
    // 联机交易数据集
	var txnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getTxnSummaryInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'settleDate',mapping: 'settleDate'},
			{name: 'merchantCode',mapping: 'merchantCode'},
			{name: 'terminalCode',mapping: 'terminalCode'},
			{name: 'transAmount',mapping: 'transAmount'},
			{name: 'cupsTransFee',mapping: 'cupsTransFee'},
			{name: 'localTransFee',mapping: 'localTransFee'}
		])
//	,
//		autoLoad: true
	});
	txnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
//			mchtFlag1: queryForm.getForm().findField('mchtFlag1').getValue(),
			timeType: queryForm.getForm().findField('timeTypeQ').getValue()||'1',
			txnDate:queryForm.getForm().findField('selectedDate').getValue(),
			txnType: queryForm.getForm().findField('txnTypeQ').getValue()||'T',
			mchntId: queryForm.getForm().findField('mchntId').getValue(),
			termId: queryForm.getForm().findField('termId').getValue()
		});
	});
	var txnColModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
		{header: '交易日期',dataIndex: 'settleDate',width: 80},
		{header: '商户号',dataIndex: 'merchantCode',width: 100},
		{header: '终端号',dataIndex: 'terminalCode',width: 100},
		{header: '交易金额',dataIndex: 'transAmount',width: 150},
		{header: '银联手续费',dataIndex: 'cupsTransFee',width: 130},
		{header: '本地手续费',dataIndex: 'localTransFee',width: 180}
	]);
	
	
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '交易统计',
		region: 'center',
		iconCls: 'pos',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: txnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: txnColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载统计信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: txnStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    
	var menuArr = new Array();
	
	var report = {
			text: '生成交易报表',
			width: 85,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",grid2);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T51001Action_download.asp',
					params: {
						txnId: '51001',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
														rspObj.msg+'&key=exl21exl';
						} else {
							showErrorMsg(rspObj.msg,grid2);
						}
					},
					failure: function(){
						hideMask();
					}
				});
			}
		};
	
	menuArr.push(report);
	
	
    var grid2 = new Ext.grid.GridPanel({
        title: '交易详细信息',
        region: 'east',
		width: 600,
        iconCls: 'pos',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: txnDetailStore,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        cm: txnDetailColModel,
        forceValidation: true,
        loadMask: {
            msg: '正在加载统计信息详细列表......'
        },
        tbar: menuArr,
        bbar: new Ext.PagingToolbar({
            store: txnDetailStore,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录',
            onFirstLayout:function(){
            	this.refresh.hide();
            }
        })
    });
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			var settleDate=grid.getSelectionModel().getSelected().data.settleDate,
				merchantCode=grid.getSelectionModel().getSelected().data.merchantCode,
				terminalCode=grid.getSelectionModel().getSelected().data.terminalCode;
			txnDetailStore.load({
				params: {
					start: 0,
					settleDate: settleDate,
					merchantCode:merchantCode,
					terminalCode:terminalCode
					},
					callback:function(records, options, success){
						if(success){
							Ext.getCmp('report').enable();
						}else{
							Ext.getCmp('report').disable();
						}
					}
			})
		}
			
	});
    
    
    var queryForm = new Ext.form.FormPanel({
        region: 'north',
        height: 160,
        frame: true,
        layout: 'column',
        items: [{                                           
            columnWidth: .5,
            layout: 'form',
            items: [{
                columnWidth:.5,
                layout:'form',
                items:[{
                    xtype: 'combo',
			fieldLabel: '查询类型*',
			id: 'timeTypeQ',
			hiddenName: 'timeTypeQuery',
			allowBlank:false,
			store: new Ext.data.ArrayStore({
                fields: ['valueField','displayField'], 

                data: [['1','按日查询'],['2','按月查询']]
            }),
            listeners:{
            	'select':function(){
            		var timetypes=Ext.getCmp('timeTypeQ').getValue();
            		if(timetypes=='1'){
            			hideLabel('startDate',true);
            			hideLabel('startMonth',false);
            			Ext.getCmp('startDate').show();
            			Ext.getCmp('startMonth').hide();
            			Ext.getCmp('selectedDate').reset();
            		}else{
            			hideLabel('startDate',false);
            			hideLabel('startMonth',true);
            			Ext.getCmp('startDate').hide();
            			Ext.getCmp('startMonth').show();
            			Ext.getCmp('selectedDate').reset();
            		}
            	}
            }
                }]
            },{
            columnWidth: .5,
            layout: 'form',
            items: [{
                xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			fieldLabel: '选择交易日期*',
			format:'Ymd',
			allowBlank: true,
			editable: false,
			width:200,
			listeners:{
				'select':function(){
					var date=Ext.getCmp('startDate').getValue(),selectdate=Ext.getCmp('selectedDate').getValue();
					date=today(date).replace(/-/g, '');
					if(!selectdate){
						Ext.getCmp('selectedDate').setValue(date);
					}else{
						var selarray=selectdate.split(','),len=selarray.length,index=selarray.indexOf(date);
						
							if(index<0){
								selarray[len]=date;
								
							}else{
								selarray.splice(index,1);
							}
						
						var finaldata=selarray.join(',');
						Ext.getCmp('selectedDate').setValue(finaldata);
					}
					
				}
			}
           }]
        },{
            columnWidth: .5,
            layout: 'form',
            items: [{
                xtype: 'datefield',
			id: 'startMonth',
			name: 'startMonth',
			fieldLabel: '选择交易日期*',
			plugins: 'monthPickerPlugin', 
			format:'Ym',
			allowBlank: true,
			editable: false,
			width:200,
			listeners:{
				'select':function(){
					var datemonth=Ext.getCmp('startMonth').getValue(),selectdatemonth=Ext.getCmp('selectedDate').getValue();
					datemonth=today(datemonth).replace(/-/g, '').substring(0, 6);
					if(!selectdatemonth){
						Ext.getCmp('selectedDate').setValue(datemonth);
					}else{
						var selarraymonth=selectdatemonth.split(','),lenmonth=selarraymonth.length,indexmonth=selarraymonth.indexOf(datemonth);
						
							if(indexmonth<0){
								selarraymonth[lenmonth]=datemonth;
								
							}else{
								selarraymonth.splice(indexmonth,1);
							}
						
						var finaldatamonth=selarraymonth.join(',');
						Ext.getCmp('selectedDate').setValue(finaldatamonth);
					}
				}
			}
           }]
        },{                                           
            columnWidth: .5,
            layout: 'form',
            items: [{
                xtype: 'textfield',
			fieldLabel: '查询日期',
			id: 'selectedDate',
			name:'selectedDate'
            }]
        }]
        },{
            columnWidth: .5,
            layout: 'form',
            items: [{
                xtype: 'combo',
			fieldLabel: '查询类型1*',
			width: 290,
			id: 'txnTypeQ',
			hiddenName: 'txnTypeQuery',
			allowBlank:false,
			store: new Ext.data.ArrayStore({
                fields: ['valueField','displayField'], 

                data: [['M','按商户'],['T','按终端']]
            })
            }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items: [{
                    xtype : 'textfield',
			fieldLabel : '商户号',
			width:200,
//			methodName : 'getMchntIdInBase',
//			hiddenName : 'mchtCd',
			id:'mchntId',
			name:'mchntId'
                  }]
        },{
            columnWidth: .5,
            layout: 'form',
            items: [{
                xtype: 'textfield',
                width:200,
			fieldLabel: '终端号',
			id: 'termId'    
            } ]
        }],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                if(queryForm.getForm().isValid()) {
                	txnDetailStore.removeAll();
					txnStore.load();
					queryWin.hide();
				}else{
					queryForm.getForm().isValid();
				}
            }
        },{
            text: '重填',
            handler: function() {
                queryForm.getForm().reset();
//				hideLabel('startDate',false);
				hideLabel('startMonth',false);
//				Ext.getCmp('startDate').hide();
				Ext.getCmp('startMonth').hide();
				Ext.getCmp('selectedDate').reset();
				queryForm.getForm().findField('timeTypeQ').setValue('1');
				queryForm.getForm().findField('txnTypeQ').setValue('T');
            }
        }]
    });

	var mainView = new Ext.Viewport({
        title: '交易统计',
		layout: 'border',
		items: [queryForm,grid,grid2],
		renderTo: Ext.getBody()
	});
	queryForm.getForm().findField('timeTypeQ').setValue('1');
	queryForm.getForm().findField('txnTypeQ').setValue('T');
//            hideLabel('startDate',false);
			hideLabel('startMonth',false);
//			Ext.getCmp('startDate').hide();
			Ext.getCmp('startMonth').hide();
			Ext.getCmp('selectedDate').reset();
			Ext.getCmp('report').disable();
})