package com.huateng.struts.settle.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;


public class T80915Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 8);
		
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
		
		reportCreator.initReportData(getJasperInputSteam("T80915.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80915RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80915RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80915RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SETTLE_DATE," +
						 "a.MCHT_NO," +
						 "c.SETTLE_ACCT_NM," +
						 "PAN," +
						 "INST_DATE," +
						 "sum(nvl(ORIG_AMT/100,0))," +
						 "sum(nvl(RETURN_AMT/100,0))," +
						 "sum(nvl(RETURN_FEE/100,0)) " +
				  "FROM TBL_R_TXN a " +
				 	   "left outer join TBL_MCHT_BASE_INF b on(a.MCHT_NO=b.MCHT_NO) " +
				 	   "left outer join TBL_MCHT_SETTLE_INF c on(a.MCHT_NO=c.MCHT_NO)");
		sb.append(" WHERE b.agr_br in " + operator.getBrhBelowId());
		
		if (!StringUtil.isNull(mchntId)) {
			sb.append(" AND a.MCHT_NO like '% "+mchntId+" %'");
		}
		// 开始日期
		if (!StringUtil.isNull(startDate)) {
			sb.append(" AND SETTLE_DATE >= '" + startDate + "' ");
		}
		// 截止日期
		if (!StringUtil.isNull(endDate)) {
			sb.append(" AND SETTLE_DATE <= '" + endDate + "' ");
		}
		
		sb.append(" group by SETTLE_DATE,a.MCHT_NO,c.SETTLE_ACCT_NM,PAN,INST_DATE " +
				  "ORDER BY SETTLE_DATE,a.MCHT_NO,pan,INST_DATE");
		
		return sb.toString();
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