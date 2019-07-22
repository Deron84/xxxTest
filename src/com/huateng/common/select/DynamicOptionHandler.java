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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.huateng.common.Operator;
import com.huateng.system.util.JSONBean;

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
public class DynamicOptionHandler {

	static String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";

	@SuppressWarnings("unchecked")
	public static String handle(int start, int limit, String inputValue,
			String methodName, Operator operator, HttpServletRequest request) {
		try {

			DynamicSqlBean bean = (DynamicSqlBean) DynamicSQL.class.getMethod(
					methodName,
					new Class[] { String.class, Operator.class,
							HttpServletRequest.class }).invoke(
					DynamicSQL.class,
					new Object[] { inputValue, operator, request });

			List<Object[]> dataList = bean.getDao().findBySQLQuery(
					bean.getSql(), start, limit);
			
			//没有数据直接返回
			if (null == dataList || dataList.size() == 0) {
				return jsonData;
			}
			
			String countSql = "select count(1) from (" + bean.getSql() + ")";

			String count = bean.getDao().findCountBySQLQuery(countSql);

			Iterator<Object[]> it = dataList.iterator();

			Map<String, Object> jsonDataMap = new HashMap<String, Object>();
			LinkedList<Object> jsonDataList = new LinkedList<Object>();
			Map<String, String> tmpMap = null;
			while (it.hasNext()) {
				tmpMap = new LinkedHashMap<String, String>();
				Object[] obj = it.next();
				if(obj[0]!=null){
					tmpMap.put("valueField", obj[0].toString());
				}
				if(obj[1]!=null){
					tmpMap.put("displayField", obj[1].toString());
				}
				jsonDataList.add(tmpMap);
			}
			jsonDataMap.put("data", jsonDataList);
			jsonDataMap.put("totalCount", count);
			return JSONBean.genMapToJSON(jsonDataMap);

		} catch (Exception e) {
			e.printStackTrace();
			return jsonData;
		}
	}

}
