package com.rail.po.tool;

import java.util.Date;

/**
 * RailToolModel entity. @author MyEclipse Persistence Tools
 */

public class RailToolModel implements java.io.Serializable {

	// Fields

	private long id;
	private String modelCode;
	private String modelName;
	private long toolName;
	private long toolUnit;
	private String toolMaterial;
	private long upperLimit;
	private long lowwerLimit;
	private String rfid;
	private long whseArea;
	private String whse;
	private String stand;
	private String floor;
	private String position;
	private String delStatus;
	private String enableStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;

	// Constructors

	/** default constructor */
	public RailToolModel() {
	}

	/** minimal constructor */
	public RailToolModel(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailToolModel(long id, String modelCode, String modelName, long toolName, long toolUnit, String toolMaterial,
			long upperLimit, long lowwerLimit, String rfid, long whseArea, String whse, String stand, String floor,
			String position, String delStatus, String enableStatus, String addUser, Date addDate, String updUser,
			Date updDate) {
		this.id = id;
		this.modelCode = modelCode;
		this.modelName = modelName;
		this.toolName = toolName;
		this.toolUnit = toolUnit;
		this.toolMaterial = toolMaterial;
		this.upperLimit = upperLimit;
		this.lowwerLimit = lowwerLimit;
		this.rfid = rfid;
		this.whseArea = whseArea;
		this.whse = whse;
		this.stand = stand;
		this.floor = floor;
		this.position = position;
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

	public String getModelCode() {
		return this.modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public long getToolName() {
		return this.toolName;
	}

	public void setToolName(long toolName) {
		this.toolName = toolName;
	}

	public long getToolUnit() {
		return this.toolUnit;
	}

	public void setToolUnit(long toolUnit) {
		this.toolUnit = toolUnit;
	}

	public String getToolMaterial() {
		return this.toolMaterial;
	}

	public void setToolMaterial(String toolMaterial) {
		this.toolMaterial = toolMaterial;
	}

	public long getUpperLimit() {
		return this.upperLimit;
	}

	public void setUpperLimit(long upperLimit) {
		this.upperLimit = upperLimit;
	}

	public long getLowwerLimit() {
		return this.lowwerLimit;
	}

	public void setLowwerLimit(long lowwerLimit) {
		this.lowwerLimit = lowwerLimit;
	}

	public String getRfid() {
		return this.rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public long getWhseArea() {
		return this.whseArea;
	}

	public void setWhseArea(long whseArea) {
		this.whseArea = whseArea;
	}

	public String getWhse() {
		return this.whse;
	}

	public void setWhse(String whse) {
		this.whse = whse;
	}

	public String getStand() {
		return this.stand;
	}

	public void setStand(String stand) {
		this.stand = stand;
	}

	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
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