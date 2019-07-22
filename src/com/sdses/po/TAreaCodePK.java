package com.sdses.po;

import java.io.Serializable;

public class TAreaCodePK implements Serializable{

	private static final long serialVersionUID = 1793802938297946575L;
	//上级地区码
	private String upAreaCode;
	//地区码
	private String areaCode;
	
	public String getUpAreaCode() {
		return upAreaCode;
	}
	public void setUpAreaCode(String upAreaCode) {
		this.upAreaCode = upAreaCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
}