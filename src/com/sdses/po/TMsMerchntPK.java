package com.sdses.po;

import java.io.Serializable;

public class TMsMerchntPK implements Serializable{
	
	private static final long serialVersionUID = -6628322516522332668L;
	
	//外部商户号
	private String outMchntId;

	public String getOutMchntId() {
		return outMchntId;
	}

	public void setOutMchntId(String outMchntId) {
		this.outMchntId = outMchntId;
	}
	
}
