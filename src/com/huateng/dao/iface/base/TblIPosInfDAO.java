package com.huateng.dao.iface.base;

public interface TblIPosInfDAO {
	
	public com.huateng.po.base.TblIPosInf get(java.lang.String key);

	public java.lang.String save(com.huateng.po.base.TblIPosInf tblIPosInf);

	public void update(com.huateng.po.base.TblIPosInf tblIPosInf);

	public void delete(java.lang.String id);
}