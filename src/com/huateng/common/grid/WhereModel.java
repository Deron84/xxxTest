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

/**
 * Title:查询条件
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
public class WhereModel {
	/**字段类型*/
	private String type;
	/**条件比较操作*/
	private String operator;
	/**逻辑连接符*/
	private String logic;
	/**数据库字段名称*/
	private String dataBaseColumn;
	/**查询关联条件名称*/
	private String queryColumn;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	/**
	 * @return the logic
	 */
	public String getLogic() {
		return logic;
	}

	/**
	 * @param logic the logic to set
	 */
	public void setLogic(String logic) {
		this.logic = logic;
	}

	public String getDataBaseColumn() {
		return dataBaseColumn;
	}

	public void setDataBaseColumn(String dataBaseColumn) {
		this.dataBaseColumn = dataBaseColumn;
	}

	public String getQueryColumn() {
		return queryColumn;
	}

	public void setQueryColumn(String queryColumn) {
		this.queryColumn = queryColumn;
	}
}
