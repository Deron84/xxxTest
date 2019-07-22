package com.huateng.struts.pos.action;

import com.huateng.bo.term.TermManagementBO;
import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;

public class T30304Action extends BaseAction {
	
	private static final long serialVersionUID = -7458083560605416590L;
	private TermManagementBO t3030BO;
	private String action;
	private String termId;
	/**
	 * @param t3030bo the t3030BO to set
	 */
	public void setT3030BO(TermManagementBO t3030bo) {
		t3030BO = t3030bo;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	@Override
	protected String subExecute() throws Exception {
		if(action == null || action.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(termId == null || termId.trim().equals(""))
			return Constants.FAILURE_CODE;
		rspCode = t3030BO.bankTermianl(Integer.parseInt(action.split(",")[0]), termId,operator);
		return rspCode;
	}

}
