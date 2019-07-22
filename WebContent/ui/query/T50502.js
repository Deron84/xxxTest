Ext.onReady(function() {
	//专业服务机构
    var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	var queryForm = new Ext.form.FormPanel({
		title: '专业服务机构分润报表',
		frame: true,
		border: true,
		width: 460,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50502',
		waitMsgTarget: true,
		defaults: {
			labelWidth: 30,
			width: 320
		},
		items: [
			{
				xtype: 'combo',
                fieldLabel: '所属机构',
                store: brhStore,
                id: 'brh',
                hiddenName: 'brhId'
            },{
				xtype: 'datefield',
				name: 'date',
				id: 'date',
				maxValue: new Date(),
				fieldLabel: '日期*',
				allowBlank: false
			},{
                xtype: 'combo',
                fieldLabel: '报表日期类型*',
                id: 'reportDType',
                allowBlank: false,
                hiddenName: 'reportDateType',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['1','日报'],['2','月报']]
              })
            },reportType],
		buttonAlign: 'center',
		buttons: [{
			text: '专业服务机构分润报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T50502Action.asp',
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