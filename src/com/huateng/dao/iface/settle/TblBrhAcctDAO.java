package com.huateng.dao.iface.settle;

public interface TblBrhAcctDAO {
	
	public com.huateng.po.TblBrhAcct get(java.lang.String key);

	public com.huateng.po.TblBrhAcct load(java.lang.String key);

	public java.util.List<com.huateng.po.TblBrhAcct> findAll ();

	public java.lang.String save(com.huateng.po.TblBrhAcct tblBrhAcct);

	public void saveOrUpdate(com.huateng.po.TblBrhAcct tblBrhAcct);

	public void update(com.huateng.po.TblBrhAcct tblBrhAcct);

	public void delete(java.lang.String id);

	public void delete(com.huateng.po.TblBrhAcct tblBrhAcct);


}