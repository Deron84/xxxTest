package com.huateng.dao.impl.risk;

import java.util.List;

import com.huateng.po.TblCtlCardInf;
import com.sdses.dao._RootDAO;


public class TblCtlCardInfDAO extends _RootDAO<com.huateng.po.TblCtlCardInf> implements com.huateng.dao.iface.risk.TblCtlCardInfDAO{

public TblCtlCardInfDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblCtlCardInfDAO#findAll()
 */
public List<TblCtlCardInf> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblCtlCardInf> getReferenceClass () {
	return com.huateng.po.TblCtlCardInf.class;
}


/**
 * Cast the object as a com.huateng.po.TblCtlCardInf
 */
public com.huateng.po.TblCtlCardInf cast (Object object) {
	return (com.huateng.po.TblCtlCardInf) object;
}


public com.huateng.po.TblCtlCardInf load(java.lang.String key)
{
	return (com.huateng.po.TblCtlCardInf) load(getReferenceClass(), key);
}

public com.huateng.po.TblCtlCardInf get(java.lang.String key)
{
	return (com.huateng.po.TblCtlCardInf) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblCtlCardInf> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblCtlCardInf a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.huateng.po.TblCtlCardInf tblCtlCardInf)
{
	return (java.lang.String) super.save(tblCtlCardInf);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblCtlCardInf a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblCtlCardInf tblCtlCardInf)
{
	super.saveOrUpdate(tblCtlCardInf);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblCtlCardInf a transient instance containing updated state
 */
public void update(com.huateng.po.TblCtlCardInf tblCtlCardInf)
{
	super.update(tblCtlCardInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblCtlCardInf the instance to be removed
 */
public void delete(com.huateng.po.TblCtlCardInf tblCtlCardInf)
{
	super.delete((Object) tblCtlCardInf);
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