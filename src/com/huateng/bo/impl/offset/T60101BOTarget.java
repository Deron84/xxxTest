package com.huateng.bo.impl.offset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.bo.offset.T60101BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.dao.common.HqlDao;
import com.huateng.dao.common.SqlDao;
import com.huateng.system.util.CommonFunction;

/**
 * project JSBConsole date 2013-4-7
 * 
 * @author 樊东东
 */
public class T60101BOTarget implements T60101BO {

	private SqlDao sqlDao;

	public SqlDao getSqlDao() {
		return sqlDao;
	}

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

	/**不能作废，这个方法不用了
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String delete(Map<String, String> params) throws Exception {
		return Constants.SUCCESS_CODE;//暂不做
	}

	/**
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String add(Map<String, String> params,HttpServletRequest request) throws Exception {

		String nowDate = CommonFunction.getCurrentDate();// 当前日期
		String nowTime = CommonFunction.getCurrentTime();// 当前时间
		String oprId = params.get("oprId");
		String txnDate = params.get("txnDate");  //原交易日期
		txnDate = StringUtils.replace(txnDate, "-", "");    
		String pan = params.get("pan");  //卡号
		String txnAmt = params.get("txnAmt");//原交易金额
		String retAmt = params.get("retAmt");//退货金额
		String misc1 = params.get("misc1");//系统参考号
		String record = params.get("record");// 补登原因
		String mchtNo=params.get("mchtNo").trim();//商户号

		try {

			// 查找原交易 清算明细表tbl_algo_dtl
			/**
			 * 查找原交易 select * from tbl_algo_dtl for update--清算明细表
			 * 日期对应清算明细表的交易日期，系统参考号对应交易流水号,原交易金额指的 MCHT_AT_C
			 * 
			 * NUMBER (22)
			 * 
			 * 商户借记金额
			 * 
			 * 减去 MCHT_AT_D
			 * 
			 * NUMBER (22)
			 * 
			 * 商户贷记金额
			 * 
			 * 
			 * 查找原交易可以找到 手续费 和手续费的计费规则 若是全额退款，退还手续费就是原交易的手续费
			 * 若是固定值百分比带封顶（百分比封顶、百分比封顶保底、固定值）不是全额退款的话不退手续费 也就是插入0 其他情况 退货金额乘以百分比
			 * 
			 *1固定值、 填值、 2按比例、 填值、 3按比例保底、 填值、按比最低收费 4按比例封顶、 填值、按比最高收费 5按比例保底封顶
			 * 填值、按比最低、按比最高
			 */
			
			//查询原交易条数
			String findSql1 = "select count(1) from tbl_algo_dtl where C_D_FLG !='1' and TRANS_DATE='"
					+ txnDate
					+ "'"
					+ " and trim(pan)='"
					+ pan
					+ "' and abs(MCHT_AT_C-MCHT_AT_D)='"
					+ txnAmt
					+ "' and substr(misc_1,21,12)='" + misc1 + "' and MCHT_CD='"
					+mchtNo+"'";
			
			//查询此条交易已处理条数
			String findSql2 = "select count(1) from tbl_r_txn where ORIG_DATE='"
				+ txnDate
				+ "' and trim(pan)='"
				+ pan
				+ "' and to_number(ORIG_AMT)='"
				+ CommonFunction.transYuanToFen(txnAmt)
				+ "' and to_number(RETURN_AMT)='"
				+ CommonFunction.transYuanToFen(retAmt)
				+ "' and RETRIVL_REF='" + misc1 + "' and mcht_no='"
				+mchtNo+"' and Settle_status != '2'";
		
			String findSqlTxnNum = "select count(1) from tbl_algo_dtl where C_D_FLG !='1' and TRANS_DATE='"
				+ txnDate
				+ "'"
				+ " and trim(pan)='"
				+ pan
				+ "' and abs(MCHT_AT_C-MCHT_AT_D)='"
				+ txnAmt
				+ "' and substr(misc_1,21,12)='" + misc1 + "' and MCHT_CD='"
					+mchtNo+"' and txn_num in ('1641','1101','1111','1741')";
			
