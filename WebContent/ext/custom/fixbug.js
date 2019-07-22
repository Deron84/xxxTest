/**
 * 覆盖CombBox的属性/方法
 */
Ext.apply(Ext.form.ComboBox.prototype,{
	// 重写下拉列表属性
	displayField: 'displayField',
	valueField: 'valueField',
	mode: 'local',
	triggerAction: 'all',
	forceSelection: true,
	typeAhead: true,
	selectOnFocus: true,
	// 验证信息有效性方法
	validateValue : function(value) {
		if(Ext.isFunction(this.validator)){
            var msg = this.validator(value);
            if(msg !== true){
                this.markInvalid(msg);
                return false;
            }
        }
        if(value.length < 1 || value === this.emptyText){ // if it's blank
             if(this.allowBlank){
                 this.clearInvalid();
                 return true;
             }else{
                 this.markInvalid(this.blankText);
                 return false;
             }
        }
        if(value.length < this.minLength){
            this.markInvalid(String.format(this.minLengthText, this.minLength));
            return false;
        }
        if(value.length > this.maxLength){
            this.markInvalid(String.format(this.maxLengthText, this.maxLength));
            return false;
        }	
        if(this.vtype){
            var vt = Ext.form.VTypes;
            if(!vt[this.vtype](value, this)){
                this.markInvalid(this.vtypeText || vt[this.vtype +'Text']);
                return false;
            }
        }
        if(this.regex && !this.regex.test(value)){
            this.markInvalid(this.regexText);
            return false;
        }
        // 非空条件下，对hiddenName也进行校验
        if(!this.allowBlank) {
        	if(Ext.getDom(Ext.getDoc()).getElementById(this.hiddenName).value == '') {
        		this.markInvalid(this.blankText);
        		return false;
       		}
        }
        return true;
    }
});
/**
 * 重写日期控件属性
 */
Ext.apply(Ext.form.DateField.prototype,{
	format: 'Ymd',
	altFormats: 'Ymd'
});

/**
 * 覆盖Ext.data.Node的属性/方法
 */
Ext.apply(Ext.data.Node.prototype,{
	// 移除子节点
	remove : function(destroy){
		// 增加了对服节点非空的判断
        if(this.parentNode != null) {
        	this.parentNode.removeChild(this, destroy);
        }
        return this;
    }
});