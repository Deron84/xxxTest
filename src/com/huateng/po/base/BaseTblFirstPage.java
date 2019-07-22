package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_FIRST_PAGE table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_FIRST_PAGE"
 */

public abstract class BaseTblFirstPage  implements Serializable {

	public static String REF = "TblFirstPage";
	public static String PROP_BRH_ID = "brhId";
	public static String PROP_PPT_MSG = "pptMsg";
	public static String PROP_CRT_OPR = "crtOpr";
	public static String PROP_UPD_OPR = "updOpr";
	public static String PROP_CRT_DATE = "crtDate";
	public static String PROP_UPD_DATE = "updDate";

	// constructors
	public BaseTblFirstPage () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblFirstPage (java.lang.String brhId) {
		this.setBrhId(brhId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String brhId;

	// fields
	private java.lang.String pptMsg;
	private java.lang.String crtOpr;
	private java.lang.String crtDate;
	private java.lang.String updOpr;
	private java.lang.String updDate;
	
	
	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="BRH_ID"
     */
	public java.lang.String getBrhId () {
		return brhId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param brhId the new ID
	 */
	public void setBrhId (java.lang.String brhId) {
		this.brhId = brhId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: PPT_MSG
	 */
	public java.lang.String getPptMsg () {
		return pptMsg;
	}

	/**
	 * Set the value related to the column: PPT_MSG
	 * @param pptMsg the PPT_MSG value
	 */
	public void setPptMsg (java.lang.String pptMsg) {
		this.pptMsg = pptMsg;
	}




	public java.lang.String getCrtOpr() {
		return crtOpr;
	}

	public void setCrtOpr(java.lang.String crtOpr) {
		this.crtOpr = crtOpr;
	}

	public java.lang.String getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(java.lang.String crtDate) {
		this.crtDate = crtDate;
	}

	public java.lang.String getUpdOpr() {
		return updOpr;
	}

	public void setUpdOpr(java.lang.String updOpr) {
		this.updOpr = updOpr;
	}

	public java.lang.String getUpdDate() {
		return updDate;
	}

	public void setUpdDate(java.lang.String updDate) {
		this.updDate = updDate;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblFirstPage)) return false;
		else {
			com.huateng.po.epos.TblFirstPage tblFirstPage = (com.huateng.po.epos.TblFirstPage) obj;
			if (null == this.getBrhId() || null == tblFirstPage.getBrhId()) return false;
			else return (this.getBrhId().equals(tblFirstPage.getBrhId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getBrhId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getBrhId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}