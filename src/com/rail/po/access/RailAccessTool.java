package com.rail.po.access;

import java.util.Date;

/**
 * RailAccessTool entity. @author MyEclipse Persistence Tools
 */

public class RailAccessTool implements java.io.Serializable {

	// Fields

	private long id;
	private String accessCode;
	private String toolCode;
	private String workCode;
	private String infoSign;
	private Date addDate;
	private String note1;
	private String note2;
	private String note3;

	// Constructors

	/** default constructor */
	public RailAccessTool() {
	}

	/** minimal constructor */
	public RailAccessTool(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailAccessTool(long id, String accessCode, String toolCode, String workCode, String infoSign, Date addDate,
			String note1, String note2, String note3) {
		this.id = id;
		this.accessCode = accessCode;
		this.toolCode = toolCode;
		this.workCode = workCode;
		this.infoSign = infoSign;
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

	public String getAccessCode() {
		return this.accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getToolCode() {
		return this.toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
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