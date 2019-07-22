package com.huateng.dao.iface.mchtSrv;

import java.io.Serializable;

public interface TblMchtCupInfoTmpDAO {
	public com.huateng.po.TblMchtCupInfoTmp get(java.lang.String key);

	public com.huateng.po.TblMchtCupInfoTmp load(java.lang.String key);

	public java.util.List<com.huateng.po.TblMchtCupInfoTmp> findAll ();

	public java.lang.String save(com.huateng.po.TblMchtCupInfoTmp tblMchtCupInfoTmp);

	public void saveOrUpdate(com.huateng.po.TblMchtCupInfoTmp tblMchtCupInfoTmp);

	public void update(com.huateng.po.TblMchtCupInfoTmp tblMchtCupInfoTmp);

	public void delete(java.lang.String id);

	public void delete(com.huateng.po.TblMchtCupInfoTmp tblMchtCupInfoTmp);
}
