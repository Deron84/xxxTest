Ext.onReady(function() {
	
	
	
	
	function showPaper(){
		
		var sel = mainGrid.getSelectionModel().getSelected();
		if(sel == null){
			showMsg("请在问卷信息中选择一条记录后再进行操作。",mainGrid);return;
		}
		
		var iLeft = (window.screen.availWidth-10-790)/2;
		EncryptUtils.encrypt(sel.data.PAPER_ID,function(ret){
			window.open(Ext.contextPath + '/page/mchtSrv/T7010101.jsp?s=' + ret, 'newwindow', 'height=600,width=790,top=0,left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes');
		})
	}
	
	function getPDF(){
		var sel = mainGrid.getSelectionModel().getSelected();
		if(sel == null){
			showMsg("请在问卷信息中选择一条记录后再进行操作。",detailGrid);return;
		}
		showMask("正在为您生成PDF文件，请稍后。。。",detailGrid);
		Ext.Ajax.request({
			url: 'T70101Action_pdf.asp',
			success: function(rsp,opt) {
				hideMask();
				var rspObj = Ext.decode(rsp.responseText);
				if(rspObj.success) {
					window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
												rspObj.msg;
				} else {
					showErrorMsg(rspObj.msg,detailGrid);
				}
			},
			failure: function(){
				hideMask();
			},
			params: { 
				paperId: sel.data.PAPER_ID,
				txnId: '70101',
				subTxnId: '01'
			}
		});
	}
	
	//数据集
	var mainStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=PaperRecInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'PAPER_ID',mapping: 'PAPER_ID'},	
			{name: 'RESERVE1',mapping: 'RESERVE1'},
			{name: 'RESERVE2',mapping: 'RESERVE2'},
			{name: 'CRT_USER',mapping: 'CRT_USER'},
			{name: 'CRT_TIME',mapping: 'CRT_TIME'},
			{name: 'STA',mapping: 'STA'}
		]),
		autoLoad: true
	});

	
	//数据集
	var detailStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=PaperHisInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'QUESTION',mapping: 'QUESTION'},	
			{name: 'OPTIONS',mapping: 'OPTIONS'}
		])
	});
	
	//列模型
	var mainModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '问卷编号',dataIndex: 'PAPER_ID',width: 140},
    		{header: '创建柜员',dataIndex: 'CRT_USER',width: 60},
    		{header: '创建时间',dataIndex: 'CRT_TIME',id: 'CRT_TIME',width: 60}
    ]);
	
    	
    //列模型
	var detailModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '题目',dataIndex: 'QUESTION',width: 160},
    		{header: '选项',dataIndex: 'OPTIONS',id: 'OPTIONS'}
    	]);
    	
    	
    //GRID
	var mainGrid = new Ext.grid.GridPanel({
		title: '商户问卷信息',
		iconCls: 'T70101',
		region: 'west',
		width: 320,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'CRT_TIME',
		viewConfig: {
	        forceFit: true,
	        getRowClass: function(record,rowIndex,rowParams,store) {
	            var c = record.get('STA');
	            if (c == '1') {
	                return 'x-grid-record-red';
	            } else {
	            	return '';
	            }
	        }
    	},
		tbar: [{
			xtype: 'button',
			text: '问卷预览',
			name: 'show',
			id: 'show',
			iconCls: 'query_2',
			width: 75,
			handler:function() {
				showPaper();
			}
		}],
		loadMask: {
			msg: '正在加载信息列表，请稍候......'
		},
		bbar: new Ext.PagingToolbar({
			store: mainStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '{0}-{1}/{2}',
			emptyMsg: '无记录'
		})
	});
	mainGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mainGrid.getSelectionModel().getSelected().data.PAPER_ID;
		detailStore.load({
			params: {
				start: 0,
				PAPER_ID: mainGrid.getSelectionModel().getSelected().data.PAPER_ID
				}
			});
	});
	
	
	//GRID
	var detailGrid = new Ext.grid.GridPanel({
		title: '商户问卷详细信息',
		iconCls: 'application_16x16',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: detailStore,
		cm: detailModel,
		disableSelection: true,
		autoExpandColumn: 'OPTIONS',
		tbar: [{
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
		}
	});
	
	
	//Main View
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mainGrid,detailGrid],
		renderTo: Ext.getBody()
	})
})