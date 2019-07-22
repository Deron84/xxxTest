package com.huateng.struts.query.action;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.common.select.SelectMethod;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T50403Action  extends ReportBaseAction  {
	
	@Override
	protected String genSql() {

		
		String sql = "select sa_term_no,txn_name,sa_txn_date,sa_txn_time,sa_txn_card," +
				"(case when sa_txn_amt is null then 0 when sa_txn_amt = ' ' then 0 else sa_txn_amt*1/100 end)," +
				"(case  SA_CLC_FLAG when '0' then '正常' when '2' then '拒绝' else SA_CLC_FLAG end)," +
				"(case sa_clc_rsn1 when 'C2' then '3日内，同一卡号在同一受理行内交易限制' when 'C3' then '3日内，同一卡号交易限制' " +
				"when 'M3' then '同一商户当日同一卡号交易限制' when 'M5' then '同一商户当日有超过一笔同金额的限制' else SA_CLC_FLAG end) " +
				"from tbl_clc_mon m,tbl_txn_name " +
				"where sa_txn_num = txn_num and trim(SA_CLC_RSN1) in (select SA_MODEL_KIND from TBL_RISK_INF) ";
		
		
		if(!StringUtil.isNull(kind)) sql += " and sa_clc_rsn1 = '" + kind + "' ";
		if(!StringUtil.isNull(date1)) sql +=" and m.sa_txn_date >= '" + date1 + "' ";
		if(!StringUtil.isNull(date2)) sql += " and m.sa_txn_date <= '" + date2 + "' ";
		
		sql += " and trim(m.SA_MCHT_NO) in " +
				"(select distinct MCHT_NO from TBL_MCHT_BASE_INF " +
				"where TRIM(ACQ_INST_ID) in " + InformationUtil.getBrhGroupString(acqInstId) + ") ";
		
		sql += "order by sa_txn_date desc,SA_TXN_TIME desc";
		return sql;
	}

	@Override
	protected void reportAction() throws Exception {

		String dataString;
		if(!StringUtil.isNull(date1)) {
			dataString = date1 + " ~ ";
		}else {
			dataString = " ~ ";
		}
		if(!StringUtil.isNull(date1)) {
			dataString = dataString + date2;
		}
		
		String[] title = InformationUtil.createTitles("V_", 8);
	
		Object[][] data = reportCreator.process(genSql(), title);
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}

		parameters.put("betdate", dataString);
		parameters.put("brh", operator.getOprBrhId());
		parameters.put("opr", operator.getOprId());

		reportModel.setData(data);
		reportModel.setTitle(title);
		
		reportCreator.initReportData(getJasperInputSteam("T50403.jasper"), parameters, reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50403RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50403RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50403RN"));
		
		writeUsefullMsg(fileName);
		
		return;
	}
	
	private String kind;
	private String date1;
	private String date2;
	private String acqInstId;

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getAcqInstId() {
		return acqInstId;
	}

	public void setAcqInstId(String acqInstId) {
		this.acqInstId = acqInstId;
	}
	
	
}
