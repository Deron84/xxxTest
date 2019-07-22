package com.huateng.po.mchnt.base;

import java.io.Serializable;

import com.huateng.po.mchnt.TblInfMchntTpPK;


public abstract class BaseTblInfMchntTpPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String mchntTp;
	private java.lang.String frnIn;


	public BaseTblInfMchntTpPK () {}
	
	public BaseTblInfMchntTpPK (
		java.lang.String mchntTp,
		java.lang.String frnIn) {

		this.setMchntTp(mchntTp);
		this.setFrnIn(frnIn);
	}


	/**
	 * Return the value associated with the column: MCHNT_TP
	 */
	public java.lang.String getMchntTp () {
		return mchntTp;
	}

	/**
	 * Set the value related to the column: MCHNT_TP
	 * @param mchntTp the MCHNT_TP value
	 */
	public void setMchntTp (java.lang.String mchntTp) {
		this.mchntTp = mchntTp;
	}



	/**
	 * Return the value associated with the column: FRN_IN
	 */
	public java.lang.String getFrnIn () {
		return frnIn;
	}

	/**
	 * Set the value related to the column: FRN_IN
	 * @param frnIn the FRN_IN value
	 */
	public void setFrnIn (java.lang.String frnIn) {
		this.frnIn = frnIn;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblInfMchntTpPK)) return false;
		else {
			TblInfMchntTpPK mObj = (TblInfMchntTpPK) obj;
			if (null != this.getMchntTp() && null != mObj.getMchntTp()) {
				if (!this.getMchntTp().equals(mObj.getMchntTp())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getFrnIn() && null != mObj.getFrnIn()) {
				if (!this.getFrnIn().equals(mObj.getFrnIn())) {
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
			StringBuffer sb = new StringBuffer();
			if (null != this.getMchntTp()) {
				sb.append(this.getMchntTp().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getFrnIn()) {
				sb.append(this.getFrnIn().hashCode());
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