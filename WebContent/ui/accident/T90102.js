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
			width : 150,
			xtype : 'datefield',
			fieldLabel : '原交易日期',
			format : 'Y-m-d',
			name : 'orgTxnDateQ',
			id : 'orgTxnDateQ',
			anchor : '60%'
		}, {
			xtype : 'textfield',
			width : 250,
			fieldLabel : '原交易金额',
			id : 'txnAmtQ',
			regex: /^([0-9]{1,9})(\.[0-9]{1,2})?$/,
			regexText : '请输入不超过12位的最多带两位小数的正数',
			emptyText : '请输入不超过12位的最多带两位小数的正数'
		}, {
			xtype : 'textfield',
			width : 250,
			fieldLabel : '退货金额',
			id : 'retAmtQ',
			regex: /^([0-9]{1,9})(\.[0-9]{1,2})?$/,
			regexText : '请输入不超过12位的最多带两位小数的正数',
			emptyText : '请输入不超过12位的最多带两位小数的正数'
		}, {
			xtype : 'textfield',
			fieldLabel : '系统参考号*',
			id : 'retrivlRef',
			allowBlank : false,
			maxLength : 12,
			vtype : 'isOverMax',
			regex : /[0-9]{12}/,
			regexText : '请输入12位数字'
		}, {
			xtype : 'textfield',
			width : 300,
			fieldLabel : '退货说明',
			id : 'recordQ'
		}, {
			xtype : 'basecomboselect',
			baseParams:'OPR_STA_R',
			fieldLabel : '操作状态',
			hiddenName:'oprStaQ',
			id : 'oprStaId'
		}, {
			xtype : 'basecomboselect',
			baseParams:'SETTLE_STATUS_R',
			fieldLabel : '清算状态',
			hiddenName:'settleStatusQ',
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
			url : 'gridPanelStoreAction.asp?storeId=tblRTxnInfoCheck'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'INST_DATE',
			mapping : 'INST_DATE'
		},{
			name : 'INST_TIME',
			mapping : 'INST_TIME'
		},{
			name : 'SETTLE_DATE',
			mapping : 'SETTLE_DATE'
		},{
			name : 'MSG_SRC_ID',
			mapping : 'MSG_SRC_ID'
		},{
			name : 'TERM_SSN',
			mapping : 'TERM_SSN'
		},{
			name : 'SYS_SEQ_NUM',
			mapping : 'SYS_SEQ_NUM'
		},{
			name : 'KEY_RSP',
			mapping : 'KEY_RSP'
		},{
			name : 'PAN',
			mapping : 'PAN'
		},{
			name : 'CARD_TYPE',
			mapping : 'CARD_TYPE'
		},{
			name : 'LICENCE_NO',
			mapping : 'LICENCE_NO'
		},{
			name : 'MCHT_NO',
			mapping : 'MCHT_NO'
		},{
			name : 'RETURN_AMT',
			mapping : 'RETURN_AMT'
		},{
			name : 'ORIG_TXN_NUM',
			mapping : 'ORIG_TXN_NUM'
		},{
			name : 'ORIG_AMT',
			mapping : 'ORIG_AMT'
		},{
			name : 'ORIG_DIV_NO',
			mapping : 'ORIG_DIV_NO'
		},{
			name : 'ORIG_PRODUCT_CODE',
			mapping : 'ORIG_PRODUCT_CODE'
		},{
			name : 'ORIG_DATE',
			mapping : 'ORIG_DATE'
		},{
			name : 'RETRIVL_REF',
			mapping : 'RETRIVL_REF'
		},{
			name : 'ORIG_FEE_ALGO_ID',
			mapping : 'ORIG_FEE_ALGO_ID'
		},{
			name : 'ORIG_FEE_MD',
			mapping : 'ORIG_FEE_MD'
		},{
			name : 'ORIG_FEE_PARAM',
			mapping : 'ORIG_FEE_PARAM'
		},{
			name : 'ORIG_FEE_PCT_MIN',
			mapping : 'ORIG_FEE_PCT_MIN'
		},{
			name : 'ORIG_FEE_PCT_MAX',
			mapping : 'ORIG_FEE_PCT_MAX'
		},{
			name : 'ORIG_FEE',
			mapping : 'ORIG_FEE'
		},{
			name : 'RETURN_FEE',
			mapping : 'RETURN_FEE'
		},{
			name : 'TERM_NO',
			mapping : 'TERM_NO'
		},{
			name : 'CREATE_OPR',
			mapping : 'CREATE_OPR'
		},{
			name : 'UPDATE_OPR',
			mapping : 'UPDATE_OPR'
		},{
			name : 'UPDATE_DATE',
			mapping : 'UPDATE_DATE'
		},{
			name : 'MATCH_STA',
			mapping : 'MATCH_STA'
		},{
			name : 'OPR_STA',
			mapping : 'OPR_STA'
		},{
			name : 'SETTLE_STATUS',
			mapping : 'SETTLE_STATUS'
		},{
			name : 'RECORD',
			mapping : 'RECORD'
		},{
			name : 'RESERVED',
			mapping : 'RESERVED'
		},{
			name : 'AUDIT_STA',
			mapping : 'AUDIT_STA'
		} ])
	});
	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});
	
	var rexpander = new Ext.ux.grid.RowExpander(
			{
				tpl : new Ext.Template(
						'<p>退货信息来源：{MSG_SRC_ID:this.getMsgSrc()}</p>',
						'<p>卡类型：{CARD_TYPE:this.getCardType()}</p>',
						'<p>原交易码：{ORIG_TXN_NUM}</p>',
						'<p>原交易分期期数：{ORIG_DIV_NO}</p>',
						'<p>原交易分期产品代码：{ORIG_PRODUCT_CODE}</p>',
						'<p>原交易费率代码：{ORIG_FEE_ALGO_ID}</p>',
						'<p>原交易手续费类型：{ORIG_FEE_MD:this.getFeeMd()}</p>',
						'<p>原交易费率值：{ORIG_FEE_PARAM}</p>',
						'<p>原交易按比最低收费(元)：{ORIG_FEE_PCT_MIN}</p>',
						'<p>原交易按比最高收费(元)：{ORIG_FEE_PCT_MAX}</p>',
						'<p>录入柜员：{CREATE_OPR}</p>',
						'<p>最近修改柜员：{UPDATE_OPR}</p>',
						'<p>最近更新时间：{UPDATE_DATE}</p>', {
							getCardType:transCardType,
							getFeeMd : function(data){
					    		if(null == data) return '';
					    		var record = flagStore.getById(data);
					    		if(null != record){
					    			return record.data.displayField;
					    		}else{
					    			return data;
					    		}
							},
							getMsgSrc:function(val){
								if(val=='1901'){
									return '管理平台';
								}else{
									return '终端';
								}
							}
						})
			});

	var txnModel = new Ext.grid.ColumnModel( [ 
//	                                           rexpander,
	                                           {
		header : '交易日期',
		dataIndex : 'INST_DATE'
	}, {
		header : '交易时间',
		dataIndex : 'INST_TIME'
	},{
		header : '原交易日期',
		dataIndex : 'ORIG_DATE'
	},{
		header : '系统参考号',
		dataIndex : 'RETRIVL_REF'
	}, {
		header : '商户',
		dataIndex : 'MCHT_NO',
		width : 480,
		renderer : function(val) {
			return getRemoteTrans(val, "mchntName")
		}
	},{
		header : '终端号',
		dataIndex : 'TERM_NO'
	},{
		header : '卡号',
		width : 160,
		dataIndex : 'PAN'
	},{
		header : '原交易金额',
		dataIndex : 'ORIG_AMT'
	},{
		header : '原交易手续费',
		dataIndex : 'ORIG_FEE'
	},{
		header : '退货金额',
		dataIndex : 'RETURN_AMT'
	},{
		header : '退货手续费',
		hidden: true,
		dataIndex : 'RETURN_FEE'
	}, {
		header : '是否重复退货',
		dataIndex : 'MATCH_STA',
		renderer : function(val){
		if(val!=null&&val!=''){
			
			var i = val.substring(0,1);
			if(i=='0'){
				val='没有重复';
			}
			if(i=='1'){
				val='重复的';
			}
			if(i=='2'){
				val='没有重复但金额超限';
			}
			if(i=='3'){
				val='重复且金额超限';
			}
		}
		return val;
		
	}
	},{
		header : '匹配原交易',
		dataIndex : 'MATCH_STA',
		renderer : function(val){
		if(val!=null&&val!=''){
			
			var i = val.substring(1,2);
			if(i=='0'){
				val='匹配单笔';
			}
			if(i=='1'){
				val='匹配多笔';
			}
			if(i=='2'){
				val='未匹配上';
			}
			
		}
		return val;
		
	}
	}, {
		header : '操作状态',
		dataIndex : 'OPR_STA',
		renderer : transROprSta
	}, {
		header : '清算处理状态',
		dataIndex : 'SETTLE_STATUS',
		renderer : transRSettleSta
	}, {
		header:'审核状态',
		dataIndex:'AUDIT_STA',
		renderer:function(val){
			if(val=='0'){
				return '正常';
			}
			if(val=='1'){
				return '强制发起待审核';
			}
			if(val=='2'){
				return '作废待审核';
			}
			if(val == '3'){
				return '审核通过';
			}
			return '';
	}
	},{
		header : '退货说明',
		dataIndex : 'RECORD'
	} ]);
	function transRSettleSta(val){
		if(val=='0'){
			return '已清算';
		}
		if(val=='1'){
			return '待清算';
		}
		if(val == '2'){
			return '不清算';
		}
		return val;
	}
	function transROprSta(val){
		if(val=='0'){
			return '正常';
		}
		if(val=='1'){
			return '异常';
		}
		if(val=='2'){
			return '无效作废';
		}
		if(val=='3'){
			return '强制发起';
		}
		return '';
	}
	
	var acceptMenu = {
			text : '审核通过',
			width : 85,
			id:'accept',
			iconCls : 'accept',
			disabled : true,
			handler : function() {
				var selectedRecord = grid.getSelectionModel().getSelected();
				if (selectedRecord == null) {
					showAlertMsg("没有选择记录", grid);
					return;
				}
				showConfirm('确定通过审核吗？', grid, function(bt) {
					if (bt == 'yes') {
						// showProcessMsg('正在提交，请稍后......');
						var rec = grid.getSelectionModel().getSelected();
						Ext.Ajax.requestNeedAuthorise( {
							url : 'T90102Action.asp?method=accept',
							method : 'post',
							params : {
								txnDate : rec.get('ORIG_DATE'),
								instDate : rec.get('INST_DATE'),
								instTime : rec.get('INST_TIME'),
								retrivlRef : rec.get('RETRIVL_REF'),
								key : rec.get('KEY_RSP'),
								retAmt : rec.get('RETURN_AMT'),
								pan : rec.get('PAN'),
								txnAmt : rec.get('ORIG_AMT'),
								txnOpr : rec.get('CREATE_OPR'),
								oprSta : rec.get('OPR_STA'),
								settleSta : rec.get('SETTLE_STATUS'),
								retFee:rec.get('RETURN_FEE'),
								matchSta:rec.get('MATCH_STA'),
								auditSta:rec.get('AUDIT_STA'),
								mchtNo:rec.get('MCHT_NO'),
								txnSsn:rec.get('SYS_SEQ_NUM'),
								txnId : '90102',
								subTxnId : '01'
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

	var refuseMenu = {
		text : '审核拒绝',
		width : 85,
		id:'refuse',
		iconCls : 'refuse',
		disabled : true,
		handler : function() {
			var selectedRecord = grid.getSelectionModel().getSelected();
			if (selectedRecord == null) {
				showAlertMsg("没有选择记录", grid);
				return;
			}
			showConfirm('确定审核拒绝吗？', grid, function(bt) {
				if (bt == 'yes') {
					// showProcessMsg('正在提交，请稍后......');
					var rec = grid.getSelectionModel().getSelected();
					Ext.Ajax.requestNeedAuthorise( {
						url : 'T90102Action.asp?method=refuse',
						method : 'post',
						params : {
							txnDate : rec.get('ORIG_DATE'),
							instDate : rec.get('INST_DATE'),
							instTime : rec.get('INST_TIME'),
							retrivlRef : rec.get('RETRIVL_REF'),
							key : rec.get('KEY_RSP'),
							retAmt : rec.get('RETURN_AMT'),
							pan : rec.get('PAN'),
							txnAmt : rec.get('ORIG_AMT'),
							txnOpr : rec.get('CREATE_OPR'),
							oprSta : rec.get('OPR_STA'),
							settleSta : rec.get('SETTLE_STATUS'),
							retFee:rec.get('RETURN_FEE'),
							matchSta:rec.get('MATCH_STA'),
							auditSta:rec.get('AUDIT_STA'),
							mchtNo:rec.get('MCHT_NO'),
							txnSsn:rec.get('SYS_SEQ_NUM'),
							txnId : '90102',
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
		width : 500,
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

		menuArr.push(acceptMenu); // [0]
		menuArr.push('-'); // [1]
		menuArr.push(refuseMenu); // [2]
		menuArr.push('-'); // [3]
		menuArr.push(queryMenu); // [4]

		// 终端信息列表
		var grid = new Ext.grid.GridPanel( {
			title : '退货信息审核',
			region : 'center',
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			store : gridStore,
//			plugins : rexpander,
			sm : new Ext.grid.RowSelectionModel( {
				singleSelect : true
			}),
			cm : txnModel,
			clicksToEdit : true,
			forceValidation : true,
			tbar : menuArr,
			renderTo : Ext.getBody(),
			loadMask : {
				msg : '正在加载商户退货信息列表......'
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
						orgTxnDate : typeof (topQueryPanel.findById('orgTxnDateQ')
								.getValue()) == 'string' ? '' : topQueryPanel
								.findById('orgTxnDateQ').getValue().dateFormat(
										'Ymd'),
						txnDate : typeof (topQueryPanel.findById('txnDateQ')
								.getValue()) == 'string' ? '' : topQueryPanel
								.findById('txnDateQ').getValue().dateFormat(
										'Ymd'),
						txnAmt : topQueryPanel.getForm().findField('txnAmtQ')
								.getValue(),
						retAmt : topQueryPanel.getForm().findField('retAmtQ')
								.getValue(),
						retrivlRef : topQueryPanel.getForm().findField('retrivlRef')
								.getValue(),
						record : topQueryPanel.getForm().findField('recordQ')
								.getValue(),
						oprSta : topQueryPanel.getForm().findField('oprStaQ')
								.getValue(),	
						settleStatus : topQueryPanel.getForm().findField('settleStatusQ')
								.getValue(),
						auditFlag:'auditFlag',//只查待审核信息
						msgSrcId:''// 28##-终端 1901-管理平台
					});
		});
		gridStore.load();

		grid.getSelectionModel().on( {
			'rowselect' : function() {
				// 行高亮
				Ext.get(grid.getView().getRow(grid.getSelectionModel().last))
						.frame();
				rec = grid.getSelectionModel().getSelected();
				
				if ((rec != null && rec.get('MATCH_STA') == '10'//重复且匹配单笔
					&& rec.get('SETTLE_STATUS') == '1'//待清算
					&&rec.get('AUDIT_STA')!='0')||(rec != null && rec.get('MATCH_STA') == '11'//重复且匹配多笔
						&& rec.get('SETTLE_STATUS') == '1'//待清算
							&&rec.get('AUDIT_STA')!='0')) {//不是待审核状态
					grid.getTopToolbar().items.items[0].enable();
					grid.getTopToolbar().items.items[2].enable();
				} else {
					grid.getTopToolbar().items.items[0].disable();
					grid.getTopToolbar().items.items[2].disable();
				}
				
			}
		});

		var mainUI = new Ext.Viewport( {
			layout : 'border',
			renderTo : Ext.getBody(),
			items : [ grid ]
		});
	})