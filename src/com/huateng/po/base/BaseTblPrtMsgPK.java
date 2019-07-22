package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblPrtMsgPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String usageKey;
	private java.lang.Integer prtId;


	public BaseTblPrtMsgPK () {}
	
	public BaseTblPrtMsgPK (
		java.lang.String usageKey,
		java.lang.Integer prtId) {

		this.setUsageKey(usageKey);
		this.setPrtId(prtId);
	}


	/**
	 * Return the value associated with the column: USAGE_KEY
	 */
	public java.lang.String getUsageKey () {
		return usageKey;
	}

	/**
	 * Set the value related to the column: USAGE_KEY
	 * @param usageKey the USAGE_KEY value
	 */
	public void setUsageKey (java.lang.String usageKey) {
		this.usageKey = usageKey;
	}



	/**
	 * Return the value associated with the column: PRT_ID
	 */
	public java.lang.Integer getPrtId () {
		return prtId;
	}

	/**
	 * Set the value related to the column: PRT_ID
	 * @param prtId the PRT_ID value
	 */
	public void setPrtId (java.lang.Integer prtId) {
		this.prtId = prtId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblPrtMsgPK)) return false;
		else {
			com.huateng.po.epos.TblPrtMsgPK mObj = (com.huateng.po.epos.TblPrtMsgPK) obj;
			if (null != this.getUsageKey() && null != mObj.getUsageKey()) {
				if (!this.getUsageKey().equals(mObj.getUsageKey())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getPrtId() && null != mObj.getPrtId()) {
				if (!this.getPrtId().equals(mObj.getPrtId())) {
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
			if (null != this.getUsageKey()) {
				sb.append(this.getUsageKey().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getPrtId()) {
				sb.append(this.getPrtId().hashCode());
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