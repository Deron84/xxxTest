package com.huateng.dao.iface.base;

import java.util.List;


public interface AlipayShareParamDAO {
	
	public com.huateng.po.AlipayShareParam get(String brhId);


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param alipayShareParam a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public void save(com.huateng.po.AlipayShareParam alipayShareParam);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param alipayShareParam a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.AlipayShareParam alipayShareParam);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param alipayShareParam a transient instance containing updated state
	 */
	public void update(com.huateng.po.AlipayShareParam alipayShareParam);
	

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param alipayShareParam the instance to be removed
	 */
	public void delete(com.huateng.po.AlipayShareParam alipayShareParam);


}