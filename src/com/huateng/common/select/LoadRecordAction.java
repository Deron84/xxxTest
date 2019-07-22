/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-21       first release
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
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Title:数据载入类
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-21
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class LoadRecordAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static LoadRecordMethod loadRecordMethod = new LoadRecordMethod();

	/**
	 * 载入数据
	 * 
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws IOException
	 * 2011-6-22下午03:54:16
	 */
	public String loadRecord() throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, IOException {

		try {
			String str = (String) LoadRecordMethod.class.getMethod(storeId, HttpServletRequest.class)
					.invoke(loadRecordMethod, new Object[] {ServletActionContext.getRequest()});
			
			if (!StringUtil.isNull(str)) {
				writeMessage(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param jsonData
	 * @throws IOException
	 *             2011-6-21下午04:18:01
	 */
	private void writeMessage(String jsonData) throws IOException {
		PrintWriter printWriter = ServletActionContext.getResponse()
				.getWriter();
		printWriter.write(jsonData);
		printWriter.flush();
		printWriter.close();
	}

	private String storeId;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

}
