Ext.onReady(function() {
	// 菜单集合
	var menuArr = new Array();
	var openMenu = {
			text : '开门',
			width : 60,
			disabled : true,
			iconCls : 'accept',
			handler : function() {
				if(grid.getSelectionModel().hasSelection()) {
	        		var rec = grid.getSelectionModel().getSelected();
					var accessCode = rec.get('accessCode');
					var accessStatus=rec.get('accessStatus');
					if(accessStatus == '1')
		            {
		                showAlertMsg("该门禁已经被禁用，不能进行操作！！",grid);
		                return;
		            } 
					showConfirm('确定要开启该门禁吗？门禁编码：' + accessCode,grid,function(bt) {
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T120101Action.asp?method=open',
								success: function(rsp,opt) {
									//alert(JSON.stringify(rsp)+"  >><<  "+JSON.stringify(opt));
//									Ext.MessageBox.alert('操作提示', accessCode+'门禁已成功开启!');
									showSuccessMsg(rsp.responseText,grid);//showErrorMsg(action.result.msg,addMhForm)
									grid.getStore().reload();
								},
								params: { 
									accessCode:accessCode,
									txnId: '120200',
									subTxnId: '00'
								}
							});
						}
					});
	        	}else{
	        		Ext.Msg.alert('提示', '请选择一个门禁！');
	        	}
			}
	};
	var stopMenu = {
			text : '关门',
			width : 60,
			disabled : true,
			iconCls : 'stop',
			handler : function() {
				if(grid.getSelectionModel().hasSelection()) {
	        		var rec = grid.getSelectionModel().getSelected();
					var accessCode = rec.get('accessCode');
					var accessStatus=rec.get('accessStatus');
					if(accessStatus == '1')
		            {
		                showAlertMsg("该门禁已经被禁用，不能进行操作！！",grid);
		                return;
		            } 
					showConfirm('确定要关闭该门禁吗？门禁编码：' + accessCode,grid,function(bt) {
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T120101Action.asp?method=close',
								success: function(rsp,opt) {
//									alert(JSON.stringify(rsp)+"  >><<  "+JSON.stringify(opt));
										//Ext.MessageBox.alert('操作提示', accessCode+'门禁已被停用!');
									showSuccessMsg(rsp.responseText,grid);//showErrorMsg(action.result.msg,addMhForm)
										grid.getStore().reload();
								},
								params: { 
									accessCode: accessCode,
									txnId: '120200',
									subTxnId: '01'
								}
							});
						}
					});
	        	}else{
	        		Ext.Msg.alert('提示', '请选择一个门禁！');
	        	}
				//window.location.href = Ext.contextPath+ '/page/mchnt/T2010102.jsp?mchntId='+ mchntGrid.getSelectionModel().getSelected().get('mchtNo');
			}
	};
	
	var openMenu1 = {
			text : '开警报',
			width : 60,
			disabled : true,
			iconCls : 'accept',
			handler : function() {
				if(grid.getSelectionModel().hasSelection()) {
	        		var rec = grid.getSelectionModel().getSelected();
					var accessCode = rec.get('accessCode');
					var accessStatus=rec.get('accessStatus');
					if(accessStatus == '1')
		            {
		                showAlertMsg("该门禁已经被禁用，不能进行操作！！",grid);
		                return;
		            } 
					showConfirm('确定要开警报吗？门禁编码：' + accessCode,grid,function(bt) {
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T120101Action.asp?method=openAccessAlarm',
								success: function(rsp,opt) {
//									alert(JSON.stringify(rsp)+"  >><<  "+JSON.stringify(opt));
										//Ext.MessageBox.alert('操作提示', accessCode+'门禁已被停用!');
									showSuccessMsg(rsp.responseText,grid);//showErrorMsg(action.result.msg,addMhForm)
										grid.getStore().reload();
								},
								params: { 
									accessCode: accessCode,
									txnId: '120200',
									subTxnId: '02'
								}
							});
						}
					});
	        	}else{
	        		Ext.Msg.alert('提示', '请选择一个门禁！');
	        	}
			}
	};
	var stopMenu1 = {
			text : '关警报',
			width : 60,
			disabled : true,
			iconCls : 'stop',
			handler : function() {
				if(grid.getSelectionModel().hasSelection()) {
	        		var rec = grid.getSelectionModel().getSelected();
					var accessCode = rec.get('accessCode');
					var accessStatus=rec.get('accessStatus');
					if(accessStatus == '1')
		            {
		                showAlertMsg("该门禁已经被禁用，不能进行操作！！",grid);
		                return;
		            } 
					showConfirm('确定要关警报吗？门禁编码：' + accessCode,grid,function(bt) {
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T120101Action.asp?method=closeAccessAlarm',
								success: function(rsp,opt) {
//									alert(JSON.stringify(rsp)+"  >><<  "+JSON.stringify(opt));
										//Ext.MessageBox.alert('操作提示', accessCode+'门禁已被停用!');
									showSuccessMsg(rsp.responseText,grid);//showErrorMsg(action.result.msg,addMhForm)
										grid.getStore().reload();
								},
								params: { 
									accessCode: accessCode,
									txnId: '120200',
									subTxnId: '03'
								}
							});
						}
					});
	        	}else{
	        		Ext.Msg.alert('提示', '请选择一个门禁！');
	        	}
			}
	};
	var videoModel = new Ext.grid.ColumnModel([
		{header: '终端设备编码', dataIndex: 'equipCode'},
		{header: '终端设备名称', dataIndex: 'equipName'},
		{header: '设备IP', dataIndex: 'equipIp'},
		{header: '用户名', dataIndex: 'note1'},
		{header: '密码', dataIndex: 'note2'},
		{header: '设备状态', dataIndex: 'equipStatus',hidden:true},
		{header: '设备状态', dataIndex: 'equipStatus2',hidden:true}
	]);
	var videoStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=railAccessEquipInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[//toolName,toolTypeName,toolNum
            {name: 'equipCode',mapping: 'equipCode'},
            {name: 'equipName',mapping: 'equipName'},
            {name: 'equipIp',mapping: 'equipIp'},
            {name: 'note1',mapping: 'note1'},
            {name: 'note2',mapping: 'note2'},
            {name: 'equipStatus',mapping: 'equipStatus'},
            {name: 'equipStatus2',mapping: 'equipStatus2'}
		])
	});
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
			videoStore.load();
			videoWin.show();
			videoWin.center();
		}
	};

	
	videoStore.on('beforeload', function() {
		var	selectedRecord = grid.getSelectionModel().getSelected();
        Ext.apply(this.baseParams, {
        	start: 0,
        	accessCode: selectedRecord.get("accessCode"),
        	equipType: 2
        });
    });
	var videoGrid = new Ext.grid.GridPanel({
		title:'监控列表<span style="color:red">*双击可查看监控视频</span><a style="margin-left:50px;" href="'+Ext.contextPath+'/template/HCNet.zip">下载播放器插件</a>',
		store: videoStore,
		height: 300,
		width: 900,
		colModel: videoModel,
		loadMask: {
			msg: '加载中......'
		},
        bbar: new Ext.PagingToolbar({
            store: videoStore,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
	});
	videoGrid.addListener('rowdblclick', rowdblclickFn);     
    
	function rowdblclickFn(videoGrid, rowindex, e){
		var gridRows = videoGrid.getStore();
		var equipStatus = gridRows.getAt(rowindex).get("equipStatus2");
		var user = gridRows.getAt(rowindex).get("note1");
		var passw = gridRows.getAt(rowindex).get("note2");
//		var equipIp = gridRows.getAt(rowindex).get("equipIp").split(".").join("*");
		var equipIp = gridRows.getAt(rowindex).get("equipIp");
		var htmlUrl= Ext.projectPath+"video.jsp?Param="+equipIp+"*"+user+"*"+passw;
		//192.168.3.12*addxw*567*
		var htmlUrl= "openhcnet://openhcnet/*"+equipIp+"*"+user+"*"+passw+"*";
//		window.open("openIE:"+htmlUrl, "_self"); 
		window.open(htmlUrl); 
//		if(equipStatus==1){
//			var user = gridRows.getAt(rowindex).get("note1");
//			var passw = gridRows.getAt(rowindex).get("note2");
//			var equipIp = gridRows.getAt(rowindex).get("equipIp").split(".").join("*");
//			var htmlUrl= Ext.projectPath+"video.jsp?Param="+equipIp+"*"+user+"*"+passw;
//			window.open("openIE:"+htmlUrl, "_self"); 
//		}else if(equipStatus==2){
//			Ext.MessageBox.alert('提示', '该设备正在维护，请查看其它设备!');
//		}else{
//			Ext.MessageBox.alert('提示', '该设备已被停用，请查看其它设备!');
//		}
	}
	var videosBoxPanel = new Ext.Panel({
		bodyStyle:'overflow-x:hidden;overflow-y:auto;',
		region : 'center',
		margins : '5 0 0 0',
		height:330,
		layout : 'fit',
		items : [videoGrid]

	});
	var videoForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 450,
		autoHeight: true,
		items: [videosBoxPanel]
	});
	var videoWin = new Ext.Window({
		title: '查看监控',
		layout: 'fit',
		iconCls : 'query',
		width: 700,
		autoHeight: true,
		items: [videoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		buttons: [{
			text: '关闭',
			handler: function() {
//            	termStore.load();
//            	queryForm.getForm().reset();
				videoWin.hide();
			}
		}]
	});
    var playerForm=new Ext.Panel({  
        xtype : 'panel',  
        id : 'playerPanel',  
        width:600,  
        height:600, 
        html:""
    });
    var songPlayer = new Ext.Window({  
        layout : 'fit',  
        width:600,  
        height:600,  
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
    
    function seeVideo(){
        // 检查插件是否已经安装过
        var iRet = WebVideoCtrl.I_CheckPluginInstall();
        if (-1 == iRet) {
            alert("您还未安装过插件，双击开发包目录里的WebComponentsKit.exe安装！");
            return;
        }

        var oPlugin = {
            iWidth: 600,             // plugin width
            iHeight: 400             // plugin height
        };

        var oLiveView = {
            iProtocol: 1,            // protocol 1：http, 2:https
            szIP: "192.168.2.144",    // protocol ip
            szPort: "80",            // protocol port
            szUsername: "admin",     // device username
            szPassword: "admin12345", // device password
            iStreamType: 1,          // stream 1：main stream  2：sub-stream  3：third stream  4：transcode stream
            iChannelID: 1,           // channel no
            bZeroChannel: false      // zero channel
        };
            
        // 初始化插件参数及插入插件
        WebVideoCtrl.I_InitPlugin(oPlugin.iWidth, oPlugin.iHeight, {
            bWndFull: true,//是否支持单窗口双击全屏，默认支持 true:支持 false:不支持
            iWndowType: 1,
            cbInitPluginComplete: function () {
                WebVideoCtrl.I_InsertOBJECTPlugin("divPlugin");

                // 检查插件是否最新
                if (-1 == WebVideoCtrl.I_CheckPluginVersion()) {
                    alert("检测到新的插件版本，双击开发包目录里的WebComponentsKit.exe升级！");
                    return;
                }

                // 登录设备
                WebVideoCtrl.I_Login(oLiveView.szIP, oLiveView.iProtocol, oLiveView.szPort, oLiveView.szUsername, oLiveView.szPassword, {
                    success: function (xmlDoc) {
                        // 开始预览
                        var szDeviceIdentify = oLiveView.szIP + "_" + oLiveView.szPort;
                        setTimeout(function () {
                            WebVideoCtrl.I_StartRealPlay(szDeviceIdentify, {
                                iStreamType: oLiveView.iStreamType,
                                iChannelID: oLiveView.iChannelID,
                                bZeroChannel: oLiveView.bZeroChannel
                            });
                        }, 1000);
                    }
                });
            }
        });

        // 关闭浏览器
        $(window).unload(function () {
            WebVideoCtrl.I_Stop();
        });
    }
    
	menuArr.push(openMenu);
	menuArr.push('-');
	menuArr.push(stopMenu);
	menuArr.push('-');
	menuArr.push(openMenu1);
	menuArr.push('-');
	menuArr.push(stopMenu1);
	menuArr.push('-');
	menuArr.push(openMenu2);
	
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=railAccessInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
			idProperty: 'accessCode'
        },[
            {name: 'accessCode',mapping: 'accessCode'},
            {name: 'accessName',mapping: 'accessName'},
            {name: 'accessType',mapping: 'accessType'},
            {name: 'note1',mapping: 'note1'},
            {name: 'accessRoute',mapping: 'accessRoute'},
			{name: 'accessAddress',mapping: 'accessAddress'},
			{name: 'accessDept',mapping: 'brhName'},
			{name: 'accessPic',mapping: 'accessPic'},
            {name: 'accessTel',mapping: 'accessTel'},
            {name: 'policeOffice',mapping: 'policeOffice'},
            {name: 'examPeriod',mapping: 'examPeriod'},
            {name: 'lastExam',mapping: 'lastExam'},
            {name: 'accessStatus',mapping: 'accessStatus'},
            {name: 'note5',mapping: 'note5'},
            {name: 'openStatus',mapping: 'openStatus'},
//            {name: 'warnWeixin',mapping: 'warnWeixin'},
            {name: 'mileage',mapping: 'mileage'},
            {name: 'mileagePrevious',mapping: 'mileagePrevious'},
            {name: 'mileageNext',mapping: 'mileageNext'},
            {name: 'installDate',mapping: 'installDate'},
            {name: 'longitude',mapping: 'longitude'},
            {name: 'latitude',mapping: 'latitude'},
            {name: 'whseCode',mapping: 'whseCode'},
            {name: 'addUser',mapping: 'addUser'}
        ])
    });
	termStore.load();
	function accessStatusRender(val) {
		if(val == '0') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="启用"/>启用';
		} else if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="停用"/>停用';
		} 
		return '状态未知';
	}
	function openStatusRender(val) {
		if(val == '已连接') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="已连接"/>已连接';
		} else if(val == '未连接') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="未连接"/>未连接';
		} 
		return '状态未知';
	}
	function openNote5Render(val) {
		if(val == '正常') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="正常"/>正常';
		} else if(val == '故障') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="故障"/>故障';
		} 
		return '状态未知';
	}
	function warnSystemRender(warnSystem) {
		switch(warnSystem) {
			case '0': return '报警';
			case '1': return '不报警';
		}
	}
