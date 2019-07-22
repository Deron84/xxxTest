var selectTermInfoNamespace = {};

selectTermInfoNamespace.termPanel = new Ext.TabPanel( {
	activeTab : 0,
	height: 350,
    width: 650,
	frame : true,
	labelWidth:120,
	items : [ {
		title : '基本信息',
		layout : 'column',
		frame : true,
		items : [ {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'combofordispaly',
				fieldLabel : '终端所属机构',
				baseParams : 'BRH_BELOW',
				hiddenName : 'termBranchDtl'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : '终端MCC码',
				id : 'termMccDtl',
				name : 'termMccDtl'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'combofordispaly',
				fieldLabel : '产权属性',
				baseParams : 'PROP_TP',
				hiddenName : 'propTpD'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'combofordispaly',
				fieldLabel : '连接类型',
				baseParams : 'CONNECT_MODE',
				hiddenName : 'connectModeD'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'combofordispaly',
				fieldLabel : '第三方服务机构',
				baseParams : 'ORGAN',
				id : 'propInsNmD',
				hiddenName : 'propInsNmDtl'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : '外包分成比例%',
				id : 'propInsRateDtl',
				name : 'propInsRateDtl'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'combofordispaly',
				fieldLabel : '终端类型',
				baseParams : 'TERM_TYPE',
				id : 'termTpD',
				hiddenName : 'termTpDtl'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : '终端单笔限额(元)',
				id : 'termSingleLimitDtl',
				name : 'termSingleLimitDtl'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : '终端安装地址',
				readOnly : true,
				id : 'termAddrDtl',
				name : 'termAddrDtl'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : '版本号',
				id : 'bindTel1Dtl',
				name : 'bindTel1Dtl'
			} ]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : 'WIFI名称',
				id : 'bindTel2Dtl',
				name : 'bindTel2Dtl'
			} ]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : 'WIFI密码',
				id : 'bindTel3Dtl',
				name : 'bindTel3Dtl'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
				fieldLabel : '是否绑定电话',
				id : 'reserveFlag1Dtl',
				name : 'reserveFlag1Dtl',
				disabled : true
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
				fieldLabel : '是否收取押金',
				id : 'depositFlagDtl',
				name : 'depositFlagDtl',
				disabled : true
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : '押金金额(元)',
				id : 'depositAmtDtl',
				name : 'depositAmtDtl'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'combofordispaly',
				fieldLabel : '押金收取状态',
				id : 'depositStateD',
				hiddenName : 'depositStateDtl',
				store : new Ext.data.ArrayStore( {
					fields : [ 'valueField', 'displayField' ],
					data : [ [ '0', '已收' ], [ '1', '未收' ], [ '2', '退回' ] ]
				})
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : '终端租凭费用(元/天)',
				id : 'leaseFeeDtl',
				name : 'leaseFeeDtl'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : '单月限额(元)',
				id : 'rentFeeDtl',
				name : 'rentFeeDtl'
			} ]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : '固话终端版本号',
				id : 'misc2',
				name : 'misc2'
			} ]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'displayfield',
				fieldLabel : '终端硬件序列号',
				id : 'termName',
				name : 'termName'
			} ]
		} ]
	}, {
		title : '交易信息',
		layout : 'column',
		frame : true,
		items : [ {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
//				fieldLabel : '预授权请求',
				fieldLabel : '预授权',
				id : 'txnSupDtl1',
				name : 'txnSupDtl1',
				disabled:true,
				inputValue : '1'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
				fieldLabel : '预授权撤销',
				id : 'txnSupDtl2',
				name : 'txnSupDtl2',
				disabled:true,
				inputValue : '1'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
//				fieldLabel : '预授权完成请求',
				fieldLabel : '预授权完成',
				id : 'txnSupDtl3',
				name : 'txnSupDtl3',
				disabled:true,
				inputValue : '1'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
				fieldLabel : '预授权完成撤销',
				id : 'txnSupDtl4',
				name : 'txnSupDtl4',
				disabled:true,
				inputValue : '1'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
//				fieldLabel : '消费请求',
				fieldLabel : '消费',
				id : 'txnSupDtl5',
				name : 'txnSupDtl5',
				disabled:true,
				inputValue : '1'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
//				fieldLabel : '消费撤销请求',
				fieldLabel : '消费撤销',
				id : 'txnSupDtl6',
				name : 'txnSupDtl6',
				disabled:true,
				inputValue : '1'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
//				fieldLabel : '余额查询请求',
				fieldLabel : '余额查询',
				id : 'txnSupDtl7',
				disabled:true,
				name : 'txnSupDtl7',
				inputValue : '1'
			} ]
		}
