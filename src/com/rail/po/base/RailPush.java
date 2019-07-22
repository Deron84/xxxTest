package com.rail.po.base;

import java.sql.Clob;
import java.util.Date;

/**
 * RailPush entity. @author MyEclipse Persistence Tools
 */

public class RailPush implements java.io.Serializable {

	// Fields

	private long id;
	private String pushAddress;
	private String pushType;
	private Clob pushContent;
	private Date pushDate;
	private String note1;
	private String note2;
	private String note3;

	// Constructors

	/** default constructor */
	public RailPush() {
	}

	/** minimal constructor */
	public RailPush(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailPush(long id, String pushAddress, String pushType, Clob pushContent, Date pushDate, String note1,
			String note2, String note3) {
		this.id = id;
		this.pushAddress = pushAddress;
		this.pushType = pushType;
		this.pushContent = pushContent;
		this.pushDate = pushDate;
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

	public String getPushAddress() {
		return this.pushAddress;
	}

	public void setPushAddress(String pushAddress) {
		this.pushAddress = pushAddress;
	}

	public String getPushType() {
		return this.pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public Clob getPushContent() {
		return this.pushContent;
	}

	public void setPushContent(Clob pushContent) {
		this.pushContent = pushContent;
	}

	public Date getPushDate() {
		return this.pushDate;
	}

	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
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