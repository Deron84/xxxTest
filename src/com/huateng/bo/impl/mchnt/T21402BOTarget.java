package com.huateng.bo.impl.mchnt;

import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.bo.mchnt.T21402BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.mchnt.TblMchntDiscountRuleDAO;
import com.huateng.po.mchnt.TblMchntDiscountRule;
import com.huateng.system.util.CommonFunction;

public class T21402BOTarget implements T21402BO{

	private static Logger logger = Logger.getLogger(T21402BOTarget.class);
	
	private TblMchntDiscountRuleDAO tblMchntDiscountRuleDAO;
	
	public TblMchntDiscountRuleDAO getTblMchntDiscountRuleDAO() {
		return tblMchntDiscountRuleDAO;
	}

	public void setTblMchntDiscountRuleDAO(
			TblMchntDiscountRuleDAO tblMchntDiscountRuleDAO) {
		this.tblMchntDiscountRuleDAO = tblMchntDiscountRuleDAO;
	}

	@Override
	public int countNum(String id) {
		String countSql="select count(*) from TBL_MCHNT_DISCOUNTRULE where MAPPING_ID='" + id.trim()+"'";
		int countNum=Integer.parseInt(CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql));
		return countNum;
	}

	@Override
	public TblMchntDiscountRule get(String id) {
		return tblMchntDiscountRuleDAO.get(id);
	}

	@Override
	public String add(TblMchntDiscountRule cstSysParam) {
		tblMchntDiscountRuleDAO.save(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(String id) {
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(
                "select DISCOUNT_ID from TBL_MCHNT_DISCOUNTRULE where DISCOUNT_ID='" + id + "'");
        if (list == null || list.isEmpty()) {
            return "您所要删除的信息已经不存在";
        } else {
            CommonFunction.getCommQueryDAO().excute("delete from TBL_MCHNT_DISCOUNTRULE where DISCOUNT_ID='" + id + "'");
            return Constants.SUCCESS_CODE;
        }
	}

	@Override
	public String update(TblMchntDiscountRule cstSysParam) {
		tblMchntDiscountRuleDAO.update(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

}
