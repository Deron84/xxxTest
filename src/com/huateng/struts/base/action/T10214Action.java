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

import com.huateng.bo.base.T10214BO;
import com.huateng.common.Constants;
import com.huateng.po.AlipayShareParam;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;

/**
 * Title:支付宝分润参数维护
 */
@SuppressWarnings("serial")
public class T10214Action extends BaseAction {

	T10214BO t10214BO = (T10214BO) ContextUtil.getBean("T10214BO");

	@Override
	protected String subExecute() throws Exception {

		// add method
		if("add".equals(method)) {
			rspCode = add();
		} else if("delete".equals(method)) {
			rspCode = delete();
		} else if("update".equals(method)) {
			rspCode = update();
		}
		return rspCode;
	}

	private String add() {

		//initialize system parameter object
		AlipayShareParam alipayShareParam = new AlipayShareParam();

//		if(t10214BO.get(brhId) != null) {
//			return "该机构的支付宝分润参数已存在。";
//		}
		 	
//		alipayShareParam.setParamId(String.valueOf(System.currentTimeMillis()));
		alipayShareParam.setBrhId(brhId);
		alipayShareParam.setShareStandardTotal(shareStandardTotal);
		alipayShareParam.setSingleShareMoney(singleShareMoney);
		alipayShareParam.setShareThreshold(shareThreshold);
		alipayShareParam.setFreeratio(freeratio);
		alipayShareParam.setMcht_shareval(mcht_shareval);
		alipayShareParam.setOrgshareval_first(orgshareval_first);
		alipayShareParam.setOrgshareval_second(orgshareval_second);
		alipayShareParam.setOrgshareval_three(orgshareval_three);
		share_ruleid = InformationUtil.getDiscountRuleId();
		alipayShareParam.setShare_ruleid(share_ruleid);
		alipayShareParam.setShare_rulename(share_rulename);
		alipayShareParam.setSharetype(sharetype);
		alipayShareParam.setShare_rulestatus(share_rulestatus);
		//add to database
		t10214BO.add(alipayShareParam);

		return Constants.SUCCESS_CODE;
	}

	private String delete() {
		AlipayShareParam alipayShareParam = new AlipayShareParam();
		alipayShareParam.setShare_ruleid(share_ruleid);
		t10214BO.delete(alipayShareParam);
		return Constants.SUCCESS_CODE;
	}

	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//		jsonBean.parseJSONArrayData(getParameterList());
//		int len = jsonBean.getArray().size();
//		AlipayShareParam alipayShareParam = null;
//
		List<AlipayShareParam> alipayShareParamList = new ArrayList<AlipayShareParam>(1);
//
//		for(int i = 0; i < len; i++) {
//			alipayShareParam = new AlipayShareParam();
//			jsonBean.setObject(jsonBean.getJSONDataAt(i));
//			BeanUtils.setObjectWithPropertiesValue(alipayShareParam, jsonBean,false);
//			BeanUtils.setNullValueWithValue(alipayShareParam, "-", 0);
//			alipayShareParamList.add(alipayShareParam);
//		}
		AlipayShareParam alipayShareParam = new AlipayShareParam();
		alipayShareParam.setShare_ruleid(share_ruleid);
		alipayShareParam.setShare_rulestatus(share_rulestatus);
		alipayShareParam.setFreeratio(freeratio);
		alipayShareParam.setMcht_shareval(mcht_shareval);
		alipayShareParam.setOrgshareval_first(orgshareval_first);
		alipayShareParam.setOrgshareval_second(orgshareval_second);
		alipayShareParam.setOrgshareval_three(orgshareval_three);
		alipayShareParam.setShare_rulename(share_rulename);
		alipayShareParam.setShareStandardTotal(shareStandardTotal);
		alipayShareParam.setShareThreshold(shareThreshold);
		alipayShareParam.setSharetype(sharetype);
		alipayShareParam.setBrhId(brhId);
		
		alipayShareParamList.add(alipayShareParam);
		t10214BO.update(alipayShareParamList);

		return Constants.SUCCESS_CODE;
	}
//	private String paramId;
	private String brhId;
	private int shareStandardTotal;
	private float singleShareMoney;
	private float shareThreshold;
	private String parameterList;
	
	private String share_ruleid;
	private String share_rulename;
	private String freeratio;
	private String sharetype;
	private String mcht_shareval;
	private String orgshareval_first;
	private String orgshareval_second;
	private String orgshareval_three;
	private String share_rulestatus;
	
	public String getShare_rulestatus() {
			return share_rulestatus;
		}

	public void setShare_rulestatus(String share_rulestatus) {
		this.share_rulestatus = share_rulestatus;
	}

	//	public String getParamId() {
//		return paramId;
//	}
//	public void setParamId(String paramId) {
//		this.paramId = paramId;
//	}
	public String getBrhId() {
		return brhId;
	}
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}
	public int getShareStandardTotal() {
		return shareStandardTotal;
	}
	public void setShareStandardTotal(int shareStandardTotal) {
		this.shareStandardTotal = shareStandardTotal;
	}
	public float getSingleShareMoney() {
		return singleShareMoney;
	}
	public void setSingleShareMoney(float singleShareMoney) {
		this.singleShareMoney = singleShareMoney;
	}
	public float getShareThreshold() {
		return shareThreshold;
	}
	public void setShareThreshold(float shareThreshold) {
		this.shareThreshold = shareThreshold;
	}
	public String getParameterList() {
		return parameterList;
	}
	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}

	
	public String getShare_ruleid() {
		return share_ruleid;
	}

	public void setShare_ruleid(String share_ruleid) {
		this.share_ruleid = share_ruleid;
	}

	public String getShare_rulename() {
		return share_rulename;
	}

	public void setShare_rulename(String share_rulename) {
		this.share_rulename = share_rulename;
	}

	public String getFreeratio() {
		return freeratio;
	}

	public void setFreeratio(String freeratio) {
		this.freeratio = freeratio;
	}

	public String getSharetype() {
		return sharetype;
	}

	public void setSharetype(String sharetype) {
		this.sharetype = sharetype;
	}

	public String getMcht_shareval() {
		return mcht_shareval;
	}

	public void setMcht_shareval(String mcht_shareval) {
		this.mcht_shareval = mcht_shareval;
	}

	public String getOrgshareval_first() {
		return orgshareval_first;
	}

	public void setOrgshareval_first(String orgshareval_first) {
		this.orgshareval_first = orgshareval_first;
	}

	public String getOrgshareval_second() {
		return orgshareval_second;
	}

	public void setOrgshareval_second(String orgshareval_second) {
		this.orgshareval_second = orgshareval_second;
	}

	public String getOrgshareval_three() {
		return orgshareval_three;
	}

	public void setOrgshareval_three(String orgshareval_three) {
		this.orgshareval_three = orgshareval_three;
	}
	
}
