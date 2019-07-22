package com.huateng.bo.impl.accident;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.accident.T90301BO;
import com.huateng.common.BtlDtlErrGcConstants;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.dao.common.SqlDao;
import com.huateng.system.util.CommonFunction;

/**
 * project JSBConsole date 2013-3-19
 * 
 * @author 樊东东
 */
public class T90301BOTarget implements T90301BO {

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.bo.accident.T90301BO#deal()
	 */
	public String deal(Map<String, String> params,HttpServletRequest request) throws Exception {
		
		String updateSql = "";
		String subTxnId = "0"+params.get("subTxnId");			// 状态
		String trans_date_time = params.get("trans_date_time");	// 交易传输时间
		String pan = params.get("pan");							// 主账号
		String acqTxnSsn = params.get("acqTxnSsn");				// 收单流水号
		String outSsn = params.get("outSsn");					//外系统流水号
		String nowDate = CommonFunction.getCurrentDate();
		
		String whereSql = "where 1=1 ";
		
		if(StringUtil.isNotEmpty(trans_date_time)){
			whereSql += " and trim(trans_date_time)='" + trans_date_time + "' ";
		}
		
		if(StringUtil.isNotEmpty(pan)){
			whereSql += " and trim(ACQ_PAN)='" + pan + "'";
		}
		
		if(StringUtil.isNotEmpty(acqTxnSsn)){
			whereSql += " and trim(ACQ_TXN_SSN)='" + acqTxnSsn + "'";
		}
		
		if(StringUtil.isNotEmpty(outSsn)){
			whereSql += " and trim(OUT_SSN)='" + outSsn + "'";
		}
		
		String upSql = "update bth_Dtl_Err_Gc set err_proc_cd='" + subTxnId + "' " + whereSql;

		
		//正常交易向商户清算
		if(BtlDtlErrGcConstants.TXN_ACQ_TYPE_0.equals(subTxnId)){
		}
		
		//等待调查押款处理
		if(BtlDtlErrGcConstants.TXN_ACQ_TYPE_2.equals(subTxnId)){
			
		}
		
		//行方托收，作办结处理
		if(BtlDtlErrGcConstants.TXN_ACQ_TYPE_4.equals(subTxnId)){
			
		}
		
		// 多收款项退回发卡行 
		if(BtlDtlErrGcConstants.TXN_ACQ_TYPE_5.equals(subTxnId)){
//			insertTblRTxn(params,request);
		}
		
		
//		System.out.println(upSql);
		sqlDao.execute(upSql);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @param params
	 * @throws Exception 
	 */
	private void insertTblRTxn(Map<String, String> params,HttpServletRequest request) throws Exception {
		try{
			Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
				
			String oprId = params.get("oprId");
			String nowDate = CommonFunction.getCurrentDate();
			String trans_date_time = params.get("trans_date_time");// 交易传输时间
			String pan = params.get("pan");// 主账号
			String acqTxnSsn = params.get("acqTxnSsn");// 终端流水号
			String outSsn = params.get("outSsn");//外系统流水号
			
			String txnSsn = acqTxnSsn.isEmpty()?outSsn:acqTxnSsn;
			
			String whereSql = "where 1=1 ";
			
			if(StringUtil.isNotEmpty(trans_date_time)){
				whereSql += " and trim(trans_date_time)='" + trans_date_time + "' ";
			}
			
			if(StringUtil.isNotEmpty(pan)){
				whereSql += " and trim(ACQ_PAN)='" + pan + "'";
			}
			
			if(StringUtil.isNotEmpty(acqTxnSsn)){
				whereSql += " and trim(ACQ_TXN_SSN)='" + acqTxnSsn + "'";
			}
			
			if(StringUtil.isNotEmpty(outSsn)){
				whereSql += " and trim(OUT_SSN)='" + outSsn + "'";
			}
			
			String getSql = "select inst_date,inst_time,ACQ_TXN_SSN,pan," +
								"card_accp_id mcht_no,amt_trans return_amt,DATE_SETTLMT orig_date,amt_trans orig_amt," +
								"retrivl_ref,card_accp_term_id term_no,key_rsp,TXN_NUM,CARD_TYPE " +
							"from bth_Dtl_Err_Gc " + whereSql;
			
			Map<String,String> map = sqlDao.queryforStrMap(getSql);
			
//			String cardTp = map.get("CARD_TYPE") == null?"-":map.get("CARD_TYPE");
			
			String sql = "insert into tbl_r_txn(inst_date,inst_time,settle_date,msg_src_id," +
											   "term_ssn,sys_seq_num,key_rsp,pan," +
											   "mcht_no,return_amt,orig_date,orig_amt," +
											   "retrivl_ref,term_no,create_opr,update_date," +
											   "update_opr,match_sta,opr_sta,Settle_status," +
											   "record,reserved,audit_sta,ORIG_TXN_NUM,CARD_TYPE) " +
						 "values('"+nowDate+"'," +
						 		"'"+CommonFunction.getCurrentTime()+"'," +
						 		"'"+nowDate+"'," +
						 		"'1901'," +
						 		"'"+txnSsn+"'," +
						 		"'"+txnSsn+"'," +
						 		"'"+map.get("KEY_RSP")+"'," +
						 		"'"+map.get("PAN")+"'," +
						 		"'"+map.get("MCHT_NO")+"'," +
						 		"'"+map.get("RETURN_AMT")+"'," +
						 		"'"+map.get("ORIG_DATE")+"'," +
						 		"'"+map.get("ORIG_AMT")+"'," +
						 		"'"+map.get("RETRIVL_REF")+"'," +
						 		"'"+map.get("TERM_NO")+"'," +
						 		"'"+oprId+"'," +
						 		"'"+nowDate+"'," +
						 		"'"+oprId+"'," +
						 		"'00'," +
						 		"'0'," +
						 		"'3'," +
						 		"'"+params.get("record")+"'," +
						 		"'"+operator.getOprBrhId()+"'," +
						 		"'0'," +
						 		"'"+ map.get("TXN_NUM") +"'," +
						 		"'"+ map.get("CARD_TYPE") +"')";

			sqlDao.execute(sql);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private SqlDao sqlDao;

	public SqlDao getSqlDao() {
		return sqlDao;
	}

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}
	public static void main(String[] args) {
		String uniqueStr = (""+System.currentTimeMillis()+UUID.randomUUID().getMostSignificantBits()).replaceAll("-", "");
		System.out.println(uniqueStr);
	}
	
}
