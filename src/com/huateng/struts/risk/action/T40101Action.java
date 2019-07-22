package com.huateng.struts.risk.action;

import java.util.Map;

import com.huateng.common.Constants;
import com.huateng.dao.common.SqlDao;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * 风险后续处理 project JSBConsole date 2013-3-26
 * 
 * @author 樊东东
 */
public class T40101Action extends BaseAction {

	private SqlDao sqlDao = (SqlDao) ContextUtil.getBean("sqlDao");

	/*
	 * 疑似套现风险交易
	 *  从0待处理状态改为 1 手工清算已办结; 不入 4 押款已办结; 改状态，不入 
	 *  的时候去修改
	 * tbl_mchnt_infile_dtl--商户入账信息表 的settle_flag的状态为1
	 * 
	 * 条件就是 abs_mon 和date_settlmt的年月一样 商户号一样且settle_flag状态为2
	 * 
	 * 
	 * 从0改为2的时候需要把settle_flag改成4
	 * 
	 * tbl_mchnt_infile_dtl没有数据、待测
	 * 
	 * 只能0、3状态能修改
	 * 
	 * 5 初始化但未触发风险; 不查出来
	 *  (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		String sql = "update ABS_MCHT_TRADE_MON set flag='" + dealType + "' "
				+ "where TRIM(ABS_MON)='" + mon + "' AND MCHT_NO='" + mchtCd
				+ "' AND FLAG='" + flag + "'";
		sqlDao.execute(sql);
		/**tbl_mchnt_infile_dtl--商户入账信息表 的settle_flag的状态为1
		 * 
		 * 条件就是 abs_mon 和date_settlmt的年月一样 商户号一样且settle_flag状态为2
		 */
//		if("0".equals(flag)&&("1".equals(dealType)||"4".equals(dealType))){
//			String sql1 = "update tbl_mchnt_infile_dtl set settle_flag='1'" +
//					" where " +
//					//可能会跨月
////					"substr(date_settlmt,1,4)='"+mon+"' and " +
//					" trim(mcht_no)='"+mchtCd+"' and settle_flag='2'";
//			sqlDao.execute(sql1);
//		}
		String sqlQuery="select whitelist_flag from tbl_mcht_settle_inf where trim(mcht_no)='"+mchtCd+"'";
		Map<String,String> map=(Map)sqlDao.queryforStrMap(sqlQuery);
		String whiteFlag=(String)map.get("WHITELIST_FLAG");
		if("0".equals(whiteFlag.trim())){
		if("0".equals(flag)&&"1".equals(dealType)){
			String sql1 = "update tbl_mchnt_infile_dtl set settle_flag='7',rec_upd_ts='"+CommonFunction.getCurrentDate()+CommonFunction.getCurrentTime()+"'" +
					" where " +
					//可能会跨月
//					"substr(date_settlmt,1,4)='"+mon+"' and " +
					" trim(mcht_no)='"+mchtCd+"' and settle_flag='2'";
			sqlDao.execute(sql1);
		}
		if("0".equals(flag)&&"4".equals(dealType)){
			String sql1 = "update tbl_mchnt_infile_dtl set settle_flag='5',rec_upd_ts='"+CommonFunction.getCurrentDate()+CommonFunction.getCurrentTime()+"'" +
					" where " +
					//可能会跨月
//					"substr(date_settlmt,1,4)='"+mon+"' and " +
					" trim(mcht_no)='"+mchtCd+"' and settle_flag='2'";
			sqlDao.execute(sql1);
		}
		if("0".equals(flag)&&"2".equals(dealType)){
			String sql1 = "update tbl_mchnt_infile_dtl set settle_flag='4',rec_upd_ts='"+CommonFunction.getCurrentDate()+CommonFunction.getCurrentTime()+"'" +
					" where " +
					//可能会跨月
//					"substr(date_settlmt,1,4)='"+mon+"' and " +
					" trim(mcht_no)='"+mchtCd+"' and settle_flag='2'";
			sqlDao.execute(sql1);
		}
		if("3".equals(flag)&&"1".equals(dealType)){
			String sql1 = "update tbl_mchnt_infile_dtl set settle_flag='7',rec_upd_ts='"+CommonFunction.getCurrentDate()+CommonFunction.getCurrentTime()+"'" +
					" where " +
					//可能会跨月
//					"substr(date_settlmt,1,4)='"+mon+"' and " +
					" trim(mcht_no)='"+mchtCd+"' and settle_flag='2'";
			sqlDao.execute(sql1);
		}
		if("3".equals(flag)&&"4".equals(dealType)){
			String sql1 = "update tbl_mchnt_infile_dtl set settle_flag='5',rec_upd_ts='"+CommonFunction.getCurrentDate()+CommonFunction.getCurrentTime()+"'" +
					" where " +
					//可能会跨月
//					"substr(date_settlmt,1,4)='"+mon+"' and " +
					" trim(mcht_no)='"+mchtCd+"' and settle_flag='2'";
			sqlDao.execute(sql1);
		}
		if("3".equals(flag)&&"2".equals(dealType)){
			String sql1 = "update tbl_mchnt_infile_dtl set settle_flag='4',rec_upd_ts='"+CommonFunction.getCurrentDate()+CommonFunction.getCurrentTime()+"'" +
					" where " +
					//可能会跨月
//					"substr(date_settlmt,1,4)='"+mon+"' and " +
					" trim(mcht_no)='"+mchtCd+"' and settle_flag='2'";
			sqlDao.execute(sql1);
		}
	}
		return Constants.SUCCESS_CODE;
	}

	private String mchtCd;

	private String mon;

	private String flag;
	
	private String oldFlag;

	private String dealType;
	
	

	
	public String getOldFlag() {
		return oldFlag;
	}

	
	public void setOldFlag(String oldFlag) {
		this.oldFlag = oldFlag;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
