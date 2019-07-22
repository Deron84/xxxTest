/*
 * System Support
 * 
 */

// 商户MCC数据集
var mchntMccStore = new Ext.data.JsonStore({
	fields : [ 'valueField', 'displayField' ],
	root : 'data'
});
// 报表类型
var reportType = new Ext.form.ComboBox({
	fieldLabel : '报表类型',
	store : new Ext.data.ArrayStore({
		fields : [ 'valueField', 'displayField' ],
		data : [ [ 'EXCEL', 'Excel 格式' ], [ 'PDF', 'PDF 格式' ] ]
	}),
	reader : new Ext.data.ArrayReader(),
	hiddenName : 'reportType',
	allowBlank : false,
	blankText : '请选择报表格式',
	value : 'EXCEL'
});
/*
 * 时间选择TimeFiled类，继承自ComboBox xtype: 'timefieldsp'
 */
Ext.form.TimeFieldSP = Ext
		.extend(
				Ext.form.ComboBox,
				{
					emptyText : '请选择',
					minValue : null,
					maxValue : null,
					minText : "输入的时间需小于或等于 {0}",
					maxText : "输入的时间需大于或等于 {0}",
					invalidText : "{0} 不是一个有效的时间",
					format : "g:i A",
					altFormats : "g:ia|g:iA|g:i a|g:i A|h:i|g:i|H:i|ga|ha|gA|h a|g a|g A|gi|hi|gia|hia|g|H",
					increment : 15,
					mode : 'local',
					triggerAction : 'all',
					typeAhead : false,
					initDate : '1/1/2008',
					initDateFormat : 'j/n/Y',

					initComponent : function() {
						if (typeof this.minValue == "string") {
							this.minValue = this.parseDate(this.minValue);
						}
						if (typeof this.maxValue == "string") {
							this.maxValue = this.parseDate(this.maxValue);
						}
						if (!this.store) {
							var min = this.parseDate(this.minValue)
									|| new Date(this.initDate).clearTime();
							var max = this.parseDate(this.maxValue)
									|| new Date(this.initDate).clearTime().add(
											'mi', (24 * 60) - 1);
							var times = [];
							while (min.dateFormat(this.format) <= max
									.dateFormat(this.format)
									&& min.dateFormat(this.format) < min.add(
											'mi', this.increment).dateFormat(
											this.format)) {
								times.push(min.dateFormat(this.format));
								min = min.add('mi', this.increment);
							}
							this.store = times;
						}
						Ext.form.TimeFieldSP.superclass.initComponent
								.call(this);
					},
					getValue : function() {
						var v = Ext.form.TimeFieldSP.superclass.getValue
								.call(this);
						return this.formatDate(this.parseDate(v)) || '';
					},
					setValue : function(value) {
						return Ext.form.TimeFieldSP.superclass.setValue.call(
								this, this.formatDate(this.parseDate(value)));
					},
					validateValue : Ext.form.DateField.prototype.validateValue,
					parseDate : Ext.form.DateField.prototype.parseDate,
					formatDate : Ext.form.DateField.prototype.formatDate,
					beforeBlur : function() {
						var v = this.parseDate(this.getRawValue());
						if (v) {
							this.setValue(v.dateFormat(this.format));
						}
						Ext.form.TimeFieldSP.superclass.beforeBlur.call(this);
					}
				});
Ext.reg('timefieldsp', Ext.form.TimeFieldSP);

/*
 * 定义Store组件 通用combo组件
 * 
 * xtype: 'basecomboselect'
 */
Ext.namespace('Ext.ux2');
Ext.ux2.baseComboStore = Ext.extend(Ext.data.Store, {
	proxy : new Ext.data.HttpProxy({
		url : 'baseSelect.asp'
	}),
	reader : new Ext.data.JsonReader({
		root : 'data'
	}, [ {
		name : 'valueField'
	}, {
		name : 'displayField'
	} ]),
	autoLoad : true
});

Ext.ux2.baseCombo = Ext.extend(Ext.form.ComboBox, {
	initComponent : function() {
		if (!this.store) {
			var combo = this;
			var basePare = this.baseParams;
			this.store = new Ext.ux2.baseComboStore({
				baseParams : {
					ID : basePare
				},
				listeners : {
					load : function() {
						if (combo.getValue() != '') {
							combo.setValue(combo.getValue());
						}
					}
				}
			});
		}
		if (null != this.hiddenName && null == this.id) {
			this.id = 'id' + this.hiddenName;
		}
		if (!this.allowBlank) {
			var label = this.fieldLabel;
			if (label.indexOf("*") != -1) {
				label = label.substring(0, label.length - 1);
			}
			this.blankText = '请选择' + label;
		}
		Ext.ux2.baseCombo.superclass.initComponent.call(this);
	},
	displayField : 'displayField',
	valueField : 'valueField',
	mode : 'local',
	emptyText : '请选择',
	triggerAction : 'all',
	forceSelection : true,
	selectOnFocus : true,
	editable : false,
	lazyRender : false,
	typeAhead : false,
	store : null,
	listeners : {
		// 修正第一次有可能获取不到数据的问题
		'focus' : function() {
			if (this.store.getTotalCount() == 0) {
				this.store.reload();
			}
		}
	}
});
Ext.reg('basecomboselect', Ext.ux2.baseCombo);

