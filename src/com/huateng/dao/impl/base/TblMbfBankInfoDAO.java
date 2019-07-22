package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.po.TblMbfBankInfo;
import com.sdses.dao._RootDAO;;


public class TblMbfBankInfoDAO extends _RootDAO<com.huateng.po.TblMbfBankInfo> implements com.huateng.dao.iface.base.TblMbfBankInfoDAO {

	public TblMbfBankInfoDAO () {}
	
	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblMbfBankInfoDAO#findAll()
	 */
	public List<TblMbfBankInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<com.huateng.po.TblMbfBankInfo> getReferenceClass () {
		return com.huateng.po.TblMbfBankInfo.class;
	}


	/**
	 * Cast the object as a com.huateng.po.brh.TblMbfBankInfo
	 */
	public com.huateng.po.TblMbfBankInfo cast (Object object) {
		return (com.huateng.po.TblMbfBankInfo) object;
	}


	public com.huateng.po.TblMbfBankInfo load(java.lang.String key)
	{
		return (com.huateng.po.TblMbfBankInfo) load(getReferenceClass(), key);
	}

	public com.huateng.po.TblMbfBankInfo get(java.lang.String key)
	{
		return (com.huateng.po.TblMbfBankInfo) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param TblMbfBankInfo a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.TblMbfBankInfo tblMbfBankInfo)
	{
		return (java.lang.String) super.save(tblMbfBankInfo);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMbfBankInfo a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.TblMbfBankInfo tblMbfBankInfo)
	{
		super.saveOrUpdate(tblMbfBankInfo);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMbfBankInfo a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblMbfBankInfo tblMbfBankInfo)
	{
		super.update(tblMbfBankInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMbfBankInfo the instance to be removed
	 */
	public void delete(com.huateng.po.TblMbfBankInfo tblMbfBankInfo)
	{
		super.delete((Object) tblMbfBankInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}
}