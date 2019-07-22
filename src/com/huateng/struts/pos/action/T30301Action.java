package com.huateng.struts.pos.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.bo.term.TermManagementBO;
import com.huateng.struts.pos.TblTermManagementConstants;
import com.huateng.struts.system.action.BaseAction;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T30301Action extends BaseAction {

	private static final long serialVersionUID = -8992241418046593410L;

	private TermManagementBO t3030BO;

	private Map<?, ?> methods;

	private String method;
	
	private String brhIdNew;

	private String manufacturerNew;

	private String terminalTypeNew;

	private String number;

	private String startProductCd;

	private String endProductCd;

	private String productCdNew;

	private String miscNew;

	private String termId;

	private String misc;

	private String termTypeNew;

	/**
	 * @param t30301bo
	 *            the t30301BO to set
	 */
	public void setT3030BO(TermManagementBO t3030bo) {
		t3030BO = t3030bo;
	}

	/**
	 * @param methods
	 *            the methods to set
	 */
	public void setMethods(Map<?, ?> methods) {
		this.methods = methods;
	}

	@Override
	protected String subExecute() throws Exception {
		if (StringUtils.isNotEmpty(method)) {
//			String methodName = (String) methods.get(method);
//			String methodName = "storeTerminals";
			String methodName = method;
			HashMap<String, Object> args = new HashMap<String, Object>();
			if (StringUtils.isNotEmpty(brhIdNew))
				args.put("brhId", brhIdNew);
			if (StringUtils.isNotEmpty(manufacturerNew))
				args.put("manufacturer", manufacturerNew);
			if(StringUtils.isNotEmpty(number)){
				args.put("number", number);
			}
			if (StringUtils.isNotEmpty(terminalTypeNew))
				args.put("terminalType", terminalTypeNew);
			if (StringUtils.length(terminalTypeNew) > 10) {
				return TblTermManagementConstants.T30301_02;
			}
			if (StringUtils.isNotEmpty(startProductCd))
				args.put("startProductCd", startProductCd);

			if (StringUtils.isNotEmpty(termTypeNew))
				args.put("termType", termTypeNew);
			if (StringUtils.isNotEmpty(miscNew))
				args.put("misc", miscNew);
			/************** 作废参数 *******************************************/
			if (StringUtils.isNotEmpty(termId))
				args.put("termNo", termId);
			if (StringUtils.isNotEmpty(misc))
				args.put("misc", misc);
			/************** 公共参数 *******************************************/
			args.put("operator", operator);
			rspCode = (String) MethodUtils.invokeMethod(t3030BO, methodName,
					args);
		}
		return rspCode;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the manufacturerNew
	 */
	public String getManufacturerNew() {
		return manufacturerNew;
	}

	/**
	 * @param manufacturerNew
	 *            the manufacturerNew to set
	 */
	public void setManufacturerNew(String manufacturerNew) {
		this.manufacturerNew = manufacturerNew;
	}

	/**
	 * @return the terminalTypeNew
	 */
	public String getTerminalTypeNew() {
		return terminalTypeNew;
	}

	/**
	 * @param terminalTypeNew
	 *            the terminalTypeNew to set
	 */
	public void setTerminalTypeNew(String terminalTypeNew) {
		this.terminalTypeNew = terminalTypeNew;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the startProductCd
	 */
	public String getStartProductCd() {
		return startProductCd;
	}

	/**
	 * @param startProductCd
	 *            the startProductCd to set
	 */
	public void setStartProductCd(String startProductCd) {
		this.startProductCd = startProductCd;
	}

	/**
	 * @return the endProductCd
	 */
	public String getEndProductCd() {
		return endProductCd;
	}

	/**
	 * @param endProductCd
	 *            the endProductCd to set
	 */
	public void setEndProductCd(String endProductCd) {
		this.endProductCd = endProductCd;
	}

	/**
	 * @return the misc
	 */
	public String getMisc() {
		return misc;
	}

	/**
	 * @param misc
	 *            the misc to set
	 */
	public void setMisc(String misc) {
		this.misc = misc;
	}

	/**
	 * @return the termTypeNew
	 */
	public String getTermTypeNew() {
		return termTypeNew;
	}

	/**
	 * @param termTypeNew
	 *            the termTypeNew to set
	 */
	public void setTermTypeNew(String termTypeNew) {
		this.termTypeNew = termTypeNew;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId
	 *            the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * @return the productCdNew
	 */
	public String getProductCdNew() {
		return productCdNew;
	}

	/**
	 * @param productCdNew
	 *            the productCdNew to set
	 */
	public void setProductCdNew(String productCdNew) {
		this.productCdNew = productCdNew;
	}

	/**
	 * @return the miscNew
	 */
	public String getMiscNew() {
		return miscNew;
	}

	/**
	 * @param miscNew
	 *            the miscNew to set
	 */
	public void setMiscNew(String miscNew) {
		this.miscNew = miscNew;
	}

	public String getBrhIdNew() {
		return brhIdNew;
	}

	public void setBrhIdNew(String brhIdNew) {
		this.brhIdNew = brhIdNew;
	}

}
