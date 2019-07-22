
/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-27       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
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
 * Title: 商户信息统计表
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-27
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T50201Action extends ReportBaseAction {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void reportAction() throws Exception {
		
		title = InformationUtil.createTitles("V_",10);

		data = reportCreator.process(genSql(), title);

		if (data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		reportModel.setData(data);
		reportModel.setTitle(title);
		

		reportCreator.initReportData(getJasperInputSteam("T50201.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
        if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50201RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50201RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50201RN"));
		
		writeUsefullMsg(fileName);
		return;
	}

	@Override
	protected String genSql() {
		String whereSql = " ";
		if (!StringUtil.isNull(termNo)) {
			whereSql += " and TERM_ID = '" + termNo + "'";
		}
		if (!StringUtil.isNull(state)) {
			whereSql += " and TERM_STA = '" + state + "' ";
		}
		if (!StringUtil.isNull(mchntId)) {
			whereSql += " AND MCHT_CD = '" + mchntId + "' ";
		}
		if (!StringUtil.isNull(startDate)) {
			whereSql += " AND TERM_STLM_DT >= '" + startDate + "' ";
		}
		if (!StringUtil.isNull(endDate)) {
			whereSql += " AND TERM_STLM_DT <= '" + endDate + "' ";
		}
		if (!StringUtil.isNull(brhId)) {
			whereSql += " AND term_branch in " + InformationUtil.getBrhGroupString(brhId) + " ";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append( "SELECT TERM_ID,MCHT_CD,(CASE TERM_STA WHEN '0' THEN '新增未审核' " +
				"WHEN '1' THEN '启用' WHEN '2' THEN '修改未审核' " +
				"WHEN '3' THEN '停用未审核' WHEN '4' THEN '停用' " +
				"WHEN '5' THEN '恢复未审核' WHEN '6' THEN '注销未审核' " +
				"WHEN '7' THEN '注销' WHEN '8' THEN '新增审核拒绝' " +
				"WHEN '9' THEN '修改审核拒绝' WHEN 'A' THEN '停用审核拒绝' " +
				"WHEN 'B' THEN '恢复审核拒绝' WHEN 'C' THEN '注销审核拒绝' END)," +
				"(CASE TERM_SIGN_STA WHEN '0' THEN '已签到' WHEN '1' THEN '未签到' END)," +
				"TERM_ID_ID,TERM_FACTORY,TERM_MACH_TP,TERM_VER,brh_name,mcht_nm FROM　TBL_TERM_INF_TMP " +
				"left outer join tbl_mcht_base_inf on mcht_no=MCHT_CD " +
				"left outer join tbl_brh_info on brh_id=term_branch where 1=1 ");
		sb.append(whereSql);
		sb.append(" order by mcht_cd");
		return sb.toString();
	}
	// 商户编号
	private String mchntId;
	//终端号
	private String termNo;
	// 终端状态
	private String state;
	// 商户状态
	private String mchntSt;
	// 开始日期
	private String startDate;
	// 结束日期
	private String endDate;
	
	private String brhId;
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
	 * @return the mchntId
	 */
	public String getMchntId() {
		return mchntId;
	}

	/**
	 * @param mchntId the mchntId to set
	 */
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	/**
	 * @return the termNoQ
	 */


	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the termNo
	 */
	public String getTermNo() {
		return termNo;
	}

	/**
	 * @param termNo the termNo to set
	 */
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the mchntSt
	 */
	public String getMchntSt() {
		return mchntSt;
	}

	/**
	 * @param mchntSt the mchntSt to set
	 */
	public void setMchntSt(String mchntSt) {
		this.mchntSt = mchntSt;
	}

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
	

}
