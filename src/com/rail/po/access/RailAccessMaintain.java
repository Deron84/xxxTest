package com.rail.po.access;

import java.util.Date;

/**
 * RailAccessMaintain entity. @author MyEclipse Persistence Tools
 */

public class RailAccessMaintain implements java.io.Serializable {

	// Fields

	private long id;
	private String accessCode;
	private String equipCode;
	private Date addDate;
	private String maintainUser;
	private String note1;
	private String note2;
	private String note3;

	// Constructors

	/** default constructor */
	public RailAccessMaintain() {
	}

	/** minimal constructor */
	public RailAccessMaintain(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailAccessMaintain(long id, String accessCode, String equipCode, Date addDate, String maintainUser,
			String note1, String note2, String note3) {
		this.id = id;
		this.accessCode = accessCode;
		this.equipCode = equipCode;
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

	public String getAccessCode() {
		return this.accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getEquipCode() {
		return this.equipCode;
	}

	public void setEquipCode(String equipCode) {
		this.equipCode = equipCode;
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