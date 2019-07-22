package com.rail.po.vo;

import java.util.Date;

/**  
* @author syl
* @version 创建时间：2019年4月16日 下午3:06:52  
* @desc api 开门时用
*/
public class ApiRailWorkAccessOpenVo {
	private Date workStart;
	private String workCode;
	private long workTeam;
	private String workTeamName;
	private String note1;
	public Date getWorkStart() {
		return workStart;
	}
	public void setWorkStart(Date workStart) {
		this.workStart = workStart;
	}
	public String getWorkCode() {
		return workCode;
	}
	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
	public long getWorkTeam() {
		return workTeam;
	}
	public void setWorkTeam(long workTeam) {
		this.workTeam = workTeam;
	}
	public String getWorkTeamName() {
		return workTeamName;
	}
	public void setWorkTeamName(String workTeamName) {
		this.workTeamName = workTeamName;
	}
	public ApiRailWorkAccessOpenVo(Date workStart, String workCode, long workTeam, String workTeamName) {
		super();
		this.workStart = workStart;
		this.workCode = workCode;
		this.workTeam = workTeam;
		this.workTeamName = workTeamName;
	}
	public ApiRailWorkAccessOpenVo(Date workStart, String workCode, long workTeam, String workTeamName,String note1) {
		super();
		this.workStart = workStart;
		this.workCode = workCode;
		this.workTeam = workTeam;
		this.workTeamName = workTeamName;
		this.note1= note1;
	}
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	
	
	
	
	
	



	

	
	

}

