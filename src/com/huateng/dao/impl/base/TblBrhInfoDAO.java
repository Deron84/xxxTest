package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.po.TblBrhInfo;
import com.sdses.dao._RootDAO;


public class TblBrhInfoDAO extends _RootDAO<com.huateng.po.TblBrhInfo> implements com.huateng.dao.iface.base.TblBrhInfoDAO {

	public TblBrhInfoDAO () {}
	
	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblBrhInfoDAO#findAll()
	 */
	public List<TblBrhInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<com.huateng.po.TblBrhInfo> getReferenceClass () {
		return com.huateng.po.TblBrhInfo.class;
	}


	/**
	 * Cast the object as a com.huateng.po.brh.TblBrhInfo
	 */
	public com.huateng.po.TblBrhInfo cast (Object object) {
		return (com.huateng.po.TblBrhInfo) object;
	}


	public com.huateng.po.TblBrhInfo load(java.lang.String key)
	{
		return (com.huateng.po.TblBrhInfo) load(getReferenceClass(), key);
	}

	public com.huateng.po.TblBrhInfo get(java.lang.String key)
	{
		return (com.huateng.po.TblBrhInfo) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblBrhInfo a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.TblBrhInfo tblBrhInfo)
	{
		return (java.lang.String) super.save(tblBrhInfo);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblBrhInfo a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.TblBrhInfo tblBrhInfo)
	{
		super.saveOrUpdate(tblBrhInfo);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblBrhInfo a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblBrhInfo tblBrhInfo)
	{
		super.update(tblBrhInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblBrhInfo the instance to be removed
	 */
	public void delete(com.huateng.po.TblBrhInfo tblBrhInfo)
	{
		super.delete((Object) tblBrhInfo);
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