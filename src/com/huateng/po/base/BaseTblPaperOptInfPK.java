package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblPaperOptInfPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String paperId;
	private java.lang.String quesId;
	private java.lang.String optId;


	public BaseTblPaperOptInfPK () {}
	
	public BaseTblPaperOptInfPK (
		java.lang.String paperId,
		java.lang.String quesId,
		java.lang.String optId) {

		this.setPaperId(paperId);
		this.setQuesId(quesId);
		this.setOptId(optId);
	}


	/**
	 * Return the value associated with the column: PAPER_ID
	 */
	public java.lang.String getPaperId () {
		return paperId;
	}

	/**
	 * Set the value related to the column: PAPER_ID
	 * @param paperId the PAPER_ID value
	 */
	public void setPaperId (java.lang.String paperId) {
		this.paperId = paperId;
	}



	/**
	 * Return the value associated with the column: QUES_ID
	 */
	public java.lang.String getQuesId () {
		return quesId;
	}

	/**
	 * Set the value related to the column: QUES_ID
	 * @param quesId the QUES_ID value
	 */
	public void setQuesId (java.lang.String quesId) {
		this.quesId = quesId;
	}



	/**
	 * Return the value associated with the column: OPT_ID
	 */
	public java.lang.String getOptId () {
		return optId;
	}

	/**
	 * Set the value related to the column: OPT_ID
	 * @param optId the OPT_ID value
	 */
	public void setOptId (java.lang.String optId) {
		this.optId = optId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchtSrv.TblPaperOptInfPK)) return false;
		else {
			com.huateng.po.mchtSrv.TblPaperOptInfPK mObj = (com.huateng.po.mchtSrv.TblPaperOptInfPK) obj;
			if (null != this.getPaperId() && null != mObj.getPaperId()) {
				if (!this.getPaperId().equals(mObj.getPaperId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getQuesId() && null != mObj.getQuesId()) {
				if (!this.getQuesId().equals(mObj.getQuesId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getOptId() && null != mObj.getOptId()) {
				if (!this.getOptId().equals(mObj.getOptId())) {
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
			if (null != this.getPaperId()) {
				sb.append(this.getPaperId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getQuesId()) {
				sb.append(this.getQuesId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getOptId()) {
				sb.append(this.getOptId().hashCode());
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