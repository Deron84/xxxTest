package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_PRT_MSG table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_PRT_MSG"
 */

public abstract class BaseTblPrtMsg  implements Serializable {

	public static String REF = "TblPrtMsg";
	public static String PROP_CRT_DATE = "crtDate";
	public static String PROP_UPD_DATE = "updDate";
	public static String PROP_ID = "Id";
	public static String PROP_PRT_MSG = "prtMsg";


	// constructors
	public BaseTblPrtMsg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblPrtMsg (com.huateng.po.epos.TblPrtMsgPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.epos.TblPrtMsgPK id;

	// fields
	private java.lang.String prtMsg;
	private java.lang.String crtDate;
	private java.lang.String updDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.epos.TblPrtMsgPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.epos.TblPrtMsgPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: PRT_MSG
	 */
	public java.lang.String getPrtMsg () {
		return prtMsg;
	}

	/**
	 * Set the value related to the column: PRT_MSG
	 * @param prtMsg the PRT_MSG value
	 */
	public void setPrtMsg (java.lang.String prtMsg) {
		this.prtMsg = prtMsg;
	}



	/**
	 * Return the value associated with the column: CRT_DATE
	 */
	public java.lang.String getCrtDate () {
		return crtDate;
	}

	/**
	 * Set the value related to the column: CRT_DATE
	 * @param crtDate the CRT_DATE value
	 */
	public void setCrtDate (java.lang.String crtDate) {
		this.crtDate = crtDate;
	}



	/**
	 * Return the value associated with the column: UPD_DATE
	 */
	public java.lang.String getUpdDate () {
		return updDate;
	}

	/**
	 * Set the value related to the column: UPD_DATE
	 * @param updDate the UPD_DATE value
	 */
	public void setUpdDate (java.lang.String updDate) {
		this.updDate = updDate;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblPrtMsg)) return false;
		else {
			com.huateng.po.epos.TblPrtMsg tblPrtMsg = (com.huateng.po.epos.TblPrtMsg) obj;
			if (null == this.getId() || null == tblPrtMsg.getId()) return false;
			else return (this.getId().equals(tblPrtMsg.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}