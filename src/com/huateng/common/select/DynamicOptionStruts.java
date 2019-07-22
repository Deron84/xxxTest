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

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

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
 * 
 * 
 * 设计思路：
 * 1.数据动态获取，前台传入前端匹配的参数
 * 2.不再使用ID作为和后台匹配的条件，而直接使用method name
 * 3.参数包括start,limit,inputValue,methodName
 * 
 */
public class DynamicOptionStruts extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(DynamicOptionStruts.class);
	public String loadComboStruts() {
		String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";
		
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//获得操作员信息
			Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
			if (null == operator) {
				writeMessage(jsonData);
			}
			int startInt = 0;
			int limitInt = Constants.QUERY_SELECT_COUNT;
			if (!StringUtil.isNull(start)) {
				startInt = Integer.parseInt(start);
			}
			if (!StringUtil.isNull(limit)) {
				limitInt = Integer.parseInt(limit);
			}
			if(inputValue.indexOf("_AS") != -1) {
				writeMessage(DynamicOptionHandler.handle(startInt, limitInt, inputValue.substring(3), methodName + "_as", operator, request));
			} else {
				writeMessage(DynamicOptionHandler.handle(startInt, limitInt, inputValue, methodName, operator, request));
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
	
	private String start;
	private String limit;
	private String inputValue;
	private String methodName;

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}


	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}


	/**
	 * @return the limit
	 */
	public String getLimit() {
		return limit;
	}


	/**
	 * @param limit the limit to set
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}


	/**
	 * @return the inputValue
	 */
	public String getInputValue() {
		return inputValue;
	}


	/**
	 * @param inputValue the inputValue to set
	 */
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}


	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}


	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
