package com.huateng.dao.base;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblVerMngDAO extends _RootDAO<com.huateng.po.epos.TblVerMng> {



	// query name references




	public Class<com.huateng.po.epos.TblVerMng> getReferenceClass () {
		return com.huateng.po.epos.TblVerMng.class;
	}


	/**
	 * Cast the object as a com.huateng.po.epos.TblVerMng
	 */
	public com.huateng.po.epos.TblVerMng cast (Object object) {
		return (com.huateng.po.epos.TblVerMng) object;
	}


	public com.huateng.po.epos.TblVerMng load(com.huateng.po.epos.TblVerMngPK key)
	{
		return (com.huateng.po.epos.TblVerMng) load(getReferenceClass(), key);
	}

	public com.huateng.po.epos.TblVerMng get(com.huateng.po.epos.TblVerMngPK key)
	{
		return (com.huateng.po.epos.TblVerMng) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.epos.TblVerMng> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblVerMng a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.huateng.po.epos.TblVerMngPK save(com.huateng.po.epos.TblVerMng tblVerMng)
	{
		return (com.huateng.po.epos.TblVerMngPK) super.save(tblVerMng);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblVerMng a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.epos.TblVerMng tblVerMng)
	{
		super.saveOrUpdate(tblVerMng);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblVerMng a transient instance containing updated state
	 */
	public void update(com.huateng.po.epos.TblVerMng tblVerMng)
	{
		super.update(tblVerMng);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblVerMng the instance to be removed
	 */
	public void delete(com.huateng.po.epos.TblVerMng tblVerMng)
	{
		super.delete((Object) tblVerMng);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.epos.TblVerMngPK id)
	{
		super.delete((Object) load(id));
	}






}