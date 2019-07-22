/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-8-3       first release
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
package com.huateng.struts.settle.action;

import java.io.File;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T80104Acion.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-3
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T80105Action extends BaseSupport{
	
	private static final long serialVersionUID = 1L;

	public String download(){
		
		try {
			if (StringUtil.isNull(date) || StringUtil.isNull(reportName)) {
				return returnService(Constants.ERR_ATTRIBUTE);
			}
			String path = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_REPORT);
			path += date;
			path += "/";
			path += brhId;
			path += "/";
			path += reportName + brhId + "_" + date;
			path += ".txt";
			path = path.replace("\\", "/");
			log("GET FILE:" + path);
			
			File down = new File(path);
			if (down.exists()) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + path);
			} else {
				return returnService("您所请求的报表文件不存在!");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}
	
	private String brhId;
	
	private String reportName;
	
	private String date;

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

}
