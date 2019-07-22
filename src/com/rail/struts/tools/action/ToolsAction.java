package com.rail.struts.tools.action;

import java.util.Map;

import com.huateng.bo.accident.T90101BO;
import com.huateng.common.DataUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

/**
 * 
 * ClassName: ToolsAction 
 * @Description: TODO
 * @author syl
 * @date 2019年4月15日
 */
public class ToolsAction extends BaseAction {
	@Override
	protected String subExecute() throws Exception {
		T90101BO bo = (T90101BO) ContextUtil.getBean("T90101BO");
		String method = request.getParameter("method");
		Map<String,String> params = DataUtil.getParamMapFromRequest(request);
		return bo.deal(params);
	}
	
	
	

}

