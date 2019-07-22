Ext.onReady(function() {
	// 保存是否验证成功的变量
	var verifyResult = false;
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

	// ******************图片处理部分**********开始********
	var storeImg = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'}
		])
	});
	
	var custom = new Ext.Resizable('showBigPic', {
		    wrap:true,
		    pinned:true,
		    minWidth: 50,
		    minHeight: 50,
		    preserveRatio: true,
		    dynamic:true,
		    handles: 'all',
		    draggable:true
		});
	
	var customEl = custom.getEl();
	document.body.insertBefore(customEl.dom, document.body.firstChild);
	customEl.on('dblclick', function(){
		customEl.puff();
	});
	customEl.hide(true);
 	function showPIC(id){
 		var rec = storeImg.getAt(id.substring(5)).data;
 		custom.resizeTo(rec.width, rec.height);
 		var src = document.getElementById('showBigPic').src;

 		if(src.indexOf(rec.fileName) == -1){
	 		document.getElementById('showBigPic').src = "";
	 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName=' + rec.fileName;
 		}
 		customEl.center();
	    customEl.show(true);
 	}
 	function delPIC(id){
 		customEl.hide();
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要删除吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg.getAt(id.substring(5)).data;
		 		T20100.deleteImgFile(rec.fileName,function(ret){
		 			if("S" == ret){
		 				storeImg.reload({
							params: {
								start: 0,
								imagesId: imagesId
							}
						});
		 			}else{
		 				showMsg('操作失败，请刷新后重试！',mchntForm);
		 			}
		 		});
			}
	 	});
 	}
	storeImg.on('load',function(){
		for(var i=0;i<storeImg.getCount();i++){
			var rec = storeImg.getAt(i).data;
        	Ext.get(rec.btBig).on('click', function(obj,val){
        		showPIC(val.id);
        	});
        	Ext.get(rec.btDel).on('click', function(obj,val){
        		delPIC(val.id);
        	});
		}
	});
	// 账户清算方式
	var acctSettleTypeStore = new Ext.data.ArrayStore ({
		
		fields: ['valueField','displayField'],
		data: [['1','单账户'],['2','多账户按比例'],['3','多账户限额']],
		reader: new Ext.data.ArrayReader()
	});

	var clearTypeStore = new Ext.data.ArrayStore ({
		
		fields: ['valueField','displayField'],
		data: [['A','本行对公账户'],['P','本行对私账户或单位卡'],['O','他行对公账户'],['S','他行对私账户']],
		reader: new Ext.data.ArrayReader()
	});
	
	
	var clearTypeStore1 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['A','本行对公账户'],['P','本行对私账户或单位卡'],['O','他行对公账户'],['S','他行对私账户']],
		reader: new Ext.data.ArrayReader()
	});
	
	var distributeModeStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','固定金额'],['1','比例(%)']],
		reader: new Ext.data.ArrayReader()
	});
	
	var clearTypeStore2 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['P','本行对私账户或单位卡'],['S','他行对私账户']],
		reader: new Ext.data.ArrayReader()
	});
	// 神思电子的只有本行的账户,上面的不用
	var clearTypeStore3 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['A','本行对公账户'],['P','本行对私账户或单位卡']],
		reader: new Ext.data.ArrayReader()
	});
	
	var dataview = new Ext.DataView({
		frame: true,
        store: storeImg,
        tpl  : new Ext.XTemplate(
            '<ul>',
                '<tpl for=".">',
                    '<li class="phone">',
                    	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
                        	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName={fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大">&nbsp;<input type="button" id="{btDel}" value="删除"></div>',
						'</div>',
                    '</li>',
                '</tpl>',
            '</ul>'
        ),
        id: 'phones',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });

    // 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T20100Action_upload.asp',
		filePostName : 'imgFile',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB',
		fileTypes : '*.jpg;*.png;*.jpeg',
		fileTypesDescription : '图片文件',
		title: '证书影印文件上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		exterMethod: function() {
			storeImg.reload({
				params: {
					start: 0,
					imagesId: imagesId
				}
			});
		},
		postParams: {
			txnId: '20101',
			subTxnId: '06',
			imagesId: imagesId
		}
	});

	// ******************图片处理部分**********结束********



    // ******************计费算法部分**********开始********
	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});

