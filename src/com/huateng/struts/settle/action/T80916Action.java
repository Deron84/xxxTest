package com.huateng.struts.settle.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;


public class T80916Action extends ReportBaseAction{

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
		
		reportCreator.initReportData(getJasperInputSteam("T80916.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80916RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80916RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80916RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DATE_SETTLMT,MCHT_CD," +
						"(select brh_name from tbl_brh_info d where b.agr_br=d.brh_id) brhNm," +
						"c.SETTLE_ACCT,c.SETTLE_ACCT_NM," +
				 		"sum(MCHT_AT_C-MCHT_AT_D) transAmt," +
				 		"sum(MCHT_FEE_D-MCHT_FEE_C) fee," +
				 		"sum(case TXN_NUM when '5171' then MCHT_AT_C-MCHT_AT_D else 0 end) errAmt," +
				 		"sum(case TXN_NUM when '5171' then MCHT_FEE_D-MCHT_FEE_C else 0 end) errFee," +
				 		"sum(MCHT_AT_C-MCHT_AT_D)-sum(MCHT_FEE_D-MCHT_FEE_C)-sum(case TXN_NUM when '5171' then MCHT_AT_C-MCHT_AT_D else 0 end)+sum(case TXN_NUM when '5171' then MCHT_FEE_D-MCHT_FEE_C else 0 end) back " +
				  "FROM TBL_ALGO_DTL a left outer join " +
				 	   "TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) left outer join " +
				 	   "TBL_MCHT_SETTLE_INF c on(a.MCHT_CD=c.MCHT_NO)");
		sb.append(" WHERE a.STLM_FLG='4' and b.agr_br in " + operator.getBrhBelowId());
		
		if (!StringUtil.isNull(mchntId)) {
			sb.append(" AND MCHT_CD like '% "+mchntId+" %'");
		}
		// 开始日期
		if (!StringUtil.isNull(startDate)) {
			sb.append(" AND date_settlmt >= '" + startDate + "' ");
		}
		// 截止日期
		if (!StringUtil.isNull(endDate)) {
			sb.append(" AND date_settlmt <= '" + endDate + "' ");
		}
		
		sb.append(" group by DATE_SETTLMT,MCHT_CD,b.agr_br,c.SETTLE_ACCT,c.SETTLE_ACCT_NM " +
				  "ORDER BY DATE_SETTLMT,MCHT_CD");
		
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