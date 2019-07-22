Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '商户交易统计表（月报）',
		frame: true,
		border: true,
		width: 420,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'mchnt',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '机构编号*',
			hiddenName: 'brhId',
			allowBlank: false,
			anchor: '75%'
		},new Ext.ux.MonthField({
			id: 'mon',
			name: 'mon',
			fieldLabel: '统计时间',
			allowBlank: false,
			anchor: '75%'
		})],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T50203Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													action.result.msg;
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