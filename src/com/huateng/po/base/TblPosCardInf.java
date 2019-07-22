package com.huateng.po.base;

import java.io.Serializable;

public class TblPosCardInf  implements Serializable {

	public static String REF = "TblPosCardInf";
	public static String PROP_UPD_OPR_ID = "UpdOprId";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_HOLDER_ID = "HolderId";
	public static String PROP_BRH_ID = "BrhId";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_RESERVED1 = "Reserved1";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_STAT = "Stat";
	public static String PROP_HOLDER_TEL = "HolderTel";
	public static String PROP_RESERVED2 = "Reserved2";
	public static String PROP_HOLDER_NAME = "HolderName";
	public static String PROP_CRT_OPR_ID = "CrtOprId";
	public static String PROP_MCHT_NO = "MchtNo";
	public static String PROP_STOP_TIME = "StopTime";
	public static String PROP_ID = "Id";
	public static String PROP_SMS_QUOTA = "SmsQuota";
	public static String PROP_START_TIME = "StartTime";

	public TblPosCardInf () {
		initialize();
	}

	public TblPosCardInf (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String mchtNo;
	private java.lang.String termId;
	private java.lang.String brhId;
	private java.lang.String holderName;
	private java.lang.String holderId;
	private java.lang.String holderTel;
	private java.lang.String stat;
	private java.lang.String smsQuota;
	private java.lang.String startTime;
	private java.lang.String stopTime;
	private java.lang.String crtOprId;
	private java.lang.String updOprId;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;
	private java.lang.String reserved1;
	private java.lang.String reserved2;

	public java.lang.String getId () {
		return id;
	}

	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	public java.lang.String getMchtNo () {
		return mchtNo;
	}

	public void setMchtNo (java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public java.lang.String getTermId () {
		return termId;
	}

	public void setTermId (java.lang.String termId) {
		this.termId = termId;
	}

	public java.lang.String getBrhId () {
		return brhId;
	}

	public void setBrhId (java.lang.String brhId) {
		this.brhId = brhId;
	}

	public java.lang.String getHolderName () {
		return holderName;
	}

	public void setHolderName (java.lang.String holderName) {
		this.holderName = holderName;
	}

	public java.lang.String getHolderId () {
		return holderId;
	}

	public void setHolderId (java.lang.String holderId) {
		this.holderId = holderId;
	}

	public java.lang.String getHolderTel () {
		return holderTel;
	}

	public void setHolderTel (java.lang.String holderTel) {
		this.holderTel = holderTel;
	}

	public java.lang.String getStat () {
		return stat;
	}

	public void setStat (java.lang.String stat) {
		this.stat = stat;
	}

	public java.lang.String getSmsQuota () {
		return smsQuota;
	}

	public void setSmsQuota (java.lang.String smsQuota) {
		this.smsQuota = smsQuota;
	}

	public java.lang.String getStartTime () {
		return startTime;
	}

	public void setStartTime (java.lang.String startTime) {
		this.startTime = startTime;
	}

	public java.lang.String getStopTime () {
		return stopTime;
	}

	public void setStopTime (java.lang.String stopTime) {
		this.stopTime = stopTime;
	}

	public java.lang.String getCrtOprId () {
		return crtOprId;
	}

	public void setCrtOprId (java.lang.String crtOprId) {
		this.crtOprId = crtOprId;
	}

	public java.lang.String getUpdOprId () {
		return updOprId;
	}

	public void setUpdOprId (java.lang.String updOprId) {
		this.updOprId = updOprId;
	}

	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public java.lang.String getReserved1 () {
		return reserved1;
	}

	public void setReserved1 (java.lang.String reserved1) {
		this.reserved1 = reserved1;
	}

	public java.lang.String getReserved2 () {
		return reserved2;
	}

	public void setReserved2 (java.lang.String reserved2) {
		this.reserved2 = reserved2;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.base.TblPosCardInf)) return false;
		else {
			com.huateng.po.base.TblPosCardInf tblPosCardInf = (com.huateng.po.base.TblPosCardInf) obj;
			if (null == this.getId() || null == tblPosCardInf.getId()) return false;
			else return (this.getId().equals(tblPosCardInf.getId()));
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