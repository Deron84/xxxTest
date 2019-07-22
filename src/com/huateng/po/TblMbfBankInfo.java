package com.huateng.po;

import java.io.Serializable;



public class TblMbfBankInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String elecbankno;
	private java.lang.String banktype;
	private java.lang.String bankname;
	private java.lang.String dpbankno;
	private java.lang.String ccpcno;
	private java.lang.String banktel;
	private java.lang.String bankper;
	private java.lang.String bankaddr;
	private java.lang.String bnkssname;


	public java.lang.String getId() {
		return id;
	}




	public void setId(java.lang.String id) {
		this.id = id;
	}




	public java.lang.String getElecbankno() {
		return elecbankno;
	}




	public void setElecbankno(java.lang.String elecbankno) {
		this.elecbankno = elecbankno;
	}




	public java.lang.String getBanktype() {
		return banktype;
	}




	public void setBanktype(java.lang.String banktype) {
		this.banktype = banktype;
	}




	public java.lang.String getBankname() {
		return bankname;
	}




	public void setBankname(java.lang.String bankname) {
		this.bankname = bankname;
	}




	public java.lang.String getDpbankno() {
		return dpbankno;
	}




	public void setDpbankno(java.lang.String dpbankno) {
		this.dpbankno = dpbankno;
	}




	public java.lang.String getCcpcno() {
		return ccpcno;
	}




	public void setCcpcno(java.lang.String ccpcno) {
		this.ccpcno = ccpcno;
	}




	public java.lang.String getBanktel() {
		return banktel;
	}




	public void setBanktel(java.lang.String banktel) {
		this.banktel = banktel;
	}




	public java.lang.String getBankper() {
		return bankper;
	}




	public void setBankper(java.lang.String bankper) {
		this.bankper = bankper;
	}




	public java.lang.String getBankaddr() {
		return bankaddr;
	}




	public void setBankaddr(java.lang.String bankaddr) {
		this.bankaddr = bankaddr;
	}




	public java.lang.String getBnkssname() {
		return bnkssname;
	}




	public void setBnkssname(java.lang.String bnkssname) {
		this.bnkssname = bnkssname;
	}




	/**
	 * 
	 */
	public TblMbfBankInfo() {
		super();
		// TODO Auto-generated constructor stub
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblMbfBankInfo)) return false;
		else {
			com.huateng.po.TblMbfBankInfo TblMbfBankInfo = (com.huateng.po.TblMbfBankInfo) obj;
			if (null == this.getId() || null == TblMbfBankInfo.getId()) return false;
			else return (this.getId().equals(TblMbfBankInfo.getId()));
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