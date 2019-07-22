Ext.onReady(function(){

	var loginForm = new Ext.form.FormPanel({
		frame: true,
		keys: [{
			key:[13],
			fn:login
		}],
		items: [{
				xtype: 'textfield',
				fieldLabel: '操作员',
				allowBlank: false,
				blankText: '请输入操作员编号',
				id: 'oprid'
//					,
//				value: 'admin'
			},{
				xtype: 'textfield',
				inputType: 'password',
				fieldLabel: '密&nbsp;&nbsp;&nbsp;码',
				allowBlank: false,
				blankText: '请输入密码',
				id: 'password'
//					,
//				value: '111111'
		}],
		buttonAlign: 'center',
		waitMsgTarget:true,
		buttons: [{
			text: '登录',
			handler: login
		},{
			text: '重置',
			handler: function() {
				loginForm.getForm().reset();
			}
		}]
	});
	
	var win = new Ext.Window({
//		title: '登录 version-20120426',
		title: '登录 ',
		layout: 'fit',
		width: 400,
		height: 150,
		closable: false,
		resizable: false,
		draggable: false,
		items: [
			loginForm
		]
	});
	
	win.show();
	
	/**
	 * 系统登录
	 */
	function login() {
		if(loginForm.getForm().isValid()) {
			loginForm.getForm().submit({
				url: 'login.asp',
				success: function(form, action) {
					window.location.href = 'redirect.asp';
				},
				failure: function(form, action) {
					showErrorMsg(action.result.msg,loginForm,function() {
						// 重置授权码
						if(action.result.code != undefined && action.result.code == '00') {
							resetPwdWin.show();
							resetPwdForm.getForm().reset();
							resetPwdForm.get('resetOprId').setValue(loginForm.get('oprid').getValue());
						}
					});
				},
				waitMsg: '登录中......'
			});
		}
	}
	
	/**
	 * 重置授权码表单
	 */
	var resetPwdForm = new Ext.form.FormPanel({
		frame: true,
		width: 300,
		autoHeight: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'textfield',
			fieldLabel: '操作员编号',
			id: 'resetOprId',
			name: 'resetOprId',
			readOnly: true
		},
		{
			xtype: 'textfield',
			fieldLabel: '原密码',
			inputType: 'password',
			regex: /^[0-9a-zA-Z]{6}$/,
			regexText: '密码必须是6位数字或字母',
			id: 'resetPassword',
			name: 'resetPassword',
			allowBlank: false,
			blankText: '原密码不能为空'
		},
		{
			xtype: 'textfield',
			fieldLabel: '新密码',
			inputType: 'password',
			regex: /^[0-9a-zA-Z]{6}$/,
			regexText: '密码必须是6位数字或字母',
			id: 'resetPassword1',
			name: 'resetPassword1',
			allowBlank: false,
			blankText: '新密码不能为空'
		},{
			xtype: 'textfield',
			fieldLabel: '重复密码',
			inputType: 'password',
			regex: /^[0-9a-zA-Z]{6}$/,
			regexText: '密码必须是6位数字或字母',
			id: 'resetPassword2',
			name: 'resetPassword2',
			allowBlank: false,
			blankText: '重复密码不能为空'
		}]
	});
	
	/**
	 * 修改授权码窗口
	 */
	var resetPwdWin = new Ext.Window({
		title: '密码修改',
		frame: true,
		width: 300,
		layout: 'fit',
		iconCls: 'key',
		items: [resetPwdForm],
		resizable: false,
		closable: true,
		closeAction: 'hide',
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: false,
		buttons: [{
			text: '提交',
			handler: function() {
				if(!resetPwdForm.getForm().isValid()) {
					return;
				}
				if(resetPwdForm.get('resetPassword').getValue() == resetPwdForm.get('resetPassword1').getValue()) {
					showAlertMsg('新密码不能和原始密码一致，请重新输入',resetPwdForm,function() {
						resetPwdForm.get('resetPassword1').setValue('');
						resetPwdForm.get('resetPassword2').setValue('');
					});
					return;
				}
				if(resetPwdForm.get('resetPassword1').getValue() != resetPwdForm.get('resetPassword2').getValue()) {
					showAlertMsg('两次输入的新密码不一致，请重新输入',resetPwdForm,function() {
						resetPwdForm.get('resetPassword1').setValue('');
						resetPwdForm.get('resetPassword2').setValue('');
					});
					return;
				}
				resetPwdForm.getForm().submit({
					url: 'resetPwd.asp',
					waitMsg: '正在提交，请稍后......',
					success: function(form, action) {
						showMsg(action.result.msg,resetPwdWin,function() {
							resetPwdWin.hide();
						});
					},
					failure: function(form, action) {
						showErrorMsg(action.result.msg,resetPwdWin);
					}
				});
			}
		},{
			text: '清空',
			handler: function() {
				resetPwdForm.getForm().reset();
				resetPwdForm.get('resetOprId').setValue(loginForm.get('oprid').getValue());
			}
		}]
	});
});