//		, {
//			columnWidth : .5,
//			layout : 'form',
//			hidden:true,
//			items : [ {
//				xtype : 'checkbox',
//				fieldLabel : '存款请求',
//				id : 'txnSupDtl8',
//				name : 'txnSupDtl8',
//				disabled:true,
//				inputValue : '1'
//			} ]
//		}
//		, {
//			columnWidth : .5,
//			layout : 'form',
//			hidden:true,
//			items : [ {
//				xtype : 'checkbox',
//				fieldLabel : '存款撤销请求',
//				id : 'txnSupDtl9',
//				name : 'txnSupDtl9',
//				disabled:true,
//				inputValue : '1'
//			} ]
//		}, {
//			columnWidth : .5,
//			layout : 'form',
//			hidden:true,
//			items : [ {
//				xtype : 'checkbox',
//				fieldLabel : '取款请求 ',
//				id : 'txnSupDtl10',
//				name : 'txnSupDtl10',
//				disabled:true,
//				inputValue : '1'
//			} ]
//		}, {
//			columnWidth : .5,
//			layout : 'form',
//			hidden:true,
//			items : [ {
//				xtype : 'checkbox',
//				fieldLabel : '取款撤销请求',
//				id : 'txnSupDtl11',
//				name : 'txnSupDtl11',
//				disabled:true,
//				inputValue : '1'
//			} ]
//		}
		, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
//				fieldLabel : '分期消费请求',
				fieldLabel : '分期消费',
				id : 'txnSupDtl8',
				name : 'txnSupDtl8',
				disabled:true,
				inputValue : '1'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
//				fieldLabel : '分期消费撤销请求',
				fieldLabel : '分期消费撤销',
				id : 'txnSupDtl9',
				name : 'txnSupDtl9',
				disabled:true,
				inputValue : '1'
			} ]
		}
//		, {
//			columnWidth : .5,
//			layout : 'form',
//			hidden:true,
//			items : [ {
//				xtype : 'checkbox',
//				fieldLabel : '转账请求',
//				id : 'txnSupDtl14',
//				name : 'txnSupDtl14',
//				disabled:true,
//				inputValue : '1'
//			} ]
//		}, {
//			columnWidth : .5,
//			layout : 'form',
//			hidden:true,
//			items : [ {
//				xtype : 'checkbox',
//				fieldLabel : '转账撤销请求',
//				id : 'txnSupDtl15',
//				name : 'txnSupDtl15',
//				disabled:true,
//				inputValue : '1'
//			} ]
//		}
//		, {
//			columnWidth : .5,
//			layout : 'form',
//			items : [ {
//				xtype : 'checkbox',
//				fieldLabel : '快速支付',
//				id : 'txnSupDtl16',
//				name : 'txnSupDtl16',
//				disabled:true,
//				inputValue : '1'
//			} ]
//		}
//		, {
//			columnWidth : .5,
//			layout : 'form',
//			hidden:true,
//			disabled:true,
//			items : [ {
//				xtype : 'checkbox',
//				fieldLabel : '脚本处理结果通知',
//				id : 'txnSupDtl17',
//				name : 'txnSupDtl17',
//				disabled:true,
//				inputValue : '1'
//			} ]
//		}
		, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
//				fieldLabel : '无磁无密消费请求',
				fieldLabel : '无磁无密消费',
				id : 'txnSupDtl10',
				name : 'txnSupDtl10',
				disabled:true,
				inputValue : '1'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'checkbox',
//				fieldLabel : '无磁无密消费撤销请求',
				fieldLabel : '无磁无密消费撤销',
				id : 'txnSupDtl11',
				name : 'txnSupDtl11',
				disabled:true,
				inputValue : '1'
			} ]
		}
//		, {
//			columnWidth : .5,
//			layout : 'form',
//			items : [ {
//				xtype : 'checkbox',
//				fieldLabel : '快速支付',
//				id : 'txnSupDtl16',
//				name : 'txnSupDtl16',
//				disabled:true,
//				inputValue : '1'
//			} ]
//		}
		]
	} ]
});

