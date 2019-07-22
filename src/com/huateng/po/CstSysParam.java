package com.huateng.po;

import java.io.Serializable;



public class CstSysParam implements Serializable{
	private static final long serialVersionUID = 1L;


	public static String REF = "CstSysParam";
	public static String PROP_TYPE = "Type";
	public static String PROP_DESCR = "Descr";
	public static String PROP_VALUE = "Value";
	public static String PROP_ID = "Id";
	public static String PROP_RESERVE = "Reserve";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.CstSysParamPK id;

	// fields
	private java.lang.String type;
	private java.lang.String value;
	private java.lang.String descr;
	private java.lang.String reserve;

	

	/**
	 * 
	 */
	public CstSysParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.CstSysParamPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.CstSysParamPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: TYPE
	 */
	public java.lang.String getType () {
		return type;
	}

	/**
	 * Set the value related to the column: TYPE
	 * @param type the TYPE value
	 */
	public void setType (java.lang.String type) {
		this.type = type;
	}



	/**
	 * Return the value associated with the column: VALUE
	 */
	public java.lang.String getValue () {
		return value;
	}

	/**
	 * Set the value related to the column: VALUE
	 * @param value the VALUE value
	 */
	public void setValue (java.lang.String value) {
		this.value = value;
	}



	/**
	 * Return the value associated with the column: DESCR
	 */
	public java.lang.String getDescr () {
		return descr;
	}

	/**
	 * Set the value related to the column: DESCR
	 * @param descr the DESCR value
	 */
	public void setDescr (java.lang.String descr) {
		this.descr = descr;
	}



	/**
	 * Return the value associated with the column: RESERVE
	 */
	public java.lang.String getReserve () {
		return reserve;
	}

	/**
	 * Set the value related to the column: RESERVE
	 * @param reserve the RESERVE value
	 */
	public void setReserve (java.lang.String reserve) {
		this.reserve = reserve;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.CstSysParam)) return false;
		else {
			com.huateng.po.CstSysParam cstSysParam = (com.huateng.po.CstSysParam) obj;
			if (null == this.getId() || null == cstSysParam.getId()) return false;
			else return (this.getId().equals(cstSysParam.getId()));
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