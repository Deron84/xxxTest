/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-8       first release
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
package com.huateng.struts.base.action;

import java.util.Iterator;

import net.sf.json.JSONObject;

import com.huateng.bo.base.T10301BO;
import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

/**
 * Title: 
 * 
 * File: T10302Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T10302Action extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	//借用T10301交易的BO
	private T10301BO t10301BO = (T10301BO) ContextUtil.getBean("T10301BO");
	

	@Override
	protected String subExecute() throws Exception {
		try {
			if("add".equals(method)) { // 新增角色信息
				rspCode = add();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，交易授权配置" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	@SuppressWarnings("unchecked")
	private String add() throws Exception {
		
		// 交易信息
		jsonBean.parseJSONArrayData(getMenuList());
		
		Iterator<JSONObject> it = jsonBean.getArray().iterator();
		StringBuffer sb = new StringBuffer("(");
		while (it.hasNext()) {
			sb.append("'");
			sb.append(it.next().get("valueId").toString());
			sb.append("'");
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
//		System.out.println(sb.toString());
		
		rspCode = t10301BO.updateAuthorMenu(sb.toString());
		
		return Constants.SUCCESS_CODE;
	}
	
	public String menuList;

	/**
	 * @return the menuList
	 */
	public String getMenuList() {
		return menuList;
	}

	/**
	 * @param menuList the menuList to set
	 */
	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}

}
