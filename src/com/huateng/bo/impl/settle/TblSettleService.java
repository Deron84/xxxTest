/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-9-8       first release
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
package com.huateng.bo.impl.settle;

import com.huateng.po.settle.TblInfileOpt;

/**
 * Title: 
 * 
 * File: TblSettleService.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public interface TblSettleService {
	
	public String makeFile(String brhId, String oprId) throws Exception;
	
	public TblInfileOpt getInf(String brhId, String today) throws Exception;
	
	public void update(TblInfileOpt inf) throws Exception;
	
	public String reset(TblInfileOpt inf, String brhId, String today) throws Exception;

}
