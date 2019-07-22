package com.huateng.bo.impl.mchnt;

import java.util.List;

import com.huateng.bo.mchnt.T20101BO;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.mchnt.TblMchntInfoTmpDAO;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;

public class T20101BOTarget implements T20101BO {
	
	private TblMchntInfoTmpDAO tblMchntInfoTmpDAO;
    private ICommQueryDAO commQueryDAO;

	
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	
	public void setTblMchntInfoTmpDAO(TblMchntInfoTmpDAO tblMchntInfoTmpDAO) {
		this.tblMchntInfoTmpDAO = tblMchntInfoTmpDAO;
	}
	
	public TblMchtBaseInfTmp get(String mchntId) {
		return tblMchntInfoTmpDAO.get(mchntId);
	}
	
	@Override
	public List<Object[]> findListByMappingMchntcdOne(String mappingMchntcdOne) {
		String sql = "select MCHT_NO,MAPPING_MCHNTCDONE from TBL_MCHT_BASE_INF_TMP where MAPPING_MCHNTCDONE = '" + mappingMchntcdOne+"'";
		return commQueryDAO.findBySQLQuery(sql);
	}
	@Override
	public List<Object[]> findListByMappingMchntcdTwo(String mappingMchntcdTwo) {
		String sql = "select MCHT_NO,MAPPING_MCHNTCDTWO from TBL_MCHT_BASE_INF_TMP where MAPPING_MCHNTCDTWO = '" + mappingMchntcdTwo+"'";
		return commQueryDAO.findBySQLQuery(sql);
	}
}
