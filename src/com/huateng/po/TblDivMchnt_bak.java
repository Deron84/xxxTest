package com.huateng.po;

import java.io.Serializable;



public class TblDivMchnt_bak implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblDivMchnt";
	public static String PROP_ID = "Id";
	public static String PROP_PRODUCT_CODE = "ProductCode";

	protected void initialize () {}


	
	/**
	 * 
	 */
	public TblDivMchnt_bak() {
		super();
		// TODO Auto-generated constructor stub
	}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.TblDivMchntPK id;

	// fields
	private java.lang.String productCode;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.TblDivMchntPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.TblDivMchntPK id) {
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
		if (!(obj instanceof com.huateng.po.TblDivMchnt_bak)) return false;
		else {
			com.huateng.po.TblDivMchnt_bak tblDivMchnt = (com.huateng.po.TblDivMchnt_bak) obj;
			if (null == this.getId() || null == tblDivMchnt.getId()) return false;
			else return (this.getId().equals(tblDivMchnt.getId()));
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