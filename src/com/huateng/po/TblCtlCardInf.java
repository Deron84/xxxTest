package com.huateng.po;

import java.io.Serializable;

public class TblCtlCardInf implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblCtlCardInf";
	public static String PROP_SA_INIT_ZONE_NO = "SaBrhId";
	public static String PROP_SA_LIMIT_AMT = "SaLimitAmt";
	public static String PROP_SA_MODI_ZONE_NO = "SaLastUpdOpr";
	public static String PROP_SA_MODI_TIME = "SaLastUpdTs";
	public static String PROP_SA_ACTION = "SaAction";
	public static String PROP_SA_CARD_NO = "Id";

	/**
	 * 
	 */
	public TblCtlCardInf() {
		super();
	}

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String saLimitAmt;
	private java.lang.String saAction;
	private java.lang.String saInitZoneNo;
	private java.lang.String saInitOprId;
	private java.lang.String saInitTime;
	private java.lang.String saModiZoneNo;
	private java.lang.String saModiOprId;
	private java.lang.String saModiTime;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="assigned" column="SA_CARD_NO"
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
	 * Return the value associated with the column: SA_LIMIT_AMT
	 */
	public java.lang.String getSaLimitAmt() {
		return saLimitAmt;
	}

	/**
	 * Set the value related to the column: SA_LIMIT_AMT
	 * 
	 * @param saLimitAmt
	 *            the SA_LIMIT_AMT value
	 */
	public void setSaLimitAmt(java.lang.String saLimitAmt) {
		this.saLimitAmt = saLimitAmt;
	}

	/**
	 * Return the value associated with the column: SA_ACTION
	 */
	public java.lang.String getSaAction() {
		return saAction;
	}

	/**
	 * Set the value related to the column: SA_ACTION
	 * 
	 * @param saAction
	 *            the SA_ACTION value
	 */
	public void setSaAction(java.lang.String saAction) {
		this.saAction = saAction;
	}

	/**
	 * @return the saInitZoneNo
	 */
	public java.lang.String getSaInitZoneNo() {
		return saInitZoneNo;
	}

	/**
	 * @param saInitZoneNo
	 *            the saInitZoneNo to set
	 */
	public void setSaInitZoneNo(java.lang.String saInitZoneNo) {
		this.saInitZoneNo = saInitZoneNo;
	}

	/**
	 * @return the saInitOprId
	 */
	public java.lang.String getSaInitOprId() {
		return saInitOprId;
	}

	/**
	 * @param saInitOprId
	 *            the saInitOprId to set
	 */
	public void setSaInitOprId(java.lang.String saInitOprId) {
		this.saInitOprId = saInitOprId;
	}

	/**
	 * @return the saInitTime
	 */
	public java.lang.String getSaInitTime() {
		return saInitTime;
	}

	/**
	 * @param saInitTime
	 *            the saInitTime to set
	 */
	public void setSaInitTime(java.lang.String saInitTime) {
		this.saInitTime = saInitTime;
	}

	/**
	 * @return the saModiZoneNo
	 */
	public java.lang.String getSaModiZoneNo() {
		return saModiZoneNo;
	}

	/**
	 * @param saModiZoneNo
	 *            the saModiZoneNo to set
	 */
	public void setSaModiZoneNo(java.lang.String saModiZoneNo) {
		this.saModiZoneNo = saModiZoneNo;
	}

	/**
	 * @return the saModiOprId
	 */
	public java.lang.String getSaModiOprId() {
		return saModiOprId;
	}

	/**
	 * @param saModiOprId
	 *            the saModiOprId to set
	 */
	public void setSaModiOprId(java.lang.String saModiOprId) {
		this.saModiOprId = saModiOprId;
	}

	/**
	 * @return the saModiTime
	 */
	public java.lang.String getSaModiTime() {
		return saModiTime;
	}

	/**
	 * @param saModiTime
	 *            the saModiTime to set
	 */
	public void setSaModiTime(java.lang.String saModiTime) {
		this.saModiTime = saModiTime;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.huateng.po.TblCtlCardInf))
			return false;
		else {
			com.huateng.po.TblCtlCardInf tblCtlCardInf = (com.huateng.po.TblCtlCardInf) obj;
			if (null == this.getId() || null == tblCtlCardInf.getId())
				return false;
			else
				return (this.getId().equals(tblCtlCardInf.getId()));
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