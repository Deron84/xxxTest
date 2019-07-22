package com.huateng.po.mchnt.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_MCHT_SETTLE_INF table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_MCHT_SETTLE_INF"
 */

public abstract class BaseTblMchtSettleInf  implements Serializable {

	public static String REF = "TblMchtSettleInf";
	public static String PROP_SETTLE_ACCT_NM = "SettleAcctNm";
	
	public static String PROP_FEE_ACCT_NM = "FeeAcctNm";
	public static String PROP_FEE_TYPE = "FeeType";
	public static String PROP_SETTLE_TYPE = "SettleType";
	public static String PROP_OPEN_STLNO = "OpenStlno";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_FEE_DIV2 = "FeeDiv2";
	public static String PROP_FEE_ACCT = "FeeAcct";
	public static String PROP_FEE_DIV1 = "FeeDiv1";
	public static String PROP_FEE_MIN_AMT = "FeeMinAmt";
	public static String PROP_SETTLE_RPT = "SettleRpt";
	public static String PROP_GROUP_FLAG = "GroupFlag";
	public static String PROP_SETTLE_CHN = "SettleChn";
	public static String PROP_SETTLE_MODE = "SettleMode";
	public static String PROP_FEE_CYCLE = "FeeCycle";
	public static String PROP_FEE_DIV3 = "FeeDiv3";
	public static String PROP_CHANGE_STLNO = "ChangeStlno";
	public static String PROP_SETTLE_BANK_NO = "SettleBankNo";
	public static String PROP_SETTLE_ACCT = "SettleAcct";
	
	public static String PROP_RATE_FLAG = "RateFlag";
	public static String PROP_PART_NUM = "PartNum";
	public static String PROP_RESERVED = "Reserved";
	public static String PROP_AUTO_STL_FLG = "AutoStlFlg";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	
	public static String PROP_SETTLE_BANK_NM = "SettleBankNm";
	public static String PROP_FEE_FIXED = "FeeFixed";
	public static String PROP_FEE_MAX_AMT = "FeeMaxAmt";
	public static String PROP_FEE_BACK_FLG = "FeeBackFlg";
	public static String PROP_FEE_RATE = "FeeRate";
	public static String PROP_MCHT_NO = "MchtNo";
	public static String PROP_BAT_TIME = "BatTime";
	public static String PROP_WHITELIST_FLAG = "WhiteListFlag";
	
	/** 1-单账户
	2-双账户按比例
	3-双账户限额*/
	public static String ACCT_SETTLE_TYPE = "acctSettleType";
	
	//下面是第二账户信息
	public static String ACCT_SETTLE_RATE = "acctSettleRate";
	public static String ACCT_SETTLE_LIMIT = "acctSettleLimit";
	public static String SETTLE_BANK_NO_SND = "settleBankNoSnd";
	public static String SETTLE_BANK_NM_SND = "settleBankNmSnd";
	public static String SETTLE_ACCT_NM_SND = "settleAcctNmSnd";
	public static String SETTLE_ACCT_SND = "settleAcctSnd";
	
	//个人结算账户标志
	public static String PERSON_SETTLE_FLG = "personSettleFlg";

