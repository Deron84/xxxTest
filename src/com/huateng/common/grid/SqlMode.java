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
 * Title:SQL组织类型
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
public class SqlMode {
	/**SQL语句*/
	private String sql;
	/**查询DAO*/
	private String dao;

	/**查询条件*/
	private WheresModel wheresModel;
	/**排序字段*/
	private OrdersModel ordersModel;
	
	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return the dao
	 */
	public String getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(String dao) {
		this.dao = dao;
	}

	/**
	 * @return the wheresModel
	 */
	public WheresModel getWheresModel() {
		return wheresModel;
	}

	/**
	 * @param wheresModel the wheresModel to set
	 */
	public void setWheresModel(WheresModel wheresModel) {
		this.wheresModel = wheresModel;
	}

	/**
	 * @return the ordersModel
	 */
	public OrdersModel getOrdersModel() {
		return ordersModel;
	}

	/**
	 * @param ordersModel the ordersModel to set
	 */
	public void setOrdersModel(OrdersModel ordersModel) {
		this.ordersModel = ordersModel;
	}
	
	
}
