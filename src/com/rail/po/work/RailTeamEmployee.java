package com.rail.po.work;

import java.util.Date;

public class RailTeamEmployee implements java.io.Serializable {
	
	private long id;
	@SuppressWarnings("unused")
	private String employeeCode;
	private long workTeam;
	private String addUser;
	private Date addDate;
	private String note1;
	private String note2;
	private String note3;
	
	public RailTeamEmployee() {
		
	}
	public RailTeamEmployee(long id, String employeeCode, long workTeam, String addUser, Date addDate, String note1,
			String note2, String note3) {
		super();
		this.id = id;
		this.employeeCode = employeeCode;
		this.workTeam = workTeam;
		this.addUser = addUser;
		this.addDate = addDate;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public long getWorkTeam() {
		return workTeam;
	}
	public void setWorkTeam(long workTeam) {
		this.workTeam = workTeam;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	public String getNote2() {
		return note2;
	}
	public void setNote2(String note2) {
		this.note2 = note2;
	}
	public String getNote3() {
		return note3;
	}
	public void setNote3(String note3) {
		this.note3 = note3;
	}
}
