/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-6-23       first release
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
package com.huateng.dwr.mchnt;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.ContextUtil;

/**
 * Title: 
 * 
 * File: RemoteTransMethod.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-23
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class RemoteTransMethod {
	
	public static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	/**
	 * mcc码翻译
	 * 
	 * @param val
	 * @return
	 */
	public String mcc(String val){
		
		String sql = "select descr from tbl_inf_mchnt_tp where trim(mchnt_tp) = '" +  val.trim() + "'";
		
		return findData(sql,val);
	}
	
	
	/**
	 * 机构名称翻译
	 * 
	 * @param val
	 * @return
	 */
	public String brh(String val){
		
		String sql = "select brh_name from tbl_brh_info where trim(brh_id) = '" +  val.trim() + "'";
		
		return findData(sql,val);
	}
	
	/**
	 * 翻译商户名称
	 * @param val
	 * @return
	 * 2011-8-4下午06:00:20
	 */
	public String mchntName(String val){
		String sql = "select MCHT_NO||'-'||MCHT_NM from TBL_MCHT_BASE_INF_TMP where TRIM(MCHT_NO) = '" +  StringUtils.trim(val) + "'";
		return findData(sql,val);
	}
	
	/**
	 * 翻译机构名称
	 * @param val
	 * @return
	 * 2011-8-4下午06:00:20
	 */
	public String brhName(String val){
		String sql = "select BRH_ID||'-'||BRH_NAME from TBL_BRH_INFO where TRIM(BRH_ID) = '" +  StringUtils.trim(val) + "'";
		return findData(sql,val);
	}
	
	
	/**
	 * 简单sql语句执行
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findData(String sql,String val){
		
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
			return list.get(0).toString();
		} else {
			return val;
		}
	}
	
	/**
	 * 翻译第三方服务机构名称
	 * @param val
	 * @return
	 * 2011-8-4下午06:00:20
	 */
	public String organName(String val){
		String sql = "select ORG_ID||'-'||ORG_NAME from TBL_PROFESSIONAL_ORGAN where TRIM(ORG_ID) = '" +  StringUtils.trim(val) + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
//		Iterator iter = list.iterator();
//		while(iter.hasNext()){
//			System.out.println(iter.next());
//		}
//		System.out.println("=================");
		if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
			return list.get(0).toString();
		} else {
			return val;
		}
//		return findData(sql,val);
	}
	
	/**
	 * 终端厂商
	 * @param val
	 * @return
	 * 2011-8-4下午06:00:20
	 */
	public String MANUFATURER(String val){
		String sql = "select VALUE from CST_SYS_PARAM where TRIM(OWNER) ='zdcs'  and KEY = '" +  StringUtils.trim(val) + "'";
		return findData(sql,val);
	}
	
	/**
	 * 在参数表CST_SYS_PARAM配置的通用转译
	 * @param val
	 * @return
	 * 2011-8-4下午06:00:20
	 */
	public String sysPara(String val){
		if(val.indexOf("-") == -1){
			val = "-";
		}
		String sql = "select VALUE from CST_SYS_PARAM where TRIM(OWNER) ='"+val.substring(0,val.indexOf("-"))+"'  and KEY = '" +  val.substring(val.indexOf("-")+1) + "'";
		return findData(sql,val);
	}

	/**
	 * 终端类型
	 * @param val
	 * @return
	 * 2011-8-4下午06:00:20
	 */
	public String termTp(String val){
		String sql = "select DESCR from TBL_TERM_TP where TERM_TP = '" +  StringUtils.trim(val) + "'";
		return findData(sql,val);
	}
}

