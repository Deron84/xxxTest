package com.huateng.dao.iface.mchnt;

public interface TblMchtCheckInfDAO {
	
	public com.huateng.po.mchnt.TblMchtCheckInf get(com.huateng.po.mchnt.TblMchtCheckInfPK key);

	public void save(com.huateng.po.mchnt.TblMchtCheckInf tblMchtCheckInf);

	public void update(com.huateng.po.mchnt.TblMchtCheckInf tblMchtCheckInf);

	public void delete(com.huateng.po.mchnt.TblMchtCheckInfPK id);
}