package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblVerMngPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String bankId;
	private java.lang.String verId;


	public BaseTblVerMngPK () {}
	
	public BaseTblVerMngPK (
		java.lang.String bankId,
		java.lang.String verId) {

		this.setBankId(bankId);
		this.setVerId(verId);
	}


	/**
	 * Return the value associated with the column: BANK_ID
	 */
	public java.lang.String getBankId () {
		return bankId;
	}

	/**
	 * Set the value related to the column: BANK_ID
	 * @param bankId the BANK_ID value
	 */
	public void setBankId (java.lang.String bankId) {
		this.bankId = bankId;
	}



	/**
	 * Return the value associated with the column: VER_ID
	 */
	public java.lang.String getVerId () {
		return verId;
	}

	/**
	 * Set the value related to the column: VER_ID
	 * @param verId the VER_ID value
	 */
	public void setVerId (java.lang.String verId) {
		this.verId = verId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblVerMngPK)) return false;
		else {
			com.huateng.po.epos.TblVerMngPK mObj = (com.huateng.po.epos.TblVerMngPK) obj;
			if (null != this.getBankId() && null != mObj.getBankId()) {
				if (!this.getBankId().equals(mObj.getBankId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getVerId() && null != mObj.getVerId()) {
				if (!this.getVerId().equals(mObj.getVerId())) {
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
			if (null != this.getBankId()) {
				sb.append(this.getBankId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getVerId()) {
				sb.append(this.getVerId().hashCode());
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