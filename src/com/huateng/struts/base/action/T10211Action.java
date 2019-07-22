/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-5       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.base.T10211BO;
import com.huateng.common.Constants;
import com.huateng.po.TblBrhParam;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.StringUtil;

/**
 * Title:系统参数维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10211Action extends BaseAction {

	T10211BO t10211BO = (T10211BO) ContextUtil.getBean("T10211BO");

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {

		// add method
		if ("add".equals(method)) {
			rspCode = add();
		} else if ("delete".equals(method)) {
			rspCode = delete();
		} else if ("update".equals(method)) {
			rspCode = update();
		}
		return rspCode;
	}

	/**
	 * add system parameter information
	 * 
	 * @return
	 */
	private String add() {

		TblBrhParam tblBrhParam = new TblBrhParam();
		if (t10211BO.get(brhId) != null) {
			return "该机构已有对应得参数信息。";
		}
		tblBrhParam.setId(brhId);
		if (null != statDate && !"".equals(statDate)) {
			if (statDate.compareTo("31") > 0) {
				return "统计周期日需是01到31。";
			}
		}
		tblBrhParam.setStatDate(StringUtil.fillString(statDate, 2, "0"));
		tblBrhParam.setStdToalcounts(stdToalcounts);
		tblBrhParam.setStdToalmoney(StringUtil
				.fillString(stdToalmoney, 12, "0"));
		tblBrhParam.setStdCondition(stdCondition);
		tblBrhParam.setResvField1(resvField1);
		tblBrhParam.setResvField2(resvField2);
		t10211BO.add(tblBrhParam);

		return Constants.SUCCESS_CODE;
	}

	/**
	 * delete system parameter information
	 * 
	 * @return
	 */
	private String delete() {
		if (t10211BO.get(brhId) == null) {
			return "您所要删除的参数信息已经不存在";
		}
		t10211BO.delete(brhId);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * update the parameter information list
	 * 
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private String update() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		jsonBean.parseJSONArrayData(getParameterList());
		int len = jsonBean.getArray().size();
		TblBrhParam tblBrhParam = null;

		List<TblBrhParam> tblBrhParamList = new ArrayList<TblBrhParam>(len);

		for (int i = 0; i < len; i++) {
			tblBrhParam = new TblBrhParam();
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			BeanUtils
					.setObjectWithPropertiesValue(tblBrhParam, jsonBean, false);
			BeanUtils.setNullValueWithValue(tblBrhParam, "", 0);
			tblBrhParam.setStatDate(StringUtil.fillString(
					tblBrhParam.getStatDate(), 2, "0"));
			tblBrhParam.setStdToalmoney(StringUtil.fillString(
					tblBrhParam.getStdToalmoney(), 12, "0"));
			tblBrhParamList.add(tblBrhParam);
		}

		t10211BO.update(tblBrhParamList);

		return Constants.SUCCESS_CODE;
	}

	private java.lang.String brhId;
	private java.lang.String statDate;
	private java.lang.Integer stdToalcounts;
	private java.lang.String stdToalmoney;
	private java.lang.String stdCondition;
	private java.lang.String resvField1;
	private java.lang.String resvField2;
	/** the list of parameter to update */
	private String parameterList;

	public java.lang.String getBrhId() {
		return brhId;
	}

	public void setBrhId(java.lang.String brhId) {
		this.brhId = brhId;
	}

	public java.lang.String getStatDate() {
		return statDate;
	}

	public void setStatDate(java.lang.String statDate) {
		this.statDate = statDate;
	}

	public java.lang.Integer getStdToalcounts() {
		return stdToalcounts;
	}

	public void setStdToalcounts(java.lang.Integer stdToalcounts) {
		this.stdToalcounts = stdToalcounts;
	}

	public java.lang.String getStdToalmoney() {
		return stdToalmoney;
	}

	public void setStdToalmoney(java.lang.String stdToalmoney) {
		this.stdToalmoney = stdToalmoney;
	}

	public java.lang.String getResvField1() {
		return resvField1;
	}

	public void setResvField1(java.lang.String resvField1) {
		this.resvField1 = resvField1;
	}

	public java.lang.String getResvField2() {
		return resvField2;
	}

	public void setResvField2(java.lang.String resvField2) {
		this.resvField2 = resvField2;
	}

	/**
	 * @return the parameterList
	 */
	public String getParameterList() {
		return parameterList;
	}

	/**
	 * @param parameterList
	 *            the parameterList to set
	 */
	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}

	public java.lang.String getStdCondition() {
		return stdCondition;
	}

	public void setStdCondition(java.lang.String stdCondition) {
		this.stdCondition = stdCondition;
	}

}
