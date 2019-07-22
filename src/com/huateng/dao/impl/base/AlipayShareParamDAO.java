package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.common.StringUtil;
import com.huateng.po.AlipayShareParam;
import com.sdses.dao._RootDAO;


public class AlipayShareParamDAO extends _RootDAO<com.huateng.po.AlipayShareParam> implements com.huateng.dao.iface.base.AlipayShareParamDAO{


/* (non-Javadoc)
 * @see com.huateng.dao.iface.AlipayShareParamDAO#findAll()
 */
public List<AlipayShareParam> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.huateng.po.AlipayShareParam> getReferenceClass () {
	return com.huateng.po.AlipayShareParam.class;
}


/**
 * Cast the object as a com.huateng.po.AlipayShareParam
 */
public com.huateng.po.AlipayShareParam cast (Object object) {
	return (com.huateng.po.AlipayShareParam) object;
}

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.AlipayShareParam> loadAll()
{
	return loadAll(getReferenceClass());
}


/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param alipayShareParam a transient instance of a persistent class
 * @return the class identifier
 */
public void save(com.huateng.po.AlipayShareParam alipayShareParam)
{
	super.save(alipayShareParam);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param alipayShareParam a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.AlipayShareParam alipayShareParam)
{
	super.saveOrUpdate(alipayShareParam);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param alipayShareParam a transient instance containing updated state
 */
public void update(com.huateng.po.AlipayShareParam alipayShareParam)
{
	super.update(alipayShareParam);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param alipayShareParam the instance to be removed
 */
public void delete(com.huateng.po.AlipayShareParam alipayShareParam)
{
	super.delete((Object) alipayShareParam);
}

@Override
public AlipayShareParam get(String brhId) {
	return (AlipayShareParam)get(getReferenceClass(), brhId);
}

}