Ext.onReady(function() {
	//保存是否验证成功的变量
	var verifyResult = false;
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

	//******************图片处理部分**********开始********
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
	
	var clearTypeStore2 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['P','本行对私账户或单位卡'],['S','他行对私账户']],
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

	//******************图片处理部分**********结束********



    //******************计费算法部分**********开始********
	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});

	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

	SelectOptionsDWR.getComboData('TXN_NUM_FEE',function(ret){
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
			{name: 'floorMount',mapping: 'floorMount'},
			{name: 'minFee',mapping: 'minFee'},
			{name: 'maxFee',mapping: 'maxFee'},
			{name: 'flag',mapping: 'flag'},
			{name: 'feeValue',mapping: 'feeValue'}
		]),
		autoLoad: false
	});

	var cm = new Ext.grid.ColumnModel({
		columns: [{
            header: '交易卡种',
            dataIndex: 'txnNum',
            width: 120,
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
            id: 'floorMount',
            header: '最低交易金额',
            dataIndex: 'floorMount',
            width: 80,
            sortable: true
        },{
            header: '回佣类型',
            dataIndex: 'flag',
            width: 90,
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
            header: '回佣值',
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
		autoExpandColumn: 'floorMount',
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
		{header: '计费代码',dataIndex: 'discCd',sortable: true,width: 60},
		{header: '计费名称',dataIndex: 'discNm',sortable: true,id:'discNm',width:100},
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
	 //******************计费算法部分**********结束********


	
	//重置账号验证
	function resetVerify(){
		verifyResult = false;
//	    Ext.getCmp('verifyName').setValue('');
//	    Ext.getCmp('verifySta').setValue('<font color="red">未验证</font>');
	}

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
			        baseParams: 'MCHT_GROUP_FLAG',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户种类*',
					id: 'idmchtGroupFlag',
					hiddenName: 'mchtGroupFlag',
					allowBlank: false,
					anchor: '90%',
					value: '1',
					listeners: {
	                	'select': function() {
	                     	var flag = mchntForm.getForm().findField('mchtGroupFlag').getValue();
	                     	//初始化
	                     	resetVerify();
	                     	mchntForm.getForm().findField("mchtGroupId").disable();
	                     	mchntForm.getForm().findField("clearType").readOnly=false;
	                     	mchntForm.getForm().findField("clearType").reset();
	                     	mchntForm.getForm().findField("settleType").readOnly=false;
	                     	mchntForm.getForm().findField("settleType").reset();
	                     	
	                     	mchntForm.getForm().findField("mchtGrp").readOnly=false;
	                     	mchntForm.getForm().findField("mchtGrp").reset();
	                     	mchntForm.getForm().findField("mcc").readOnly=false;
	                     	mchntForm.getForm().findField("mcc").reset();
	                     	
	                     	//根据状态选择
	                     	if(flag=='2'){
	                     		mchntForm.getForm().findField("mchtGroupId").enable();
	                     	}else if(flag=='3'){
	                     		mchntForm.getForm().findField("clearType").readOnly=true;
								mchntForm.getForm().findField("clearType").setValue('P');
	                     		mchntForm.getForm().findField("settleType").readOnly=true;
	                     		mchntForm.getForm().findField("settleType").setValue('N');
	                     		
	                     		//MCC
	                     		mchntForm.getForm().findField("mchtGrp").readOnly=true;
	                     		mchntForm.getForm().findField("mcc").readOnly=true;
	                     		
	                     		mchntForm.getForm().findField("mchtGrp").setValue('08');
	                     		SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrp').getValue(),function(ret){
									mchntMccStore.loadData(Ext.decode(ret));
									mchntForm.getForm().findField("mcc").setValue('0000');
								});
								
	                     	}
	                    }
					}
		        }]
        	},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
        			xtype: 'basecomboselect',
			        baseParams: 'CONN_TYPE',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户类型*',
					id: 'idconnType',
					hiddenName: 'connType',
					allowBlank: false,
					anchor: '90%',
					value: 'J'
				}]
        	},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
			        xtype: 'checkbox',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '是否映射其他商户号',
					id: 'selectOtherNo',
					name: 'selectOtherNo',
		        	listeners: {
		        		'check':function(r,c){
		        			if(c){
								mchntForm.getForm().findField("idOtherNo").enable();
							}else{
								mchntForm.getForm().findField("idOtherNo").disable();
							}
		        		}
		        	}
        		}]
        	},
