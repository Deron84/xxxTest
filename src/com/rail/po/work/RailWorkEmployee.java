package com.rail.po.work;

import java.util.Date;

/**
 * RailWorkEmployee entity. @author MyEclipse Persistence Tools
 */


public class RailWorkEmployee implements java.io.Serializable {
	// Fields

	private long id;
	private String workCode;
	private String emplpoyeeCode;
	private String infoSign;
	private String infoSign1;
	private String infoSign2;
	private String infoSign3;
	private long workTeam;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String note1;
	private String note2;
	private String note3;
	private String openSign;

	// Constructors

	/** default constructor */
	public RailWorkEmployee() {
	}

	/** minimal constructor */
	public RailWorkEmployee(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailWorkEmployee(long id, String workCode, String emplpoyeeCode, String infoSign, long workTeam,
			String addUser, Date addDate, String updUser, Date updDate, String note1, String note2, String note3,
			String openSign) {
		this.id = id;
		this.workCode = workCode;
		this.emplpoyeeCode = emplpoyeeCode;
		this.infoSign = infoSign;
		this.workTeam = workTeam;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
		this.openSign = openSign;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWorkCode() {
		return this.workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getEmplpoyeeCode() {
		return this.emplpoyeeCode;
	}

	public void setEmplpoyeeCode(String emplpoyeeCode) {
		this.emplpoyeeCode = emplpoyeeCode;
	}

	public String getInfoSign() {
		return this.infoSign;
	}

	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
	}

	public long getWorkTeam() {
		return this.workTeam;
	}

	public void setWorkTeam(long workTeam) {
		this.workTeam = workTeam;
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

	public String getOpenSign() {
		return this.openSign;
	}

	public void setOpenSign(String openSign) {
		this.openSign = openSign;
	}

	public String getInfoSign1() {
		return infoSign1;
	}

	public void setInfoSign1(String infoSign1) {
		this.infoSign1 = infoSign1;
	}

	public String getInfoSign2() {
		return infoSign2;
	}

	public void setInfoSign2(String infoSign2) {
		this.infoSign2 = infoSign2;
	}

	public String getInfoSign3() {
		return infoSign3;
	}

	public void setInfoSign3(String infoSign3) {
		this.infoSign3 = infoSign3;
	}

}