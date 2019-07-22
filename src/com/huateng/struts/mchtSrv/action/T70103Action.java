/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-10-18       first release
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
package com.huateng.struts.mchtSrv.action;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.PdfUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-10-18
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T70103Action extends BaseSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;





	public String pdf(){
		
		try {
			
			String path = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK);
			path += selMchtId;
			path += ".pdf";
			
			//因第一次生成文件可能损坏而需要重新生成，故暂不判断是否已生成。
			
			LinkedHashMap<String, List<Object[]>> map = new LinkedHashMap<String, List<Object[]>>();
			Set<String> set = new HashSet<String>();
			//问题和选项
			List<Object[]> list = InformationUtil.getPaperOpts(paperId, map);
			//结果集
			InformationUtil.getAnswers(selMchtId, set);
			PdfUtil.create(mchtId, selMchtId, path, list, map, set);

			return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + path);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("生成PDF问卷失败，请稍后再试！", e);
		}
	}
	
	private String mchtId;
	private String paperId;
	private String selMchtId;
	
	
	
	
	
	/**
	 * @return the mchtId
	 */
	public String getMchtId() {
		return mchtId;
	}

	/**
	 * @param mchtId the mchtId to set
	 */
	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	/**
	 * @return the paperId
	 */
	public String getPaperId() {
		return paperId;
	}

	/**
	 * @param paperId the paperId to set
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	/**
	 * @return the selMchtId
	 */
	public String getSelMchtId() {
		return selMchtId;
	}

	/**
	 * @param selMchtId the selMchtId to set
	 */
	public void setSelMchtId(String selMchtId) {
		this.selMchtId = selMchtId;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}
	
}
