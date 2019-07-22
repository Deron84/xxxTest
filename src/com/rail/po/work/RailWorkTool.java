package com.rail.po.work;

import java.util.Date;

/**
 * RailWorkTool entity. @author MyEclipse Persistence Tools
 */

public class RailWorkTool implements java.io.Serializable {

	// Fields

	private Long id;
	private String workCode;
	private String toolCode;
	private String infoSign;
	private String infoSign1;
	private String infoSign2;
	private String infoSign3;
	private String infoSign4;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String note1;
	private String note2;
	private String note3;
	private Long toolName;
	
	
	

	// Constructors

	/** default constructor */
	public RailWorkTool() {
	}

	/** minimal constructor */
	public RailWorkTool(Long id) {
		this.id = id;
	}

	/** full constructor */
	public RailWorkTool(Long id, String workCode, String toolCode, String infoSign, String delStatus, String addUser,
			Date addDate, String updUser, Date updDate, String note1, String note2, String note3) {
		this.id = id;
		this.workCode = workCode;
		this.toolCode = toolCode;
		this.infoSign = infoSign;
		this.delStatus = delStatus;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkCode() {
		return this.workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getToolCode() {
		return this.toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}

	public String getInfoSign() {
		return this.infoSign;
	}

	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
	}

	public String getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
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

	public Long getToolName() {
		return toolName;
	}

	public void setToolName(Long toolName) {
		this.toolName = toolName;
	}

	public RailWorkTool(Long id, String workCode, String toolCode, String infoSign, String delStatus, String addUser,
			Date addDate, String updUser, Date updDate, String note1, String note2, String note3, Long toolName) {
		super();
		this.id = id;
		this.workCode = workCode;
		this.toolCode = toolCode;
		this.infoSign = infoSign;
		this.delStatus = delStatus;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
		this.toolName = toolName;
	}

	public String getInfoSign1() {
		return infoSign1;
	}

	public void setInfoSign1(String infoSign1) {
		this.infoSign1 = infoSign1;
	}

	public String getInfoSign2() {
		return infoSign2;
	}

	public void setInfoSign2(String infoSign2) {
		this.infoSign2 = infoSign2;
	}

	public String getInfoSign3() {
		return infoSign3;
	}

	public void setInfoSign3(String infoSign3) {
		this.infoSign3 = infoSign3;
	}

	public String getInfoSign4() {
		return infoSign4;
	}

	public void setInfoSign4(String infoSign4) {
		this.infoSign4 = infoSign4;
	}

}