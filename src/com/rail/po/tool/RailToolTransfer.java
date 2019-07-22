package com.rail.po.tool;

import java.util.Date;

/**
 * RailToolTransfer entity. @author MyEclipse Persistence Tools
 */

public class RailToolTransfer implements java.io.Serializable {

	// Fields

	private long id;
	private String toolCode;
	private String transferMsg;
	private String infoSign;
	private String whseBefore;
	private String whseAfter;
	private String addUser;
	private Date addDate;
	private String note1;
	private String note2;
	private String note3;

	// Constructors

	/** default constructor */
	public RailToolTransfer() {
	}

	/** minimal constructor */
	public RailToolTransfer(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailToolTransfer(long id, String toolCode, String transferMsg, String infoSign, String whseBefore,
			String whseAfter, String addUser, Date addDate, String note1, String note2, String note3) {
		this.id = id;
		this.toolCode = toolCode;
		this.transferMsg = transferMsg;
		this.infoSign = infoSign;
		this.whseBefore = whseBefore;
		this.whseAfter = whseAfter;
		this.addUser = addUser;
		this.addDate = addDate;
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

	public String getTransferMsg() {
		return this.transferMsg;
	}

	public void setTransferMsg(String transferMsg) {
		this.transferMsg = transferMsg;
	}

	public String getInfoSign() {
		return this.infoSign;
	}

	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
	}

	public String getWhseBefore() {
		return this.whseBefore;
	}

	public void setWhseBefore(String whseBefore) {
		this.whseBefore = whseBefore;
	}

	public String getWhseAfter() {
		return this.whseAfter;
	}

	public void setWhseAfter(String whseAfter) {
		this.whseAfter = whseAfter;
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

}