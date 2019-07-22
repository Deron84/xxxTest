package com.huateng.bo.impl.accident;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.xwork.StringUtils;
import com.huateng.bo.accident.T90102BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.CommQueryDAO;
import com.huateng.dao.common.SqlDao;
import com.huateng.system.util.CommonFunction;

/**
 * project JSBConsole date 2013-4-12
 * 
 * @author 樊东东
 */
public class T90102BOTarget implements T90102BO {

	private SqlDao sqlDao;

	public SqlDao getSqlDao() {
		return sqlDao;
	}

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

	public String accept(Map<String, String> params) throws Exception {
		
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
		String txnAmt = params.get("txnAmt");// 原交易金额（元）
		String retAmt = params.get("retAmt");// 退货金额 本次手工退货的退货金额（元）
		String txnSsn = params.get("txnSsn");
		String record = params.get("record");// 退货原因
		String retFee = params.get("retFee");// 退货手续费（元）
		String matchSta = params.get("matchSta");// 匹配状态
		String auditSta = params.get("auditSta");// 审核状态
		String method = params.get("method");
		String mchtNo=params.get("mchtNo");
		String retrivlRef=params.get("retrivlRef");
		
		String retAmt12 = CommonFunction.fillString(Integer.parseInt(CommonFunction.transYuanToFen(retAmt.isEmpty()?"0":retAmt))+"", '0', 12, false);
		String txnAmt12 = CommonFunction.fillString(Integer.parseInt(CommonFunction.transYuanToFen(txnAmt.isEmpty()?"0":txnAmt))+"", '0', 12, false);
		String fee12 =CommonFunction.fillString(Integer.parseInt(CommonFunction.transYuanToFen(retFee.isEmpty()?"0":retFee))+"", '0', 12, false);
		
		if ("2".equals(auditSta)) {// 作废
			
			// 修改退货表 tbl_r_txn --手工退货流水表
			String updateSql2 = "update tbl_r_txn set opr_sta='2' ," +
													 "Settle_status='2'," +
													 "audit_sta='3'," +
													 "update_date='"+ (nowDate+nowTime) + "'," +
													 "update_opr='" + oprId + "' " +
								"where trim(ORIG_DATE)='"+ txnDate +"' " +
								  "and trim(INST_DATE)='"+ instDate +"' " +
								  "and trim(INST_TIME)='"+ instTime +"' " +
								  "and trim(PAN)='"+ pan +"' " +
								  "and trim(CREATE_OPR)='"+ txnOpr +"' " +
								  "and opr_sta='1' " +
								  "and Settle_status='1' " +
								  "and audit_sta='2' " +
								  "and mcht_no='"+ mchtNo +"' " ;
			if(!txnSsn.isEmpty()){// SYS_SEQ_NUM系统参考号
				updateSql2 += " and trim(SYS_SEQ_NUM)='"+ txnSsn +"' ";
			}
			if(!key.isEmpty()){// KEY_RSP关键值
				updateSql2 += " and trim(KEY_RSP)='"+ key +"' ";
			}
			if(!retAmt.isEmpty()){// RETURN_AMT退货金额
				updateSql2 += " and trim(RETURN_AMT)='"+ retAmt12 +"' ";
			}
			if(!txnAmt.isEmpty()){// ORIG_AMT原交易金额
				updateSql2 += " and trim(ORIG_AMT)='"+ txnAmt12 +"' ";
			}
//			if(!retFee.isEmpty()){// RETURN_FEE退货手续费
//				updateSql2 += " and trim(RETURN_FEE)='"+ fee12 +"' ";
//			}	
			
			CommonFunction.getCommQueryDAO().excute(updateSql2);
		}else if ("1".equals(auditSta)) {// 强制发起
			
			if("10".equals(matchSta)){//匹配单笔
				
				 
				// 查找原交易 清算明细表tbl_algo_dtl
				/**
				 * 查找原交易 select * from tbl_algo_dtl for update--清算明细表
				 * 日期对应清算明细表的交易日期，系统参考号对应交易流水号,原交易金额指的 
				 * MCHT_AT_C
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
				String findSql1 = "select count(1) from tbl_algo_dtl " +
						"where C_D_FLG !='1' " +
						"and TRANS_DATE='"+ txnDate + "' " +
						"and trim(pan)='"+ pan + "' " +
						"and abs(MCHT_AT_C-MCHT_AT_D)='"+ txnAmt + "' " +
						"and substr(misc_1,21,12)='" + retrivlRef + "' " +
						"and mcht_cd='"+mchtNo+"'";
				int count1 = sqlDao.queryForInt(findSql1);
				if (count1 == 0) {
					return "找不到原交易,不能提交请求";
				}
				
				String queryFeeSql = "select TXN_KEY,TRANS_DATE_TIME,MCHT_FEE_ALGO_ID,TXN_NUM,TERM_SSN,PRODUCT_CODE," +
										"DIV_NO,OBJ_INS_ID_CD,ACQ_INS_ID_CD,CARD_TP,AMT_RETURN+0 returnAmt, MCHT_CD,TERM_ID," +
										"MCHT_FEE_D-MCHT_FEE_C as fee, MCHT_FEE_MD as feeTag,MCHT_FEE_PARAM as feeValue," +
										"MCHT_FEE_PCT_MIN as feeMin,MCHT_FEE_PCT_MAX as feeMax " +
								"from tbl_algo_dtl " +
								"where C_D_FLG !='1' and TRANS_DATE='" + txnDate + "' " +
									 "and trim(pan)='" + pan + "' " +
									 "and abs(MCHT_AT_C-MCHT_AT_D)='" + txnAmt + "' " +
									 "and substr(misc_1,21,12)='" + retrivlRef + "' " +
									 "and mcht_cd='"+mchtNo+"'";
				Map<String, String> feeMap = sqlDao.queryforStrMap(queryFeeSql);
				String txnTime = feeMap.get("TRANS_DATE_TIME");// 原交易时间
				String txnKey = feeMap.get("TXN_KEY");// 关键字
				String feeId = feeMap.get("MCHT_FEE_ALGO_ID");// 费率id
				String acqInsId = feeMap.get("ACQ_INS_ID_CD");// 收单机构银联码
				String objInsId = feeMap.get("OBJ_INS_ID_CD");// 发送机构银联码
				String txnNum = feeMap.get("TXN_NUM");// 交易码
				String termSsn = feeMap.get("TERM_SSN");// 终端跟踪号
				String divNo = feeMap.get("DIV_NO");// 原交易分期期数
				String productCode = feeMap.get("PRODUCT_CODE");// 原交易产品代码
				String cardTp = feeMap.get("CARD_TP");// 卡类型
				String termId = feeMap.get("TERM_ID");// 终端号
				String amtReturn = feeMap.get("returnAmt");// 退货金额 (需要更新)（分）
				String mchtCd = feeMap.get("MCHT_CD");// 商户号
				String fee = feeMap.get("FEE");// 手续费（元）
				String feeTag = feeMap.get("FEETAG");// 手续费计算方式
				String feeValue = feeMap.get("FEEVALUE");// 值
				String feeMin = feeMap.get("FEEMIN");// 按比最大
				String feeMax = feeMap.get("FEEMAX");// 按比最小
				
				
				if (StringUtils.isEmpty(amtReturn)) {
					amtReturn = "0";
				}
				
				//退货金额（amtreturn），已退货金额（retAmt）
				Integer returnAll = Integer.parseInt(amtReturn)
						+ Integer.parseInt(CommonFunction.transYuanToFen(retAmt));// 元转分
				
				//判断退货金额是否大于原交易金额
				if (returnAll > Integer.parseInt(CommonFunction
						.transYuanToFen(txnAmt))) {
					return "累计退货金额大于原交易金额,不能提交请求";
				}
				
				/** 转换成分 十二位 前面补零 */
				String returnAllStr = CommonFunction.fillString(returnAll + "",
						'0', 12, false);
				
				// 计算退货手续费
				// 若是全额退款，退还手续费就是原交易的手续费
//				if (retAmt.equals(txnAmt)) {
//					retFee = fee;
//				} else {// 若不是全额退款，若是固定值百分比带封顶（百分比封顶、百分比封顶保底、固定值）话不退手续费 也就是插入0 其他情况
//					// 退货金额乘以百分比(退货金额/交易金额)
//					if ("1".endsWith(feeTag) || "4".equals(feeTag)
//							|| "5".equals(feeTag)) {
//						retFee = "0";
//					} else {
//						BigDecimal result = BigDecimal.valueOf(
//								Double.parseDouble(fee)
//										* Double.parseDouble(retAmt)
//										/ Double.parseDouble(txnAmt)).setScale(2,
//								RoundingMode.HALF_UP);// 四舍五入，保留两位小数
//						retFee = result.toString();
//					}
//				}
				
				// 修改原交易的退货金额
				String updateSql1 = "update tbl_algo_dtl set AMT_RETURN ='" + returnAllStr + "'," +
														   " REC_UPD_TS ='" + nowDate + "' " +  
						" where C_D_FLG !='1' and TRANS_DATE='" + txnDate + "' " +
						  "and pan='" + pan + "' " +
						  "and abs(MCHT_AT_C-MCHT_AT_D)='" + txnAmt + "' " +
						  "and substr(misc_1,21,12)='" + retrivlRef + "' " +
						  "and mcht_cd='"+mchtNo+"'";
				
				// 将退货记录的审核状态改为审核通过，操作状态改为强制发起
				// 修改退货表 tbl_r_txn --手工退货流水表
				String updateSql2 = "update tbl_r_txn set opr_sta='3'," +
														 "SETTLE_STATUS='1'," +
														 "AUDIT_STA='3'," +
														 "UPDATE_DATE='"+ (nowDate + nowTime) + "'," +
														 "update_opr='" + oprId + "' " +
						 "where trim(ORIG_DATE)='"+ txnDate +"' " +// ORIG_DATE原交易日期
							  "and trim(INST_DATE)='"+ instDate +"' " +// INST_DATE交易日期
							  "and trim(INST_TIME)='"+ instTime +"' " +// INST_TIME交易时间
							  "and trim(PAN)='"+ pan +"' " +// PAN卡号
							  "and trim(CREATE_OPR)='"+ txnOpr +"' " +// CREATE_OPR记录柜员
							  "and opr_sta='1' " +
							  "and Settle_status='1' " +
							  "and audit_sta='1' " +
							  "and mcht_no='"+mchtNo+"' " ;
	
				if(!txnSsn.isEmpty()){// SYS_SEQ_NUM系统参考号
					updateSql2 += " and trim(SYS_SEQ_NUM)='"+ txnSsn +"' ";
				}
				if(!key.isEmpty()){// KEY_RSP关键值
					updateSql2 += " and trim(KEY_RSP)='"+ key +"' ";
				}
				if(!retAmt.isEmpty()){// RETURN_AMT退货金额
					updateSql2 += " and trim(RETURN_AMT)='"+ retAmt12 +"' ";
				}
				if(!txnAmt.isEmpty()){// ORIG_AMT原交易金额
					updateSql2 += " and trim(ORIG_AMT)='"+ txnAmt12 +"' ";
				}
//				if(!retFee.isEmpty()){// RETURN_FEE退货手续费
//					updateSql2 += " and trim(RETURN_FEE)='"+ fee12 +"' ";
//				}	
				
				CommonFunction.getCommQueryDAO().excute(updateSql2);
				CommonFunction.getCommQueryDAO().excute(updateSql1);
			}else if("11".equals(matchSta)){//匹配多笔
					
				  String querySql = "select TXN_KEY,DATE_SETTLMT,TRANS_DATE_TIME,MCHT_FEE_ALGO_ID,TXN_NUM,TERM_SSN," +
						  		"PRODUCT_CODE,DIV_NO,OBJ_INS_ID_CD,ACQ_INS_ID_CD,CARD_TP,AMT_RETURN+0, MCHT_CD,TERM_ID," +
						  		"MCHT_FEE_D-MCHT_FEE_C as fee, MCHT_FEE_MD as feeTag,MCHT_FEE_PARAM as feeValue," +
						  		"MCHT_FEE_PCT_MIN as feeMin,MCHT_FEE_PCT_MAX as feeMax " +
				  		"from tbl_algo_dtl " +
				  		"where C_D_FLG !='1' and TRANS_DATE='"
								+ txnDate
								+ "'"
								+ " and trim(pan)='"
								+ pan
								+ "' and abs(MCHT_AT_C-MCHT_AT_D)='"
								+ txnAmt
								+ "' and substr(misc_1,21,12)='" + retrivlRef + "' and mcht_cd='"+mchtNo+"' " +
						"order by TRANS_DATE ASC,TRANS_DATE_TIME ASC";
				
				  List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(querySql);
				  
				  for(int i=0;i<list.size();i++){
					
//					  Object[] o=list.get(i);
					  
					  String amt = list.get(i)[11].toString().trim().isEmpty()?"0":list.get(i)[11].toString().trim();
					  Integer returnAll = Integer.parseInt(amt)+ Integer.parseInt(CommonFunction.transYuanToFen(retAmt));
				
					  if(returnAll <= Integer.parseInt(CommonFunction.transYuanToFen(txnAmt))){
						  
						  String returnAllStr = CommonFunction.fillString(returnAll + "",
								  '0', 12, false);
					
						  String updateSql1 = "update tbl_algo_dtl set AMT_RETURN ='" + returnAllStr + "'," +
								  									 " REC_UPD_TS ='" + nowDate + "' " + 
										  		"where C_D_FLG !='1' " +
										  			"and DATE_SETTLMT='"+list.get(i)[1].toString().trim()+"' " +
										  			"and TXN_KEY='"+list.get(i)[0].toString().trim()+"'";
					
						  String updateSql2 = "update tbl_r_txn set opr_sta='3'," +
						  										   "Settle_status='1'," +
						  										   "audit_sta='3'," +
						  										   "update_date='"+ (nowDate + nowTime) + "'," +
						  										   "update_opr='"+ oprId + "' " +
						  		"where trim(ORIG_DATE)='"+ txnDate +"' " +// ORIG_DATE原交易日期
									  "and trim(INST_DATE)='"+ instDate +"' " +// INST_DATE交易日期
									  "and trim(INST_TIME)='"+ instTime +"' " +// INST_TIME交易时间
									  "and trim(PAN)='"+ pan +"' " +// PAN卡号
									  "and trim(CREATE_OPR)='"+ txnOpr +"' " +// CREATE_OPR记录柜员
									  "and opr_sta='1' " +
									  "and Settle_status='1' " +
									  "and audit_sta='1' " +
									  "and mcht_no='"+mchtNo+"' " ;
	
							if(!txnSsn.isEmpty()){// SYS_SEQ_NUM系统参考号
								updateSql2 += " and trim(SYS_SEQ_NUM)='"+ txnSsn +"' ";
							}
							if(!key.isEmpty()){// KEY_RSP关键值
								updateSql2 += " and trim(KEY_RSP)='"+ key +"' ";
							}
							if(!retAmt.isEmpty()){// RETURN_AMT退货金额
								updateSql2 += " and trim(RETURN_AMT)='"+ retAmt12 +"' ";
							}
							if(!txnAmt.isEmpty()){// ORIG_AMT原交易金额
								updateSql2 += " and trim(ORIG_AMT)='"+ txnAmt12 +"' ";
							}
//							if(!retFee.isEmpty()){// RETURN_FEE退货手续费
//								updateSql2 += " and trim(RETURN_FEE)='"+ fee12 +"' ";
//							}		
							
							CommonFunction.getCommQueryDAO().excute(updateSql2);
							CommonFunction.getCommQueryDAO().excute(updateSql1);
					  }else{
						  return "操作失败(退货金额大于交易金额)";
					  }
					  break;
				  }
		    }else {
			  return "无效的审核状态:" + auditSta;
		    }
		}
	    return Constants.SUCCESS_CODE;
	}


