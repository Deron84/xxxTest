package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_BRH_ACCT table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_BRH_ACCT"
 */

public abstract class BaseTblBrhAcct  implements Serializable {

	public static String REF = "TblBrhAcct";
	public static String PROP_LAST_UPD_TS = "LastUpdTs";
	public static String PROP_RESV1 = "Resv1";
	public static String PROP_LAST_UPD_OPR_ID = "LastUpdOprId";
	public static String PROP_LAST_UPD_TXN_ID = "LastUpdTxnId";
	public static String PROP_ID = "Id";
	public static String PROP_ACCT2 = "Acct2";
	public static String PROP_ACCT1 = "Acct1";


	// constructors
	public BaseTblBrhAcct () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblBrhAcct (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String acct1;
	private java.lang.String acct2;
	private java.lang.String resv1;
	private java.lang.String lastUpdOprId;
	private java.lang.String lastUpdTxnId;
	private java.lang.String lastUpdTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="BRH_ID"
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
	 * Return the value associated with the column: ACCT1
	 */
	public java.lang.String getAcct1 () {
		return acct1;
	}

	/**
	 * Set the value related to the column: ACCT1
	 * @param acct1 the ACCT1 value
	 */
	public void setAcct1 (java.lang.String acct1) {
		this.acct1 = acct1;
	}



	/**
	 * Return the value associated with the column: ACCT2
	 */
	public java.lang.String getAcct2 () {
		return acct2;
	}

	/**
	 * Set the value related to the column: ACCT2
	 * @param acct2 the ACCT2 value
	 */
	public void setAcct2 (java.lang.String acct2) {
		this.acct2 = acct2;
	}



	/**
	 * Return the value associated with the column: RESV1
	 */
	public java.lang.String getResv1 () {
		return resv1;
	}

	/**
	 * Set the value related to the column: RESV1
	 * @param resv1 the RESV1 value
	 */
	public void setResv1 (java.lang.String resv1) {
		this.resv1 = resv1;
	}



	/**
	 * Return the value associated with the column: LAST_UPD_OPR_ID
	 */
	public java.lang.String getLastUpdOprId () {
		return lastUpdOprId;
	}

	/**
	 * Set the value related to the column: LAST_UPD_OPR_ID
	 * @param lastUpdOprId the LAST_UPD_OPR_ID value
	 */
	public void setLastUpdOprId (java.lang.String lastUpdOprId) {
		this.lastUpdOprId = lastUpdOprId;
	}



	/**
	 * Return the value associated with the column: LAST_UPD_TXN_ID
	 */
	public java.lang.String getLastUpdTxnId () {
		return lastUpdTxnId;
	}

	/**
	 * Set the value related to the column: LAST_UPD_TXN_ID
	 * @param lastUpdTxnId the LAST_UPD_TXN_ID value
	 */
	public void setLastUpdTxnId (java.lang.String lastUpdTxnId) {
		this.lastUpdTxnId = lastUpdTxnId;
	}



	/**
	 * Return the value associated with the column: LAST_UPD_TS
	 */
	public java.lang.String getLastUpdTs () {
		return lastUpdTs;
	}

	/**
	 * Set the value related to the column: LAST_UPD_TS
	 * @param lastUpdTs the LAST_UPD_TS value
	 */
	public void setLastUpdTs (java.lang.String lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblBrhAcct)) return false;
		else {
			com.huateng.po.TblBrhAcct tblBrhAcct = (com.huateng.po.TblBrhAcct) obj;
			if (null == this.getId() || null == tblBrhAcct.getId()) return false;
			else return (this.getId().equals(tblBrhAcct.getId()));
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