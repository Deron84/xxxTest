Ext.onReady(function() {	
	var actStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('ACT_NO',function(ret){
        actStore.loadData(Ext.decode(ret));
    });
	
	var queryForm = new Ext.form.FormPanel({
		title: '营销活动代发文件',
		frame: true,
		border: true,
		width: 420,
		autoHeight: true,
		iconCls: 'T70207',
		waitMsgTarget: true,
		renderTo: 'panel',
		defaults: {
			width: 180,
			labelStyle: 'padding-left: 35px'
		},
		items: [{	
				xtype: 'combo',
                fieldLabel: '活动编号',
                store: actStore,
                hiddenName: 'actNo',
                id: 'actNoQ',
                displayField: 'displayField',
                valueField: 'valueField'
           },new Ext.ux.MonthField({
			id: 'date',
			name: 'date',
			fieldLabel: '统计时间*',
			allowBlank: false
		})],
		buttonAlign: 'center',
		buttons: [{
			text: '1.生成代发文件',
			iconCls: 'accept',
			id: 'makefile',
			handler: function(bt) {
				var selected = Ext.getCmp('date').getValue();
				var year = (new Date()).getFullYear();
				var month = (new Date()).getMonth();
				if(Number(selected.substring(0,4)) > year || 
					(Number(selected.substring(4,6)) > month+1 && Number(selected.substring(0,4)) == year ))
				{
					showAlertMsg("只能选择本月或者前一月份的数据",queryForm);
					return;
				}
				queryForm.getForm().submit({
					url: 'T70207Action_makefile.asp',
					waitMsg: '正在发送请求，请稍等......',
					success: function(form,action) {
						showSuccessAlert(action.result.msg,queryForm);
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '70207',
						subTxnId: '01'
					}
				});
			}
		},{
			text: '2.下载代发文件',
			iconCls: 'download',
			id: 'download',
			handler: function(bt) {
				var selected = Ext.getCmp('date').getValue();
				var year = (new Date()).getFullYear();
				var month = (new Date()).getMonth();
				if(Number(selected.substring(0,4)) > year || 
					(Number(selected.substring(4,6)) > month+1 && Number(selected.substring(0,4)) == year ))
				{
					showAlertMsg("只能选择本月或者前一月份的数据",queryForm);
					return;
				}
				if(queryForm.getForm().findField('actNo').getValue() == '')
				{
					showAlertMsg("下载代发文件必须填写活动编号",queryForm);
					return;
				}
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