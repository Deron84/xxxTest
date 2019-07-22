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
public class T50200Action extends ReportBaseAction {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void reportAction() throws Exception {
		
		title = InformationUtil.createTitles("V_", 17);

		data = reportCreator.process(genSql(), title);

		if (data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		parameters.put("BRH_NAME", InformationUtil.getBrhName(brhId));  
		reportModel.setData(data);
		reportModel.setTitle(title);

		reportCreator.initReportData(getJasperInputSteam("T50200.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50200RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50200RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50200RN"));
		
		writeUsefullMsg(fileName);
		return;
	}

	@Override
	protected String genSql() {
		String whereSql = " ";
		if (!StringUtil.isNull(mchtGrp)) {
			whereSql += " and MCHT_GRP = '" + mchtGrp + "'";
		}
		if (!StringUtil.isNull(brhId)) {
			whereSql += " and ACQ_INST_ID in " + InformationUtil.getBrhGroupString(brhId) + " ";;
		}
		if (!StringUtil.isNull(mchtNo)) {
			whereSql += " AND MCHT_NO = '" + mchtNo + "' ";
		}
		if (!StringUtil.isNull(mchntSt)) {
			whereSql += " and MCHT_STATUS = '" + mchntSt + "' ";
		}
		if (!StringUtil.isNull(startDate)) {
			whereSql += " AND APPLY_DATE >= '" + startDate + "' ";
		}
		if (!StringUtil.isNull(endDate)) {
			whereSql += " AND APPLY_DATE <= '" + endDate + "' ";
		}
		if (!StringUtil.isNull(operNo)) {
			whereSql += " AND TRIM(OPER_NO) = '" + operNo.trim() + "' ";
		}
		if (!StringUtil.isNull(operNm)) {
			whereSql += " AND TRIM(OPER_NM) = '" + operNm.trim() + "' ";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append( " SELECT A.MCHT_NO,trim(MCHT_NM)," +
				"(case MCHT_GRP when '01' then '宾馆娱乐类' when '02' then '房产批发类' " +
				"when '03' then '超市加油类' when '04' then '医院学校类' when '05' then '一般商户类' " +
				"when '06' then '新兴行业类' when '07' then '县乡优惠' when '08' then '财务POS商户' " +
				"when '09' then '本行签约POS' end),LICENCE_NO," +
				"APPLY_DATE, " +
				"(case MCHT_STATUS when '0' then '正常' when '1' then '添加待审核' " +
				"when '3' then '修改待审核' when '5' then '停用待审核' when '6' then '停用' else '恢复待审核' end)," +
				"nvl(B.TERM_COUNT,0),rtrim(MANAGER),rtrim(CONTACT),rtrim(COMM_TEL)," +
				"OPER_NO,OPER_NM,RTRIM(D.DISC_NM)," +
				"(case C.SETTLE_TYPE  when '0' then 'T+0' when '1' then 'T+1' when 'N' then '不适用' else C.SETTLE_TYPE end)," +
				"(case substr(TRIM(C.SETTLE_ACCT),1,1)  when 'A' then '本行对公账户' when 'P' then '本行对私或单位卡' " +
				"when 'O' then '他行对公账户' when 'S' then '他行对私账户' else C.SETTLE_ACCT end),RTRIM(SETTLE_ACCT_NM)," +
				"substr(TRIM(C.SETTLE_ACCT),2) FROM " +
				"(SELECT * FROM TBL_MCHT_BASE_INF_TMP) A left outer join (select MCHT_CD,count(1) " +
				"AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B ON (A.MCHT_NO = B.MCHT_CD) " +
				"left outer join TBL_MCHT_SETTLE_INF C ON (A.MCHT_NO = C.MCHT_NO) " +
				"left outer join TBL_INF_DISC_CD D ON (TRIM(C.FEE_RATE) = TRIM(D.DISC_CD)) " +
				" where 1=1 ");
		sb.append(whereSql);
		sb.append(" ORDER BY MCHT_NO");
		return sb.toString();
	}

	// 商户编号
	private String mchtNo;
	// 商户MCC
	private String mchtGrp;
	// 所属机构
	private String brhId;
	// 商户状态
	private String mchntSt;
	// 开始日期
	private String startDate;
	// 结束日期
	private String endDate;
	
	private String operNo;
	
	private String operNm;

	/**
	 * @return the mcc
	 */

	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	
	
	/**
	 * @return the mchtNo
	 */
	public String getMchtNo() {
		return mchtNo;
	}

	/**
	 * @param mchtNo the mchtNo to set
	 */
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	/**
	 * @return the mchtGrp
	 */
	public String getMchtGrp() {
		return mchtGrp;
	}

	/**
	 * @param mchtGrp the mchtGrp to set
	 */
	public void setMchtGrp(String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	/**
	 * @param brhId
	 *            the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	/**
	 * @return the mchntSt
	 */
	public String getMchntSt() {
		return mchntSt;
	}

	/**
	 * @param mchntSt
	 *            the mchntSt to set
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
	 * @param startDate
	 *            the startDate to set
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
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the operNo
	 */
	public String getOperNo() {
		return operNo;
	}

	/**
	 * @param operNo the operNo to set
	 */
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	/**
	 * @return the operNm
	 */
	public String getOperNm() {
		return operNm;
	}

	/**
	 * @param operNm the operNm to set
	 */
	public void setOperNm(String operNm) {
		this.operNm = operNm;
	}

}
