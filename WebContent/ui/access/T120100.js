Ext.onReady(function() {
	var addMhForm = new Ext.FormPanel({
		title: '新增门禁信息',
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
       	            fieldLabel: '门禁编码*',
       	            maxLength: 20,
       	            id: 'accessCode',
       	            name: 'accessCode',
       	            width: 300,
       	       }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '门禁名称*',
					id: 'accessName',
					name: 'accessName',
					width:300,
	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{//0通道门，1库房门
	   				xtype: 'basecomboselect',
//	   				store:maintenceTypeStore,
	   				baseParams : 'accessTypeStore',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '门禁类型*',
					hiddenName: 'acceccType',
					allowBlank: false,
					editable: false,
					width:300,
					emptyText: "--请选择门禁类型--",
					select:function(combo, record, index){
						if(combo==0){
					    Ext.getCmp('whseCode1').setVisible(false);
					    Ext.getCmp('mileage1').setVisible(true);
					    Ext.getCmp('mileagePrevious1').setVisible(true);
					    Ext.getCmp('mileageNext1').setVisible(true);
						}else if(combo==1){
						Ext.getCmp('whseCode1').setVisible(true);
						Ext.getCmp('mileage1').setVisible(false);
					    Ext.getCmp('mileagePrevious1').setVisible(false);
					    Ext.getCmp('mileageNext1').setVisible(false);
						}
					}
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '线路名称*',
					width:300,
					id: 'accessRoute',
					name: 'accessRoute'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '门禁位置*',
					width:300,
					id: 'accessAddress',
					name: 'accessAddress'
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
					hiddenName: 'accessDept',
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
					fieldLabel: '门禁负责人*',
					width:300,
					id: 'accessPic',
					name: 'accessPic'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '所属派出所',
					width:300,
					id: 'policeOffice',
					name: 'policeOffice',
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '检查周期（天）*',
					width:300,
					id: 'examPeriod',
					name: 'examPeriod'
	        	}]
			},{
			columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
				xtype: 'datefield',
				width: 300,
				labelStyle: 'padding-left: 5px',
				id: 'lastExam',
				name: 'lastExam',
				format: 'Y-m-d',
				altFormats: 'Y-m-d',
				vtype: 'daterange',
				fieldLabel: '最后一次检修时间*',
				editable: false
   			  }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	        	id: 'mileage1',
				name: 'mileage1',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '通道门里程',
					width:300,
					id: 'mileage',
					name: 'mileage',
					allowBlank: true,
					
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	        	id: 'mileagePrevious1',
				name: 'mileagePrevious1',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '与上一通道门距离',
					width:300,
					id: 'mileagePrevious',
					name: 'mileagePrevious',
					allowBlank: true,
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	        	id: 'mileageNext1',
				name: 'mileageNext1',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '与下一通道门距离',
					width:300,
					id: 'mileageNext',
					name: 'mileageNext',
					allowBlank: true,
	        	}]
			},{
			columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
				xtype: 'datefield',
				width: 300,
				labelStyle: 'padding-left: 5px',
				id: 'installDate',
				name: 'installDate',
				format: 'Y-m-d',
				altFormats: 'Y-m-d',
				vtype: 'daterange',
				fieldLabel: '安装时间*',
				editable: false
   			}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '地理经度*',
					width:300,
					id: 'longitude',
					name: 'longitude'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '地理纬度*',
					width:300,
					id: 'latitude',
					name: 'latitude'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',

	        	id: 'whseCode1',
				name: 'whseCode1',
       			items: [{
		        	xtype: 'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '仓库编码',
					methodName: 'getRailWhse',
					hiddenName: 'whseCode',
					blankText: '请选择仓库',
					emptyText: "--请选择仓库--",
					allowBlank: true,
					editable: false,
					width:300
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'radiogroup',
       	            width:300,
       	            fieldLabel: '微信预警*',
       	            labelStyle: 'padding-left: 5px',
       	            vertical: true,
       	            id: 'warnWeixin',
					name:'warnWeixin',
       	            items: [{
						boxLabel: '不预警', 
						name: 'warnWeixin', 
						inputValue: 1, 
						checked: true
					},{
						boxLabel: '预警', 
						name: 'warnWeixin', 
						inputValue: 0
					}]
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
				url: 'T120100Action.asp?method=add',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
					hasSub = false;
					showSuccessAlert(action.result.msg,addMhForm);
					btn.enable();
					frm.reset();;
				},
				failure : function(form,action) {
					btn.enable();
					hasSub = false;
					if (action.result.msg.substr(0,2) == 'CZ') {
						Ext.MessageBox.show({
							msg: action.result.msg.substr(2) + '<br><h1>是否继续保存吗？</h1>',
							title: '确认提示',
							animEl: Ext.get(addMhForm.getEl()),
							buttons: Ext.MessageBox.YESNO,
							icon: Ext.MessageBox.QUESTION,
							modal: true,
							width: 500,
							fn: function(bt) {
								if(bt == 'yes') {
									saveWhse();
								}
							}
						});
					} else {
						showErrorMsg(action.result.msg,addMhForm);
					}
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