package com.huateng.dao.iface.accident;

public interface TblManualReturnDAO {

	public void save(com.huateng.po.accident.TblManualReturn tblManualReturn);

	public void update(com.huateng.po.accident.TblManualReturn tblManualReturn);

	public void delete(com.huateng.po.accident.TblManualReturn tblManualReturn);
	
	public com.huateng.po.accident.TblManualReturn get(java.lang.String key);
	
}