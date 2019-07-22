package com.huateng.dao.impl.base;

import com.huateng.po.TblBrhTlrInfo;
import com.huateng.po.TblBrhTlrInfoPK;
import com.sdses.dao._RootDAO;


public class TblBrhTlrInfoDao extends _RootDAO<com.huateng.po.TblBrhTlrInfo> implements com.huateng.dao.iface.base.TblBrhTlrInfoDao{

public TblBrhTlrInfoDao () {}

/* (non-Javadoc)
 * @see com.huateng.dao._RootDAO#getReferenceClass()
 */

public Class<TblBrhTlrInfo> getReferenceClass () {
	return TblBrhTlrInfo.class;
}


/* (non-Javadoc)
 * @see com.huateng.dao.iface.base.TblBrhTlrInfoDao#delete(java.lang.Integer)
 */
public void delete(TblBrhTlrInfoPK id) {
	super.delete((Object) load(id));
}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.base.TblBrhTlrInfoDao#delete(com.huateng.po.TblBrhTlrInfo)
 */
public void delete(TblBrhTlrInfo tblBrhTlrInfo) {
	super.delete((Object) tblBrhTlrInfo);
}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.base.TblBrhTlrInfoDao#get(com.huateng.po.TblBrhTlrInfoPK)
 */
public TblBrhTlrInfo get(TblBrhTlrInfoPK key) {
	return (TblBrhTlrInfo) super.get(getReferenceClass(), key);
}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.base.TblBrhTlrInfoDao#load(com.huateng.po.TblBrhTlrInfoPK)
 */
public TblBrhTlrInfo load(TblBrhTlrInfoPK key) {
	// TODO Auto-generated method stub
	return (TblBrhTlrInfo) super.load(getReferenceClass(),key);
}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.base.TblBrhTlrInfoDao#refresh(com.huateng.po.TblBrhTlrInfo)
 */
public void refresh(TblBrhTlrInfo tblBrhTlrInfo) {
	super.refresh(tblBrhTlrInfo);
}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.base.TblBrhTlrInfoDao#save(com.huateng.po.TblBrhTlrInfo)
 */
public TblBrhTlrInfoPK save(TblBrhTlrInfo tblBrhTlrInfo) {
	return (TblBrhTlrInfoPK) super.save(tblBrhTlrInfo);
}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.base.TblBrhTlrInfoDao#saveOrUpdate(com.huateng.po.TblBrhTlrInfo)
 */
public void saveOrUpdate(TblBrhTlrInfo tblBrhTlrInfo) {
	super.saveOrUpdate(tblBrhTlrInfo);
}

/* (non-Javadoc)
 * @see com.huateng.dao.iface.base.TblBrhTlrInfoDao#update(com.huateng.po.TblBrhTlrInfo)
 */
public void update(TblBrhTlrInfo tblBrhTlrInfo) {
	super.update(tblBrhTlrInfo);
	
}


}