package com.rail.po.access;

import java.util.Date;

/**
 * RailAccessInfo entity. @author MyEclipse Persistence Tools
 */

public class RailAccessInfo implements java.io.Serializable {

	// Fields

	private long id;
	private String accessCode;
	private String accessName;
	private String accessType;
	private String accessRoute;
	private String accessAddress;
	private String accessDept;
	private String accessPic;
	private String accessTel;
	private String policeOffice;
	private long examPeriod;
	private Date lastExam;
	private String accessStatus;
	private String openStatus;
	private String delStatus;
	private String warnSystem;
	private String warnWeixin;
	private String mileage;
	private String mileagePrevious;
	private String mileageNext;
	private Date installDate;
	private String longitude;
	private String latitude;
	private String whseCode;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String note1;
	private String note2;
	private String note3;
	private String note4;
	private String note5;

	// Constructors

	/** default constructor */
	public RailAccessInfo() {
	}

	/** minimal constructor */
	public RailAccessInfo(long id) {
		this.id = id;
	}

	public RailAccessInfo(String accessCode, String whseCode) {
		super();
		this.accessCode = accessCode;
		this.whseCode = whseCode;
	}
	public RailAccessInfo(String accessCode, String whseCode,String accessDept) {
		super();
		this.accessCode = accessCode;
		this.whseCode = whseCode;
		this.accessDept = accessDept;
	}
	public RailAccessInfo(String accessCode, String whseCode,String accessDept,String note1) {
		super();
		this.accessCode = accessCode;
		this.whseCode = whseCode;
		this.accessDept = accessDept;
		this.note1 = note1;
	}

	/** full constructor */
	public RailAccessInfo(long id, String accessCode, String accessName, String accessType, String accessRoute,
			String accessAddress, String accessDept, String accessPic, String accessTel, String policeOffice,
			long examPeriod, Date lastExam, String accessStatus, String openStatus, String delStatus, String warnSystem,
			String warnWeixin, String mileage, String mileagePrevious, String mileageNext, Date installDate,
			String longitude, String latitude, String whseCode, String addUser, Date addDate, String updUser,
			Date updDate, String note1, String note2, String note3, String note4, String note5) {
		this.id = id;
		this.accessCode = accessCode;
		this.accessName = accessName;
		this.accessType = accessType;
		this.accessRoute = accessRoute;
		this.accessAddress = accessAddress;
		this.accessDept = accessDept;
		this.accessPic = accessPic;
		this.accessTel = accessTel;
		this.policeOffice = policeOffice;
		this.examPeriod = examPeriod;
		this.lastExam = lastExam;
		this.accessStatus = accessStatus;
		this.openStatus = openStatus;
		this.delStatus = delStatus;
		this.warnSystem = warnSystem;
		this.warnWeixin = warnWeixin;
		this.mileage = mileage;
		this.mileagePrevious = mileagePrevious;
		this.mileageNext = mileageNext;
		this.installDate = installDate;
		this.longitude = longitude;
		this.latitude = latitude;
		this.whseCode = whseCode;
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

	public String getAccessName() {
		return this.accessName;
	}

	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}

	public String getAccessType() {
		return this.accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getAccessRoute() {
		return this.accessRoute;
	}

	public void setAccessRoute(String accessRoute) {
		this.accessRoute = accessRoute;
	}

	public String getAccessAddress() {
		return this.accessAddress;
	}

	public void setAccessAddress(String accessAddress) {
		this.accessAddress = accessAddress;
	}

	public String getAccessDept() {
		return this.accessDept;
	}

	public void setAccessDept(String accessDept) {
		this.accessDept = accessDept;
	}

	public String getAccessPic() {
		return this.accessPic;
	}

	public void setAccessPic(String accessPic) {
		this.accessPic = accessPic;
	}

	public String getAccessTel() {
		return this.accessTel;
	}

	public void setAccessTel(String accessTel) {
		this.accessTel = accessTel;
	}

	public String getPoliceOffice() {
		return this.policeOffice;
	}

	public void setPoliceOffice(String policeOffice) {
		this.policeOffice = policeOffice;
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

	public String getAccessStatus() {
		return this.accessStatus;
	}

	public void setAccessStatus(String accessStatus) {
		this.accessStatus = accessStatus;
	}

	public String getOpenStatus() {
		return this.openStatus;
	}

	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}

	public String getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getWarnSystem() {
		return this.warnSystem;
	}

	public void setWarnSystem(String warnSystem) {
		this.warnSystem = warnSystem;
	}

	public String getWarnWeixin() {
		return this.warnWeixin;
	}

	public void setWarnWeixin(String warnWeixin) {
		this.warnWeixin = warnWeixin;
	}

	public String getMileage() {
		return this.mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getMileagePrevious() {
		return this.mileagePrevious;
	}

	public void setMileagePrevious(String mileagePrevious) {
		this.mileagePrevious = mileagePrevious;
	}

	public String getMileageNext() {
		return this.mileageNext;
	}

	public void setMileageNext(String mileageNext) {
		this.mileageNext = mileageNext;
	}

	public Date getInstallDate() {
		return this.installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getWhseCode() {
		return this.whseCode;
	}

	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
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

}