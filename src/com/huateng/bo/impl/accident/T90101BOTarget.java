package com.huateng.bo.impl.accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.huateng.bo.accident.T90101BO;
import com.huateng.common.Constants;
import com.huateng.dao.common.SqlDao;
import com.huateng.system.util.CommonFunction;

public class T90101BOTarget implements T90101BO {

	/*
	 * 退货强制发起或作废作废 (non-Javadoc)
	 * 
	 * @see com.huateng.bo.accident.T90101BO#delete(java.util.Map)
	 */
	public String deal(Map<String, String> params) throws Exception { 
		
		String nowDate = CommonFunction.getCurrentDate();// 当前日期
		String nowTime = CommonFunction.getCurrentTime();// 当前时间
		String oprId = params.get("oprId");
		String instDate = params.get("instDate");// 交易日期
		String txnDate = params.get("txnDate");// 原交易日期
		String instTime = params.get("instTime");// 交易时间
		String key = params.get("key");
		String txnOpr = params.get("txnOpr");// 记录柜员
		String oprSta = params.get("oprSta");// 操作状态
		String settleSta = params.get("settleSta");// 清算状态
		String pan = params.get("pan");
		String txnAmt = params.get("txnAmt");// 原交易金额
		String retAmtStr = params.get("retAmt");// 退货金额 本次手工退货的退货金额
		String txnSsn = params.get("txnSsn");
		String record = params.get("record");// 退货原因
		String retFee = params.get("retFee");// 退货手续费
		String matchSta = params.get("matchSta");// 匹配状态
		String auditSta = params.get("auditSta");
		String method = params.get("method");
		String mchtNo=params.get("mchtNo");
		String sta = "";
		if ("force".equals(method)) {
			sta = "1";
		} else if ("delete".equals(method)) {
			sta = "2";
		} else {
			return "无效请求";
		}
		// 判断该条记录是否是强制退货状态和未清算状态
		if(!"0".equals(auditSta) && !auditSta.isEmpty()){
			return "请等待审核";
		}
		if (!"10".equals(matchSta) && !"11".equals(matchSta)) {
			return "不是重复且匹配单笔(多笔)的退货记录";
		}
		if (!"1".equals(oprSta) && !"2".equals(oprSta)) {
			return "不是异常的操作状态";
		}
		if (!"1".equals(settleSta)) {
			return "不是待清算状态";
		}
		try {
			// 修改退货表 tbl_r_txn --退货流水表 修改审核状态
			String updateSql2 = "update tbl_r_txn set AUDIT_STA='" + sta + "'," +
													 "update_date = '" +(nowDate + nowTime) + "' "+
					"where trim(ORIG_DATE)='"+ txnDate +"' " +	// ORIG_DATE原交易日期
					  "and trim(INST_DATE)='"+ instDate +"' " +	// INST_DATE交易日期
					  "and trim(INST_TIME)='"+ instTime +"' " + // INST_TIME交易时间
					  "and trim(PAN)='"+ pan +"' " +			// PAN卡号
					  "and trim(CREATE_OPR)='"+ txnOpr +"' " +  // CREATE_OPR记录柜员
					  "and match_sta='"+ matchSta +"' " +       // MATCH_STA匹配状态
					  "and Settle_status='1' " +
					  "and mcht_no='"+mchtNo  +"' ";            //mchtNo商户号
			
			if(!txnSsn.isEmpty()){// SYS_SEQ_NUM系统参考号
				updateSql2 += " and trim(SYS_SEQ_NUM)='"+ txnSsn +"' ";
			}
			if(!key.isEmpty()){// KEY_RSP关键值
				updateSql2 += " and trim(KEY_RSP)='"+ key +"' ";
			}
			if(!retAmtStr.isEmpty()){// RETURN_AMT退货金额
				updateSql2 += " and to_number(RETURN_AMT)/100='"+ retAmtStr +"' ";
			}
			if(!txnAmt.isEmpty()){// ORIG_AMT原交易金额
				updateSql2 += " and to_number(ORIG_AMT)/100='"+ txnAmt +"' ";
			}
			if(!retFee.isEmpty()){// RETURN_FEE退货手续费
				updateSql2 += " and to_number(RETURN_FEE)/100='"+ retFee +"' ";
			}		
			
			CommonFunction.getCommQueryDAO().excute(updateSql2);

			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			throw e;
		}
	}

	private SqlDao sqlDao;

	public SqlDao getSqlDao() {
		return sqlDao;
	}

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

}
