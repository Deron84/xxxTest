package com.huateng.dao.base;

import com.huateng.dao.iface.TblInfileOptDAO;
import com.sdses.dao._RootDAO;



/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblInfileOptDAO extends _RootDAO<com.huateng.po.settle.TblInfileOpt> implements TblInfileOptDAO{



	// query name references




	public Class<com.huateng.po.settle.TblInfileOpt> getReferenceClass () {
		return com.huateng.po.settle.TblInfileOpt.class;
	}


	/**
	 * Cast the object as a com.huateng.po.settle.TblInfileOpt
	 */
	public com.huateng.po.settle.TblInfileOpt cast (Object object) {
		return (com.huateng.po.settle.TblInfileOpt) object;
	}


	public com.huateng.po.settle.TblInfileOpt load(com.huateng.po.settle.TblInfileOptPK key)
	{
		return (com.huateng.po.settle.TblInfileOpt) load(getReferenceClass(), key);
	}

	public com.huateng.po.settle.TblInfileOpt get(com.huateng.po.settle.TblInfileOptPK key)
	{
		return (com.huateng.po.settle.TblInfileOpt) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.settle.TblInfileOpt> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblInfileOpt a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.huateng.po.settle.TblInfileOptPK save(com.huateng.po.settle.TblInfileOpt tblInfileOpt)
	{
		return (com.huateng.po.settle.TblInfileOptPK) super.save(tblInfileOpt);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblInfileOpt a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.settle.TblInfileOpt tblInfileOpt)
	{
		super.saveOrUpdate(tblInfileOpt);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblInfileOpt a transient instance containing updated state
	 */
	public void update(com.huateng.po.settle.TblInfileOpt tblInfileOpt)
	{
		super.update(tblInfileOpt);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblInfileOpt the instance to be removed
	 */
	public void delete(com.huateng.po.settle.TblInfileOpt tblInfileOpt)
	{
		super.delete((Object) tblInfileOpt);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.settle.TblInfileOptPK id)
	{
		super.delete((Object) load(id));
	}






}