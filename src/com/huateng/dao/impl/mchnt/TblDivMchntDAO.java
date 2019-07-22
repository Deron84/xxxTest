package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.po.TblDivMchnt;
import com.sdses.dao._RootDAO;


public class TblDivMchntDAO extends _RootDAO<com.huateng.po.TblDivMchnt> implements com.huateng.dao.iface.mchnt.TblDivMchntDAO{

public TblDivMchntDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblDivMchntDAO#findAll()
 */
public List<TblDivMchnt> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblDivMchnt> getReferenceClass () {
	return com.huateng.po.TblDivMchnt.class;
}


/**
 * Cast the object as a com.huateng.po.TblDivMchnt
 */
public com.huateng.po.TblDivMchnt cast (Object object) {
	return (com.huateng.po.TblDivMchnt) object;
}


public com.huateng.po.TblDivMchnt load(com.huateng.po.TblDivMchntPK key)
{
	return (com.huateng.po.TblDivMchnt) load(getReferenceClass(), key);
}

public com.huateng.po.TblDivMchnt get(com.huateng.po.TblDivMchntPK key)
{
	return (com.huateng.po.TblDivMchnt) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblDivMchnt> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblDivMchnt a transient instance of a persistent class
 * @return the class identifier
 */
public com.huateng.po.TblDivMchntPK save(com.huateng.po.TblDivMchnt tblDivMchnt)
{
	return (com.huateng.po.TblDivMchntPK) super.save(tblDivMchnt);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblDivMchnt a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblDivMchnt tblDivMchnt)
{
	super.saveOrUpdate(tblDivMchnt);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblDivMchnt a transient instance containing updated state
 */
public void update(com.huateng.po.TblDivMchnt tblDivMchnt)
{
	super.update(tblDivMchnt);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblDivMchnt the instance to be removed
 */
public void delete(com.huateng.po.TblDivMchnt tblDivMchnt)
{
	super.delete((Object) tblDivMchnt);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.huateng.po.TblDivMchntPK id)
{
	super.delete((Object) load(id));
}
}