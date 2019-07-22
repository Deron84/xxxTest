package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.po.TblBrhParam;
import com.sdses.dao._RootDAO;

public class TblBrhParamDAOTarget extends _RootDAO<com.huateng.po.TblBrhParam>
		implements com.huateng.dao.iface.base.TblBrhParamDAO {

	public TblBrhParamDAOTarget() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.dao.iface.TblBrhParamDAO#findAll()
	 */
	public List<TblBrhParam> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<com.huateng.po.TblBrhParam> getReferenceClass() {
		return com.huateng.po.TblBrhParam.class;
	}

	/**
	 * Cast the object as a com.huateng.po.TblBrhParam
	 */
	public com.huateng.po.TblBrhParam cast(Object object) {
		return (com.huateng.po.TblBrhParam) object;
	}

	public com.huateng.po.TblBrhParam load(String brhId) {
		return (com.huateng.po.TblBrhParam) load(getReferenceClass(), brhId);
	}

	public com.huateng.po.TblBrhParam get(String brhId) {
		return (com.huateng.po.TblBrhParam) get(getReferenceClass(), brhId);
	}

	@SuppressWarnings("unchecked")
	public java.util.List<com.huateng.po.TblBrhParam> loadAll() {
		return loadAll(getReferenceClass());
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param tblBrhParam
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.TblBrhParam tblBrhParam) {
		super.saveOrUpdate(tblBrhParam);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param tblBrhParam
	 *            a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblBrhParam tblBrhParam) {
		super.update(tblBrhParam);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param tblBrhParam
	 *            the instance to be removed
	 */
	public void delete(com.huateng.po.TblBrhParam tblBrhParam) {
		super.delete((Object) tblBrhParam);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	@Override
	public void save(TblBrhParam tblBrhParam) {
		super.save(tblBrhParam);
	}

}