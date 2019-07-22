Ext.onReady(function() {	
	var queryForm = new Ext.form.FormPanel({
		title: '全辖自购终端使用情况月报表',
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50901',
		waitMsgTarget: true,
		items: [new Ext.ux.MonthField({
			id: 'mon',
			name: 'mon',
			fieldLabel: '统计时间',
			allowBlank: false,
			anchor: '75%'
		}),{
			xtype: 'panel',
			html: '<br/><br/>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '全辖自购终端使用情况月报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					timeout: 60000,
					url: 'T50911Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																		action.result.msg+'&key=exl11exl';
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '50911',
						subTxnId: '01'
					}
				});
			}
		}]
	});
})