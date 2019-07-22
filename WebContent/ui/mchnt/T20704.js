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
            width: 140,
            editor: {
					xtype: 'basecomboselect',
			        store: flagStore,
					id: 'idflagcom',
					hiddenName: 'flagcom',
					width: 250
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
                    decimalPrecision:3,
                    maxValue: 100000
                })
        },{
            header: '按笔最低收费(元)',
            dataIndex: 'minFee',
            width: 120,
            sortable: true,
            editor: new fm.NumberField({
                    allowNegative: false,
//                    decimalPrecision:3,
                    decimalPrecision:4,
                    maxValue: 100000
                })
        },{
            header: '按笔最高收费(元)',
            dataIndex: 'maxFee',
            width: 120,
            sortable: true,
            editor: new fm.NumberField({
                    allowNegative: false,
//                    decimalPrecision:3,
                    decimalPrecision:4,
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
						xtype: 'textnotnull',
						fieldLabel: '计费代码*',
						labelStyle: 'padding-left: 5px',
						maxLength:8,
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
	        	columnWidth: 5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '计费名称*',
						maxLength:60,
						id: 'discNm',
						width: 360
		        	}]
	        	}]
			}]
		},{
			xtype: 'panel',
			height: 200,
			layout: 'border',
			items: [grid]
		},{
			xtype: 'panel',
			title: '配置说明',
			height: 200,
			header: true,
			frame: true,
			html: '<font color=red>第一步</font>：确定交易类型，每个交易类型都需要配置；<font color=red>第二步</font>：确定交易卡种，一次性选择全卡种或分四次选择不同卡种；<br>' +
				  '<font color=red>第三步</font>：确定手续费计费方式，分每笔按固定金额，按比例，按比例保底，按比例封顶，按比例保底加封顶。<br>' +
				  '<font color=red>第四步</font>：具体配置指定卡种范围指定手续费计费方式的参数<br>' +
				  '&nbsp&nbsp<font color=green>1)</font>：按固定金额方式：在“值”一栏填写手续费收取金额，如：0.30元/笔输入0.30；<br>'+
				  '&nbsp&nbsp<font color=green>2)</font>：按比例方式：在“值”一栏填写手续费占交易金额百分比，如0.78%输入0.78；<br>'+
				  '&nbsp&nbsp<font color=green>3)</font>：按比例加固定金额限制方式：<br>'+
				  '&nbsp&nbsp&nbsp&nbsp<font color=gray>a</font>：按比例保底：在“值”一栏填写手续费占交易金额百分比，在“按笔最低收费”一栏填写保底金额，如：0.3%保底1.20元，即在“值”项输入0.3、在“按笔最低收费”项输入1.2；<br>'+
				  '&nbsp&nbsp&nbsp&nbsp<font color=gray>b</font>：按比例封顶：在“值”一栏填写手续费占交易金额百分比，在“按笔最高收费”一栏填写封顶金额，如：1.228%封顶80元，即在“值”项输入1.228、在“按笔最高收费”项输入80；<br>'+
				  '&nbsp&nbsp&nbsp&nbsp<font color=gray>c</font>：按比例封顶加保底：在“值”一栏填写手续费占交易金额百分比，在“按笔最低收费”和“按笔最高收费”一栏分别填写保底和封顶金额，如：1.228%保底20封顶80元，即在“值”项输入1.228、在“按笔最低收费”项输入20、在“按笔最高收费”项输入80。<br>'
		}]
	});

	var groupWin = new Ext.Window({
		title: '计费算法维护（固话POS）',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 820,
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
					var txnNumArrayTemp = new Array();
					for(var i=0;i<store.getCount();i++){
//						grid.getSelectionModel().selectRow(i);
//						var re = grid.getSelectionModel().getSelected();
						
						var re = store.getAt(i);
						if(re.data.txnNum == null||re.data.txnNum==''){showMsg("第" + (i+1) + "行的[交易类型]不能为空",grid);return;}
						if(re.data.cardType == null||re.data.cardType==''){showMsg("第" + (i+1) + "行的[交易卡种]不能为空",grid);return;}
						if(re.data.flag == null){showMsg("第" + (i+1) + "行的[手续费类型]不能为空",grid);return;}
						if(re.data.feeValue == null){showMsg("第" + (i+1) + "行的[值]不能为空",grid);return;}
                        
						//把所有的记录中的卡种类型放进数组txnNumArrayTmp中
						txnNumArrayTemp[i]=re.data.txnNum+","+re.data.cardType;
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
							cardType:re.data.cardType,
							flag: re.data.flag,
							feeValue: re.data.feeValue,
							minFee: re.data.minFee,
							maxFee: re.data.maxFee
						};
						dataArray.push(data);
					} 
					//判断交易和卡种的合法性
					var array1=new Array();
					var array2=new Array();
					var array3=new Array();
					var flag=checkInfo(txnNumArrayTemp,array1,array2,array3);
					if(flag=='1'||flag=='2'||flag=='3'||flag=='4'||flag=='5'||flag=='6'){
						return;
					}
					//判断卡种，如果没有”全部卡种“，而且其他四种卡种也没配置全，就不通过
//					if(!validTxnNum(txnNumArrayTmp)) {
//						showMsg("如果您没有配置{全部卡种},请配置全其他4种卡种,且同一卡种不能重复配置计费信息",grid);
//						return;
//					}else
					//判断卡种，如果配置了”全部卡种“，而且其他四种卡种不能配置，就不通过
//					if(validateTxnNum2(txnNumArrayTmp)){
//						showMsg("如果您配置了{全部卡种},不能配置其他4种任何一种，且同一卡种不能重复配置计费信息",grid);
//						return;
//					}
					
					
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
							mchtFlag:'1',
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
	 function checkInfo(txnNumArray,array1,array2,array3){
		    var count1=0;
		    var count2=0;
		    var count3=0;
	    	for(var x=0;x<txnNumArray.length;x++){
	    		if(txnNumArray[x].split(',')[0]=='1301'){
	    			array1[count1]=txnNumArray[x].split(',')[1];
	    			count1++;
	    		}
	    		if(txnNumArray[x].split(',')[0]=='1501'){
	    			array2[count2]=txnNumArray[x].split(',')[1];
	    			count2++;
	    		}
	    		if(txnNumArray[x].split(',')[0]=='1801'){
	    			array3[count3]=txnNumArray[x].split(',')[1];
	    			count3++;
	    		}
	    	}
            var flag;
			//判断卡种，如果没有”全部卡种“，而且其他四种卡种也没配置全，就不通过
			if(!validTxnNum(array1)) {
				showMsg("商户付款交易，如果您没有配置{全部卡种},请配置全其他4种卡种,且同一卡种不能重复配置计费信息",grid);
				flag='1';
				return flag;
			}else
			//判断卡种，如果配置了”全部卡种“，而且其他四种卡种不能配置，就不通过
			if(validateTxnNum2(array1)){
				showMsg("商户付款交易，如果您配置了{全部卡种},不能配置其他4种任何一种，且同一卡种不能重复配置计费信息",grid);
				flag='2';
				return flag;
			}else{
				flag='0';
			}
			if(flag=='0'){
			if(flag!='1'||flag!='2'||flag!='5'||flag!='6'){
				//判断卡种，如果没有”全部卡种“，而且其他四种卡种也没配置全，就不通过
				if(!validTxnNum(array2)) {
					showMsg("商户收款交易，如果您没有配置{全部卡种},请配置全其他4种卡种,且同一卡种不能重复配置计费信息",grid);
					flag='3';
					return flag;
				}else
				//判断卡种，如果配置了”全部卡种“，而且其他四种卡种不能配置，就不通过
				if(validateTxnNum2(array2)){
					showMsg("商户收款交易，如果您配置了{全部卡种},不能配置其他4种任何一种，且同一卡种不能重复配置计费信息",grid);
					flag='4';
					return flag;
				}else{
					flag='0';
				}
			}
			}
			if(flag=='0'){
			if(flag!='1'||flag!='2'||flag!='3'||flag!='4'){
				//判断卡种，如果没有”全部卡种“，而且其他四种卡种也没配置全，就不通过
				if(!validTxnNum(array3)) {
					showMsg("指定账户圈存交易，如果您没有配置{全部卡种},请配置全其他4种卡种,且同一卡种不能重复配置计费信息",grid);
					flag='5';
					return flag;
				}else
				//判断卡种，如果配置了”全部卡种“，而且其他四种卡种不能配置，就不通过
				if(validateTxnNum2(array3)){
					showMsg("指定账户圈存交易，如果您配置了{全部卡种},不能配置其他4种任何一种，且同一卡种不能重复配置计费信息",grid);
					flag='6';
					return flag;
				}else{
					flag='0';
				}
			}
			}
	    }
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
	
	//validTxnNum方法用来验证“交易卡种”，txnNumArray为数组Array类型
	//如果txnNumArray中有4种卡种其中一种，也返回true
	//否则，返回false
	function validateTxnNum2(txnNumArray){
		var count = 0;
//		var flag = false ;
		for(var i=0; i<txnNumArray.length; i++) {
			if(txnNumArray[i] == "00"){
				for(var j=0; j<txnNumArray.length; j++){
					if(txnNumArray[j] == "01" || txnNumArray[j] == "02"||txnNumArray[j] == "03"||txnNumArray[j] == "04"){
						return true;
					}
					for(var k=0; k<i; k++) {
						if(txnNumArray[k] == txnNumArray[i]){
							return true;
						} 
					}
					count = 4;
				}
			}else{
				for(var j=0; j<i; j++) {
					if(txnNumArray[j] == txnNumArray[i]){
						return true;
					} 
				}
				count++;
			}
		}
		if(count ==4) {
			return false;
		}else {
			return true;
		}
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
			height: 200,
			header: true,
			frame: true,
			html: '<font color=red>第一步</font>：确定交易类型，每个交易类型都需要配置；<font color=red>第二步</font>：确定交易卡种，一次性选择全卡种或分四次选择不同卡种；<br>' +
				  '<font color=red>第三步</font>：确定手续费计费方式，分每笔按固定金额，按比例，按比例保底，按比例封顶，按比例保底加封顶。<br>' +
				  '<font color=red>第四步</font>：具体配置指定卡种范围指定手续费计费方式的参数<br>' +
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
		width: 820,
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
    		feeForm.getForm().findField("discCd").enable();
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
			showConfirm('确定要删除该计费算法信息吗？计费代码：' + discCd,grid,function(bt) {
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
			editable: true,
			width:260,
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
	
	
	menuArr.push(addMenu);     //[0]
	menuArr.push('-'); 	       //[1]
	menuArr.push(editMenu);    //[2]
	menuArr.push('-');         //[3]
	menuArr.push(delMenu);     //[4]
	menuArr.push('-');         //[5]
	menuArr.push(detailMenu);  //[6]
	menuArr.push('-');         //[7]
	menuArr.push(queryCondition);  //[8]
	
	


	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfDiscCd2'
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
		title: '计费算法维护（固话POS）',
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
		gridPanel.getTopToolbar().items.items[2].disable();
		gridPanel.getTopToolbar().items.items[4].disable();
		gridPanel.getTopToolbar().items.items[6].disable();
	}); 
	
	var rec;
	
	gridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel.getView().getRow(gridPanel.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = gridPanel.getSelectionModel().getSelected();
			if(null != rec){
				gridPanel.getTopToolbar().items.items[2].enable();
				gridPanel.getTopToolbar().items.items[4].enable();
				gridPanel.getTopToolbar().items.items[6].enable();
			}else{
				gridPanel.getTopToolbar().items.items[2].disable();
				gridPanel.getTopToolbar().items.items[4].disable();
				gridPanel.getTopToolbar().items.items[6].enable();
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