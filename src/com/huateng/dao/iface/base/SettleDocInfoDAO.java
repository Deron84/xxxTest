package com.huateng.dao.iface.base;

public interface SettleDocInfoDAO {
	
	public com.huateng.po.SettleDocInfo get(java.lang.String key);

	public com.huateng.po.SettleDocInfo load(java.lang.String key);

	public java.lang.String save(com.huateng.po.SettleDocInfo settleDocInfo);

	public void update(com.huateng.po.SettleDocInfo settleDocInfo);

	public void delete(java.lang.String id);
}