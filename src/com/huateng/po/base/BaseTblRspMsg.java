package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_RSP_MSG table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_RSP_MSG"
 */

public abstract class BaseTblRspMsg  implements Serializable {

	public static String REF = "TblRspMsg";
	public static String PROP_ERR_NO = "errNo";
	public static String PROP_CRT_DATE = "crtDate";
	public static String PROP_UPD_DATE = "updDate";
	public static String PROP_ERR_MSG = "errMsg";
	public static String PROP_ID = "Id";


	// constructors
	public BaseTblRspMsg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblRspMsg (com.huateng.po.epos.TblRspMsgPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.epos.TblRspMsgPK id;

	// fields
	private java.lang.String errNo;
	private java.lang.String errMsg;
	private java.lang.String crtDate;
	private java.lang.String updDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.epos.TblRspMsgPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.epos.TblRspMsgPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: ERR_MSG
	 */
	public java.lang.String getErrMsg () {
		return errMsg;
	}

	/**
	 * Set the value related to the column: ERR_MSG
	 * @param errMsg the ERR_MSG value
	 */
	public void setErrMsg (java.lang.String errMsg) {
		this.errMsg = errMsg;
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




	public java.lang.String getErrNo() {
		return errNo;
	}

	public void setErrNo(java.lang.String errNo) {
		this.errNo = errNo;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblRspMsg)) return false;
		else {
			com.huateng.po.epos.TblRspMsg tblRspMsg = (com.huateng.po.epos.TblRspMsg) obj;
			if (null == this.getId() || null == tblRspMsg.getId()) return false;
			else return (this.getId().equals(tblRspMsg.getId()));
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