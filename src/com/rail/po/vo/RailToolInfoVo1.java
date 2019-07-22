package com.rail.po.vo;

import java.util.Date;

/**
 * RailToolInfo entity. @author MyEclipse Persistence Tools
 */

public class RailToolInfoVo1 implements java.io.Serializable {

	// Fields

	private long id;
	private String toolCode;
	private String toolName;
	private String modelCode;
	private String toolImg;
	private Date toolExpiration;
	private String whseCode;
	private String rfid;
	private String purchaseDept;
	private String purchaseUser;
	private long mfrsOrg;
	private String toolStatus;
	private String delStatus;
	private String inWhse;
	private long examPeriod;
	private Date lastExam;
	private String accessCode;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String note1;
	private String note2;
	private String note3;
	private String note4;
	private String note5;
	private String listPrice;
	private String initPrice;
	private String initPrice2;
	private String initPrice3;
	private String dept;

	// Constructors

	/** default constructor */
	public RailToolInfoVo1() {
	}

	/** minimal constructor */
	public RailToolInfoVo1(long id) {
		this.id = id;
	}
	public RailToolInfoVo1(String toolCode) {
		this.toolCode = toolCode;
	}

	/** full constructor */
	public RailToolInfoVo1(long id, String toolCode, String toolName, String modelCode, String toolImg,
			Date toolExpiration, String whseCode, String rfid, String purchaseDept, String purchaseUser, long mfrsOrg,
			String toolStatus, String delStatus, String inWhse, long examPeriod, Date lastExam, String accessCode,
			String addUser, Date addDate, String updUser, Date updDate, String note1, String note2, String note3,
			String note4, String note5, String listPrice, String initPrice, String initPrice2, String initPrice3,String dept) {
		this.id = id;
		this.toolCode = toolCode;
		this.toolName = toolName;
		this.modelCode = modelCode;
		this.toolImg = toolImg;
		this.toolExpiration = toolExpiration;
		this.whseCode = whseCode;
		this.rfid = rfid;
		this.purchaseDept = purchaseDept;
		this.purchaseUser = purchaseUser;
		this.mfrsOrg = mfrsOrg;
		this.toolStatus = toolStatus;
		this.delStatus = delStatus;
		this.inWhse = inWhse;
		this.examPeriod = examPeriod;
		this.lastExam = lastExam;
		this.accessCode = accessCode;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
		this.note4 = note4;
		this.note5 = note5;
		this.listPrice = listPrice;
		this.initPrice = initPrice;
		this.initPrice2 = initPrice2;
		this.initPrice3 = initPrice3;
		this.dept=dept;
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

	public String getToolName() {
		return this.toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getModelCode() {
		return this.modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getToolImg() {
		return this.toolImg;
	}

	public void setToolImg(String toolImg) {
		this.toolImg = toolImg;
	}

	public Date getToolExpiration() {
		return this.toolExpiration;
	}

	public void setToolExpiration(Date toolExpiration) {
		this.toolExpiration = toolExpiration;
	}

	public String getWhseCode() {
		return this.whseCode;
	}

	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
	}

	public String getRfid() {
		return this.rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public String getPurchaseDept() {
		return this.purchaseDept;
	}

	public void setPurchaseDept(String purchaseDept) {
		this.purchaseDept = purchaseDept;
	}

	public String getPurchaseUser() {
		return this.purchaseUser;
	}

	public void setPurchaseUser(String purchaseUser) {
		this.purchaseUser = purchaseUser;
	}

	public long getMfrsOrg() {
		return this.mfrsOrg;
	}

	public void setMfrsOrg(long mfrsOrg) {
		this.mfrsOrg = mfrsOrg;
	}

	public String getToolStatus() {
		return this.toolStatus;
	}

	public void setToolStatus(String toolStatus) {
		this.toolStatus = toolStatus;
	}

	public String getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getInWhse() {
		return this.inWhse;
	}

	public void setInWhse(String inWhse) {
		this.inWhse = inWhse;
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

	public String getAccessCode() {
		return this.accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
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

	public String getListPrice() {
		return this.listPrice;
	}

	public void setListPrice(String listPrice) {
		this.listPrice = listPrice;
	}

	public String getInitPrice() {
		return this.initPrice;
	}

	public void setInitPrice(String initPrice) {
		this.initPrice = initPrice;
	}

	public String getInitPrice2() {
		return this.initPrice2;
	}

	public void setInitPrice2(String initPrice2) {
		this.initPrice2 = initPrice2;
	}

	public String getInitPrice3() {
		return this.initPrice3;
	}

	public void setInitPrice3(String initPrice3) {
		this.initPrice3 = initPrice3;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

}