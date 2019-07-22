package com.huateng.dao.impl.term;

import java.util.Iterator;
import java.util.List;

import com.huateng.dao.base.BaseTblTermTmkLogDAO;
import com.huateng.po.TblTermTmkLog;
import com.huateng.po.mchtSrv.TblMchntParticipatReview;


public class TblTermTmkLogDAO extends BaseTblTermTmkLogDAO implements com.huateng.dao.iface.term.TblTermTmkLogDAO {

	public TblTermTmkLogDAO () {}

	public List<TblTermTmkLog> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void batchCheck(List<TblTermTmkLog> list) {
		Iterator<TblTermTmkLog> it = list.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().saveOrUpdate(it.next());
		}
	}

	public void batchRefuse(List<TblTermTmkLog> list) {
		Iterator<TblTermTmkLog> it = list.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().delete(it.next());
		}
	}
}