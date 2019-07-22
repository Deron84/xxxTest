package com.sdses.po;

import java.io.Serializable;

public class TMsPayChannelPK implements Serializable{

	private static final long serialVersionUID = 1593642796128188912L;
	//外部商户号
	private String outMchntId;
	//民生商户号
	private String cmbcMchntId;
	public String getOutMchntId() {
		return outMchntId;
	}
	public void setOutMchntId(String outMchntId) {
		this.outMchntId = outMchntId;
	}
	public String getCmbcMchntId() {
		return cmbcMchntId;
	}
	public void setCmbcMchntId(String cmbcMchntId) {
		this.cmbcMchntId = cmbcMchntId;
	}
	
}