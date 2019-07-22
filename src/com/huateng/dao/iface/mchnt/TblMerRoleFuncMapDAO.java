/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-6-14       first release
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

import com.huateng.po.mchnt.TblMerRoleFuncMap;
import com.huateng.po.mchnt.TblMerRoleFuncMapId;



/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-14
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public interface TblMerRoleFuncMapDAO {
	
	public TblMerRoleFuncMap get(TblMerRoleFuncMapId key);

	public TblMerRoleFuncMap load(TblMerRoleFuncMapId key);

	public java.util.List<TblMerRoleFuncMap> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblMchntInfo a transient instance of a persistent class 
	 * @return the class identifier
	 */
	
	public TblMerRoleFuncMapId save(TblMerRoleFuncMap tblMchntInfo); 
	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblMchntInfo a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(TblMerRoleFuncMap tblMchntInfo);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchntInfo a transient instance containing updated state
	 */
	public void update(TblMerRoleFuncMap tblMchntInfo);
	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(TblMerRoleFuncMapId id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblMchntInfo the instance to be removed
	 */
	public void delete(TblMerRoleFuncMap tblMchntInfo);

	/**
	 * @param tblRoleFuncMap
	 */
	public void merge(TblMerRoleFuncMap tblRoleFuncMap);


}
