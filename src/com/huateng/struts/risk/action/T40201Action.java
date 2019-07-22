/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-20       first release
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
package com.huateng.struts.risk.action;

import com.huateng.bo.risk.T40101BO;
import com.huateng.common.Constants;
import com.huateng.po.TblRiskInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:设置风险模型
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-20
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T40201Action extends BaseAction {
	
	T40101BO t40101BO = (T40101BO) ContextUtil.getBean("T40101BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		jsonBean.parseJSONArrayData(getModelDataList());
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			TblRiskInf tblRiskInf = new TblRiskInf();
			BeanUtils.setObjectWithPropertiesValue(tblRiskInf,jsonBean,true);
			//tblRiskInf.setSaLimitAmount(CommonFunction.transYuanToFen(tblRiskInf.getSaLimitAmount()));
			tblRiskInf.setModiZoneNo(operator.getOprBrhId());
			tblRiskInf.setModiOprId(operator.getOprId());
			tblRiskInf.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInf.setSaBranchCode(operator.getOprBrhId());
			
			TblRiskInf oldTblRiskInf = new TblRiskInf();
			oldTblRiskInf = t40101BO.get(tblRiskInf.getId());
			t40101BO.update(tblRiskInf, oldTblRiskInf, operator);
		}
		
		return Constants.SUCCESS_CODE;
	}
	
	private String saModelKind;
	
	private String saBeUse;
	
	private String saAction;
	
	private String saLimitNum;
	
	private String saLimitAmount;
	
	private String modelDataList;

	/**
	 * @return the saModelKind
	 */
	public String getSaModelKind() {
		return saModelKind;
	}

	/**
	 * @param saModelKind the saModelKind to set
	 */
	public void setSaModelKind(String saModelKind) {
		this.saModelKind = saModelKind;
	}

	/**
	 * @return the saBeUse
	 */
	public String getSaBeUse() {
		return saBeUse;
	}

	/**
	 * @param saBeUse the saBeUse to set
	 */
	public void setSaBeUse(String saBeUse) {
		this.saBeUse = saBeUse;
	}

	/**
	 * @return the saAction
	 */
	public String getSaAction() {
		return saAction;
	}

	/**
	 * @param saAction the saAction to set
	 */
	public void setSaAction(String saAction) {
		this.saAction = saAction;
	}

	/**
	 * @return the saLimitNum
	 */
	public String getSaLimitNum() {
		return saLimitNum;
	}

	/**
	 * @param saLimitNum the saLimitNum to set
	 */
	public void setSaLimitNum(String saLimitNum) {
		this.saLimitNum = saLimitNum;
	}

	/**
	 * @return the saLimitAmount
	 */
	public String getSaLimitAmount() {
		return saLimitAmount;
	}

	/**
	 * @param saLimitAmount the saLimitAmount to set
	 */
	public void setSaLimitAmount(String saLimitAmount) {
		this.saLimitAmount = saLimitAmount;
	}

	/**
	 * @return the modelDataList
	 */
	public String getModelDataList() {
		return modelDataList;
	}

	/**
	 * @param modelDataList the modelDataList to set
	 */
	public void setModelDataList(String modelDataList) {
		this.modelDataList = modelDataList;
	}
	
}
