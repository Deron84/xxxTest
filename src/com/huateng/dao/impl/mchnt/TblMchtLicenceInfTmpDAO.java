package com.huateng.dao.impl.mchnt;

import com.huateng.dao.iface.mchnt.ITblMchtLicenceInfTmpDAO;
import com.sdses.dao._RootDAO;


public class TblMchtLicenceInfTmpDAO extends _RootDAO<com.huateng.po.mchnt.TblMchtLicenceInfTmp> implements ITblMchtLicenceInfTmpDAO{



	// query name references




	public Class<com.huateng.po.mchnt.TblMchtLicenceInfTmp> getReferenceClass () {
		return com.huateng.po.mchnt.TblMchtLicenceInfTmp.class;
	}


	/**
	 * Cast the object as a com.huateng.po.mchnt.TblMchtBaseInfTmp
	 */
	public com.huateng.po.mchnt.TblMchtLicenceInfTmp cast (Object object) {
		return (com.huateng.po.mchnt.TblMchtLicenceInfTmp) object;
	}


	public com.huateng.po.mchnt.TblMchtLicenceInfTmp load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.huateng.po.mchnt.TblMchtLicenceInfTmp) load(getReferenceClass(), key);
	}

	public com.huateng.po.mchnt.TblMchtLicenceInfTmp get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.huateng.po.mchnt.TblMchtLicenceInfTmp) get(getReferenceClass(), key);
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtBaseInfTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.mchnt.TblMchtLicenceInfTmp tblMchtLicenceInfTmp)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblMchtLicenceInfTmp);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtBaseInfTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.mchnt.TblMchtLicenceInfTmp tblMchtLicenceInfTmp)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtLicenceInfTmp);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtBaseInfTmp a transient instance containing updated state
	 */
	public void update(com.huateng.po.mchnt.TblMchtLicenceInfTmp tblMchtLicenceInfTmp)
		throws org.hibernate.HibernateException {
		super.update(tblMchtLicenceInfTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtBaseInfTmp the instance to be removed
	 */
	public void delete(com.huateng.po.mchnt.TblMchtLicenceInfTmp tblMchtLicenceInfTmp)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtLicenceInfTmp);
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