package com.huateng.struts.accident.action;

import java.util.Map;

import com.huateng.bo.accident.T90101BO;
import com.huateng.common.DataUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

/**
 * project JSBConsole date 2013-4-12
 * 
 * @author 樊东东
 */
public class T90101Action extends BaseAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		T90101BO bo = (T90101BO) ContextUtil.getBean("T90101BO");
		String method = request.getParameter("method");
		Map<String,String> params = DataUtil.getParamMapFromRequest(request);
		return bo.deal(params);
	}

}
