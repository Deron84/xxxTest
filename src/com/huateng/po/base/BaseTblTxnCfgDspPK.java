package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblTxnCfgDspPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String termTxnCode;
	private java.lang.String usageKey;


	public BaseTblTxnCfgDspPK () {}
	
	public BaseTblTxnCfgDspPK (
		java.lang.String termTxnCode,
		java.lang.String usageKey) {

		this.setTermTxnCode(termTxnCode);
		this.setUsageKey(usageKey);
	}


	/**
	 * Return the value associated with the column: TERM_TXN_CODE
	 */
	public java.lang.String getTermTxnCode () {
		return termTxnCode;
	}

	/**
	 * Set the value related to the column: TERM_TXN_CODE
	 * @param termTxnCode the TERM_TXN_CODE value
	 */
	public void setTermTxnCode (java.lang.String termTxnCode) {
		this.termTxnCode = termTxnCode;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblTxnCfgDspPK)) return false;
		else {
			com.huateng.po.epos.TblTxnCfgDspPK mObj = (com.huateng.po.epos.TblTxnCfgDspPK) obj;
			if (null != this.getTermTxnCode() && null != mObj.getTermTxnCode()) {
				if (!this.getTermTxnCode().equals(mObj.getTermTxnCode())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getUsageKey() && null != mObj.getUsageKey()) {
				if (!this.getUsageKey().equals(mObj.getUsageKey())) {
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
			if (null != this.getTermTxnCode()) {
				sb.append(this.getTermTxnCode().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getUsageKey()) {
				sb.append(this.getUsageKey().hashCode());
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