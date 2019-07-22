package com.huateng.dao.impl.risk;

import java.util.List;

import com.huateng.po.TblCtlMchtInf;
import com.sdses.dao._RootDAO;


public class TblCtlMchtInfDAO extends _RootDAO<com.huateng.po.TblCtlMchtInf> implements com.huateng.dao.iface.risk.TblCtlMchtInfDAO{

public TblCtlMchtInfDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblCtlMchtInfDAO#findAll()
 */
public List<TblCtlMchtInf> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblCtlMchtInf> getReferenceClass () {
	return com.huateng.po.TblCtlMchtInf.class;
}


/**
 * Cast the object as a com.huateng.po.TblCtlMchtInf
 */
public com.huateng.po.TblCtlMchtInf cast (Object object) {
	return (com.huateng.po.TblCtlMchtInf) object;
}


public com.huateng.po.TblCtlMchtInf load(java.lang.String key)
{
	return (com.huateng.po.TblCtlMchtInf) load(getReferenceClass(), key);
}

public com.huateng.po.TblCtlMchtInf get(java.lang.String key)
{
	return (com.huateng.po.TblCtlMchtInf) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblCtlMchtInf> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblCtlMchtInf a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.huateng.po.TblCtlMchtInf tblCtlMchtInf)
{
	return (java.lang.String) super.save(tblCtlMchtInf);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblCtlMchtInf a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblCtlMchtInf tblCtlMchtInf)
{
	super.saveOrUpdate(tblCtlMchtInf);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblCtlMchtInf a transient instance containing updated state
 */
public void update(com.huateng.po.TblCtlMchtInf tblCtlMchtInf)
{
	super.update(tblCtlMchtInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblCtlMchtInf the instance to be removed
 */
public void delete(com.huateng.po.TblCtlMchtInf tblCtlMchtInf)
{
	super.delete((Object) tblCtlMchtInf);
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