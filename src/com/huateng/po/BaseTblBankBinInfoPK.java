package com.huateng.po;

import java.io.Serializable;

import com.huateng.dao.impl.base.TblBankBinInfPK;

@SuppressWarnings("serial")
public abstract class BaseTblBankBinInfoPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;
	
	public java.lang.String getInsIdCd() {
		return InsIdCd;
	}

	public void setInsIdCd(java.lang.String insIdCd) {
		InsIdCd = insIdCd;
	}

	public java.lang.String getBinOffSet() {
		return BinOffSet;
	}

	public void setBinOffSet(java.lang.String binOffSet) {
		BinOffSet = binOffSet;
	}

	public java.lang.String getBinLen() {
		return BinLen;
	}

	public void setBinLen(java.lang.String binLen) {
		BinLen = binLen;
	}

	public java.lang.String getBinStaNo() {
		return BinStaNo;
	}

	public void setBinStaNo(java.lang.String binStaNo) {
		BinStaNo = binStaNo;
	}

	public java.lang.String getBinEndNo() {
		return BinEndNo;
	}

	public void setBinEndNo(java.lang.String binEndNo) {
		BinEndNo = binEndNo;
	}

	java.lang.String InsIdCd;
	java.lang.String BinOffSet;
	java.lang.String BinLen;
	java.lang.String BinStaNo;
	java.lang.String BinEndNo;

	public BaseTblBankBinInfoPK() {
	}

	public BaseTblBankBinInfoPK(java.lang.String InsIdCd,
			java.lang.String BinOffSet, java.lang.String BinLen,
			java.lang.String BinStaNo, java.lang.String BinEndNo) {
		this.setInsIdCd(InsIdCd);
		this.setBinOffSet(BinOffSet);
		this.setBinLen(BinLen);
		this.setBinStaNo(BinStaNo);
		this.setBinEndNo(BinEndNo);
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof TblBankBinInfPK))
			return false;
		else {
			TblBankBinInfPK mObj = (TblBankBinInfPK) obj;
			if (null != this.getInsIdCd() && null != mObj.getInsIdCd()) {
				if (!this.getInsIdCd().trim().equals(mObj.getInsIdCd().trim())) {
					return false;
				}
			} else {
				return false;
			}
			if (null != this.getBinOffSet() && null != mObj.getBinOffSet()) {
				if (!this.getBinOffSet().trim().equals(mObj.getBinOffSet().trim())) {
					return false;
				}
			} else {
				return false;
			}
			if (null != this.getBinLen() && null != mObj.getBinLen()) {
				if (!this.getBinLen().trim().equals(mObj.getBinLen().trim())) {
					return false;
				}
			} else {
				return false;
			}
			if (null != this.getBinStaNo() && null != mObj.getBinStaNo()) {
				if (!this.getBinStaNo().trim().equals(mObj.getBinStaNo().trim())) {
					return false;
				}
			} else {
				return false;
			}
			if (null != this.getBinEndNo() && null != mObj.getBinEndNo()) {
				if (!this.getBinEndNo().trim().equals(mObj.getBinEndNo().trim())) {
					return false;
				}
			} else {
				return false;
			}
			return true;
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuffer sb = new StringBuffer();
			if (null != this.getInsIdCd()) {
				sb.append(this.getInsIdCd().hashCode());
				sb.append(":");
			} else {
				return super.hashCode();
			}
			if (null != this.getBinOffSet()) {
				sb.append(this.getBinOffSet().hashCode());
				sb.append(":");
			} else {
				return super.hashCode();
			}
			if (null != this.getBinLen()) {
				sb.append(this.getBinLen().hashCode());
				sb.append(":");
			} else {
				return super.hashCode();
			}
			if (null != this.getBinStaNo()) {
				sb.append(this.getBinStaNo().hashCode());
				sb.append(":");
			} else {
				return super.hashCode();
			}
			if (null != this.getBinEndNo()) {
				sb.append(this.getBinEndNo().hashCode());
				sb.append(":");
			} else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}

}