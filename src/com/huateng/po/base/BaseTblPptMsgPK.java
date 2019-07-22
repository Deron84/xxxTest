package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblPptMsgPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.Integer pptId;
	private java.lang.String usageKey;
	private java.lang.String msgType;
	private java.lang.String verId;


	public BaseTblPptMsgPK () {}
	
	public BaseTblPptMsgPK (
		java.lang.Integer pptId,
		java.lang.String usageKey,
		java.lang.String msgType,
		java.lang.String verId) {

		this.setPptId(pptId);
		this.setUsageKey(usageKey);
		this.setMsgType(msgType);
		this.setVerId(verId);
	}


	/**
	 * Return the value associated with the column: PPT_ID
	 */
	public java.lang.Integer getPptId () {
		return pptId;
	}

	/**
	 * Set the value related to the column: PPT_ID
	 * @param pptId the PPT_ID value
	 */
	public void setPptId (java.lang.Integer pptId) {
		this.pptId = pptId;
	}



	/**
	 * Return the value associated with the column: USAGE_KEY
	 */
	public java.lang.String getUsageKey () {
		return usageKey;
	}

	/**
	 * Set the value related to the column: USAGE_KEY
	 * @param usageKey the USAGE_KEY value
	 */
	public void setUsageKey (java.lang.String usageKey) {
		this.usageKey = usageKey;
	}



	/**
	 * Return the value associated with the column: MSG_TYPE
	 */
	public java.lang.String getMsgType () {
		return msgType;
	}

	/**
	 * Set the value related to the column: MSG_TYPE
	 * @param msgType the MSG_TYPE value
	 */
	public void setMsgType (java.lang.String msgType) {
		this.msgType = msgType;
	}



	/**
	 * Return the value associated with the column: VER_ID
	 */
	public java.lang.String getVerId () {
		return verId;
	}

	/**
	 * Set the value related to the column: VER_ID
	 * @param verId the VER_ID value
	 */
	public void setVerId (java.lang.String verId) {
		this.verId = verId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblPptMsgPK)) return false;
		else {
			com.huateng.po.epos.TblPptMsgPK mObj = (com.huateng.po.epos.TblPptMsgPK) obj;
			if (null != this.getPptId() && null != mObj.getPptId()) {
				if (!this.getPptId().equals(mObj.getPptId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getUsageKey() && null != mObj.getUsageKey()) {
				if (!this.getUsageKey().equals(mObj.getUsageKey())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getMsgType() && null != mObj.getMsgType()) {
				if (!this.getMsgType().equals(mObj.getMsgType())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getVerId() && null != mObj.getVerId()) {
				if (!this.getVerId().equals(mObj.getVerId())) {
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
			if (null != this.getPptId()) {
				sb.append(this.getPptId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getUsageKey()) {
				sb.append(this.getUsageKey().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getMsgType()) {
				sb.append(this.getMsgType().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getVerId()) {
				sb.append(this.getVerId().hashCode());
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