Ext.onReady(function() {	
	var queryForm = new Ext.form.FormPanel({
		title: '商户终端信息统计表',
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50202',
		waitMsgTarget: true,
		items: [{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '收单机构*',
			hiddenName: 'brhId',
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
			text: '商户信息统计表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T50202Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
							action.result.msg
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '20101',
						subTxnId: '01',
						date: Ext.getCmp('mon').getValue()
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