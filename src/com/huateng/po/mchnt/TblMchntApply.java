package com.huateng.po.mchnt;

import java.io.Serializable;

public class TblMchntApply implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1424675537209334477L;

	private String applyId;
	
	private String mchntId; 
	private String applyType; 
	private String applyStatus; 
	private String applyName; 
	private String createTime; 
	private String createPerson;
	private String createPhone;
	private String auditTime; 
	private String auditId;
	private String auditPhone;
	private String remark;
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getCreatePhone() {
		return createPhone;
	}
	public void setCreatePhone(String createPhone) {
		this.createPhone = createPhone;
	}
	public String getAuditPhone() {
		return auditPhone;
	}
	public void setAuditPhone(String auditPhone) {
		this.auditPhone = auditPhone;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
}
