package com.rail.po.base;

import java.util.Date;

/**
 * RailArea entity. @author MyEclipse Persistence Tools
 */

public class RailArea implements java.io.Serializable {

	// Fields

	private long id;
	private String whseAreaName;
	private String delStatus;
	private String enableStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private long constOrg;

	// Constructors

	/** default constructor */
	public RailArea() {
	}

	/** minimal constructor */
	public RailArea(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailArea(long id, String whseAreaName, String delStatus, String enableStatus, String addUser, Date addDate,
			String updUser, Date updDate, long constOrg) {
		this.id = id;
		this.whseAreaName = whseAreaName;
		this.delStatus = delStatus;
		this.enableStatus = enableStatus;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
		this.constOrg = constOrg;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWhseAreaName() {
		return this.whseAreaName;
	}

	public void setWhseAreaName(String whseAreaName) {
		this.whseAreaName = whseAreaName;
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

	public long getConstOrg() {
		return this.constOrg;
	}

	public void setConstOrg(long constOrg) {
		this.constOrg = constOrg;
	}

}