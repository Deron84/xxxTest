package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.po.TblMchntRefuse;
import com.sdses.dao._RootDAO;


public class TblMchntRefuseDAO extends _RootDAO<com.huateng.po.TblMchntRefuse> implements com.huateng.dao.iface.mchnt.TblMchntRefuseDAO{

public TblMchntRefuseDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblMchntRefuseDAO#findAll()
 */
public List<TblMchntRefuse> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblMchntRefuse> getReferenceClass () {
	return com.huateng.po.TblMchntRefuse.class;
}


/**
 * Cast the object as a com.huateng.po.TblMchntRefuse
 */
public com.huateng.po.TblMchntRefuse cast (Object object) {
	return (com.huateng.po.TblMchntRefuse) object;
}


public com.huateng.po.TblMchntRefuse load(com.huateng.po.TblMchntRefusePK key)
{
	return (com.huateng.po.TblMchntRefuse) load(getReferenceClass(), key);
}

public com.huateng.po.TblMchntRefuse get(com.huateng.po.TblMchntRefusePK key)
{
	return (com.huateng.po.TblMchntRefuse) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblMchntRefuse> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblMchntRefuse a transient instance of a persistent class
 * @return the class identifier
 */
public com.huateng.po.TblMchntRefusePK save(com.huateng.po.TblMchntRefuse tblMchntRefuse)
{
	return (com.huateng.po.TblMchntRefusePK) super.save(tblMchntRefuse);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblMchntRefuse a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblMchntRefuse tblMchntRefuse)
{
	super.saveOrUpdate(tblMchntRefuse);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblMchntRefuse a transient instance containing updated state
 */
public void update(com.huateng.po.TblMchntRefuse tblMchntRefuse)
{
	super.update(tblMchntRefuse);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblMchntRefuse the instance to be removed
 */
public void delete(com.huateng.po.TblMchntRefuse tblMchntRefuse)
{
	super.delete((Object) tblMchntRefuse);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.huateng.po.TblMchntRefusePK id)
{
	super.delete((Object) load(id));
}

}