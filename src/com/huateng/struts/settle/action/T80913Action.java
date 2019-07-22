package com.huateng.struts.settle.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;


public class T80913Action extends ReportBaseAction{

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
		
		reportCreator.initReportData(getJasperInputSteam("T80913.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80913RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80913RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80913RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DATE_SETTLMT_8,CARD_ACCP_ID," +
						"(select brh_name from tbl_brh_info d where b.agr_br=d.brh_id) brhNm," +
						"c.SETTLE_ACCT,c.SETTLE_ACCT_NM," +
				 		"sum(AMT_TRANS/100) transAmt," +
				 		"sum(MCHT_FEE/100) fee," +
				 		"0 errAmt," +
				 		"0 errFee," +
				 		"0 back " +
				  "FROM BTH_GC_TXN_SUCC a " +
				 	   "left outer join TBL_MCHT_BASE_INF b on(a.CARD_ACCP_ID=b.MCHT_NO) " +
				 	   "left outer join TBL_MCHT_SETTLE_INF c on(a.CARD_ACCP_ID=c.MCHT_NO)");
		sb.append(" WHERE a.STLM_FLAG not in('0','7','8','9','A','D') and b.agr_br in " + operator.getBrhBelowId());
		
		if (!StringUtil.isNull(mchntId)) {
			sb.append(" AND CARD_ACCP_ID like '% "+mchntId+" %'");
		}
		// 开始日期
		if (!StringUtil.isNull(startDate)) {
			sb.append(" AND DATE_SETTLMT_8 >= '" + startDate + "' ");
		}
		// 截止日期
		if (!StringUtil.isNull(endDate)) {
			sb.append(" AND DATE_SETTLMT_8 <= '" + endDate + "' ");
		}
		
		sb.append(" group by DATE_SETTLMT_8,CARD_ACCP_ID,b.agr_br,c.SETTLE_ACCT,c.SETTLE_ACCT_NM " +
				  "ORDER BY DATE_SETTLMT_8,CARD_ACCP_ID");
		
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