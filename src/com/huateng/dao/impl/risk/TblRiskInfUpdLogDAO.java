package com.huateng.dao.impl.risk;

import com.sdses.dao._RootDAO;

public class TblRiskInfUpdLogDAO extends
		_RootDAO<com.huateng.po.TblRiskInfUpdLog> implements
		com.huateng.dao.iface.risk.TblRiskInfUpdLogDAO {

	public TblRiskInfUpdLogDAO() {
	}

	public java.lang.String save(com.huateng.po.TblRiskInfUpdLog tblRiskInfUpdLog) {
		return (java.lang.String) super.save(tblRiskInfUpdLog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.dao._RootDAO#getReferenceClass()
	 */
	@Override
	public Class<com.huateng.po.TblRiskInfUpdLog> getReferenceClass() {
		return com.huateng.po.TblRiskInfUpdLog.class;
	}

}