selectTermInfoNamespace.termForm = new Ext.form.FormPanel( {
	frame : true,
	autoHeight : true,
	labelWidth : 80,
	waitMsgTarget : true,
	layout : 'form',
	labelAlign: 'left',
	items : [{
		layout:'column',
		items:[{
			 columnWidth : .33,
			 layout:'form',
			 items:[{
					xtype : 'displayfield',
					fieldLabel : '终端ID',
					labelStyle : 'padding-left: 5px',
					name : 'termIdDtl',
					id : 'termIdDtl'
				}]
		},{
			 columnWidth : .33,
			 layout:'form',
			 items:[{
					xtype : 'displayfield',
					fieldLabel : '一卡通终端号',
					labelStyle : 'padding-left: 5px',
					name : 'mappingTermidOneDtl',
					id : 'mappingTermidOne'
				}]
		},{
			 columnWidth : .33,
			 layout:'form',
			 items:[{
					xtype : 'displayfield',
					fieldLabel : '银联终端号',
					labelStyle : 'padding-left: 5px',
					name : 'mappingTermidTwoDtl',
					id : 'mappingTermidTwo'
				}]
		},{
			 columnWidth : .33,
			 layout:'form',
			 labelWidth : 60,
			 items:[{
					xtype : 'displayfield',
					fieldLabel : '商户ID',
					labelStyle : 'padding-left: 5px',
					name : 'mchnNoDtl',
					id : 'mchnNoD'
				}]
		},{
			 columnWidth : .33,
			 layout:'form',
			 items:[{
					xtype : 'displayfield',
					fieldLabel : '一卡通商户',
					labelStyle : 'padding-left: 5px',
					name : 'mappingMchntcdOneDtl',
					id : 'mappingMchntcdOne'
				}]
		},{
			 columnWidth : .33,
			 layout:'form',
			 items:[{
					xtype : 'displayfield',
					fieldLabel : '银联商户',
					labelStyle : 'padding-left: 5px',
					name : 'mappingMchntcdTwoDtl',
					id : 'mappingMchntcdTwo'
				}]
		},{columnWidth:1,
			layout:'form',
			items:[selectTermInfoNamespace.termPanel]}]
	}]
});

selectTermInfoNamespace.termWin = new Ext.Window( {
	title : '终端信息',
	initHidden : true,
	header : true,
	frame : true,
	closable : true,
	modal : true,
	width : 626,
	autoHeight : true,
	layout : 'fit',
	items : [ selectTermInfoNamespace.termForm ],
	buttonAlign : 'center',
	closeAction : 'hide',
	iconCls : 'logo',
	resizable : false
});


