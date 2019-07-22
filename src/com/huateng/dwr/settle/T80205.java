/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-9-5       first release
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
package com.huateng.dwr.settle;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.huateng.bo.impl.settle.TblSettleService;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.settle.TblInfileOpt;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:
 * 
 * File: T80205.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T80205 {

	private static Logger log = Logger.getLogger(T80205.class);
	public HashMap<String, String> init(HttpServletRequest request,
			HttpServletResponse response) {

		HashMap<String, String> map = new HashMap<String, String>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String today = sdf.format(new Date());
			Operator operator = (Operator) request.getSession().getAttribute(
					"operator");
			String brhId = operator.getOprBrhId();
			map.put("today", today);
			map.put("brhName", InformationUtil.getBrhName(brhId));
			
			//获取记录信息
			TblSettleService service = (TblSettleService) ContextUtil
				.getBean("TblSettleService");
			TblInfileOpt inf = service.getInf(brhId, today);
			
			if (null != inf) {
				map.put("SETTLE_AMT", inf.getSettleAmt().toString());
				map.put("TXN_AMT", inf.getTxnAmt().toString());
				map.put("SETTLE_FEE1", inf.getSettleFee1().toString());
				map.put("MCHNT_COUNT", inf.getMchntCount());
				
				map.put("SETTLE_AMT_DIS", inf.getSettleAmtDis().toString());
				map.put("TXN_AMT_DIS", inf.getTxnAmtDis().toString());
				map.put("SETTLE_FEE1_DIS", inf.getSettleFee1Dis().toString());
				map.put("MCHNT_COUNT_DIS", inf.getMchntCountDis());
				
				map.put("startDate", inf.getStartDate());
				map.put("endDate", inf.getEndDate());
				
				map.put("succFile", inf.getSuccFile());
				//如果成功文件不为空，可以执行解析结果文件
				if (!StringUtil.isNull(inf.getSuccFile()) || !StringUtil.isNull(inf.getFailFile())) {
					map.put("exec", "F");
				}
				map.put("failFile", inf.getFailFile());
				
				String flag = "";
				if ("1".equals(inf.getStlmFlag2())) {
					flag = "初始化结果文件";
				} else if ("2".equals(inf.getStlmFlag2())) {
					flag = "<font color='green'>解析结果文件成功</font>";
				} else if ("3".equals(inf.getStlmFlag2())) {
					flag = "<font color='red'>解析结果文件失败</font>";
				} else {
					flag = inf.getStlmFlag2();
				}
				map.put("resultFlag", flag);
				
				map.put("resultState", inf.getStlmDsp());
				
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
				log("GET FILE:" + path);
				File file = new File(path);
				if (file.exists()) {
					map.put("makefile", "T");
					map.put("download", "F");
					map.put("pdf", "F");
					map.put("upload", "F");
					map.put("fileState", "<font color='green'>已生成代发文件</font>");
				} else {
					if (inf.getStlmFlag().equals("3")) {
						map.put("makefile", "F");
						map.put("fileState", "<font color='green'>生成失败</font>");
					} else if (inf.getStlmFlag().equals("2")) {
						map.put("makefile", "T");
						map.put("fileState", "<font color='green'>生成文件异常，请检查系统日志</font>");
					} else {
						map.put("makefile", "T");
						map.put("fileState", "<font color='green'>正在生成代发文件</font>");
					}
					map.put("download", "T");
					map.put("pdf", "T");
					map.put("upload", "T");
				}
			} else {
				//读取最后成功生成文件的时间
				String startDate = "";
				String endDate = "";
				endDate = sdf.format(sdf.parse(String.valueOf(Integer
						.parseInt(today) - 1)));
				String sql = "select MAX(DATE_SETTLMT) from TBL_MCHNT_INFILE_DTL "
						+ "where TRIM(BRH_CODE) = '" + brhId.trim() + "' and SETTLE_FLAG in ('C','B','1','2','3')";
				List list = CommonFunction.getCommQueryDAO()
						.findBySQLQuery(sql);
				if (null != list && !list.isEmpty()
						&& !StringUtil.isNull(list.get(0))) {
					startDate = list.get(0).toString();
					try {
						startDate = sdf.format(sdf.parse(String.valueOf(Integer
								.parseInt(startDate) + 1)));
					} catch (Exception e) {
						e.printStackTrace();
						log("清算日期转换失败，读取的原日期为：" + startDate);
					}
				} else {
					startDate = endDate;
				}
				//读取代发信息
				sql = "select NVL(sum(SETTLE_AMT),0.00),NVL(sum(TXN_AMT),0.00),NVL(sum(SETTLE_FEE1),0.00)," +
						"COUNT(DISTINCT MCHT_NO) from TBL_MCHNT_INFILE_DTL ";
				sql +=	"where trim(BRH_CODE) = '" + brhId.trim() + "' and SETTLE_FLAG in ('0','A') " +
						"and DATE_SETTLMT >= '" + startDate + "' and DATE_SETTLMT <= '" + endDate + "' " +
						"and trim(MCHT_NO) in " +
						"(select trim(b.MCHT_NO) from TBL_MCHT_BASE_INF b,TBL_MCHT_SETTLE_INF s " +
						"where b.MCHT_NO = s.MCHT_NO and s.AUTO_STL_FLG = '1' and trim(b.ACQ_INST_ID) = '" + brhId.trim() + "')";
				list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
				if (null != list && !list.isEmpty()) {
					Object[] obj = (Object[]) list.get(0);
					if (!StringUtil.isNull(obj[0])) {
						map.put("SETTLE_AMT", obj[0].toString());
					}
					if (!StringUtil.isNull(obj[1])) {
						map.put("TXN_AMT", obj[1].toString());
					}
					if (!StringUtil.isNull(obj[2])) {
						map.put("SETTLE_FEE1", obj[2].toString());
					}
					if (!StringUtil.isNull(obj[3])) {
						map.put("MCHNT_COUNT", obj[3].toString());
					}
					
				}
				map.put("startDate", startDate);
				map.put("endDate", endDate);
				map.put("makefile", "F");
				map.put("download", "T");
				map.put("pdf", "T");
				map.put("upload", "T");
				map.put("fileState", "<font color='red'>未生成代发文件</font>");
				
				//读取非代发信息
				sql = "select NVL(sum(SETTLE_AMT),0.00),NVL(sum(TXN_AMT),0.00),NVL(sum(SETTLE_FEE1),0.00)," +
						"COUNT(DISTINCT MCHT_NO) from TBL_MCHNT_INFILE_DTL ";
				sql +=	"where trim(BRH_CODE) = '" + brhId.trim() + "' and SETTLE_FLAG in ('0','C') " +
						"and DATE_SETTLMT >= '" + startDate + "' and DATE_SETTLMT <= '" + endDate + "' " +
						"and trim(MCHT_NO) in " +
						"(select trim(b.MCHT_NO) from TBL_MCHT_BASE_INF b,TBL_MCHT_SETTLE_INF s " +
						"where b.MCHT_NO = s.MCHT_NO and s.AUTO_STL_FLG = '0' and trim(b.ACQ_INST_ID) = '" + brhId.trim() + "')";
				list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
				if (null != list && !list.isEmpty()) {
					Object[] obj = (Object[]) list.get(0);
					if (!StringUtil.isNull(obj[0])) {
						map.put("SETTLE_AMT_DIS", obj[0].toString());
					}
					if (!StringUtil.isNull(obj[1])) {
						map.put("TXN_AMT_DIS", obj[1].toString());
					}
					if (!StringUtil.isNull(obj[2])) {
						map.put("SETTLE_FEE1_DIS", obj[2].toString());
					}
					if (!StringUtil.isNull(obj[3])) {
						map.put("MCHNT_COUNT_DIS", obj[3].toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	public HashMap<String, String> getStatus(String brhId, HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			
			if (StringUtil.isNull(brhId)) {
				map.put("fileState", "<font color='red'>获取机构信息异常，请刷新后重试。</font>");
				return map;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String today = sdf.format(new Date());
			map.put("today", today);
			map.put("brhId", brhId);
			map.put("reset", "F");
			//获取记录信息
			TblSettleService service = (TblSettleService) ContextUtil
				.getBean("TblSettleService");
			TblInfileOpt inf = service.getInf(brhId, today);
			
			if (null != inf) {
				map.put("SETTLE_AMT", inf.getSettleAmt().toString());
				map.put("TXN_AMT", inf.getTxnAmt().toString());
				map.put("SETTLE_FEE1", inf.getSettleFee1().toString());
				map.put("MCHNT_COUNT", inf.getMchntCount());
				
				map.put("SETTLE_AMT_DIS", inf.getSettleAmtDis().toString());
				map.put("TXN_AMT_DIS", inf.getTxnAmtDis().toString());
				map.put("SETTLE_FEE1_DIS", inf.getSettleFee1Dis().toString());
				map.put("MCHNT_COUNT_DIS", inf.getMchntCountDis());
				
				map.put("startDate", inf.getStartDate());
				map.put("endDate", inf.getEndDate());
				
				map.put("succFile", inf.getSuccFile());
				map.put("failFile", inf.getFailFile());
				
				String flag = "";
				if ("1".equals(inf.getStlmFlag2())) {
					flag = "初始化结果文件";
				} else if ("2".equals(inf.getStlmFlag2())) {
					flag = "<font color='green'>解析结果文件成功</font>";
				} else if ("3".equals(inf.getStlmFlag2())) {
					flag = "<font color='red'>解析结果文件失败</font>";
				} else {
					flag = inf.getStlmFlag2();
				}
				map.put("resultFlag", flag);
				
				map.put("resultState", inf.getStlmDsp());
				
				String sflag = "";
				if ("1".equals(inf.getStlmFlag())) {
					sflag = "初始化代发文件";
				} else if ("2".equals(inf.getStlmFlag())) {
					sflag = "<font color='green'>生成代发文件成功</font>";
				} else if ("3".equals(inf.getStlmFlag())) {
					sflag = "<font color='red'>生成代发文件失败</font>";
				} else {
					sflag = inf.getStlmFlag();
				}
				map.put("reset", "T");
				map.put("fileState", sflag);
			} else {
				map.put("fileState", "<font color='red'>未找到该机构当天的代发记录</font>");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
		
	}

	protected void log(String info) {
		log.info(info);
	}
}