// var txnStore = new Ext.data.JsonStore({
// fields: ['valueField','displayField'],
// root: 'data',
// id: 'valueField'
// });
//
// SelectOptionsDWR.getComboData('TXN_NUM_FEE',function(ret){
// txnStore.loadData(Ext.decode(ret));
// });
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
	

	var hasSub = false;
	var hasUpload = "0";
	var fm = Ext.form;

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
            width: 160,
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
            width: 120,
            editor: {
					xtype: 'basecomboselect',
			        store: flagStore,
					id: 'idfalg',
					hiddenName: 'falg',
					width: 160
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
            width: 70
        },{
            header: '按比最低收费',
            dataIndex: 'minFee',
            width: 90
        },{
            header: '按比最高收费',
            dataIndex: 'maxFee',
            width: 90
        }]
	});

    var detailGrid = new Ext.grid.GridPanel({
		title: '详细信息',
		frame: true,
		border: true,
		height: 250,
		columnLines: true,
		
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法详细信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});



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
			{name: 'discOrg',mapping: 'discOrg'}
		])
	});
	gridStore.load({
		params: {
			start: 0
		}
	});
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '计费代码',dataIndex: 'discCd',sortable: true,width: 80},
		{header: '计费名称',dataIndex: 'discNm',sortable: true,id:'discNm',width:240},
		{header: '所属机构',dataIndex: 'discOrg',sortable: true,width:100,renderer:function(val){return getRemoteTrans(val, "brh");}}
	]);
	var gridPanel = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 250,
		columnLines: true,
		autoExpandColumn: 'discNm',
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		tbar: ['计费代码：',{
			xtype: 'textfield',
			id: 'serdiscCd',
			width: 60
		},'-','计费名称：',{
			xtype: 'textfield',
			id: 'serdiscNm',
			width: 110
		},'-',{
			xtype: 'button',
			iconCls: 'query',
			text:'查询',
			id: 'widfalg',
			width: 60,
			handler: function(){
				gridStore.load();
				queryWin.hide();
			}
		}]
	});
	gridPanel.getStore().on('beforeload',function() {
		Ext.getCmp('setup').disable();
	});

	gridPanel.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		Ext.getCmp('setup').enable();
		var id = gridPanel.getSelectionModel().getSelected().data.discCd;
		store.load({
			params: {
				start: 0,
				discCd: gridPanel.getSelectionModel().getSelected().data.discCd
				}
			});
	});
	gridStore.on('beforeload', function() {
		store.removeAll();
	});
	 // ******************计费算法部分**********结束********

	/** ********* 直联商户费率设置 **************** */
	var cupMccStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
	SelectOptionsDWR.getComboData('MCHNT_TP_CUP',function(ret){
		cupMccStore.loadData(Ext.decode(ret));
	});
	
	var cupFeeStore1 = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
	SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP1','----',function(ret){
		cupFeeStore1.loadData(Ext.decode(ret));
	});
	
	var feeForm = new Ext.form.FormPanel({
    	region: 'north',
        height: 100,
        frame: true,
        layout: 'column',
        labelWidth: 80,
        items: [{
    			columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_FEE_ACT',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '决定索引1*',
					id: 'mchtCup521I',
					hiddenName: 'mchtCup521',
					allowBlank: false,
					readOnly: true,
					anchor: '90%',
					value: 'NK001'
		        }]
			},{
    			columnWidth: .22,
            	layout: 'form',
        		items: [{
			        xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','0-按比例'],['1','1-固定金额']],
						reader: new Ext.data.ArrayReader()
					}),
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法类型1*',
					id: 'mchtCup522I',
					hiddenName: 'mchtCup522',
					allowBlank: false,
					anchor: '90%',
					listeners:{
						'select': function() {
							cupFeeStore1.removeAll();
							SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP1',feeForm.getForm().findField('mchtCup522I').getValue(),function(ret){
								cupFeeStore1.loadData(Ext.decode(ret));
							});
							feeForm.getForm().findField('mchtCup523I').reset();
						}
					}
		        }]
			},{
    			columnWidth: .44,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        store: cupFeeStore1,
			        displayField: 'displayField',
					valueField: 'valueField',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法配置1*',
					id: 'mchtCup523I',
					hiddenName: 'mchtCup523',
					allowBlank: false,
					anchor: '90%'
		        }]
			},{
    			columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_FEE_ACT',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '决定索引2*',
					id: 'mchtCup524I',
					hiddenName: 'mchtCup524',
					allowBlank: false,
					anchor: '90%',
					listeners:{
						'select': function() {
							var feeAct2 = feeForm.getForm().findField('mchtCup524I').getValue();
							if(feeAct2 == 'NK001'){
								feeForm.getForm().findField('mchtCup524I').reset();
								showMsg('请选择“NK001-全国南卡北用决定索引”以外的类型！',feeForm);
							}
						}
					}
		        }]
			},{
    			columnWidth: .22,
            	layout: 'form',
        		items: [{
			        xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','0-按比例'],['1','1-固定金额']],
						reader: new Ext.data.ArrayReader()
					}),
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法类型2*',
					id: 'mchtCup525I',
					hiddenName: 'mchtCup525',
					allowBlank: false,
					anchor: '90%',
					listeners:{
						'select': function() {
							cupFeeStore1.removeAll();
							SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP1',feeForm.getForm().findField('mchtCup525I').getValue(),function(ret){
								cupFeeStore1.loadData(Ext.decode(ret));
							});
							feeForm.getForm().findField('mchtCup526I').reset();
						}
					}
		        }]
			},{
    			columnWidth: .44,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        store: cupFeeStore1,
			        displayField: 'displayField',
					valueField: 'valueField',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法配置2*',
					id: 'mchtCup526I',
					hiddenName: 'mchtCup526',
					allowBlank: false,
					anchor: '90%'
		        }]
			}]
    });
    
    
	var feeWin = new Ext.Window({
		title: '商户手续费决定索引',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 1000,
		autoHeight: true,
		layout: 'fit',
		items: [feeForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {

				if(feeForm.getForm().isValid()) {
					var fee1 = feeForm.getForm().findField('mchtCup521I').getValue();
					var fee2 = feeForm.getForm().findField('mchtCup522I').getValue();
					var fee3 = feeForm.getForm().findField('mchtCup523I').getValue();
					var fee4 = feeForm.getForm().findField('mchtCup524I').getValue();
					var fee5 = feeForm.getForm().findField('mchtCup525I').getValue();
					var fee6 = feeForm.getForm().findField('mchtCup526I').getValue();
					
					mchntForm.getForm().findField("fee_act").setValue(fee1 + '&' + fee3 + '&#' + fee4 + '&' + fee6 + '&#');
					
				    feeForm.getForm().reset();
                  	feeWin.hide();
				} 
			}
		},{
			text: '清空',
			handler: function() {
				feeForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				feeForm.getForm().reset();
				feeWin.hide();
			}
		}]
	});
	
	var feeForm2 = new Ext.form.FormPanel({
    	region: 'north',
        height: 100,
        frame: true,
        layout: 'column',
        labelWidth: 80,
        items: [{
    			columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_FEE_ACT2',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '决定索引*',
					id: 'mchtCup2521I',
					hiddenName: 'mchtCup2521',
					allowBlank: false,
					readOnly: true,
					anchor: '90%',
					value: 'JS119'
		        }]
			},{
    			columnWidth: .22,
            	layout: 'form',
        		items: [{
			        xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['3','3-按比例'],['4','4-固定金额']],
						reader: new Ext.data.ArrayReader()
					}),
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法类型*',
					id: 'mchtCup2522I',
					hiddenName: 'mchtCup2522',
					allowBlank: false,
					anchor: '90%',
					listeners:{
						'select': function() {
							cupFeeStore1.removeAll();
							SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP1',feeForm2.getForm().findField('mchtCup2522I').getValue(),function(ret){
								cupFeeStore1.loadData(Ext.decode(ret));
							});
							feeForm2.getForm().findField('mchtCup2523I').reset();
						}
					}
		        }]
			},{
    			columnWidth: .44,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        store: cupFeeStore1,
			        displayField: 'displayField',
					valueField: 'valueField',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法配置*',
					id: 'mchtCup2523I',
					hiddenName: 'mchtCup2523',
					allowBlank: false,
					anchor: '90%'
		        }]
			}]
    });
    
    var feeWin2 = new Ext.Window({
		title: '商户分润手续费索引',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 1000,
		autoHeight: true,
		layout: 'fit',
		items: [feeForm2],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {

				if(feeForm2.getForm().isValid()) {
					var fee1 = feeForm2.getForm().findField('mchtCup2521I').getValue();
					var fee2 = feeForm2.getForm().findField('mchtCup2522I').getValue();
					var fee3 = feeForm2.getForm().findField('mchtCup2523I').getValue();
					
					mchntForm.getForm().findField("feerate_index").setValue(fee1 + '&' + fee3 + '&#');
					
				    feeForm2.getForm().reset();
                  	feeWin2.hide();
				} 
			}
		},{
			text: '清空',
			handler: function() {
				feeForm2.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				feeForm2.getForm().reset();
				feeWin2.hide();
			}
		}]
	});
	/** ********* 直联商户费率设置 结束 **************** */
	

	var flag2Store = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	var mchntForm = new Ext.FormPanel({
        title: '新增商户信息',
		region: 'center',
		iconCls: 'T20100',
		frame: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'left',
        items: [{
        	layout:'column',
        	items: [{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_FALG1',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户性质1*',
					id: 'idMchtFlag1',
					hiddenName: 'mchtFlag1',
					allowBlank: false,
					anchor: '90%',
					listeners: {
	                	'select': function() {    
	                     	var flag1 = mchntForm.getForm().findField('mchtFlag1').getValue();
	                     	mchntForm.getForm().findField('mchtFlag2').reset();
	                     	if(flag1=='7' || flag1=='1'){
	                     		mchntForm.getForm().findField("settleType").setValue('0');
	                     		mchntForm.getForm().findField("settleType").readOnly= true;
	                     	}else{
	                     		mchntForm.getForm().findField("settleType").setValue('1');
	                     		mchntForm.getForm().findField("settleType").readOnly= true;
	                     	}
	                     	if(flag1=='7'){
	                     		mchntForm.getForm().findField("acctSettleType").setValue('1');
	                     		mchntForm.getForm().findField("acctSettleType").readOnly= true;
	                     	}else{
	                     		mchntForm.getForm().findField("acctSettleType").setValue('1');
	                     		mchntForm.getForm().findField("acctSettleType").readOnly= true;
	                     	}
	                     	if(flag1=='5'||flag1=='6'||flag1=='7'){// 没有二级数据
	                     		mchntForm.getForm().findField('mchtFlag2').disable();
	                     		return;
	                     	}
	                     	mchntForm.getForm().findField('mchtFlag2').enable();
	                     	flag2Store.removeAll();
	                     	SelectOptionsDWR.getComboDataWithParameter('MCHT_FALG2_BY_FLAG1',flag1,function(ret){
	                     		var data=Ext.decode(ret);
	                     		flag2Store.loadData(data);
							});
	                    }
					}
		        }]
        	},{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'combo',
			        store:flag2Store,
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户性质2*',
					id: 'idMchtFlag2',
					hiddenName: 'mchtFlag2',
					allowBlank: false,
					editable:false,
					anchor: '90%'
		        }]
        	},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
        			xtype: 'basecomboselect',
			        baseParams: 'CONN_TYPE',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户接入方式*',
					id: 'idconnType',
					hiddenName: 'connType',
					allowBlank: false,
					anchor: '90%',
					value: 'J',
					listeners:{
						'select':function(){
							var connType= mchntForm.getForm().findField('connType').getValue();
							if(connType=='Z'){
								// 直联支持全卡种
								mchntForm.getForm().findField('cupMchtFlg').setValue('1');
								mchntForm.getForm().findField('debMchtFlg').setValue('1');
								mchntForm.getForm().findField('creMchtFlg').setValue('1');
								mchntForm.getForm().findField('cdcMchtFlg').setValue('1');
							}else if(connType=='J'){
								// 间联卡种自配
								mchntForm.getForm().findField('cupMchtFlg').setValue('1');
								mchntForm.getForm().findField('debMchtFlg').setValue('1');
								mchntForm.getForm().findField('creMchtFlg').setValue('1');
								mchntForm.getForm().findField('cdcMchtFlg').setValue('1');
							}
						}
					}
				}]
        	},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'BRH_BELOW1',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户所在地区*',
					allowBlank: false,
					blankText: '请选择商户所在地区',
					id: 'IdSignInstId',
					hiddenName: 'signInstId',
					anchor: '90%',
					listeners: {
						'select': function() {
							var orgId = Ext.getCmp('IdSignInstId').getValue();
							brhDwr.getCupBrhIdByBrhId(orgId,function(data){
								mchntForm.getForm().findField("acqInstId").setValue(data);
							});
						}
					}
		        }]
			},{
        		columnWidth: .33,
       			xtype: 'panel',
	        	layout: 'form',
       			items: [{
                    xtype: 'textfield',
					fieldLabel: '商户受理机构*',
					readOnly:true,
					labelStyle: 'padding-left: 5px',
					allowBlank: false,
					id:'acqInstId',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户全称*',
					maxLength: '60',
					vtype: 'isOverMax',
					id: 'mchtNm',
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
		        	xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户简称*',
					maxLength: '40',
					vtype: 'isOverMax',
					id: 'mchtCnAbbr',
  				    allowBlank: false,
					blankText: '请输入商户简称',
					name: 'mchtCnAbbr',
					anchor: '90%'
	        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '英文名称',
						maxLength: 40,
						vtype: 'isOverMax',
						regex: /^\w+[\w\s]+\w+$/,
						regexText:'英文名称必须是字母，如Bill Gates',
						id: 'engName',
						name: 'engName',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
						xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						methodName: 'getAreaCode',
						fieldLabel: '商户装机地区*',
						hiddenName: 'areaNo',
						allowBlank: false,
						editable: true,
						anchor: '90%'
		        	}]
			},{
				columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
	       			//xtype: 'dynamicCombo',
	       			xtype: 'basecomboselect',
	       			baseParams: 'get_Mcc_Id',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户MCC*',
					//methodName: 'getMccId',
					editable: true,
					allowBlank: false,
					anchor: '90%',
					blankText: '请选择商户MCC',
					id: 'idmcc',
					hiddenName: 'mcc',
					listeners: {
						'select': function() {
							var mcc = (Ext.getDom(Ext.getDoc()).getElementById('idmcc').value).substring(7,9);
							mchntForm.getForm().findField('mchtGrp').setValue(mcc);
						}
					}
	        	}]
			},{
        		columnWidth: .33,
       			xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'checkbox',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '白名单商户标志',
					id: 'whiteListFlag',
					name: 'whiteListFlag',
					width:40
	        	}]
			},{
				columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户组别*',
						readOnly:true,
						allowBlank: false,
						hiddenName: 'mchtGrp',
						anchor: '90%',
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户号2',
					maxLength: '15',
					maxLengthText:'最长15位',
					vtype: 'isOverMax',
					allowBlank:false,
					blankText:'商户号必填', 
					id: 'mappingMchntcdTwo',
					name: 'mappingMchntcdTwo',
					regex:/^[0-9]{15}$/,
		    		regexText:'只能输入15位数字',
					anchor: '90%'
	        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户号1',
//					maxLength: '15',
//					maxLengthText:'最长15位',
					vtype: 'isOverMax',
					allowBlank:true,
					id: 'mappingMchntcdOne',
					name: 'mappingMchntcdOne',
					anchor: '90%'
	        	}]
			},{
				columnWidth: .33,
       			xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'basecomboselect',
			        baseParams: 'CUP_BRH_SETT',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '银联清算机构',
					editable:true,
					disabled:true,
					allowBlank:true,
					id: 'printInstIdN',
					hiddenName: 'printInstId',
					anchor: '90%',
					hidden:true,
					hideLabel:true
	        	}]
			},{
				columnWidth: 1,
				xtype: 'panel',
				html:'<br/>'
			}]
        },{
        	xtype: 'tabpanel',
        	id: 'tab',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 360,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 180,
        	items:[{
                title:'基本信息',
                id: 'basic',
                frame: true,
				layout:'column',
                items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       	            xtype: 'textnotnull',
	       	            labelStyle: 'padding-left: 5px',
	       	            fieldLabel: '营业执照编号*',
	       	            maxLength: 20,
	       	            id: 'licenceNo',
	       	            width: 250
	       	       }]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户地址*',
						width:250,
						maxLength: 60,
						vtype: 'isOverMax',
						id: 'addr'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '巡检频率(天)',
						width:250,
						maxLength: 3,
						vtype: 'isOverMax',
						id: 'checkFrqc',
						name: 'checkFrqc',
						value:180
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id:'accountHide3',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表(负责人)*',
						width:250,
						maxLength: 10,
						vtype: 'isOverMax',
						id: 'manager1'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id:'accountHide4',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATE_CUP',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表(负责人)证件类型*',
						width:250,
						allowBlank: false,
						hiddenName: 'artifCertifTp',
						id: 'idartifCertifTp'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id:'accountHide5',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表(负责人)证件号码*',
						width:250,
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'identityNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人',
						width:250,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'contact'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人手机',
						width:250,
						regex: /^[0-9]{11}$/,
						regexText: '手机只能是11位数字',
						maxLength: 18,
						vtype: 'isOverMax',
						id: 'commMobil'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人电话',
						width:250,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 18,
						vtype: 'isOverMax',
						id: 'commTel'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人电子邮件',
						width:250,
						maxLength: 40,
						vtype: 'isOverMax',
						id: 'commEmail',
						vtype: 'email'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人二',
						width:250,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'contactSnd'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人二手机',
						width:250,
						regex: /^[0-9]{11}$/,
						regexText: '手机只能是11位数字',
						maxLength: 18,
						vtype: 'isOverMax',
						id: 'commMobilSnd'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人二电话',
						width:250,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 18,
						vtype: 'isOverMax',
						id: 'commTelSnd'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人二电子邮件',
						width:250,
						maxLength: 40,
						vtype: 'isOverMax',
						id: 'commEmailSnd',
						vtype: 'email'
		        	}]
				},{
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '邮政编码',
						width:250,
						regex: /^[0-9]{6}$/,
						regexText: '邮政编码必须是6位数字',
						id: 'postCode',
						name: 'postCode'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户对账单邮箱',
						width:250,
						maxLength: 40,
						vtype: 'isOverMax',
						id: 'mchtPostEmail',
						vtype: 'email'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '单笔限额(分)',
						vtype: 'isOverMax',
						width:250,
						id: 'homePage',
						name: 'homePage',
						maxLength: 60
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id:'accountHiderouting',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'ROUTING_FLAG',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '路由信息*',
						width:250,
						allowBlank: false,
						hiddenName: 'routingFlag',
						id: 'idroutingflag'
		        	}]
				}]
            },{
                title:'附加信息',
                layout:'column',
                id: 'append',
                frame: true,
                items: [{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持他行借记卡',
						checked:true,
						id: 'cupMchtFlg',
						name: 'cupMchtFlg'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
			        	checked:true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持他行贷记卡',
						id: 'debMchtFlg',
						name: 'debMchtFlg'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
			        	checked:true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持本行借记卡',
						id: 'creMchtFlg',
						name: 'creMchtFlg'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
			        	checked:true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持本行贷记卡',
						id: 'cdcMchtFlg',
						name: 'cdcMchtFlg'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'textfield',
	        			labelStyle: 'padding-left: 5px'
	        		},
	       			items: [
					{
						fieldLabel: '协议编号',
						maxLength: 20,
						vtype: 'isOverMax',
						width:150,
						id: 'protocalId',
						name: 'protocalId'
		        	},{
		        		xtype: 'dynamicCombo',
   			            methodName: 'getBranchId12',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '归属机构*',
						allowBlank: false,
						editable:true,
						blankText: '请选择归属机构',
						id: 'agrBrId',
						hiddenName: 'agrBr',
						anchor: '90%'
		        		},{
		        		xtype: 'textfield',
						fieldLabel: '营销员工号',
						width:150,
						id: 'operNo',
						name: 'operNo',
						maxLength: 8
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'textfield',
	        			labelStyle: 'padding-left: 5px'
	        		},
	       			items: [{
	       				xtype: 'textfield',
						fieldLabel: '批准人',
						maxLength: 40,
						vtype: 'isOverMax',
						width:150,
						id: 'confirmNm',
						name: 'confirmNm'
					}
	       			,{
		        		xtype: 'textfield',
						fieldLabel: '营销方',
						maxLength: 10,
						vtype: 'isOverMax',
						width:150,
						id: 'operNm',
						name: 'operNm'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营销员',
						width:150,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'marketerName'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营销员联系方式',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 18,
						vtype: 'isOverMax',
						id: 'marketerContact'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'timefieldsp',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户营业开始时间',
						id: 'openTime',
						name: 'openTime',
						minValue: '00:00',
    					maxValue: '23:59',
    					altFormats: 'H:i',
    					format: 'H:i',
    					editable: true,
    					increment: 10,
    					// anchor: '55%'
    					width:150
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'timefieldsp',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户营业结束时间',
						id: 'closeTime',
						name: 'closeTime',
						minValue: '00:00',
    					maxValue: '23:59',
    					altFormats: 'H:i',
    					format: 'H:i',
    					editable: true,
    					increment: 10,
    					// anchor: '60%'
    					width:150
		        	}]
				}]
			},{
                title:'清算信息',
                layout:'column',
                id: 'settle',
                frame: true,
                items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'basecomboselect',
						fieldLabel: '商户结算周期',
						baseParams: 'SETTLE_TYPE',
						allowBlank: true,
						disabled:false,
						labelStyle: 'padding-left: 5px',
						hiddenName: 'settleType',
						anchor: '60%'
					}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '商户开户行同城清算号',
						allowBlank: true,
						disabled:false,
						maxLength: 20,
						vtype: 'isOverMax',
						width:150,
						id: 'openStlno'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						disabled:true,
						fieldLabel: '商户开户行同城交换号',
						maxLength: 20,
						vtype: 'isOverMax',
						width:150,
						id: 'changeStlno'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combo',
						store: acctSettleTypeStore,
						labelStyle: 'padding-left: 5px',
						displayField: 'displayField',
						valueField: 'valueField',
						emptyText: '请选择',
						hiddenName: 'acctSettleType',
						mode: 'local',
						triggerAction: 'all',
