package com.huateng.dao.impl.mchnt;


import com.huateng.po.mchnt.TblMchtBranInf;
import com.huateng.po.mchnt.TblMchtBranInfPK;
import com.sdses.dao._RootDAO;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class TblMchtBranInfDAO  extends _RootDAO<TblMchtBranInf> implements com.huateng.dao.iface.mchnt.TblMchtBranInfDAO{
	// query name references




	public Class<TblMchtBranInf> getReferenceClass () {
		return TblMchtBranInf.class;
	}


	/**
	 * Cast the object as a TblMchtBranInf
	 */
	public TblMchtBranInf cast (Object object) {
		return (TblMchtBranInf) object;
	}


	public TblMchtBranInf load(TblMchtBranInfPK key)
		throws org.hibernate.HibernateException {
		return (TblMchtBranInf) load(getReferenceClass(), key);
	}

	public TblMchtBranInf get(TblMchtBranInfPK key)
		throws org.hibernate.HibernateException {
		return (TblMchtBranInf) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtBranInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public TblMchtBranInfPK save(TblMchtBranInf tblMchtBranInf)
		throws org.hibernate.HibernateException {
		return (TblMchtBranInfPK) super.save(tblMchtBranInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtBranInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtBranInf tblMchtBranInf)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtBranInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtBranInf a transient instance containing updated state
	 */
	public void update(TblMchtBranInf tblMchtBranInf)
		throws org.hibernate.HibernateException {
		super.update(tblMchtBranInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtBranInf the instance to be removed
	 */
	public void delete(TblMchtBranInf tblMchtBranInf)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtBranInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(TblMchtBranInfPK id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}

}
