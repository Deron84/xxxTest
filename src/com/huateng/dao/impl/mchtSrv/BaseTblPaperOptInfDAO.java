package com.huateng.dao.impl.mchtSrv;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblPaperOptInfDAO extends _RootDAO<com.huateng.po.mchtSrv.TblPaperOptInf> {



	// query name references




	public Class<com.huateng.po.mchtSrv.TblPaperOptInf> getReferenceClass () {
		return com.huateng.po.mchtSrv.TblPaperOptInf.class;
	}


	/**
	 * Cast the object as a com.huateng.po.mchtSrv.TblPaperOptInf
	 */
	public com.huateng.po.mchtSrv.TblPaperOptInf cast (Object object) {
		return (com.huateng.po.mchtSrv.TblPaperOptInf) object;
	}


	public com.huateng.po.mchtSrv.TblPaperOptInf load(com.huateng.po.mchtSrv.TblPaperOptInfPK key)
	{
		return (com.huateng.po.mchtSrv.TblPaperOptInf) load(getReferenceClass(), key);
	}

	public com.huateng.po.mchtSrv.TblPaperOptInf get(com.huateng.po.mchtSrv.TblPaperOptInfPK key)
	{
		return (com.huateng.po.mchtSrv.TblPaperOptInf) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.mchtSrv.TblPaperOptInf> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblPaperOptInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.huateng.po.mchtSrv.TblPaperOptInfPK save(com.huateng.po.mchtSrv.TblPaperOptInf tblPaperOptInf)
	{
		return (com.huateng.po.mchtSrv.TblPaperOptInfPK) super.save(tblPaperOptInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblPaperOptInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.mchtSrv.TblPaperOptInf tblPaperOptInf)
	{
		super.saveOrUpdate(tblPaperOptInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblPaperOptInf a transient instance containing updated state
	 */
	public void update(com.huateng.po.mchtSrv.TblPaperOptInf tblPaperOptInf)
	{
		super.update(tblPaperOptInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblPaperOptInf the instance to be removed
	 */
	public void delete(com.huateng.po.mchtSrv.TblPaperOptInf tblPaperOptInf)
	{
		super.delete((Object) tblPaperOptInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.mchtSrv.TblPaperOptInfPK id)
	{
		super.delete((Object) load(id));
	}






}