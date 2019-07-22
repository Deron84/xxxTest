package com.huateng.po;

import java.io.Serializable;

public class TblTermTmkLogPK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String termIdId;
	private java.lang.String batchNo;


	public TblTermTmkLogPK () {}
	
	public TblTermTmkLogPK (
		java.lang.String termIdId,
		java.lang.String batchNo) {

		this.setTermIdId(termIdId);
		this.setBatchNo(batchNo);
	}


	/**
	 * Return the value associated with the column: TERM_ID_ID
	 */
	public java.lang.String getTermIdId () {
		return termIdId;
	}

	/**
	 * Set the value related to the column: TERM_ID_ID
	 * @param termIdId the TERM_ID_ID value
	 */
	public void setTermIdId (java.lang.String termIdId) {
		this.termIdId = termIdId;
	}



	/**
	 * Return the value associated with the column: BATCH_NO
	 */
	public java.lang.String getBatchNo () {
		return batchNo;
	}

	/**
	 * Set the value related to the column: BATCH_NO
	 * @param batchNo the BATCH_NO value
	 */
	public void setBatchNo (java.lang.String batchNo) {
		this.batchNo = batchNo;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblTermTmkLogPK)) return false;
		else {
			com.huateng.po.TblTermTmkLogPK mObj = (com.huateng.po.TblTermTmkLogPK) obj;
			if (null != this.getTermIdId() && null != mObj.getTermIdId()) {
				if (!this.getTermIdId().equals(mObj.getTermIdId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getBatchNo() && null != mObj.getBatchNo()) {
				if (!this.getBatchNo().equals(mObj.getBatchNo())) {
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
			if (null != this.getTermIdId()) {
				sb.append(this.getTermIdId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getBatchNo()) {
				sb.append(this.getBatchNo().hashCode());
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