Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	var addPig = {
			text: '上传图片',
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
	var showPig = {
			text: '查看工单图片',
			width: 85,
			disabled: false,
			iconCls: 'download',
			handler:function() {
				var	selectedRecord = grid.getSelectionModel().getSelected();
	            if(selectedRecord == null)
	            {
	                showAlertMsg("没有选择记录",grid);
	                return;
	            }
	            var imgPath = selectedRecord.data.imgPath;
	            html = '<div style="width:100%;height:100%;"><img id="videoSource" style="width:100%;height:100%;" src="'+imgPath+'" /><div>';
	            songPlayer.show();
	            Ext.getCmp("playerPanel").body.update(html); 

			}
	};
	
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
				fieldLabel: '工单编码',
				methodName: 'getTdWork',
				hiddenName: 'workCode',
				blankText: '请选择工单',
				emptyText: "--请选择工单编码--",
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
//            	queryForm.getForm().reset();
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
				var  workCode = queryForm.getForm().findField("workCode").getValue();
	            var imgType= '0';
				var param = "?imgType=0";
				if(workCode){
					param = param + "&workCode="+workCode;
				}
				window.location.href = Ext.contextPath +"/exportExcelT130200.asp"+param;
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
	var brhInfoFormAll1 = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 600,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		fileUpload: true,
		enctype:'multipart/form-data',
		items: [{
			     xtype : 'box',     //类型 
			     fieldLable : '圖片預覽', // 标签的名字
			     width : 400,
			     height : 400,
			      name : 'specialDesc.imgsrc',//ext Store里的 name
			     id : 'imgsrc', //
			     autoEl : {
			       tag : 'img',
			       src : ''
			     }
			 }
			]
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
		items: [{
    		xtype: 'dynamicCombo',
	        methodName: 'getRailWorkInfolList',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '作业工单*',
			hiddenName: 'workCode',
			allowBlank: false,
			editable: false,
			width:300,
			emptyText: "--请选择工单--"
    	},{
        	xtype: 'textnotnull',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '图片描述',
			width:300,
			id: 'note1',
			name: 'note1',
			maxLength: 80,
			allowBlank: true
    	},{
			xtype: 'fileuploadfield',
			fieldLabel: '图片',
			buttonText:'浏览',
			width:'300',
			labelStyle: 'padding-left: 5px',
			id: 'upload',   
		    name: 'upload',
		    allowBlank: false,
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
		}]
	});
	var brhWinAll = new Ext.Window({
		title: '工单上传图片',
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
				var	selectedRecord = grid.getSelectionModel().getSelected();
				if(brhInfoFormAll.getForm().isValid()) {
					//var submitValues = brhInfoFormAll.getForm().getValues();  
					brhInfoFormAll.getForm().submit({
						url: 'T130200Action_upload.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
						},
						failure: function(form,action) {
							if(action.result.code ==200){
								brhWinAll.hide();
								showSuccessMsg(action.result.msg,brhInfoFormAll);
								brhInfoFormAll.getForm().reset();
								grid.getStore().reload();
							}else{
								showErrorMsg(action.result.msg,brhInfoFormAll);
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
	
	var brhWinAll1 = new Ext.Window({
		title: '查看图片',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		height: 400,
		layout: 'fit',
		items: [brhInfoFormAll1],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '关闭',
			handler: function() {
				brhWinAll1.hide(grid);
				brhInfoFormAll1.getForm().reset();
				   Ext.getCmp('imgsrc').initialConfig.autoEl.src ="";
			}
		}]
	});
	 var playerForm=new Ext.Panel({  
	        xtype : 'panel',  
	        id : 'playerPanel',  
	        width:400,  
	        height:400, 
	        html:""
//	        html : '<object classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" type="audio/mpeg" width="310">'  
//	                + '<param name="fileName" value="‪‪‪E:\all_download\mp4\lts12.mp4"/>' +  
//	             '<embed width="420" height="360" type="application/x-mplayer2" src="‪‪‪E:\all_download\mp4\lts12.mp4"></embed></object>'  
	    })
	var songPlayer = new Ext.Window({  
        layout : 'fit',  
        width:400,  
        height:400,  
        modal : true,  
        frame: true,
        closable : false, 
//        html:"",
        items : [playerForm]  ,
    	buttons: [{
			text: '关闭',
			handler: function() {
//				this.close();
				songPlayer.hide();
//				Ext.getCmp('playerPanel').remove();
//				var oVideo = document.getElementById('videoSource');
//				oVideo.pause();
//				  Ext.getCmp('playerPanel').html ="";
				Ext.getCmp("playerPanel").body.update("");  
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
	menuArr.push(showPig);
	menuArr.push('-');
	menuArr.push(addPig);
	menuArr.push('-');
	menuArr.push(queryMenu);
	menuArr.push('-');
	menuArr.push(exportMenu);
	
	function imgTypeRender(imgType) {
		switch(imgType) {
			case '0': return '图片';
			case '1': return '视频';
		}
	}
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=workImgs'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'id'
        },[
            {name: 'warnId',mapping: 'id'},
            {name: 'workCode',mapping: 'workCode'},
            {name: 'workName',mapping: 'workName'},
            {name: 'imgType',mapping: 'imgType'},
            {name: 'note1',mapping: 'note1'},
            {name: 'imgPath',mapping: 'imgPath'},
			{name: 'addUser',mapping: 'addUser'},
			{name: 'addDate',mapping: 'addDate'},
			
        ])
    });
	termStore.load({  
	       params: { start: 0,
	    	         imgType:'0'
	    	        }  
	       });
	
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            workCode: queryForm.getForm().findField("workCode").getValue(),
            imgType:'0'
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
//    	new Ext.grid.RowNumberer(),
    	{id:'warnId',header: '图片id',dataIndex: 'warnId',sortable: true,width: 60,hidden : true},
    	{id:'accessCode',header: '工单编码',dataIndex: 'workCode',sortable: true,width: 120},
		{header: '工单名称',dataIndex: 'workName',sortable: true,width: 150},
		{header: '类型',dataIndex: 'imgType',sortable: true,width: 120},
		{header: '图片描述',dataIndex: 'note1',sortable: true,width: 250},
		{header: '图片保存路径',dataIndex: 'imgPath',sortable: true,width: 250,hidden : true},
		{header: '创建人',dataIndex: 'addUser',sortable: true,width: 150},
		{header: '创建时间',dataIndex: 'addDate',sortable: true,width: 150},
    ]);
	// 仓库信息列表
    var grid = new Ext.grid.GridPanel({
        title: '作业现场图片',
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
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})