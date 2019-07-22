package com.rail.po.warehouse;

import java.util.Date;

/**
 * RailWhseTool entity. @author MyEclipse Persistence Tools
 */

public class RailWhseTool implements java.io.Serializable {

	// Fields

	private long id;
	private String whseCode;
	private String toolCode;
	private String workCode;
	private String infoSign;
	private Date addDate;
	private String note1;
	private String note2;
	private String note3;

	// Constructors

	/** default constructor */
	public RailWhseTool() {
	}

	/** minimal constructor */
	public RailWhseTool(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailWhseTool(long id, String whseCode, String toolCode, String workCode, String infoSign, Date addDate,
			String note1, String note2, String note3) {
		this.id = id;
		this.whseCode = whseCode;
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

	public String getWhseCode() {
		return this.whseCode;
	}

	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
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