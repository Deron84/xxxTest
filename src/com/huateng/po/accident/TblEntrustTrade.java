package com.huateng.po.accident;

import java.io.Serializable;

public class TblEntrustTrade implements Serializable {

	public static String REF = "TblEntrustTrade";
	public static String PROP_STATUS = "Status";
	public static String PROP_DEFER_DAY = "DeferDay";
	public static String PROP_CARD_ACCP_ID = "CardAccpId";
	public static String PROP_CRT_OPR_ID = "CrtOprId";
	public static String PROP_CRT_TS = "CrtTs";
	public static String PROP_AUDIT_OPR_ID = "AuditOprId";
	public static String PROP_AUDIT_TS = "AuditTs";
	public static String PROP_COUNT_DAY = "CountDay";
	public static String PROP_PAN = "Pan";
	public static String PROP_RESERVED = "Reserved";
	public static String PROP_ID = "Id";
	public static String PROP_AMT_TRANS = "AmtTrans";

	private java.lang.String id;

	private java.lang.String status;
	private java.lang.String pan;
	private java.lang.String cardAccpId;
	private java.lang.String amtTrans;
	private java.lang.String countDay;
	private java.lang.String deferDay;
	private java.lang.String crtTs;
	private java.lang.String crtOprId;
	private java.lang.String auditTs;
	private java.lang.String auditOprId;
	private java.lang.String reserved;

	public java.lang.String getId () {
		return id;
	}

	public void setId (java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getStatus () {
		return status;
	}

	public void setStatus (java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getPan () {
		return pan;
	}

	public void setPan (java.lang.String pan) {
		this.pan = pan;
	}

	public java.lang.String getCardAccpId () {
		return cardAccpId;
	}

	public void setCardAccpId (java.lang.String cardAccpId) {
		this.cardAccpId = cardAccpId;
	}

	public java.lang.String getAmtTrans () {
		return amtTrans;
	}

	public void setAmtTrans (java.lang.String amtTrans) {
		this.amtTrans = amtTrans;
	}

	public java.lang.String getCountDay () {
		return countDay;
	}

	public void setCountDay (java.lang.String countDay) {
		this.countDay = countDay;
	}

	public java.lang.String getDeferDay () {
		return deferDay;
	}

	public void setDeferDay (java.lang.String deferDay) {
		this.deferDay = deferDay;
	}

	public java.lang.String getCrtTs () {
		return crtTs;
	}

	public void setCrtTs (java.lang.String crtTs) {
		this.crtTs = crtTs;
	}

	public java.lang.String getCrtOprId () {
		return crtOprId;
	}

	public void setCrtOprId (java.lang.String crtOprId) {
		this.crtOprId = crtOprId;
	}

	public java.lang.String getAuditTs () {
		return auditTs;
	}

	public void setAuditTs (java.lang.String auditTs) {
		this.auditTs = auditTs;
	}

	public java.lang.String getAuditOprId () {
		return auditOprId;
	}

	public void setAuditOprId (java.lang.String auditOprId) {
		this.auditOprId = auditOprId;
	}

	public java.lang.String getReserved () {
		return reserved;
	}

	public void setReserved (java.lang.String reserved) {
		this.reserved = reserved;
	}
}