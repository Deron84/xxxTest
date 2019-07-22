package com.huateng.dao.iface.accident;

import com.huateng.po.accident.TblRTxn;
import com.huateng.po.accident.TblRTxnPK;

public interface TblRTxnDAO {

	public void save(TblRTxn rTxn);

	public void update(TblRTxn rTxn);

	public void delete(TblRTxn rTxn);
	
	public TblRTxn get(TblRTxnPK key);
	
	public String executeSql(String sql) throws Exception;
	
}