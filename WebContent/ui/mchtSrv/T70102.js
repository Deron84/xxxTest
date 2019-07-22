Ext.onReady(function() {
	
	//变量定义
	//form
	var fm = Ext.form;
	//当前操作
	var curOp = '';
	
	
	function lockAll(){
		Ext.getCmp('lock').disable();
		Ext.getCmp('active').disable();
		Ext.getCmp('add').disable();
		Ext.getCmp('edit').disable();
		Ext.getCmp('delete').disable();
	}
	function unlockAll(){
		Ext.getCmp('add').enable();
		Ext.getCmp('edit').enable();
		Ext.getCmp('delete').enable();
	}
	
	/**业务逻辑部分**/
	//获取当前问卷状态
	function getStatesCallback(ret){
		lockAll();
		var status = ret.substring(0,1);
		var msg = ret.substring(1);
		if (status == "T") {
			Ext.getCmp('PAPER_STATUS').setValue('<font color="green">正常</font>');
			Ext.getCmp('lock').enable();
		} else if (status == "L") {
			Ext.getCmp('PAPER_STATUS').setValue('<font color="blue">已被柜员['+msg+']锁定</font>');
			Ext.getCmp('active').enable();
			unlockAll();
		} else if (status == "U") {
			Ext.getCmp('PAPER_STATUS').setValue('<font color="red">已被柜员['+msg+']锁定</font>');
		} else if (status == "E") {
			Ext.getCmp('PAPER_STATUS').setValue('<font color="red">'+msg+'</font>');
		}
	}
	function getCountCallback(ret){
		Ext.getCmp('PAPER_COUNT').setValue(ret);
	}
	T70102.getStatus(getStatesCallback);
	T70102.getCount(getCountCallback);
	
	
	//锁定并编辑
	function lock(){
		showConfirm('确定要锁定并编辑该问卷吗？',mainGrid,function(bt) {
			if(bt == "yes") {
				Ext.Ajax.requestNeedAuthorise({
					url: 'T70102Action_lock.asp',
					success : function(form, action) {
						T70102.getStatus(getStatesCallback);
						T70102.getCount(getCountCallback);
						var rspObj = Ext.util.JSON.decode(form.responseText);
						if(rspObj.success){
							showSuccessMsg(rspObj.msg,mainGrid);
							mainStore.reload();
						}else{
							showErrorMsg(rspObj.msg,mainGrid);
						}
					},
					failure : function(form,action) {
						T70102.getStatus(getStatesCallback);
						T70102.getCount(getCountCallback);
						showErrorMsg(action.result.msg,mainGrid);
					},
					params: { 
						txnId: '70102',
						subTxnId: '04'
					}
				});
			}
		});
	}
	
	//激活修改
	function active(){
		showConfirm('确定要激活对该问卷的修改吗？',mainGrid,function(bt) {
			if(bt == "yes") {
				Ext.Ajax.requestNeedAuthorise({
					url: 'T70102Action_active.asp',
					success : function(form, action) {
						T70102.getStatus(getStatesCallback);
						T70102.getCount(getCountCallback);
						var rspObj = Ext.util.JSON.decode(form.responseText);
						if(rspObj.success){
							showSuccessMsg(rspObj.msg,mainGrid);
							mainStore.reload();
						}else{
							showErrorMsg(rspObj.msg,mainGrid);
						}
					},
					failure : function(form,action) {
						T70102.getStatus(getStatesCallback);
						T70102.getCount(getCountCallback);
						showErrorMsg(action.result.msg,mainGrid);
					},
					params: { 
						txnId: '70102',
						subTxnId: '05'
					}
				});
			}
		});
	}
	
	//新增题目
	function addQues(){
		curOp = "01",//add
		subWin.show();
		Ext.getCmp('saveBt').enable();
	}
	
	//修改题目
	function editQues(){
		var sel = mainGrid.getSelectionModel().getSelected();
		if(sel == null){
			showMsg("请选择一条记录后再进行操作。",mainGrid);return;
		}
		curOp = "02";//update
		subStore.load({
			params: {
					quesId: sel.data.QUES_ID
				},
			callback: function(records, options, success){
				if(success){
					subForm.getForm().findField('paperId').setValue(sel.data.PAPER_ID);
					subForm.getForm().findField('quesId').setValue(sel.data.QUES_ID);
					subForm.getForm().findField('question').setValue(sel.data.QUESTION);
					subWin.show(mainGrid);
				}else{
					showErrorMsg("加载信息失败，请刷新后重试",mainGrid);
				}
			}
		})
		Ext.getCmp('saveBt').enable();
	}
	
	//删除题目
	function deleteQues(){
		var sel = mainGrid.getSelectionModel().getSelected();
		if(sel == null){
			showMsg("请选择一条记录后再进行操作。",mainGrid);return;
		}
		showConfirm('确定要删除该问卷题目吗？',mainGrid,function(bt) {
			if(bt == "yes") {
				Ext.Ajax.request({
					url: 'T70102Action_delete.asp',
					success : function(form, action) {
						T70102.getCount(getCountCallback);
						var rspObj = Ext.util.JSON.decode(form.responseText);
						if(rspObj.success){
							showSuccessMsg(rspObj.msg,mainGrid);
							mainStore.reload();
						}else{
							showErrorMsg(rspObj.msg,mainGrid);
						}
					},
					failure : function(form,action) {
						T70102.getCount(getCountCallback);
						showErrorMsg(action.result.msg,mainGrid);
					},
					params: { 
						paperId: sel.data.PAPER_ID,
						quesId: sel.data.QUES_ID,
						txnId: '70102',
						subTxnId: '03'
					}
				});
			}
		});
	}
	function showPaper(){
		var iLeft = (window.screen.availWidth-10-790)/2; 
		window.open(Ext.contextPath + '/page/mchtSrv/T7010201.jsp', 'newwindow', 'height=600,width=790,top=0,left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no');
	}
	
	
	/**编辑题目表单部分**/
	var QUESTION = Ext.data.Record.create([{
        name: 'option',
        type: 'string'
	},{
        name: 'point',
        type: 'string'
    }])
	
	var subStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getQuestionInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'option',mapping: 'option',type:'string'},
			{name: 'point',mapping: 'point',type:'string'}
		]),
		autoLoad: false
	});
	
	var subModel = new Ext.grid.ColumnModel({
		columns: [{
            header: '选项',
            dataIndex: 'option',
            id: 'option',
            width: 100,
            sortable: true,
            editor: new fm.TextField({
                    allowBlank: false
                })
        },{
            header: '分数(0-100,整数)',
            dataIndex: 'point',
            width: 120,
            sortable: true,
            editor: new fm.NumberField({
                    allowNegative: false,
                    maxValue: 100,
                    minValue: 0,
                    decimalPrecision: 0
                })
        }]
	});
	
	
	var subGrid = new Ext.grid.EditorGridPanel({
		region: 'center',
        title: '选项',
        store: subStore,
        cm: subModel,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        autoExpandColumn: 'option', 
        frame: true,
        clicksToEdit: 1,
        tbar: [{
            text: '添加一行',
            iconCls: 'add_max',
            handler : function(){
                var p = new QUESTION();
                subGrid.stopEditing();
                subStore.insert(subStore.getCount(), p);
                subGrid.startEditing(subStore.getCount() - 1, 0);
            }
        },'-',{
            text: '删除一行',
            iconCls: 'del_min',
            handler : function(){
            	subGrid.stopEditing();
            	subStore.remove(subGrid.getSelectionModel().getSelected());
            }
        }]
    });
    
    
    
	//题目表单
	var subForm = new Ext.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		buttonAlign: 'center',
		items: [{
			xtype: 'panel',
			layout: 'form',
			items: [{
				xtype: 'hidden',
				fieldLabel: '问卷编号',
				id: 'paperId',
				name: 'paperId'
		    },{
				xtype: 'hidden',
				fieldLabel: '题目编号',
				id: 'quesId',
				name: 'quesId'
		    },{
				xtype: 'textarea',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '题目*',
				id: 'question',
				width: 460,
				vtype: 'isOverMax',
				maxLength: 180,
				allowBlank: false
		    }]
		},{
			xtype: 'panel',
			height: 280,
			layout: 'border',
			items: [subGrid]
		}],
		buttons: [{
			text: '保存',
			id: 'saveBt',
			handler: function(btn) {
				subGrid.stopEditing();
				Ext.getCmp('saveBt').disable();
				var frm = subForm.getForm();
				if(frm.isValid()) {
					subGrid.getStore().commitChanges();
					var dataArray = new Array();
					for(var i=0;i<subStore.getCount();i++){
						var re = subStore.getAt(i);
						subGrid.getSelectionModel().selectRow(i);
						
						if(re.data.option == null&&re.data.point == null){
							continue;
						}
						
						if(re.data.option == null){showMsg("第" + (i+1) + "行的[选项]不能为空",subGrid);return;}
						if(re.data.point == null){showMsg("第" + (i+1) + "行的[分数]不能为空",subGrid);return;}
                        
						if(Number(re.data.point)<0||Number(re.data.point)>100){
							showMsg("第" + (i+1) + "行的[分数]应为0-100间的数字(整数)",subGrid);return;
						}
						
						var data = {
							option: re.data.option,
							point: re.data.point
						};
						dataArray.push(data);
					}
					if(dataArray.length == 0){
						showMsg("题目应该至少包含一个选项",subGrid);
						return;
					}
					if(1!=1){
						
					}else{
						frm.submit({
							url: 'T70102Action_' + (curOp=='01'?'add':'update') + '.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								T70102.getCount(getCountCallback);
								mainStore.reload();
								subStore.removeAll();
								showSuccessMsg(action.result.msg,mainGrid);
								subWin.hide(mainGrid);
								frm.resetAll();
							},
							failure : function(form,action) {
								T70102.getCount(getCountCallback);
								Ext.getCmp('saveBt').enable();
								showErrorMsg(action.result.msg,mainGrid);
							},
							params: {
								txnId: '70102',
								subTxnId: curOp,
								data: Ext.encode(dataArray)
							}
						});
					}
				}
			}
			},{
			text: '取消',
			handler: function() {
				subWin.hide(mainGrid);
				subForm.getForm().resetAll();
				subStore.removeAll();
			}
		}]
	});
	var subWin = new Ext.Window({
		title: '商户问卷维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 670,
		autoHeight: true,
		items: [subForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false
	})
	
	
	
	
	
	
	
	/**主数据部分**/
	//数据集
	var mainStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=PaperDefInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'PAPER_ID',mapping: 'PAPER_ID'},
			{name: 'QUES_ID',mapping: 'QUES_ID'},	
			{name: 'QUESTION',mapping: 'QUESTION'},	
			{name: 'OPTIONS',mapping: 'OPTIONS'}
		]),
		autoLoad: true
	});
	
	
	//列模型
	var mainModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
			{header: '题目',dataIndex: 'QUESTION',width: 160},
    		{header: '选项',dataIndex: 'OPTIONS',id: 'OPTIONS'}
    	]);
	
    //GRID
	var mainGrid = new Ext.grid.GridPanel({
		title: '商户问卷维护',
		iconCls: 'T70102',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mainStore,
		cm: mainModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'OPTIONS',
		tbar: ['问卷状态：',{
			xtype: 'displayfield',
			id: 'PAPER_STATUS',
			width: 140
		},'-',{
			xtype: 'button',
			text: '锁定并编辑',
			name: 'lock',
			id: 'lock',
			iconCls: 'lock_edit',
			width: 55,
			disabled: true,
			handler:function() {
				lock();
			}
		},'-',{
			xtype: 'button',
			text: '激活修改',
			name: 'active',
			id: 'active',
			iconCls: 'save_16_1',
			width: 55,
			disabled: true,
			handler:function() {
				active();
			}
		},'-',{
			xtype: 'button',
			text: '新增题目',
			name: 'add',
			id: 'add',
			iconCls: 'add',
			width: 75,
			disabled: true,
			handler:function() {
				addQues();
			}
		},'-',{
			xtype: 'button',
			text: '修改题目',
			name: 'edit',
			id: 'edit',
			iconCls: 'edit',
			width: 75,
			disabled: true,
			handler:function() {
				editQues();
			}
		},'-',{
			xtype: 'button',
			text: '删除题目',
			name: 'delete',
			id: 'delete',
			iconCls: 'delete',
			width: 75,
			disabled: true,
			handler:function() {
				deleteQues();
			}
		},'-',{
			xtype: 'button',
			text: '问卷预览',
			name: 'show',
			id: 'show',
			iconCls: 'query_2',
			width: 75,
			handler:function() {
				showPaper();
			}
		},'-','当前权重：',{
			xtype: 'displayfield',
			id: 'PAPER_COUNT'
		}],
		loadMask: {
			msg: '正在加载信息列表，请稍候......'
		}
	});
	
	
	
	
	
	//Main View
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mainGrid],
		renderTo: Ext.getBody()
	})
})