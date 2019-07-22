package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblInfMchntTpGrp;
import com.huateng.po.mchnt.TblInfMchntTpGrpPK;
import com.sdses.dao._RootDAO;



public class TblMchntTpGrpDAO extends _RootDAO<com.huateng.po.TblMchntRefuse> implements com.huateng.dao.iface.mchnt.TblMchntTpGrpDAO{

public TblMchntTpGrpDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblMchntTpGrpDAO#findAll()
 */
public List<TblInfMchntTpGrp> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<TblInfMchntTpGrp> getReferenceClass () {
	return TblInfMchntTpGrp.class;
}


/**
 * Cast the object as a TblInfMchntTpGrp
 */
public TblInfMchntTpGrp cast (Object object) {
	return (TblInfMchntTpGrp) object;
}


public TblInfMchntTpGrp load(TblInfMchntTpGrpPK id)
{
	return (TblInfMchntTpGrp) load(getReferenceClass(), id);
}

public TblInfMchntTpGrp get(TblInfMchntTpGrpPK id)
{
	return (TblInfMchntTpGrp) get(getReferenceClass(),id);
}

@SuppressWarnings("unchecked")
public java.util.List<TblInfMchntTpGrp> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param TblInfMchntTpGrp a transient instance of a persistent class
 * @return the class identifier
 */
public TblInfMchntTpGrpPK save(TblInfMchntTpGrp TblInfMchntTpGrp)
{
	return (TblInfMchntTpGrpPK) super.save(TblInfMchntTpGrp);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param TblInfMchntTpGrp a transient instance containing new or updated state
 */
public void saveOrUpdate(TblInfMchntTpGrp TblInfMchntTpGrp)
{
	super.saveOrUpdate(TblInfMchntTpGrp);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param TblInfMchntTpGrp a transient instance containing updated state
 */
public void update(TblInfMchntTpGrp TblInfMchntTpGrp)
{
	super.update(TblInfMchntTpGrp);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param TblInfMchntTpGrp the instance to be removed
 */
public void delete(TblInfMchntTpGrp TblInfMchntTpGrp)
{
	super.delete((Object) TblInfMchntTpGrp);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(	TblInfMchntTpGrpPK id)
{
	super.delete((Object) load(id));
}

}