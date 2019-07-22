package com.huateng.dao.base;

import org.hibernate.criterion.Order;

import com.huateng.dao.iface.TblTermInfTmpDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblTermInfTmpDAO extends com.sdses.dao._RootDAO {

	public BaseTblTermInfTmpDAO () {}
	
	// query name references


	public static TblTermInfTmpDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static TblTermInfTmpDAO getInstance () {
		if (null == instance) instance = new com.huateng.dao.TblTermInfTmpDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.huateng.po.TblTermInfTmp.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a com.huateng.po.TblTermInfTmp
	 */
	public com.huateng.po.TblTermInfTmp cast (Object object) {
		return (com.huateng.po.TblTermInfTmp) object;
	}

	public com.huateng.po.TblTermInfTmp get(com.huateng.po.TblTermInfTmpPK key)
	{
		return (com.huateng.po.TblTermInfTmp) get(getReferenceClass(), key);
	}


	public com.huateng.po.TblTermInfTmp load(com.huateng.po.TblTermInfTmpPK key)
	{
		return (com.huateng.po.TblTermInfTmp) load(getReferenceClass(), key);
	}



/* Generic methods */


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblTermInfTmp a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.huateng.po.TblTermInfTmpPK save(com.huateng.po.TblTermInfTmp tblTermInfTmp)
	{
		return (com.huateng.po.TblTermInfTmpPK) super.save(tblTermInfTmp);
	}


	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblTermInfTmp a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.TblTermInfTmp tblTermInfTmp)
	{
		saveOrUpdate((Object) tblTermInfTmp);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTermInfTmp a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblTermInfTmp tblTermInfTmp) 
	{
		update((Object) tblTermInfTmp);
	}


	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.TblTermInfTmpPK id)
	{
		delete((Object) load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblTermInfTmp the instance to be removed
	 */
	public void delete(com.huateng.po.TblTermInfTmp tblTermInfTmp)
	{
		delete((Object) tblTermInfTmp);
	}

	


}