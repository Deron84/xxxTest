package com.huateng.bo.impl.mchnt;

import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.bo.mchnt.T21405BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.mchnt.TblMchntFeeDAO;
import com.huateng.po.mchnt.TblMchntFee;
import com.huateng.system.util.CommonFunction;

public class T21405BOTarget implements T21405BO{

	private static Logger logger = Logger.getLogger(T21405BOTarget.class);
	
	private TblMchntFeeDAO tblMchntFeeDAO;
	
	public TblMchntFeeDAO getTblMchntFeeDAO() {
		return tblMchntFeeDAO;
	}

	public void setTblMchntFeeDAO(
			TblMchntFeeDAO tblMchntFeeDAO) {
		this.tblMchntFeeDAO = tblMchntFeeDAO;
	}

	@Override
	public int countNum(String id) {
		String countSql="select count(*) from TBL_MCHNT_FEE where MCHNT_ID='" + id.trim()+"'";
		int countNum=Integer.parseInt(CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql));
		return countNum;
	}

	@Override
	public TblMchntFee get(String id) {
		return tblMchntFeeDAO.get(id);
	}

	@Override
	public String add(TblMchntFee cstSysParam) {
		tblMchntFeeDAO.save(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(String id) {
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(
                "select MCHNT_ID from TBL_MCHNT_FEE where MCHNT_ID='" + id + "'");
        if (list == null || list.isEmpty()) {
            return "您所要删除的信息已经不存在";
        } else {
            CommonFunction.getCommQueryDAO().excute("delete from TBL_MCHNT_FEE where MCHNT_ID='" + id + "'");
            return Constants.SUCCESS_CODE;
        }
	}

	@Override
	public String update(TblMchntFee cstSysParam) {
		tblMchntFeeDAO.update(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String saveOrupdate(TblMchntFee cstSysParam) {
		tblMchntFeeDAO.saveOrUpdate(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

}
