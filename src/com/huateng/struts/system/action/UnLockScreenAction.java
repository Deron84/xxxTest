/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-9-14       first release
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

import com.huateng.dao.iface.base.TblOprInfoDAO;
import com.huateng.po.TblOprInfo;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.Encryption;

/**
 * Title:屏幕锁定解锁
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-9-14
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class UnLockScreenAction extends BaseAction {

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		TblOprInfoDAO tblOprInfoDAO = (TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
		
		TblOprInfo tblOprInfo = tblOprInfoDAO.get(lockOprId);
		
		// 操作员授权码验证
		if(tblOprInfo.getOprPwd().trim().equals(Encryption.encrypt(lockPassword).trim())) {
			writeSuccessMsg("解锁屏幕密码验证成功");
		} else {
			writeErrorMsg("您输入的密码不正确");
		}
		return SUCCESS;
	}
	
	// 操作员编号
	private String lockOprId;
	// 操作员授权码
	private String lockPassword;

	/**
	 * @return the lockOprId
	 */
	public String getLockOprId() {
		return lockOprId;
	}

	/**
	 * @param lockOprId the lockOprId to set
	 */
	public void setLockOprId(String lockOprId) {
		this.lockOprId = lockOprId;
	}

	/**
	 * @return the lockPassword
	 */
	public String getLockPassword() {
		return lockPassword;
	}

	/**
	 * @param lockPassword the lockPassword to set
	 */
	public void setLockPassword(String lockPassword) {
		this.lockPassword = lockPassword;
	}
	
	
}
