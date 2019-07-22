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
		hiddenName: 'brh'
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
                fieldLabel: '商户名',
                allowBlank: true,
                id: 'mchntNameId',
                width: 155,
                name: 'mchntName'
            }]
        },{
			columnWidth: .5,
			layout: 'form',
			width: 300,
        	labelWidth : 200,
			items: [{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','批发类'],['2','非批发类'],['0','全部']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择商户类型',
			id: 'mchntType',
			hiddenName: 'mchntType',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: true,
			fieldLabel: '商户类型'
			}]
		},{
            columnWidth: .5,
            layout: 'form',
            width: 300,
        	labelWidth : 200,
            items: [{
                xtype: 'combo',
                fieldLabel: '终端号',
                hiddenName: 'terminalCode',
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
                xtype: 'combo',
                fieldLabel: 'POS终端类型',
                hiddenName: 'posType',
                id: 'posType',
                store: termTypeStore,
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
						fieldLabel: '交易开始日期*',
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
						fieldLabel: '交易结束日期*',
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
    			xtype: 'combo',
    			store: new Ext.data.ArrayStore({
    				fields: ['valueField','displayField'],
    				data: [['1','借记卡'],['2','信用卡'],['0','全部']],
    				reader: new Ext.data.ArrayReader()
    			}),
    			displayField: 'displayField',
    			valueField: 'valueField',
    			emptyText: '请选择卡种',
    			id: 'codeType',
    			hiddenName: 'codeType',
    			mode: 'local',
    			triggerAction: 'all',
    			forceSelection: true,
    			typeAhead: true,
    			selectOnFocus: true,
    			editable: false,
    			allowBlank: true,
    			fieldLabel: '卡种'
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
                layout: 'form',
                width: 355,
	        	labelWidth : 200,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '单笔相同交易金额',
                    allowBlank: true,
                    id: 'dbjyId',
                    width: 155,
                    name: 'dbjy'
                }]
            },{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 355,
		        	labelWidth : 200,
					items: [{
						 xtype: 'textfield',
	                    fieldLabel: '单笔相同交易金额浮动范围(%)',
	                    allowBlank: true,
	                    id: 'rule1',
	                    name: 'rule1',
	                    width: 155
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 355,
		        	labelWidth : 200,
					items: [{
						 xtype: 'textfield',
	                    fieldLabel: '同一卡号相同交易金额大于等于(笔)',
	                    allowBlank: true,
	                    id: 'rule2',
	                    name: 'rule2',
	                    width: 155
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
            	layout: 'form',
        		items: [{
        			xtype: 'radiogroup',
           			labelStyle: 'padding-left: 5px',
            		fieldLabel: '报表种类*',
            		allowBlank: false,
					blankText: '请选择报表种类',
            		items: [
            	   		{boxLabel: 'PDF报表', name: 'reportType', inputValue: 'PDF'},
            	    	{boxLabel: 'EXCEL报表', name: 'reportType', inputValue: 'EXCEL'}
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
					url: 'T40306Action.asp',
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
		title: '单户同卡号相同金额交易监测',
		frame: true,
		borde: true,
		autoHeight: true,
		renderTo: Ext.getBody(),
		items: [queryForm]
	});
})