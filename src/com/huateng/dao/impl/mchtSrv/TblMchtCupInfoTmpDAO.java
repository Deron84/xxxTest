package com.huateng.dao.impl.mchtSrv;

import java.util.List;

import com.huateng.po.TblMchtCupInfoTmp;
import com.sdses.dao._RootDAO;


public class TblMchtCupInfoTmpDAO extends _RootDAO<com.huateng.po.TblMchtCupInfoTmp> implements com.huateng.dao.iface.mchtSrv.TblMchtCupInfoTmpDAO{

public TblMchtCupInfoTmpDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblMchtCupInfoTmpDAO#findAll()
 */
public List<TblMchtCupInfoTmp> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblMchtCupInfoTmp> getReferenceClass () {
	return com.huateng.po.TblMchtCupInfoTmp.class;
}


/**
 * Cast the object as a com.huateng.po.TblMchtCupInfoTmp
 */
public com.huateng.po.TblMchtCupInfoTmp cast (Object object) {
	return (com.huateng.po.TblMchtCupInfoTmp) object;
}


public com.huateng.po.TblMchtCupInfoTmp load(java.lang.String key)
{
	return (com.huateng.po.TblMchtCupInfoTmp) load(getReferenceClass(), key);
}

public com.huateng.po.TblMchtCupInfoTmp get(java.lang.String key)
{
	return (com.huateng.po.TblMchtCupInfoTmp) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblMchtCupInfoTmp> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblMchtCupInfoTmp a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.huateng.po.TblMchtCupInfoTmp tblMchtCupInfoTmp)
{
	return (java.lang.String) super.save(tblMchtCupInfoTmp);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblMchtCupInfoTmp a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblMchtCupInfoTmp tblMchtCupInfoTmp)
{
	super.saveOrUpdate(tblMchtCupInfoTmp);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblMchtCupInfoTmp a transient instance containing updated state
 */
public void update(com.huateng.po.TblMchtCupInfoTmp tblMchtCupInfoTmp)
{
	super.update(tblMchtCupInfoTmp);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblMchtCupInfoTmp the instance to be removed
 */
public void delete(com.huateng.po.TblMchtCupInfoTmp tblMchtCupInfoTmp)
{
	super.delete((Object) tblMchtCupInfoTmp);
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