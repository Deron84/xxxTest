package com.huateng.dao.impl.mchnt;

import com.huateng.dao.iface.mchnt.ITblMchtAcctInfDAO;
import com.huateng.po.mchnt.TblMchtAcctInf;
import com.sdses.dao._RootDAO;



public class TblMchtAcctInfDAO extends _RootDAO<TblMchtAcctInf> implements ITblMchtAcctInfDAO {

	public Class<TblMchtAcctInf> getReferenceClass () {
		return TblMchtAcctInf.class;
	}


	/**
	 * Cast the object as a TblMchtAcctInf
	 */
	public TblMchtAcctInf cast (Object object) {
		return (TblMchtAcctInf) object;
	}


	public TblMchtAcctInf load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblMchtAcctInf) load(getReferenceClass(), key);
	}

	public TblMchtAcctInf get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblMchtAcctInf) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtAcctInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(TblMchtAcctInf tblMchtAcctInf)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblMchtAcctInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtAcctInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtAcctInf tblMchtAcctInf)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtAcctInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtAcctInf a transient instance containing updated state
	 */
	public void update(TblMchtAcctInf tblMchtAcctInf)
		throws org.hibernate.HibernateException {
		super.update(tblMchtAcctInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtAcctInf the instance to be removed
	 */
	public void delete(TblMchtAcctInf tblMchtAcctInf)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtAcctInf);
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