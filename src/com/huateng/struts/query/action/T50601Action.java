package com.huateng.struts.query.action;

import java.io.FileOutputStream;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;


public class T50601Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;
	

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 18);
		
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			data[i][9] = CommonFunction.transFenToYuan(data[i][9].toString());
			data[i][10] = CommonFunction.transFenToYuan(data[i][10].toString());
			data[i][11] = CommonFunction.transFenToYuan(data[i][11].toString());
			data[i][12] = CommonFunction.transFenToYuan(data[i][12].toString());
			data[i][13] = CommonFunction.transFenToYuan(data[i][13].toString());
		}

		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		String dateStart = "/";
		String dateEnd = "/";
		
		if(!StringUtil.isNull(startDate)){
			dateStart = startDate.substring(0, 4) +
				   "/" + 
				   startDate.substring(4, 6) +
				   "/" + 
				   startDate.substring(6, 8);
		}
		
		
		if(!StringUtil.isNull(endDate)){
			dateEnd = endDate.substring(0, 4) +
			   "/" + 
			   endDate.substring(4, 6) +
			   "/" + 
			   endDate.substring(6, 8);
		}

		
		
		parameters.put("P_1", dateStart+" - "+dateEnd);
		parameters.put("P_2", mchtCd);
		
		reportCreator.initReportData(getJasperInputSteam("T50601.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50601RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50601RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50601RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		
		String whereSql = " ";
		String whereSql1 = " ";
		String whereSql2 = " ";
		
		if(!"0000".equals(operator.getOprBrhId())){
			whereSql += " and b.AGR_BR in " + operator.getBrhBelowId();
		}
		if (isNotEmpty(mchtCd)) {
			whereSql += " AND trim(mcht_cd)='"+mchtCd+"'";
		}
		
		if (isNotEmpty(termId)) {
			whereSql += " AND trim(term_id)='" + termId + "'";
		}
		if (isNotEmpty(mchtGrp)) {
			whereSql += " AND trim(MCHT_GRP)='" + mchtGrp + "'";
		}
		if (isNotEmpty(mchtFlag1)) {
			whereSql += " AND trim(MCHT_FLAG1)='" + mchtFlag1 + "'";
		}
		
		
		if (isNotEmpty(startDate)) {
			whereSql1 += " and trim(DATE_SETTLMT)>='"+startDate+"'";
			whereSql2 += " and SUBSTR(REC_UPD_TS,1,8)>='"+startDate+"'";
		}
		if (isNotEmpty(endDate)) {
			whereSql1 += " and trim(DATE_SETTLMT)<='"+endDate+"'";
			whereSql2 += " and SUBSTR(REC_UPD_TS,1,8)<='"+endDate+"'";
		}
		String sql = "select trans_date,trans_date_time," +
						"date_settlmt,txn_ssn,pan,CARD_TP," +
						"(MCHT_AT_C - MCHT_AT_D)*100 mchtAmt," +
						"(MCHT_FEE_C-MCHT_FEE_D)*100 mchtFee," +
						"(FEE_D_OUT-FEE_C_OUT)*100 cupAmt,SVC_FEE*100 svcFee," +
						"((MCHT_AT_C - MCHT_AT_D)-(MCHT_FEE_D-MCHT_FEE_C))*100 pureAmt," +
						"mcht_cd,term_id," +
//						"(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm," +
						"(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM ) txnNm," +
						"acq_ins_id_cd,OLD_TXN_FLG,substr(misc_1,21,12) refFlag " +
					"from TBL_ALGO_DTL a " +
					"where txn_num not in('5171', '9103') and C_D_FLG <> '1'  and SUBSTR(TXN_NUM,1,1) <> '3' " + 
					whereSql1 +
				" union all " +
					"select trans_date,trans_date_time," +
						"trim(REC_UPD_TS) date_settlmt,txn_ssn,pan,CARD_TP," +
						"(MCHT_AT_C - MCHT_AT_D)*100 mchtAmt," +
						"(MCHT_FEE_C-MCHT_FEE_D)*100 mchtFee," +
						"(FEE_D_OUT-FEE_C_OUT)*100 cupAmt,SVC_FEE*100 svcFee," +
						"((MCHT_AT_C - MCHT_AT_D)-(MCHT_FEE_D-MCHT_FEE_C))*100 pureAmt," +
						"mcht_cd,term_id," +
//						"(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm," +
						"(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM ) txnNm," +
						"acq_ins_id_cd,OLD_TXN_FLG,substr(misc_1,21,12) refFlag " +
					"from TBL_ALGO_DTL a " +
					"where TXN_NUM in ('5171', '9103') and OLD_TXN_FLG = '1' " +
					whereSql2;
		
		sql = "select date_settlmt,trans_date,trans_date_time,mcht_cd,term_id,txn_ssn,acq_ins_id_cd,pan,txnNm," +
				"mchtAmt,mchtFee,cupAmt,svcFee,pureAmt,refFlag," +
				"(select DESCR from TBL_INF_MCHNT_TP_GRP g where b.MCHT_GRP=g.MCHNT_TP_GRP)," +
				"(case mcht_flag1 when '0' then '收款商户' " +
			 					"when '1' then '老板通商户' " +
			 					"when '2' then '分期商户' " +
			 					"when '3' then '财务转账商户' " +
			 					"when '4' then '项目形式商户' " +
			 					"when '5' then '积分业务' " +
			 					"when '6' then '其他业务' end), " +
			 					"(case a.CARD_TP when '01' then '本行借记卡' " +
								" when '02' then '本行信用卡' " +
								" when '03' then '他行借记卡' " +
								" when '04' then '他行信用卡' end) CARD_TYPE " +
			" from (" + sql + ") a left outer join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) " +
			"where 1=1 " + 
			whereSql +
			" order by DATE_SETTLMT desc,TRANS_DATE desc,TRANS_DATE_TIME desc,acq_ins_id_cd,MCHT_CD,term_id";
		
		return sql;
	}
	

	private String startDate;
	private String endDate;
	private String mchtCd;
	private String termId;
	private String mchtGrp;
	private String mchtFlag1;

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getMchtGrp() {
		return mchtGrp;
	}

	public void setMchtGrp(String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	public String getMchtFlag1() {
		return mchtFlag1;
	}

	public void setMchtFlag1(String mchtFlag1) {
		this.mchtFlag1 = mchtFlag1;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}
}