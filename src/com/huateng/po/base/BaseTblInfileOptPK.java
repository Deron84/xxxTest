package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblInfileOptPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String brhId;
	private java.lang.String instDate;


	public BaseTblInfileOptPK () {}
	
	public BaseTblInfileOptPK (
		java.lang.String brhId,
		java.lang.String instDate) {

		this.setBrhId(brhId);
		this.setInstDate(instDate);
	}


	/**
	 * Return the value associated with the column: BRH_ID
	 */
	public java.lang.String getBrhId () {
		return brhId;
	}

	/**
	 * Set the value related to the column: BRH_ID
	 * @param brhId the BRH_ID value
	 */
	public void setBrhId (java.lang.String brhId) {
		this.brhId = brhId;
	}



	/**
	 * Return the value associated with the column: INST_DATE
	 */
	public java.lang.String getInstDate () {
		return instDate;
	}

	/**
	 * Set the value related to the column: INST_DATE
	 * @param instDate the INST_DATE value
	 */
	public void setInstDate (java.lang.String instDate) {
		this.instDate = instDate;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.settle.TblInfileOptPK)) return false;
		else {
			com.huateng.po.settle.TblInfileOptPK mObj = (com.huateng.po.settle.TblInfileOptPK) obj;
			if (null != this.getBrhId() && null != mObj.getBrhId()) {
				if (!this.getBrhId().equals(mObj.getBrhId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getInstDate() && null != mObj.getInstDate()) {
				if (!this.getInstDate().equals(mObj.getInstDate())) {
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
			if (null != this.getBrhId()) {
				sb.append(this.getBrhId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getInstDate()) {
				sb.append(this.getInstDate().hashCode());
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