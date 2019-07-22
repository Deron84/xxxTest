/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-31       first release
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
 * Title: 清算报表
 * 
 * File: T80206Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-31
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T80206Action extends BaseSupport{

	private static final long serialVersionUID = 1L;

	/**
	 * 下载报表
	 * 
	 * @return
	 * 2011-8-2下午02:23:26
	 */
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
	
	
	/**
	 * 打包下载
	 * 
	 * @return
	 */
	public String downloadAll(){
		
		try {
			if (StringUtil.isNull(date) || StringUtil.isNull(brhId)) {
				return returnService(Constants.ERR_ATTRIBUTE);
			}
			reportName = "settle";
			
			String path = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_REPORT);
			path += date;
			path += "/";
			path += brhId;
			path += "/";
			path += date + "_" + brhId + "_" + reportName;
			path += ".tar";
			
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
	
	private String date;
	
	private String reportType;
	
	private String reportName;
	
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return msg;
	}

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return success;
	}
	
	
	
	

}
