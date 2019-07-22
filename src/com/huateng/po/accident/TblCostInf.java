package com.huateng.po.accident;

public class TblCostInf {

	private java.lang.String id;		//流水号，主键

	private java.lang.String state;		//状态
	private java.lang.String amount;	//费用金额
	private java.lang.String cardId;	//账户号
	private java.lang.String mchtNo;	//商户号
	private java.lang.String remarkInf;	//备注信息
	private java.lang.String crtOprId;	//创建操作员
	private java.lang.String updOprId;	//修改操作员
	private java.lang.String recCrtTs;	//创建时间
	private java.lang.String recUpdTs;	//修改时间
	private java.lang.String reserve1;	//保留域

	public java.lang.String getId () {
		return id;
	}

	public void setId (java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getState () {
		return state;
	}

	public void setState (java.lang.String state) {
		this.state = state;
	}

	public java.lang.String getAmount () {
		return amount;
	}

	public void setAmount (java.lang.String amount) {
		this.amount = amount;
	}

	public java.lang.String getCardId () {
		return cardId;
	}

	public void setCardId (java.lang.String cardId) {
		this.cardId = cardId;
	}

	public java.lang.String getMchtNo () {
		return mchtNo;
	}

	public void setMchtNo (java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public java.lang.String getRemarkInf () {
		return remarkInf;
	}

	public void setRemarkInf (java.lang.String remarkInf) {
		this.remarkInf = remarkInf;
	}

	public java.lang.String getCrtOprId () {
		return crtOprId;
	}

	public void setCrtOprId (java.lang.String crtOprId) {
		this.crtOprId = crtOprId;
	}

	public java.lang.String getUpdOprId () {
		return updOprId;
	}

	public void setUpdOprId (java.lang.String updOprId) {
		this.updOprId = updOprId;
	}

	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public java.lang.String getReserve1 () {
		return reserve1;
	}

	public void setReserve1 (java.lang.String reserve1) {
		this.reserve1 = reserve1;
	}
}