/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-30       first release
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
package com.huateng.common;

/**
 * Title:终端信息宏定义
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class TblTermInfoConstants {
	
	/**终端状态-可用*/
	public static final String TERM_ST_OK = "0";
	/**终端状态-添加待审核*/
	public static final String TERM_ST_NEW_UNCK = "1";
	/**终端状态-添加审核退回*/
	public static final String TERM_ST_NEW_UNCK_BACK = "2";
	/**终端状态-修改待审核*/
	public static final String TERM_ST_MODI_UNCK = "3";
	/**终端状态-修改审核退回*/
	public static final String TERM_ST_MODI_UNCK_BACK = "4";
	/**终端状态-停用待审核*/
	public static final String TERM_ST_STOP_UNCK = "5";
	/**终端状态-停用*/
	public static final String TERM_ST_STOP = "6";
	/**终端状态-恢复待审核*/
	public static final String TERM_ST_RCV_UNCK = "7";
	/**终端密钥未下发标识*/
	public static final String TERM_KEY_ST_UNLOAD = "0";
	/**终端密钥已下发标识*/
	public static final String TERM_KEY_ST_LOADED = "1";
	/**终端拨入方式-GPRS*/
	public static final String TERM_CALL_TYPE_GPRS = "00";
	/**终端拨入方式-CDMA*/
	public static final String TERM_CALL_TYPE_CDMA = "01";
	/**终端拨入方式-其他*/
	public static final String TERM_CALL_TYPE_OTHER = "02";
	/**终端加解密方式-加密机加密*/
	public static final String TERM_ENCTYPE_HARD = "0";
	/**终端加解密方式-软件加密*/
	public static final String TERM_ENCTYPE_SOFT = "1";
}
