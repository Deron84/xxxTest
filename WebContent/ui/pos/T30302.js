Ext.onReady(function() {
		// 商户数据集
		var manufacturerStore = new Ext.data.JsonStore({
			fields: ['valueField','displayField'],
			root: 'data'
		});
		SelectOptionsDWR.getComboData('MANUFACTURER',function(ret){
			manufacturerStore.loadData(Ext.decode(ret));
		});
		var terminalTypeStore = new Ext.data.JsonStore({
			fields: ['valueField','displayField'],
			root: 'data'
		});

		SelectOptionsDWR.getComboDataWithParameter('TERMINALTYPE',value,function(ret){
			terminalTypeStore.loadData(Ext.decode(ret));
		});
		var brhStore = new Ext.data.JsonStore({
			fields: ['valueField','displayField'],
			root: 'data'
		});
		SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
			brhStore.loadData(Ext.decode(ret));
		});
		 var manufacturer = new Ext.form.ComboBox({
				fieldLabel: '终端厂商',
				name: 'manufacturer',
				id: 'manufacturer',
				store: manufacturerStore,
				displayField: 'displayField',
				valueField: 'valueField',
				blankText: '终端厂商不能为空'
		});
		var terminalType = new Ext.form.ComboBox({
				fieldLabel: '终端型号',
				id: 'terminalType',
				name: 'terminalType',
				store: terminalTypeStore,
				displayField: 'displayField',
				valueField: 'valueField'
		});
		var number = new Ext.form.TextField({
			id: 'number',
			name : 'number',
			width: 200,
			allowBlank : false,
			blankText : '请输入下发数量',
			fieldLabel: '下发数量*'
		});
		var termType = new Ext.form.ComboBox({
				fieldLabel: '终端类型*',
				name: 'termType',
				id: 'termType',
				store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['P','POS'],['E','EPOS']]
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				emptyText: '请选择终端类型',
				blankText: '终端类型不能为空'
		});
		var brhid = new Ext.form.ComboBox({
				store: brhStore,
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'brhId',
				emptyText: '请选择机构',
				fieldLabel: '下发机构*',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true,
				allowBlank: false,
				blankText: '请选择一个机构'
        });
		var value = Ext.getCmp('manufacturer').getValue();
		var uploadForm = new Ext.form.FormPanel({
			frame: true,
			layout: 'column',
			items: [{
				columnWidth: .4,
				layout: 'form',
				items: [termType]
			},{
				columnWidth: .4,
				layout: 'form',
				items: [manufacturer]
			},{
				columnWidth: .4,
				layout: 'form',
				items: [terminalType]
			},{
				columnWidth: .4,
				layout: 'form',
				items: [brhid]
			},{
				columnWidth: .4,
				layout: 'form',
				items: [number]
			}],
			buttons : [{
                text : '下发终端',
                iconCls : 'download',
                handler : function() {
                    if (uploadForm.form.isValid()) {
                        uploadForm.getForm().submit({
                       	 url : 'T30302Action.asp',
                       	 waitMsg: '正在下发终端，请稍后......',
	                     params: {
                        	txnId: '30302',
	 						subTxnId: '01'
	 					 },
                            success : function(form, action) {
	 						 	uploadForm.getForm().reset();
								showSuccessMsg(action.result.msg,uploadForm);
                            },
                            failure : function(form, action) {
                            	showErrorMsg(action.result.msg,uploadForm);
                            }
                        });
                    }
                }
            },{
                text : '重填',
                handler : function() {
            		uploadForm.getForm().reset();
                }
            }]
		});
		 var mainPanel = new Ext.Panel({
				title: '终端下发',
				frame: true,
				borde: true,
				autoHeight: true,
				renderTo: Ext.getBody(),
				items: [uploadForm]
		 });
});   