	// constructors
	public BaseTblMchtSettleInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblMchtSettleInf (java.lang.String mchtNo) {
		this.setMchtNo(mchtNo);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblMchtSettleInf (
		java.lang.String mchtNo,
		java.lang.String settleType,
		java.lang.String feeType,
		java.lang.String feeMaxAmt,
		java.lang.String feeMinAmt,
		java.lang.String feeDiv1,
		java.lang.String feeDiv2,
		java.lang.String feeDiv3,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs) {

		this.setMchtNo(mchtNo);
		this.setSettleType(settleType);
		this.setFeeType(feeType);
		this.setFeeMaxAmt(feeMaxAmt);
		this.setFeeMinAmt(feeMinAmt);
		this.setFeeDiv1(feeDiv1);
		this.setFeeDiv2(feeDiv2);
		this.setFeeDiv3(feeDiv3);
		this.setRecUpdTs(recUpdTs);
		this.setRecCrtTs(recCrtTs);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String mchtNo;

	// fields
	private java.lang.String settleType;
	private java.lang.String rateFlag;
	private java.lang.String settleChn;
	private java.lang.String batTime;
	private java.lang.String autoStlFlg;
	private java.lang.String partNum;
	private java.lang.String feeType;
	private java.lang.String feeFixed;
	private java.lang.String feeMaxAmt;
	private java.lang.String feeMinAmt;
	private java.lang.String feeRate;
	private java.lang.String feeDiv1;
	private java.lang.String feeDiv2;
	private java.lang.String feeDiv3;
	private java.lang.String settleMode;
	private java.lang.String feeCycle;
	private java.lang.String settleRpt;
	private java.lang.String settleBankNo;
	private java.lang.String settleBankNm;
	private java.lang.String settleAcctNm;
	private java.lang.String settleAcct;
	private java.lang.String feeAcctNm;
	private java.lang.String feeAcct;
	private java.lang.String groupFlag;
	private java.lang.String openStlno;
	private java.lang.String changeStlno;
	
	private java.lang.String feeBackFlg;
	private java.lang.String reserved;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;

	//白名单标志
	private String whiteListFlag;
	
	
	//第二账户信息
	private String acctSettleType;
	private String acctSettleRate;
	private String acctSettleLimit;
	private java.lang.String settleBankNoSnd;
	private java.lang.String settleBankNmSnd;
	private java.lang.String settleAcctNmSnd;
	private java.lang.String settleAcctSnd;

	//个人结算账户标志
	private String personSettleFlg;
	
	
	public String getPersonSettleFlg() {
		return personSettleFlg;
	}

	
	public void setPersonSettleFlg(String personSettleFlg) {
		this.personSettleFlg = personSettleFlg;
	}

	public String getAcctSettleRate() {
		return acctSettleRate;
	}

	
	public void setAcctSettleRate(String acctSettleRate) {
		this.acctSettleRate = acctSettleRate;
	}

	
	public String getAcctSettleLimit() {
		return acctSettleLimit;
	}

	
	public void setAcctSettleLimit(String acctSettleLimit) {
		this.acctSettleLimit = acctSettleLimit;
	}

	public String getAcctSettleType() {
		return acctSettleType;
	}

	
	public void setAcctSettleType(String acctSettleType) {
		this.acctSettleType = acctSettleType;
	}

	public java.lang.String getSettleBankNoSnd() {
		return settleBankNoSnd;
	}

	
	public void setSettleBankNoSnd(java.lang.String settleBankNoSnd) {
		this.settleBankNoSnd = settleBankNoSnd;
	}

	
	public java.lang.String getSettleBankNmSnd() {
		return settleBankNmSnd;
	}

	
	public void setSettleBankNmSnd(java.lang.String settleBankNmSnd) {
		this.settleBankNmSnd = settleBankNmSnd;
	}

	
	public java.lang.String getSettleAcctNmSnd() {
		return settleAcctNmSnd;
	}

	
	public void setSettleAcctNmSnd(java.lang.String settleAcctNmSnd) {
		this.settleAcctNmSnd = settleAcctNmSnd;
	}

	
	public java.lang.String getSettleAcctSnd() {
		return settleAcctSnd;
	}

	
	public void setSettleAcctSnd(java.lang.String settleAcctSnd) {
		this.settleAcctSnd = settleAcctSnd;
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="MCHT_NO"
     */
	public java.lang.String getMchtNo () {
		return mchtNo;
	}

	/**
	 * Set the unique identifier of this class
	 * @param mchtNo the new ID
	 */
	public void setMchtNo (java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: SETTLE_TYPE
	 */
	public java.lang.String getSettleType () {
		return settleType;
	}

	/**
	 * Set the value related to the column: SETTLE_TYPE
	 * @param settleType the SETTLE_TYPE value
	 */
	public void setSettleType (java.lang.String settleType) {
		this.settleType = settleType;
	}



	/**
	 * Return the value associated with the column: RATE_FLAG
	 */
	public java.lang.String getRateFlag () {
		return rateFlag;
	}

	/**
	 * Set the value related to the column: RATE_FLAG
	 * @param rateFlag the RATE_FLAG value
	 */
	public void setRateFlag (java.lang.String rateFlag) {
		this.rateFlag = rateFlag;
	}



	/**
	 * Return the value associated with the column: SETTLE_CHN
	 */
	public java.lang.String getSettleChn () {
		return settleChn;
	}

	/**
	 * Set the value related to the column: SETTLE_CHN
	 * @param settleChn the SETTLE_CHN value
	 */
	public void setSettleChn (java.lang.String settleChn) {
		this.settleChn = settleChn;
	}



	/**
	 * Return the value associated with the column: BAT_TIME
	 */
	public java.lang.String getBatTime () {
		return batTime;
	}

	/**
	 * Set the value related to the column: BAT_TIME
	 * @param batTime the BAT_TIME value
	 */
	public void setBatTime (java.lang.String batTime) {
		this.batTime = batTime;
	}



	/**
	 * Return the value associated with the column: AUTO_STL_FLG
	 */
	public java.lang.String getAutoStlFlg () {
		return autoStlFlg;
	}

	/**
	 * Set the value related to the column: AUTO_STL_FLG
	 * @param autoStlFlg the AUTO_STL_FLG value
	 */
	public void setAutoStlFlg (java.lang.String autoStlFlg) {
		this.autoStlFlg = autoStlFlg;
	}



	/**
	 * Return the value associated with the column: PART_NUM
	 */
	public java.lang.String getPartNum () {
		return partNum;
	}

	/**
	 * Set the value related to the column: PART_NUM
	 * @param partNum the PART_NUM value
	 */
	public void setPartNum (java.lang.String partNum) {
		this.partNum = partNum;
	}



	/**
	 * Return the value associated with the column: FEE_TYPE
	 */
	public java.lang.String getFeeType () {
		return feeType;
	}

	/**
	 * Set the value related to the column: FEE_TYPE
	 * @param feeType the FEE_TYPE value
	 */
	public void setFeeType (java.lang.String feeType) {
		this.feeType = feeType;
	}



	/**
	 * Return the value associated with the column: FEE_FIXED
	 */
	public java.lang.String getFeeFixed () {
		return feeFixed;
	}

	/**
	 * Set the value related to the column: FEE_FIXED
	 * @param feeFixed the FEE_FIXED value
	 */
	public void setFeeFixed (java.lang.String feeFixed) {
		this.feeFixed = feeFixed;
	}



	/**
	 * Return the value associated with the column: FEE_MAX_AMT
	 */
	public java.lang.String getFeeMaxAmt () {
		return feeMaxAmt;
	}

	/**
	 * Set the value related to the column: FEE_MAX_AMT
	 * @param feeMaxAmt the FEE_MAX_AMT value
	 */
	public void setFeeMaxAmt (java.lang.String feeMaxAmt) {
		this.feeMaxAmt = feeMaxAmt;
	}



	/**
	 * Return the value associated with the column: FEE_MIN_AMT
	 */
	public java.lang.String getFeeMinAmt () {
		return feeMinAmt;
	}

	/**
	 * Set the value related to the column: FEE_MIN_AMT
	 * @param feeMinAmt the FEE_MIN_AMT value
	 */
	public void setFeeMinAmt (java.lang.String feeMinAmt) {
		this.feeMinAmt = feeMinAmt;
	}



	/**
	 * Return the value associated with the column: FEE_RATE
	 */
	public java.lang.String getFeeRate () {
		return feeRate;
	}

	/**
	 * Set the value related to the column: FEE_RATE
	 * @param feeRate the FEE_RATE value
	 */
	public void setFeeRate (java.lang.String feeRate) {
		this.feeRate = feeRate;
	}



	/**
	 * Return the value associated with the column: FEE_DIV_1
	 */
	public java.lang.String getFeeDiv1 () {
		return feeDiv1;
	}

	/**
	 * Set the value related to the column: FEE_DIV_1
	 * @param feeDiv1 the FEE_DIV_1 value
	 */
	public void setFeeDiv1 (java.lang.String feeDiv1) {
		this.feeDiv1 = feeDiv1;
	}



	/**
	 * Return the value associated with the column: FEE_DIV_2
	 */
	public java.lang.String getFeeDiv2 () {
		return feeDiv2;
	}

	/**
	 * Set the value related to the column: FEE_DIV_2
	 * @param feeDiv2 the FEE_DIV_2 value
	 */
	public void setFeeDiv2 (java.lang.String feeDiv2) {
		this.feeDiv2 = feeDiv2;
	}



	/**
	 * Return the value associated with the column: FEE_DIV_3
	 */
	public java.lang.String getFeeDiv3 () {
		return feeDiv3;
	}

	/**
	 * Set the value related to the column: FEE_DIV_3
	 * @param feeDiv3 the FEE_DIV_3 value
	 */
	public void setFeeDiv3 (java.lang.String feeDiv3) {
		this.feeDiv3 = feeDiv3;
	}



	/**
	 * Return the value associated with the column: SETTLE_MODE
	 */
	public java.lang.String getSettleMode () {
		return settleMode;
	}

	/**
	 * Set the value related to the column: SETTLE_MODE
	 * @param settleMode the SETTLE_MODE value
	 */
	public void setSettleMode (java.lang.String settleMode) {
		this.settleMode = settleMode;
	}



	/**
	 * Return the value associated with the column: FEE_CYCLE
	 */
	public java.lang.String getFeeCycle () {
		return feeCycle;
	}

	/**
	 * Set the value related to the column: FEE_CYCLE
	 * @param feeCycle the FEE_CYCLE value
	 */
	public void setFeeCycle (java.lang.String feeCycle) {
		this.feeCycle = feeCycle;
	}



	/**
	 * Return the value associated with the column: SETTLE_RPT
	 */
	public java.lang.String getSettleRpt () {
		return settleRpt;
	}

	/**
	 * Set the value related to the column: SETTLE_RPT
	 * @param settleRpt the SETTLE_RPT value
	 */
	public void setSettleRpt (java.lang.String settleRpt) {
		this.settleRpt = settleRpt;
	}



	/**
	 * Return the value associated with the column: SETTLE_BANK_NO
	 */
	public java.lang.String getSettleBankNo () {
		return settleBankNo;
	}

	/**
	 * Set the value related to the column: SETTLE_BANK_NO
	 * @param settleBankNo the SETTLE_BANK_NO value
	 */
	public void setSettleBankNo (java.lang.String settleBankNo) {
		this.settleBankNo = settleBankNo;
	}



	/**
	 * Return the value associated with the column: SETTLE_BANK_NM
	 */
	public java.lang.String getSettleBankNm () {
		return settleBankNm;
	}

	/**
	 * Set the value related to the column: SETTLE_BANK_NM
	 * @param settleBankNm the SETTLE_BANK_NM value
	 */
	public void setSettleBankNm (java.lang.String settleBankNm) {
		this.settleBankNm = settleBankNm;
	}



	/**
	 * Return the value associated with the column: SETTLE_ACCT_NM
	 */
	public java.lang.String getSettleAcctNm () {
		return settleAcctNm;
	}

	/**
	 * Set the value related to the column: SETTLE_ACCT_NM
	 * @param settleAcctNm the SETTLE_ACCT_NM value
	 */
	public void setSettleAcctNm (java.lang.String settleAcctNm) {
		this.settleAcctNm = settleAcctNm;
	}



	/**
	 * Return the value associated with the column: SETTLE_ACCT
	 */
	public java.lang.String getSettleAcct () {
		return settleAcct;
	}

	/**
	 * Set the value related to the column: SETTLE_ACCT
	 * @param settleAcct the SETTLE_ACCT value
	 */
	public void setSettleAcct (java.lang.String settleAcct) {
		this.settleAcct = settleAcct;
	}



	/**
	 * Return the value associated with the column: FEE_ACCT_NM
	 */
	public java.lang.String getFeeAcctNm () {
		return feeAcctNm;
	}

	/**
	 * Set the value related to the column: FEE_ACCT_NM
	 * @param feeAcctNm the FEE_ACCT_NM value
	 */
	public void setFeeAcctNm (java.lang.String feeAcctNm) {
		this.feeAcctNm = feeAcctNm;
	}



	/**
	 * Return the value associated with the column: FEE_ACCT
	 */
	public java.lang.String getFeeAcct () {
		return feeAcct;
	}

	/**
	 * Set the value related to the column: FEE_ACCT
	 * @param feeAcct the FEE_ACCT value
	 */
	public void setFeeAcct (java.lang.String feeAcct) {
		this.feeAcct = feeAcct;
	}



	/**
	 * Return the value associated with the column: GROUP_FLAG
	 */
	public java.lang.String getGroupFlag () {
		return groupFlag;
	}

	/**
	 * Set the value related to the column: GROUP_FLAG
	 * @param groupFlag the GROUP_FLAG value
	 */
	public void setGroupFlag (java.lang.String groupFlag) {
		this.groupFlag = groupFlag;
	}



	/**
	 * Return the value associated with the column: OPEN_STLNO
	 */
	public java.lang.String getOpenStlno () {
		return openStlno;
	}

	/**
	 * Set the value related to the column: OPEN_STLNO
	 * @param openStlno the OPEN_STLNO value
	 */
	public void setOpenStlno (java.lang.String openStlno) {
		this.openStlno = openStlno;
	}



	/**
	 * Return the value associated with the column: CHANGE_STLNO
	 */
	public java.lang.String getChangeStlno () {
		return changeStlno;
	}

	/**
	 * Set the value related to the column: CHANGE_STLNO
	 * @param changeStlno the CHANGE_STLNO value
	 */
	public void setChangeStlno (java.lang.String changeStlno) {
		this.changeStlno = changeStlno;
	}




	/**
	 * Return the value associated with the column: FEE_BACK_FLG
	 */
	public java.lang.String getFeeBackFlg () {
		return feeBackFlg;
	}

	/**
	 * Set the value related to the column: FEE_BACK_FLG
	 * @param feeBackFlg the FEE_BACK_FLG value
	 */
	public void setFeeBackFlg (java.lang.String feeBackFlg) {
		this.feeBackFlg = feeBackFlg;
	}



	/**
	 * Return the value associated with the column: RESERVED
	 */
	public java.lang.String getReserved () {
		return reserved;
	}

	/**
	 * Set the value related to the column: RESERVED
	 * @param reserved the RESERVED value
	 */
	public void setReserved (java.lang.String reserved) {
		this.reserved = reserved;
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



	/**
	 * Return the value associated with the column: REC_CRT_TS
	 */
	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: REC_CRT_TS
	 * @param recCrtTs the REC_CRT_TS value
	 */
	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	/**
	 * @return the whiteListFlag
	 */
	public String getWhiteListFlag() {
		return whiteListFlag;
	}

	/**
	 * @param whiteListFlag the whiteListFlag to set
	 */
	public void setWhiteListFlag(String whiteListFlag) {
		this.whiteListFlag = whiteListFlag;
	}
	
	

	


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchnt.TblMchtSettleInf)) return false;
		else {
			com.huateng.po.mchnt.TblMchtSettleInf tblMchtSettleInf = (com.huateng.po.mchnt.TblMchtSettleInf) obj;
			if (null == this.getMchtNo() || null == tblMchtSettleInf.getMchtNo()) return false;
			else return (this.getMchtNo().equals(tblMchtSettleInf.getMchtNo()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getMchtNo()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getMchtNo().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}