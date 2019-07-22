package com.huateng.po.mchnt;

import java.io.Serializable;

public class TblMchtCheckInf {

	private int hashCode = Integer.MIN_VALUE;

	private com.huateng.po.mchnt.TblMchtCheckInfPK id;

	private java.lang.String checkName;
	private java.lang.String checkInf;
	private java.lang.String crtOprId;
	private java.lang.String updOprId;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;
	private java.lang.String reserve1;

	public com.huateng.po.mchnt.TblMchtCheckInfPK getId () {
		return id;
	}

	public void setId (com.huateng.po.mchnt.TblMchtCheckInfPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	public java.lang.String getCheckName () {
		return checkName;
	}

	public void setCheckName (java.lang.String checkName) {
		this.checkName = checkName;
	}

	public java.lang.String getCheckInf () {
		return checkInf;
	}

	public void setCheckInf (java.lang.String checkInf) {
		this.checkInf = checkInf;
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

	public java.lang.String getReserve1 () {
		return reserve1;
	}

	public void setReserve1 (java.lang.String reserve1) {
		this.reserve1 = reserve1;
	}
}