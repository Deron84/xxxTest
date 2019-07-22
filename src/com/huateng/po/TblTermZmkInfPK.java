package com.huateng.po;

import java.io.Serializable;

public class TblTermZmkInfPK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String mchntId;
	private java.lang.String termId;
	
	
	/**
	 * 
	 */
	public TblTermZmkInfPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param mchntId2
	 * @param termId2
	 */
	public TblTermZmkInfPK(String mchntId, String termId) {
		this.mchntId = mchntId;
		this.termId = termId;
	}

	/**
	 * Return the value associated with the column: mchnt_id
	 */
	public java.lang.String getMchntId () {
		return mchntId;
	}

	/**
	 * Set the value related to the column: mchnt_id
	 * @param mchntId the mchnt_id value
	 */
	public void setMchntId (java.lang.String mchntId) {
		this.mchntId = mchntId;
	}



	/**
	 * Return the value associated with the column: term_id
	 */
	public java.lang.String getTermId () {
		return termId;
	}

	/**
	 * Set the value related to the column: term_id
	 * @param termId the term_id value
	 */
	public void setTermId (java.lang.String termId) {
		this.termId = termId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblTermZmkInfPK)) return false;
		else {
			com.huateng.po.TblTermZmkInfPK mObj = (com.huateng.po.TblTermZmkInfPK) obj;
			if (null != this.getMchntId() && null != mObj.getMchntId()) {
				if (!this.getMchntId().equals(mObj.getMchntId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getTermId() && null != mObj.getTermId()) {
				if (!this.getTermId().equals(mObj.getTermId())) {
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
			if (null != this.getMchntId()) {
				sb.append(this.getMchntId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getTermId()) {
				sb.append(this.getTermId().hashCode());
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