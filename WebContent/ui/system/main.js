Ext.onReady(function() {

 	var task = {
 		run : function() {
 			Ext.Ajax.request({
				url : 'T130101Action.asp?method=warningInfos',
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success){
						var msgs = rspObj.msg;
//						alert(msgs);
						if(msgs!=""){
							for(var i = 0;i<msgs.length;i++){
								new Ext.ux.ToastWindow({  
									title: '警报',  
									html: '<a onclick="urlRedirect('+msgs[i].warningType+')" style="color:red;cursor:pointer;">有'+msgs[i].warningNum+'条'+msgs[i].warningName+'未处理，请尽快处理！</a>',  
									iconCls: 'warning'
								}).show(document);
							}
						}
					}
				}
			});
 		},
 		interval : 30000,
	};
	Ext.TaskMgr.start(task);

	
	
	
	
	
	var mainhref=window.location.href;
	mainhref=mainhref.replace('redirect.asp','');
	//操作员
	var oprStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('OPR_ID_BELLOW',function(ret){
		oprStore.loadData(Ext.decode(ret));
	});
	
	//菜单树
	menuTree = new Ext.tree.TreePanel({
		region: 'west',
		useArrows: true,
		autoScroll: true,
		animate: true,
		containerScroll: true,
		width: 200,
		//frame: true,
		split: true,
		//renderTo: Ext.getBody(),
		title: '<center>系统菜单</center>',
		collapsible: true,
		rootVisible: false,
		root: new Ext.tree.AsyncTreeNode({
			text: '测试',
			loader: new Ext.tree.TreeLoader({
				dataUrl: 'tree.asp?id=init'
			})
		}),
		listeners: {
			click: function(node) {
				if(node.leaf) {
					initMask.msg = '系统界面加载中，请稍后......';
					initMask.show();
					Ext.get("mainUI").dom.src = node.attributes.url;
					txnCode = node.attributes.id;
					hideToolInitMask.defer(500);
				}
			}
		}
	});
	//打开菜单树
	menuTree.getRootNode().expand(true);
	
	var timeToolItem = new Ext.Toolbar.TextItem('');
	
	// 顶部菜单
	var topToolBar = new Array(toolBarStr);
	
	// 修改操作员密码
	var resetPwdMenu = {
		text: '修改密码',
		id: 'key',
		iconCls: 'key',
		handler: function() {
			resetPwdWin.show();
			var d = new Ext.util.DelayedTask(function(){
				resetPwdForm.getForm().reset();
				resetPwdForm.get('resetOprId').setValue(operator[OPR_ID]);
			});
			d.delay(1000);
		}
	}


	var lockScreenMenu = {
		text: '锁屏',
		id: 'lock',
		iconCls: 'lock',
		handler: function() {
			lockWin.show();
			lockForm.getForm().reset();
			lockForm.get('lockOprId').setValue(operator[OPR_ID]);
		}
	}
	
	var quitMenu = {
		text: '安全退出',
		iconCls: 'quit',
		handler: function() {			
			showConfirm('确定要退出并关闭吗？',this,function(bt) {
				if(bt == 'yes') {
					Ext.Ajax.request({
						url: 'logout.asp',
						success:function(){
							var userAgent = navigator.userAgent;
							if(mainhref.indexOf("redirect.as")>0){
								mainhref=mainhref.replace('redirect.asp','');
							}
							window.location.href=mainhref;
						},
						failure:function(){
							Ext.MessageBox.show({
								msg: '操作失败，请重试！',
								title: '错误提示',
								animEl: Ext.getBody(),
								buttons: Ext.MessageBox.OK,
								icon: Ext.MessageBox.ERROR,
								modal: true,
								width: 250
							});
						}
						
					});
				/*	var userAgent = navigator.userAgent;
					if(mainhref.indexOf("redirect.as")>0){
						mainhref=mainhref.replace('redirect.asp','');
					}
					window.location.href=mainhref;*/

				/*	if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") !=-1) {

					   window.location.href=mainhref;

					} else {

					   window.opener = null;

					   window.open(mainhref, "_top");

					};*/
				}
			});
		}
	};

	var welcomeMenu = {
		text: '欢迎使用',
		iconCls: 'branch',
		handler: function() {
			//window.location.href=mainhref;
			Ext.get("mainUI").dom.src = Ext.contextPath + "/page/system/main_0.jsp";
		}
	};
	
