/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-31       first release
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
package com.huateng.struts.system.action;

import com.huateng.common.SysParamConstants;
import com.huateng.dao.iface.base.TblOprInfoDAO;
import com.huateng.po.TblOprInfo;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.Encryption;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 操作员授权码修改
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-31
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ResetPwdAction extends BaseAction {

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		log("操作员[ " + resetOprId + " ]修改授权码");
		
		TblOprInfoDAO tblOprInfoDAO = (TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
		
		TblOprInfo tblOprInfo = tblOprInfoDAO.get(resetOprId);
		
		// 判断原授权码是否输入正确
		if(!tblOprInfo.getOprPwd().trim().equals(Encryption.encrypt(resetPassword).trim())) {
			log("原始密码码输入不正确");
			writeErrorMsg("原密码输入不正确");
			return SUCCESS;
		}
		
		tblOprInfo.setOprPwd(Encryption.encrypt(resetPassword1).trim());
		tblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(
						CommonFunction.getCurrentDate(), SysParamUtil.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		tblOprInfo.setPwdWrLastDt("-");
		tblOprInfo.setPwdWrTm("0");
		tblOprInfo.setPwdWrTmTotal("0");
		tblOprInfoDAO.update(tblOprInfo);
		log("修改授权码成功");
		writeSuccessMsg("您的密码已经修改成功，请牢记新密码并使用新密码登录");
		return SUCCESS;
	}
	
	// 操作员编号
	private String resetOprId;
	// 操作员原始授权码
	private String resetPassword;
	// 操作员新授权码
	private String resetPassword1;
	/**
	 * @return the resetOprId
	 */
	public String getResetOprId() {
		return resetOprId;
	}

	/**
	 * @param resetOprId the resetOprId to set
	 */
	public void setResetOprId(String resetOprId) {
		this.resetOprId = resetOprId;
	}

	/**
	 * @return the resetPassword
	 */
	public String getResetPassword() {
		return resetPassword;
	}

	/**
	 * @param resetPassword the resetPassword to set
	 */
	public void setResetPassword(String resetPassword) {
		this.resetPassword = resetPassword;
	}

	/**
	 * @return the resetPassword1
	 */
	public String getResetPassword1() {
		return resetPassword1;
	}

	/**
	 * @param resetPassword1 the resetPassword1 to set
	 */
	public void setResetPassword1(String resetPassword1) {
		this.resetPassword1 = resetPassword1;
	}
}
