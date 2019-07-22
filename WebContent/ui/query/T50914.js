Ext.onReady(function() {	
	var queryForm = new Ext.form.FormPanel({
		title: '全辖零交易和零（负）收益终端明细月报表',
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50914',
		waitMsgTarget: true,
		items: [{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_FALG2',
					fieldLabel: '商户性质2*',
					id: 'idMchtFlag2',
					hiddenName: 'mchtFlag2',
					allowBlank: false,
					anchor: '90%'
		        }]
        	},new Ext.ux.MonthField({
			id: 'mon',
			name: 'mon',
			fieldLabel: '统计时间',
			allowBlank: false,
			anchor: '90%'
		}),{
			xtype: 'panel',
			html: '<br/><br/>'
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
					url: 'T50914Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																		action.result.msg+'&key=exl14exl';
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '50914',
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