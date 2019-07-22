package com.huateng.po.mchnt.base;

import java.io.Serializable;

import com.huateng.po.mchnt.TblInfDiscCd;


/**
 * This is an object that contains data related to the TBL_INF_DISC_CD table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_INF_DISC_CD"
 */

public abstract class BaseTblInfDiscCd  implements Comparable, Serializable {

	public static String REF = "TblInfDiscCd";
	public static String PROP_DISC_CD = "DiscCd";
	public static String DISC_NM = "DiscNm";
	public static String DISC_ORG = "DiscOrg";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_LAST_OPER_IN = "LastOperIn";
	public static String PROP_REC_UPD_USR_ID = "RecUpdUserId";


	// constructors
	public BaseTblInfDiscCd () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblInfDiscCd (java.lang.String discCd) {
		this.setDiscCd(discCd);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblInfDiscCd (
		java.lang.String discCd,
		java.lang.String discNm,
		java.lang.String discOrg,
		java.lang.String lastOperIn,
		java.lang.String recUpdUsrId,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs) {

		this.setDiscCd(discCd);
		this.setDiscNm(discNm);
		this.setDiscOrg(discOrg);
		this.setLastOperIn(lastOperIn);
		this.setRecUpdUserId(recUpdUsrId);
		this.setRecUpdTs(recUpdTs);
		this.setRecCrtTs(recCrtTs);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary 手续费类型IDdisc_cd
	private java.lang.String discCd;
	// 手续费名称
	private java.lang.String discNm;
	//手续费所属机构
	private java.lang.String discOrg;	
	//最后操作状态
	private java.lang.String lastOperIn;
//	/操作柜员
	private java.lang.String recUpdUserId;	
	//记录创建时间
	private java.lang.String recUpdTs;
	
	//记录修改时间
	private java.lang.String recCrtTs;


	

	public java.lang.String getDiscOrg() {
		return discOrg;
	}

	public void setDiscOrg(java.lang.String discOrg) {
		this.discOrg = discOrg;
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="DISC_CD"
     */
	public java.lang.String getDiscCd () {
		return discCd;
	}

	/**
	 * Set the unique identifier of this class
	 * @param discCd the new ID
	 */
	public void setDiscCd (java.lang.String discCd) {
		this.discCd = discCd;
		this.hashCode = Integer.MIN_VALUE;
	}




	


	public java.lang.String getDiscNm() {
		return discNm;
	}

	public void setDiscNm(java.lang.String discNm) {
		this.discNm = discNm;
	}

	

	/**
	 * Return the value associated with the column: LAST_OPER_IN
	 */
	public java.lang.String getLastOperIn () {
		return lastOperIn;
	}

	/**
	 * Set the value related to the column: LAST_OPER_IN
	 * @param lastOperIn the LAST_OPER_IN value
	 */
	public void setLastOperIn (java.lang.String lastOperIn) {
		this.lastOperIn = lastOperIn;
	}

	public java.lang.String getRecUpdUserId() {
		return recUpdUserId;
	}

	public void setRecUpdUserId(java.lang.String recUpdUserId) {
		this.recUpdUserId = recUpdUserId;
	}

	/**
	 * Return the value associated with the column: REC_UPD_TS
	 */
	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: REC_UPD_TS
	 * @param recUpdTs the REC_UPD_TS value
	 */
	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}



	/**
	 * Return the value associated with the column: REC_CRT_TS
	 */
	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: REC_CRT_TS
	 * @param recCrtTs the REC_CRT_TS value
	 */
	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblInfDiscCd)) return false;
		else {
			TblInfDiscCd tblInfDiscCd = (TblInfDiscCd) obj;
			if (null == this.getDiscCd() || null == tblInfDiscCd.getDiscCd()) return false;
			else return (this.getDiscCd().equals(tblInfDiscCd.getDiscCd()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getDiscCd()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getDiscCd().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public int compareTo (Object obj) {
		if (obj.hashCode() > hashCode()) return 1;
		else if (obj.hashCode() < hashCode()) return -1;
		else return 0;
	}

	public String toString () {
		return super.toString();
	}


}