package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_PAPER_SEL_INF table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_PAPER_SEL_INF"
 */

public abstract class BaseTblPaperSelInf  implements Serializable {

	public static String REF = "TblPaperSelInf";
	public static String PROP_RESERVE = "reserve";
	public static String PROP_ID = "id";


	// constructors
	public BaseTblPaperSelInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblPaperSelInf (com.huateng.po.mchtSrv.TblPaperSelInfPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.mchtSrv.TblPaperSelInfPK id;

	// fields
	private java.lang.String reserve;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.mchtSrv.TblPaperSelInfPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.mchtSrv.TblPaperSelInfPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: RESERVE
	 */
	public java.lang.String getReserve () {
		return reserve;
	}

	/**
	 * Set the value related to the column: RESERVE
	 * @param reserve the RESERVE value
	 */
	public void setReserve (java.lang.String reserve) {
		this.reserve = reserve;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchtSrv.TblPaperSelInf)) return false;
		else {
			com.huateng.po.mchtSrv.TblPaperSelInf tblPaperSelInf = (com.huateng.po.mchtSrv.TblPaperSelInf) obj;
			if (null == this.getId() || null == tblPaperSelInf.getId()) return false;
			else return (this.getId().equals(tblPaperSelInf.getId()));
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