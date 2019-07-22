package com.huateng.struts.mchnt.action;

import java.util.HashMap;
import java.util.Map;

import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;

public class MchntNetDetailAction extends BaseAction {

	protected String subExecute() throws Exception {
		
		Map<String, String> mchntInfoMap = new HashMap<String, String>();
		
		BeanUtils.iterateBeanProperties(mchntInfoMap, mchntInfoMap);
		
		setSessionAttribute("mchntInfo", mchntInfoMap);
		
		return Constants.SUCCESS_CODE;
	}
	
	private String mchntId;

	/**
	 * @return the mchntId
	 */
	public String getMchntId() {
		return mchntId;
	}

	/**
	 * @param mchntId the mchntId to set
	 */
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
}
