/**
 * 统一授权系统
 * 
 * 如某交易需要授权，需进行如下配置：
 * 
 * 1.由表单submit提交的交易将submit改为submitNeedAuthorise
 *   由Ext.Ajax.request产生的交易将request改为requestNeedAuthorise
 * 
 * 2.TBL_FUNC_INF中的MISC1值为1或者2
 * 	'1'当前交易需授权
 *  '2'可启用授权，但当前不需要授权
 * 
 * **/


//交易码
var txnCode = "";
//是否需要授权
var needAuthorise = "";
//取得交易码
if(window.parent.txnCode!=null){
	txnCode = window.parent.txnCode;
}
//取得是否需要授权的配置
if(txnCode != ""){
	AuthoriseDwr.checkNeedAuthorise(txnCode,function(ret){
		needAuthorise = ret;
	});
}

Ext.apply(Ext.form.BasicForm.prototype,{
	submitNeedAuthorise: function(options){
		if("S" == needAuthorise){
			window.parent.lackScreenSubmit(this,options);
		}else{
			this.submit(options);
		}
		return this;
	}
});


Ext.apply(Ext.data.Connection.prototype,{
	requestNeedAuthorise: function(options){
		if("S" == needAuthorise){
			window.parent.lackScreenRequest(this,options);
		}else{
			this.request(options);
		}
		return this;
	}
});





