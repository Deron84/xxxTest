Ext.onReady(function() {

	var dialog = new UploadDialog({
		uploadUrl : 'T10502Action.asp?method=batch',
		filePostName : 'files',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB',
		fileTypes : '*.txt;*.TXT',
		fileTypesDescription : '文本文件(*.txt;*.TXT)',
		title: '公司转出卡信息文件上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		exterMethod: function() {
		},
		postParams: {
			txnId: '10502',
			subTxnId: '04'
		}
	});
	
	var posStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=comCardInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'oprId'
		},[
			{name: 'cardNo',mapping: 'card_no'},
			{name: 'mchtNo',mapping: 'mcht_no'},
			{name: 'termId',mapping: 'term_id'},
			{name: 'brhId',mapping: 'brh_id'},
			{name: 'holderName',mapping: 'holder_name'},
			{name: 'holderId',mapping: 'holder_id'},
			{name: 'holderTel',mapping: 'holder_tel'},
			{name: 'stat',mapping: 'stat'},
			{name: 'smsQuota',mapping: 'sms_quota'},
			{name: 'startTime',mapping: 'start_time'},
			{name: 'stopTime',mapping: 'stop_time'}
		])
	});
	
	gridStore.load({
		params:{start: 0}
	});
	
	var colModel = new Ext.grid.ColumnModel([
	    {header: '卡号',dataIndex: 'cardNo',width: 80},
	    {header: '商户号',dataIndex: 'mchtNo',width: 120},
	    {header: 'POS终端号',dataIndex: 'termId',width: 80},
	    {header: '持卡人姓名',dataIndex: 'holderName',width: 80,
	    	editor: new Ext.form.TextField({
			 	allowBlank: false,
				blankText: '持卡人姓名不能为空',
				maxLength: 40,
				vtype: 'isOverMax'
			 })},
	    {header: '持卡人身份证',dataIndex: 'holderId',width: 120,
	    	editor: new Ext.form.TextField({
			 	allowBlank: false,
				blankText: '持卡人身份证号不能为空',
				maxLength: 20,
				vtype: 'isOverMax'
			 })},
	    {header: '持卡人电话',dataIndex: 'holderTel',width: 80,
	    	editor: new Ext.form.TextField({
			 	allowBlank: false,
				blankText: '持卡人电话不能为空',
				maxLength: 20,
				vtype: 'isOverMax'
			 })},
	    {header: '短信通知限额',dataIndex: 'smsQuota',width: 120,
	    	editor: new Ext.form.TextField({
			 	allowBlank: false,
				blankText: '短信通知限额不能为空',
				maxLength: 15,
				vtype: 'isOverMax'
			 })},
	    {header: '起始时间',dataIndex: 'startTime',width: 80,
		    editor: new Ext.form.TimeFieldSP({
		    	fieldLabel: '交易开始时间',
				minValue: '00:00',
				maxValue: '23:59',
				altFormats: 'H:i',
				format: 'H:i',
				editable: true
		    })},
	    {header: '终止时间',dataIndex: 'stopTime',width: 80,
	    	editor: new Ext.form.TimeFieldSP({
		    	fieldLabel: '交易结束时间',
				minValue: '00:00',
				maxValue: '23:59',
				altFormats: 'H:i',
				format: 'H:i',
				editable: true
		    })}
	    ]);

	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			addWin.show();
			addWin.center();
		}
	};
	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				showConfirm('确定要删除该条信息吗？',grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T10502Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								cardId: rec.get('cardNo'),
								txnId: '10502',
								subTxnId: '03'
							}
						});
					}
				});
			}
		}
	};
	
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
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {		      
						cardId : record.get('cardNo'),
						holderName: record.get('holderName'),
						holderId: record.get('holderId'),
						holderTel: record.get('holderTel'),
						smsQuota: record.get('smsQuota'),
						startTime: record.get('startTime'),
						stopTime: record.get('stopTime')
				};
				array.push(data);
			}
			
			Ext.Ajax.request({
				url: 'T10502Action.asp?method=update',
				params: {
					infoList: Ext.encode(array),
					txnId: '10502',
					subTxnId: '02'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						grid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,grid);
					} else {
						grid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,grid);
					}
					grid.getTopToolbar().items.items[4].disable();
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	
	var upload = {
			text: '批量添加账户信息',
			width: 85,
			id: 'upload',
			iconCls: 'upload',
			handler:function() {
				dialog.show();
			}
		};
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);
//	menuArr.push('-');
//	menuArr.push(upload);
	
	// 操作员信息列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '公司转出卡维护',
		iconCls: 'T105',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: colModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	
	grid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			grid.getTopToolbar().items.items[4].enable();
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[2].enable();
		}
	});	
	
	// 可POS下拉列表
	var posCombo = new Ext.form.ComboBox({
		disabled: true,
		store: posStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个POS终端',
		fieldLabel: 'POS终端号*',
		hiddenName: 'posId'
	});
	
	//信息添加表单
	var infoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 330,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			xtype: 'textnotnull',
			fieldLabel: '卡号*',
			maxLength: 40,
			vtype: 'isOverMax',
			width:160,
			id: 'cardId'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号*',
			methodName: 'getMchntIdfor10501',
			hiddenName: 'mchtId',
			allowBlank: false,
			editable: true,
			width: 300,
			listeners: {
            	'select': function() {
            		posCombo.enable();
            		SelectOptionsDWR.getComboDataWithParameter('POS_FOR_MCHT',this.getValue(),function(ret){
            			posStore.removeAll();
            			posStore.loadData(Ext.decode(ret));
            			posCombo.setValue(posStore.getAt(0).get('valueField'));
            		});
                }
			}
		},posCombo,
		{
			xtype: 'textnotnull',
			fieldLabel: '持卡人姓名*',
			maxLength: 40,
			vtype: 'isOverMax',
			width:160,
			id: 'holderName'
		},{
			xtype: 'textnotnull',
			fieldLabel: '持卡人身份证*',
			maxLength: 20,
			vtype: 'isOverMax',
			width:160,
			id: 'holderId'
		},{
			xtype: 'textnotnull',
			fieldLabel: '持卡人电话*',
			maxLength: 20,
			vtype: 'isOverMax',
			width:160,
			id: 'holderTel'
		},{
			xtype: 'textnotnull',
			fieldLabel: '短信通知限额*',
			maxLength: 15,
			vtype: 'isOverMax',
			width:160,
			id: 'smsQuota'
		},{
        	xtype: 'timefieldsp',
			fieldLabel: '交易开始时间',
			id: 'startTime',
			minValue: '00:00',
			maxValue: '23:59',
			altFormats: 'H:i',
			format: 'H:i',
			editable: true
    	},{
        	xtype: 'timefieldsp',
			fieldLabel: '交易结束时间',
			id: 'stopTime',
			minValue: '00:00',
			maxValue: '23:59',
			altFormats: 'H:i',
			format: 'H:i',
			editable: true
    	}]
	});
	
	//新增Win
	var addWin = new Ext.Window({
		title: '公司转出卡新增',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 500,
		autoHeight: true,
		layout: 'fit',
		items: [infoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(infoForm.getForm().isValid()) {
					infoForm.getForm().submitNeedAuthorise({
						url: 'T10502Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,infoForm);
							infoForm.getForm().reset();
							grid.getStore().reload();
							addWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,infoForm);
							addWin.hide();
						},
						params: {
							txnId: '10502',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				infoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				addWin.hide(grid);
			}
		}]
	});
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});