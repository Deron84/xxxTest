package com.rail.po.org;

import java.util.Date;

/**
 * RailFormOrg entity. @author MyEclipse Persistence Tools
 */

public class RailFormOrg implements java.io.Serializable {

	// Fields

	private long id;
	private String formOrgName;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String enableStatus;

	// Constructors

	/** default constructor */
	public RailFormOrg() {
	}

	/** minimal constructor */
	public RailFormOrg(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailFormOrg(long id, String formOrgName, String delStatus, String addUser, Date addDate, String updUser,
			Date updDate, String enableStatus) {
		this.id = id;
		this.formOrgName = formOrgName;
		this.delStatus = delStatus;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
		this.enableStatus = enableStatus;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFormOrgName() {
		return this.formOrgName;
	}

	public void setFormOrgName(String formOrgName) {
		this.formOrgName = formOrgName;
	}

	public String getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
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

	public String getEnableStatus() {
		return this.enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}

}