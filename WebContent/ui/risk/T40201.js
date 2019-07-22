Ext.onReady(function() {
	// 当前选择记录
	var record;
	
	// 风险模型数据集
	var riskModelStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getRiskModelInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			id: 'saModelKind'
		},[
			{name: 'saModelKind',mapping: 'saModelKind'},
			{name: 'saLimitNum',mapping: 'saLimitNum'},
			{name: 'saLimitAmountSingle',mapping: 'saLimitAmountSingle'},
			{name: 'saLimitAmountTotle',mapping: 'saLimitAmountTotle'},
			{name: 'saBeUse',mapping: 'saBeUse'},
			{name: 'saAction',mapping: 'saAction'},	
			{name: 'modiZoneNo',mapping: 'modiZoneNo'},
			{name: 'modiOprId',mapping: 'modiOprId'},
			{name: 'modiTime',mapping: 'modiTime'}
		]),
		autoLoad: true
	}); 
	
	//var sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn}); 
	var riskColModel = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
	    //sm,
		{id: 'saModelKind',header: '模型',dataIndex: 'saModelKind',renderer: saModelKind,width: 320}	,
		{header: '受控交易笔数',dataIndex: 'saLimitNum',width: 110,editor: new Ext.form.TextField({
			allowBlank: false,
			blankText: '请输入受控交易笔数',
			regex: /^[0-9]{1,8}$/,
			regexText: '只能输入1-8位数字'
		})},
		{header: '受控金额-单笔（元）',dataIndex: 'saLimitAmountSingle',width: 140,editor: new Ext.form.TextField({
			maxLength: 12,
			allowBlank: false,
			blankText: '请输入受控单笔金额'
		})},
		{header: '受控金额-总计（元）',dataIndex: 'saLimitAmountTotle',width: 140,editor: new Ext.form.TextField({
			maxLength: 12,
			allowBlank: false,
			blankText: '请输入受控总金额'
		})},
		{header: '使用标识',dataIndex: 'saBeUse',width: 60,renderer: saBeUse,editor: new Ext.form.ComboBox({
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','未启用'],['1','启用']],
				reader: new Ext.data.ArrayReader()
			})
		})},	
//		{header: '受控动作',dataIndex: 'saAction',width: 60,renderer: saAction,editor: new Ext.form.ComboBox({
//			store: new Ext.data.ArrayStore({
//				fields: ['valueField','displayField'],
//				data: [['0','正常'],['2','拒绝']],
//				reader: new Ext.data.ArrayReader()
//			})
//		})},
		{header: '更新分行',dataIndex: 'modiZoneNo',width: 70},
		{header: '更新柜员',dataIndex: 'modiOprId',width: 70},
		{header: '更新时间',dataIndex: 'modiTime',width: 130,renderer: formatTs}
	]);
	
	var menuArr = new Array();

	var upMenu = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
			var modifiedRecords = grid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			var store = grid.getStore();
			var len = store.getCount();
			for(var i = 0; i < len; i++) {
				var record = store.getAt(i);
					//record.get(''))
			}
			//存放要修改的监控模型
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				
				if(record.get('saModelKind') == 'C1' && record.get('saLimitAmountSingle') != '-'){
					showErrorMsg("模型C1不能修改受控金额-单笔!",grid);
					return;
				}
				if(record.get('saModelKind') == 'C2' && record.get('saLimitAmountTotle') != '-'){
					showErrorMsg("模型C2不能修改受控金额-总计!",grid);
					return;
				}

				var data = {
					id : record.get('saModelKind'),
					saLimitNum: record.get('saLimitNum'),
					saLimitAmountSingle: record.get('saLimitAmountSingle'),
					saLimitAmountTotle: record.get('saLimitAmountTotle'),
					saBeUse: record.get('saBeUse'),
					saAction: record.get('saAction')
				};
				array.push(data);
			}
			grid.getTopToolbar().items.items[0].disable();
			Ext.Ajax.request({
				url: 'T40201Action.asp',
				method: 'post',
				params: {
					modelDataList : Ext.encode(array),
					txnId: '40101',
					subTxnId: '01'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					grid.enable();
					if(rspObj.success) {
						grid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,grid);
					} else {
						grid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,grid);
					}
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	var detail = {
		text: '模型说明',
		width: 85,
		iconCls: 'detail',
		handler: function(){
			Ext.MessageBox.show({
				msg: 
//					 '<font color=green>参加风控商户</font>：商户类型MCC为《银联卡特约商户类别码使用细则》规定的。<br>' +
//					 '&nbsp;&nbsp;<font color=grey>a</font>：全部批发商户MCC。<br>' +
//					 '&nbsp;&nbsp;<font color=grey>b</font>：零售商户MCC中教育、卫生、福利及其他政府服务MCC。<br>'+
//					 '&nbsp;&nbsp;<font color=grey>c</font>：零售商户MCC中抵扣率（房地产、加油）。<br><br>' +
					 '<font color=red>C1-疑似套现按月监测触发规则</font><br>' +
					 '<font color=green>1</font>：商户信用卡当月交易总额超过<font color=blue>受控金额-总计</font>或当月交易总笔数超过<font color=blue>受控交易笔数</font>。<br>' +
					 '<font color=green>2</font>：商户信用卡交易单笔交易金额超过<font color=blue>受控金额-单笔</font>。<br>' +
					 '<font color=red>C2-疑似套现按日监测触发规则</font><br>' +
					 '<font color=green>1</font>：商户信用卡当日交易总笔数超过<font color=blue>受控交易笔数</font>。<br>' +
					 '<font color=green>2</font>：商户信用卡交易单笔整数交易金额超过<font color=blue>受控金额-单笔</font>。<br>',
				buttons: Ext.MessageBox.OK,
				modal: true,
				width: 480
			});
		}
	};
	
	menuArr.push(upMenu);  
	menuArr.push('-'); 
	menuArr.push(detail); 
	
	// 转译风险模型
	function saModelKind(val) {
		if(val == 'C1') {
			return 'C1-疑似套现按月监控';
		} else if(val == 'C2') {
			return 'C2-疑似套现按日监控';
		} 
	}
	
	// 转译启用标识
	function saBeUse(val) {
		if(val == '1') {
			return '<font color="green">启用</font>';
		} else {
			return '<font color="red">未启用</font>';
		}
	}
	
	// 转译受控动作
	function saAction(val) {
		if(val == '0') {
			return '<font color="green">正常</font>';
		} else if(val == '1') {
			return '<font color="gray">托收</font>';
		} else if(val == '2') {
			return '<font color="red">拒绝</font>';
		} else {
			return '未知的受控动作';
		}
	}

	// 风险模型列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '风险模型参数修改',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
//		autoHeight: true,
		clicksToEdit: true,
		store: riskModelStore,
		//sm: sm,
//		autoExpandColumn:'saModelKind',
		cm: riskColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载风险模型列表......'
		},
		tbar: 	menuArr,
		bbar: new Ext.PagingToolbar({
			store: riskModelStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[0] != undefined) {
				grid.getTopToolbar().items.items[0].enable();
			}
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	
	// 风险模型列表
	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})