package com.huateng.po;

import java.io.Serializable;



public class TblBrhInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static String REF = "TblBrhInfo";
	public static String PROP_CUP_BRH_ID = "CupBrhId";
	public static String PROP_LAST_UPD_TS = "LastUpdTs";
	public static String PROP_LAST_UPD_TXN_ID = "LastUpdTxnId";
	public static String PROP_RESV1 = "Resv1";
	public static String PROP_REG_DT = "RegDt";
	public static String PROP_BRH_LEVEL = "BrhLevel";
	public static String PROP_BRH_CONT_NAME = "BrhContName";
	public static String PROP_BRH_STA = "BrhSta";
	public static String PROP_BRH_TEL_NO = "BrhTelNo";
	public static String PROP_RESV2 = "Resv2";
	public static String PROP_BRH_NAME = "BrhName";
	public static String PROP_BRH_ADDR = "BrhAddr";
	public static String PROP_UP_BRH_ID = "UpBrhId";
	public static String PROP_POST_CD = "PostCd";
	public static String PROP_ID = "Id";
	public static String PROP_LAST_UPD_OPR_ID = "LastUpdOprId";
	public static String LEVEL_ONE = "LevelOne";
	public static String LEVEL_TWO = "LevelTwo";
	public static String LEVEL_THR = "LevelThr";
	public static String LEVEL_FOU = "LevelFou";
	private String LevelOne;
	private String LevelTwo;
	private String LevelThr;
	private String LevelFou;

	public String getLevelFou() {
		return LevelFou;
	}

	public void setLevelFou(String levelFou) {
		LevelFou = levelFou;
	}

	public String getLevelThr() {
		return LevelThr;
	}

	public void setLevelThr(String levelThr) {
		LevelThr = levelThr;
	}

	public String getLevelTwo() {
		return LevelTwo;
	}

	public void setLevelTwo(String levelTwo) {
		LevelTwo = levelTwo;
	}

	public String getLevelOne() {
		return LevelOne;
	}

	public void setLevelOne(String levelOne) {
		LevelOne = levelOne;
	}


	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private String id;

	// fields
	private String cupBrhId;
	private String brhLevel;
	private String brhSta;
	private String upBrhId;
	private String regDt;
	private String postCd;
	private String brhAddr;
	private String brhName;
	private String brhTelNo;
	private String brhContName;
	private String resv1;
	private String resv2;
	private String lastUpdOprId;
	private String lastUpdTxnId;
	private String lastUpdTs;


	
	
	/**
	 * 
	 */
	public TblBrhInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="BRH_ID"
     */
	public String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: CUP_BRH_ID
	 */
	public String getCupBrhId () {
		return cupBrhId;
	}

	/**
	 * Set the value related to the column: CUP_BRH_ID
	 * @param cupBrhId the CUP_BRH_ID value
	 */
	public void setCupBrhId (String cupBrhId) {
		this.cupBrhId = cupBrhId;
	}



	/**
	 * Return the value associated with the column: BRH_LEVEL
	 */
	public String getBrhLevel () {
		return brhLevel;
	}

	/**
	 * Set the value related to the column: BRH_LEVEL
	 * @param brhLevel the BRH_LEVEL value
	 */
	public void setBrhLevel (String brhLevel) {
		this.brhLevel = brhLevel;
	}



	/**
	 * Return the value associated with the column: BRH_STA
	 */
	public String getBrhSta () {
		return brhSta;
	}

	/**
	 * Set the value related to the column: BRH_STA
	 * @param brhSta the BRH_STA value
	 */
	public void setBrhSta (String brhSta) {
		this.brhSta = brhSta;
	}



	/**
	 * Return the value associated with the column: UP_BRH_ID
	 */
	public String getUpBrhId () {
		return upBrhId;
	}

	/**
	 * Set the value related to the column: UP_BRH_ID
	 * @param upBrhId the UP_BRH_ID value
	 */
	public void setUpBrhId (String upBrhId) {
		this.upBrhId = upBrhId;
	}



	/**
	 * Return the value associated with the column: REG_DT
	 */
	public String getRegDt () {
		return regDt;
	}

	/**
	 * Set the value related to the column: REG_DT
	 * @param regDt the REG_DT value
	 */
	public void setRegDt (String regDt) {
		this.regDt = regDt;
	}



	/**
	 * Return the value associated with the column: POST_CD
	 */
	public String getPostCd () {
		return postCd;
	}

	/**
	 * Set the value related to the column: POST_CD
	 * @param postCd the POST_CD value
	 */
	public void setPostCd (String postCd) {
		this.postCd = postCd;
	}



	/**
	 * Return the value associated with the column: BRH_ADDR
	 */
	public String getBrhAddr () {
		return brhAddr;
	}

	/**
	 * Set the value related to the column: BRH_ADDR
	 * @param brhAddr the BRH_ADDR value
	 */
	public void setBrhAddr (String brhAddr) {
		this.brhAddr = brhAddr;
	}



	/**
	 * Return the value associated with the column: BRH_NAME
	 */
	public String getBrhName () {
		return brhName;
	}

	/**
	 * Set the value related to the column: BRH_NAME
	 * @param brhName the BRH_NAME value
	 */
	public void setBrhName (String brhName) {
		this.brhName = brhName;
	}



	/**
	 * Return the value associated with the column: BRH_TEL_NO
	 */
	public String getBrhTelNo () {
		return brhTelNo;
	}

	/**
	 * Set the value related to the column: BRH_TEL_NO
	 * @param brhTelNo the BRH_TEL_NO value
	 */
	public void setBrhTelNo (String brhTelNo) {
		this.brhTelNo = brhTelNo;
	}



	/**
	 * Return the value associated with the column: BRH_CONT_NAME
	 */
	public String getBrhContName () {
		return brhContName;
	}

	/**
	 * Set the value related to the column: BRH_CONT_NAME
	 * @param brhContName the BRH_CONT_NAME value
	 */
	public void setBrhContName (String brhContName) {
		this.brhContName = brhContName;
	}



	/**
	 * Return the value associated with the column: RESV1
	 */
	public String getResv1 () {
		return resv1;
	}

	/**
	 * Set the value related to the column: RESV1
	 * @param resv1 the RESV1 value
	 */
	public void setResv1 (String resv1) {
		this.resv1 = resv1;
	}



	/**
	 * Return the value associated with the column: RESV2
	 */
	public String getResv2 () {
		return resv2;
	}

	/**
	 * Set the value related to the column: RESV2
	 * @param resv2 the RESV2 value
	 */
	public void setResv2 (String resv2) {
		this.resv2 = resv2;
	}



	/**
	 * Return the value associated with the column: LAST_UPD_OPR_ID
	 */
	public String getLastUpdOprId () {
		return lastUpdOprId;
	}

	/**
	 * Set the value related to the column: LAST_UPD_OPR_ID
	 * @param lastUpdOprId the LAST_UPD_OPR_ID value
	 */
	public void setLastUpdOprId (String lastUpdOprId) {
		this.lastUpdOprId = lastUpdOprId;
	}



	/**
	 * Return the value associated with the column: LAST_UPD_TXN_ID
	 */
	public String getLastUpdTxnId () {
		return lastUpdTxnId;
	}

	/**
	 * Set the value related to the column: LAST_UPD_TXN_ID
	 * @param lastUpdTxnId the LAST_UPD_TXN_ID value
	 */
	public void setLastUpdTxnId (String lastUpdTxnId) {
		this.lastUpdTxnId = lastUpdTxnId;
	}



	/**
	 * Return the value associated with the column: LAST_UPD_TS
	 */
	public String getLastUpdTs () {
		return lastUpdTs;
	}

	/**
	 * Set the value related to the column: LAST_UPD_TS
	 * @param lastUpdTs the LAST_UPD_TS value
	 */
	public void setLastUpdTs (String lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblBrhInfo)) return false;
		else {
			TblBrhInfo tblBrhInfo = (TblBrhInfo) obj;
			if (null == this.getId() || null == tblBrhInfo.getId()) return false;
			else return (this.getId().equals(tblBrhInfo.getId()));
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