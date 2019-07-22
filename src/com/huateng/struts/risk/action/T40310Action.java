package com.huateng.struts.risk.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 财务POS交易监测
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

public class T40310Action  extends ReportBaseAction  {
	
	private String brhId; //收单机构号
	private String mchntCd; //商户编号
	private String companyName; //单位名称
	private String terminalCode; //终端号
	private String mchntManagerCd; //客户经理工号
	private String startDate ; //交易开始日期
	private String endDate ; //交易 结束日期
	private String codeNo; //卡号
	private String rule1; //对同一商卡“借款+报销”累计笔数大于等于(笔)
	private String rule2; //对同一商卡“借款+报销”累计金额大于等于(元)
	private String rule3; //借款笔数/还款笔数大于等于(%)
	private String rule4; //借款金额/还款金额大于等于(%)
	private String reportType; //报表类型

	protected String genSql() {

//		String whereSql = " where 1=1 ";
//		if(isNotEmpty(brhId)) {
//			whereSql += " and MCC = " + wrapSql(brhId);
//		}
//		if(isNotEmpty(mchntCd)) {
//			whereSql += " and fee_type = " + wrapSql(mchntCd);
//		}
//		if(isNotEmpty(mchntNm)) {
//			whereSql += " and mchnt_st = " + wrapSql(mchntNm);
//		}
//		if(isNotEmpty(mchntType)) {
//			whereSql += " and register_date = " + wrapSql(mchntType);
//		}
//		sql = "select MCHT_NO from TBL_MCHT_BASE_INF " + whereSql;
		
		sql = "select MCHT_NO,MCHT_NO,MCHT_NO,MCHT_NO,MCHT_NO,MCHT_NO,MCHT_NO,MCHT_NO,MCHT_NO,MCHT_NO,MCHT_NO from TBL_MCHT_BASE_INF"; 
		return sql;
	}

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.ReportBaseAction#reportAction()
	 */
	@Override
	protected void reportAction() throws Exception {

		String[] title = {"ROW_ID", "BRH_ID", "MCHNT_CD", "COMP_NAME", "TER_NO", "MAN_NO",
				 "CARD_NO", "J_B_B", "J_B_J", "J_H_B", "J_H_J"};
		
//		data = reportCreator.process(genSql(), title);
//		
//		if(data.length == 0) {
//			writeNoDataMsg("没有找到符合条件的记录");
//			return;
//		}
		
		data = new Object[1][title.length];
		
		for(int i=0; i<title.length; i++) data[0][i] = "test";
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		reportCreator.initReportData(getJasperInputSteam("T40310.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, "财务POS交易监测");
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			callDownload(fileName,"财务POS交易监测.xls");
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			callDownload(fileName,"财务POS交易监测.pdf");
		
	}

	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	/**
	 * @return the mchntCd
	 */
	public String getMchntCd() {
		return mchntCd;
	}

	/**
	 * @param mchntCd the mchntCd to set
	 */
	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the terminalCode
	 */
	public String getTerminalCode() {
		return terminalCode;
	}

	/**
	 * @param terminalCode the terminalCode to set
	 */
	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	/**
	 * @return the mchntManagerCd
	 */
	public String getMchntManagerCd() {
		return mchntManagerCd;
	}

	/**
	 * @param mchntManagerCd the mchntManagerCd to set
	 */
	public void setMchntManagerCd(String mchntManagerCd) {
		this.mchntManagerCd = mchntManagerCd;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the codeNo
	 */
	public String getCodeNo() {
		return codeNo;
	}

	/**
	 * @param codeNo the codeNo to set
	 */
	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	/**
	 * @return the rule1
	 */
	public String getRule1() {
		return rule1;
	}

	/**
	 * @param rule1 the rule1 to set
	 */
	public void setRule1(String rule1) {
		this.rule1 = rule1;
	}

	/**
	 * @return the rule2
	 */
	public String getRule2() {
		return rule2;
	}

	/**
	 * @param rule2 the rule2 to set
	 */
	public void setRule2(String rule2) {
		this.rule2 = rule2;
	}

	/**
	 * @return the rule3
	 */
	public String getRule3() {
		return rule3;
	}

	/**
	 * @param rule3 the rule3 to set
	 */
	public void setRule3(String rule3) {
		this.rule3 = rule3;
	}

	/**
	 * @return the rule4
	 */
	public String getRule4() {
		return rule4;
	}

	/**
	 * @param rule4 the rule4 to set
	 */
	public void setRule4(String rule4) {
		this.rule4 = rule4;
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
}
