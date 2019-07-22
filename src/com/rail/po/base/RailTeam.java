package com.rail.po.base;

import java.util.Date;

/**
 * RailTeam entity. @author MyEclipse Persistence Tools
 */

public class RailTeam implements java.io.Serializable {

	// Fields

	private long id;
	private String workTeamName;
	private String delStatus;
	private String enableStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String dept;

	// Constructors

	/** default constructor */
	public RailTeam() {
	}

	/** minimal constructor */
	public RailTeam(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailTeam(long id, String workTeamName, String delStatus, String enableStatus, String addUser, Date addDate,
			String updUser, Date updDate) {
		this.id = id;
		this.workTeamName = workTeamName;
		this.delStatus = delStatus;
		this.enableStatus = enableStatus;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWorkTeamName() {
		return this.workTeamName;
	}

	public void setWorkTeamName(String workTeamName) {
		this.workTeamName = workTeamName;
	}

	public String getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getEnableStatus() {
		return this.enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}

	public String getAddUser() {
		return this.addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public Date getAddDate() {
		return this.addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getUpdUser() {
		return this.updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

}