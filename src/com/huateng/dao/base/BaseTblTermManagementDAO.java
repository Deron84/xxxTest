package com.huateng.dao.base;

import org.hibernate.criterion.Order;

import com.huateng.dao.impl.term.TblTermManagementDAO;
/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblTermManagementDAO extends com.sdses.dao._RootDAO {

	public BaseTblTermManagementDAO () {}
	

	// query name references


	public static com.huateng.dao.iface.term.TblTermManagementDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static com.huateng.dao.iface.term.TblTermManagementDAO getInstance () {
		if (null == instance) instance = new TblTermManagementDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.huateng.po.TblTermManagement.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a com.huateng.po.TblTermManagement
	 */
	public com.huateng.po.TblTermManagement cast (Object object) {
		return (com.huateng.po.TblTermManagement) object;
	}

	public com.huateng.po.TblTermManagement get(java.lang.String key)
	{
		return (com.huateng.po.TblTermManagement) get(getReferenceClass(), key);
	}

	public com.huateng.po.TblTermManagement load(java.lang.String key)
	{
		return (com.huateng.po.TblTermManagement) load(getReferenceClass(), key);
	}


/* Generic methods */


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblTermManagement a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.TblTermManagement tblTermManagement)
	{
		return (java.lang.String) super.save(tblTermManagement);
	}


	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblTermManagement a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.TblTermManagement tblTermManagement)
	{
		saveOrUpdate((Object) tblTermManagement);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTermManagement a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblTermManagement tblTermManagement) 
	{
		update((Object) tblTermManagement);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
	{
		delete((Object) load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblTermManagement the instance to be removed
	 */
	public void delete(com.huateng.po.TblTermManagement tblTermManagement)
	{
		delete((Object) tblTermManagement);
	}



}