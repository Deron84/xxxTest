package com.huateng.dao.impl.base;

import com.huateng.po.TblFuncInf;
import com.sdses.dao._RootDAO;



/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblFuncInfDAO extends _RootDAO<TblFuncInf> {



	// query name references




	public Class<TblFuncInf> getReferenceClass () {
		return TblFuncInf.class;
	}


	/**
	 * Cast the object as a TblFuncInf
	 */
	public TblFuncInf cast (Object object) {
		return (TblFuncInf) object;
	}


	public TblFuncInf load(java.lang.Long key)
	{
		return (TblFuncInf) load(getReferenceClass(), key);
	}

	public TblFuncInf get(java.lang.Long key)
	{
		return (TblFuncInf) get(getReferenceClass(), key);
	}

	public java.util.List<TblFuncInf> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblFuncInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.Long save(TblFuncInf tblFuncInf)
	{
		return (java.lang.Long) super.save(tblFuncInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblFuncInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblFuncInf tblFuncInf)
	{
		super.saveOrUpdate(tblFuncInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblFuncInf a transient instance containing updated state
	 */
	public void update(TblFuncInf tblFuncInf)
	{
		super.update(tblFuncInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblFuncInf the instance to be removed
	 */
	public void delete(TblFuncInf tblFuncInf)
	{
		super.delete((Object) tblFuncInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Long id)
	{
		super.delete((Object) load(id));
	}






}