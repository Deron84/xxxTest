Ext.onReady(function() {
	//0维护，1维修maintenceType
//	var maintenceTypeStore = new Ext.data.JsonStore({
//		fields : [ 'valueField', 'displayField' ],
//		root: 'data'
//	});
//	SelectOptionsDWR.getComboData('MAINTENCE_TYPE',function(ret){
//		alert(JSON.stringify(ret));
//		maintenceTypeStore.loadData(Ext.decode(ret));
//	});
	var addMhForm = new Ext.FormPanel({
		title: '新增工单信息',
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
       	            fieldLabel: '工单编码*',
       	            id: 'workCode',
       	            name: 'workCode',
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
					fieldLabel: '工单名称*',//工单名称应为必填，如果为空，在添加工具和添加人员时无法选择该工单。
					id: 'workName',
					name: 'workName',
					width:300,
					maxLength: 80,
					allowBlank: false
	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
			        methodName: 'getAllRailConstOrg',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '作业单位',
					hiddenName: 'constOrg',
					allowBlank: true,
					editable: false,
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
					fieldLabel: '调度号',
					width:300,
					maxLength: 80,
					id: 'dispatchCode',
					name: 'dispatchCode',
					allowBlank: true
	        	}]
//			},{
//        		columnWidth: .5,
//	        	xtype: 'panel',
//	        	layout: 'form',
//       			items: [{
//       	            xtype: 'radiogroup',
//       	            width:300,
//       	            fieldLabel: '工单类型*',
//       	            labelStyle: 'padding-left: 5px',
//       	            vertical: true,
//       	            items: [{
//						boxLabel: '同步', 
//						name: 'registerType', 
//						inputValue: 1, 
//						checked: true
//					},{
//						boxLabel: 'OCR', 
//						name: 'registerType', 
//						inputValue: 2
//					},{
//						boxLabel: 'WEB', 
//						name: 'registerType', 
//						inputValue: 3
//					},{
//						boxLabel: '终端', 
//						name: 'registerType', 
//						inputValue: 4
//					}]
//       	        }]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
					xtype: 'datetimefield',
					width: 300,
					labelStyle: 'padding-left: 5px',
					id: 'skylightStart',
					name: 'skylightStart',
					format: 'Y-m-d H:m:s',
					altFormats: 'Y-m-d H:m:s',
					vtype: 'daterange',
					endDateField: 'skylightEnd',
					fieldLabel: '施工作业天窗开始*',
					editable: false,
					allowBlank: false
       			}]			
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
					xtype: 'datetimefield',
					width: 300,
					labelStyle: 'padding-left: 5px',
					id: 'skylightEnd',
					name: 'skylightEnd',
					format: 'Y-m-d H:m:s',
					altFormats: 'Y-m-d H:m:s',
					vtype: 'daterange',
					startDateField: 'skylightStart',
					fieldLabel: '施工作业天窗结束*',
					editable: false,
					allowBlank: false
				}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
			        methodName: 'getTdAccesses1',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '入网通道门禁',
					hiddenName: 'accessInCode',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择入网通道门禁--"
	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
			        methodName: 'getTdAccesses',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '出网通道门禁',
					hiddenName: 'accessOutCode',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择出网通道门禁--"
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '作业负责人',
					width:300,
					id: 'workPic',
					name: 'workPic',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '负责人电话',
					width:300,
					id: 'workTel',
					name: 'workTel',
					allowBlank: true,
					regexText:"请填写正确的联系方式！", 
					regex: /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|[1][3,4,5,7,8,9][0-9]{9})$/,///^[1][3,4,5,7,8,9][0-9]{9}$/
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '作业清点人',
					width:300,
					id: 'workCount',
					name: 'workCount',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '进门巡护确认人',
					width:300,
					id: 'inPatrol',
					name: 'inPatrol',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '出门巡护确认人',
					width:300,
					id: 'outPatrol',
					name: 'outPatrol',
					maxLength: 80,
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '作业地点',
					width:300,
					maxLength: 80,
					id: 'workAddress',
					name: 'workAddress',
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '作业里程',
					width:300,
					maxLength: 20,
					id: 'workMileage',
					name: 'workMileage',
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '作业人数',
					width:300,
					maxLength: 20,
					id: 'employeeCount',
					name: 'employeeCount',
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '工作量及质量标准',
					width:300,
					maxLength: 80,
					id: 'workStandard',
					name: 'workStandard',
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '风险控制措施',
					width:300,
					maxLength: 80,
					id: 'riskControl',
					name: 'riskControl',
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '线别',
					width:300,
					maxLength: 80,
					id: 'lineLevel',
					name: 'lineLevel',
					allowBlank: true
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '行别',
					width:300,
					maxLength: 80,
					id: 'rowLevel',
					name: 'rowLevel',
					allowBlank: true
	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{//0维护，1维修
	   				xtype: 'basecomboselect',
//	   				store:maintenceTypeStore,
	   				baseParams : 'maintenceTypeStore',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '维修类型',
					hiddenName: 'maintenceType',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择维修类型--"
	        	}]
			},{
//        		columnWidth: .5,
//	        	xtype: 'panel',
//	        	layout: 'form',
//       			items: [{
//		        	xtype: 'textnotnull',
//					labelStyle: 'padding-left: 5px',
//					fieldLabel: '天窗类型*',
//					width:300,
//					id: 'skyLightType',
//					name: 'skyLightType'
//	        	}]
//			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '站/区段',
					width:300,
					maxLength: 80,
					id: 'station',
					name: 'station',
					allowBlank: true
	        	}]
