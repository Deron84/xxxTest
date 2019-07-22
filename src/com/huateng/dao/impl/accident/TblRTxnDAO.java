package com.huateng.dao.impl.accident;

import com.huateng.common.Constants;
import com.huateng.po.accident.TblRTxn;
import com.huateng.po.accident.TblRTxnPK;
import com.sdses.dao._RootDAO;

public class TblRTxnDAO extends _RootDAO<com.huateng.po.accident.TblRTxn> implements com.huateng.dao.iface.accident.TblRTxnDAO {

	public void save(TblRTxn rTxn) {

		super.save(rTxn);
	}

	public void update(TblRTxn rTxn) {
		
		super.update(rTxn);
	}

	public void delete(TblRTxn rTxn) {
		super.delete(rTxn);
	}

	protected Class getReferenceClass() {
		
		return com.huateng.po.accident.TblRTxn.class;
	}

	public com.huateng.po.accident.TblRTxn get(TblRTxnPK key) {
		
		return (com.huateng.po.accident.TblRTxn) get(getReferenceClass(), key);
	}

	public String executeSql(String sql) throws Exception {
		super.getSession().connection().prepareStatement(sql).execute();
		return Constants.SUCCESS_CODE;
	}
	
	
}