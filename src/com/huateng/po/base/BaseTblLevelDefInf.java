package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_LEVEL_DEF_INF table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_LEVEL_DEF_INF"
 */

public abstract class BaseTblLevelDefInf  implements Serializable {

	public static String REF = "TblLevelDefInf";
	public static String PROP_RESERVE1 = "reserve1";
	public static String PROP_UPD_USER = "updUser";
	public static String PROP_RESERVE2 = "reserve2";
	public static String PROP_CRT_TIME = "crtTime";
	public static String PROP_LEVEL_ID = "levelId";
	public static String PROP_MIN_POINT = "minPoint";
	public static String PROP_LEVEL_NAME = "levelName";
	public static String PROP_REMARKS = "remarks";
	public static String PROP_UPD_TIME = "updTime";
	public static String PROP_CRT_USER = "crtUser";


	// constructors
	public BaseTblLevelDefInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblLevelDefInf (java.lang.String levelId) {
		this.setLevelId(levelId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String levelId;

	// fields
	private java.lang.String crtTime;
	private java.lang.String crtUser;
	private java.lang.String levelName;
	private java.lang.Integer minPoint;
	private java.lang.String remarks;
	private java.lang.String reserve1;
	private java.lang.String reserve2;
	private java.lang.String updTime;
	private java.lang.String updUser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="LEVEL_ID"
     */
	public java.lang.String getLevelId () {
		return levelId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param levelId the new ID
	 */
	public void setLevelId (java.lang.String levelId) {
		this.levelId = levelId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: CRT_TIME
	 */
	public java.lang.String getCrtTime () {
		return crtTime;
	}

	/**
	 * Set the value related to the column: CRT_TIME
	 * @param crtTime the CRT_TIME value
	 */
	public void setCrtTime (java.lang.String crtTime) {
		this.crtTime = crtTime;
	}



	/**
	 * Return the value associated with the column: CRT_USER
	 */
	public java.lang.String getCrtUser () {
		return crtUser;
	}

	/**
	 * Set the value related to the column: CRT_USER
	 * @param crtUser the CRT_USER value
	 */
	public void setCrtUser (java.lang.String crtUser) {
		this.crtUser = crtUser;
	}



	/**
	 * Return the value associated with the column: LEVEL_NAME
	 */
	public java.lang.String getLevelName () {
		return levelName;
	}

	/**
	 * Set the value related to the column: LEVEL_NAME
	 * @param levelName the LEVEL_NAME value
	 */
	public void setLevelName (java.lang.String levelName) {
		this.levelName = levelName;
	}



	/**
	 * Return the value associated with the column: MIN_POINT
	 */
	public java.lang.Integer getMinPoint () {
		return minPoint;
	}

	/**
	 * Set the value related to the column: MIN_POINT
	 * @param minPoint the MIN_POINT value
	 */
	public void setMinPoint (java.lang.Integer minPoint) {
		this.minPoint = minPoint;
	}



	/**
	 * Return the value associated with the column: REMARKS
	 */
	public java.lang.String getRemarks () {
		return remarks;
	}

	/**
	 * Set the value related to the column: REMARKS
	 * @param remarks the REMARKS value
	 */
	public void setRemarks (java.lang.String remarks) {
		this.remarks = remarks;
	}



	/**
	 * Return the value associated with the column: RESERVE1
	 */
	public java.lang.String getReserve1 () {
		return reserve1;
	}

	/**
	 * Set the value related to the column: RESERVE1
	 * @param reserve1 the RESERVE1 value
	 */
	public void setReserve1 (java.lang.String reserve1) {
		this.reserve1 = reserve1;
	}



	/**
	 * Return the value associated with the column: RESERVE2
	 */
	public java.lang.String getReserve2 () {
		return reserve2;
	}

	/**
	 * Set the value related to the column: RESERVE2
	 * @param reserve2 the RESERVE2 value
	 */
	public void setReserve2 (java.lang.String reserve2) {
		this.reserve2 = reserve2;
	}



	/**
	 * Return the value associated with the column: UPD_TIME
	 */
	public java.lang.String getUpdTime () {
		return updTime;
	}

	/**
	 * Set the value related to the column: UPD_TIME
	 * @param updTime the UPD_TIME value
	 */
	public void setUpdTime (java.lang.String updTime) {
		this.updTime = updTime;
	}



	/**
	 * Return the value associated with the column: UPD_USER
	 */
	public java.lang.String getUpdUser () {
		return updUser;
	}

	/**
	 * Set the value related to the column: UPD_USER
	 * @param updUser the UPD_USER value
	 */
	public void setUpdUser (java.lang.String updUser) {
		this.updUser = updUser;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchtSrv.TblLevelDefInf)) return false;
		else {
			com.huateng.po.mchtSrv.TblLevelDefInf tblLevelDefInf = (com.huateng.po.mchtSrv.TblLevelDefInf) obj;
			if (null == this.getLevelId() || null == tblLevelDefInf.getLevelId()) return false;
			else return (this.getLevelId().equals(tblLevelDefInf.getLevelId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getLevelId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getLevelId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}