package com.huateng.bo.impl.mchnt;

import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.bo.mchnt.T21403BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.mchnt.TblMchntDiscountRuleBindDAO;
import com.huateng.po.mchnt.TblMchntDiscountRuleBind;
import com.huateng.po.mchnt.TblMchntDiscountRuleBindPK;
import com.huateng.system.util.CommonFunction;

public class T21403BOTarget implements T21403BO{

	private static Logger logger = Logger.getLogger(T21403BOTarget.class);
	
	private TblMchntDiscountRuleBindDAO tblMchntDiscountRuleBindDAO;
	private ICommQueryDAO commQueryDAO;

	
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	public TblMchntDiscountRuleBindDAO getTblMchntDiscountRuleBindDAO() {
		return tblMchntDiscountRuleBindDAO;
	}

	public void setTblMchntDiscountRuleBindDAO(
			TblMchntDiscountRuleBindDAO tblMchntDiscountRuleBindDAO) {
		this.tblMchntDiscountRuleBindDAO = tblMchntDiscountRuleBindDAO;
	}

	@Override
	public int countNum(TblMchntDiscountRuleBindPK id) {
		String countSql="select count(*) from TBL_MCHNT_DISCOUNTRULE_BIND where BIND_ID='" + id.getBindId().trim()+"'"
		;		
		int countNum=Integer.parseInt(CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql));
		return countNum;
	}

	

	@Override
	public String add(TblMchntDiscountRuleBind cstSysParam) {
		tblMchntDiscountRuleBindDAO.save(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblMchntDiscountRuleBindPK id) {
		int countNum = countNum(id);
        if (countNum == 0 ) {
            return "您所要删除的商户映射信息已经不存在";
        } else {
            CommonFunction.getCommQueryDAO().excute("delete from TBL_MCHNT_DISCOUNTRULE_BIND where BIND_ID='" + id.getBindId().trim()+"'"
            		
            		           		
            		);
            return Constants.SUCCESS_CODE;
        }
	}

	@Override
	public String update(TblMchntDiscountRuleBind cstSysParam) {
		tblMchntDiscountRuleBindDAO.update(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public TblMchntDiscountRuleBind get(TblMchntDiscountRuleBindPK id) {
		// TODO Auto-generated method stub
		return tblMchntDiscountRuleBindDAO.get(id);
	}

	@Override
	public List<Object[]> findBindListByDiscountId(
			String discountId) {		
		String sql = "select MCHNT_ID,EQUIPMENT_ID,ACQUIRERS_ID,DISCOUNT_ID from TBL_MCHNT_DISCOUNTRULE_BIND where DISCOUNT_ID = '" + discountId+"'";
		return commQueryDAO.findBySQLQuery(sql);
	}

	@Override
	public List<Object[]> findBindListByEquipmentId(String equipmentId) {
		String sql = "select MCHNT_ID,EQUIPMENT_ID,ACQUIRERS_ID,DISCOUNT_ID from TBL_MCHNT_DISCOUNTRULE_BIND where EQUIPMENT_ID = '" + equipmentId+"'";
		return commQueryDAO.findBySQLQuery(sql);
	}

	@Override
	public List<Object[]> findTermListAllOne() {
		String sql = "SELECT t.MAPPING_MCHNTCDONE,t.MAPPING_TERMIDONE,t.MAPPING_MCHNTTYPEONE "
				+ " FROM tbl_term_inf t"
				+ " where 1=1 "
				+ " and TERM_STA in (1,2)"
				+ " and MAPPING_MCHNTCDONE is not null and MAPPING_TERMIDONE is not null";
		return commQueryDAO.findBySQLQuery(sql);
	}

	@Override
	public List<Object[]> findTermListAllTwo() {
		String sql = "SELECT t.MAPPING_MCHNTCDTWO,t.MAPPING_TERMIDTWO,t.MAPPING_MCHNTTYPETWO "
				+ " FROM tbl_term_inf t"
				+ " where 1=1 "
				+ " and TERM_STA in (1,2)"
				+ " and MAPPING_MCHNTCDTWO is not null and MAPPING_TERMIDTWO is not null";
		return commQueryDAO.findBySQLQuery(sql);
	}

	@Override
	public List<Object[]> findTermListAllOneMchntStr(String mchntStr) {
		String[] mchntcdArr = mchntStr.split(",");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<mchntcdArr.length;i++){
			sb.append("'").append(mchntcdArr[i]).append("'").append(",");
			if(i==mchntcdArr.length-1){
				sb.append("'").append(mchntcdArr[i]).append("'");
			}
		}
		String sql = "SELECT t.MAPPING_MCHNTCDONE,t.MAPPING_TERMIDONE,t.MAPPING_MCHNTTYPEONE "
				+ " FROM tbl_term_inf t"
				+ " where 1=1 "
				+ " and TERM_STA in (1,2)"
				+ " and MAPPING_MCHNTCDONE in ("+sb.toString()+")";
		return commQueryDAO.findBySQLQuery(sql);
	}
	@Override
	public List<Object[]> findTermListAllTwoMchntStr(String mchntStr) {
		String[] mchntcdArr = mchntStr.split(",");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<mchntcdArr.length;i++){
			sb.append("'").append(mchntcdArr[i]).append("'").append(",");
			if(i==mchntcdArr.length-1){
				sb.append("'").append(mchntcdArr[i]).append("'");
			}
		}
		String sql = "SELECT t.MAPPING_MCHNTCDTWO,t.MAPPING_TERMIDTWO,t.MAPPING_MCHNTTYPETWO "
				+ " FROM tbl_term_inf t"
				+ " where 1=1 "
				+ " and TERM_STA in (1,2)"				
				+ " and MAPPING_MCHNTCDTWO in ("+sb.toString()+")";
		return commQueryDAO.findBySQLQuery(sql);
	}

	@Override
	public List<Object[]> findTermListAllOneTermStr(String termStr) {
		String[] termStrArr = termStr.split(",");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<termStrArr.length;i++){
			sb.append("'").append(termStrArr[i]).append("'").append(",");
			if(i==termStrArr.length-1){
				sb.append("'").append(termStrArr[i]).append("'");
			}
		}
		String sql = "SELECT t.MAPPING_MCHNTCDONE,t.MAPPING_TERMIDONE,t.MAPPING_MCHNTTYPEONE "
				+ " FROM tbl_term_inf t"
				+ " where 1=1 "
				+ " and TERM_STA in (1,2)"
				+ " and MAPPING_TERMIDONE in ("+sb.toString()+")";
		return commQueryDAO.findBySQLQuery(sql);
	}

	

	@Override
	public List<Object[]> findTermListAllTwoTermStr(String termStr) {
		String[] termStrArr = termStr.split(",");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<termStrArr.length;i++){
			sb.append("'").append(termStrArr[i]).append("'").append(",");
			if(i==termStrArr.length-1){
				sb.append("'").append(termStrArr[i]).append("'");
			}
		}
		String sql = "SELECT t.MAPPING_MCHNTCDTWO,t.MAPPING_TERMIDTWO,t.MAPPING_MCHNTTYPETWO "
				+ " FROM tbl_term_inf t"
				+ " where 1=1 "
				+ " and TERM_STA in (1,2)"
				+ " and MAPPING_TERMIDTWO in ("+sb.toString()+")";				
		return commQueryDAO.findBySQLQuery(sql);
	}
	

	@Override
	public List<Object[]> findByTermIdTimes(String equipmentId,
			String stratTime, String endTime) {
		String sql = "select MCHNT_ID,EQUIPMENT_ID,ACQUIRERS_ID,DISCOUNT_ID "
				+ " from TBL_MCHNT_DISCOUNTRULE_BIND "				
				+ " where EQUIPMENT_ID = '" + equipmentId+"'"
				+ " and ((START_TIME <='"+ stratTime+"'"
				+ " and END_TIME >='"+ stratTime+"')"
				+ " or (START_TIME <='"+ endTime+"'"
				+ " and END_TIME >='"+ endTime+"'))"
				;
		return commQueryDAO.findBySQLQuery(sql);
	}

	@Override
	public List<Object[]> findBindListByDiscountCode(String discountCode) {
		String sql = "select DISCOUNT_ID,DISCOUNT_CODE from TBL_MCHNT_DISCOUNTRULE where DISCOUNT_CODE = '" + discountCode+"'";
		return commQueryDAO.findBySQLQuery(sql);
	}

}
