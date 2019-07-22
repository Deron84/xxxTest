Ext.onReady(function() {
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getFeeInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data'
		},[
			{name: 'discCd',mapping: 'discCd'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'discOrg',mapping: 'discOrg'}
		]),
		autoLoad: false
	});
	
	
	var curOp;
	
	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});
	
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('TXN_NUM_FEE',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('TXN_NUM_EPOS',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});
	
	var fm = Ext.form;
	
	var Fee = Ext.data.Record.create([{
        name: 'txnNum',
        type: 'string'
	},{
        name: 'cardType',
        type: 'string'
	}, {
        name: 'flag',
        type: 'string'
    }, {
        name: 'feeValue',
        type: 'float'
    },{
        name: 'minFee',
        type: 'float'
    },{
        name: 'maxFee',
        type: 'float'
    }]);
	
    var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getDiscInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnNum',mapping: 'txnNum',type:'string'},
			{name: 'cardType',mapping: 'cardType',type:'string'},
			{name: 'minFee',mapping: 'minFee'},
			{name: 'maxFee',mapping: 'maxFee'},
			{name: 'flag',mapping: 'flag'},
			{name: 'feeValue',mapping: 'feeValue'}
		]),
		autoLoad: false
	});
	
	var cm = new Ext.grid.ColumnModel({
		columns: [{
            header: '交易类型',
            dataIndex: 'txnNum',
            width: 200,
            editor: {
					xtype: 'basecomboselect',
			        store: txnStore,
					id: 'idTxnNum',
					hiddenName: 'txnNum',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = txnStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '交易卡种',
            dataIndex: 'cardType',
            width: 120,
            editor: {
					xtype: 'basecomboselect',
			        store: cardTypeStore,
					id: 'idCardType',
					hiddenName: 'cardType',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = cardTypeStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '手续费类型',
            dataIndex: 'flag',
            width: 100,
            editor: {
					xtype: 'basecomboselect',
			        store: flagStore,
					id: 'idflagcom',
					hiddenName: 'flagcom',
					width: 180
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = flagStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '值',
            dataIndex: 'feeValue',
            width: 80,
            sortable: true,
            editor: new fm.NumberField({
            		allowBlank: false,
                    allowNegative: false,
                    maxValue: 100000
                })
        },{
            header: '按比最低收费(元)',
            dataIndex: 'minFee',
            width: 120,
            sortable: true,
            editor: new fm.NumberField({
                    allowNegative: false,
                    maxValue: 100000
                })
        },{
            header: '按比最高收费(元)',
            dataIndex: 'maxFee',
            width: 120,
            sortable: true,
            editor: new fm.NumberField({
                    allowNegative: false,
                    maxValue: 100000
                })
        }]
	});
	
	var grid = new Ext.grid.EditorGridPanel({
		region: 'center',
        title: '计费信息',
        store: store,
        cm: cm,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
       
        frame: true,
        clicksToEdit: 1,
        tbar: [{
            text: '添加一行',
            handler : function(){
                var p = new Fee();
                grid.stopEditing();
                store.insert(store.getCount(), p);
                grid.startEditing(store.getCount() - 1, 0);
            }
        },{
            text: '删除一行',
            handler : function(){
            	store.remove(grid.getSelectionModel().getSelected());
            }
        }]
    });
    store.insert(0, new Fee({
    	
    }));
    
	var feeForm = new Ext.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		items: [{
			xtype: 'panel',
			layout: 'column',
			items: [{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'hidden',
						fieldLabel: '计费代码*',
						id: 'discCd',
						name: 'discCd'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'hidden',
						fieldLabel: '所属机构*',
						id: 'discOrg',
						name: 'discOrg'
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '计费名称*',
						id: 'discNm',
						width: 360
		        	}]
	        	}]
			}]
		},{
			xtype: 'panel',
			height: 280,
			layout: 'border',
			items: [grid]
		},{
			xtype: 'panel',
			title: '配置说明',
			height: 140,
			header: true,
			frame: true,
			html: '<font color=red>第一步</font>：确定交易卡种，一次性选择全卡种或分四次选择不同卡种；<br>' +
				  '<font color=red>第二步</font>：确定手续费计费方式，分每笔按固定金额，按比例，按比例保底，按比例封顶，按比例保底加封顶。<br>' +
				  '<font color=red>第三步</font>：具体配置指定卡种范围指定手续费计费方式的参数<br>' +
				  '&nbsp&nbsp<font color=green>1)</font>：按固定金额方式：在“值”一栏填写手续费收取金额，如：0.30元/笔输入0.30；<br>'+
				  '&nbsp&nbsp<font color=green>2)</font>：按比例方式：在“值”一栏填写手续费占交易金额百分比，如0.78%输入0.78；<br>'+
				  '&nbsp&nbsp<font color=green>3)</font>：按比例加固定金额限制方式：<br>'+
				  '&nbsp&nbsp&nbsp&nbsp<font color=gray>a</font>：按比例保底：在“值”一栏填写手续费占交易金额百分比，在“按笔最低收费”一栏填写保底金额，如：0.3%保底1.20元，即在“值”项输入0.3、在“按笔最低收费”项输入1.2；<br>'+
				  '&nbsp&nbsp&nbsp&nbsp<font color=gray>b</font>：按比例封顶：在“值”一栏填写手续费占交易金额百分比，在“按笔最高收费”一栏填写封顶金额，如：1.228%封顶80元，即在“值”项输入1.228、在“按笔最高收费”项输入80；<br>'+
				  '&nbsp&nbsp&nbsp&nbsp<font color=gray>c</font>：按比例封顶加保底：在“值”一栏填写手续费占交易金额百分比，在“按笔最低收费”和“按笔最高收费”一栏分别填写保底和封顶金额，如：1.228%保底20封顶80元，即在“值”项输入1.228、在“按笔最低收费”项输入20、在“按笔最高收费”项输入80。<br>'
		}]
	});

	var groupWin = new Ext.Window({
		title: '计费算法维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 670,
		autoHeight: true,
		items: [feeForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		buttons: [{
			text: '保存',
			id: 'saveBt',
			handler: function(btn) {
				grid.focus();
				grid.stopEditing();
				var frm = feeForm.getForm();
				var records = '';
				if(feeForm.getForm().isValid()) {
					grid.getStore().commitChanges();
					var min = new Array();
					var dataArray = new Array();
					
					var txnArray = new Array();
					
					//把每条中的交易卡种代码放进这个数组中
					var txnNumArrayTmp = new Array();
					for(var i=0;i<store.getCount();i++){
//						grid.getSelectionModel().selectRow(i);
//						var re = grid.getSelectionModel().getSelected();
						
						var re = store.getAt(i);
						
						if(re.data.txnNum == null||re.data.txnNum==''){showMsg("第" + (i+1) + "行的[交易卡种]不能为空",grid);return;}
						if(re.data.flag == null){showMsg("第" + (i+1) + "行的[手续费类型]不能为空",grid);return;}
						if(re.data.feeValue == null){showMsg("第" + (i+1) + "行的[值]不能为空",grid);return;}
                        
						//把所有的记录中的卡种类型放进数组txnNumArrayTmp中
						txnNumArrayTmp[i] = re.data.txnNum;
						
						if(re.data.flag != '1'&&Number(re.data.feeValue)>=100){
							showMsg("第" + (i+1) + "行[当手续费类型是按比例时请输入小于100的值]",grid);
							return;
						}
                       
                        if(re.data.flag == '3' && re.data.minFee == null ){
							showMsg("第" + (i+1) + "行[当手续费类型是按比例保底时请输入按比最低收费]",grid);
							return;
						}

						
                        if(re.data.flag == '4' &&  re.data.maxFee ==null){
							showMsg("第" + (i+1) + "行[当手续费类型是按比例封顶时请输入按比最高收费]",grid);
							return;
						}
						
                        if(re.data.flag == '5' && (re.data.minFee == null||re.data.minFee == '' || re.data.maxFee == null||re.data.maxFee == ''||Number(re.data.minFee) > Number(re.data.maxFee))){
							showMsg("第" + (i+1) + "行[手续费类型是按比例保底封顶,请按大小输入按比最低收费和按比最高收费]",grid);
							return;
						}
						
                      
						
						//记录手续费类型的种类
						if(txnArray.getIndex(re.data.txnNum) == -1){
							txnArray.push(re.data.txnNum);
						}
						
						var data = {
							txnNum: re.data.txnNum,
							
							flag: re.data.flag,
							feeValue: re.data.feeValue,
							minFee: re.data.minFee,
							maxFee: re.data.maxFee
						};
						dataArray.push(data);
					}
					
					//判断卡种，如果没有”全部卡种“，而且其他四种卡种也没配置全，就不通过
					if(!validTxnNum(txnNumArrayTmp)) {
						showMsg("如果您没有配置{全部卡种},请配置全其他4种卡种,且卡种不能重复",grid);
						return;
					}
					
					
						frm.findField("discCd").enable();
						frm.submitNeedAuthorise({
							url: 'T20701Action_' + (curOp=='01'?'add':'update') + '.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								gridStore.reload();
								showSuccessMsg(action.result.msg,gridPanel);
								groupWin.hide(grid);
								feeForm.getForm().resetAll();
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,gridPanel);
							},
							params: {
								txnId: '20701',
								subTxnId: curOp,
								record: records,
								data: Ext.encode(dataArray)
							}
						});
					
				}
			}
		},{
			text: '重置',
			id: 'resetBt',
			handler: function() {
				if("01" == curOp){
					feeForm.getForm().resetAll();
					store.removeAll();
					store.insert(0, new Fee({
    					
    				}));
				}else if("02" == curOp){
					feeForm.getForm().loadRecord(baseStore.getAt(0));
					store.load({
							params: {
								start: 0,
								discCd: gridPanel.getSelectionModel().getSelected().data.discCd
								}
						});
				}else{
					showAlertMsg("没有获得当前的操作状态，重置失败",mchntForm);
				}
			}
		},{
			text: '取消',
			handler: function() {
				groupWin.hide(gridPanel);
				feeForm.getForm().resetAll();
//				store.removeAll();
				store.insert(0, new Fee({
    				floorMount: 0
    			}));
			}
		}]
	});

	//validTxnNum方法用来验证“交易卡种”，txnNumArray为数组Array类型
	//如果txnNumArray中有'0000'(全部卡种)，则返回true
	//如果txnNumArray中没有'0000',但有4种不同卡种，也返回true
	//否则，返回false
	function validTxnNum(txnNumArray) {
		
		var count = 0;
		
		for(var i=0; i<txnNumArray.length; i++) {
			if(txnNumArray[i] == '00') {
				
				return true;
			} else {
				
				for(var j=0; j<i; j++) {
					
					if(txnNumArray[j] == txnNumArray[i]){
						return false;
					} 
				}
				count++;
			}
		}
		
		if(count ==4) return true;
		else return false;
	};
		
	var detailGrid = new Ext.grid.GridPanel({
		region: 'center',
        title: '计费信息',
        store: store,
        cm: cm,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
       
        frame: true
    });
	
	
	var detailForm = new Ext.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		items: [{
			xtype: 'panel',
			layout: 'column',
			items: [{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '计费代码',
						id: 'dediscCd'
		        	}]
	        	}]
			},{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'BRH_BELOW_ID',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '所属机构',
						hiddenName: 'dediscOrg',
						allowBlank: false,
						width: 160,
						disabled: true
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '计费名称',
						id: 'dediscNm'
		        	}]
	        	}]
			}]
		},{
			xtype: 'panel',
			height: 200,
			layout: 'border',
			items: [detailGrid]
		},{
			xtype: 'panel',
			title: '配置说明',
			height: 210,
			header: true,
			frame: true,
			html: '<font color=red>第一步</font>：确定交易卡种，一次性选择全卡种或分四次选择不同卡种；<br>' +
				  '<font color=red>第二步</font>：确定手续费计费方式，分每笔按固定金额，按比例，按比例保底，按比例封顶，按比例保底加封顶。<br>' +
				  '<font color=red>第三步</font>：具体配置指定卡种范围指定手续费计费方式的参数<br>' +
				  '&nbsp&nbsp<font color=green>1)</font>：按固定金额方式：在“值”一栏填写手续费收取金额，如：0.30元/笔输入0.30；<br>'+
				  '&nbsp&nbsp<font color=green>2)</font>：按比例方式：在“值”一栏填写手续费占交易金额百分比，如0.78%输入0.78；<br>'+
				  '&nbsp&nbsp<font color=green>3)</font>：按比例加固定金额限制方式：<br>'+
				  '&nbsp&nbsp&nbsp&nbsp<font color=gray>a</font>：按比例保底：在“值”一栏填写手续费占交易金额百分比，在“按笔最低收费”一栏填写保底金额，如：0.3%保底1.20元，即在“值”项输入0.3、在“按笔最低收费”项输入1.2；<br>'+
				  '&nbsp&nbsp&nbsp&nbsp<font color=gray>b</font>：按比例封顶：在“值”一栏填写手续费占交易金额百分比，在“按笔最高收费”一栏填写封顶金额，如：1.228%封顶80元，即在“值”项输入1.228、在“按笔最高收费”项输入80；<br>'+
				  '&nbsp&nbsp&nbsp&nbsp<font color=gray>c</font>：按比例封顶加保底：在“值”一栏填写手续费占交易金额百分比，在“按笔最低收费”和“按笔最高收费”一栏分别填写保底和封顶金额，如：1.228%保底20封顶80元，即在“值”项输入1.228、在“按笔最低收费”项输入20、在“按笔最高收费”项输入80。<br>'
		}]
	});
	
	
	var detailWin = new Ext.Window({
		title: '计费算法详细信息',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 800,
		autoHeight: true,
		items: [detailForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		closable: true,
		resizable: false
	});
	

	// 菜单集合
	var menuArr = new Array();
	var childWin;
	
	var addMenu = {
		text: '新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			curOp = '01';
			store.removeAll();
			store.insert(0, new Fee({
    			floorMount: 0
    		}));
			groupWin.show();
			grid.focus();
		}
	};
	
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			baseStore.load({
				params: {
					discCd: gridPanel.getSelectionModel().getSelected().data.discCd
				},
				callback: function(records, options, success){
					if(success){
						curOp = "02";//update
						feeForm.getForm().loadRecord(baseStore.getAt(0));
						store.load({
							params: {
								start: 0,
								discCd: gridPanel.getSelectionModel().getSelected().data.discCd
								}
						});
						feeForm.getForm().findField("discCd").disable();
						groupWin.show(gridPanel);
					}else{
						showErrorMsg("加载计费算法信息失败，请刷新数据后重试",gridPanel);
					}
				}
			});
		}
	};
	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler:function() {
			var discCd = gridPanel.getSelectionModel().getSelected().data.discCd;
			showConfirm('确定要删除该计费算法信息吗吗？计费代码：' + discCd,grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T20701Action_delete.asp',
							success : function(form, action) {
								var rspObj = Ext.util.JSON.decode(form.responseText);
								if(rspObj.success){
									showSuccessMsg(rspObj.msg,gridPanel);
									gridStore.reload();
								}else{
									showErrorMsg(rspObj.msg,gridPanel);
								}
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,gridPanel);
							},
							params: { 
								discCd: discCd,
								txnId: '20701',
								subTxnId: '03'
							}
						});
					}
				});
		}
	};
	
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			//重新载入以获得最新数据
			baseStore.load({
				params: {
					discCd: gridPanel.getSelectionModel().getSelected().data.discCd
				},
				callback: function(records, options, success){
					if(success){
						detailForm.getForm().findField('dediscCd').setValue(baseStore.getAt(0).data.discCd);
						detailForm.getForm().findField('dediscNm').setValue(baseStore.getAt(0).data.discNm);
						detailForm.getForm().findField('dediscOrg').setValue(baseStore.getAt(0).data.discOrg);
						store.load({
							params: {
								start: 0,
								discCd: gridPanel.getSelectionModel().getSelected().data.discCd
								}
						});
						detailWin.show(gridPanel);
					}else{
						showErrorMsg("加载计费算法信息失败，请刷新数据后重试",gridPanel);
					}
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
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 380,
		autoHeight: true,
		labelWidth:80,
		items: [{
			xtype: 'textfield',
			id: 'qudiscCd',
			name: 'qudiscCd',
			vtype: 'alphanum',
			width:260,
			fieldLabel: '计费代码'
		},{
			xtype: 'textfield',
			id: 'qudiscNm',
			name: 'qudiscNm',
			width:260,
//			vtype: 'alphanum',
			fieldLabel: '计费名称'
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_ID',
			fieldLabel: '归属机构',
			width:260,
			editable: true,
			id: 'idqudiscOrg',
			hiddenName: 'qudiscOrg'
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 400,
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
	
	
	
	menuArr.push(detailMenu);  //[6]
	menuArr.push('-');         //[7]
	menuArr.push(queryCondition);  //[8]
	
	


	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfDiscCd'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[															
			{name: 'discCd',mapping: 'discCd'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'discOrg',mapping: 'discOrg'},
			{name: 'lastOperIn',mapping: 'lastOperIn'},
			{name: 'recUpdUserId',mapping: 'recUpdUserId'},				
			{name: 'recUpdTs',mapping: 'recUpdTs'},				
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	gridStore.load({
		params: {
			start: 0
		}
	});
	
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),									
		{header: '计费代码',dataIndex: 'discCd',sortable: true},
		{header: '计费名称',dataIndex: 'discNm',sortable: true,id:'discNm'},
		{header: '所属机构',dataIndex: 'discOrg',sortable: true,width: 160,renderer: function(v){return getRemoteTrans(v,'brh');}},
		{header: '操作柜员号',dataIndex: 'recUpdUserId',sortable: true},
		{header: '修改时间',dataIndex: 'recUpdTs',sortable: true},
		{header: '创建时间',dataIndex: 'recCrtTs',sortable: true}
	]);

	var gridPanel = new Ext.grid.GridPanel({
		title: '计费算法查询',
		region: 'center',
		iconCls: 'T207',
		frame: true,
		border: true,
		columnLines: true,	
		autoExpandColumn: 'discNm',
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
		forceValidation: true,
		tbar:[menuArr],
		loadMask: {
			msg: '正在加载计费算法信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	gridStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0
		});
		gridPanel.getTopToolbar().items.items[0].disable();
		
	}); 
	
	var rec;
	
	gridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel.getView().getRow(gridPanel.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = gridPanel.getSelectionModel().getSelected();
			if(null != rec){
				gridPanel.getTopToolbar().items.items[0].enable();
				
			}else{
				gridPanel.getTopToolbar().items.items[0].disable();
				
			}
		}
	});
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			discCd: queryForm.getForm().findField('qudiscCd').getValue(),
			discNm: queryForm.getForm().findField('qudiscNm').getValue(),
			discOrg: queryForm.getForm().findField('qudiscOrg').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [gridPanel],
		renderTo: Ext.getBody()
	});
	
});