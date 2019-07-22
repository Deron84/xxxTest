Ext.onReady(function() {	
	var queryForm = new Ext.form.FormPanel({
		title: '商户交易报表',
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50918',
		waitMsgTarget: true,
		items: [{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '归属分行',
			hiddenName: 'brhId',
			value:'0000',
			anchor: '90%'
		},{
		/*	xtype : 'dynamicCombo',
			fieldLabel : '商户ID',
			methodName : 'getMchntIdTmp',
			hiddenName : 'mchtNo',
			editable : true,*/
			xtype:'textfield',
			fieldLabel: '商户号',
			id:'mchtNo',
			name:'mchtNo',
			anchor: '90%'
		},{
			xtype:'datefield',
			id: 'date',
			name: 'date',
			fieldLabel: '统计开始时间*',
			allowBlank: false,
			anchor: '90%'
		},{
			xtype:'datefield',
			id: 'date2',
			name: 'date2',
			fieldLabel: '统计结束时间*',
			allowBlank: false,
			anchor: '90%'
		},{
			xtype: 'panel',
			html: '<br/><br/>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '商户交易报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					timeout: 60000,
					url: 'T50918Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																	action.result.msg+'&key=exl18exl';
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