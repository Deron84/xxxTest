/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-9-28       first release
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

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.system.util.CommonFunction;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-28
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T70102 {
	
	
	public String getStatus(HttpServletRequest request,HttpServletResponse response){
		
		
		try {
			String sql = "select RESERVE1,RESERVE2 from TBL_PAPER_DEF_INF where PAPER_ID = 'PAPER_STATUS'";
			List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				Object[] obj = (Object[]) list.get(0);
				//正常状态-0
				//锁定状态-1
				if (obj[0].equals("0")) {
					return "T00000000";
				} else if (obj[0].equals("1")) {
					//判断是否为当前柜员锁定
					Operator opr = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
					if (obj[1].equals(opr.getOprId())) {
						return "L" + obj[1].toString();
					} else {
						return "U" + obj[1].toString();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "E获取当前问卷状态异常";
	}
	
	public String getCount(HttpServletRequest request,HttpServletResponse response){
		try {
			String sql = "select nvl(sum(max(point)),0) from TBL_PAPER_OPT_INF group by QUES_ID";
			BigDecimal bg = (BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0);
			int count = bg.intValue();
			if (count != 100) {
				return "<font color='red'>" + bg.toString() + "%</font>";
			} else {
				return "<font color='green'>" + bg.toString() + "%</font>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "<font color='red'>异常</font>";
	}

}
