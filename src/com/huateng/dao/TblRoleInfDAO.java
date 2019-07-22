package com.huateng.dao;

import java.util.List;

import com.huateng.po.TblRoleInf;
import com.sdses.dao._RootDAO;


public class TblRoleInfDAO extends _RootDAO<com.huateng.po.TblRoleInf> implements com.huateng.dao.iface.TblRoleInfDAO{

public TblRoleInfDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblRoleInfDAO#findAll()
 */
public List<TblRoleInf> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblRoleInf> getReferenceClass () {
	return com.huateng.po.TblRoleInf.class;
}


/**
 * Cast the object as a com.huateng.po.role.TblRoleInf
 */
public com.huateng.po.TblRoleInf cast (Object object) {
	return (com.huateng.po.TblRoleInf) object;
}


public com.huateng.po.TblRoleInf load(java.lang.Integer key)
{
	return (com.huateng.po.TblRoleInf) load(getReferenceClass(), key);
}

public com.huateng.po.TblRoleInf get(java.lang.Integer key)
{
	return (com.huateng.po.TblRoleInf) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblRoleInf> loadAll()
{
	return loadAll(getReferenceClass());
}

/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRoleInf a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.Integer save(com.huateng.po.TblRoleInf tblRoleInf)
{
	return (java.lang.Integer) super.save(tblRoleInf);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblRoleInf a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblRoleInf tblRoleInf)
{
	super.saveOrUpdate(tblRoleInf);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRoleInf a transient instance containing updated state
 */
public void update(com.huateng.po.TblRoleInf tblRoleInf)
{
	super.update(tblRoleInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblRoleInf the instance to be removed
 */
public void delete(com.huateng.po.TblRoleInf tblRoleInf)
{
	super.delete((Object) tblRoleInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(java.lang.Integer id)
{
	super.delete((Object) load(id));
}

}