	public String refuse(Map<String, String> params) throws Exception {
		
		String nowDate = CommonFunction.getCurrentDate();// 当前日期
		String nowTime = CommonFunction.getCurrentTime();// 当前时间
		String instDate = params.get("instDate");// 交易日期
		String txnDate = params.get("txnDate");// 原交易日期
		String instTime = params.get("instTime");// 交易时间
		String key = params.get("key");
		String txnOpr = params.get("txnOpr");// 记录柜员
		String oprSta = params.get("oprSta");// 操作状态
		String settleSta = params.get("settleSta");// 清算状态
		String pan = params.get("pan");
		String txnAmt = params.get("txnAmt");// 原交易金额
		String retAmt = params.get("retAmt");// 退货金额 本次手工退货的退货金额
		String retrivlRef=params.get("retrivlRef");
		String record = params.get("record");// 退货原因
		String retFee = params.get("retFee");// 退货手续费
		String matchSta = params.get("matchSta");// 匹配状态
		String auditSta = params.get("auditSta");// 审核状态
		String method = params.get("method");
		String oprId = params.get("oprId");
		
		String retAmt12 = CommonFunction.fillString(Integer.parseInt(CommonFunction.transYuanToFen(retAmt.isEmpty()?"0":retAmt))+"", '0', 12, false);
		String txnAmt12 = CommonFunction.fillString(Integer.parseInt(CommonFunction.transYuanToFen(txnAmt.isEmpty()?"0":txnAmt))+"", '0', 12, false);
		String fee12 =CommonFunction.fillString(Integer.parseInt(CommonFunction.transYuanToFen(retFee.isEmpty()?"0":retFee))+"", '0', 12, false);
		
		String updateSql = "UPDATE TBL_R_TXN SET AUDIT_STA='0'," +
												"UPDATE_DATE='"+ (nowDate + nowTime) + "'," +
												"UPDATE_OPR='"+ oprId + "' " + 
				"where trim(ORIG_DATE)='"+ txnDate +"' " +
				  "and trim(INST_DATE)='"+ instDate +"' " +
				  "and trim(INST_TIME)='"+ instTime +"' " +
				  "and trim(RETRIVL_REF)='"+ retrivlRef +"' " +
				  "and trim(PAN)='"+ pan +"' " +
				  "and trim(CREATE_OPR)='"+ txnOpr +"'  " +
				  "and opr_sta='1' " +
				  "and Settle_status='1' " +
				  "and audit_sta!='0' ";
		
		if(!key.isEmpty()){// KEY_RSP关键值
			updateSql += " and trim(KEY_RSP)='"+ key +"' ";
		}
		if(!retAmt.isEmpty()){// RETURN_AMT退货金额
			updateSql += " and trim(RETURN_AMT)='"+ retAmt12 +"' ";
		}
		if(!txnAmt.isEmpty()){// ORIG_AMT原交易金额
			updateSql += " and trim(ORIG_AMT)='"+ txnAmt12 +"' ";
		}
//		if(!retFee.isEmpty()){// RETURN_FEE退货手续费
//			updateSql += " and trim(RETURN_FEE)='"+ fee12 +"' ";
//		}	
		
		CommonFunction.getCommQueryDAO().excute(updateSql);
		
		return Constants.SUCCESS_CODE;
	}
}
