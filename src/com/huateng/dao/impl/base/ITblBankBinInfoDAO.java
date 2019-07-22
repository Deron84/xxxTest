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
package com.huateng.dao.impl.base;

import com.huateng.po.TblBankBinInf;
import com.sdses.dao._RootDAO;


/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class ITblBankBinInfoDAO extends _RootDAO<com.huateng.po.TblBankBinInf> implements com.huateng.dao.iface.base.ITblBankBinInfoDAO{
	
	// query name references

	@SuppressWarnings("unchecked")
	public Class getReferenceClass() {
		return TblBankBinInf.class;
	}

	public TblBankBinInf get(Integer key) {
		return (TblBankBinInf) get(
				getReferenceClass(), key);
	}

	public TblBankBinInf load(Integer key) {
		return (TblBankBinInf) load(
				getReferenceClass(), new Integer(key));
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblBankBinInf
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public Integer save(TblBankBinInf TblBankBinInf) {
		return (Integer) super.save(TblBankBinInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblBankBinInf
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(
			TblBankBinInf TblBankBinInf) {
		super.saveOrUpdate(TblBankBinInf);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblBankBinInf
	 *            a transient instance containing updated state
	 */
	public void update(TblBankBinInf TblBankBinInf) {
		super.update(TblBankBinInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(Integer id) {
		super.delete(load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblBankBinInf
	 *            the instance to be removed
	 */
	public void delete(TblBankBinInf TblBankBinInf) {
		super.delete(TblBankBinInf);
	}

	/**
	 * Re-read the state of the given instance from the underlying database. It
	 * is inadvisable to use this to implement long-running sessions that span
	 * many business tasks. This method is, however, useful in certain special
	 * circumstances. For example
	 * <ul>
	 * <li>where a database trigger alters the object state upon insert or
	 * update</li>
	 * <li>after executing direct SQL (eg. a mass update) in the same session</li>
	 * <li>after inserting a Blob or Clob</li>
	 * </ul>
	 */
	public void refresh(TblBankBinInf TblBankBinInf) {
		super.refresh(TblBankBinInf);
	}


}
