package com.huateng.struts.accident.action;

import java.util.Map;

import com.huateng.bo.accident.T90102BO;
import com.huateng.common.DataUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;


/**
 * project JSBConsole
 * date 2013-4-12
 * @author 樊东东
 */
public class T90102Action extends BaseAction{

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {

		T90102BO bo = (T90102BO)ContextUtil.getBean("T90102BO");
		Map<String,String> params = DataUtil.getParamMapFromRequest(request);
		params.put("oprId", this.operator.getOprId());
		String method = params.get("method");
		if("accept".equals(method)){
			return bo.accept(params);
		}
		if("refuse".equals(method)){
			return bo.refuse(params);
		}
		return "无效请求";
	}

}
