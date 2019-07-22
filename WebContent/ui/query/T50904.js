Ext.onReady(function() {	
	var queryForm = new Ext.form.FormPanel({
		title: '疑似风险处理结果月报表',
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50904',
		waitMsgTarget: true,
		items: [new Ext.ux.MonthField({
			id: 'date',
			name: 'date',
			fieldLabel: '统计时间*',
			width: 163,
			allowBlank: false
		}),{
			xtype: 'panel',
			html: '<br/><br/>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '疑似风险处理结果月报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					timeout: 60000,
					url: 'T50904Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																	action.result.msg+'&key=exl4exl';
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '50904',
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