package com.huateng.dao.iface.mchnt;

import com.huateng.po.mchnt.TblInfMchntTp;
import com.huateng.po.mchnt.TblInfMchntTpPK;



public interface TblMchntTpDAO {
	public TblInfMchntTp get(TblInfMchntTpPK key);

	public TblInfMchntTp load(TblInfMchntTpPK key);

	public java.util.List<TblInfMchntTp> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblMchntTp a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public TblInfMchntTpPK save(TblInfMchntTp tblMchntTp);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblMchntTp a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(TblInfMchntTp tblMchntTp);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchntTp a transient instance containing updated state
	 */
	public void update(TblInfMchntTp tblMchntTp);
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(TblInfMchntTpPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblMchntTp the instance to be removed
	 */
	public void delete(TblInfMchntTp tblMchntTp);


}