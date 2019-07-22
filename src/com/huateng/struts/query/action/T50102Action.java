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
 * Title: 
 * 
 * File: T50102Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T50102Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 12);
		
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {

			data[i][8] = CommonFunction.transFenToYuan(data[i][8].toString());
		}
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		parameters.put("betdate", startDate + " - " + endDate);
		
		reportCreator.initReportData(getJasperInputSteam("T50102.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50102RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50102RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50101RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		
		
		String whereSql = "";
		if (!StringUtil.isNull(startDate)) {
			whereSql += "AND substr(UPDT_DATE,1,8) >= '" + startDate + "' ";
		}
		if (!StringUtil.isNull(endDate)) {
			whereSql += "AND substr(UPDT_DATE,1,8) <= '" + endDate + "' ";
		}
		if (!StringUtil.isNull(mchntNo)) {
			whereSql += "AND CARD_ACCP_ID = '" + mchntNo + "' ";
		}
		//终端编号
		if (!StringUtil.isNull(termId)) {
			whereSql += " AND CARD_ACCP_TERM_ID = '" + termId + "' ";
		}
		//卡号
		if (!StringUtil.isNull(pan)) {
			whereSql += " AND rtrim(PAN) like '" + pan.trim() + "%' ";
		}
		//检索参考号
		if (!StringUtil.isNull(retrivlRef)) {
			whereSql += " AND rtrim(RETRIVL_REF) like '" + retrivlRef.trim() + "%' ";
		}
		//应答码
		if (!StringUtil.isNull(respCode)) {
			whereSql += " AND RESP_CODE = '" + respCode + "' ";
		}

		StringBuffer sb = new StringBuffer();
		sb.append("select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,"
						+ "SYS_SEQ_NUM,PAN,CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,"
						+ "ACQ_INST_ID_CODE,name.TXN_NAME,RESP_CODE from tbl_t_txn t left outer join "
						+ "tbl_txn_name name on (t.txn_num = name.txn_num) where 1 = 1 ");
		
		//根据终端号前两位定义受理机构,总行不受控制
		if (StringUtil.isNull(brhId)) {
			brhId = operator.getOprBrhId();
		}
		if (!"9900".equals(brhId)){
			sb.append(" and substr(CARD_ACCP_TERM_ID,1,2) = '" + brhId.substring(0, 2) + "'");
		}
		sb.append(whereSql);
		sb.append(" ORDER BY UPDT_DATE DESC");
		
		return sb.toString();
	}
	
	private String startDate;
	private String endDate;
	private String mchntNo;
	private String brhId;
	
	private String termId;
	private String pan;
	private String retrivlRef;
	private String respCode;

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
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * @return the pan
	 */
	public String getPan() {
		return pan;
	}

	/**
	 * @param pan the pan to set
	 */
	public void setPan(String pan) {
		this.pan = pan;
	}

	/**
	 * @return the retrivlRef
	 */
	public String getRetrivlRef() {
		return retrivlRef;
	}

	/**
	 * @param retrivlRef the retrivlRef to set
	 */
	public void setRetrivlRef(String retrivlRef) {
		this.retrivlRef = retrivlRef;
	}

	/**
	 * @return the respCode
	 */
	public String getRespCode() {
		return respCode;
	}

	/**
	 * @param respCode the respCode to set
	 */
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
}
