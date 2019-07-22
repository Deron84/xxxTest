Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '移机监测报表',
		frame: true,
		border: true,
		width: 460,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50401',
		waitMsgTarget: true,
		defaults: {
			labelWidth: 30,
			width: 320
		},
		items: [{
			xtype: 'datefield',
			name: 'date',
			fieldLabel: '统计日期*',
			allowBlank: false
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '机构编号*',
			hiddenName: 'acqInstId',
			allowBlank: false
		},reportType],
		buttonAlign: 'center',
		buttons: [{
			text: '移机监测报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T50401Action.asp',
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