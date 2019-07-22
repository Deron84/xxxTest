package com.huateng.po.mchnt;

import java.io.Serializable;

public class TblMchntUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1424675537209334477L;

	private String id;
	
	private String parentId; 
	private String mchntId; 
	private String loginPwd; 
	private String status; 
	private String wechatId; 
	private String mchntPhone;
	private String mchntName;
	private String createTime; 
	private String updateTime;
	private String loginName;
	
	
	


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getMchntId() {
		return mchntId;
	}


	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}


	public String getLoginPwd() {
		return loginPwd;
	}


	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getWechatId() {
		return wechatId;
	}


	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}


	public String getMchntPhone() {
		return mchntPhone;
	}


	public void setMchntPhone(String mchntPhone) {
		this.mchntPhone = mchntPhone;
	}


	public String getMchntName() {
		return mchntName;
	}


	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
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


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
}
