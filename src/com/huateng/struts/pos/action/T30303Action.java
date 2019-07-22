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
 * Copyright: Copyright (c) 2011-6-15
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T30303Action extends BaseAction {

	private static final long serialVersionUID = -7307513144684076109L;
	private Map methods;
	private String method;
	private String batchNo;
	private String termId;
	private String mechNo;
	private TermManagementBO t3030BO;
	@Override
	protected String subExecute() throws Exception {
		if(method != null && !method.equals(""))
		{
			String methodName = (String) methods.get(method);
			Object[] args = new Object[4];
			if(batchNo !=  null && !batchNo.trim().equals(""))
				args[0] = batchNo;
			if(termId !=  null && !termId.trim().equals(""))
				args[1] = termId;
			if(mechNo !=  null && !mechNo.trim().equals(""))
				args[2] = mechNo.split(",")[0];
			args[3] = operator.getOprBrhId();
			rspCode = (String) MethodUtils.invokeMethod(t3030BO, methodName, args);
		}
		return rspCode;
	}
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
	 * @return the mchnNo
	 */
	public String getMechNo() {
		return mechNo;
	}
	/**
	 * @param mchnNo the mchnNo to set
	 */
	public void setMechNo(String mechNo) {
		this.mechNo = mechNo;
	}
	/**
	 * @param t3030bo the t3030BO to set
	 */
	public void setT3030BO(TermManagementBO t3030bo) {
		t3030BO = t3030bo;
	}

}