			String matchSta = "00";// 没有重复的退货记录
			String oprSta = "0";// 正常
			
			int count1 = sqlDao.queryForInt(findSql1);
			int count2 = sqlDao.queryForInt(findSql2);
			int count3=sqlDao.queryForInt(findSqlTxnNum);
			
			//分期消费、T+1消费、无磁无密消费、脱机消费
//			if(count3>0){
//				return "该交易不支持退货";
//			}
			
			String insert1 = "insert into tbl_r_txn(INST_DATE,INST_TIME,SETTLE_DATE,MCHT_NO," +
		    		"RETURN_AMT,PAN,RETRIVL_REF,RECORD," +
		    		"OPR_STA,SETTLE_STATUS,SYS_SEQ_NUM," +
		    		"RESERVED,LICENCE_NO,MSG_SRC_ID,MATCH_STA)" + 
				" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    List<String> errList = new ArrayList<String>();
		    errList.add(CommonFunction.getCurrentDate());
		    errList.add(CommonFunction.getCurrentTime());
		    errList.add(CommonFunction.getCurrentDate());
		    errList.add(mchtNo);//插入商户号
		    errList.add(CommonFunction.transYuanToChar12(retAmt));
		    errList.add(pan);
		    errList.add(misc1);
		    errList.add(record);
		    errList.add("1");//异常
		    errList.add("2");//不清算
		    errList.add(" ");
		    errList.add(" ");
		    errList.add(" ");
		    errList.add("1901");
		    
			if (count1 == 0&&count2==0) {
			    matchSta="02";
			    errList.add(matchSta);
			    sqlDao.execute(insert1, errList);
				return "找不到原交易,无处理记录,不能提交请求";
			}
			if (count1 == 0&&count2>0) {
				matchSta="12";
				errList.add(matchSta);
			    sqlDao.execute(insert1, errList);
				return "找不到原交易,有处理记录,不能提交请求";
			}
			
			if (count1 > 1&&count2==0) {
				matchSta="01";
				errList.add(matchSta);
			    sqlDao.execute(insert1, errList);
				return "匹配到多笔原交易,无处理记录,不能提交请求";
			}
			if (count1 > 1&&count2>0) { 
				matchSta="11";
				errList.add(matchSta);
			    sqlDao.execute(insert1, errList);
				return "匹配到多笔原交易,有处理记录,不能提交请求";
			}

			String queryFeeSql = "select TXN_KEY,TRANS_DATE_TIME,MCHT_FEE_ALGO_ID,TXN_NUM,TXN_SSN,TERM_SSN," +
					"PRODUCT_CODE,DIV_NO,OBJ_INS_ID_CD,ACQ_INS_ID_CD,CARD_TP,AMT_RETURN+0 amtReturn, MCHT_CD,TERM_ID , " +
					"abs(MCHT_FEE_C-MCHT_FEE_D) as fee, MCHT_FEE_MD as feeTag,MCHT_FEE_PARAM as feeValue," +
					"MCHT_FEE_PCT_MIN as feeMin,MCHT_FEE_PCT_MAX as feeMax,SETTL_DATE,MCHT_TYPE," +
					"CHNL_CD,substr(MISC_1,45,6) as outNum " +
					"from tbl_algo_dtl where C_D_FLG !='1' and TRANS_DATE='"
					+ txnDate
					+ "'"
					+ " and trim(pan)='"
					+ pan
					+ "' and abs(MCHT_AT_C-MCHT_AT_D)='"
					+ txnAmt
					+ "' and substr(misc_1,21,12)='" + misc1 + "' and mcht_cd='"
					+mchtNo+"'";
			
			Map<String, String> feeMap = sqlDao.queryforStrMap(queryFeeSql);
			
