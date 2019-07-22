Ext.onReady(function() {
	
	// 商户添加
	var mchntForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		labelWidth: 165,
		waitMsgTarget: true,
		labelAlign: 'left',
		layout: 'column',
		items: [{
			xtype: 'fieldset',
			title: '商户基本信息',
			layout: 'column',
			width: 780,
			bodyStyle: 'margin-top: 10px',
			items: [{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 389,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户编号*',
						readOnly: true,
						id: 'mchntId',
						value: mchntInfo['id']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 389,
					items: [{
			        	xtype: 'combo',
						fieldLabel: '所在地区*',
						labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: cityStoreData,
							reader: new Ext.data.ArrayReader()
						}),
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						lazyRender: true,
						width: 100,
						readOnly: true,
						value: mchntInfo['mchntCity']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '中文名称*',
						readOnly: true,
						value: mchntInfo['mchntCnName']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '英文名称*',
						readOnly: true,
						value: mchntInfo['mchntEnName']
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户组别*',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: mchntGrpStoreData,
							reader: new Ext.data.ArrayReader()
						}),
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						lazyRender: true,
						width: 400,
						readOnly: true,
						value: mchntInfo['mchntGrp']
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户MCC*',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: mchntMccStoreData,
							reader: new Ext.data.ArrayReader()
						}),
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						lazyRender: true,
						width: 400,
						readOnly: true,
						value: mchntInfo['mcc']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照编号*',
						readOnly: true,
						value: mchntInfo['certificate']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户性质*',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: mchntAttrStoreData,
							reader: new Ext.data.ArrayReader()
						}),
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						lazyRender: true,
						readOnly: true,
						value: mchntInfo['businessAttr']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户地址*',
						readOnly: true,
						value: mchntInfo['addr']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '邮政编码*',
						readOnly: true,
						value: mchntInfo['postCode']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'EMAIL*',
						readOnly: true,
						value: mchntInfo['email']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表*',
						readOnly: true,
						value: mchntInfo['legalName']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px;width: 147px',
						fieldLabel: '法人代表证件类型*',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: certificateStoreData,
							reader: new Ext.data.ArrayReader()
						}),
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						lazyRender: true,
						readOnly: true,
						value: mchntInfo['legalCertiType']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px;width: 147px',
						fieldLabel: '法人代表证件号码*',
						readOnly: true,
						value: mchntInfo['legalCertiId']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人姓名*',
						readOnly: true,
						value: mchntInfo['contactName']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人电话*',
						readOnly: true,
						value: mchntInfo['contactTel']
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收单机构*',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: aquiredStoreData,
							reader: new Ext.data.ArrayReader()
						}),
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						lazyRender: true,
						readOnly: true,
						value: mchntInfo['acqBrhId']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '夏季开门时间*',
						readOnly: true,
						value: mchntInfo['summerOpenTime']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '夏季关门时间*',
						readOnly: true,
						value: mchntInfo['summerCloseTime']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '冬季开门时间*',
						readOnly: true,
						value: mchntInfo['winterOpenTime']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '冬季关门时间*',
						readOnly: true,
						value: mchntInfo['winterCloseTime']
		        	}]
	        	}]
			}]
		},{
			xtype: 'fieldset',
			bodyStyle: 'margin-top: 10px;padding-top: 10px',
			title: '商户清算信息',
			layout: 'column',
			width: 780,
			items: [{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 389,
					items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '手续费类型*',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','按比例'],['1','固定金额']],
							reader: new Ext.data.ArrayReader()
						}),
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: false,
						readOnly: true,
						value: mchntInfo['feeType']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 389,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '固定金额（元）',
						readOnly: true,
						value: mchntInfo['feeAmt']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '比例（%）',
						readOnly: true,
						value: mchntInfo['feePercent']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 378,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '封顶金额（元）',
						readOnly: true,
						value: mchntInfo['feeLimit']
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '结算账户*',
						width: 200,
						readOnly: true,
						value: mchntInfo['settleAccount']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行编号*',
						readOnly: true,
						value: mchntInfo['accountBankId']
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
		        	width: 700,
					items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行名称*',
						readOnly: true,
						value: mchntInfo['accountBankNm']
		        	}]
	        	}]
			}]
		},{
			xtype: 'fieldset',
			layout: 'form',
			width: 780,
			title: '分期信息',
			html: mchntDivInfo
		},{
			xtype: 'fieldset',
			title: '证书影像',
			layout: 'form',
			width: 780,
			items: [{
				xtype: 'panel',
				id: 'picPanel',
				width: 170,
				height: 150,
				listeners: {
					'render': function() {
						Ext.getCmp('picPanel').html = '<center><div id="pic" onmouseover="this.style.cursor=\'hand\'" title="点击查看大图片">' +
						'<img style="width: 120px;heigth: 90px;" src="' + Ext.contextPath + 
							'/PrintImage?fileName=' + Ext.getCmp('mchntId').getValue() + '.jpg&width=120&height=90"/>';
					}
				}
			}]
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '关闭窗口',
			handler: function() {
				window.close();
			}
		}],
		renderTo: Ext.getBody()
	});
	
	document.getElementById('pic').onclick = function() {
		picWin.show();
		document.getElementById('bigPic').innerHTML = '<img style="width: 400px;heigth: 400px;" src="' + Ext.contextPath + 
							'/PrintImage?fileName=' + Ext.getCmp('mchntId').getValue() + '.jpg&width=400&height=400"/>';
	}
	
	var picWin = new Ext.Window({
		title: '图片预览',
		layout: 'fit',
		animateTarget: 'picPanel',
		initHidden: true,
		header: true,
		frame: true,
		closable: true,
		modal: true,
		draggable: false,
		width: 415,
		height: 430,
		layout: 'fit',
		closeAction: 'hide',
		resizable: false,
		html: '<center><div id="bigPic"><img style="width: 400px;heigth: 400px;"/></div></center>'
	});
	
})