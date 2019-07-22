package com.huateng.po;

import java.io.Serializable;



public class TblTermTmkLog implements Serializable {
	public static String REF = "TblTermTmkLog";
	public static String PROP_REQ_OPR = "ReqOpr";
	public static String PROP_TERM_BRANCH = "TermBranch";
	public static String PROP_REC_UPD_OPR = "RecUpdOpr";
	public static String PROP_STATE = "State";
	public static String PROP_MISC = "Misc";
	public static String PROP_CHK_OPR = "ChkOpr";
	public static String PROP_MCHN_NO = "MchnNo";
	public static String PROP_CHK_DATE = "ChkDate";
	public static String PROP_REQ_DATE = "ReqDate";
	public static String PROP_ID = "Id";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	//zjx
	public static String  PRT_OPR="prtOpr";
	public static String  PRT_DATE="prtDate";
	public static String  PRT_COUNT="prtCount";

	// constructors
	public TblTermTmkLog () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblTermTmkLog (com.huateng.po.TblTermTmkLogPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.TblTermTmkLogPK id;

	// fields
	private java.lang.String mchnNo;
	private java.lang.String termBranch;
	private java.lang.String state;
	private java.lang.String reqOpr;
	private java.lang.String reqDate;
	private java.lang.String chkOpr;
	private java.lang.String chkDate;
	private java.lang.String misc;
	private java.lang.String recUpdOpr;
	private java.lang.String recUpdTs;
	private java.lang.String prtOpr;
	private java.lang.String prtDate;
	private java.lang.Integer prtCount;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.TblTermTmkLogPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.TblTermTmkLogPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: MCHN_NO
	 */
	public java.lang.String getMchnNo () {
		return mchnNo;
	}

	/**
	 * Set the value related to the column: MCHN_NO
	 * @param mchnNo the MCHN_NO value
	 */
	public void setMchnNo (java.lang.String mchnNo) {
		this.mchnNo = mchnNo;
	}



	/**
	 * Return the value associated with the column: TERM_BRANCH
	 */
	public java.lang.String getTermBranch () {
		return termBranch;
	}

	/**
	 * Set the value related to the column: TERM_BRANCH
	 * @param termBranch the TERM_BRANCH value
	 */
	public void setTermBranch (java.lang.String termBranch) {
		this.termBranch = termBranch;
	}



	/**
	 * Return the value associated with the column: STATE
	 */
	public java.lang.String getState () {
		return state;
	}

	/**
	 * Set the value related to the column: STATE
	 * @param state the STATE value
	 */
	public void setState (java.lang.String state) {
		this.state = state;
	}



	/**
	 * Return the value associated with the column: REQ_OPR
	 */
	public java.lang.String getReqOpr () {
		return reqOpr;
	}

	/**
	 * Set the value related to the column: REQ_OPR
	 * @param reqOpr the REQ_OPR value
	 */
	public void setReqOpr (java.lang.String reqOpr) {
		this.reqOpr = reqOpr;
	}



	/**
	 * Return the value associated with the column: REQ_DATE
	 */
	public java.lang.String getReqDate () {
		return reqDate;
	}

	/**
	 * Set the value related to the column: REQ_DATE
	 * @param reqDate the REQ_DATE value
	 */
	public void setReqDate (java.lang.String reqDate) {
		this.reqDate = reqDate;
	}



	/**
	 * Return the value associated with the column: CHK_OPR
	 */
	public java.lang.String getChkOpr () {
		return chkOpr;
	}

	/**
	 * Set the value related to the column: CHK_OPR
	 * @param chkOpr the CHK_OPR value
	 */
	public void setChkOpr (java.lang.String chkOpr) {
		this.chkOpr = chkOpr;
	}



	/**
	 * Return the value associated with the column: CHK_DATE
	 */
	public java.lang.String getChkDate () {
		return chkDate;
	}

	/**
	 * Set the value related to the column: CHK_DATE
	 * @param chkDate the CHK_DATE value
	 */
	public void setChkDate (java.lang.String chkDate) {
		this.chkDate = chkDate;
	}



	/**
	 * Return the value associated with the column: MISC
	 */
	public java.lang.String getMisc () {
		return misc;
	}

	/**
	 * Set the value related to the column: MISC
	 * @param misc the MISC value
	 */
	public void setMisc (java.lang.String misc) {
		this.misc = misc;
	}



	/**
	 * Return the value associated with the column: REC_UPD_OPR
	 */
	public java.lang.String getRecUpdOpr () {
		return recUpdOpr;
	}

	/**
	 * Set the value related to the column: REC_UPD_OPR
	 * @param recUpdOpr the REC_UPD_OPR value
	 */
	public void setRecUpdOpr (java.lang.String recUpdOpr) {
		this.recUpdOpr = recUpdOpr;
	}



	/**
	 * Return the value associated with the column: REC_UPD_TS
	 */
	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: REC_UPD_TS
	 * @param recUpdTs the REC_UPD_TS value
	 */
	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblTermTmkLog)) return false;
		else {
			com.huateng.po.TblTermTmkLog tblTermTmkLog = (com.huateng.po.TblTermTmkLog) obj;
			if (null == this.getId() || null == tblTermTmkLog.getId()) return false;
			else return (this.getId().equals(tblTermTmkLog.getId()));
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

	/**
	 * @return the prtOpr
	 */
	public java.lang.String getPrtOpr() {
		return prtOpr;
	}

	/**
	 * @param prtOpr the prtOpr to set
	 */
	public void setPrtOpr(java.lang.String prtOpr) {
		this.prtOpr = prtOpr;
	}

	/**
	 * @return the prtDate
	 */
	public java.lang.String getPrtDate() {
		return prtDate;
	}

	/**
	 * @param prtDate the prtDate to set
	 */
	public void setPrtDate(java.lang.String prtDate) {
		this.prtDate = prtDate;
	}

	/**
	 * @return the prtCount
	 */
	public java.lang.Integer getPrtCount() {
		return prtCount;
	}

	/**
	 * @param prtCount the prtCount to set
	 */
	public void setPrtCount(java.lang.Integer prtCount) {
		this.prtCount = prtCount;
	}



}