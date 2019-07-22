/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-6-15       first release
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
package com.huateng.dao.impl.mchnt;

import com.huateng.po.mchnt.CstMchtFeeInf;
import com.huateng.po.mchnt.CstMchtFeeInfPK;
import com.sdses.dao._RootDAO;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-15
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class CstMchtFeeInfDAO extends _RootDAO<com.huateng.po.TblDivMchnt> implements com.huateng.dao.iface.mchnt.CstMchtFeeInfDAO{
	

	// query name references




	public Class<CstMchtFeeInf> getReferenceClass () {
		return CstMchtFeeInf.class;
	}


	/**
	 * Cast the object as a CstMchtFeeInf
	 */
	public CstMchtFeeInf cast (Object object) {
		return (CstMchtFeeInf) object;
	}


	public CstMchtFeeInf load(CstMchtFeeInfPK key)
		throws org.hibernate.HibernateException {
		return (CstMchtFeeInf) load(getReferenceClass(), key);
	}

	public CstMchtFeeInf get(CstMchtFeeInfPK key)
		throws org.hibernate.HibernateException {
		return (CstMchtFeeInf) get(getReferenceClass(), key);
	}



	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param cstMchtFeeInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public CstMchtFeeInfPK save(CstMchtFeeInf cstMchtFeeInf)
		throws org.hibernate.HibernateException {
		return (CstMchtFeeInfPK) super.save(cstMchtFeeInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param cstMchtFeeInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(CstMchtFeeInf cstMchtFeeInf)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(cstMchtFeeInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param cstMchtFeeInf a transient instance containing updated state
	 */
	public void update(CstMchtFeeInf cstMchtFeeInf)
		throws org.hibernate.HibernateException {
		super.update(cstMchtFeeInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param cstMchtFeeInf the instance to be removed
	 */
	public void delete(CstMchtFeeInf cstMchtFeeInf)
		throws org.hibernate.HibernateException {
		super.delete((Object) cstMchtFeeInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(CstMchtFeeInfPK id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}


}
