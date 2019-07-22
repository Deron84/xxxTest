Ext.onReady(function() {
	
	//
	function showPaper(){
		
		var sel = mainGrid.getSelectionModel().getSelected();
		if(sel == null){
			showMsg("请选择一条记录后再进行操作。",mainGrid);return;
		}
		
		var iLeft = (window.screen.availWidth-10-890)/2; 
		
		var s = sel.data.MCHT_ID + '|' + sel.data.PAPER_ID + '|' + sel.data.SEL_MCHT_ID;
		
		EncryptUtils.encrypt(s,function(ret){
			window.open(Ext.contextPath + '/page/mchtSrv/T7010301.jsp?s=' + ret, 'newwindow', 'height=600,width=790,top=0,left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes');
		})
	}
	
	function getPDF(){
		var sel = mainGrid.getSelectionModel().getSelected();
		if(sel == null){
			showMsg("请选择一条记录后再进行操作。",mainGrid);return;
		}
		showMask("正在为您生成PDF文件，请稍后。。。",mainGrid);
		Ext.Ajax.request({
			url: 'T70103Action_pdf.asp',
			success: function(rsp,opt) {
				hideMask();
				var rspObj = Ext.decode(rsp.responseText);
				if(rspObj.success) {
					window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
												rspObj.msg;
				} else {
					showErrorMsg(rspObj.msg,mainGrid);
				}
			},
			failure: function(){
				hideMask();
			},
			params: { 
				mchtId: sel.data.MCHT_ID,
				paperId: sel.data.PAPER_ID,
				selMchtId: sel.data.SEL_MCHT_ID,
				txnId: '70301',
				subTxnId: '01'
			}
		});
	}
	
	//数据集
	mainStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=PaperLelInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'SEL_MCHT_ID',mapping: 'SEL_MCHT_ID'},	
			{name: 'PAPER_ID',mapping: 'PAPER_ID'},
			{name: 'MCHT_ID',mapping: 'MCHT_ID'},
			{name: 'MCHT_NM',mapping: 'MCHT_NM'},
			{name: 'MCHT_POINT',mapping: 'MCHT_POINT'},
			{name: 'MCHT_LEVEL',mapping: 'MCHT_LEVEL'},	
			{name: 'RESERVE1',mapping: 'RESERVE1'},
			{name: 'RESERVE2',mapping: 'RESERVE2'},	
			{name: 'CRT_USER',mapping: 'CRT_USER'},	
			{name: 'CRT_TIME',mapping: 'CRT_TIME'}
		]),
		autoLoad: true
	});
	mainStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			ACQ_INST_ID: Ext.get('ACQ_INST_ID').getValue(),
			MCHT_NO: Ext.get('MCHT_NO').getValue()
		});
	})
	
	
	//列模型
	var mainModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '商户编号',dataIndex: 'MCHT_ID',width: 120},
			{header: '商户名称',dataIndex: 'MCHT_NM',width: 160,id: 'MCHT_NM'},
    		{header: '问卷编号',dataIndex: 'PAPER_ID',width: 140},
    		{header: '问卷得分',dataIndex: 'MCHT_POINT',width: 90},
    		{header: '商户级别',dataIndex: 'MCHT_LEVEL',width: 120},
    		{header: '评级柜员',dataIndex: 'CRT_USER',width: 90},
    		{header: '评级时间',dataIndex: 'CRT_TIME',width: 90}
    	]);
    	
    	
    //GRID
	var mainGrid = new Ext.grid.GridPanel({
		title: '商户问卷信息',
		iconCls: 'T70103',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'MCHT_NM',
		tbar: [{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 55,
			handler:function() {
				mainStore.load();
			}
		},'-','机构编号：',{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW',
			hiddenName: 'ACQ_INST_ID',
			width: 160
		},'-','商户编号：',{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			hiddenName: 'MCHT_NO',
			editable: true,
			width: 320
		},'-',{
			xtype: 'button',
			text: '查看问卷',
			name: 'show',
			id: 'show',
			iconCls: 'query_2',
			width: 75,
			handler:function() {
				showPaper();
			}
		},'-',{
			xtype: 'button',
			text: '生成PDF',
			name: 'pdf',
			id: 'pdf',
			iconCls: 'script_16x16',
			width: 75,
			handler:function() {
				getPDF();
			}
		}],
		loadMask: {
			msg: '正在加载信息列表，请稍候......'
		},
		bbar: new Ext.PagingToolbar({
			store: mainStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	
	//Main View
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mainGrid],
		renderTo: Ext.getBody()
	})
})