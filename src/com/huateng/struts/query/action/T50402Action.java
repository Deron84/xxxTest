package com.huateng.struts.query.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 疑似套现监测报表
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-26
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T50402Action  extends ReportBaseAction  {
	
	@Override
	protected void reportAction() throws Exception {
		
		String rpName = "";
		
		if("1".endsWith(reportName)){
			title = InformationUtil.createTitles("V_", 16);
			rpName = "RN50402RN";
			
		}else{
			title = InformationUtil.createTitles("V_", 18);
			rpName = "RN504021RN";
		}
		data = reportCreator.process(genSql(), title);		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}		
		reportModel.setData(data);
		reportModel.setTitle(title);		
		//组装参数
		//统计日期
		parameters.put("betdate", date);
		//机构编号
		parameters.put("brh", InformationUtil.getBrhName(acqInstId));
		//柜员
		parameters.put("opr", operator.getOprId());
		
		if("1".endsWith(reportName)){
			reportCreator.initReportData(getJasperInputSteam("T50402.jasper"), parameters, 
					reportModel.wrapReportDataSource(), getReportType());
		}else{
			reportCreator.initReportData(getJasperInputSteam("T504021.jasper"), parameters, 
					reportModel.wrapReportDataSource(), getReportType());
		}
			
	
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rpName + "_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rpName + "_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam(rpName));
		
		writeUsefullMsg(fileName);
		return;
		
	}
	
	@Override
	protected String genSql() {
		
		if("1".endsWith(reportName)){
			sql = "select M.MCHT_NO,M.MCHT_NM,N.CARD_ACCP_TERM_ID,trim(N.MISC_FLAG),BIND_TEL1,TXN_NAME,SUBSTR(UPDT_DATE,9,8) AS UPT,DATE_SETTLMT,PAN," +
					"SUBSTR(M.SETTLE_ACCT,2) AS ACCT," +
						"(case when AMT_TRANS is null then 0 when AMT_TRANS = ' ' then 0 else AMT_TRANS*1/100 end) AS TRANS," +
						"TERM_SSN,AUTHR_ID_R,RETRIVL_REF,TLR_NUM,EMV_VAL " +
				  "from " +
				  		"TBL_N_TXN N,TBL_TERM_INF,TBL_TXN_NAME T,ABS_MCHT_TRADE_MON M " +
				  "where " +
				  	"TERM_ID = CARD_ACCP_TERM_ID AND N.TXN_NUM = T.TXN_NUM " +
				  	"and trim(CARD_ACCP_ID) = trim(M.MCHT_NO) ";
			
			sql += "and M.FLAG = '1' and trim(M.ACQ_INST_ID) in " + InformationUtil.getBrhGroupString(acqInstId) + " " +
					"and M.ABS_MON = '" + date + "'";
			
			sql += "and substr(updt_date,1,6) = '" + date + "' ";
			
//			sql += "and CARD_ACCP_ID in (select MON.MCHT_NO from ABS_MCHT_TRADE_MON MON where MON.FLAG = '1' " +
//					"and MON.ACQ_INST_ID in " + InformationUtil.getBrhGroupString(acqInstId) + " and MON.ABS_MON = '" + date + "')";
			
			sql += "order by CARD_ACCP_TERM_ID,N.TXN_NUM,UPDT_DATE";
		} else {
			sql = "select ACQ_INST_ID,MCHT_NO,MCHT_NM,FIRST_T_DATE,SUBSTR(SETTLE_ACCT,2),SINGLE_COUNT,SINGLE_AMT," +
				"AVE_AMT,CREDIT_COUNT,CREDIT_AMT,RET_COUNT,RET_AMT,INTE_COUNT,INTE_AMT," +
				"BIG_COUNT,BIG_AMT,REF_COUNT,REF_AMT from ABS_MCHT_TRADE_MON where FLAG = '1' ";
			
			if (!StringUtil.isNull(acqInstId)) {
				sql += " and trim(ACQ_INST_ID) in " + InformationUtil.getBrhGroupString(acqInstId);
			}
			
			if (!StringUtil.isNull(date)) {
				sql += " and ABS_MON = '" + date + "'";
			}
			sql += "order by ACQ_INST_ID,MCHT_NO";
		}
		return sql;
	}

	private String date;
	private String acqInstId;
	private String reportName;
	private String reportType;

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

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	public String StringOf(Object obj){
		if (null == obj) {
			return "";
		} else {
			return obj.toString();
		}
	}
}
