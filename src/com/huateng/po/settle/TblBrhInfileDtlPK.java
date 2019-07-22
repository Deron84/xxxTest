package com.huateng.po.settle;

import java.io.Serializable;

public class TblBrhInfileDtlPK implements Serializable{
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String dateSettlmt;
	private java.lang.String brhCode;


	public TblBrhInfileDtlPK () {}
	
	public TblBrhInfileDtlPK (
		java.lang.String dateSettlmt,
		java.lang.String brhCode) {

		this.setDateSettlmt(dateSettlmt);
		this.setBrhCode(brhCode);
	}


	/**
	 * Return the value associated with the column: DATE_SETTLMT
	 */
	public java.lang.String getDateSettlmt () {
		return dateSettlmt;
	}

	/**
	 * Set the value related to the column: DATE_SETTLMT
	 * @param dateSettlmt the DATE_SETTLMT value
	 */
	public void setDateSettlmt (java.lang.String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}



	/**
	 * Return the value associated with the column: BRH_CODE
	 */
	public java.lang.String getBrhCode () {
		return brhCode;
	}

	/**
	 * Set the value related to the column: BRH_CODE
	 * @param brhCode the BRH_CODE value
	 */
	public void setBrhCode (java.lang.String brhCode) {
		this.brhCode = brhCode;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.settle.TblBrhInfileDtlPK)) return false;
		else {
			com.huateng.po.settle.TblBrhInfileDtlPK mObj = (com.huateng.po.settle.TblBrhInfileDtlPK) obj;
			if (null != this.getDateSettlmt() && null != mObj.getDateSettlmt()) {
				if (!this.getDateSettlmt().equals(mObj.getDateSettlmt())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getBrhCode() && null != mObj.getBrhCode()) {
				if (!this.getBrhCode().equals(mObj.getBrhCode())) {
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
			if (null != this.getDateSettlmt()) {
				sb.append(this.getDateSettlmt().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getBrhCode()) {
				sb.append(this.getBrhCode().hashCode());
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