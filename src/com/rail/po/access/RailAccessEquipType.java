package com.rail.po.access;

import java.util.Date;

/**
 * RailAccessEquipType entity. @author MyEclipse Persistence Tools
 */

public class RailAccessEquipType implements java.io.Serializable {

	// Fields

	private long id;
	private String equipTypeName;
	private String delStatus;
	private String enableStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;

	// Constructors

	/** default constructor */
	public RailAccessEquipType() {
	}

	/** minimal constructor */
	public RailAccessEquipType(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailAccessEquipType(long id, String equipTypeName, String delStatus, String enableStatus, String addUser,
			Date addDate, String updUser, Date updDate) {
		this.id = id;
		this.equipTypeName = equipTypeName;
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

	public String getEquipTypeName() {
		return this.equipTypeName;
	}

	public void setEquipTypeName(String equipTypeName) {
		this.equipTypeName = equipTypeName;
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

}