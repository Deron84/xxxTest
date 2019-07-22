Ext.onReady(function() {
	//查询商户编号
	var mchntNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntNoStore.loadData(Ext.decode(ret));
	});
	// 顶部查询面板
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		layout: 'column',
		labelWidth: 240,
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			columnWidth: .5,
			layout: 'form',
			items: [{
				xtype: 'basecomboselect',
			    baseParams: 'BRH_BELOW',
				fieldLabel: '收单机构*',
				hiddenName: 'brh',
				allowBlank: true
			}]
		},{
        	columnWidth: .5,
       		items: [{
	        	xtype: 'panel',
	        	layout: 'form',
				items: [{
		        	xtype: 'combo',
					fieldLabel: '商户编号*',
					store: mchntNoStore,
					displayField: 'displayField',
					valueField: 'valueField',
					mode: 'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: true,
					allowBlank: true,
					lazyRender: true,
					width: 230,
					blankText: '请选择商户编号',
					id: 'mchntCdId',
					hiddenName: 'mchntCd'
	        	}]
        	}]
		},{
            columnWidth: .5,
            layout: 'form',
            items: [{
                xtype: 'textfield',
                fieldLabel: '商户名',
                allowBlank: true,
                id: 'mchntNameId',
                name: 'mchntName'
            }]
        },{
			columnWidth: .5,
			layout: 'form',
			items: [{
			xtype: 'basecomboselect',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','批发类'],['2','非批发类'],['0','全部']],
				reader: new Ext.data.ArrayReader()
			}),
			hiddenName: 'mchntType',
			allowBlank: true,
			fieldLabel: '商户类型'
			}]
		}
//        ,{
//            columnWidth: .5,
//            layout: 'form',
//            items: [{
//                xtype: 'basecomboselect',
//                baseParams:'TERMID',
//                fieldLabel: '终端编号',
//                hiddenName: 'terminalCode',
//                allowBlank: true
//             }]
//            }
		,{
            columnWidth: .5,
            layout: 'form',
            items: [{
			xtype: 'basecomboselect',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','全部'],['1','直联电话POS'],['2','本行间联POS'],['3','他行间联POS'],['4','他行间联POS']],
				reader: new Ext.data.ArrayReader()
			}),
			hiddenName: 'posType',
			allowBlank: true,
			fieldLabel: 'POS类型'
			}]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '客户经理工号',
                    allowBlank: true,
                    id: 'mchntManagerCd',
                    name: 'mchntManagerCd'
                }]
            },{
			columnWidth: .5,
			layout: 'form',
			items: [{
			xtype: 'basecomboselect',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','信用卡'],['0','所有卡']],
				reader: new Ext.data.ArrayReader()
			}),
			hiddenName: 'cardType',
			allowBlank: true,
			fieldLabel: '卡种'
			}]
		},{
                columnWidth: .5,
                layout: 'form',
                items: [{
						xtype: 'datefield',
						fieldLabel: '监测月份*',
						maxLength: 10,
						format: 'Ymd',
						id: 'startDate',
						name: 'startDate'
		        	}]
          },{
                columnWidth: .5,
                layout: 'form',
                items: [{
						xtype: 'datefield',
						fieldLabel: '比较月份*',
						maxLength: 10,
						format: 'Ymd',
						id: 'endDate',
						name: 'endDate'
		        }]
            },{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						 xtype: 'textfield',
	                    fieldLabel: '监测月份累计交易笔数>=比较月份累计交易笔数算术平均数的(%)',
	                    allowBlank: true,
	                    id: 'rule1',
	                    name: 'rule1'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						 xtype: 'textfield',
	                    fieldLabel: '监测月份累计交易金额>=比较月份累计交易金额算术平均数的(%)',
	                    allowBlank: true,
	                    id: 'rule2',
	                    name: 'rule2'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
            	layout: 'form',
        		items: [{
        			xtype: 'radiogroup',
            		id: 'reportType',
            		name: 'reportType',
            		fieldLabel: '报表种类*',
            		allowBlank: false,
					blankText: '请选择报表种类',
            		items: [
            	    	{boxLabel: 'EXCEL报表', name: 'reportType', inputValue: 'EXCEL',checked: true},
            	   		{boxLabel: 'PDF报表', name: 'reportType', inputValue: 'PDF'}
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
						url: 'T40303Action.asp',
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
		title: '单月单户信用卡交易监测',
		frame: true,
		borde: true,
		autoHeight: true,
		renderTo: Ext.getBody(),
		items: [queryForm]
	});
})