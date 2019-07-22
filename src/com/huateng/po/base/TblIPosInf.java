package com.huateng.po.base;

import java.io.Serializable;

public class TblIPosInf implements Serializable {

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String posStage;
	private java.lang.String mchNumber;
	private java.lang.String inmchNumber;
	private java.lang.String outmchNumber;
	private java.lang.String feeCode;
	private java.lang.String crtOprId;
	private java.lang.String updOprId;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;
	private java.lang.String reserve1;
	private java.lang.String reserve2;
	private java.lang.String reserve3;
	private java.lang.String reserve4;

	public java.lang.String getId () {
		return id;
	}

	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	public java.lang.String getPosStage () {
		return posStage;
	}

	public void setPosStage (java.lang.String posStage) {
		this.posStage = posStage;
	}

	public java.lang.String getMchNumber () {
		return mchNumber;
	}

	public void setMchNumber (java.lang.String mchNumber) {
		this.mchNumber = mchNumber;
	}

	public java.lang.String getOutmchNumber() {
		return outmchNumber;
	}

	public void setOutmchNumber(java.lang.String outmchNumber) {
		this.outmchNumber = outmchNumber;
	}
	
	public java.lang.String getInmchNumber () {
		return inmchNumber;
	}

	public void setInmchNumber (java.lang.String inmchNumber) {
		this.inmchNumber = inmchNumber;
	}

	public java.lang.String getFeeCode () {
		return feeCode;
	}

	public void setFeeCode (java.lang.String feeCode) {
		this.feeCode = feeCode;
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

	public java.lang.String getReserve2 () {
		return reserve2;
	}

	public void setReserve2 (java.lang.String reserve2) {
		this.reserve2 = reserve2;
	}

	public java.lang.String getReserve3 () {
		return reserve3;
	}

	public void setReserve3 (java.lang.String reserve3) {
		this.reserve3 = reserve3;
	}

	public java.lang.String getReserve4 () {
		return reserve4;
	}

	public void setReserve4 (java.lang.String reserve4) {
		this.reserve4 = reserve4;
	}
}