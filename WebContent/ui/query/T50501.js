Ext.onReady(function() {
	
	var actStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('ACT_NO',function(ret){
        actStore.loadData(Ext.decode(ret));
    });
	
	var queryForm = new Ext.form.FormPanel({
		title: '商户营销活动报表',
		frame: true,
		border: true,
		width: 460,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50501',
		waitMsgTarget: true,
		defaults: {
			labelWidth: 30,
			anchor: '75%'
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
			id: 'actMon',
			name: 'actMon',
			fieldLabel: '统计时间',
			allowBlank: false,
			anchor: '75%'
		}),reportType],
		buttonAlign: 'center',
		buttons: [{
			text: '商户营销活动报表',
			iconCls: 'download',
			handler: function() {
				var selected = Ext.getCmp('actMon').getValue();
				var year = (new Date()).getFullYear();
				var month = (new Date()).getMonth();
				if(Number(selected.substring(0,4)) > year || 
					(Number(selected.substring(4,6)) > month+1 && Number(selected.substring(0,4)) == year ))
				{
					showAlertMsg("只能选择本月或前一月份的数据",queryForm);
					return;
				}
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T50501Action.asp',
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