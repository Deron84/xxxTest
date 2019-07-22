package com.huateng.dao.iface;



public interface TblRoleInfDAO {
	public com.huateng.po.TblRoleInf get(java.lang.Integer key);

	public com.huateng.po.TblRoleInf load(java.lang.Integer key);

	public java.util.List<com.huateng.po.TblRoleInf> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblRoleInf a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Integer save(com.huateng.po.TblRoleInf tblRoleInf);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblRoleInf a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.TblRoleInf tblRoleInf);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblRoleInf a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblRoleInf tblRoleInf);
	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Integer id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblRoleInf the instance to be removed
	 */
	public void delete(com.huateng.po.TblRoleInf tblRoleInf);


}