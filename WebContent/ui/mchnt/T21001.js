Ext.onReady(function() {

	// 商户
		var mchtStore = new Ext.data.JsonStore( {
			fields : [ 'valueField', 'displayField' ],
			root : 'data'
		});
		SelectOptionsDWR.getComboData('MCHT_INFO_FOR_BIND', function(ret) {
			mchtStore.loadData(Ext.decode(ret));
		});

		var topQueryPanel = new Ext.form.FormPanel( {
			frame : true,
			border : true,
			width : 500,
			autoHeight : true,
			labelWidth : 80,
			items : [ {
				xtype : 'combo',
				fieldLabel : '商户',
				hiddenName : 'mchtNo',
				id : 'qryMchtNo',
				store : mchtStore
			}, {
				xtype : 'numberfield',
				id : 'qryCardNo',
				name : 'cardNo',
				fieldLabel : '卡号',
				regex : /^[0-9]*$/,
				regexText : '卡号只能是0-9的数字'
			}, {
				xtype : 'basecomboselect',
				baseParams : 'TBL_MCHT_BIND_CARDFLAG',
				fieldLabel : '卡标志',
				id : 'qryCardFlag',
				hiddenName : 'cardFlag'
			} ],
			buttons : [ {
				text : '查询',
				handler : function() {
					baseStore.load();
					queryWin.hide();
				}
			}, {
				text : '重填',
				handler : function() {
					topQueryPanel.getForm().reset();
				}
			} ]
		});

		var baseStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'gridPanelStoreAction.asp?storeId=mchtUnitPersonBindInfo'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data',
				totalProperty : 'totalCount'
			}, [ {
				name : 'mchtNoUpd',
				mapping : 'mchtNo'
			}, {
				name : 'mchtNameUpd',
				mapping : 'mchtName'
			}, {
				name : 'cardNoUpd',
				mapping : 'cardNo'
			}, {
				name : 'cardFlagUpd',
				mapping : 'cardFlag'
			}, {
				name : 'modiOprIdUpd',
				mapping : 'modiOprId'
			}, {
				name : 'initTimeUpd',
				mapping : 'initTime'
			}, {
				name : 'modiTimeUpd',
				mapping : 'modiTime'
			} ])
		});

		var divMchtModel = new Ext.grid.ColumnModel( [ {
			id : 'mchtNo',
			header : '商户号',
			dataIndex : 'mchtNoUpd',
			width : 100
		}, {
			header : '商户名称',
			dataIndex : 'mchtNameUpd',
			width : 150
		}, {
			header : '卡号',
			dataIndex : 'cardNoUpd',
			width : 100
		}, {
			header : '卡标志',
			dataIndex : 'cardFlagUpd',
			width : 100,
			renderer : mchtCardBindCardFlag
		}, {
			header : '最后操作柜员号',
			dataIndex : 'modiOprIdUpd',
			width : 100
		}, {
			header : '最后修改时间',
			dataIndex : 'modiTimeUpd'
		}, {
			header : '创建时间',
			dataIndex : 'initTimeUpd'
		} ]);

		var addMenu = {
			text : '新增',
			width : 85,
			iconCls : 'add',
			handler : function() {
				addWin.show();
				addWin.center();
			}
		};

		var delMenu = {
			text : '删除',
			width : 85,
			iconCls : 'delete',
			handler : function() {
				var selectedRecord = grid.getSelectionModel().getSelected();
				if (selectedRecord == null) {
					showAlertMsg("没有选择记录", grid);
					return;
				}
				showConfirm('确定要删除该签约信息吗？', grid, function(bt) {
					if (bt == 'yes') {
						// showProcessMsg('正在提交，请稍后......');
						var rec = grid.getSelectionModel().getSelected();
						Ext.Ajax.requestNeedAuthorise( {
							url : 'T21001Action_delete.asp',
							method : 'post',
							params : {
								mchtNo : rec.get('mchtNoUpd'),
								cardNo : rec.get('cardNoUpd'),
								cardFlag : rec.get('cardFlagUpd'),
								txnId : '21001',
								subTxnId : '02'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									grid.getStore().commitChanges();
									showSuccessMsg(rspObj.msg, grid);
								} else {
									grid.getStore().rejectChanges();
									showErrorMsg(rspObj.msg, grid);
								}
								grid.getStore().reload();
								hideProcessMsg();
							}
						});

					}
				});
			}
		};

		var queryMenu = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};
		var queryWin = new Ext.Window( {
			title : '查询条件',
			layout : 'fit',
			width : 300,
			autoHeight : true,
			items : [ topQueryPanel ],
			buttonAlign : 'center',
			closeAction : 'hide',
			resizable : false,
			closable : true,
			animateTarget : 'query',
			tools : [
					{
						id : 'minimize',
						handler : function(event, toolEl, panel, tc) {
							panel.tools.maximize.show();
							toolEl.hide();
							queryWin.collapse();
							queryWin.getEl().pause(1);
							queryWin.setPosition(10, Ext.getBody()
									.getViewSize().height - 30);
						},
						qtip : '最小化',
						hidden : false
					}, {
						id : 'maximize',
						handler : function(event, toolEl, panel, tc) {
							panel.tools.minimize.show();
							toolEl.hide();
							queryWin.expand();
							queryWin.center();
						},
						qtip : '恢复',
						hidden : true
					} ]
		});
		var menuArr = new Array();

		menuArr.push(addMenu); // [0]
		menuArr.push('-');
		// menuArr.push(updMenu); //[1]
		// menuArr.push('-');
		menuArr.push(delMenu); // [2]
		menuArr.push('-');
		menuArr.push(queryMenu); // [3]

		// 终端信息列表
		var grid = new Ext.grid.GridPanel( {
			title : '单位卡个人卡签约维护',
			region : 'center',
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			store : baseStore,
			sm : new Ext.grid.RowSelectionModel( {
				singleSelect : true
			}),
			cm : divMchtModel,
			clicksToEdit : true,
			forceValidation : true,
			tbar : menuArr,
			renderTo : Ext.getBody(),
			loadMask : {
				msg : '正在加载商户单位卡个人卡签约信息列表......'
			},
			bbar : new Ext.PagingToolbar( {
				store : baseStore,
				pageSize : System[QUERY_RECORD_COUNT],
				displayInfo : true,
				displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg : '没有找到符合条件的记录'
			})
		});

		// 每次在列表信息加载前都将保存按钮屏蔽
		baseStore.on('beforeload', function() {
			Ext.apply(this.baseParams, {
				start : 0,
				mchtNo : Ext.getCmp('qryMchtNo').getValue(),
				cardNo : Ext.getCmp('qryCardNo').getValue(),
				cardFlag : Ext.getCmp('qryCardFlag').getValue()
			});
		});
		baseStore.load();

		grid.getSelectionModel().on( {
			'rowselect' : function() {
				// 行高亮
				Ext.get(grid.getView().getRow(grid.getSelectionModel().last))
						.frame();
				rec = grid.getSelectionModel().getSelected();
				if (rec != null) {
					grid.getTopToolbar().items.items[1].enable();
				} else {
					grid.getTopToolbar().items.items[1].disable();
				}
			}
		});

		// 添加表单
		var addForm = new Ext.form.FormPanel( {
			frame : true,
			autoHeight : true,
			width : 300,
			labelWidth : 160,
			waitMsgTarget : true,
			layout : 'column',
			items : [ {
				layout : 'form',
				width : 389,
				items : [ {
					xtype : 'combo',
					fieldLabel : '商户*',
					hiddenName : 'mchtNo',
					id : 'mchtNoNew',
					allowBlank : false,
					store : mchtStore
				} ]
			}, {
				layout : 'form',
				width : 389,
				items : [ {
					xtype : 'numberfield',
					fieldLabel : '卡号*',
					maxLength : 19,
					vtype : 'isOverMax',
					name : 'cardNo',
					id : 'cardNoNew',
					allowBlank : false
				} ]
			}, {
				layout : 'form',
				width : 389,
				items : [ {
					xtype : 'basecomboselect',
					baseParams : 'TBL_MCHT_BIND_CARDFLAG',
					fieldLabel : '卡标志*',
					hiddenName : 'cardFlag',
					id : 'cardFlagNew',
					allowBlank : false
				} ]
			} ]
		});

		// 信息窗口
		var addWin = new Ext.Window( {
			title : '商户单位卡个人卡签约信息新增',
			initHidden : true,
			header : true,
			frame : true,
			closable : false,
			modal : true,
			width : 400,
			autoHeight : true,
			layout : 'fit',
			items : [ addForm ],
			buttonAlign : 'center',
			closeAction : 'hide',
			iconCls : 'logo',
			resizable : false,
			buttons : [ {
				text : '确定',
				handler : function() {
					if (addForm.getForm().isValid()) {
						addForm.getForm().submit( {
							url : 'T21001Action_add.asp',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								// 重新加载参数列表
							grid.getStore().reload();
							// 重置表单
							addForm.getForm().reset();
							showSuccessMsg(action.result.msg, grid);
							addWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, grid);
						},
						params : {
							txnId : '21001',
							subTxnId : '01'
						}
						});
					}
				}
			}, {
				text : '重置',
				handler : function() {
					addForm.getForm().reset();
				}
			}, {
				text : '关闭',
				handler : function() {
					addWin.hide();
				}
			} ]
		});

		var mainUI = new Ext.Viewport( {
			layout : 'border',
			renderTo : Ext.getBody(),
			items : [ grid ]
		});
	})