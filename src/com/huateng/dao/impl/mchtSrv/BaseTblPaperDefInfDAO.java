package com.huateng.dao.impl.mchtSrv;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblPaperDefInfDAO extends _RootDAO<com.huateng.po.mchtSrv.TblPaperDefInf> {



	// query name references




	public Class<com.huateng.po.mchtSrv.TblPaperDefInf> getReferenceClass () {
		return com.huateng.po.mchtSrv.TblPaperDefInf.class;
	}


	/**
	 * Cast the object as a com.huateng.po.mchtSrv.TblPaperDefInf
	 */
	public com.huateng.po.mchtSrv.TblPaperDefInf cast (Object object) {
		return (com.huateng.po.mchtSrv.TblPaperDefInf) object;
	}


	public com.huateng.po.mchtSrv.TblPaperDefInf load(com.huateng.po.mchtSrv.TblPaperDefInfPK key)
	{
		return (com.huateng.po.mchtSrv.TblPaperDefInf) load(getReferenceClass(), key);
	}

	public com.huateng.po.mchtSrv.TblPaperDefInf get(com.huateng.po.mchtSrv.TblPaperDefInfPK key)
	{
		return (com.huateng.po.mchtSrv.TblPaperDefInf) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.mchtSrv.TblPaperDefInf> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblPaperDefInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.huateng.po.mchtSrv.TblPaperDefInfPK save(com.huateng.po.mchtSrv.TblPaperDefInf tblPaperDefInf)
	{
		return (com.huateng.po.mchtSrv.TblPaperDefInfPK) super.save(tblPaperDefInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblPaperDefInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.mchtSrv.TblPaperDefInf tblPaperDefInf)
	{
		super.saveOrUpdate(tblPaperDefInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblPaperDefInf a transient instance containing updated state
	 */
	public void update(com.huateng.po.mchtSrv.TblPaperDefInf tblPaperDefInf)
	{
		super.update(tblPaperDefInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblPaperDefInf the instance to be removed
	 */
	public void delete(com.huateng.po.mchtSrv.TblPaperDefInf tblPaperDefInf)
	{
		super.delete((Object) tblPaperDefInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.mchtSrv.TblPaperDefInfPK id)
	{
		super.delete((Object) load(id));
	}






}