package com.huateng.dao.iface.term;

import java.io.Serializable;

public interface TblTermRefuseDAO {
	public com.huateng.po.TblTermRefuse get(com.huateng.po.TblTermRefusePK key);

	public com.huateng.po.TblTermRefuse load(com.huateng.po.TblTermRefusePK key);

	public java.util.List<com.huateng.po.TblTermRefuse> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblTermRefuse a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.huateng.po.TblTermRefusePK save(com.huateng.po.TblTermRefuse tblTermRefuse);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblTermRefuse a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.TblTermRefuse tblTermRefuse);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTermRefuse a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblTermRefuse tblTermRefuse);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.TblTermRefusePK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblTermRefuse the instance to be removed
	 */
	public void delete(com.huateng.po.TblTermRefuse tblTermRefuse);


}