//        	{
//        		columnWidth: .33,
//        		xtype: 'panel',
//		        layout: 'form',
//	       		items: [{
//			        xtype: 'basecomboselect',
//			        baseParams: 'MCHT_GROUP',
//					labelStyle: 'padding-left: 5px',
//					fieldLabel: '集团商户',
//					id: 'idmchtGroupId',
//					hiddenName: 'mchtGroupId',
//					disabled: true,
//					anchor: '90%'
//		        }]
//			},
        	{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_BUS_TYPE',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '业务类型',
					id: 'idBusType',
					hiddenName: 'busType',
					//disabled: true,
					anchor: '90%'
		        }]
			},{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_CARD_TYPE',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '卡类型',
					id: 'idCardType',
					hiddenName: 'cardType',
					//disabled: true,
					anchor: '90%'
		        }]
			},
			{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '映射商户号',
					maxLength: '15',
					vtype: 'isOverMax',
					id: 'idOtherNo',
					regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字0-9',
					maskRe: /^[0-9]+$/,
					disabled: true,
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '自定义商户号',
					maxLength: '15',
					vtype: 'isOverMax',
					id: 'mchtNoBySelf',
					name: 'mchtNoBySelf',
					regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字0-9',
					maskRe: /^[0-9]+$/,
					anchor: '90%'
	        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'BRH_BELOW',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '收单机构*',
					allowBlank: false,
					blankText: '请选择收单机构',
					id: 'idacqInstId',
					hiddenName: 'acqInstId',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '中文名称*',
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
					fieldLabel: '中文名称简写*',
					maxLength: '40',
					vtype: 'isOverMax',
					id: 'mchtCnAbbr',
  				    allowBlank: false,
					blankText: '请输入中文名称简写',
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
						fieldLabel: '所在地区*',
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
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户组别*',
						allowBlank: false,
						hiddenName: 'mchtGrp',
						anchor: '90%',
						listeners: {
							'select': function() {
								mchntMccStore.removeAll();
								Ext.getCmp('idmcc').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('mcc').value = '';
								SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrp').getValue(),function(ret){
									mchntMccStore.loadData(Ext.decode(ret));
								});
							}
						}
		        	}]
			},{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_OUT_CHANNEL',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '接出渠道',
					id: 'idOutChannel',
					hiddenName: 'outChannel',
					//disabled: true,
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户MCC*',
						store: mchntMccStore,
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						typeAhead: false,
						forceSelection: true,
						selectOnFocus: true,
						editable: true,
						allowBlank: false,
						lazyRender: true,
						anchor: '90%',
						blankText: '请选择商户MCC',
						id: 'idmcc',
						hiddenName: 'mcc'
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
            height: 340,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 150,
        	items:[{
                title:'证照号信息',
                id: 'basic',
                frame: true,
				layout:'column',
                items: [{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否使用已有证照号',
						id: 'useFlag',
						name: 'useFlag'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照编号*',
						width:150,
						maxLength: 20,
						id: 'licenceNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '税务登记证号码*',
						maxLength: 20,
						vtype: 'isOverMax',
						width:150,
						id: 'faxNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '机构代码证号码',
						maxLength: 20,
						vtype: 'isOverMax',
						width:150,
						id: 'bankLicenceNo',
						name: 'bankLicenceNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
			        	//baseParams: 'MCHNT_ATTR',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业名称',
						width:150,
						hiddenName: 'etpsNm'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_ATTR',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业性质',
						width:150,
						hiddenName: 'etpsAttr'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_CRE_LVL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业资质等级',
						width:150,
						hiddenName: 'mchtCreLvl'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '注册资金',
						regex: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
						regexText: '注册资金必须是正数，如123.45',
						maxLength: 12,
						vtype: 'isOverMax',
						width:150,
						id: 'busAmt',
						name: 'busAmt'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表*',
						width:150,
						maxLength: 10,
						vtype: 'isOverMax',
						id: 'manager'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人联系电话*',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 18,
						vtype: 'isOverMax',
						id: 'managerTel'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表证件类型*',
						width:150,
						allowBlank: false,
						hiddenName: 'artifCertifTp'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表证件号码*',
						width:150,
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
			        	width: 380,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '注册地址',
						maxLength: 60,
						vtype: 'isOverMax',
						width:150,
						id: 'regAddr',
						name: 'regAddr'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '传真',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'fax',
						name: 'fax'
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
						fieldLabel: '是否支持无磁无密交易',
						id: 'passFlag',
						name: 'passFlag'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持人工授权',
						id: 'manuAuthFlag',
						name: 'manuAuthFlag'
		        	}]
				},{
	        		columnWidth: 1,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持折扣消费',
						id: 'discConsFlg',
						name: 'discConsFlg'
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
						fieldLabel: '经营单位',
						width:150,
						maxLength: 8,
						vtype: 'isOverMax',
						id: 'prolTlr',
						name: 'prolTlr'
					},{
						fieldLabel: '协议编号',
						maxLength: 20,
						vtype: 'isOverMax',
						width:150,
						id: 'protocalId',
						name: 'protocalId'
		        	},{
						fieldLabel: '签约网点',
						maxLength: 6,
						vtype: 'isOverMax',
						width:150,
						id: 'agrBr',
						name: 'agrBr'
		        	},{
		        		xtype: 'textnotnull',
						fieldLabel: '客户经理工号*',
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
	       				xtype: 'textnotnull',
						fieldLabel: '批准人*',
						maxLength: 40,
						vtype: 'isOverMax',
						width:150,
						id: 'confirmNm',
						name: 'confirmNm'
					},{
                        xtype: 'basecomboselect',
						fieldLabel: '受理机构标示码*',
						baseParams: 'CUP_BRH_BELOW',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						hiddenName: 'signInstId',
						anchor: '80%'
			        },{
						fieldLabel: '客户经理电话',
						maxLength: 18,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						vtype: 'isOverMax',
						width:150,
						id: 'netTel',
						name: 'netTel'
		        	},{
		        		xtype: 'textnotnull',
						fieldLabel: '客户经理姓名*',
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
    					anchor: '55%'
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
    					anchor: '55%'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人姓名*',
						width:150,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'contact'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人手机*',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
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
						fieldLabel: '联系人电话*',
						width:150,
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
						fieldLabel: '电子邮件',
						width:150,
						maxLength: 40,
						vtype: 'isOverMax',
						id: 'commEmail',
						name: 'commEmail',
						vtype: 'email'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户地址*',
						width:150,
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
						fieldLabel: '单笔限额(分)',
//						regex:/^[a-zA-z]+:/,
//						regexText:'必须是正确的地址格式，如http://www.xxx.com',
                        maxLength: 60,
						vtype: 'isOverMax',
						id: 'homePage',
						name: 'homePage',
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
						fieldLabel: '商户结算周期*',
						baseParams: 'SETTLE_TYPE',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						hiddenName: 'settleType',
						anchor: '60%',
						value: '1',
                        listeners: {
		                    'select': function() {
			                    var flag = mchntForm.getForm().findField('settleType').getValue();
			                    if(flag == "0") {
			                    	clearTypeStore.removeAll();
			                    	clearTypeStore.add(clearTypeStore2.getRange());
			                    } else {
			                    	clearTypeStore.removeAll();
			                    	clearTypeStore.add(clearTypeStore1.getRange());
			                    }
			                    mchntForm.getForm().findField('clearType').reset();
			                    resetVerify();
		                    },
	                     	'change':function(){
	                     		resetVerify();
	                     	}
	                    }
					}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'combo',
						store: clearTypeStore,
						labelStyle: 'padding-left: 5px',
						displayField: 'displayField',
						valueField: 'valueField',
						emptyText: '请选择',
						hiddenName: 'clearType',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: false,
						allowBlank: false,
						fieldLabel: '商户结算账户类型*',
						anchor: '60%',
						listWidth: 160,
                        listeners: {
	                     'select': function() {
                                resetVerify();
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
						fieldLabel: '商户账户开户行名称*',
						maxLength: 80,
						vtype: 'isOverMax',
						width:150,
						id: 'settleBankNm'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '商户账户开户行代码',
						maxLength: 11,
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
						fieldLabel: '商户账户户名*',
						maxLength: 80,
						vtype: 'isOverMax',
						width:150,
						id: 'settleAcctNm'
		        	}]
				},{
	        		columnWidth: .35,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'textnotnull',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户账户账号*',
                        maxLength: 39,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        width:150,
                        id: 'settleAcct',
                        listeners: {
	                     'change': function(){
	                     		resetVerify();
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
						fieldLabel: '商户账户户名2*',
						maxLength: 80,
						vtype: 'isOverMax',
						width:150,
						id: 'settleAcctNm2'
		        	}]
				},{
	        		columnWidth: .35,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'textnotnull',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户账户账号2*',
                        maxLength: 39,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        width:150,
                        id: 'settleAcct2'
                    }]
				}
				
