Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '商户报表',
		frame: true,
		border: true,
		width: 540,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80207',
		waitMsgTarget: true,
		defaults: {
			width: 380,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'basecomboselect',
			fieldLabel: '报表文件名称*',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['MCHT_DTL','01 商户交易对账明细表'],['POSPSET','02 商户待清算信息汇总表']
					],
				reader: new Ext.data.ArrayReader()
			}),
			hiddenName: 'reportName',
			allowBlank: false
		},{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号*',
			hiddenName: 'mchntNo',
			allowBlank: false,
			editable: true
		},{
			xtype: 'datefield',
			name: 'date',
			fieldLabel: '清算日期',
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
				queryForm.getForm().submit({
					url: 'T80207Action_download.asp',
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
	queryForm.getForm().findField('date').setValue(YESTERDAY);
})