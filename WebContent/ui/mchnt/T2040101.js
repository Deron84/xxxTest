Ext.onReady(function() {	
	var now= new Date();
	var year=now.getYear();
	var month=now.getMonth()+1;
	var day=now.getDate();
	var hour=now.getHours();
	var minute=now.getMinutes();
	var second=now.getSeconds();
//	alert(year+"-"+month+"-"+day+" "+hour+":"+":"+minute+":"+second);
	
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
			title: '商户入网信息添加',
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
						fieldLabel: '商户名称*',
							allowBlank: false,
							blankText: '请输入商户中文名称',
							maxLength: '120',
							vtype: 'isOverMax',
							id: 'mchtNm',
							name: 'mchtNm'
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
						fieldLabel: '法人证件号*',
						allowBlank: false,
						blankText: '请输入商户法人证件号',
						maxLength: '120',
						vtype: 'isOverMax',
						id: 'legalNo',
						name: 'legalNo'
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
						fieldLabel: '法人姓名*',
						allowBlank: false,
						blankText: '请输入法人姓名',
						maxLength: '120',
						vtype: 'isOverMax',
						id: 'legalNm',
						name: 'legalNm'
		        	}]
	        	}]
			}]
		},{
			xtype: 'fieldset',
			title: '法定代表人证件复印件',
			layout: 'form',
			width: 780,
			items: [{
				xtype: 'panel',
				id: 'legalUrl',
				width: 170,
				height: 150,
				html: '<center><div id="legalPic" onmouseover="this.style.cursor=\'hand\'" title="点击查看大图片">' +
						'<img style="width: 120px;heigth: 90px;" src="' + Ext.contextPath + 
							'/ext/resources/images/nopic.gif"/></div></center>',
				buttons: [{
					text: '浏览',
					id: 'legalView'
				},{
					text: '上传',
					handler: function() {
						if(Ext.getCmp('legalNo').getValue().length == 0) {
							showAlertMsg('请确定法人证件号完整，该编号将作为法定代表人证件复印件关联信息',Ext.getCmp('legalView'));
							return;
						}
						if(legalUploadButton == undefined || legalUploadButton.fileSequence() == 0) {
							showAlertMsg('您还没有选择要上传的文件',Ext.getCmp('legalView'));
							return;
						}
						
						var postParams = {
							fileName: Ext.getCmp('legalNo').getValue()+year+month+day+hour+minute+second+'_LEGAL',
							fileType: 'jpg'
						}
						legalUploadButton.setPostParams(postParams);
						legalUploadButton.start();
					}
				}]
			}]
		},{
			xtype: 'fieldset',
			title: '营业执照复印件',
			layout: 'form',
			width: 780,
			items: [{
				xtype: 'panel',
				id: 'certUrl',
				width: 170,
				height: 150,
				html: '<center><div id="certPic" onmouseover="this.style.cursor=\'hand\'" title="点击查看大图片">' +
						'<img style="width: 120px;heigth: 90px;" src="' + Ext.contextPath + 
							'/ext/resources/images/nopic.gif"/></div></center>',
				buttons: [{
					text: '浏览',
					id: 'certView'
				},{
					text: '上传',
					handler: function() {
						if(Ext.getCmp('legalNo').getValue().length = 0) {
							showAlertMsg('请确定法人证件号信息完整，该编号将作为营业执照复印件关联信息',Ext.getCmp('certView'));
							return;
						}
						if(certUploadButton == undefined || certUploadButton.fileSequence() == 0) {
							showAlertMsg('您还没有选择要上传的文件',Ext.getCmp('certView'));
							return;
						}
						var postParams = {
							fileName: Ext.getCmp('legalNo').getValue()+year+month+day+hour+minute+second+'_CERT',
							fileType: 'jpg'
						}
						certUploadButton.setPostParams(postParams);
						certUploadButton.start();
					}
				}]
			}]
		},{
			xtype: 'fieldset',
			title: '组织机构代码证复印件',
			layout: 'form',
			width: 780,
			items: [{
				xtype: 'panel',
				id: 'orgUrl',
				width: 170,
				height: 150,
				html: '<center><div id="orgPic" onmouseover="this.style.cursor=\'hand\'" title="点击查看大图片">' +
						'<img style="width: 120px;heigth: 90px;" src="' + Ext.contextPath + 
							'/ext/resources/images/nopic.gif"/></div></center>',
				buttons: [{
					text: '浏览',
					id: 'orgView'
				},{
					text: '上传',
					handler: function() {
						if(Ext.getCmp('legalNo').getValue().length == 0) {
							showAlertMsg('请确定法人证件号完整，该编号将作为法定代表人证件复印件关联信息',Ext.getCmp('orgView'));
							return;
						}
						if(orgUploadButton == undefined || orgUploadButton.fileSequence() == 0) {
							showAlertMsg('您还没有选择要上传的文件',Ext.getCmp('orgView'));
							return;
						}
						var postParams = {
							fileName: Ext.getCmp('legalNo').getValue()+year+month+day+hour+minute+second+'_ORG',
							fileType: 'jpg'
						}
						orgUploadButton.setPostParams(postParams);
						orgUploadButton.start();
					}
				}]
			}]
		},{
			xtype: 'fieldset',
			title: '税务登记证复印件',
			layout: 'form',
			width: 780,
			items: [{
				xtype: 'panel',
				id: 'taxUrl',
				width: 170,
				height: 150,
				html: '<center><div id="taxPic" onmouseover="this.style.cursor=\'hand\'" title="点击查看大图片">' +
						'<img style="width: 120px;heigth: 90px;" src="' + Ext.contextPath + 
							'/ext/resources/images/nopic.gif"/></div></center>',
				buttons: [{
					text: '浏览',
					id: 'taxView'
				},{
					text: '上传',
					handler: function() {
						if(Ext.getCmp('legalNo').getValue().length = 0) {
							showAlertMsg('请确定法人证件号信息完整，该编号将作为营业执照复印件关联信息',Ext.getCmp('taxView'));
							return;
						}
						if(taxUploadButton == undefined || taxUploadButton.fileSequence() == 0) {
							showAlertMsg('您还没有选择要上传的文件',Ext.getCmp('taxView'));
							return;
						}
						var postParams = {
							fileName: Ext.getCmp('legalNo').getValue()+year+month+day+hour+minute+second+'_TAX',
							fileType: 'jpg'
						}
						taxUploadButton.setPostParams(postParams);
						taxUploadButton.start();
					}
				}]
			}]
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '提交商户信息',
			handler: function() {
				if(mchntForm.getForm().isValid()) {
					mchntForm.getForm().submit({
						url: 'T20401Action.asp?method=add',
						waitMsg: '正在提交商户信息，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,mchntForm);
							//重置表单
							mchntForm.getForm().reset();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,mchntForm);
						},
						params: {
							txnId: '20401',
							subTxnId: '01',
							mchntDivNoArr: mchntDivArr.toString(),
							productDivArr: productDivArr.toString()
						}
					});
				}
			}
		},{
			text: '关闭窗口',
			handler: function() {
				window.close();
			}
		}],
		renderTo: Ext.getBody()
	});
		
	var certUploadButton = new UploadButton('uploadFile.asp','file','certView','*.jpg','图片文件',function() {
		document.getElementById('certPic').innerHTML = '<img style="width: 120px;heigth: 90px;" src="' + Ext.contextPath + 
							'/PrintImage?fileName=' + Ext.getCmp('legalNo').getValue()+year+month+day+hour+minute+second+'_CERT' + '.jpg&width=120&height=90"/>';
	})
	
	document.getElementById('certPic').onclick = function() {
		picWin.show();
		document.getElementById('bigPic').innerHTML = '<img style="width: 400px;heigth: 400px;" src="' + Ext.contextPath + 
							'/PrintImage?fileName=' + Ext.getCmp('legalNo').getValue() + year+month+day+hour+minute+second+'_CERT' +'.jpg&width=400&height=400"/>';
	}
	
	var legalUploadButton = new UploadButton('uploadFile.asp','file','legalView','*.jpg','图片文件',function() {
		document.getElementById('legalPic').innerHTML = '<img style="width: 120px;heigth: 90px;" src="' + Ext.contextPath + 
							'/PrintImage?fileName=' + Ext.getCmp('legalNo').getValue()+year+month+day+hour+minute+second+'_LEGAL' + '.jpg&width=120&height=90"/>';
	})
	
	document.getElementById('legalPic').onclick = function() {
		picWin.show();
		document.getElementById('bigPic').innerHTML = '<img style="width: 400px;heigth: 400px;" src="' + Ext.contextPath + 
							'/PrintImage?fileName=' + Ext.getCmp('legalNo').getValue() + year+month+day+hour+minute+second+'_LEGAL' +'.jpg&width=400&height=400"/>';
	}
	
	
	var orgUploadButton = new UploadButton('uploadFile.asp','file','orgView','*.jpg','图片文件',function() {
		document.getElementById('orgPic').innerHTML = '<img style="width: 120px;heigth: 90px;" src="' + Ext.contextPath + 
							'/PrintImage?fileName=' + Ext.getCmp('legalNo').getValue()+year+month+day+hour+minute+second+'_ORG' + '.jpg&width=120&height=90"/>';
	})
	
	document.getElementById('orgPic').onclick = function() {
		picWin.show();
		document.getElementById('bigPic').innerHTML = '<img style="width: 400px;heigth: 400px;" src="' + Ext.contextPath + 
							'/PrintImage?fileName=' + Ext.getCmp('legalNo').getValue() + year+month+day+hour+minute+second+'_ORG' +'.jpg&width=400&height=400"/>';
	}
	
	var taxUploadButton = new UploadButton('uploadFile.asp','file','taxView','*.jpg','图片文件',function() {
		document.getElementById('taxPic').innerHTML = '<img style="width: 120px;heigth: 90px;" src="' + Ext.contextPath + 
							'/PrintImage?fileName=' + Ext.getCmp('legalNo').getValue()+year+month+day+hour+minute+second+'_TAX' + '.jpg&width=120&height=90"/>';
	})
	
	document.getElementById('taxPic').onclick = function() {
		picWin.show();
		document.getElementById('bigPic').innerHTML = '<img style="width: 400px;heigth: 400px;" src="' + Ext.contextPath + 
							'/PrintImage?fileName=' + Ext.getCmp('legalNo').getValue() + year+month+day+hour+minute+second+'_TAX' +'.jpg&width=400&height=400"/>';
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