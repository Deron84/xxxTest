package com.huateng.dao.impl.accident;

import com.huateng.po.accident.TblEntrustTrade;
import com.sdses.dao._RootDAO;

public class TblEntrustTradeDAO extends _RootDAO<com.huateng.po.accident.TblEntrustTrade> implements com.huateng.dao.iface.accident.TblEntrustTradeDAO {

	public void save(TblEntrustTrade t) {

		super.save(t);
	}

	public void update(TblEntrustTrade t) {
		
		super.update(t);
	}

	public void delete(TblEntrustTrade t) {
		super.delete(t);
	}

	protected Class getReferenceClass() {
		
		return com.huateng.po.accident.TblEntrustTrade.class;
	}

	public com.huateng.po.accident.TblEntrustTrade get(java.lang.String key) {
		
		return (com.huateng.po.accident.TblEntrustTrade) get(getReferenceClass(), key);
	}
}