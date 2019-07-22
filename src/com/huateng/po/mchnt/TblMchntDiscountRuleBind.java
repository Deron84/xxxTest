package com.huateng.po.mchnt;

import java.io.Serializable;

public class TblMchntDiscountRuleBind implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8489933827713998216L;

	//主键
    private TblMchntDiscountRuleBindPK id;
    private String mchntId;	
	private String equipmentId;	
	private String acquirersId;	
    private String discountId;
    private String createTime;
    private String mchntName;
    private String discountValue;
    private String discountValueInfo;
	
	private String activityId;
	private String activityDesc;
	private String startTime;
	private String endTime;
	private String isOpen;
	private String createPerson;
	private String acquirersType;
	
	public TblMchntDiscountRuleBindPK getId() {
		return id;
	}

	public void setId(TblMchntDiscountRuleBindPK id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMchntName() {
		return mchntName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public String getDiscountValueInfo() {
		return discountValueInfo;
	}

	public void setDiscountValueInfo(String discountValueInfo) {
		this.discountValueInfo = discountValueInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDiscountId() {
		return discountId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getAcquirersType() {
		return acquirersType;
	}

	public void setAcquirersType(String acquirersType) {
		this.acquirersType = acquirersType;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getAcquirersId() {
		return acquirersId;
	}

	public void setAcquirersId(String acquirersId) {
		this.acquirersId = acquirersId;
	}
	
	
}
