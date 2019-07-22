package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblEmvParaPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String usageKey;
	private java.lang.String paraIdx;


	public BaseTblEmvParaPK () {}
	
	public BaseTblEmvParaPK (
		java.lang.String usageKey,
		java.lang.String paraIdx) {

		this.setUsageKey(usageKey);
		this.setParaIdx(paraIdx);
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
	 * Return the value associated with the column: PARA_IDX
	 */
	public java.lang.String getParaIdx () {
		return paraIdx;
	}

	/**
	 * Set the value related to the column: PARA_IDX
	 * @param paraIdx the PARA_IDX value
	 */
	public void setParaIdx (java.lang.String paraIdx) {
		this.paraIdx = paraIdx;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblEmvParaPK)) return false;
		else {
			com.huateng.po.TblEmvParaPK mObj = (com.huateng.po.TblEmvParaPK) obj;
			if (null != this.getUsageKey() && null != mObj.getUsageKey()) {
				if (!this.getUsageKey().equals(mObj.getUsageKey())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getParaIdx() && null != mObj.getParaIdx()) {
				if (!this.getParaIdx().equals(mObj.getParaIdx())) {
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
			if (null != this.getParaIdx()) {
				sb.append(this.getParaIdx().hashCode());
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