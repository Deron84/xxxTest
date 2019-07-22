Ext.onReady(function() {
	
	//资料分类
	var edTypeStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['01','营业执照'],['02','业务申请表'],['03','其他'],
		       ['04','银行卡'],['05','商户协议'],['06','店铺照片'],
		       ['07','法人/负责人身份证']],
		reader: new Ext.data.ArrayReader()
	});
	//资料数量
	var upFileCountStore= new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['1','1'],['2','2'],['3','3'],['4','4'],['5','5']],
		reader: new Ext.data.ArrayReader()
	});
	var uploadForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		autoHeight: true,
		renderTo: Ext.getBody(),
		//style: 'padding-top: 80px',
		waitMsgTarget: true,
	    fileUpload: true,
	    enctype:'multipart/form-data',
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items:[{
			title: '上传信息',
            id: 'infoNew1',
            layout: 'column',
            height:180,
            frame: true,
            items :[{
            	columnWidth : .33,
			    layout : 'form',
			    style: 'padding-top: 20px',
			    items :[{
			    	xtype:'textnotnull',
			     	fieldLabel: '拓展人编号*',
	            	labelStyle: 'padding-left: 5px',
	        	    allowBlank: false,
	        	    id: 'operId',
	        	    width: 220,
	        	    maxLength:20,
		            emptyText: '请输入拓展人编号'
			    }]
            },{
            	columnWidth : .33,
    			layout : 'form',
    			style: 'padding-top: 20px',
    			items :[{
    				xtype:'textnotnull',
    				fieldLabel: '外部商户号*',
    	         	labelStyle: 'padding-left: 5px',
    	        	allowBlank: false,
    	        	id: 'outMchntId',
    	        	width: 220,
    	        	maxLength:64,
    		        emptyText: '请输入外部商户号'
    			}]
            },{
            	columnWidth : .33,
    			layout : 'form',
    			style: 'padding-top: 20px',
    			items :[{
    				xtype:'textnotnull',
    				fieldLabel: '民生商户号*',
    	         	labelStyle: 'padding-left: 5px',
    	        	allowBlank: false,
    	        	id: 'cmbcMchntId',
    	        	width: 220,
    	        	maxLength:21,
    		        emptyText: '请输入民生商户号'
    			}]
            },{
            	columnWidth : .33,
    			layout : 'form',
    			items :[{
    				xtype: 'combo',
    				store: edTypeStore,
    				labelStyle: 'padding-left: 5px',
    				displayField: 'displayField',
    				valueField: 'valueField',
    				emptyText: '请选择',
    				id:'edType',
    				mode: 'local',
    				triggerAction: 'all',
    				forceSelection: true,
    				typeAhead: true,
    				selectOnFocus: true,
    				editable: false,
    				allowBlank: true,
    				fieldLabel: '资料分类',
    				anchor: '70%',
    				value: '01',
    				listWidth: 110
    			}]					
            },{
            	columnWidth : .66,
    			layout : 'form',
    			items :[{
    				xtype: 'combo',
    				store: upFileCountStore,
    				labelStyle: 'padding-left: 5px',
    				displayField: 'displayField',
    				valueField: 'valueField',
    				emptyText: '请选择',
    				id:'upFileCount',
    				mode: 'local',
    				triggerAction: 'all',
    				forceSelection: true,
    				typeAhead: true,
    				selectOnFocus: true,
    				editable: false,
    				allowBlank: true,
    				fieldLabel: '资料数量',
    				anchor: '30%',
    				value: '1',
    				listWidth: 110,
    				listeners:{
    					'select': function(){
    						var upFileCount = uploadForm.getForm().findField('upFileCount').getValue();
    						if(upFileCount==1){
    							uploadForm.findById('upload1').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload2').getEl().up('.x-form-item').setDisplayed(false);
    							uploadForm.findById('upload3').getEl().up('.x-form-item').setDisplayed(false);
    							uploadForm.findById('upload4').getEl().up('.x-form-item').setDisplayed(false);
    							uploadForm.findById('upload5').getEl().up('.x-form-item').setDisplayed(false);
    						} else if (upFileCount==2) {
    							uploadForm.findById('upload1').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload2').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload3').getEl().up('.x-form-item').setDisplayed(false);
    							uploadForm.findById('upload4').getEl().up('.x-form-item').setDisplayed(false);
    							uploadForm.findById('upload5').getEl().up('.x-form-item').setDisplayed(false);
							} else if (upFileCount==3) {
    							uploadForm.findById('upload1').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload2').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload3').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload4').getEl().up('.x-form-item').setDisplayed(false);
    							uploadForm.findById('upload5').getEl().up('.x-form-item').setDisplayed(false);
							} else if (upFileCount==4) {
    							uploadForm.findById('upload1').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload2').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload3').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload4').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload5').getEl().up('.x-form-item').setDisplayed(false);
							} else if (upFileCount==5) {
    							uploadForm.findById('upload1').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload2').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload3').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload4').getEl().up('.x-form-item').setDisplayed(true);
    							uploadForm.findById('upload5').getEl().up('.x-form-item').setDisplayed(true);
							}
    					}
    				}
    			}]					
            },{
            	columnWidth : 0.33,
            	layout : 'form',
    			style: 'padding-left: 5px',
    			enctype:'multipart/form-data',
    			fileUpload: true,
    			items :[{
    				xtype: 'fileuploadfield',
    				fieldLabel: '文 件1',
    				buttonText:'浏览',
    				width:'220',
    				style: 'padding-left: 5px',
    				id: 'upload1',   
    			    name: 'upload'
    			}]
            },{
            	columnWidth : 0.33,
            	layout : 'form',
    			style: 'padding-left: 5px',
    			enctype:'multipart/form-data',
    			fileUpload: true,
    			items :[{
    				xtype: 'fileuploadfield',
    				fieldLabel: '文 件2',
    				buttonText:'浏览',
    				width:'220',
    				style: 'padding-left: 5px',
    				id: 'upload2',   
    			    name: 'upload'
    			}]
    		},{
    			columnWidth : 0.33,
    			layout : 'form',
    			style: 'padding-left: 5px',
    			enctype:'multipart/form-data',
    			fileUpload: true,
    			items :[{
    				xtype: 'fileuploadfield',
    				fieldLabel: '文 件3',
    				buttonText:'浏览',
    				width:'220',
    				style: 'padding-left: 5px',
    				id: 'upload3',   
    			    name: 'upload'
    			}]
    		},{
            	columnWidth : 0.33,
    			layout : 'form',
    			style: 'padding-left: 5px',
    			fileUpload: true,
    			items :[{
    				xtype: 'fileuploadfield',
    				fieldLabel: '文 件4',
    				buttonText:'浏览',
    				width:'220',
    				style: 'padding-left: 5px',
    				id: 'upload4',   
    			    name: 'upload'
    			}]					
            },{
            	columnWidth : 0.33,
    			layout : 'form',
    			style: 'padding-left: 5px',
    			fileUpload: true,
    			items :[{
    				xtype: 'fileuploadfield',
    				fieldLabel: '文 件5',
    				buttonText:'浏览',
    				width:'220',
    				style: 'padding-left: 5px',
    				id: 'upload5',   
    			    name: 'upload'
    			}]					
            }]
		},{
			title: '返回信息',
            id: 'infoNew2',
            layout: 'column',
            height:220,
            frame: true,
            items :[{
            	columnWidth : 1,
    			layout : 'form',
    			style: 'padding-left: 5px',
    			style: 'padding-top: 20px',
    			fileUpload: true,
    			items :[{
    				xtype:'textarea',
    				id:'response',
    		        grow:true,
    		        growMin:140,
    		        growMax:140,
    		        fieldLabel: '返回内容',
    		        width: 400
    			}]					
            }]
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '上传',
			handler: function() {
				if(!uploadForm.getForm().isValid()) {
					return;
				}
				/*var upload1FilePath = Ext.getCmp('upload1').getValue();
                if(!CheckFileExt(upload1FilePath,/.jpg|.gif|.png|.bmp/i))
                {
                    alert('错误','您上传的文件不是图片类型，请重新选择！');
                    return;
                }*/
				uploadForm.getForm().submit({
					url: 'dataUploadAction.asp?method=upload',
					method: 'post',
					waitMsg: '正在请求上传，请稍后......',
					success: function(form,action) {
						uploadForm.getForm().findField("response").setValue(action.result.msg);
					},
					failure: function(form,action) {
						uploadForm.getForm().findField("response").setValue(action.result.msg);
					},
					params: {
						txnId: '20110',
						subTxnId: '00'
					}
				});
			}
		}]
	});
	uploadForm.findById('upload2').getEl().up('.x-form-item').setDisplayed(false);
	uploadForm.findById('upload3').getEl().up('.x-form-item').setDisplayed(false);
	uploadForm.findById('upload4').getEl().up('.x-form-item').setDisplayed(false);
	uploadForm.findById('upload5').getEl().up('.x-form-item').setDisplayed(false);
})