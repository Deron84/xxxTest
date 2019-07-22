package com.sdses.po;

import java.io.Serializable;



public class TblTxnInfo implements Serializable {
	public static String REF = "TblTxnInfo";
	public static String PROP_IP_ADDR = "IpAddr";
	public static String PROP_TXN_DATE = "TxnDate";
	public static String PROP_TXN_CODE = "TxnCode";
	public static String PROP_TXN_TIME = "TxnTime";
	public static String PROP_ORG_CODE = "OrgCode";
	public static String PROP_OPR_ID = "OprId";
	public static String PROP_ERR_MSG = "ErrMsg";
	public static String PROP_ID = "Id";
	public static String PROP_TXN_NAME = "TxnName";
	public static String PROP_SUB_TXN_CODE = "SubTxnCode";
	public static String PROP_TXN_STA = "TxnSta";


	// constructors
	public TblTxnInfo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblTxnInfo (TblTxnInfoPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private TblTxnInfoPK id;

	// fields
	private java.lang.String txnDate;
	private java.lang.String txnTime;
	private java.lang.String txnCode;
	private java.lang.String subTxnCode;
	private java.lang.String oprId;
	private java.lang.String orgCode;
	private java.lang.String ipAddr;
	private java.lang.String txnName;
	private java.lang.String txnSta;
	private java.lang.String errMsg;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public TblTxnInfoPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (TblTxnInfoPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: TXN_DATE
	 */
	public java.lang.String getTxnDate () {
		return txnDate;
	}

	/**
	 * Set the value related to the column: TXN_DATE
	 * @param txnDate the TXN_DATE value
	 */
	public void setTxnDate (java.lang.String txnDate) {
		this.txnDate = txnDate;
	}



	/**
	 * Return the value associated with the column: TXN_TIME
	 */
	public java.lang.String getTxnTime () {
		return txnTime;
	}

	/**
	 * Set the value related to the column: TXN_TIME
	 * @param txnTime the TXN_TIME value
	 */
	public void setTxnTime (java.lang.String txnTime) {
		this.txnTime = txnTime;
	}



	/**
	 * Return the value associated with the column: TXN_CODE
	 */
	public java.lang.String getTxnCode () {
		return txnCode;
	}

	/**
	 * Set the value related to the column: TXN_CODE
	 * @param txnCode the TXN_CODE value
	 */
	public void setTxnCode (java.lang.String txnCode) {
		this.txnCode = txnCode;
	}



	/**
	 * Return the value associated with the column: SUB_TXN_CODE
	 */
	public java.lang.String getSubTxnCode () {
		return subTxnCode;
	}

	/**
	 * Set the value related to the column: SUB_TXN_CODE
	 * @param subTxnCode the SUB_TXN_CODE value
	 */
	public void setSubTxnCode (java.lang.String subTxnCode) {
		this.subTxnCode = subTxnCode;
	}



	/**
	 * Return the value associated with the column: OPR_ID
	 */
	public java.lang.String getOprId () {
		return oprId;
	}

	/**
	 * Set the value related to the column: OPR_ID
	 * @param oprId the OPR_ID value
	 */
	public void setOprId (java.lang.String oprId) {
		this.oprId = oprId;
	}



	/**
	 * Return the value associated with the column: ORG_CODE
	 */
	public java.lang.String getOrgCode () {
		return orgCode;
	}

	/**
	 * Set the value related to the column: ORG_CODE
	 * @param orgCode the ORG_CODE value
	 */
	public void setOrgCode (java.lang.String orgCode) {
		this.orgCode = orgCode;
	}



	/**
	 * Return the value associated with the column: IP_ADDR
	 */
	public java.lang.String getIpAddr () {
		return ipAddr;
	}

	/**
	 * Set the value related to the column: IP_ADDR
	 * @param ipAddr the IP_ADDR value
	 */
	public void setIpAddr (java.lang.String ipAddr) {
		this.ipAddr = ipAddr;
	}



	/**
	 * Return the value associated with the column: TXN_NAME
	 */
	public java.lang.String getTxnName () {
		return txnName;
	}

	/**
	 * Set the value related to the column: TXN_NAME
	 * @param txnName the TXN_NAME value
	 */
	public void setTxnName (java.lang.String txnName) {
		this.txnName = txnName;
	}



	/**
	 * Return the value associated with the column: TXN_STA
	 */
	public java.lang.String getTxnSta () {
		return txnSta;
	}

	/**
	 * Set the value related to the column: TXN_STA
	 * @param txnSta the TXN_STA value
	 */
	public void setTxnSta (java.lang.String txnSta) {
		this.txnSta = txnSta;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblTxnInfo)) return false;
		else {
			TblTxnInfo tblTxnInfo = (TblTxnInfo) obj;
			if (null == this.getId() || null == tblTxnInfo.getId()) return false;
			else return (this.getId().equals(tblTxnInfo.getId()));
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