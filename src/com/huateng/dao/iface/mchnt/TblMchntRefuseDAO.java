package com.huateng.dao.iface.mchnt;


public interface TblMchntRefuseDAO {
	public com.huateng.po.TblMchntRefuse get(com.huateng.po.TblMchntRefusePK key);

	public com.huateng.po.TblMchntRefuse load(com.huateng.po.TblMchntRefusePK key);

	public java.util.List<com.huateng.po.TblMchntRefuse> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblMchntRefuse a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.huateng.po.TblMchntRefusePK save(com.huateng.po.TblMchntRefuse tblMchntRefuse);
	
	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblMchntRefuse a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.TblMchntRefuse tblMchntRefuse);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchntRefuse a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblMchntRefuse tblMchntRefuse);
	

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.TblMchntRefusePK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblMchntRefuse the instance to be removed
	 */
	public void delete(com.huateng.po.TblMchntRefuse tblMchntRefuse);


}