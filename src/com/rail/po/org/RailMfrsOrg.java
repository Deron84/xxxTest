package com.rail.po.org;

import java.util.Date;

/**
 * RailMfrsOrg entity. @author MyEclipse Persistence Tools
 */

public class RailMfrsOrg implements java.io.Serializable {

	// Fields

	private long id;
	private String mfrsOrgName;
	private String delStatus;
	private String enableStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String mfrsAddress;
	private String mfrsPic;
	private String mfrsTel;
	private String mfrsFax;

	// Constructors

	/** default constructor */
	public RailMfrsOrg() {
	}

	/** minimal constructor */
	public RailMfrsOrg(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailMfrsOrg(long id, String mfrsOrgName, String delStatus, String enableStatus, String addUser, Date addDate,
			String updUser, Date updDate, String mfrsAddress, String mfrsPic, String mfrsTel, String mfrsFax) {
		this.id = id;
		this.mfrsOrgName = mfrsOrgName;
		this.delStatus = delStatus;
		this.enableStatus = enableStatus;
		this.addUser = addUser;
		this.addDate = addDate;
		this.updUser = updUser;
		this.updDate = updDate;
		this.mfrsAddress = mfrsAddress;
		this.mfrsPic = mfrsPic;
		this.mfrsTel = mfrsTel;
		this.mfrsFax = mfrsFax;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMfrsOrgName() {
		return this.mfrsOrgName;
	}

	public void setMfrsOrgName(String mfrsOrgName) {
		this.mfrsOrgName = mfrsOrgName;
	}

	public String getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getEnableStatus() {
		return this.enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
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

	public String getMfrsAddress() {
		return this.mfrsAddress;
	}

	public void setMfrsAddress(String mfrsAddress) {
		this.mfrsAddress = mfrsAddress;
	}

	public String getMfrsPic() {
		return this.mfrsPic;
	}

	public void setMfrsPic(String mfrsPic) {
		this.mfrsPic = mfrsPic;
	}

	public String getMfrsTel() {
		return this.mfrsTel;
	}

	public void setMfrsTel(String mfrsTel) {
		this.mfrsTel = mfrsTel;
	}

	public String getMfrsFax() {
		return this.mfrsFax;
	}

	public void setMfrsFax(String mfrsFax) {
		this.mfrsFax = mfrsFax;
	}

}