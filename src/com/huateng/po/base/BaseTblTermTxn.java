package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_TERM_TXN table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_TERM_TXN"
 */

public abstract class BaseTblTermTxn  implements Serializable {

	public static String REF = "TblTermTxn";
	public static String PROP_DSP = "Dsp";
	public static String PROP_INT_TXN_CODE = "IntTxnCode";
	public static String PROP_TERM_TXN_CODE = "TermTxnCode";


	// constructors
	public BaseTblTermTxn () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblTermTxn (java.lang.String termTxnCode) {
		this.setTermTxnCode(termTxnCode);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String termTxnCode;

	// fields
	private java.lang.String intTxnCode;
	private java.lang.String dsp;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="TERM_TXN_CODE"
     */
	public java.lang.String getTermTxnCode () {
		return termTxnCode;
	}

	/**
	 * Set the unique identifier of this class
	 * @param termTxnCode the new ID
	 */
	public void setTermTxnCode (java.lang.String termTxnCode) {
		this.termTxnCode = termTxnCode;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: INT_TXN_CODE
	 */
	public java.lang.String getIntTxnCode () {
		return intTxnCode;
	}

	/**
	 * Set the value related to the column: INT_TXN_CODE
	 * @param intTxnCode the INT_TXN_CODE value
	 */
	public void setIntTxnCode (java.lang.String intTxnCode) {
		this.intTxnCode = intTxnCode;
	}



	/**
	 * Return the value associated with the column: DSP
	 */
	public java.lang.String getDsp () {
		return dsp;
	}

	/**
	 * Set the value related to the column: DSP
	 * @param dsp the DSP value
	 */
	public void setDsp (java.lang.String dsp) {
		this.dsp = dsp;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblTermTxn)) return false;
		else {
			com.huateng.po.epos.TblTermTxn tblTermTxn = (com.huateng.po.epos.TblTermTxn) obj;
			if (null == this.getTermTxnCode() || null == tblTermTxn.getTermTxnCode()) return false;
			else return (this.getTermTxnCode().equals(tblTermTxn.getTermTxnCode()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getTermTxnCode()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getTermTxnCode().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}