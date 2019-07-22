Ext.onReady(function() {
	
	// 顶部查询面板
	var queryForm = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        renderTo: 'report',
        labelWidth: 80,
		items : [{
			xtype : 'basecomboselect',
			baseParams : 'BRH_BELOW_BRANCH',
			fieldLabel : '机构编号*',
			hiddenName : 'brhId',
			anchor : '58%',
			allowBlank: false
		}, {
			width: 200,
			fieldLabel : '起始时间*',
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
			endDateField: 'endDate',
			allowBlank: false
		}, {
			width: 200,
			fieldLabel : '截止时间*',
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
			maxValue: new Date(),
			startDateField: 'startDate',
			allowBlank: false
		}],
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submitNeedAuthorise({
					url: 'T50206Action.asp',
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
	queryForm.getForm().findField('brhId').setValue(BRHID);
})