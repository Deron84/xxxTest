/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-9-3       first release
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.huateng.common.SysParamConstants;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 文件上传
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-9-3
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FileUploadAction extends BaseAction {

	@Override
	protected String subExecute() throws Exception {
		
		FileInputStream fileInputStream = new FileInputStream(file);
		
		String uploadFileName = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK) + fileName + "." + fileType;
		
		FileOutputStream fileOutputStream = new FileOutputStream(uploadFileName);
		
		byte[] data = new byte[8192];
		
		int len = -1;
		
		while((len = fileInputStream.read(data,0,8192)) != -1) {
			fileOutputStream.write(data, 0, len);
		}
		fileOutputStream.flush();
		fileOutputStream.close();
		fileInputStream.close();
		writeSuccessMsg("文件上传成功");
		return SUCCESS;
	}
	
	// 上传的文件名称
	private String fileName;
	// 文件类型
	private String fileType;
	// 上传的文件
	private File file;

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
}
