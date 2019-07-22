package com.huateng.dao.impl.term;

import java.util.List;

import com.huateng.po.TblTermRefuse;
import com.sdses.dao._RootDAO;


public class TblTermRefuseDAO extends _RootDAO<com.huateng.po.TblTermRefuse> implements com.huateng.dao.iface.term.TblTermRefuseDAO{

public TblTermRefuseDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblTermRefuseDAO#findAll()
 */
public List<TblTermRefuse> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblTermRefuse> getReferenceClass () {
	return com.huateng.po.TblTermRefuse.class;
}


/**
 * Cast the object as a com.huateng.po.TblTermRefuse
 */
public com.huateng.po.TblTermRefuse cast (Object object) {
	return (com.huateng.po.TblTermRefuse) object;
}


public com.huateng.po.TblTermRefuse load(com.huateng.po.TblTermRefusePK key)
{
	return (com.huateng.po.TblTermRefuse) load(getReferenceClass(), key);
}

public com.huateng.po.TblTermRefuse get(com.huateng.po.TblTermRefusePK key)
{
	return (com.huateng.po.TblTermRefuse) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblTermRefuse> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblTermRefuse a transient instance of a persistent class
 * @return the class identifier
 */
public com.huateng.po.TblTermRefusePK save(com.huateng.po.TblTermRefuse tblTermRefuse)
{
	return (com.huateng.po.TblTermRefusePK) super.save(tblTermRefuse);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblTermRefuse a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblTermRefuse tblTermRefuse)
{
	super.saveOrUpdate(tblTermRefuse);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblTermRefuse a transient instance containing updated state
 */
public void update(com.huateng.po.TblTermRefuse tblTermRefuse)
{
	super.update(tblTermRefuse);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblTermRefuse the instance to be removed
 */
public void delete(com.huateng.po.TblTermRefuse tblTermRefuse)
{
	super.delete((Object) tblTermRefuse);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.huateng.po.TblTermRefusePK id)
{
	super.delete((Object) load(id));
}

}