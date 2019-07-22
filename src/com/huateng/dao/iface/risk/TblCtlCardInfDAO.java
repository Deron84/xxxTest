package com.huateng.dao.iface.risk;

import java.io.Serializable;

public interface TblCtlCardInfDAO {
	public com.huateng.po.TblCtlCardInf get(java.lang.String key);

	public com.huateng.po.TblCtlCardInf load(java.lang.String key);

	public java.util.List<com.huateng.po.TblCtlCardInf> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblCtlCardInf a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.TblCtlCardInf tblCtlCardInf);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblCtlCardInf a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.TblCtlCardInf tblCtlCardInf);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblCtlCardInf a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblCtlCardInf tblCtlCardInf);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblCtlCardInf the instance to be removed
	 */
	public void delete(com.huateng.po.TblCtlCardInf tblCtlCardInf);


}