//	window.onunload = function(){
//		Ext.Ajax.request({
//			url: 'logout.asp'
//			
//		});
//	}
//	
	
	var e = event || window.event || arguments.callee.caller.arguments[0];
	window.onbeforeunload = function(e){
		
		
	/*	var userAgent = navigator.userAgent;

		if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") !=-1) {
					Ext.Ajax.request({
						url: 'logout.asp'
						
					});	
		}else{
			
				var r=confirm('确定要退出并关闭吗？');
				if(r){
					Ext.Ajax.request({
						url: 'logout.asp'
						
					});
				}
		}*/
	}

	//下载操作手册
	var loadfile = {
			text: '操作手册下载',
			iconCls: 'quit',
			handler: function() {
				showConfirm('确定要下载吗？',this,function(bt) {
						Ext.Ajax.requestNeedAuthorise({
							url: 'TFileLoadAction_downloadreport.asp',
							success: function(form,action) {
							    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
							                                              '/posp/file/caozuoshouce/caozuoshouce.doc';
							},
							failure: function(form,action) {
								Ext.MessageBox.show({
									msg: '下载失败！',
									title: '错误提示',
									animEl: Ext.getBody(),
									buttons: Ext.MessageBox.OK,
									icon: Ext.MessageBox.ERROR,
									modal: true,
									width: 250
								});
							}
						});
				});
			}
		};
	// 重置操作员密码
	var clearPwdMenu = {
		text: '重置密码',
		id: 'otkey',
		iconCls: 'key',
		handler: function() {
			clearPwdWin.show();
			clearPwdForm.getForm().reset();
			SelectOptionsDWR.getComboData('OPR_ID_BELLOW',function(ret){
				oprStore.loadData(Ext.decode(ret));
			});
		}
	}
	
	topToolBar.add('->');
	topToolBar.add(welcomeMenu);
	topToolBar.add('-');
	topToolBar.add(resetPwdMenu);
	//if(operator[OPR_ID]=="admin"){
    	topToolBar.add('-');
        topToolBar.add(clearPwdMenu);
	//}
	topToolBar.add('-');
	topToolBar.add(quitMenu);
	topToolBar.add('-');
	//topToolBar.add(loadfile);
	
	
	//用户UI主面板
	var mainPanel = new Ext.Panel({
		frame: true,
		html: '<iframe id="mainUI" name="mainUI" width="100%" height="100%" frameborder="0" scrolling="auto"/>',
		region: 'center',
		tbar: topToolBar,
		bbar: [
//			new Ext.Toolbar.TextItem('神思电子技术股份有限公司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Synthesis Electronic Technology Co., Ltd.'),
			{
				xtype: 'tbspacer',
				width: 650
			},
			new Ext.Toolbar.TextItem('<image src="' + Ext.contextPath + '/ext/resources/images/user.png" title="操作员"/>' + ' ' + operator['opr_name']),
			'-',
			new Ext.Toolbar.TextItem('<image src="' + Ext.contextPath + '/ext/resources/images/branch.png" title="机构"/>' + ' ' + operator['opr_brh_name']),
			'-',
			new Ext.Toolbar.TextItem('<image src="' + Ext.contextPath + '/ext/resources/images/time.png" title="当前时间"/>' + ' '),
			timeToolItem
		],
		listeners: {
			render: function() {
//				Ext.TaskMgr.start({
//					run: function() {
//						Ext.fly(timeToolItem.getEl()).update(new Date().pattern('yyyy-MM-dd HH:mm:ss'));
//					},
//					interval: 1000
//				});
				setInterval(function() {Ext.fly(timeToolItem.getEl()).update(new Date().pattern('yyyy-MM-dd HH:mm:ss'));},1000);
			}
		}
	});
	
	


	//用户界面
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [menuTree,mainPanel],
		renderTo: Ext.getBody()
	});
	
	
	//移除主界面初始化图层
	var hideMainUIMask = function() {
		Ext.fly('load-mask').fadeOut({
			remove: true,
			easing: 'easeOut',
    		duration: 2

		});
	}
	
	hideMainUIMask.defer(2000);
	
	/**
	 * 重置密码表单
	 */
	var clearPwdForm = new Ext.form.FormPanel({
		frame: true,
		width: 300,
		autoHeight: true,
		waitMsgTarget: true,
		labelWidth: 50,
		items: [{
			xtype: 'combo',
			fieldLabel: '操作员',
			store: oprStore,
			id: 'clearOprId',
			name: 'clearOprId',
			readOnly: false,
			editable: false
		}]
	});
	
	/**
	 * 重置密码窗口
	 */
	var clearPwdWin = new Ext.Window({
		title: '密码重置',
		frame: true,
		width: 300,
		layout: 'fit',
		iconCls: 'otkey',
		items: [clearPwdForm],
		resizable: false,
		closable: true,
		closeAction: 'hide',
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: false,
		animateTarget: 'key',
		buttons: [{
			text: '重置密码',
			handler: function() {
				if(!clearPwdForm.getForm().isValid()) {
					return;
				}
				clearPwdForm.getForm().submit({
					url: 'clearPwd.asp?resetOprId= '+clearPwdForm.get('clearOprId').getValue(),
					waitMsg: '正在处理，请稍后......',
					success: function(form, action) {
						showMsg(action.result.msg,clearPwdWin,function() {
							clearPwdWin.hide();
						});
					},
					failure: function(form, action) {
						showErrorMsg(action.result.msg,clearPwdWin);
					}
				});
			}
		},{
			text: '清空重填',
			handler: function() {
				clearPwdForm.getForm().reset();
			}
		}]
	});
	
	/**
	 * 重置密码表单
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
		},{
			xtype: 'textfield',
			fieldLabel: '原密码',
			inputType: 'password',
			regex: /^[0-9a-zA-Z]{6,16}$/,
			regexText: '密码必须是6-16位数字或字母',
			id: 'resetPassword',
			name: 'resetPassword',
			allowBlank: false,
			blankText: '原密码不能为空'
		},{
			xtype: 'textfield',
			fieldLabel: '新密码',
			inputType: 'password',
			regex: /^[0-9a-zA-Z]{6,16}$/,
			regexText: '密码必须是6-16位数字或字母',
			id: 'resetPassword1',
			name: 'resetPassword1',
			allowBlank: false,
			blankText: '新密码不能为空'
		},{
			xtype: 'textfield',
			fieldLabel: '重复密码',
			inputType: 'password',
			regex: /^[0-9a-zA-Z]{6,16}$/,
			regexText: '密码必须是6-16位数字或字母',
			id: 'resetPassword2',
			name: 'resetPassword2',
			allowBlank: false,
			blankText: '重复密码不能为空'
		}]
	});
	
	/**
	 * 修改密码窗口
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
		animateTarget: 'key',
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
						Ext.Ajax.request({
							url: 'logout.asp'
						});
						window.location.href="";
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
				resetPwdForm.get('resetOprId').setValue(operator[OPR_ID]);
			}
		}]
	});
	
	// 锁屏表单
	var lockForm = new Ext.form.FormPanel({
		frame: true,
		width: 300,
		autoHeight: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'textfield',
			fieldLabel: '操作员编号',
			id: 'lockOprId',
			name: 'lockOprId',
			readOnly: true
		},{
			xtype: 'textfield',
			fieldLabel: '密码',
			inputType: 'password',
			regex: /^[0-9a-zA-Z]{6}$/,
			regexText: '密码必须是6位数字或字母',
			id: 'lockPassword',
			name: 'lockPassword',
			allowBlank: false,
			blankText: '请输入密码'
		}]
	});
	
	// 锁屏对话框
	var lockWin = new Ext.Window({
		title: '屏幕锁定',
		frame: true,
		width: 300,
		height: 300,
		layout: 'fit',
		iconCls: 'lock',
		items: [lockForm],
		resizable: false,
		closable: false,
		closeAction: 'hide',
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: false,
		animateTarget: 'lock',
		buttons: [{
			text: '解锁',
			iconCls: 'key',
			tooltip: '解锁',
			handler: function() {
				if(!lockForm.getForm().isValid()) {
					return;
				}
				lockForm.getForm().submit({
					url: 'unlockScreen.asp',
					waitMsg: '正在验证密码，请稍后......',
					success: function(form, action) {
						lockWin.hide();
						
					},
					failure: function(form, action) {
						showErrorMsg(action.result.msg,lockWin);
					}
				});
			}
		}]
	});
	
	
	Ext.get("mainUI").dom.src = Ext.contextPath + '/page/system/main_0.jsp';
});

function lackScreenSubmit(obj,options){
	
	var form = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		height: 80,
		items: [{
			xtype: 'textfield',
			id: 'username',
			name: 'username',
			fieldLabel: '柜员号',
			maskRe: /^[0-9]+$/,
			allowBlank: false,
			blankText: '请输入柜员号'
		},{
			xtype: 'textfield',
			inputType: 'password',
			id: 'pass',
			name: 'pass',
			fieldLabel: '密码',
			allowBlank: false,
			blankText: '请输入柜员密码'
		}]
	});
	
	
	var win = new Ext.Window({
		title: '统一授权系统',
		frame: true,
		width: 300,
		height: 140,
		layout: 'fit',
		iconCls: 'logo',
		items: [form],
		resizable: false,
		closable: true,
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: true,
		animateTarget: 'lock',
		buttons: [{
			text:'确定',
			handler: function(bt){
				var frm = form.getForm();
				if(frm.isValid()) {
					frm.submit({
						url: 'AuthoriseAction.asp',
						waitTitle : '请稍候',
						waitMsg : '正在验证授权信息,请稍候...',
						success : function(form, action) {
							frm.reset();
							win.close();
							obj.submit(options);
						},
						failure : function(form,action) {
							frm.reset();
							showErrorMsg(action.result.msg,obj);
						},
						params: {
							txnCode: txnCode
						}
					});
				}
			}
		}]
	})
	win.show();
}


function lackScreenRequest(obj,options){
	
	
	var form = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		height: 80,
		items: [{
			xtype: 'textfield',
			id: 'username',
			name: 'username',
			fieldLabel: '柜员号',
			maskRe: /^[0-9]+$/,
			allowBlank: false,
			blankText: '请输入柜员号'
		},{
			xtype: 'textfield',
			inputType: 'password',
			id: 'pass',
			name: 'pass',
			fieldLabel: '密码',
			allowBlank: false,
			blankText: '请输入柜员密码'
		}]
	});
	
	
	var win = new Ext.Window({
		title: '统一授权系统',
		frame: true,
		width: 300,
		height: 140,
		layout: 'fit',
		iconCls: 'logo',
		items: [form],
		resizable: false,
		closable: true,
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: true,
		animateTarget: 'lock',
		buttons: [{
			text:'确定',
			handler: function(bt){
				var frm = form.getForm();
				if(frm.isValid()) {
					frm.submit({
						url: 'AuthoriseAction.asp',
						waitTitle : '请稍候',
						waitMsg : '正在验证授权信息,请稍候...',
						success : function(form, action) {
							frm.reset();
							win.close();
							obj.request(options);
						},
						failure : function(form,action) {
							frm.reset();
							Ext.MessageBox.show({
								msg: action.result.msg,
								title: '错误提示',
								animEl: Ext.getBody(),
								buttons: Ext.MessageBox.OK,
								icon: Ext.MessageBox.ERROR,
								modal: true,
								width: 250
							});
						},
						params: {
							txnCode: txnCode
						}
					});
				}
			}
		}]
	})
	win.show();
}
