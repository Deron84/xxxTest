package com.sdses.po;

import java.io.Serializable;

public class TAirpayMapPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 银联商户号
	private String ylMchntNo;
	
	// 银联终端号
	private String ylTermNo;
	
	// 支付类型
	private String payFlag;

	public String getYlMchntNo() {
		return ylMchntNo;
	}

	public void setYlMchntNo(String ylMchntNo) {
		this.ylMchntNo = ylMchntNo;
	}

	public String getYlTermNo() {
		return ylTermNo;
	}

	public void setYlTermNo(String ylTermNo) {
		this.ylTermNo = ylTermNo;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

}
