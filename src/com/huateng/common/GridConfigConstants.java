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
package com.huateng.common;

/**
 * Title:信息列表配置项宏定义
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
public class GridConfigConstants {
	/**根节点*/
	public static final String ROOT_GRIDS = "Grids";
	/**信息列表子节点*/
	public static final String NODE_GRID = "Grid";
	/**信息列表ID*/
	public static final String GRID_ID = "id";
	/**信息组织类型*/
	public static final String GRID_TYPE = "type";
	/**SQL查询组织形式*/
	public static final String TYPE_SQL = "sql";
	/**动态方法组织形式*/
	public static final String TYPE_SYNC = "sync";
	/**查询数据库组织数据*/
	public static final String TYPE_SQLMODE= "SqlMode";
	/**查询数据库SQL*/
	public static final String SQL = "Sql";
	/**查询的列集合*/
	public static final String COLUMNS = "Columns";
	/**查询所用的DAO*/
	public static final String QUERY_DAO = "Dao";
	/**查询条件组合*/
	public static final String WHERES = "Wheres";
	/**查询子条件*/
	public static final String WHERE = "where";
	/**查询子条件数据类型*/
	public static final String WHERE_TYPE = "type";
	/**查询条件数据类型为字符型*/
	public static final String WHERE_TYPE_CHAR = "string";
	/**查询条件数据类型为数字型*/
	public static final String WHERE_TYPE_NUMBER = "int";
	/**查询条件操作符*/
	public static final String WHERE_OPERATOR = "operator";
	/**查询条件逻辑连接符*/
	public static final String WHERE_LOGIC = "logic";
	/**查询条件小于*/
	public static final String WHERE_OPERATOR_LT = "LT";
	/**查询条件小于等于*/
	public static final String WHERE_OPERATOR_LE = "LE";
	/**查询条件大于*/
	public static final String WHERE_OPERATOR_GT = "GT";
	/**查询条件大于等于*/
	public static final String WHERE_OPERATOR_GE = "GE";
	/**查询条件等于*/
	public static final String WHERE_OPERATOR_EQ = "EQ";
	/**查询条件不等于*/
	public static final String WHERE_OPERATOR_NE = "NE";
	/**查询条件模糊匹配*/
	public static final String WHERE_OPERATOR_LIKE = "LIKE";
	/**查询条件集合匹配*/
	public static final String WHERE_OPERATOR_IN = "IN";
	/**查询子条件数据库列名*/
	public static final String WHERE_DATABASE_COLUMN = "DataBaseColumn";
	/**查询子条件客户端映射信息*/
	public static final String WHERE_QUERY_COLUMN = "QueryColumn";
	/**排序组合*/
	public static final String ORDERS = "Orders";
	/**排序方式*/
	public static final String ORDERS_SORT = "sort";
	/**排序字段*/
	public static final String ORDER = "order";
	/**排序字段名称*/
	public static final String ORDER_NAME = "name";
	/**动态方法调用模式*/
	public static final String TYPE_SYNCMODE = "SyncMode";
	/**动态方法名称*/
	public static final String SYNC_METHOD = "Method";
	/**动态方法名VALUE属性*/
	public static final String SYNC_METHOD_VALUE = "value";
}
