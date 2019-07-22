package com.rail.po.access;

import java.util.Date;

/**
 * RailAccessEquipInfo entity. @author MyEclipse Persistence Tools
 */

public class RailAccessEquipInfo implements java.io.Serializable {

	// Fields

	private long id;
	private String accessCode;
	private String equipCode;
	private String equipName;
	private long equipType;
	private String equipIp;
	private long mfrsOrg;
	private String mfrsTel;
	private Date expirationDate;
	private Date installDate;
	private long examPeriod;
	private Date lastExam;
	private String equipStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String note1;
	private String note2;
	private String note3;
	private String note4;
	private String note5;
	private long defence;

	// Constructors

	/** default constructor */
	public RailAccessEquipInfo() {
	}

	/** minimal constructor */
	public RailAccessEquipInfo(long id) {
		this.id = id;
	}

	
	public RailAccessEquipInfo(String accessCode, String equipCode) {
		super();
		this.accessCode = accessCode;
		this.equipCode = equipCode;
	}

	/** full constructor */
	public RailAccessEquipInfo(long id, String accessCode, String equipCode, String equipName, long equipType,
			String equipIp, long mfrsOrg, String mfrsTel, Date expirationDate, Date installDate, long examPeriod,
			Date lastExam, String equipStatus, String addUser, Date addDate, String updUser, Date updDate, String note1,
			String note2, String note3, String note4, String note5) {
		this.id = id;
		this.accessCode = accessCode;
		this.equipCode = equipCode;
		this.equipName = equipName;
		this.equipType = equipType;
		this.equipIp = equipIp;
		this.mfrsOrg = mfrsOrg;
		this.mfrsTel = mfrsTel;
		this.expirationDate = expirationDate;
		this.installDate = installDate;
		this.examPeriod = examPeriod;
		this.lastExam = lastExam;
		this.equipStatus = equipStatus;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
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

	public String getEquipName() {
		return this.equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public long getEquipType() {
		return this.equipType;
	}

	public void setEquipType(long equipType) {
		this.equipType = equipType;
	}

	public String getEquipIp() {
		return this.equipIp;
	}

	public void setEquipIp(String equipIp) {
		this.equipIp = equipIp;
	}

	public long getMfrsOrg() {
		return this.mfrsOrg;
	}

	public void setMfrsOrg(long mfrsOrg) {
		this.mfrsOrg = mfrsOrg;
	}

	public String getMfrsTel() {
		return this.mfrsTel;
	}

	public void setMfrsTel(String mfrsTel) {
		this.mfrsTel = mfrsTel;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getInstallDate() {
		return this.installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	public long getExamPeriod() {
		return this.examPeriod;
	}

	public void setExamPeriod(long examPeriod) {
		this.examPeriod = examPeriod;
	}

	public Date getLastExam() {
		return this.lastExam;
	}

	public void setLastExam(Date lastExam) {
		this.lastExam = lastExam;
	}

	public String getEquipStatus() {
		return this.equipStatus;
	}

	public void setEquipStatus(String equipStatus) {
		this.equipStatus = equipStatus;
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

	public long getDefence() {
		return defence;
	}

	public void setDefence(long defence) {
		this.defence = defence;
	}

	public RailAccessEquipInfo(long id, String accessCode, String equipCode, String equipName, long equipType,
			String equipIp, long mfrsOrg, String mfrsTel, Date expirationDate, Date installDate, long examPeriod,
			Date lastExam, String equipStatus, String addUser, Date addDate, String updUser, Date updDate, String note1,
			String note2, String note3, String note4, String note5, long defence) {
		super();
		this.id = id;
		this.accessCode = accessCode;
		this.equipCode = equipCode;
		this.equipName = equipName;
		this.equipType = equipType;
		this.equipIp = equipIp;
		this.mfrsOrg = mfrsOrg;
		this.mfrsTel = mfrsTel;
		this.expirationDate = expirationDate;
		this.installDate = installDate;
		this.examPeriod = examPeriod;
		this.lastExam = lastExam;
		this.equipStatus = equipStatus;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
		this.note4 = note4;
		this.note5 = note5;
		this.defence = defence;
	}

}