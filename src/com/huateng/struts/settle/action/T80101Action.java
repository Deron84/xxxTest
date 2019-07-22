/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-27       first release
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

import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseSupport;

/**
 * Title: 
 * 
 * File: T80102.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-27
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T80101Action extends BaseSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String init(){
		
		rspCode = Constants.SUCCESS_CODE;
		
		return returnService(rspCode);
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
