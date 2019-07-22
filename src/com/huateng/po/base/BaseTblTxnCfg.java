package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_TXN_CFG table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_TXN_CFG"
 */

public abstract class BaseTblTxnCfg  implements Serializable {

	public static String REF = "TblTxnCfg";
	public static String PROP_PPT_ID = "pptId";
	public static String PROP_CRT_DATE = "crtDate";
	public static String PROP_DSP = "dsp";
	public static String PROP_UPD_DATE = "updDate";
	public static String PROP_ENC_MOD = "encMod";
	public static String PROP_BRH_ID = "brhId";
	public static String PROP_ID = "id";
	public static String PROP_CMD_ID = "cmdId";
	public static String PROP_CMD_TYPE = "cmdType";


	// constructors
	public BaseTblTxnCfg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblTxnCfg (com.huateng.po.epos.TblTxnCfgPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.epos.TblTxnCfgPK id;

	// fields
	private java.lang.String cmdId;
	private java.lang.Integer pptId;
	private java.lang.String encMod;
	private java.lang.String cmdType;
	private java.lang.String brhId;
	private java.lang.String dsp;
	private java.lang.String crtDate;
	private java.lang.String updDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.epos.TblTxnCfgPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.epos.TblTxnCfgPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: CMD_ID
	 */
	public java.lang.String getCmdId () {
		return cmdId;
	}

	/**
	 * Set the value related to the column: CMD_ID
	 * @param cmdId the CMD_ID value
	 */
	public void setCmdId (java.lang.String cmdId) {
		this.cmdId = cmdId;
	}



	/**
	 * Return the value associated with the column: PPT_ID
	 */
	public java.lang.Integer getPptId () {
		return pptId;
	}

	/**
	 * Set the value related to the column: PPT_ID
	 * @param pptId the PPT_ID value
	 */
	public void setPptId (java.lang.Integer pptId) {
		this.pptId = pptId;
	}



	/**
	 * Return the value associated with the column: ENC_MOD
	 */
	public java.lang.String getEncMod () {
		return encMod;
	}

	/**
	 * Set the value related to the column: ENC_MOD
	 * @param encMod the ENC_MOD value
	 */
	public void setEncMod (java.lang.String encMod) {
		this.encMod = encMod;
	}



	/**
	 * Return the value associated with the column: CMD_TYPE
	 */
	public java.lang.String getCmdType () {
		return cmdType;
	}

	/**
	 * Set the value related to the column: CMD_TYPE
	 * @param cmdType the CMD_TYPE value
	 */
	public void setCmdType (java.lang.String cmdType) {
		this.cmdType = cmdType;
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



	/**
	 * Return the value associated with the column: CRT_DATE
	 */
	public java.lang.String getCrtDate () {
		return crtDate;
	}

	/**
	 * Set the value related to the column: CRT_DATE
	 * @param crtDate the CRT_DATE value
	 */
	public void setCrtDate (java.lang.String crtDate) {
		this.crtDate = crtDate;
	}



	/**
	 * Return the value associated with the column: UPD_DATE
	 */
	public java.lang.String getUpdDate () {
		return updDate;
	}

	/**
	 * Set the value related to the column: UPD_DATE
	 * @param updDate the UPD_DATE value
	 */
	public void setUpdDate (java.lang.String updDate) {
		this.updDate = updDate;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblTxnCfg)) return false;
		else {
			com.huateng.po.epos.TblTxnCfg tblTxnCfg = (com.huateng.po.epos.TblTxnCfg) obj;
			if (null == this.getId() || null == tblTxnCfg.getId()) return false;
			else return (this.getId().equals(tblTxnCfg.getId()));
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