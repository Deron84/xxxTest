package com.huateng.struts.offset.action;

import java.util.Map;

import com.huateng.bo.offset.T601BO;
import com.huateng.common.DataUtil;
import com.huateng.struts.system.action.BaseAction;


/**
 * project JSBConsole
 * date 2013-4-8
 * @author 樊东东
 */
public class T601Action extends BaseAction{
	private T601BO bo;

	
	public T601BO getBo() {
		return bo;
	}

	
	public void setBo(T601BO bo) {
		this.bo = bo;
	}


	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> params = DataUtil.getParamMapFromRequest(request);
		params.put("oprId", this.operator.getOprId());
		return bo.deal(params);
		
	}
	
}
