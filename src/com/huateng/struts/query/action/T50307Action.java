package com.huateng.struts.query.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T50307Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;
	
	private String date;
	private String acqInstId;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 18);
//		System.out.println(genSql());
		data = reportCreator.process(genSql(), title);
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		//统计日期
		parameters.put("betdate", date);
		//机构编号
		parameters.put("brh", InformationUtil.getBrhName(acqInstId));
		//柜员
		parameters.put("opr", operator.getOprId());
				
		reportCreator.initReportData(getJasperInputSteam("T50307.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50307RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50307RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50307RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("select ACQ_INST_ID,MCHT_NO,MCHT_NM,FIRST_T_DATE,SUBSTR(SETTLE_ACCT,2),SINGLE_COUNT,SINGLE_AMT," +
				"AVE_AMT,CREDIT_COUNT,CREDIT_AMT,RET_COUNT,RET_AMT,INTE_COUNT,INTE_AMT," +
				"BIG_COUNT,BIG_AMT,REF_COUNT,REF_AMT from ABS_MCHT_TRADE_MON where FLAG= '0' " );
		
		if (!StringUtil.isNull(acqInstId)) {
			sb.append(" and trim(ACQ_INST_ID) in ").append(InformationUtil.getBrhGroupString(acqInstId));
		}
		
		if (!StringUtil.isNull(date)) {
			sb.append(" and ABS_MON = '" + date + "'");
		}
		
		sb.append("order by ACQ_INST_ID,MCHT_NO");
		
		return sb.toString();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAcqInstId() {
		return acqInstId;
	}

	public void setAcqInstId(String acqInstId) {
		this.acqInstId = acqInstId;
	}

	
	
	
}
