package com.huateng.po.mchnt;

import java.io.Serializable;


public class TblMchtCheckInfPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String mchtNo;
	private java.lang.String checkDate;


	public TblMchtCheckInfPK () {}
	
	public TblMchtCheckInfPK (
		java.lang.String mchtNo,
		java.lang.String checkDate) {

		this.setMchtNo(mchtNo);
		this.setCheckDate(checkDate);
	}

	public java.lang.String getMchtNo () {
		return mchtNo;
	}

	public void setMchtNo (java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public java.lang.String getCheckDate () {
		return checkDate;
	}

	public void setCheckDate (java.lang.String checkDate) {
		this.checkDate = checkDate;
	}
}