			String txnKey = feeMap.get("TXN_KEY");// 关键字
			String feeId = feeMap.get("MCHT_FEE_ALGO_ID");// 费率id
			String txnNum = feeMap.get("TXN_NUM");// 交易码
			String txnSsn=feeMap.get("TXN_SSN");
			String termSsn = feeMap.get("TERM_SSN");// 终端跟踪号
			String divNo = feeMap.get("DIV_NO");// 原交易分期期数
			String productCode = feeMap.get("PRODUCT_CODE");// 原交易产品代码
			String cardTp = feeMap.get("CARD_TP");// 卡类型
			String termId = feeMap.get("TERM_ID");// 终端号
			String amtReturn = feeMap.get("AMTRETURN");// 退货金额 (需要更新)
			String mchtCd = feeMap.get("MCHT_CD");// 商户号
			String fee = feeMap.get("FEE");// 手续费
			String feeTag = feeMap.get("FEETAG");// 手续费计算方式
			String feeValue = feeMap.get("FEEVALUE");// 值
			String feeMin = feeMap.get("FEEMIN");// 按比最大
			String feeMax = feeMap.get("FEEMAX");// 按比最小
			String settleDate=feeMap.get("SETTL_DATE");//原交易日期
			String mchtType=feeMap.get("MCHT_TYPE");//商户类型
			String chnlCd=feeMap.get("CHNL_CD");//交易渠道
			String outNum=feeMap.get("OUTNUM");//外系统号
			
			
			// 判断已退货金额是否大于原交易金额
			if (StringUtils.isEmpty(amtReturn)) {//已退货金额
				amtReturn = "0";
			}
			
			Integer returnAll = 0;
		    
			returnAll = Integer.parseInt(amtReturn)+ Integer.parseInt(CommonFunction.transYuanToFen(retAmt));//元转分
			
			if (count2==0) {
				if(returnAll>Integer.parseInt(CommonFunction.transYuanToFen(txnAmt))){
					matchSta="20";
					errList.add(matchSta);
				    sqlDao.execute(insert1, errList);
					return "退货金额大于原交易金额,不能提交请求";
				}
			}
			
			if (count2>0) {
				if(returnAll>Integer.parseInt(CommonFunction.transYuanToFen(txnAmt))){
				matchSta="30";
				errList.add(matchSta);
			    sqlDao.execute(insert1, errList);
				return "累计退货金额大于原交易金额,不能提交请求";
				}
			}
			
			/**转换成分 十二位  前面补零*/
			String returnAllStr = CommonFunction.fillString(returnAll+"", '0', 12, false);
			
			//退货金额是以分为单位且是12位，前面补零
			String updateSql = "update tbl_algo_dtl set AMT_RETURN ='"
					+ returnAllStr + "' where C_D_FLG !='1' and TRANS_DATE='" + txnDate + "'"
					+ " and pan='" + pan + "' and abs(MCHT_AT_C-MCHT_AT_D)='"
					+ txnAmt + "' and substr(misc_1,21,12)='" + misc1 + "' and MCHT_CD='"+mchtNo+"'";
			
            String sql="select acq_inst_id,agr_br from tbl_mcht_base_inf where mcht_no='"+mchtNo+"'";
            
            Map<String, String> brhMap = sqlDao.queryforStrMap(sql);
            String acqInstId=(String)brhMap.get("ACQ_INST_ID").trim();
            String agrBr=(String)brhMap.get("AGR_BR").trim();
            
			// 插入退货表 tbl_r_txn --手工退货流水表
			if (count2 > 0) {// 有重复的退货记录 操作状态改为异常
				matchSta = "10";// 有重复的退货记录
				oprSta = "1";// 操作状态异常
			} else {// 没有重复的退货记录 且匹配单笔 修改原交易的退货金额  执行修改sql
				sqlDao.update(updateSql);
			}
			
