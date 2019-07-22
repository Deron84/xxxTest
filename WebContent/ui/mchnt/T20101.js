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
	
		// 商户数据部分
		var mchntStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'gridPanelStoreAction.asp?storeId=mchntInfoTmp'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data',
				totalProperty : 'totalCount',
				idProperty : 'mchtNo'
			}, [ {
				name : 'mchtNo',
				mapping : 'mchtNo'
			}, {
				name : 'mchtNm',
				mapping : 'mchtNm'
			}, {
				name : 'engName',
				mapping : 'engName'
			}, {
				name : 'mcc',
				mapping : 'mcc'
			}, {
				name : 'licenceNo',
				mapping : 'licenceNo'
			}, {
				name : 'addr',
				mapping : 'addr'
			}, {
				name : 'mappingMchntcdOne',
				mapping : 'mappingMchntcdOne'
			}, {
				name : 'mappingMchntcdTwo',
				mapping : 'mappingMchntcdTwo'
			},
			// {name: 'postCode',mapping: 'postCode'},
					{
						name : 'commEmail',
						mapping : 'commEmail'
					},
					// {name: 'manager',mapping: 'manager'},
					{
						name : 'contact',
						mapping : 'contact'
					}, {
						name : 'commTel',
						mapping : 'commTel'
					}, {
						name : 'applyDate',
						mapping : 'applyDate'
					}, {
						name : 'mchtStatus',
						mapping : 'mchtStatus'
					}, {
						name : 'termCount',
						mapping : 'termCount'
					}, {
						name : 'crtOprId',
						mapping : 'crtOprId'
					}, {
						name : 'updOprId',
						mapping : 'updOprId'
					}, {
						name : 'updTs',
						mapping : 'updTs'
					} ])
		});

		var mchntRowExpander = new Ext.ux.grid.RowExpander(
				{
					tpl : new Ext.Template(
							'<p>商户英文名称：{engName}</p>',
							'<p>商户MCC：{mcc:this.getmcc()}</p>',
							'<p>商户地址：{addr}</p>',
							// '<p>邮编：{postCode}</p>',
							'<p>电子邮件：{commEmail}</p>',
							// '<p>法人代表名称：{manager}</p>',
							'<p>联系人姓名：{contact}</p>',
							'<p>联系人电话：{commTel}</p>',
							'<p>录入柜员：{crtOprId}&nbsp;&nbsp;&nbsp;&nbsp;审核柜员：{updOprId}</p>',
							'<p>最近更新时间：{updTs}</p>', {
								getmcc : function(val) {
									return getRemoteTrans(val, "mcc");
								}
							})
				});

		var mchntColModel = new Ext.grid.ColumnModel( [ mchntRowExpander, {
			id : 'mchtNo',
			header : '商户ID',
			dataIndex : 'mchtNo',
			sortable : true,
			width : 130
		}, {
			header : '商户名称',
			dataIndex : 'mchtNm',
			width : 200,
			id : 'mchtNm'
		}, {
			header : '商户号1',
			dataIndex : 'mappingMchntcdOne',
			width : 120
		}, {
			header : '商户号2',
			dataIndex : 'mappingMchntcdTwo',
			width : 120
		}, {
			header : '营业执照编号',
			dataIndex : 'licenceNo',
			width : 140
		}, {
			header : '注册日期',
			dataIndex : 'applyDate',
			width : 80,
			renderer : formatDt
		}, {
			header : '商户状态',
			dataIndex : 'mchtStatus',
			renderer : mchntSt,
			width : 80
		}, {
			header : '终端数量',
			dataIndex : 'termCount',
			width : 60
		} ]);
		
		//修改header
		setHeader(mchntColModel,3,4);

		// 终端数据部分
		var termStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'gridPanelStoreAction.asp?storeId=mchntTermInfo'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data',
				totalProperty : 'totalCount'
			}, [ {
				name : 'termId',
				mapping : 'termId'
			}, {
				name : 'termStatus',
				mapping : 'termStatus'
			}, {
				name : 'termSignSta',
				mapping : 'termSignSta'
			}, {
				name : 'mchtCd',
				mapping : 'mchtCd'
			} ])
		});
		var termColModel = new Ext.grid.ColumnModel( [
				new Ext.grid.RowNumberer(), {
					id : 'termId',
					header : '终端编号',
					dataIndex : 'termId',
					sortable : true,
					width : 70
				}, {
					id : 'termSta',
					header : '终端状态',
					dataIndex : 'termStatus',
					renderer : termSta,
					width : 80
				}, {
					id : 'termSta',
					header : '签到状态',
					dataIndex : 'termSignSta',
					renderer : termSignSta,
					//width : 60,
					hidden : true
				}, {
					id : 'mchtCd',
					dataIndex : 'mchtCd',
					hidden : true
				} ]);

		// 菜单集合
		var menuArr = new Array();
		var childWin;

		var editMenu = {
			text : '修改',
			width : 85,
			iconCls : 'edit',
			disabled : true,
			handler : function() {
				window.location.href = Ext.contextPath
						+ '/page/mchnt/T2010102.jsp?mchntId='
						+ mchntGrid.getSelectionModel().getSelected().get(
								'mchtNo');
			}
		};

		var stopMenu = {
			text : '冻结',
			width : 85,
			iconCls : 'stop',
			disabled : true,
			handler : function() {
			
			showConfirm('确定冻结吗？', mchntGrid, function(bt) {
				if (bt == 'yes') {
					showProcessMsg('正在提交信息，请稍后......');
					Ext.Ajax.requestNeedAuthorise( {
						url : 'T20101Action_stop.asp',
						params : {
							mchtNo : mchntGrid.getSelectionModel()
									.getSelected().get('mchtNo'),
							txnId : '20101',
							subTxnId : '03'
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if (rspObj.success) {
								showSuccessMsg(rspObj.msg, mchntGrid);
							} else {
								showErrorMsg(rspObj.msg, mchntGrid);
							}
							// 重新加载商户信息
						mchntGrid.getStore().reload();
					}
					});
					hideProcessMsg();
				}
			});
		  }
		};
		var recoverMenu = {
				text : '解冻',
				width : 85,
				iconCls : 'recover',
				disabled : true,
				handler : function() {
				
				showConfirm('确定取消冻结吗？', mchntGrid, function(bt) {
					if (bt == 'yes') {
						showProcessMsg('正在提交信息，请稍后......');
						Ext.Ajax.requestNeedAuthorise( {
							url : 'T20101Action_recover.asp',
							params : {
								mchtNo : mchntGrid.getSelectionModel()
										.getSelected().get('mchtNo'),
								txnId : '20101',
								subTxnId : '05'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg, mchntGrid);
								} else {
									showErrorMsg(rspObj.msg, mchntGrid);
								}
								// 重新加载商户信息
							mchntGrid.getStore().reload();
						}
						});
						hideProcessMsg();
					}
				});
			  }
			};

		var closeMenu = {
			text : '注销',
			width : 85,
			iconCls : 'stop',
			disabled : true,
			handler : function() {
				showConfirm('确定注销吗？', mchntGrid, function(bt) {
					if (bt == 'yes') {
						showProcessMsg('正在提交信息，请稍后......');
						Ext.Ajax.requestNeedAuthorise( {
							url : 'T20101Action_close.asp',
							params : {
								mchtNo : mchntGrid.getSelectionModel()
										.getSelected().get('mchtNo'),
								txnId : '20101',
								subTxnId : '04'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg, mchntGrid);
								} else {
									showErrorMsg(rspObj.msg, mchntGrid);
								}
								// 重新加载商户信息
							mchntGrid.getStore().reload();
						}
						});
						hideProcessMsg();
					}
				});

			}
		};
		
		var delMenu = {
				text : '删除',
				width : 85,
				iconCls : 'delete',
				disabled : true,
				handler : function() {
					showConfirm('确定删除吗？', mchntGrid, function(bt) {
						if (bt == 'yes') {
							showProcessMsg('正在提交信息，请稍后......');
							Ext.Ajax.requestNeedAuthorise( {
								url : 'T20101Action_delete.asp',
								params : {
									mchtNo : mchntGrid.getSelectionModel()
											.getSelected().get('mchtNo'),
									txnId : '20101',
									subTxnId : '08'
								},
								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, mchntGrid);
									} else {
										showErrorMsg(rspObj.msg, mchntGrid);
									}
									// 重新加载商户信息
								mchntGrid.getStore().reload();
							}
							});
							hideProcessMsg();
						}
					});

				}
			};

		function stopBack(bt, text) {
			if (bt == 'ok') {
				if (getLength(text) > 60) {
					alert('操作原因最多可以输入30个汉字或60个字母、数字');
					showInputMsg('提示', '请输入进行该操作的原因', true, stopBack);
					return;
				}
				showProcessMsg('正在提交信息，请稍后......');
				Ext.Ajax.requestNeedAuthorise( {
					url : 'T20101Action_stop.asp',
					params : {
						mchtNo : mchntGrid.getSelectionModel().getSelected()
								.get('mchtNo'),
						txnId : '20101',
						subTxnId : '03',
						exMsg : text
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if (rspObj.success) {
							showSuccessMsg(rspObj.msg, mchntGrid);
						} else {
							showErrorMsg(rspObj.msg, mchntGrid);
						}
						// 重新加载商户信息
					mchntGrid.getStore().reload();
				}
				});
				hideProcessMsg();
			}
		}

		var reviveMenu = {
			text : '恢复',
			width : 85,
			iconCls : 'recover',
			disabled : true,
			handler : function() {
			
			showConfirm('确定注销恢复吗？', mchntGrid, function(bt) {
				if (bt == 'yes') {
					showProcessMsg('正在提交信息，请稍后......');
					Ext.Ajax.requestNeedAuthorise( {
						url : 'T20101Action_revive.asp',
						params : {
							mchtNo : mchntGrid.getSelectionModel()
									.getSelected().get('mchtNo'),
							txnId : '20101',
							subTxnId : '09'
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if (rspObj.success) {
								showSuccessMsg(rspObj.msg, mchntGrid);
							} else {
								showErrorMsg(rspObj.msg, mchntGrid);
							}
							// 重新加载商户信息
						mchntGrid.getStore().reload();
					}
					});
					hideProcessMsg();
				}
			});
		  }
		};

		var detailMenu = {
			text : '查看详细信息',
			width : 85,
			iconCls : 'detail',
			disabled : true,
			handler : function() {
				showMchntDetailS(mchntGrid.getSelectionModel().getSelected()
						.get('mchtNo'), mchntGrid);
			}
		};

		var queryCondition = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};

		// menuArr.push(addMenu); //[0]
		// menuArr.push('-'); //[1]
		menuArr.push(editMenu); // [0]
		menuArr.push('-'); // [1]
		menuArr.push(stopMenu); // [2]
		menuArr.push('-'); // [3]
		menuArr.push(recoverMenu); // [4]
		menuArr.push('-'); // [5]
		menuArr.push(closeMenu); // [6]
		menuArr.push('-'); // [7]
		menuArr.push(reviveMenu); // [8]
		menuArr.push('-'); // [9]
		menuArr.push(detailMenu); // [10]
		menuArr.push('-'); // [11]
		menuArr.push(queryCondition); // [12]
		menuArr.push('-'); // [13]
		menuArr.push(delMenu); // [14]//商户不提供删除操作
		

		var termDetailMenu = {
			text : '详细信息',
			width : 85,
			iconCls : 'detail',
			disabled : true,
			handler : function() {
				selectTermInfo(termGrid.getSelectionModel().getSelected().get(
						'termId'), termGrid.getSelectionModel().getSelected()
						.get('mchtCd'));
			}
		};

		termGrid = new Ext.grid.GridPanel( {
			title : '终端信息',
			region : 'east',
			width : 200,
			iconCls : 'T301',
			split : true,
			collapsible : true,
			frame : true,
			border : true,
			columnLines : true,
			autoExpandColumn : 'termSta',
			stripeRows : true,
			store : termStore,
			sm : new Ext.grid.RowSelectionModel( {
				singleSelect : true
			}),
			cm : termColModel,
			clicksToEdit : true,
			forceValidation : true,
			tbar : [ termDetailMenu ],
			loadMask : {
				msg : '正在加载终端信息列表......'
			},
			bbar : new Ext.PagingToolbar( {
				store : termStore,
				pageSize : System[QUERY_RECORD_COUNT],
				displayInfo : false
			})
		});

		// 禁用编辑按钮
		termGrid.getStore().on('beforeload', function() {
			termGrid.getTopToolbar().items.items[0].disable();
		});

		termGrid.getSelectionModel().on( {
			'rowselect' : function() {
				termGrid.getTopToolbar().items.items[0].enable();
			}
		});

		var mchntGrid = new Ext.grid.GridPanel( {
			title : '商户信息维护',
			region : 'center',
			iconCls : 'T20101',
			frame : true,
			border : true,
			columnLines : true,
			autoExpandColumn : 'mchtNm',
			stripeRows : true,
			store : mchntStore,
			sm : new Ext.grid.RowSelectionModel( {
				singleSelect : true
			}),
			cm : mchntColModel,
			clicksToEdit : true,
			forceValidation : true,
			tbar : menuArr,
			plugins : mchntRowExpander,
			loadMask : {
				msg : '正在加载商户信息列表......'
			},
			bbar : new Ext.PagingToolbar( {
				store : mchntStore,
				pageSize : System[QUERY_RECORD_COUNT],
				displayInfo : true,
				displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg : '没有找到符合条件的记录'
			})
		});
