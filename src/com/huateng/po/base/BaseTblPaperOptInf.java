package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_PAPER_OPT_INF table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_PAPER_OPT_INF"
 */

public abstract class BaseTblPaperOptInf  implements Serializable {

	public static String REF = "TblPaperOptInf";
	public static String PROP_RESERVE = "reserve";
	public static String PROP_OPT = "opt";
	public static String PROP_ID = "id";
	public static String PROP_POINT = "point";


	// constructors
	public BaseTblPaperOptInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblPaperOptInf (com.huateng.po.mchtSrv.TblPaperOptInfPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.mchtSrv.TblPaperOptInfPK id;

	// fields
	private java.lang.String opt;
	private java.lang.Integer point;
	private java.lang.String reserve;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.mchtSrv.TblPaperOptInfPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.mchtSrv.TblPaperOptInfPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: OPT
	 */
	public java.lang.String getOpt () {
		return opt;
	}

	/**
	 * Set the value related to the column: OPT
	 * @param opt the OPT value
	 */
	public void setOpt (java.lang.String opt) {
		this.opt = opt;
	}



	/**
	 * Return the value associated with the column: POINT
	 */
	public java.lang.Integer getPoint () {
		return point;
	}

	/**
	 * Set the value related to the column: POINT
	 * @param point the POINT value
	 */
	public void setPoint (java.lang.Integer point) {
		this.point = point;
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
		if (!(obj instanceof com.huateng.po.mchtSrv.TblPaperOptInf)) return false;
		else {
			com.huateng.po.mchtSrv.TblPaperOptInf tblPaperOptInf = (com.huateng.po.mchtSrv.TblPaperOptInf) obj;
			if (null == this.getId() || null == tblPaperOptInf.getId()) return false;
			else return (this.getId().equals(tblPaperOptInf.getId()));
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