package com.huateng.dao.iface.accident;

public interface TblEntrustTradeDAO {

	public void save(com.huateng.po.accident.TblEntrustTrade t);

	public void update(com.huateng.po.accident.TblEntrustTrade t);

	public void delete(com.huateng.po.accident.TblEntrustTrade t);
	
	public com.huateng.po.accident.TblEntrustTrade get(java.lang.String key);
}
