package com.huateng.dao.iface.mchnt;

import com.huateng.po.mchnt.TblMchtBaseInf;



public interface TblMchntInfoDAO {
	public TblMchtBaseInf get(java.lang.String key);

	public TblMchtBaseInf load(java.lang.String key);

	public java.util.List<TblMchtBaseInf> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblMchntInfo a transient instance of a persistent class 
	 * @return the class identifier
	 */
	
	public java.lang.String save(TblMchtBaseInf tblMchntInfo); 
	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblMchntInfo a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(TblMchtBaseInf tblMchntInfo);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchntInfo a transient instance containing updated state
	 */
	public void update(TblMchtBaseInf tblMchntInfo);
	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblMchntInfo the instance to be removed
	 */
	public void delete(TblMchtBaseInf tblMchntInfo);


}