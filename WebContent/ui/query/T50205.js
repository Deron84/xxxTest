Ext.onReady(function() {
	
	// 顶部查询面板
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		labelWidth: 240,
		layout: 'column',
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
	        	columnWidth: .5,
            	layout: 'form',
        		items: [{
        			xtype: 'radiogroup',
            		id: 'repType',
            		name: 'repType',
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
				queryForm.getForm().submitNeedAuthorise({
					url: 'T50205Action.asp',
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
		title: '商户及终端情况（月表）',
		frame: true,
		borde: true,
		autoHeight: true,
		renderTo: Ext.getBody(),
		items: [queryForm]
	});
})