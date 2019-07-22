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
	
	var delMenu = {
			text : '删除',
			width : 60,
			iconCls : 'delete',
			disabled : true,
			handler : function() {
				if(grid.getSelectionModel().hasSelection()) {
	        		var rec = grid.getSelectionModel().getSelected();
					var equipCode = rec.get('equipCode');
					var equipId = rec.get('id');
					showConfirm('确定要删除该终端设备吗？终端设备编号：' + equipId,grid,function(bt) {
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T120103Action.asp?method=delete',
								success: function(rsp,opt) {
//									alert(JSON.stringify(rsp)+"  >><<  "+JSON.stringify(opt));
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj == "00"){
//										Ext.MessageBox.alert('操作提示', equipCode+'终端设备已成功删除!');
										showSuccessMsg(equipCode+'终端设备已成功删除!',grid);//showErrorMsg(action.result.msg,addMhForm)
										grid.getStore().reload();
									}else if(rspObj == "-1"){
										Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
									}
								},
								params: { 
									equipId:equipId,
									txnId: '120103',
									subTxnId: '02'
								}
							});
						}
					});
	        	}else{
	        		Ext.Msg.alert('提示', '请选择一个终端设备！');
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
	            var equipType=selectedRecord.get('equipType')
				 if(equipType =='配套终端')
		            {
		                showAlertMsg("配套终端禁止修改",grid);
		                return;
		            } 
	            
	            Ext.Ajax.request({
					url : 'T120103Action_getData.asp',
					params : {
						equipId: selectedRecord.get('id')
					},
					success : function(rsp, opt) {
						//showErrorMsg(rspObj.msg,grid);
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							if(rspObj.msg.equipType=='1'){
								updTermWin1.show();
								updTermWin1.center();
								Ext.getCmp('idUp1').disable();
								Ext.getCmp('idUp1').setValue(rspObj.msg.id);
								updTermForm1.getForm().findField("accessCodeUp1").disable();
								Ext.getCmp('equipCodeUp1').disable();
					            updTermForm1.getForm().findField("accessCodeUp1").setValue(rspObj.msg.accessCode);
					            Ext.getCmp('equipCodeUp1').setValue(rspObj.msg.equipCode);
					            updTermForm1.getForm().findField("equipTypeUp1").disable();
					            updTermForm1.getForm().findField("equipTypeUp1").setValue(rspObj.msg.equipType);
					            Ext.getCmp('installDateUp1').setValue(strdate(rspObj.msg.installDate) );	   
							}else if(rspObj.msg.equipType=='2'){
								updTermWin2.show();
								updTermWin2.center();
								Ext.getCmp('idUp2').disable();
								Ext.getCmp('idUp2').setValue(rspObj.msg.id);
								updTermForm2.getForm().findField("accessCodeUp2").disable();
								Ext.getCmp('equipCodeUp2').disable();
					            updTermForm2.getForm().findField("accessCodeUp2").setValue(rspObj.msg.accessCode);
					            Ext.getCmp('equipCodeUp2').setValue(rspObj.msg.equipCode);
					            Ext.getCmp('equipNameUp2').setValue(rspObj.msg.equipName);
					            updTermForm2.getForm().findField("equipTypeUp2").disable();
					            updTermForm2.getForm().findField("equipTypeUp2").setValue(rspObj.msg.equipType);
					            Ext.getCmp('equipIpUp2').setValue(rspObj.msg.equipIp);
					            Ext.getCmp('note1Up2').setValue(rspObj.msg.note1);
					            Ext.getCmp('note2Up2').setValue(rspObj.msg.note2);
					            Ext.getCmp('installDateUp2').setValue(strdate(rspObj.msg.installDate) );	   
							}else if(rspObj.msg.equipType=='3'){
								updTermWin3.show();
								updTermWin3.center();
								Ext.getCmp('idUp3').disable();
								Ext.getCmp('idUp3').setValue(rspObj.msg.id);
								updTermForm3.getForm().findField("accessCodeUp3").disable();
								Ext.getCmp('equipCodeUp3').disable();
					            updTermForm3.getForm().findField("accessCodeUp3").setValue(rspObj.msg.accessCode);
					            Ext.getCmp('equipCodeUp3').setValue(rspObj.msg.equipCode);
					            Ext.getCmp('equipNameUp3').setValue(rspObj.msg.equipName);
					            updTermForm3.getForm().findField("equipTypeUp3").disable();
					            updTermForm3.getForm().findField("equipTypeUp3").setValue(rspObj.msg.equipType);
					            Ext.getCmp('installDateUp3').setValue(strdate(rspObj.msg.installDate) );	   
							}         
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					}
				});
				}
		};
		var updTermForm1 = new Ext.form.FormPanel({
	        frame: true,
	        autoHeight: true,
	        width: 500,
	        labelWidth: 85,
	        waitMsgTarget: true,
	            items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden : true,
	       			items: [{
	       	            xtype: 'textnotnull',
	       	            labelStyle: 'padding-left: 5px',
	       	            fieldLabel: 'id*',
	       	            maxLength: 20,
	       	            id: 'idUp1',
	       	            name: 'idUp1',
	       	            width: 300,
	       	       }]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '门禁编码',
						methodName: 'getTdAccesses2',
						hiddenName: 'accessCodeUp1',
						blankText: '请选择门禁',
						emptyText: "--请选择门禁--",
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
						fieldLabel: '终端设备编码*',
						id: 'equipCodeUp1',
						name: 'equipCodeUp1',
						width:300,
						regex: /^[a-zA-Z0-9]{4}$/,
	     			    regexText: '编码必须由4位数字或字母组成'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '设备类型*',
						methodName: 'getRailAccessEquipType',
						hiddenName: 'equipTypeUp1',
						blankText: '请选择设备类型',
						emptyText: "--请选择设备类型--",
						allowBlank: false,
						editable: false,
						width:300
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       	            xtype: 'checkboxgroup',
	       	            width:380,
	       	            fieldLabel: '防区',
	       	            labelStyle: 'padding-left:5px',
	       	            vertical: true,
	       	            id: 'equipName11',
						name:'equipName11',
	       	            items: [{
							boxLabel: '烟感-外', 
							name: 'equipName11', 
							inputValue:0
						},{
							boxLabel: '烟感-内', 
							name: 'equipName11', 
							inputValue:1
						},{
							boxLabel: '红外-外', 
							name: 'equipName11', 
							inputValue:2
						},{
							boxLabel: '红外-内', 
							name: 'equipName11', 
							inputValue:3
						},{
							boxLabel: ' 防 拆', 
							name: 'equipName11', 
							inputValue:4
						},{
							boxLabel: '门磁', 
							name: 'equipName11', 
							inputValue:5
						}]
	       	        }]
				},{
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		   			items: [{
						xtype: 'datetimefield',
						width: 300,
						labelStyle: 'padding-left: 5px',
						id: 'installDateUp1',
						name: 'installDateUp1',
						format: 'Y-m-d H:m:s',
						altFormats: 'Y-m-d H:m:s',
						vtype: 'daterange',
						fieldLabel: '安装时间*',
						editable: false
		   			  }]
				}],
	    });
		var updTermWin1 = new Ext.Window({
	        title: '修改终端设备信息',
	        initHidden: true,
	        header: true,
	        frame: true,
	        closable: false,
	        modal: true,
	        width: 500,
	        autoHeight: true,
	        layout: 'fit',
	        items: [updTermForm1],
	        buttonAlign: 'center',
	        closeAction: 'hide',
	        iconCls: 'edit',
	        resizable: false,
	        buttons: [{
	            text: '提交',
	            handler: function() {
	            	if(updTermForm1.getForm().isValid()) {
	            		var submitValues = updTermForm1.getForm().getValues();  
						for (var param in submitValues) {  
							if (updTermForm1.getForm().findField(param) && updTermForm1.getForm().findField(param).emptyText == submitValues[param]) {  
								updTermForm1.getForm().findField(param).setValue(' ');  
							}  
						}
						updTermForm1.getForm().submit({
							url: 'T120103Action.asp?method=edit2',
							waitMsg: '正在提交，请稍后......',
							success: function(form,action) {
								updTermWin1.hide();
								showSuccessMsg(action.result.msg,updTermForm1);
								updTermForm1.getForm().reset();
								grid.getStore().reload();
							},
							failure: function(form,action) {
								showErrorMsg(action.result.msg,updTermForm1);
							},
							
							params: {
								equipId: Ext.getCmp('idUp1').getValue(),
								accessCode: updTermForm1.getForm().findField("accessCodeUp1").getValue(),
								equipCode: Ext.getCmp('equipCodeUp1').getValue(),
								//equipName:Ext.getCmp('equipName11').getValue(),
								equipType : updTermForm1.getForm().findField("equipTypeUp1").getValue(),
								installDate : updTermForm1.getForm().findField("installDateUp1").getValue(),	
								txnId: '120103',
								subTxnId: '01'
							}
						});
					}
	            }
	        },{
	            text: '关闭',
	            handler: function() {
	                updTermWin1.hide();
	            }
	        }]
	    });
		
		
		var updTermForm2 = new Ext.form.FormPanel({
	        frame: true,
	        autoHeight: true,
	        width: 450,
	        labelWidth: 85,
	        waitMsgTarget: true,
	            items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden : true,
	       			items: [{
	       	            xtype: 'textnotnull',
	       	            labelStyle: 'padding-left: 5px',
	       	            fieldLabel: 'id*',
	       	            maxLength: 20,
	       	            id: 'idUp2',
	       	            name: 'idUp2',
	       	            width: 300,
	       	       }]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '门禁编码',
						methodName: 'getTdAccesses2',
						hiddenName: 'accessCodeUp2',
						blankText: '请选择门禁',
						emptyText: "--请选择门禁--",
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
						fieldLabel: '终端设备编码*',
						id: 'equipCodeUp2',
						name: 'equipCodeUp2',
						width:300,
						regex: /^[a-zA-Z0-9]{4}$/,
	     			    regexText: '编码必须由4位数字或字母组成'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端设备名称*',
						id: 'equipNameUp2',
						name: 'equipNameUp2',
						width:300,
						maxLength: 80
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '设备类型*',
						methodName: 'getRailAccessEquipType',
						hiddenName: 'equipTypeUp2',
						blankText: '请选择设备类型',
						emptyText: "--请选择设备类型--",
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
						fieldLabel: '设备IP*',
						id: 'equipIpUp2',
						name: 'equipIpUp2',
						width:300,
						maxLength: 40
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '用户名*',
						id: 'note1Up2',
						name: 'note1Up2',
						width:300,
						maxLength: 20
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '密码*',
						id: 'note2Up2',
						name: 'note2Up2',
						width:300,
						maxLength: 40
		        	}]
				},{
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		   			items: [{
						xtype: 'datetimefield',
						width: 300,
						labelStyle: 'padding-left: 5px',
						id: 'installDateUp2',
						name: 'installDateUp2',
						format: 'Y-m-d H:m:s',
						altFormats: 'Y-m-d H:m:s',
						vtype: 'daterange',
						fieldLabel: '安装时间*',
						editable: false
		   			  }]
				}],
	    });
		var updTermWin2 = new Ext.Window({
	        title: '修改终端设备信息',
	        initHidden: true,
	        header: true,
	        frame: true,
	        closable: false,
	        modal: true,
	        width: 450,
	        autoHeight: true,
	        layout: 'fit',
	        items: [updTermForm2],
	        buttonAlign: 'center',
	        closeAction: 'hide',
	        iconCls: 'edit',
	        resizable: false,
	        buttons: [{
	            text: '提交',
	            handler: function() {
	            	if(updTermForm2.getForm().isValid()) {
						
						var submitValues = updTermForm2.getForm().getValues();  
						for (var param in submitValues) {  
							if (updTermForm2.getForm().findField(param) && updTermForm2.getForm().findField(param).emptyText == submitValues[param]) {  
								updTermForm2.getForm().findField(param).setValue(' ');  
							}  
						}
						updTermForm2.getForm().submit({
							url: 'T120103Action.asp?method=edit',
							waitMsg: '正在提交，请稍后......',
							success: function(form,action) {
								updTermWin2.hide();
								showSuccessMsg(action.result.msg,updTermForm2);
								updTermForm2.getForm().reset();
								grid.getStore().reload();
							},
							failure: function(form,action) {
								showErrorMsg(action.result.msg,updTermForm2);
							},
							params: {
								equipId: Ext.getCmp('idUp2').getValue(),
								accessCode: updTermForm2.getForm().findField("accessCodeUp2").getValue(),
								equipCode: Ext.getCmp('equipCodeUp2').getValue(),
								equipName: Ext.getCmp('equipNameUp2').getValue(),
								equipType : updTermForm2.getForm().findField("equipTypeUp2").getValue(),
								equipIp: Ext.getCmp('equipIpUp2').getValue(),
								note1: Ext.getCmp('note1Up2').getValue(),
								note2: Ext.getCmp('note2Up2').getValue(),
								installDate : updTermForm2.getForm().findField("installDateUp2").getValue(),	
								txnId: '120103',
								subTxnId: '01'
							}
						});
					}
	            }
	        },{
	            text: '关闭',
	            handler: function() {
	                updTermWin2.hide();
	            }
	        }]
	    });
		
		
		
		var updTermForm3 = new Ext.form.FormPanel({
	        frame: true,
	        autoHeight: true,
	        width: 450,
	        labelWidth: 85,
	        waitMsgTarget: true,
	            items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden : true,
	       			items: [{
	       	            xtype: 'textnotnull',
	       	            labelStyle: 'padding-left: 5px',
	       	            fieldLabel: 'id*',
	       	            maxLength: 20,
	       	            id: 'idUp3',
	       	            name: 'idUp3',
	       	            width: 300,
	       	       }]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '门禁编码',
						methodName: 'getTdAccesses2',
						hiddenName: 'accessCodeUp3',
						blankText: '请选择门禁',
						emptyText: "--请选择门禁--",
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
						fieldLabel: '终端设备编码*',
						id: 'equipCodeUp3',
						name: 'equipCodeUp3',
						width:300,
						regex: /^[a-zA-Z0-9]{4}$/,
	     			    regexText: '编码必须由4位数字或字母组成'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端设备名称*',
						id: 'equipNameUp3',
						name: 'equipNameUp3',
						width:300,
						maxLength: 80
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '设备类型*',
						methodName: 'getRailAccessEquipType',
						hiddenName: 'equipTypeUp3',
						blankText: '请选择设备类型',
						emptyText: "--请选择设备类型--",
						allowBlank: false,
						editable: false,
						width:300
		        	}]
				},{
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		   			items: [{
						xtype: 'datetimefield',
						width: 300,
						labelStyle: 'padding-left: 5px',
						id: 'installDateUp3',
						name: 'installDateUp3',
						format: 'Y-m-d H:m:s',
						altFormats: 'Y-m-d H:m:s',
						vtype: 'daterange',
						fieldLabel: '安装时间*',
						editable: false
		   			  }]
				}],
	    });
		var updTermWin3 = new Ext.Window({
	        title: '修改终端设备信息',
	        initHidden: true,
	        header: true,
	        frame: true,
	        closable: false,
	        modal: true,
	        width: 450,
	        autoHeight: true,
	        layout: 'fit',
	        items: [updTermForm3],
	        buttonAlign: 'center',
	        closeAction: 'hide',
	        iconCls: 'edit',
	        resizable: false,
	        buttons: [{
	            text: '提交',
	            handler: function() {
	            	if(updTermForm3.getForm().isValid()) {
						
						var submitValues = updTermForm3.getForm().getValues();  
						for (var param in submitValues) {  
							if (updTermForm3.getForm().findField(param) && updTermForm3.getForm().findField(param).emptyText == submitValues[param]) {  
								updTermForm3.getForm().findField(param).setValue(' ');  
							}  
						}
						updTermForm3.getForm().submit({
							url: 'T120103Action.asp?method=edit',
							waitMsg: '正在提交，请稍后......',
							success: function(form,action) {
								updTermWin3.hide();
								showSuccessMsg(action.result.msg,updTermForm3);
								updTermForm3.getForm().reset();
								grid.getStore().reload();
							},
							failure: function(form,action) {
								showErrorMsg(action.result.msg,updTermForm3);
							},
							params: {
								equipId: Ext.getCmp('idUp3').getValue(),
								accessCode: updTermForm3.getForm().findField("accessCodeUp3").getValue(),
								equipCode: Ext.getCmp('equipCodeUp3').getValue(),
								equipName: Ext.getCmp('equipNameUp3').getValue(),
								equipType : updTermForm3.getForm().findField("equipTypeUp3").getValue(),
								installDate : updTermForm3.getForm().findField("installDateUp3").getValue(),	
								txnId: '120103',
								subTxnId: '01'
							}
						});
					}
	            }
	        },{
	            text: '关闭',
	            handler: function() {
	                updTermWin3.hide();
	            }
	        }]
	    });
		
		
		
		
		
		
		
		
		
		
		
		var openMenu = {
				text : '启用',
				width : 60,
				iconCls : 'accept',
				disabled : true,
				handler : function() {
					if(grid.getSelectionModel().hasSelection()) {
		        		var rec = grid.getSelectionModel().getSelected();
						var equipCode = rec.get('equipCode');
						showConfirm('确定要开启该终端设备吗？终端设备编号：' + equipCode,grid,function(bt) {
							if(bt == "yes") {
								Ext.Ajax.requestNeedAuthorise({
									url: 'T120103Action.asp?method=open',
									success: function(rsp,opt) {
//										alert(JSON.stringify(rsp)+"  >><<  "+JSON.stringify(opt));
										var rspObj = Ext.decode(rsp.responseText);
										if(rspObj == "00"){
//											Ext.MessageBox.alert('操作提示', equipCode+'终端设备已成功开启!');
											showSuccessMsg(equipCode+'终端设备已成功成功!',grid);//showErrorMsg(action.result.msg,addMhForm)
											grid.getStore().reload();
										}else if(rspObj == "-1"){
											Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
										}
									},
									params: { 
										equipCode:equipCode,
										txnId: '120103',
										subTxnId: '03'
									}
								});
							}
						});
		        	}else{
		        		Ext.Msg.alert('提示', '请选择一个终端设备！');
		        	}
				}
		};
		var stopMenu = {
				text : '停用',
				width : 60,
				iconCls : 'stop',
				disabled : true,
				handler : function() {
					if(grid.getSelectionModel().hasSelection()) {
		        		var rec = grid.getSelectionModel().getSelected();
						var equipCode = rec.get('equipCode');
						showConfirm('确定要停用该终端设备吗？终端设备编号：' + equipCode,grid,function(bt) {
							if(bt == "yes") {
								Ext.Ajax.requestNeedAuthorise({
									url: 'T120103Action.asp?method=close',
									success: function(rsp,opt) {
//										alert(JSON.stringify(rsp)+"  >><<  "+JSON.stringify(opt));
										var rspObj = Ext.decode(rsp.responseText);
										if(rspObj == "00"){
											Ext.MessageBox.alert('操作提示', equipCode+'终端设备已被停用!');
											showSuccessMsg(equipCode+'终端设备已成功停用!',grid);//showErrorMsg(action.result.msg,addMhForm)
											grid.getStore().reload();
										}else if(rspObj == "-1"){
											Ext.MessageBox.alert('操作提示', '操作失败，请稍后重试!');
										}
									},
									params: { 
										equipCode: equipCode,
										txnId: '120103',
										subTxnId: '04'
									}
								});
							}
						});
		        	}else{
		        		Ext.Msg.alert('提示', '请选择一个终端设备！');
		        	}
					//window.location.href = Ext.contextPath+ '/page/mchnt/T2010102.jsp?mchntId='+ mchntGrid.getSelectionModel().getSelected().get('mchtNo');
				}
		};
		var detailMenu = {
				text : '查看详细信息',
				width : 100,
				iconCls : 'detail',
				disabled : true,
				handler : function() {
					var	selectedRecord = grid.getSelectionModel().getSelected();
		            if(selectedRecord == null)
		            {
		                showAlertMsg("没有选择记录",grid);
		                return;
		            }
		            Ext.Ajax.request({
		            	url : 'T120103Action_getData.asp',
						params : {
							equipCode : selectedRecord.get('equipCode')
						},
						success : function(rsp, opt) {
							//showErrorMsg(rspObj.msg,grid);
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								detTermWin.show();
								detTermWin.center();
								Ext.getCmp('equipCodeDet').disable();
					            Ext.getCmp('accessCodeDet').setValue(rspObj.msg.accessCode);
					            Ext.getCmp('equipCodeDet').setValue(rspObj.msg.equipCode);
					            Ext.getCmp('equipNameDet').setValue(rspObj.msg.equipName);
					            Ext.getCmp('equipTypeDet').setValue(rspObj.msg.equipType);
					            Ext.getCmp('mfrsOrgDet').setValue(rspObj.msg.mfrsOrg);
					            Ext.getCmp('mfrsTelDet').setValue(rspObj.msg.mfrsTel);
					            Ext.getCmp('expirationDateDet').setValue(rspObj.msg.expirationDate);
					            Ext.getCmp('installDateDet').setValue(rspObj.msg.installDate );
					            Ext.getCmp('examPeriodDet').setValue(rspObj.msg.examPeriod);
					            Ext.getCmp('lastExamDet').setValue(rspObj.msg.lastExam );
					            Ext.getCmp('equipStatusDet').setValue(equipStatusRender(rspObj.msg.equipStatus));
					            Ext.getCmp('addUserDet').setValue(rspObj.msg.addUser);
					            Ext.getCmp('addDateDet').setValue(rspObj.msg.addDate);
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
						}
					});
				}
			};
			var detTermForm = new Ext.form.FormPanel({
		        frame: true,
		        autoHeight: true,
		        width: 1000,
		        labelWidth: 110,
		        waitMsgTarget: true,
		        layout: 'column',
		        items: [{
		        	layout:'column',
		            items: [{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
		       	            xtype: 'displayfield',
		       	            labelStyle: 'padding-left: 5px',
		       	            fieldLabel: '门禁编码*',
		       	            maxLength: 20,
		       	            id: 'accessCodeDet',
		       	            name: 'accessCodeDet',
		       	            width: 300,
		       	       }]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '终端设备编码*',
							id: 'equipCodeDet',
							name: 'equipCodeDet',
							width:300,
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '终端设备名称*',
							id: 'equipNameDet',
							name: 'equipNameDet',
							width:300,
							maxLength: 80
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '设备类型*',
							id: 'equipTypeDet',
							name: 'equipTypeDet',
							width:300,
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '供应商*',
							width:300,
							id: 'mfrsOrgDet',
							name: 'mfrsOrgDet'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '供应商电话*',
							width:300,
							id: 'mfrsTelDet',
							name: 'mfrsTelDet'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '设备有效期*',
							width:300,
							id: 'expirationDateDet',
							name: 'expirationDateDet'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '安装时间*',
							width:300,
							id: 'installDateDet',
							name: 'installDateDet'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '检查周期（天）*',
							width:300,
							id: 'examPeriodDet',
							name: 'examPeriodDet'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '最后一次检修时间*',
							width:300,
							id: 'lastExamDet',
							name: 'lastExamDet'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '设备状态*',
							width:300,
							id: 'equipStatusDet',
							name: 'equipStatusDet'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '创建人*',
							width:300,
							id: 'addUserDet',
							name: 'addUserDet'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '创建时间*',
							width:300,
							id: 'addDateDet',
							name: 'addDateDet'
			        	}]
					}],
		        }]
		    });
			var detTermWin = new Ext.Window({
		        title: '查看门禁详细信息',
		        initHidden: true,
		        header: true,
		        frame: true,
		        closable: false,
		        modal: true,
		        width: 1000,
		        autoHeight: true,
		        layout: 'fit',
		        items: [detTermForm],
		        buttonAlign: 'center',
		        closeAction: 'hide',
		        iconCls: 'edit',
		        resizable: false,
		        buttons: [{
		            text: '关闭',
		            handler: function() {
		            	detTermWin.hide();
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
		width: 450,
		autoHeight: true,
		items: [{
    		columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'dynamicCombo',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '门禁编码',
				methodName: 'getTdAccesses2',
				hiddenName: 'accessCodeselect',
				blankText: '请选择门禁',
				emptyText: "--请选择门禁--",
				allowBlank: true,
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
				fieldLabel: '终端设备编码',
				methodName: 'getTdAccessesEquip',
				hiddenName: 'equipCodeselect',
				blankText: '请选择终端设备',
				emptyText: "--请选择终端设备--",
				allowBlank: true,
				editable: false,
				width:300
        	}]
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		iconCls : 'query',
		width: 450,
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
            	grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[4].disable();
//                grid.getTopToolbar().items.items[6].disable();
//                grid.getTopToolbar().items.items[8].disable();
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
		text: '导出',
		width: 60,
		id:'download',
		iconCls: 'download',
		handler:function() {
			//Ext.MessageBox.alert('提示', '你点了导出报表按钮!');
			excelDown.show();
		}
	};
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
			   var equipCode=  queryForm.getForm().findField("equipCodeselect").getValue();
			   var accessCode=  queryForm.getForm().findField("accessCodeselect").getValue();
				var param = "?a=1";
				if(accessCode){
					param = param + "&accessCode="+accessCode;
				}
				if(equipCode){
					param = param + "&equipCode="+equipCode;
				}
				window.location.href = Ext.contextPath +"/exportExcelT120103.asp"+param;
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
	
	//终端设备添加表单
	var brhInfoForm = new Ext.form.FormPanel({
		frame: true,
		height: 250,
        width: 500,
        labelWidth: 85,
        waitMsgTarget: true,
        	items: [{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '门禁编码*',
					methodName: 'getTdAccesses2',
					hiddenName: 'accessCode',
					blankText: '请选择门禁编码',
					emptyText: "--请选择门禁编码--",
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
					fieldLabel: '终端设备编码*',
					id: 'equipCode',
					name: 'equipCode',
					width:300,
       	            regex: /^[a-zA-Z0-9]{4}$/,
     			    regexText: '编码必须由4位数字或字母组成'
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '终端设备名称*',
					id: 'equipName',
					name: 'equipName',
					width:300,
					maxLength: 80
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'dynamicCombo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '设备类型*',
					methodName: 'getRailAccessEquipType',
					hiddenName: 'equipType',
					blankText: '请选择设备类型',
					emptyText: "--请选择设备类型--",
					allowBlank: false,
					editable: false,
					width:300,
					select:function(combo, record, index){
						if(combo==0){
						Ext.getCmp('note33').setVisible(true);
						Ext.getCmp('equipIp2').setVisible(true);
						Ext.getCmp('note11').setVisible(true);
						Ext.getCmp('note22').setVisible(true);
						}else if(combo==1){
						Ext.getCmp('note33').setVisible(true);
				        Ext.getCmp('equipIp2').setVisible(false);
						Ext.getCmp('note11').setVisible(false);
						Ext.getCmp('note22').setVisible(false);
						}else if(combo==2){
						Ext.getCmp('note33').setVisible(false);
						Ext.getCmp('equipIp2').setVisible(true);
						Ext.getCmp('note11').setVisible(true);
						Ext.getCmp('note22').setVisible(true);
						}else if(combo==3){
						Ext.getCmp('note33').setVisible(false);
						Ext.getCmp('equipIp2').setVisible(false);
						Ext.getCmp('note11').setVisible(false);
						Ext.getCmp('note22').setVisible(false);
						}
					}
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	        	id: 'note33',
				name:'note33',
       			items: [{
       	            xtype: 'checkboxgroup',
       	            width:380,
       	            fieldLabel: '防区',
       	            labelStyle: 'padding-left:5px',
       	            vertical: true,
       	            id: 'note3',
					name:'note3',
       	            items: [{
       	            	labelStyle: 'padding-left:5px',
						boxLabel: '烟感-外', 
						name: 'note3', 
						inputValue:1, 
					//	checked: true
					},{
						boxLabel: '烟感-内', 
						name: 'note3', 
						inputValue:2
					},{
						boxLabel: '红外-外', 
						name: 'note3', 
						inputValue:3
					},{
						boxLabel: '红外-内', 
						name: 'note3', 
						inputValue:4
					},{
						boxLabel: ' 防 拆', 
						name: 'note3', 
						inputValue:5
					},{
						boxLabel: '门磁', 
						name: 'note3', 
						inputValue:6
					}]
       	        }]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	        	id: 'equipIp2',
				name: 'equipIp2',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '设备IP',
					allowBlank: true,
					id: 'equipIp',
					name: 'equipIp',
					width:300,
					maxLength: 40
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	        	id: 'note11',
				name: 'note11',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '用户名',
					allowBlank: true,
					id: 'note1',
					name: 'note1',
					width:300,
					maxLength: 20
	        	}]
			},{
        		columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	        	id: 'note22',
				name: 'note22',
       			items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '密码',
					allowBlank: true,
					id: 'note2',
					name: 'note2',
					width:300,
					maxLength: 40
	        	}]
			},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
	   			items: [{
					xtype: 'datetimefield',
					width: 300,
					labelStyle: 'padding-left: 5px',
					id: 'installDate',
					name: 'installDate',
					format: 'Y-m-d H:m:s',
					altFormats: 'Y-m-d H:m:s',
					vtype: 'daterange',
					fieldLabel: '安装时间*',
					editable: false
	   			  }]
			}],
	});
	
	//终端设备添加窗口
	var brhWin = new Ext.Window({
		title: '新增终端设备',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 500,
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
			//btn.disable();
			frm.submit({
				url: 'T120103Action.asp?method=add',
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
				},
				params: {
					txnId: '120103',
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
	var openMenu2 = {
			text : '查看监控',
			width : 60,
			disabled : true,
			iconCls : 'accept',
			handler : function() {
				
				var	selectedRecord = grid.getSelectionModel().getSelected();
	            if(selectedRecord == null){
	                showAlertMsg("没有选择记录",grid);
	                return;
	            }
	            var ip = selectedRecord.get("equipIp");
	            var user = selectedRecord.get("note1");
	            var pw = selectedRecord.get("note2");
	            var ipnums = ip.split(".");
	            var newIp = "";
	            for(var j= 0;j<ipnums.length;j++){
					if(newIp==""){
						newIp = ipnums[j];
					}else{
						newIp = newIp+"*"+ipnums[j];
					}
				}
	            var htmlUrl= Ext.projectPath+"video.jsp?Param="+newIp+"*"+user+"*"+pw;
//				alert(htmlUrl);
				window.open("openIE:"+htmlUrl, "_self");

			}
	};
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(editMenu);
	menuArr.push('-');
//	menuArr.push(openMenu);
//	menuArr.push('-');
//	menuArr.push(stopMenu);
//	menuArr.push('-');
//	menuArr.push(detailMenu);
//	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
//	menuArr.push('-');
//	menuArr.push(openMenu2);
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=railAccessEquipInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: ''
        },[
            {name: 'id',mapping: 'id'},
            {name: 'accessCode',mapping: 'accessCode'},
            {name: 'equipCode',mapping: 'equipCode'},
            {name: 'equipName',mapping: 'equipName'},
            {name: 'equipTypeName',mapping: 'equipTypeName'},
            {name: 'equipIp',mapping: 'equipIp'},
            {name: 'note1',mapping: 'note1'},
            {name: 'note2',mapping: 'note2'},
            {name: 'installDate',mapping: 'installDate'},
            {name: 'equipStatus',mapping: 'equipStatus'},
            {name: 'addUser',mapping: 'addUser'},
            {name: 'addDate',mapping: 'addDate'},
            {name: 'equipType',mapping: 'equipType'}
            ])
    });
	termStore.load();
	
	function equipStatusRender(val) {
		if(val == '正常') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="正常"/>正常';
		} else if(val == '故障') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="故障"/>故障';
		} else if(val == '未连接') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="未连接"/>未连接';
		} 
		return '状态未知';
	}
	
	function strdate(date) {
		var date = new Date(date);
		Y = date.getFullYear() + '-';
		M = (date.getMonth() < 10 ? '0'+(date.getMonth()+1) : date.getMonth()) + '-';
		D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
		h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
		m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
		s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds()) + '';
		return  Y+M+D+h+m+s;
	    }
	
	
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            accessCode: queryForm.getForm().findField("accessCodeselect").getValue(),
            equipCode: queryForm.getForm().findField("equipCodeselect").getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
    	{header: 'id',dataIndex: 'id',width: 100,hidden : true},
    	{header: '门禁编码',dataIndex: 'accessCode',sortable: true,width: 100},
		{id: 'equipCode',header: '终端设备编码',dataIndex: 'equipCode',sortable: true,width: 100},
		{header: '终端设备名称',dataIndex: 'equipName',sortable: true,width: 120},
		{header: '设备类型',dataIndex: 'equipTypeName',sortable: true,width: 100},
		{header: '设备IP',dataIndex: 'equipIp',sortable: true,width: 100},
		{header: '用户名',dataIndex: 'note1',sortable: true,width: 80},
		{header: '密码',dataIndex: 'note2',sortable: true,width: 100},
		{header: '安装时间',dataIndex: 'installDate',sortable: true,width: 150},
		{header: '设备状态',dataIndex: 'equipStatus',sortable: true,renderer: equipStatusRender,width: 80},
		{header: '创建人',dataIndex: 'addUser',sortable: true,width: 80},
		{header: '创建时间',dataIndex: 'addDate',sortable: true,width: 150},
		{header: '类型',dataIndex: 'equipType',sortable: true,width: 150,hidden:true}
    ]);
	// 工单信息列表
    var grid = new Ext.grid.GridPanel({
        title: '终端设备管理',
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
           
            	var equipType=rec.get('equipType');
                grid.getTopToolbar().items.items[2].enable();
                grid.getTopToolbar().items.items[4].enable();
                if(equipType==3){
                	grid.getTopToolbar().items.items[10].enable();
                }else{
                	grid.getTopToolbar().items.items[10].disable();
                }

            } else {
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[4].disable();
                grid.getTopToolbar().items.items[10].disable();
            }
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})