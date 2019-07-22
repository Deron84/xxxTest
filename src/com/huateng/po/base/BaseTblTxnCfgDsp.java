package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_TXN_CFG_DSP table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_TXN_CFG_DSP"
 */

public abstract class BaseTblTxnCfgDsp  implements Serializable {

	public static String REF = "TblTxnCfgDsp";
	public static String PROP_DSP = "dsp";
	public static String PROP_BRH_ID = "brhId";
	public static String PROP_ID = "id";


	// constructors
	public BaseTblTxnCfgDsp () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblTxnCfgDsp (com.huateng.po.epos.TblTxnCfgDspPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.epos.TblTxnCfgDspPK id;

	// fields
	private java.lang.String brhId;
	private java.lang.String dsp;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.epos.TblTxnCfgDspPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.epos.TblTxnCfgDspPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: BRH_ID
	 */
	public java.lang.String getBrhId () {
		return brhId;
	}

	/**
	 * Set the value related to the column: BRH_ID
	 * @param brhId the BRH_ID value
	 */
	public void setBrhId (java.lang.String brhId) {
		this.brhId = brhId;
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
		if (!(obj instanceof com.huateng.po.epos.TblTxnCfgDsp)) return false;
		else {
			com.huateng.po.epos.TblTxnCfgDsp tblTxnCfgDsp = (com.huateng.po.epos.TblTxnCfgDsp) obj;
			if (null == this.getId() || null == tblTxnCfgDsp.getId()) return false;
			else return (this.getId().equals(tblTxnCfgDsp.getId()));
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