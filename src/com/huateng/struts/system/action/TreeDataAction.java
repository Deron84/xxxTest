/* @(#)
 *
 * Project:EXTJSConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-3-9       first release
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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;

import com.huateng.common.Constants;

/**
 * Title:初始化菜单树
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class TreeDataAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private String id;
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		LinkedHashMap<String, Object> menuMap = (LinkedHashMap<String, Object>) 
							getSessionAttribute(Constants.TREE_MENU_MAP);
		List<Object> menuList = new LinkedList<Object>();
		//页面初始化
		if(Constants.TREE_INIT_FLG.equals(id)) {
			Iterator<String> iter = menuMap.keySet().iterator();
			while(iter.hasNext()) {
				String key = iter.next();
				menuList.add(menuMap.get(key));
				break;
			}
			writeMessage(JSONArray.fromObject(menuList).toString());
			return SUCCESS;
		}
		menuList.add(menuMap.get(id));
		writeMessage(JSONArray.fromObject(menuList).toString());
		return SUCCESS;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
}
