package com.huateng.dao.iface.base;

public interface TblComCardInfDAO {
	
	public com.huateng.po.base.TblComCardInf get(java.lang.String key);

	public java.lang.String save(com.huateng.po.base.TblComCardInf tblComCardInf);

	public void saveOrUpdate(com.huateng.po.base.TblComCardInf tblComCardInf);

	public void update(com.huateng.po.base.TblComCardInf tblComCardInf);

	public void delete(java.lang.String id);
}