package com.huateng.dao.impl.mchtSrv;

import java.util.List;

import com.huateng.po.TblMchtCupInfo;
import com.sdses.dao._RootDAO;


public class TblMchtCupInfoDAO extends _RootDAO<com.huateng.po.TblMchtCupInfo> implements com.huateng.dao.iface.mchtSrv.TblMchtCupInfoDAO{

public TblMchtCupInfoDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblMchtCupInfoDAO#findAll()
 */
public List<TblMchtCupInfo> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblMchtCupInfo> getReferenceClass () {
	return com.huateng.po.TblMchtCupInfo.class;
}


/**
 * Cast the object as a com.huateng.po.TblMchtCupInfo
 */
public com.huateng.po.TblMchtCupInfo cast (Object object) {
	return (com.huateng.po.TblMchtCupInfo) object;
}


public com.huateng.po.TblMchtCupInfo load(java.lang.String key)
{
	return (com.huateng.po.TblMchtCupInfo) load(getReferenceClass(), key);
}

public com.huateng.po.TblMchtCupInfo get(java.lang.String key)
{
	return (com.huateng.po.TblMchtCupInfo) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblMchtCupInfo> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblMchtCupInfo a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.huateng.po.TblMchtCupInfo tblMchtCupInfo)
{
	return (java.lang.String) super.save(tblMchtCupInfo);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblMchtCupInfo a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblMchtCupInfo tblMchtCupInfo)
{
	super.saveOrUpdate(tblMchtCupInfo);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblMchtCupInfo a transient instance containing updated state
 */
public void update(com.huateng.po.TblMchtCupInfo tblMchtCupInfo)
{
	super.update(tblMchtCupInfo);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblMchtCupInfo the instance to be removed
 */
public void delete(com.huateng.po.TblMchtCupInfo tblMchtCupInfo)
{
	super.delete((Object) tblMchtCupInfo);
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