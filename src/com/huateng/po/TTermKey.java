package com.huateng.po;

import java.io.Serializable;

public class TTermKey implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 银联商户号
	private String ylMchntNo;
	
	// 银联终端号
	private String ylTermNo;
	
	// 主密钥
	private String cKey;

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

	public String getcKey() {
		return cKey;
	}

	public void setcKey(String cKey) {
		this.cKey = cKey;
	}

}
