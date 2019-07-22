package com.huateng.struts.offset.action;

import java.util.Map;

import com.huateng.bo.offset.T60101BO;
import com.huateng.common.DataUtil;
import com.huateng.struts.system.action.BaseAction;

/**
 * project JSBConsole date 2013-4-7
 * 
 * @author 樊东东
 */
public class T60101Action extends BaseAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub

		Map<String, String> params = DataUtil.getParamMapFromRequest(request);
		params.put("oprId", this.operator.getOprId());
		String method = params.get("method");
		if ("add".equals(method)) {
			return t60101bo.add(params,request);
		}
		if ("delete".equals(method)) {
			return t60101bo.delete(params);
		}
		return "无效请求";

	}

	private T60101BO t60101bo;

	public T60101BO getT60101bo() {
		return t60101bo;
	}

	public void setT60101bo(T60101BO t60101bo) {
		this.t60101bo = t60101bo;
	}

}
