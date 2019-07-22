package com.sdses.dao.iface;

import com.sdses.po.TblTxnInfo;
import com.sdses.po.TblTxnInfoPK;

public interface TblTxnInfoDAO2 {
	public TblTxnInfo get(TblTxnInfoPK key);

	public TblTxnInfo load(TblTxnInfoPK key);

	public java.util.List<TblTxnInfo> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblTxnInfo a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public TblTxnInfoPK save(TblTxnInfo tblTxnInfo);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblTxnInfo a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(TblTxnInfo tblTxnInfo);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTxnInfo a transient instance containing updated state
	 */
	public void update(TblTxnInfo tblTxnInfo);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(TblTxnInfoPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblTxnInfo the instance to be removed
	 */
	public void delete(TblTxnInfo tblTxnInfo);


}