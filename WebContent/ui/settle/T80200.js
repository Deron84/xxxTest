Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '收单账户对账报表下载',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80200',
		waitMsgTarget: true,
		items: [{
			xtype: 'basecomboselect',
			fieldLabel: '报表文件名称',
			width: 300,
			labelStyle: 'padding-left: 5px',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['000','01  间联收单表'],
					   ['001','02  退货表'],
					   ['002','T+0入账汇总日报表'],
					   ['003','T+0入账明细日报表'],
					   ['004','清算渠道分析汇总报表']
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
//							alert(action.result.msg);
						    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														action.result.msg;
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,queryForm);
						},
						params: {
                            txnId: '80200',
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