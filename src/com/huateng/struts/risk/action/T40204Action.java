/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-8-10       first release
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
package com.huateng.struts.risk.action;

import java.io.FileOutputStream;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T40204Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-24
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T40204Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 8);
		
		data = reportCreator.process(genSql(), title);
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		parameters.put("betdate", startDate + " - " + endDate);
		
		reportCreator.initReportData(getJasperInputSteam("T40204.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40204RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40204RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, "商户黑名单历史交易表");
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		String whereSql = "";
		if (!StringUtil.isNull(startDate)) {
			whereSql += "AND trim(sa_txn_date) >= '" + startDate.trim() + "' ";
		}
		if (!StringUtil.isNull(endDate)) {
			whereSql += "AND trim(sa_txn_date) <= '" + endDate.trim() + "' ";
		}
		if (!StringUtil.isNull(srBrhNo)) {
			whereSql += "AND trim(SA_OPEN_INST) = '" + srBrhNo.trim() + "' ";
		}
		if (!StringUtil.isNull(srCardNo)) {
			whereSql += "AND trim(sa_txn_card) = '" + srCardNo.trim() + "' ";
		}

		sql = "select sa_txn_card,sa_mcht_no,sa_term_no,txn_name," +
		"(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end)," +
		"sa_txn_date,SA_TXN_TIME,(case SA_CLC_FLAG when '0' then '正常' when '2' then '拒绝' when '3' then '预警' else '未知' end) " +
		"from tbl_clc_mon,tbl_txn_name " +
		"where sa_txn_num = txn_num and trim(SA_CLC_RSN1) = '商户单笔交易额超限' ";

		sql += whereSql.toString();

		sql += "order by sa_txn_date desc,SA_TXN_TIME desc";
		
		return sql;
	}
	
	private String startDate;
	private String endDate;
	private String srCardNo;
	/**
	 * @return the srCardNo
	 */
	public String getSrCardNo() {
		return srCardNo;
	}

	/**
	 * @param srCardNo the srCardNo to set
	 */
	public void setSrCardNo(String srCardNo) {
		this.srCardNo = srCardNo;
	}

	private String srBrhNo;

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the srBrhNo
	 */
	public String getSrBrhNo() {
		return srBrhNo;
	}

	/**
	 * @param srBrhNo the srBrhNo to set
	 */
	public void setSrBrhNo(String srBrhNo) {
		this.srBrhNo = srBrhNo;
	}
}
