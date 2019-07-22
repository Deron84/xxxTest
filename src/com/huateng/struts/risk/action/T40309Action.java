package com.huateng.struts.risk.action;

import java.io.FileOutputStream;
import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T40309Action  extends ReportBaseAction  {
	
	private String brhId ;//收单机构号
	private String mchntCd ;//商户编号
	private String mchntNm ;//商户名称
	private String mchntType;//商户类型
	private String terminalCode;//终端号
	private String posType;//POS终端类型
	private String mchntManagerCd;//客户经理工号
	private String startDate;//交易开始日期
	private String endDate;//交易 结束日期
	private String rule1;//单户退货交易笔数大于等于（笔）
	private String rule2;//单户退货交易笔数在同期所有交易笔数中占比大于等于（%）
	private String reportType;//报表类型

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
						   "V_3", "V_4", "V_5", "V_6", "V_7", "V_8", "V_9", "V_10"};
		
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
		
		reportCreator.initReportData(getJasperInputSteam("T40309.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, "单户退货情况监测");
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			callDownload(fileName,"单户退货情况监测.xls");
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			callDownload(fileName,"单户退货情况监测.pdf");
		
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
	 * @return the mchntNm
	 */
	public String getMchntNm() {
		return mchntNm;
	}

	/**
	 * @param mchntNm the mchntNm to set
	 */
	public void setMchntNm(String mchntNm) {
		this.mchntNm = mchntNm;
	}

	/**
	 * @return the mchntType
	 */
	public String getMchntType() {
		return mchntType;
	}

	/**
	 * @param mchntType the mchntType to set
	 */
	public void setMchntType(String mchntType) {
		this.mchntType = mchntType;
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
	 * @return the posType
	 */
	public String getPosType() {
		return posType;
	}

	/**
	 * @param posType the posType to set
	 */
	public void setPosType(String posType) {
		this.posType = posType;
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
