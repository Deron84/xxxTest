package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.po.CstSysParam;
import com.sdses.dao._RootDAO;


public class CstSysParamDAO extends _RootDAO<com.huateng.po.CstSysParam> implements com.huateng.dao.iface.base.CstSysParamDAO{

public CstSysParamDAO () {}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.CstSysParamDAO#findAll()
 */
public List<CstSysParam> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.CstSysParam> getReferenceClass () {
	return com.huateng.po.CstSysParam.class;
}


/**
 * Cast the object as a com.huateng.po.CstSysParam
 */
public com.huateng.po.CstSysParam cast (Object object) {
	return (com.huateng.po.CstSysParam) object;
}


public com.huateng.po.CstSysParam load(com.huateng.po.CstSysParamPK key)
{
	return (com.huateng.po.CstSysParam) load(getReferenceClass(), key);
}

public com.huateng.po.CstSysParam get(com.huateng.po.CstSysParamPK key)
{
	return (com.huateng.po.CstSysParam) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.CstSysParam> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param cstSysParam a transient instance of a persistent class
 * @return the class identifier
 */
public com.huateng.po.CstSysParamPK save(com.huateng.po.CstSysParam cstSysParam)
{
	return (com.huateng.po.CstSysParamPK) super.save(cstSysParam);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param cstSysParam a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.CstSysParam cstSysParam)
{
	super.saveOrUpdate(cstSysParam);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param cstSysParam a transient instance containing updated state
 */
public void update(com.huateng.po.CstSysParam cstSysParam)
{
	super.update(cstSysParam);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param cstSysParam the instance to be removed
 */
public void delete(com.huateng.po.CstSysParam cstSysParam)
{
	super.delete((Object) cstSysParam);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.huateng.po.CstSysParamPK id)
{
	super.delete((Object) load(id));
}

}