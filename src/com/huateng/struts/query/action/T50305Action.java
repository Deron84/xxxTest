/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-7-19       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.struts.query.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysParamUtil;

public class T50305Action  extends ReportBaseAction  {

	@Override
	protected String genSql() {
		
		return sql;
	}

	@Override
	protected void reportAction() throws Exception {
		
		String[] title={"V","V_1","V_2","V_3","V_4","V_5","V_6",
				"V_7","V_8","V_9","V_10","V_11"};
		
		data = new Object[1][title.length];
		
		for(int i=0; i<title.length; i++) {
			
			data[0][i] = "1";
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		reportCreator.initReportData(getJasperInputSteam("T50305.jasper"), parameters, 
				                     reportModel.wrapReportDataSource(), getReportType());

		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, "专业化服务机构费用汇总表（月表）机构号");
		
		callDownload(fileName,"fuwujigouyuebiao.xls");
	}
}
