package com.huateng.struts.risk.action;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.huateng.bo.risk.T40202BO;
import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.po.TblCtlMchtInf;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 单笔信用卡交易监测
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
public class T40302Action  extends ReportBaseAction  {
	
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
//		sql = "select MCHT_NO,MCHT_NM,CRE_MCHT_FLG,CDC_MCHT_FLG,RISL_LVL ,MCHT_STATUS,MAIL_TLR,MANU_AUTH_FLAG ,DISC_CONS_FLG ,PASS_FLAG ,SPELL_NAME ,AREA_NO ,ADDR ,MCC  ,MCHT_GRP ,MCHT_GROUP_FLAG   from TBL_MCHT_BASE_INF_TMP "+ whereSql;
		
		return sql;
	}

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.ReportBaseAction#reportAction()
	 */
	@Override
	protected void reportAction() throws Exception {
		String[] title = new String[] { "V_1", "V_2", "V_3", "V_4",
				"V_5", "V_6", "V_7","V_8",
				"V_9", "V_10", "V_11", "V_12", "V_13", "V_14", "V_15", "V_16"
				 };
		
//		data = reportCreator.process(genSql(), title);
		data = new Object[1][title.length];
		data[0][0]="1";
		data[0][1]="2";
		data[0][2]="3";
		data[0][3]="4";
		data[0][4]="5";
		data[0][5]="6";
		data[0][6]="7";
		data[0][7]="8";
		data[0][8]="9";
		data[0][9]="10";
		data[0][10]="11";
		data[0][11]="12";
		data[0][12]="13";
		data[0][13]="14";
		data[0][14]="15";		
		data[0][15]="16";
//		data[0][16]="17";
//		data[0][17]="18";
//		data[0][18]="19";
//		data[0][19]="20";	
//		data[0][20]="21";
//		data[0][21]="22";
//		data[0][22]="23";
//		data[0][23]="24";
//		data[0][24]="25";
//		data[0][25]="26";
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		parameters.put("DATE_RANGE", createDateRange(startDate, endDate));
		
		reportCreator.initReportData(getJasperInputSteam("T40302.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_singlCreditCardRreport_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_singlCreditCardRreport_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, "单笔信用卡交易监测");
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			callDownload(fileName,"singlCreditCardRreport.xls");
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			callDownload(fileName,"singlCreditCardRreport.pdf");
		
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

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	
	
}
