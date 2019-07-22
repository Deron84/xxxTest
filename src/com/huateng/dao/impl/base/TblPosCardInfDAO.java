package com.huateng.dao.impl.base;

import com.sdses.dao._RootDAO;

public class TblPosCardInfDAO extends _RootDAO<com.huateng.po.base.TblPosCardInf> implements com.huateng.dao.iface.base.TblPosCardInfDAO {

	public Class<com.huateng.po.base.TblPosCardInf> getReferenceClass () {
		return com.huateng.po.base.TblPosCardInf.class;
	}

	public com.huateng.po.base.TblPosCardInf load(java.lang.String key)
	{
		return (com.huateng.po.base.TblPosCardInf) load(getReferenceClass(), key);
	}

	public com.huateng.po.base.TblPosCardInf get(java.lang.String key)
	{
		return (com.huateng.po.base.TblPosCardInf) get(getReferenceClass(), key);
	}

	public java.lang.String save(com.huateng.po.base.TblPosCardInf tblPosCardInf)
	{
		return (java.lang.String) super.save(tblPosCardInf);
	}

	public void saveOrUpdate(com.huateng.po.base.TblPosCardInf tblPosCardInf)
	{
		super.saveOrUpdate(tblPosCardInf);
	}

	public void update(com.huateng.po.base.TblPosCardInf tblPosCardInf)
	{
		super.update(tblPosCardInf);
	}
	
	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}
}