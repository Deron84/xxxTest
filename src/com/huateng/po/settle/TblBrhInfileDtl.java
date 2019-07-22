package com.huateng.po.settle;

import java.io.Serializable;



public class TblBrhInfileDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblBrhInfileDtl";
	public static String PROP_REC_OPR_ID = "RecOprId";
	public static String PROP_SETTLE_AMT5 = "SettleAmt5";
	public static String PROP_SETTLE_AMT4 = "SettleAmt4";
	public static String PROP_SETTLE_AMT3 = "SettleAmt3";
	public static String PROP_SETTLE_AMT2 = "SettleAmt2";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_SETTLE_AMT1 = "SettleAmt1";
	public static String PROP_FAIL_RESN = "FailResn";
	public static String PROP_SETTLE_FLAG = "SettleFlag";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_REC_UPD_USR = "RecUpdUsr";
	public static String PROP_SEND_NUM = "SendNum";
	public static String PROP_MISC1 = "Misc1";
	public static String PROP_ID = "Id";


	// constructors
	public TblBrhInfileDtl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblBrhInfileDtl (com.huateng.po.settle.TblBrhInfileDtlPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.settle.TblBrhInfileDtlPK id;

	// fields
	private java.math.BigDecimal settleAmt1;
	private java.math.BigDecimal settleAmt2;
	private java.math.BigDecimal settleAmt3;
	private java.math.BigDecimal settleAmt4;
	private java.math.BigDecimal settleAmt5;
	private java.lang.Integer sendNum;
	private java.lang.String misc1;
	private java.lang.String settleFlag;
	private java.lang.String failResn;
	private java.lang.String recOprId;
	private java.lang.String recUpdUsr;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.settle.TblBrhInfileDtlPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.settle.TblBrhInfileDtlPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: SETTLE_AMT_1
	 */
	public java.math.BigDecimal getSettleAmt1 () {
		return settleAmt1;
	}

	/**
	 * Set the value related to the column: SETTLE_AMT_1
	 * @param settleAmt1 the SETTLE_AMT_1 value
	 */
	public void setSettleAmt1 (java.math.BigDecimal settleAmt1) {
		this.settleAmt1 = settleAmt1;
	}



	/**
	 * Return the value associated with the column: SETTLE_AMT_2
	 */
	public java.math.BigDecimal getSettleAmt2 () {
		return settleAmt2;
	}

	/**
	 * Set the value related to the column: SETTLE_AMT_2
	 * @param settleAmt2 the SETTLE_AMT_2 value
	 */
	public void setSettleAmt2 (java.math.BigDecimal settleAmt2) {
		this.settleAmt2 = settleAmt2;
	}



	/**
	 * Return the value associated with the column: SETTLE_AMT_3
	 */
	public java.math.BigDecimal getSettleAmt3 () {
		return settleAmt3;
	}

	/**
	 * Set the value related to the column: SETTLE_AMT_3
	 * @param settleAmt3 the SETTLE_AMT_3 value
	 */
	public void setSettleAmt3 (java.math.BigDecimal settleAmt3) {
		this.settleAmt3 = settleAmt3;
	}



	/**
	 * Return the value associated with the column: SETTLE_AMT_4
	 */
	public java.math.BigDecimal getSettleAmt4 () {
		return settleAmt4;
	}

	/**
	 * Set the value related to the column: SETTLE_AMT_4
	 * @param settleAmt4 the SETTLE_AMT_4 value
	 */
	public void setSettleAmt4 (java.math.BigDecimal settleAmt4) {
		this.settleAmt4 = settleAmt4;
	}



	/**
	 * Return the value associated with the column: SETTLE_AMT_5
	 */
	public java.math.BigDecimal getSettleAmt5 () {
		return settleAmt5;
	}

	/**
	 * Set the value related to the column: SETTLE_AMT_5
	 * @param settleAmt5 the SETTLE_AMT_5 value
	 */
	public void setSettleAmt5 (java.math.BigDecimal settleAmt5) {
		this.settleAmt5 = settleAmt5;
	}



	/**
	 * Return the value associated with the column: SEND_NUM
	 */
	public java.lang.Integer getSendNum () {
		return sendNum;
	}

	/**
	 * Set the value related to the column: SEND_NUM
	 * @param sendNum the SEND_NUM value
	 */
	public void setSendNum (java.lang.Integer sendNum) {
		this.sendNum = sendNum;
	}



	/**
	 * Return the value associated with the column: MISC_1
	 */
	public java.lang.String getMisc1 () {
		return misc1;
	}

	/**
	 * Set the value related to the column: MISC_1
	 * @param misc1 the MISC_1 value
	 */
	public void setMisc1 (java.lang.String misc1) {
		this.misc1 = misc1;
	}



	/**
	 * Return the value associated with the column: SETTLE_FLAG
	 */
	public java.lang.String getSettleFlag () {
		return settleFlag;
	}

	/**
	 * Set the value related to the column: SETTLE_FLAG
	 * @param settleFlag the SETTLE_FLAG value
	 */
	public void setSettleFlag (java.lang.String settleFlag) {
		this.settleFlag = settleFlag;
	}



	/**
	 * Return the value associated with the column: FAIL_RESN
	 */
	public java.lang.String getFailResn () {
		return failResn;
	}

	/**
	 * Set the value related to the column: FAIL_RESN
	 * @param failResn the FAIL_RESN value
	 */
	public void setFailResn (java.lang.String failResn) {
		this.failResn = failResn;
	}



	/**
	 * Return the value associated with the column: REC_OPR_ID
	 */
	public java.lang.String getRecOprId () {
		return recOprId;
	}

	/**
	 * Set the value related to the column: REC_OPR_ID
	 * @param recOprId the REC_OPR_ID value
	 */
	public void setRecOprId (java.lang.String recOprId) {
		this.recOprId = recOprId;
	}



	/**
	 * Return the value associated with the column: REC_UPD_USR
	 */
	public java.lang.String getRecUpdUsr () {
		return recUpdUsr;
	}

	/**
	 * Set the value related to the column: REC_UPD_USR
	 * @param recUpdUsr the REC_UPD_USR value
	 */
	public void setRecUpdUsr (java.lang.String recUpdUsr) {
		this.recUpdUsr = recUpdUsr;
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
		if (!(obj instanceof com.huateng.po.settle.TblBrhInfileDtl)) return false;
		else {
			com.huateng.po.settle.TblBrhInfileDtl tblBrhInfileDtl = (com.huateng.po.settle.TblBrhInfileDtl) obj;
			if (null == this.getId() || null == tblBrhInfileDtl.getId()) return false;
			else return (this.getId().equals(tblBrhInfileDtl.getId()));
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