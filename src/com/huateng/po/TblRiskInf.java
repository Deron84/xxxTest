package com.huateng.po;

import java.io.Serializable;

public class TblRiskInf implements Serializable {
	private static final long serialVersionUID = 1L;

	protected void initialize() {
	}

	/**
	 * 
	 */
	public TblRiskInf() {
		super();
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String saBeUse;
	private java.lang.String saAction;
	private java.lang.String saLimitNum;
	private java.lang.String SaLimitAmountSingle;
	private java.lang.String SaLimitAmountTotle;
	private java.lang.String modiZoneNo;
	private java.lang.String modiOprId;
	private java.lang.String modiTime;
	private java.lang.String saBranchCode;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="assigned" column="sa_model_kind"
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: sa_be_use
	 */
	public java.lang.String getSaBeUse() {
		return saBeUse;
	}

	/**
	 * Set the value related to the column: sa_be_use
	 * 
	 * @param saBeUse
	 *            the sa_be_use value
	 */
	public void setSaBeUse(java.lang.String saBeUse) {
		this.saBeUse = saBeUse;
	}

	/**
	 * Return the value associated with the column: sa_action
	 */
	public java.lang.String getSaAction() {
		return saAction;
	}

	/**
	 * Set the value related to the column: sa_action
	 * 
	 * @param saAction
	 *            the sa_action value
	 */
	public void setSaAction(java.lang.String saAction) {
		this.saAction = saAction;
	}

	/**
	 * Return the value associated with the column: sa_limit_num
	 */
	public java.lang.String getSaLimitNum() {
		return saLimitNum;
	}

	/**
	 * Set the value related to the column: sa_limit_num
	 * 
	 * @param saLimitNum
	 *            the sa_limit_num value
	 */
	public void setSaLimitNum(java.lang.String saLimitNum) {
		this.saLimitNum = saLimitNum;
	}

	public java.lang.String getSaLimitAmountSingle() {
		return SaLimitAmountSingle;
	}

	public void setSaLimitAmountSingle(java.lang.String saLimitAmountSingle) {
		SaLimitAmountSingle = saLimitAmountSingle;
	}

	public java.lang.String getSaLimitAmountTotle() {
		return SaLimitAmountTotle;
	}

	public void setSaLimitAmountTotle(java.lang.String saLimitAmountTotle) {
		SaLimitAmountTotle = saLimitAmountTotle;
	}

	/**
	 * @return the modiZoneNo
	 */
	public java.lang.String getModiZoneNo() {
		return modiZoneNo;
	}

	/**
	 * @param modiZoneNo
	 *            the modiZoneNo to set
	 */
	public void setModiZoneNo(java.lang.String modiZoneNo) {
		this.modiZoneNo = modiZoneNo;
	}

	/**
	 * @return the modiOprId
	 */
	public java.lang.String getModiOprId() {
		return modiOprId;
	}

	/**
	 * @param modiOprId
	 *            the modiOprId to set
	 */
	public void setModiOprId(java.lang.String modiOprId) {
		this.modiOprId = modiOprId;
	}

	/**
	 * @return the modiTime
	 */
	public java.lang.String getModiTime() {
		return modiTime;
	}

	/**
	 * @param modiTime
	 *            the modiTime to set
	 */
	public void setModiTime(java.lang.String modiTime) {
		this.modiTime = modiTime;
	}

	/**
	 * @return the saBranchCode
	 */
	public java.lang.String getSaBranchCode() {
		return saBranchCode;
	}

	/**
	 * @param saBranchCode
	 *            the saBranchCode to set
	 */
	public void setSaBranchCode(java.lang.String saBranchCode) {
		this.saBranchCode = saBranchCode;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.huateng.po.TblRiskInf))
			return false;
		else {
			com.huateng.po.TblRiskInf tblRiskInf = (com.huateng.po.TblRiskInf) obj;
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