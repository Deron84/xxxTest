Ext.onReady(function() {
	var flag2Store = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCHT_FALG3',function(ret){
 		var data=Ext.decode(ret);
 		flag2Store.loadData(data);
	});
	
	var mchntTermIdStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntInCardQuery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'InCardNum'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'InCardNum',mapping: 'InCardNum'},
			{name: 'inOutFlag',mapping: 'inOutFlag'},
			{name: 'agrBr',mapping: 'agrBr'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'contactName',mapping: 'contactName'},
			{name: 'contactTel',mapping: 'contactTel'},
			{name: 'contactIdentify',mapping: 'contactIdentify'},
			{name: 'inFlag',mapping: 'inFlag'},
			{name: 'useFlag',mapping: 'useFlag'},
			{name: 'messageLimit',mapping: 'messageLimit'},
			{name: 'dealBeginTime',mapping: 'dealBeginTime'},
			{name: 'dealEndTime',mapping: 'dealEndTime'}
		])
	});
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>商户英文名称：{engName}</p>',
			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			'<p>商户地址：{addr}</p>',
			//'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{commEmail}</p>',
			//'<p>法人代表名称：{manager}</p>',
			'<p>联系人姓名：{contact}</p>',
			'<p>联系人电话：{commTel}</p>',
			'<p>录入柜员：{crtOprId}&nbsp;&nbsp;&nbsp;&nbsp;审核柜员：{updOprId}</p>',
			'<p>最近更新时间：{updTs}</p>',{
			getmcc : function(val){return getRemoteTrans(val, "mcc");}
			}
		)
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
//		mchntRowExpander,
		new Ext.grid.RowNumberer(),
		{id: 'mchtNo',header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 130},
		{header: '终端号',dataIndex: 'termId',width: 80},
		{header: '商户名称',dataIndex: 'mchtNm',width: 250},
		{header: '机构号',dataIndex: 'agrBr',width: 80},
		{header: '机构名称',dataIndex: 'brhName',width: 130},
		{header: '转入转入(出)卡类型',dataIndex: 'inOutFlag',align: 'center',width: 150,id: 'inOutFlag',sortable: true,renderer : inOutFlagSta},
		{header: '单位转入(出)卡号',dataIndex: 'InCardNum',width: 130,id: 'InCardNum',sortable: true},
		{header: '联系人名称',dataIndex: 'contactName',id: 'contactName',width: 130},
		{header: '联系人身份证号码',dataIndex: 'contactIdentify',id: 'contactIdentify',width: 180},
		{header: '联系人手机号',dataIndex: 'contactTel',id: 'contactTel',width: 130},
		{header: '默认转入(出)卡标志',dataIndex: 'inFlag',align: 'center',id: 'inFlag',width: 130,renderer : defaultUseSta},
		{header: '使用标志',dataIndex: 'useFlag',align: 'center',id: 'useFlag',width: 120,renderer : incardUseSta},
		{header: '短信通知限额',dataIndex: 'messageLimit',width: 130},
		{header: '正常交易时间',dataIndex: 'dealBeginTime',width: 130},
		{header: '结束交易时间',dataIndex: 'dealEndTime',width: 130}
	]);
	
	function defaultUseSta(val){
		if(val=='0'){
			return '<font color="green">是</font>';
		}
		if(val=='1'){
			return '<font color="red">否</font>';
		}else{
			return '<font color="red">未知</font>';
		}
	}
	
	function incardUseSta(val){
		if(val=='0'){
			return '<font color="green">启用</font>';
		}
		if(val=='1'){
			return '<font color="red">未启用</font>';
		}else{
			return '<font color="red">未知</font>';
		}
	}
	function inOutFlagSta(val){
		if(val=='0'){
			return '<font color="green">单位转入卡</font>';
		}
		if(val=='1'){
			return '<font color="green">单位转出卡</font>';
		}else{
			return '<font color="red">未知</font>';
		}
	}
	//终端数据部分
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntTermInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'termStatus',mapping: 'termStatus'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'mchtCd',mapping: 'mchtCd'}
		])
	});
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'termId',header: '终端编号',dataIndex: 'termId',sortable: true,width: 70},
		{id: 'termSta',header: '终端状态',dataIndex: 'termStatus',renderer: termSta,width: 80},
		{id: 'termSta',header: '签到状态',dataIndex: 'termSignSta',renderer: termSignSta,width: 60},
		{id: 'mchtCd',dataIndex:'mchtCd',hidden:true}
	]);
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
		
	var modifyForm = new Ext.FormPanel({
		frame: true,
        border: true,
        width: 550,
        autoHeight: true,
        labelWidth: 120,
		items: [{
			width: 550,
        	xtype: 'panel',
	        layout: 'form',
       		items: [{
					xtype: 'basecomboselect',
		        	baseParams: 'MCHNT_INFO',
		        	labelStyle: 'padding-left: 5px',
					fieldLabel: '商户编号*',
					allowBlank: false,
					hiddenName: 'mchtNo',
					anchor: '90%',
					listeners: {
						'select': function() {
							mchntTermIdStore.removeAll();
							Ext.getCmp('idTerm').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('idTerm').value = '';
							SelectOptionsDWR.getComboDataWithParameter('MCHNT_TERM_ID',modifyForm.getForm().findField('mchtNo').getValue(),function(ret){
								mchntTermIdStore.loadData(Ext.decode(ret));
							});
						}
					}
	        	}]
		},{
			width: 550,
        	xtype: 'panel',
	        layout: 'form',
       		items: [{
		        	xtype: 'combo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '终端号*',
					store: mchntTermIdStore,
					displayField: 'displayField',
					valueField: 'valueField',
					mode: 'local',
					triggerAction: 'all',
					typeAhead: false,
					forceSelection: true,
					selectOnFocus: true,
					editable: true,
					allowBlank: false,
					lazyRender: true,
					anchor: '90%',
					blankText: '请选择终端信息………',
					id: 'idTerm',
					hiddenName: 'termId'
	        	}]
			},{xtype:'textfield',
				fieldLabel:'联系人名称',
				labelStyle: 'padding-left: 5px',
				id:'contactName',
				width : 250,
				maxLength:10,
				vtype:'isOverMax',
				allowBlank:true
			},{xtype:'textfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel:'联系人手机号码',
				id:'contactTel',
				width : 250,
				maxLength:12,
				vtype:'isOverMax',
				allowBlank:true
			},{xtype:'textfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel:'联系人身份证号码',
				id:'contactIdentify',
				width : 250,
				maxLength:20,
				vtype:'isOverMax',
				allowBlank:true
			},new Ext.form.TextField({
				id: 'inCardNum',
				labelStyle: 'padding-left: 5px',
				name: 'inCardNum',
				allowBlank: false,
				fieldLabel: '单位转入(出)卡号*',
				maxLength: 19,
				regex: /^(([0-9]{13,14})||([0-9]{19}))$/,
				vtype:'isOverMax',
				regexText : '请输入长度为13位、14位或者是19位长度的正确卡号',
				emptyText : '请输入不超过19位长度的正确卡号',
				width: 250
			}),{
				xtype : 'textfield',
				width : 250,
				labelStyle: 'padding-left: 5px',
				fieldLabel : '短信通知限额',
				id : 'messageLimit',
				allowBlank : true,
				regex: /^([0-9]{1,9})(\.[0-9]{1,2})?$/,
				regexText : '请输入不超过12位的最多带两位小数的正数',
				emptyText : '请输入不超过12位的最多带两位小数的正数'
			},{
				columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'basecomboselect',
                    fieldLabel: '转入转出卡类型*',
                    labelStyle: 'padding-left: 5px',
                    id: 'defaultInOutFlag',
                    editable: false,
                    selectOnFocus: true,
                    value: '0',
                    width:150,
//                    allowBlank:false,
                    hiddenName: 'inOutFlag',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','单位转入卡'],['1','单位转出卡']]
                    })
                }]
            },{
				columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'basecomboselect',
                    fieldLabel: '默认转入(出)卡标志*',
                    labelStyle: 'padding-left: 5px',
                    id: 'defaultInFlag',
                    editable: false,
                    selectOnFocus: true,
                    value: '0',
                    width:150,
//                    allowBlank:false,
                    hiddenName: 'inFlag',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','是'],['1','否']]
                    })
                }]
            },{
            	columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'basecomboselect',
                    fieldLabel: '使用标志*',
                    labelStyle: 'padding-left: 5px',
                    id: 'defaultUseFlag',
                    editable: false,
                    selectOnFocus:true,
                    value: '0',
                    width:80,
//                    allowBlank:false,
                    hiddenName: 'useFlag',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','启动'],['1','不启用']]
                    })
                }]
            },{
    			xtype : 'datefield',
    			labelStyle: 'padding-left: 5px',
    			id : 'dealBeginTime',
    			name : 'dealBeginTime',
    			format : 'Ymd',
    			altFormats : 'Ymd',
    			vtype : 'daterange',
    			endDateField : 'dealEndTime',
    			fieldLabel : '正常交易时间',
    			editable : false
    		},{
    			xtype: 'datefield',
    			labelStyle: 'padding-left: 5px',
    			id: 'dealEndTime',
    			name: 'dealEndTime',
    			format: 'Ymd',
    			altFormats: 'Ymd',
    			vtype: 'daterange',
    			startDateField: 'dealBeginTime',
    			fieldLabel: '结束交易时间',
    			editable: false
    		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(modifyForm.getForm().isValid()) {
	                    modifyForm.getForm().submit({
	                        url: 'T21100Action.asp?method=add',
	                        waitMsg: '正在提交，请稍后......',
	                        success: function(form,action) {
	                            modifyForm.getForm().reset();
	                            showSuccessMsg(action.result.msg,mainView);
	                            modifyWin.hide();
                                mchntStore.load();
	                        },
	                        failure: function(form,action) {
	                            showErrorMsg(action.result.msg,mainView);
	                        },
	                        params: {
	                            txnId: '21100',
	                            subTxnId: '01'
	                        }
	                    });
                    }
			}
		},{
			text: '关闭',
			handler: function() {
				modifyWin.hide();
			}
		}]
	});
	
	var modifyWin = new Ext.Window({
		title: '商户转入卡新增',
		layout: 'fit',
		width: 550,
		closeAction: 'hide',
		resizable: false,
		closable: true,
		modal: true,
		autoHeight: true,
		items: [modifyForm]
	});
	
	var addMenu = {
			text: '新增',
			id: 'addMchtButton',
			width: 85,
			iconCls: 'add',
			disabled: false,
			handler:function() {
				mchtManInfo = mchntGrid.getSelectionModel().getSelected();
	            termManInfo = termGrid.getSelectionModel().getSelected();
	            modifyWin.show();
			}
	};
	
	var modifyMenu = {
		text: '修改',
		id: 'modifyButton',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			selectedRecord = mchntGrid.getSelectionModel().getSelected();
			if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",mchntGrid);
                return;
            }
			var mchtNoQQQ = selectedRecord.data.mchtNo;
			var inCardNumQQQ = selectedRecord.data.InCardNum;
			var inOutFlagQQQ = selectedRecord.data.inOutFlag;
			var f = updCardForm.getForm();
			T21100.getTblInCardManagent(mchtNoQQQ,inCardNumQQQ,inOutFlagQQQ, function(data) {
				f.findField('mchtNoU').setValue(selectedRecord.get('mchtNo'));
				f.findField('idTermUU').setValue(selectedRecord.get('termId'));
				f.findField('inOutFlagU').setValue(selectedRecord.get('inOutFlag'));
				f.findField('inCardNumU').setValue(selectedRecord.get('InCardNum'));
				f.findField('contactTelU').setValue(selectedRecord.get('contactTel'));
				f.findField('cardContanctNameU').setValue(selectedRecord.get('contactName'));
				f.findField('cardContanctIdenU').setValue(selectedRecord.get('contactIdentify'));
				f.findField('messageLimitU').setValue(selectedRecord.get('messageLimit'));
				f.findField('inFlagUU').setValue(selectedRecord.get('inFlag'));
				f.findField('useFlagUU').setValue(selectedRecord.get('useFlag'));
				f.findField('startDateU').setValue(selectedRecord.get('dealBeginTime'));
				f.findField('endDateU').setValue(selectedRecord.get('dealEndTime'));
			});
			updCardWin.show();
			updCardWin.center();
		}
	};
	
	var deleteMenu = {
		id : 'deleButton',
		text : '删除',
		width : 85,
		iconCls : 'delete',
		disabled : true,
		handler : function() {
			showConfirm('确定删除吗？', mchntGrid, function(bt) {
				if (bt == 'yes') {
					showProcessMsg('正在提交信息，请稍后......');
					Ext.Ajax.requestNeedAuthorise( {
						url : 'T21100Action.asp?method=delete',
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if (rspObj.success) {
								showSuccessMsg(rspObj.msg, mchntGrid);
							} else {
								showErrorMsg(rspObj.msg, mchntGrid);
							}
							Ext.getCmp('deleButton').disable();
							// 重新加载商户信息
							mchntGrid.getStore().reload();
							termGrid.getStore().reload();
						},
						failure: function(form,action) {
	                        showErrorMsg(action.result.msg,mainView);
	                    },
	                    params : {
							mchtNo : mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
							inCardNum : mchntGrid.getSelectionModel().getSelected().get('InCardNum'),
							inOutFlag : mchntGrid.getSelectionModel().getSelected().get('inOutFlag'),
							txnId : '21100',
							subTxnId : '02'
						}
					});
					hideProcessMsg();
				}
			});

		}
	};
	
	// 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T21100Action.asp?method=upload',
		filePostName : 'files',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB',
		fileTypes : '*.txt;*.TXT',
		fileTypesDescription : '文本文件(*.txt;*.TXT)',
		title: '转入卡批量导入',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
			mchntGrid.getStore().reload();
		},
		exterMethod: function() {
		},
		postParams: {
			txnId: '21100',
			subTxnId: '03'
		}
	});
	var upload = {
			text: '导入转入卡文件',
			width: 85,
			id: 'upload',
			iconCls: 'upload',
			handler:function() {
				dialog.show();
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
	
	var termDetailMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			var selectedRecord = termGrid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",termGrid);
                return;
            } 
            alert(selectedRecord.get('mchtCd'));
            selectTermInfo(selectedRecord.get('termId'),selectedRecord.get('mchtCd'));	
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
		
	menuArr.push(addMenu);  //[0]
	menuArr.push('-');         //[1]
	menuArr.push(modifyMenu);  //[2]
	menuArr.push('-');         //[3]
	menuArr.push(deleteMenu);  //[4]
	menuArr.push('-');         //[5]
	menuArr.push(upload);         //[6]
	menuArr.push('-');         //[7]
	menuArr.push(queryCondition);         //[8]
	
	var termGrid = new Ext.grid.GridPanel({
		title: '终端信息',
		region: 'east',
		width: 260,
		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'termSta',
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
//		tbar: [termDetailMenu],
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: false
		})
	});
	
	// 禁用编辑按钮
