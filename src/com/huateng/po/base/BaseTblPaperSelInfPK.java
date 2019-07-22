package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblPaperSelInfPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String paperId;
	private java.lang.String quesId;
	private java.lang.String selOptId;
	private java.lang.String selMchtId;


	public BaseTblPaperSelInfPK () {}
	
	public BaseTblPaperSelInfPK (
		java.lang.String paperId,
		java.lang.String quesId,
		java.lang.String selOptId,
		java.lang.String selMchtId) {

		this.setPaperId(paperId);
		this.setQuesId(quesId);
		this.setSelOptId(selOptId);
		this.setSelMchtId(selMchtId);
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
	 * Return the value associated with the column: SEL_OPT_ID
	 */
	public java.lang.String getSelOptId () {
		return selOptId;
	}

	/**
	 * Set the value related to the column: SEL_OPT_ID
	 * @param selOptId the SEL_OPT_ID value
	 */
	public void setSelOptId (java.lang.String selOptId) {
		this.selOptId = selOptId;
	}



	/**
	 * Return the value associated with the column: SEL_MCHT_ID
	 */
	public java.lang.String getSelMchtId () {
		return selMchtId;
	}

	/**
	 * Set the value related to the column: SEL_MCHT_ID
	 * @param selMchtId the SEL_MCHT_ID value
	 */
	public void setSelMchtId (java.lang.String selMchtId) {
		this.selMchtId = selMchtId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchtSrv.TblPaperSelInfPK)) return false;
		else {
			com.huateng.po.mchtSrv.TblPaperSelInfPK mObj = (com.huateng.po.mchtSrv.TblPaperSelInfPK) obj;
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
			if (null != this.getSelOptId() && null != mObj.getSelOptId()) {
				if (!this.getSelOptId().equals(mObj.getSelOptId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getSelMchtId() && null != mObj.getSelMchtId()) {
				if (!this.getSelMchtId().equals(mObj.getSelMchtId())) {
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
			if (null != this.getSelOptId()) {
				sb.append(this.getSelOptId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getSelMchtId()) {
				sb.append(this.getSelMchtId().hashCode());
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