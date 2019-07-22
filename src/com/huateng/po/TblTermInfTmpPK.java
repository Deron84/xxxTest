package com.huateng.po;

import java.io.Serializable;

import com.huateng.system.util.CommonFunction;

public class TblTermInfTmpPK implements Serializable {

	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String termId;

	private java.lang.String mchtCd;

	public TblTermInfTmpPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TblTermInfTmpPK(String termId, String mchtCd) {
		super();
		this.termId = CommonFunction.fillString(termId, ' ', 12, true);
		this.mchtCd = mchtCd;
	}

	public java.lang.String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(java.lang.String mchtCd) {
		this.mchtCd = mchtCd;
	}

	/**
	 * Return the value associated with the column: TERM_ID
	 */
	public java.lang.String getTermId() {
		return termId;
	}

	/**
	 * Set the value related to the column: TERM_ID
	 * 
	 * @param termId
	 *            the TERM_ID value
	 */
	public void setTermId(java.lang.String termId) {
		this.termId = CommonFunction.fillString(termId, ' ', 12, true);
		;
	}

}