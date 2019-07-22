package com.huateng.dao.base;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblPptMsgDAO extends _RootDAO<com.huateng.po.epos.TblPptMsg> {



	// query name references




	public Class<com.huateng.po.epos.TblPptMsg> getReferenceClass () {
		return com.huateng.po.epos.TblPptMsg.class;
	}


	/**
	 * Cast the object as a com.huateng.po.epos.TblPptMsg
	 */
	public com.huateng.po.epos.TblPptMsg cast (Object object) {
		return (com.huateng.po.epos.TblPptMsg) object;
	}


	public com.huateng.po.epos.TblPptMsg load(com.huateng.po.epos.TblPptMsgPK key)
	{
		return (com.huateng.po.epos.TblPptMsg) load(getReferenceClass(), key);
	}

	public com.huateng.po.epos.TblPptMsg get(com.huateng.po.epos.TblPptMsgPK key)
	{
		return (com.huateng.po.epos.TblPptMsg) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.epos.TblPptMsg> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblPptMsg a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.huateng.po.epos.TblPptMsgPK save(com.huateng.po.epos.TblPptMsg tblPptMsg)
	{
		return (com.huateng.po.epos.TblPptMsgPK) super.save(tblPptMsg);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblPptMsg a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.epos.TblPptMsg tblPptMsg)
	{
		super.saveOrUpdate(tblPptMsg);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblPptMsg a transient instance containing updated state
	 */
	public void update(com.huateng.po.epos.TblPptMsg tblPptMsg)
	{
		super.update(tblPptMsg);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblPptMsg the instance to be removed
	 */
	public void delete(com.huateng.po.epos.TblPptMsg tblPptMsg)
	{
		super.delete((Object) tblPptMsg);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.epos.TblPptMsgPK id)
	{
		super.delete((Object) load(id));
	}






}