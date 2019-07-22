Ext.onReady(function() {

	// 顶部查询面板
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		layout: 'column',
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
			layout: 'form',
			items: [{
				xtype: 'basecomboselect',
			    baseParams: 'MCHNT_NO',
				fieldLabel: '商户编号*',
				hiddenName: 'mchntCd',
				allowBlank: true
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
		},{
            columnWidth: .5,
            layout: 'form',
            items: [{
                xtype: 'basecomboselect',
                 baseParams: 'TERMIDID',
                fieldLabel: '终端号',
                hiddenName: 'terminalCode',
                allowBlank: true
             }]
            },{
            columnWidth: .5,
            layout: 'form',
            items: [{
                xtype: 'basecomboselect',
                baseParams: 'TERMINALTYPE',
                fieldLabel: 'POS终端类型',
                hiddenName: 'posType',
                allowBlank: true
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
						xtype: 'datefield',
						fieldLabel: '交易开始日期*',
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
						fieldLabel: '交易结束日期*',
						maxLength: 10,
						format: 'Ymd',
						id: 'endDate',
						name: 'endDate'
		        	}]
            },{
    			columnWidth: .5,
    			layout: 'form',
    			items: [{
    			xtype: 'basecomboselect',
    			store: new Ext.data.ArrayStore({
    				fields: ['valueField','displayField'],
    				data: [['1','借记卡'],['2','信用卡'],['0','全部']],
    				reader: new Ext.data.ArrayReader()
    			}),
    			hiddenName: 'codeType',
    			allowBlank: true,
    			fieldLabel: '卡种'
    			}]
    		},{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '卡号',
                    allowBlank: true,
                    id: 'codeNoId',
                    name: 'codeNo'
                }]
            },{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						 xtype: 'textfield',
	                    fieldLabel: '同一卡号交易笔数大于等于(笔)',
	                    allowBlank: true,
	                    id: 'rule1',
	                    name: 'rule1'
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
					url: 'T40305Action.asp',
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
		title: '单户同一卡号监控',
		frame: true,
		borde: true,
		autoHeight: true,
		renderTo: Ext.getBody(),
		items: [queryForm]
	});
})