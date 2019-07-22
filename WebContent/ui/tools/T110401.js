Ext.onReady(function() {
	var addMhForm = new Ext.FormPanel({
		title: '新增工具检修',
		region: 'center',
		iconCls: 'T20100',
		frame: true,
		renderTo: Ext.getBody(),
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'left',
		fileUpload: true,
		enctype:'multipart/form-data',
        items:[{
        	layout:'column',
            items: [{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '工具名称',
					methodName: 'getToolCode',
					hiddenName: 'toolCode',
					blankText: '请选择工具名称',
					emptyText: "--请选择工具名称--",
					allowBlank: false,
					editable: false,
					width:300
	        	}]
			},{
				columnWidth: .5,
				xtype: 'panel',
				layout: 'form',
				items: [{
					xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '申请人*',
					width:300,
					id: 'applyUser',
					name: 'applyUser'
				}]
			},{
				columnWidth: .5,
				xtype: 'panel',
				layout: 'form',
				items: [{
					xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '申请事由*',
					width:300,
					id: 'applyMsg',
					name: 'applyMsg'
				}]
			}],
        }],
        buttons: [ {
        	text: '保存',
        	  id: 'save',
              name: 'save',
              handler : function() {
              	saveWhse();
			}
		},{
            text: '清空',
            handler: function() {
            	hasSub = false;
            	addMhForm.getForm().reset();
			}
        }]
	})
	var hasSub = false;
	function saveWhse(){
		var btn = Ext.getCmp('save');
		var frm = addMhForm.getForm();
		hasSub = true;
		if (frm.isValid()) {
			btn.disable();
			frm.submit({
				url: 'T110401Action_add.asp',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				type: 'json',
				success : function(form, action) {
					hasSub = false;
					showSuccessAlert("操作成功",addMhForm);
					btn.enable();
					frm.reset();;
				},
				failure : function(form,action) {
					console.log(action);
					var code = action.result.code;
					var msg = action.result.msg;
					if(code==200){
						hasSub = false;
						showSuccessAlert("操作成功",addMhForm);
						btn.enable();
						frm.reset();;
					}else{
						btn.enable();
						hasSub = false;
						
						showErrorMsg(msg,addMhForm);
					}
				},
				params: {
					subTxnId: '00'
				}
			});
		}else{
			// 自动切换到未通过验证的tab
			var finded = true;
			frm.items.each(function(f){
	    		if(finded && !f.validate()){
	    			var tab = f.ownerCt.ownerCt.id;
	    			var tab2 = f.ownerCt.ownerCt.ownerCt.id;
	    			if(tab == 'basic' || tab == 'append'|| tab == 'settle' ){
	    				 Ext.getCmp("tab").setActiveTab(tab);
	    			}
	    			finded = false;
	    		}
	    	});
		}
	}
    var mainView = new Ext.Viewport({
		layout: 'border',
		items: [addMhForm],
		renderTo: Ext.getBody()
	});
    
});