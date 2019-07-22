package com.huateng.po;

import java.io.Serializable;



public class TblTermTp implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblTermTp";
	public static String PROP_DESCR = "Descr";
	public static String PROP_TERM_NUM = "TermNum";
	public static String PROP_TERM_TYPE = "TermType";
	public static String PROP_PRO_DESCR = "ProDescr";
	public static String PROP_ID = "Id";


	protected void initialize () {}
	
	
	/**
	 * 
	 */
	public TblTermTp() {
		super();
		// TODO Auto-generated constructor stub
	}


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String descr;
	private java.lang.String termNum;
	private java.lang.String termType;
	private java.lang.String proDescr;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="term_tp"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: descr
	 */
	public java.lang.String getDescr () {
		return descr;
	}

	/**
	 * Set the value related to the column: descr
	 * @param descr the descr value
	 */
	public void setDescr (java.lang.String descr) {
		this.descr = descr;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblTermTp)) return false;
		else {
			com.huateng.po.TblTermTp tblTermTp = (com.huateng.po.TblTermTp) obj;
			if (null == this.getId() || null == tblTermTp.getId()) return false;
			else return (this.getId().equals(tblTermTp.getId()));
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


	public java.lang.String getTermNum() {
		return termNum;
	}


	public void setTermNum(java.lang.String termNum) {
		this.termNum = termNum;
	}


	public java.lang.String getTermType() {
		return termType;
	}


	public void setTermType(java.lang.String termType) {
		this.termType = termType;
	}


	public java.lang.String getProDescr() {
		return proDescr;
	}


	public void setProDescr(java.lang.String proDescr) {
		this.proDescr = proDescr;
	}
}