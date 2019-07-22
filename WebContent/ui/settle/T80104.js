Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '退货及对账文件报表',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80104',
		waitMsgTarget: true,
		items: [{
			xtype: 'basecomboselect',
			fieldLabel: '报表文件名称',
			width: 300,
			labelStyle: 'padding-left: 5px',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0521_DFERROR_000001','01 银数(无磁)贷记卡退货'],
				       ['0521_DFERROR_000002','02 银数(分期)贷记卡退货'],
					   ['0521_HOSTERROR_000001','03 借记卡退货文件'],
					   ['ONE','04 银联差错文件'],
					   ['mchtBill','05 商户对账单']
					],
				reader: new Ext.data.ArrayReader()
			}),
			hiddenName: 'reportName',
			allowBlank: false
		},{	
			xtype: 'datefield',
			name: 'date',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '清算日期*',
			width: 300,
			blankText: '请选择清算日期',
			allowBlank: false
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				var reportName = queryForm.getForm().findField('reportName').getValue();
				if(reportName == ''){
					showErrorMsg('请选择报表文件名称。',queryForm);
					return;
				}
				
				queryForm.getForm().submit({
						url: 'T801041Action.asp?method=down',
						timeout: 60000,
						waitMsg: '正在下载报表，请稍等......',
						
						success: function(form,action) {
						    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														action.result.msg;
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,queryForm);
						},
						params: {
                            txnId: '80104',
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
	
	
	queryForm.getForm().findField('date').setValue(YESTERDAY);

});