//						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: false,
						allowBlank: true,
						disabled:false,
						fieldLabel: '账户清算方式',
						anchor: '60%',
						listWidth: 150,
                        listeners: {
	                     'select': function(combo,record,index) {
                                var value = combo.getValue();
                                if(value=='1'){
                                	//Ext.getCmp('idClearType2').reset();
                                	Ext.getCmp('settleBankNoSnd').reset();
                                	Ext.getCmp('settleBankNmSnd').reset();
                                	Ext.getCmp('settleAcctNmSnd').reset();
                                	Ext.getCmp('settleAcctSnd').reset();
                                	Ext.getCmp('acctSettleRate').reset();
                                	Ext.getCmp('acctSettleLimit').reset();
                                	
//                                	Ext.getCmp('idClearType2').disable();
//                                	Ext.getCmp('settleBankNoSnd').disable();
//                                	Ext.getCmp('settleBankNmSnd').disable();
//                                	Ext.getCmp('settleAcctNmSnd').disable();
//                                	Ext.getCmp('settleAcctSnd').disable();
//                                	Ext.getCmp('acctSettleRate').disable();
//                                	Ext.getCmp('acctSettleLimit').disable();
                                }else{
//                                	Ext.getCmp('idClearType2').enable();
//                                	Ext.getCmp('settleBankNoSnd').enable();
//                                	Ext.getCmp('settleBankNmSnd').enable();
//                                	Ext.getCmp('settleAcctNmSnd').enable();
//                                	Ext.getCmp('settleAcctSnd').enable();
//                                	if(value=='2'){
//                                		Ext.getCmp('acctSettleRate').enable();
//                                		Ext.getCmp('acctSettleLimit').reset();
//                                		Ext.getCmp('acctSettleLimit').disable();
//                                	}else{
//                                		Ext.getCmp('acctSettleRate').reset();
//                                		Ext.getCmp('acctSettleRate').disable();
//                                		Ext.getCmp('acctSettleLimit').enable();
//                                	}
                                	
                                }
	                        }
	                    }
	       			}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'combo',
						store: clearTypeStore1,
						labelStyle: 'padding-left: 5px',
						displayField: 'displayField',
						valueField: 'valueField',
						emptyText: '请选择',
						hiddenName: 'clearType',
						id:'idclearType',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: false,
						allowBlank: true,
