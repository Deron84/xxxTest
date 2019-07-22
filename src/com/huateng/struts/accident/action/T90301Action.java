package com.huateng.struts.accident.action;

import java.util.Map;

import com.huateng.bo.accident.T90301BO;
import com.huateng.common.DataUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

/**
 * project JSBConsole date 2013-3-19
 * 
 * @author 樊东东
 */
public class T90301Action extends BaseAction {

	private T90301BO bo = (T90301BO) ContextUtil.getBean("T90301BOTarget");

	@Override
	protected String subExecute() throws Exception {
		Map<String, String> params = DataUtil.getParamMapFromRequest(request);
		String oprId = this.operator.getOprId();
		params.put("oprId", oprId);
		return bo.deal(params,request);
	}

}
