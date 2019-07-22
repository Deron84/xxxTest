/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-8-8       first release
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
package com.huateng.system.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Title: Ajax文件下载类
 * 
 * File: AjaxDownLoad.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class AjaxDownLoad extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AjaxDownLoad.class);
	
	@Override
	public String execute() throws Exception {
		
		BufferedInputStream bis = null;
		
		try {
			
			HttpServletResponse response = ServletActionContext.getResponse();
			
			path = path.replace("\\", "/");
			
			String fileName = path.substring(path.lastIndexOf("/") + 1);
			String fileType = "";
			
			if (path.indexOf(".") != -1) {
				fileType = path.substring(path.lastIndexOf(".") + 1);
			}
			
			
			if ("txt".equalsIgnoreCase(fileType)){
				response.setContentType("text/plain; charset=\"utf-8\"");
			} else if ("xls".equalsIgnoreCase(fileType)){
				response.setContentType("application/vnd.ms-excel; charset=\"utf-8\"");
			} else if ("pdf".equalsIgnoreCase(fileType)){
				response.setContentType("application/pdf; charset=\"utf-8\"");
			} else if ("tar".equalsIgnoreCase(fileType)){
				response.setContentType("application/x-tar; charset=\"utf-8\"");
			} else {
				response.setContentType("application/octet-stream; charset=\"utf-8\"");
			}
			
			ServletOutputStream out = response.getOutputStream();

			String fileNameNew = null ;
			int length = fileName.length();
//			System.out.println("Print:-==>>>>" + length);
			if(length == 19 && (fileName.contains("ACCTRPT"))){
				fileNameNew = "银联间联清算日报表-" + fileName.substring(7,15) + ".csv";
			}else if(length == 21 && (fileName.contains("RETURNRPT"))){
				fileNameNew = "商户退货清算日报表-" + fileName.substring(9, 17) + ".csv";
			}else if(length == 30 && (fileName.contains("tar"))){
				fileNameNew = fileName.substring(0, 8) + "商户入账明细"+ ".tar";
			}else{
				fileNameNew = fileName;
			}
			
			if (fileNameNew.indexOf("RN") == 0) {
				String rn = path.substring(path.indexOf("RN"), path.lastIndexOf("RN") + 2);
				response.setHeader("Content-Disposition", "attachment; filename=" 
						 + URLEncoder.encode(fileNameNew.replaceAll(rn, SysParamUtil.getParam(rn)),"UTF-8"));
			} else {
				response.setHeader("Content-Disposition", "attachment; filename=" 
						+ URLEncoder.encode(fileNameNew,"UTF-8"));
			}
			
			//Win服务器需要这条，Linux注释掉
//			path = new String(path.getBytes("ISO-8859-1"),"UTF-8");
			
//			System.out.println(path);
			File file = new File(path);
			bis = new BufferedInputStream(new FileInputStream(file));
			int len = 0;
			byte[] buf = new byte[512];
			while ((len = bis.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			
			if (file != null && file.exists()) {
				file.delete();//删除临时文件
			}
			if (null != out) {
				out.close();
			}
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null){
				bis.close();
			}
		}
		return super.ERROR;
	}
	
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * 记录系统日志
	 * @param info
	 */
	protected static void log(String info) {
		log.info(info);
	}
}
