package com.huateng.struts.query.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T50302Action  extends ReportBaseAction  {

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
	 * @return the mchntNo
	 */
	public String getMchntNo() {
		return mchntNo;
	}

	/**
	 * @param mchntNo the mchntNo to set
	 */
	public void setMchntNo(String mchntNo) {
		this.mchntNo = mchntNo;
	}

	/**
	 * @return the stDate
	 */
	public String getStDate() {
		return stDate;
	}

	/**
	 * @param stDate the stDate to set
	 */
	public void setStDate(String stDate) {
		this.stDate = stDate;
	}


	private String brhId="2345" ;//收单机构号
	private String mchntNo = "2345551" ;//商户编号
	private String stDate = "20011132";//统计时间
//	private String reportType ;//报表类型
	private String mchtNm = "34we";
	private String betId = "34we";
	private String betType = "33eserrd";
	@Override
	protected String genSql() {
		String whereSql = " where 1=1 ";
		if(isNotEmpty(mchntNo)) {
			whereSql += " and MCHT_NO = " + wrapSql(mchntNo);
		}
		if(isNotEmpty(stDate)) {
			whereSql += " and stDate = " + wrapSql(stDate);
		}

		sql = "select MCHT_NO ,MCHT_NM ,RISL_LVL ,MCHT_STATUS,MANU_AUTH_FLAG ,DISC_CONS_FLG ,PASS_FLAG ,SPELL_NAME ,AREA_NO ,ADDR ,MCC AS ,MCHT_GRP ,MCHT_GROUP_FLAG   from TBL_MCHT_BASE_INF_TMP "+ whereSql;
		return sql;
	}

	@Override
	protected void reportAction() throws Exception {

		title = new String[]{"V_1","V_2","V_3","V_4","V_5","V_6","V_7","V_8","V_9","V_10","V_11","V_12","V_13"};
		
		data = reportCreator.process(genSql(), title);		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		reportModel.setData(data);
		reportModel.setTitle(title);
		parameters.put("mchtNo",mchntNo );
		parameters.put("brhId",brhId );
		parameters.put("betDate",stDate );
		parameters.put("mchtNm",mchtNm );
		parameters.put("betId",betId );
		parameters.put("betType",betType );
		reportCreator
				.initReportData(getJasperInputSteam("T50301.jasper"),
						parameters, reportModel.wrapReportDataSource(),
						getReportType());

		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_singlCreditCardRreport_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_singlCreditCardRreport_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, "商户对账单月表");
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			callDownload(fileName,"singlCreditCardRreport.xls");
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			callDownload(fileName,"singlCreditCardRreport.pdf");
	}
}
