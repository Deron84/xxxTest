Ext.onReady(function() {	
	var queryForm = new Ext.form.FormPanel({
		title: '银行卡受理终端数量统计报表',
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50906',
		waitMsgTarget: true,
		items: [new Ext.ux.MonthField({
			id: 'startMon',
			name: 'startMon',
			fieldLabel: '统计开始月份',
			allowBlank: false,
			anchor: '75%'
		}),new Ext.ux.MonthField({
			id: 'endMon',
			name: 'endMon',
			fieldLabel: '统计结束月份',
			allowBlank: false,
			anchor: '75%'
		}),{
			xtype: 'panel',
			html: '<br/><br/>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					timeout: 60000,
					url: 'T50906Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+	
																	action.result.msg+'&key=exl6exl';
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '50906',
						subTxnId: '01'
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
})