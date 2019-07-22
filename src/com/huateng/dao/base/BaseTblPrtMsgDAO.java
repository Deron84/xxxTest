package com.huateng.dao.base;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblPrtMsgDAO extends _RootDAO<com.huateng.po.epos.TblPrtMsg> {



	// query name references




	public Class<com.huateng.po.epos.TblPrtMsg> getReferenceClass () {
		return com.huateng.po.epos.TblPrtMsg.class;
	}


	/**
	 * Cast the object as a com.huateng.po.epos.TblPrtMsg
	 */
	public com.huateng.po.epos.TblPrtMsg cast (Object object) {
		return (com.huateng.po.epos.TblPrtMsg) object;
	}


	public com.huateng.po.epos.TblPrtMsg load(com.huateng.po.epos.TblPrtMsgPK key)
	{
		return (com.huateng.po.epos.TblPrtMsg) load(getReferenceClass(), key);
	}

	public com.huateng.po.epos.TblPrtMsg get(com.huateng.po.epos.TblPrtMsgPK key)
	{
		return (com.huateng.po.epos.TblPrtMsg) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.epos.TblPrtMsg> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblPrtMsg a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.huateng.po.epos.TblPrtMsgPK save(com.huateng.po.epos.TblPrtMsg tblPrtMsg)
	{
		return (com.huateng.po.epos.TblPrtMsgPK) super.save(tblPrtMsg);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblPrtMsg a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.epos.TblPrtMsg tblPrtMsg)
	{
		super.saveOrUpdate(tblPrtMsg);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblPrtMsg a transient instance containing updated state
	 */
	public void update(com.huateng.po.epos.TblPrtMsg tblPrtMsg)
	{
		super.update(tblPrtMsg);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblPrtMsg the instance to be removed
	 */
	public void delete(com.huateng.po.epos.TblPrtMsg tblPrtMsg)
	{
		super.delete((Object) tblPrtMsg);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.epos.TblPrtMsgPK id)
	{
		super.delete((Object) load(id));
	}






}