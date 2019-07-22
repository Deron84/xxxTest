Ext.onReady(function() {	
	//查询机构编号
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	var queryForm = new Ext.form.FormPanel({
		title: '商户清算',
		frame: true,
		border: true,
		width: 600,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'mchnt',
		waitMsgTarget: true,
		items: [{
			xtype: 'datefield',
			name: 'stDate',
			width: 200,
			fieldLabel: '开始日期'
			
		},{
			xtype: 'combo',
			fieldLabel: '清算类型*',
			width: 200,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','0:商户清算'],['1','1:其他在商户清算']],
				reader: new Ext.data.ArrayReader()
			}),
			editable: true,
			hiddenName: 'feeType'
		}, {
            xtype: 'textarea',
			fieldLabel: '结果信息',
			width: 400,
            name: 'msg',
            flex: 1  // Take up all *remaining* vertical space
        }],
		buttonAlign: 'center',
		buttons: [{
			text: '获取清算状体',
			handler: function() {
				queryForm.getForm().reset();
			}
		},{
			text: '确定',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T50201Action.asp',
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
			text: '清空条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
})