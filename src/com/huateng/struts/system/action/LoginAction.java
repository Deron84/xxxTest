package com.huateng.struts.system.action;

import org.apache.commons.lang.StringUtils;

import com.huateng.common.SysParamConstants;
import com.huateng.dao.iface.base.TblOprInfoDAO;
import com.huateng.po.TblOprInfo;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.Encryption;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:系统登录
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-5-21
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = -915297506148396706L;

	/**操作员编号*/
	private String oprid;
	/**操作员授权码*/
	private String password;

	@Override
	protected String subExecute() throws Exception {
		
		TblOprInfoDAO tblOprInfoDAO = (TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
		TblOprInfo tblOprInfo = tblOprInfoDAO.get(oprid);
		
		//判断操作员是否存在
		if(tblOprInfo == null) {
			
			log("登录失败，操作员不存在。编号[ " + oprid + " ]");
			writeErrorMsg("登录失败，操作员不存在");
			return SUCCESS;
		}
		
		// 判断操作员合法性
		if("1".equals(tblOprInfo.getOprSta().trim())) {
			log("登录失败，操作员被冻结。编号[ " + oprid + " ]");
			writeErrorMsg("该操作员已经被冻结，请与管理员联系。");
			return SUCCESS;
		}
		
		// 判断授权码是否过期
		if(Integer.parseInt(tblOprInfo.getPwdOutDate().trim()) <= Integer.parseInt(CommonFunction.getCurrentDate())) {
			log("登录失败，操作员密码过期。编号[ " + oprid + " ]");
			writeAlertMsg("您的密码已经过期，请进行修改。", SysParamUtil.getParam(SysParamConstants.RESET_PWD));
			return SUCCESS;
		}
		
		//判断授权码输入是否正确
		String oprPassword = StringUtils.trim(tblOprInfo.getOprPwd());
		password = StringUtils.trim(Encryption.encrypt(password));
		if(!oprPassword.equals(password)) {
			
			log("登录失败，密码错误。编号[ " + oprid + " ]");
			writeErrorMsg("登录失败，您输入的密码错误");
			return SUCCESS;
		}
		
		setSessionAttribute("oprId", oprid);
		writeSuccessMsg("登录成功");
		return SUCCESS;
	}

	public String getOprid() {
		return oprid;
	}

	public void setOprid(String oprid) {
		this.oprid = oprid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
