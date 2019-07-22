package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblPaperDefInfPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String quesId;
	private java.lang.String paperId;


	public BaseTblPaperDefInfPK () {}
	
	public BaseTblPaperDefInfPK (
		java.lang.String quesId,
		java.lang.String paperId) {

		this.setQuesId(quesId);
		this.setPaperId(paperId);
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchtSrv.TblPaperDefInfPK)) return false;
		else {
			com.huateng.po.mchtSrv.TblPaperDefInfPK mObj = (com.huateng.po.mchtSrv.TblPaperDefInfPK) obj;
			if (null != this.getQuesId() && null != mObj.getQuesId()) {
				if (!this.getQuesId().equals(mObj.getQuesId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getPaperId() && null != mObj.getPaperId()) {
				if (!this.getPaperId().equals(mObj.getPaperId())) {
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
			if (null != this.getQuesId()) {
				sb.append(this.getQuesId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getPaperId()) {
				sb.append(this.getPaperId().hashCode());
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