//						disabled:true,
						fieldLabel: '账户1结算类型',
						anchor: '60%',
						listWidth: 160,
                        listeners: {
	                     'select': function() {
                                var clearType = mchntForm.getForm().findField("idclearType").getValue();
                                if(clearType == 'A'|| clearType == 'P'){
                                	mchntForm.getForm().findField("settleBankNo").allowBlank = true;
                                }else{
                                	mchntForm.getForm().findField("settleBankNo").allowBlank = true;
                                }
                                var flag1 = mchntForm.getForm().findField('mchtFlag1').getValue();
                                if(flag1==null||flag1==""){
                                	showAlertMsg("请先选择商户性质1！",mchntForm);
                                	mchntForm.getForm().findField("idclearType").setValue('');
                                    return;
                                }
                                if(flag1=='7'){
                                	if(clearType=='O'||clearType=='S'){
                                		mchntForm.getForm().findField("idclearType").setValue('');
                                		showAlertMsg("固话POS商户，不能选择他行结算类型",mchntForm);
                                        return;
                                	}
                                }
	                        }
	                    }
					}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户账户1开户行名称',
						allowBlank: true,
						disabled:false,
						maxLength: 30,
						vtype: 'isOverMax',
						width:150,
						id: 'settleBankNm'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '账户1所属行联行号',
						allowBlank: true,
						disabled:false,
						maxLength: 12,
						vtype: 'isOverMax',
						width:150,
						id: 'settleBankNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户账户1户名',
						allowBlank: true,
						disabled:false,
						maxLength: 80,
						vtype: 'isOverMax',
						width:150,
						id: 'settleAcctNm'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'textnotnull',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户账户1账号',
                        allowBlank: true,
						disabled:false,
                        maxLength: 39,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        width:150,
                        id: 'settleAcct'
                    }]
				}
				// 下面是第二账户信息 支持双账户则显示
				,{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '支付宝账号',
						width:150,
						allowBlank: true,
//						disabled:true,
						id: 'acctSettleRate'
		        	}]
				},
				{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '账户1分账限额(元)*',
						maxLength: 15,
						vtype: 'isOverMax',
						width:150,
						allowBlank: true,
						disabled:true,
						regex:/^[0-9]{1,12}\.[0-9]{2}$/,
						regexText:"请输入正的不超过十五位的带两位小数的数字",
						id: 'acctSettleLimit'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        		xtype: 'dynamicCombo',
	   			            methodName: 'getShareRole',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '分润规则',
							allowBlank: true,
							editable:true,
							//blankText: '请选择分润规则',
							id: 'idClearType2',
							hiddenName: 'clearType2',
							anchor: '85%',
							listWidth:300,
							readOnly:false
//                        listeners: {
//		                     'select': function() {
//	                                var clearType = mchntForm.getForm().findField("idClearType2").getValue();
//	                                if(clearType == 'A'|| clearType == 'P'){
//	                                	mchntForm.getForm().findField("settleBankNoSnd").allowBlank = true;
//	                                }else{
//	                                	mchntForm.getForm().findField("settleBankNoSnd").allowBlank = false;
//	                                }
//	                                var flag1 = mchntForm.getForm().findField('mchtFlag1').getValue();
//	                                if(flag1==null||flag1==""){
//	                                	showAlertMsg("请先选择商户性质1！",mchntForm);
//	                                	mchntForm.getForm().findField("idClearType2").setValue('');
//	                                    return;
//	                                }
//	                                if(flag1=='7'){
//	                                	if(clearType=='O'||clearType=='S'){
//	                                		mchntForm.getForm().findField("idClearType2").setValue('');
//	                                		showAlertMsg("固话POS商户，不能选择他行结算类型",mchntForm);
//	                                        return;
//	                                	}
//	                                }
//		                        }
//		                    }
					}]
				}
				,{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户账户2开户行名称*',
						maxLength: 80,
						vtype: 'isOverMax',
						width:150,
						allowBlank: true,
						disabled:true,
						id: 'settleBankNmSnd'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '账户2所属行联行号*',
						maxLength: 12,
						vtype: 'isOverMax',
						width:150,
						allowBlank: true,
						disabled:true,
						id: 'settleBankNoSnd'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户账户2户名*',
						maxLength: 80,
						vtype: 'isOverMax',
						width:150,
						allowBlank: true,
						disabled:true,
						id: 'settleAcctNmSnd'
		        	}]
				},{
	        		columnWidth: .35,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'textfield',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户账户2账号*',
                        maxLength: 39,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        width:150,
                        allowBlank: true,
						disabled:true,
                        id: 'settleAcctSnd'
                    }]
				}]
			},{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                	region: 'north',
                	height: 35,
                	layout: 'column',
                	items: [{
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					xtype: 'textnotnull',
	       					fieldLabel: '计费代码',
	       					allowBlank: true,
							disabled:true,
							id: 'discCode',
							name: 'discCode',
							readOnly: true
						}]
					},{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'recover',
							text:'清空',
							id: 'resetbu',
							disabled:true,
							width: 60,
							handler: function(){
								Ext.getCmp('discCode').reset();
							}
                		}]
					},{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'accept',
							text:'设为该商户计费算法',
							disabled:true,
							id: 'setup',
							width: 60,
							disabled: true,
							handler: function(){
	       				 var flag1 = mchntForm.getForm().findField('mchtFlag1').getValue();
                         if(flag1==null||flag1==""){
                         	showAlertMsg("请先选择商户性质1！",mchntForm);
                         	Ext.getCmp('discCode').setValue('');
                             return;
                         }
                         T20100.getFeeFlag(gridPanel.getSelectionModel().getSelected().data.discCd,function(ret){
                        	 if(ret=='1'&&flag1!='7'){
                        		 showAlertMsg("该计费信息只能设置给固话POS商户！",mchntForm);
                        		 Ext.getCmp('discCode').setValue('');
                        		 return;
                        	 }else if(ret=='0'&&flag1=='7'){
                        		 showAlertMsg("该计费信息不能设置给固话POS商户！",mchntForm);
                        		 Ext.getCmp('discCode').setValue('');
                        		 return;
                        	 }
                         });
								Ext.getCmp('discCode').setValue(gridPanel.getSelectionModel().getSelected().data.discCd);
							}
                		}]
					},{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
	       					xtype: 'button',
							iconCls: 'detail',
							text:'计费算法配置说明',
							disabled:true,
							id: 'detailbu',
							width: 60,
							handler: function(){
								Ext.MessageBox.show({
									msg: '<font color=red>第一步</font>：确定交易类型，每个交易类型都需要配置；（非固话POS商户计费算法配置跳过该步。）<font color=red>第二步</font>：确定交易卡种，一次性选择全卡种或分四次选择不同卡种；<br>' +
										  '<font color=red>第三步</font>：确定手续费计费方式，分每笔按固定金额，按比例，按比例保底，按比例封顶，按比例保底加封顶。<br>' +
										  '<font color=red>第四步</font>：具体配置指定卡种范围指定手续费计费方式的参数<br>' +
										  '&nbsp&nbsp<font color=green>1)</font>：按固定金额方式：在“值”一栏填写手续费收取金额，如：0.30元/笔输入0.30；<br>'+
										  '&nbsp&nbsp<font color=green>2)</font>：按比例方式：在“值”一栏填写手续费占交易金额百分比，如0.78%输入0.78；<br>'+
										  '&nbsp&nbsp<font color=green>3)</font>：按比例加固定金额限制方式：<br>'+
										  '&nbsp&nbsp&nbsp&nbsp<font color=gray>a</font>：按比例保底：在“值”一栏填写手续费占交易金额百分比，在“按笔最低收费”一栏填写保底金额，如：0.3%保底1.20元，即在“值”项输入0.3、在“按笔最低收费”项输入1.2；<br>'+
										  '&nbsp&nbsp&nbsp&nbsp<font color=gray>b</font>：按比例封顶：在“值”一栏填写手续费占交易金额百分比，在“按笔最高收费”一栏填写封顶金额，如：1.228%封顶80元，即在“值”项输入1.228、在“按笔最高收费”项输入80；<br>'+
										  '&nbsp&nbsp&nbsp&nbsp<font color=gray>c</font>：按比例封顶加保底：在“值”一栏填写手续费占交易金额百分比，在“按笔最低收费”和“按笔最高收费”一栏分别填写保底和封顶金额，如：1.228%保底20封顶80元，即在“值”项输入1.228、在“按笔最低收费”项输入20、在“按笔最高收费”项输入80。<br>',
									title: '计费算法配置说明',
									animEl: Ext.get(mchntForm.getEl()),
									buttons: Ext.MessageBox.OK,
									modal: true,
									width: 650
								});
							}
                		}]
                	}]
                },{
                	region: 'center',
                	items:[gridPanel]
                },{
                	region: 'east',
                	width:600,
                	items: [detailGrid]
                }]
	    	},{
        		title:'直联基本',
        		xtype: 'panel',
                id: 'cupBasic',
                disabled:true,
                frame: true,
				layout:'column',
	            items: [{
	        		columnWidth: .35,
	            	layout: 'form',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'MCHT_CUP_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户服务类型*',
						id: 'idmchnt_srv_tp',
						hiddenName: 'mchnt_srv_tp',
						allowBlank: true,
						disabled:true,
						anchor: '90%',
						value: '00'
			        }]
			    },{
		        	columnWidth: .31,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-普通商户'],['1','1-仿公益类商户'],['2','2-周期商户']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户归类',
						id: 'cycle_mchtI',
						hiddenName: 'cycle_mcht',
						value:'0',
						anchor: '90%',
						allowBlank: true,
						disabled:true,
						listeners:{
								'select': function(){
									var mchtTp = mchntForm.getForm().findField('cycle_mchtI').getValue();
									
									if(mchtTp == 1){
										mchntForm.getForm().findField('rate_typeI').allowBlank = false;
									}else{
										mchntForm.getForm().findField('rate_typeI').allowBlank = true;
									}
								}
							}
		        	}]
				},{
					columnWidth: .34,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combo',
			        		labelStyle: 'padding-left: 5px',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['156','156-中国']],
								reader: new Ext.data.ArrayReader()
							}),
							fieldLabel: '国家代码*',
							id: 'cntry_codeI',
							hiddenName: 'cntry_code',
							value: '156',
							allowBlank: true,
							disabled:true,
							anchor: '90%'
			        	}]
				},{
	        		columnWidth: .56,
	        		xtype: 'panel',
			        layout: 'form',
		       		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'CUP_BRH_S',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '内卡收单机构*',
// allowBlank: false,
						hiddenName: 'inner_acq_inst_id',
						allowBlank: true,
						disabled:true,
						anchor: '90%'
			        }]
				},{
// columnWidth: .33,
// layout: 'form',
// xtype: 'panel',
// hidden:true,
// items: [{
// xtype: 'basecomboselect',
// baseParams: 'CUP_BRH_S',
// labelStyle: 'padding-left: 5px',
// fieldLabel: '代理机构*32',
// id: 'mchtCup5I',
// hiddenName: 'mchtCup5',
// anchor: '90%'
// }]
// },{
					columnWidth: .44,
		        	xtype: 'panel',
			        layout: 'form',
		       		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'CUP_BRH_S',
						hiddenName: 'inner_stlm_ins_id',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						disabled:true,
						fieldLabel: '&nbsp;内卡结算机构*',
// allowBlank: false,
						anchor: '90%'
			        }]
				},{
		        	columnWidth: .33,
		        	xtype: 'panel',
			        layout: 'form',
		       		items: [{
		       			xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '所属平台机构*',
						allowBlank: true,
						disabled:true,
						id: 'inst_id',
						name: 'inst_id',
						value: '0800093000',
// allowBlank: false,
						anchor: '90%'
			        }]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
			        layout: 'form',
		       		items: [{
		       			xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收单接入机构*',
						id: 'conn_inst_cd',
						name: 'conn_inst_cd',
						allowBlank: true,
						disabled:true,
						value: '0800093000',
// allowBlank: false,
						blankText: '请选接入机构',
						anchor: '90%'
			        }]
				},{
// columnWidth: .33,
// id: 'mchtNmPanel',
// xtype: 'panel',
// layout: 'form',
// hidden:true,
// items: [{
// xtype: 'textfield',
// labelStyle: 'padding-left: 5px',
// fieldLabel: '商户中文名称*',
// maxLength: '60',
// // allowBlank: false,
// vtype: 'isOverMax',
// id: 'mchtCup10',
// anchor: '90%'
// }]
// },{
// columnWidth: .33,
// layout: 'form',
// xtype: 'panel',
// hidden:true,
// items: [{
// xtype: 'textfield',
// labelStyle: 'padding-left: 5px',
// fieldLabel: '商户中文简写*',
// maxLength: '40',
// vtype: 'isOverMax',
// id: 'mchtCup11',
// // allowBlank: false,
// blankText: '请输入商户简称',
// name: 'mchtCup11',
// anchor: '90%'
// }]
// },{
// columnWidth: .33,
// layout: 'form',
// xtype: 'panel',
// hidden:true,
// items: [{
// xtype: 'textfield',
// labelStyle: 'padding-left: 5px',
// fieldLabel: '英文名称',
// maxLength: 100,
// vtype: 'isOverMax',
// regex: /^\w+[\w\s]+\w+$/,
// regexText:'英文名称必须是字母，如Bill Gates',
// id: 'mchtCup12',
// name: 'mchtCup12',
// anchor: '90%'
// }]
// },{
		        	columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
							xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '行政区划码*',
							allowBlank: true,
							disabled:true,
// allowBlank: false,
							id: 'area_no',
							name: 'area_no',
							regex: /^[0-9]{6}$/,
							regexText: '行政区划码必须是6位数字',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
// xtype: 'dynamicCombo',
		       				xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
// methodName: 'getAreaCode_as',
							fieldLabel: '受理地区码*',
							name: 'acq_area_cd',
							maxLength: 4,
							allowBlank: true,
							disabled:true,
// allowBlank: false,
							anchor: '90%'
			        	}]
				},{
					columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
// xtype: 'dynamicCombo',
							xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
// methodName: 'getAreaCode_as',
							fieldLabel: '清算地区码*',
							maxLength: 4,
							allowBlank: true,
							disabled:true,
							name: 'settle_area_cd',
// allowBlank: false,
							anchor: '90%'
			        	}]
				},{
					columnWidth: .66,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'basecomboselect',
				        	store: cupMccStore,
				        	displayField: 'displayField',
							valueField: 'valueField',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户交易类型*',
							allowBlank: true,
							disabled:true,
							editable: true,
// allowBlank: false,
							id: 'mcht_typeI',
							hiddenName: 'mcht_type',
							anchor: '90%',
							'listeners':{
								'select': function(){
									var amMchtTp = mchntForm.getForm().findField('mcht_typeI').getValue();
									var realMchtTp = mchntForm.getForm().findField('real_mcht_tpI').getValue();
									
									if(amMchtTp != realMchtTp){
										mchntForm.getForm().findField('mcht_tp_reasonI').allowBlank = false;
										mchntForm.getForm().findField('mcc_mdI').allowBlank = false;
									}else{
										mchntForm.getForm().findField('mcht_tp_reasonI').allowBlank = true;
										mchntForm.getForm().findField('mcc_mdI').allowBlank = true;
									}
								}
							}
			        	}]
				},{
					columnWidth: .66,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'basecomboselect',
				        	store: cupMccStore,
				        	displayField: 'displayField',
							valueField: 'valueField',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '真实商户类型*',
							allowBlank: true,
							disabled:true,
							editable: true,
// allowBlank: false,
							id: 'real_mcht_tpI',
							hiddenName: 'real_mcht_tp',
							anchor: '90%',
							'listeners':{
								'select': function(){
									var amMchtTp = mchntForm.getForm().findField('mcht_typeI').getValue();
									var realMchtTp = mchntForm.getForm().findField('real_mcht_tpI').getValue();
									
									if(amMchtTp != realMchtTp){
										mchntForm.getForm().findField('mcht_tp_reasonI').allowBlank = false;
										mchntForm.getForm().findField('mcc_mdI').allowBlank = false;
									}else{
										mchntForm.getForm().findField('mcht_tp_reasonI').allowBlank = true;
										mchntForm.getForm().findField('mcc_mdI').allowBlank = true;
									}
								}
							}
			        	}]
				},{
		        	columnWidth: .33,
		        	xtype: 'panel',
			        layout: 'form',
		       		items: [{
							xtype: 'basecomboselect',
				        	baseParams: 'MCHNT_GRP_CUP',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户组别*',
// allowBlank: false,
							hiddenName: 'mchnt_tp_grp',
							allowBlank: true,
							disabled:true,
							id:'mchnt_tp_grpI',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'basecomboselect',
				        	baseParams: 'APP_REASON',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '套用商户类型原因',
							vtype: 'isOverMax',
							id: 'mcht_tp_reasonI',
							allowBlank: true,
							disabled:true,
							hiddenName: 'mcht_tp_reason',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'basecomboselect',
				        	baseParams: 'MCC_MD',
							labelStyle: 'padding-left: 5px',
							fieldLabel: 'MCC套用依据',
							id: 'mcc_mdI',
							hiddenName: 'mcc_md',
							allowBlank: true,
							disabled:true,
							anchor: '90%'
			        	}]
				},{
					columnWidth: .33,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'basecomboselect',
				        	baseParams: 'CH_STORE_FLAG',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '连锁店标识*',
							width:150,
							id: 'ch_store_flagI',
							hiddenName: 'ch_store_flag',
							allowBlank: true,
							disabled:true,
							value: '0',
// allowBlank: false,
							anchor: '90%',
							'listeners':{
                        		'select': function() {
                        			mchntForm.getForm().findField('mcht_file_flagI').reset();
                        		}
							}
			        	}]
				},{
                  	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不生成'],['1','1-生成自身流水文件'],['2','2-生成子商户流水文件']],
							reader: new Ext.data.ArrayReader()
						}),
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户文件生成标志*',
                        allowBlank: true,
						disabled:true,
                        id: 'mcht_file_flagI',
						hiddenName: 'mcht_file_flag',
						value: '0',
