package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.po.TblOprInfo;
import com.sdses.dao._RootDAO;


public class TblOprInfoDAO extends _RootDAO<com.huateng.po.TblOprInfo> implements com.huateng.dao.iface.base.TblOprInfoDAO{

public TblOprInfoDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblOprInfoDAO#findAll()
 */
public List<TblOprInfo> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblOprInfo> getReferenceClass () {
	return com.huateng.po.TblOprInfo.class;
}


/**
 * Cast the object as a com.huateng.po.TblOprInfo
 */
public com.huateng.po.TblOprInfo cast (Object object) {
	return (com.huateng.po.TblOprInfo) object;
}


public com.huateng.po.TblOprInfo load(java.lang.String key)
{
	return (com.huateng.po.TblOprInfo) load(getReferenceClass(), key);
}

public com.huateng.po.TblOprInfo get(java.lang.String key)
{
	return (com.huateng.po.TblOprInfo) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblOprInfo> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblOprInfo a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.huateng.po.TblOprInfo tblOprInfo)
{
	return (java.lang.String) super.save(tblOprInfo);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblOprInfo a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblOprInfo tblOprInfo)
{
	super.saveOrUpdate(tblOprInfo);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblOprInfo a transient instance containing updated state
 */
public void update(com.huateng.po.TblOprInfo tblOprInfo)
{
	super.update(tblOprInfo);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblOprInfo the instance to be removed
 */
public void delete(com.huateng.po.TblOprInfo tblOprInfo)
{
	super.delete((Object) tblOprInfo);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(java.lang.String id)
{
	super.delete((Object) load(id));
}

}