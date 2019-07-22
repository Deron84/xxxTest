Ext.onReady(function() {	
	var queryForm = new Ext.form.FormPanel({
		title: '全辖外包服务费用统计月报表',
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50902',
		waitMsgTarget: true,
		items: [new Ext.ux.MonthField({
			id: 'mon',
			name: 'mon',
			fieldLabel: '统计时间',
			allowBlank: false,
			anchor: '90%%'
		}),{
//			xtype: 'basecomboselect',
//			baseParams: 'BRH_BELOW_BRANCH',
//			fieldLabel: '归属分行*',
//			hiddenName: 'brhId',
//			allowBlank: false,
//			anchor: '90%'
//		},{
			xtype: 'panel',
			html: '<br/><br/>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '全辖外包服务费用统计月报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					timeout: 60000,
					url: 'T50902Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																	action.result.msg+'&key=exl2exl';
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '50902',
						subTxnId: '01'
					}
				});
			}
		}]
	});
})