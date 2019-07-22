Ext.onReady(function() {
	
	//查询商户编号
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	//查询商户编号
	var mchntNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntNoStore.loadData(Ext.decode(ret));
	});
	
	// 终端库存号
	var termIdIdStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('TERMIDID',function(ret){
		termIdIdStore.loadData(Ext.decode(ret));
	});
	
	// 终端类型
	var termTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('TERMINALTYPE',function(ret){
		termTypeStore.loadData(Ext.decode(ret));
	});
	
	
	
	// 可选机构下拉列表
	var brhCombo = new Ext.form.ComboBox({
		store: brhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择机构',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		blankText: '请选择一个机构',
		fieldLabel: '收单机构*',
		id: 'brhIdId',
		name: 'brhId',
		hiddenName: 'brh',
		listeners:{
            'select': function() {
         var args = Ext.getCmp('termBranchNew').getValue()+","+Ext.get('terminalTypeN').dom.value+","+Ext.get('termFactoryN').dom.value;
            SelectOptionsDWR.getComboDataWithParameter('TERMIDID',args,function(ret){
                termIdIdStore.loadData(Ext.decode(ret));
            });
            }
        }
	});
	
	// 可选商户下拉列表
	var mchntNoCombo = new Ext.form.ComboBox({
		store: mchntNoStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择商户号',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		blankText: '请选择一个商户号',
		fieldLabel: '商户编号*',
		id: 'mchntCdId',
		name: 'mchntCd',
		hiddenName: 'mchntCd'
	});
	
	// 顶部查询面板
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		layout: 'column',
		items: [{
			columnWidth: .5,
			layout: 'form',
			width: 300,
        	labelWidth : 200,
			items: [brhCombo]
		},{
			columnWidth: .5,
			layout: 'form',
			width: 300,
        	labelWidth : 200,
			items: [mchntNoCombo]
		},{
            columnWidth: .5,
            layout: 'form',
            width: 300,
        	labelWidth : 200,
            items: [{
                xtype: 'textfield',
                fieldLabel: '单位名称',
                allowBlank: true,
                id: 'companyNameId',
                width: 155,
                name: 'companyName'
            }]
        },{
            columnWidth: .5,
            layout: 'form',
            width: 300,
        	labelWidth : 200,
            items: [{
                xtype: 'combo',
                fieldLabel: '终端编号',
                hiddenName: 'terminalCode1',
                id: 'terminalCode',
                store: termIdIdStore,
                displayField: 'displayField',
                valueField: 'valueField',
                allowBlank: true
             }]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
	        	labelWidth : 200,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '客户经理工号',
                    allowBlank: true,
                    id: 'mchntManagerCd',
                    width: 155,
                    name: 'mchntManagerCd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
	        	labelWidth : 200,
                items: [{
						xtype: 'datefield',
						fieldLabel: '开始日期*',
						maxLength: 10,
						format: 'Ymd',
						width: 155,
						id: 'startDate',
						name: 'startDate'
		        	}]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
	        	labelWidth : 200,
                items: [{
						xtype: 'datefield',
						fieldLabel: '结束日期*',
						maxLength: 10,
						width: 155,
						format: 'Ymd',
						id: 'endDate',
						name: 'endDate'
		        	}]
            },{
                columnWidth: .5,
                layout: 'form',
                width: 300,
            	labelWidth : 200,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '卡号',
                    allowBlank: true,
                    id: 'codeNoId',
                    width: 155,
                    name: 'codeNo'
                }]
            },{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 500,
		        	labelWidth : 200,
					items: [{
						 xtype: 'textfield',
	                    fieldLabel: '对同一商卡“借款+报销”累计笔数大于等于(笔)',
	                    allowBlank: true,
	                    id: 'rule1',
	                    name: 'rule1',
	                    width: 200
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 500,
		        	labelWidth : 200,
					items: [{
						 xtype: 'textfield',
	                    fieldLabel: '对同一商卡“借款+报销”累计金额大于等于(元)',
	                    allowBlank: true,
	                    id: 'rule12',
	                    name: 'rule2',
	                    width: 200
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 500,
		        	labelWidth : 200,
					items: [{
						 xtype: 'textfield',
	                    fieldLabel: '借款笔数/还款笔数大于等于(%)',
	                    allowBlank: true,
	                    id: 'rule13',
	                    name: 'rule3',
	                    width: 200
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 500,
		        	labelWidth : 200,
					items: [{
						 xtype: 'textfield',
	                    fieldLabel: '借款金额/还款金额大于等于(%)',
	                    allowBlank: true,
	                    id: 'rule14',
	                    name: 'rule4',
	                    width: 200
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
            	layout: 'form',
        		items: [{
        			xtype: 'radiogroup',
            		fieldLabel: '报表种类*',
            		allowBlank: false,
					blankText: '请选择报表种类',
            		items: [
            	   		{boxLabel: 'PDF报表', name: 'reportType', inputValue: 'PDF'},
            	    	{boxLabel: 'EXCEL报表', name: 'reportType', inputValue: 'EXCEL',checked: true}
            		]
        		}]
			}],
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T40310Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    var downLoadFile = action.result.downLoadFile;
						var downLoadFileName = action.result.downLoadFileName;
						var downLoadFileType = action.result.downLoadFileType;
						window.location.href = Ext.contextPath + '/page/system/download.jsp?downLoadFile='+
													downLoadFile+'&downLoadFileName='+downLoadFileName+'&downLoadFileType='+downLoadFileType;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		},{
			text: '清空查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	
	// 主面板
	var secondMainPanel = new Ext.Panel({
		title: '财务POS交易监测',
		frame: true,
		borde: true,
		autoHeight: true,
		renderTo: Ext.getBody(),
		items: [queryForm]
	});
})