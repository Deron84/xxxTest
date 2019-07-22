package com.huateng.dao.iface.mchnt;

import java.util.List;

public interface ITblMchtBaseInfTmpDAO {



	// query name references




	public Class<com.huateng.po.mchnt.TblMchtBaseInfTmp> getReferenceClass ();


	/**
	 * Cast the object as a com.huateng.po.mchnt.TblMchtBaseInfTmp
	 */
	public com.huateng.po.mchnt.TblMchtBaseInfTmp cast (Object object);


	public com.huateng.po.mchnt.TblMchtBaseInfTmp load(java.lang.String key)
		throws org.hibernate.HibernateException;

	public com.huateng.po.mchnt.TblMchtBaseInfTmp get(java.lang.String key)
		throws org.hibernate.HibernateException;


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtBaseInfTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtBaseInfTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtBaseInfTmp a transient instance containing updated state
	 */
	public void update(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtBaseInfTmp the instance to be removed
	 */
	public void delete(com.huateng.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException;

	public void saveBatch(List<com.huateng.po.mchnt.TblMchtBaseInfTmp> list);
}