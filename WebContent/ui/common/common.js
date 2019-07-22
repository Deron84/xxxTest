	/**
	 * 操作员状态
	 */
	function oprState(val) {
		if(val == '0') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="可用"/>正常';
		} else if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="锁定"/>锁定';
		} else if(val == '2') {
			return '添加待审查';
		}else if(val == '3') {
			return '修改待审核';
		}else if(val == '4') {
			return '审核不通过';
		}
		return '状态未知';
	}
	
	function transConnType(val){
		if(val=='Z'){
			return '直联';
		}
		if(val=='J'){
			return '间联';
		}
		return val;
	};
	
	function getMisc(val){
		var t1= val.charAt(0)
		var t3 = val.charAt(2)
		if(t1=='1' && t3=='1'){
			return '即触发日风控，又触发月风控';
		}else if(t1=='1' && t3!='1'){
			return '触发日风控';
		}else if(t1!='1' && t3=='1'){
			return '触发月风控';
		}else {
			return '未知';
		}
	}
	
/**
	 * 操作员性别
	 */
	
	function gender(val) {
		if(val == '0') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/male.png" />';
		} else if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/female.png" />';
		}
		return val;
	}
	
	function mchtCupStInfo(val) {
		if(val == '1') {
			return '<font color="green">正常</font>';
		} else if(val == '2') {
			return '<font color="red">冻结</font>';
		} else if(val == '0') {
			return '<font color="red">注销</font>';
		} else if(val == '3') {
			return '<font color="gray">修改待审核</font>';
		}else if(val == '4') {
			return '<font color="gray">修改待审核退回</font>';
		} else if(val == '8') {
			return '<font color="gray">注销待审核</font>';
		} else if(val == '5') {
			return '<font color="gray">冻结待审核</font>';
		} else if(val == '7') {
			return '<font color="gray">解冻待审核</font>';
		}else if(val == '9') {
			return '<font color="gray">新增待审核</font>';
		}else if(val == '6') {
			return '<font color="gray">新增待审核退回</font>';
		}
		return val;
	}
	
	function insetFlag01(val) {
		 switch(val) {
			case 'I': return '新增';
			case 'U': return '修改';
			case 'D': return '删除';
			default : return '未知';
		}
	 }
	 
	 function mchtTp(val) {
		 switch(val) {
			case '0': return '收款商户';
			case '1': return '老板通商户';
			case '2': return '分期商户';
			case '3': return '财务转账商户';
			case '4': return '项目形式商户';
			case '5': return '积分业务';
			case '6': return '其他业务';
			case '7': return '固话POS商户';
			default : return '未知';
		}
	 }
	 
	 function mchtTp2(val) {
		 switch(val) {
			case '00': return '线上商户';
			case '01': return '线下商户';
			case '10': return '业主收款商户';
			case '11': return '业主付款商户';
			case '20': return '汽车分期商户';
			case '21': return '停车位分期商户';
			case '22': return '家装分期商户';
			case '23': return 'POS分期商户';
			case '24': return '其他分期商户';
			case '30': return '本行财务转账商户';
			case '31': return '跨行财务转账商户';
			case '40': return '交通罚没项目';
			case '41': return '体彩购额项目';
			case '42': return '其他项目';
			default : return '未知';
		}
	 }
	 
	 function mchtGrp(val) {
		 switch(val) {
			case '01': return '宾馆类';
			case '02': return '餐饮类';
			case '03': return '珠宝类';
			case '04': return '娱乐类';
			case '05': return '房产类';
			case '06': return '汽车类';
			case '07': return '批发类';
			case '08': return '航空类';
			case '09': return '铁路类';
			case '10': return '加油类';
			case '11': return '超市类';
			case '12': return '公益类';
			case '13': return '其他类';
			default : return '未知';
		}
	 }
	 
	 function errFlag(val) {
		 switch(val) {
			case '1': return '收单成功，银联失败';
			case '2': return '收单失败，银联成功';
			case '3': return '收单与银联金额不一致';
			case '4': return '收单成功，核心失败';
			case '5': return '收单失败， 核心成功';
			case '6': return '收单与核心金额不一致';
			case 'R': return '收单成功，银联数据失败';
			case 'S': return '收单失败，银联数据成功';
			case 'T': return '收单与银联数据金额不一致';
			default : return '未知';
		}
	 }
	 
	/**
     * 直联商户类型
	 */
	 function mchtCupType(val) {
		 switch(val) {
			case '00': return '传统POS商户';
			case '01': return '服务提供机构';
			case '02': return '接入渠道机构';
			case '03': return 'CUPSECURE商户';
			case '04': return '虚拟商户';
			case '05': return '行业商户';
			case '06': return '服务提供机构+接入渠道机构';
			case '07': return '多渠道直联终端商户';
			default : return '未知';
		}
	 }
	 
