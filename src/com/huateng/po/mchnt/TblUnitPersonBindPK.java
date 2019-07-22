package com.huateng.po.mchnt;

import java.io.Serializable;


/**
 * project JSBConsole
 * date 2013-3-11
 * @author 樊东东
 */
public class TblUnitPersonBindPK implements Serializable{
	/**商户号 mcht_no*/
	private String mchtNo;
	/**卡号 card_no*/
	private String cardNo;
	/**卡标志card_flag  0-单位卡 1-个人卡*/
	private String cardFlag;
	
	public String getMchtNo() {
		return mchtNo;
	}
	
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	
	public String getCardNo() {
		return cardNo;
	}
	
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getCardFlag() {
		return cardFlag;
	}
	
	public void setCardFlag(String cardFlag) {
		this.cardFlag = cardFlag;
	}

	public TblUnitPersonBindPK(String mchtNo, String cardNo, String cardFlag) {
		super();
		this.mchtNo = mchtNo;
		this.cardNo = cardNo;
		this.cardFlag = cardFlag;
	}
	
}
