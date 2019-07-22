Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '对账报表',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'mchnt',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW',
			fieldLabel: '收单机构*',
			hiddenName: 'brhId',
			allowBlank: false
		},{
			xtype: 'basecomboselect',
			fieldLabel: '报表文件名称*',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['HOSTPOSDIS','1:机构对账不平交易明细表BDB'],['HOSTPOSSUS','2:机构对账可疑交易明细表BDB'],
						['POSPCUPSDIS','3:机构对账不平交易明细表BDT'],['POSPCUPSSUS','4:机构对账可疑交易明细表BDT'],
						['BRANCHBANKDET','5:对账交易明细表'],['T0SUCREPORTBRANCH','6:分行T+0入账成功交易明细表'],
						['T0FAILREPORTBRANCH','7:分行T+0入账失败交易明细表'],['T0NOTREPORTBRANCH','8:分行T+0未入账交易明细'],
						['OLDPOSREPORTBRANCH','9:分行老POS交易明细表'],['T0SUCSUMBRANCH','10:T+0入账成功汇总表（分行）'],
						['T0FAILNUMBRANCH','11:T+0入账失败汇总表（分行）'],['T0NOTNUMBRANCH','12:T+0未入账汇总表（分行）'],
						['NOPBRANCHTRANS','13:银联跨行交易清分报表（分行）']
				      ],
				reader: new Ext.data.ArrayReader()
			}),
			hiddenName: 'reportName',
			allowBlank: false
		},{
			xtype: 'datefield',
			name: 'date',
			fieldLabel: '日终日期*',
			blankText: '请选择日终日期',
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
				queryForm.getForm().submit({
					url: 'T80105Action_download.asp',
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
})