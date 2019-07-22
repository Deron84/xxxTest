Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '异常交易监控报表',
		frame: true,
		border: true,
		width: 460,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50403',
		waitMsgTarget: true,
		defaults: {
			labelWidth: 30,
			width: 320
		},
		items: [{
			xtype: 'basecomboselect',
			baseParams: 'KIND',
			fieldLabel: '风险模型类型',
			hiddenName: 'kind',
			allowBlank: true
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '机构编号*',
			hiddenName: 'acqInstId',
			allowBlank: false
		},{
			xtype: 'datefield',
			id: 'date1',
			name: 'date1',
			vtype: 'daterange',
			endDateField: 'date2',
			fieldLabel: '开始日期*',
			editable: false,
			allowBlank: false
		},{
			xtype: 'datefield',
			id: 'date2',
			name: 'date2',
			vtype: 'daterange',
			startDateField: 'date1',
			fieldLabel: '结束日期*',
			editable: false,
			allowBlank: false
		},reportType],
		buttonAlign: 'center',
		buttons: [{
			text: '异常交易监控报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T50403Action.asp',
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
});