//				,{
//	        		columnWidth: .15,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//                        xtype: 'button',
//                        text: '账号验证',
//                        id: 'verifyButton',
//                        handler: function() {
//                        	var bt = this;
//                        	bt.disable();
//                            var flag = mchntForm.getForm().findField('clearType').getValue();
//                            if(flag==''){
//                            	showErrorMsg("验证账号前请选择商户结算账户类型。",mchntForm);
//                            	bt.enable();
//                            	return;
//                            }
//                            if(mchntForm.getForm().findField('settleAcct').getValue()==''){
//                            	showErrorMsg("验证账号前请输入账户账号。",mchntForm);
//                            	bt.enable();
//                            	return;
//                            }
//                            if(flag=='A'||flag=='P'){
//                            	
//                            	mchntForm.getForm().findField('clearType').disable();
//                            	mchntForm.getForm().findField('settleAcct').disable();
//                            	
//                            	T20100.verifyAccount(flag,Ext.getCmp("settleAcct").getValue(),'01',function(ret){
//                            		var sta = '';
//                            		var msg = '';
//                            		var err = '';
//                            		
//                            		for(var key in ret){
//                            			if(key=='result'){
//                            				sta = ret[key];
//                            			}else if(key=='accountNo'){
//                            				msg += '账户账号：';
//                            				msg += ret[key];
//                            				msg += '<br>';
//                            			}else if(key=='accountNm'){
//                            				msg += '账户户名：';
//                            				msg += ret[key];
//                            				msg += '<br>';
//                            				Ext.getCmp('verifyName').setValue('<font color="blue">'+ret[key]+'</font>');
//                            			}else if(key=='licenceNo'){
//                            				msg += '身份证：';
//                            				msg += ret[key];
//                            				msg += '<br>';
//                            			}else if(key=='msg'){
//                            				err = ret[key];
//                            				Ext.getCmp('verifySta').setValue('<font color="red">'+ret[key]+'</font>');
//                            			}
//                            		}
//                            		if(sta=='S'){
//                            			verifyResult = true;
//                            			Ext.MessageBox.show({
//											msg: msg,
//											title: '成功',
//											animEl: Ext.get(mchntForm.getEl()),
//											buttons: Ext.MessageBox.OK,
//											icon: 'message-success',
//											modal: true,
//											width: 300
//										});
//                            		}else{
//                            			showErrorMsg(err,mchntForm);
//                            		}
//                            		mchntForm.getForm().findField('clearType').enable();
//	                            	mchntForm.getForm().findField('settleAcct').enable();
//	                            	bt.enable();
//                                });
//                            }else{
//                            	 showErrorMsg('该类账户类型无需验证。',mchntForm);
//                            	 bt.enable();
//                            }
//                        }
//                    }]
//				},{
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'displayfield',
//						labelStyle: 'padding-left: 5px',
//						allowBlank: true,
//						fieldLabel: '账号验证户名',
//						maxLength: 20,	
//						vtype: 'isOverMax',
//						width:150,
//						id: 'verifyName'
//		        	}]
//				},{
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'displayfield',
//						labelStyle: 'padding-left: 5px',
//						allowBlank: true,
//						fieldLabel: '验证状态',
//						maxLength: 20,
//						vtype: 'isOverMax',
//						width:150,
//						id: 'verifySta'
//		        	}]
//				}
				 ,{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '商户开户行同城清算号',
						maxLength: 20,
						vtype: 'isOverMax',
						width:150,
						id: 'openStlno'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
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
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否自动清算',
						id: 'autoStlFlg',
						name: 'autoStlFlg',
						checked: true
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '退货返还手续费',
						id: 'feeBackFlg',
						name: 'feeBackFlg'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费类型',
						maxLength: 12,
						vtype: 'isOverMax',
						width:150,
						id: 'speSettleTp'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费档次',
						maxLength: 48,
						vtype: 'isOverMax',
						width:150,
						id: 'speSettleLv'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费描述',
						maxLength: 255,
						vtype: 'isOverMax',
						width:150,
						id: 'speSettleDs'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '入账凭单打印机构',
						maxLength: 13,
						vtype: 'isOverMax',
						width:150,
						id: 'printInstId',
						name: 'printInstId'
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
	       					fieldLabel: '计费代码*',
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
							text:'重置',
							id: 'resetbu',
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
							id: 'setup',
							width: 60,
							disabled: true,
							handler: function(){
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
							id: 'detailbu',
							width: 60,
							handler: function(){
								Ext.MessageBox.show({
									msg: '<font color=red>交易卡种</font>：指定执行该计费算法的交易卡种，优先选择单独配置的卡种，如没有配置则选择全部卡种。<br>' +
											'<font color=red>最低交易金额</font>：指定执行该计费算法的最低交易金额，如已配置最低交易金额为0和5000的两条计费算法信息，那么当交易金额在0-5000(含5000)时，执行最低交易金额为0的计费算法，大于5000时，执行最低交易金额为5000的计费算法。<br>' +
											'<font color=red>回佣类型</font>：指定该算法计算回佣值时的计算方式。<br>' +
											'<font color=red>回佣值</font>：当回佣类型为“按笔收费”时，回佣为回佣值所示金额(此时不需输入按比最低收费/按比最高收费)；当回佣类型为“按比例收费时”，回佣为当前 交易金额x回佣值(需满足最低和最高收费的控制)。<br>' +
											'<font color=red>按比最低收费/按比最高收费</font>：指定当回佣类型为“按比例收费”时的最低/最高收费金额。',
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
				title:'证书影像',
                id: 'images',
                frame: true,
                layout: 'border',
                items: [{
                	xtype: 'panel',
                	region: 'center',
        			items : dataview,
        			frame: true,
        			tbar: [{
        				xtype: 'button',
						text: '刷新',
						width: '80',
						id: 'view',
						handler:function() {
							storeImg.reload({
								params: {
									start: 0,
									imagesId: imagesId
								}
							});
						}
					},{
						xtype: 'button',
						width: '80',
						text: '上传',
						id: 'upload',
						handler:function() {
							hasUpload = "1";
							dialog.show();
						}
					}]
                }]
		    }]
        }],
        buttons: [{
            text: '保存',
            id: 'save',
            name: 'save',
            handler : function() {
            	subSave();
            }
        },{
            text: '重置',
            handler: function() {
            	hasSub = false;
            	checkIds = "T";
				mchntForm.getForm().reset();
			}
        }]
    });
    
    var checkIds = "T";
    function subSave(){
    	var btn = Ext.getCmp('save');
		var frm = mchntForm.getForm();
		hasSub = true;
		if (frm.isValid()) {
//			Ext.getCmp('verifyButton').enable();
			var flag = mchntForm.getForm().findField('clearType').getValue();
//			if(!verifyResult&&(flag == 'A'||flag == 'P')){
			if(false) {
				//额外验证
//	                Ext.getCmp('verifyButton').enable();
					showErrorMsg('保存商户信息之前请验证账户账号!',mchntForm);
			}else{
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
//						Ext.getCmp('verifyButton').disable();
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
		}
	}else{
		//自动切换到未通过验证的tab
		var finded = true;
		frm.items.each(function(f){
    		if(finded && !f.validate()){
    			var tab = f.ownerCt.ownerCt.id;
    			var tab2 = f.ownerCt.ownerCt.ownerCt.id;
    			if(tab2 == 'feeamt'){
    				 Ext.getCmp("tab").setActiveTab(tab2);
    			}
    			if(tab == 'basic' || tab == 'append' || tab == 'settle' || tab == 'feeamt'){
    				 Ext.getCmp("tab").setActiveTab(tab);
    			}
    			finded = false;
    		}
    	}
    );
	}}

    mchntForm.getForm().findField('acqInstId').setValue(BRHID);
    mchntForm.getForm().findField('signInstId').setValue(CUPBRHID);

    //为保证验证信息显示的正确，当切换tab时重新验证
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
});