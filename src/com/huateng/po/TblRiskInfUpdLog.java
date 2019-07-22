package com.huateng.po;

import java.io.Serializable;

public class TblRiskInfUpdLog implements Serializable {
	private static final long serialVersionUID = 1L;

	protected void initialize() {
	}

	/**
	 * 
	 */
	public TblRiskInfUpdLog() {
		super();
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String saModelKind;
	private java.lang.String saFieldName;
	private java.lang.String saFieldValueBF;
	private java.lang.String saFieldValue;
	private java.lang.String modiZoneNo;
	private java.lang.String modiOprId;
	private java.lang.String modiTime;
	

	/**
	 * @return the id
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * @return the saModelKind
	 */
	public java.lang.String getSaModelKind() {
		return saModelKind;
	}

	/**
	 * @param saModelKind the saModelKind to set
	 */
	public void setSaModelKind(java.lang.String saModelKind) {
		this.saModelKind = saModelKind;
	}

	/**
	 * @return the saFieldName
	 */
	public java.lang.String getSaFieldName() {
		return saFieldName;
	}

	/**
	 * @param saFieldName the saFieldName to set
	 */
	public void setSaFieldName(java.lang.String saFieldName) {
		this.saFieldName = saFieldName;
	}

	/**
	 * @return the saFieldValueBF
	 */
	public java.lang.String getSaFieldValueBF() {
		return saFieldValueBF;
	}

	/**
	 * @param saFieldValueBF the saFieldValueBF to set
	 */
	public void setSaFieldValueBF(java.lang.String saFieldValueBF) {
		this.saFieldValueBF = saFieldValueBF;
	}

	/**
	 * @return the saFieldValue
	 */
	public java.lang.String getSaFieldValue() {
		return saFieldValue;
	}

	/**
	 * @param saFieldValue the saFieldValue to set
	 */
	public void setSaFieldValue(java.lang.String saFieldValue) {
		this.saFieldValue = saFieldValue;
	}

	/**
	 * @return the modiZoneNo
	 */
	public java.lang.String getModiZoneNo() {
		return modiZoneNo;
	}

	/**
	 * @return the modiOprId
	 */
	public java.lang.String getModiOprId() {
		return modiOprId;
	}

	/**
	 * @param modiOprId the modiOprId to set
	 */
	public void setModiOprId(java.lang.String modiOprId) {
		this.modiOprId = modiOprId;
	}

	/**
	 * @param modiZoneNo the modiZoneNo to set
	 */
	public void setModiZoneNo(java.lang.String modiZoneNo) {
		this.modiZoneNo = modiZoneNo;
	}

	/**
	 * @return the modiTime
	 */
	public java.lang.String getModiTime() {
		return modiTime;
	}

	/**
	 * @param modiTime the modiTime to set
	 */
	public void setModiTime(java.lang.String modiTime) {
		this.modiTime = modiTime;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof TblRiskInfUpdLog))
			return false;
		else {
			TblRiskInfUpdLog tblRiskInf = (TblRiskInfUpdLog) obj;
			if (null == this.getId() || null == tblRiskInf.getId())
				return false;
			else
				return (this.getId().equals(tblRiskInf.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}
}