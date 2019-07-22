/* @(#)
 *
 * Project:PAConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2009-8-21       first release
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

import javax.swing.table.AbstractTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;

/**
 * 
 * Title: 报表模版
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-27
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ReportModel extends AbstractTableModel {
	/**报表标题*/
	private String[] title = null;
	/**报表数据集合*/
	private Object[][] data = null;
	
	public ReportModel() {};
	
	/**
	 * 生成报表模版对象
	 * @return
	 * 2010-8-27下午02:49:31
	 */
	public JRTableModelDataSource wrapReportDataSource() {
		if(data == null) {
			throw new IllegalArgumentException("报表数据集不能为null");
		}
		if(title == null) {
			throw new IllegalArgumentException("报表标题信息不能为null");
		}
		return new JRTableModelDataSource(this);
	}
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return title.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return data.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	public String getColumnName(int colum) {
		return title[colum];
	}
	
	public void setData(Object[][] data) {
		this.data = data;
	}

	/**
	 * @return the title
	 */
	public String[] getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String[] title) {
		this.title = title;
	}

	/**
	 * @return the data
	 */
	public Object[][] getData() {
		return data;
	}
}
