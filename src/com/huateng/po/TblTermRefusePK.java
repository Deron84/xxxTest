package com.huateng.po;

import java.io.Serializable;

public class TblTermRefusePK implements Serializable {

	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String txnTime;

	private java.lang.String termId;

	private String mchtCd;

	
	public TblTermRefusePK(String txnTime, String termId, String mchtCd) {
		super();
		this.txnTime = txnTime;
		this.termId = termId;
		this.mchtCd = mchtCd;
	}

	public String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}

	/**
	 * 
	 */
	public TblTermRefusePK() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param currentDateTime
	 * @param termId2
	 */
	public TblTermRefusePK(String txnTime, String termId) {
		this.txnTime = txnTime;
		this.termId = termId;
	}

	/**
	 * Return the value associated with the column: txn_time
	 */
	public java.lang.String getTxnTime() {
		return txnTime;
	}

	/**
	 * Set the value related to the column: txn_time
	 * 
	 * @param txnTime
	 *            the txn_time value
	 */
	public void setTxnTime(java.lang.String txnTime) {
		this.txnTime = txnTime;
	}

	/**
	 * Return the value associated with the column: term_id
	 */
	public java.lang.String getTermId() {
		return termId;
	}

	/**
	 * Set the value related to the column: term_id
	 * 
	 * @param termId
	 *            the term_id value
	 */
	public void setTermId(java.lang.String termId) {
		this.termId = termId;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.huateng.po.TblTermRefusePK))
			return false;
		else {
			com.huateng.po.TblTermRefusePK mObj = (com.huateng.po.TblTermRefusePK) obj;
			if (null != this.getTxnTime() && null != mObj.getTxnTime()) {
				if (!this.getTxnTime().equals(mObj.getTxnTime())) {
					return false;
				}
			} else {
				return false;
			}
			if (null != this.getTermId() && null != mObj.getTermId()) {
				if (!this.getTermId().equals(mObj.getTermId())) {
					return false;
				}
			} else {
				return false;
			}
			return true;
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getTxnTime()) {
				sb.append(this.getTxnTime().hashCode());
				sb.append(":");
			} else {
				return super.hashCode();
			}
			if (null != this.getTermId()) {
				sb.append(this.getTermId().hashCode());
				sb.append(":");
			} else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}
}