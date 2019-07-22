/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-26       first release
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
package com.huateng.dwr.risk;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.huateng.bo.mchnt.T20201BO;
import com.huateng.po.mchnt.base.BaseTblMchtBaseInf;
import com.huateng.system.util.ContextUtil;

/**
 * Title: 商户黑名单管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-26
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T40202 {
	
	private BaseTblMchtBaseInf mchntInfo;
	private static Logger log = Logger.getLogger(T40202.class);
	
	/**
	 * 查询商户信息
	 * @param mchntNo
	 * @return
	 * 2010-8-26下午11:01:01
	 */
	public String getMchntInfo(String mchntNo,HttpServletRequest request,HttpServletResponse response) {
		String jsonData = null;
		try {
			T20201BO t20201BO = (T20201BO) ContextUtil.getBean("T20201BO");
			mchntInfo = t20201BO.get(mchntNo);			
			if(mchntInfo != null){
				jsonData = JSONArray.fromObject(mchntInfo).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}


	public void setMchntInfo(BaseTblMchtBaseInf mchntInfo) {
		this.mchntInfo = mchntInfo;
	}
	
	
}
