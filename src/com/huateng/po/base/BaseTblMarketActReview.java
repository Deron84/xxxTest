package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_MARKET_ACT_REVIEW table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_MARKET_ACT_REVIEW"
 */

public abstract class BaseTblMarketActReview  implements Serializable {

	public static String REF = "TblMarketActReview";
	public static String PROP_AMOUNT02 = "amount02";
	public static String PROP_BANK_NO = "bankNo";
	public static String PROP_ORGAN_TYPE = "organType";
	public static String PROP_AMOUNT01 = "amount01";
	public static String PROP_CRT_TS = "crtTs";
	public static String PROP_REMARKS = "remarks";
	public static String PROP_ACT_NO = "actNo";
	public static String PROP_REC_OPR = "recOpr";
	public static String PROP_FLAG02 = "flag02";
	public static String PROP_FLAG03 = "flag03";
	public static String PROP_FLAG01 = "flag01";
	public static String PROP_UPD_OPR = "updOpr";
	public static String PROP_ACT_CONTENT = "actContent";
	public static String PROP_FLAG04 = "flag04";
	public static String PROP_CRT_OPR = "crtOpr";
	public static String PROP_START_DATE = "startDate";
	public static String PROP_ACT_CYCLE = "actCycle";
	public static String PROP_ACT_NAME = "actName";
	public static String PROP_ACT_FEE = "actFee";
	public static String PROP_END_DATE = "endDate";
	public static String PROP_STATE = "state";
	public static String PROP_MISC2 = "misc2";
	public static String PROP_MISC3 = "misc3";
	public static String PROP_MISC1 = "misc1";
	public static String PROP_UPD_DT = "updDt";
	public static String PROP_MISC4 = "misc4";
	public static String PROP_ORGAN_NO = "organNo";