//			},{
//				columnWidth: .5,
//	        	xtype: 'panel',
//	        	layout: 'form',
//	   			items: [{
//	   				xtype: 'dynamicCombo',
//			        methodName: 'getRailFormOrg',
//					labelStyle: 'padding-left: 5px',
//					fieldLabel: '组织单位*',
//					hiddenName: 'formOrg',
//					allowBlank: false,
//					editable: true,
//					width:300,
//					emptyText: "--请选择组织单位--"
//	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
			        methodName: 'getRailpatrol',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '巡护中队',
					hiddenName: 'patrol',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择巡护中队--"
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '登记站',
					width:300,
					maxLength: 80,
					allowBlank: true,
					id: 'regStation',
					name: 'regStation'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '驻所联系人',
					allowBlank: true,
					width:300,
					maxLength: 80,
					id: 'residentLiaison',
					name: 'residentLiaison'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '驻站联系人',
					width:300,
					maxLength: 80,
					allowBlank: true,
					id: 'residentStation',
					name: 'residentStation'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '现场联系人',
					allowBlank: true,
					width:300,
					maxLength: 80,
					id: 'residentOnline',
					name: 'residentOnline'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '对讲机联系频道',
					allowBlank: true,
					width:300,
					maxLength: 80,
					id: 'interphone',
					name: 'interphone'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
					xtype: 'dynamicCombo',
					methodName: 'getAllRailTeam',
					fieldLabel: '班组*',
					labelStyle: 'padding-left: 5px',
					labelAlign: 'right',
					hiddenName: 'workTeam',
					blankText: '请选择班组',
					width:300,
					editable: false,
					allowBlank: false,
					emptyText: "--请选择班组--"
       			}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
    				xtype: 'dynamicCombo',
    				methodName: 'getAllWhse',
    				fieldLabel: '仓库*',
    				labelStyle: 'padding-left: 5px',
    				labelAlign: 'right',
    				hiddenName: 'whseCode',
    				blankText: '请选择',
    				width:300,
    				editable: false,
    				allowBlank: false,
    				emptyText: "--请选择仓库--",
    			}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
	   				xtype: 'dynamicCombo',
			        methodName: 'getRailDkEmployee',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '盯控干部',
					hiddenName: 'targetingEmployeeCode',
					allowBlank: true,
					editable: false,
					width:300,
					emptyText: "--请选择盯控干部--"
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
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '申请事由',
					width:300,
					maxLength: 100,
					allowBlank: true,
					id: 'applyMsg',
					name: 'applyMsg'
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
				url: 'T130100Action.asp?method=add',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
					hasSub = false;
					showSuccessMsg(action.result.msg,addMhForm);
					btn.enable();
					frm.reset();;
				},
				failure : function(form,action) {
					btn.enable();
					hasSub = false;
					showSuccessMsg(action.result.msg,addMhForm);
				},
				params: {
					txnId: '1301',
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