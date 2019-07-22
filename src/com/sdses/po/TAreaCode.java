package com.sdses.po;

import java.io.Serializable;

public class TAreaCode implements Serializable{

	private static final long serialVersionUID = 1483445779314143452L;


	private TAreaCodePK tAreaCodePK;
	private String levels;
	private String rootAreaCode;
	private String areaName;

	
	public TAreaCodePK gettAreaCodePK() {
		return tAreaCodePK;
	}
	public void settAreaCodePK(TAreaCodePK tAreaCodePK) {
		this.tAreaCodePK = tAreaCodePK;
	}
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
	}
	public String getRootAreaCode() {
		return rootAreaCode;
	}
	public void setRootAreaCode(String rootAreaCode) {
		this.rootAreaCode = rootAreaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
}