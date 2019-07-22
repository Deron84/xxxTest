package com.huateng.dao.iface.mchnt;

import com.huateng.po.mchnt.TblInfDiscCd;

public interface TblInfDiscCdDAO {
	
	public abstract Class getReferenceClass();

	public abstract TblInfDiscCd get(java.lang.String key);

	public abstract TblInfDiscCd load(java.lang.String key);

    public abstract java.lang.String save(TblInfDiscCd tblInfDiscCd);

    public abstract void saveOrUpdate(TblInfDiscCd tblInfDiscCd);

    public abstract void update(TblInfDiscCd tblInfDiscCd);

    public abstract void delete(java.lang.String id);

    public abstract void delete(TblInfDiscCd tblInfDiscCd);

    public abstract void refresh (TblInfDiscCd tblInfDiscCd);

	
}