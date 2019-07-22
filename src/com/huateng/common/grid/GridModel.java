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
 * Title:列表组织类型
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
public class GridModel {
	/**编号*/
	private String id;
	/**类型*/
	private String type;
	/**界面映射列名*/
	private String columns;
	/**SQL类型*/
	private SqlMode sqlMode;
	/**动态方法类型*/
	private SyncMode syncMode;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}

	public SqlMode getSqlMode() {
		return sqlMode;
	}

	public void setSqlMode(SqlMode sqlMode) {
		this.sqlMode = sqlMode;
	}

	public SyncMode getSyncMode() {
		return syncMode;
	}

	public void setSyncMode(SyncMode syncMode) {
		this.syncMode = syncMode;
	}
}
