package com.rail.po.tool;

import java.util.Date;

public class RailToolMaintainView implements java.io.Serializable {
	private RailToolMaintain railToolMaintain; 
	private Date addDate;
	private String maintainUser;
	public RailToolMaintain getRailToolMaintain() {
		return railToolMaintain;
	}
	public void setRailToolMaintain(RailToolMaintain railToolMaintain) {
		this.railToolMaintain = railToolMaintain;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getMaintainUser() {
		return maintainUser;
	}
	public void setMaintainUser(String maintainUser) {
		this.maintainUser = maintainUser;
	}
	public RailToolMaintainView(RailToolMaintain railToolMaintain, Date addDate, String maintainUser) {
		super();
		this.railToolMaintain = railToolMaintain;
		this.addDate = addDate;
		this.maintainUser = maintainUser;
	}
	
}
