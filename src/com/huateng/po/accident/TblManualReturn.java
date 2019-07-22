package com.huateng.po.accident;

import java.io.Serializable;

public class TblManualReturn  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String REF = "TblManualReturn";
	public static String PROP_STATUS = "Status";
	public static String PROP_COUNT = "Count";
	public static String PROP_CARD_ACCP_ID = "CardAccpId";
	public static String PROP_CRT_OPR_ID = "CrtOprId";
	public static String PROP_CRT_TS = "CrtTs";
	public static String PROP_AUDIT_OPR_ID = "AuditOprId";
	public static String PROP_AUDIT_TS = "AuditTs";
	public static String PROP_PAN = "Pan";
	public static String PROP_RESERVED = "Reserved";
	public static String PROP_KEY_RSP = "KeyRsp";
	public static String PROP_AMT_TRANS = "AmtTrans";

	// fields
	private java.lang.String keyRsp;
	private java.lang.String status;
	private java.lang.String pan;
	private java.lang.String cardAccpId;
	private java.lang.String amtTrans;
	private java.lang.String count;
	private java.lang.String crtTs;
	private java.lang.String crtOprId;
	private java.lang.String auditTs;
	private java.lang.String auditOprId;
	private java.lang.String reserved;

	public java.lang.String getKeyRsp () {
		return keyRsp;
	}

	public void setKeyRsp (java.lang.String keyRsp) {
		this.keyRsp = keyRsp;
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

	public java.lang.String getCount () {
		return count;
	}

	public void setCount (java.lang.String count) {
		this.count = count;
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