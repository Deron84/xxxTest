Ext.onReady(function() {	
	// 所属机构
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	var queryForm = new Ext.form.FormPanel({
		title: '营销活动代发文件',
		frame: true,
		border: true,
		width: 420,
		autoHeight: true,
		iconCls: 'T70208',
		waitMsgTarget: true,
		renderTo: 'panel',
		defaults: {
			width: 180,
			labelStyle: 'padding-left: 35px'
		},
		items: [{	
				xtype: 'textfield',
                fieldLabel: '活动编号 *',
                id: 'actNo',
                name: 'actNo',
                maxLength: 10,
                allowBlank: false
           },new Ext.ux.MonthField({
			id: 'date',
			name: 'date',
			fieldLabel: '统计时间*',
			allowBlank: false
		})],
		buttonAlign: 'center',
		buttons: [{
			text: '下载代发文件',
			iconCls: 'download',
			id: 'makefile',
			handler: function(bt) {
				queryForm.getForm().submit({
					url: 'T70208Action_download.asp',
					waitMsg: '正在发送请求，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '70208',
						subTxnId: '01'
					}
				});
			}
		}]
	});
})