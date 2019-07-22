Ext.onReady(function() {	
	var queryForm = new Ext.form.FormPanel({
		title: '全辖本行开户客户统计月报表',
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50909',
		waitMsgTarget: true,
		items: [{
			xtype: 'basecomboselect',
			baseParams: 'MCHT_FALG1',
			fieldLabel: '商户性质1*',
			hiddenName: 'mchtFlag1',
			id: 'idMchtFlag1',
			allowBlank: false,
			anchor: '75%'
		},new Ext.ux.MonthField({
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
			text: '全辖本行开户客户统计月报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					timeout: 60000,
					url: 'T50909Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																		action.result.msg+'&key=exl9exl';
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '50909',
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