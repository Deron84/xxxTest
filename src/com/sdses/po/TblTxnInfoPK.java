package com.sdses.po;

import java.io.Serializable;

public class TblTxnInfoPK implements Serializable {
	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String txnSeqNo;
	private java.lang.String acctDate;


	public TblTxnInfoPK () {}
	
	public TblTxnInfoPK (
		java.lang.String txnSeqNo,
		java.lang.String acctDate) {

		this.setTxnSeqNo(txnSeqNo);
		this.setAcctDate(acctDate);
	}


	/**
	 * Return the value associated with the column: TXN_SEQ_NO
	 */
	public java.lang.String getTxnSeqNo () {
		return txnSeqNo;
	}

	/**
	 * Set the value related to the column: TXN_SEQ_NO
	 * @param txnSeqNo the TXN_SEQ_NO value
	 */
	public void setTxnSeqNo (java.lang.String txnSeqNo) {
		this.txnSeqNo = txnSeqNo;
	}



	/**
	 * Return the value associated with the column: ACCT_DATE
	 */
	public java.lang.String getAcctDate () {
		return acctDate;
	}

	/**
	 * Set the value related to the column: ACCT_DATE
	 * @param acctDate the ACCT_DATE value
	 */
	public void setAcctDate (java.lang.String acctDate) {
		this.acctDate = acctDate;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblTxnInfoPK)) return false;
		else {
			TblTxnInfoPK mObj = (TblTxnInfoPK) obj;
			if (null != this.getTxnSeqNo() && null != mObj.getTxnSeqNo()) {
				if (!this.getTxnSeqNo().equals(mObj.getTxnSeqNo())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getAcctDate() && null != mObj.getAcctDate()) {
				if (!this.getAcctDate().equals(mObj.getAcctDate())) {
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
			if (null != this.getTxnSeqNo()) {
				sb.append(this.getTxnSeqNo().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getAcctDate()) {
				sb.append(this.getAcctDate().hashCode());
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