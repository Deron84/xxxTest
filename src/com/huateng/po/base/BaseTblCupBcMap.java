package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_CUP_BC_MAP table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_CUP_BC_MAP"
 */

public abstract class BaseTblCupBcMap  implements Serializable {

	public static String REF = "TblCupBcMap";
	public static String PROP_BC_ID = "bcId";
	public static String PROP_ID = "id";
	public static String PROP_UPDATE_DATE = "updateDate";
	public static String PROP_BANK_NAME = "bankName";
	public static String PROP_CREATE_DATE = "createDate";


	// constructors
	public BaseTblCupBcMap () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblCupBcMap (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblCupBcMap (
		java.lang.String id,
		java.lang.String bcId) {

		this.setId(id);
		this.setBcId(bcId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String bcId;
	private java.lang.String bankName;
	private java.lang.String createDate;
	private java.lang.String updateDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.Assigned"
     *  column="CUP_ID"
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
	 * Return the value associated with the column: BC_ID
	 */
	public java.lang.String getBcId () {
		return bcId;
	}

	/**
	 * Set the value related to the column: BC_ID
	 * @param bcId the BC_ID value
	 */
	public void setBcId (java.lang.String bcId) {
		this.bcId = bcId;
	}



	/**
	 * Return the value associated with the column: BANK_NAME
	 */
	public java.lang.String getBankName () {
		return bankName;
	}

	/**
	 * Set the value related to the column: BANK_NAME
	 * @param bankName the BANK_NAME value
	 */
	public void setBankName (java.lang.String bankName) {
		this.bankName = bankName;
	}



	/**
	 * Return the value associated with the column: CREATE_DATE
	 */
	public java.lang.String getCreateDate () {
		return createDate;
	}

	/**
	 * Set the value related to the column: CREATE_DATE
	 * @param createDate the CREATE_DATE value
	 */
	public void setCreateDate (java.lang.String createDate) {
		this.createDate = createDate;
	}



	/**
	 * Return the value associated with the column: UPDATE_DATE
	 */
	public java.lang.String getUpdateDate () {
		return updateDate;
	}

	/**
	 * Set the value related to the column: UPDATE_DATE
	 * @param updateDate the UPDATE_DATE value
	 */
	public void setUpdateDate (java.lang.String updateDate) {
		this.updateDate = updateDate;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblCupBcMap)) return false;
		else {
			com.huateng.po.epos.TblCupBcMap tblCupBcMap = (com.huateng.po.epos.TblCupBcMap) obj;
			if (null == this.getId() || null == tblCupBcMap.getId()) return false;
			else return (this.getId().equals(tblCupBcMap.getId()));
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