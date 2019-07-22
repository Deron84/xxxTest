Ext.onReady(function() {

	var topQueryPanel = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 550,
		autoHeight : true,
		labelWidth : 80,
		items : [ {
			width : 150,
			xtype : 'datefield',
			fieldLabel : '交易日期',
			format : 'Y-m-d',
			name : 'txnDateQ',
			id : 'txnDateQ',
			anchor : '60%'
		}, {
			xtype : 'textfield',
			width : 250,
			fieldLabel : '交易金额',
			id : 'txnAmtQ',
			regex: /^([0-9]{1,9})(\.[0-9]{1,2})?$/,
			regexText : '请输入不超过12位的最多带两位小数的正数',
			emptyText : '请输入不超过12位的最多带两位小数的正数'
		}, {
			xtype : 'dynamicCombo',
			width : 420,
			fieldLabel : '商户编号',
			methodName : 'getMchntIdInCup',
			hiddenName : 'mchtNoQ',
			editable : true
		}, {
			xtype : 'textfield',
			width : 420,
			fieldLabel : '补登原因',
			id : 'recordQ'
		}
//		, {
//			xtype : 'basecomboselect',
//			baseParams : 'OPR_STA_D',
//			fieldLabel : '操作状态',
//			hiddenName : 'oprStaQ',
//			id : 'oprStaId'
//		}
		, {
			xtype : 'basecomboselect',
			baseParams : 'SETTLE_STATUS_D',
			fieldLabel : '清算状态',
			hiddenName : 'settleStatusQ',
			id : 'settleStatusId'
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {
				gridStore.load();
//				queryWin.hide();
			}
		}, {
			text : '重填',
			handler : function() {
				topQueryPanel.getForm().reset();
			}
		} ]
	});

	var gridStore = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : 'gridPanelStoreAction.asp?storeId=tblDTxnInfo'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'INST_DATE',
			mapping : 'INST_DATE'
		}, {
			name : 'INST_TIME',
			mapping : 'INST_TIME'
		}, {
			name : 'MCHT_NO',
			mapping : 'MCHT_NO'
		}, {
			name : 'TRANS_AMT',
			mapping : 'TRANS_AMT'
		}, {
			name : 'CREATE_OPR',
			mapping : 'CREATE_OPR'
		}, {
			name : 'OPR_STA',
			mapping : 'OPR_STA'
		}, {
			name : 'SETTLE_STATUS',
			mapping : 'SETTLE_STATUS'
		}, {
			name : 'RECORD',
			mapping : 'RECORD'
		} ])
	});

	var txnModel = new Ext.grid.ColumnModel( [ {
		header : '交易日期',
		dataIndex : 'INST_DATE',
		width : 100
	}, {
		header : '交易时间',
		dataIndex : 'INST_TIME',
		width : 100
	}, {
		header : '商户',
		dataIndex : 'MCHT_NO',
		width : 520,
		renderer : function(val) {
			return getRemoteTrans(val, "mchntName")
		}
	}, {
		header : '交易金额',
		dataIndex : 'TRANS_AMT'
	}, {
		header : '记录操作员',
		dataIndex : 'CREATE_OPR'
	}
//	, {
//		header : '操作状态',
//		dataIndex : 'OPR_STA',
//		renderer : transOprSta
//	}
	, {
		header : '清算处理状态',
		dataIndex : 'SETTLE_STATUS',
		renderer : transSettleSta
	}, {
		header : '补登原因',
		dataIndex : 'RECORD'
	} ]);

	var addMenu = {
		text : '新增',
		width : 85,
		iconCls : 'add',
		handler : function() {
			txnWin.show();
			txnWin.center();
		}
	};

	var delMenu = {
		text : '作废',
		width : 85,
		iconCls : 'delete',
		disabled : true,
		handler : function() {
			var selectedRecord = grid.getSelectionModel().getSelected();
			if (selectedRecord == null) {
				showAlertMsg("没有选择记录", grid);
				return;
			}
			showConfirm('确定要作废该条补登信息吗？', grid, function(bt) {
				if (bt == 'yes') {
					// showProcessMsg('正在提交，请稍后......');
					var rec = grid.getSelectionModel().getSelected();
					Ext.Ajax.requestNeedAuthorise( {
						url : 'T60104Action.asp?method=delete',
						method : 'post',
						params : {
							txnDate : rec.get('INST_DATE'),
							txnTime : rec.get('INST_TIME'),
							mchtNo : rec.get('MCHT_NO'),
							txnAmt : rec.get('TRANS_AMT'),
							txnOpr : rec.get('CREATE_OPR'),
//							oprSta : rec.get('OPR_STA'),
							settleSta : rec.get('SETTLE_STATUS'),
							txnId : '60104',
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
		width : 550,
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
						queryWin.setPosition(10,
								Ext.getBody().getViewSize().height - 30);
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

		menuArr.push(queryMenu); // [4]

		// 终端信息列表
		var grid = new Ext.grid.GridPanel( {
			title : '商户手续费退还补登查询',
			region : 'center',
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			store : gridStore,
			sm : new Ext.grid.RowSelectionModel( {
				singleSelect : true
			}),
			cm : txnModel,
			clicksToEdit : true,
			forceValidation : true,
			tbar : menuArr,
			renderTo : Ext.getBody(),
			loadMask : {
				msg : '正在加载商户手续费退还补登信息列表......'
			},
			bbar : new Ext.PagingToolbar( {
				store : gridStore,
				pageSize : System[QUERY_RECORD_COUNT],
				displayInfo : true,
				displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg : '没有找到符合条件的记录'
			})
		});

		// 每次在列表信息加载前都将保存按钮屏蔽
		gridStore.on('beforeload', function() {
			Ext.apply(this.baseParams,
					{
						start : 0,
						txnDate : typeof (topQueryPanel.findById('txnDateQ')
								.getValue()) == 'string' ? '' : topQueryPanel
								.findById('txnDateQ').getValue().dateFormat(
										'Ymd'),
						txnAmt : topQueryPanel.getForm().findField('txnAmtQ')
								.getValue(),
						mchtNo : topQueryPanel.getForm().findField('mchtNoQ')
								.getValue(),
						record : topQueryPanel.getForm().findField('recordQ')
								.getValue(),
						txnNum : '9205',// 后台定义的交易码 表示商户手续费退还补登
//						oprSta : topQueryPanel.getForm().findField('oprStaQ')
//								.getValue(),
						settleStatus : topQueryPanel.getForm().findField(
								'settleStatusQ').getValue()
					});
		});
		gridStore.load();

		grid.getSelectionModel().on( {
			'rowselect' : function() {
				// 行高亮
				Ext.get(grid.getView().getRow(grid.getSelectionModel().last))
						.frame();
//				rec = grid.getSelectionModel().getSelected();
//
//				if (rec != null && rec.get('OPR_STA') == '1'
//						&& rec.get('SETTLE_STATUS') == '2') {// 只能作废未清算的
//					grid.getTopToolbar().items.items[2].enable();
//				} else {
//					grid.getTopToolbar().items.items[2].disable();
//				}
			}
		});

		// 添加表单
		var txnForm = new Ext.form.FormPanel( {
			frame : true,
			autoHeight : true,
			width : 500,
			labelWidth : 80,
			waitMsgTarget : true,
			layout : 'column',
			items : [ {
				layout : 'form',
				width : 400,
				items : [ {
					xtype : 'textfield',
					fieldLabel : '交易金额*',
					width : 250,
					id : 'txnAmt',
					allowBlank : false,
					regex: /^([0-9]{1,9})(\.[0-9]{1,2})?$/,
					regexText : '请输入不超过12位的最多带两位小数的正数',
					emptyText : '请输入不超过12位的最多带两位小数的正数'
				} ]
			}, {
				layout : 'form',
				width : 400,
				items : [ {
					xtype : 'dynamicCombo',
					width : 250,
					fieldLabel : '商户编号*',
					methodName : 'getMchntIdInBase',
					hiddenName : 'mchtNo',
					allowBlank : false,
					editable : true
				} ]
			}, {
				layout : 'form',
				width : 400,
				items : [ {
					xtype : 'textfield',
					width : 250,
					fieldLabel : '补登原因',
					id : 'record',
					maxLength : 60,
					vtype : 'isOverMax',
					allowBlank : false
				} ]
			} ]
		});

		// 信息窗口
		var txnWin = new Ext.Window( {
			title : '商户手续费退还补登',
			initHidden : true,
			header : true,
			frame : true,
			closable : false,
			modal : true,
			width : 500,
			autoHeight : true,
			layout : 'fit',
			items : [ txnForm ],
			buttonAlign : 'center',
			closeAction : 'hide',
			iconCls : 'logo',
			resizable : false,
			buttons : [ {
				text : '确定',
				handler : function() {
					if (txnForm.getForm().isValid()) {
						txnForm.getForm().submit( {
							url : 'T60104Action.asp?method=add',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								// 重新加载参数列表
							grid.getStore().reload();
							// 重置表单
							txnForm.getForm().reset();
							showSuccessMsg(action.result.msg, grid);
							txnWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, grid);
						},
						params : {
							txnNum : '9205',
							txnId : '60104',
							subTxnId : '01'
						}
						});
					}
				}
			}, {
				text : '重置',
				handler : function() {
					txnForm.getForm().reset();
				}
			}, {
				text : '关闭',
				handler : function() {
					txnWin.hide();
				}
			} ]
		});

		var mainUI = new Ext.Viewport( {
			layout : 'border',
			renderTo : Ext.getBody(),
			items : [ grid ]
		});
	})