package com.huateng.dao.iface.accident;

import com.huateng.po.accident.TblCostInf;

public interface TblCostInfDAO {
	
	public TblCostInf get(java.lang.String key);
	public java.lang.String save(TblCostInf tblCostInf);
	public void update(TblCostInf tblCostInf);
	public void delete(java.lang.String id);
}