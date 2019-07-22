/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-31       first release
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
package com.huateng.struts.settle.action;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.bo.impl.settle.TblSettleService;
import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtSettleInf;
import com.huateng.po.settle.TblInfileOpt;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 生成商户手动代发凭证
 * 
 * File: T80205S1.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-31
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T80205S1Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;
	
	protected TblSettleService service = (TblSettleService) ContextUtil
		.getBean("TblSettleService");
	protected TblMchntService mchntSrv = (TblMchntService) ContextUtil
		.getBean("TblMchntService");

	@Override
	protected void reportAction() throws Exception {
		
		String brhId = operator.getOprBrhId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		
		TblInfileOpt inf = service.getInf(brhId, today);
		
		if (null == inf) {
			writeNoDataMsg("没有找到该机构的代发记录信息，请稍后重试!");
			return;
		}
		
		
		reportType = "PDF";
		
		title = InformationUtil.createTitles("V_", 13);
		DecimalFormat df = new DecimalFormat("#");

		String settleAcct = InformationUtil.getSettleAcct(brhId);
		
		Object[][] data;
		
		String sql = "select MCHT_NO,sum(SETTLE_AMT) FROM TBL_MCHNT_INFILE_DTL " +
			"WHERE DATE_SETTLMT >= '" + inf.getStartDate() + "' " +
			"AND DATE_SETTLMT <= '" + inf.getEndDate() + "' " +
			"AND START_DATE = '" + today + "' AND trim(SETTLE_FLAG) = 'C' " +
			"AND trim(BRH_CODE) = '" + brhId.trim() + "' GROUP BY MCHT_NO ";
		
		
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			//重新组装数据
			data = new Object[list.size()][title.length];
			Iterator<Object[]> it = list.iterator();
			int i = 0;
			while (it.hasNext()) {
				Object[] obj = it.next();
				data[i][0] = "POS收单清算";
				data[i][1] = obj[0];//商户编号
				TblMchtBaseInf base = mchntSrv.getBaseInf(obj[0].toString());
				TblMchtSettleInf settle = mchntSrv.getSettleInf(obj[0].toString());
				if (null == base || null == settle) {
					writeNoDataMsg("没有找到代发商户的信息[编号：" + obj[0] + "]!");
					return;
				}
				data[i][2] = base.getMchtNm();//商户名称
				data[i][3] = settleAcct;//付款账号
				data[i][4] = settle.getSettleAcct().substring(1);//商户账号
				data[i][5] = settle.getSettleBankNm();//开户行
				data[i][6] = settle.getSettleBankNo();//开户行编号
				data[i][7] = CommonFunction.transMoney(Double.parseDouble(obj[1].toString()));
				data[i][8] = "￥" + CommonFunction.insertString(String.valueOf(df.format(Double.parseDouble(obj[1].toString()) * 100)), " ");
				data[i][9] = inf.getStartDate() + " - " + inf.getEndDate() + " POS清算资金";
				data[i][10] = today.substring(0, 4);
				data[i][11] = today.substring(4, 6);
				data[i][12] = today.substring(6);
				i++;
			}
			
			
			
		} else {
			writeNoDataMsg("没有找到该机构的手动代发信息，请稍后重试!");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
				
		reportCreator.initReportData(getJasperInputSteam("T80205.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80205RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80205RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80205RN"));
		
		writeUsefullMsg(fileName);
		System.out.println("finish");
		return;
	}
	
	@Override
	protected String genSql() {
		return sql;
	}

	
}
