/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-6-17       first release
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
package com.huateng.dao.iface.base;

import com.huateng.po.TblBrhTlrInfo;
import com.huateng.po.TblBrhTlrInfoPK;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public interface TblBrhTlrInfoDao {
	
	public abstract Class getReferenceClass();

	public abstract TblBrhTlrInfo get(TblBrhTlrInfoPK key);

	public abstract TblBrhTlrInfo load(TblBrhTlrInfoPK key);

    public abstract TblBrhTlrInfoPK save(TblBrhTlrInfo TblBrhTlrInfo);

    public abstract void saveOrUpdate(TblBrhTlrInfo TblBrhTlrInfo);

    public abstract void update(TblBrhTlrInfo TblBrhTlrInfo);

    public abstract void delete(TblBrhTlrInfoPK id);

    public abstract void delete(TblBrhTlrInfo TblBrhTlrInfo);

    public abstract void refresh(TblBrhTlrInfo TblBrhTlrInfo);

}
