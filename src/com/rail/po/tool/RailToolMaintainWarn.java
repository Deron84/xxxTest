package com.rail.po.tool;

import java.util.Date;

/**
 * RailToolMaintainWarn entity. @author MyEclipse Persistence Tools
 */

public class RailToolMaintainWarn implements java.io.Serializable {

	// Fields

	private long id;
	private String toolCode;
	private String warnMsg;
	private String infoSign;
	private Date addDate;
	private String verifyUser;
	private Date verifyDate;
	private String verifyMsg;
	private String note1;
	private String note2;
	private String note3;

	// Constructors

	/** default constructor */
	public RailToolMaintainWarn() {
	}

	/** minimal constructor */
	public RailToolMaintainWarn(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailToolMaintainWarn(long id, String toolCode, String warnMsg, String infoSign, Date addDate,
			String verifyUser, Date verifyDate, String verifyMsg, String note1, String note2, String note3) {
		this.id = id;
		this.toolCode = toolCode;
		this.warnMsg = warnMsg;
		this.infoSign = infoSign;
		this.addDate = addDate;
		this.verifyUser = verifyUser;
		this.verifyDate = verifyDate;
		this.verifyMsg = verifyMsg;
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

	public String getWarnMsg() {
		return this.warnMsg;
	}

	public void setWarnMsg(String warnMsg) {
		this.warnMsg = warnMsg;
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

	public String getVerifyUser() {
		return this.verifyUser;
	}

	public void setVerifyUser(String verifyUser) {
		this.verifyUser = verifyUser;
	}

	public Date getVerifyDate() {
		return this.verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getVerifyMsg() {
		return this.verifyMsg;
	}

	public void setVerifyMsg(String verifyMsg) {
		this.verifyMsg = verifyMsg;
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