/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-7       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
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

import java.util.List;

import org.apache.commons.lang.StringUtils;


import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.dao.iface.base.TblOprInfoDAO;
import com.huateng.po.TblOprInfo;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.Encryption;



/**
 * Title: 
 * 
 * File: AuthoriseAction.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-7
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class AuthoriseAction extends BaseSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public String authorise(){
		
		try {
			if (StringUtil.isNull(username)) {
				return returnService("没有正确获得授权柜员号！");
			}
			if (StringUtil.isNull(pass)) {
				return returnService("没有正确获得授权柜员密码！");
			}
			if (StringUtil.isNull(txnCode)) {
				return returnService("没有正确获得该交易编号！");
			}
			
			//获取授权员工信息
			TblOprInfoDAO tblOprInfoDAO = (TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
			TblOprInfo user = tblOprInfoDAO.get(username);
			
			
			if (getOperator().getOprId().equals(username)) {
				return returnService("授权柜员号和当前柜员号不能相同！");
			}

			// Step 1 :判断授权员工的合法性
			if (user == null) {
				return returnService("该授权柜员不存在！");
			}
			// 判断授权员工号状态
			if("1".equals(user.getOprSta().trim())) {
				return returnService("授权柜员号已经被冻结，请与管理员联系！");
			}
			// 判断授权码是否过期
			if(Integer.parseInt(user.getPwdOutDate().trim()) <= Integer.parseInt(CommonFunction.getCurrentDate())) {
				return returnService("授权柜员号授权码已经过期，请进行修改！");
			}
			
			
			// Step 2 :判断员工所属机构是否相同
			if (!getOperator().getOprBrhId().equals(user.getBrhId())) {
				return returnService("授权柜员所属机构和当前操作柜员所属机构不相同，不能进行授权！");
			}
			
			
			// Step 3 :判断员工是否该交易的权限并验证授权码
			String txn = "select KEY_ID from TBL_ROLE_FUNC_MAP "
					+ "where KEY_ID = '" + user.getOprDegree().trim()
					+ "' and VALUE_ID = '" + txnCode.trim() + "'";
			List list = commQueryDAO.findBySQLQuery(txn);
			if (list != null && !list.isEmpty()) {
				// 验证授权码
				String oprPassword = StringUtils.trim(user.getOprPwd());
				pass = StringUtils.trim(Encryption.encrypt(pass));
				if (pass.equals(oprPassword)) {
					return returnService(Constants.SUCCESS_CODE);
				} else {
					return returnService("授权员工密码验证失败！");
				}
			} else {
				return returnService("该柜员不具备授权该交易的权限！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
	}

	
	public String txnCode;
	
	public String username;
	
	public String pass;


	/**
	 * @return the txnCode
	 */
	public String getTxnCode() {
		return txnCode;
	}

	/**
	 * @param txnCode the txnCode to set
	 */
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMsg() {
		return msg;
	}

	public boolean isSuccess() {
		return success;
	}

}
