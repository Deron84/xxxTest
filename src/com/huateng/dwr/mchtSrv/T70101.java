/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-10-11       first release
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
package com.huateng.dwr.mchtSrv;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huateng.common.StringUtil;
import com.huateng.system.util.CommonFunction;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-10-11
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T70101 {
	
	public String getPaperId(HttpServletRequest request,HttpServletResponse response){
		try {
			String sql = "select QUESTION from TBL_PAPER_DEF_INF where PAPER_ID = 'PAPER_STATUS'";
			List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			
			if (null != list && !list.isEmpty()) {
				if (!StringUtil.isNull(list.get(0))) {
					return list.get(0).toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
