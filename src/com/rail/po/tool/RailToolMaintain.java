package com.rail.po.tool;

import java.util.Date;

/**
 * RailToolMaintain entity. @author MyEclipse Persistence Tools
 */

public class RailToolMaintain implements java.io.Serializable {

	// Fields

	private long id;
	private String toolCode;
	private Date addDate;
	private String maintainUser;
	private String note1;
	private String note2;
	private String note3;

	// Constructors

	/** default constructor */
	public RailToolMaintain() {
	}

	/** minimal constructor */
	public RailToolMaintain(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailToolMaintain(long id, String toolCode, Date addDate, String maintainUser, String note1, String note2,
			String note3) {
		this.id = id;
		this.toolCode = toolCode;
		this.addDate = addDate;
		this.maintainUser = maintainUser;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToolCode() {
		return this.toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}

	public Date getAddDate() {
		return this.addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getMaintainUser() {
		return this.maintainUser;
	}

	public void setMaintainUser(String maintainUser) {
		this.maintainUser = maintainUser;
	}

	public String getNote1() {
		return this.note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	public String getNote2() {
		return this.note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	public String getNote3() {
		return this.note3;
	}

	public void setNote3(String note3) {
		this.note3 = note3;
	}

}