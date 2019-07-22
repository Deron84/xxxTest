package com.huateng.dao.impl.mchnt;

import com.huateng.dao.iface.mchnt.ITblGroupMchtInfDAO;
import com.huateng.po.mchnt.TblGroupMchtInf;
import com.sdses.dao._RootDAO;



public class TblGroupMchtInfDAO extends _RootDAO<TblGroupMchtInf> implements ITblGroupMchtInfDAO {

	public Class<TblGroupMchtInf> getReferenceClass () {
		return TblGroupMchtInf.class;
	}

	public TblGroupMchtInf cast (Object object) {
		return (TblGroupMchtInf) object;
	}


	public TblGroupMchtInf load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblGroupMchtInf) load(getReferenceClass(), key);
	}

	public TblGroupMchtInf get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblGroupMchtInf) get(getReferenceClass(), key);
	}


	public java.lang.String save(TblGroupMchtInf tblGroupMchtInf)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblGroupMchtInf);
	}

	public void saveOrUpdate(TblGroupMchtInf tblGroupMchtInf)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblGroupMchtInf);
	}


	public void update(TblGroupMchtInf tblGroupMchtInf)
		throws org.hibernate.HibernateException {
		super.update(tblGroupMchtInf);
	}

	public void delete(TblGroupMchtInf tblGroupMchtInf)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblGroupMchtInf);
	}

	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}

}