package com.sdses.dao;

import com.sdses.po.TblTxnInfo;
import com.sdses.po.TblTxnInfoPK;

public class TblTxnInfoDAO extends _RootDAO<com.huateng.po.TblTermZmkInf> {

public TblTxnInfoDAO () {}


public Class<TblTxnInfo> getReferenceClass () {
	return TblTxnInfo.class;
}


/**
 * Cast the object as a com.huateng.po.TblTxnInfo
 */
public TblTxnInfo cast (Object object) {
	return (TblTxnInfo) object;
}


public TblTxnInfo load(TblTxnInfoPK key)
{
	return (TblTxnInfo) load(getReferenceClass(), key);
}

public TblTxnInfo get(TblTxnInfoPK key)
{
	return (TblTxnInfo) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<TblTxnInfo> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblTxnInfo a transient instance of a persistent class
 * @return the class identifier
 */
public TblTxnInfoPK save(TblTxnInfo tblTxnInfo)
{
	return (TblTxnInfoPK) super.save(tblTxnInfo);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblTxnInfo a transient instance containing new or updated state
 */
public void saveOrUpdate(TblTxnInfo tblTxnInfo)
{
	super.saveOrUpdate(tblTxnInfo);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblTxnInfo a transient instance containing updated state
 */
public void update(TblTxnInfo tblTxnInfo)
{
	super.update(tblTxnInfo);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblTxnInfo the instance to be removed
 */
public void delete(TblTxnInfo tblTxnInfo)
{
	super.delete((Object) tblTxnInfo);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(TblTxnInfoPK id)
{
	super.delete((Object) load(id));
}
}