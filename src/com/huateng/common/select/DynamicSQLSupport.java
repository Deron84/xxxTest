/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-10-26       first release
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
package com.huateng.common.select;

import com.huateng.common.StringUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-10-26
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class DynamicSQLSupport {

	/**
	 * 组装适用于like关键字的sql
	 * @param srcSql
	 * @param field
	 * @param inputValue
	 * @return
	 * 2011-10-26下午05:48:25
	 */
	protected static String provideSqlLike(String srcSql, String field,
			String inputValue) {
		if (!StringUtil.isNull(inputValue)) {
			if (srcSql.toLowerCase().indexOf("where") != -1) {
				return " and " + field + " like '%" + inputValue.trim() + "%'";
			} else {
				return " where " + field + " like '%" + inputValue.trim() + "%'";
			}
		} else {
			return "";
		}
	}
	/**
	 * 模糊查询
	 * @param srcSql
	 * @param field
	 * @param inputValue
	 * @return
	 */
	protected static String provideSql(String srcSql, String field,
			String inputValue) {
		if (!StringUtil.isNull(inputValue) && !inputValue.trim().equals("-")) {
			if (srcSql.toLowerCase().indexOf("where") != -1) {
				return " and instr("+field+",'"+inputValue.trim()+"')>0";
			} else {
				return " where instr("+field+",'"+inputValue.trim()+"')>0";
			}
		} else {
			return "";
		}
	}
	
	
	/**
	 * 组装适用于like关键字的sql,前后匹配
	 * @param srcSql
	 * @param field
	 * @param inputValue
	 * @return
	 * 2011-10-26下午05:48:25
	 */
	protected static String provideSqlDouLike(String srcSql, String field,
			String inputValue) {
		if (!StringUtil.isNull(inputValue)) {
			if (srcSql.toLowerCase().indexOf("where") != -1) {
				return " and " + field + " like '%" + inputValue.trim() + "%'";
			} else {
				return " where " + field + " like '%" + inputValue.trim() + "%'";
			}
		} else {
			return "";
		}
	}
	
	/**
	 * 组装适用于in关键字的sql
	 * @param srcSql
	 * @param field
	 * @param inputValue
	 * @return
	 * 2011-10-26下午05:48:43
	 */
	protected static String provideSqlIn(String srcSql, String field,
			String inputValue) {
		if (!StringUtil.isNull(inputValue)) {
			if (srcSql.toLowerCase().indexOf("where") != -1) {
				return " and " + field + " in " + inputValue.trim() + "";
			} else {
				return " where " + field + " in " + inputValue.trim() + "";
			}
		} else {
			return "";
		}
	}
}
