package com.huateng.po.mchnt;

import java.io.Serializable;

public class TblMchntDiscountRule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7601551076524326437L;

	private String discountId;
	private String discountCode;
	private String discountType;
	
	private String discountCard;
	
	private String discountValue;
	private String discountValueInfo;
	private String createTime; 
	private String createPerson;
	private String startTimeEff;
	private String endTimeEff;
	private String status;
	
	private String openType;
	private String acquirersType;
	private String openLian;
	private String isDownload;
	private String remark;
	private String issuedContent;//下发内容
	private String acquirersId;
	
	private String sumcount0;
	private String sumcount1;
	private String sumcount0l;
	private String sumcount1l;
	
	public String getDiscountId() {
		return discountId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscountCard() {
		return discountCard;
	}

	public void setDiscountCard(String discountCard) {
		this.discountCard = discountCard;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getStartTimeEff() {
		return startTimeEff;
	}

	public void setStartTimeEff(String startTimeEff) {
		this.startTimeEff = startTimeEff;
	}

	public String getEndTimeEff() {
		return endTimeEff;
	}

	public void setEndTimeEff(String endTimeEff) {
		this.endTimeEff = endTimeEff;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getAcquirersType() {
		return acquirersType;
	}

	public void setAcquirersType(String acquirersType) {
		this.acquirersType = acquirersType;
	}

	public String getOpenLian() {
		return openLian;
	}

	public void setOpenLian(String openLian) {
		this.openLian = openLian;
	}

	public String getIsDownload() {
		return isDownload;
	}

	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIssuedContent() {
		return issuedContent;
	}

	public void setIssuedContent(String issuedContent) {
		this.issuedContent = issuedContent;
	}

	public String getAcquirersId() {
		return acquirersId;
	}

	public void setAcquirersId(String acquirersId) {
		this.acquirersId = acquirersId;
	}

	public String getSumcount0() {
		return sumcount0;
	}

	public void setSumcount0(String sumcount0) {
		this.sumcount0 = sumcount0;
	}

	public String getSumcount1() {
		return sumcount1;
	}

	public void setSumcount1(String sumcount1) {
		this.sumcount1 = sumcount1;
	}

	public String getSumcount0l() {
		return sumcount0l;
	}

	public void setSumcount0l(String sumcount0l) {
		this.sumcount0l = sumcount0l;
	}

	public String getSumcount1l() {
		return sumcount1l;
	}

	public void setSumcount1l(String sumcount1l) {
		this.sumcount1l = sumcount1l;
	}
	
	
}
