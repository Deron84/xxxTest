package com.sdses.po;

import java.io.Serializable;

public class TcMchntMapPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//银联商户号
    private String ylMchntNo;
    
    //银联终端号
    private String ylTermNo;
    
    // 系统硬件编号
    private String termFixNo;
    
    // 第三方机构
    private String brhFlag;
    
    // 扫码方式
    private String scanFlag;

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

	public String getTermFixNo() {
		return termFixNo;
	}

	public void setTermFixNo(String termFixNo) {
		this.termFixNo = termFixNo;
	}

	public String getBrhFlag() {
		return brhFlag;
	}

	public void setBrhFlag(String brhFlag) {
		this.brhFlag = brhFlag;
	}

	public String getScanFlag() {
		return scanFlag;
	}

	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}
}
