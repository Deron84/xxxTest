package com.rail.po.tool;

import java.util.Date;

/**
 * RailToolScrap entity. @author MyEclipse Persistence Tools
 */

public class RailToolScrap implements java.io.Serializable {

	// Fields

	private long id;
	private String toolCode;
	private String applyMsg;
	private String applyUser;
	private Date applyDate;
	private String infoSign;
	private String verifyUser;
	private Date verifyDate;
	private String verifyMsg;
	private String note1;
	private String note2;
	private String note3;

	// Constructors

	/** default constructor */
	public RailToolScrap() {
	}

	/** minimal constructor */
	public RailToolScrap(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailToolScrap(long id, String toolCode, String applyMsg, String applyUser, Date applyDate, String infoSign,
			String verifyUser, Date verifyDate, String verifyMsg, String note1, String note2, String note3) {
		this.id = id;
		this.toolCode = toolCode;
		this.applyMsg = applyMsg;
		this.applyUser = applyUser;
		this.applyDate = applyDate;
		this.infoSign = infoSign;
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

	public String getApplyMsg() {
		return this.applyMsg;
	}

	public void setApplyMsg(String applyMsg) {
		this.applyMsg = applyMsg;
	}

	public String getApplyUser() {
		return this.applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getInfoSign() {
		return this.infoSign;
	}

	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
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