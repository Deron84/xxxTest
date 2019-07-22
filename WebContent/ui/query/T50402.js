Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '疑似套现检测报表',
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50402',
		waitMsgTarget: true,
		items: [new Ext.ux.MonthField({
			id: 'date',
			name: 'date',
			fieldLabel: '统计时间*',
			width: 163,
			allowBlank: false
		}),{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '机构编号*',
			width: 163,
			hiddenName: 'acqInstId',
			allowBlank: false
		},{
			xtype: 'basecomboselect',
			fieldLabel: '报表文件名称*',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','1:疑似套现监测明细'],['2','2:疑似套现监测']
				],
				reader: new Ext.data.ArrayReader()
			}),
			hiddenName: 'reportName',
			allowBlank: false
		},reportType],
		buttonAlign: 'center',
		buttons: [{
			text: '疑似套现检测报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T50402Action.asp',
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