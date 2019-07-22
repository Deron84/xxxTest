Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '清算报表',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80206',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'basecomboselect',
			fieldLabel: '报表文件名称',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['POSPSET','01 机构清算信息汇总表'],['T1ACCOUNT','02 T+1待入账信息明细表'],
						['T0SUCACCOUNT','03 T+0已入账信息明细表'],['T0NOTACCOUNT','04 T+0未入账信息明细表'],
						['T0FAILACCOUNT','05 T+0入账失败信息明细表'],['T0TRANSFERBRANCH','06 T+0资金头寸划拨对照表'],
						['MCHT_SETTLE_REV','07 负金额商户明细表'],['MCHT_SETTLE','08 商户资金结算明细表'],
						['BRH_MCHT_','09 商户交易明细表（分行）'],['NOPROCMCHT','10 分行商户未正常清分报表']
					],
				reader: new Ext.data.ArrayReader()
			}),
			hiddenName: 'reportName'
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '机构编号*',
			hiddenName: 'brhId',
			allowBlank: false,
			editable: true
		},{
			xtype: 'datefield',
			name: 'date',
			fieldLabel: '清算日期*',
			blankText: '请选择清算日期',
			allowBlank: false
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				var reportName = queryForm.getForm().findField('reportName').getValue();
				if(reportName == ''){
					showErrorMsg('请选择报表文件名称。',queryForm);
					return;
				}
				var brhId = queryForm.getForm().findField('brhId').getValue();
				if(reportName == 'BRH_MCHT_'){
					if(brhId == '9900'){
						showErrorMsg('[商户交易明细表（分行）]暂时只为分行提供',queryForm);
						return;
					}
				}
				queryForm.getForm().submit({
					url: 'T80206Action_download.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		},{
			text: '打包下载',
			iconCls: 'icon_download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T80206Action_downloadAll.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													action.result.msg;
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
	queryForm.getForm().findField('date').setValue(YESTERDAY);
})