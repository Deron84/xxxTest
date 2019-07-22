package com.huateng.po;

import java.io.Serializable;

public class TblRoleFuncMapPK implements Serializable {
	private static final long serialVersionUID = 1L;


	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.Integer keyId;
	private java.lang.Integer valueId;
	
	
	/**
	 * 
	 */
	public TblRoleFuncMapPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Return the value associated with the column: KEY_ID
	 */
	public java.lang.Integer getKeyId () {
		return keyId;
	}

	/**
	 * Set the value related to the column: KEY_ID
	 * @param keyId the KEY_ID value
	 */
	public void setKeyId (java.lang.Integer keyId) {
		this.keyId = keyId;
	}



	/**
	 * Return the value associated with the column: VALUE_ID
	 */
	public java.lang.Integer getValueId () {
		return valueId;
	}

	/**
	 * Set the value related to the column: VALUE_ID
	 * @param valueId the VALUE_ID value
	 */
	public void setValueId (java.lang.Integer valueId) {
		this.valueId = valueId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblRoleFuncMapPK)) return false;
		else {
			com.huateng.po.TblRoleFuncMapPK mObj = (com.huateng.po.TblRoleFuncMapPK) obj;
			if (null != this.getKeyId() && null != mObj.getKeyId()) {
				if (!this.getKeyId().equals(mObj.getKeyId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getValueId() && null != mObj.getValueId()) {
				if (!this.getValueId().equals(mObj.getValueId())) {
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
			if (null != this.getKeyId()) {
				sb.append(this.getKeyId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getValueId()) {
				sb.append(this.getValueId().hashCode());
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