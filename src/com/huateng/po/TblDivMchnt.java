package com.huateng.po;

import java.io.Serializable;

public class TblDivMchnt implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String REF = "TblDivMchnt";

	public static String PROP_ID = "Id";

	public static String PROP_PRODUCT_CODE = "ProductCode";

	public static String PROP_INPRODUCT_CODE = "InProductCode";

	public static String PROP_FEE_CODE = "FeeCode";

	public static String PROP_MISC = "Misc";

	public static String PROP_PRODUCT_DIV_NAME = "productDivName";

	public static String PROP_FEE_TYPE = "FeeType";

	public static String PROP_FEE_MIXED = "FeeMixed";

	public static String PROP_FEE_MAX_AM = "FeeMaxAm";

	protected void initialize() {
	}

	/**
	 * 
	 */
	public TblDivMchnt() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.TblDivMchntPK id;

	// fields
	private java.lang.String productCode;

	private String inProductCode;

	private String feeCode;

	private String misc;

	private String productDivName;

	private String feeType;

	private String feeMixed;

	private String feeMaxAm;
	
	private String initOprId;
	private String modiOprId;
	private String initTime;
	private String modiTime;
	
	public String getInitOprId() {
		return initOprId;
	}

	
	public void setInitOprId(String initOprId) {
		this.initOprId = initOprId;
	}

	
	public String getModiOprId() {
		return modiOprId;
	}

	
	public void setModiOprId(String modiOprId) {
		this.modiOprId = modiOprId;
	}

	
	public String getInitTime() {
		return initTime;
	}

	
	public void setInitTime(String initTime) {
		this.initTime = initTime;
	}

	
	public String getModiTime() {
		return modiTime;
	}

	
	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id
	 */
	public com.huateng.po.TblDivMchntPK getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(com.huateng.po.TblDivMchntPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: PRODUCT_CODE
	 */
	public java.lang.String getProductCode() {
		return productCode;
	}

	/**
	 * Set the value related to the column: PRODUCT_CODE
	 * 
	 * @param productCode
	 *            the PRODUCT_CODE value
	 */
	public void setProductCode(java.lang.String productCode) {
		this.productCode = productCode;
	}

	public String getInProductCode() {
		return inProductCode;
	}

	public void setInProductCode(String inProductCode) {
		this.inProductCode = inProductCode;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}

	public String getProductDivName() {
		return productDivName;
	}

	public void setProductDivName(String productDivName) {
		this.productDivName = productDivName;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeMixed() {
		return feeMixed;
	}

	public void setFeeMixed(String feeMixed) {
		this.feeMixed = feeMixed;
	}

	public String getFeeMaxAm() {
		return feeMaxAm;
	}

	public void setFeeMaxAm(String feeMaxAm) {
		this.feeMaxAm = feeMaxAm;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.huateng.po.TblDivMchnt))
			return false;
		else {
			com.huateng.po.TblDivMchnt tblDivMchnt = (com.huateng.po.TblDivMchnt) obj;
			if (null == this.getId() || null == tblDivMchnt.getId())
				return false;
			else
				return (this.getId().equals(tblDivMchnt.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}

}