Ext.onReady(function(){

	function startTesting(){
		//再次验证状态
		T70104.getPaperId(function(ret){
			var status = ret.substring(0,1);
			var msg = ret.substring(1);
			if (status == "T") {
				if(!mainForm.getForm().isValid()) {
					return;
				}
				var mchtId = mainForm.getForm().findField('mchntNo').getValue();
				var iLeft = (window.screen.availWidth-10-790)/2; 

				var s = mchtId + '|' + msg;
				EncryptUtils.encrypt(s,function(ret){
					window.open(Ext.contextPath + '/page/mchtSrv/T7010401.jsp?s=' + ret, 'newwindow', 'height=600,width=790,top=0,left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes');
				})
			} else {
				showErrorMsg('问卷状态验证不成功，请刷新后重试。',mainForm);
			}
		});
	}
	
	T70104.getPaperId(function(ret){
		var status = ret.substring(0,1);
		var msg = ret.substring(1);
		
		if (status == "T") {
			Ext.getCmp('paper').setValue(msg);
			Ext.getCmp('start').enable();
		} else if (status == "E") {
			Ext.getCmp('paper').setValue('<font color="red">'+msg+'</font>');
		}
	});

	
	
	
	//Main Form
	var mainForm = new Ext.form.FormPanel({
		title: '商户评级',
		iconCls: 'T70104',
		renderTo: 'form',
		width: 540,
		height: 160,
		frame: true,
		buttonAlign: 'center',
		waitMsgTarget: true,
		defaults: {
			labelWidth: 140,
			width: 380,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype:'displayfield',
			fieldLabel: '问卷编号*',
			id: 'paper',
			name: 'paper'
		},{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号*',
			hiddenName: 'mchntNo',
			allowBlank: false,
			editable: true
		}],
		buttons: [{
			text: '开始评级',
			id: 'start',
			name: 'start',
			iconCls: 'download',
			disabled: true,
			handler: function(){
				startTesting();
			}
		}]
	})
})