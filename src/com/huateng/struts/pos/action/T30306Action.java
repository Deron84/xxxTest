package com.huateng.struts.pos.action;

import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;

import com.huateng.bo.term.TermManagementBO;
import com.huateng.struts.system.action.BaseAction;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T30306Action extends BaseAction {
	
	private static final long serialVersionUID = -3420039274779765248L;
	private TermManagementBO t3030BO;
	private Map methods;
	private String method;
	private String termId;
	private String batchNo;
	
	/**
	 * @return the methods
	 */
	public Map getMethods() {
		return methods;
	}

	/**
	 * @param methods the methods to set
	 */
	public void setMethods(Map methods) {
		this.methods = methods;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
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

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @param t3030bo the t3030BO to set
	 */
	public void setT3030BO(TermManagementBO t3030bo) {
		t3030BO = t3030bo;
	}

	@Override
	protected String subExecute() throws Exception {
		if(method != null && !method.equals(""))
		{
			String methodName = (String) methods.get(method);
			Object[] args = new Object[3];
			if(batchNo !=  null && !batchNo.trim().equals(""))
				args[0] = batchNo;
			if(termId !=  null && !termId.trim().equals(""))
				args[1] = termId;
			args[2] = operator.getOprBrhId();
			rspCode = (String) MethodUtils.invokeMethod(t3030BO, methodName, args);
		}
		return rspCode;
	}

}
