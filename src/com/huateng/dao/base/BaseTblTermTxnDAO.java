package com.huateng.dao.base;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblTermTxnDAO extends _RootDAO<com.huateng.po.epos.TblTermTxn> {



	// query name references




	public Class<com.huateng.po.epos.TblTermTxn> getReferenceClass () {
		return com.huateng.po.epos.TblTermTxn.class;
	}


	/**
	 * Cast the object as a com.huateng.po.epos.TblTermTxn
	 */
	public com.huateng.po.epos.TblTermTxn cast (Object object) {
		return (com.huateng.po.epos.TblTermTxn) object;
	}


	public com.huateng.po.epos.TblTermTxn load(java.lang.String key)
	{
		return (com.huateng.po.epos.TblTermTxn) load(getReferenceClass(), key);
	}

	public com.huateng.po.epos.TblTermTxn get(java.lang.String key)
	{
		return (com.huateng.po.epos.TblTermTxn) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.epos.TblTermTxn> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblTermTxn a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.epos.TblTermTxn tblTermTxn)
	{
		return (java.lang.String) super.save(tblTermTxn);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblTermTxn a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.epos.TblTermTxn tblTermTxn)
	{
		super.saveOrUpdate(tblTermTxn);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTermTxn a transient instance containing updated state
	 */
	public void update(com.huateng.po.epos.TblTermTxn tblTermTxn)
	{
		super.update(tblTermTxn);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblTermTxn the instance to be removed
	 */
	public void delete(com.huateng.po.epos.TblTermTxn tblTermTxn)
	{
		super.delete((Object) tblTermTxn);
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