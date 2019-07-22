package com.huateng.dao.iface.term;

import java.util.List;

import com.huateng.po.TblTermManagement;

public interface TblTermManagementDAO {
	public com.huateng.po.TblTermManagement get(java.lang.String key);

	public com.huateng.po.TblTermManagement load(java.lang.String key);

	public java.util.List<com.huateng.po.TblTermManagement> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblTermManagement a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.TblTermManagement tblTermManagement);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblTermManagement a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.TblTermManagement tblTermManagement);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTermManagement a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblTermManagement tblTermManagement);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblTermManagement the instance to be removed
	 */
	public void delete(com.huateng.po.TblTermManagement tblTermManagement);
	/**
	 * 批量存储
	 * @param list
	 * 2011-6-13下午05:05:55
	 */
	public void saveBatch(List<TblTermManagement> list);
	/**
	 * 下发库存
	 * @param hql
	 * @param count
	 * @return
	 * 2011-6-15下午12:44:36
	 */
	public List<TblTermManagement> findByHQLQuery(final String hql,final int count);
}