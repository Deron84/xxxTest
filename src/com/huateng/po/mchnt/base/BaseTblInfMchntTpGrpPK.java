package com.huateng.po.mchnt.base;

import java.io.Serializable;

import com.huateng.po.mchnt.TblInfMchntTpGrpPK;


public abstract class BaseTblInfMchntTpGrpPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String mchntTpGrp;
	private java.lang.String frnIn;


	public BaseTblInfMchntTpGrpPK () {}
	
	public BaseTblInfMchntTpGrpPK (
		java.lang.String mchntTpGrp,
		java.lang.String frnIn) {

		this.setMchntTpGrp(mchntTpGrp);
		this.setFrnIn(frnIn);
	}


	/**
	 * Return the value associated with the column: MCHNT_TP_GRP
	 */
	public java.lang.String getMchntTpGrp () {
		return mchntTpGrp;
	}

	/**
	 * Set the value related to the column: MCHNT_TP_GRP
	 * @param mchntTpGrp the MCHNT_TP_GRP value
	 */
	public void setMchntTpGrp (java.lang.String mchntTpGrp) {
		this.mchntTpGrp = mchntTpGrp;
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
		if (!(obj instanceof TblInfMchntTpGrpPK)) return false;
		else {
			TblInfMchntTpGrpPK mObj = (TblInfMchntTpGrpPK) obj;
			if (null != this.getMchntTpGrp() && null != mObj.getMchntTpGrp()) {
				if (!this.getMchntTpGrp().equals(mObj.getMchntTpGrp())) {
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
			if (null != this.getMchntTpGrp()) {
				sb.append(this.getMchntTpGrp().hashCode());
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