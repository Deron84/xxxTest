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
package com.huateng.struts.system.action;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.ReportCreator;
import com.huateng.common.ReportModel;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.JSONBean;
import com.huateng.system.util.SysParamUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Title: 报表基类
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
@SuppressWarnings("serial")
public abstract class ReportBaseAction extends  ActionSupport{
	
	private static Logger log = Logger.getLogger(ReportBaseAction.class);
	/**操作员信息*/
	protected Operator operator;
	/**报表类型*/
	protected String reportType;
	/**报表创建类*/
	protected ReportCreator reportCreator = new ReportCreator();
	/**报表模版类*/
	protected ReportModel reportModel = new ReportModel();
	/**报表参数*/
	protected Map<String, String> parameters = new HashMap<String, String>();
	/**报表数据*/
	protected Object[][] data;
	/**报表标题*/
	protected String[] title;
	/**报表SQL*/
	protected String sql;
	/**导出临时文件*/
	protected String fileName;
	/**报表文件输出流*/
	protected FileOutputStream outputStream;
	/**客户端信息对象*/
	private JSONBean jsonBean = new JSONBean();
	/**下载文件路径*/
	private final static String DOWNLOAD_FILE = "downLoadFile";
	/**下载文件保存名称*/
	private final static String DOWNLOAD_FILE_NAME = "downLoadFileName";
	/**下载文件类型*/
	private final static String DOWNLOAD_FILE_TYPE = "downLoadFileType";
	
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.action.SafeAction#safeExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String execute() {
		
		operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		parameters.put("OPR_ID", operator.getOprId());
		parameters.put("BRH_ID", operator.getOprBrhId());
		parameters.put("PRINT_DATE", CommonFunction.getCurrDate("yyyy-MM-dd"));
	
			
		try{
			reportAction();
			// 开发模式下打印结果
			if(!Boolean.valueOf(System.getProperty(SysParamConstants.PRODUCTION_MODE))) {
//				display(data);
			}
		} catch(Exception e) {
			e.printStackTrace();
			log("生成报表错误：" + e.getMessage());
			try {
				writeNoDataMsg(e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
				log(e1.getMessage());
			}
		}
		return SUCCESS;
	}
	
	protected abstract String genSql();
	
	protected abstract void reportAction() throws Exception;
	
	/**
	 * 获得jasper文件流
	 * @param request
	 * @param path
	 * @return
	 */
	public InputStream getJasperInputSteam(String fileName) {
		return ServletActionContext.getServletContext().
				getResourceAsStream(SysParamUtil.getParam(SysParamConstants.REPORT_MODEL_DIR) + fileName);
	}
	
