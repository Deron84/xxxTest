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


public class T80304Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 15);
		
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			data[i][4] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][4].toString()));
			data[i][5] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][5].toString()));
			data[i][7] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][7].toString()));
			data[i][8] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][8].toString()));
			data[i][9] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][9].toString()));
			data[i][10] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][10].toString()));
			data[i][11] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][11].toString()));
			data[i][12] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][12].toString()));
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
		parameters.put("P_3", signInstId);
		
		reportCreator.initReportData(getJasperInputSteam("T80304.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80304RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80304RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80304RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		String sql = "select a.MCHT_NO,trim(a.MCHT_NAME),d.brh_name,sum(TRANS_NUM_COM+0),sum(TRANS_AMT_COM),sum(TRANS_FEE_COM)," +
					"sum(TRANS_NUM_ERR+0),sum(TRANS_AMT_ERR),sum(TRANS_BAK_AMT),sum(OTHER_AMT)," +
					"sum(TRANS_AMT_COM+TRANS_FEE_COM+TRANS_AMT_ERR+TRANS_BAK_AMT+OTHER_AMT)," +
					"sum((TRANS_AMT_COM+TRANS_FEE_COM+TRANS_AMT_ERR+TRANS_BAK_AMT+OTHER_AMT) " +
						"+ (IN_EX_AMT+AUR_RELIEF_AMT+AUR_CHARGE_AMT+AUR_BAK_AMT)) ,sum(bc.FEE_CREDIT - bc.FEE_DEBIT),b.MCHT_FLAG1,b.MCHT_FLAG1||b.MCHT_FLAG2 " +
					"from BTH_CUP_ZPSUM a left outer join TBL_MCHT_BASE_INF b on (a.MCHT_NO =b.MCHT_NO) " 
					+ " left outer join BTH_CUP_COMA bc on (bc.CARD_ACCP_ID = a.MCHT_NO and bc.INST_DATE = a.inst_date) " 
					+ " left outer join tbl_brh_info d on (b.agr_br = d.brh_id)  "  +
					" where 1=1 ";

		if (isNotEmpty(mchtCd)) {
			sql += " AND trim(a.MCHT_NO)='"+mchtCd+"'";
		}
		if (isNotEmpty(signInstId) && !"0000".equals(signInstId)) {
			sql += " AND trim(b.SIGN_INST_ID)='"+signInstId+"'";
		}
		if (isNotEmpty(startDate)) {
			sql += " and trim(a.INST_DATE)>='"+startDate+"'";
		}
		if (isNotEmpty(endDate)) {
			sql += " and trim(a.INST_DATE)<='"+endDate+"'";
		}
		
		return sql + "group by a.MCHT_NO,a.MCHT_NAME,b.MCHT_FLAG1,b.MCHT_FLAG1||b.MCHT_FLAG2,d.brh_name ";
	}
	

	private String startDate;
	private String endDate;
	private String mchtCd;
	private String signInstId;

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

	public String getSignInstId() {
		return signInstId;
	}

	public void setSignInstId(String signInstId) {
		this.signInstId = signInstId;
	}
	
}