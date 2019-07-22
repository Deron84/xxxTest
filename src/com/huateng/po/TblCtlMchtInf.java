package com.huateng.po;

import java.io.Serializable;

public class TblCtlMchtInf implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblCtlMchtInf";
	public static String PROP_SA_BRH_ID = "SaBrhId";
	public static String PROP_SA_MER_EN_NAME = "SaMerEnName";
	public static String PROP_SA_LIMIT_AMT = "SaLimitAmt";
	public static String PROP_SA_LAST_UPD_OPR = "SaLastUpdOpr";
	public static String PROP_SA_LAST_UPD_TS = "SaLastUpdTs";
	public static String PROP_SA_ACTION = "SaAction";
	public static String PROP_SA_MER_CH_NAME = "SaMerChName";
	public static String PROP_ID = "Id";

	protected void initialize() {
	}

	/**
	 * 
	 */
	public TblCtlMchtInf() {
		super();
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String saMerChName;
	private java.lang.String saMerEnName;
	private java.lang.String saLimitAmt;
	private java.lang.String saAction;

	private java.lang.String saZoneNo;
	private java.lang.String saAdmiBranNo;
	private java.lang.String saConnOr;
	private java.lang.String saConnTel;

	private java.lang.String saInitZoneNo;
	private java.lang.String saInitOprId;
	private java.lang.String saModiZoneNo;
	private java.lang.String saModiOprId;
	private java.lang.String saInitTime;
	private java.lang.String saModiTime;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="assigned" column="SA_MER_NO"
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
	 * Return the value associated with the column: SA_MER_CH_NAME
	 */
	public java.lang.String getSaMerChName() {
		return saMerChName;
	}

	/**
	 * Set the value related to the column: SA_MER_CH_NAME
	 * 
	 * @param saMerChName
	 *            the SA_MER_CH_NAME value
	 */
	public void setSaMerChName(java.lang.String saMerChName) {
		this.saMerChName = saMerChName;
	}

	/**
	 * Return the value associated with the column: SA_MER_EN_NAME
	 */
	public java.lang.String getSaMerEnName() {
		return saMerEnName;
	}

	/**
	 * Set the value related to the column: SA_MER_EN_NAME
	 * 
	 * @param saMerEnName
	 *            the SA_MER_EN_NAME value
	 */
	public void setSaMerEnName(java.lang.String saMerEnName) {
		this.saMerEnName = saMerEnName;
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
	 * @return the saZoneNo
	 */
	public java.lang.String getSaZoneNo() {
		return saZoneNo;
	}

	/**
	 * @param saZoneNo
	 *            the saZoneNo to set
	 */
	public void setSaZoneNo(java.lang.String saZoneNo) {
		this.saZoneNo = saZoneNo;
	}

	/**
	 * @return the saAdmiBranNo
	 */
	public java.lang.String getSaAdmiBranNo() {
		return saAdmiBranNo;
	}

	/**
	 * @param saAdmiBranNo
	 *            the saAdmiBranNo to set
	 */
	public void setSaAdmiBranNo(java.lang.String saAdmiBranNo) {
		this.saAdmiBranNo = saAdmiBranNo;
	}

	/**
	 * @return the saConnOr
	 */
	public java.lang.String getSaConnOr() {
		return saConnOr;
	}

	/**
	 * @param saConnOr
	 *            the saConnOr to set
	 */
	public void setSaConnOr(java.lang.String saConnOr) {
		this.saConnOr = saConnOr;
	}

	/**
	 * @return the saConnTel
	 */
	public java.lang.String getSaConnTel() {
		return saConnTel;
	}

	/**
	 * @param saConnTel
	 *            the saConnTel to set
	 */
	public void setSaConnTel(java.lang.String saConnTel) {
		this.saConnTel = saConnTel;
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
		if (!(obj instanceof com.huateng.po.TblCtlMchtInf))
			return false;
		else {
			com.huateng.po.TblCtlMchtInf tblCtlMchtInf = (com.huateng.po.TblCtlMchtInf) obj;
			if (null == this.getId() || null == tblCtlMchtInf.getId())
				return false;
			else
				return (this.getId().equals(tblCtlMchtInf.getId()));
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