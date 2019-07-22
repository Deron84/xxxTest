/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-9-8       first release
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
package com.huateng.bo.impl.settle;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.dao.iface.TblInfileOptDAO;
import com.huateng.po.settle.TblInfileOpt;
import com.huateng.po.settle.TblInfileOptPK;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:
 * 
 * File: TblSettleService.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class TblSettleServiceImpl implements TblSettleService {

	public TblInfileOptDAO tblInfileOptDAO;

	/**
	 * @return the tblInfileOptDAO
	 */
	public TblInfileOptDAO getTblInfileOptDAO() {
		return tblInfileOptDAO;
	}

	/**
	 * @param tblInfileOptDAO
	 *            the tblInfileOptDAO to set
	 */
	public void setTblInfileOptDAO(TblInfileOptDAO tblInfileOptDAO) {
		this.tblInfileOptDAO = tblInfileOptDAO;
	}

	public String makeFile(String brhId, String oprId) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		String sql = "";
		
		TblInfileOptPK id = new TblInfileOptPK();
		id.setBrhId(brhId);
		id.setInstDate(today);
		
		
		//判断是否已有记录
		TblInfileOpt existinf = tblInfileOptDAO.get(id);
		if (null != existinf) {
			// 检查是否已生成代发文件
			String path = SysParamUtil
					.getParam(SysParamConstants.FILE_PATH_SETTLE_DOWNLOAD);
			path += today;
			path += "/";
			path += brhId;
			path += "/";
			path += "MCHT_INFILE_";
			path += brhId;
			path += "_";
			path += today;
			path = path.replace("\\", "/");
			File file = new File(path);

			if (file.exists()) {
				throw new SecurityException();
			} else {
				//判断是否为生成失败
				if (existinf.getStlmFlag().equals("3")) {
					sql = "update TBL_MCHNT_INFILE_DTL set SETTLE_FLAG = 'A' where START_DATE = '" + today + "' " +
						"and SETTLE_FLAG != 'C' and TRIM(BRH_CODE) = '" + brhId.trim() + "' " +
						"and DATE_SETTLMT >= '" + existinf.getStartDate() + "' and DATE_SETTLMT <= '" + existinf.getEndDate() + "'";
					CommonFunction.getCommQueryDAO().excute(sql);
					return Constants.SUCCESS_CODE;
				} else {
					throw new SecurityException();
				}
			}
		}
		
		
		TblInfileOpt inf = new TblInfileOpt();
		inf.setId(id);
		
		// 读取最后成功生成文件的时间
		String startDate = "";
		String endDate = "";
		endDate = sdf
				.format(sdf.parse(String.valueOf(Integer.parseInt(today) - 1)));
		sql = "select MAX(DATE_SETTLMT) from TBL_MCHNT_INFILE_DTL "
				+ "where TRIM(BRH_CODE) = '" + brhId.trim()
				+ "' and SETTLE_FLAG in ('B','1','2','3')";
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
			startDate = list.get(0).toString();
			try {
				startDate = sdf.format(sdf.parse(String.valueOf(Integer
						.parseInt(startDate) + 1)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			startDate = endDate;
		}

		// STEP1,插入记录(代发)
		sql = "select NVL(sum(SETTLE_AMT),0.00),NVL(sum(TXN_AMT),0.00),NVL(sum(SETTLE_FEE1),0.00),"
				+ "COUNT(DISTINCT MCHT_NO) from TBL_MCHNT_INFILE_DTL ";
		sql += "where trim(BRH_CODE) = '" + brhId.trim()
				+ "' and SETTLE_FLAG in ('0','C') " + "and DATE_SETTLMT >= '"
				+ startDate + "' and DATE_SETTLMT <= '" + endDate + "' " + 
				"and trim(MCHT_NO) in " +
				"(select trim(b.MCHT_NO) from TBL_MCHT_BASE_INF b,TBL_MCHT_SETTLE_INF s " +
				"where b.MCHT_NO = s.MCHT_NO and s.AUTO_STL_FLG = '1' and trim(b.ACQ_INST_ID) = '" + brhId.trim() + "')";
		list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			Object[] obj = (Object[]) list.get(0);
			inf.setSettleAmt(new BigDecimal(obj[0].toString()));
			inf.setTxnAmt(new BigDecimal(obj[1].toString()));
			inf.setSettleFee1(new BigDecimal(obj[2].toString()));
			inf.setMchntCount(obj[3].toString());
		}
		// STEP2,插入记录(非代发)
		sql = "select NVL(sum(SETTLE_AMT),0.00),NVL(sum(TXN_AMT),0.00),NVL(sum(SETTLE_FEE1),0.00),"
				+ "COUNT(DISTINCT MCHT_NO) from TBL_MCHNT_INFILE_DTL ";
		sql += "where trim(BRH_CODE) = '" + brhId.trim()
				+ "' and SETTLE_FLAG in ('0','A') " + "and DATE_SETTLMT >= '"
				+ startDate + "' and DATE_SETTLMT <= '" + endDate + "' " + 
				"and trim(MCHT_NO) in " +
				"(select trim(b.MCHT_NO) from TBL_MCHT_BASE_INF b,TBL_MCHT_SETTLE_INF s " +
				"where b.MCHT_NO = s.MCHT_NO and s.AUTO_STL_FLG = '0' and trim(b.ACQ_INST_ID) = '" + brhId.trim() + "')";
		list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			Object[] obj = (Object[]) list.get(0);
			inf.setSettleAmtDis(new BigDecimal(obj[0].toString()));
			inf.setTxnAmtDis(new BigDecimal(obj[1].toString()));
			inf.setSettleFee1Dis(new BigDecimal(obj[2].toString()));
			inf.setMchntCountDis(obj[3].toString());
		}
		
		// STEP3,更新商户入账表(代发)
		sql = "update TBL_MCHNT_INFILE_DTL set SETTLE_FLAG = 'A',START_DATE = '"
				+ today + "' ";
		sql += "where trim(BRH_CODE) = '" + brhId.trim()
				+ "' and SETTLE_FLAG in ('0','A') ";
		if (!StringUtil.isNull(startDate)) {
			sql += " and DATE_SETTLMT >= '" + startDate + "' ";
		}
		if (!StringUtil.isNull(endDate)) {
			sql += "and DATE_SETTLMT <= '" + endDate + "' ";
		}
		sql += "and trim(MCHT_NO) in " +
			"(select trim(b.MCHT_NO) from TBL_MCHT_BASE_INF b,TBL_MCHT_SETTLE_INF s " +
			"where b.MCHT_NO = s.MCHT_NO and s.AUTO_STL_FLG = '1' and trim(b.ACQ_INST_ID) = '" + brhId.trim() + "')";
		
		CommonFunction.getCommQueryDAO().excute(sql);
		
		// STEP4,更新商户入账表(非代发)
		sql = "update TBL_MCHNT_INFILE_DTL set SETTLE_FLAG = 'C',START_DATE = '"
				+ today + "' ";
		sql += "where trim(BRH_CODE) = '" + brhId.trim()
				+ "' and SETTLE_FLAG in ('0','C') ";
		if (!StringUtil.isNull(startDate)) {
			sql += " and DATE_SETTLMT >= '" + startDate + "' ";
		}
		if (!StringUtil.isNull(endDate)) {
			sql += "and DATE_SETTLMT <= '" + endDate + "' ";
		}
		sql += "and trim(MCHT_NO) in " +
			"(select trim(b.MCHT_NO) from TBL_MCHT_BASE_INF b,TBL_MCHT_SETTLE_INF s " +
			"where b.MCHT_NO = s.MCHT_NO and s.AUTO_STL_FLG = '0' and trim(b.ACQ_INST_ID) = '" + brhId.trim() + "')";
		
		CommonFunction.getCommQueryDAO().excute(sql);
		
		inf.setStlmFlag("1");
		inf.setOprId1(oprId);
		inf.setRecCreTime(CommonFunction.getCurrentDateTime());
		
		inf.setStartDate(startDate);
		inf.setEndDate(endDate);

		tblInfileOptDAO.save(inf);
		
		return Constants.SUCCESS_CODE;
	}
	
	
	


	public TblInfileOpt getInf(String brhId, String today) throws Exception {
		
		return tblInfileOptDAO.get(new TblInfileOptPK(brhId, today));
		
	}

	public void update(TblInfileOpt inf) throws Exception {
		tblInfileOptDAO.update(inf);
	}

	/* 
	 * 重置商户入账
	 * 
	 * @see com.huateng.bo.impl.settle.TblSettleService#reset(com.huateng.po.settle.TblInfileOpt)
	 */
	public String reset(TblInfileOpt inf, String brhId, String today) throws Exception {
		
		//重置状态
		String sql = "update TBL_MCHNT_INFILE_DTL set SETTLE_FLAG = '0' where START_DATE = '" + today + "' " +
			"and TRIM(BRH_CODE) = '" + brhId.trim() + "' " +
			"and DATE_SETTLMT >= '" + inf.getStartDate() + "' and DATE_SETTLMT <= '" + inf.getEndDate() + "'";
		CommonFunction.getCommQueryDAO().excute(sql);
		
		//删除记录
		tblInfileOptDAO.delete(inf);

		return Constants.SUCCESS_CODE;
	}

}