// allowBlank: false,
                        anchor: '90%',
                        'listeners':{
                        	'select': function() {
							     
							    var storeFlag = mchntForm.getForm().findField('ch_store_flagI').getValue();
							    var fileFlag = mchntForm.getForm().findField('mcht_file_flagI').getValue();
							    
							    if(storeFlag == ''|| storeFlag == null){
							    	mchntForm.getForm().findField('mcht_file_flagI').reset();
							    	showMsg('请先选择连锁店标识！',mchntForm);
							    }else{
							    	if(storeFlag != '1' && fileFlag =='2'){
								    	mchntForm.getForm().findField('mcht_file_flagI').reset();
								    	showMsg('仅在连锁店标识为“根总店”时可选“2-生成子商户流水文件”！',mchntForm);
							    	}
							    }
                        	}
                        }
                    }]
				}]
    	   },{
                title:'直联签约',
                xtype: 'panel',
                disabled:true,
                id: 'sign',
                frame: true,
				layout:'column',
                items: [{
                	columnWidth: .5,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['80000000000000000000','80000000000000000000-人民币']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户受理币种*',
							allowBlank: true,
							disabled:true,
							maxLength: 40,
// allowBlank: false,
							vtype: 'isOverMax',
							value:'80000000000000000000',
							id: 'mcht_acq_currI',
							hiddenName: 'mcht_acq_curr',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .5,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['156','156-人民币']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户默认交易币种*',
							maxLength: 40,
// allowBlank: false,
							vtype: 'isOverMax',
							value:'156',
							allowBlank: true,
							disabled:true,
							id: 'currcy_cdI',
							hiddenName: 'currcy_cd',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否向商户收取押金 *',
//	                        allowBlank: true,
							disabled:true,
	                        id: 'deposit_flag',
	                        name: 'deposit_flag'
	                    }]
                },{
                	columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '商户是否保存卡号 *',
//	                        allowBlank: true,
							disabled:true,
	                        id: 'res_pan_flag',
	                        name: 'res_pan_flag'
	                    }]
                },{
                	columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '商户是否保存磁道信息 ',
//	                        allowBlank: true,
							disabled:true,
	                        id: 'res_track_flag',
	                        name: 'res_track_flag'
	                    }]
                },{
                	columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否向商户收取服务费 ',
//	                        allowBlank: true,
							disabled:true,
	                        id: 'process_flag',
	                        name: 'process_flag'
	                    }]
                },{
// columnWidth: .5,
// layout: 'form',
// xtype: 'panel',
// items: [{
// xtype: 'checkbox',
// labelStyle: 'padding-left: 5px',
// fieldLabel: '是否为周期商户*',
// id: 'cycle_mcht',
// name: 'cycle_mcht'
// }]
// },{
					columnWidth: .5,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['1','1-校验MAC、交易加密'],['2','2-校验MAC、交易不加密'],
									   ['3','3-不校验MAC、交易加密'],['4','4-不校验MAC、交易不加密']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: 'MAC校验和交易加密标志*',
							allowBlank: true,
							disabled:true,
							id: 'mac_chk_flagI',
							hiddenName: 'mac_chk_flag',
							value:'4',
							anchor: '90%'
			        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	id:'accountHide1',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照编号',
						allowBlank: true,
						disabled:true,
						maxLength: 20,
						id: 'licence_no',
						anchor: '90%'
		        	}]
				}
                ,{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
                	hidden:true,
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业地址',
						allowBlank: true,
						disabled:true,
						maxLength: 60,
						vtype: 'isOverMax',
						id: 'licence_add',
						anchor: '90%'
		        	}]
				}
                ,{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '负责人',
						maxLength: 10,
						hidden:true,
						allowBlank: true,
						disabled:true,
						vtype: 'isOverMax',
						id: 'principal',
						anchor: '90%'
		        	}]
		        }
                ,{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '固定电话',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maxLength: 15,
						vtype: 'isOverMax',
						id: 'comm_tel',
						allowBlank: true,
						disabled:true,
						anchor: '90%'
		        	}]
