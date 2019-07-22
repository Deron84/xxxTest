package com.huateng.dao.base;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblTxnCfgDspDAO extends _RootDAO<com.huateng.po.epos.TblTxnCfgDsp> {



	// query name references




	public Class<com.huateng.po.epos.TblTxnCfgDsp> getReferenceClass () {
		return com.huateng.po.epos.TblTxnCfgDsp.class;
	}


	/**
	 * Cast the object as a com.huateng.po.epos.TblTxnCfgDsp
	 */
	public com.huateng.po.epos.TblTxnCfgDsp cast (Object object) {
		return (com.huateng.po.epos.TblTxnCfgDsp) object;
	}


	public com.huateng.po.epos.TblTxnCfgDsp load(com.huateng.po.epos.TblTxnCfgDspPK key)
	{
		return (com.huateng.po.epos.TblTxnCfgDsp) load(getReferenceClass(), key);
	}

	public com.huateng.po.epos.TblTxnCfgDsp get(com.huateng.po.epos.TblTxnCfgDspPK key)
	{
		return (com.huateng.po.epos.TblTxnCfgDsp) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.epos.TblTxnCfgDsp> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblTxnCfgDsp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.huateng.po.epos.TblTxnCfgDspPK save(com.huateng.po.epos.TblTxnCfgDsp tblTxnCfgDsp)
	{
		return (com.huateng.po.epos.TblTxnCfgDspPK) super.save(tblTxnCfgDsp);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblTxnCfgDsp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.epos.TblTxnCfgDsp tblTxnCfgDsp)
	{
		super.saveOrUpdate(tblTxnCfgDsp);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTxnCfgDsp a transient instance containing updated state
	 */
	public void update(com.huateng.po.epos.TblTxnCfgDsp tblTxnCfgDsp)
	{
		super.update(tblTxnCfgDsp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblTxnCfgDsp the instance to be removed
	 */
	public void delete(com.huateng.po.epos.TblTxnCfgDsp tblTxnCfgDsp)
	{
		super.delete((Object) tblTxnCfgDsp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.epos.TblTxnCfgDspPK id)
	{
		super.delete((Object) load(id));
	}






}