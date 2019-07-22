package com.huateng.po.accident;

import java.io.Serializable;

public class TblRTxnPK implements Serializable {

	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String instDate;
	private java.lang.String sysSeqNum;


	public TblRTxnPK () {}
	
	public TblRTxnPK (String instDate,String sysSeqNum) {

		this.setInstDate(instDate);
		this.setSysSeqNum(sysSeqNum);
	}


	/**
	 * @return the instDate
	 */
	public java.lang.String getInstDate() {
		return instDate;
	}

	/**
	 * @param instDate the instDate to set
	 */
	public void setInstDate(java.lang.String instDate) {
		this.instDate = instDate;
	}

	/**
	 * @return the sysSeqNum
	 */
	public java.lang.String getSysSeqNum() {
		return sysSeqNum;
	}

	/**
	 * @param sysSeqNum the sysSeqNum to set
	 */
	public void setSysSeqNum(java.lang.String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblRTxnPK)) return false;
		else {
			TblRTxnPK mObj = (TblRTxnPK) obj;
			if (null != this.getInstDate() && null != mObj.getInstDate()) {
				if (!this.getInstDate().equals(mObj.getInstDate())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getSysSeqNum() && null != mObj.getSysSeqNum()) {
				if (!this.getSysSeqNum().equals(mObj.getSysSeqNum())) {
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
			if (null != this.getInstDate()) {
				sb.append(this.getInstDate().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getSysSeqNum()) {
				sb.append(this.getSysSeqNum().hashCode());
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