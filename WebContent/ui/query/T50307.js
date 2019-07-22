Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '新商户交易监测',
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'mchnt',
		waitMsgTarget: true,
//		defaults: {
//			labelWidth: 20,
//			width: 300
//		},
		items: [new Ext.ux.MonthField({
			id: 'date',
			name: 'date',
			fieldLabel: '统计时间',
			width: 163,
			allowBlank: false
		}),{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '机构编号*',
			width: 163,
			hiddenName: 'acqInstId',
			allowBlank: false
		},reportType],
		buttonAlign: 'center',
		buttons: [{
			text: '新商户交易监测',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T50307Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+action.result.msg;
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
})