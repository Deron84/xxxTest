Ext.onReady(function(){
	
	
	function init(brhId){
		T80205.getStatus(brhId, function(retVal){
			queryForm.getForm().reset();
			queryForm.getForm().findField('brhId').setValue(brhId)
			for(var key in retVal){
				if (key == 'reset') {
					if (retVal[key] == 'T'){
						Ext.getCmp(key).enable();
					} else {
						Ext.getCmp(key).disable();							
					}							
				} else {
					queryForm.getForm().findField(key).setValue(retVal[key]);
				}
			}
		});
	}
	
	
	var queryForm = new Ext.form.FormPanel({
		title: '重置商户入账',
		frame: true,
		border: true,
		width: 480,
		autoHeight: true,
		iconCls: 'T80210',
		waitMsgTarget: true,
		renderTo: 'reportPanel',
		defaults: {
			width: 180,
			labelStyle: 'padding-left: 35px'
		},
		items: [{
			fieldLabel: '功能说明',
			xtype: 'displayfield',
			width: 300,
			value: '<font color="red">本交易适用于商户入账时发生异常需要重置为初始状态，由总行柜员(强制)完成该操作；该交易需授权。</font>'
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '清算机构*',
			hiddenName: 'brhId',
			allowBlank: false,
			listeners: {
		      'select': function() {
			     var brhId = queryForm.getForm().findField('brhId').getValue();
			     init(brhId)
	        }}
		},{
	        fieldLabel: '代发日期',
	        xtype: 'displayfield',
			name: 'today'
        },{
	        fieldLabel: '清算起始日期',
	        xtype: 'displayfield',
			name: 'startDate'
        },{
	        fieldLabel: '清算终止日期',
	        xtype: 'displayfield',
			name: 'endDate'
        },{
        	type: 'panel',
        	width: 480,
        	layout: 'column',
        	items: [{
        		columnWidth: 0.5,
        		layout: 'form',
        		items: [{
					labelStyle: 'padding-left: 35px',
        			fieldLabel: '清算金额(自动)',
	        		xtype: 'displayfield',
					name: 'SETTLE_AMT'
				}]
        	},{
        		columnWidth: 0.5,
        		layout: 'form',
        		items: [{
					labelStyle: 'padding-left: 35px;color:blue',
        			fieldLabel: '清算金额(手动)',
		        	xtype: 'displayfield',
					name: 'SETTLE_AMT_DIS'
        		}]
        	}]
        },{
        	type: 'panel',
        	width: 480,
        	layout: 'column',
        	items: [{
        		columnWidth: 0.5,
        		layout: 'form',
        		items: [{
					labelStyle: 'padding-left: 35px',
        			fieldLabel: '交易金额(自动)',
	        		xtype: 'displayfield',
					name: 'TXN_AMT'
				}]
        	},{
        		columnWidth: 0.5,
        		layout: 'form',
        		items: [{
					labelStyle: 'padding-left: 35px;color:blue',
        			fieldLabel: '交易金额(手动)',
		        	xtype: 'displayfield',
					name: 'TXN_AMT_DIS'
        		}]
        	}]
        },{
        	type: 'panel',
        	width: 480,
        	layout: 'column',
        	items: [{
        		columnWidth: 0.5,
        		layout: 'form',
        		items: [{
					labelStyle: 'padding-left: 35px',
        			fieldLabel: '手续费(自动)',
	        		xtype: 'displayfield',
					name: 'SETTLE_FEE1'
				}]
        	},{
        		columnWidth: 0.5,
        		layout: 'form',
        		items: [{
					labelStyle: 'padding-left: 35px;color:blue',
        			fieldLabel: '手续费(手动)',
		        	xtype: 'displayfield',
					name: 'SETTLE_FEE1_DIS'
        		}]
        	}]
        },{
        	type: 'panel',
        	width: 480,
        	layout: 'column',
        	items: [{
        		columnWidth: 0.5,
        		layout: 'form',
        		items: [{
					labelStyle: 'padding-left: 35px',
        			fieldLabel: '商户数量(自动)',
	        		xtype: 'displayfield',
					name: 'MCHNT_COUNT'
				}]
        	},{
        		columnWidth: 0.5,
        		layout: 'form',
        		items: [{
					labelStyle: 'padding-left: 35px;color:blue',
        			fieldLabel: '商户数量(手动)',
		        	xtype: 'displayfield',
					name: 'MCHNT_COUNT_DIS'
        		}]
        	}]
        },{
	        fieldLabel: '代发文件状态',
	        xtype: 'displayfield',
			name: 'fileState'
        },{
	        fieldLabel: '代发成功文件',
	        xtype: 'displayfield',
			name: 'succFile'
        },{
	        fieldLabel: '代发失败文件',
	        xtype: 'displayfield',
			name: 'failFile'
        },{
	        fieldLabel: '解析结果状态',
	        xtype: 'displayfield',
			name: 'resultFlag'
        },{
	        fieldLabel: '失败状态说明',
	        xtype: 'displayfield',
			name: 'resultState'
        },{
			xtype: 'panel',
			html: '<br/><br/>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '获取入账状态',
			iconCls: 'accept',
			id: 'makefile',
			handler: function(bt) {
				var brhId = queryForm.getForm().findField('brhId').getValue();
				if (brhId == '') {
					showAlertMsg('请选择清算机构。', queryForm);
					return;
				}
				init(brhId);
			}
		},{
			text: '重置商户入账',
			iconCls: 'T80210',
			id: 'reset',
			disabled: true,
			handler: function(bt) {
				
				var brhId = queryForm.getForm().findField('brhId').getValue();
				if (brhId == '') {
					showAlertMsg('请选择清算机构。', queryForm);
					return;
				}
				
				showConfirm('确定要重置该机构的商户入账吗？',queryForm,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T80205Action_reset.asp',
							success : function(form, action) {
								var rspObj = Ext.util.JSON.decode(form.responseText);
								if(rspObj.success){
									showSuccessMsg(rspObj.msg,queryForm);
								}else{
									showErrorMsg(rspObj.msg,queryForm);
								}
								init(brhId);
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,queryForm);
								init(brhId);
							},
							params: { 
								brhId: brhId,
								txnId: '80210',
								subTxnId: '06'
							}
						});
					}
				});
			}
		}
		]
	});
})