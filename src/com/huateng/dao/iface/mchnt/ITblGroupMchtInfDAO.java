/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-21       first release
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
package com.huateng.dao.iface.mchnt;

import com.huateng.po.mchnt.TblGroupMchtInf;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-21
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public interface ITblGroupMchtInfDAO {
	
	public Class<TblGroupMchtInf> getReferenceClass ();

	public TblGroupMchtInf cast (Object object);


	public TblGroupMchtInf load(java.lang.String key)
		throws org.hibernate.HibernateException;

	public TblGroupMchtInf get(java.lang.String key)
		throws org.hibernate.HibernateException;


	public java.lang.String save(TblGroupMchtInf tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblGroupMchtInf tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	public void update(TblGroupMchtInf tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	public void delete(TblGroupMchtInf tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException;
}