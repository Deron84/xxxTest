package com.sdses.po;

import java.io.Serializable;

public class TBrhDatadicPK implements Serializable{

	private static final long serialVersionUID = 4431482344451530393L;
	
	private String cBrhId;
	
	private String cBrhName;
	
	public String getcBrhId() {
		return cBrhId;
	}
	public void setcBrhId(String cBrhId) {
		this.cBrhId = cBrhId;
	}
	public String getcBrhName() {
		return cBrhName;
	}
	public void setcBrhName(String cBrhName) {
		this.cBrhName = cBrhName;
	}
	
}
