var selectTermInfoNamespace = {};

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
			columnWidth : .5,
			layout:'form',
			items:[{
				xtype : 'displayfield',
				fieldLabel : '仓库编码',
				labelStyle : 'padding-left: 5px',
				name : 'termIdDtl',
				id : 'termIdDtl'
			}]
		},{
			columnWidth : .5,
			layout:'form',
			items:[{
				xtype : 'displayfield',
				fieldLabel : '仓库名称',
				labelStyle : 'padding-left: 5px',
				name : 'mappingTermidOneDtl',
				id : 'mappingTermidOne'
			}]
		},{
			columnWidth : .5,
			layout:'form',
			items:[{
				xtype : 'displayfield',
				fieldLabel : '仓库地址',
				labelStyle : 'padding-left: 5px',
				name : 'mappingTermidTwoDtl',
				id : 'mappingTermidTwo'
			}]
		},{
			columnWidth : .5,
			layout:'form',
			labelWidth : 60,
			items:[{
				xtype : 'displayfield',
				fieldLabel : '仓库等级',
				labelStyle : 'padding-left: 5px',
				name : 'mchnNoDtl',
				id : 'mchnNoD'
			}]
		},{
			columnWidth : .5,
			layout:'form',
			items:[{
				xtype : 'displayfield',
				fieldLabel : '仓库容量',
				labelStyle : 'padding-left: 5px',
				name : 'mappingMchntcdOneDtl',
				id : 'mappingMchntcdOne'
			}]
		},{
			columnWidth : .5,
			layout:'form',
			items:[{
				xtype : 'displayfield',
				fieldLabel : '上级仓库',
				labelStyle : 'padding-left: 5px',
				name : 'mappingMchntcdTwoDtl',
				id : 'mappingMchntcdTwo'
			}]
		},{
			columnWidth : .5,
			layout:'form',
			items:[{
				xtype : 'displayfield',
				fieldLabel : '仓库负责人',
				labelStyle : 'padding-left: 5px',
				name : 'mappingMchntcdTwoDtl1',
				id : 'mappingMchntcdTwo1'
			}]
		},{
			columnWidth : .5,
			layout:'form',
			items:[{
				xtype : 'displayfield',
				fieldLabel : '仓库联系方式',
				labelStyle : 'padding-left: 5px',
				name : 'mappingMchntcdTwoDtl2',
				id : 'mappingMchntcdTwo2'
			}]
		},{
			columnWidth : .5,
			layout:'form',
			items:[{
				xtype : 'displayfield',
				fieldLabel : '仓库所属机构',
				labelStyle : 'padding-left: 5px',
				name : 'mappingMchntcdTwoDtl3',
				id : 'mappingMchntcdTwo3'
			}]
		},{
			columnWidth : .5,
			layout:'form',
			items:[{
				xtype : 'displayfield',
				fieldLabel : '仓库状态',
				labelStyle : 'padding-left: 5px',
				name : 'mappingMchntcdTwoDtl4',
				id : 'mappingMchntcdTwo4'
			}]
		},{
			columnWidth : .5,
			layout:'form',
			items:[{
				xtype : 'displayfield',
				fieldLabel : '门禁标识',
				labelStyle : 'padding-left: 5px',
				name : 'mappingMchntcdTwoDtl5',
				id : 'mappingMchntcdTwo5'
			}]
		}]
	}]
});

selectTermInfoNamespace.termWin = new Ext.Window( {
	title : '仓库信息',
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
	}]),
	autoLoad : false
});

function parseTxnSup(val) {
	for ( var i = 1; i <= 11; i++) {
		Ext.getCmp('txnSupDtl' + i).setValue(val.charAt(i - 1));
	}
}

function selectTermInfo(termId) {
	selectTermInfoNamespace.termWin.show();
	selectTermInfoNamespace.termWin.center();
	selectTermInfoNamespace.termInfoStore.load( {
		params : {
			storeId : 'getTermInfo',
			termId : termId
		},
		callback : function(records, options, success) {

			if (success) {
				var record = selectTermInfoNamespace.termInfoStore.getAt(0);
				
				selectTermInfoNamespace.termForm.getForm().loadRecord(record);
				Ext.getCmp('termSingleLimitDtl').setValue(record.get("termSingleLimitDtl")/100);
		// 处理所支持的交易选框
		var termTxnSup = record.data.termTxnSupU;
//		alert(termTxnSup);
		parseTxnSup(termTxnSup);

		selectTermInfoNamespace.termWin.show();
		selectTermInfoNamespace.termWin.center();
	} else {
		selectTermInfoNamespace.termWin.hide();
		alert("载入终端信息失败，请稍后再试!")
	}
}
	});
};