//	termGrid.getStore().on('beforeload',function() {
//		termGrid.getTopToolbar().items.items[0].disable();
//	});
	
//	termGrid.getSelectionModel().on({
//		'rowselect': function() {
//			termGrid.getTopToolbar().items.items[0].enable();
//		}
//	});
	
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户转入卡管理',
		region: 'center',
		iconCls: 'T10403',
		frame: true,
		border: true,
		columnLines: true,
//		autoExpandColumn: 'mchtNm',
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	mchntStore.load();
	
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo;
		termStore.load({
			params: {
				start: 0,
				mchntNo: id
				}
			})
	});
	
//	termStore.on('beforeload', function() {
//		Ext.apply(this.baseParams,
//				{mchntNo : mchntGrid.getSelectionModel().getSelected().data.mchtNo
//				});
//	});
	
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].enable();
	});
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			mchntGrid.getTopToolbar().items.items[2].enable();
			mchntGrid.getTopToolbar().items.items[4].enable();
		}
	});
	

	/***************************查询条件*************************/
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 510,
		autoHeight: true,
		labelWidth: 120,
		items: [{
			width: 500,
        	xtype: 'panel',
	        layout: 'form',
       		items: [{
					xtype: 'basecomboselect',
		        	baseParams: 'MCHNT_INFO',
		        	labelStyle: 'padding-left: 5px',
					fieldLabel: '商户编号',
					allowBlank: true,
					hiddenName: 'mchtNoQ',
					anchor: '90%',
					listeners: {
						'select': function() {
							mchntTermIdStore.removeAll();
							Ext.getCmp('idTermQQ').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('idTermQQ').value = '';
							SelectOptionsDWR.getComboDataWithParameter('MCHNT_TERM_ID',queryForm.getForm().findField('mchtNoQ').getValue(),function(ret){
								mchntTermIdStore.loadData(Ext.decode(ret));
							});
						}
					}
	        	}]
		},{
			width: 500,
        	xtype: 'panel',
	        layout: 'form',
       		items: [{
		        	xtype: 'combo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '终端号',
					store: mchntTermIdStore,
					displayField: 'displayField',
					valueField: 'valueField',
					mode: 'local',
					triggerAction: 'all',
					typeAhead: false,
					forceSelection: true,
					selectOnFocus: true,
					editable: true,
//					allowBlank: false,
					lazyRender: true,
					anchor: '90%',
					blankText: '请选择终端信息………',
					id: 'idTermQQ',
					hiddenName: 'termIdV'
	        	}]
		},{	
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '持卡人姓名',
			id: 'cardContanctNameQ',
			name: 'cardContanctName',
			width: 10,
			width: 300
		},{	
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '持卡人身份证号码',
			id: 'cardContanctIden',
			name: 'cardContanctIdenQ',
			width: 20,
			width: 300
		},{	
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '单位转入(出)卡号',
			id: 'inCardNumQ',
			name: 'inCardNum',
			width: 300,
			maxLength: 19,
			regex: /^(([0-9]{13,14})||([0-9]{19}))$/,
			regexText : '请输入长度为13位、14位或者是19位长度的正确卡号',
			emptyText : '请输入不超过19位长度的正确卡号',
			vtype : 'isOverMax'
		},{	
			xtype: 'basecomboselect',
            fieldLabel: '单位转入转出卡类型',
            labelStyle: 'padding-left: 5px',
            id: 'inOutFlagQQ',
            editable: false,
            selectOnFocus:true,
            width:150,
            allowBlank:true,
            hiddenName: 'inOutFlagQ',
            store: new Ext.data.ArrayStore({
                fields: ['valueField','displayField'],
                data: [['0','单位转入卡'],['1','单位转出卡']]
            })
		},{	
			xtype: 'basecomboselect',
            fieldLabel: '使用标志',
            labelStyle: 'padding-left: 5px',
            id: 'defaultUseFlagQ',
            editable: false,
            selectOnFocus:true,
            width:80,
            allowBlank:true,
            hiddenName: 'useFlagQQ',
            store: new Ext.data.ArrayStore({
                fields: ['valueField','displayField'],
                data: [['0','启动'],['1','不启用']]
            })
		},{
			xtype : 'datefield',
			labelStyle: 'padding-left: 5px',
			id : 'startDate',
			name : 'startDate',
			format : 'Ymd',
			altFormats : 'Ymd',
			vtype : 'daterange',
			endDateField : 'endDate',
			fieldLabel : '正常交易时间',
			editable : false
		},{
			xtype: 'datefield',
			labelStyle: 'padding-left: 5px',
			id: 'endDate',
			name: 'endDate',
			format: 'Ymd',
			altFormats: 'Ymd',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '结束交易时间',
			editable: false
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
				mchntStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	/*******************  转入转出卡修改表单  *********************/
    var updCardForm = new Ext.form.FormPanel({
        frame: true,
        height: 500,
        labelWidth: 120,
        width : 500,
        autoHeight : true,
        waitMsgTarget: true,
        layout: 'column',
        items: [{
			width: 500,
        	xtype: 'panel',
	        layout: 'form',
       		items: [{
				xtype : 'textfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel : '商户号*',
				id : 'mchtNoU',
				name: 'mchtNo',
				maxLength : 20,
				width: 300,
				vtype : 'isOverMax',
				allowBlank : false,
				readOnly : true
	        	}]
		},{
			width: 500,
        	xtype: 'panel',
	        layout: 'form',
       		items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '终端号',
					id: 'idTermUU',
					name: 'termId',
					width: 300,
					allowBlank : false,
					readOnly : true
	        	}]
		},{
			width: 500,
        	xtype: 'panel',
	        layout: 'form',
       		items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '持卡人姓名',
					id: 'cardContanctNameU',
					name: 'contactName',
					maxLength: 10
	        	}]
		},{
			width: 500,
        	xtype: 'panel',
	        layout: 'form',
       		items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '联系人手机号码',
					maxLength: 12,
					id: 'contactTelU',
					name: 'contactTel'
	        	}]
		},{
			width: 500,
        	xtype: 'panel',
	        layout: 'form',
       		items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '持卡人身份证号码',
					maxLength: 20,
					id: 'cardContanctIdenU',
					name: 'contactIdentify'
	        	}]
		},{
			width: 500,
        	xtype: 'panel',
	        layout: 'form',
       		items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '单位转入(出)卡号',
					id : 'inCardNumU',
					name : 'inCardNum',
					width: 300,
					maxLength: 19,
					regex: /^(([0-9]{13,14})||([0-9]{19}))$/,
					regexText : '请输入长度为13位、14位或者是19位长度的正确卡号',
					emptyText : '请输入不超过19位长度的正确卡号',
					vtype : 'isOverMax'
	        	}]
		},{
			width: 500,
        	xtype: 'panel',
	        layout: 'form',
       		items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '短信通知限额',
					id : 'messageLimitU',
					name : 'messageLimit',
					width: 300,
					maxLength : 20,
					vtype : 'isOverMax',
					regex: /^([0-9]{1,9})(\.[0-9]{1,2})?$/,
					regexText : '请输入不超过12位的最多带两位小数的正数',
					emptyText : '请输入不超过12位的最多带两位小数的正数'
	        	}]
		},{	
			layout : 'form',
			width : 500,
			items : [{
				xtype: 'basecomboselect',
	            fieldLabel: '单位转入转出卡类型',
	            labelStyle: 'padding-left: 5px',
	            id: 'inOutFlagU',
	            editable: false,
	            selectOnFocus:true,
	            readOnly: true,
	            allowBlank:true,
	            hiddenName: 'inOutFlag',
	            store: new Ext.data.ArrayStore({
	                fields: ['valueField','displayField'],
	                data: [['0','单位转入卡'],['1','单位转出卡']]
	            })
			}]
		},{	
			layout : 'form',
			width : 500,
				items : [{
				xtype: 'basecomboselect',
	            fieldLabel: '使用标志',
	            labelStyle: 'padding-left: 5px',
	            id: 'useFlagUU',
	            editable: false,
	            selectOnFocus:true,
	            allowBlank:true,
	            hiddenName: 'useFlag',
	            store: new Ext.data.ArrayStore({
	                fields: ['valueField','displayField'],
	                data: [['0','启动'],['1','不启用']]
	            })
			}]
		},{	
			layout : 'form',
			width : 500,
				items : [{
				xtype: 'basecomboselect',
	            fieldLabel: '默认转入(出)卡标志',
	            labelStyle: 'padding-left: 5px',
	            id: 'inFlagUU',
	            editable: false,
	            selectOnFocus:true,
	            allowBlank:true,
	            hiddenName: 'inFlag',
	            store: new Ext.data.ArrayStore({
	                fields: ['valueField','displayField'],
	                data: [['0','是'],['1','否']]
	            })
			}]
		},{
			layout : 'form',
			width : 500,
				items : [{
				xtype : 'datefield',
				labelStyle: 'padding-left: 5px',
				id : 'startDateU',
				name : 'dealBeginTime',
				format : 'Ymd',
				altFormats : 'Ymd',
				vtype : 'daterange',
				endDateField : 'endDateU',
				fieldLabel : '正常交易时间',
				editable : false
			}]
		},{
			layout : 'form',
			width : 500,
				items : [{
				xtype: 'datefield',
				labelStyle: 'padding-left: 5px',
				id: 'endDateU',
				name: 'dealEndTime',
				format: 'Ymd',
				altFormats: 'Ymd',
				vtype: 'daterange',
				startDateField: 'startDateU',
				fieldLabel: '结束交易时间',
				editable: false
				}]
		}]
	});
    
    /****************转入转出卡修改*******************/
	var updCardWin = new Ext.Window({
        title: '终端修改',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 500,
//        autoHeight: true,
        layout: 'fit',
        items: [updCardForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'logo',
        resizable: false,
        buttons: [{
            text: '确定',
            handler: function() {
				if(updCardForm.getForm().isValid()) {
					updCardForm.getForm().submitNeedAuthorise({
						url: 'T21100Action.asp?method=update',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,updCardWin);
							//重新加载参数列表
							mchntGrid.getStore().reload();
							//重置表单
							updCardForm.getForm().reset();
							updCardWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,updCardWin);
						},
						params: {
							txnId: '21100',
							subTxnId: '04'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				updCardForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				updCardWin.hide();
			}
        }]
    });
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntNo: queryForm.getForm().findField('mchtNoQ').getValue(),
			termId: queryForm.getForm().findField('termIdV').getValue(),
			inCardNum: queryForm.findById('inCardNumQ').getValue(),
			inOutFlag: queryForm.getForm().findField('inOutFlagQ').getValue(),
			useFlag: queryForm.getForm().findField('useFlagQQ').getValue(),
			contactName: queryForm.findById('cardContanctNameQ').getValue(),
			contactIden: queryForm.getForm().findField('cardContanctIdenQ').getValue(),
			startDate : typeof (queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm
					.findById('startDate').getValue().dateFormat(
							'Ymd'),
			endDate : typeof (queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm
					.findById('endDate').getValue().dateFormat(
							'Ymd'),
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,termGrid],
		renderTo: Ext.getBody()
	});
});