/**
	 * 商户状态转译
	 */
	function mchntSt(val) {
		if(val == '0') {
			return '<font color="green">正常</font>';
		} else if(val == '1') {
			return '<font color="gray">添加待审核</font>';
		} else if(val == '2') {
			return '<font color="gray">添加审核退回</font>';
		} else if(val == '3') {
			return '<font color="gray">修改待审核</font>';
		} else if(val == '4') {
			return '<font color="gray">修改审核退回</font>';
		} else if(val == '5') {
			return '<font color="gray">冻结待审核</font>';
		} else if(val == '6') {//把停用改为冻结、添加注销状态
			return '<font color="red">冻结</font>';
		} else if(val == '7') {
			return '<font color="gray">解冻恢复待审核</font>';
		} else if(val == '8'){
			return '<font color="gray">注销待审核</font>';
		}else if(val == '9'){
			return '<font color="red">注销</font>';
		}else if(val == 'R'){
			return '<font color="gray">注销恢复审核</font>';
		}
		return val;
	}

	
	/**
	     * 银行转换
		 */
	 function brhLvlRender(val) {
		 switch(val) {
			case '0': return '总行';
			case '1': return '分行';
			case '2': return '支行';
			default : return '未知';
		}
	 }
	 
	 	/**
	     * 翻译是或者否
		 */
	 function yesOrNo(val) {
		 switch(val) {
			case '0': return '是';
			case '1': return '否';
			default : return '未知';
		}
	 }
	 
	 function backFlag(val) {
		 switch(val) {
			case '0': return '未承兑';
			case '1': return '已承兑';
			default : return '未知';
		}
	 }
	     
     /**
	     * 银行转换
		 */
	 function groupType(val) {
		 switch(val) {
			case '1': return '全国性集团';
			case '2': return '地方性集团（省内）';
			default : return '未知';
		}
	 }
	     
     /**
	     * 交易渠道转换
		 */
	 function channelType(val) {
		 switch(val) {
			case '0': return '间联POS';
			case '1': return '否';
			default : return '未知';
		}
	 }
     /**
	     * 卡类型转换
		 */
	 function cardType(val) {
		 switch(val) {
			case '01': return '借记卡';
			case '00': return '贷记卡';
			default : return '未知卡';
		}
	 }
	     
     /**
	     * 交易名称转换
		 */
	 function funType(val) {
		 switch(val) {
			case '0001': return   '预授权请求';
			case '0002': return   '预授权撤销';
			case '0003': return   '预授权完成请求';
			case '0004': return   '预授权完成撤销';
			case '0005': return   '消费请求';
			case '0006': return   '消费撤销请求';
			case '0007': return   '余额查询请求';
			case '0008': return   '存款请求';
			case '0009': return   '存款撤销请求';
			case '0010': return   '取款请求';
			case '0011': return   '取款撤销请求';
			case '0012': return   '分期消费请求';
			case '0013': return   '分期消费撤销请求';
			case '0014': return   '转账请求';
			case '0015': return   '转账撤销请求';
			case '0016': return   '快速支付';
			case '0017': return   '脚本处理结果通知';
			
			default : return '未知交易类型';
		}
	 }
	     
     /**
	     * 服务等级转换
		 */
	 function svrLvlType(val) {
		 switch(val) {
			case '0': return   'VIP';
			case '1': return   '重点';
			case '2': return   '普通';
			default : return '未知卡';
		}
	 }
	 	
	 //终端库存状态
	 function translateState(val) {
			if(val == '1')
				return "已作废";
			if(val == '4')
				return "已入库";
			if(val == '5')
				return "已出库";
			return val;
		}
	// 终端类型
	function termType(val) {
			if(val == 'P')
				return "POS";
			if(val == 'E')
				return "EPOS";
			if(val == 'A')
				return "ATM";
			return val;
		}
    // 终端状态
    function termSta(val) {
    		if(val == '0')
    			return "新增待审核";
            if(val == '1')
                return "待装机";
            if(val == '2')
                return "已装机";
            if(val == '3')
                return "修改待审核";
            if(val == '4')
                return "冻结 ";
            if(val == '5')
                return "冻结待审核";
            if(val == '6')
                return "解冻待审核";
            if(val == '7')
                return "注销";
            if(val == '8')
                return "注销待审核";
//            if(val == '9')
//                return "撤机";
            if(val == 'A')
                return "新增退回";
            if(val == 'R')
                return "注销恢复审核";
           
            return val;
        }
    // 终端签到状态
	function termSignSta(val) {
			if(val == '0')
				return "未签到";
			if(val == '1')
				return "已签到";
			return val;
		}
    function termState(val) {
        if(val == '0')
            return "已申请";
        if(val == '1')
            return "已审核";
        return val;
    }   
    
    function clcAction(val) {
        if(val == '0')
            return "<font color='green'>正常</font>";
        if(val == '2')
            return "<font color='red''>拒绝</font>";
        if(val == '3')
            return "<font color='blue'>预警</font>";
        return val;
    }
    
    function settleFlag(val) {
        if(val == '0')
            return "未处理";
        if(val == '1')
            return "<font color='red'>划拨失败</font>";
        if(val == '2')
            return "<font color='green'>划拨成功</font>";
        return val;
    }
    function actState(val) {
        if(val == '0')
            return "<font color='green'>正常</font>";
        if(val == '1')
            return "未审核";
        if(val == '2')
            return "<font color='red'>关闭</font>";
        return val;
    }

    function actOprState(val){
    	if(val == '2')
            return "关闭";
    	if(val == '1')
    		return "修改";
    	if(val == '0')
    		return "新增";
    	return "";
    }
    
    function mchntOprState(val){
    	if(val == '2')
            return "删除";
    	if(val == '1')
    		return "追加";
    	if(val == '0')
    		return "新增";
    	return val;
    }
    
    function mchtCardBindCardFlag(val){
    	if(val=='0'){
    		return '单位卡';
    	}
    	if(val=='1'){
    		return '个人卡';
    	}
    	return val;
    }
    
    function transTxnAcqType(val){
    	if(val=='00'){
    		return '按照正常交易向商户清算';
    	}
    	if(val=='01'){
    		return '初始待处理状态';
    	}
    	if(val=='02'){
    		return '等待调查押款处理';
    	}
    	if(val=='03'){
    		return '次日资金扣回';
    	}
    	if(val=='04'){
    		return '行方托收';
    	}
    	if(val=='05'){
    		return '多收款项退回发卡行';
    	}
    	if(val=='06'){
    		return '已清算';
    	}
    	if(val=='07'){
    		return '已退还';
    	}
    	return val;
    }
    
    function mchtType(val){
		if(val=='0'){
			return "否";
		}else if(val=='1'){
			return "是";
		}else {
			return "未知";
		}
	}
    
    function riskDealType(val){
    	if(val=='0'){
    		return '触发风险待处理';
    	}
    	if(val=='1'){
    		return '手工清算已办结';
    	}
    	if(val=='2'){
    		return '正常清算已办结';
    	}
    	if(val=='3'){
    		return '暂不清算等待处理';
    	}
    	if(val=='4'){
    		return '押款已办结';
    	}
    }
    //交易补登中的操作状态 0-无失败作废 1-	强制发起
    function transOprSta(val){
    	if(val=='0'){
    		return '无效作废';
    	}
    	if(val=='1'){
    		return '强制发起';
    	}
    	return val;
    }
    //交易补登中的清算处理状态   1.	已清算  2.	未清算
    function transSettleSta(val){
    	if(val=='1'){
    		return '已清算';
    	}
    	if(val=='2'){
    		return '未清算';
    	}
    	return val;
    }
    //卡类型转换
    function transCardType(val){
    	if(val=='01'){
    		return '本行借记卡';
    	}
    	if(val=='02'){
    		return '本行贷记卡';
    	}
    	if(val=='03'){
    		return '他行借记卡';
    	}
    	if(val=='04'){
    		return '他行贷记卡';
    	}
    	return val;
    }
    
    //申请状态
    function applyStatus(val){
    	if(val=='0'){
    		return '待审核';
    	}
    	if(val=='1'){
    		return '审核中';
    	}
    	if(val=='2'){
    		return '已审核';
    	}
    	if(val=='3'){
    		return '已拒绝';
    	}
    	return val;
    }
    //申请类型
    function applyType(val){    	
    	if(val=='1'){
    		return '增机申请';
    	}
    	if(val=='2'){
    		return '撤机申请';
    	}
    	if(val=='3'){
    		return '重置密码申请';
    	}
    	if(val=='4'){
    		return '故障报修';
    	}
    	return val;
    }
    
  //折扣类型
    function discountType(val){    	
    	if(val=='0'){
    		return '满减';
    	}
    	if(val=='1'){
    		return '折扣';
    	}
    	if(val=='2'){
    		return '任意';
    	}
    	if(val=='3'){
    		return '仅联机优惠';
    	}
    	return val;
    }
  //收单方
    function shoudanfang(val){    	
    	if(val=='01'){
    		return '银联';
    	}
    	if(val=='00'){
    		return '山东一卡通';
    	}
    	return val;
    }
    
    //规则内容
    function discountInfo(val){
    	if(val.indexOf('MJ')>-1){
    		var vals=val.replace('MJ','').split('-');
    		return '满'+parseInt(vals[0],10)+' 减'+parseInt(vals[1],10);
    	}else if(val.indexOf('ZK')>-1){
    		var vals=val.replace('ZK','').split('-');
    		return '折扣'+parseInt(vals[1],10)+'%'+' 限额'+parseInt(vals[0],10);
    	}else{
    		return val;
    	}
    }
    
    function acquirersType(val){
    	if(val=='00'){
    		return '满减';
    	}
    	if(val=='01'){
    		return '折扣';
    	}
    	if(val=='02'){
    		return '任意';
    	}
    	if(val=='03'){
    		return '仅联机优惠';
    	}
    	return val;
    }
    
    function openType(val){
    	if(val=='01'){
    		return '依据终端开启'
    	}
    	if(val=='02'){
    		return '依据卡bin开启'
    	}
    	return val;
    }
    function isDownload(val){
    	if(val=='00'){
    		return '已完成'
    	}
    	if(val=='01'){
    		return '需继续下载'
    	}
    	return val;
    }
    function openLian(val){
    	if(val=='00'){
    		return '仅脱机优惠'
    	}
    	if(val=='01'){
    		return '脱机+联机优惠'
    	}
    	if(val=='02'){
    		return '仅联机优惠'
    	}
    	return val;
    }
    
    //系统参数类型
    function systemType(val){
    	if(val=='00'){
    		return "<font color='green'>可维护</font>";
    	}else if(val=='01'){
    		return "<font color='red'>不可维护</font>";
    	}
    	return val;
    }
    
  //绑定开启状态
    function openStatus(val){
    	if(val=='01'){
    		return "<font color='green'>开启</font>";
    	}else if(val=='00'){
    		return "<font color='red'>关闭</font>";
    	}
    	return val;
    }
    
    
