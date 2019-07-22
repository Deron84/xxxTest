Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '收单对账',
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		renderTo: 'middlediv',
		iconCls: 'T80101',
		waitMsgTarget: true,
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'datefield',
			name: 'date',
			id: 'date',
			fieldLabel: '清算日期',
			allowBlank: false,
			maxValue : TORDAY,
			blankText: '请选择清算日期'
		},{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T80101Action_init.asp',
					waitMsg: '正在请求对账任务，请稍后......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/page/settle/T8010101.jsp?date='+Ext.getCmp('date').getValue().format('Ymd');
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '80101',
						subTxnId: '01'
					}
				});
			}
		}]
	});
	queryForm.getForm().findField('date').setValue(YESTERDAY);

})