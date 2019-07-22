package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblHisDiscAlgo;
import com.huateng.po.mchnt.TblHisDiscAlgoPK;
import com.sdses.dao._RootDAO;

public class TblHisDiscAlgoDAO extends _RootDAO<com.huateng.po.mchnt.TblHisDiscAlgo> implements com.huateng.dao.iface.mchnt.TblHisDiscAlgoDAO {

	public TblHisDiscAlgoDAO () {}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblDivMchntTmpDAO#findAll()
	 */
	public List<TblHisDiscAlgo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<TblHisDiscAlgo> getReferenceClass () {
		return TblHisDiscAlgo.class;
	}


	/**
	 * Cast the object as a TblHisDiscAlgo
	 */
	public TblHisDiscAlgo cast (Object object) {
		return (TblHisDiscAlgo) object;
	}


	public TblHisDiscAlgo load(TblHisDiscAlgoPK key)
	{
		return (TblHisDiscAlgo) load(getReferenceClass(), key);
	}

	public TblHisDiscAlgo get(TblHisDiscAlgoPK key)
	{
		return (TblHisDiscAlgo) get(getReferenceClass(), key);
	}

	@SuppressWarnings("unchecked")
	public java.util.List<TblHisDiscAlgo> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblDivMchntTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public TblHisDiscAlgoPK save(TblHisDiscAlgo tblDivMchntTmp)
	{
		return (TblHisDiscAlgoPK) super.save(tblDivMchntTmp);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblDivMchntTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblHisDiscAlgo tblDivMchntTmp)
	{
		super.saveOrUpdate(tblDivMchntTmp);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblDivMchntTmp a transient instance containing updated state
	 */
	public void update(TblHisDiscAlgo tblDivMchntTmp)
	{
		super.update(tblDivMchntTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblDivMchntTmp the instance to be removed
	 */
	public void delete(TblHisDiscAlgo tblDivMchntTmp)
	{
		super.delete((Object) tblDivMchntTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(TblHisDiscAlgoPK id)
	{
		super.delete((Object) load(id));
	}
}