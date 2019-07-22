Ext.onReady(function() {
	var getAllWhseStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('GET_ALL_WHSE',function(ret){
    	getAllWhseStore.loadData(Ext.decode(ret));
    });

	var addMhForm = new Ext.FormPanel({
		title: '新增仓库信息',
		region: 'center',
		iconCls: 'T20100',
		frame: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'left',
        items:[{
        	layout:'column',
            items: [{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'textnotnull',
       	            labelStyle: 'padding-left: 5px',
       	            fieldLabel: '仓库编码*',
       	            id: 'whseCode',
       	            name: 'whseCode',
       	            width: 300,
       	            maxLength: 8,
       	            minLength: 8,
    	            regexText:"只能填写数字！", 
					regex: /^[0-9]\d*$/
       	       }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库名称*',
					maxLength: 80,
					id: 'whseName',
					name: 'whseName',
					width:300,
	        	}]
			},{
				columnWidth: .5,
				xtype: 'panel',
				layout: 'form',
				items: [{
					xtype: 'basecomboselect',
//					methodName: 'getAllWhse',
					store:getAllWhseStore,
					labelStyle: 'padding-left: 5px',
					fieldLabel: '上级仓库',
					hiddenName: 'parentWhseCode',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择上级仓库--"
				}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库容量*',
					width:300,
					maxLength: 40,
					id: 'whseCapa',
					name: 'whseCapa',
					regexText:"只能填写数字！", 
				    regex: /^[0-9]\d*$/
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
					hiddenName: 'whseDept',
					allowBlank: false,
					editable: false,
					width:300,
					emptyText: "--请选择所属机构--"
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '负责人*',
					width:300,
					maxLength: 40,
					id: 'whsePic',
					name: 'whsePic'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '联系方式*',
					width:300,
					id: 'whseTel',
					name: 'whseTel',
					regexText:"请填写正确的联系方式！", 
					regex: /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|[1][3,4,5,7,8,9][0-9]{9})$/
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库地址*',
					width:300,
					maxLength: 80,
					id: 'whseAddress',
					name: 'whseAddress'
	        	}]
			}],
        }],
        buttons: [{
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
				url: 'T100100Action.asp?method=add',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
					hasSub = false;
					showSuccessMsg(action.result.msg,addMhForm);
					btn.enable();
					SelectOptionsDWR.getComboData('GET_ALL_WHSE',function(ret){
				    	getAllWhseStore.loadData(Ext.decode(ret));
				    });
					frm.reset();
//					frm.reload();
				},
				failure : function(form,action) {
					btn.enable();
					hasSub = false;
					showErrorMsg(action.result.msg,addMhForm);
				},
				params: {
					txnId: '100100',
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