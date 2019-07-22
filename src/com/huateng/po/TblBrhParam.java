package com.huateng.po;

import java.io.Serializable;

/**
 * 机构参数信息
 * 
 * @author zhangkai
 *
 */
public class TblBrhParam implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String REF = "TblBrhParam";
	public static String BRH_ID = "BrhId";
	public static String STAT_DATE = "StatDate";
	public static Integer STD_TOALCOUNTS = 0;
	public static String STD_TOALMONEY = "StdToalmoney";
	public static String STD_CONDITION = "StdCondition";
	public static String RESV_FIELD1 = "ResvField1";
	public static String RESV_FIELD2 = "ResvField2";

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String statDate;
	private java.lang.Integer stdToalcounts;
	private java.lang.String stdToalmoney;
	private java.lang.String stdCondition;
	private java.lang.String resvField1;
	private java.lang.String resvField2;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	public java.lang.String getStatDate() {
		return statDate;
	}

	public void setStatDate(java.lang.String statDate) {
		this.statDate = statDate;
	}

	public java.lang.Integer getStdToalcounts() {
		return stdToalcounts;
	}

	public void setStdToalcounts(java.lang.Integer stdToalcounts) {
		this.stdToalcounts = stdToalcounts;
	}

	public java.lang.String getStdToalmoney() {
		return stdToalmoney;
	}

	public void setStdToalmoney(java.lang.String stdToalmoney) {
		this.stdToalmoney = stdToalmoney;
	}

	public java.lang.String getStdCondition() {
		return stdCondition;
	}

	public void setStdCondition(java.lang.String stdCondition) {
		this.stdCondition = stdCondition;
	}

	public java.lang.String getResvField1() {
		return resvField1;
	}

	public void setResvField1(java.lang.String resvField1) {
		this.resvField1 = resvField1;
	}

	public java.lang.String getResvField2() {
		return resvField2;
	}

	public void setResvField2(java.lang.String resvField2) {
		this.resvField2 = resvField2;
	}

	/**
	 * 
	 */
	public TblBrhParam() {
		super();
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.huateng.po.TblBrhParam))
			return false;
		else {
			com.huateng.po.TblBrhParam tblBrhParam = (com.huateng.po.TblBrhParam) obj;
			if (null == this.getId() || null == tblBrhParam.getId())
				return false;
			else
				return (this.getId().equals(tblBrhParam.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}

}