/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-9-7       first release
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
package com.huateng.struts.query.action;

import java.io.File;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T50203Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-7
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T50203Action extends BaseSupport{

	private static final long serialVersionUID = 1L;

	public String download(){
		
		try {
			if (StringUtil.isNull(mon) || StringUtil.isNull(brhId)) {
				return returnService(Constants.ERR_ATTRIBUTE);
			}
			
			mon += "01";
			
			String path = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_REPORT);
			path += mon;
			path += "/";
			path += brhId;
			path += "/MCHTREPORT";
			path += brhId + "_" + mon;
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
	
	
	private String mon;
	private String brhId;
	
	
	
	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
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
