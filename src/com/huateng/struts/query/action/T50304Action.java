package com.huateng.struts.query.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysParamUtil;

public class T50304Action extends ReportBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.ReportBaseAction#genSql()
	 */
	@Override
	protected void reportAction() throws Exception {
		
		 String[] title={"V_1","V_2","V_3","V_4","V_5","V_6","V_7","V_8"
				 ,"V_9","V_10","V_11","V_12","V_13","V_14"};
		// data = reportCreator.process(genSql(), title);
		 data = new Object[1][title.length];
				
				data[0][0] = "2367367728321";
				data[0][1]="2011-07-01";
				data[0][2]="2011-07-05";
				data[0][3]="3211";
				data[0][4]="3213123222111";
				data[0][5]="发卡交易";
				data[0][6]="10021.00";
				data[0][7]="12.00";
				data[0][8]="12";
				data[0][9]="3232";
				data[0][10]="22";
				data[0][11]="2312";
				data[0][12]="22211";
				data[0][13]="交易成功";
			
			if(data.length == 0) {
				writeNoDataMsg("没有找到符合条件的记录");
				return;
			}
			
			reportModel.setData(data);
			reportModel.setTitle(title);
			
			reportCreator.initReportData(getJasperInputSteam("T50304.jasper"), parameters, 
					reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
							operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
							operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
			
			outputStream = new FileOutputStream(fileName);
			
			reportCreator.exportReport(outputStream, "间联POS待清算交易汇总表");
			callDownload(fileName,"jianlianqingsuanhuizongribiao.xls");
	}
	@Override
	protected String genSql() {
		String whereSql = " where 1=1 ";
		sql = "select MCHT_NO from TBL_MCHT_BASE_INF " + whereSql;
		
		if(isNotEmpty(mchtNo)) {
			whereSql += " and MCC = " ;
		}
		
	
		return sql;
		
	}
	
	// 商户号码
	private String mchtNo;
	//统计日期
	private String stDate;
	//报表类型
	private String reportType;

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getStDate() {
		return stDate;
	}

	public void setStDate(String stDate) {
		this.stDate = stDate;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	
}
