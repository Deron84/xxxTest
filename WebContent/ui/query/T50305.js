Ext.onReady(function() {	
	// 可选差错交易类型下拉列表
	var queryForm = new Ext.form.FormPanel({
		title: '服务机构费用(月表)',
		frame: true,
		border: true,
		width: 360,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'mchnt',
		waitMsgTarget: true,
		items: [{
		    columnWidth: 1,
		    layout: 'form',
		    items: [{
			width: 200,
			allowBlank: false,
			forceSelection: false,
			editable:true,
		    baseParams: 'MCHNT_NO',
			fieldLabel: '商户号*',
			xtype: 'basecomboselect',
			hiddenName: 'mchntCd'
			}]
		
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
			width: 200,
			allowBlank: false,
			forceSelection: false,
			editable:true,
		    baseParams: 'BRH_ID',
			fieldLabel: '机构号',
			xtype: 'basecomboselect',
			hiddenName: 'brh'
			}]
		},{
			xtype: 'datefield',
			name: 'stDate',
			width: 200,
			fieldLabel: '统计日期'
			
		},{
			xtype: 'combo',
			fieldLabel: '报表类型',
			width: 200,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['EXCEL','Excel 格式'],['PDF','PDF 格式']],
				reader: new Ext.data.ArrayReader()
			}),
			editable: true,
			hiddenName: 'reportType',
			allowBlank: false,
			blankText: '请选择报表格式'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '服务机构费用(月表)',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T50305Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    var downLoadFile = action.result.downLoadFile;
						var downLoadFileName = action.result.downLoadFileName;
						var downLoadFileType = action.result.downLoadFileType;
						window.location.href = Ext.contextPath + '/page/system/download.jsp?downLoadFile='+
													downLoadFile+'&downLoadFileName='+downLoadFileName+'&downLoadFileType='+downLoadFileType;
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