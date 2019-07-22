package com.rail.po.work;

import java.util.Date;

/**
 * RailWorkImg entity. @author MyEclipse Persistence Tools
 */

public class RailWorkImg implements java.io.Serializable {

	// Fields

	private long id;
	private String workCode;
	private String imgType;
	private String imgPath;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String note1;
	private String note2;
	private String note3;

	// Constructors

	/** default constructor */
	public RailWorkImg() {
	}

	/** minimal constructor */
	public RailWorkImg(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailWorkImg(long id, String workCode, String imgType, String imgPath, String delStatus, String addUser,
			Date addDate, String updUser, Date updDate, String note1, String note2, String note3) {
		this.id = id;
		this.workCode = workCode;
		this.imgType = imgType;
		this.imgPath = imgPath;
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

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWorkCode() {
		return this.workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getImgType() {
		return this.imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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

}