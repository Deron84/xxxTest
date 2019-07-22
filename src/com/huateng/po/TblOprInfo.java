package com.huateng.po;

import java.io.Serializable;




public class TblOprInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblOprInfo";
	public static String PROP_BRH_ID = "BrhId";
	public static String PROP_OPR_DEGREE = "OprDegree";
	public static String PROP_OPR_STA = "OprSta";
	public static String PROP_LAST_UPD_TS = "LastUpdTs";
	public static String PROP_LAST_UPD_TXN_ID = "LastUpdTxnId";
	public static String PROP_PWD_WR_TM = "PwdWrTm";
	public static String PROP_OPR_PWD = "OprPwd";
	public static String PROP_OPR_NAME = "OprName";
	public static String PROP_PWD_OUT_DATE = "PwdOutDate";
	public static String PROP_REGISTER_DT = "RegisterDt";
	public static String PROP_OPR_GENDER = "OprGender";
	public static String PROP_OPR_TEL = "OprTel";
	public static String PROP_OPR_MOBILE = "OprMobile";
	public static String PROP_PWD_WR_TM_TOTAL = "PwdWrTmTotal";
	public static String PROP_ID = "Id";
	public static String PROP_LAST_UPD_OPR_ID = "LastUpdOprId";
	public static String OPR_EMAIL = "oprEmail";
	public static String OPR_DEGREE_RSC = "oprDegreeRsc";
	public static String OPR_LOG_STA = "oprLogSta";
	public static String SET_OPR_ID= "setOprId";
	

	protected void initialize () {}
	
	
	/**
	 * 
	 */
	public TblOprInfo() {
		super();
		// TODO Auto-generated constructor stub
	}


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String brhId;
	private java.lang.String oprDegree;
	private java.lang.String oprName;
	private java.lang.String oprGender;
	private java.lang.String registerDt;
	private java.lang.String oprPwd;
	private java.lang.String oprTel;
	private java.lang.String oprMobile;
	private java.lang.String pwdWrTm;
	private java.lang.String pwdWrTmTotal;
	private java.lang.String pwdWrLastDt;
	private java.lang.String pwdOutDate;
	private java.lang.String oprSta;
	private java.lang.String lastUpdOprId;
	private java.lang.String lastUpdTxnId;
	private java.lang.String lastUpdTs;
	private java.lang.String oprEmail;
	private java.lang.String oprDegreeRsc;
	private java.lang.String oprLogSta;
	private java.lang.String setOprId;

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="OPR_ID"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: BRH_ID
	 */
	public java.lang.String getBrhId () {
		return brhId;
	}

	/**
	 * Set the value related to the column: BRH_ID
	 * @param brhId the BRH_ID value
	 */
	public void setBrhId (java.lang.String brhId) {
		this.brhId = brhId;
	}



	/**
	 * Return the value associated with the column: OPR_DEGREE
	 */
	public java.lang.String getOprDegree () {
		return oprDegree;
	}

	/**
	 * Set the value related to the column: OPR_DEGREE
	 * @param oprDegree the OPR_DEGREE value
	 */
	public void setOprDegree (java.lang.String oprDegree) {
		this.oprDegree = oprDegree;
	}



	/**
	 * Return the value associated with the column: OPR_NAME
	 */
	public java.lang.String getOprName () {
		return oprName;
	}

	/**
	 * Set the value related to the column: OPR_NAME
	 * @param oprName the OPR_NAME value
	 */
	public void setOprName (java.lang.String oprName) {
		this.oprName = oprName;
	}



	/**
	 * Return the value associated with the column: OPR_GENDER
	 */
	public java.lang.String getOprGender () {
		return oprGender;
	}

	/**
	 * Set the value related to the column: OPR_GENDER
	 * @param oprGender the OPR_GENDER value
	 */
	public void setOprGender (java.lang.String oprGender) {
		this.oprGender = oprGender;
	}



	/**
	 * Return the value associated with the column: REGISTER_DT
	 */
	public java.lang.String getRegisterDt () {
		return registerDt;
	}

	/**
	 * Set the value related to the column: REGISTER_DT
	 * @param registerDt the REGISTER_DT value
	 */
	public void setRegisterDt (java.lang.String registerDt) {
		this.registerDt = registerDt;
	}



	/**
	 * Return the value associated with the column: OPR_PWD
	 */
	public java.lang.String getOprPwd () {
		return oprPwd;
	}

	/**
	 * Set the value related to the column: OPR_PWD
	 * @param oprPwd the OPR_PWD value
	 */
	public void setOprPwd (java.lang.String oprPwd) {
		this.oprPwd = oprPwd;
	}



	/**
	 * Return the value associated with the column: OPR_TEL
	 */
	public java.lang.String getOprTel () {
		return oprTel;
	}

	/**
	 * Set the value related to the column: OPR_TEL
	 * @param oprTel the OPR_TEL value
	 */
	public void setOprTel (java.lang.String oprTel) {
		this.oprTel = oprTel;
	}



	/**
	 * Return the value associated with the column: OPR_MOBILE
	 */
	public java.lang.String getOprMobile () {
		return oprMobile;
	}

	/**
	 * Set the value related to the column: OPR_MOBILE
	 * @param oprMobile the OPR_MOBILE value
	 */
	public void setOprMobile (java.lang.String oprMobile) {
		this.oprMobile = oprMobile;
	}



	/**
	 * Return the value associated with the column: PWD_WR_TM
	 */
	public java.lang.String getPwdWrTm () {
		return pwdWrTm;
	}

	/**
	 * Set the value related to the column: PWD_WR_TM
	 * @param pwdWrTm the PWD_WR_TM value
	 */
	public void setPwdWrTm (java.lang.String pwdWrTm) {
		this.pwdWrTm = pwdWrTm;
	}



	/**
	 * Return the value associated with the column: PWD_WR_TM_TOTAL
	 */
	public java.lang.String getPwdWrTmTotal () {
		return pwdWrTmTotal;
	}

	/**
	 * Set the value related to the column: PWD_WR_TM_TOTAL
	 * @param pwdWrTmTotal the PWD_WR_TM_TOTAL value
	 */
	public void setPwdWrTmTotal (java.lang.String pwdWrTmTotal) {
		this.pwdWrTmTotal = pwdWrTmTotal;
	}


	/**
	 * @return the pwdWrLastDt
	 */
	public java.lang.String getPwdWrLastDt() {
		return pwdWrLastDt;
	}

	/**
	 * @param pwdWrLastDt the pwdWrLastDt to set
	 */
	public void setPwdWrLastDt(java.lang.String pwdWrLastDt) {
		this.pwdWrLastDt = pwdWrLastDt;
	}

	/**
	 * Return the value associated with the column: PWD_OUT_DATE
	 */
	public java.lang.String getPwdOutDate () {
		return pwdOutDate;
	}

	/**
	 * Set the value related to the column: PWD_OUT_DATE
	 * @param pwdOutDate the PWD_OUT_DATE value
	 */
	public void setPwdOutDate (java.lang.String pwdOutDate) {
		this.pwdOutDate = pwdOutDate;
	}



	/**
	 * Return the value associated with the column: OPR_STA
	 */
	public java.lang.String getOprSta () {
		return oprSta;
	}

	/**
	 * Set the value related to the column: OPR_STA
	 * @param oprSta the OPR_STA value
	 */
	public void setOprSta (java.lang.String oprSta) {
		this.oprSta = oprSta;
	}



	/**
	 * Return the value associated with the column: LAST_UPD_OPR_ID
	 */
	public java.lang.String getLastUpdOprId () {
		return lastUpdOprId;
	}

	/**
	 * Set the value related to the column: LAST_UPD_OPR_ID
	 * @param lastUpdOprId the LAST_UPD_OPR_ID value
	 */
	public void setLastUpdOprId (java.lang.String lastUpdOprId) {
		this.lastUpdOprId = lastUpdOprId;
	}



	/**
	 * Return the value associated with the column: LAST_UPD_TXN_ID
	 */
	public java.lang.String getLastUpdTxnId () {
		return lastUpdTxnId;
	}

	/**
	 * Set the value related to the column: LAST_UPD_TXN_ID
	 * @param lastUpdTxnId the LAST_UPD_TXN_ID value
	 */
	public void setLastUpdTxnId (java.lang.String lastUpdTxnId) {
		this.lastUpdTxnId = lastUpdTxnId;
	}



	/**
	 * Return the value associated with the column: LAST_UPD_TS
	 */
	public java.lang.String getLastUpdTs () {
		return lastUpdTs;
	}

	/**
	 * Set the value related to the column: LAST_UPD_TS
	 * @param lastUpdTs the LAST_UPD_TS value
	 */
	public void setLastUpdTs (java.lang.String lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}



	public java.lang.String getOprEmail() {
		return oprEmail;
	}


	public void setOprEmail(java.lang.String oprEmail) {
		this.oprEmail = oprEmail;
	}

	

	public java.lang.String getOprDegreeRsc() {
		return oprDegreeRsc;
	}


	public void setOprDegreeRsc(java.lang.String oprDegreeRsc) {
		this.oprDegreeRsc = oprDegreeRsc;
	}
	

	public java.lang.String getOprLogSta() {
		return oprLogSta;
	}


	public void setOprLogSta(java.lang.String oprLogSta) {
		this.oprLogSta = oprLogSta;
	}

	

	public java.lang.String getSetOprId() {
		return setOprId;
	}


	public void setSetOprId(java.lang.String setOprId) {
		this.setOprId = setOprId;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblOprInfo)) return false;
		else {
			com.huateng.po.TblOprInfo tblOprInfo = (com.huateng.po.TblOprInfo) obj;
			if (null == this.getId() || null == tblOprInfo.getId()) return false;
			else return (this.getId().equals(tblOprInfo.getId()));
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