package com.rail.po.warehouse;

import java.util.Date;

/**
 * RailWhseInfo entity. @author MyEclipse Persistence Tools
 */

public class RailWhseInfo implements java.io.Serializable {

	// Fields

	private long id;
	private String whseCode;//仓库编码
	private String whseName;//仓库名称
	private long whseAreaId;//仓库分区关联ID
	private String whseAddress;//仓库地址
	private String whseRank;//仓库等级
	private String whseCapa;//仓库容量
	private String whsePic;//仓库负责人
	private String whseTel;//仓库联系电话
	private String whseDept;//仓库所属部门
	private String accessCode;//门禁关联ID
	private String delStatus;//是否删除
	private String enableStatus;//仓库状态，0开启；1停用
	private String addUser;//创建人ID
	private Date addDate;//创建日期
	private String updUser;//更新人id
	private Date updDate;//更新时间
	private String note1;//以下为预留字段
	private String note2;
	private String note3;
	private String note4;
	private String note5;
	private String parentWhseCode;//上级仓库标识
	
	
	private String whseAreaName;//分区名称
	private String whseBrhName;//部门名称
	private String parentWhse;//上级仓库名称

	// Constructors

	/** default constructor */
	public RailWhseInfo() {
	}

	/** minimal constructor */
	public RailWhseInfo(long id) {
		this.id = id;
	}

	/** full constructor */
	public RailWhseInfo(long id, String whseCode, String whseName, long whseAreaId, String whseAddress, String whseRank,
			String whseCapa, String whsePic, String whseTel, String whseDept, String accessCode, String delStatus,
			String enableStatus, String addUser, Date addDate, String updUser, Date updDate, String note1, String note2,
			String note3, String note4, String note5) {
		this.id = id;
		this.whseCode = whseCode;
		this.whseName = whseName;
		this.whseAreaId = whseAreaId;
		this.whseAddress = whseAddress;
		this.whseRank = whseRank;
		this.whseCapa = whseCapa;
		this.whsePic = whsePic;
		this.whseTel = whseTel;
		this.whseDept = whseDept;
		this.accessCode = accessCode;
		this.delStatus = delStatus;
		this.enableStatus = enableStatus;
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

	public String getWhseCode() {
		return this.whseCode;
	}

	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
	}

	public String getWhseName() {
		return this.whseName;
	}

	public void setWhseName(String whseName) {
		this.whseName = whseName;
	}

	public long getWhseAreaId() {
		return this.whseAreaId;
	}

	public void setWhseAreaId(long whseAreaId) {
		this.whseAreaId = whseAreaId;
	}

	public String getWhseAddress() {
		return this.whseAddress;
	}

	public void setWhseAddress(String whseAddress) {
		this.whseAddress = whseAddress;
	}

	public String getWhseRank() {
		return this.whseRank;
	}

	public void setWhseRank(String whseRank) {
		this.whseRank = whseRank;
	}

	public String getWhseCapa() {
		return this.whseCapa;
	}

	public void setWhseCapa(String whseCapa) {
		this.whseCapa = whseCapa;
	}

	public String getWhsePic() {
		return this.whsePic;
	}

	public void setWhsePic(String whsePic) {
		this.whsePic = whsePic;
	}

	public String getWhseTel() {
		return this.whseTel;
	}

	public void setWhseTel(String whseTel) {
		this.whseTel = whseTel;
	}

	public String getWhseDept() {
		return this.whseDept;
	}

	public void setWhseDept(String whseDept) {
		this.whseDept = whseDept;
	}

	public String getAccessCode() {
		return this.accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
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

	public String getWhseAreaName() {
		return whseAreaName;
	}

	public void setWhseAreaName(String whseAreaName) {
		this.whseAreaName = whseAreaName;
	}

	public String getWhseBrhName() {
		return whseBrhName;
	}

	public void setWhseBrhName(String whseBrhName) {
		this.whseBrhName = whseBrhName;
	}

	public String getParentWhseCode() {
		return parentWhseCode;
	}

	public void setParentWhseCode(String parentWhseCode) {
		this.parentWhseCode = parentWhseCode;
	}

	public String getParentWhse() {
		return parentWhse;
	}

	public void setParentWhse(String parentWhse) {
		this.parentWhse = parentWhse;
	}

}