//		mchntStore.load();

		mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
			var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo;
			termStore.load( {
				params : {
					start : 0,
					mchntNo : id
				}
			});
		});
		termStore.on('beforeload', function() {
			Ext.apply(this.baseParams,
					{mchntNo : mchntGrid.getSelectionModel().getSelected().data.mchtNo
					});
		});

		// 禁用编辑按钮
		mchntGrid.getStore().on('beforeload', function() {
			mchntGrid.getTopToolbar().items.items[0].disable();
			mchntGrid.getTopToolbar().items.items[2].disable();
			mchntGrid.getTopToolbar().items.items[4].disable();
			mchntGrid.getTopToolbar().items.items[6].disable();
			mchntGrid.getTopToolbar().items.items[8].disable();
			mchntGrid.getTopToolbar().items.items[10].disable();
			mchntGrid.getTopToolbar().items.items[14].disable();
		});

		var rec;

		mchntGrid.getSelectionModel().on( {
			'rowselect' : function() {
				// 行高亮
				Ext.get(
						mchntGrid.getView().getRow(
								mchntGrid.getSelectionModel().last)).frame();
				mchntGrid.getTopToolbar().items.items[10].enable();//可以查看详细信息
				// 根据商户状态判断哪个编辑按钮可用
				rec = mchntGrid.getSelectionModel().getSelected();
				var stat = rec.get('mchtStatus');
				if(stat == '0' || stat == '1' || stat == '2' || stat == '3' ){
					mchntGrid.getTopToolbar().items.items[0].enable();
				}else{
					mchntGrid.getTopToolbar().items.items[0].disable();
				}
				
				if(stat == '0'){
					//mchntGrid.getTopToolbar().items.items[2].enable();
					mchntGrid.getTopToolbar().items.items[2].disable();
				}else{
					mchntGrid.getTopToolbar().items.items[2].disable();
				}
				
				if(stat == '6'){
//					mchntGrid.getTopToolbar().items.items[4].enable();
					mchntGrid.getTopToolbar().items.items[4].disable();
				}else{
					mchntGrid.getTopToolbar().items.items[4].disable();
				}
				
				if(stat == '0' || stat == '6'){
					mchntGrid.getTopToolbar().items.items[6].enable();
				}else{
					mchntGrid.getTopToolbar().items.items[6].disable();
				}
				
				if (stat == '9') {
					mchntGrid.getTopToolbar().items.items[8].enable();
				} else {
					mchntGrid.getTopToolbar().items.items[8].disable();
				}
				
				if (stat == '1') {
					mchntGrid.getTopToolbar().items.items[14].enable();
				} else {
					mchntGrid.getTopToolbar().items.items[14].disable();
				}
			}
		});

		/** *************************查询条件************************ */

		var queryForm = new Ext.form.FormPanel( {
			frame : true,
			border : true,
			width : 500,
			autoHeight : true,
			labelWidth : 80,
			items : [
					{
						xtype : 'datefield',
						id : 'startDate',
						name : 'startDate',
						format : 'Y-m-d',
						altFormats : 'Y-m-d',
						vtype : 'daterange',
						//endDateField : 'endDate',
						fieldLabel : '注册开始日期',
						editable : false
					},
					{
						xtype : 'datefield',
						id : 'endDate',
						name : 'endDate',
						format : 'Y-m-d',
						altFormats : 'Y-m-d',
						vtype : 'daterange',
						//startDateField : 'startDate',
						fieldLabel : '注册结束日期',
						editable : false
					},
					{
						xtype: 'basecomboselect',
				        baseParams: 'MCHT_FALG1',
						fieldLabel: '商户性质1',
						id: 'idMchtFlag1Q',
						hiddenName: 'mchtFlag1',
						width: 380
					},{
						xtype: 'combo',
				        store:flag2Store,
						fieldLabel: '商户性质2',
						id: 'idMchtFlag2Q',
						hiddenName: 'mchtFlag2',
						editable:false,
						width: 380
					},{
						xtype : 'basecomboselect',
//						xtype : 'combo',
						id : 'mchtStatus',
						fieldLabel : '商户状态',
//						hiddenName: 'mchtStatusQuery',
//						store: new Ext.data.ArrayStore({
//	                        fields: ['valueField','displayField'], 
//
//	                        data: [['0','正常'],['1','添加待审核'],['2','添加审核退回'],['3','修改待审核'],['4','修改审核退回'],['5','冻结待审核'],['6','冻结'],['7','解冻恢复待审核'],['8','注销待审核'],['9','注销'],['R','注销恢复审核']]
//	                    }),
						baseParams : 'MCHT_STATUS',
						anchor : '70%'
					}, {
						xtype : 'basecomboselect',
						baseParams : 'BRH_BELOW',
						fieldLabel : '归属机构',
						id : 'idacqInstId',
						hiddenName : 'acqInstId',
						anchor : '70%'
//			            xtype: 'dynamicCombo',
//			        	methodName: 'getBranchId12',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '归属机构*',
//						allowBlank: false,
//						editable:true,
//						blankText: '请选择归属机构',
//						id: 'idacqInstId',
//						hiddenName: 'acqInstId',
//						anchor: '90%'
					}, {
						xtype : 'dynamicCombo',
						fieldLabel : '商户ID',
						methodName : 'getMchntIdTmp',
						hiddenName : 'mchtNo',
						editable : true,
						width : 380
					},{
						xtype:'textfield',
						fieldLabel: '商户号',
						id:'acmchntIdQ',
						name:'acmchntIdQ',
						width:300
					},{
						xtype: 'textfield',
						fieldLabel: '商户简称',
						id: 'mchtNmShort',
						name: 'mchtNmShort',
						width: 380
					},{	
						xtype: 'textfield',
						fieldLabel: '营业执照号',
						id: 'licenceNoQ',
						name: 'licenceNoQ',
						maxLength: 20,
						width: 380
					},{	
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						fieldLabel: '商户组别',
						hiddenName: 'mchtGrpQ',
						width: 380
					},{
						xtype: 'basecomboselect',
						baseParams: 'CONN_TYPE',
						fieldLabel: '连接类型',
						hiddenName: 'connTypeQ',
						allowBlank: true,
						width: 380
					} ]
		});
		var queryWin = new Ext.Window( {
			title : '查询条件',
			layout : 'fit',
			width : 500,
			autoHeight : true,
			items : [ queryForm ],
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
					} ],
			buttons : [ {
				text : '查询',
				handler : function() {
			       	var endtime=Ext.getCmp('endDate').getValue(),starttime=Ext.getCmp('startDate').getValue();
	            	if(endtime!=''&&starttime!=''&&endtime<starttime){
	            		showErrorMsg("请保证截止日期不小于起始日期",queryWin);
	    				return;
	            	}
					mchntStore.load();
					queryWin.hide();
				}
			}, {
				text : '清除查询条件',
				handler : function() {
					queryForm.getForm().reset();
				}
			} ]
		});

		mchntStore.on('beforeload', function() {
			Ext.apply(this.baseParams,
					{
						start : 0,
						mchtFlag1: queryForm.getForm().findField('mchtFlag1').getValue(),
						mchtFlag2: queryForm.getForm().findField('mchtFlag2').getValue(),
						mchntId : queryForm.getForm().findField('mchtNo')
								.getValue(),
						mchtStatus : queryForm.findById('mchtStatus')
								.getValue(),
						mchtGrp : queryForm.getForm().findField('mchtGrpQ')
								.getValue(),
						acmchntId: Ext.getCmp('acmchntIdQ').getValue(),
						mchtNmShort: queryForm.findById('mchtNmShort').getValue(),
						licenceNo: queryForm.findById('licenceNoQ').getValue(),
						connType:  queryForm.getForm().findField('connTypeQ').getValue(),
						startDate : typeof (queryForm.findById('startDate')
								.getValue()) == 'string' ? '' : queryForm
								.findById('startDate').getValue().dateFormat(
										'Ymd'),
						endDate : typeof (queryForm.findById('endDate')
								.getValue()) == 'string' ? '' : queryForm
								.findById('endDate').getValue().dateFormat(
										'Ymd'),
						brhId : queryForm.getForm().findField('acqInstId')
								.getValue()
					});
		});

		var mainView = new Ext.Viewport( {
			layout : 'border',
			items : [ mchntGrid, termGrid ],
			renderTo : Ext.getBody()
		});

	});