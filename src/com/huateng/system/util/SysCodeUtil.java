/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-16       first release
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

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.huateng.common.StringUtil;

/**
 * 
 * Title:系统返回码信息
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-31
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class SysCodeUtil {
	
	private static String ERR_CODE_FILE = "errCode";
	
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle(ERR_CODE_FILE);
	/**
	 * 获取错误信息
	 * @param key
	 * @return
	 * 2011-7-1下午04:20:55
	 */
	public static String getErrCode(String key) {
		try {
			String msg = BUNDLE.getString(key);
			if (!StringUtil.isNull(msg)) {
				return msg;
			}
		} catch (MissingResourceException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return key;
	}
}
