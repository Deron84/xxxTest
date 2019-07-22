Ext.onReady(function() {
	var addMhForm = new Ext.FormPanel({
		title: '新增人员',
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
       	            fieldLabel: '人员编码*',
       	            maxLength: 20,
       	            id: 'employeeCode',
       	            name: 'employeeCode',
       	            width: 300,
       	            regex: /^[a-zA-Z0-9]{8}$/,
     			    regexText: '编码必须由8位数字或字母组成'
       	       }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '人员名称*',
					id: 'employeeName',
					name: 'employeeName',
					width:300,
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	        	id: 'sex',
	        	name: 'sex',
       			items: [{
       	            xtype: 'combo',
       	            width:300,
       	            fieldLabel: '性别*',
       	            hiddenName: 'sex',
					allowBlank: false,
					editable: true,
       	            labelStyle: 'padding-left: 5px',
       	            vertical: true,
       	            store: new Ext.data.ArrayStore({
     				fields: ['valueField','displayField'],
     				data: [['1','男'],['2','女']],
     				reader: new Ext.data.ArrayReader()
     			    }),
       	        }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       	            xtype: 'radiogroup',
       	            width:300,
       	            fieldLabel: '出入网状态*',
       	            labelStyle: 'padding-left: 5px',
       	            vertical: true,
       	            items: [{
						boxLabel: '未入网', 
						name: 'infoSign', 
						inputValue: 0, 
						checked: true
					},{
						boxLabel: '已入网', 
						name: 'infoSign', 
						inputValue: 1
					},{
						boxLabel: '已出网', 
						name: 'infoSign', 
						inputValue: 2
					}]
       	        }]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
					xtype: 'datefield',
					width: 300,
					labelStyle: 'padding-left: 5px',
					id: 'birthday',
					name: 'birthday',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					vtype: 'daterange',
					fieldLabel: '出生日期*',
					editable: false
	   			  }]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '身份证号*',
						width:300,
						id: 'idNumber',
						name: 'idNumber',
						regexText:"请填写正确的身份证号！", 
						regex: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
		        	}]
				},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '照片存放地址*',
					width:300,
					id: 'employeeImg',
					name: 'employeeImg'
	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
			        methodName: 'getAllRailConstOrg',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '作业单位*',
					hiddenName: 'constOrg',
					allowBlank: false,
					editable: true,
					width:300,
					emptyText: "--请选择作业单位--"
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '职位*',
					width:300,
					id: 'job',
					name: 'job'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
       				xtype: 'dynamicCombo',
			        methodName: 'getRailEmployeeType',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '人员类型*',
					hiddenName: 'employeeType',
					allowBlank: false,
					editable: true,
					width:300,
					emptyText: "--请选择人员类型--"
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
					id: 'employeeTel',
					name: 'employeeTel',
					regexText:"请填写正确的联系方式！", 
					regex: /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|[1][3,4,5,7,8,9][0-9]{9})$/
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
				url: 'T130400Action.asp?method=add',
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
						//showErrorMsg(action.result.msg,addMhForm);
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