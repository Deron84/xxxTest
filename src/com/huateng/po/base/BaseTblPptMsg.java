package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_PPT_MSG table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_PPT_MSG"
 */

public abstract class BaseTblPptMsg  implements Serializable {

	public static String REF = "TblPptMsg";
	public static String PROP_MSG_FMT3 = "MsgFmt3";
	public static String PROP_CRT_DATE = "CrtDate";
	public static String PROP_MSG_FMT2 = "MsgFmt2";
	public static String PROP_UPD_DATE = "UpdDate";
	public static String PROP_MSG_FMT1 = "MsgFmt1";
	public static String PROP_PPT_MSG3 = "PptMsg3";
	public static String PROP_PPT_MSG1 = "PptMsg1";
	public static String PROP_PPT_MSG2 = "PptMsg2";
	public static String PROP_ID = "Id";
	public static String PROP_TMP_ID = "TmpId";


	// constructors
	public BaseTblPptMsg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblPptMsg (com.huateng.po.epos.TblPptMsgPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblPptMsg (
		com.huateng.po.epos.TblPptMsgPK id,
		java.lang.Integer tmpId,
		java.lang.String crtDate,
		java.lang.String updDate) {

		this.setId(id);
		this.setTmpId(tmpId);
		this.setCrtDate(crtDate);
		this.setUpdDate(updDate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.epos.TblPptMsgPK id;

	// fields
	private java.lang.Integer tmpId;
	private java.lang.String msgFmt1;
	private java.lang.String pptMsg1;
	private java.lang.String msgFmt2;
	private java.lang.String pptMsg2;
	private java.lang.String msgFmt3;
	private java.lang.String pptMsg3;
	private java.lang.String crtDate;
	private java.lang.String updDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.epos.TblPptMsgPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.epos.TblPptMsgPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: TMP_ID
	 */
	public java.lang.Integer getTmpId () {
		return tmpId;
	}

	/**
	 * Set the value related to the column: TMP_ID
	 * @param tmpId the TMP_ID value
	 */
	public void setTmpId (java.lang.Integer tmpId) {
		this.tmpId = tmpId;
	}



	/**
	 * Return the value associated with the column: MSG_FMT1
	 */
	public java.lang.String getMsgFmt1 () {
		return msgFmt1;
	}

	/**
	 * Set the value related to the column: MSG_FMT1
	 * @param msgFmt1 the MSG_FMT1 value
	 */
	public void setMsgFmt1 (java.lang.String msgFmt1) {
		this.msgFmt1 = msgFmt1;
	}



	/**
	 * Return the value associated with the column: PPT_MSG1
	 */
	public java.lang.String getPptMsg1 () {
		return pptMsg1;
	}

	/**
	 * Set the value related to the column: PPT_MSG1
	 * @param pptMsg1 the PPT_MSG1 value
	 */
	public void setPptMsg1 (java.lang.String pptMsg1) {
		this.pptMsg1 = pptMsg1;
	}



	/**
	 * Return the value associated with the column: MSG_FMT2
	 */
	public java.lang.String getMsgFmt2 () {
		return msgFmt2;
	}

	/**
	 * Set the value related to the column: MSG_FMT2
	 * @param msgFmt2 the MSG_FMT2 value
	 */
	public void setMsgFmt2 (java.lang.String msgFmt2) {
		this.msgFmt2 = msgFmt2;
	}



	/**
	 * Return the value associated with the column: PPT_MSG2
	 */
	public java.lang.String getPptMsg2 () {
		return pptMsg2;
	}

	/**
	 * Set the value related to the column: PPT_MSG2
	 * @param pptMsg2 the PPT_MSG2 value
	 */
	public void setPptMsg2 (java.lang.String pptMsg2) {
		this.pptMsg2 = pptMsg2;
	}



	/**
	 * Return the value associated with the column: MSG_FMT3
	 */
	public java.lang.String getMsgFmt3 () {
		return msgFmt3;
	}

	/**
	 * Set the value related to the column: MSG_FMT3
	 * @param msgFmt3 the MSG_FMT3 value
	 */
	public void setMsgFmt3 (java.lang.String msgFmt3) {
		this.msgFmt3 = msgFmt3;
	}



	/**
	 * Return the value associated with the column: PPT_MSG3
	 */
	public java.lang.String getPptMsg3 () {
		return pptMsg3;
	}

	/**
	 * Set the value related to the column: PPT_MSG3
	 * @param pptMsg3 the PPT_MSG3 value
	 */
	public void setPptMsg3 (java.lang.String pptMsg3) {
		this.pptMsg3 = pptMsg3;
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
		if (!(obj instanceof com.huateng.po.epos.TblPptMsg)) return false;
		else {
			com.huateng.po.epos.TblPptMsg tblPptMsg = (com.huateng.po.epos.TblPptMsg) obj;
			if (null == this.getId() || null == tblPptMsg.getId()) return false;
			else return (this.getId().equals(tblPptMsg.getId()));
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