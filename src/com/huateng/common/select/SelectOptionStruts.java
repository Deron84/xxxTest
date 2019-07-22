/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-6-15       first release
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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.system.util.JSONBean;
import com.opensymphony.xwork2.ActionSupport;



/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-15
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class SelectOptionStruts extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private Logger log = Logger.getLogger(SelectOptionStruts.class);
	
	/**
	 * Struts版
	 * @param txnId
	 * @return
	 */
	public String loadComboStruts() {
		String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";
		
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//获得操作员信息
			Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
			LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(getID(), new Object[]{operator});
			Iterator<String> iter = dataMap.keySet().iterator();
			if(iter.hasNext()) {
				Map<String, Object> jsonDataMap = new HashMap<String, Object>();
				LinkedList<Object> jsonDataList = new LinkedList<Object>();
				Map<String, String> tmpMap = null;
				String key = null;
				while(iter.hasNext()) {
					tmpMap = new LinkedHashMap<String, String>();
					key = iter.next();
					tmpMap.put("valueField", key);
					tmpMap.put("displayField", dataMap.get(key));
					jsonDataList.add(tmpMap);
				}
				jsonDataMap.put("data", jsonDataList);
				jsonData = JSONBean.genMapToJSON(jsonDataMap);
				writeMessage(jsonData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return SUCCESS;
	}


	/**
	 * @param jsonData
	 * 2011-6-15上午11:28:37
	 * @throws IOException 
	 */
	private void writeMessage(String jsonData) throws IOException {
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(jsonData);
		printWriter.flush();
		printWriter.close();
	}


	public String getID() {
		return ID;
	}


	public void setID(String id) {
		ID = id;
	}

}
