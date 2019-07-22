package com.huateng.struts.settle.action;


import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.dao.common.SqlDao;
import com.huateng.ftp.Download;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T80200Action extends BaseAction{
	
	SqlDao sqlDao = (SqlDao)ContextUtil.getBean("sqlDao");
	
	private String brhId;
	
	private String HmchntId;
	
	private String reportName;
	
	private String date;
	
	@Override
	protected String subExecute() throws Exception {
		if ("down".equals(method)) {
			return down();
		}
		return rspCode;
	}

	private String down() throws Exception {
		String filename = "";
		String downUrl =  SysParamUtil.getParam("TEMP_FILE_DISK");
		String fileUrl = SysParamUtil.getParam("ftpPath") + date + "/";
		
		if("000".equals(reportName)){
			filename = "ACCTRPT" + date + ".csv";
		}else if("001".equals(reportName)){
			filename = "RETURNRPT" + date + ".csv";
		}
		
		log("GET FILE:" + fileUrl + filename);
		rspCode=Download.FTPDownLoad(fileUrl,filename,downUrl);
		
		return rspCode;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getHmchntId() {
		return HmchntId;
	}

	public void setHmchntId(String hmchntId) {
		HmchntId = hmchntId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
