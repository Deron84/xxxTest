Ext.onReady(function() {	
	function init(){
		T80205.init(function(retVal){
			for(var key in retVal){
				if(key == 'makefile' || key == 'download' || key == 'pdf' || key == 'upload' || key == 'exec'){
					if(retVal[key] == 'F'){
						Ext.getCmp(key).enable();
					}else{
						Ext.getCmp(key).disable();
					}
				}else{
					queryForm.getForm().findField(key).setValue(retVal[key]);
				}
			}
		});
	}
	init();
	
	var task = {
		run: function() {
			init();
		},
		interval: 8000
	};
	
	// 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T80205Action_upload.asp',
		filePostName : 'files',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB', 
		fileTypes : '',
		fileTypesDescription : '文件',
		title: '上传商户代发结果信息',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		postParams: {
			txnId: '80205',
			subTxnId: '01'
		}
	});
	
	var queryForm = new Ext.form.FormPanel({
		title: '商户入账',
		frame: true,
		border: true,
		width: 520,
		autoHeight: true,
		iconCls: 'T80205',
		waitMsgTarget: true,
		renderTo: 'reportPanel',
		defaults: {
			width: 180,
			labelStyle: 'padding-left: 35px'
		},
		items: [{
	        fieldLabel: '清算机构',
	        xtype: 'displayfield',
			name: 'brhName'
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
        	width: 520,
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
        	width: 520,
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
        	width: 520,
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
        	width: 520,
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
			text: '生成代发文件',
			iconCls: 'accept',
			id: 'makefile',
			disabled: true,
			handler: function(bt) {
				bt.disable();
				queryForm.getForm().submit({
					url: 'T80205Action_makefile.asp',
					waitMsg: '正在发送请求，请稍等......',
					success: function(form,action) {
						setTimeout(function(){Ext.TaskMgr.start(task);},5000);
						showSuccessAlert(action.result.msg,queryForm);
					},
					failure: function(form,action) {
						setTimeout(function(){Ext.TaskMgr.start(task);},5000);
						showAlertMsg(action.result.msg,queryForm);
					},
					params: {
						txnId: '80205',
						subTxnId: '05'
					}
				});
			}
		},{
			text: '下载代发文件',
			iconCls: 'download',
			id: 'download',
			disabled: true,
			handler: function(bt) {
				bt.disable();
				queryForm.getForm().submit({
					url: 'T80205Action_download.asp',
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
			text: '生成手动凭证',
			iconCls: 'icon_download',
			id: 'pdf',
			disabled: true,
			handler: function(bt) {
				bt.disable();
				queryForm.getForm().submit({
					url: 'T80205S1Action.asp',
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
			text: '上传入账结果',
			iconCls: 'upload',
			id: 'upload',
			disabled: true,
			handler: function(bt) {
				dialog.show();
			}
		},{
			text: '解析入账结果',
			iconCls: 'money_query',
			id: 'exec',
			disabled: true,
			handler: function(bt) {
				bt.disable();
				queryForm.getForm().submit({
					url: 'T80205Action_exec.asp',
					waitMsg: '正在发送请求，请稍等......',
					success: function(form,action) {
						setTimeout(function(){Ext.TaskMgr.start(task);},5000);
						showSuccessAlert(action.result.msg,queryForm);
					},
					failure: function(form,action) {
						setTimeout(function(){Ext.TaskMgr.start(task);},5000);
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		}
		]
	});
});