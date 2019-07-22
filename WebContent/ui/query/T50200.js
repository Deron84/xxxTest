Ext.onReady(function() {
	
	//MCC类别
	var mccStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_GRP_ALL',function(ret){
		mccStore.loadData(Ext.decode(ret));
	});
	
	
	var queryForm = new Ext.form.FormPanel({
		title: '商户信息统计表',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T50200',
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
			xtype: 'combo',
			store:mccStore,
			fieldLabel: 'MCC类别',
			hiddenName: 'mchtGrp',
			width: 350
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '机构编号*',
			hiddenName: 'brhId',
			allowBlank: false
		},{
			xtype: 'combo',
			fieldLabel: '商户状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','正常'],['3','修改待审核'],['5','停用待审核'],['6','停用'],['7','恢复待审核']],
				reader: new Ext.data.ArrayReader()
			}),
			editable: true,
			hiddenName: 'mchntSt',
			width: 163
		},{
			xtype: 'datefield',
			width:163,
			id: 'startDate',
			name: 'startDate',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '商户注册开始日期',
			editable: false
		},{
			xtype: 'datefield',
			width:163,
			id: 'endDate',
			name: 'endDate',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '商户注册结束日期',
			editable: false
		},{
		    xtype: 'textfield',
			fieldLabel: '客户经理工号',
			width:163,
			id: 'operNo',
			name: 'operNo'
		},{
		    xtype: 'textfield',
			fieldLabel: '客户经理姓名',
			width:163,
			id: 'operNm',
			name: 'operNm'
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
					url: 'T50200Action.asp',
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