// },{
// columnWidth: .5,
// xtype: 'panel',
// layout: 'form',
// items: [{
// xtype: 'textfield',
// labelStyle: 'padding-left: 5px',
// fieldLabel: '法人代表（负责人）*',
// maxLength: 20,
// allowBlank: false,
// vtype: 'isOverMax',
// id: 'manager',
// anchor: '90%'
// }]
// },{
// columnWidth: .5,
// xtype: 'panel',
// layout: 'form',
// items: [{
// xtype: 'basecomboselect',
// baseParams: 'CERTIFICATE_CUP',
// labelStyle: 'padding-left: 5px',
// fieldLabel: '法人代表证件类型*',
// allowBlank: false,
// value: '01',
// hiddenName: 'mana_cred_tp',
// id: 'mana_cred_tpI',
// anchor: '90%'
// }]
// },{
// columnWidth: .5,
// xtype: 'panel',
// layout: 'form',
// items: [{
// xtype: 'textfield',
// labelStyle: 'padding-left: 5px',
// fieldLabel: '法人证件号*',
// maxLength: 20,
// allowBlank: false,
// vtype: 'isOverMax',
// id: 'mana_cred_no',
// anchor: '90%'
// }]
				}
                ]
            },{
                title:'直联清算',
                xtype: 'panel',
                layout:'column',
                id: 'cupSettle',
                disabled:true,
                frame: true,
                items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'rebateFlag',
	       			items: [{
			        	xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','1-固定比例'],['2','2-固定金额'],
								   ['3','3-算法描述']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '扣率算法标识*',
						id: 'rebate_flagI',
						hiddenName: 'rebate_flag',
						allowBlank: true,
						disabled:true,
// allowBlank: false,
						anchor: '90%',
						listeners: {
						      'select': function() {
							     
							    var rebateFlag = mchntForm.getForm().findField('rebate_flagI').getValue();
	                    	
		                    	if(rebateFlag == '1'){
		                    		Ext.getCmp('rebateACQH').hide();
		                    		Ext.getCmp('rebateACQW').show();
		                    		Ext.getCmp('rebate_stlm_cd').disable();
		                    		
		                    		Ext.getCmp('mchtCup401').allowBlank = false;
		                    		Ext.getCmp('mchtCup40').allowBlank = true;
		                    		Ext.getCmp('rebate_stlm_cd').allowBlank = true;
		                    		
		                    		mchntForm.getForm().findField("mchtCup40").reset();
		                    		mchntForm.getForm().findField("mchtCup401").reset();
		                    		mchntForm.getForm().findField("rebate_stlm_cd").reset();
			                     	
		                    	}else if(rebateFlag == '2'){
		                    		Ext.getCmp('rebateACQW').hide();
		                    		Ext.getCmp('rebateACQH').show();
		                    		Ext.getCmp('rebate_stlm_cd').disable();
		                    		
		                    		Ext.getCmp('mchtCup40').allowBlank = false;
		                    		Ext.getCmp('mchtCup401').allowBlank = true;
		                    		Ext.getCmp('rebate_stlm_cd').allowBlank = true;
		                    		
		                    		mchntForm.getForm().findField("mchtCup40").reset();
		                    		mchntForm.getForm().findField("mchtCup401").reset();
		                    		mchntForm.getForm().findField("rebate_stlm_cd").reset();
		                    	}else if(rebateFlag == '3'){
		                    		Ext.getCmp('rebateACQH').hide();
		                    		Ext.getCmp('rebateACQW').show();
		                    		
		                    		Ext.getCmp('mchtCup401').allowBlank = false;
		                    		Ext.getCmp('mchtCup40').allowBlank = true;
		                    		Ext.getCmp('rebate_stlm_cd').enable();
		                    		Ext.getCmp('rebate_stlm_cd').allowBlank = false;
			                     	
			                     	
			                     	mchntForm.getForm().findField("mchtCup40").reset();
		                    		mchntForm.getForm().findField("mchtCup401").reset();
		                    		mchntForm.getForm().findField("rebate_stlm_cd").reset();
		                    	}
					        }}
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'rebateACQH',
		        	labelWidth:160,
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '签约商户内卡收单扣率(分)*',
						maxLength: 8,
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						allowBlank: true,
						disabled:true,
						id: 'mchtCup40',
						name: 'mchtCup40',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'rebateACQW',
		        	hidden: true,
		        	labelWidth:180,
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '签约商户内卡收单扣率(%%)*',
						allowBlank: true,
						disabled:true,
						maxLength: 8,
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						id: 'mchtCup401',
						name: 'mchtCup401',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'stlmCd',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '扣率算法代码*',
						allowBlank: true,
						disabled:true,
						maxLength: 5,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						disabled: true,
						id: 'rebate_stlm_cd',
						name: 'rebate_stlm_cd',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combo',
	       				labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','1-清算到收单行'],['2','2-清算到开户行'],
								   ['3','3-清算到银联']],
							reader: new Ext.data.ArrayReader()
						}),
						allowBlank: true,
						disabled:true,
						fieldLabel: '清算机构类型*',
						value:'1',
// allowBlank: false,
						id: 'settle_bank_typeI',
						hiddenName: 'settle_bank_type',
						anchor: '90%',
						listeners:{
							'select': function() {
								var settleBrhTp = mchntForm.getForm().findField('settle_bank_typeI').getValue();
								
								if(settleBrhTp == '1'){
									mchntForm.getForm().findField('fee_act').allowBlank = false;
									mchntForm.getForm().findField('card_in_settle_bank').allowBlank = true;
									mchntForm.getForm().findField('settle_bank_no').allowBlank = true;
								}
								if(settleBrhTp == '2'){
									mchntForm.getForm().findField('fee_act').allowBlank = false;
									mchntForm.getForm().findField('card_in_settle_bank').allowBlank = false;
									mchntForm.getForm().findField('settle_bank_no').allowBlank = false;
								}
								if(settleBrhTp == '3'){
									mchntForm.getForm().findField('fee_act').allowBlank = true;
									mchntForm.getForm().findField('card_in_settle_bank').allowBlank = false;
									mchntForm.getForm().findField('settle_bank_no').allowBlank = false;
								}
							}
						}
					}]
				}, {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
// xtype: 'dynamicCombo',
// methodName: 'getMchntCupBank',
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '内卡清算资金开户行清算号',
						id: 'card_in_settle_bank',
						name: 'card_in_settle_bank',
						allowBlank: true,
						disabled:true,
						anchor: '90%'
		        	}]
		        },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '内卡清算资金开户行名称',
						allowBlank: true,
						disabled:true,
						id: 'settle_bank_no',
						name: 'settle_bank_no',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'textfield',
		        		labelStyle: 'padding-left: 5px',
		        		allowBlank: true,
						disabled:true,
						fieldLabel: '开户行清算号*',
// allowBlank: false,
						maxLength:12,
						id: 'open_stlno',
						name: 'open_stlno',
						anchor: '90%'
		        	}]
				},{
                	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行',
						allowBlank: true,
						disabled:true,
						id: 'settle_bank',
						name: 'settle_bank',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'datefield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '内卡开始收单日期',
						allowBlank: true,
						disabled:true,
						format : 'Ymd',
						id: 'card_in_start_date',
						name: 'card_in_start_date',
						anchor: '90%'
					}]
				},{
		        	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '清算账户账号',
						allowBlank: true,
						disabled:true,
                        maxLength: 40,
						id: 'settle_acct',
						name: 'settle_acct',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-否'],['1','1-是']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '异常时收单垫付标志*',
						allowBlank: true,
						disabled:true,
// allowBlank: false,
						value: '0',
						id: 'advanced_flagI',
						hiddenName: 'advanced_flag',
						anchor: '90%',
						listeners:{
							'select':function(){
								mchntForm.getForm().findField('prior_flagI').reset();
							}
						}
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combo',
		        		labelStyle: 'padding-left: 5px',
		        		allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-普通商户'],['1','1-重点商户'],['2','2-收单T+1保障']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '商户优先标志*',
// allowBlank: false,
						value: '0',
						id: 'prior_flagI',
						hiddenName: 'prior_flag',
						anchor: '90%',
						listeners: {
							'select': function() {
								var advanceFlag = mchntForm.getForm().findField('advanced_flagI').getValue();
								var priorFlag = mchntForm.getForm().findField('prior_flagI').getValue();
								
								if(advanceFlag =='' || advanceFlag == null){
									mchntForm.getForm().findField('prior_flagI').reset();
									showMsg('请先选择异常时收单垫付标志！',mchntForm);
								}else{
									if(advanceFlag == '0' && priorFlag == '2'){
										mchntForm.getForm().findField('prior_flagI').reset();
										showMsg('异常时收单垫付标志为否时商户优先标志只能为普通商户和重点商户！',mchntForm);
									}
									if(advanceFlag == '1' && (priorFlag =='0' || priorFlag =='1')){
										mchntForm.getForm().findField('prior_flagI').reset();
										showMsg('异常时收单垫付标志为是时商户优先标志只能为收单T+1保障！',mchntForm);
									}
								}
							}
						}
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','1-清算到本店'],['2','2-清算到总店']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '商户清算模式*',
// allowBlank: false,
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						disabled:true,
						id: 'settle_modeI',
						hiddenName: 'settle_mode',
						value: '1',
						anchor: '90%'
			    	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
			        	labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '周期结算类型*',
// allowBlank: false,
						id: 'cycle_settle_typeI',
						allowBlank: true,
						disabled:true,
						hiddenName: 'cycle_settle_type',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combo',
		        		labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-否'],['1','1-是']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '是否参加日间清算*',
						allowBlank: true,
						disabled:true,
// allowBlank: false,
						id: 'day_stlm_flagI',
						hiddenName: 'day_stlm_flag',
						value: '0',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-否'],['1','1-是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						disabled:true,
						fieldLabel: '银联代理清算标识*',
// allowBlank: false,
						id: 'cup_stlm_flagI',
						hiddenName: 'cup_stlm_flag',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combo',
		        		labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-部分回补'],['1','1-全部回补']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '垫付回补类型*',
						allowBlank: true,
						disabled:true,
// allowBlank: false,
						id: 'adv_ret_flagI',
						hiddenName: 'adv_ret_flag',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combo',
		        		allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
							reader: new Ext.data.ArrayReader()
						}),
		        		labelStyle: 'padding-left: 5px',
						fieldLabel: '本金清算周期',
