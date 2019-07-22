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
import com.huateng.struts.system.action.BaseAction;
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
public class T80104Action extends BaseAction{
	
	@Override
	protected String subExecute() throws Exception {
		if ("down".equals(method)) {
			return down();
		}
		return rspCode;
	}
	
	private static final long serialVersionUID = 1L;
	
	public String down(){
		
		try {
			if (StringUtil.isNull(date) || StringUtil.isNull(reportName)) {
				return Constants.ERR_ATTRIBUTE;
			}
			String path = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_REPORT);
			path += date + "/";

			String filename = "";
			if("ONE".equals(reportName)){
				filename = "ONE"+date.substring(2, 8)+"01EXPREQ";
				filename += ".txt";
			}else if("mchtBill".equals(reportName)){
				filename = date + "商户入账明细" + ".tar";
			}else{
				filename = reportName+ "_" + date;
				filename += ".txt";
			}
			path = path.replace("\\", "/");
			
			String file = path+filename;

			log("GET FILE:" + file);
			
			File down = new File(file);
			if (down.exists()) {
				writeSuccessMsg(file);
				return Constants.SUCCESS_CODE;
			} else {
				return "您所请求的报表文件不存在!";
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			return "对不起，本次操作失败!";
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
				return Constants.ERR_ATTRIBUTE;
			}
			reportName = "batch";
			
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
				return Constants.SUCCESS_CODE;
			} else {
				return "您所请求的报表文件不存在!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "对不起，本次操作失败!";
		}
	}
	
	private String brhId;
	
	private String HmchntId;
	
	private String reportName;
	
	private String date;

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

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}


	public String getHmchntId() {
		return HmchntId;
	}

	public void setHmchntId(String hmchntId) {
		HmchntId = hmchntId;
	}

}
