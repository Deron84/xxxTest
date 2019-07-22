Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '终端信息统计表',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50201',
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号',
			hiddenName: 'mchntNo',
			allowBlank: true,
			editable: true,
			width: 350
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '机构编号*',
			hiddenName: 'brhId',
			allowBlank: false
		},{                                         
            columnWidth: .4,
            layout: 'form',
            items: [new Ext.form.TextField({
                id: 'termNo',
                width: 163,
                fieldLabel: '终端编号'
            })]
            },{                                         
            columnWidth: .4,
            layout: 'form',
            items: [{
                xtype: 'combo',
                fieldLabel: '终端状态',
                width: 163,
                hiddenName: 'state',
                editable:false,
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['1','正常'],['4','停用'],['0','新增待审核'],['2','修改待审核'],['3','停用待审核'],['5','恢复待审核']]
                })
            }]
        },{
            columnWidth: .4,
            layout: 'form',
            items: [{
                width: 163,
                xtype: 'datefield',
                fieldLabel: '终端注册起始时间',
                name :'startDate',
                id :'startDate'      
            } ]
        },{                                         
            columnWidth: .4,
            layout: 'form',
            items: [{
                width: 163,
                xtype: 'datefield',
                fieldLabel: '终端注册终止时间',
                name :'endDate',
                id :'endDate'     
            }]
        },reportType],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T50201Action.asp',
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