//    if(x=='00'){
//		columnObj.setColumnHeader(j,shdfconfig[x]);
//	}else{
//		columnObj.setColumnHeader(j,shdfconfig[x]);
//	}
//	if(shdfconfig[1]=='01'){
//		columnObj.setColumnHeader(k,'银联商户号');
//	}else{
//		columnObj.setColumnHeader(k,'一卡通商户号');
//	}
    
    //收单方配置
//    var shdfconfig={'00':'一卡通商户号','01':'银联商户号*'};
    var shdfconfig={'00':'支付宝用户ID','01':'银联商户号*'};
    
    function setHeader(columnObj,j,k){
    	for(var x in shdfconfig){
	        if(x=='00'){
	    		columnObj.setColumnHeader(j,shdfconfig[x]);
	    	}
	    	if(x=='01'){
	    		columnObj.setColumnHeader(k,shdfconfig[x]);
	    	}
    	}
    }
    
    function setDetail(id1,id2){
    	for(var x in shdfconfig){
	        if(x=='00'){
	        	Ext.getCmp(id1).getEl().up('.x-form-item').child('.x-form-item-label').dom.innerHTML=shdfconfig[x]+':';
	    	}
	    	if(x=='01'){
	    		Ext.getCmp(id2).getEl().up('.x-form-item').child('.x-form-item-label').dom.innerHTML=shdfconfig[x]+':';
	    	}
    	}
    	
    }
    
    
    function  today(d){
    	var today;
    	today=new Date();
    	if(d){
    		today=new Date(d);
    	}
    	var todaytime=today.getFullYear()+'-';
    	if(today.getMonth()<9){
    		todaytime+='0'+(today.getMonth()+1)+'-';
    	}else{
    		todaytime+=(today.getMonth()+1)+'-';
    	}
    	if(today.getDate()<10){
    		todaytime+='0'+today.getDate();
    	}else{
    		todaytime+=today.getDate();
    	}
    	return todaytime;
    }
    
    //console
    if(!window.console)  
        window.console = {};  
    var console = window.console;  

    var funcs = ['assert', 'clear', 'count', 'debug', 'dir', 'dirxml',  
                 'error', 'exception', 'group', 'groupCollapsed', 'groupEnd',  
                 'info', 'log', 'markTimeline', 'profile', 'profileEnd',  
                 'table', 'time', 'timeEnd', 'timeStamp', 'trace', 'warn'];  
    for(var i=0,l=funcs.length;i<l;i++) {  
        var func = funcs[i];  
        if(!console[func])  
            console[func] = function(){};  
    }  
    if(!console.memory)  
        console.memory = {};
 
