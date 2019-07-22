package com.huateng.po.accident;

import java.io.Serializable;

public class TblRTxn  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String REF = "TblRTxn";
	public static String PROP_KEY_RSP = "KeyRsp";
	public static String PROP_INST_TIME = "InstTime";
	public static String PROP_SETTLE_DATE = "SettleDate";
	public static String PROP_MSG_SRC_ID = "MsgSrcId";
	public static String PROP_TERM_SSN = "TermSsn";
	public static String PROP_PAN = "Pan";
	public static String PROP_LICENCE_NO = "LicenceNo";
	public static String PROP_MCHT_NO = "MchtNo";
	public static String PROP_RETURN_AMT = "ReturnAmt";
	public static String PROP_ORIG_AMT = "OrigAmt";
	public static String PROP_ORIG_DATE = "OrigDate";
	public static String PROP_RETRIVL_REL = "RetrivlRef";
	public static String PROP_ORIG_FEE = "OrigFee";
	public static String PROP_RETURN_FEE = "ReturnFee";
	public static String PROP_TERM_NO = "TermNo";
	public static String PROP_CREATE_OPR = "CreateOpr";
	public static String PROP_UPDATE_OPR = "UpdateOpr";
	public static String PROP_UPDATE_DTIME = "UpdateDTime";
	public static String PROP_MATCH_STA = "MatchSta";
	public static String PROP_OPR_STA = "OprSta";
	public static String PROP_STLM_FLAG = "StlmFlag";
	public static String PROP_RECORD = "Record";
	public static String PROP_RESERVED = "Reserved";
	
	//primary key
	private TblRTxnPK id;
	
	// fields
	private String keyRsp;
	private String instTime;
	private String settleDate;
	private String msgSrcId;
	private String termSsn;
	private String pan;
	private String licenceNo;
	private String mchtNo;
	private String returnAmt;
	private String origAmt;
	private String origDate;
	private String retrivlRef;
	private String origFee;
	private String returnFee;
	private String termNo;
	private String createOpr;
	private String updateOpr;
	private String updateDTime;
	private String matchSta;
	private String oprSta;
	private String stlmFlag;
	private String record;
	private String reserved;
	/**
	 * @return the id
	 */
	public TblRTxnPK getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(TblRTxnPK id) {
		this.id = id;
	}
	/**
	 * @return the instTime
	 */
	public String getInstTime() {
		return instTime;
	}
	/**
	 * @param instTime the instTime to set
	 */
	public void setInstTime(String instTime) {
		this.instTime = instTime;
	}
	/**
	 * @return the settleTime
	 */
	public String getSettleDate() {
		return settleDate;
	}
	/**
	 * @param settleTime the settleTime to set
	 */
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	/**
	 * @return the msgSrcId
	 */
	public String getMsgSrcId() {
		return msgSrcId;
	}
	/**
	 * @param msgSrcId the msgSrcId to set
	 */
	public void setMsgSrcId(String msgSrcId) {
		this.msgSrcId = msgSrcId;
	}
	/**
	 * @return the termSsn
	 */
	public String getTermSsn() {
		return termSsn;
	}
	/**
	 * @param termSsn the termSsn to set
	 */
	public void setTermSsn(String termSsn) {
		this.termSsn = termSsn;
	}
	/**
	 * @return the pan
	 */
	public String getPan() {
		return pan;
	}
	/**
	 * @param pan the pan to set
	 */
	public void setPan(String pan) {
		this.pan = pan;
	}
	/**
	 * @return the licenceNo
	 */
	public String getLicenceNo() {
		return licenceNo;
	}
	/**
	 * @param licenceNo the licenceNo to set
	 */
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}
	/**
	 * @return the mchtNo
	 */
	public String getMchtNo() {
		return mchtNo;
	}
	/**
	 * @param mchtNo the mchtNo to set
	 */
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	/**
	 * @return the returnAmt
	 */
	public String getReturnAmt() {
		return returnAmt;
	}
	/**
	 * @param returnAmt the returnAmt to set
	 */
	public void setReturnAmt(String returnAmt) {
		this.returnAmt = returnAmt;
	}
	/**
	 * @return the origAmt
	 */
	public String getOrigAmt() {
		return origAmt;
	}
	/**
	 * @param origAmt the origAmt to set
	 */
	public void setOrigAmt(String origAmt) {
		this.origAmt = origAmt;
	}
	/**
	 * @return the origDate
	 */
	public String getOrigDate() {
		return origDate;
	}
	/**
	 * @param origDate the origDate to set
	 */
	public void setOrigDate(String origDate) {
		this.origDate = origDate;
	}
	/**
	 * @return the retrivlRef
	 */
	public String getRetrivlRef() {
		return retrivlRef;
	}
	/**
	 * @param retrivlRef the retrivlRef to set
	 */
	public void setRetrivlRef(String retrivlRef) {
		this.retrivlRef = retrivlRef;
	}
	/**
	 * @return the origFee
	 */
	public String getOrigFee() {
		return origFee;
	}
	/**
	 * @param origFee the origFee to set
	 */
	public void setOrigFee(String origFee) {
		this.origFee = origFee;
	}
	/**
	 * @return the returnFee
	 */
	public String getReturnFee() {
		return returnFee;
	}
	/**
	 * @param returnFee the returnFee to set
	 */
	public void setReturnFee(String returnFee) {
		this.returnFee = returnFee;
	}
	/**
	 * @return the termNo
	 */
	public String getTermNo() {
		return termNo;
	}
	/**
	 * @param termNo the termNo to set
	 */
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	/**
	 * @return the createOpr
	 */
	public String getCreateOpr() {
		return createOpr;
	}
	/**
	 * @param createOpr the createOpr to set
	 */
	public void setCreateOpr(String createOpr) {
		this.createOpr = createOpr;
	}
	/**
	 * @return the updateOpr
	 */
	public String getUpdateOpr() {
		return updateOpr;
	}
	/**
	 * @param updateOpr the updateOpr to set
	 */
	public void setUpdateOpr(String updateOpr) {
		this.updateOpr = updateOpr;
	}
	/**
	 * @return the updateDTime
	 */
	public String getUpdateDTime() {
		return updateDTime;
	}
	/**
	 * @param updateDTime the updateDTime to set
	 */
	public void setUpdateDTime(String updateDTime) {
		this.updateDTime = updateDTime;
	}
	/**
	 * @return the matchSta
	 */
	public String getMatchSta() {
		return matchSta;
	}
	/**
	 * @param matchSta the matchSta to set
	 */
	public void setMatchSta(String matchSta) {
		this.matchSta = matchSta;
	}
	/**
	 * @return the oprSta
	 */
	public String getOprSta() {
		return oprSta;
	}
	/**
	 * @param oprSta the oprSta to set
	 */
	public void setOprSta(String oprSta) {
		this.oprSta = oprSta;
	}
	/**
	 * @return the record
	 */
	public String getRecord() {
		return record;
	}
	/**
	 * @param record the record to set
	 */
	public void setRecord(String record) {
		this.record = record;
	}
	/**
	 * @return the reserved
	 */
	public String getReserved() {
		return reserved;
	}
	/**
	 * @param reserved the reserved to set
	 */
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	/**
	 * @return the keyRsp
	 */
	public String getKeyRsp() {
		return keyRsp;
	}
	/**
	 * @param keyRsp the keyRsp to set
	 */
	public void setKeyRsp(String keyRsp) {
		this.keyRsp = keyRsp;
	}
	public String getStlmFlag() {
		return stlmFlag;
	}
	public void setStlmFlag(String stlmFlag) {
		this.stlmFlag = stlmFlag;
	}
}