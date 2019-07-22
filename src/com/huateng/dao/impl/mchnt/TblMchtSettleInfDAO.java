package com.huateng.dao.impl.mchnt;

import com.huateng.dao.iface.mchnt.ITblMchtSettleInfDAO;
import com.huateng.po.mchnt.TblMchtSettleInf;
import com.sdses.dao._RootDAO;



public class TblMchtSettleInfDAO extends _RootDAO<TblMchtSettleInf> implements ITblMchtSettleInfDAO{



	// query name references




	public Class<TblMchtSettleInf> getReferenceClass () {
		return TblMchtSettleInf.class;
	}


	/**
	 * Cast the object as a TblMchtSettleInf
	 */
	public TblMchtSettleInf cast (Object object) {
		return (TblMchtSettleInf) object;
	}


	public TblMchtSettleInf load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblMchtSettleInf) load(getReferenceClass(), key);
	}

	public TblMchtSettleInf get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblMchtSettleInf) get(getReferenceClass(), key);
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtSettleInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(TblMchtSettleInf tblMchtSettleInf)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblMchtSettleInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtSettleInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtSettleInf tblMchtSettleInf)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtSettleInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtSettleInf a transient instance containing updated state
	 */
	public void update(TblMchtSettleInf tblMchtSettleInf)
		throws org.hibernate.HibernateException {
		super.update(tblMchtSettleInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtSettleInf the instance to be removed
	 */
	public void delete(TblMchtSettleInf tblMchtSettleInf)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtSettleInf);
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