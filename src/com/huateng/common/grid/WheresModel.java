/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-6-5       first release
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
package com.huateng.common.grid;

import java.util.List;

/**
 * Title:查询条件组合
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class WheresModel {
	/**查询条件集合*/
	private List<WhereModel> whereModelList;

	public List<WhereModel> getWhereModelList() {
		return whereModelList;
	}

	public void setWhereModelList(List<WhereModel> whereModelList) {
		this.whereModelList = whereModelList;
	}

	
}
