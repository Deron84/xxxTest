package com.huateng.struts.mchnt.action;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;


public class T2080201Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 9);
		
		data = reportCreator.process(genSql(), title);
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		parameters.put("P_1",agrBrId + "-" + InformationUtil.getBrhName(agrBrId));
		parameters.put("P_2", thirdId);
		
		reportCreator.initReportData(getJasperInputSteam("T2080201.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN2080201RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN2080201RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN2080201RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		Calendar c =  new GregorianCalendar();
		c.add(Calendar.DAY_OF_YEAR, 30);
		Date dateAfter = c.getTime();
		String dateString = new SimpleDateFormat("yyyyMMdd").format(dateAfter);

		StringBuffer sb = new StringBuffer();
		sb.append("select (select brh_name from TBL_BRH_INFO b where a.agr_br=b.BRH_ID) brhNm," +
				"mcht_no,mcht_nm,addr,contact,comm_tel,rct_check_date,check_frqc,plan_check_date " +
				"from tbl_mcht_base_inf a ");
		
		sb.append(" where (to_date(nvl(rct_check_date,'19700101'),'yyyymmdd')+to_number(trim(check_frqc))-30) <= to_date('"+dateString+"','yyyymmdd') and mcht_status ='0' ");
		
		if(!"0000".equals(operator.getOprBrhId())){
			sb.append(" AND agr_br IN "+operator.getBrhBelowId()+" ");
		}
		
		if (!StringUtil.isNull(agrBrId)) {
			sb.append(" AND agr_br = '" + agrBrId.trim() + "'");
		}

		if (!StringUtil.isNull(thirdId)) {
			sb.append(" and mcht_no in (select mcht_cd from tbl_term_inf where term_sta not in('4','7','9') and prop_tp='1' and prop_ins_nm='" + thirdId.trim() + "')");
		}
		
		sb.append(" order by rct_check_date");
		
		return sb.toString();
	}
	
	private String agrBrId;
	private String thirdId;

	public String getAgrBrId() {
		return agrBrId;
	}

	public void setAgrBrId(String agrBrId) {
		this.agrBrId = agrBrId;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

}