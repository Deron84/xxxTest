package com.huateng.struts.risk.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T40304Action  extends ReportBaseAction  {
	
	private String brhId ;//收单机构号
	private String mchntCd ;//商户编号
	private String mchntNm ;//商户名称
	private String mchntType;//商户类型
	private String terminalCode;//终端号
	private String mchntManagerCd;//客户经理工号
	private String startDate ;//交易开始日期
	private String endDate ;//交易 结束日期
	//private String posType = request.getParameter("POS_TYPE");

	private String rule1 ;// 单户信用卡累计交易笔数大于,整数
	private String rule2 ;// 单户信用卡累计交易笔数小于,整数
	private String rule3 ;// 单户信用卡累计交易金额大于,金额
	private String rule4 ;// 单户信用卡笔均交易金额大于,金额
	private String rule5 ;// 单户所有卡累计交易笔数大于,整数
	private String rule6 ;// 单户所有卡累计交易笔数小于,整数
	private String rule7 ;// 单户所有卡累计交易金额大于,金额
	private String rule8;// 单户所有卡笔均交易金额大于,金额
	private String reportType ;

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.ReportBaseAction#genSql()
	 */
	@Override
	protected String genSql() {
		
		String whereSql = " where 1=1 ";
		
		if(isNotEmpty(brhId)) {
			whereSql += " and MCC = " + wrapSql(brhId);
		}
		if(isNotEmpty(mchntCd)) {
			whereSql += " and fee_type = " + wrapSql(mchntCd);
		}
		if(isNotEmpty(mchntNm)) {
			whereSql += " and mchnt_st = " + wrapSql(mchntNm);
		}
		if(isNotEmpty(mchntType)) {
			whereSql += " and register_date = " + wrapSql(mchntType);
		}
		
		sql = "select MCHT_NO from TBL_MCHT_BASE_INF " + whereSql;

		return sql;
	}

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.ReportBaseAction#reportAction()
	 */
	@Override
	protected void reportAction() throws Exception {
		String[] title = { "V", "V_1", "V_2", 
				   "V_3", "V_4", "V_5", "V_6", "V_7", "V_8", "V_9"};
		
//		data = reportCreator.process(genSql(), title);
//		
//		if(data.length == 0) {
//			writeNoDataMsg("没有找到符合条件的记录");
//			return;
//		}
		
		data = new Object[1][title.length];
		
		for(int i=0; i<title.length; i++) {
			
			data[0][i] = "1";
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		reportCreator.initReportData(getJasperInputSteam("T40304.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, "信用卡整数类交易监测");
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			callDownload(fileName,"信用卡整数类交易监测.xls");
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			callDownload(fileName,"信用卡整数类交易监测.pdf");
		
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getMchntCd() {
		return mchntCd;
	}

	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}

	public String getMchntNm() {
		return mchntNm;
	}

	public void setMchntNm(String mchntNm) {
		this.mchntNm = mchntNm;
	}

	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getMchntType() {
		return mchntType;
	}

	public void setMchntType(String mchntType) {
		this.mchntType = mchntType;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public String getMchntManagerCd() {
		return mchntManagerCd;
	}

	public void setMchntManagerCd(String mchntManagerCd) {
		this.mchntManagerCd = mchntManagerCd;
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

	public String getRule1() {
		return rule1;
	}

	public void setRule1(String rule1) {
		this.rule1 = rule1;
	}

	public String getRule2() {
		return rule2;
	}

	public void setRule2(String rule2) {
		this.rule2 = rule2;
	}

	public String getRule3() {
		return rule3;
	}

	public void setRule3(String rule3) {
		this.rule3 = rule3;
	}

	public String getRule4() {
		return rule4;
	}

	public void setRule4(String rule4) {
		this.rule4 = rule4;
	}

	public String getRule5() {
		return rule5;
	}

	public void setRule5(String rule5) {
		this.rule5 = rule5;
	}

	public String getRule6() {
		return rule6;
	}

	public void setRule6(String rule6) {
		this.rule6 = rule6;
	}

	public String getRule7() {
		return rule7;
	}

	public void setRule7(String rule7) {
		this.rule7 = rule7;
	}

	public String getRule8() {
		return rule8;
	}

	public void setRule8(String rule8) {
		this.rule8 = rule8;
	}
	
	
	
}
