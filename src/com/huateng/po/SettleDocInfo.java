package com.huateng.po;

import java.io.Serializable;

public class SettleDocInfo  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String REF = "SettleDocInfo";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_PAYER_ACCOUNT_NO = "PayerAccountNo";
	public static String PROP_PAY_BANK_NO = "PayBankNo";
	public static String PROP_RESERVED = "Reserved";
	public static String PROP_ID = "Id";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_REC_UPD_USER = "RecUpdUser";
	public static String PROP_SETTLE_BUS_NO = "SettleBusNo";
	public static String PROP_IN_BANK_SETTLE_NO = "InBankSettleNo";
	public static String PROP_PAYER_NAME = "PayerName";
	public static String PROP_EXCHANGE_NO = "ExchangeNo";
	public static String PROP_INNER_ACCT = "InnerAcct";
	public static String PROP_INNER_ACCT1 = "InnerAcct1";
	public static String PROP_INNER_ACCT2 = "InnerAcct2";


	// constructors
	public SettleDocInfo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public SettleDocInfo (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public SettleDocInfo (
		java.lang.String id,
		java.lang.String exchangeNo) {

		this.setId(id);
		this.setExchangeNo(exchangeNo);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String exchangeNo;
	private java.lang.String payerName;
	private java.lang.String payerAccountNo;
	private java.lang.String payBankNo;
	private java.lang.String inBankSettleNo;
	private java.lang.String settleBusNo;
	private java.lang.String reserved;
	private java.lang.String recUpdUser;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;
	private java.lang.String innerAcct;
	private java.lang.String innerAcct1;
	private java.lang.String innerAcct2;
	



	/**
	 * @return the innerAcct1
	 */
	public java.lang.String getInnerAcct1() {
		return innerAcct1;
	}

	/**
	 * @param innerAcct1 the innerAcct1 to set
	 */
	public void setInnerAcct1(java.lang.String innerAcct1) {
		this.innerAcct1 = innerAcct1;
	}

	/**
	 * @return the innerAcct2
	 */
	public java.lang.String getInnerAcct2() {
		return innerAcct2;
	}

	/**
	 * @param innerAcct2 the innerAcct2 to set
	 */
	public void setInnerAcct2(java.lang.String innerAcct2) {
		this.innerAcct2 = innerAcct2;
	}

	/**
	 * @return the innerAcct
	 */
	public java.lang.String getInnerAcct() {
		return innerAcct;
	}

	/**
	 * @param innerAcct the innerAcct to set
	 */
	public void setInnerAcct(java.lang.String innerAcct) {
		this.innerAcct = innerAcct;
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="sequence"
     *  column="BRH_ID"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: EXCHANGE_NO
	 */
	public java.lang.String getExchangeNo () {
		return exchangeNo;
	}

	/**
	 * Set the value related to the column: EXCHANGE_NO
	 * @param exchangeNo the EXCHANGE_NO value
	 */
	public void setExchangeNo (java.lang.String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}



	/**
	 * Return the value associated with the column: PAYER_NAME
	 */
	public java.lang.String getPayerName () {
		return payerName;
	}

	/**
	 * Set the value related to the column: PAYER_NAME
	 * @param payerName the PAYER_NAME value
	 */
	public void setPayerName (java.lang.String payerName) {
		this.payerName = payerName;
	}



	/**
	 * Return the value associated with the column: PAYER_ACCOUNT_NO
	 */
	public java.lang.String getPayerAccountNo () {
		return payerAccountNo;
	}

	/**
	 * Set the value related to the column: PAYER_ACCOUNT_NO
	 * @param payerAccountNo the PAYER_ACCOUNT_NO value
	 */
	public void setPayerAccountNo (java.lang.String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}



	/**
	 * Return the value associated with the column: PAY_BANK_NO
	 */
	public java.lang.String getPayBankNo () {
		return payBankNo;
	}

	/**
	 * Set the value related to the column: PAY_BANK_NO
	 * @param payBankNo the PAY_BANK_NO value
	 */
	public void setPayBankNo (java.lang.String payBankNo) {
		this.payBankNo = payBankNo;
	}



	/**
	 * Return the value associated with the column: IN_BANK_SETTLE_NO
	 */
	public java.lang.String getInBankSettleNo () {
		return inBankSettleNo;
	}

	/**
	 * Set the value related to the column: IN_BANK_SETTLE_NO
	 * @param inBankSettleNo the IN_BANK_SETTLE_NO value
	 */
	public void setInBankSettleNo (java.lang.String inBankSettleNo) {
		this.inBankSettleNo = inBankSettleNo;
	}



	/**
	 * Return the value associated with the column: SETTLE_BUS_NO
	 */
	public java.lang.String getSettleBusNo () {
		return settleBusNo;
	}

	/**
	 * Set the value related to the column: SETTLE_BUS_NO
	 * @param settleBusNo the SETTLE_BUS_NO value
	 */
	public void setSettleBusNo (java.lang.String settleBusNo) {
		this.settleBusNo = settleBusNo;
	}



	/**
	 * Return the value associated with the column: RESERVED
	 */
	public java.lang.String getReserved () {
		return reserved;
	}

	/**
	 * Set the value related to the column: RESERVED
	 * @param reserved the RESERVED value
	 */
	public void setReserved (java.lang.String reserved) {
		this.reserved = reserved;
	}



	/**
	 * Return the value associated with the column: REC_UPD_USER
	 */
	public java.lang.String getRecUpdUser () {
		return recUpdUser;
	}

	/**
	 * Set the value related to the column: REC_UPD_USER
	 * @param recUpdUser the REC_UPD_USER value
	 */
	public void setRecUpdUser (java.lang.String recUpdUser) {
		this.recUpdUser = recUpdUser;
	}

	/**
	 * Return the value associated with the column: REC_UPD_TS
	 */
	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: REC_UPD_TS
	 * @param recUpdTs the REC_UPD_TS value
	 */
	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}



	/**
	 * Return the value associated with the column: REC_CRT_TS
	 */
	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: REC_CRT_TS
	 * @param recCrtTs the REC_CRT_TS value
	 */
	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.SettleDocInfo)) return false;
		else {
			com.huateng.po.SettleDocInfo settleDocInfo = (com.huateng.po.SettleDocInfo) obj;
			if (null == this.getId() || null == settleDocInfo.getId()) return false;
			else return (this.getId().equals(settleDocInfo.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}