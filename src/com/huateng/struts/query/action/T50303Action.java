package com.huateng.struts.query.action;

import java.io.FileOutputStream;

import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T50303Action  extends ReportBaseAction  {

	@Override
	protected String genSql() {
		
		return sql;
	}

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "XLS";
		
		String[] title = {"V_1","V_2","V_3","V_4","V_5","V_6","V_7","V_8","V_9","V_10","V_11","V_12","V_13",};
		
		data = new Object[1][title.length];
		
		for(int i=0; i<title.length; i++) {
			
			data[0][i] = "1";
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		reportCreator.initReportData(getJasperInputSteam("T50303.jasper"), parameters, 
				                     reportModel.wrapReportDataSource(), getReportType());

		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
		           operator.getOprId() + "_singleUserReport_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, "间联POS待清算交易汇总（日表）");
		
		callDownload(fileName,"singleUserReport.xls");
	}
}
