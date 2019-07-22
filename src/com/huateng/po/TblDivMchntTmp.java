package com.huateng.po;

import java.io.Serializable;



public class TblDivMchntTmp implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblDivMchntTmp";
	public static String PROP_ID = "Id";
	public static String PROP_PRODUCT_CODE = "ProductCode";

	/**
	 * @param tblDivMchntTmpPK
	 * @param productCode2
	 */
	public TblDivMchntTmp(TblDivMchntTmpPK tblDivMchntTmpPK, String productCode) {
		this.id = tblDivMchntTmpPK;
		this.productCode = productCode;
	}

	/**
	 * 
	 */
	public TblDivMchntTmp() {}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.TblDivMchntTmpPK id;

	// fields
	private java.lang.String productCode;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.TblDivMchntTmpPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.TblDivMchntTmpPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: PRODUCT_CODE
	 */
	public java.lang.String getProductCode () {
		return productCode;
	}

	/**
	 * Set the value related to the column: PRODUCT_CODE
	 * @param productCode the PRODUCT_CODE value
	 */
	public void setProductCode (java.lang.String productCode) {
		this.productCode = productCode;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblDivMchntTmp)) return false;
		else {
			com.huateng.po.TblDivMchntTmp tblDivMchntTmp = (com.huateng.po.TblDivMchntTmp) obj;
			if (null == this.getId() || null == tblDivMchntTmp.getId()) return false;
			else return (this.getId().equals(tblDivMchntTmp.getId()));
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