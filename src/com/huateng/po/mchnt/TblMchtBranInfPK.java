package com.huateng.po.mchnt;

import java.io.Serializable;


@SuppressWarnings("serial")
public class TblMchtBranInfPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String mchtCd;
	private java.lang.String branchCd;


	public TblMchtBranInfPK () {}
	
	public TblMchtBranInfPK (
		java.lang.String mchtCd,
		java.lang.String branchCd) {

		this.setMchtCd(mchtCd);
		this.setBranchCd(branchCd);
	}


	/**
	 * Return the value associated with the column: mcht_cd
	 */
	public java.lang.String getMchtCd () {
		return mchtCd;
	}

	/**
	 * Set the value related to the column: mcht_cd
	 * @param mchtCd the mcht_cd value
	 */
	public void setMchtCd (java.lang.String mchtCd) {
		this.mchtCd = mchtCd;
	}



	/**
	 * Return the value associated with the column: branch_cd
	 */
	public java.lang.String getBranchCd () {
		return branchCd;
	}

	/**
	 * Set the value related to the column: branch_cd
	 * @param branchCd the branch_cd value
	 */
	public void setBranchCd (java.lang.String branchCd) {
		this.branchCd = branchCd;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblMchtBranInfPK)) return false;
		else {
			TblMchtBranInfPK mObj = (TblMchtBranInfPK) obj;
			if (null != this.getMchtCd() && null != mObj.getMchtCd()) {
				if (!this.getMchtCd().equals(mObj.getMchtCd())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getBranchCd() && null != mObj.getBranchCd()) {
				if (!this.getBranchCd().equals(mObj.getBranchCd())) {
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
			if (null != this.getMchtCd()) {
				sb.append(this.getMchtCd().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getBranchCd()) {
				sb.append(this.getBranchCd().hashCode());
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