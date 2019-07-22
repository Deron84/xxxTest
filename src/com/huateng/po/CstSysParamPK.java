package com.huateng.po;

import java.io.Serializable;

public class CstSysParamPK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String owner;
	private java.lang.String key;
	

	/**
	 * @param owner2
	 * @param key2
	 */
	public CstSysParamPK(String owner, String key) {
		this.owner = owner;
		this.key = key;
	}

	/**
	 * 
	 */
	public CstSysParamPK() {}

	/**
	 * Return the value associated with the column: OWNER
	 */
	public java.lang.String getOwner () {
		return owner;
	}

	/**
	 * Set the value related to the column: OWNER
	 * @param owner the OWNER value
	 */
	public void setOwner (java.lang.String owner) {
		this.owner = owner;
	}



	/**
	 * Return the value associated with the column: OWNER_KEY
	 */
	public java.lang.String getKey () {
		return key;
	}

	/**
	 * Set the value related to the column: OWNER_KEY
	 * @param key the OWNER_KEY value
	 */
	public void setKey (java.lang.String key) {
		this.key = key;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.CstSysParamPK)) return false;
		else {
			com.huateng.po.CstSysParamPK mObj = (com.huateng.po.CstSysParamPK) obj;
			if (null != this.getOwner() && null != mObj.getOwner()) {
				if (!this.getOwner().equals(mObj.getOwner())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getKey() && null != mObj.getKey()) {
				if (!this.getKey().equals(mObj.getKey())) {
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
			if (null != this.getOwner()) {
				sb.append(this.getOwner().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getKey()) {
				sb.append(this.getKey().hashCode());
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