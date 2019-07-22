package com.huateng.po.mchnt;

import java.io.Serializable;


@SuppressWarnings("serial")
public class CstMchtFeeInfPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String mchtCd;
	private java.lang.String txnNum;
	private java.lang.String cardType;


	public CstMchtFeeInfPK () {}
	
	public CstMchtFeeInfPK (
		java.lang.String mchtCd,
		java.lang.String txnNum,
		java.lang.String cardType) {

		this.setMchtCd(mchtCd);
		this.setTxnNum(txnNum);
		this.setCardType(cardType);
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
	 * Return the value associated with the column: txn_num
	 */
	public java.lang.String getTxnNum () {
		return txnNum;
	}

	/**
	 * Set the value related to the column: txn_num
	 * @param txnNum the txn_num value
	 */
	public void setTxnNum (java.lang.String txnNum) {
		this.txnNum = txnNum;
	}



	/**
	 * Return the value associated with the column: card_type
	 */
	public java.lang.String getCardType () {
		return cardType;
	}

	/**
	 * Set the value related to the column: card_type
	 * @param cardType the card_type value
	 */
	public void setCardType (java.lang.String cardType) {
		this.cardType = cardType;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CstMchtFeeInfPK)) return false;
		else {
			CstMchtFeeInfPK mObj = (CstMchtFeeInfPK) obj;
			if (null != this.getMchtCd() && null != mObj.getMchtCd()) {
				if (!this.getMchtCd().equals(mObj.getMchtCd())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getTxnNum() && null != mObj.getTxnNum()) {
				if (!this.getTxnNum().equals(mObj.getTxnNum())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getCardType() && null != mObj.getCardType()) {
				if (!this.getCardType().equals(mObj.getCardType())) {
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
			if (null != this.getTxnNum()) {
				sb.append(this.getTxnNum().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getCardType()) {
				sb.append(this.getCardType().hashCode());
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