//	function warnWeixinRender(val) {
//		if(val == '0') {
//			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="预警"/>预警';
//		} else if(val == '1') {
//			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="不预警"/>不预警';
//		} 
//		return '状态未知';
//	}
	function accessTypeRender(accessType) {
		switch(accessType) {
			case '0': return '通道门';
			case '1': return '库房门';
		}
	}
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0
//            whseAddress: Ext.getCmp('whseAddress').getValue(),
//            whseRank: Ext.getCmp('whseRank').getValue()
        });
    });
	
	var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
    	{id:'accessCode',header: '门禁编码',sortable: true,dataIndex: 'accessCode',width: 100},
		{header: '门禁名称',dataIndex: 'accessName',sortable: true,width: 120},
		{header: '设备状态',dataIndex: 'note5',sortable: true,renderer: openNote5Render,width: 80},
		{header: '当前状态',dataIndex: 'openStatus',sortable: true,renderer: openStatusRender,width: 80},
		{header: '门禁类型',dataIndex: 'accessType',sortable: true,renderer: accessTypeRender,width: 100},
		{header: '配套设备编码',dataIndex: 'note1',sortable: true,width: 120},
		{header: '线路名称',dataIndex: 'accessRoute',sortable: true,width: 120},
		{header: '门禁位置',dataIndex: 'accessAddress',sortable: true,width: 120},
		{header: '所属机构',dataIndex: 'accessDept',sortable: true,width: 120},
		{header: '负责人',dataIndex: 'accessPic',sortable: true,width: 120},
		{header: '联系电话',dataIndex: 'accessTel',sortable: true,width: 120},
		{header: '所属派出所',dataIndex: 'policeOffice',sortable: true,width: 120},
		{header: '检修周期（天）',dataIndex: 'examPeriod',sortable: true,width: 120},
		{header: '最后一次检修时间',dataIndex: 'lastExam',sortable: true,width: 120},
		{header: '使用状态',dataIndex: 'accessStatus',sortable: true,width: 80},
