Ext.onReady(function() {
	var _subsidyType2;
	var _openType2;
	var _ruleName2;
	var _startTime2;
	var _endTime2;
	var _ruleID2;
	function hideLabel(sId, bShow) {
		Ext.getCmp(sId).getEl().up('.x-form-item').child('.x-form-item-label')
				.setDisplayed(bShow);
		Ext.getCmp(sId).getEl().up('.x-panel').setDisplayed(bShow);
	}
	function fix2Length(str) {
		if ((str + "").length < 2) {
			str = "0" + str;
		}
		return str;
	}
	function fix6Length(str) {
		if ((str + "").length < 6) {
			str = "0" + str;
		}
		return str;
	}

	function getCode() {
		var date = new Date();
		return date.getFullYear() + "" + fix2Length(date.getMonth() + 1)
				+ fix2Length(date.getDate()) + fix2Length(date.getHours())
				+ fix2Length(date.getMinutes()) + fix2Length(date.getSeconds())
				+ fix6Length(Math.floor(Math.random() * 1000000));
	}
	function linkrender(val) {
		return "<a href='" + Ext.contextPath
				+ "/page/mchnt/T21504.jsp?ruleId=" + val + "'>" + val
				+ "</a>";
	}
	var subsidyStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=mchntSubsidyRuleInfo'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, 
		[ {name : 'ruleId',mapping : 'RULE_ID'},
		  {name : 'ruleName',mapping : 'RULE_NAME'},
		  {name : 'subsifyType',mapping : 'SUBSIDY_TYPE'}, 
		  {name : 'subsifyRule',mapping : 'SUBSIDY_RULE'},
		  {name : 'openType',mapping : 'OPEN_TYPE'}, 
		  {name : 'cardBin',mapping : 'CARD_BIN'}, 
		  {name : 'startTime',mapping : 'START_TIME'}, 
		  {name : 'endTime',mapping : 'END_TIME'},
		  {name : 'cardLinit',mapping : 'CARD_LIMIT'}, 
		  {name : 'termLimit',mapping : 'TERM_LIMIT'}, 
		  {name : 'mchtLimit',mapping : 'MCHT_LIMIT'}, 
		  {name : 'addTime',mapping : 'ADD_TIME'}, 
		  {name : 'modifyTime',mapping : 'MODIFY_TIME'}, 
		  {name : 'remark',mapping : 'REMARK'} 
		  ]),
		  autoLoad : true
	});
	var cbBox = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});
	cbBox.handleMouseDown = Ext.emptyFn;
	var subsidyColModel = new Ext.grid.ColumnModel([ 
	    cbBox, 
	    {header : '补贴规则ID',dataIndex : 'ruleId',width : 160,align : 'center',renderer : linkrender}, 
	    {header : '规则名称',dataIndex : 'ruleName',width : 140,align : 'center'},  
	    {header : '补贴类型',dataIndex : 'subsifyType',width : 90,align : 'center'}, 
	    {header : '补贴规则',dataIndex : 'subsifyRule',width : 230,align : 'center'},
	    {header : '开启方式',dataIndex : 'openType',width : 130,align : 'center'}, 
	    {header : '卡BIN',dataIndex : 'cardBin',width : 180,align : 'center'},
	    {header : '开始时间',dataIndex : 'startTime',width : 140,align : 'center'}, 
	    {header : '结束时间',dataIndex : 'endTime',width : 140,align : 'center'}, 
	    {header : '按卡限次',dataIndex : 'cardLinit',width : 80,align : 'center'}, 
	    {header : '按终端限次',dataIndex : 'termLimit',width : 80,align : 'center'},
	    {header : '按商户限次',dataIndex : 'mchtLimit',width : 80,align : 'center'}, 
		{header : '添加时间',dataIndex : 'addTime',width : 140,align : 'center'}, 
		{header : '修改时间',dataIndex : 'modifyTime',width : 140,align : 'center'}, 
		{header : '备注',dataIndex : 'remark',width : 140,align : 'center'} 
	]);

	var subsidyPanel = new Ext.TabPanel({
		activeTab : 0,
		height : 380,
		width : 750,
		frame : true,
		items : [ {
			title : '基本信息',
			id : 'infoNew1',
			layout : 'column',
			frame : true,
			items : [ {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '规则ID*',
					allowBlank : false,
					id : 'ruleID1',
					name : 'ruleIDNM1',
					disabled : true,
					width : 180,
					value : getCode(),
					readOnly : true,
				} ]
			},{
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'combo',
					fieldLabel : '补贴类型*',
					id : 'subsidyType1',
					hiddenName : 'subsidyTypeNM1',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField', 'displayField' ],
						data : [ [ '00', '满额补' ], [ '01', '按比例补' ] ]
					}),
					value : '00',
					width : 180,
					allowBlank : false,
					readOnly : false,
					listeners:{
                	   'select':function(){
                		   var type = Ext.getCmp("subsidyType1").getValue();
                		   if(type=='00'){
                			   Ext.getCmp("cardLimit1").reset();
                               Ext.getCmp("termLimit1").reset();
                               Ext.getCmp("mchtLimit1").reset();
                               hideLabel('subsidyRuleLimit1',false);
                			   hideLabel('subsidyRuleDist1',false);
                			   hideLabel('subsidyRuleFull1',true);
                   			   hideLabel('subsidyRuleFill1',true);
                   			   Ext.getCmp("subsidyRuleLimit1").setValue('0');
                   			   Ext.getCmp("subsidyRuleDist1").setValue('0');
                   			   Ext.getCmp("subsidyRuleFull1").reset();
                			   Ext.getCmp("subsidyRuleFill1").reset();
                		   }else if(type=='01'){
                			   Ext.getCmp("cardLimit1").reset();
                               Ext.getCmp("termLimit1").reset();
                               Ext.getCmp("mchtLimit1").reset();
                               hideLabel('subsidyRuleLimit1',true);
                			   hideLabel('subsidyRuleDist1',true);
                			   hideLabel('subsidyRuleFull1',false);
                   			   hideLabel('subsidyRuleFill1',false);
                   			   Ext.getCmp("subsidyRuleLimit1").reset();
                 			   Ext.getCmp("subsidyRuleDist1").reset();
                 			   Ext.getCmp("subsidyRuleFull1").setValue('0');
                 			   Ext.getCmp("subsidyRuleFill1").setValue('0');
                		   }else{
                			   Ext.getCmp("cardLimit1").reset();
                               Ext.getCmp("termLimit1").reset();
                               Ext.getCmp("mchtLimit1").reset();
                               hideLabel('subsidyRuleLimit1',false);
                			   hideLabel('subsidyRuleDist1',false);
                			   hideLabel('subsidyRuleFull1',true);
                   			   hideLabel('subsidyRuleFill1',true);
                   			   Ext.getCmp("subsidyRuleLimit1").setValue('0');
                 			   Ext.getCmp("subsidyRuleDist1").setValue('0');
                 			   Ext.getCmp("subsidyRuleFull1").reset();
                 			   Ext.getCmp("subsidyRuleFill1").reset();
                		   }
                		}
					}
				} ]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'combo',
					fieldLabel : '开启方式*',
					id : 'openType1',
					hiddenName : 'openTypeNM1',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField', 'displayField' ],
						data : [ [ '00', '依据商户开启' ], [ '01', '依据终端开启' ],['02', '依据卡BIN开启' ] ]
					}),
					value : '00',
					width : 180,
					allowBlank : false,
					readOnly : false
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '规则名称*',
					id : 'ruleName1',
					name : 'ruleNameNM1',
					maxLength : 50,
					readOnly : false,
					allowBlank : false,
					width : 180
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'numberfield',
					fieldLabel : '满*（单位:分）',
					width : 180,
					id : 'subsidyRuleFull1',
					name : 'subsidyRuleFullNM1',
					readOnly : false,
					allowBlank : false,
					regex : /^[0-9]{1,10}$/,
					regexText : '只能输入1-10位数字',
				//   hidden:true
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'numberfield',
					fieldLabel : '补*（单位:分）',
					width : 180,
					id : 'subsidyRuleFill1',
					name : 'subsidyRuleFillNM1',
					readOnly : false,
					allowBlank : false,
					regex : /^[0-9]{1,10}$/,
					regexText : '只能输入1-10位数字',
				//       hidden:true
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'numberfield',
					fieldLabel : '限额*（单位:分）',
					width : 180,
					id : 'subsidyRuleLimit1',
					name : 'subsidyRuleLimitNM1',
					readOnly : false,
					allowBlank : false,
					regex : /^[0-9]{1,10}$/,
					regexText : '只能输入1-10位数字',
				//       hidden:true
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'numberfield',
					fieldLabel : '折扣*(%)',
					width : 180,
					minValue : 0,
					maxValue : 99,
					minText : '最小为0',
					maxText : '最大为99',
					maxLength : 2,
					maxLengthText : '长度过长',
					id : 'subsidyRuleDist1',
					name : 'subsidyRuleDistNM1',
					readOnly : false,
					allowBlank : false,
					//            hidden:true,
					listeners : {
						render : function(field) {
							Ext.QuickTips.init();
							Ext.QuickTips.register({
								target : field.el,
								text : '填写90即按90%打折'
							})
						}
					}
				} ]
			},{
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'textarea',
					fieldLabel : '卡bin',
					id : 'cardBin1',
					name : 'cardBinNM1',
					width : '80%',
					height : 60,
					grow : true,
					listeners : {
						render : function(field) {
							Ext.QuickTips.init();
							Ext.QuickTips.register({
								target : field.el,
								text : '示例：111111,222222'
							})
						}
					}
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '开始日期*',
					format : 'Y-m-d',
					id : 'startTime1',
					name : 'startTimeNM1',
					anchor : '60%',
					vtype : 'daterange',
			//		minValue : nowDate,
					allowBlank : false,
					width : 180
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '截止日期*',
					format : 'Y-m-d',
					id : 'endTime1',
					name : 'endTimeNM1',
					vtype : 'daterange',
		//			minValue : nowDate,
					anchor : '60%',
					allowBlank : false,
					width : 180
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '备注',
					id : 'remark1',
					name : 'remarkNM1',
					width : 180,
					maxLength : 100
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'numberfield',
					fieldLabel : '按卡限次(不限填0)*',
					width : 180,
					id : 'cardLimit1',
					name : 'cardLimitNM1',
					readOnly : false,
					allowBlank : false,
					regex : /^[0-9]{1,4}$/,
					regexText : '只能输入1-4位数字'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'numberfield',
					fieldLabel : '按终端限次(不限填0)*',
					width : 180,
					id : 'termLimit1',
					name : 'termLimitNM1',
					readOnly : false,
					allowBlank : false,
					regex : /^[0-9]{1,4}$/,
					regexText : '只能输入1-4位数字'
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'numberfield',
					fieldLabel : '按商户限次(不限填0)*',
					width : 180,
					id : 'mchtLimit1',
					name : 'mchtLimitNM1',
					readOnly : false,
					allowBlank : false,
					regex : /^[0-9]{1,4}$/,
					regexText : '只能输入1-4位数字'
				} ]
			} ]
		} ]
	});
	/**************  营销补贴表单  *********************/
	var subsidyForm = new Ext.form.FormPanel({
		frame : true,
		height : 380,
		width : 750,
		labelWidth : 140,
		waitMsgTarget : true,
		layout : 'column',
		items : [ subsidyPanel ]
	});

	/***********  营销补贴窗口  *****************/
	var subsidyWin = new Ext.Window({
		title : '商户补贴规则维护',
		initHidden : true,
		header : true,
		frame : true,
		closable : false,
		modal : true,
		width : 750,
		autoHeight : true,
		layout : 'fit',
		items : [ subsidyForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'T201',
		resizable : false,
		buttons : [ {
			id : 'ensure1',
			text : '确定',
			handler : function() {
				if(subsidyForm.getForm().isValid()){
					var _openType1=Ext.getCmp("openType1").getValue();
					if(_openType1 == null || _openType1 == '' ){
						showErrorMsg("开启方式不能为空",grid);
						return false;
					}else if(_openType1 == '02'){
						//验证卡bin格式	
						var _cardBin1=Ext.getCmp("cardBin1").getValue();
						if(_cardBin1 == null || _cardBin1 == ''){
							showErrorMsg("卡bin不能为空",grid);
							return false;
						}
						var reg=/^((([0-9]{5})|([0-9]{6})|([0-9]{8})){1,})((,(([0-9]{5})|([0-9]{6})|([0-9]{8})))*)$/;
						if(!reg.test(_cardBin1)){
							showErrorMsg("卡bin格式不正确。(示例：111111,222222)",grid);
							return false;
						}
					}
					var _ruleName1=Ext.getCmp("ruleName1").getValue();
					if(_ruleName1 == null || _ruleName1 == '' ){
						showErrorMsg("规则名称不能为空",grid);
						return false;
					}
					var _endTime1=Ext.getCmp('endTime1').getValue(),_startTime1=Ext.getCmp('startTime1').getValue();
	            	if(_endTime1!=''&&_startTime1!=''&&_endTime1<_startTime1){
	            		showErrorMsg("请保证截止时间不小于起始时间",grid);
	    				return;
	            	}
	            	var _subsidyType1=Ext.getCmp('subsidyType1').getValue();
					if(_subsidyType1 == '00'){
						var _subsidyRuleFull1=Ext.getCmp('subsidyRuleFull1').getValue(),_subsidyRuleFill1=Ext.getCmp('subsidyRuleFill1').getValue();
						if(_subsidyRuleFull1<=_subsidyRuleFill1){
							showErrorMsg("请保证补助数额小于消费金额",grid);
		    				return;
						}
						var _fullLen1=_subsidyRuleFull1.toString().length,_fillLen1=_subsidyRuleFill1.toString().length;
						if(_fullLen1 > 12 || _fillLen1 > 12){
							showErrorMsg("满补数额长度过长,不能超出12位",grid);
		    				return;
						}
					}
					if(_subsidyType1 == '01'){
						var _subsidyRuleDist1=Ext.getCmp('subsidyRuleDist1').getValue();
						if(_subsidyRuleDist1> 99 || _subsidyRuleDist1 < 0){
							showErrorMsg("请保证折扣数小于100",grid);
		    				return;
						}
						var _limitLen1=Ext.getCmp('subsidyRuleLimit1').getValue().toString().length;
						if(_limitLen1 > 12){
							showErrorMsg("限额长度过长,不能超出12位",grid);
		    				return;
						}
						if(_limitLen1 < 0){
							showErrorMsg("限额不能为负值",grid);
		    				return;
						}
					}
					Ext.Ajax.requestNeedAuthorise({
						url: 'T21503Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(rsp,opt) {
							subsidyWin.hide();
                            var rspObj = Ext.decode(rsp.responseText);
                            if(rspObj.msg.indexOf("成功")>0){
                            	showSuccessMsg(rspObj.msg,grid); 
                            }else{
                            	showErrorMsg(rspObj.msg,grid);
                            }
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							subsidyForm.getForm().reset();
						},
						failure: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							showErrorMsg(rspObj.msg,grid);
						},
						params: {
							ruleID : Ext.getCmp("ruleID1").getValue(),
							subsidyType : Ext.getCmp("subsidyType1").getValue(),
							subsidyRuleFull : Ext.getCmp("subsidyRuleFull1").getValue(),
							subsidyRuleFill : Ext.getCmp("subsidyRuleFill1").getValue(),
							subsidyRuleLimit : Ext.getCmp("subsidyRuleLimit1").getValue(),
							subsidyRuleDist : Ext.getCmp("subsidyRuleDist1").getValue(),
							cardBin : Ext.getCmp("cardBin1").getValue(),
							startTime : Ext.getCmp("startTime1").getValue(),
							endTime : Ext.getCmp("endTime1").getValue(),
							remark : Ext.getCmp("remark1").getValue(),
							cardLimit : Ext.getCmp("cardLimit1").getValue(),
							termLimit : Ext.getCmp("termLimit1").getValue(),						
							mchtLimit : Ext.getCmp("mchtLimit1").getValue(),
							openType : Ext.getCmp("openType1").getValue(),
							ruleName : Ext.getCmp("ruleName1").getValue(),
							txnId: '21503',
							subTxnId: '01'
						}
					});
				}	
			}
		},{
			id : 'ensure2',
			text : '确定',
			handler : function() {
				if(subsidyForm.getForm().isValid()){
					var _openType1=Ext.getCmp("openType1").getValue();
					if(_openType1 == null || _openType1 == '' ){
						showErrorMsg("开启方式不能为空",grid);
						return false;
					}else if(_openType1 == '02'){
						//验证卡bin格式	
						var _cardBin1=Ext.getCmp("cardBin1").getValue();
						if(_cardBin1 == null || _cardBin1 == ''){
							showErrorMsg("卡bin不能为空",grid);
							return false;
						}
						var reg=/^((([0-9]{5})|([0-9]{6})|([0-9]{8})){1,})((,(([0-9]{5})|([0-9]{6})|([0-9]{8})))*)$/;
						if(!reg.test(_cardBin1)){
							showErrorMsg("卡bin格式不正确。(示例：111111,222222)",grid);
							return false;
						}
					}
					var _ruleName1=Ext.getCmp("ruleName1").getValue();
					if(_ruleName1 == null || _ruleName1 == '' ){
						showErrorMsg("规则名称不能为空",grid);
						return false;
					}
					var _endTime1=Ext.getCmp('endTime1').getValue(),_startTime1=Ext.getCmp('startTime1').getValue();
	            	if(_endTime1!=''&&_startTime1!=''&&_endTime1<_startTime1){
	            		showErrorMsg("请保证截止时间不小于起始时间",grid);
	    				return;
	            	}
	            	var _subsidyType1=Ext.getCmp('subsidyType1').getValue();
					if(_subsidyType1 == '00'){
						var _subsidyRuleFull1=Ext.getCmp('subsidyRuleFull1').getValue(),_subsidyRuleFill1=Ext.getCmp('subsidyRuleFill1').getValue();
						if(_subsidyRuleFull1<=_subsidyRuleFill1){
							showErrorMsg("请保证补助数额小于消费金额",grid);
		    				return;
						}
						var _fullLen1=_subsidyRuleFull1.toString().length,_fillLen1=_subsidyRuleFill1.toString().length;
						if(_fullLen1 > 12 || _fillLen1 > 12){
							showErrorMsg("满补数额长度过长,不能超出12位",grid);
		    				return;
						}
					}
					if(_subsidyType1 == '01'){
						var _subsidyRuleDist1=Ext.getCmp('subsidyRuleDist1').getValue();
						if(_subsidyRuleDist1> 99 || _subsidyRuleDist1 < 0){
							showErrorMsg("请保证折扣数小于100",grid);
		    				return;
						}
						var _limitLen1=Ext.getCmp('subsidyRuleLimit1').getValue().toString().length;
						if(_limitLen1 > 12){
							showErrorMsg("限额长度过长,不能超出12位",grid);
		    				return;
						}
						if(_limitLen1 < 0){
							showErrorMsg("限额不能为负值",grid);
		    				return;
						}
					}
					Ext.Ajax.requestNeedAuthorise({
						url: 'T21503Action.asp?method=update',
						waitMsg: '正在提交，请稍后......',
						success: function(rsp,opt) {
							subsidyWin.hide();
                            var rspObj = Ext.decode(rsp.responseText);
                            if(rspObj.msg.indexOf("成功")>0){
                            	showSuccessMsg(rspObj.msg,grid); 
                            }else{
                            	showErrorMsg(rspObj.msg,grid);
                            }
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							subsidyForm.getForm().reset();
						},
						failure: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							showErrorMsg(rspObj.msg,grid);
						},
						params: {
							ruleID : Ext.getCmp("ruleID1").getValue(),
							subsidyType : Ext.getCmp("subsidyType1").getValue(),
							subsidyRuleFull : Ext.getCmp("subsidyRuleFull1").getValue(),
							subsidyRuleFill : Ext.getCmp("subsidyRuleFill1").getValue(),
							subsidyRuleLimit : Ext.getCmp("subsidyRuleLimit1").getValue(),
							subsidyRuleDist : Ext.getCmp("subsidyRuleDist1").getValue(),
							cardBin : Ext.getCmp("cardBin1").getValue(),
							startTime : Ext.getCmp("startTime1").getValue(),
							endTime : Ext.getCmp("endTime1").getValue(),
							remark : Ext.getCmp("remark1").getValue(),
							cardLimit : Ext.getCmp("cardLimit1").getValue(),
							termLimit : Ext.getCmp("termLimit1").getValue(),						
							mchtLimit : Ext.getCmp("mchtLimit1").getValue(),
							openType : Ext.getCmp("openType1").getValue(),
							ruleName : Ext.getCmp("ruleName1").getValue(),
							txnId: '21503',
							subTxnId: '03'
						}
					});
				}
			}
		}, {
			id : 'reset1',
			text : '重置',
			handler : function() {
				subsidyForm.getForm().reset();
			}
		}, {
			text : '关闭',
			handler : function() {
				subsidyWin.hide(grid);
				subsidyForm.getForm().reset();
			}
		} ]
	});

	var addMenu = {
		text : '添加',
		width : 85,
		iconCls : 'add',
		handler : function() {
			subsidyWin.show();
			subsidyWin.center();
			subsidyWin.setTitle('商户补贴规则添加');
			Ext.getCmp('ensure1').show().enable();
			Ext.getCmp('ensure2').hide().disable();
			hideLabel('ruleID1',false);
			Ext.getCmp('reset1').show().enable();
			hideLabel('subsidyRuleLimit1',false);
			hideLabel('subsidyRuleDist1',false);
			hideLabel('subsidyRuleFull1',true);
			hideLabel('subsidyRuleFill1',true);
			Ext.getCmp("subsidyRuleLimit1").setValue('0');
			Ext.getCmp("subsidyRuleDist1").setValue('0');
			Ext.getCmp("subsidyRuleFull1").reset();
			Ext.getCmp("subsidyRuleFill1").reset();
		}
	};
	subsidyStore.on('beforeload', function(){
		Ext.select('td.x-grid3-cell-first').setStyle({ visibility:'hidden'});
		Ext.apply(this.baseParams, {
			start: 0,
			subsidyType : _subsidyType2,
			openType : _openType2,
			ruleId : _ruleID2,
			ruleName : _ruleName2,
			startTime : _startTime2,
			endTime : _endTime2
		});
	});	
	 //查询
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 570,
        autoHeight: true,
        labelWidth: 100,
        items: [{
			columnWidth : 1,
			layout : 'form',
			labelWidth: 80,
			items : [ {
				xtype : 'basecomboselect',
				fieldLabel : '补贴类型',
				id : 'subsidyType2',
				hiddenName : 'subsidyTypeNM2',
				store : new Ext.data.ArrayStore({
					fields : [ 'valueField', 'displayField' ],
					data : [ [ '00', '满额补' ], [ '01', '按比例补' ] ]
				}),
				width : 180,
				allowBlank : true,
				readOnly : false,				
			} ]
		},{
			columnWidth : 1,
			layout : 'form',
			labelWidth: 80,
			items : [{
				xtype : 'basecomboselect',
				fieldLabel : '开启方式',
				id : 'openType2',
				hiddenName : 'openTypeNM2',
				store : new Ext.data.ArrayStore({
					fields : [ 'valueField', 'displayField' ],
					data : [ [ '00', '依据商户开启' ], [ '01', '依据终端开启' ],['02', '依据卡BIN开启' ] ]
				}),
				width : 180,
				allowBlank : true,
				readOnly : false
			}]
		},{
			columnWidth: 1,
			layout: 'column',
			labelWidth: 80,
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '开始日期',
					format : 'Y-m-d',
					id : 'startTime2',
					name : 'startTimeNM2',
			//		anchor : '90%',
					vtype : 'daterange',
			//		minValue : nowDate,
					allowBlank : true,
					width : 180
				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '截止日期',
					format : 'Y-m-d',
					id : 'endTime2',
					name : 'endTimeNM2',
					vtype : 'daterange',
		//			minValue : nowDate,
			//		anchor : '90%',
					allowBlank : true,
					width : 180
				} ]
			}]
		},{
			columnWidth: 1,
			layout: 'column',
			labelWidth: 80,
			items : [{
				columnWidth : .5,
				layout : 'form',
				labelWidth: 80,
				items : [ {
					xtype : 'textfield',
					fieldLabel : '规则ID',
					id : 'ruleID2',
					name : 'ruleIDNM2',
			//		readOnly : false,
					allowBlank : true,
					width : 180
				} ]
			},{
				columnWidth : .5,
				layout : 'form',
				labelWidth: 80,
				items : [ {
					xtype : 'textfield',
					fieldLabel : '规则名称',
					id : 'ruleName2',
					name : 'ruleNameNM2',
			//		readOnly : false,
					allowBlank : true,
					width : 180
				} ]
			}]
		}],
        buttons: [{
            text: '查询',
            handler: function() 
            {
            	if(topQueryPanel.getForm().isValid()){
            		var endtime=Ext.getCmp('endTime2').getValue(),starttime=Ext.getCmp('startTime2').getValue();
	            	if(endtime!=''&&starttime!=''&&endtime<starttime){
	            		showErrorMsg("请保证截止时间不小于起始时间",grid);
	    				return;
	            	} 
	            	_subsidyType2=Ext.getCmp('subsidyType2').getValue();
	            	_openType2=Ext.getCmp('openType2').getValue();
	            	_ruleID2=Ext.getCmp('ruleID2').getValue();
	            	_ruleName2=Ext.getCmp('ruleName2').getValue();
	            	_startTime2=Ext.getCmp('startTime2').getValue();
	            	_endTime2=Ext.getCmp('endTime2').getValue();
	            	subsidyStore.load();
	            	queryWin.hide();
	            	grid.getTopToolbar().items.items[2].disable();
	                grid.getTopToolbar().items.items[3].disable();
            	}	
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });

   var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 570,
        autoHeight: true,
        items: [topQueryPanel],
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
        },{
            id: 'close',
            handler: function(event,toolEl,panel,tc) { 
                queryWin.hide();
                topQueryPanel.getForm().reset();
            },
            qtip: '关闭'
        }]
    });
	var queryMenu = {
		text : '录入查询条件',
		width : 85,
		id : 'query',
		iconCls : 'query',
		handler : function() {
			queryWin.show();
			topQueryPanel.getForm().reset();
		}
	};

	var editMenu = {
		text : '修改',
		width : 85,
		iconCls : 'edit',
		disabled : true,
		handler : function() {
			selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            Ext.Ajax.request({
				url : 'T21503Action_getData.asp',
				params : {
					ruleID : selectedRecord.get('ruleId')
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						subsidyWin.show();
						subsidyWin.center();
						subsidyWin.setTitle('商户补贴规则维护');
			            Ext.getCmp('ensure1').hide().disable();
			            Ext.getCmp('ensure2').show().enable();
			            Ext.getCmp('reset1').hide().disable();
			            hideLabel('ruleID1',true);
			            Ext.getCmp('ruleID1').setValue(rspObj.msg.ruleID);
			            Ext.getCmp('subsidyType1').setValue(rspObj.msg.subsidyType);
			            Ext.getCmp('openType1').setValue(rspObj.msg.openType);
			            if(rspObj.msg.subsidyType == '00'){
			            	hideLabel('subsidyRuleLimit1',false);
              			   	hideLabel('subsidyRuleDist1',false);
              			   	hideLabel('subsidyRuleFull1',true);
                 			hideLabel('subsidyRuleFill1',true);
                 			Ext.getCmp("subsidyRuleLimit1").setValue('0');
                 			Ext.getCmp("subsidyRuleDist1").setValue('0');
                 			Ext.getCmp("subsidyRuleFull1").setValue(rspObj.msg.subsidyRuleFull);
              			   	Ext.getCmp("subsidyRuleFill1").setValue(rspObj.msg.subsidyRuleFill);
			            }else if(rspObj.msg.subsidyType == '01'){
			            	hideLabel('subsidyRuleLimit1',true);
              			   	hideLabel('subsidyRuleDist1',true);
              			   	hideLabel('subsidyRuleFull1',false);
              			   	hideLabel('subsidyRuleFill1',false);
              			   	Ext.getCmp("subsidyRuleLimit1").setValue(rspObj.msg.subsidyRuleLimit);
              			   	Ext.getCmp("subsidyRuleDist1").setValue(rspObj.msg.subsidyRuleDist);
              			   	Ext.getCmp("subsidyRuleFull1").setValue('0');
              			   	Ext.getCmp("subsidyRuleFill1").setValue('0');
			            }
			            Ext.getCmp('ruleName1').setValue(rspObj.msg.ruleName);
			            Ext.getCmp('cardBin1').setValue(rspObj.msg.cardBin);
			            Ext.getCmp('remark1').setValue(rspObj.msg.remark);
			            Ext.getCmp('cardLimit1').setValue(rspObj.msg.cardLimit);
			            Ext.getCmp('termLimit1').setValue(rspObj.msg.termLimit);
			            Ext.getCmp('mchtLimit1').setValue(rspObj.msg.mchtLimit);
			            Ext.getCmp('startTime1').setValue(rspObj.msg.startTime );
			            Ext.getCmp('endTime1').setValue(rspObj.msg.endTime );
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
				}
			});

		}
	};

	var delMenu = {//不提供删除
		text : '删除',
		width : 85,
		iconCls : 'delete',
		disabled : true,
		handler : function() {
			selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }  
            showConfirm('确定要删除该规则吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在提交信息，请稍后......');
                    Ext.Ajax.requestNeedAuthorise({
                        url: 'T21503Action.asp?method=delete',
                        params: {
                        	ruleID: selectedRecord.get('ruleId'),
                            txnId: '21503',
                            subTxnId: '02'
                        },
                        success: function(rsp,opt) {
                            var rspObj = Ext.decode(rsp.responseText);
                            
                            if(rspObj.success) {
                                showSuccessMsg(rspObj.msg,grid);
                                subsidyStore.reload();
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                        }
                    });
                    grid.getTopToolbar().items.items[2].disable();
                    grid.getTopToolbar().items.items[3].disable();
                    hideProcessMsg();
                }
            });
		}
	};

	var menuArr = new Array();
	menuArr.push(addMenu); //[0]新增
	menuArr.push(queryMenu); //[1]查询
	menuArr.push(editMenu); //[2]修改
	menuArr.push(delMenu); //[7]不提供删除

	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title : '商户补贴规则',
		iconCls : 'T201',
		region : 'center',
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		store : subsidyStore,
		sm : cbBox,
		cm : subsidyColModel,
		clicksToEdit : true,
		forceValidation : true,
		tbar : menuArr,
		renderTo : Ext.getBody(),
		loadMask : {
			msg : '正在加载商户信息列表......'
		},
		bbar : new Ext.PagingToolbar({
			store : subsidyStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});
	 //工具按钮可用性
    grid.getSelectionModel().on({
        'rowselect': function() {
            //行高亮
            Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
            // 根据商户状态判断哪个编辑按钮可用
            rec = grid.getSelectionModel().getSelected();
            if(rec != null) {
                grid.getTopToolbar().items.items[2].enable();
                grid.getTopToolbar().items.items[3].enable();
            } else {
                grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[3].disable();
            }

        }
    });
	//渲染到页面
	var mainUI = new Ext.Viewport({
		layout : 'border',
		renderTo : Ext.getBody(),
		items : [ grid ]
	});
});