/*
 * 通用textfield非空组件
 * 
 * xtype: 'basecomboselect'
 */
Ext.ux2.textfieldNotNull = Ext.extend(Ext.form.TextField, {
	initComponent : function() {
		if (null != this.id && null == this.name) {
			this.name = this.id;
		}
		if (!this.allowBlank) {
			var label = this.fieldLabel;
			if (label.indexOf("*") != -1) {
				label = label.substring(0, label.length - 1);
			}
			this.blankText = '请输入' + label;
		}
		Ext.ux2.textfieldNotNull.superclass.initComponent.call(this);
	},
	allowBlank : false,
	id : null
});
Ext.reg('textnotnull', Ext.ux2.textfieldNotNull);

// 返回radio value
function getRadioChecked(name) {
	var checks = Ext.getDom(Ext.getDoc()).getElementsByName(name);
	for (var i = 0; i < checks.length; i++) {
		if (checks[i].checked) {
			return checks[i].value;
		}
	}
}

/*
 * 可用于远程转译字段的工具
 * 
 * 使用： getRemoteTrans(value,id) 具体执行方法见RemoteTransMethod.java,其中方法名=ID
 * 
 */
var TransRecord = new Ext.data.Record.create([ {
	name : 'index',
	type : 'string'
}, {
	name : 'trans',
	type : 'string'
} ]);
var TransData = [ [ 'basic', 'basic' ] ];
var TransStore = new Ext.data.Store({
	proxy : new Ext.data.MemoryProxy(TransData),
	reader : new Ext.data.ArrayReader({
		idProperty : 'index'
	}, TransRecord)
});
TransStore.load();
function getRemoteTrans(val, id) {
	var midVar2 = id + val;
	var midVar = TransStore.findExact('index', id + val);
	if (TransStore.findExact('index', id + val) < 0) {
		dwr.engine.setAsync(false);
		RemoteTransDWR.getTrans(id, val, RemoteBack);
		dwr.engine.setAsync(true);
	}
	var midVar3 = TransStore.getAt(TransStore.findExact('index', id + val))
			.get('trans');
	return TransStore.getAt(TransStore.findExact('index', id + val)).get(
			'trans');
}

function RemoteBack(ret) {
	for (key in ret) {
		TransStore.add(new TransRecord({
			index : key,
			trans : ret[key]
		}));
	}
}

/*
 * 通过Store获取内容后显示
 */
Ext.form.ComboForDispaly = Ext.extend(Ext.form.Field, {
	validationEvent : false,
	validateOnBlur : false,
	defaultAutoCreate : {
		tag : "div"
	},
	fieldClass : "x-form-display-field",
	htmlEncode : false,
	baseParams : null,
	initEvents : Ext.emptyFn,
	store : null,
	keyValue : null,
	hiddenName : null,
	initComponent : function() {
		this.name = this.hiddenName;
		Ext.form.ComboForDispaly.superclass.initComponent.call(this);
	},
	isValid : function() {
		return true;
	},
	validate : function() {
		return true;
	},
	getRawValue : function() {
		var v = this.rendered ? this.el.dom.innerHTML : Ext.value(this.value,
				'');
		if (v === this.emptyText) {
			v = '';
		}
		if (this.htmlEncode) {
			v = Ext.util.Format.htmlDecode(v);
		}
		return v;
	},
	getValue : function() {
		return this.keyValue;
	},
	getName : function() {
		return this.name;
	},
	setRawValue : function(v) {
		if (this.htmlEncode) {
			v = Ext.util.Format.htmlEncode(v);
		}
		return this.rendered ? (this.el.dom.innerHTML = (Ext.isEmpty(v) ? ''
				: v)) : (this.value = v);
	},
	setValue : function(v) {
		if (v == null || v == '') {
			return;
		}
		v = v.trim();
		this.keyValue = v;
		if (!this.store && this.baseParams) {
			var field = this;
			var basePare = this.baseParams;
			this.store = new Ext.ux2.baseComboStore({
				baseParams : {
					ID : basePare
				},
				listeners : {
					load : function() {
						var hasFind = false;
						if (field.store.getCount() > 0) {
							field.store.each(function(r) {
								if (r.get('valueField') == v) {
									field.setRawValue(r.get('displayField'));
									hasFind = true;
								}
							});
						}
						if (!hasFind) {
							field.setRawValue(v);
						}
					}
				}
			});
			return field;
		} else if (this.store) {
			var field = this;
			var hasFind = false;
			if (field.store.getCount() > 0) {
				field.store.each(function(r) {
					if (r.get('valueField') == v) {
						field.setRawValue(r.get('displayField'));
						hasFind = true;
					}
				});
			}
			if (!hasFind) {
				field.setRawValue(v);
			}
			return field;
		} else {
			this.setRawValue(v);
			return this;
		}
	}
});
Ext.reg('combofordispaly', Ext.form.ComboForDispaly);

