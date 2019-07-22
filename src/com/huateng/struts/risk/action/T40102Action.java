package com.huateng.struts.risk.action;


import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.common.SqlDao;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * 风险后续处理 project JSBConsole date 2013-3-26
 * 
 * @author 樊东东
 */
public class T40102Action extends BaseAction {

	ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");


	@Override
	protected String subExecute() throws Exception {
		String sqlMchtTp = "select CONN_TYPE from TBL_MCHT_BASE_INF where MCHT_NO ='"+ mchtNo +"'";
		List<Object[]> listTp = commQueryDAO.findBySQLQuery(sqlMchtTp);
		
		if(!"J".equals(listTp.get(0))){
			return "只能操作间联商户！";
		}

		String sql1 = null;
		String sql2 = null;
		String sql3 = null;
		if("1".equals(dealType)){//手工清算已办结
			sql1 = "update ABS_MCHT_TRADE_MON set flag='1' where MCHT_NO='" + mchtNo +"' AND FLAG in('0','3')";
			sql2 = "update ABS_SHIFT_TERM_MON set flag='1' where MCHT_NO='" + mchtNo +"' AND FLAG in('0','3')";
			sql3 = "update TBL_MCHNT_INFILE_DTL set SETTLE_FLAG='7' where MCHT_NO='" + mchtNo +"' AND SETTLE_FLAG ='2'";
			CommonFunction.getCommQueryDAO().excute(sql1);
			CommonFunction.getCommQueryDAO().excute(sql2);
			CommonFunction.getCommQueryDAO().excute(sql3);
		}
		if("2".equals(dealType)){//正常清算已办结
			sql1 = "update ABS_MCHT_TRADE_MON set flag='2' where MCHT_NO='" + mchtNo +"' AND FLAG in('0','3')";
			sql2 = "update ABS_SHIFT_TERM_MON set flag='2' where MCHT_NO='" + mchtNo +"' AND FLAG in('0','3')";
			sql3 = "update TBL_MCHNT_INFILE_DTL set SETTLE_FLAG='4' where MCHT_NO='" + mchtNo +"' AND SETTLE_FLAG ='2'";
			CommonFunction.getCommQueryDAO().excute(sql1);
			CommonFunction.getCommQueryDAO().excute(sql2);
			CommonFunction.getCommQueryDAO().excute(sql3);
		}
		if("3".equals(dealType)){//暂不清算等待处理
			sql1 = "update ABS_MCHT_TRADE_MON set flag='3' where MCHT_NO='" + mchtNo +"' AND FLAG in('0','3')";
			sql2 = "update ABS_SHIFT_TERM_MON set flag='3' where MCHT_NO='" + mchtNo +"' AND FLAG in('0','3')";
			CommonFunction.getCommQueryDAO().excute(sql1);
			CommonFunction.getCommQueryDAO().excute(sql2);
		}
		if("4".equals(dealType)){//押款已办结
			sql1 = "update ABS_MCHT_TRADE_MON set flag='4' where MCHT_NO='" + mchtNo +"' AND FLAG in('0','3')";
			sql2 = "update ABS_SHIFT_TERM_MON set flag='4' where MCHT_NO='" + mchtNo +"' AND FLAG in('0','3')";
			sql3 = "update TBL_MCHNT_INFILE_DTL set SETTLE_FLAG='5' where MCHT_NO='" + mchtNo +"' AND SETTLE_FLAG ='2'";
			CommonFunction.getCommQueryDAO().excute(sql1);
			CommonFunction.getCommQueryDAO().excute(sql2);
			CommonFunction.getCommQueryDAO().excute(sql3);
		}

		return Constants.SUCCESS_CODE;
	}

	private String mchtNo;
	private String FLAG;
	private String dealType;

	public String getMchtNo() {
		return mchtNo;
	}
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String flag) {
		FLAG = flag;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
}
