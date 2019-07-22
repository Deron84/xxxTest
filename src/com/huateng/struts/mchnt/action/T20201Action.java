/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-5       first release
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
package com.huateng.struts.mchnt.action;


import java.util.List;

import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

import com.huateng.common.StringUtil;
/**
 * Title:商户信息审核
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20201Action extends BaseAction {
	

	private TblMchntService service = (TblMchntService) ContextUtil.getBean("TblMchntService");
	
	@Override
	protected String subExecute() throws Exception {
		
		//在新增、修改、停用和解冻时，CRT_OPR_ID均保存该交易的申请人（发起柜员），UPD_OPR_ID保存该交易的审核人
		String sql = "SELECT UPD_OPR_ID FROM Tbl_Mcht_Base_Inf_Tmp WHERE MCHT_NO = '" + mchntId + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
//		if (null != list && !list.isEmpty()) {
//			if (!StringUtil.isNull(list.get(0))) {
//				
//				if(list.get(0).equals(operator.getOprId())){
//					return "同一操作员不能审核！";
//				}
//			}
//		}

		log("审核商户编号：" + mchntId);
		
		if("accept".equals(method)) {
			rspCode = accept();
		} else if("back".equals(method)) {
			rspCode = back();
		} else if("refuse".equals(method)) {
			rspCode = refuse();
		}
		
		return rspCode;
	}
		
	/**
	 * 审核通过
	 * @return
	 * @throws Exception 
	 */
	private String accept() throws Exception {
		return service.accept(mchntId);
	}
	
	/**
	 * 审核退回
	 * @return
	 */
	private String back() throws Exception{
		return service.back(mchntId, refuseInfo);
	}
	
	/**
	 * 审核拒绝
	 * @return
	 * @throws Exception 
	 */
	private String refuse() throws Exception {
		return service.refuse(mchntId, refuseInfo);
	}
	
	
	// 商户编号
	private String mchntId;
	// 商户退回、拒绝的原因
	private String refuseInfo;
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

	/**
	 * @return the refuseInfo
	 */
	public String getRefuseInfo() {
		return refuseInfo;
	}

	/**
	 * @param refuseInfo the refuseInfo to set
	 */
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	
	
}