//		{header: '微信预警',dataIndex: 'warnWeixin',renderer: warnWeixinRender,width: 80},
		{header: '通道门里程',dataIndex: 'mileage',sortable: true,width: 120},
		{header: '与上一通道门距离',dataIndex: 'mileagePrevious',sortable: true,width: 120},
		{header: '与下一通道门距离',dataIndex: 'mileageNext',sortable: true,width: 120},
		{header: '安装时间',dataIndex: 'installDate',sortable: true,width: 120},
		{header: '地理经度',dataIndex: 'longitude',sortable: true,width: 120},
		{header: '地理纬度',dataIndex: 'latitude',sortable: true,width: 120},
		{header: '仓库编码',dataIndex: 'whseCode',sortable: true,width: 120},
		{header: '创建人',dataIndex: 'addUser',sortable: true,width: 120}
    ]);
	// 仓库信息列表
    var grid = new Ext.grid.GridPanel({
        title: '门禁监控',
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
            rec = grid.getSelectionModel().getSelected();
            if(rec != null) {
                grid.getTopToolbar().items.items[0].enable();
                grid.getTopToolbar().items.items[2].enable();
                grid.getTopToolbar().items.items[4].enable();
                grid.getTopToolbar().items.items[6].enable();
                grid.getTopToolbar().items.items[8].enable();
               
            } else {
                grid.getTopToolbar().items.items[0].disable();
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[4].disable();
                grid.getTopToolbar().items.items[6].disable();
                grid.getTopToolbar().items.items[8].disable();
            }
        }
    });
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})