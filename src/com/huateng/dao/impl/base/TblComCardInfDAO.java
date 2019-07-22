package com.huateng.dao.impl.base;

import com.sdses.dao._RootDAO;

public class TblComCardInfDAO extends _RootDAO<com.huateng.po.base.TblComCardInf> implements com.huateng.dao.iface.base.TblComCardInfDAO {

	public Class<com.huateng.po.base.TblComCardInf> getReferenceClass () {
		return com.huateng.po.base.TblComCardInf.class;
	}

	public com.huateng.po.base.TblComCardInf load(java.lang.String key)
	{
		return (com.huateng.po.base.TblComCardInf) load(getReferenceClass(), key);
	}

	public com.huateng.po.base.TblComCardInf get(java.lang.String key)
	{
		return (com.huateng.po.base.TblComCardInf) get(getReferenceClass(), key);
	}

	public java.lang.String save(com.huateng.po.base.TblComCardInf tblComCardInf)
	{
		return (java.lang.String) super.save(tblComCardInf);
	}

	public void saveOrUpdate(com.huateng.po.base.TblComCardInf tblComCardInf)
	{
		super.saveOrUpdate(tblComCardInf);
	}

	public void update(com.huateng.po.base.TblComCardInf tblComCardInf)
	{
		super.update(tblComCardInf);
	}
	
	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}
}