Ext.onReady(function(){
	
	var batchStore;
	var batchGrid;

	//对账任务启动控制中心
	batchStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=batchMission'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'batId'
		},[
			{name: 'batId',mapping: 'batId'},
			{name: 'batDsp',mapping: 'batDsp'},
			{name: 'batState',mapping: 'batState'},
			{name: 'batNote',mapping: 'batNote'}
//			,
//			{name: 'oprate',mapping: 'oprate'}
		]),
		autoLoad: true
	});
	

	
	var task = {
		run: function (){
			batchStore.load({
				params: {start: 0}
			});;
		},
		interval: 3000
	};
	
	
	
//	function oprate(val,metadata,record,rowIndex){
//		if(val == "0" || val == "3"){
//                var value = record.get('batId');
//				return "<button class=btn_2k3 id="+value+" onclick='javascript:missionConsole(this.id)'>启动任务</button>";
//			}
//	}
	
	var colModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '任务编号',dataIndex: 'batId',width: 100},
		{header: '任务描述',dataIndex: 'batDsp',width: 200,id: 'batDsp'},
		{header: '运行状态',dataIndex: 'batState',width: 200,renderer:function(val){
			if("0" == val){return "未运行"}
			else if("1" == val){return "运行中"}
			else if("2" == val){return "<font color='#00FF00'>运行成功</font>"}
			else if("3" == val){return "<font color='red'>运行失败</font>"}
			else return val;
		}},
		{header: '任务备注',dataIndex: 'batNote',width: 200}
//		,
//		{header: '操作',dataIndex: 'oprate',width: 100,renderer:oprate}
	]);
	
	batchGrid = new Ext.grid.GridPanel({
		title: '收单对账：' + date,
		region: 'center',
		iconCls: 'T80101',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'batDsp',
		stripeRows: true,
		store: batchStore,
		cm: colModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		clicksToEdit: true,
		forceValidation: true
		,buttons: [{
			text: '任务启动',
			id: 'taskStart',
			iconCls: 'download',
			disabled: false,
			timeout:6000,
			handler: function() {
				Ext.TaskMgr.start(task);
				Ext.getCmp('taskStart').disable();
				showMask("正在执行任务，请勿切换页面！",batchGrid);
			    HandleOfBatch.sendMsg(date,function(ret){
					if('S'==ret){
						showSuccessMsg("全部任务启动成功！",batchGrid);
						Ext.getCmp('taskStart').disable();
					}else{
						showErrorMsg(ret,batchGrid);
						
						Ext.getCmp('taskStart').enable();
					}
					Ext.TaskMgr.stop(task);
					batchStore.reload();
					hideMask();
				});
			}
		}]
	});
	
	batchStore.on('beforeload', function(){
		CheckTask.sendMsg(date,function(ret){
			if("E"==ret){
				Ext.getCmp('taskStart').enable();
			}else{
				Ext.getCmp('taskStart').disable();
			}
		});
		Ext.apply(this.baseParams, {
			date: date
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [batchGrid],
		renderTo: Ext.getBody()
	});
});

//初始时判断启动任务按钮是否可用
function checkBeforeLoad(){
	
}