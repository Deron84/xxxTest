/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-3-10       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
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
package com.huateng.system.util;

import java.util.ResourceBundle;

/**
 * Title:系统参数
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class SysParamUtil {

	private static String SYSPARAM_FILE = "SysParam";
	
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle(SYSPARAM_FILE);
	
	/**
	 * 获得系统参数
	 * @param key
	 * @return
	 */
	public static String getParam(String key) {
		return BUNDLE.getString(key);
	}
}
