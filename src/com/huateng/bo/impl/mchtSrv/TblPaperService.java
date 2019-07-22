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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.huateng.po.mchtSrv.TblPaperDefInf;
import com.huateng.po.mchtSrv.TblPaperDefInfPK;
import com.huateng.po.mchtSrv.TblPaperOptInf;

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
public interface TblPaperService {
	
	public String save(TblPaperDefInf inf, List<TblPaperOptInf> list) throws Exception;
	
	public String update(TblPaperDefInf inf, List<TblPaperOptInf> list) throws Exception;
	
	public String delete(TblPaperDefInf inf) throws Exception;
	
	public TblPaperDefInf getPaperDef(TblPaperDefInfPK id) throws Exception;
	
	public String active(String oprId) throws Exception;
	
	public String lock(String oprId) throws Exception;
	
	public String submitPaper(String result, String mchtId, String paperId, HttpServletRequest request) throws Exception;
}
