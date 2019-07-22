/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-9-26       first release
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
package com.huateng.bo.impl.mchtSrv;

import com.huateng.common.Constants;
import com.huateng.dao.iface.mchtSrv.TblLevelDefInfDAO;
import com.huateng.po.mchtSrv.TblLevelDefInf;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-26
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class TblLevelServiceImpl implements TblLevelService{

	
	public String save(TblLevelDefInf inf) throws Exception {
		
		tblLevelDefInfDAO.save(inf);
		
		return Constants.SUCCESS_CODE;
	}

	public TblLevelDefInfDAO tblLevelDefInfDAO;

	public TblLevelDefInfDAO getTblLevelDefInfDAO() {
		return tblLevelDefInfDAO;
	}

	public void setTblLevelDefInfDAO(TblLevelDefInfDAO tblLevelDefInfDAO) {
		this.tblLevelDefInfDAO = tblLevelDefInfDAO;
	}
	
	
}
