package com.huateng.struts.pos.action;

import java.util.Iterator;
import java.util.List;

import com.huateng.bo.term.TermManagementBO;
import com.huateng.common.Constants;
import com.huateng.po.TblTermManagement;
import com.huateng.struts.pos.TblTermManagementConstants;
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
public class T30302Action extends BaseAction {


	private static final long serialVersionUID = -1673317644406998528L;
	private TermManagementBO t3030BO;
	private String manufacturer;
	private String terminalType;
	private String number;
	private String termType;
	private String brhId;
	
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the terminalType
	 */
	public String getTerminalType() {
		return terminalType;
	}

	/**
	 * @param terminalType the terminalType to set
	 */
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the termType
	 */
	public String getTermType() {
		return termType;
	}

	/**
	 * @param termType the termType to set
	 */
	public void setTermType(String termType) {
		this.termType = termType;
	}

	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	/**
	 * @param t3030bo the t3030BO to set
	 */
	public void setT3030BO(TermManagementBO t3030bo) {
		t3030BO = t3030bo;
	}

	@Override
	protected String subExecute() throws Exception {
		int num = Integer.parseInt(number);
		List<TblTermManagement> list = t3030BO.queryTerminal(manufacturer, terminalType, num, termType);
		if(list == null||list.isEmpty())
			return TblTermManagementConstants.T30302_01;
		if(list.size()<num)
			return TblTermManagementConstants.T30302_02;
		
		Iterator <TblTermManagement>it = list.iterator();
		String batchNo = t3030BO.getNextBatchNo();
		while(it.hasNext())
		{
			TblTermManagement tmp = it.next();
			tmp.setBatchNo(batchNo);
			t3030BO.updTerminal(tmp, TblTermManagementConstants.TERM_STATE_RECI_UNCHECK, operator.getOprId(), brhId);
		}
		return Constants.SUCCESS_CODE;
	}

}