// allowBlank: false,
						id: 'capital_sett_cycleI',
						hiddenName: 'capital_sett_cycle',
						value: '0',
						anchor: '90%'
		        	}]
		        },{
					columnWidth: .25,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv',
		        	labelWidth: 120,
	       			items: [{
			        	xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分段计费模式： 段1',
						allowBlank: true,
						disabled:true,
						id: 'mchtCup54I',
						hiddenName: 'mchtCup54',
						width:100,
						value: '0',
						listeners: {
	                		'select': function() {
	                			var div = mchntForm.getForm().findField('mchtCup54I').getValue();
	                			
	                			if(div =='0'){
	                				mchntForm.getForm().findField("mchtCup541I").setValue('0');
	                				mchntForm.getForm().findField("mchtCup542I").setValue('0');
	                				mchntForm.getForm().findField("mchtCup543I").setValue('0');
	                				Ext.getCmp('mchtCup541I').readOnly = true;
	                				Ext.getCmp('mchtCup542I').readOnly = true;
	                				Ext.getCmp('mchtCup543I').readOnly = true;
	                			}else{
	                				mchntForm.getForm().findField("mchtCup541I").reset();
	                				mchntForm.getForm().findField("mchtCup542I").reset();
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				Ext.getCmp('mchtCup541I').readOnly = false;
	                				Ext.getCmp('mchtCup542I').readOnly = false;
	                				Ext.getCmp('mchtCup543I').readOnly = false;
	                			}
	                		}
						}
		        	}]
				},{
	        		columnWidth: .15,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv1',
		        	labelWidth: 30,
	       			items: [{
			        	xtype: 'combo',
			        	fieldLabel: '段2',
			        	allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						id: 'mchtCup541I',
						hiddenName: 'mchtCup541',
						value: '0',
						width:100,
						listeners: {
	                		'select': function() {
	                			var div = mchntForm.getForm().findField('mchtCup54I').getValue();
	                			var div1 = mchntForm.getForm().findField('mchtCup541I').getValue();
	                			
	                			
	                			if(div1 =='0'){
	                				mchntForm.getForm().findField("mchtCup542I").setValue('0');
	                				mchntForm.getForm().findField("mchtCup543I").setValue('0');
	                				Ext.getCmp('mchtCup542I').readOnly = true;
	                				Ext.getCmp('mchtCup543I').readOnly = true;
	                			}else{
	                				if(div=='0'){
	                					mchntForm.getForm().findField("mchtCup541I").reset();
	                					showMsg('上段不启用时此段只能为不启用！',mchntForm);
	                				}
	                				mchntForm.getForm().findField("mchtCup542I").reset();
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				Ext.getCmp('mchtCup542I').readOnly = false;
	                				Ext.getCmp('mchtCup543I').readOnly = false;
	                			}
	                		}
						}
		        	}]
				},{
					columnWidth: .15,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv2',
		        	labelWidth: 30,
	       			items: [{
			        	xtype: 'combo',
			        	fieldLabel: '段3',
			        	allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						id: 'mchtCup542I',
						hiddenName: 'mchtCup542',
						value: '0',
						width:100,
						listeners: {
	                		'select': function() {
	                			var div1 = mchntForm.getForm().findField('mchtCup541I').getValue();
	                			var div2 = mchntForm.getForm().findField('mchtCup542I').getValue();
	                			
	                			if(div2 =='0'){
	                				mchntForm.getForm().findField("mchtCup543I").setValue('0');
	                				Ext.getCmp('mchtCup543I').readOnly = true;
	                			}else{
	                				if(div1=='0'){
	                					mchntForm.getForm().findField("mchtCup542I").reset();
	                					showMsg('上段不启用时此段只能为不启用！',mchntForm);
	                				}
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				
	                				Ext.getCmp('mchtCup543I').readOnly = false;
	                			}
	                		}
						}
		        	}]
				},{
					columnWidth: .4,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv3',
		        	labelWidth: 30,
	       			items: [{
			        	xtype: 'combo',
			        	fieldLabel: '段4',
			        	allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
// readOnly: true,
						id: 'mchtCup543I',
						hiddenName: 'mchtCup543',
						value: '0',
						width:100,
// anchor: '13%',
						listeners: {
	                		'select': function() {
	                			var div2 = mchntForm.getForm().findField('mchtCup542I').getValue();
	                			var div3 = mchntForm.getForm().findField('mchtCup543I').getValue();
	                			
	                			if(div3 !='0' && div2=='0'){
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				showMsg('上段不启用时此段只能为不启用！',mchntForm);
	                			}
	                		}
						}
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combo',
	       				allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['00','00-无特殊计费类型'],['01','01-周期计费'],['02','02-微额打包'],['03','03-固定比例'],['04','04-县乡优惠'],['05','05-大商户优惠']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费类型',
						id: 'fee_spe_typeI',
						hiddenName: 'fee_spe_type',
						value:'00',
						anchor: '90%',
						listeners: {
	                		'select': function() {
	                			mchntForm.getForm().findField('fee_spe_graI').reset();
	                		}
						}
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combo',
	       				allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-月结按MCC计费'],['1','1-月结不按MCC计费'],['2','2-普通商户'],['3','3-三农商户'],
								   ['4','4-大商户1级'],['5','5-大商户2级'],['6','6-大商户3级'],['7','7-无特殊计费档次'] ],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费档次',
						id: 'fee_spe_graI',
						hiddenName: 'fee_spe_gra',
						value:'7',
						anchor: '90%',
						listeners: {
	                		'select': function() {
	                			var feeSpeT = Ext.getCmp("fee_spe_typeI").getValue();
	                			var feeSpe = Ext.getCmp("fee_spe_graI").getValue();
	                			
	                			if(feeSpeT == null || feeSpeT ==''){
	                				mchntForm.getForm().findField('fee_spe_graI').reset();
	                				showMsg('请先选择特殊计费类型！',mchntForm);
	                			}else{
	                				if(feeSpeT =='01'&& feeSpe !='0' && feeSpe !='1'){
		                				mchntForm.getForm().findField('fee_spe_graI').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择月结按MCC计费或月结不按MCC计费！',mchntForm);
		                			}else if(feeSpeT =='04'&& feeSpe !='2' && feeSpe !='3'){
		                				mchntForm.getForm().findField('fee_spe_graI').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择普通商户或三农商户！',mchntForm);
		                			}else if(feeSpeT =='05'&& feeSpe !='4' && feeSpe !='5' && feeSpe !='6'){
		                				mchntForm.getForm().findField('fee_spe_graI').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择大商户1级或大商户2级或大商户3级！',mchntForm);
		                			}else if((feeSpeT =='00' || feeSpeT =='02' || feeSpeT =='03') && feeSpe !='7'){
		                				mchntForm.getForm().findField('fee_spe_graI').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择无特殊计费档次！',mchntForm);
		                			}
	                			}
	                		}
						}
		        	}]
				}]
			},{
                title:'直联分润',
                xtype: 'panel',
                layout:'column',
                id: 'rate',
                disabled:true,
                frame: true,
                items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'basecomboselect',
				        baseParams: 'REASON_CODE',
                        labelStyle: 'padding-left: 5px',
                        allowBlank: true,
						disabled:true,
                        fieldLabel: '登记原因码*',
// allowBlank: false,
                        id: 'reason_codeI',
                        hiddenName: 'reason_code',
                        anchor: '90%'
                    }]
				} ,{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
			        	allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-正常结算类'],['1','1-特殊结算类（普通月结）'],['2','2-特殊结算类（包月类）'],['3','3-特殊结算类（月结封顶、保底类）']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '结算类型标识',
						id: 'rate_typeI',
                        hiddenName: 'rate_type',
						anchor: '90%'
						
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
			        	allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '手续费清算周期',
						value: '0',
						id: 'fee_cycleI',
                        hiddenName: 'fee_cycle',
						anchor: '90%'
		        	}]
				}, {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
			        	allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-固定比例（封顶、保底）'],['1','1-固定金额'],['2','2-算法代码']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收费类型-单笔',
						id: 'fee_typeI',
                        hiddenName: 'fee_type',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
			        	allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-普通'],['1','1-封顶'],['2','2-保底']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '封顶、保底信息-单笔',
						id: 'limit_flagI',
                        hiddenName: 'limit_flag',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
			        	allowBlank: true,
						disabled:true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '手续费扣率-单笔(%%)',
						maxLength: 8,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'fee_rebate',
                        name: 'fee_rebate',
                        value: '0',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
			        	allowBlank: true,
						disabled:true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '固定金额-单笔(分)', 
						maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'settle_amt',
                        name: 'settle_amt',
                        value: '0',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'textfield',
                        allowBlank: true,
						disabled:true,
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '封顶值-单笔*(分)',
                        maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        id: 'amt_top',
                        name: 'amt_top',
                        value: '999999999999',
// allowBlank: false,
						anchor: '90%'
                    }]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
			        	allowBlank: true,
						disabled:true,
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '保底值-单笔*(分)',
                        maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        id: 'amt_bottom',
                        name: 'amt_bottom',
