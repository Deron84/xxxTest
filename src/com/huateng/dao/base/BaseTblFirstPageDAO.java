package com.huateng.dao.base;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblFirstPageDAO extends _RootDAO<com.huateng.po.epos.TblFirstPage> {



	// query name references




	public Class<com.huateng.po.epos.TblFirstPage> getReferenceClass () {
		return com.huateng.po.epos.TblFirstPage.class;
	}


	/**
	 * Cast the object as a com.huateng.po.epos.TblFirstPage
	 */
	public com.huateng.po.epos.TblFirstPage cast (Object object) {
		return (com.huateng.po.epos.TblFirstPage) object;
	}


	public com.huateng.po.epos.TblFirstPage load(java.lang.String key)
	{
		return (com.huateng.po.epos.TblFirstPage) load(getReferenceClass(), key);
	}

	public com.huateng.po.epos.TblFirstPage get(java.lang.String key)
	{
		return (com.huateng.po.epos.TblFirstPage) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.epos.TblFirstPage> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblFirstPage a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.epos.TblFirstPage tblFirstPage)
	{
		return (java.lang.String) super.save(tblFirstPage);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblFirstPage a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.epos.TblFirstPage tblFirstPage)
	{
		super.saveOrUpdate(tblFirstPage);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblFirstPage a transient instance containing updated state
	 */
	public void update(com.huateng.po.epos.TblFirstPage tblFirstPage)
	{
		super.update(tblFirstPage);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblFirstPage the instance to be removed
	 */
	public void delete(com.huateng.po.epos.TblFirstPage tblFirstPage)
	{
		super.delete((Object) tblFirstPage);
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