/*
 * 动态加载的COMBO 该组空间暂时不支持修改时使用
 */
Ext.ux2.dynamicStore = Ext.extend(Ext.data.Store, {
	proxy : new Ext.data.HttpProxy({
		url : 'dynamicSelect.asp'
	}),
	reader : new Ext.data.JsonReader({
		root : 'data',
		totalProperty : 'totalCount'
	}, [ {
		name : 'valueField'
	}, {
		name : 'displayField'
	} ]),
	listeners: {
        load: function (store) {
            var data = {'valueField': '', 'displayField': ''};
            var rs = [new Ext.data.Record(data)];
            store.insert(0, rs);
        }
    },
	autoLoad : true
});
Ext.ux2.dynamicCombo = Ext.extend(Ext.form.ComboBox, {
	initComponent : function() {
		if (!this.store) {
			
			var combo = this;
			var method = this.methodName;
			this.store = new Ext.ux2.dynamicStore({
				baseParams : {
					methodName : method
				},
				listeners : {
					load : function(store2) {
						// 如果为已赋值,直接请求setValue
						if (combo.isHasValue) {
							var v = combo.hasValue;
							combo.isHasValue = false;
							combo.hasValue = '';
							
							Ext.ux2.dynamicCombo.superclass.setValue.call(
									combo, v);
							
						}
						// 如果无法找到数据就将查询条件重置，但不适用于combo的reload
						if (this.getTotalCount() == 1
								&& this.getAt(0).data.valueField == '') {
							combo.inputValue == '';
						}
						var data2 = {'valueField': '', 'displayField': ""+combo.emptyText};
			            var rs = [new Ext.data.Record(data2)];
			            store2.insert(0, rs);
					},
					beforeload : function() {
						Ext.apply(this.baseParams, {
							inputValue : combo.inputValue
						});
						switchs = false;
					}
				}
			});
//			this.store.insert(0,{"valueField": "0000", "displayField": "不限制"});
		}
		if (null != this.hiddenName && null == this.id) {
			this.id = 'id' + this.hiddenName;
		}
		if (!this.allowBlank) {
			var label = this.fieldLabel;
			if (label.indexOf("*") != -1) {
				label = label.substring(0, label.length - 1);
			}
			this.blankText = '请选择' + label;
		}
		Ext.ux2.dynamicCombo.superclass.initComponent.call(this);
	},
	// 覆盖此方法，做设置的值是否在其中的判断
	// 用于维护类操作，如商户信息维护
	setValue : function(v) {
		if (this.valueField) {
			var r = this.findRecord(this.valueField, v);
			// 如果当前列表已包含该值，直接set，否则，根据该值重新加载列表
			if (r || v == '' || !v) {
				this.isHasValue = false;
				this.hasValue = '';
				Ext.ux2.dynamicCombo.superclass.setValue.call(this, v);
			} else {
				this.inputValue = '_AS' + v;
				this.isHasValue = true;
				this.hasValue = v;
				this.getStore().load();
			}
		}
	},
	displayField : 'displayField',
	valueField : 'valueField',
	mode : 'local',
	emptyText : '请选择',
	triggerAction : 'all',
	forceSelection : true,
	selectOnFocus : true,
	editable : true,
	lazyRender : false,
	typeAhead : false,
	minChars : 1,
	pageSize : 50,
	lastValue : '',
	store : null,
	inputValue : '',
	isHasValue : false,// 定义是否为已赋值后取数据
	hasValue : '',
	switchs : false,// 定义是否正在请求数据
	isFirstTime : true,// 定义是否为初次使用
	listeners : {
		// 修正第一次有可能获取不到数据的问题
		'focus' : function() {
			if (this.store.getTotalCount() == 0) {
				this.store.reload();
			}
		},
		'keyup' : function(c, d) {
			var combo = this;
			var p = d.keyCode;
			// 判断按键的有效性,暂时只考虑这些
			if (Number(p) == 13 || Number(p) == 37 || Number(p) == 38
					|| Number(p) == 39 || Number(p) == 40) {
				return;
			}
			// 如果输入的字符没有变化，则不做处理
			if (combo.inputValue == combo.getRawValue()) {
				return;
			}
			// 只有输入的字符才做为匹配的条件
			combo.inputValue = combo.getRawValue();
			// 请求数据开关
			if (!switchs) {
				switchs = true;
				setTimeout(function() {
					combo.getStore().load();
				}, 500);
			}
		}
	}
});
Ext.reg('dynamicCombo', Ext.ux2.dynamicCombo);
