Ext.onReady(function() {	
	var queryForm = new Ext.form.FormPanel({
		title: '营销员业绩明细日报表',
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50903',
		waitMsgTarget: true,
		items: [{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '归属分行',
			hiddenName: 'brhId',
			value:'0000',
			anchor: '90%'
		},{
			xtype: 'textfield',
			fieldLabel: '营业员',
			id: 'name',
			name: 'name',
			anchor: '90%'
		},{
			xtype:'datefield',
			id: 'date',
			name: 'date',
			fieldLabel: '统计时间*',
			allowBlank: false,
			anchor: '90%'
		},{
			xtype: 'panel',
			html: '<br/><br/>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '营销员业绩明细日报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					timeout: 60000,
					url: 'T50903Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																	action.result.msg+'&key=exl3exl';
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '50903',
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