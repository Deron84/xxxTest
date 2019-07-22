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
 * Title: 移机检测报表
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
public class T50401Action  extends ReportBaseAction  {
	
	@Override
	protected String genSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("select ACQ_INST_ID,MCHT_NO,MCHT_NM,TERM_NO,REAL_TEL,BIND_TEL1,BIND_TEL2,BIND_TEL3,B.TXN_NAME,SUBSTR(INST_TIME,9)," +
				"PAN,AMT_TRANS,TERM_SSN,CERTI_ID,AUTHR_ID_R,RETRIVL_REF,DESR " +
				"from ABS_SHIFT_TERM_MON A,TBL_TXN_NAME B where A.TXN_NUM = B.TXN_NUM ");
		
		if (!StringUtil.isNull(acqInstId)) {
			sb.append(" and trim(ACQ_INST_ID) in ").append(InformationUtil.getBrhGroupString(acqInstId));
		}
		
		if (!StringUtil.isNull(date)) {
			sb.append(" and ABS_DATE = '" + date + "'");
		}
		sb.append("order by ACQ_INST_ID,MCHT_NO");
		
		return sb.toString();
	}

	@Override
	protected void reportAction() throws Exception {
		String[] title = InformationUtil.createTitles("V_", 17);
	
		Object[][] data = reportCreator.process(genSql(), title);
		
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
		
		reportCreator.initReportData(getJasperInputSteam("T50401.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50401RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50401RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50401RN"));
		
		writeUsefullMsg(fileName);
		
		return;
		
	}

	private String date;
	private String acqInstId;
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

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	
	
}
