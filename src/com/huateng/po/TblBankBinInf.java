package com.huateng.po;

import java.io.Serializable;

public class TblBankBinInf  implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	public static String REF = "TblBankBinInf";
	public static String PROP_REC_OPR_ID = "RecOprId";
	public static String PROP_ACC1_LEN = "Acc1Len";
	public static String PROP_BIN_TNUM = "BinTnum";
	public static String PROP_ACC2_OFFSET = "Acc2Offset";
	public static String PROP_ACC2_LEN = "Acc2Len";
	public static String PROP_BIN_LEN = "BinLen";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_BIN_OFF_SET = "BinOffSet";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_BIN_STA_NO = "BinStaNo";
	public static String PROP_BIN_END_NO = "BinEndNo";
	public static String PROP_INS_ID_CD = "InsIdCd";
	public static String PROP_REC_UPD_OPR = "RecUpdOpr";
	public static String PROP_CARD_DIS = "CardDis";
	public static String PROP_ACC2_TNUM = "Acc2Tnum";
	public static String PROP_ACC1_OFFSET = "Acc1Offset";
	public static String PROP_ID = "id";
	public static String PROP_ACC1_TNUM = "Acc1Tnum";
	public static String PROP_CARD_TP = "CardTp";

	/* [CONSTRUCTOR MARKER BEGIN] */
	public TblBankBinInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblBankBinInf (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public TblBankBinInf (
			java.lang.Integer id,
			java.lang.String insIdCd,
			java.lang.String binOffSet,
			java.lang.String binLen,
			java.lang.String binStaNo,
			java.lang.String binEndNo,
			java.lang.String cardTp,
			java.lang.String binTnum) {

			this.setId(id);
			this.setInsIdCd(insIdCd);
			this.setBinOffSet(binOffSet);
			this.setBinLen(binLen);
			this.setBinStaNo(binStaNo);
			this.setBinEndNo(binEndNo);
			this.setCardTp(cardTp);
			this.setBinTnum(binTnum);
			initialize();
	}
	
	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String insIdCd;
	private java.lang.String binOffSet;
	private java.lang.String binLen;
	private java.lang.String binStaNo;
	private java.lang.String binEndNo;
	private java.lang.String cardTp;
	private java.lang.String binTnum;
	private java.lang.String acc1Offset;
	private java.lang.String acc1Len;
	private java.lang.String acc1Tnum;
	private java.lang.String acc2Offset;
	private java.lang.String acc2Len;
	private java.lang.String acc2Tnum;
	private java.lang.String cardDis;
	private java.lang.String recOprId;
	private java.lang.String recUpdOpr;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;
	
	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="IND"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: INS_ID_CD
	 */
	public java.lang.String getInsIdCd () {
		return insIdCd;
	}

	/**
	 * Set the value related to the column: INS_ID_CD
	 * @param insIdCd the INS_ID_CD value
	 */
	public void setInsIdCd (java.lang.String insIdCd) {
		this.insIdCd = insIdCd;
	}



	/**
	 * Return the value associated with the column: BIN_OFFSET
	 */
	public java.lang.String getBinOffSet () {
		return binOffSet;
	}

	/**
	 * Set the value related to the column: BIN_OFFSET
	 * @param binOffSet the BIN_OFFSET value
	 */
	public void setBinOffSet (java.lang.String binOffSet) {
		this.binOffSet = binOffSet;
	}



	/**
	 * Return the value associated with the column: BIN_LEN
	 */
	public java.lang.String getBinLen () {
		return binLen;
	}

	/**
	 * Set the value related to the column: BIN_LEN
	 * @param binLen the BIN_LEN value
	 */
	public void setBinLen (java.lang.String binLen) {
		this.binLen = binLen;
	}



	/**
	 * Return the value associated with the column: BIN_STA_NO
	 */
	public java.lang.String getBinStaNo () {
		return binStaNo;
	}

	/**
	 * Set the value related to the column: BIN_STA_NO
	 * @param binStaNo the BIN_STA_NO value
	 */
	public void setBinStaNo (java.lang.String binStaNo) {
		this.binStaNo = binStaNo;
	}



	/**
	 * Return the value associated with the column: BIN_END_NO
	 */
	public java.lang.String getBinEndNo () {
		return binEndNo;
	}

	/**
	 * Set the value related to the column: BIN_END_NO
	 * @param binEndNo the BIN_END_NO value
	 */
	public void setBinEndNo (java.lang.String binEndNo) {
		this.binEndNo = binEndNo;
	}



	/**
	 * Return the value associated with the column: CARD_TP
	 */
	public java.lang.String getCardTp () {
		return cardTp;
	}

	/**
	 * Set the value related to the column: CARD_TP
	 * @param cardTp the CARD_TP value
	 */
	public void setCardTp (java.lang.String cardTp) {
		this.cardTp = cardTp;
	}



	/**
	 * Return the value associated with the column: BIN_TNUM
	 */
	public java.lang.String getBinTnum () {
		return binTnum;
	}

	/**
	 * Set the value related to the column: BIN_TNUM
	 * @param binTnum the BIN_TNUM value
	 */
	public void setBinTnum (java.lang.String binTnum) {
		this.binTnum = binTnum;
	}



	/**
	 * Return the value associated with the column: ACC1_OFFSET
	 */
	public java.lang.String getAcc1Offset () {
		return acc1Offset;
	}

	/**
	 * Set the value related to the column: ACC1_OFFSET
	 * @param acc1Offset the ACC1_OFFSET value
	 */
	public void setAcc1Offset (java.lang.String acc1Offset) {
		this.acc1Offset = acc1Offset;
	}



	/**
	 * Return the value associated with the column: ACC1_LEN
	 */
	public java.lang.String getAcc1Len () {
		return acc1Len;
	}

	/**
	 * Set the value related to the column: ACC1_LEN
	 * @param acc1Len the ACC1_LEN value
	 */
	public void setAcc1Len (java.lang.String acc1Len) {
		this.acc1Len = acc1Len;
	}



	/**
	 * Return the value associated with the column:  ACC1_TNUM
	 */
	public java.lang.String getAcc1Tnum () {
		return acc1Tnum;
	}

	/**
	 * Set the value related to the column:  ACC1_TNUM
	 * @param acc1Tnum the  ACC1_TNUM value
	 */
	public void setAcc1Tnum (java.lang.String acc1Tnum) {
		this.acc1Tnum = acc1Tnum;
	}



	/**
	 * Return the value associated with the column: ACC2_OFFSET
	 */
	public java.lang.String getAcc2Offset () {
		return acc2Offset;
	}

	/**
	 * Set the value related to the column: ACC2_OFFSET
	 * @param acc2Offset the ACC2_OFFSET value
	 */
	public void setAcc2Offset (java.lang.String acc2Offset) {
		this.acc2Offset = acc2Offset;
	}



	/**
	 * Return the value associated with the column: ACC2_LEN
	 */
	public java.lang.String getAcc2Len () {
		return acc2Len;
	}

	/**
	 * Set the value related to the column: ACC2_LEN
	 * @param acc2Len the ACC2_LEN value
	 */
	public void setAcc2Len (java.lang.String acc2Len) {
		this.acc2Len = acc2Len;
	}



	/**
	 * Return the value associated with the column: ACC2_TNUM 
	 */
	public java.lang.String getAcc2Tnum () {
		return acc2Tnum;
	}

	/**
	 * Set the value related to the column: ACC2_TNUM 
	 * @param acc2Tnum the ACC2_TNUM  value
	 */
	public void setAcc2Tnum (java.lang.String acc2Tnum) {
		this.acc2Tnum = acc2Tnum;
	}



	/**
	 * Return the value associated with the column: CARD_DIS
	 */
	public java.lang.String getCardDis () {
		return cardDis;
	}

	/**
	 * Set the value related to the column: CARD_DIS
	 * @param cardDis the CARD_DIS value
	 */
	public void setCardDis (java.lang.String cardDis) {
		this.cardDis = cardDis;
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


	public java.lang.String getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public java.lang.String getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblBankBinInf)) return false;
		else {
			com.huateng.po.TblBankBinInf tblBankBinInf = (com.huateng.po.TblBankBinInf) obj;
			if (null == this.getId() || null == tblBankBinInf.getId()) return false;
			else return (this.getId().equals(tblBankBinInf.getId()));
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

	/* [CONSTRUCTOR MARKER END] */

}