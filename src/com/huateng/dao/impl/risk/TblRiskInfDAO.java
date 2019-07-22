package com.huateng.dao.impl.risk;

import java.util.List;

import com.huateng.po.TblRiskInf;
import com.sdses.dao._RootDAO;


public class TblRiskInfDAO extends _RootDAO<com.huateng.po.TblRiskInf> implements com.huateng.dao.iface.risk.TblRiskInfDAO{

public TblRiskInfDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblRiskInfDAO#findAll()
 */
public List<TblRiskInf> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.TblRiskInf> getReferenceClass () {
	return com.huateng.po.TblRiskInf.class;
}


/**
 * Cast the object as a com.huateng.po.TblRiskInf
 */
public com.huateng.po.TblRiskInf cast (Object object) {
	return (com.huateng.po.TblRiskInf) object;
}


public com.huateng.po.TblRiskInf load(java.lang.String key)
{
	return (com.huateng.po.TblRiskInf) load(getReferenceClass(), key);
}

public com.huateng.po.TblRiskInf get(java.lang.String key)
{
	return (com.huateng.po.TblRiskInf) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblRiskInf> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRiskInf a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.huateng.po.TblRiskInf tblRiskInf)
{
	return (java.lang.String) super.save(tblRiskInf);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblRiskInf a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblRiskInf tblRiskInf)
{
	super.saveOrUpdate(tblRiskInf);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRiskInf a transient instance containing updated state
 */
public void update(com.huateng.po.TblRiskInf tblRiskInf)
{
	super.update(tblRiskInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblRiskInf the instance to be removed
 */
public void delete(com.huateng.po.TblRiskInf tblRiskInf)
{
	super.delete((Object) tblRiskInf);
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