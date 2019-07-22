package com.huateng.dao.iface.mchnt;

import com.huateng.po.mchnt.TblMchtBranInf;
import com.huateng.po.mchnt.TblMchtBranInfPK;

public interface TblMchtBranInfDAO {
	
	public Class<TblMchtBranInf> getReferenceClass ();
	/**
	 * Cast the object as a TblMchtBranInf
	 */
	public TblMchtBranInf cast (Object object);

	public TblMchtBranInf load(TblMchtBranInfPK key)throws org.hibernate.HibernateException ;

	public TblMchtBranInf get(TblMchtBranInfPK key)
		throws org.hibernate.HibernateException ;
	
	public TblMchtBranInfPK save(TblMchtBranInf tblMchtBranInf)
		throws org.hibernate.HibernateException ;
	
	public void saveOrUpdate(TblMchtBranInf tblMchtBranInf)
		throws org.hibernate.HibernateException ;
	
	public void update(TblMchtBranInf tblMchtBranInf)
		throws org.hibernate.HibernateException;
	
	public void delete(TblMchtBranInf tblMchtBranInf)
		throws org.hibernate.HibernateException;
	
	public void delete(TblMchtBranInfPK id)
		throws org.hibernate.HibernateException ;

}
