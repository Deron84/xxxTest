package com.huateng.dao.iface.base;


public interface TblEmvParaDAO {

	public Class<com.huateng.po.base.TblEmvPara> getReferenceClass ();

	/**
	 * Cast the object as a com.huateng.po.base.TblEmvPara
	 */
	public com.huateng.po.base.TblEmvPara cast (Object object) ;


	public com.huateng.po.base.TblEmvPara load(com.huateng.po.base.TblEmvParaPK key);

	public com.huateng.po.base.TblEmvPara get(com.huateng.po.base.TblEmvParaPK key);

	public java.util.List<com.huateng.po.base.TblEmvPara> loadAll();

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblEmvPara a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.huateng.po.base.TblEmvParaPK save(com.huateng.po.base.TblEmvPara tblEmvPara);
	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblEmvPara a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.base.TblEmvPara tblEmvPara);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblEmvPara a transient instance containing updated state
	 */
	public void update(com.huateng.po.base.TblEmvPara tblEmvPara);
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblEmvPara the instance to be removed
	 */
	public void delete(com.huateng.po.base.TblEmvPara tblEmvPara);
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.base.TblEmvParaPK id);


}