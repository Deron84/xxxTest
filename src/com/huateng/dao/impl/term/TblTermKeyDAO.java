package com.huateng.dao.impl.term;

import java.util.List;

import com.huateng.po.TblTermKey;
import com.sdses.dao._RootDAO;


public class TblTermKeyDAO extends _RootDAO<com.huateng.po.TblTermKey> implements com.huateng.dao.iface.term.TblTermKeyDAO{

public TblTermKeyDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblTermKeyDAO#findAll()
 */
public List<TblTermKey> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblTermKey> getReferenceClass () {
	return com.huateng.po.TblTermKey.class;
}


/**
 * Cast the object as a com.huateng.po.TblTermKey
 */
public com.huateng.po.TblTermKey cast (Object object) {
	return (com.huateng.po.TblTermKey) object;
}


public com.huateng.po.TblTermKey load(com.huateng.po.TblTermKeyPK key)
{
	return (com.huateng.po.TblTermKey) load(getReferenceClass(), key);
}

public com.huateng.po.TblTermKey get(com.huateng.po.TblTermKeyPK key)
{
	return (com.huateng.po.TblTermKey) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblTermKey> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblTermKey a transient instance of a persistent class
 * @return the class identifier
 */
public com.huateng.po.TblTermKeyPK save(com.huateng.po.TblTermKey tblTermKey)
{
	return (com.huateng.po.TblTermKeyPK) super.save(tblTermKey);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblTermKey a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblTermKey tblTermKey)
{
	super.saveOrUpdate(tblTermKey);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblTermKey a transient instance containing updated state
 */
public void update(com.huateng.po.TblTermKey tblTermKey)
{
	super.update(tblTermKey);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblTermKey the instance to be removed
 */
public void delete(com.huateng.po.TblTermKey tblTermKey)
{
	super.delete((Object) tblTermKey);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.huateng.po.TblTermKeyPK id)
{
	super.delete((Object) load(id));
}

}