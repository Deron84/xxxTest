var checkNamespace = {}; 
checkNamespace.brhStore = new Ext.data.JsonStore({
    fields: ['valueField','displayField'],
    root: 'data'
});
SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
	checkNamespace.brhStore.loadData(Ext.decode(ret));
});


/**************  商户营销活动表单  *********************/
checkNamespace.actForm = new Ext.form.FormPanel({
	frame: true,
	height: 300,
	width: 500,
	labelWidth: 150,
	waitMsgTarget: true,
	layout: 'column',
	items: [{
		    columnWidth: .6,
        	layout: 'form',
		    items: [{
                xtype: 'combo',
                fieldLabel: '所属分行*',
                id: 'bankNoU',
                hiddenName: 'bankNo',
                store: checkNamespace.brhStore,
                displayField: 'displayField',
                valueField: 'valueField',
                mode: 'local',
                allowBlank: false,
                width: 130,
		 		readOnly: true
             }]
        },{
		    columnWidth: .6,
        	layout: 'form',
		    items: [{	
		         	xtype: 'textfield',
		 			fieldLabel: '商业营销活动编号*',
		 			id: 'actNo',
		 			name: 'actNo',
		 			allowBlank: false,
		 			readOnly: true
			}]
		},{
		    columnWidth: .6,
        	layout: 'form',
		    items: [{	
		         	xtype: 'textfield',
		 			fieldLabel: '商业营销活动名称*',
		 			id: 'actName',
		 			name: 'actName',
		 			allowBlank: false,
		 			readOnly: true
			}]
		},{
			columnWidth: .8,
        	layout: 'form',
		    items: [{
					xtype: 'combo',
					fieldLabel: '状态*',
					allowBlank: false,
					hiddenName :'state',
					id :'stateUpd',
					displayField: 'displayField',
                	valueField: 'valueField',
					store: new Ext.data.ArrayStore({
	                    fields: ['valueField','displayField'],
	                    data: [['0','正常'],['1','未审核'],['2','关闭']]
	                }),
		 			readOnly: true
		    }]
		},{
		    columnWidth: .6,
        	layout: 'form',
		    items: [{	
		         	xtype: 'datefield',
					fieldLabel: '活动起始时间*',
					allowBlank: false,
					format: 'Ymd',
					name :'startDate',
					id :'startDate',
					readOnly: true
			}]
		},{
			columnWidth: .6,
        	layout: 'form',
		    items: [{
					xtype: 'datefield',
					fieldLabel: '活动截止时间*',
					allowBlank: false,
					format: 'Ymd',
					name :'endDateChk',
					id :'endDateChk'
		    }]
		},{
            columnWidth: .6,
            layout: 'form',
            items: [{
                    xtype: 'textfield',
                    fieldLabel: '自动清算单位编号 *',
                    id: 'organNoChk',
                    name: 'organNoChk',
                    allowBlank: false,
                    maxLength: 8,
                    vtype: 'isNumber'
                 }]
        },{
            columnWidth: .6,
            layout: 'form',
            items: [{
                    xtype: 'textfield',
                    fieldLabel: '自动清算业务种类编号* ',
                    id: 'organTypeChk',
                    name: 'organTypeChk',
                    allowBlank: false,
                    maxLength: 4,
                    vtype: 'isNumber'
                 }]
        },{
	   	 	columnWidth: .8,
            layout: 'form',
            items: [{
            		width: 200,
                    xtype: 'combo',
	                fieldLabel: '营销活动算法',
	                id: 'actContentA',
	                hiddenName: 'actContent',
	                readOnly: true,
	                value: 0,
	                store: new Ext.data.ArrayStore({
	                    fields: ['valueField','displayField'],
	                    data: [['0','算法1：应收商户手续费*费率']]
	                })
                 }]
        },{
		    layout: 'form',
		    columnWidth: .9,
		    items: [{	
		         	xtype: 'textarea',
		 			fieldLabel: '备注描述',
		 			id: 'remarks',
		 			name: 'remarks',
		 			width: 250,
		 			maxLength: 40,
			 		readOnly: true
	 				}]
	 }]
});
/**************  商户营销活动表单  *********************/
checkNamespace.chkForm = new Ext.form.FormPanel({
	frame: true,
	height: 300,
	width: 500,
	labelWidth: 150,
	waitMsgTarget: true,
	layout: 'column',
	items: [{
		    columnWidth: .6,
        	layout: 'form',
		    items: [{
                xtype: 'combo',
                fieldLabel: '所属分行*',
                id: 'chkBankNoU',
                hiddenName: 'chkbankNo',
                store: checkNamespace.brhStore,
                displayField: 'displayField',
                valueField: 'valueField',
                mode: 'local',
                width: 130,
		 		readOnly: true
             }]
        },{
		    columnWidth: .6,
        	layout: 'form',
		    items: [{	
		         	xtype: 'textfield',
		 			fieldLabel: '商业营销活动编号*',
		 			id: 'chkActNo',
		 			name: 'chkActNo',
		 			allowBlank: false,
		 			readOnly: true
			}]
		},{
		    columnWidth: .6,
        	layout: 'form',
		    items: [{	
		         	xtype: 'textfield',
		 			fieldLabel: '商业营销活动名称*',
		 			id: 'chkActName',
		 			name: 'chkActName',
		 			allowBlank: false,
		 			readOnly: true
			}]
		},{
			columnWidth: .8,
        	layout: 'form',
		    items: [{
					xtype: 'combo',
					fieldLabel: '状态*',
					allowBlank: false,
					hiddenName :'chkState',
					id :'chkStateUpd',
					displayField: 'displayField',
                	valueField: 'valueField',
					store: new Ext.data.ArrayStore({
	                    fields: ['valueField','displayField'],
	                    data: [['0','正常'],['1','未审核'],['2','关闭']]
	                }),
		 			readOnly: true
		    }]
		},{
		    columnWidth: .6,
        	layout: 'form',
		    items: [{	
		         	xtype: 'datefield',
					fieldLabel: '活动起始时间*',
					allowBlank: false,
					format: 'Ymd',
					name :'chkStartDate',
					id :'chkStartDate',
					readOnly: true
			}]
		},{
			columnWidth: .6,
        	layout: 'form',
		    items: [{
					xtype: 'datefield',
					fieldLabel: '活动截止时间*',
					allowBlank: false,
					format: 'Ymd',
					name :'chkEndDate',
					id :'chkEndDate',
					readOnly: true
		    }]
		},{
            columnWidth: .6,
            layout: 'form',
            items: [{
                    xtype: 'textfield',
                    fieldLabel: '自动清算单位编号 *',
                    id: 'chkOrganNo',
                    name: 'chkOrganNo',
                    allowBlank: false,
                    readOnly: true
                 }]
        },{
            columnWidth: .6,
            layout: 'form',
            items: [{
                    xtype: 'textfield',
                    fieldLabel: '自动清算业务种类编号* ',
                    id: 'chkOrganType',
                    name: 'chkOrganType',
                    allowBlank: false,
                    readOnly: true
                 }]
        },{
	   	 	columnWidth: .8,
            layout: 'form',
            items: [{
            		width: 200,
                    xtype: 'combo',
	                fieldLabel: '营销活动算法',
	                id: 'chkActContentA',
	                hiddenName: 'chkActContent',
	                readOnly: true,
	                value: 0,
	                store: new Ext.data.ArrayStore({
	                    fields: ['valueField','displayField'],
	                    data: [['0','算法1：应收商户手续费*费率']]
	                })
                 }]
        },{
		    layout: 'form',
		    columnWidth: .9,
		    items: [{	
		         	xtype: 'textarea',
		 			fieldLabel: '备注描述',
		 			id: 'chkRemarks',
		 			name: 'chkRemarks',
		 			width: 250,
		 			maxLength: 40,
			 		readOnly: true
	 				}]
	 }]
});

 /***********  商户营销活动窗口  *****************/
