package com.huateng.dao.impl.mchtSrv;

import java.util.Iterator;
import java.util.List;

import com.huateng.po.mchtSrv.TblMchntParticipatReview;



public class TblMchntParticipatReviewDAO extends BaseTblMchntParticipatReviewDAO implements com.huateng.dao.iface.mchtSrv.TblMchntParticipatReviewDAO{

	public TblMchntParticipatReviewDAO () {}
	
	public List<TblMchntParticipatReview> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void deleteBatch(List<TblMchntParticipatReview> list) {
		Iterator<TblMchntParticipatReview> it = list.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().saveOrUpdate(it.next());
		}
	}

	public void saveBatch(List<TblMchntParticipatReview> list)
	{
		Iterator<TblMchntParticipatReview> it = list.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().saveOrUpdate(it.next());
		}
	}

}