package com.rail.po.base;

import java.util.Date;

/**
 * RailEmployee entity. @author MyEclipse Persistence Tools
 */

public class RailEmployee implements java.io.Serializable {

	// Fields

	private Long id;
	private String employeeCode;
	private String employeeName;
	private String sex;
	private Date birthday;
	private String idNumber;
	private String employeeImg;
	private Long constOrg;
	private String job;
	private Long employeeType;
	private String employeeTel;
	private String infoSign;
	private Date addDate;
	private String addUser;
	private Date updDate;
	private String updUser;
	private String note1;//用于表示是否删除，0未删除；1已删除
	private String note2;
	private String note3;
	private String password;
	private Date entryDate;
	private String dept;
	
	private String detpName;
	private String workStatus;

	// Constructors

	/** default constructor */
	public RailEmployee() {
	}

	/** minimal constructor */
	public RailEmployee(Long id) {
		this.id = id;
	}

	
	
	public RailEmployee(Long id, String employeeCode, String employeeName, String employeeImg, String employeeTel, String note1,
			String dept) {
		super();
		this.id = id;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.employeeImg = employeeImg;
		this.employeeTel = employeeTel;
		this.note1 = note1;
		this.dept = dept;
	}

	/** full constructor */
	public RailEmployee(Long id, String employeeCode, String employeeName, String sex, Date birthday, String idNumber,
			String employeeImg, Long constOrg, String job, Long employeeType, String employeeTel, String infoSign,
			Date addDate, String addUser, Date updDate, String updUser, String note1, String note2, String note3,
			String password, Date entryDate, String dept) {
		this.id = id;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.sex = sex;
		this.birthday = birthday;
		this.idNumber = idNumber;
		this.employeeImg = employeeImg;
		this.constOrg = constOrg;
		this.job = job;
		this.employeeType = employeeType;
		this.employeeTel = employeeTel;
		this.infoSign = infoSign;
		this.addDate = addDate;
		this.addUser = addUser;
		this.updDate = updDate;
		this.updUser = updUser;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
		this.password = password;
		this.entryDate = entryDate;
		this.dept = dept;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDetpName() {
		return detpName;
	}

	public void setDetpName(String detpName) {
		this.detpName = detpName;
	}


	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getEmployeeCode() {
		return this.employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getEmployeeImg() {
		return this.employeeImg;
	}

	public void setEmployeeImg(String employeeImg) {
		this.employeeImg = employeeImg;
	}

	public Long getConstOrg() {
		return this.constOrg;
	}

	public void setConstOrg(Long constOrg) {
		this.constOrg = constOrg;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Long getEmployeeType() {
		return this.employeeType;
	}

	public void setEmployeeType(Long employeeType) {
		this.employeeType = employeeType;
	}

	public String getEmployeeTel() {
		return this.employeeTel;
	}

	public void setEmployeeTel(String employeeTel) {
		this.employeeTel = employeeTel;
	}

	public String getInfoSign() {
		return this.infoSign;
	}

	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
	}

	public Date getAddDate() {
		return this.addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getAddUser() {
		return this.addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getUpdUser() {
		return this.updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}



}