checkNamespace.actWin = new Ext.Window({
		title: '商户营销活动审核',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 1000,
		height: 400,
		layout:'column',
        autoScroll:true,
		items: [{
				id: 'leftTab',
                layout:'column',
                columnWidth: .5,
                items: [checkNamespace.actForm]
            },{
            	id: 'rightTab',
                layout:'column',
                columnWidth: .5,
                items: [checkNamespace.chkForm]
               }],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '审核',
			handler: function() {
				var	url = 'T70204Action_modifyCheck.asp';
				if(checkNamespace.actForm.getForm().isValid()) {
					checkNamespace.actForm.getForm().submitNeedAuthorise({
						url: url,
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,checkNamespace.actWin);
							checkNamespace.actForm.getForm().reset();
                            checkNamespace.actWin.hide();
							grid.getStore().load();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,checkNamespace.actWin);
						},
						params: {
							txnId: '70204',
							subTxnId: '02'
						}
					});
				}
			}
		},{
			text: '关闭',
			handler: function() {
				checkNamespace.actWin.hide();
			}
		}]
	});
	
	checkNamespace.actStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'chkActNo',mapping: 'actNo'},
            {name: 'chkbankNo',mapping: 'bankNo'},
            {name: 'chkActName',mapping: 'actName'},
            {name :'chkState',mapping: 'state'},
            {name :'chkStartDate',mapping: 'startDate'},
            {name :'chkEndDate',mapping: 'endDate'},
            {name: 'chkOrganNo',mapping: 'organNo'},
            {name: 'chkOrganType',mapping: 'organType'},
            {name: 'chkRemarks',mapping: 'remarks'}
        ]),
        autoLoad: false
    });

	function checkMod(record,actNo,mchntNo) {
		checkNamespace.actStore.load({
	        params: {
	             storeId: 'getActInfo',
	             actNo: actNo
	        },
	        callback: function(records, options, success){
	            if(success){
	            	checkNamespace.chkForm.getForm().loadRecord(checkNamespace.actStore.getAt(0));
	            	checkNamespace.actForm.getForm().loadRecord(record);
	            	checkNamespace.actWin.show();
	            	checkNamespace.actWin.center();
	            }else{
	            	checkNamespace.actWin.hide();
	            }
	        }
	    });
	};