Ext.onReady(function() {
	var _brhId;//机构
	var _merchantNo;//商品号
	var _startDate;//分润开始时间
	var _endDate;//分润结束时间
	var brhLevel;//机构级别
	
	// 联机交易数据集
	var txnStore;
	var txnColModel;
	
	Ext.Ajax.request({
		url:"T10101Action.asp?method=getBrhLevel",// 要跳转的url,此为属性必须要有
		success: function(response, options){
			var ret = Ext.util.JSON.decode(response.responseText);
			brhLevel = ret.brhLevel;
			if(brhLevel == "0"){//1级机构
				txnStore = new Ext.data.Store({
					proxy: new Ext.data.HttpProxy({
						url: 'gridPanelStoreAction.asp?storeId=alipayShareSta'   
					}),
					reader: new Ext.data.JsonReader({
						root: 'data',
						totalProperty: 'totalCount'
					},[
						{name: 'orderTime',mapping: 'ORDER_TIME'},
						{name: 'mchtNm',mapping: 'MCHT_NM'},
						{name: 'settleAreaNo',mapping: 'SETTLE_AREA_NO'},
						{name: 'infoId',mapping: 'INFO_ID'},
						{name: 'ylMchntNo',mapping: 'Yl_MCHNT_NO'},
						{name: 'ylTermNo',mapping: 'YL_TERM_NO'},
						{name: 'termOrderId',mapping: 'TERM_ORDER_ID'},
						{name: 'orgFee',mapping: 'ORG_FEE'},
						{name: 'orderFee',mapping: 'ORDER_FEE'},
//						{name: 'noticePayType',mapping: 'PAY_TYPE'},
//						{name: 'noticeStatus',mapping: 'NOTICE_STATUS'},
						{name: 'cMchntNo',mapping: 'C_MCHNT_NO'},
						{name: 'noticeOrderId',mapping: 'NOTICE_ORDER_ID'},
						{name: 'cMchntBrh',mapping: 'C_MCHNT_BRH'},
						{name: 'orderRespTime',mapping: 'ORDER_RESP_TIME'},
						{name: 'agrBr',mapping: 'AGR_BR'},
						{name: 'brhName',mapping: 'BRH_NAME'},
						{name: 'sellerId',mapping: 'SELLER_ID'},
						{name: 'buyerId',mapping: 'BUYER_ID'},
						{name: 'sellerAccount',mapping: 'SELLER_EMAIL'},
						{name: 'buyerAccount',mapping: 'BUYER_LOGON_ID'},
						{name: 'mchtShare',mapping: 'MCHT_SHARE'},
						{name: 'oneorgShare',mapping: 'ONEORG_SHARE'},
						{name: 'twoorgShare',mapping: 'TWOORG_SHARE'},
						{name: 'threeorgShare',mapping: 'THREEORG_SHARE'}
					])
				}); 
				
				txnColModel = new Ext.grid.ColumnModel([
				        new Ext.grid.RowNumberer(),
				        {header: '交易时间',dataIndex: 'orderTime',width: 140,align:'center'},
						{header: '商户名称',dataIndex: 'mchtNm',width: 150,align:'center'},
						{header: '归属机构',dataIndex: 'settleAreaNo',width: 150,align:'center',renderer:function(val){return getRemoteTrans(val, "brhName");}},
						{header: '银联商户号',dataIndex: 'ylMchntNo',width: 140,align:'center'},
						{header: '银联终端号',dataIndex: 'ylTermNo',width: 140,align:'center'},
						{header: '终端流水号',dataIndex: 'termOrderId',width: 135,align:'center'},
						{header: '商户订单号',dataIndex: 'infoId',width: 235,align:'center'},
						{header: '原始金额（元）',dataIndex: 'orgFee',width: 85,align:'center'},
						{header: '交易金额（元）',dataIndex: 'orderFee',width: 85,align:'center'},
//						{header: '支付方式',dataIndex: 'noticePayType',width: 80,align:'center'},
//						{header: '交易状态',dataIndex: 'noticeStatus',width: 75,align:'center'},
						{header: '扫码支付商户号',dataIndex: 'cMchntNo',width: 140,align:'center'},
						{header: '第三方订单号',dataIndex: 'noticeOrderId',width: 135,align:'center'},
						{header: '第三方机构',dataIndex: 'cMchntBrh',width: 135,align:'center'},
						{header: '异步通知时间',dataIndex: 'orderRespTime',width: 140,align:'center'},
						{header: '签约网点',dataIndex: 'agrBr',width: 75,align:'center'},
						{header: '网点名称',dataIndex: 'brhName',width: 135,align:'center'},
						{header: '支付宝卖家ID',dataIndex: 'sellerId',width: 100,align:'center'},
						{header: '支付宝买家ID',dataIndex: 'buyerId',width: 100,align:'center'},
						{header: '支付宝卖家账号',dataIndex: 'sellerAccount',width: 100,align:'center'},
						{header: '支付宝买家账号',dataIndex: 'buyerAccount',width: 100,align:'center'},
						{header: '商户分润（元）',dataIndex: 'mchtShare',width: 100,align:'center'},
						{header: '1级机构分润（元）',dataIndex: 'oneorgShare',width: 100,align:'center'},
						{header: '2级机构分润（元）',dataIndex: 'twoorgShare',width: 100,align:'center'},
						{header: '3级机构分润（元）',dataIndex: 'threeorgShare',width: 100,align:'center'}
				]);
			}else if(brhLevel == "1"){//2级机构
				txnStore = new Ext.data.Store({
					proxy: new Ext.data.HttpProxy({
						url: 'gridPanelStoreAction.asp?storeId=alipayShareSta'   
					}),
					reader: new Ext.data.JsonReader({
						root: 'data',
						totalProperty: 'totalCount'
					},[
						{name: 'orderTime',mapping: 'ORDER_TIME'},
						{name: 'mchtNm',mapping: 'MCHT_NM'},
						{name: 'settleAreaNo',mapping: 'SETTLE_AREA_NO'},
						{name: 'infoId',mapping: 'INFO_ID'},
						{name: 'ylMchntNo',mapping: 'Yl_MCHNT_NO'},
						{name: 'ylTermNo',mapping: 'YL_TERM_NO'},
						{name: 'termOrderId',mapping: 'TERM_ORDER_ID'},
						{name: 'orgFee',mapping: 'ORG_FEE'},
						{name: 'orderFee',mapping: 'ORDER_FEE'},
						{name: 'cMchntNo',mapping: 'C_MCHNT_NO'},
						{name: 'noticeOrderId',mapping: 'NOTICE_ORDER_ID'},
						{name: 'cMchntBrh',mapping: 'C_MCHNT_BRH'},
						{name: 'orderRespTime',mapping: 'ORDER_RESP_TIME'},
						{name: 'agrBr',mapping: 'AGR_BR'},
						{name: 'brhName',mapping: 'BRH_NAME'},
						{name: 'sellerId',mapping: 'SELLER_ID'},
						{name: 'buyerId',mapping: 'BUYER_ID'},
						{name: 'sellerAccount',mapping: 'SELLER_EMAIL'},
						{name: 'buyerAccount',mapping: 'BUYER_LOGON_ID'},
						{name: 'mchtShare',mapping: 'MCHT_SHARE'},
						{name: 'twoorgShare',mapping: 'TWOORG_SHARE'},
						{name: 'threeorgShare',mapping: 'THREEORG_SHARE'}
					])
				}); 
				
				txnColModel = new Ext.grid.ColumnModel([
				        new Ext.grid.RowNumberer(),
				        {header: '交易时间',dataIndex: 'orderTime',width: 140,align:'center'},
						{header: '商户名称',dataIndex: 'mchtNm',width: 150,align:'center'},
						{header: '归属机构',dataIndex: 'settleAreaNo',width: 150,align:'center',renderer:function(val){return getRemoteTrans(val, "brhName");}},
						{header: '银联商户号',dataIndex: 'ylMchntNo',width: 140,align:'center'},
						{header: '银联终端号',dataIndex: 'ylTermNo',width: 140,align:'center'},
						{header: '终端流水号',dataIndex: 'termOrderId',width: 135,align:'center'},
						{header: '商户订单号',dataIndex: 'infoId',width: 235,align:'center'},
						{header: '原始金额（元）',dataIndex: 'orgFee',width: 85,align:'center'},
						{header: '交易金额（元）',dataIndex: 'orderFee',width: 85,align:'center'},
						{header: '扫码支付商户号',dataIndex: 'cMchntNo',width: 140,align:'center'},
						{header: '第三方订单号',dataIndex: 'noticeOrderId',width: 135,align:'center'},
						{header: '第三方机构',dataIndex: 'cMchntBrh',width: 135,align:'center'},
						{header: '异步通知时间',dataIndex: 'orderRespTime',width: 140,align:'center'},
						{header: '签约网点',dataIndex: 'agrBr',width: 75,align:'center'},
						{header: '网点名称',dataIndex: 'brhName',width: 135,align:'center'},
						{header: '支付宝卖家ID',dataIndex: 'sellerId',width: 100,align:'center'},
						{header: '支付宝买家ID',dataIndex: 'buyerId',width: 100,align:'center'},
						{header: '支付宝卖家账号',dataIndex: 'sellerAccount',width: 100,align:'center'},
						{header: '支付宝买家账号',dataIndex: 'buyerAccount',width: 100,align:'center'},
						{header: '商户分润（元）',dataIndex: 'mchtShare',width: 100,align:'center'},
						{header: '2级机构分润（元）',dataIndex: 'twoorgShare',width: 100,align:'center'},
						{header: '3级机构分润（元）',dataIndex: 'threeorgShare',width: 100,align:'center'}
				]);
			}else if(brhLevel == "2"){//3级机构
				txnStore = new Ext.data.Store({
					proxy: new Ext.data.HttpProxy({
						url: 'gridPanelStoreAction.asp?storeId=alipayShareSta'   
					}),
					reader: new Ext.data.JsonReader({
						root: 'data',
						totalProperty: 'totalCount'
					},[
						{name: 'orderTime',mapping: 'ORDER_TIME'},
						{name: 'mchtNm',mapping: 'MCHT_NM'},
						{name: 'settleAreaNo',mapping: 'SETTLE_AREA_NO'},
						{name: 'infoId',mapping: 'INFO_ID'},
						{name: 'ylMchntNo',mapping: 'Yl_MCHNT_NO'},
						{name: 'ylTermNo',mapping: 'YL_TERM_NO'},
						{name: 'termOrderId',mapping: 'TERM_ORDER_ID'},
						{name: 'orgFee',mapping: 'ORG_FEE'},
						{name: 'orderFee',mapping: 'ORDER_FEE'},
						{name: 'cMchntNo',mapping: 'C_MCHNT_NO'},
						{name: 'noticeOrderId',mapping: 'NOTICE_ORDER_ID'},
						{name: 'cMchntBrh',mapping: 'C_MCHNT_BRH'},
						{name: 'orderRespTime',mapping: 'ORDER_RESP_TIME'},
						{name: 'agrBr',mapping: 'AGR_BR'},
						{name: 'brhName',mapping: 'BRH_NAME'},
						{name: 'sellerId',mapping: 'SELLER_ID'},
						{name: 'buyerId',mapping: 'BUYER_ID'},
						{name: 'sellerAccount',mapping: 'SELLER_EMAIL'},
						{name: 'buyerAccount',mapping: 'BUYER_LOGON_ID'},
						{name: 'mchtShare',mapping: 'MCHT_SHARE'},
						{name: 'threeorgShare',mapping: 'THREEORG_SHARE'}
					])
				}); 
				
				txnColModel = new Ext.grid.ColumnModel([
				        new Ext.grid.RowNumberer(),
				        {header: '交易时间',dataIndex: 'orderTime',width: 140,align:'center'},
						{header: '商户名称',dataIndex: 'mchtNm',width: 150,align:'center'},
						{header: '归属机构',dataIndex: 'settleAreaNo',width: 150,align:'center',renderer:function(val){return getRemoteTrans(val, "brhName");}},
						{header: '银联商户号',dataIndex: 'ylMchntNo',width: 140,align:'center'},
						{header: '银联终端号',dataIndex: 'ylTermNo',width: 140,align:'center'},
						{header: '终端流水号',dataIndex: 'termOrderId',width: 135,align:'center'},
						{header: '商户订单号',dataIndex: 'infoId',width: 235,align:'center'},
						{header: '原始金额（元）',dataIndex: 'orgFee',width: 85,align:'center'},
						{header: '交易金额（元）',dataIndex: 'orderFee',width: 85,align:'center'},
						{header: '扫码支付商户号',dataIndex: 'cMchntNo',width: 140,align:'center'},
						{header: '第三方订单号',dataIndex: 'noticeOrderId',width: 135,align:'center'},
						{header: '第三方机构',dataIndex: 'cMchntBrh',width: 135,align:'center'},
						{header: '异步通知时间',dataIndex: 'orderRespTime',width: 140,align:'center'},
						{header: '签约网点',dataIndex: 'agrBr',width: 75,align:'center'},
						{header: '网点名称',dataIndex: 'brhName',width: 135,align:'center'},
						{header: '支付宝卖家ID',dataIndex: 'sellerId',width: 100,align:'center'},
						{header: '支付宝买家ID',dataIndex: 'buyerId',width: 100,align:'center'},
						{header: '支付宝卖家账号',dataIndex: 'sellerAccount',width: 100,align:'center'},
						{header: '支付宝买家账号',dataIndex: 'buyerAccount',width: 100,align:'center'},
						{header: '商户分润（元）',dataIndex: 'mchtShare',width: 100,align:'center'},
						{header: '3级机构分润（元）',dataIndex: 'threeorgShare',width: 100,align:'center'}
				]);
			}
			
			/**
			 * 查询条件
			 */
			var queryForm = new Ext.form.FormPanel({
				frame: true,
				border: true,
				width: 500,
				autoHeight: true,
				items: [{
		            id : 'aline',  
		            name : 'aline',  
		            layout: 'form',  
		            items : [
		            {
						xtype: 'basecomboselect',
				        baseParams: 'BRH_BELOW',
						fieldLabel: '归属机构*',
						width: 300,
						id: 'brhIdQ',
						emptyText : '选择统计分润的机构', 
						allowBlank: false,
						editable: true,
						hiddenName: 'brhId'
					},{
						xtype : 'textfield',
						fieldLabel : '商户号',
						id:'merchantNo',
						width : 300
					},{
						xtype: 'datefield',
						width : 300,
						plugins: 'monthPickerPlugin', 
						format:'Ym', 
						id: 'startDate',
						name: 'startDate',
						fieldLabel: '开始月份*',
						emptyText : '选择分润统计的开始月份', 
						allowBlank: false,
						editable: false 
					},{
						xtype: 'datefield',
						width : 300,
						plugins: 'monthPickerPlugin', 
						format:'Ym', 
						id: 'endDate',
						name: 'endDate',
						fieldLabel: '截止月份*',
						emptyText : '选择分润统计的截止月份', 
						allowBlank: false,
						editable: false 
					}]
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
						if(queryForm.getForm().isValid()) {
							var endtime=Ext.getCmp('endDate').getValue(),starttime=Ext.getCmp('startDate').getValue();
			            	if(endtime!=''&&starttime!=''&&endtime<starttime){
			            		showErrorMsg("请保证截止日期不小于起始日期",grid);
			    				return;
			            	}
							_startDate = queryForm.findById('startDate').getValue().dateFormat('Ym');
							_endDate = queryForm.findById('endDate').getValue().dateFormat('Ym');
							
							_merchantNo = queryForm.getForm().findField('merchantNo').getValue(),
							_brhId = queryForm.getForm().findField('brhId').getValue();
							
							grid.getTopToolbar().items.items[2].enable();
							grid.getTopToolbar().items.items[4].enable();
							queryWin.hide();
							pageBar.doRefresh();
							txnStore.load();
						}else{
							queryForm.getForm().isValid();
						}
					}
				},{
					text: '清除查询条件',
					handler: function() {
						queryForm.getForm().reset();
					}
				}]
			});
			var queryConditionMebu = {
				text: '录入查询条件',
				width: 85,
				id: 'query',
				iconCls: 'query',
				handler:function() {
					queryWin.show();
					queryForm.getForm().reset();
				}
			};
			
			var report = {
				text: '生成明细报表',
				width: 85,
				id: 'report',
				iconCls: 'download',
				handler:function() {
					var totalCount=txnStore.getTotalCount();
					if(totalCount > recordNum){
						showErrorMsg("导出的数据不能超过"+ recordNum + "条",grid);
						return;
					}
					showMask("正在为您准备报表，请稍后。。。",grid);
						Ext.Ajax.request({
							url: 'T51008Action_download2.asp',
							params: {
								merchantNo :_merchantNo,
								brhId : _brhId,
								startDate : _startDate,
								endDate : _endDate
							},
							success: function(rsp,opt){
								hideMask();
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																rspObj.msg+'&key=exl51008exl';
								} else {
									showErrorMsg(rspObj.msg,grid2);
								}
							},
							failure: function(rsp,opt){
								hideMask();
							}
						});
				}
			};
			
			var report2 = {
					text: '生成统计报表',
					width: 85,
					id: 'report2',
					iconCls: 'download',
					handler:function() {
						var totalCount=txnStore.getTotalCount();
						if(totalCount > recordNum){
							showErrorMsg("导出的数据不能超过"+ recordNum + "条",grid);
							return;
						}
						showMask("正在为您准备报表，请稍后。。。",grid);
							Ext.Ajax.request({
								url: 'T51008Action2_download2.asp',
								params: {
									merchantNo :_merchantNo,
									brhId : _brhId,
									startDate : _startDate,
									endDate : _endDate
								},
								success: function(rsp,opt){
									hideMask();
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										window.location.href = Ext.contextPath + '/page/system/exceldown.jsp?path='+
																	rspObj.msg+'&key=exl51008exl2';
									} else {
										showErrorMsg(rspObj.msg,grid2);
									}
								},
								failure: function(rsp,opt){
									hideMask();
								}
							});
					}
				};
			
			
			var menuArr = new Array();
			menuArr.push(queryConditionMebu);  //[0]
			menuArr.push('-');  //[0]
			menuArr.push(report);
			menuArr.push('-');  //[0]
			menuArr.push(report2);

			var pageBar = new Ext.PagingToolbar({
				store: txnStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			});

			// 交易查询
			var grid = new Ext.grid.GridPanel({
				title: '支付宝分润统计',
				iconCls: 'T501',
				region: 'center',
				frame: true,
				border: true,
				columnLines: true,
				stripeRows: true,
				clicksToEdit: true,
				store: txnStore,
				cm: txnColModel,
				forceValidation: true,
				renderTo: Ext.getBody(),
				loadMask: {
					msg: '正在加载联机交易列表......'
				},
				tbar: menuArr,
				bbar: pageBar
			});
			txnStore.on('beforeload', function(){
				Ext.apply(this.baseParams, {
					brhId: _brhId,
					merchantNo:_merchantNo,
					startDate : _startDate,
					endDate : _endDate
				});
			});
			grid.getTopToolbar().items.items[2].disable();
			grid.getTopToolbar().items.items[4].disable();
			new Ext.Viewport({
				layout: 'border',
				items: [grid],
				renderTo: Ext.getBody()
			});
			
		},
		failure:function(response,options){
			
		}
	});
	
});