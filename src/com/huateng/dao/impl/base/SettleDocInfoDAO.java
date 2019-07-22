package com.huateng.dao.impl.base;

import com.sdses.dao._RootDAO;

public class SettleDocInfoDAO extends _RootDAO<com.huateng.po.SettleDocInfo> implements com.huateng.dao.iface.base.SettleDocInfoDAO{

	public Class<com.huateng.po.SettleDocInfo> getReferenceClass () {
		return com.huateng.po.SettleDocInfo.class;
	}

	public com.huateng.po.SettleDocInfo cast (Object object) {
		return (com.huateng.po.SettleDocInfo) object;
	}

	public com.huateng.po.SettleDocInfo load(java.lang.String key)
	{
		return (com.huateng.po.SettleDocInfo) load(getReferenceClass(), key);
	}

	public com.huateng.po.SettleDocInfo get(java.lang.String key)
	{
		return (com.huateng.po.SettleDocInfo) get(getReferenceClass(), key);
	}

	public java.lang.String save(com.huateng.po.SettleDocInfo settleDocInfo)
	{
		return (java.lang.String) super.save(settleDocInfo);
	}

	public void update(com.huateng.po.SettleDocInfo settleDocInfo)
	{
		super.update(settleDocInfo);
	}

	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}
}