Ext.onReady(function() {
	var addMhForm = new Ext.FormPanel({
		title: '新增工具信息',
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
       	            xtype: 'textnotnull',
       	            labelStyle: 'padding-left: 5px',
       	            fieldLabel: '工具编码*',
       	            maxLength: 20,
       	            id: 'toolCode',
       	            name: 'toolCode',
       	            width: 300,
       	       }]
			}/*,{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '工具名称',
					methodName: 'getToolName',
					hiddenName: 'toolName',
					blankText: '请选择工具名称',
					emptyText: "--请选择工具名称--",
					allowBlank: false,
					editable: false,
					width:300
	        	}]
			}*/,{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '工具型号',
					methodName: 'getToolModel',
					hiddenName: 'modelCode',
					blankText: '请选择工具型号',
					emptyText: "--请选择工具型号--",
					allowBlank: false,
					editable: false,
					width:300
	        	}]
			},{
            	columnWidth : 0.5,
            	layout : 'form',
    			style: 'padding-left: 5px',
    			enctype:'multipart/form-data',
    			fileUpload: true,
    			items :[{
    				xtype: 'fileuploadfield',
    				fieldLabel: '工具图片',
    				buttonText:'浏览',
    				width:'220',
    				style: 'padding-left: 5px',
    				id: 'upload',   
    			    name: 'upload'
    			}]
            },{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库编码',
					methodName: 'getRailWhse',
					hiddenName: 'whseCode',
					blankText: '请选择仓库',
					emptyText: "--请选择仓库--",
					allowBlank: false,
					editable: false,
					width:300
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'datefield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '工具有效期*',
					id: 'toolExpiration1',
					name: 'toolExpiration1',
					width:300,
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       				xtype: 'dynamicCombo',
			        methodName: 'getBranchId12',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '采购部门*',
					hiddenName: 'purchaseDept',
					allowBlank: false,
					editable: true,
					width:300,
					emptyText: "--请选择采购部门--"
	        	}]
			},{
				columnWidth: .5,
				xtype: 'panel',
				layout: 'form',
				items: [{
					xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '采购人*',
					width:300,
					id: 'purchaseUser',
					name: 'purchaseUser'
				}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       				xtype: 'dynamicCombo',
			        methodName: 'getOrgName',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '供应商*',
					hiddenName: 'mfrsOrg',	
					allowBlank: false,
					editable: true,
					width:300,
					emptyText: "--请选择供应商--"
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       				xtype: 'dynamicCombo',
			        methodName: 'getBranchId12',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '所属机构*',
					hiddenName: 'dept',
					allowBlank: false,
					editable: true,
					width:300,
					emptyText: "--请选择所属机构--"
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
				url: 'T110100Action.asp?method=add',
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
					var code = action.result.code;
					if(code==200){
						hasSub = false;
						showSuccessAlert("操作成功",addMhForm);
						btn.enable();
						frm.reset();;
					}else{
						btn.enable();
						hasSub = false;
						
						showErrorMsg("操作失败，请稍后重试",addMhForm);
					}
				},
				params: {
					txnId: '20110',
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