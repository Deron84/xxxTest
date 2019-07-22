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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.bo.impl.settle.TblSettleService;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.settle.TblInfileOpt;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SocketConnect;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 商户入账文件下载
 * 
 * File: T80205.java
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
public class T80205Action extends BaseSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected TblSettleService service = (TblSettleService) ContextUtil
		.getBean("TblSettleService");
	
	/**
	 * 下载商户入账文件
	 * 
	 * @return
	 * 2011-8-2下午02:23:26
	 */
	public String download(){
		
		try {
			String brhId = getOperator().getOprBrhId();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String today = sdf.format(new Date());
			
			String path = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_DOWNLOAD);

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
			File down = new File(path);
			if (down.exists()) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + path);
			} else {
				return returnService("您所请求的商户代发文件不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}
	
	/**
	 * 生成文件
	 * 
	 * @return
	 * 2011-8-2下午02:23:22
	 */
	public String makefile(){
		
		try {
			service.makeFile(getOperator().getOprBrhId(), getOperator().getOprId());
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("初始化生成代发文件处理失败，请稍后重试！");
		}
		try {
//			交易码
			StringBuffer req = new StringBuffer("8211062");
			req.append(CommonFunction.fillString(CommonFunction.getCurrentDate() + getOperator().getOprBrhId(), ' ', 4, true));
			String len = Integer.toHexString(req.toString().length());
			len = new String(CommonFunction.hexStringToByte(len));
			len = new String(CommonFunction.hexStringToByte("00"))+len;
			
			SocketConnect socket = null;
			try {
				log.info("报文:[" + len + req.toString() + "]");
				socket = new SocketConnect(len + req.toString());
				socket.run("GBK");
				String resp = socket.getRsp();
				
				if(resp.substring(4,6).equals("00"))
					return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "生成商户代发文件成功，请稍后下载！");
				else
					return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "生成商户代发文件失败，返回码:" + resp.substring(4,6));
			} catch (UnknownHostException e) {
				log.error(e);
			} catch (IOException e) {
				log.error(e);
			} catch (Exception e) {
				log.error(e);
			} finally {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnService("生成代发文件处理失败，请稍后重试！");
	}
	
	/**
	 * 商户入账结果上传
	 * 
	 * @return
	 * 2011-8-2下午02:23:30
	 */
	public String upload(){
		
		FileInputStream is = null;
		DataOutputStream out = null;
		DataInputStream dis = null;
		
		String brhId = getOperator().getOprBrhId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		String errflag = "";
		try {
			
			String fileName = "";
			int fileNameIndex = 0;
			
			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_UPLOAD);
			
			basePath += today;
			basePath += "/";
			basePath += brhId;
			basePath += "/";
			basePath = basePath.replace("\\", "/");
			
			for(File file : files) {
				is = new FileInputStream(file);
				fileName = filesFileName.get(fileNameIndex);
				
				if(!fileName.startsWith("DDSS") && !fileName.startsWith("DDSF")){
					errflag = "存在文件名不为DDSS或DDSF开头的文件";
				}
				
				log("WRITE FILE:" + basePath + fileName);
				
				File writeFile = new File(basePath + fileName);
				
				if (!writeFile.getParentFile().exists()) {
					writeFile.getParentFile().mkdirs();
				}
				if (writeFile.exists()) {
					writeFile.delete();
				}
				try {
					boolean res = writeFile.setReadable(true, false);
					log("writeFile.setReadable(true, false):" + res);
					res = writeFile.setWritable(true, false);
					log("writeFile.setReadable(true, false):" + res);
				} catch (Exception e) {
					e.printStackTrace();
				}
				dis = new DataInputStream(is);
				out = new DataOutputStream(new FileOutputStream(writeFile));

				int temp;
				while((temp = dis.read()) != -1){
					out.write(temp);
				}
				out.flush();
				
				try {
					Runtime.getRuntime().exec("chmod 777 " + basePath + fileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				TblInfileOpt inf = service.getInf(brhId, today);
				if (null != inf) {
					if (fileName.startsWith("DDSS")) {
						inf.setSuccFile(fileName);
					} else if (fileName.startsWith("DDSF")) {
						inf.setFailFile(fileName);
					}
					service.update(inf);
				} else {
					errflag = "没找到原始的代发操作记录，请稍后重试！";
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return returnService("写入文件失败(没有权限将文件写入指定目录)！", e);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("上传文件失败！", e);
		} finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != is) {
					is.close();
				}
				if (null != dis) {
					dis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!StringUtil.isNull(errflag)) {
			return returnService(errflag);
		} else {
			return returnService(Constants.SUCCESS_CODE);
		}
	}
	
	
	public String exec(){
		
		try {
			
			TblInfileOpt inf = service.getInf(getOperator().getOprBrhId(), CommonFunction.getCurrentDate());
			if (null != inf) {
				inf.setOprId2(getOperator().getOprId());
				inf.setRecUpdTime(CommonFunction.getCurrentDateTime());
				service.update(inf);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
//			交易码
			StringBuffer req = new StringBuffer("8221062");
			req.append(CommonFunction.fillString(CommonFunction.getCurrentDate() + getOperator().getOprBrhId(), ' ', 62, true));
			String len = Integer.toHexString(req.toString().length());
			len = new String(CommonFunction.hexStringToByte(len));
			len = new String(CommonFunction.hexStringToByte("00"))+len;
			
			SocketConnect socket = null;
			try {
				log.info("报文:[" + len + req.toString() + "]");
				socket = new SocketConnect(len + req.toString());
				socket.run("GBK");
				String resp = socket.getRsp();
				
				if(resp.substring(4,6).equals("00"))
					return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "解析代发文件结果处理成功，请稍后查看状态！");
				else
					return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "解析代发文件结果处理失败，返回码:" + resp.substring(4,6));
			} catch (UnknownHostException e) {
				log.error(e);
			} catch (IOException e) {
				log.error(e);
			} catch (Exception e) {
				log.error(e);
			} finally {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnService("解析代发文件结果处理失败，请稍后重试！");
	}
	
	
	/**
	 * 重置商户入账
	 * 
	 * @return
	 * 2011-11-16上午10:45:24
	 */
	public String reset(){
		
		try {
			
			//判断柜员是否为总行柜员
			String oprBrh = getOperator().getOprBrhId();
			if (!"9900".equals(oprBrh)) {
				return returnService("重置商户入账只能由总行柜员操作！");
			}
			
			//判断是否有该机构的代发记录
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String today = sdf.format(new Date());
			TblSettleService service = (TblSettleService) ContextUtil
				.getBean("TblSettleService");
			TblInfileOpt inf = service.getInf(brhId, today);
			
			if (null == inf) {
				return returnService("未找到该机构当天的代发记录,请刷新后再试！");
			}
			
			rspCode = service.reset(inf, brhId, today);

			return returnService(rspCode);
			
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("重置状态发生异常，请稍后再试。", e);
		}
		
		
	}
	
	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}
	
	private List<File> files;
	
	private List<String> filesFileName;
	
	private String brhId;

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<String> getFilesFileName() {
		return filesFileName;
	}

	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
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

	private static Logger log = Logger.getLogger(T80205Action.class);
	
}
