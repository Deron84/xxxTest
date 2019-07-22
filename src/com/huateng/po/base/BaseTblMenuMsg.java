package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_MENU_MSG table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_MENU_MSG"
 */

public abstract class BaseTblMenuMsg  implements Serializable {

	public static String REF = "TblMenuMsg";
	public static String PROP_PPT_ID = "PptId";
	public static String PROP_CRT_DATE = "CrtDate";
	public static String PROP_UPD_DATE = "UpdDate";
	public static String PROP_TXN_CODE = "TxnCode";
	public static String PROP_OPR_ID = "OprId";
	public static String PROP_MENU_MSG = "MenuMsg";
	public static String PROP_ID = "Id";
	public static String PROP_CON_FLAG = "ConFlag";


	// constructors
	public BaseTblMenuMsg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblMenuMsg (com.huateng.po.epos.TblMenuMsgPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.epos.TblMenuMsgPK id;

	// fields
	private java.lang.String menuMsg;
	private java.lang.String txnCode;
	private java.lang.String conFlag;
	private java.lang.Integer pptId;
	private java.lang.String oprId;
	private java.lang.String crtDate;
	private java.lang.String updDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.epos.TblMenuMsgPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.epos.TblMenuMsgPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: MENU_MSG
	 */
	public java.lang.String getMenuMsg () {
		return menuMsg;
	}

	/**
	 * Set the value related to the column: MENU_MSG
	 * @param menuMsg the MENU_MSG value
	 */
	public void setMenuMsg (java.lang.String menuMsg) {
		this.menuMsg = menuMsg;
	}



	/**
	 * Return the value associated with the column: TXN_CODE
	 */
	public java.lang.String getTxnCode () {
		return txnCode;
	}

	/**
	 * Set the value related to the column: TXN_CODE
	 * @param txnCode the TXN_CODE value
	 */
	public void setTxnCode (java.lang.String txnCode) {
		this.txnCode = txnCode;
	}



	/**
	 * Return the value associated with the column: CON_FLAG
	 */
	public java.lang.String getConFlag () {
		return conFlag;
	}

	/**
	 * Set the value related to the column: CON_FLAG
	 * @param conFlag the CON_FLAG value
	 */
	public void setConFlag (java.lang.String conFlag) {
		this.conFlag = conFlag;
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
	 * Return the value associated with the column: OPR_ID
	 */
	public java.lang.String getOprId () {
		return oprId;
	}

	/**
	 * Set the value related to the column: OPR_ID
	 * @param oprId the OPR_ID value
	 */
	public void setOprId (java.lang.String oprId) {
		this.oprId = oprId;
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
		if (!(obj instanceof com.huateng.po.epos.TblMenuMsg)) return false;
		else {
			com.huateng.po.epos.TblMenuMsg tblMenuMsg = (com.huateng.po.epos.TblMenuMsg) obj;
			if (null == this.getId() || null == tblMenuMsg.getId()) return false;
			else return (this.getId().equals(tblMenuMsg.getId()));
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