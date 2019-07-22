package com.rail.po.access;

import java.util.Date;

/**
 * RailAccessOptlog entity. @author MyEclipse Persistence Tools
 */

public class RailAccessOptlog implements java.io.Serializable {

	// Fields

	private long id;
	private String accessCode;
	private String equipCode;
	private String employeeCode;
	private String workCode;
	private String infoSign;
	private String openSign;
	private String addUser;
	private Date addDate;
	private String note1;
	private String note2;
	private String note3;
	private String note4;
	private String note5;

	// Constructors

	/** default constructor */
	public RailAccessOptlog() {
	}

	/** minimal constructor */
	public RailAccessOptlog(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailAccessOptlog(long id, String accessCode, String equipCode, String employeeCode, String workCode,
			String infoSign, String openSign, String addUser, Date addDate, String note1, String note2, String note3,
			String note4, String note5) {
		this.id = id;
		this.accessCode = accessCode;
		this.equipCode = equipCode;
		this.employeeCode = employeeCode;
		this.workCode = workCode;
		this.infoSign = infoSign;
		this.openSign = openSign;
		this.addUser = addUser;
		this.addDate = addDate;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
		this.note4 = note4;
		this.note5 = note5;
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

	public String getEmployeeCode() {
		return this.employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getWorkCode() {
		return this.workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getInfoSign() {
		return this.infoSign;
	}

	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
	}

	public String getOpenSign() {
		return this.openSign;
	}

	public void setOpenSign(String openSign) {
		this.openSign = openSign;
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

	public String getNote4() {
		return this.note4;
	}

	public void setNote4(String note4) {
		this.note4 = note4;
	}

	public String getNote5() {
		return this.note5;
	}

	public void setNote5(String note5) {
		this.note5 = note5;
	}

}