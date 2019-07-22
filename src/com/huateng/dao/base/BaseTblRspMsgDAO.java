package com.huateng.dao.base;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblRspMsgDAO extends _RootDAO<com.huateng.po.epos.TblRspMsg> {



	// query name references




	public Class<com.huateng.po.epos.TblRspMsg> getReferenceClass () {
		return com.huateng.po.epos.TblRspMsg.class;
	}


	/**
	 * Cast the object as a com.huateng.po.epos.TblRspMsg
	 */
	public com.huateng.po.epos.TblRspMsg cast (Object object) {
		return (com.huateng.po.epos.TblRspMsg) object;
	}


	public com.huateng.po.epos.TblRspMsg load(com.huateng.po.epos.TblRspMsgPK key)
	{
		return (com.huateng.po.epos.TblRspMsg) load(getReferenceClass(), key);
	}

	public com.huateng.po.epos.TblRspMsg get(com.huateng.po.epos.TblRspMsgPK key)
	{
		return (com.huateng.po.epos.TblRspMsg) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.epos.TblRspMsg> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblRspMsg a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.huateng.po.epos.TblRspMsgPK save(com.huateng.po.epos.TblRspMsg tblRspMsg)
	{
		return (com.huateng.po.epos.TblRspMsgPK) super.save(tblRspMsg);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblRspMsg a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.epos.TblRspMsg tblRspMsg)
	{
		super.saveOrUpdate(tblRspMsg);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblRspMsg a transient instance containing updated state
	 */
	public void update(com.huateng.po.epos.TblRspMsg tblRspMsg)
	{
		super.update(tblRspMsg);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblRspMsg the instance to be removed
	 */
	public void delete(com.huateng.po.epos.TblRspMsg tblRspMsg)
	{
		super.delete((Object) tblRspMsg);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.epos.TblRspMsgPK id)
	{
		super.delete((Object) load(id));
	}






}