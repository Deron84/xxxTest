package com.huateng.po;

import java.io.Serializable;



public class TblRoleFuncMap implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblRoleFuncMap";
	public static String PROP_REC_UPD_OPR = "RecUpdOpr";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_ID = "Id";
	public static String PROP_REC_UPD_TS = "RecUpdTs";


	protected void initialize () {}
	
	
	/**
	 * 
	 */
	public TblRoleFuncMap() {
		super();
		// TODO Auto-generated constructor stub
	}


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.TblRoleFuncMapPK id;

	// fields
	private java.lang.String recUpdOpr;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.TblRoleFuncMapPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.TblRoleFuncMapPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: REC_UPD_OPR
	 */
	public java.lang.String getRecUpdOpr () {
		return recUpdOpr;
	}

	/**
	 * Set the value related to the column: REC_UPD_OPR
	 * @param recUpdOpr the REC_UPD_OPR value
	 */
	public void setRecUpdOpr (java.lang.String recUpdOpr) {
		this.recUpdOpr = recUpdOpr;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblRoleFuncMap)) return false;
		else {
			com.huateng.po.TblRoleFuncMap tblRoleFuncMap = (com.huateng.po.TblRoleFuncMap) obj;
			if (null == this.getId() || null == tblRoleFuncMap.getId()) return false;
			else return (this.getId().equals(tblRoleFuncMap.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}
}