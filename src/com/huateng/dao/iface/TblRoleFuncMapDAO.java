package com.huateng.dao.iface;


public interface TblRoleFuncMapDAO {
	public com.huateng.po.TblRoleFuncMap get(com.huateng.po.TblRoleFuncMapPK key);

	public com.huateng.po.TblRoleFuncMap load(com.huateng.po.TblRoleFuncMapPK key);

	public java.util.List<com.huateng.po.TblRoleFuncMap> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblRoleFuncMap a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.huateng.po.TblRoleFuncMapPK save(com.huateng.po.TblRoleFuncMap tblRoleFuncMap);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblRoleFuncMap a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.TblRoleFuncMap tblRoleFuncMap);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblRoleFuncMap a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblRoleFuncMap tblRoleFuncMap);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.TblRoleFuncMapPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblRoleFuncMap the instance to be removed
	 */
	public void delete(com.huateng.po.TblRoleFuncMap tblRoleFuncMap);


}