	/**
	 * 根据传入的日期和偏移量获得偏移后的日期
	 * @param date
	 * @param offsize
	 * @return
	 */
	public String getOffSizeDate(String date,int offsize) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Integer.parseInt(date.substring(0, 4)), 
							  Integer.parseInt(date.substring(4, 6)) - 1, 
							  Integer.parseInt(date.substring(6, 8)));
		calendar.add(Calendar.DATE, offsize);
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String retDate = String.valueOf(calendar.get(Calendar.DATE));
		if(Integer.parseInt(month) < 10) {
			month = "0" + month;
		}
		if(Integer.parseInt(retDate) < 10) {
			retDate = "0" + retDate;
		}
		return year + month + retDate;
	}
	
	/**
	 * 打印结果集
	 * @param data
	 */
	protected void  display(Object[][] data) {
		for(int i = 0,n = data.length; i < n; i++) {
			for(int j = 0,m = data[i].length; j < m; j++) {
				System.out.print(data[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	/**
	 * 将空指针转换为"--"
	 * @param data
	 */
	protected void transNullToLine(Object[][] data) {
		for(int i = 0,n = data.length; i < n; i++) {
			for(int j = 0,m = data[i].length; j < m; j++) {
				if(data[i][j] == null) 
					data[i][j] = "--";
			}
		}
	}
	
	/**
	 * 格式化月份
	 * @param date
	 * @return
	 */
	protected String formatMonth(String date) {
		date = date.trim();
		if(date.length() == 6) {
			return date.substring(0, 4) + "/" + 
				   date.substring(4, 6);
		} else {
			return date;
		}
	}
	
	/**
	 * 向客户端输出信息
	 * @param response
	 * @param msg
	 * @throws IOException
	 * 2010-8-30下午07:45:40
	 */
	protected void writeNoDataMsg(String msg) throws IOException {
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, false);
		jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(jsonBean.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 向客户端输出有效信息
	 * @param response
	 * @param msg
	 * @throws IOException
	 * 2010-8-30下午07:45:40
	 */
	protected void writeUsefullMsg(String msg) throws IOException {
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
		jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(jsonBean.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 调用下载模块
	 * @param downLoadFile		下载的文件路径
	 * @param downLoadFileName	保存文件的名称
	 * @throws IOException
	 * 2010-8-31下午02:20:30
	 */
	protected void callDownload(String downLoadFile,String downLoadFileName) throws IOException {
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
		jsonBean.addJSONElement(DOWNLOAD_FILE, downLoadFile);
		jsonBean.addJSONElement(DOWNLOAD_FILE_NAME, downLoadFileName);
		jsonBean.addJSONElement(DOWNLOAD_FILE_TYPE, reportType);
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(jsonBean.toString());
		printWriter.flush();
		printWriter.close();
	}
	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	protected String formatDate(String date) {
		date = date.trim();
		if(date.length() == 8) {
			return date.substring(0, 4) + "/" + 
				   date.substring(4, 6) + "/" + 
				   date.substring(6, 8);
		} else if(date.length() == 10) {
			return date.substring(0, 2) + "/" + 
				   date.substring(2, 4) + " " + 
				   date.substring(4, 6) + ":" + 
				   date.substring(6, 8) + ":" +
				   date.substring(8, 10);
		} else if(date.length() == 14) {
			return date.substring(0, 4) + "/" + 
			   date.substring(4, 6) + "/" + 
			   date.substring(6, 8) + " " + 
			   date.substring(8, 10) + ":" +
			   date.substring(10, 12)+ ":" + 
			   date.substring(12, 14);
		} else {
			return date;
		}
	}
	
	/**
	 * 格式化查询日期范围
	 * 
	 * @param startDate
	 * @param endDate
	 * @return String
	 */
	public String createDateRange(String startDate, String endDate) {
		String dateRange = null;
		if (StringUtil.isEmpty(startDate)) {
			if (StringUtil.isEmpty(endDate)) {
				// 没有选择开始日期，也没用选择结束日期
				dateRange = "";
			} else {
				// 只选择了结束日期
				dateRange = "0000-00-00_"+ CommonFunction.formate8Date(endDate);
			}
		} else {
			if (StringUtil.isEmpty(endDate)) {
				// 只选择了开始日期，默认已当前日起为结束日期
				dateRange = CommonFunction.formate8Date(startDate)+ "_"+ CommonFunction.getCurrDate("yyyy-MM-dd");
			} else {
				// 选择了开始日期和结束日期，以选择的为准
				dateRange = CommonFunction.formate8Date(startDate) + "_" + CommonFunction.formate8Date(endDate);
			}
		}

		return dateRange;
	}
	
	/**
	 * 判断数据是否为空
	 * @param str
	 * @return
	 * 2010-8-30下午08:12:59
	 */
	protected boolean isNotEmpty(String str) {
		if(str != null && !"".equals(str))
			return true;
		return false;
	}
	
	/**
	 * 包装SQL
	 * @param sql
	 * @return
	 * 2010-8-30下午08:12:56
	 */
	protected String wrapSql(String sql) {
		return "'" + sql + "'";
	}
	/**
	 * 记录系统日志
	 * @param info
	 */
	protected void log(String info) {
		log.info(info);
	}
	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
}
