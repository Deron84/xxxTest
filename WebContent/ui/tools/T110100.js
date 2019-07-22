Ext.onReady(function() {
	var addMhForm = new Ext.FormPanel({
		title: '采购入库',
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
			        methodName: 'getToolNameLong',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '工具名称*',
					hiddenName: 'toolName',
					allowBlank: false,
					editable: false,
					width:300,
					emptyText: "--请选择名称--"
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'textnotnull',
       	            labelStyle: 'padding-left: 5px',
       	            fieldLabel: '工具标签*',
       	            maxLength: 30,
       	            id: 'rfid',
       	            name: 'rfid',
       	            width: 300,
       	       }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'textnotnull',
       	            labelStyle: 'padding-left: 5px',
       	            fieldLabel: '工具型号*',
       	            maxLength: 30,
       	            id: 'note2',
       	            name: 'note2',
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
			},{
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
			}*/,{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库名称*',
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
					xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '架*',
					width:300,
					maxLength: 10,
					id: 'stand',
					name: 'stand'
				}]
			},{
				columnWidth: .5,
				xtype: 'panel',
				layout: 'form',
				items: [{
					xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '层*',
					width:300,
					maxLength: 10,
					id: 'floor',
					name: 'floor'
				}]
			},{
				columnWidth: .5,
				xtype: 'panel',
				layout: 'form',
				items: [{
					xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '位*',
					width:300,
					maxLength: 10,
					id: 'position',
					name: 'position'
				}]
			},{
				columnWidth: .5,
				xtype: 'panel',
				layout: 'form',
				items: [{
					xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '材质*',
					width:300,
					maxLength: 20,
					id: 'toolMaterial',
					name: 'toolMaterial'
				}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       				xtype: 'dynamicCombo',
			        methodName: 'getToolunit',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '单位*',
					hiddenName: 'toolUnit',
					allowBlank: false,
					editable: false,
					width:300,
					emptyText: "--请选择单位--"
	        	}]
			},{
				columnWidth: .5,
				xtype: 'panel',
				layout: 'form',
				items: [{
					xtype: 'numberfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '保养周期（天）*',
					width:300,
					maxLength: 80,
					allowBlank: false,
					id: 'examPeriod',
					name: 'examPeriod'
				}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'datefield',
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					editable: false,
					fieldLabel: '有效期*',
					minValue: new Date(),
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
					fieldLabel: '采购部门',
					hiddenName: 'purchaseDept',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择采购部门--"
	        	}]
			},{
				columnWidth: .5,
				xtype: 'panel',
				layout: 'form',
				items: [{
					xtype: 'textfield',
					allowBlank: true,
					labelStyle: 'padding-left: 5px',
					fieldLabel: '采购人',
					width:300,
					maxLength: 40,
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
					fieldLabel: '供应商',
					hiddenName: 'mfrsOrg',	
					allowBlank: true,
					editable: false,
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
					editable: false,
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
						
						showErrorMsg(action.result.msg,addMhForm);
					}
				},
				params: {
					txnId: '1101',
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