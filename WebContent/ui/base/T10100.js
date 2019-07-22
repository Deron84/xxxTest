Ext.onReady(function() {
	
	var upBrhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});

	//上级营业网点编码
	SelectOptionsDWR.getComboData('BRH_ID_NEW',function(ret){
		upBrhStore.loadData(Ext.decode(ret));
	});
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'brhId'
		},[
			{name: 'brhId',mapping: 'brhId'},
			{name: 'brhLvl',mapping: 'brhLvl'},
			{name: 'upBrhId',mapping: 'upBrhId'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'brhAddr',mapping: 'brhAddr'},
			{name: 'brhTelNo',mapping: 'brhTelNo'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'brhContName',mapping: 'brhContName'},
			{name: 'resv1',mapping: 'resv1'},
			{name: 'cupBrhId',mapping: 'cupBrhId'}
		]),
		autoLoad: true
	});
	
	function brhLvlRender(brhLvl) {
		switch(brhLvl) {
			case '0': return '一级';
			case '1': return '二级';
			case '2': return '三级';
			case '3':return '四级';
		}
	}
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'brhId',header: '编码',dataIndex: 'brhId',sortable: true,width: 120},
		{header: '上级编码',dataIndex: 'upBrhId',sortable: true,width: 120},
		{header: '机构名称',dataIndex: 'brhName',sortable: true,width: 160},
		{id:'brhAddr',header: '机构地址',dataIndex: 'brhAddr',sortable: true,width: 120},
		{header: '联系方式',dataIndex: 'brhTelNo',sortable: true,width: 120},
		{header: '联系人',dataIndex: 'brhContName',sortable: true,width: 120}
	]);
	
	var menuArr = new Array();

	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	
	menuArr.push(queryCondition);
	
	// 可选机构下拉列表
	var brhCombo = new Ext.form.ComboBox({
		store: upBrhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择机构',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		width: 260,
		//blankText: '请选择一个机构',
		fieldLabel: '机构编码',
		id: 'editBrh',
		name: 'editBrh',
		hiddenName: 'brhIdEdit'
	});
	
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 440,
		autoHeight: true,
		items: [brhCombo,{
			xtype: 'textfield',
			id: 'searchBrhName',
			width: 260,
			name: 'searchBrhName',
			fieldLabel: '机构名称'
		}]
	});
	
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
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
				gridStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	
	
	
	
	
	//机构列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '机构信息查询',
		iconCls: 'T10100',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		autoExpandColumn:'brhAddr',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载机构信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
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
			var brhId = grid.getSelectionModel().getSelected().data.brhId;
			//查询机构下操作员信息
			oprGridStore.load({
				params: {
					start: 0,
					brhId: brhId
				}
			});
		}
	});
	
	
	
	/************************************************以下是机构相关操作员信息************************************************************/
	
	/*****************************************操作员信息*****************************************/
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=oprInfoWithBrh'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'oprId'
		},[
			{name: 'oprId',mapping: 'oprId'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprName',mapping: 'oprName'},
			{name: 'oprGender',mapping: 'oprGender'},
			{name: 'registerDt',mapping: 'registerDt'},
			{name: 'oprTel',mapping: 'oprTel'},
			{name: 'oprMobile',mapping: 'oprMobile'}
		])
	});
	
	oprGridStore.on('beforeload',function() {
		Ext.apply(this.baseParams,{
			brhId : grid.getSelectionModel().getSelected().data.brhId
		});
	});

	var oprTpl = new Ext.Template(
			'<p title="操作员姓名"><img src="' + Ext.contextPath + '/ext/resources/images/user.png"/>：{oprName}</p>',
			'<p title="操作员性别"><img src="' + Ext.contextPath + '/ext/resources/images/gender_16.png"/>：{oprGender:this.gender}</p>',
			'<p title="注册日期"><img src="' + Ext.contextPath + '/ext/resources/images/calendar_16.png"/>：{registerDt:this.date}</p>',
			'<p title="操作员联系电话"><img src="' + Ext.contextPath + '/ext/resources/images/phone_16.png"/>：{oprTel}</p>',
			'<p title="操作员移动电话"><img src="' + Ext.contextPath + '/ext/resources/images/mobile_16.png"/>：{oprMobile}</p>'
	);
	
	oprTpl.gender = function(val) {
		if(val == '0') {
			return '男';
		} else {
			return '女';
		}
	};
	
	oprTpl.date = function(val) {
		if(val.length == 8) {
			return val.substring(0,4) + '年' +
				   val.substring(4,6) + '月' + 
				   val.substring(6,8) + '日';
		} else {
			return val;
		}
	};

	var oprRowExpander = new Ext.ux.grid.RowExpander({
		tpl: oprTpl
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		oprRowExpander,
		{id: 'oprId',header: '操作员编码',dataIndex: 'oprId',align: 'center',sortable: true},
		{header: '所在机构编码',align: 'center',dataIndex: 'brhId'}
	]);
	
	var oprGridPanel = new Ext.grid.GridPanel({
		id: 'oprPanel',
		title: '操作员信息',
		frame: true,
		iconCls: 'T104',
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		collapsible: true,
		plugins: oprRowExpander,
		loadMask: {
			msg: '正在加载操作员信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: oprGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	/********************************主界面*************************************************/
	
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});
	
	var rightPanel = new Ext.Panel({
		region: 'east',
		split: true,
		width: 265,
		frame: true,
		collapsible: true,
		autoScroll: true,
		title: '机构关联信息',
		items:[oprGridPanel]
	});
	
	
	oprGridPanel.on('render',function() {
		new Ext.dd.DragSource(oprGridPanel.getEl(), {
			ddGroup: 'dd'
		});
	});
	
	var items;
	var draggedPanel;
	var positionY;
	var itemY;
	var index;
	rightPanel.on('render',function() {
		var rightPanelTarget = new Ext.dd.DropTarget(rightPanel.getEl(),{
			ddGroup: 'dd',
			notifyDrop: function(source,e) {
				items = rightPanel.items.items;
				draggedPanel = Ext.getCmp(source.getEl().id);
				positionY = e.getPageY();
				heigh = 0;
				index = 10;
				for(var i = 0; i < draggedPanel.el.dom.parentNode.childNodes.length; i++) {
					itemY = Ext.getCmp(draggedPanel.el.dom.parentNode.childNodes[i].id).getPosition()[1];
					if(itemY > positionY) {
						index = i;
						break;
					}
				}
				if(index == 10) {
					draggedPanel.el.dom.parentNode.appendChild(draggedPanel.el.dom);
				} else {
					draggedPanel.el.dom.parentNode.insertBefore(draggedPanel.el.dom, draggedPanel.el.dom.parentNode.childNodes[index]);
				}
				
			}
		});		
	});
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,						
			brhId: queryForm.findById('editBrh').getValue(),
			brhName: queryForm.findById('searchBrhName').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});