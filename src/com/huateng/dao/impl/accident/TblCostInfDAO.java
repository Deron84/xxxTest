package com.huateng.dao.impl.accident;

import com.sdses.dao._RootDAO;

public class TblCostInfDAO extends _RootDAO<com.huateng.po.accident.TblCostInf> implements com.huateng.dao.iface.accident.TblCostInfDAO {

	public Class<com.huateng.po.accident.TblCostInf> getReferenceClass () {
		return com.huateng.po.accident.TblCostInf.class;
	}

	public com.huateng.po.accident.TblCostInf load(java.lang.String key)
	{
		return (com.huateng.po.accident.TblCostInf) load(getReferenceClass(), key);
	}
	
	public com.huateng.po.accident.TblCostInf get(java.lang.String key)
	{
		return (com.huateng.po.accident.TblCostInf) get(getReferenceClass(), key);
	}

	public java.lang.String save(com.huateng.po.accident.TblCostInf tblCostInf)
	{
		return (java.lang.String) super.save(tblCostInf);
	}

	public void update(com.huateng.po.accident.TblCostInf tblCostInf)
	{
		super.update(tblCostInf);
	}

	public void delete(com.huateng.po.accident.TblCostInf tblCostInf)
	{
		super.delete((Object) tblCostInf);
	}

	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}
}