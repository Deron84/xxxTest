package com.huateng.dao.iface.mchtSrv;

import java.io.Serializable;

public interface TblMchtCupInfoDAO {
	public com.huateng.po.TblMchtCupInfo get(java.lang.String key);

	public com.huateng.po.TblMchtCupInfo load(java.lang.String key);

	public java.util.List<com.huateng.po.TblMchtCupInfo> findAll ();

	public java.lang.String save(com.huateng.po.TblMchtCupInfo tblMchtCupInfo);

	public void saveOrUpdate(com.huateng.po.TblMchtCupInfo tblMchtCupInfo);

	public void update(com.huateng.po.TblMchtCupInfo tblMchtCupInfo);

	public void delete(java.lang.String id);

	public void delete(com.huateng.po.TblMchtCupInfo tblMchtCupInfo);
}
