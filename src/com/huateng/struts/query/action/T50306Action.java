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

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-19
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T50306Action extends ReportBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.ReportBaseAction#genSql()
	 */
	@Override
	protected String genSql() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.ReportBaseAction#reportAction()
	 */
	@Override
	protected void reportAction() throws Exception {

		String[] title={"V","V_1","V_2","V_3","V_4","V_5","V_6",
				"V_7","V_8","V_9","V_10","V_11"};
		
		// data = reportCreator.process(genSql(), title);
		 data = new Object[1][title.length];
		 for(int i=0; i<title.length; i++) {
				
				data[0][i] = "1001";
			}
			
			if(data.length == 0) {
				writeNoDataMsg("没有找到符合条件的记录");
				return;
			}
			
			reportModel.setData(data);
			reportModel.setTitle(title);
			
			reportCreator.initReportData(getJasperInputSteam("T50306.jasper"), parameters, 
					reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
							operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
							operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
			
			outputStream = new FileOutputStream(fileName);
			
			reportCreator.exportReport(outputStream, "专业化服务机构费用汇总表（日表）机构号");
			
			callDownload(fileName,"fuwujigouribiao.xls");
			

	}

}
