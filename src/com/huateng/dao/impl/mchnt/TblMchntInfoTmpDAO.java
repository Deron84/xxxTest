package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.sdses.dao._RootDAO;


public class TblMchntInfoTmpDAO extends _RootDAO<TblMchtBaseInfTmp> implements com.huateng.dao.iface.mchnt.TblMchntInfoTmpDAO{

public TblMchntInfoTmpDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblMchntInfoTmpDAO#findAll()
 */
public List<TblMchtBaseInfTmp> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<TblMchtBaseInfTmp> getReferenceClass () {
	return TblMchtBaseInfTmp.class;
}


/**
 * Cast the object as a TblMchtBaseInfTmp
 */
public TblMchtBaseInfTmp cast (Object object) {
	return (TblMchtBaseInfTmp) object;
}


public TblMchtBaseInfTmp load(java.lang.String key)
{
	return (TblMchtBaseInfTmp) load(getReferenceClass(), key);
}

public TblMchtBaseInfTmp get(java.lang.String key)
{
	return (TblMchtBaseInfTmp) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<TblMchtBaseInfTmp> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param TblMchtBaseInfTmp a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(TblMchtBaseInfTmp tblMchtBaseInfTmp)
{
	return (java.lang.String) super.save(tblMchtBaseInfTmp);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param TblMchtBaseInfTmp a transient instance containing new or updated state
 */
public void saveOrUpdate(TblMchtBaseInfTmp tblMchtBaseInfTmp)
{
	super.saveOrUpdate(tblMchtBaseInfTmp);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param TblMchtBaseInfTmp a transient instance containing updated state
 */
public void update(TblMchtBaseInfTmp tblMchtBaseInfTmp)
{
	super.update(tblMchtBaseInfTmp);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param TblMchtBaseInfTmp the instance to be removed
 */
public void delete(TblMchtBaseInfTmp tblMchtBaseInfTmp)
{
	super.delete((Object) tblMchtBaseInfTmp);
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