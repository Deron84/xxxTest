package com.rail.po.org;

import java.util.Date;

/**
 * RailConstOrg entity. @author MyEclipse Persistence Tools
 */

public class RailConstOrg implements java.io.Serializable {

	// Fields

	private long id;
	private String costOrgName;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String enableStatus;
	private long formOrg;
    private String formOrgName;
	
	public String getFormOrgName() {
		return formOrgName;
	}

	public void setFormOrgName(String formOrgName) {
		this.formOrgName = formOrgName;
	}

	// Constructors

	/** default constructor */
	public RailConstOrg() {
	}

	/** minimal constructor */
	public RailConstOrg(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailConstOrg(long id, String costOrgName, String delStatus, String addUser, Date addDate, String updUser,
			Date updDate, String enableStatus, long formOrg) {
		this.id = id;
		this.costOrgName = costOrgName;
		this.delStatus = delStatus;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
		this.enableStatus = enableStatus;
		this.formOrg = formOrg;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCostOrgName() {
		return this.costOrgName;
	}

	public void setCostOrgName(String costOrgName) {
		this.costOrgName = costOrgName;
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

	public long getFormOrg() {
		return this.formOrg;
	}

	public void setFormOrg(long formOrg) {
		this.formOrg = formOrg;
	}

}