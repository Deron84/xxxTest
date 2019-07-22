package com.huateng.po;

import java.io.Serializable;

public class TblMchntRefusePK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String mchntId;
	private java.lang.String txnTime;
	
	
	/**
	 * 
	 */
	public TblMchntRefusePK() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param mchntId2
	 * @param currentDateTime
	 */
	public TblMchntRefusePK(String mchntId, String txnTime) {
		this.mchntId = mchntId;
		this.txnTime = txnTime;
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
	 * Return the value associated with the column: txn_time
	 */
	public java.lang.String getTxnTime () {
		return txnTime;
	}

	/**
	 * Set the value related to the column: txn_time
	 * @param txnTime the txn_time value
	 */
	public void setTxnTime (java.lang.String txnTime) {
		this.txnTime = txnTime;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblMchntRefusePK)) return false;
		else {
			com.huateng.po.TblMchntRefusePK mObj = (com.huateng.po.TblMchntRefusePK) obj;
			if (null != this.getMchntId() && null != mObj.getMchntId()) {
				if (!this.getMchntId().equals(mObj.getMchntId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getTxnTime() && null != mObj.getTxnTime()) {
				if (!this.getTxnTime().equals(mObj.getTxnTime())) {
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
			if (null != this.getTxnTime()) {
				sb.append(this.getTxnTime().hashCode());
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