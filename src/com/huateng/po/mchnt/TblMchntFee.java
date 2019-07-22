package com.huateng.po.mchnt;

import java.io.Serializable;

public class TblMchntFee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7601551076524326437L;

	private String mchntId;
	
	private String feeType;
	
	private String feeRate;
	
	private String feeMin;
	private String feeMax;
	private String createTime; 
	private String updateTime;
	private String operator;
	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getFeeRate() {
		return feeRate;
	}
	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}
	public String getFeeMin() {
		return feeMin;
	}
	public void setFeeMin(String feeMin) {
		this.feeMin = feeMin;
	}
	public String getFeeMax() {
		return feeMax;
	}
	public void setFeeMax(String feeMax) {
		this.feeMax = feeMax;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
