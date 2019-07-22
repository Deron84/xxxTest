package com.huateng.dao.impl.mchtSrv;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblMchntParticipatDAO extends _RootDAO<com.huateng.po.mchtSrv.TblMchntParticipat> {



	// query name references




	public Class<com.huateng.po.mchtSrv.TblMchntParticipat> getReferenceClass () {
		return com.huateng.po.mchtSrv.TblMchntParticipat.class;
	}


	/**
	 * Cast the object as a com.huateng.po.mchtSrv.TblMchntParticipat
	 */
	public com.huateng.po.mchtSrv.TblMchntParticipat cast (Object object) {
		return (com.huateng.po.mchtSrv.TblMchntParticipat) object;
	}


	public com.huateng.po.mchtSrv.TblMchntParticipat load(com.huateng.po.mchtSrv.TblMchntParticipatPK key)
	{
		return (com.huateng.po.mchtSrv.TblMchntParticipat) load(getReferenceClass(), key);
	}

	public com.huateng.po.mchtSrv.TblMchntParticipat get(com.huateng.po.mchtSrv.TblMchntParticipatPK key)
	{
		return (com.huateng.po.mchtSrv.TblMchntParticipat) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.mchtSrv.TblMchntParticipat> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchntParticipat a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.huateng.po.mchtSrv.TblMchntParticipatPK save(com.huateng.po.mchtSrv.TblMchntParticipat tblMchntParticipat)
	{
		return (com.huateng.po.mchtSrv.TblMchntParticipatPK) super.save(tblMchntParticipat);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchntParticipat a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.mchtSrv.TblMchntParticipat tblMchntParticipat)
	{
		super.saveOrUpdate(tblMchntParticipat);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchntParticipat a transient instance containing updated state
	 */
	public void update(com.huateng.po.mchtSrv.TblMchntParticipat tblMchntParticipat)
	{
		super.update(tblMchntParticipat);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchntParticipat the instance to be removed
	 */
	public void delete(com.huateng.po.mchtSrv.TblMchntParticipat tblMchntParticipat)
	{
		super.delete((Object) tblMchntParticipat);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.mchtSrv.TblMchntParticipatPK id)
	{
		super.delete((Object) load(id));
	}






}