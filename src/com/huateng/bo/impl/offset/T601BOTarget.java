package com.huateng.bo.impl.offset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.bo.offset.T601BO;
import com.huateng.common.Constants;
import com.huateng.dao.common.SqlDao;
import com.huateng.system.util.CommonFunction;

/**
 * project JSBConsole date 2013-4-8
 * 
 * @author 樊东东
 */
public class T601BOTarget implements T601BO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.bo.offset.T60102BO#deal(java.util.Map)
	 */
	public String deal(Map<String, String> params) throws Exception {
		
		String method = params.get("method");
		if ("add".equals(method)) {
			return add(params);
		}
		if ("delete".equals(method)) {
			return delete(params);
		}
		return "无效请求";
	}

	/**
	 * 作废
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private String delete(Map<String, String> params) throws Exception {

		String txnDate = params.get("txnDate");
		String txnTime = params.get("txnTime");
		String mchtNo = params.get("mchtNo");
		String txnAmt = params.get("txnAmt");
		String txnOpr = params.get("txnOpr");
//		String oprSta = params.get("oprSta");
		String settleSta = params.get("settleSta");
		if ("1".equals(settleSta)) {
			return "该条记录已清算,不能作废";
		}
//		if ("0".equals(oprSta)) {
//			return "该条记录已作废";
//		}
		String sql = "update tbl_d_txn set OPR_STA='0' where INST_DATE='"
				+ txnDate + "' and " + "INST_TIME='" + txnTime
				+ "' and SETTLE_DATE='" + txnDate + "' and MCHT_NO='" + mchtNo
				+ "'" + " and TRANS_AMT='" + txnAmt + "' and CREATE_OPR='"
				+ txnOpr + "' and OPR_STA='1' and SETTLE_STATUS='2'";
		try {
			sqlDao.update(sql);
		} catch (Exception e) {
			throw e;
		}

		return Constants.SUCCESS_CODE;
	}

	/**
	 * 补登
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private String add(Map<String, String> params) throws Exception {

		String txnAmt = params.get("txnAmt");
		String mchtNo = params.get("mchtNo");
		String record = params.get("record");
		String txnNum = params.get("txnNum");
		String oprId = params.get("oprId");
		
		if (StringUtils.isEmpty(record)) {
			record = "";
		}
		
		String nowDate = CommonFunction.getCurrentDate();
		String nowTime = CommonFunction.getCurrentTime();
		
		String sqlSysSeqNun="SELECT SEQ_SYS_NO.NEXTVAL SYSNUM FROM DUAL";
		
		Map<String,String> map=sqlDao.queryforStrMap(sqlSysSeqNun);
		String sysNum=(String)map.get("SYSNUM");
		
		sysNum = CommonFunction.fillString(sysNum, '0', 6, false);

		String sql = "insert into tbl_d_txn(INST_DATE,INST_TIME,SETTLE_DATE,SYS_SEQ_NUM," +
										   "MCHT_NO,TRANS_AMT,CREATE_OPR,UPDATE_DATE," +
										   "UPDATE_OPR,OPR_STA,SETTLE_STATUS,RECORD,TXN_NUM) " +
									"values(?,?,?,?," +
										   "?,?,?,?," +
										   "?,?,?,?,?)";
		List<String> pl = new ArrayList<String>();
		pl.add(nowDate);// INST_DATE交易发生日期
		pl.add(nowTime);// INST_TIME交易发生时间
		pl.add("-");// SETTLE_DATE清算日期--不需要前台插值，字段非空
		pl.add(sysNum);
		pl.add(mchtNo);// MCHT_NO商户号
		pl.add(txnAmt);// TRANS_AMT交易金额
		pl.add(oprId);// CREATE_OPR记录操作员
		pl.add(nowDate + nowTime);// UPDATE_DATE更新日期时间
		pl.add(oprId);// UPDATE_OPR更新操作员
		pl.add("1");// OPR_STA操作状态 0-无效作废 1- 强制发起
		pl.add("2");// SETTLE_STATUS清算处理状态 1. 已清算2. 未清算
		pl.add(record);// RECORD补登原因
		pl.add(txnNum);// TXN_NUM交易码
		try {
			sqlDao.execute(sql, pl);
		} catch (Exception e) {
			throw e;
		}
		return Constants.SUCCESS_CODE;
	}

	private SqlDao sqlDao;

	public SqlDao getSqlDao() {
		return sqlDao;
	}

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

}
