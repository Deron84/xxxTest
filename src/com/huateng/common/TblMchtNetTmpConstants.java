/* @(#)
 *
 * Project:spdb
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   赵兴财                          2011-3-2         first release
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
package com.huateng.common;

/**
 * Title:入网商户新增时的常量
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-3-2
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 赵兴财
 * 
 * @version 1.0
 */
public class TblMchtNetTmpConstants {
	public static final String  MCHT_OK = "0";			//入网商户正常状态
	public static final String  MCHT_ADD_CHECK = "1";   //入网待审核状态
	public static final String  MCHT_ADD_UNCHECK = "2";	//入网审核审核退回  
	public static final String  MCHT_REG_OK = "3";		//添加商户审核通过

}
