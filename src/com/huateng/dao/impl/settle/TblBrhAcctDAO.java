package com.huateng.dao.impl.settle;

import java.util.List;

import com.huateng.po.TblBrhAcct;
import com.sdses.dao._RootDAO;

public class TblBrhAcctDAO extends _RootDAO<com.huateng.po.TblBrhAcct> implements com.huateng.dao.iface.settle.TblBrhAcctDAO {

	public TblBrhAcctDAO () {}

	public List<TblBrhAcct> findAll() {
		return null;
	}

	public Class<com.huateng.po.TblBrhAcct> getReferenceClass () {
		return com.huateng.po.TblBrhAcct.class;
	}

	public com.huateng.po.TblBrhAcct cast (Object object) {
		return (com.huateng.po.TblBrhAcct) object;
	}


	public com.huateng.po.TblBrhAcct load(java.lang.String key)
	{
		return (com.huateng.po.TblBrhAcct) load(getReferenceClass(), key);
	}

	public com.huateng.po.TblBrhAcct get(java.lang.String key)
	{
		return (com.huateng.po.TblBrhAcct) get(getReferenceClass(), key);
	}

	public java.lang.String save(com.huateng.po.TblBrhAcct tblBrhAcct)
	{
		return (java.lang.String) super.save(tblBrhAcct);
	}

	public void saveOrUpdate(com.huateng.po.TblBrhAcct tblBrhAcct)
	{
		super.saveOrUpdate(tblBrhAcct);
	}

	public void update(com.huateng.po.TblBrhAcct tblBrhAcct)
	{
		super.update(tblBrhAcct);
	}

	public void delete(com.huateng.po.TblBrhAcct tblBrhAcct)
	{
		super.delete((Object) tblBrhAcct);
	}

	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}
}