	// constructors
	public BaseTblMarketActReview () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblMarketActReview (java.lang.String actNo) {
		this.setActNo(actNo);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String actNo;

	// fields
	private java.lang.String bankNo;
	private java.lang.String actName;
	private java.lang.String startDate;
	private java.lang.String endDate;
	private java.lang.Integer actCycle;
	private java.lang.String organNo;
	private java.lang.String organType;
	private java.lang.String actContent;
	private java.math.BigDecimal actFee;
	private java.lang.String remarks;
	private java.math.BigDecimal amount01;
	private java.math.BigDecimal amount02;
	private java.lang.String flag01;
	private java.lang.String flag02;
	private java.lang.String flag03;
	private java.lang.String flag04;
	private java.lang.String misc1;
	private java.lang.String misc2;
	private java.lang.String misc3;
	private java.lang.String misc4;
	private java.lang.String crtOpr;
	private java.lang.String crtTs;
	private java.lang.String updOpr;
	private java.lang.String updDt;
	private java.lang.String recOpr;
	private java.lang.String state;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="ACT_NO"
     */
	public java.lang.String getActNo () {
		return actNo;
	}

	/**
	 * Set the unique identifier of this class
	 * @param actNo the new ID
	 */
	public void setActNo (java.lang.String actNo) {
		this.actNo = actNo;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: BANK_NO
	 */
	public java.lang.String getBankNo () {
		return bankNo;
	}

	/**
	 * Set the value related to the column: BANK_NO
	 * @param bankNo the BANK_NO value
	 */
	public void setBankNo (java.lang.String bankNo) {
		this.bankNo = bankNo;
	}



	/**
	 * Return the value associated with the column: ACT_NAME
	 */
	public java.lang.String getActName () {
		return actName;
	}

	/**
	 * Set the value related to the column: ACT_NAME
	 * @param actName the ACT_NAME value
	 */
	public void setActName (java.lang.String actName) {
		this.actName = actName;
	}



	/**
	 * Return the value associated with the column: START_DATE
	 */
	public java.lang.String getStartDate () {
		return startDate;
	}

	/**
	 * Set the value related to the column: START_DATE
	 * @param startDate the START_DATE value
	 */
	public void setStartDate (java.lang.String startDate) {
		this.startDate = startDate;
	}



	/**
	 * Return the value associated with the column: END_DATE
	 */
	public java.lang.String getEndDate () {
		return endDate;
	}

	/**
	 * Set the value related to the column: END_DATE
	 * @param endDate the END_DATE value
	 */
	public void setEndDate (java.lang.String endDate) {
		this.endDate = endDate;
	}



	/**
	 * Return the value associated with the column: ACT_CYCLE
	 */
	public java.lang.Integer getActCycle () {
		return actCycle;
	}

	/**
	 * Set the value related to the column: ACT_CYCLE
	 * @param actCycle the ACT_CYCLE value
	 */
	public void setActCycle (java.lang.Integer actCycle) {
		this.actCycle = actCycle;
	}



	/**
	 * Return the value associated with the column: ORGAN_NO
	 */
	public java.lang.String getOrganNo () {
		return organNo;
	}

	/**
	 * Set the value related to the column: ORGAN_NO
	 * @param organNo the ORGAN_NO value
	 */
	public void setOrganNo (java.lang.String organNo) {
		this.organNo = organNo;
	}



	/**
	 * Return the value associated with the column: ORGAN_TYPE
	 */
	public java.lang.String getOrganType () {
		return organType;
	}

	/**
	 * Set the value related to the column: ORGAN_TYPE
	 * @param organType the ORGAN_TYPE value
	 */
	public void setOrganType (java.lang.String organType) {
		this.organType = organType;
	}



	/**
	 * Return the value associated with the column: ACT_CONTENT
	 */
	public java.lang.String getActContent () {
		return actContent;
	}

	/**
	 * Set the value related to the column: ACT_CONTENT
	 * @param actContent the ACT_CONTENT value
	 */
	public void setActContent (java.lang.String actContent) {
		this.actContent = actContent;
	}



	/**
	 * Return the value associated with the column: ACT_FEE
	 */
	public java.math.BigDecimal getActFee () {
		return actFee;
	}

	/**
	 * Set the value related to the column: ACT_FEE
	 * @param actFee the ACT_FEE value
	 */
	public void setActFee (java.math.BigDecimal actFee) {
		this.actFee = actFee;
	}



	/**
	 * Return the value associated with the column: REMARKS
	 */
	public java.lang.String getRemarks () {
		return remarks;
	}

	/**
	 * Set the value related to the column: REMARKS
	 * @param remarks the REMARKS value
	 */
	public void setRemarks (java.lang.String remarks) {
		this.remarks = remarks;
	}



	/**
	 * Return the value associated with the column: AMOUNT01
	 */
	public java.math.BigDecimal getAmount01 () {
		return amount01;
	}

	/**
	 * Set the value related to the column: AMOUNT01
	 * @param amount01 the AMOUNT01 value
	 */
	public void setAmount01 (java.math.BigDecimal amount01) {
		this.amount01 = amount01;
	}



	/**
	 * Return the value associated with the column: AMOUNT02
	 */
	public java.math.BigDecimal getAmount02 () {
		return amount02;
	}

	/**
	 * Set the value related to the column: AMOUNT02
	 * @param amount02 the AMOUNT02 value
	 */
	public void setAmount02 (java.math.BigDecimal amount02) {
		this.amount02 = amount02;
	}



	/**
	 * Return the value associated with the column: FLAG01
	 */
	public java.lang.String getFlag01 () {
		return flag01;
	}

	/**
	 * Set the value related to the column: FLAG01
	 * @param flag01 the FLAG01 value
	 */
	public void setFlag01 (java.lang.String flag01) {
		this.flag01 = flag01;
	}



	/**
	 * Return the value associated with the column: FLAG02
	 */
	public java.lang.String getFlag02 () {
		return flag02;
	}

	/**
	 * Set the value related to the column: FLAG02
	 * @param flag02 the FLAG02 value
	 */
	public void setFlag02 (java.lang.String flag02) {
		this.flag02 = flag02;
	}



	/**
	 * Return the value associated with the column: FLAG03
	 */
	public java.lang.String getFlag03 () {
		return flag03;
	}

	/**
	 * Set the value related to the column: FLAG03
	 * @param flag03 the FLAG03 value
	 */
	public void setFlag03 (java.lang.String flag03) {
		this.flag03 = flag03;
	}



	/**
	 * Return the value associated with the column: FLAG04
	 */
	public java.lang.String getFlag04 () {
		return flag04;
	}

	/**
	 * Set the value related to the column: FLAG04
	 * @param flag04 the FLAG04 value
	 */
	public void setFlag04 (java.lang.String flag04) {
		this.flag04 = flag04;
	}



	/**
	 * Return the value associated with the column: MISC1
	 */
	public java.lang.String getMisc1 () {
		return misc1;
	}

	/**
	 * Set the value related to the column: MISC1
	 * @param misc1 the MISC1 value
	 */
	public void setMisc1 (java.lang.String misc1) {
		this.misc1 = misc1;
	}



	/**
	 * Return the value associated with the column: MISC2
	 */
	public java.lang.String getMisc2 () {
		return misc2;
	}

	/**
	 * Set the value related to the column: MISC2
	 * @param misc2 the MISC2 value
	 */
	public void setMisc2 (java.lang.String misc2) {
		this.misc2 = misc2;
	}



	/**
	 * Return the value associated with the column: MISC3
	 */
	public java.lang.String getMisc3 () {
		return misc3;
	}

	/**
	 * Set the value related to the column: MISC3
	 * @param misc3 the MISC3 value
	 */
	public void setMisc3 (java.lang.String misc3) {
		this.misc3 = misc3;
	}



	/**
	 * Return the value associated with the column: MISC4
	 */
	public java.lang.String getMisc4 () {
		return misc4;
	}

	/**
	 * Set the value related to the column: MISC4
	 * @param misc4 the MISC4 value
	 */
	public void setMisc4 (java.lang.String misc4) {
		this.misc4 = misc4;
	}



	/**
	 * Return the value associated with the column: CRT_OPR
	 */
	public java.lang.String getCrtOpr () {
		return crtOpr;
	}

	/**
	 * Set the value related to the column: CRT_OPR
	 * @param crtOpr the CRT_OPR value
	 */
	public void setCrtOpr (java.lang.String crtOpr) {
		this.crtOpr = crtOpr;
	}



	/**
	 * Return the value associated with the column: CRT_DT
	 */
	public java.lang.String getCrtTs () {
		return crtTs;
	}

	/**
	 * Set the value related to the column: CRT_DT
	 * @param crtTs the CRT_DT value
	 */
	public void setCrtTs (java.lang.String crtTs) {
		this.crtTs = crtTs;
	}



	/**
	 * Return the value associated with the column: UPD_OPR
	 */
	public java.lang.String getUpdOpr () {
		return updOpr;
	}

	/**
	 * Set the value related to the column: UPD_OPR
	 * @param updOpr the UPD_OPR value
	 */
	public void setUpdOpr (java.lang.String updOpr) {
		this.updOpr = updOpr;
	}



	/**
	 * Return the value associated with the column: UPD_DT
	 */
	public java.lang.String getUpdDt () {
		return updDt;
	}

	/**
	 * Set the value related to the column: UPD_DT
	 * @param updDt the UPD_DT value
	 */
	public void setUpdDt (java.lang.String updDt) {
		this.updDt = updDt;
	}



	/**
	 * Return the value associated with the column: REC_OPR
	 */
	public java.lang.String getRecOpr () {
		return recOpr;
	}

	/**
	 * Set the value related to the column: REC_OPR
	 * @param recOpr the REC_OPR value
	 */
	public void setRecOpr (java.lang.String recOpr) {
		this.recOpr = recOpr;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchtSrv.TblMarketActReview)) return false;
		else {
			com.huateng.po.mchtSrv.TblMarketActReview tblMarketActReview = (com.huateng.po.mchtSrv.TblMarketActReview) obj;
			if (null == this.getActNo() || null == tblMarketActReview.getActNo()) return false;
			else return (this.getActNo().equals(tblMarketActReview.getActNo()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getActNo()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getActNo().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}