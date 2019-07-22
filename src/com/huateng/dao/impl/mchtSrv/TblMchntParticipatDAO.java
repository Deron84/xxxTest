package com.huateng.dao.impl.mchtSrv;

import java.util.Iterator;
import java.util.List;

import com.huateng.po.mchtSrv.TblMchntParticipat;



public class TblMchntParticipatDAO extends BaseTblMchntParticipatDAO implements com.huateng.dao.iface.mchtSrv.TblMchntParticipatDAO{

public TblMchntParticipatDAO () {}

	public List<TblMchntParticipat> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	public void saveBatch(List<TblMchntParticipat> list)
	{
		Iterator<TblMchntParticipat> it = list.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().saveOrUpdate(it.next());
		}
	}

}