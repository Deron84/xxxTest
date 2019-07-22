package com.huateng.bo.impl.mchnt;

import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.bo.mchnt.T21401BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.mchnt.TblMchntMapDAO;
import com.huateng.po.mchnt.TblMchntMap;
import com.huateng.po.mchnt.TblMchntMapPK;
import com.huateng.system.util.CommonFunction;

public class T21401BOTarget implements T21401BO{

	private static Logger logger = Logger.getLogger(T21401BOTarget.class);
	
	private TblMchntMapDAO tblMchntMapTmpDAO;
    private ICommQueryDAO commQueryDAO;

	
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	
	public TblMchntMapDAO getTblMchntMapTmpDAO() {
		return tblMchntMapTmpDAO;
	}

	public void setTblMchntMapTmpDAO(TblMchntMapDAO tblMchntMapTmpDAO) {
		this.tblMchntMapTmpDAO = tblMchntMapTmpDAO;
	}

	@Override
	public int countNum(TblMchntMapPK id) {
		String countSql="select count(*) from TBL_MCHNT_MAP where MAPPING_ID='" + id.getMappingId().trim()+"'";
		int countNum=Integer.parseInt(CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql));
		return countNum;
	}

	@Override
	public TblMchntMap get(TblMchntMapPK id) {
		return tblMchntMapTmpDAO.get(id);
	}

	@Override
	public String add(TblMchntMap cstSysParam) {
		tblMchntMapTmpDAO.save(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(String mappingId) {
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(
                "select MCHNT_ID from TBL_MCHNT_MAP where MAPPING_ID='" + mappingId + "'");
        if (list == null || list.isEmpty()) {
            return "您所要删除的商户映射信息已经不存在";
        } else {
            CommonFunction.getCommQueryDAO().excute("delete from TBL_MCHNT_MAP where MAPPING_ID='" + mappingId + "'");
            return Constants.SUCCESS_CODE;
        }
	}

	@Override
	public String update(TblMchntMap cstSysParam) {
		tblMchntMapTmpDAO.update(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public List<Object[]> findMappingListByTwoId(String mchntId,
			String equipmentId) {
		String sql = "select MAPPING_ID,MCHNT_ID from TBL_MCHNT_MAP where MCHNT_ID = '" + mchntId+ "'";
		sql=sql+" and EQUIPMENT_ID= '"+equipmentId+ "'";		
		return commQueryDAO.findBySQLQuery(sql);		
	}

	@Override
	public List<Object[]> findMappingListByThreeId(String mchntId,
			String equipmentId, String acquiresId) {
		String sql = "select MAPPING_ID,MCHNT_ID from TBL_MCHNT_MAP where MCHNT_ID = '" + mchntId+ "'";
		sql=sql+" and EQUIPMENT_ID= '"+equipmentId+ "'";
		sql=sql+" and ACQUIRERS_ID= '"+acquiresId+ "'";
		return commQueryDAO.findBySQLQuery(sql);
	}
	

}
