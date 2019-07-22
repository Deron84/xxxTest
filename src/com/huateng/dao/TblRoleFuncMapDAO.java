package com.huateng.dao;

import java.util.List;

import com.huateng.po.TblRoleFuncMap;
import com.sdses.dao._RootDAO;


public class TblRoleFuncMapDAO extends _RootDAO<com.huateng.po.TblRiskInf> implements com.huateng.dao.iface.TblRoleFuncMapDAO{

public TblRoleFuncMapDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblRoleFuncMapDAO#findAll()
 */
public List<TblRoleFuncMap> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblRoleFuncMap> getReferenceClass () {
	return com.huateng.po.TblRoleFuncMap.class;
}


/**
 * Cast the object as a com.huateng.po.role.TblRoleFuncMap
 */
public com.huateng.po.TblRoleFuncMap cast (Object object) {
	return (com.huateng.po.TblRoleFuncMap) object;
}


public com.huateng.po.TblRoleFuncMap load(com.huateng.po.TblRoleFuncMapPK key)
{
	return (com.huateng.po.TblRoleFuncMap) load(getReferenceClass(), key);
}

public com.huateng.po.TblRoleFuncMap get(com.huateng.po.TblRoleFuncMapPK key)
{
	return (com.huateng.po.TblRoleFuncMap) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblRoleFuncMap> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRoleFuncMap a transient instance of a persistent class
 * @return the class identifier
 */
public com.huateng.po.TblRoleFuncMapPK save(com.huateng.po.TblRoleFuncMap tblRoleFuncMap)
{
	return (com.huateng.po.TblRoleFuncMapPK) super.save(tblRoleFuncMap);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblRoleFuncMap a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblRoleFuncMap tblRoleFuncMap)
{
	super.saveOrUpdate(tblRoleFuncMap);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRoleFuncMap a transient instance containing updated state
 */
public void update(com.huateng.po.TblRoleFuncMap tblRoleFuncMap)
{
	super.update(tblRoleFuncMap);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblRoleFuncMap the instance to be removed
 */
public void delete(com.huateng.po.TblRoleFuncMap tblRoleFuncMap)
{
	super.delete((Object) tblRoleFuncMap);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.huateng.po.TblRoleFuncMapPK id)
{
	super.delete((Object) load(id));
}

}