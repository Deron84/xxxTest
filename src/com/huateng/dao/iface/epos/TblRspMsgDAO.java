package com.huateng.dao.iface.epos;

import java.io.Serializable;

public interface TblRspMsgDAO {
	public com.huateng.po.epos.TblRspMsg get(com.huateng.po.epos.TblRspMsgPK key);

	public com.huateng.po.epos.TblRspMsg load(com.huateng.po.epos.TblRspMsgPK key);

	public java.util.List<com.huateng.po.epos.TblRspMsg> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblRspMsg a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.huateng.po.epos.TblRspMsgPK save(com.huateng.po.epos.TblRspMsg tblRspMsg);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblRspMsg a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.epos.TblRspMsg tblRspMsg);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblRspMsg a transient instance containing updated state
	 */
	public void update(com.huateng.po.epos.TblRspMsg tblRspMsg);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.epos.TblRspMsgPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblRspMsg the instance to be removed
	 */
	public void delete(com.huateng.po.epos.TblRspMsg tblRspMsg);


}