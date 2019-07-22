/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-6-4       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2008 Huateng Software, Inc. All rights reserved.
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
package com.sdses.struts.system.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.common.grid.GridConfigUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:获得GridPanel数据
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-4
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class GridPanelStoreAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**数据仓库编号*/
	private String storeId;
	private String start;
	
	public String execute() {
		
		if(!Boolean.valueOf(SysParamUtil.getParam(SysParamConstants.PRODUCTION_MODE))) {
			try {
				GridConfigUtil.initGirdConfig(ServletActionContext.getServletContext());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			
			if (StringUtil.isNull(start)) {
				start = "0";
			}
			String jsonData = GridConfigUtil.getGridData(storeId, Integer.parseInt(start), request);
			writeMessage(jsonData);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		return SUCCESS;
	}
}