			// /**key_rsp的规则是trans_date_time(月日时分秒) acq_txn_ssn acq_inst_id_code
			// fwd_inst_id_code acq_term_id 相加*/
			// String txnKey = StringUtils.substring(txnDate+txnTime,
			// 2)+txnSsn+acqInsId+objInsId+termId;//原交易主键
			String insertSql2 = "insert into tbl_r_txn(INST_DATE,INST_TIME,SETTLE_DATE,MSG_SRC_ID," +
									"TERM_SSN,SYS_SEQ_NUM,KEY_RSP,PAN," +
									"CARD_TYPE,MCHT_NO,RETURN_AMT,ORIG_TXN_NUM," +
									"ORIG_AMT,ORIG_DIV_NO,ORIG_PRODUCT_CODE,ORIG_DATE," +
									"ORIG_FEE_ALGO_ID,ORIG_FEE_MD,ORIG_FEE_PARAM,ORIG_FEE_PCT_MIN," +
									"ORIG_FEE_PCT_MAX,ORIG_FEE,TERM_NO," +
									"CREATE_OPR,UPDATE_OPR,UPDATE_DATE,MATCH_STA," +
									"OPR_STA,SETTLE_STATUS,RECORD,AUDIT_STA," +
									"RETRIVL_REF,RESERVED,LICENCE_NO)"
								+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			List<String> pl = new ArrayList<String>();// 参数列表
			pl.add(nowDate);// INST_DATE交易发生日期
			pl.add(nowTime);// INST_TIME交易发生时间
			pl.add(nowDate);// SETTLE_DATE清算日期
			pl.add("1901");// MSG_SRC_ID消息来源28##-终端 1901-管理平台
			pl.add(termSsn);// TERM_SSN终端跟踪号
			pl.add(txnSsn);// SYS_SEQ_NUM系统跟踪号
			pl.add(txnKey.trim()+nowDate+nowTime);// KEY_RSP原交易主键加当前日期时间
			pl.add(pan);// PAN卡号
			pl.add(cardTp);// CARD_TYPE卡类型
			pl.add(mchtCd);// MCHT_NO商户号
			pl.add(CommonFunction.transYuanToChar12(retAmt));// RETURN_AMT退货金额
			pl.add(txnNum);// ORIG_TXN_NUM原交易码
			pl.add(CommonFunction.transYuanToChar12(txnAmt));// ORIG_AMT原交易金额
			pl.add(StringUtil.isEmpty(divNo)? "-":divNo);// ORIG_DIV_NO原交易分期期数
			pl.add(StringUtil.isEmpty(productCode)? "-":productCode);// ORIG_PRODUCT_CODE原交易产品代码
			pl.add(txnDate);// ORIG_DATE原交易日期
			pl.add(feeId);// ORIG_FEE_ALGO_ID原交易费率代码
			pl.add(feeTag);// ORIG_FEE_MD原交易手续费类型
			pl.add(feeValue);// ORIG_FEE_PARAM原交易 费率 的 值
			pl.add(feeMin);// ORIG_FEE_PCT_MIN手续费最小值
			pl.add(feeMax);// ORIG_FEE_PCT_MAX手续费最大值
			pl.add(CommonFunction.transYuanToChar12(fee));// ORIG_FEE原交易手续费
			pl.add(termId);// TERM_NO终端号
			pl.add(oprId);// CREATE_OPR
			pl.add(oprId);// UPDATE_OPR
			pl.add(nowDate + nowTime);// UPDATE_DATE
			/**
			 * 第一位：手工退货有没有重复 0- 没有重复 1- 重复的 2- 没有重复但金额超限 3- 重复且金额超限 第二位：原交易有没有重复
			 * 0- 匹配单笔 1- 匹配多笔 2- 未匹配上
			 */
			pl.add(matchSta);// MATCH_STA
			/**
			 * 操作状态0- 正常 1- 异常 2- 无效作废 3- 强制发起
			 */
			pl.add(oprSta);// opr_sta
			/**
			 * SETTLE_STATUS 清算处理状态0. 已清算 1. 待清算
			 */
			pl.add("1");// SETTLE_STATUS
			pl.add(record);// RECORD备注说明
			/**
			 * audit_sta char(1) 退货强制发起或作废审核状态 0 : 未审核 1 : 强制发起待审核 2 : 作废待审核 3：审核通过
			 * */
			pl.add("0");
			pl.add(misc1);
			pl.add(agrBr+"000000"+settleDate+mchtType+chnlCd+outNum);
			pl.add(acqInstId);
			sqlDao.execute(insertSql2, pl);
			return Constants.SUCCESS_CODE;

		} catch (Exception e) {
			throw e;
		}
	}

	public static void main(String[] args) {
		String str = CommonFunction.fillString((int)(1.23*100)+"", '0', 12, false);
		System.out.println(str);
	}

}
