package com.huateng.po;

import java.io.Serializable;

public class TblDivMchntTmpPK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String mchtId;
	private java.lang.String divNo;
	
	
	/**
	 * 
	 */
	public TblDivMchntTmpPK() {
		super();
	}

	/**
	 * @param mchtId2
	 * @param divNo2
	 */
	public TblDivMchntTmpPK(String mchtId, String divNo) {
		this.mchtId = mchtId;
		this.divNo = divNo;
	}

	/**
	 * Return the value associated with the column: MCHT_ID
	 */
	public java.lang.String getMchtId () {
		return mchtId;
	}

	/**
	 * Set the value related to the column: MCHT_ID
	 * @param mchtId the MCHT_ID value
	 */
	public void setMchtId (java.lang.String mchtId) {
		this.mchtId = mchtId;
	}



	/**
	 * Return the value associated with the column: DIV_NO
	 */
	public java.lang.String getDivNo () {
		return divNo;
	}

	/**
	 * Set the value related to the column: DIV_NO
	 * @param divNo the DIV_NO value
	 */
	public void setDivNo (java.lang.String divNo) {
		this.divNo = divNo;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblDivMchntTmpPK)) return false;
		else {
			com.huateng.po.TblDivMchntTmpPK mObj = (com.huateng.po.TblDivMchntTmpPK) obj;
			if (null != this.getMchtId() && null != mObj.getMchtId()) {
				if (!this.getMchtId().equals(mObj.getMchtId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getDivNo() && null != mObj.getDivNo()) {
				if (!this.getDivNo().equals(mObj.getDivNo())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getMchtId()) {
				sb.append(this.getMchtId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getDivNo()) {
				sb.append(this.getDivNo().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}