package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_INFILE_OPT table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_INFILE_OPT"
 */

public abstract class BaseTblInfileOpt  implements Serializable {

	public static String REF = "TblInfileOpt";
	public static String PROP_FAIL_FILE = "FailFile";
	public static String PROP_OPR_ID2 = "OprId2";
	public static String PROP_STLM_DSP = "StlmDsp";
	public static String PROP_OPR_ID1 = "OprId1";
	public static String PROP_STLM_FLAG2 = "StlmFlag2";
	public static String PROP_REC_CRE_TIME = "RecCreTime";
	public static String PROP_SETTLE_AMT = "SettleAmt";
	public static String PROP_TXN_AMT = "TxnAmt";
	public static String PROP_END_DATE = "EndDate";
	public static String PROP_REC_UPD_TIME = "RecUpdTime";
	public static String PROP_START_DATE = "StartDate";
	public static String PROP_OUT_FILE = "OutFile";
	public static String PROP_SUCC_FILE = "SuccFile";
	public static String PROP_STLM_FLAG = "StlmFlag";
	public static String PROP_ID = "Id";
	public static String PROP_SETTLE_FEE1 = "SettleFee1";


	// constructors
	public BaseTblInfileOpt () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblInfileOpt (com.huateng.po.settle.TblInfileOptPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblInfileOpt (
		com.huateng.po.settle.TblInfileOptPK id,
		java.lang.String startDate,
		java.lang.String endDate,
		java.math.BigDecimal settleAmt,
		java.math.BigDecimal txnAmt,
		java.math.BigDecimal settleFee1,
		java.lang.String stlmFlag) {

		this.setId(id);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setSettleAmt(settleAmt);
		this.setTxnAmt(txnAmt);
		this.setSettleFee1(settleFee1);
		this.setStlmFlag(stlmFlag);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.settle.TblInfileOptPK id;

	// fields
	private java.lang.String startDate;
	private java.lang.String endDate;
	private java.math.BigDecimal settleAmt;
	private java.math.BigDecimal txnAmt;
	private java.math.BigDecimal settleFee1;
	private java.lang.String mchntCount;
	private java.lang.String outFile;
	private java.lang.String succFile;
	private java.lang.String failFile;
	private java.lang.String stlmFlag;
	private java.lang.String stlmFlag2;
	private java.lang.String oprId1;
	private java.lang.String oprId2;
	private java.lang.String stlmDsp;
	private java.lang.String recCreTime;
	private java.lang.String recUpdTime;
	
	private java.math.BigDecimal settleAmtDis;
	private java.math.BigDecimal txnAmtDis;
	private java.math.BigDecimal settleFee1Dis;
	private java.lang.String mchntCountDis;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.settle.TblInfileOptPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.settle.TblInfileOptPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
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
	 * Return the value associated with the column: SETTLE_AMT
	 */
	public java.math.BigDecimal getSettleAmt () {
		return settleAmt;
	}

	/**
	 * Set the value related to the column: SETTLE_AMT
	 * @param settleAmt the SETTLE_AMT value
	 */
	public void setSettleAmt (java.math.BigDecimal settleAmt) {
		this.settleAmt = settleAmt;
	}



	/**
	 * Return the value associated with the column: TXN_AMT
	 */
	public java.math.BigDecimal getTxnAmt () {
		return txnAmt;
	}

	/**
	 * Set the value related to the column: TXN_AMT
	 * @param txnAmt the TXN_AMT value
	 */
	public void setTxnAmt (java.math.BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}



	/**
	 * Return the value associated with the column: SETTLE_FEE1
	 */
	public java.math.BigDecimal getSettleFee1 () {
		return settleFee1;
	}

	/**
	 * Set the value related to the column: SETTLE_FEE1
	 * @param settleFee1 the SETTLE_FEE1 value
	 */
	public void setSettleFee1 (java.math.BigDecimal settleFee1) {
		this.settleFee1 = settleFee1;
	}



	/**
	 * Return the value associated with the column: OUT_FILE
	 */
	public java.lang.String getOutFile () {
		return outFile;
	}

	/**
	 * Set the value related to the column: OUT_FILE
	 * @param outFile the OUT_FILE value
	 */
	public void setOutFile (java.lang.String outFile) {
		this.outFile = outFile;
	}



	/**
	 * Return the value associated with the column: SUCC_FILE
	 */
	public java.lang.String getSuccFile () {
		return succFile;
	}

	/**
	 * Set the value related to the column: SUCC_FILE
	 * @param succFile the SUCC_FILE value
	 */
	public void setSuccFile (java.lang.String succFile) {
		this.succFile = succFile;
	}



	/**
	 * Return the value associated with the column: FAIL_FILE
	 */
	public java.lang.String getFailFile () {
		return failFile;
	}

	/**
	 * Set the value related to the column: FAIL_FILE
	 * @param failFile the FAIL_FILE value
	 */
	public void setFailFile (java.lang.String failFile) {
		this.failFile = failFile;
	}



	/**
	 * Return the value associated with the column: STLM_FLAG
	 */
	public java.lang.String getStlmFlag () {
		return stlmFlag;
	}

	/**
	 * Set the value related to the column: STLM_FLAG
	 * @param stlmFlag the STLM_FLAG value
	 */
	public void setStlmFlag (java.lang.String stlmFlag) {
		this.stlmFlag = stlmFlag;
	}



	/**
	 * Return the value associated with the column: STLM_FLAG2
	 */
	public java.lang.String getStlmFlag2 () {
		return stlmFlag2;
	}

	/**
	 * Set the value related to the column: STLM_FLAG2
	 * @param stlmFlag2 the STLM_FLAG2 value
	 */
	public void setStlmFlag2 (java.lang.String stlmFlag2) {
		this.stlmFlag2 = stlmFlag2;
	}



	/**
	 * Return the value associated with the column: OPR_ID1
	 */
	public java.lang.String getOprId1 () {
		return oprId1;
	}

	/**
	 * Set the value related to the column: OPR_ID1
	 * @param oprId1 the OPR_ID1 value
	 */
	public void setOprId1 (java.lang.String oprId1) {
		this.oprId1 = oprId1;
	}



	/**
	 * Return the value associated with the column: OPR_ID2
	 */
	public java.lang.String getOprId2 () {
		return oprId2;
	}

	/**
	 * Set the value related to the column: OPR_ID2
	 * @param oprId2 the OPR_ID2 value
	 */
	public void setOprId2 (java.lang.String oprId2) {
		this.oprId2 = oprId2;
	}



	/**
	 * Return the value associated with the column: STLM_DSP
	 */
	public java.lang.String getStlmDsp () {
		return stlmDsp;
	}

	/**
	 * Set the value related to the column: STLM_DSP
	 * @param stlmDsp the STLM_DSP value
	 */
	public void setStlmDsp (java.lang.String stlmDsp) {
		this.stlmDsp = stlmDsp;
	}



	/**
	 * Return the value associated with the column: REC_CRE_TIME
	 */
	public java.lang.String getRecCreTime () {
		return recCreTime;
	}

	/**
	 * Set the value related to the column: REC_CRE_TIME
	 * @param recCreTime the REC_CRE_TIME value
	 */
	public void setRecCreTime (java.lang.String recCreTime) {
		this.recCreTime = recCreTime;
	}



	/**
	 * Return the value associated with the column: REC_UPD_TIME
	 */
	public java.lang.String getRecUpdTime () {
		return recUpdTime;
	}

	/**
	 * Set the value related to the column: REC_UPD_TIME
	 * @param recUpdTime the REC_UPD_TIME value
	 */
	public void setRecUpdTime (java.lang.String recUpdTime) {
		this.recUpdTime = recUpdTime;
	}

	/**
	 * @return the mchntCount
	 */
	public java.lang.String getMchntCount() {
		return mchntCount;
	}

	/**
	 * @param mchntCount the mchntCount to set
	 */
	public void setMchntCount(java.lang.String mchntCount) {
		this.mchntCount = mchntCount;
	}

	/**
	 * @return the settleAmtDis
	 */
	public java.math.BigDecimal getSettleAmtDis() {
		return settleAmtDis;
	}

	/**
	 * @param settleAmtDis the settleAmtDis to set
	 */
	public void setSettleAmtDis(java.math.BigDecimal settleAmtDis) {
		this.settleAmtDis = settleAmtDis;
	}

	/**
	 * @return the txnAmtDis
	 */
	public java.math.BigDecimal getTxnAmtDis() {
		return txnAmtDis;
	}

	/**
	 * @param txnAmtDis the txnAmtDis to set
	 */
	public void setTxnAmtDis(java.math.BigDecimal txnAmtDis) {
		this.txnAmtDis = txnAmtDis;
	}

	/**
	 * @return the settleFee1Dis
	 */
	public java.math.BigDecimal getSettleFee1Dis() {
		return settleFee1Dis;
	}

	/**
	 * @param settleFee1Dis the settleFee1Dis to set
	 */
	public void setSettleFee1Dis(java.math.BigDecimal settleFee1Dis) {
		this.settleFee1Dis = settleFee1Dis;
	}

	/**
	 * @return the mchntCountDis
	 */
	public java.lang.String getMchntCountDis() {
		return mchntCountDis;
	}

	/**
	 * @param mchntCountDis the mchntCountDis to set
	 */
	public void setMchntCountDis(java.lang.String mchntCountDis) {
		this.mchntCountDis = mchntCountDis;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.settle.TblInfileOpt)) return false;
		else {
			com.huateng.po.settle.TblInfileOpt tblInfileOpt = (com.huateng.po.settle.TblInfileOpt) obj;
			if (null == this.getId() || null == tblInfileOpt.getId()) return false;
			else return (this.getId().equals(tblInfileOpt.getId()));
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