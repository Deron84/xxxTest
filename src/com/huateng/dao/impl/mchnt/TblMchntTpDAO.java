package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblInfMchntTp;
import com.huateng.po.mchnt.TblInfMchntTpPK;
import com.sdses.dao._RootDAO;



public class TblMchntTpDAO extends _RootDAO<com.huateng.po.TblMchntRefuse> implements com.huateng.dao.iface.mchnt.TblMchntTpDAO{

public TblMchntTpDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblMchntTpDAO#findAll()
 */
public List<TblInfMchntTp> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<TblInfMchntTp> getReferenceClass () {
	return TblInfMchntTp.class;
}


/**
 * Cast the object as a TblInfMchntTp
 */
public TblInfMchntTp cast (Object object) {
	return (TblInfMchntTp) object;
}


public TblInfMchntTp load(TblInfMchntTpPK key)
{
	return (TblInfMchntTp) load(getReferenceClass(), key);
}

public TblInfMchntTp get(TblInfMchntTpPK key)
{
	return (TblInfMchntTp) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<TblInfMchntTp> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblMchntTp a transient instance of a persistent class
 * @return the class identifier
 */
public TblInfMchntTpPK save(TblInfMchntTp tblMchntTp)
{
	return (TblInfMchntTpPK) super.save(tblMchntTp);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblMchntTp a transient instance containing new or updated state
 */
public void saveOrUpdate(TblInfMchntTp tblMchntTp)
{
	super.saveOrUpdate(tblMchntTp);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblMchntTp a transient instance containing updated state
 */
public void update(TblInfMchntTp tblMchntTp)
{
	super.update(tblMchntTp);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblMchntTp the instance to be removed
 */
public void delete(TblInfMchntTp tblMchntTp)
{
	super.delete((Object) tblMchntTp);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(TblInfMchntTpPK id)
{
	super.delete((Object) load(id));
}

}