package com.huateng.dao.impl.mchnt;

import com.sdses.dao._RootDAO;

public class TblMchtCheckInfDAO extends _RootDAO<com.huateng.po.mchnt.TblMchtCheckInf> implements com.huateng.dao.iface.mchnt.TblMchtCheckInfDAO {

	public Class<com.huateng.po.mchnt.TblMchtCheckInf> getReferenceClass () {
		return com.huateng.po.mchnt.TblMchtCheckInf.class;
	}

	public com.huateng.po.mchnt.TblMchtCheckInf load(com.huateng.po.mchnt.TblMchtCheckInfPK key)
	{
		return (com.huateng.po.mchnt.TblMchtCheckInf) load(getReferenceClass(), key);
	}

	public com.huateng.po.mchnt.TblMchtCheckInf get(com.huateng.po.mchnt.TblMchtCheckInfPK key)
	{
		return (com.huateng.po.mchnt.TblMchtCheckInf) get(getReferenceClass(), key);
	}

	public void save(com.huateng.po.mchnt.TblMchtCheckInf tblMchtCheckInf)
	{
		super.save(tblMchtCheckInf);
	}

	public void update(com.huateng.po.mchnt.TblMchtCheckInf tblMchtCheckInf)
	{
		super.update(tblMchtCheckInf);
	}
	
	public void delete(com.huateng.po.mchnt.TblMchtCheckInfPK id)
	{
		super.delete((Object) load(id));
	}
}