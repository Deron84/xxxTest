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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huateng.bo.impl.mchtSrv.TblPaperService;
import com.huateng.common.StringUtil;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

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
public class T70104 {
	
	/**
	 * 获取问卷编号
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 2011-10-10上午10:37:55
	 */
	public String getPaperId(HttpServletRequest request,HttpServletResponse response){
		try {
			String sql = "select QUESTION from TBL_PAPER_DEF_INF where PAPER_ID = 'PAPER_STATUS'";
			List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			
			if (null != list && !list.isEmpty()) {
				if (!StringUtil.isNull(list.get(0))) {
					return "T" + list.get(0).toString();
				}
			}
			return "E" + "没有找到可用的问卷";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "E" + "获取问卷出现异常";
	}
	
	
	public String markPoint(String result, String mchtId, String paperId, HttpServletRequest request,HttpServletResponse response){
		
		try {
			//暂时未判断当前试卷是否有效，待确认是否需判断
			
			
			TblPaperService service = (TblPaperService) ContextUtil.getBean("TblPaperService");
			
			return service.submitPaper(result, mchtId, paperId, request);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return "生成商户级别异常[" + e.getMessage() + "]";
		}
		
	}
}
