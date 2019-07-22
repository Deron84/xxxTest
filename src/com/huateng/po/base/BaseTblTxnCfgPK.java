package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblTxnCfgPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String termTxnCode;
	private java.lang.String usageKey;
	private java.lang.Integer cmdIdx;


	public BaseTblTxnCfgPK () {}
	
	public BaseTblTxnCfgPK (
		java.lang.String termTxnCode,
		java.lang.String usageKey,
		java.lang.Integer cmdIdx) {

		this.setTermTxnCode(termTxnCode);
		this.setUsageKey(usageKey);
		this.setCmdIdx(cmdIdx);
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



	/**
	 * Return the value associated with the column: CMD_IDX
	 */
	public java.lang.Integer getCmdIdx () {
		return cmdIdx;
	}

	/**
	 * Set the value related to the column: CMD_IDX
	 * @param cmdIdx the CMD_IDX value
	 */
	public void setCmdIdx (java.lang.Integer cmdIdx) {
		this.cmdIdx = cmdIdx;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblTxnCfgPK)) return false;
		else {
			com.huateng.po.epos.TblTxnCfgPK mObj = (com.huateng.po.epos.TblTxnCfgPK) obj;
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
			if (null != this.getCmdIdx() && null != mObj.getCmdIdx()) {
				if (!this.getCmdIdx().equals(mObj.getCmdIdx())) {
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
			if (null != this.getCmdIdx()) {
				sb.append(this.getCmdIdx().hashCode());
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