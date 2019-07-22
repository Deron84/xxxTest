package com.huateng.struts.settle.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;


public class T80912Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 10);
		
		data = reportCreator.process(genSql(), title);
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		if (StringUtil.isNull(startDate)) {
			startDate = "";
		}
		if (StringUtil.isNull(endDate)) {
			endDate =  "";
		}
		
//		parameters.put("P_1", brhId + " - " +InformationUtil.getBrhName(brhId));
		parameters.put("P_1", startDate + " - " + endDate);
		
		reportCreator.initReportData(getJasperInputSteam("T80912.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80912RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80912RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80912RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {

		StringBuffer whereSql = new StringBuffer(" WHERE agr_br in " + operator.getBrhBelowId());
		if (!StringUtil.isNull(startDate)) {
			whereSql.append(" AND dateSettle>= '"+startDate+"'");
		}
		if (!StringUtil.isNull(endDate)) {
			whereSql.append(" AND dateSettle<= '"+endDate+"'");
		}
		if (!StringUtil.isNull(mchntId)) {
			whereSql.append(" AND mcht like '%");
			whereSql.append(mchntId);
			whereSql.append("%'");
		}
		String sql = "select dateSettle," +
						    "mcht," +
						    "(select brh_name from tbl_brh_info d where agr_br=d.brh_id) brhNm," +
						    "SETTLE_ACCT," +
						    "SETTLE_ACCT_NM," +
						    "sum(amt)," +
						    "sum(fee)," +
						    "sum(errAmt)," +
						    "sum(errFee)," +
						    "sum(back) " +
					 "from (select DATE_SETTLMT dateSettle," +
					 			  "a.MCHT_NO mcht," +
					 			  "b.agr_br," +
					 			  "c.SETTLE_ACCT," +
					 			  "c.SETTLE_ACCT_NM," +
					 			  "TRANS_AMT/100 amt," +
					 			  "TRANS_FEE/100 fee," +
					 			  "0 errAmt," +
					 			  "0 errFee," +
					 			  "0 back " +
					 	   "from BTH_HOST_TXN_SUSPS a " +
					 	   		"left outer join TBL_MCHT_BASE_INF b on(a.MCHT_NO=b.MCHT_NO) " +
					 	   		"left outer join TBL_MCHT_SETTLE_INF c on(a.MCHT_NO=c.MCHT_NO) " +
					 	   		"where STLM_FLAG ='7' " +
					 	   "union all " +
					 	   "select DATE_SETTLMT_8," +
					 	   		  "CARD_ACCP_ID," +
					 	   		  "b.agr_br," +
					 	   		  "c.SETTLE_ACCT," +
					 	   		  "c.SETTLE_ACCT_NM," +
					 	   		  "AMT_TRANS/100," +
					 	   		  "MCHT_FEE/100," +
					 	   		  "0 errAmt," +
					 	   		  "0 errFee," +
					 	   		  "0 back " +
					 	   "from BTH_GC_TXN_SUCC_SUSPS a " +
					 	   		"left outer join TBL_MCHT_BASE_INF b on(a.CARD_ACCP_ID=b.MCHT_NO) " +
					 	   		"left outer join TBL_MCHT_SETTLE_INF c on(a.CARD_ACCP_ID=c.MCHT_NO) " +
					 	   		"where STLM_FLAG='7') "
					+ whereSql.toString() 
					+ " group by dateSettle,mcht,agr_br,SETTLE_ACCT,SETTLE_ACCT_NM " 
					+ "ORDER BY dateSettle,mcht";
		
		return sql;
	}
	
	private String startDate;
	private String endDate;
	private String mchntId;


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

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

}