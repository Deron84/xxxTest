package com.huateng.dao.base;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblCupBcMapDAO extends _RootDAO<com.huateng.po.epos.TblCupBcMap> {



	// query name references




	public Class<com.huateng.po.epos.TblCupBcMap> getReferenceClass () {
		return com.huateng.po.epos.TblCupBcMap.class;
	}


	/**
	 * Cast the object as a com.huateng.po.epos.TblCupBcMap
	 */
	public com.huateng.po.epos.TblCupBcMap cast (Object object) {
		return (com.huateng.po.epos.TblCupBcMap) object;
	}


	public com.huateng.po.epos.TblCupBcMap load(java.lang.String key)
	{
		return (com.huateng.po.epos.TblCupBcMap) load(getReferenceClass(), key);
	}

	public com.huateng.po.epos.TblCupBcMap get(java.lang.String key)
	{
		return (com.huateng.po.epos.TblCupBcMap) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.epos.TblCupBcMap> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblCupBcMap a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.epos.TblCupBcMap tblCupBcMap)
	{
		return (java.lang.String) super.save(tblCupBcMap);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblCupBcMap a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.epos.TblCupBcMap tblCupBcMap)
	{
		super.saveOrUpdate(tblCupBcMap);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblCupBcMap a transient instance containing updated state
	 */
	public void update(com.huateng.po.epos.TblCupBcMap tblCupBcMap)
	{
		super.update(tblCupBcMap);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblCupBcMap the instance to be removed
	 */
	public void delete(com.huateng.po.epos.TblCupBcMap tblCupBcMap)
	{
		super.delete((Object) tblCupBcMap);
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