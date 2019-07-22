package com.huateng.dao.impl.base;

import com.sdses.dao._RootDAO;

public class TblIPosInfDAO extends _RootDAO<com.huateng.po.base.TblIPosInf> implements com.huateng.dao.iface.base.TblIPosInfDAO {

	public Class<com.huateng.po.base.TblIPosInf> getReferenceClass () {
		return com.huateng.po.base.TblIPosInf.class;
	}

	public com.huateng.po.base.TblIPosInf cast (Object object) {
		return (com.huateng.po.base.TblIPosInf) object;
	}

	public com.huateng.po.base.TblIPosInf load(java.lang.String key)
	{
		return (com.huateng.po.base.TblIPosInf) load(getReferenceClass(), key);
	}

	public com.huateng.po.base.TblIPosInf get(java.lang.String key)
	{
		return (com.huateng.po.base.TblIPosInf) get(getReferenceClass(), key);
	}

	public java.lang.String save(com.huateng.po.base.TblIPosInf tblIPosInf)
	{
		return (java.lang.String) super.save(tblIPosInf);
	}

	public void update(com.huateng.po.base.TblIPosInf tblIPosInf)
	{
		super.update(tblIPosInf);
	}

	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}
}