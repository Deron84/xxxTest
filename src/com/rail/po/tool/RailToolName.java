package com.rail.po.tool;

import java.util.Date;

/**
 * RailToolName entity. @author MyEclipse Persistence Tools
 */

public class RailToolName implements java.io.Serializable {

	// Fields

	private long id;
	private String toolName;
	private String delStatus;
	private String enableStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String toolImg;
	private long toolType;

	// Constructors

	/** default constructor */
	public RailToolName() {
	}

	/** minimal constructor */
	public RailToolName(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailToolName(long id, String toolName, String delStatus, String enableStatus, String addUser, Date addDate,
			String updUser, Date updDate,long toolType) {
		this.id = id;
		this.toolName = toolName;
		this.delStatus = delStatus;
		this.enableStatus = enableStatus;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
		this.toolType=toolType;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToolName() {
		return this.toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
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

	public long getToolType() {
		return toolType;
	}

	public void setToolType(long toolType) {
		this.toolType = toolType;
	}

	public String getToolImg() {
		return toolImg;
	}

	public void setToolImg(String toolImg) {
		this.toolImg = toolImg;
	}

	public RailToolName(long id, String toolName, String delStatus, String enableStatus, String addUser, Date addDate,
			String updUser, Date updDate, String toolImg, long toolType) {
		super();
		this.id = id;
		this.toolName = toolName;
		this.delStatus = delStatus;
		this.enableStatus = enableStatus;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
		this.toolImg = toolImg;
		this.toolType = toolType;
	}

}