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

import java.util.Date;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;

/**
 * Title: 操作员退出
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
public class LogoutAction extends BaseAction {

	@Override
	protected String subExecute() throws Exception {
		
		log("USER:[" + operator.getOprId() + "] LOGOUT.");
		
		// 删除操作员信息
		removeSessionAttribute(Constants.OPERATOR_INFO);
		// 删除操作员信息
		removeSessionAttribute(Constants.OPERATOR_ID);
		// 删除菜单树信息
		removeSessionAttribute(Constants.TREE_MENU_MAP);
		// 删除工具栏信息
		removeSessionAttribute(Constants.TOOL_BAR_STR);
		
		Cookie[] cookies = getRequest().getCookies(); 
		try 
		{ 
		     for(int i=0;i<cookies.length;i++)   
		     { 
		      Cookie cookie = new Cookie("spdb",null); 
		      cookie.setMaxAge(0); 
		      //cookie.setPath("/");//根据你创建cookie的路径进行填写     
		      ServletActionContext.getResponse().addCookie(cookie);
		     } 
		}catch(Exception ex) 
		{ 
			log(ex.toString());
		}
		return null;
	}

}