// allowBlank: false,
                        value: '0',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
			        	allowBlank: true,
						disabled:true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '算法代码-单笔',
						maxLength: 5,
						vtype: 'isOverMax',
						id: 'disc_cd',
						name: 'disc_cd',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
			        	allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-封顶/保底'],['1','1-固定金额'],['2','2-计费代码']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收费类型-月结类',
						id: 'fee_type_mI',
						hiddenName: 'fee_type_m',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
			        	allowBlank: true,
						disabled:true,
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-普通'],['1','1-封顶'],['2','2-保底']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '封顶、保底信息-月结类',
						id: 'limit_flag_mI',
						hiddenName: 'limit_flag_m',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
			        	allowBlank: true,
						disabled:true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '固定金额-月结类（分）',
						maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'settle_amt_m',
						name: 'settle_amt_m',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'textfield',
                        allowBlank: true,
						disabled:true,
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '封顶值-月结*（分）',
                        maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        id: 'amt_top_m',
                        name: 'amt_top_m',
                        value: '999999999999',
						anchor: '90%'
                    }]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '保底值-月结类(分)',
						allowBlank: true,
						disabled:true,
						maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'amt_bottom_m',
						name: 'amt_bottom_m',
						value: '0',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '算法代码-月结类',
						allowBlank: true,
						disabled:true,
						maxLength: 5,
						vtype: 'isOverMax',
						id: 'disc_cd_m',
						name: 'disc_cd_m',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润方式（CUPS侧）',
						allowBlank: true,
						disabled:true,
						maxLength: 5,
						vtype: 'isOverMax',
						id: 'fee_rate_type',
						name: 'fee_rate_type',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润代码',
						allowBlank: true,
						disabled:true,
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'rate_no',
						name: 'rate_no',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .38,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'textfield',
		        		labelStyle: 'padding-left: 5px',
						fieldLabel: '商户手续费决定索引',
						allowBlank: true,
						disabled:true,
						maxLength: 30,
						id: 'fee_act',
						name: 'fee_act',
						readOnly:true,
						anchor: '95%'
		        	}]
				},{
	        		columnWidth: .12,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'button',
                        text: '设置',
                        id: 'setButton',
//                        allowBlank: true,
						disabled:true,
                        width: 120,
                        handler: function() {
							feeWin.show();
							feeWin.center();
                        }
                    }]
				},{
	        		columnWidth: .38,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户分润手续费索引*',
						allowBlank: true,
						disabled:true,
						readOnly:true,
						id: 'feerate_index',
						name: 'feerate_index',
						anchor: '95%'
		        	}]
		        }, {
		        	columnWidth: .12,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'button',
                        text: '设置',
                        id: 'setButton2',
//                        allowBlank: true,
						disabled:true,
                        width: 120,
                        handler: function() {
							feeWin2.show();
							feeWin2.center();
                        }
                    }]
				},{
	        		columnWidth: .99,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
					    baseParams: 'MCHNT_CUP_FATE',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						disabled:true,
						fieldLabel: '分润算法',
						id: 'rate_discI',
						hiddenName: 'rate_disc',
						width: 820
		        	}]
				},{
	        		columnWidth: .22,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 130,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润角色： 分润机构1',
//						allowBlank: true,
						disabled:true,
						width: 150,
						id: 'mchtCup781',
						name: 'mchtCup781',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构2',
//						allowBlank: true,
						disabled:true,
						id: 'mchtCup782',
						name: 'mchtCup782',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构3',
//						allowBlank: true,
						disabled:true,
						id: 'mchtCup783',
						name: 'mchtCup783',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构4',
//						allowBlank: true,
						disabled:true,
						id: 'mchtCup784',
						name: 'mchtCup784',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构5',
//						allowBlank: true,
						disabled:true,
						id: 'mchtCup785',
						name: 'mchtCup785',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 40,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行',
//						allowBlank: true,
						disabled:true,
						id: 'mchtCup786',
						name: 'mchtCup786',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 40,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收单行',
//						allowBlank: true,
						disabled:true,
						id: 'mchtCup787',
						name: 'mchtCup787',
						anchor: '90%'
		        	}]
				}]
			},{
                title:'直联其他',
                xtype: 'panel',
                layout:'column',
                id: 'other',
                disabled:true,
                frame: true,
                items: [{
                    columnWidth: .3,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:260,
	       			items: [{
                        xtype: 'checkbox',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '报表生成标志位图* ：  是否生成汇总类报表',
                        id: 'mchtCup82',
						name: 'mchtCup82',
//						allowBlank: true,
						disabled:true,
						checked: true,
						anchor: '90%'
                    }]
                  },{
                  	columnWidth: .18,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:140,
	       			items: [{
                        xtype: 'checkbox',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '是否生成扣账垫付类报表',
//                        allowBlank: true,
						disabled:true,
                        id: 'mchtCup821',
						name: 'mchtCup821',
						checked: true,
                        anchor: '90%'
                    }]
                  },{
                  	columnWidth: .52,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:140,
		        	html:'<br/>',
	       			items: [{
                        xtype: 'checkbox',
                        labelStyle: 'padding-left: 5px',
//                        allowBlank: true,
						disabled:true,
                        fieldLabel: '是否生成交易明细类报表',
                        id: 'mchtCup822',
						name: 'mchtCup822',
						checked: true,
                        anchor: '90%'
                    }]
                  },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:190,
	       			items: [{
                        xtype: 'checkbox',
                        labelStyle: 'padding-left: 5px',
//                        allowBlank: true,
						disabled:true,
                        fieldLabel: '商户属性位图* : 是否收支两条线',// 填充到10位
                        id: 'attr_bmp',
						name: 'attr_bmp',
                        anchor: '90%'
                    }]
                  }]
			}
// title:'证书影像',
// id: 'images',
// frame: true,
// layout: 'border',
// items: [{
// xtype: 'panel',
// region: 'center',
// items : dataview,
// frame: true,
// tbar: [{
// xtype: 'button',
// text: '刷新',
// width: '80',
// id: 'view',
// handler:function() {
// storeImg.reload({
// params: {
// start: 0,
// imagesId: imagesId
// }
// });
// }
// },{
// xtype: 'button',
// width: '80',
// text: '上传',
// id: 'upload',
// handler:function() {
// hasUpload = "1";
// dialog.show();
// }
// }]
// }]
// }
			]
        }],
        buttons: [{
            text: '提交审批',
            id: 'save',
            name: 'save',
            handler : function() {
            	subSave();
            }
        },{
            text: '清空',
            handler: function() {
            	hasSub = false;
            	checkIds = "T";
				mchntForm.getForm().reset();
			}
        }]
    });
    
    // 外部加入监听
// Ext.getCmp("card_in_settle_bankI").on('select',function(){
// T20100.getInfo(Ext.getCmp("card_in_settle_bankI").getValue(),function(ret){
// if(ret=='0'){
// showErrorMsg("找不到相应信息",grid);
// mchntForm.getForm().findField('card_in_settle_bankI').reset();
// mchntForm.getForm().findField('settle_bank_no').reset();
// mchntForm.getForm().findField('open_stlno').reset();
// return;
// }
//    		
// var mbfBankInfo = Ext.decode(ret.substring(1,ret.length-1));
// Ext.getCmp("settle_bank_no").setValue(mbfBankInfo.bankname);
// Ext.getCmp("open_stlno").setValue(mbfBankInfo.dpbankno);
// });
// });
    
    var checkIds = "T";
    function subSave(){
    	Ext.getCmp('settleAcct').setValue('11111111');
    	Ext.getCmp('idclearType').setValue('O');
    	Ext.getCmp('serdiscNm').setValue('000000');
    	
    	var btn = Ext.getCmp('save');
		var frm = mchntForm.getForm();
		hasSub = true;
		if (frm.isValid()) {
			var flag = mchntForm.getForm().findField('clearType').getValue();
			var regIdCard=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
			if(Ext.getCmp('idartifCertifTp').getValue()=='01'||Ext.getCmp('idartifCertifTp').getValue()=='09'){
				var card=Ext.getCmp('identityNo').getValue();
				if(!regIdCard.test(card)){
					showAlertMsg("身份证号填写不正确",mchntForm);
					Ext.getCmp("tab").setActiveTab('basic');
					return false;
				}
			}
			
			btn.disable();
			frm.submit({
				url: 'T20100Action_add.asp',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
					hasSub = false;
					checkIds = "T";
					showSuccessAlert(action.result.msg,mchntForm);
					btn.enable();
					mchntForm.getForm().findField("mchtGrp").readOnly=false;
             		mchntForm.getForm().findField("mcc").readOnly=false;
					frm.reset();
					hasUpload = "0";
					resetImagesId();
					storeImg.reload({
					params: {
						start: 0,
						imagesId: imagesId
						}
					});
				},
				failure : function(form,action) {
					btn.enable();
					hasSub = false;
					if (action.result.msg.substr(0,2) == 'CZ') {
						
						Ext.MessageBox.show({
							msg: action.result.msg.substr(2) + '<br><h1>是否继续保存吗？</h1>',
							title: '确认提示',
							animEl: Ext.get(mchntForm.getEl()),
							buttons: Ext.MessageBox.YESNO,
							icon: Ext.MessageBox.QUESTION,
							modal: true,
							width: 500,
							fn: function(bt) {
								if(bt == 'yes') {
									checkIds = "F";
									subSave();
								}
							}
						});
					} else {
						showErrorMsg(action.result.msg,mchntForm);
					}
				},
				params: {
					txnId: '20101',
					subTxnId: '01',
					hasUpload: hasUpload,
					imagesId: imagesId,
					checkIds: checkIds
				}
		});
		
	}else{
		// 自动切换到未通过验证的tab
		var finded = true;
		frm.items.each(function(f){
    		if(finded && !f.validate()){
    			var tab = f.ownerCt.ownerCt.id;
    			var tab2 = f.ownerCt.ownerCt.ownerCt.id;
//    			if(tab2 == 'feeamt'){
//    				 Ext.getCmp("tab").setActiveTab(tab2);
//    			}
//    			|| tab == 'settle' || tab == 'feeamt' || 
//				tab == 'cupBasic' || tab == 'sign' || tab == 'cupSettle' || tab == 'rate' || tab == 'other'
    			if(tab == 'basic' || tab == 'append'|| tab == 'settle' ){
    				 Ext.getCmp("tab").setActiveTab(tab);
    			}
    			finded = false;
    		}
    	}
    );
	}}


    // 为保证验证信息显示的正确，当切换tab时重新验证
    Ext.getCmp("tab").on('tabchange',function(){
    	if(hasSub){
			mchntForm.getForm().isValid();
		}else{
			mchntForm.getForm().clearInvalid();
		}
    });

    gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			discCd: Ext.getCmp('serdiscCd').getValue(),
			discNm: Ext.getCmp('serdiscNm').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntForm],
		renderTo: Ext.getBody()
	});
	setDetail('mappingMchntcdOne','mappingMchntcdTwo');
	//隐藏选项卡
	var maintab=Ext.getCmp('tab');
	
	maintab.hideTabStripItem(3);
	maintab.hideTabStripItem(4);
	maintab.hideTabStripItem(5);
	maintab.hideTabStripItem(6);
	maintab.hideTabStripItem(7);
	maintab.hideTabStripItem(8);
});