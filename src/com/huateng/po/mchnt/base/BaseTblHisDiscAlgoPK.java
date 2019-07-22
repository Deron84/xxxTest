package com.huateng.po.mchnt.base;

import java.io.Serializable;

import com.huateng.po.mchnt.TblHisDiscAlgoPK;


public abstract class BaseTblHisDiscAlgoPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String discId;
	private java.lang.Integer indexNum;


	public BaseTblHisDiscAlgoPK () {}
	
	public BaseTblHisDiscAlgoPK (
		java.lang.String discId,
		java.lang.Integer indexNum) {

		this.setDiscId(discId);
		this.setIndexNum(indexNum);
	}



	public java.lang.String getDiscId() {
		return discId;
	}

	public void setDiscId(java.lang.String discId) {
		this.discId = discId;
	}

	/**
	 * Return the value associated with the column: INDEX_NUM
	 */
	public java.lang.Integer getIndexNum () {
		return indexNum;
	}

	/**
	 * Set the value related to the column: INDEX_NUM
	 * @param indexNum the INDEX_NUM value
	 */
	public void setIndexNum (java.lang.Integer indexNum) {
		this.indexNum = indexNum;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblHisDiscAlgoPK)) return false;
		else {
			TblHisDiscAlgoPK mObj = (TblHisDiscAlgoPK) obj;
			if (null != this.getDiscId() && null != mObj.getDiscId()) {
				if (!this.getDiscId().equals(mObj.getDiscId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getIndexNum() && null != mObj.getIndexNum()) {
				if (!this.getIndexNum().equals(mObj.getIndexNum())) {
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
			if (null != this.getDiscId()) {
				sb.append(this.getDiscId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getIndexNum()) {
				sb.append(this.getIndexNum().hashCode());
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