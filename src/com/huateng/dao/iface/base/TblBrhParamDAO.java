package com.huateng.dao.iface.base;

public interface TblBrhParamDAO {

	public com.huateng.po.TblBrhParam get(String brhId);

	public java.util.List<com.huateng.po.TblBrhParam> findAll();

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param tblBrhParam
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.TblBrhParam tblBrhParam);

	public void save(com.huateng.po.TblBrhParam tblBrhParam);

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param tblBrhParam
	 *            a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblBrhParam tblBrhParam);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param tblBrhParam
	 *            the instance to be removed
	 */
	public void delete(String brhId);

	public void delete(com.huateng.po.TblBrhParam tblBrhParam);

}