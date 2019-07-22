package com.huateng.dao.iface.base;

public interface TblPosCardInfDAO {
	
	public com.huateng.po.base.TblPosCardInf get(java.lang.String key);

	public java.lang.String save(com.huateng.po.base.TblPosCardInf tblPosCardInf);

	public void saveOrUpdate(com.huateng.po.base.TblPosCardInf tblPosCardInf);

	public void update(com.huateng.po.base.TblPosCardInf tblPosCardInf);

	public void delete(java.lang.String id);
}