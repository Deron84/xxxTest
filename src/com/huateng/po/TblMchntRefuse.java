package com.huateng.po;

import java.io.Serializable;



public class TblMchntRefuse implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblMchntRefuse";
	public static String PROP_BRH_ID = "BrhId";
	public static String PROP_OPR_ID = "OprId";
	public static String PROP_REFUSE_INFO = "RefuseInfo";
	public static String PROP_REFUSE_TYPE = "RefuseType";
	public static String PROP_ID = "Id";

	protected void initialize () {}
	
	
	/**
	 * 
	 */
	public TblMchntRefuse() {
		super();
		// TODO Auto-generated constructor stub
	}


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.TblMchntRefusePK id;

	// fields
	private java.lang.String brhId;
	private java.lang.String oprId;
	private java.lang.String refuseType;
	private java.lang.String refuseInfo;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.TblMchntRefusePK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.TblMchntRefusePK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: brh_id
	 */
	public java.lang.String getBrhId () {
		return brhId;
	}

	/**
	 * Set the value related to the column: brh_id
	 * @param brhId the brh_id value
	 */
	public void setBrhId (java.lang.String brhId) {
		this.brhId = brhId;
	}



	/**
	 * Return the value associated with the column: opr_id
	 */
	public java.lang.String getOprId () {
		return oprId;
	}

	/**
	 * Set the value related to the column: opr_id
	 * @param oprId the opr_id value
	 */
	public void setOprId (java.lang.String oprId) {
		this.oprId = oprId;
	}



	/**
	 * Return the value associated with the column: refuse_type
	 */
	public java.lang.String getRefuseType () {
		return refuseType;
	}

	/**
	 * Set the value related to the column: refuse_type
	 * @param refuseType the refuse_type value
	 */
	public void setRefuseType (java.lang.String refuseType) {
		this.refuseType = refuseType;
	}



	/**
	 * Return the value associated with the column: refuse_info
	 */
	public java.lang.String getRefuseInfo () {
		return refuseInfo;
	}

	/**
	 * Set the value related to the column: refuse_info
	 * @param refuseInfo the refuse_info value
	 */
	public void setRefuseInfo (java.lang.String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblMchntRefuse)) return false;
		else {
			com.huateng.po.TblMchntRefuse tblMchntRefuse = (com.huateng.po.TblMchntRefuse) obj;
			if (null == this.getId() || null == tblMchntRefuse.getId()) return false;
			else return (this.getId().equals(tblMchntRefuse.getId()));
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