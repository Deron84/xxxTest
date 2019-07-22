package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblRspMsgPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String chgNo;
	private java.lang.String rspNo;


	public BaseTblRspMsgPK () {}
	
	public BaseTblRspMsgPK (
		java.lang.String chgNo,
		java.lang.String rspNo) {

		this.setChgNo(chgNo);
		this.setRspNo(rspNo);
	}


	/**
	 * Return the value associated with the column: CHG_NO
	 */
	public java.lang.String getChgNo () {
		return chgNo;
	}

	/**
	 * Set the value related to the column: CHG_NO
	 * @param chgNo the CHG_NO value
	 */
	public void setChgNo (java.lang.String chgNo) {
		this.chgNo = chgNo;
	}




	public java.lang.String getRspNo () {
		return rspNo;
	}


	public void setRspNo (java.lang.String rspNo) {
		this.rspNo = rspNo;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblRspMsgPK)) return false;
		else {
			com.huateng.po.epos.TblRspMsgPK mObj = (com.huateng.po.epos.TblRspMsgPK) obj;
			if (null != this.getChgNo() && null != mObj.getChgNo()) {
				if (!this.getChgNo().equals(mObj.getChgNo())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getRspNo() && null != mObj.getRspNo()) {
				if (!this.getRspNo().equals(mObj.getRspNo())) {
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
			if (null != this.getChgNo()) {
				sb.append(this.getChgNo().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getRspNo()) {
				sb.append(this.getRspNo().hashCode());
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