selectTermInfoNamespace.termInfoStore = new Ext.data.Store( {

	proxy : new Ext.data.HttpProxy( {
		url : 'loadRecordAction.asp'
	}),

	reader : new Ext.data.JsonReader( {
		root : 'data',
		idProperty : 'id'
	}, [ {
		name : 'termIdDtl',
		mapping : 'id.termId'
	}, {
		name : 'recCrtTs',
		mapping : 'recCrtTs'
	}, {
		name : 'mappingMchntcdOneDtl',
		mapping : 'mappingMchntcdOne'
	}, {
		name : 'mappingMchntcdTwoDtl',
		mapping : 'mappingMchntcdTwo'
	}, {
		name : 'mappingTermidOneDtl',
		mapping : 'mappingTermidOne'
	}, {
		name : 'mappingTermidTwoDtl',
		mapping : 'mappingTermidTwo'
	}, {
		name : 'mchnNoDtl',
		mapping : 'id.mchtCd'
	}, {
		name : 'termMccDtl',
		mapping : 'termMcc'
	}, {
		name : 'termBranchDtl',
		mapping : 'termBranch'
	}, {
		name : 'termSignStaU',
		mapping : 'termSignSta'
	}, {
		name : 'termFactoryDtl',
		mapping : 'termFactory'
	}, {
		name : 'termMachTpDtl',
		mapping : 'termMachTp'
	}, {
		name : 'termIdIdU',
		mapping : 'termIdId'
	}, {
		name : 'termVerDtl',
		mapping : 'termVer'
	}, {
		name : 'termTpD',
		mapping : 'termTp'
	}, {
		name : 'contTelDtl',
		mapping : 'contTel'
	}, {
		name : 'propTpD',
		mapping : 'propTp'
	}, {
		name : 'propInsNmDtl',
		mapping : 'propInsNm'
	}, {
		name : 'termBatchNmDtl',
		mapping : 'termBatchNm'
	}, {
		name : 'termStlmDtDtl',
		mapping : 'termStlmDt'
	}, {
		name : 'connectModeD',
		mapping : 'connectMode'
	}, {
		name : 'financeCard1Dtl',
		mapping : 'financeCard1'
	}, {
		name : 'financeCard2Dtl',
		mapping : 'financeCard2'
	}, {
		name : 'termTxnSupU',
		mapping : 'termTxnSup'
	}, {
		name : 'financeCard3Dtl',
		mapping : 'financeCard3'
	}, {
		name : 'bindTel1Dtl',
		mapping : 'bindTel1'
	}, {
		name : 'bindTel2Dtl',
		mapping : 'bindTel2'
	}, {
		name : 'bindTel3Dtl',
		mapping : 'bindTel3'
	}, {
		name : 'termAddrDtl',
		mapping : 'termAddr'
	}, {
		name : 'termPlaceDtl',
		mapping : 'termPlace'
	}, {
		name : 'oprNmDtl',
		mapping : 'oprNm'
	}, {
		name : 'termParaDtl',
		mapping : 'termPara'
	}, {
		name : 'keyDownSignDtl',
		mapping : 'keyDownSign'
	}, {
		name : 'paramDownSignDtl',
		mapping : 'paramDownSign'
	}, {
		name : 'icDownSignDtl',
		mapping : 'icDownSign'
	}, {
		name : 'reserveFlag1Dtl',
		mapping : 'reserveFlag1'
	}, {
		name : 'licenceNoDtl',
		mapping : 'licenceNo'
	}, {
		name : 'busTypeDtl',
		mapping : 'busType'
	}, {
		name : 'cardTypeDtl',
		mapping : 'cardType'
	}, {
		name : 'leaseFeeDtl',
		mapping : 'leaseFee'
	}, {
		name : 'depositFlagDtl',
		mapping : 'depositFlag'
	}, {
		name : 'depositStateD',
		mapping : 'depositState'
	},{
		name : 'termSingleLimitDtl',
		mapping : 'termSingleLimit'
	}, {
		name : 'depositAmtDtl',
		mapping : 'depositAmt'
	}, {
		name : 'propInsRateDtl',
		mapping : 'propInsRate'
	}, {
		name : 'checkCardNoDtl',
		mapping : 'checkCardNo'
	}, {
		name : 'rentFeeDtl',
		mapping : 'rentFee'
	} , {
		name : 'misc2',
		mapping : 'misc2'
	} , {
		name : 'termName',
		mapping : 'termName'
	} ]),
	autoLoad : false
});

function parseTxnSup(val) {
	for ( var i = 1; i <= 11; i++) {
		Ext.getCmp('txnSupDtl' + i).setValue(val.charAt(i - 1));
	}
}

function selectTermInfo(termId, mchtCd) {

	selectTermInfoNamespace.termInfoStore.load( {
		params : {
			storeId : 'getTermInfo',
			termId : termId,
			mchtCd : mchtCd
		},
		callback : function(records, options, success) {

			if (success) {
				var record = selectTermInfoNamespace.termInfoStore.getAt(0);
				
				selectTermInfoNamespace.termForm.getForm().loadRecord(record);
				Ext.getCmp('termSingleLimitDtl').setValue(record.get("termSingleLimitDtl")/100);
				// var termPara =
		// selectTermInfoNamespace.termForm.getForm().findField("termParaDtl").value;
		// 处理所支持的交易选框
		var termTxnSup = record.data.termTxnSupU;
//		alert(termTxnSup);
		parseTxnSup(termTxnSup);

		selectTermInfoNamespace.termPanel.setActiveTab(0);
		selectTermInfoNamespace.termWin.show();
		selectTermInfoNamespace.termWin.center();
	} else {
		selectTermInfoNamespace.termWin.hide();
		alert("载入终端信息失败，请稍后再试!")
	}
}
	});
};