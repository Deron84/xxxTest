package com.rail.po.tool;

import java.util.Date;

/**
 * RailToolInfo entity. @author MyEclipse Persistence Tools
 */

public class RailToolInfo implements java.io.Serializable {

	// Fields

	private long id;
	private String toolCode;
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

	private long toolName;
	private long toolUnit;
	private String toolMaterial;
	private String stand;
	private String floor;
	private String position;
	
	// Constructors
	
	private String whseName;
	private String modelName;
	//工具调库时用
	private String transferMsg;
	
	
	
	

	/** default constructor */
	public RailToolInfo() {
	}

	/** minimal constructor */
	public RailToolInfo(long id) {
		this.id = id;
	}
	public RailToolInfo(String toolCode) {
		this.toolCode = toolCode;
	}

	
	public RailToolInfo(long id, String toolCode, long toolName) {
		super();
		this.id = id;
		this.toolCode = toolCode;
		this.toolName = toolName;
	}
	

	public RailToolInfo(long id, String toolCode, long toolName,
			Date toolExpiration, String whseCode, String rfid, String purchaseDept, String purchaseUser, long mfrsOrg,
			String toolStatus, String delStatus, String inWhse, long examPeriod, Date lastExam, String accessCode,
			String addUser, Date addDate, String updUser, Date updDate, String note1, String listPrice,
			String initPrice, String dept) {
		super();
		this.id = id;
		this.toolCode = toolCode;
		this.toolName = toolName;
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
		this.listPrice = listPrice;
		this.initPrice = initPrice;
		this.dept = dept;
	}

	/** full constructor */
	public RailToolInfo(long id, String toolCode, long toolName, 
			Date toolExpiration, String whseCode, String rfid, String purchaseDept, String purchaseUser, long mfrsOrg,
			String toolStatus, String delStatus, String inWhse, long examPeriod, Date lastExam, String accessCode,
			String addUser, Date addDate, String updUser, Date updDate, String note1, String note2, String note3,
			String note4, String note5, String listPrice, String initPrice, String initPrice2, String initPrice3,String dept) {
		this.id = id;
		this.toolCode = toolCode;
		this.toolName = toolName;
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

	public String getTransferMsg() {
		return transferMsg;
	}

	public void setTransferMsg(String transferMsg) {
		this.transferMsg = transferMsg;
	}

	public String getToolCode() {
		return this.toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
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

	public long getToolName() {
		return toolName;
	}

	public void setToolName(long toolName) {
		this.toolName = toolName;
	}

	public long getToolUnit() {
		return toolUnit;
	}

	public void setToolUnit(long toolUnit) {
		this.toolUnit = toolUnit;
	}

	public String getToolMaterial() {
		return toolMaterial;
	}

	public void setToolMaterial(String toolMaterial) {
		this.toolMaterial = toolMaterial;
	}

	public String getStand() {
		return stand;
	}

	public void setStand(String stand) {
		this.stand = stand;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getWhseName() {
		return whseName;
	}

	public void setWhseName(String whseName) {
		this.whseName = whseName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public RailToolInfo(long id, String toolCode, Date toolExpiration, String whseCode, String rfid,
			String purchaseDept, String purchaseUser, long mfrsOrg, String toolStatus, String delStatus, String inWhse,
			long examPeriod, Date lastExam, String accessCode, String addUser, Date addDate, String updUser,
			Date updDate, String note1, String note2, String note3, String note4, String note5, String listPrice,
			String initPrice, String initPrice2, String initPrice3, String dept, long toolName, long toolUnit,
			String toolMaterial, String stand, String floor, String position, String whseName, String modelName) {
		super();
		this.id = id;
		this.toolCode = toolCode;
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
		this.dept = dept;
		this.toolName = toolName;
		this.toolUnit = toolUnit;
		this.toolMaterial = toolMaterial;
		this.stand = stand;
		this.floor = floor;
		this.position = position;
		this.whseName = whseName;
		this.modelName = modelName;
	}

}