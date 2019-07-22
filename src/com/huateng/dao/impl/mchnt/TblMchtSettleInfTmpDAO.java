package com.huateng.dao.impl.mchnt;

import com.huateng.dao.iface.mchnt.ITblMchtSettleInfTmpDAO;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.sdses.dao._RootDAO;


public class TblMchtSettleInfTmpDAO extends _RootDAO<TblMchtSettleInfTmp> implements ITblMchtSettleInfTmpDAO{



	// query name references




	public Class<TblMchtSettleInfTmp> getReferenceClass () {
		return TblMchtSettleInfTmp.class;
	}


	/**
	 * Cast the object as a TblMchtSettleInfTmp
	 */
	public TblMchtSettleInfTmp cast (Object object) {
		return (TblMchtSettleInfTmp) object;
	}


	public TblMchtSettleInfTmp load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblMchtSettleInfTmp) load(getReferenceClass(), key);
	}

	public TblMchtSettleInfTmp get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblMchtSettleInfTmp) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtSettleInfTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(TblMchtSettleInfTmp tblMchtSettleInfTmp)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblMchtSettleInfTmp);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtSettleInfTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtSettleInfTmp tblMchtSettleInfTmp)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtSettleInfTmp);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtSettleInfTmp a transient instance containing updated state
	 */
	public void update(TblMchtSettleInfTmp tblMchtSettleInfTmp)
		throws org.hibernate.HibernateException {
		super.update(tblMchtSettleInfTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtSettleInfTmp the instance to be removed
	 */
	public void delete(TblMchtSettleInfTmp tblMchtSettleInfTmp)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtSettleInfTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}

}