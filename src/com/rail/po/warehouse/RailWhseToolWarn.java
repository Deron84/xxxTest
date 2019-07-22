package com.rail.po.warehouse;

import java.util.Date;

/**
 * RailWhseTool entity. @author MyEclipse Persistence Tools
 */

public class RailWhseToolWarn implements java.io.Serializable {

	// Fields

	private long id;
	private String whseCode;
	private String toolName;
	private String lowerThreshold;
	private String  delStatus;
	private Date addDate;
	private String addUser;
	private String note1;
	private String note2;
	private String note3;
	// Constructors

	/** default constructor */
	public RailWhseToolWarn() {
	}

	/** minimal constructor */
	public RailWhseToolWarn(long id) {
		this.id = id;
	}

	public RailWhseToolWarn(long id, String whseCode, String toolName, String lowerThreshold, String delStatus,
			Date addDate, String addUser, String note1, String note2, String note3) {
		super();
		this.id = id;
		this.whseCode = whseCode;
		this.toolName = toolName;
		this.lowerThreshold = lowerThreshold;
		this.delStatus = delStatus;
		this.addDate = addDate;
		this.addUser = addUser;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
	}

	public String getWhseCode() {
		return whseCode;
	}

	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getLowerThreshold() {
		return lowerThreshold;
	}

	public void setLowerThreshold(String lowerThreshold) {
		this.lowerThreshold = lowerThreshold;
	}

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	
}