package com.huateng.struts.query.action;

import java.io.FileOutputStream;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;


public class T80303Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 13);
		
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			data[i][9] = CommonFunction.transFenToYuan(data[i][9].toString());
			data[i][10] = CommonFunction.transFenToYuan(data[i][10].toString());
			data[i][11] = CommonFunction.transFenToYuan(data[i][11].toString());
			data[i][12] = CommonFunction.transFenToYuan(data[i][12].toString());
		}

		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		String dateStart = "/";
		String dateEnd = "/";
		
		if(!StringUtil.isNull(startDate)){
			dateStart = startDate.substring(0, 4) +
				   "/" + 
				   startDate.substring(4, 6) +
				   "/" + 
				   startDate.substring(6, 8);
		}
		
		
		if(!StringUtil.isNull(endDate)){
			dateEnd = endDate.substring(0, 4) +
			   "/" + 
			   endDate.substring(4, 6) +
			   "/" + 
			   endDate.substring(6, 8);
		}

		
		
		parameters.put("P_1", dateStart+" - "+dateEnd);
		parameters.put("P_2", mchtCd);
		
		reportCreator.initReportData(getJasperInputSteam("T80303.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80303RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80303RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80303RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		
		String whereSql = " ";
		
		if (isNotEmpty(mchtCd)) {
			whereSql += " AND trim(CARD_ACCP_ID)='"+mchtCd+"'";
		}
		
		
		if (isNotEmpty(startDate)) {
			whereSql += " and trim(INST_DATE)>='"+startDate+"'";
		}
		if (isNotEmpty(endDate)) {
			whereSql += " and trim(INST_DATE)<='"+endDate+"'";
		}
		
		String sql = "select INST_DATE,TRANS_DATE_TIME,ACQ_INST_ID_CODE,CARD_ACCP_ID,trim(MCHT_NAME)," +
				"CARD_ACCP_TERM_ID,CUP_SSN,PAN," +
				"(select TXN_NAME from TBL_TXN_NAME b where a.TXN_NUM=b.TXN_NUM)," +
				"AMT_TRANS,FEE_MCHT,FEE_CREDIT-FEE_DEBIT,AMT_TRANS+FEE_MCHT " +
			"from BTH_CUP_COMA a " +
			"where CANCEL_FLAG not in('R','C')  and substr(a.TXN_NUM,0,1) !='3' " + 
			whereSql;
	//		" order by INST_DATE desc,TRANS_DATE_TIME desc,ACQ_INST_ID_CODE,CARD_ACCP_ID,CARD_ACCP_TERM_ID,TXN_NUM";
		
		return sql;
	}
	

	private String startDate;
	private String endDate;
	private String mchtCd;


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


	public String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}
}