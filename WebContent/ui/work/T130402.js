Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			brhWin.show();
		}
	};
    var addMenu1 = {
        text : '更换图片',
        width : 60,
        iconCls : 'edit',
        disabled : true,
        handler : function() {
            var	selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            brhWin1.show();
            brhWin1.center();
        }
    };

    var delMenu = {
			text : '删除',
			width : 60,
			iconCls : 'delete',
			disabled : true,
			handler : function() {
				if(grid.getSelectionModel().hasSelection()) {
	        		var rec = grid.getSelectionModel().getSelected();
					var employeeCode = rec.get('employeeCode');
					showConfirm('确定要删除该人员吗？人员编码：' + employeeCode,grid,function(bt) {
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T130400Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj == "00"){
										Ext.MessageBox.alert('操作提示', employeeCode+'人员已成功删除!');
										showSuccessMsg('人员已成功删除!',grid);//showErrorMsg(action.result.msg,addMhForm)
										grid.getStore().reload();
									}else if(rspObj == "-1"){
										Ext.MessageBox.alert('操作提示', '未查询到该人员!');
									}else if(rspObj == "-2"){
										Ext.MessageBox.alert('操作提示', '该人员已在工单中，禁止删除！');
									}
								},
								params: { 
									employeeCode:employeeCode
								}
							});
						}
					});
	        	}else{
	        		Ext.Msg.alert('提示', '请选择一个人员！');
	        	}
			}
	};
	
	var editMenu = {
			text : '修改',
			width : 60,
			iconCls : 'edit',
			disabled : true,
			handler : function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
	            if(selectedRecord == null)
	            {
	                showAlertMsg("没有选择记录",grid);
	                return;
	            } 
	            Ext.Ajax.request({
					url : 'T130400Action_getData.asp',
					params : {
						employeeCode : selectedRecord.get('employeeCode')
					},
					success : function(rsp, opt) {
						//showErrorMsg(rspObj.msg,grid);
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							updTermWin.show();
							updTermWin.center();
							Ext.getCmp('employeeCodeUp').disable();
							Ext.getCmp('employeeCodeUp').setValue(rspObj.msg.employeeCode);
				            Ext.getCmp('employeeNameUp').setValue(rspObj.msg.employeeName);
				            updTermForm.getForm().findField("sexUp").setValue(rspObj.msg.sex);
//				            updTermForm.getForm().findField("infoSignUp").setValue(rspObj.msg.infoSign);
				            Ext.getCmp('birthdayUp').setValue(time1(rspObj.msg.birthday));
				            Ext.getCmp('idNumberUp').setValue(rspObj.msg.idNumber);
//				            Ext.getCmp('employeeImgUp').setValue(rspObj.msg.employeeImg);
				            updTermForm.getForm().findField("constOrgUp").setValue(rspObj.msg.constOrg);
//				            alert(rspObj.msg.job);
				            Ext.getCmp('jobUp').setValue(rspObj.msg.job);
				            updTermForm.getForm().findField("employeeTypeUp").setValue(rspObj.msg.employeeType);
				            Ext.getCmp('employeeTelUp').setValue(rspObj.msg.employeeTel);
				            updTermForm.getForm().findField("deptUp").setValue(rspObj.msg.dept);
				            
				        } else {
							showErrorMsg(rspObj.msg,grid);
						}
					}
				});
				}
		};
	
	function time1(shijianchuo)
	{
	//shijianchuo是整数，否则要parseInt转换
	var time = new Date(shijianchuo);
	var y = time.getFullYear();
	var m = time.getMonth();
	var d = time.getDate();
	return y+'-'+(m < 10 ? "0" + m : m)+'-'+(d < 10 ? "0" + d : d);
	}
	
	
		var updTermForm = new Ext.form.FormPanel({
	        frame: true,
	        height: 350,
	        width: 450,
	        labelWidth: 85,
	        waitMsgTarget: true, 
	            items: [{
	        		columnWidth: .5,
	            	xtype: 'panel',
	            	layout: 'form',
	       			items: [{
	       	            xtype: 'textnotnull',
	       	            labelStyle: 'padding-left: 5px',
	       	            fieldLabel: '人员编码*',
	       	            maxLength: 20,
	       	            id: 'employeeCodeUp',
	       	            name: 'employeeCodeUp',
	       	            width: 300,
	       	       }]
	    		},{
	        		columnWidth: .5,
	            	xtype: 'panel',
	            	layout: 'form',
	       			items: [{
	    	        	xtype: 'textnotnull',
	    				labelStyle: 'padding-left: 5px',
	    				fieldLabel: '人员名称*',
	    				id: 'employeeNameUp',
	    				name: 'employeeNameUp',
	    				maxLength: 80,
	    				width:300,
	            	}]
	    		},{
	        		columnWidth: .5,
	            	xtype: 'panel',
	            	layout: 'form',
	            	id: 'sexUp',
	            	name: 'sexUp',
	       			items: [{
	       	            xtype: 'combo',
	       	            width:300,
	       	            fieldLabel: '性别*',
	       	            hiddenName: 'sexUp',
	    				allowBlank: false,
	    				editable: false,
	       	            labelStyle: 'padding-left: 5px',
	       	            vertical: true,
	       	            store: new Ext.data.ArrayStore({
	     				fields: ['valueField','displayField'],
	     				data: [['1','男'],['2','女']],
	     				reader: new Ext.data.ArrayReader()
	     			    }),
	       	        }]
//	    		},{
//	        		columnWidth: .5,
//	            	xtype: 'panel',
//	            	layout: 'form',
//	       			items: [{
//	       	            xtype: 'radiogroup',
//	       	            width:300,
//	       	            fieldLabel: '出入网状态*',
//	       	            labelStyle: 'padding-left: 5px',
//	       	            vertical: true,
//	       	            id: 'infoSignUp', 
//	       	            name: 'infoSignUp', 
//	       	            items: [{
//	    					boxLabel: '未入网', 
//	    					name: 'infoSignUp', 
//	    					inputValue: 0, 
//	    					checked: true
//	    				},{
//	    					boxLabel: '已入网', 
//	    					name: 'infoSignUp', 
//	    					inputValue: 1
//	    				},{
//	    					boxLabel: '已出网', 
//	    					name: 'infoSignUp', 
//	    					inputValue: 2
//	    				}]
//	       	        }]
	    		},{
	    			columnWidth: .5,
	            	xtype: 'panel',
	            	layout: 'form',
	       			items: [{
	    				xtype: 'datefield',
	    				width: 300,
	    				labelStyle: 'padding-left: 5px',
	    				id: 'birthdayUp',
	    				name: 'birthdayUp',
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
	    					id: 'idNumberUp',
	    					name: 'idNumberUp',
	    					regexText:"请填写正确的身份证号！", 
	    					regex: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
	    	        	}]
//	    			},{
//	        		columnWidth: .5,
//	            	xtype: 'panel',
//	            	layout: 'form',
//	       			items: [{
//	    	        	xtype: 'textnotnull',
//	    				labelStyle: 'padding-left: 5px',
//	    				fieldLabel: '照片存放地址*',
//	    				width:300,
//	    				id: 'employeeImgUp',
//	    				name: 'employeeImgUp'
//	            	}]
	    		},{
	    			columnWidth: .5,
	            	xtype: 'panel',
	            	layout: 'form',
	       			items: [{
	       				xtype: 'dynamicCombo',
	    		        methodName: 'getAllRailConstOrg',
	    				labelStyle: 'padding-left: 5px',
	    				fieldLabel: '作业单位*',
	    				hiddenName: 'constOrgUp',
	    				allowBlank: false,
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
	    				fieldLabel: '职位*',
	    				width:300,
	    				maxLength: 80,
	    				id: 'jobUp',
	    				name: 'jobUp'
	            	}]
	    		},{
	        		columnWidth: .5,
	            	xtype: 'panel',
	            	layout: 'form',
	            	id: 'employeeTypeUp', 
       	            name: 'employeeTypeUp',
	       			items: [{
	       				xtype: 'dynamicCombo',
	    		        methodName: 'getRailEmployeeType',
	    				labelStyle: 'padding-left: 5px',
	    				fieldLabel: '人员类型*',
	    				hiddenName: 'employeeTypeUp',
	    				allowBlank: false,
	    				editable: false,
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
	    				id: 'employeeTelUp',
	    				name: 'employeeTelUp'
	            	}]
	    		},{
	        		columnWidth: .5,
	            	xtype: 'panel',
	            	layout: 'form',
	            	id: 'deptUp', 
       	            name: 'deptUp',
	       			items: [{
	       				xtype: 'dynamicCombo',
	    		        methodName: 'getBranchId12',
	    				labelStyle: 'padding-left: 5px',
	    				fieldLabel: '所属机构*',
	    				hiddenName: 'deptUp',
	    				allowBlank: false,
	    				editable: false,
	    				width:300,
	    				emptyText: "--请选择所属机构--"
	            	}]
	    		}]
	    });
		var updTermWin = new Ext.Window({
	        title: '修改人员信息',
	        initHidden: true,
	        header: true,
	        frame: true,
	        closable: false,
	        modal: true,
	        width: 450,
	        autoHeight: true,
	        layout: 'fit',
	        items: [updTermForm],
	        buttonAlign: 'center',
	        closeAction: 'hide',
	        iconCls: 'edit',
	        resizable: false,
	        buttons: [{
	            text: '提交',
	            handler: function() {
	            	if(updTermForm.getForm().isValid()) {
//						var infoSignUp=Ext.getCmp('infoSignUp'); 
//						var asVal = "0";
//						infoSignUp.eachItem(function(item){  
//						    if(item.checked===true){  
//						    	asVal = item.inputValue;
//						    }  
//						});
						
						var date= Ext.getCmp('birthdayUp').getValue();
						var birthDay=date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
						var age = jsGetAge(birthDay);
						if((age-18)<0){
							showErrorMsg("年龄不满18岁",brhInfoForm);
							return;
						}
						updTermForm.getForm().submit({
							url: 'T130400Action.asp?method=edit',
							waitMsg: '正在提交，请稍后......',
							success: function(form,action) {
								updTermWin.hide();
								showSuccessMsg(action.result.msg,updTermForm);
								updTermForm.getForm().reset();
								grid.getStore().reload();
							},
							failure: function(form,action) {
								showErrorMsg(action.result.msg,updTermForm);
							},
							params: {
								employeeCode:Ext.getCmp('employeeCodeUp').getValue(),
								employeeName:Ext.getCmp('employeeNameUp').getValue(),
							    sex:updTermForm.getForm().findField("sexUp").getValue(),
//								infoSign:asVal,
							    birthday:Ext.getCmp('birthdayUp').getValue(),			
								idNumber:Ext.getCmp('idNumberUp').getValue(),		
//								employeeImg:Ext.getCmp('employeeImgUp').getValue(),
								constOrg:updTermForm.getForm().findField("constOrgUp").getValue(),		
								job:Ext.getCmp('jobUp').getValue(),
								employeeType:updTermForm.getForm().findField("employeeTypeUp").getValue(),
								employeeTel:Ext.getCmp('employeeTelUp').getValue(),
								dept:  updTermForm.getForm().findField("deptUp").getValue()
							}
						});
					}
	            }
	        },{
	            text: '关闭',
	            handler: function() {
	                updTermWin.hide();
	            }
	        }]
	    });


		
		var detailMenu = {
				text: '查看人员图片',
				width: 85,
				disabled: false,
				iconCls: 'detail',
				handler:function() {
					var	selectedRecord = grid.getSelectionModel().getSelected();
		            if(selectedRecord == null)
		            {
		                showAlertMsg("没有选择记录",grid);
		                return;
		            }
		            Ext.Ajax.request({
		            	url : 'T130400Action_getData.asp',
						params : {
							employeeCode : selectedRecord.get('employeeCode')
						},
						success : function(rsp, opt) {
							//showErrorMsg(rspObj.msg,grid);
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								var imgPath = selectedRecord.data.employeeImg;
					            html = '<div style="width:100%;height:100%;"><img id="videoSource" style="width:100%;height:100%;" src="'+imgPath+'" /><div>';
					            songPlayer.show();
					            songPlayer.center();
					            Ext.getCmp("playerPanel").body.update(html); 
//					            Ext.getCmp('toolNameDet').setValue(rspObj.msg.toolName);
//					            Ext.getCmp('enableStatusDet').setValue(enableStatusRender(rspObj.msg.enableStatus));
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
						}
					}); 
		          
				}
		};
		
		 var playerForm=new Ext.Panel({
		        frame: true,
		        height: 100,
		        width: 400,
		        labelWidth: 85,
		        waitMsgTarget: true,
		        items: [{  
				        xtype : 'panel',  
				        id : 'playerPanel', 
				        labelStyle: 'padding-left: 5px',
				        width:400,  
				        height:400, 
				        html:""
				    }],
		    });
				 
		var songPlayer = new Ext.Window({  
	        layout : 'fit',  
	        width:400,  
	        height:400,  
	        modal : true,  
	        frame: true,
	        closable : false,
	        items : [playerForm]  ,
	    	buttons: [{
				text: '关闭',
				handler: function() {
					songPlayer.hide();
					Ext.getCmp("playerPanel").body.update("");  
				}
			}]
	    }); 
		
		
		
		
	var queryMenu = {
		text : '录入查询条件',
		width : 100,
		iconCls : 'query',
		handler : function() {
			//Ext.MessageBox.alert('提示', '你点了录入查询条件按钮!');
			queryWin.show();
		}
	};
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'employeeCodefind',
			name: 'employeeCodefind',
			fieldLabel: '人员编码',
			width:200
		},{
			xtype: 'textfield',
			id: 'employeeNamefind',
			name: 'employeeNamefind',
			fieldLabel: '人员名称',
			width:200
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		iconCls : 'query',
		width: 400,
		autoHeight: true,
		items: [queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				queryWin.collapse();
				queryWin.getEl().pause(1);
				queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryWin.expand();
				queryWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				termStore.load();
                //grid.getTopToolbar().items.items[10].disable();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	var exportMenu = {
		text: '导入人员信息',
		width: 100,
		id:'download',
		iconCls: 'download',
		handler:function() {
			//Ext.MessageBox.alert('提示', '你点了导出报表按钮!');
			excelDown.show();
		}
	};
	var exportMenu = {
			text: '导出',
			width: 60,
			id:'download',
			iconCls: 'download',
			handler:function() {
				//Ext.MessageBox.alert('提示', '你点了导出报表按钮!');
				excelDown.show();
			}
	};
	var exportMuBan = {
			text: '下载人员导入模板',
			width: 100,
			id:'download1',
			iconCls: 'download',
			handler:function() {
				window.location.href = Ext.contextPath +"/template/employeeInfoTemplte.xls";
			}
	};
	var importExcel = {
			text: '导入人员信息',
			width: 85,
			disabled: false,
			iconCls: 'edit',
			handler:function() {
//				var	selectedRecord = grid.getSelectionModel().getSelected();
//	            if(selectedRecord == null)
//	            {
//	                showAlertMsg("没有选择记录",grid);
//	                return;
//	            }
				brhWinAll.show();
				brhWinAll.center();
			}
		};
	var importEmploeeImgs = {
			text: '导入人员头像',
			width: 85,
			disabled: false,
			iconCls: 'edit',
			handler:function() {
				dialog.show();
				
//				 dialog.on( 'uploadsuccess' , onUploadSuccess); //定义上传成功回调函数      
//		           dialog.on( 'uploadfailed' , onUploadFailed); //定义上传失败回调函数      
//		           dialog.on( 'uploaderror' , onUploadFailed); //定义上传出错回调函数     
//		           dialog.on( 'uploadcomplete' , onUploadComplete); //定义上传完成回调函数     
				
//				var	selectedRecord = grid.getSelectionModel().getSelected();
//	            if(selectedRecord == null)
//	            {
//	                showAlertMsg("没有选择记录",grid);
//	                return;
//	            }
//				brhWinAll2.show();
//				brhWinAll2.center();
			}
	};
	  //文件上传成功后的回调函数     
    var onUploadSuccess = function(dialog, filename, resp_data, record){   
    	console.log(resp_data);
        if(!resp_data.code==200){  
            alert(resp_data.message);//resp_data是json格式的数据     
        }          
    }     
         
    //文件上传失败后的回调函数     
    var onUploadFailed = function(dialog, filename, resp_data, record){     
    	console.log(resp_data);
        alert(resp_data);     
    }     
         
    //文件上传完成后的回调函数     
   var  onUploadComplete = function(dialog){       
	   console.log(resp_data);
       dialog.hide();     
    }     
	var excelQueryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 520,
		autoHeight: true,
		iconCls: 'T50902',
		buttonAlign: 'center',
		buttons: [{
			text: '确认导出',
			iconCls: 'download',
			handler: function() {
				if(!excelQueryForm.getForm().isValid()) {
					return;
				}
				var employeeCode= Ext.getCmp('employeeCodefind').getValue();
	            var employeeName= Ext.getCmp('employeeNamefind').getValue();
				var param = "?a=1";
				if(employeeCode){
					param = param + "&employeeCode="+employeeCode;
				}
				if(employeeName){
					param = param + "&employeeName="+employeeName;
				}
				window.location.href = Ext.contextPath +"/exportExcelT130402.asp"+param;
				excelDown.hide();
//				excelQueryForm.getForm().submit({
//					url: 'T30104Action_download.asp',
//					
//					waitMsg: '正在下载报表，请稍等......',
//					success: function(form,action) {
//					//showAlertMsg(action.result.msg,grid);
//						window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
//																		action.result.msg+'&key=exl20exl';
//						excelDown.hide();
//					},
//					failure: function(form,action) {
//						Ext.MessageBox.show({
//							msg: '下载失败！',
//							title: '错误提示',
//							animEl: Ext.getBody(),
//							buttons: Ext.MessageBox.OK,
//							icon: Ext.MessageBox.ERROR,
//							modal: true,
//							width: 250
//						});
//						excelDown.hide();
//					},
//					params: {
//						txnId: '30104',
//						subTxnId: '03'
//					}
//				});
			}
		},{
			text: '取消导出',
			iconCls: 'refuse',
			handler: function() {
				excelDown.hide();
			}
		}]
	});
	var excelDown = new Ext.Window({
		title: '导出',
		layout: 'fit',
		width: 350,
		autoHeight: true,
		items: [excelQueryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				excelDown.collapse();
				excelDown.getEl().pause(1);
				excelDown.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				excelDown.expand();
				excelDown.center();
			},
			qtip: '恢复',
			hidden: true
		}]
	});
    //
    var brhInfoForm1 = new Ext.form.FormPanel({
        frame: true,
        autoHeight: true,
        width: 450,
        defaultType: 'textfield',
        labelWidth: 85,
        waitMsgTarget: true,
        fileUpload: true,
        enctype:'multipart/form-data',
        items: [{
            xtype: 'fileuploadfield',
            labelStyle: 'padding-left: 5px',
            fieldLabel: '选择图片',
            buttonText:'浏览',
            allowBlank: false,
            width:'220',
            name: 'upload1',
            validator: function(value){
                // 文件类型判断
                var arrType = value.split('.');
                var docType = arrType[arrType.length-1].toLowerCase();
                //bmp,jpg,jpeg,png,tif,gif
                if(docType != 'jpg' && docType != 'jpeg'  && docType != 'gif' && docType != 'png'){
                    return '文件类型必须为图片';
                }
                // 文件名称长度判断
//                var arrName = value.split('\\');
//                var name = arrName[arrName.length-1];
//                if(name.length > 50){
//                    return '文件名称长度必须小于50字符';
//                }
                return true;
            }
        
        }],
    });

    var brhWin1 = new Ext.Window({
        title: '更换工具图片',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 450,
        autoHeight: true,
        layout: 'fit',
        items: [brhInfoForm1],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'logo',
        resizable: false,
        buttons: [{
            id : 'save1',
            text: '确定',
            handler: function() {
                var	selectedRecord = grid.getSelectionModel().getSelected();
                console.log(selectedRecord);
                
                if(brhInfoForm1.getForm().isValid()) {
                    //var submitValues = brhInfoFormAll.getForm().getValues();
                    brhInfoForm1.getForm().submit({
                        async:false,
                        url: 'T130200Action_editEmployeePig.asp',
                        waitMsg: '正在提交，请稍后......',
                        success: function(form,action) {

                        },
                        failure: function(form,action) {
                            if(action.result.code ==200){
                                brhWin1.hide();
                                showSuccessMsg("修改成功",brhInfoForm1);
                                brhInfoForm1.getForm().reset();
                                grid.getStore().reload();
                            }else{
                                showErrorMsg(action.result.msg,brhInfoForm1);
                            }
                        },
                        params: {
                            txnId: '1304',
                            subTxnId: '22',
                            employeeCode:selectedRecord.id
                        }
                    });
                }
            }
        },{
            text: '关闭',
            handler: function() {
                brhWin1.hide(grid);
                brhInfoForm1.getForm().reset();
            }
        }]
    });
	var brhInfoFormAll = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		fileUpload: true,
		enctype:'multipart/form-data',
		items: [/*{
    		xtype: 'dynamicCombo',
	        methodName: 'getRailWorkInfolList',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '作业工单*',
			hiddenName: 'workCode',
			allowBlank: false,
			editable: false,
			width:300,
			emptyText: "--请选择工单--"
    	},*/{
			xtype: 'fileuploadfield',
			fieldLabel: '请选择excel',
			buttonText:'浏览',
			width:'220',
			fileUpload: true,
            allowBlank: false,
			style: 'padding-left: 5px',
		    name: 'upload'
		}]
	});
	var brhWinAll = new Ext.Window({
		title: '导入excel',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [brhInfoFormAll],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			id : 'ensure1',
			text: '确定',
			handler: function() {
				var ff  = true;
				var	selectedRecord = grid.getSelectionModel().getSelected();
				if(brhInfoFormAll.getForm().isValid()) {
					//var submitValues = brhInfoFormAll.getForm().getValues();  
					brhInfoFormAll.getForm().submit({
                        async:false,
                        url: 'T130200Action_excelImport.asp',
						//waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
                            // brhWinAll.hide(grid);
                            // showSuccessMsg("导入详情请查看导入结果报告",brhInfoFormAll);
                            // brhInfoFormAll.getForm().reset();
                            // grid.getStore().reload();

						},
						failure: function(form,action) {
                            // brhWinAll.hide(grid);
                            // showSuccessMsg("导入详情请查看导入结果报告",brhInfoFormAll);
                            // brhInfoFormAll.getForm().reset();
                            // grid.getStore().reload();
                            ff = false;
							if(action.result.code ==200){
								brhWinAll.hide();
								showSuccessMsg(action.result.info,brhInfoFormAll);
								brhInfoFormAll.getForm().reset();
								grid.getStore().reload();
							}else{
								showErrorMsg(action.result.msg,brhInfoFormAll);
							}
						},
						params: {
							txnId: '1304',
							subTxnId: '22'
						}
					});
					if(ff){
                        setTimeout(function (){
                            brhWinAll.hide(grid);
                            showSuccessMsg("导入详情请查看导入结果报告",brhInfoFormAll);
                            brhInfoFormAll.getForm().reset();
                            grid.getStore().reload();
                        },1000);
					}
                    setTimeout(function (){
                        grid.getStore().reload();
                    },3000);


				}
			}
		},{
			id : 'reset1',
			text: '重置',
			handler: function() {
				brhInfoFormAll.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				brhWinAll.hide(grid);
				brhInfoFormAll.getForm().reset();
			}
		}]
	});
	var brhInfoFormAll2 = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 400,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		fileUpload: true,
		allowNumber : 10000,//0表示不限制文件个数  
		enctype:'multipart/form-data',
		items: [/*{
    		xtype: 'dynamicCombo',
	        methodName: 'getRailWorkInfolList',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '作业工单*',
			hiddenName: 'workCode',
			allowBlank: false,
			editable: false,
			width:300,
			emptyText: "--请选择工单--"
    	},*/{
    		xtype: 'fileuploadfield',
    		fieldLabel: '请选择员工头像',
    		buttonText:'浏览',
    		width:'220',
    		style: 'padding-left: 5px',
    		name: 'upload'
    	}]
	});
	//多文件上传 
	// 文件上传窗口
	var dialog = new UploadDialog({
		width: 550, 
		//uploadUrl : Ext.contextPath+'/page/work/T130400Action_upload.asp',
		uploadUrl : Ext.contextPath+'/T130400Action_upload.asp',
		filePostName : 'files',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB',
		fileTypes : '*',
		fileTypesDescription : '文本文件(*)',
		title: '头像文件上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		exterMethod: function() {
			
		},
		postParams: {
			txnId: '1304',
			subTxnId: '21'
		}
	});
    
	var brhWinAll2 = new Ext.Window({
		title: '员工头像上传',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [dialog],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			id : 'ensure1',
			text: '确定',
			handler: function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
				if(brhInfoFormAll.getForm().isValid()) {
					//var submitValues = brhInfoFormAll.getForm().getValues();  
					brhInfoFormAll.getForm().submit({
						url: 'T130400Action_upload.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
						},
						failure: function(form,action) {
							if(action.result.code ==200){
								brhWinAll2.hide();
								showSuccessMsg(action.result.info,brhInfoFormAll2);
								brhInfoFormAll2.getForm().reset();
								grid.getStore().reload();
							}else{
								showErrorMsg(action.result.msg,brhInfoFormAll2);
							}
						},
						params: {
							txnId: '1302',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			id : 'reset1',
			text: '重置',
			handler: function() {
				brhInfoFormAll2.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				brhWinAll2.hide(grid);
				brhInfoFormAll2.getForm().reset();
			}
		}]
	});
	//人员添加表单
	var brhInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 450,
		defaultType: 'textfield',
		labelWidth: 85,
		waitMsgTarget: true,
		fileUpload: true,
		enctype:'multipart/form-data',
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
				maxLength: 80,
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
				editable: false,
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
				xtype: 'fileuploadfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '人员图片',
				buttonText:'浏览',
				width:'300',
				id: 'upload22',
			    name: 'upload22'
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
				fieldLabel: '职位*',
				width:300,
				maxLength: 80,
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
				editable: false,
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
				editable: false,
				width:300,
				emptyText: "--请选择所属机构--"
        	}]
		}],
	});
	
	//人员添加窗口
	var brhWin = new Ext.Window({
		title: '新增人员',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
		autoHeight: true,
		layout: 'fit',
		items: [brhInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			id : 'save',
			text: '确定',
			handler: function() {
				saveWhse();
			}
		},{
			id : 'reset1',
			text: '重置',
			handler: function() {
				brhInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				brhWin.hide(grid);
				brhInfoForm.getForm().reset();
			}
		}]
	});
	
	var hasSub = false;
	function saveWhse(){
		var btn = Ext.getCmp('save');
		var frm = brhInfoForm.getForm();
		hasSub = true;
		if (frm.isValid()) {
			
			var date= Ext.getCmp('birthday').getValue();
			if(date==null||date==''){
				showErrorMsg("请选择年龄",brhInfoForm);
				return;
			}
			var birthDay=date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
			var age = jsGetAge(birthDay);
			if((age-18)<0){
				showErrorMsg("年龄不满18岁",brhInfoForm);
				return;
			}
//			btn.disable();
			frm.submit({
				url: 'T130400Action.asp?method=add',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
					brhWin.hide();
					showSuccessMsg(action.result.msg,brhInfoForm);
					brhInfoForm.getForm().reset();
					grid.getStore().reload();
				},
				failure : function(form,action) {
					btn.enable();
					hasSub = false;
					if (action.result.msg.substr(0,2) == 'CZ') {
						Ext.MessageBox.show({
							msg: action.result.msg.substr(2) + '<br><h1>是否继续保存吗？</h1>',
							title: '确认提示',
							animEl: Ext.get(brhInfoForm.getEl()),
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
						showErrorMsg(action.result.msg,brhInfoForm);
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
	
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(addMenu1);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(editMenu);
	menuArr.push('-');
	menuArr.push(detailMenu);
	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	menuArr.push('-');
	menuArr.push(exportMuBan);
	menuArr.push('-');
	menuArr.push(importExcel);
	menuArr.push('-');
	menuArr.push(importEmploeeImgs);
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=employees'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'employeeCode'
        },[
            {name: 'employeeCode',mapping: 'employeeCode'},
            {name: 'employeeName',mapping: 'employeeName'},
            {name: 'sex',mapping: 'sex'},
			{name: 'birthday',mapping: 'age'},
			{name: 'idNumber',mapping: 'idNumber'},
			{name: 'employeeImg',mapping: 'employeeImg'},
			{name: 'constOrg',mapping: 'constOrgName'},
			{name: 'job',mapping: 'job'},
			{name: 'employeeType',mapping: 'typeName'},
			{name: 'employeeTel',mapping: 'employeeTel'},
			{name: 'password',mapping: 'password'},
			{name: 'entryDate',mapping: 'entryDate'},
			{name: 'dept',mapping: 'brhName'}
            ])
    });
	termStore.load();
	
	/**
	 * 人员性别
	 */
	function sexRender(val) {
		if(val == '男') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/male.png" />';
		} else if(val == '女') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/female.png" />';
		}
		return val;
	}
	
	
	function jsGetAge(strBirthday) {
		var returnAge=0;
		var strBirthdayArr = strBirthday.split("-");
		var birthYear = strBirthdayArr[0];
		var birthMonth = strBirthdayArr[1];
		var birthDay = strBirthdayArr[2];
		var d = new Date();
		var nowYear = d.getFullYear();
		var nowMonth = d.getMonth() + 1;
		var nowDay = d.getDate();
		 
		if (nowYear == birthYear) {
		returnAge = 0;//同年 则为0岁  
		} else {
		   var ageDiff = nowYear - birthYear; //年之差  
	     if (ageDiff > 0) {
		   if (nowMonth == birthMonth) {
		         var dayDiff = nowDay - birthDay;//日之差  
		        if (dayDiff < 0) {
		       returnAge = ageDiff - 1;
		         } else {
		      	returnAge = ageDiff;
			     }
	      } else {
			   var monthDiff = nowMonth - birthMonth;//月之差  
			   if (monthDiff < 0) {
			   returnAge = ageDiff - 1;
			   } else {
			   returnAge = ageDiff;
			  }
		   }

			} else {

			returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天  

			}

			}
		return returnAge;
	}
    termStore.on('beforeload', function() {
    	Ext.apply(this.baseParams, {
            start: 0,
            employeeCode: Ext.getCmp('employeeCodefind').getValue(),
            employeeName: Ext.getCmp('employeeNamefind').getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
//    	new Ext.grid.RowNumberer(),
		{id: 'employeeCode',header: '人员编码',dataIndex: 'employeeCode',sortable: true,width: 80},
		{header: '人员名称',dataIndex: 'employeeName',sortable: true,width: 80},
		{header: '性别',dataIndex: 'sex',sortable: true,renderer:sexRender,width: 50},
		{header: '年龄',dataIndex: 'birthday',sortable: true,width: 50},
		{header: '身份证号',dataIndex: 'idNumber',sortable: true,width: 150},
		{header: '照片存放地址',dataIndex: 'employeeImg',sortable: true,width: 150},
		{header: '作业单位',dataIndex: 'constOrg',sortable: true,width: 100},
		{header: '职务',dataIndex: 'job',sortable: true,width: 90},
		{header: '人员类型',dataIndex: 'employeeType',sortable: true,width: 80},
		{header: '联系方式',dataIndex: 'employeeTel',sortable: true,width: 100},
		{header: '所属机构',dataIndex: 'dept',sortable: true,width: 120}
    ]);
	// 工单信息列表
    var grid = new Ext.grid.GridPanel({
        title: '人员信息维护',
        iconCls: 'T301',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        region:'center',
        store: termStore,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        cm: termColModel,
        clicksToEdit: true,
        forceValidation: true,
        tbar: menuArr,
        renderTo: Ext.getBody(),
        loadMask: {
            msg: '加载中......'
        },
        bbar: new Ext.PagingToolbar({
            store: termStore,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
    });
    
    grid.getSelectionModel().on({
        'rowselect': function() {
            //行高亮
            Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
            // 根据商户状态判断哪个编辑按钮可用
            rec = grid.getSelectionModel().getSelected();
            if(rec != null) {
           
            	var enableStatus=rec.get('enableStatus');
                
                grid.getTopToolbar().items.items[0].enable();
                grid.getTopToolbar().items.items[2].enable();
                grid.getTopToolbar().items.items[4].enable();
                grid.getTopToolbar().items.items[6].enable();
                if(enableStatus=="1"){
                	grid.getTopToolbar().items.items[6].enable();
                	grid.getTopToolbar().items.items[8].disable();
                }else if(enableStatus=="0"){
                	grid.getTopToolbar().items.items[6].disable();
                	grid.getTopToolbar().items.items[8].enable();
                }
               // grid.getTopToolbar().items.items[10].enable();
            } else {
                grid.getTopToolbar().items.items[0].disable();
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[4].disable();
                grid.getTopToolbar().items.items[6].disable();
                grid.getTopToolbar().items.items[8].disable();
               // grid.getTopToolbar().items.items[10].disable();
            }
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})